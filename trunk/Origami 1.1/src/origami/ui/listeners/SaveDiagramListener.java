package origami.ui.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import origami.graphics.MainWindow;
import origami.graphics.view.SaveFileView;
import origami.graphics.view.SaveType;



public class SaveDiagramListener implements SelectionListener{
    
    public SaveDiagramListener() {
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	SaveFileView save = new SaveFileView();
	if(MainWindow.getComponents()._diagrams.getTabItem().getSave().getDir()=="null"){
	    save.setSaveType(SaveType.SAVE);
	    save.createWindow();
	}
	else{
	    save.saveAction();
	}
    }

}
