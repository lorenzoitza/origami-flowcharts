package ui.actions;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import Administracion.TabFolder;
import Administracion.Funcionalidad.Exportar;
import Administracion.Funcionalidad.Codigo.Instruccion;
import Grafico.MainWindow;
import Grafico.Help.AboutWindow;


public class ViewAboutAction implements SelectionListener{
    
    private MainWindow mainWindow;
    
    public ViewAboutAction(MainWindow mainWindow ) {
	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	AboutWindow acercade = new AboutWindow();
	acercade.createWindow(mainWindow.display);
	acercade.showWindow();
    }

}
