package ui.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Administracion.Funcionalidad.Codigo.ManagerCodeFormat;
import Grafico.MainWindow;
import Grafico.view.DiagramCodeView;


public class ViewCodeCppAction implements SelectionListener{

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	  ManagerCodeFormat manager = new ManagerCodeFormat(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
	  manager.formatCodeCpp();
	  DiagramCodeView view = new DiagramCodeView(manager.getInstructionsFormat());
	  view.createWindow();
	  view.show();
    }

}
