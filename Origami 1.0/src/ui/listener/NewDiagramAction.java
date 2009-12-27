package ui.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Administracion.actions.NewDiagramLogic;

public class NewDiagramAction implements SelectionListener{
   
    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	new NewDiagramLogic().action();
    }

}
