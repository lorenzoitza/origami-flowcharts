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


public class SaveDiagramAction implements SelectionListener{
    private TabFolder diagrams;
    private MainWindow mainWindow;
    
    public SaveDiagramAction(TabFolder diagrams, MainWindow mainWindow ) {
	this.diagrams = diagrams;
	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	if(diagrams.getTabItem().getSave().getDir()=="null"){
		FileDialog dialog = new FileDialog(MainWindow.shell,SWT.SAVE);
	    dialog.setFilterExtensions(new String[] { "*.Org"});
	    String archivo = dialog.open();
	    if(archivo!=null){
	    	if(dialog.getFileName().contains("\\") || dialog.getFileName().contains("/") ||
				dialog.getFileName().contains(":") || dialog.getFileName().contains("*") ||
						dialog.getFileName().contains("?") || dialog.getFileName().contains("<") ||
						dialog.getFileName().contains(">") || dialog.getFileName().contains("|") ||
						dialog.getFileName().contains("\"")){
			MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_ERROR| SWT.OK);
	    		messageBox.setText("Origami");
	    		messageBox.setMessage("El nombre de archivo, directorio o etiqueta del volumn no es vlido");
	    		int seleccion = messageBox.open();
	    		switch(seleccion){
	    			case 64:
	    				break;
	    			case 128:			    	
	    				break;
	    		}
		}
		else{
			boolean existe = false;
			try{
				File arch = new File(archivo);
				if(arch.exists()){
					existe = true;
				}
			}
			catch(Exception e1){
			}		    	
			if(existe){
				MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
		    		messageBox.setText("Origami");
		    		messageBox.setMessage("El archivo ya existe. Desea reemplazarlo?");
		    		int seleccion = messageBox.open();
		    		switch(seleccion){
		    			case 64:
					    	mainWindow.getSerializer().SetFil(archivo);
					    	diagrams.getTabItem().getSave().setDir(archivo);
					    	mainWindow.getSerializer().saveFile(diagrams);
					    	archivo = dialog.getFileName();
					    	int pos = archivo.indexOf('.');
					    	String name = archivo.substring(0, pos);
					    	diagrams.getTabItem().getSave().setSave(true);
					    	diagrams.cambiarNombre(name);
		    				break;
		    			case 128:							
		    				break;
		    		}
		    	}
		    	else{
		    	mainWindow.getSerializer().SetFil(archivo);
			    	diagrams.getTabItem().getSave().setDir(archivo);
			    	boolean error = mainWindow.getSerializer().saveFile(diagrams);
			    	if(error){
			    		archivo = dialog.getFileName();
			    		int pos = archivo.indexOf('.');
				    	String name = archivo.substring(0, pos);
				    	diagrams.getTabItem().getSave().setSave(true);
				    	diagrams.cambiarNombre(name);
			    	}
		    	}
		}
	    }
	}
	else{
	    mainWindow.getSerializer().SetFil(diagrams.getTabItem().getSave().getDir());
	    mainWindow.getSerializer().saveFile(diagrams);
	diagrams.getTabItem().getSave().setSave(true);
	}
	
    }

}
