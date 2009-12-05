package ui.actions;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import Administracion.TabFolder;
import Administracion.Funcionalidad.Exporter;
import Grafico.MainWindow;


public class OpenDiagramAction implements SelectionListener{
    private TabFolder diagrams;
    private MainWindow mainWindow;
    
    public OpenDiagramAction(TabFolder diagrams, MainWindow mainWindow ) {
	this.diagrams = diagrams;
	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	if(MainWindow.getComponents().eje != null && MainWindow.getComponents().getEnEjecucion()
		&& diagrams.getSelectedTabItemId() == MainWindow.getComponents().eje.a.GetId()){
	MainWindow.getComponents().stopEjecucion();
	} else if(MainWindow.getComponents().paso != null && MainWindow.getComponents().getEnEjecucion()
		&& diagrams.getSelectedTabItemId() == MainWindow.getComponents().paso.a.GetId()){
	    MainWindow.getComponents().stopEjecucion();
	    }
	FileDialog dialog = new FileDialog(MainWindow.shell,SWT.OPEN);
	dialog.setFilterExtensions(new String[] { "*.Org","*.*" });
	String archivo = dialog.open();
	if(archivo!=null){
	File file = new File(archivo);
	if(file.exists()){
		if(MainWindow._diagrams.getHoja().getSizeDiagrama()==0){
	    	String archivo2 = dialog.getFileName();
	    	int pos = archivo2.indexOf('.');
	    	String name = archivo2.substring(0, pos);
	    	diagrams.cambiarNombre("*"+name);
	    	diagrams.getTabItem().getSave().setSave(true);
	    	diagrams.abrir(archivo,MainWindow.getSerializer());
	    	diagrams.getTabItem().getSave().setDir(archivo);
	    	
    	}
    	else{
    		MainWindow._selectionAdministrator.setFiguraSeleccionada(0);
    		MainWindow._diagrams.getHoja().openFile(archivo);
    		MainWindow._diagrams.getTabItem().getSave().setDir(archivo);
		    archivo = dialog.getFileName();
		    int pos = archivo.indexOf('.');
		    String name = archivo.substring(0, pos);
		    MainWindow._diagrams.cambiarNombre("*"+name);
		    MainWindow._diagrams.getTabItem().getSave().setSave(true);
		    MainWindow._diagrams.getTabItem().resetRetroceso();		 
		    MainWindow._diagrams.getTabItem().agregarRetroceso(MainWindow._diagrams.getHoja().getDiagrama(), MainWindow._diagrams.selec);
    	}
    	MainWindow.getComponentes().disablePasoAPaso(false);
    	MainWindow.getComponents().disableAll(true);
	}
}
	
    }

}
