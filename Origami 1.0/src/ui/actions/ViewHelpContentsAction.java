package ui.actions;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Grafico.Help.HelpWindow;

public class ViewHelpContentsAction implements SelectionListener{

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	HelpWindow helpWindow = new HelpWindow();
	helpWindow.createWindow();
	helpWindow.showWindow();		
    }

}
