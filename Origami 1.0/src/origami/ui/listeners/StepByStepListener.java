package origami.ui.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import origami.administration.funtionality.CodeCompiler;
import origami.graphics.Componentes;
import origami.graphics.MainWindow;
import origami.graphics.view.SaveFileView;
import origami.graphics.view.SaveType;




public class StepByStepListener implements SelectionListener{
    
    public StepByStepListener( ) {
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }
    
    public boolean guardar() {
	SaveFileView save = new SaveFileView();
	if(Componentes._diagrams.getTabItem().getSave().getDir() == "null") {
	    save.setSaveType(SaveType.SAVE);
	    return save.createWindow();
	} else {
	    return save.saveAction();
	}
    }
    @Override
    public void widgetSelected(SelectionEvent arg0) {
	if(!Componentes._diagrams.getTabItem().getSave().isSave()){
		if(guardar()){
		    CodeCompiler codigo = new CodeCompiler(Componentes._diagrams);
			codigo.main(false,false);
			if(codigo.isError){
				int aux = MainWindow.getComponents().console.getTextField().getText().length();
				if(aux>=0){
				    MainWindow.getComponents().console.getTextField().setText("");
				}
				
				MainWindow.getComponents().console.getTextField().setText(codigo.errorTipe);
				Componentes._diagrams.getTabItem().getInformation().addInformation("/Ep - Error en el paso a paso:");
				Componentes._diagrams.getTabItem().getInformation().addInformation(codigo.errorTipe);
				codigo.deleteMainFiles();
			}
			else{
				
			    	MainWindow.getComponents().getByStepComponents().disablePasoAPaso(MainWindow.getComponents(), true);
			    	MainWindow.getComponents().getByStepComponents().ejecutar(MainWindow.getComponents(), false,codigo);
				Componentes._diagrams.getTabItem().getInformation().addInformation("/P - Se inicio el paso a paso de manera correcta");
			}
		}
	}
	else{
	    CodeCompiler codigo = new CodeCompiler(Componentes._diagrams);
		codigo.main(false,false);
		if(codigo.isError){
			int aux = MainWindow.getComponents().console.getTextField().getText().length();
			if(aux>=0){
			    MainWindow.getComponents().console.getTextField().setText("");
			}
			
			MainWindow.getComponents().console.getTextField().setText(codigo.errorTipe);
			Componentes._diagrams.getTabItem().getInformation().addInformation("/Ep - Error en el paso a paso:");
			Componentes._diagrams.getTabItem().getInformation().addInformation(codigo.errorTipe);
			codigo.deleteMainFiles();
		}
		else{
		    	MainWindow.getComponents().getByStepComponents().disablePasoAPaso(MainWindow.getComponents(), true);
		    	MainWindow.getComponents().getByStepComponents().ejecutar(MainWindow.getComponents(), false,codigo);
			Componentes._diagrams.getTabItem().getInformation().addInformation("/P - Se inicio el paso a paso de manera correcta");
		}
	}
			
	
    }

}
