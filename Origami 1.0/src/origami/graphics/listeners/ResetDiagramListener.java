package origami.graphics.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import origami.administration.ApplicationState;
import origami.graphics.BaseDeDiagrama;
import origami.graphics.MainWindow;
import origami.graphics.figures.CircleFigure;


public class ResetDiagramListener implements SelectionListener{
    
    public ResetDiagramListener() {
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	ApplicationState._selectionAdministrator.setFiguraSeleccionada(0);
	for(int y=MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getSizeDiagrama()-1;y>0;y--){
	    MainWindow.getComponents().tabFolder.getTabItem().getLeaf().removeFigureIndexOf(y);
	}
	CircleFigure fin = new CircleFigure();
	MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama().add(fin);
	fin.setMessage("  Fin");
	BaseDeDiagrama.getInstance().resetScrollBar();
	//MainWindow.getComponents()._diagrams.getHoja().resetScrollBar();
	MainWindow.getComponents().tabFolder.getTabItem().getLeaf().addFigure();
	MainWindow.getComponents().tabFolder.getTabItem().getLeaf().guardarRetroceso();
	//MainWindow.getComponents()._diagrams.getHoja().guardarRetroceso();
	MainWindow.getComponents().tabFolder.getTabItem().getSave().setSave(false);		
    }

}
