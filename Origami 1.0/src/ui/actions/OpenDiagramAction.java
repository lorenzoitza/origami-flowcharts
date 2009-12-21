package ui.actions;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;

import Grafico.MainWindow;
import Grafico.view.OpenFileView;
import Grafico.view.OpenType;

public class OpenDiagramAction implements SelectionListener{

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	OpenFileView open = new OpenFileView();
	open.setOpenType(OpenType.OPEN);
	open.createWindow();
    }

}
