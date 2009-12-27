package ui.listener;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import Administracion.Funcionalidad.Exporter;
import Grafico.MainWindow;
import Grafico.view.SaveFileView;
import Grafico.view.SaveType;


public class ExportToCAction implements SelectionListener{
    
    public ExportToCAction() {
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	SaveFileView save = new SaveFileView();
	save.setSaveType(SaveType.EXPORTC);
    }

}
