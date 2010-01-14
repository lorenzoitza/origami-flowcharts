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
    
    public boolean isSave() {
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
		if (isSave()) {
			CodeCompiler code = new CodeCompiler(MainWindow.getComponents()._diagrams);
			if (MainWindow.getComponents().getByStepComponents().getEnEjecucion(MainWindow.getComponents())) {
			    MainWindow.getComponents().getByStepComponents().stopEjecucion(MainWindow.getComponents());
			}
			code.main(false, true);
			if (code.isError) {
				int aux = MainWindow.getComponents().console.getTextField().getText().length();
				if (aux >= 0) {
				    MainWindow.getComponents().console.getTextField().setText("");
				}
				MainWindow.getComponents().console.getTextField().setText(code.errorTipe);
				MainWindow.getComponents()._diagrams.getTabItem().getInformation().addInformation(
						"/Ec - Error en la compilacion:");
				MainWindow.getComponents()._diagrams.getTabItem().getInformation().addInformation(
						code.errorTipe);
				code.deleteMainFiles();
			} else {
			    MainWindow.getComponents().getByStepComponents().ejecutar(MainWindow.getComponents(), true, code);
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
		CodeCompiler code = new CodeCompiler(MainWindow.getComponents()._diagrams);
		if (MainWindow.getComponents().getByStepComponents().getEnEjecucion(MainWindow.getComponents())) {
		    MainWindow.getComponents().getByStepComponents().stopEjecucion(MainWindow.getComponents());
		}
		code.main(false, true);
		if (code.isError) {
			int aux = MainWindow.getComponents().console.getTextField().getText().length();
			if (aux >= 0) {
			    MainWindow.getComponents().console.getTextField().setText("");
			}
			MainWindow.getComponents().console.getTextField().setText(code.errorTipe);
			code.deleteMainFiles();
		} else {
		    MainWindow.getComponents().getByStepComponents().ejecutar(MainWindow.getComponents(), true, code);
		}
		if (!CustomMenu.getConsoleMenuItem().getSelection()) {
		    CustomMenu.getConsoleMenuItem().setSelection(true);
			MainWindow.getComponents().moverConsola(true);
		}
	}
    }
}
