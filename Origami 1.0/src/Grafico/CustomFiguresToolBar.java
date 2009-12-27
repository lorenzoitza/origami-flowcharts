package Grafico;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;

import ui.listener.AddDecisionFigureAction;
import ui.listener.AddForFigureAction;
import ui.listener.AddInputFigureAction;
import ui.listener.AddOutputFigureAction;
import ui.listener.AddSentenceFigureAction;
import ui.listener.AddWhileFigureAction;
import ui.listener.KeyEvent;
import Imagenes.ImageLoader;


public class CustomFiguresToolBar {
    public ToolBar barraFiguras;
    private Button boton[] = new Button[7];
    private Display display;
    
    public CustomFiguresToolBar(GridData figurasData, Display display) {
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
	
	this.display = display;
    }
    
    public void getBotones(ToolBar toolbar) {
	boton[0] = new Button(toolbar, SWT.FLAT);
	boton[0].setImage(ImageLoader.getImage("Entrada.png"));
	boton[0].pack();
	boton[0].setToolTipText("Entrada");
	boton[0].addSelectionListener(new AddInputFigureAction(display));
	boton[0].addKeyListener(new KeyEvent());
	
	boton[1] = new Button(toolbar, SWT.FLAT);
	boton[1].setImage(ImageLoader.getImage("Proceso.png"));
	boton[1].pack();
	boton[1].setToolTipText("Expresin");
	boton[1].addSelectionListener(new AddSentenceFigureAction(display));
	boton[1].addKeyListener(new KeyEvent());
	
	boton[2] = new Button(toolbar, SWT.FLAT);
	boton[2].setImage(ImageLoader.getImage("If.png"));
	boton[2].pack();
	boton[2].setToolTipText("Decisin");
	boton[2].addSelectionListener(new AddDecisionFigureAction(display));
	boton[2].addKeyListener(new KeyEvent());
	
	boton[3] = new Button(toolbar, SWT.FLAT);
	boton[3].setImage(ImageLoader.getImage("While.png"));
	boton[3].pack();
	boton[3].setToolTipText("Ciclo Mientras");
	boton[3].addSelectionListener(new AddWhileFigureAction(display));
	boton[3].addKeyListener(new KeyEvent());
	
	boton[4] = new Button(toolbar, SWT.FLAT);
	boton[4].setImage(ImageLoader.getImage("For.png"));
	boton[4].pack();
	boton[4].setToolTipText("Ciclo Para");
	boton[4].addSelectionListener(new AddForFigureAction(display));
	boton[4].addKeyListener(new KeyEvent());
	
	boton[5] = new Button(toolbar, SWT.FLAT);
	boton[5].setImage(ImageLoader.getImage("Salida.png"));
	boton[5].pack();
	boton[5].setToolTipText("Salida");
	boton[5].addSelectionListener(new AddOutputFigureAction(display));
	boton[5].addKeyListener(new KeyEvent());
	
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
				MainWindow.getComponents().moverConsola(false);
			} else {
			    MainWindow.getComponents().moverConsola(true);
				MainWindow.menu.consoleMenuItem.setSelection(true);
			}
		}
	});
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
