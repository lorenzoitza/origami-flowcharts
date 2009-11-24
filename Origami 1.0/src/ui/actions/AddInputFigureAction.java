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
import Grafico.Figuras.InputFigure;
import Imagenes.ImageLoader;


public class AddInputFigureAction implements SelectionListener{
 
    private MainWindow mainWindow;
    
    public AddInputFigureAction(MainWindow mainWindow ) {

	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent event) {
	mainWindow.getComponents().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorEntrada.png").getImageData(), 0, 0);
	InputFigure inputFigure = new InputFigure();
	inputFigure.instruction.instruccion = "null";
	MainWindow.mainFigure = null;
	MainWindow.mainFigure = inputFigure;
	mainWindow.disableCursor();
    }

}
