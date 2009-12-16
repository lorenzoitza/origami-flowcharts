package ui.actions;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Grafico.MainWindow;
import Grafico.Figuras.CircleFigure;


public class ResetDiagramAction implements SelectionListener{
    private MainWindow mainWindow;
    
    public ResetDiagramAction(MainWindow mainWindow ) {
	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	mainWindow._selectionAdministrator.setFiguraSeleccionada(0);
	for(int y=MainWindow.getComponentes()._diagrams.getHoja().getSizeDiagrama()-1;y>0;y--){
	    MainWindow.getComponentes()._diagrams.getHoja().removeFigureIndexOf(y);
	}
	CircleFigure fin = new CircleFigure();
	MainWindow.getComponentes()._diagrams.getHoja().getDiagrama().add(fin);
	fin.setMesagge("  Fin");
	MainWindow.getComponentes()._diagrams.getHoja().resetScrollBar();
	MainWindow.getComponentes()._diagrams.getHoja().addFigure();
	MainWindow.getComponentes()._diagrams.getHoja().guardarRetroceso();
	MainWindow.getComponentes()._diagrams.getTabItem().getSave().setSave(false);		
    }

}
