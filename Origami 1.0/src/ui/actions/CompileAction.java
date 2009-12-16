package ui.actions;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Administracion.Funcionalidad.CodeCompiler;
import Grafico.MainWindow;


public class CompileAction implements SelectionListener{
    
    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	if (!MainWindow.getComponentes().diagramas.getTabItem().getSave().isSave()) {
		// llamo abrir la ventana para guardar
		if (MainWindow.getComponentes().guardar()) {
			CodeCompiler codigo = new CodeCompiler(MainWindow.getComponentes().diagramas);
			if (MainWindow.getComponentes().getEnEjecucion()) {
			    MainWindow.getComponentes().stopEjecucion();
			}
			codigo.main(false, true);
			if (codigo.isError) {
				int aux = MainWindow.getComponentes().console.text.getText().length();
				if (aux >= 0) {
				    MainWindow.getComponentes().console.text.setText("");
				}
				MainWindow.getComponentes().console.text.setText(codigo.errorTipe);
				MainWindow.getComponentes().diagramas.getTabItem().getInfo().addInformation(
						"/Ec - Error en la compilacion:");
				MainWindow.getComponentes().diagramas.getTabItem().getInfo().addInformation(
						codigo.errorTipe);
				codigo.deleteMainFiles();
			} else {
			    MainWindow.getComponentes().ejecutar(true, codigo);
				MainWindow.getComponentes().diagramas
						.getTabItem()
						.getInfo()
						.addInformation(
								"/C - Se Compilo el diagrama de manera correcta");
			}
			if (!MainWindow.menu.consoleMenuItem.getSelection()) {
				MainWindow.menu.consoleMenuItem.setSelection(true);
				MainWindow.getComponentes().moverConsola(true);
			}
		}
	} else {
		CodeCompiler codigo = new CodeCompiler(MainWindow.getComponentes().diagramas);
		if (MainWindow.getComponentes().getEnEjecucion()) {
		    MainWindow.getComponentes().stopEjecucion();
		}
		codigo.main(false, true);
		if (codigo.isError) {
			int aux = MainWindow.getComponentes().console.text.getText().length();
			if (aux >= 0) {
			    MainWindow.getComponentes().console.text.setText("");
			}
			MainWindow.getComponentes().console.text.setText(codigo.errorTipe);
			codigo.deleteMainFiles();
		} else {
		    MainWindow.getComponentes().ejecutar(true, codigo);
		}
		if (!MainWindow.menu.consoleMenuItem.getSelection()) {
			MainWindow.menu.consoleMenuItem.setSelection(true);
			MainWindow.getComponentes().moverConsola(true);
		}
	}
    }
}
