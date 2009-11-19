package ui.actions;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import Administracion.TabFolder;
import Administracion.Funcionalidad.CodeCompiler;
import Administracion.Funcionalidad.Exportar;
import Grafico.CustomeMenu;
import Grafico.MainWindow;


public class CompileAction implements SelectionListener{
    private TabFolder diagrams;
    private MainWindow mainWindow;
    
    public CompileAction(TabFolder diagrams, MainWindow mainWindow ) {
	this.diagrams = diagrams;
	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	CodeCompiler codigo = new CodeCompiler(diagrams);
	if(mainWindow.getComponents().getEnEjecucion()){
	    mainWindow.getComponents().stopEjecucion();
	}
	codigo.main(false,true);
	if(codigo.isError){
		int aux = mainWindow.getComponents().text.getText().length();
		if(aux>=0){
		    mainWindow.getComponents().text.setText("");
		}
		mainWindow.getComponents().text.setText(codigo.errorTipe);
		diagrams.getTabItem().getInfo().addInformation("/Ec - Error en la compilacion:");
		diagrams.getTabItem().getInfo().addInformation(codigo.errorTipe);
		codigo.deleteMainFiles();
	}
	else{
	    	mainWindow.getComponents().ejecutar(true,codigo);
		diagrams.getTabItem().getInfo().addInformation("/C - Se Compilo el diagrama de manera correcta");
	}
	if(!CustomeMenu.consoleMenuItem.getSelection()){
	    CustomeMenu.consoleMenuItem.setSelection(true);
		mainWindow.getComponents().moverConsola(true);
	}
			
	
    }

}
