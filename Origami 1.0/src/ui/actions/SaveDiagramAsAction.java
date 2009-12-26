package ui.actions;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import Grafico.MainWindow;
import Grafico.view.SaveFileView;
import Grafico.view.SaveType;


public class SaveDiagramAsAction implements SelectionListener{
    
    public SaveDiagramAsAction() {
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	if(MainWindow.getComponents().eje != null && MainWindow.getComponents().getEnEjecucion() && MainWindow.getComponents()._diagrams.getSelectedTabItemId() == MainWindow.getComponents().eje.a.GetId()){
	    MainWindow.getComponents().stopEjecucion();
	}
	else if(MainWindow.getComponents().paso != null && MainWindow.getComponents().getEnEjecucion() && MainWindow.getComponents()._diagrams.getSelectedTabItemId() == MainWindow.getComponents().paso.a.GetId()){
	    MainWindow.getComponents().stopEjecucion();
	}
	MainWindow.getComponents().disablePasoAPaso(false);
	
	SaveFileView save = new SaveFileView();
	save.setSaveType(SaveType.SAVEAS);
	save.createWindow();
	
    }
}
