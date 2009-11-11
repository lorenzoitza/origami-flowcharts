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


public class ExportToCAction implements SelectionListener{
    private TabFolder _diagrams;
    private MainWindow _mainWindow;
    
    public ExportToCAction(TabFolder diagrams, MainWindow mainWindow ) {
	_diagrams = diagrams;
	_mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	 FileDialog dialog = new FileDialog(_mainWindow.getShell(),SWT.SAVE);
	     dialog.setFilterExtensions(new String[] { "*.c"});
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
			    				 Exportar expor = new Exportar(_diagrams);
						    	 expor.exportarCodigoC(archivo);					    	
			    				break;
			    			case 128:							
			    				break;
			    		}
			    	}
			    	else{
			    		 Exportar expor = new Exportar(_diagrams);
				    	 expor.exportarCodigoC(archivo);
			    	}
	    		}
	    }
	
    }

}
