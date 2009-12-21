package ui.actions;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Grafico.MainWindow;
import Grafico.view.SaveFileView;
import Grafico.view.SaveType;


public class SaveDiagramAction implements SelectionListener{
    
    public SaveDiagramAction(MainWindow mainWindow ) {
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	if(MainWindow.getComponentes()._diagrams.getTabItem().getSave().getDir()=="null"){
	    SaveFileView save = new SaveFileView();
	    save.setSaveType(SaveType.SAVE);
	    save.createWindow();
	}
	else{
	    MainWindow.getSerializer().setFile(MainWindow.getComponentes()._diagrams.getTabItem().getSave().getDir());
	    MainWindow.getSerializer().saveDiagram(MainWindow.getComponentes()._diagrams);
	    MainWindow.getComponentes()._diagrams.getTabItem().getSave().setSave(true);
	}
	
    }

}
