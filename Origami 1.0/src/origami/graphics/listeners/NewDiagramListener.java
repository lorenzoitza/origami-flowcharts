package origami.graphics.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import origami.administration.actions.NewDiagramLogic;

public class NewDiagramListener implements SelectionListener{
   
    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	new NewDiagramLogic().addTab();
    }

}
