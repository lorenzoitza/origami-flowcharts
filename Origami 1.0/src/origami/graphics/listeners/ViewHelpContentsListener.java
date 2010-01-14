package origami.graphics.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import origami.graphics.Help.HelpWindow;

public class ViewHelpContentsListener implements SelectionListener{

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	HelpWindow helpWindow = new HelpWindow();
	helpWindow.showWindow();		
    }

}
