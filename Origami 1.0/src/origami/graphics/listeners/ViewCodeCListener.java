package origami.graphics.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import origami.administration.funtionality.code.ManagerCodeFormat;
import origami.graphics.MainWindow;
import origami.graphics.view.DiagramCodeView;




public class ViewCodeCListener implements SelectionListener{

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	ManagerCodeFormat manager = new ManagerCodeFormat(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama());
	manager.formatCodeC();
	DiagramCodeView view = new DiagramCodeView(manager.getInstructionsFormat());
	view.createWindow();
	view.show();
    }

}
