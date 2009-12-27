package ui.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;

import Administracion.actions.AddFigureLogic;
import Grafico.MainWindow;
import Imagenes.ImageLoader;

public class AddOutputFigureAction implements SelectionListener{
    
    private Display display;
    
    public AddOutputFigureAction(Display display){
	this.display = display;
    }
    
    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent event) {
	MainWindow.cursor[0] = new Cursor(display, ImageLoader.getImage("cursorSalida.png").getImageData(), 0, 0);
	new AddFigureLogic().addOutput();
	new AddFigureLogic().disableCursor();
    }

}
