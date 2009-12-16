package ui.actions;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;

import Grafico.MainWindow;
import Grafico.Figuras.InputFigure;
import Imagenes.ImageLoader;

public class AddInputFigureAction implements SelectionListener{
    
    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent event) {
	MainWindow.getComponents().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorEntrada.png").getImageData(), 0, 0);
	InputFigure inputFigure = new InputFigure();
	inputFigure.instruction.instruccion = "null";
	MainWindow.mainFigure = null;
	MainWindow.mainFigure = inputFigure;
	MainWindow.getComponents().disableCursor();
    }

}
