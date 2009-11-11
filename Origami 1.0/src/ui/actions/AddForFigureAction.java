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
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.MainWindow;
import Grafico.Figuras.ForFigure;
import Imagenes.ImageLoader;


public class AddForFigureAction implements SelectionListener{
    
    private MainWindow _mainWindow;
    
    public AddForFigureAction(MainWindow mainWindow ) {
	_mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	_mainWindow.getComponents().cursor[0] = new Cursor(MainWindow._display, ImageLoader.getImage("cursorFor.png").getImageData(), 0, 0);
	ForFigure For2 = new ForFigure();
	InstruccionSimple codigo = new InstruccionSimple();
	codigo.setInstruccionSimple("null");
	For2.instruction.instruccion.add(0,codigo);
	MainWindow._mainFigure = null;
	MainWindow._mainFigure = For2;
	MainWindow.bandera = false;
	_mainWindow.disableCursor();
    }

}
