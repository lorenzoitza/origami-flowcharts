package origami.graphics.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import origami.graphics.MainWindow;
import origami.graphics.Help42.AboutWindow;

public class ViewAboutListener implements SelectionListener{
    
    public ViewAboutListener() {
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	AboutWindow about = new AboutWindow();
	about.createWindow(MainWindow.display);
	about.showWindow();
    }

}
