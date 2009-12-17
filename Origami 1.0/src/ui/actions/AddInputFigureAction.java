package ui.actions;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;

import Administracion.actions.AddFigureLogic;
import Grafico.MainWindow;
import Imagenes.ImageLoader;

public class AddInputFigureAction implements SelectionListener{
    
    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent event) {
	MainWindow.getComponents().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorEntrada.png").getImageData(), 0, 0);
	new AddFigureLogic().addInput();
	MainWindow.getComponents().disableCursor();
    }

}
