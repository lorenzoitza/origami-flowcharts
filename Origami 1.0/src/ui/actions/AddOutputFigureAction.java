package ui.actions;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import Administracion.TabFolder;
import Administracion.Funcionalidad.Exporter;
import Grafico.MainWindow;
import Grafico.Figuras.OutputFigure;
import Imagenes.ImageLoader;


public class AddOutputFigureAction implements SelectionListener{
  
    private MainWindow mainWindow;
    
    public AddOutputFigureAction(MainWindow mainWindow ) {
	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	mainWindow.getComponents().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorSalida.png").getImageData(), 0, 0);
	OutputFigure salida2 = new OutputFigure();
	salida2.instruction.instruccion = "null";
	MainWindow.mainFigure = null;
	MainWindow.mainFigure = salida2;
	mainWindow.disableCursor();
    }

}
