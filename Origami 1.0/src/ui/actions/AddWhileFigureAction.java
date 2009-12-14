package ui.actions;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;

import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.MainWindow;
import Grafico.Figuras.WhileFigure;
import Imagenes.ImageLoader;


public class AddWhileFigureAction implements SelectionListener{
   
    private MainWindow mainWindow;
    
    public AddWhileFigureAction(MainWindow mainWindow ) {
	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent event) {
	mainWindow.getComponents().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorWhile.png").getImageData(), 0, 0);
	WhileFigure whileFigure = new WhileFigure();
	InstruccionSimple instructionCode = new InstruccionSimple();
	instructionCode.setInstruccionSimple("null");
	whileFigure.instruccion.instruccion.add(0,instructionCode);
	MainWindow.mainFigure = null;
	MainWindow.mainFigure = whileFigure;
	mainWindow.disableCursor();
    }

}
