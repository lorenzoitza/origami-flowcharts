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
    private MainWindow mainWindow;
    
    public StepByStepAction(MainWindow mainWindow ) {
	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	if(!MainWindow.getComponents()._diagrams.getTabItem().getSave().isSave()){
		if(mainWindow.getComponents().guardar()){
		    CodeCompiler codigo = new CodeCompiler(MainWindow.getComponents()._diagrams);
			codigo.main(false,false);
			if(codigo.isError){
				int aux = mainWindow.getComponents().console.text.getText().length();
				if(aux>=0){
				    mainWindow.getComponents().console.text.setText("");
				}
				
				mainWindow.getComponents().console.text.setText(codigo.errorTipe);
				MainWindow.getComponents()._diagrams.getTabItem().getInfo().addInformation("/Ep - Error en el paso a paso:");
				MainWindow.getComponents()._diagrams.getTabItem().getInfo().addInformation(codigo.errorTipe);
				codigo.deleteMainFiles();
			}
			else{
				
			    	mainWindow.getComponents().disablePasoAPaso(true);
			    	mainWindow.getComponents().ejecutar(false,codigo);
			    	MainWindow.getComponents()._diagrams.getTabItem().getInfo().addInformation("/P - Se inicio el paso a paso de manera correcta");
			}
		}
	}
	else{
	    CodeCompiler codigo = new CodeCompiler(MainWindow.getComponents()._diagrams);
		codigo.main(false,false);
		if(codigo.isError){
			int aux = mainWindow.getComponents().console.text.getText().length();
			if(aux>=0){
			    mainWindow.getComponents().console.text.setText("");
			}
			
			mainWindow.getComponents().console.text.setText(codigo.errorTipe);
			MainWindow.getComponents()._diagrams.getTabItem().getInfo().addInformation("/Ep - Error en el paso a paso:");
			MainWindow.getComponents()._diagrams.getTabItem().getInfo().addInformation(codigo.errorTipe);
			codigo.deleteMainFiles();
		}
		else{
			
		    	mainWindow.getComponents().disablePasoAPaso(true);
		    	mainWindow.getComponents().ejecutar(false,codigo);
		    	MainWindow.getComponents()._diagrams.getTabItem().getInfo().addInformation("/P - Se inicio el paso a paso de manera correcta");
		}
	}
			
	
    }

}
