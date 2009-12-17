package ui.actions;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Administracion.Funcionalidad.Codigo.Instruccion;
import Grafico.MainWindow;


public class BuildCCodeAction implements SelectionListener{
    private MainWindow mainWindow;
    
    public BuildCCodeAction(MainWindow mainWindow ) {
	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent event) {
	Instruccion instructionCode = new Instruccion();
	instructionCode.main(MainWindow.getComponentes()._diagrams.getHoja().getDiagrama());
	instructionCode.createWindow(mainWindow.display);
    }

}
