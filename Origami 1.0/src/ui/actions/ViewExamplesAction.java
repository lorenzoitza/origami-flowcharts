package ui.actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;

import Grafico.MainWindow;

public class ViewExamplesAction implements SelectionListener{
    private MainWindow mainWindow;
    
    public ViewExamplesAction(MainWindow mainWindow ) {
	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	    FileDialog dialog = new FileDialog(mainWindow.getShell(),SWT.OPEN);
	    dialog.setFilterExtensions(new String[] { "*.Org","*.*" });
	    dialog.setFilterPath("ejemplos\\");
	    String archivo = dialog.open();
	    if(archivo != null){
	    	if(MainWindow.getComponentes()._diagrams.getHoja().getSizeDiagrama()==0){
	    		String archivo2 = dialog.getFileName();
			    int pos = archivo2.indexOf('.');
			    String name = archivo2.substring(0, pos);
			    MainWindow.getComponentes()._diagrams.cambiarNombre(name);
			    MainWindow.getComponentes()._diagrams.abrir(archivo,mainWindow.getSerializer());
	    	}
		    else{
		    	MainWindow._selectionAdministrator.setFiguraSeleccionada(0);
		    	MainWindow.getComponentes()._diagrams.getHoja().openFile(archivo);
				archivo = dialog.getFileName();
				int pos = archivo.indexOf('.');
				String name = archivo.substring(0, pos);
				MainWindow.getComponentes()._diagrams.cambiarNombre(name);
		    }
	    }
    }

}
