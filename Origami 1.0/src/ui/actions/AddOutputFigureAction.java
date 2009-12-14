package ui.actions;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;

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
    }

    @Override
    public void widgetSelected(SelectionEvent event) {
	mainWindow.getComponents().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorSalida.png").getImageData(), 0, 0);
	OutputFigure outPutFigure = new OutputFigure();
	outPutFigure.instruction.instruccion = "null";
	MainWindow.mainFigure = null;
	MainWindow.mainFigure = outPutFigure;
	mainWindow.disableCursor();
    }

}
