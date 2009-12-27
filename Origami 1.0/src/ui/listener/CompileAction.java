package ui.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Administracion.Funcionalidad.CodeCompiler;
import Grafico.Componentes;
import Grafico.MainWindow;
import Grafico.view.SaveFileView;
import Grafico.view.SaveType;


public class CompileAction implements SelectionListener{
    
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
			if (MainWindow.getComponents().getEnEjecucion()) {
			    MainWindow.getComponents().stopEjecucion();
			}
			codigo.main(false, true);
			if (codigo.isError) {
				int aux = MainWindow.getComponents().console.text.getText().length();
				if (aux >= 0) {
				    MainWindow.getComponents().console.text.setText("");
				}
				MainWindow.getComponents().console.text.setText(codigo.errorTipe);
				MainWindow.getComponents()._diagrams.getTabItem().getInfo().addInformation(
						"/Ec - Error en la compilacion:");
				MainWindow.getComponents()._diagrams.getTabItem().getInfo().addInformation(
						codigo.errorTipe);
				codigo.deleteMainFiles();
			} else {
			    MainWindow.getComponents().ejecutar(true, codigo);
				MainWindow.getComponents()._diagrams
						.getTabItem()
						.getInfo()
						.addInformation(
								"/C - Se Compilo el diagrama de manera correcta");
			}
			if (!MainWindow.menu.consoleMenuItem.getSelection()) {
				MainWindow.menu.consoleMenuItem.setSelection(true);
				MainWindow.getComponents().moverConsola(true);
			}
		}
	} else {
		CodeCompiler codigo = new CodeCompiler(MainWindow.getComponents()._diagrams);
		if (MainWindow.getComponents().getEnEjecucion()) {
		    MainWindow.getComponents().stopEjecucion();
		}
		codigo.main(false, true);
		if (codigo.isError) {
			int aux = MainWindow.getComponents().console.text.getText().length();
			if (aux >= 0) {
			    MainWindow.getComponents().console.text.setText("");
			}
			MainWindow.getComponents().console.text.setText(codigo.errorTipe);
			codigo.deleteMainFiles();
		} else {
		    MainWindow.getComponents().ejecutar(true, codigo);
		}
		if (!MainWindow.menu.consoleMenuItem.getSelection()) {
			MainWindow.menu.consoleMenuItem.setSelection(true);
			MainWindow.getComponents().moverConsola(true);
		}
	}
    }
}
