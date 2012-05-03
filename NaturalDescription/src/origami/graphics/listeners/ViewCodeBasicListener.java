package origami.graphics.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import origami.administration.functionality.code.ManagerCodeFormat;
import origami.graphics.MainWindow;
import origami.graphics.view.DiagramCodeView;

public class ViewCodeBasicListener implements SelectionListener{
    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	ManagerCodeFormat manager = new ManagerCodeFormat(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama());
	manager.formatCodeBasic();
	DiagramCodeView view = new DiagramCodeView(manager.getInstructionsFormat());
	view.createWindow();
	view.show();
    }

}
