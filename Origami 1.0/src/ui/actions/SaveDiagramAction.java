package ui.actions;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Grafico.MainWindow;
import Grafico.view.SaveFileView;
import Grafico.view.SaveType;


public class SaveDiagramAction implements SelectionListener{
    
    public SaveDiagramAction() {
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
