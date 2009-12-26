package ui.actions;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Grafico.MainWindow;
import Grafico.Help.AboutWindow;


public class ViewAboutAction implements SelectionListener{
    
    private MainWindow mainWindow;
    
    public ViewAboutAction(MainWindow mainWindow ) {
	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	AboutWindow acercade = new AboutWindow();
	acercade.createWindow(mainWindow.display);
	acercade.showWindow();
    }

}
