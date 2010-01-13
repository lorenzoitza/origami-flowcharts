package origami.graphics;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import origami.administration.Figura;
import origami.administration.actions.ContextualMenuActions;
import origami.images.ImageLoader;
import origami.ui.listeners.CompileAction;
import origami.ui.listeners.ExportToCAction;
import origami.ui.listeners.ExportToCPPAction;
import origami.ui.listeners.ExportToEXEAction;
import origami.ui.listeners.NewDiagramAction;
import origami.ui.listeners.OpenDiagramAction;
import origami.ui.listeners.SaveDiagramAction;
import origami.ui.listeners.StepByStepAction;
import origami.ui.listeners.ViewCodeCAction;
import origami.ui.listeners.ViewCodeCppAction;



public class CustomToolBar {
    private ToolBar toolBar;
    
    private ArrayList<ToolItem> toolItems;
    
    public CustomToolBar(GridData toolBarLayoutData){
	toolBar = new ToolBar(MainWindow.shell, SWT.HORIZONTAL | SWT.FLAT);
        toolBar.setLayoutData(toolBarLayoutData);
        toolBar.setCursor(new Cursor(null, SWT.CURSOR_ARROW));
        
        toolItems = new ArrayList<ToolItem>();
        
        getToolItems(toolBar);
    }

    public void getToolItems(ToolBar toolbar) {
	ToolItem toolItem;
	
	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("nuevo.png"));
	toolItem.setToolTipText("Nuevo");
	toolItem.addSelectionListener(new NewDiagramAction());
	toolItems.add(toolItem);
	
	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("abrir.png"));
	toolItem.setToolTipText("Abrir");
	toolItem.addSelectionListener(new OpenDiagramAction());
	toolItems.add(toolItem);
	
	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("save.png"));
	toolItem.setToolTipText("Guardar");
	toolItem.addSelectionListener(new SaveDiagramAction());
	toolItems.add(toolItem);
	
	new ToolItem(toolbar, SWT.SEPARATOR);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("undo.png"));
	toolItem.setToolTipText(" Deshacer ");
	toolItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent event) {
		TabItem item = (TabItem) MainWindow.getComponents()._diagrams.getSeleccion();
		item.retroceder();
		MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
	    }
	});
	toolItems.add(toolItem);
	
	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("cortar.ico"));
	toolItem.setToolTipText(" Cortar ");
	toolItem.setEnabled(false);
	toolItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			Figura fig = MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(Componentes._selectionAdministrator.getFiguraSeleccionada());
			new ContextualMenuActions().Cortar(fig);
			toolBarDisable();
		}
	});
	toolItems.add(toolItem);
	
	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("copiar.png"));
	toolItem.setToolTipText(" Copiar ");
	toolItem.setEnabled(false);
	toolItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			Figura fig = MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(
				Componentes._selectionAdministrator
							.getFiguraSeleccionada());
			new ContextualMenuActions().Copiar(fig);
			toolBarDisable();
		}
	});
	toolItems.add(toolItem);
	
	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("pegar.ico"));
	toolItem.setToolTipText(" Pegar ");
	toolItem.setEnabled(false);
	toolItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			Figura fig = MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(
				Componentes._selectionAdministrator
							.getFiguraSeleccionada());
			new ContextualMenuActions().Pegar(fig);
			toolBarDisable();
		}
	});
	toolItems.add(toolItem);
	
	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("eliminar.png"));
	toolItem.setToolTipText(" Eliminar ");
	toolItem.setEnabled(false);
	toolItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			Figura fig = MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(
				Componentes._selectionAdministrator
							.getFiguraSeleccionada());
			new ContextualMenuActions().Eliminar(fig);
		}
	});
	toolItems.add(toolItem);
	
	new ToolItem(toolbar, SWT.SEPARATOR);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("codigo.png"));
	toolItem.setToolTipText("Generar Codigo");
	toolItem.addSelectionListener(new ViewCodeCAction());
	toolItems.add(toolItem);
	
	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("codigoCpp.png"));
	toolItem.setToolTipText("Generar Codigo C++");
	toolItem.addSelectionListener(new ViewCodeCppAction());
	toolItems.add(toolItem);
	
	new ToolItem(toolbar, SWT.SEPARATOR);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("run.png"));
	toolItem.setToolTipText("Compilar/Ejecutar");
	toolItem.addSelectionListener(new CompileAction());
	toolItems.add(toolItem);
	
	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("check.png"));
	toolItem.setToolTipText("Paso A Paso");
	toolItem.addSelectionListener(new StepByStepAction());
	toolItems.add(toolItem);
	
	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("next.png"));
	toolItem.setEnabled(false);
	toolItem.setToolTipText("Paso Siguiente");
	toolItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			if (MainWindow.getComponents().paso.ventanaLeer) {
			    MainWindow.getComponents().paso.ventanaLeer();
			} else {
			    MainWindow.getComponents().next();
			}
		}
	});
	toolItems.add(toolItem);
	
	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("Stop.png"));
	toolItem.setEnabled(false);
	toolItem.setToolTipText("Terminar Ejecucion");
	toolItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			if (MainWindow.getComponents().isPasoAPaso) {
				MainWindow.getComponents().disablePasoAPaso(false);
			}
			MainWindow.getComponents().stopEjecucion();
		}
	});
	toolItems.add(toolItem);
	
	new ToolItem(toolbar, SWT.SEPARATOR);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("exportV.png"));
	toolItem.setToolTipText("Exportar a C");
	toolItem.addSelectionListener(new ExportToCAction());
	toolItems.add(toolItem);
	
	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("cpp.png"));
	toolItem.setToolTipText("Exportar a C++");
	toolItem.addSelectionListener(new ExportToCPPAction());
	toolItems.add(toolItem);
	
	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("exportExe.png"));
	toolItem.setToolTipText("Exportar a .exe");
	toolItem.addSelectionListener(new ExportToEXEAction());
	toolItems.add(toolItem);
	
	new ToolItem(toolbar, SWT.SEPARATOR);
	
	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("Donate.png"));
	toolItem.setToolTipText("Donaciones");
	toolItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent event) {
	    }
	});
	toolItems.add(toolItem);
    }
    public void toolBarDisable() {
	if (Componentes._selectionAdministrator.getFiguraSeleccionada() == -1) {
		for (int i = 4; i <= 7; i++) {
		    toolItems.get(i).setEnabled(false);
		}
	} else {
		if (Componentes._selectionAdministrator.getFiguraSeleccionada() != 0) {
			for (int i = 4; i <= 7; i++) {
			    toolItems.get(i).setEnabled(true);
			}
			if (Componentes._diagramAdministrator.diagrama.size() == 0) {
			    toolItems.get(6).setEnabled(false);
			}
		} else {
			for (int i = 4; i <= 7; i++) {
			    toolItems.get(i).setEnabled(false);
			}
			if (Componentes._diagramAdministrator.diagrama.size() != 0) {
			    toolItems.get(6).setEnabled(true);
			}
		}
	}
    }
    public void disablePasoAPaso(boolean disable) {
	if (disable) {
	    toolItems.get(3).setEnabled(false);
	    toolItems.get(4).setEnabled(false);
	    toolItems.get(5).setEnabled(false);
	    toolItems.get(6).setEnabled(false);
	    toolItems.get(7).setEnabled(false);
	    toolItems.get(10).setEnabled(false);
	    toolItems.get(11).setEnabled(false);
	    toolItems.get(13).setEnabled(true);
	    toolItems.get(14).setEnabled(false);
	    toolItems.get(15).setEnabled(false);
	    toolItems.get(16).setEnabled(false);
	} else {
	    toolItems.get(3).setEnabled(true);
	    toolItems.get(10).setEnabled(true);
	    toolItems.get(11).setEnabled(true);
	    toolItems.get(13).setEnabled(true);
	    toolItems.get(14).setEnabled(true);
	    toolItems.get(15).setEnabled(true);
	    toolItems.get(16).setEnabled(true);
	}
    }
    public void disableAll(boolean disable) {
	toolItems.get(3).setEnabled(disable);
	toolItems.get(8).setEnabled(disable);
	toolItems.get(9).setEnabled(disable);
	toolItems.get(10).setEnabled(disable);
	toolItems.get(11).setEnabled(disable);
	toolItems.get(14).setEnabled(disable);
	toolItems.get(15).setEnabled(disable);
	toolItems.get(16).setEnabled(disable);
    }
    
    public ArrayList<ToolItem> getToolItems() {
        return toolItems;
    }
    
    public ToolBar getToolBar() {
        return toolBar;
    }
}
