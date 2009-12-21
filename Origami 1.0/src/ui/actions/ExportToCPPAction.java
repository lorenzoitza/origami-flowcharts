package ui.actions;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Grafico.MainWindow;
import Grafico.view.SaveFileView;
import Grafico.view.SaveType;

public class ExportToCPPAction implements SelectionListener{
    
    public ExportToCPPAction(MainWindow mainWindow ) {
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	SaveFileView save = new SaveFileView();
	save.setSaveType(SaveType.EXPORTCPP);
	save.createWindow();
    }

}
