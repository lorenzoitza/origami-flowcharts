package Grafico.view;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import Administracion.Funcionalidad.Exporter;
import Grafico.MainWindow;


public class SaveFileView {
    
    private SaveType saveType;
    
    
    public SaveType getSaveType() {
        return saveType;
    }
    
    public void setSaveType(SaveType saveType) {
        this.saveType = saveType;
    }

    public void createWindow(){
	FileDialog dialog = new FileDialog(MainWindow.shell,SWT.SAVE);
	switch (saveType) {
	case SAVE: 
	    dialog.setFilterExtensions(new String[] { "*.Org"});
	    break;	
	case SAVEAS: 
	    dialog.setFilterExtensions(new String[] { "*.Org"});
	    break;	     
	case EXPORTC:
	    dialog.setFilterExtensions(new String[] { "*.c"});
	case EXPORTCPP: 
	    dialog.setFilterExtensions(new String[] { "*.cpp"});
	    break;
	case EXPORTEXE: 
	    dialog.setFilterExtensions(new String[] { "*.exe"});
	    break;
	case EXPORTIMAGE: 
	    dialog.setFilterExtensions(new String[] { "*.jpg","*.bmp","*.png"});
	    break;
	default:
	    break;
	}
	
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
		    messageBox.setMessage("El archivo ya existe. ¿Desea reemplazarlo?");
		    int seleccion = messageBox.open();
		    switch(seleccion){
		    case 64:
			 action(dialog.getFileName(),archivo);					    	
			break;
		    case 128:							
			break;
		    }
		}
		else{
		    action(dialog.getFileName(),archivo);
		}
	    }
	}
    }
    
    public void action(String nomArchivo,String address){
	switch (saveType) {
	case SAVE: 
	    MainWindow.getSerializer().setFile(nomArchivo);
	    MainWindow.getComponentes()._diagrams.getTabItem().getSave().setDir(nomArchivo);
	    boolean error = MainWindow.getSerializer().saveDiagram(MainWindow.getComponentes()._diagrams);
	    if(error){
		int pos = nomArchivo.indexOf('.');
		String name = nomArchivo.substring(0, pos);
		MainWindow.getComponentes()._diagrams.getTabItem().getSave().setSave(true);
		MainWindow.getComponentes()._diagrams.cambiarNombre(name);
	    }
	    break;	
	case SAVEAS: 
	    MainWindow.getSerializer().setFile(nomArchivo);
	    boolean error2 = MainWindow.getSerializer().saveDiagram(MainWindow.getComponentes()._diagrams);
	    if(error2){
		int pos = nomArchivo.indexOf('.');
		String name = nomArchivo.substring(0, pos);
		MainWindow.getComponentes()._diagrams.getTabItem().getSave().setSave(true);
		MainWindow.getComponentes()._diagrams.cambiarNombre(name);
	    }	
	    break;	     
	case EXPORTC:
	    Exporter expor = new Exporter(MainWindow.getComponentes()._diagrams);
	    expor.codeCExport(nomArchivo);
	    break;
	case EXPORTCPP: 
	    Exporter expor2 = new Exporter(MainWindow.getComponentes()._diagrams);
	    expor2.codeCppExport(nomArchivo);
	    break;
	case EXPORTEXE: 
	    nomArchivo = nomArchivo.substring(0,nomArchivo.indexOf("."));
	    Exporter expor3 = new Exporter(MainWindow.getComponentes()._diagrams);
	    expor3.executeFileExport(address,nomArchivo);
	    break;
	case EXPORTIMAGE: 
	    Exporter expor4 = new Exporter(MainWindow.getComponentes()._diagrams);
	    expor4.exportJPGFile(address,MainWindow.getComponentes()._diagrams.getHoja().getDiagrama(),MainWindow.getComponentes()._diagrams.getHoja().getConexion());
	    break;
	default:
	    break;
	}
    }
    
}
