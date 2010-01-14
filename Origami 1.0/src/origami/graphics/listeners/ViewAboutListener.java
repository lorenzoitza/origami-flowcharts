package origami.graphics.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import origami.graphics.MainWindow;
import origami.graphics.Help.AboutWindow;



public class ViewAboutListener implements SelectionListener{
    
    public ViewAboutListener() {
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	AboutWindow acercade = new AboutWindow();
	acercade.createWindow(MainWindow.display);
	acercade.showWindow();
    }

}
