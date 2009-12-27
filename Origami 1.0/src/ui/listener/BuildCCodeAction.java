package ui.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Administracion.Funcionalidad.Codigo.Instruccion;
import Grafico.Componentes;
import Grafico.MainWindow;


public class BuildCCodeAction implements SelectionListener{
    
    public BuildCCodeAction() {
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent event) {
	Instruccion instructionCode = new Instruccion();
	instructionCode.main(Componentes._diagrams.getHoja().getDiagrama());
	instructionCode.createWindow(MainWindow.display);
    }

}
