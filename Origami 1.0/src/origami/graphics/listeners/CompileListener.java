package origami.graphics.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import origami.administration.funtionality.CodeCompiler;
import origami.graphics.Componentes;
import origami.graphics.MainWindow;
import origami.graphics.view.SaveFileView;
import origami.graphics.view.SaveType;
import origami.graphics.widgets.CustomMenu;




public class CompileListener implements SelectionListener{
    
    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
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
	if (!MainWindow.getComponents()._diagrams.getTabItem().getSave().isSave()) {
		// llamo abrir la ventana para guardar
		if (guardar()) {
			CodeCompiler codigo = new CodeCompiler(MainWindow.getComponents()._diagrams);
			if (MainWindow.getComponents().getByStepComponents().getEnEjecucion(MainWindow.getComponents())) {
			    MainWindow.getComponents().getByStepComponents().stopEjecucion(MainWindow.getComponents());
			}
			codigo.main(false, true);
			if (codigo.isError) {
				int aux = MainWindow.getComponents().console.getTextField().getText().length();
				if (aux >= 0) {
				    MainWindow.getComponents().console.getTextField().setText("");
				}
				MainWindow.getComponents().console.getTextField().setText(codigo.errorTipe);
				MainWindow.getComponents()._diagrams.getTabItem().getInformation().addInformation(
						"/Ec - Error en la compilacion:");
				MainWindow.getComponents()._diagrams.getTabItem().getInformation().addInformation(
						codigo.errorTipe);
				codigo.deleteMainFiles();
			} else {
			    MainWindow.getComponents().getByStepComponents().ejecutar(MainWindow.getComponents(), true, codigo);
				MainWindow.getComponents()._diagrams
						.getTabItem()
						.getInformation()
						.addInformation(
								"/C - Se Compilo el diagrama de manera correcta");
			}
			if (!CustomMenu.getConsoleMenuItem().getSelection()) {
			    CustomMenu.getConsoleMenuItem().setSelection(true);
				MainWindow.getComponents().moverConsola(true);
			}
		}
	} else {
		CodeCompiler codigo = new CodeCompiler(MainWindow.getComponents()._diagrams);
		if (MainWindow.getComponents().getByStepComponents().getEnEjecucion(MainWindow.getComponents())) {
		    MainWindow.getComponents().getByStepComponents().stopEjecucion(MainWindow.getComponents());
		}
		codigo.main(false, true);
		if (codigo.isError) {
			int aux = MainWindow.getComponents().console.getTextField().getText().length();
			if (aux >= 0) {
			    MainWindow.getComponents().console.getTextField().setText("");
			}
			MainWindow.getComponents().console.getTextField().setText(codigo.errorTipe);
			codigo.deleteMainFiles();
		} else {
		    MainWindow.getComponents().getByStepComponents().ejecutar(MainWindow.getComponents(), true, codigo);
		}
		if (!CustomMenu.getConsoleMenuItem().getSelection()) {
		    CustomMenu.getConsoleMenuItem().setSelection(true);
			MainWindow.getComponents().moverConsola(true);
		}
	}
    }
}
