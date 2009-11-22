package ui.actions;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import Administracion.TabFolder;
import Administracion.Funcionalidad.CodeCompiler;
import Administracion.Funcionalidad.Exporter;
import Grafico.MainWindow;


public class StepByStepAction implements SelectionListener{
    private TabFolder diagrams;
    private MainWindow mainWindow;
    
    public StepByStepAction(TabFolder diagrams, MainWindow mainWindow ) {
	this.diagrams = diagrams;
	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	if(!diagrams.getTabItem().getSave().isSave()){
		if(mainWindow.getComponents().guardar()){
		    CodeCompiler codigo = new CodeCompiler(diagrams);
			codigo.main(false,false);
			if(codigo.isError){
				int aux = mainWindow.getComponents().text.getText().length();
				if(aux>=0){
				    mainWindow.getComponents().text.setText("");
				}
				
				mainWindow.getComponents().text.setText(codigo.errorTipe);
				diagrams.getTabItem().getInfo().addInformation("/Ep - Error en el paso a paso:");
				diagrams.getTabItem().getInfo().addInformation(codigo.errorTipe);
				codigo.deleteMainFiles();
			}
			else{
				
			    	mainWindow.getComponents().disablePasoAPaso(true);
			    	mainWindow.getComponents().ejecutar(false,codigo);
				diagrams.getTabItem().getInfo().addInformation("/P - Se inicio el paso a paso de manera correcta");
			}
		}
	}
	else{
	    CodeCompiler codigo = new CodeCompiler(diagrams);
		codigo.main(false,false);
		if(codigo.isError){
			int aux = mainWindow.getComponents().text.getText().length();
			if(aux>=0){
			    mainWindow.getComponents().text.setText("");
			}
			
			mainWindow.getComponents().text.setText(codigo.errorTipe);
			diagrams.getTabItem().getInfo().addInformation("/Ep - Error en el paso a paso:");
			diagrams.getTabItem().getInfo().addInformation(codigo.errorTipe);
			codigo.deleteMainFiles();
		}
		else{
			
		    	mainWindow.getComponents().disablePasoAPaso(true);
		    	mainWindow.getComponents().ejecutar(false,codigo);
			diagrams.getTabItem().getInfo().addInformation("/P - Se inicio el paso a paso de manera correcta");
		}
	}
			
	
    }

}
