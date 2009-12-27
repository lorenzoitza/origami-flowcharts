package ui.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Administracion.Funcionalidad.Codigo.Manager;
import Grafico.MainWindow;
import Grafico.view.DiagramCodeView;


public class ViewCodeCAction implements SelectionListener{

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	Manager manager = new Manager(MainWindow.getComponents()._diagrams.getHoja().getDiagrama());
	manager.formatCodeC();
	DiagramCodeView view = new DiagramCodeView(manager.getInstructionsFormat());
	view.createWindow();
	view.show();
    }

}
