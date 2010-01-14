package origami.graphics.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MenuItem;

import origami.graphics.MainWindow;


public class ViewFiguresBarListener implements SelectionListener{
    
    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
	MenuItem widget = (MenuItem)e.widget;
	MainWindow.getComponents().setFiguresToolBar(widget.getSelection());
    }

}
