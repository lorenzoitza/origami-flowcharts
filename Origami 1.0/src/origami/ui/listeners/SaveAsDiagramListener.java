package origami.ui.listeners;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import origami.graphics.MainWindow;
import origami.graphics.StepByStepComponents;
import origami.graphics.view.SaveFileView;
import origami.graphics.view.SaveType;



public class SaveAsDiagramListener implements SelectionListener{
    
    public SaveAsDiagramListener() {
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	if(MainWindow.getComponents().getByStepComponents().getEje() != null && MainWindow.getComponents().getByStepComponents().getEnEjecucion(MainWindow.getComponents()) && MainWindow.getComponents()._diagrams.getSelectedTabItemId() == MainWindow.getComponents().getByStepComponents().getEje().tabItemSelected.GetId()){
	    MainWindow.getComponents().getByStepComponents().stopEjecucion(MainWindow.getComponents());
	}
	else if(MainWindow.getComponents().getByStepComponents().getPaso() != null && MainWindow.getComponents().getByStepComponents().getEnEjecucion(MainWindow.getComponents()) && MainWindow.getComponents()._diagrams.getSelectedTabItemId() == MainWindow.getComponents().getByStepComponents().getPaso().a.GetId()){
	    MainWindow.getComponents().getByStepComponents().stopEjecucion(MainWindow.getComponents());
	}
	MainWindow.getComponents().getByStepComponents().disablePasoAPaso(MainWindow.getComponents(), false);
	
	SaveFileView save = new SaveFileView();
	save.setSaveType(SaveType.SAVEAS);
	save.createWindow();
	
    }
}
