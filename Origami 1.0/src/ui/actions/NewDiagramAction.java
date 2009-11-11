package ui.actions;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Grafico.Componentes;
import Grafico.MainWindow;
import Administracion.TabFolder;


public class NewDiagramAction implements SelectionListener{
    private TabFolder _diagrams;
    private Componentes _components;
    private MainWindow _mainWindow;
    public NewDiagramAction(TabFolder diagrams, Componentes components, MainWindow mainWindow) {
	_diagrams = diagrams;
	_components = components;
	_mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	_diagrams.addTabItem();
		_components.guardarDisable(true);
	MainWindow._components.disableAll(true);
    }

}
