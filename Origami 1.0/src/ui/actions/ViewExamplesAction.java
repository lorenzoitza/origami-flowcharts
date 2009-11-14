package ui.actions;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import Administracion.TabFolder;
import Administracion.Funcionalidad.Compilar;
import Administracion.Funcionalidad.Exportar;
import Administracion.Funcionalidad.Codigo.Instruccion;
import Grafico.MainWindow;


public class ViewExamplesAction implements SelectionListener{
    private TabFolder diagrams;
    private MainWindow mainWindow;
    
    public ViewExamplesAction(TabFolder diagrams, MainWindow mainWindow ) {
	this.diagrams = diagrams;
	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	    FileDialog dialog = new FileDialog(mainWindow.getShell(),SWT.OPEN);
	    dialog.setFilterExtensions(new String[] { "*.Org","*.*" });
	    dialog.setFilterPath("ejemplos\\");
	    String archivo = dialog.open();
	    if(archivo != null){
	    	if(diagrams.getHoja().getSizeDiagrama()==0){
	    		String archivo2 = dialog.getFileName();
			    int pos = archivo2.indexOf('.');
			    String name = archivo2.substring(0, pos);
			    diagrams.cambiarNombre(name);
			    diagrams.abrir(archivo,mainWindow.getSerializer());
	    	}
		    else{
		    	mainWindow.selectionAdministrator.setFiguraSeleccionada(0);
				diagrams.getHoja().openFile(archivo);
				archivo = dialog.getFileName();
				int pos = archivo.indexOf('.');
				String name = archivo.substring(0, pos);
				diagrams.cambiarNombre(name);
		    }
	    }		
	
    }

}
