package ui.actions;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import Administracion.TabFolder;
import Administracion.Funcionalidad.Compilar;
import Administracion.Funcionalidad.Exporter;
import Grafico.MainWindow;
import Grafico.Figuras.CircleFigure;


public class ResetDiagramAction implements SelectionListener{
    private TabFolder diagrams;
    private MainWindow mainWindow;
    
    public ResetDiagramAction(TabFolder diagrams, MainWindow mainWindow ) {
	this.diagrams = diagrams;
	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	mainWindow.selectionAdministrator.setFiguraSeleccionada(0);
	for(int y=diagrams.getHoja().getSizeDiagrama()-1;y>0;y--){
		diagrams.getHoja().removeFigureIndexOf(y);
	}
	CircleFigure fin = new CircleFigure();
	diagrams.getHoja().getDiagrama().add(fin);
	fin.setMesagge("  Fin");
	diagrams.getHoja().resetScrollBar();
	diagrams.getHoja().addFigure();
	diagrams.getHoja().guardarRetroceso();
	diagrams.getTabItem().getSave().setSave(false);		
    }

}
