package ui.actions;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;

import Grafico.MainWindow;

public class OpenDiagramAction implements SelectionListener{

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	if(MainWindow.getComponents().eje != null && MainWindow.getComponents().getEnEjecucion()
		&& MainWindow.getComponents()._diagrams.getSelectedTabItemId() == MainWindow.getComponents().eje.a.GetId()){
	MainWindow.getComponents().stopEjecucion();
	} else if(MainWindow.getComponents().paso != null && MainWindow.getComponents().getEnEjecucion()
		&& MainWindow.getComponents()._diagrams.getSelectedTabItemId() == MainWindow.getComponents().paso.a.GetId()){
	    MainWindow.getComponents().stopEjecucion();
	    }
	FileDialog dialog = new FileDialog(MainWindow.shell,SWT.OPEN);
	dialog.setFilterExtensions(new String[] { "*.Org","*.*" });
	String archivo = dialog.open();
	if(archivo!=null){
	File file = new File(archivo);
	if(file.exists()){
		if(MainWindow.getComponents()._diagrams.getHoja().getSizeDiagrama()==0){
	    	String archivo2 = dialog.getFileName();
	    	int pos = archivo2.indexOf('.');
	    	String name = archivo2.substring(0, pos);
	    	MainWindow.getComponents()._diagrams.cambiarNombre("*"+name);
	    	MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(true);
	    	MainWindow.getComponents()._diagrams.abrir(archivo,MainWindow.getSerializer());
	    	MainWindow.getComponents()._diagrams.getTabItem().getSave().setDir(archivo);
	    	
    	}
    	else{
    		MainWindow._selectionAdministrator.setFiguraSeleccionada(0);
    		MainWindow.getComponents()._diagrams.getHoja().openFile(archivo);
    		MainWindow.getComponents()._diagrams.getTabItem().getSave().setDir(archivo);
		    archivo = dialog.getFileName();
		    int pos = archivo.indexOf('.');
		    String name = archivo.substring(0, pos);
		    MainWindow.getComponents()._diagrams.cambiarNombre("*"+name);
		    MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(true);
		    MainWindow.getComponents()._diagrams.getTabItem().resetRetroceso();		 
		    MainWindow.getComponents()._diagrams.getTabItem().agregarRetroceso(MainWindow.getComponents()._diagrams.getHoja().getDiagrama(), MainWindow.getComponents()._diagrams.selec);
    	}
    	MainWindow.getComponentes().disablePasoAPaso(false);
    	MainWindow.getComponents().disableAll(true);
	}
	}
	
    }

}
