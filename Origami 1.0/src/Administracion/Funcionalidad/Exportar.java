package Administracion.Funcionalidad;

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
import Administracion.*;
import Administracion.Funcionalidad.Codigo.Instruccion;
import Grafico.MainWindow;
import Grafico.Figuras.*;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Exportar {
	private TabFolder tab;
	
	public Exportar(TabFolder tabfolder){
		tab = tabfolder;
	}
	public Vector<Figura> getFiguras(Vector<Figura> diagrama){
		Vector<Figura> diag = new Vector<Figura>();
		for(int x =0;x<diagrama.size();x++){
			if(diagrama.elementAt(x) instanceof DecisionFigure){
				DecisionFigure ellipse = new DecisionFigure();
				ellipse.setBounds(diagrama.elementAt(x).getBounds());
				DecisionFigure figuras = (DecisionFigure)diagrama.elementAt(x);
				ellipse.instruction = figuras.instruction;
				diag.add(ellipse);
			}
			else if(diagrama.elementAt(x) instanceof ForFigure ){
				ForFigure ellipse = new ForFigure();
				ellipse.setBounds(diagrama.elementAt(x).getBounds());
				ForFigure figuras = (ForFigure)diagrama.elementAt(x);
				ellipse.instruction = figuras.instruction;
				diag.add(ellipse);
			}
			else if(diagrama.elementAt(x) instanceof WhileFigure){
				WhileFigure ellipse = new WhileFigure();
				ellipse.setBounds(diagrama.elementAt(x).getBounds());
				WhileFigure figuras = (WhileFigure)diagrama.elementAt(x);
				ellipse.instruccion = figuras.instruccion;
				diag.add(ellipse);
			}
			else if(diagrama.elementAt(x) instanceof SentenceFigure){
				SentenceFigure ellipse = new SentenceFigure();
				ellipse.setBounds(diagrama.elementAt(x).getBounds());
				SentenceFigure figuras = (SentenceFigure)diagrama.elementAt(x);
				ellipse.instruccion = figuras.instruccion;
				diag.add(ellipse);
			}
			else if(diagrama.elementAt(x) instanceof InputFigure){
				InputFigure ellipse = new InputFigure();
				ellipse.setBounds(diagrama.elementAt(x).getBounds());
				InputFigure figuras = (InputFigure)diagrama.elementAt(x);
				ellipse.instruction = figuras.instruction;
				diag.add(ellipse);
			}
			else if(diagrama.elementAt(x) instanceof OutputFigure){
				OutputFigure ellipse = new OutputFigure();
				ellipse.setBounds(diagrama.elementAt(x).getBounds());
				OutputFigure figuras = (OutputFigure)diagrama.elementAt(x);
				ellipse.instruction = figuras.instruction;
				diag.add(ellipse);
			}
			else if(diagrama.elementAt(x) instanceof CircleFigure){
				CircleFigure ellipse = new CircleFigure();
				ellipse.setBounds(diagrama.elementAt(x).getBounds());
				CircleFigure figuras = (CircleFigure)diagrama.elementAt(x);
				ellipse.setMensagge(figuras.getMensagge());
				diag.add(ellipse);
			}
			else if(diagrama.elementAt(x) instanceof Elipse){
				Elipse ellipse = new Elipse();
				ellipse.setBounds(diagrama.elementAt(x).getBounds());
				diag.add(ellipse);
			}	
			else if(diagrama.elementAt(x) instanceof DecisionFigureEnd){
				DecisionFigureEnd ellipse = new DecisionFigureEnd();
				ellipse.setBounds(diagrama.elementAt(x).getBounds());
				diag.add(ellipse);
			}	
		}
		return diag;
	}
	public void exportarJpg(String fileDir,Vector<Figura> diagrama,Conexion conexion){
		Vector<Figura> diag = getFiguras(diagrama);
		byte[] data = createImage(diag,conexion,SWT.IMAGE_BMP);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(fileDir);
			fos.write(data);
			fos.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	private DibujarDiagrama getSize(Vector<Figura> diagrama,Conexion conexion){
		int xIzq=diagrama.elementAt(0).getBounds().x,xDer=diagrama.elementAt(0).getBounds().x;
		int yAbajo=diagrama.lastElement().getBounds().y;
		int yArriba=diagrama.elementAt(0).getBounds().y;
		int altura,ancho;
		for(int x=1;x<diagrama.size();x++){
			if(diagrama.elementAt(x).getBounds().x < xIzq){
				xIzq = diagrama.elementAt(x).getBounds().x;
			}
			else if(diagrama.elementAt(x).getBounds().x > xDer){
				xDer = diagrama.elementAt(x).getBounds().x;
			}
		}
		ancho=xDer-xIzq;
		altura=yAbajo-yArriba;
		DibujarDiagrama panel = new DibujarDiagrama(ancho,altura);
		if(ancho<=0){
			ancho=50;
		}
		else{
			ancho=(int)(ancho/1.5);
		}
		panel.agregarFigurasExportar(diagrama,panel,ancho,50);
		Conexion conex = new Conexion(tab);
		conex.crearConexiones(diagrama);
		panel.agregarConexiones(conex.getConexion(),panel);
		return panel;
	}
	public Shell shell;
	public Figure getPan(Figure contents){
		shell = new Shell(MainWindow._display);
		shell.setBounds(0,0,contents.getBounds().width+40,contents.getBounds().height+30);
		Canvas composite = new Canvas(shell,SWT.NONE);
		composite.setBounds(0,0,contents.getBounds().width+40,contents.getBounds().height+30);
		LightweightSystem lws = new LightweightSystem(composite);
		lws.setContents(contents);
		shell.open();
		shell.setVisible(false);
		return contents;
	}
	private byte[] createImage(Vector<Figura> diagrama,Conexion conexion, int format){
		Figure figure = getSize(diagrama,conexion);
		Rectangle r = figure.getBounds();
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		Image image = null;
		GC gc = null;
		Graphics g = null;
		figure=getPan(figure);
		try {
			image = new Image(MainWindow._display, r.width, r.height);
			gc = new GC(image);
			g = new SWTGraphics(gc);
			g.translate(r.x * -1, r.y * -1);
			figure.paint(g);
			ImageLoader imageLoader = new ImageLoader();
			imageLoader.data = new ImageData[] { image.getImageData() };
			imageLoader.save(result, format);
		} 
		finally {
			if (g != null) {
				g.dispose();
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
	
	public void exportarCodigoC(String dir){
		Instruccion codigos = new Instruccion();
		String source2 = "",source[] = codigos.getCodigoC(tab.getHoja().getDiagrama()).split("\n");
		for(int i=0; i<source.length; i++){
			source[i] = source[i] + "\r\n";
			source2 = source2 + source[i];
		}
		//source.replaceAll("\n","\r\n");
		guardar(dir,source2);
	}
	public void exportarCodigoCpp(String dir){
		Instruccion codigos = new Instruccion();
		String source2 = "",source[] = codigos.getCodigoC(tab.getHoja().getDiagrama()).split("\n");
		for(int i=0; i<source.length; i++){
			source[i] = source[i] + "\r\n";
			source2 = source2 + source[i];
		}
		//source.replaceAll("\n","\r\n");
		guardar(dir,source2);
	}
	public void exportarEjecutable(String dir,String nombre){
		Compilar ejecutable = new Compilar(tab);
		boolean crear = ejecutable.crearExe(nombre);
		if(!crear){
			moverArchivoEjecutable(nombre+".exe",dir);
		}
		else{
			mensajeDeError();
		}
	}
	public void exportarInfo(String dir){
		String contenido="";
		tab.getTabItem().getInfo().addTime();
		for(int i=0; i<tab.getTabItem().getInfo().getInfo().size(); i++){
			contenido = contenido + tab.getTabItem().getInfo().getInfo().elementAt(i);
		}
		guardar(dir,contenido);
		tab.getTabItem().getInfo().removeTime();
	}
	private void guardar(String dir,String contenido){
		FileWriter fichero;
        PrintWriter pw;
        try
        {
            fichero = new FileWriter(dir);
            pw = new PrintWriter(fichero);
            pw.println(contenido);
            pw.close();
            fichero.close();

        } catch (Exception e) {
            e.printStackTrace();
        } 
	}
	private void moverArchivoEjecutable(String origen,String destino){
		try{
			FileInputStream fis = new FileInputStream(origen);
			FileOutputStream fos = new FileOutputStream(destino);
			FileChannel canalFuente = fis.getChannel();
			FileChannel canalDestino = fos.getChannel();
			canalFuente.transferTo(0, canalFuente.size(), canalDestino);
			fis.close();
			fos.close();
			File file2 = new File(origen);
			file2.delete();
		}catch(IOException e){}	
	}
	private void mensajeDeError(){
		MessageBox messageBox = new MessageBox(MainWindow._shell, SWT.ICON_ERROR | SWT.OK);
		messageBox.setText("Origami");
		messageBox.setMessage("Error al exportar ejecutable verifique su sintaxis");
		messageBox.open();
	}
}
