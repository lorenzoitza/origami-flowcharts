package ui.actions;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Grafico.Componentes;
import Grafico.MainWindow;
import Administracion.TabFolder;


public class NewDiagramAction implements SelectionListener{
    private TabFolder diagrams;
    private Componentes components;
    private MainWindow mainWindow;
    public NewDiagramAction(TabFolder diagrams, Componentes components, MainWindow mainWindow) {
	this.diagrams = diagrams;
	this.components = components;
	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	diagrams.addTabItem();
		components.guardarDisable(true);
	MainWindow.getComponents().disableAll(true);
    }

}
