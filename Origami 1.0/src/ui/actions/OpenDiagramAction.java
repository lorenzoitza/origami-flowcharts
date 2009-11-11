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


public class OpenDiagramAction implements SelectionListener{
    private TabFolder _diagrams;
    private MainWindow _mainWindow;
    
    public OpenDiagramAction(TabFolder diagrams, MainWindow mainWindow ) {
	_diagrams = diagrams;
	_mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	if(MainWindow.getComponents().eje != null && MainWindow.getComponents().getEnEjecucion()
		&& _diagrams.getSelectedTabItemId() == MainWindow.getComponents().eje.a.GetId()){
	MainWindow.getComponents().stopEjecucion();
	} else if(MainWindow.getComponents().paso != null && MainWindow.getComponents().getEnEjecucion()
		&& _diagrams.getSelectedTabItemId() == MainWindow.getComponents().paso.a.GetId()){
	    MainWindow.getComponents().stopEjecucion();
	    }
	FileDialog dialog = new FileDialog(MainWindow._shell,SWT.OPEN);
	dialog.setFilterExtensions(new String[] { "*.Org","*.*" });
	String archivo = dialog.open();
	if(archivo!=null){
	File file = new File(archivo);
	if(file.exists()){
		if(_diagrams.getHoja().getSizeDiagrama()==0){
	    	String archivo2 = dialog.getFileName();
	    	int pos = archivo2.indexOf('.');
	    	String name = archivo2.substring(0, pos);
	    	_diagrams.cambiarNombre("*"+name);
	    	_diagrams.getTabItem().getSave().setSave(true);
	    	_diagrams.abrir(archivo,_mainWindow.getSerializer());
	    	_diagrams.getTabItem().getSave().setDir(archivo);
	    	
    	}
    	else{
    		MainWindow._selectionAdministrator.setFiguraSeleccionada(0);
		    _diagrams.getHoja().openFile(archivo);
		    _diagrams.getTabItem().getSave().setDir(archivo);
		    archivo = dialog.getFileName();
		    int pos = archivo.indexOf('.');
		    String name = archivo.substring(0, pos);
		    _diagrams.cambiarNombre("*"+name);
	    	_diagrams.getTabItem().getSave().setSave(true);
	    	_diagrams.getTabItem().resetRetroceso();		 
	    	_diagrams.getTabItem().agregarRetroceso(_diagrams.getHoja().getDiagrama(), _diagrams.selec);
    	}
    	MainWindow.getComponentes().disablePasoAPaso(false);
    	MainWindow.getComponents().disableAll(true);
	}
}
	
    }

}
