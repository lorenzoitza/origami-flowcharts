package ui.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MenuItem;

import Grafico.MainWindow;

public class ViewConsoleAction implements SelectionListener{
    
    public ViewConsoleAction() {
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
	MenuItem widget = (MenuItem)e.widget;
	MainWindow.getComponents().moverConsola(widget.getSelection());
    }

}
