package ui.actions;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import Administracion.TabFolder;
import Administracion.Funcionalidad.Exporter;
import Administracion.Funcionalidad.Codigo.Instruccion;
import Grafico.MainWindow;


public class BuildCCodeAction implements SelectionListener{
    private TabFolder diagrams;
    private MainWindow mainWindow;
    
    public BuildCCodeAction(TabFolder diagrams, MainWindow mainWindow ) {
	this.diagrams = diagrams;
	this.mainWindow = mainWindow;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	Instruccion codigo = new Instruccion();
	codigo.main(diagrams.getHoja().getDiagrama(),true);
	codigo.ventana(mainWindow.display);
			
	
    }

}
