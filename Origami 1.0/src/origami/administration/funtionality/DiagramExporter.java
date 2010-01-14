package origami.administration.funtionality;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Vector;
import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import origami.administration.*;
import origami.administration.funtionality.code.ManagerCodeFormat;
import origami.graphics.MainWindow;
import origami.graphics.PaintDiagram;
import origami.graphics.figures.*;
import origami.graphics.widgets.TabFolder;


/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class DiagramExporter {
	
	private TabFolder selectedTab;
	
	private Shell shell;

	public DiagramExporter(TabFolder tabfolder) {
		selectedTab = tabfolder;
	}

	public Vector<Figura> getFiguras(Vector<Figura> diagrama) {
		Vector<Figura> diagramResult = new Vector<Figura>();
		for (int x = 0; x < diagrama.size(); x++) {
			if (diagrama.elementAt(x) instanceof DecisionFigure) {
				DecisionFigure decisionFigure = (DecisionFigure) diagrama.elementAt(x);
				diagramResult.add(decisionFigure);
			} else if (diagrama.elementAt(x) instanceof ForFigure) {
				ForFigure forFigure = (ForFigure) diagrama.elementAt(x);
				diagramResult.add(forFigure);
			} else if (diagrama.elementAt(x) instanceof WhileFigure) {
				WhileFigure whileFigure = (WhileFigure) diagrama.elementAt(x);
				diagramResult.add(whileFigure);
			} else if (diagrama.elementAt(x) instanceof SentenceFigure) {
				SentenceFigure sentenceFigure = (SentenceFigure) diagrama.elementAt(x);
				diagramResult.add(sentenceFigure);
			} else if (diagrama.elementAt(x) instanceof InputFigure) {
				InputFigure inputFigure = (InputFigure) diagrama.elementAt(x);
				diagramResult.add(inputFigure);
			} else if (diagrama.elementAt(x) instanceof OutputFigure) {
				OutputFigure outputFigure = (OutputFigure) diagrama.elementAt(x);
				diagramResult.add(outputFigure);
			} else if (diagrama.elementAt(x) instanceof CircleFigure) {
				CircleFigure circleFigure = new CircleFigure();
				circleFigure.setBounds(diagrama.elementAt(x).getBounds());
				CircleFigure figuras = (CircleFigure) diagrama.elementAt(x);
				circleFigure.setMessage(figuras.getMessage());
				diagramResult.add(circleFigure);
			} else if (diagrama.elementAt(x) instanceof EllipseFigure) {
				EllipseFigure ellipse = new EllipseFigure();
				ellipse.setBounds(diagrama.elementAt(x).getBounds());
				diagramResult.add(ellipse);
			} else if (diagrama.elementAt(x) instanceof DecisionFigureEnd) {
				DecisionFigureEnd desicionFigureEnd = new DecisionFigureEnd();
				desicionFigureEnd.setBounds(diagrama.elementAt(x).getBounds());
				diagramResult.add(desicionFigureEnd);
			}
		}
		return diagramResult;
	}

	public void exportJPGFile(String fileDirectory, Vector<Figura> sourceDiagram,
			Conexion connection) {
		
		Vector<Figura> exportedDiagram = getFiguras(sourceDiagram);
		
		byte[] data = createImage(exportedDiagram, connection, SWT.IMAGE_BMP);
		
		FileOutputStream outputStream;
		
		try {
			outputStream = new FileOutputStream(fileDirectory);
			outputStream.write(data);
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private PaintDiagram getSize(Vector<Figura> sourceDiagram, Conexion connection) {
		
		int x1Coord = sourceDiagram.elementAt(0).getBounds().x;
		
		int x2Coord = sourceDiagram.elementAt(0).getBounds().x;
		
		int y1Coord = sourceDiagram.lastElement().getBounds().y;
		
		int y2Coord = sourceDiagram.elementAt(0).getBounds().y;
		
		for (int diagramIndex = 1; diagramIndex < sourceDiagram.size(); diagramIndex++) {
			
			if (sourceDiagram.elementAt(diagramIndex).getBounds().x < x1Coord) {
				
				x1Coord = sourceDiagram.elementAt(diagramIndex).getBounds().x;
			} else if (sourceDiagram.elementAt(diagramIndex).getBounds().x > x2Coord) {
				x2Coord = sourceDiagram.elementAt(diagramIndex).getBounds().x;
			}
		}
		int width = x2Coord - x1Coord;
		int height = y1Coord - y2Coord;
		PaintDiagram panel = new PaintDiagram(width, height);
		
		if (width <= 0) {
			
			width = 50;
		} else {
			width = (int) (width / 1.5);
		}
		panel.agregarFigurasExportar(sourceDiagram, panel, width, 50);
		Conexion conex = new Conexion(selectedTab);
		conex.crearConexiones(sourceDiagram);
		panel.agregarConexiones(conex.getConexion(), panel);
		
		return panel;
	}

	private Figure getPanel(Figure contents) {
		
		shell = new Shell(MainWindow.display);
		shell.setBounds(0, 0, contents.getBounds().width + 40, contents
				.getBounds().height + 30);
		
		Canvas composite = new Canvas(shell, SWT.NONE);
		composite.setBounds(0, 0, contents.getBounds().width + 40, contents
				.getBounds().height + 30);
		
		LightweightSystem lightweightSystem = new LightweightSystem(composite);
		lightweightSystem.setContents(contents);
		
		shell.open();
		shell.setVisible(false);
		return contents;
	}

	private byte[] createImage(Vector<Figura> diagrama, Conexion conexion,
			int format) {
		
		Figure figure = getSize(diagrama, conexion);
		
		Rectangle rectangle = figure.getBounds();
		
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		
		Image image = null;
		
		GC gc = null;
		
		Graphics graphics = null;
		
		getPanel(figure);
		try {
			image = new Image(MainWindow.display, rectangle.width, rectangle.height);
			gc = new GC(image);
			graphics = new SWTGraphics(gc);
			graphics.translate(rectangle.x * -1, rectangle.y * -1);
			figure.paint(graphics);
			ImageLoader imageLoader = new ImageLoader();
			imageLoader.data = new ImageData[] { image.getImageData() };
			imageLoader.save(result, format);
		} finally {
			if (graphics != null) {
				graphics.dispose();
			}
			if (gc != null) {
				gc.dispose();
			}
			if (image != null) {
				image.dispose();
			}
		}
		return result.toByteArray();
	}

	public void codeCExport(String adress) {
	    
		String content = "";
		
		ManagerCodeFormat manager = new ManagerCodeFormat(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
		manager.formatCodeC();
		
		String[] source = manager.getInstructionsFormat().split("\n");
		
		
		for (int i = 0; i < source.length; i++) {
			source[i] = source[i] + "\r\n";
			content = content + source[i];
		}

		save(adress, content);
	}

	public void codeCppExport(String adress) {
		
		String content = "";
		
		ManagerCodeFormat manager = new ManagerCodeFormat(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
		manager.formatCodeCpp();
		
		String[] source = manager.getInstructionsFormat().split("\n");
		
		for (int i = 0; i < source.length; i++) {
			
			source[i] = source[i] + "\r\n";
			content = content + source[i];
		}
		
		save(adress, content);
	}

	public void executeFileExport(String adress, String name) {
		
		CodeCompiler executeFile = new CodeCompiler(selectedTab);
		
		boolean crear = executeFile.createExecuteFile(name);
		
		if (!crear) {
			
			executeFileMover(name + ".exe", adress);
		} else {
			errorMessage();
		}
	}

	public void infomationExport(String adress) {
		String content = "";
		
		selectedTab.getTabItem().getInformation().addTime();
		for (int i = 0; i < selectedTab.getTabItem().getInformation().getInfo().size(); i++) {
			
			content += selectedTab.getTabItem().getInformation().getInfo().elementAt(i);
		}
		save(adress, content);
		selectedTab.getTabItem().getInformation().removeTime();
	}

	private void save(String adress, String content) {
		FileWriter writer;
		PrintWriter printer;
		try {
			writer = new FileWriter(adress);
			printer = new PrintWriter(writer);
			printer.println(content);
			printer.close();
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void executeFileMover(String sourceAdress, String destinyAdress) {
		
		try {
			FileInputStream input = new FileInputStream(sourceAdress);
			
			FileOutputStream output = new FileOutputStream(destinyAdress);
			
			FileChannel sourceChannel = input.getChannel();
			
			FileChannel destinyChannel = output.getChannel();
			
			sourceChannel.transferTo(0, sourceChannel.size(), destinyChannel);
			input.close();
			output.close();
			new File(sourceAdress).delete();
		} catch (IOException e) {
		}
	}

	private void errorMessage() {
		MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_ERROR | SWT.OK);
		messageBox.setText("Origami");
		messageBox.setMessage("Error al exportar ejecutable verifique su sintaxis");
		messageBox.open();
	}
}
