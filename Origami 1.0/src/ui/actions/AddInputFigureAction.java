package ui.actions;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import Administracion.TabFolder;
import Administracion.Funcionalidad.Exportar;
import Grafico.MainWindow;
import Grafico.Figuras.InputFigure;
import Imagenes.ImageLoader;


public class AddInputFigureAction implements SelectionListener{
 
    private MainWindow _mainWindow;
    
    public AddInputFigureAction(MainWindow mainWindow ) {

	_mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	_mainWindow.getComponents().cursor[0] = new Cursor(MainWindow._display, ImageLoader.getImage("cursorEntrada.png").getImageData(), 0, 0);
	InputFigure entrada2 = new InputFigure();
	entrada2.instruction.instruccion = "null";
	MainWindow._mainFigure = null;
	MainWindow._mainFigure = entrada2;
	MainWindow.bandera = false;
	_mainWindow.disableCursor();
    }

}
