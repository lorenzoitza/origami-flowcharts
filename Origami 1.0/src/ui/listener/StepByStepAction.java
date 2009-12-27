package ui.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Administracion.Funcionalidad.CodeCompiler;
import Grafico.Componentes;
import Grafico.MainWindow;


public class StepByStepAction implements SelectionListener{
    
    public StepByStepAction( ) {
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	if(!Componentes._diagrams.getTabItem().getSave().isSave()){
		if(MainWindow.getComponents().guardar()){
		    CodeCompiler codigo = new CodeCompiler(Componentes._diagrams);
			codigo.main(false,false);
			if(codigo.isError){
				int aux = MainWindow.getComponents().console.text.getText().length();
				if(aux>=0){
				    MainWindow.getComponents().console.text.setText("");
				}
				
				MainWindow.getComponents().console.text.setText(codigo.errorTipe);
				Componentes._diagrams.getTabItem().getInfo().addInformation("/Ep - Error en el paso a paso:");
				Componentes._diagrams.getTabItem().getInfo().addInformation(codigo.errorTipe);
				codigo.deleteMainFiles();
			}
			else{
				
			    	MainWindow.getComponents().disablePasoAPaso(true);
			    	MainWindow.getComponents().ejecutar(false,codigo);
				Componentes._diagrams.getTabItem().getInfo().addInformation("/P - Se inicio el paso a paso de manera correcta");
			}
		}
	}
	else{
	    CodeCompiler codigo = new CodeCompiler(Componentes._diagrams);
		codigo.main(false,false);
		if(codigo.isError){
			int aux = MainWindow.getComponents().console.text.getText().length();
			if(aux>=0){
			    MainWindow.getComponents().console.text.setText("");
			}
			
			MainWindow.getComponents().console.text.setText(codigo.errorTipe);
			Componentes._diagrams.getTabItem().getInfo().addInformation("/Ep - Error en el paso a paso:");
			Componentes._diagrams.getTabItem().getInfo().addInformation(codigo.errorTipe);
			codigo.deleteMainFiles();
		}
		else{
		    	MainWindow.getComponents().disablePasoAPaso(true);
		    	MainWindow.getComponents().ejecutar(false,codigo);
			Componentes._diagrams.getTabItem().getInfo().addInformation("/P - Se inicio el paso a paso de manera correcta");
		}
	}
			
	
    }

}
