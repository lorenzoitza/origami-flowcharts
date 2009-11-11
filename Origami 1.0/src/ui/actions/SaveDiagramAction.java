package ui.actions;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import Administracion.TabFolder;
import Administracion.Funcionalidad.Exportar;
import Grafico.MainWindow;


public class SaveDiagramAction implements SelectionListener{
    private TabFolder _diagrams;
    private MainWindow _mainWindow;
    
    public SaveDiagramAction(TabFolder diagrams, MainWindow mainWindow ) {
	_diagrams = diagrams;
	_mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	if(_diagrams.getTabItem().getSave().getDir()=="null"){
		FileDialog dialog = new FileDialog(MainWindow._shell,SWT.SAVE);
	    dialog.setFilterExtensions(new String[] { "*.Org"});
	    String archivo = dialog.open();
	    if(archivo!=null){
	    	if(dialog.getFileName().contains("\\") || dialog.getFileName().contains("/") ||
				dialog.getFileName().contains(":") || dialog.getFileName().contains("*") ||
						dialog.getFileName().contains("?") || dialog.getFileName().contains("<") ||
						dialog.getFileName().contains(">") || dialog.getFileName().contains("|") ||
						dialog.getFileName().contains("\"")){
			MessageBox messageBox = new MessageBox(MainWindow._shell, SWT.ICON_ERROR| SWT.OK);
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
				MessageBox messageBox = new MessageBox(MainWindow._shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
		    		messageBox.setText("Origami");
		    		messageBox.setMessage("El archivo ya existe. Desea reemplazarlo?");
		    		int seleccion = messageBox.open();
		    		switch(seleccion){
		    			case 64:
					    	_mainWindow.getSerializer().SetFil(archivo);
					    	_diagrams.getTabItem().getSave().setDir(archivo);
					    	_mainWindow.getSerializer().guardar(_diagrams);
					    	archivo = dialog.getFileName();
					    	int pos = archivo.indexOf('.');
					    	String name = archivo.substring(0, pos);
					    	_diagrams.getTabItem().getSave().setSave(true);
					    	_diagrams.cambiarNombre(name);
		    				break;
		    			case 128:							
		    				break;
		    		}
		    	}
		    	else{
		    	_mainWindow.getSerializer().SetFil(archivo);
			    	_diagrams.getTabItem().getSave().setDir(archivo);
			    	boolean error = _mainWindow.getSerializer().guardar(_diagrams);
			    	if(error){
			    		archivo = dialog.getFileName();
			    		int pos = archivo.indexOf('.');
				    	String name = archivo.substring(0, pos);
				    	_diagrams.getTabItem().getSave().setSave(true);
				    	_diagrams.cambiarNombre(name);
			    	}
		    	}
		}
	    }
	}
	else{
	    _mainWindow.getSerializer().SetFil(_diagrams.getTabItem().getSave().getDir());
	    _mainWindow.getSerializer().guardar(_diagrams);
	_diagrams.getTabItem().getSave().setSave(true);
	}
	
    }

}
