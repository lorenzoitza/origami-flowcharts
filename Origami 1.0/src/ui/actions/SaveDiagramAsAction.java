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


public class SaveDiagramAsAction implements SelectionListener{
    private MainWindow mainWindow;
    
    public SaveDiagramAsAction(MainWindow mainWindow ) {
	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
				if(MainWindow.getComponents().eje != null && MainWindow.getComponents().getEnEjecucion()
						&& MainWindow.getComponentes()._diagrams.getSelectedTabItemId() == MainWindow.getComponents().eje.a.GetId()){
					MainWindow.getComponents().stopEjecucion();
				}
				else if(MainWindow.getComponents().paso != null && MainWindow.getComponents().getEnEjecucion()
						&& MainWindow.getComponentes()._diagrams.getSelectedTabItemId() == MainWindow.getComponents().paso.a.GetId()){
					MainWindow.getComponents().stopEjecucion();
				}
				MainWindow.getComponentes().disablePasoAPaso(false);
				FileDialog dialog = new FileDialog(mainWindow.getShell(),SWT.SAVE);
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
							    	mainWindow.getSerializer().setFile(archivo);
							    	mainWindow.getSerializer().saveDiagram(MainWindow.getComponentes()._diagrams);
							    	archivo = dialog.getFileName();
							    	int pos = archivo.indexOf('.');
							    	String name = archivo.substring(0, pos);
							    	MainWindow.getComponentes()._diagrams.cambiarNombre(name);							    	
				    				break;
				    			case 128:							
				    				break;
				    		}
				    	}
				    	else{
				    	    mainWindow.getSerializer().setFile(archivo);
					    	boolean error = mainWindow.getSerializer().saveDiagram(MainWindow.getComponentes()._diagrams);
					    	if(error){
					    		archivo = dialog.getFileName();
					    		int pos = archivo.indexOf('.');
						    	String name = archivo.substring(0, pos);
						    	MainWindow.getComponentes()._diagrams.cambiarNombre(name);
					    	}			    	
				    	}
		    		}
			    }
	
    }

}
