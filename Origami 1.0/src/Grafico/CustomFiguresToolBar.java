package Grafico;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;
import Imagenes.ImageLoader;


public class CustomFiguresToolBar {
    public ToolBar barraFiguras;
    Button boton[] = new Button[7];
    
    public CustomFiguresToolBar(GridData figurasData, MainWindow mainWindow) {
	GridLayout layout2 = new GridLayout();
	layout2.numColumns = 1;
	layout2.horizontalSpacing = 0;
	layout2.verticalSpacing = 3;
	layout2.marginWidth = layout2.marginHeight = 0;
	barraFiguras = new ToolBar(MainWindow.shell, SWT.LEFT | SWT.FLAT
		| SWT.BORDER);
	barraFiguras.setLayoutData(figurasData);
	figurasData.widthHint = 62;
	barraFiguras.setCursor(new Cursor(null, SWT.CURSOR_ARROW));
	getBotones(barraFiguras);
	barraFiguras.setLayout(layout2);
    }
    public void getBotones(ToolBar toolbar) {
	boton[0] = new Button(toolbar, SWT.FLAT);
	boton[0].setImage(ImageLoader.getImage("Entrada.png"));
	boton[0].pack();
	boton[0].setToolTipText("Entrada");
	boton[0].addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
		    MainWindow.getComponentes().cursor[0] = new Cursor(MainWindow.display, ImageLoader
					.getImage("cursorEntrada.png").getImageData(), 0, 0);
			InputFigure entrada2 = new InputFigure();
			entrada2.instruction.instruccion = "null";
			MainWindow.mainFigure = null;
			MainWindow.mainFigure = entrada2;
			MainWindow.getComponentes().disableCursor();
		}
	});
	boton[1] = new Button(toolbar, SWT.FLAT);
	boton[1].setImage(ImageLoader.getImage("Proceso.png"));
	boton[1].pack();
	boton[1].setToolTipText("Expresin");
	boton[1].addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
		    MainWindow.getComponentes().cursor[0] = new Cursor(MainWindow.display, ImageLoader
					.getImage("cursorProceso.png").getImageData(), 0, 0);
			SentenceFigure proceso2 = new SentenceFigure();
			proceso2.instruccion.instruccion = "null";
			MainWindow.mainFigure = null;
			MainWindow.mainFigure = proceso2;
			MainWindow.getComponentes().disableCursor();
		}
	});
	boton[2] = new Button(toolbar, SWT.FLAT);
	boton[2].setImage(ImageLoader.getImage("If.png"));
	boton[2].pack();
	boton[2].setToolTipText("Decisin");
	boton[2].addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
		    MainWindow.getComponentes().cursor[0] = new Cursor(MainWindow.display, ImageLoader
					.getImage("cursorIf.png").getImageData(), 0, 0);
			DecisionFigure decision2 = new DecisionFigure();
			InstruccionSimple codigo = new InstruccionSimple();
			codigo.setInstruccionSimple("null");
			decision2.instruction.instruccion.add(0, codigo);
			MainWindow.mainFigure = null;
			MainWindow.mainFigure = decision2;
			MainWindow.getComponentes().disableCursor();
		}
	});
	boton[3] = new Button(toolbar, SWT.FLAT);
	boton[3].setImage(ImageLoader.getImage("While.png"));
	boton[3].pack();
	boton[3].setToolTipText("Ciclo Mientras");
	boton[3].addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
		    MainWindow.getComponentes().cursor[0] = new Cursor(MainWindow.display, ImageLoader
					.getImage("cursorWhile.png").getImageData(), 0, 0);
			WhileFigure While2 = new WhileFigure();
			InstruccionSimple codigo = new InstruccionSimple();
			codigo.setInstruccionSimple("null");
			While2.instruccion.instruccion.add(0, codigo);
			MainWindow.mainFigure = null;
			MainWindow.mainFigure = While2;
			MainWindow.getComponentes().disableCursor();
		}
	});
	boton[4] = new Button(toolbar, SWT.FLAT);
	boton[4].setImage(ImageLoader.getImage("For.png"));
	boton[4].pack();
	boton[4].setToolTipText("Ciclo Para");
	boton[4].addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
		    MainWindow.getComponentes().cursor[0] = new Cursor(MainWindow.display, ImageLoader
					.getImage("cursorFor.png").getImageData(), 0, 0);
			ForFigure For2 = new ForFigure();
			InstruccionSimple codigo = new InstruccionSimple();
			codigo.setInstruccionSimple("null");
			For2.instruction.instruccion.add(0, codigo);
			MainWindow.mainFigure = null;
			MainWindow.mainFigure = For2;
			MainWindow.getComponentes().disableCursor();
		}
	});
	boton[5] = new Button(toolbar, SWT.FLAT);
	boton[5].setImage(ImageLoader.getImage("Salida.png"));
	boton[5].pack();
	boton[5].setToolTipText("Salida");
	boton[5].addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
		    MainWindow.getComponentes().cursor[0] = new Cursor(MainWindow.display, ImageLoader
					.getImage("cursorSalida.png").getImageData(), 0, 0);
			OutputFigure salida2 = new OutputFigure();
			salida2.instruction.instruccion = "null";
			MainWindow.mainFigure = null;
			MainWindow.mainFigure = salida2;
			MainWindow.getComponentes().disableCursor();
		}
	});
	Label label = new Label(toolbar, SWT.NONE);
	label.setVisible(false);
	GridData labelData = new GridData();
	labelData.grabExcessVerticalSpace = true;
	label.setLayoutData(labelData);

	Button botonConsola = new Button(toolbar, SWT.FLAT);
	botonConsola.setBounds(40, 600, 50, 30);
	botonConsola.setImage(ImageLoader.getImage("consola.ico"));
	botonConsola.pack();
	botonConsola.setToolTipText("Consola ");
	botonConsola.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			if (MainWindow.menu.consoleMenuItem.getSelection()) {
				MainWindow.menu.consoleMenuItem.setSelection(false);
				MainWindow.getComponentes().moverConsola(false);
			} else {
			    MainWindow.getComponentes().moverConsola(true);
				MainWindow.menu.consoleMenuItem.setSelection(true);
			}
		}
	});
	for (int i = 0; i <= 5; i++) {
		boton[i].addKeyListener(new org.eclipse.swt.events.KeyAdapter() {
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				MainWindow._keyEvent.setKey(e);
				MainWindow._keyEvent.Accion();
			}
		});
	}
    }
    public void disablePasoAPaso(boolean disable) {
	if (disable) {
	    boton[0].setEnabled(false);
	    boton[1].setEnabled(false);
	    boton[2].setEnabled(false);
	    boton[3].setEnabled(false);
	    boton[4].setEnabled(false);
	    boton[5].setEnabled(false);

	} else {
	    boton[0].setEnabled(true);
	    boton[1].setEnabled(true);
	    boton[2].setEnabled(true);
	    boton[3].setEnabled(true);
	    boton[4].setEnabled(true);
	    boton[5].setEnabled(true);
	}
    }
    public void disableAll(boolean disable) {
	boton[0].setEnabled(disable);
	boton[1].setEnabled(disable);
	boton[2].setEnabled(disable);
	boton[3].setEnabled(disable);
	boton[4].setEnabled(disable);
	boton[5].setEnabled(disable);
    }
}
