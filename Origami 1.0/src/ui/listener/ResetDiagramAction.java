package ui.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Grafico.BaseDeDiagrama;
import Grafico.Componentes;
import Grafico.MainWindow;
import Grafico.Figuras.CircleFigure;

public class ResetDiagramAction implements SelectionListener{
    
    public ResetDiagramAction() {
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	Componentes._selectionAdministrator.setFiguraSeleccionada(0);
	for(int y=MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getSizeDiagrama()-1;y>0;y--){
	    MainWindow.getComponents()._diagrams.getTabItem().getLeaf().removeFigureIndexOf(y);
	}
	CircleFigure fin = new CircleFigure();
	MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama().add(fin);
	fin.setMesagge("  Fin");
	BaseDeDiagrama.getInstance().resetScrollBar();
	//MainWindow.getComponents()._diagrams.getHoja().resetScrollBar();
	MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigure();
	MainWindow.getComponents()._diagrams.getTabItem().getLeaf().guardarRetroceso();
	//MainWindow.getComponents()._diagrams.getHoja().guardarRetroceso();
	MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);		
    }

}
