package Grafico.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import Grafico.MainWindow;


public class OpenFileView {
    
    private OpenType openType;
    
    public void createWindow(){
	FileDialog dialog = new FileDialog(MainWindow.shell,SWT.OPEN);
	dialog.setFilterExtensions(new String[] { "*.Org","*.*" });
	switch (openType) {
	case OPENEXAMPLE: 
	    dialog.setFilterPath("ejemplos\\");
	    break;
	default:
	    break;
	}
	String archivo = dialog.open();
	if(archivo!=null){
            if(MainWindow.getComponents()._diagrams.getHoja().getSizeDiagrama()==0){
        	action(dialog.getFileName(), archivo);
            }
            else{
        	action2(dialog.getFileName(), archivo);
            }
	}
    }
    public void action(String nomArchivo,String address){
	switch (openType) {
	case OPEN: 
	    int pos = nomArchivo.indexOf('.');
	    String name = nomArchivo.substring(0, pos);
	    MainWindow.getComponents()._diagrams.cambiarNombre(name);
	    MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(true);
	    MainWindow.getComponents()._diagrams.abrir(address,MainWindow.getSerializer());
	    MainWindow.getComponents()._diagrams.getTabItem().getSave().setDir(address);
	    break;
	case OPENEXAMPLE: 
    	    int pos2 = nomArchivo.indexOf('.');
    	    String name2 = nomArchivo.substring(0, pos2);
    	    MainWindow.getComponentes()._diagrams.cambiarNombre(name2);
    	    MainWindow.getComponentes()._diagrams.abrir(address,MainWindow.getSerializer());
	    break;
	default:
	    break;
	}
    }
    public void action2(String nomArchivo,String address){
	switch (openType) {
	case OPEN: 
	    MainWindow._selectionAdministrator.setFiguraSeleccionada(0);
	    MainWindow.getComponents()._diagrams.getHoja().openFile(address);
	    MainWindow.getComponents()._diagrams.getTabItem().getSave().setDir(address);
	    int pos = nomArchivo.indexOf('.');
	    String name = nomArchivo.substring(0, pos);
	    MainWindow.getComponents()._diagrams.cambiarNombre(name);
	    MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(true);
	    MainWindow.getComponents()._diagrams.getTabItem().resetRetroceso();		 
	    MainWindow.getComponents()._diagrams.getTabItem().agregarRetroceso(MainWindow.getComponents()._diagrams.getHoja().getDiagrama(), MainWindow.getComponents()._diagrams.selec);
	    break;
	case OPENEXAMPLE: 
	    MainWindow._selectionAdministrator.setFiguraSeleccionada(0);
	    MainWindow.getComponentes()._diagrams.getHoja().openFile(address);
	    int pos2 = nomArchivo.indexOf('.');
	    String name2 = nomArchivo.substring(0, pos2);
	    MainWindow.getComponentes()._diagrams.cambiarNombre(name2);
	    break;
	default:
	    break;
	}
    }
    public void setOpenType(OpenType openType) {
	this.openType = openType;
    }
    public OpenType getOpenType() {
	return openType;
    }
}
