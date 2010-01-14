package origami.ui.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;

import origami.administration.ApplicationState;
import origami.administration.actions.AddFigureLogic;
import origami.images.ImageLoader;



public class AddOutputFigureListener implements SelectionListener{
    
    private Display display;
    
    public AddOutputFigureListener(Display display){
	this.display = display;
    }
    
    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent event) {
	ApplicationState.cursor[0] = new Cursor(display, ImageLoader.getImage("cursorSalida.png").getImageData(), 0, 0);
	new AddFigureLogic().addOutput();
	new AddFigureLogic().disableCursor();
    }

}