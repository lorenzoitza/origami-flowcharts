package Grafico;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import ui.actions.CompileAction;
import ui.actions.ExportToCAction;
import ui.actions.ExportToCPPAction;
import ui.actions.ExportToEXEAction;
import ui.actions.NewDiagramAction;
import ui.actions.OpenDiagramAction;
import ui.actions.SaveDiagramAction;
import ui.actions.StepByStepAction;
import Administracion.Figura;
import Administracion.TabItem;
import Administracion.Funcionalidad.Codigo.Manager;
import Administracion.actions.ContextualMenuActions;
import Imagenes.ImageLoader;


public class CustomToolBar {
    public ToolBar barraHerramientas;
    public ToolItem toolItem[] = new ToolItem[20];

    public MainWindow mainWindow;
    
    public CustomToolBar(GridData toolData, MainWindow mainWindow){
	barraHerramientas = new ToolBar(MainWindow.shell, SWT.HORIZONTAL | SWT.FLAT);
        barraHerramientas.setLayoutData(toolData);
        toolData.heightHint = 23;
        this.mainWindow = mainWindow;
        barraHerramientas.setCursor(new Cursor(null, SWT.CURSOR_ARROW));
        getToolItems(barraHerramientas);
    }

    public void getToolItems(ToolBar toolbar) {
	toolItem[0] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[0].setImage(ImageLoader.getImage("nuevo.png"));
	toolItem[0].setToolTipText("Nuevo");
	toolItem[0].addSelectionListener(new NewDiagramAction());

	toolItem[1] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[1].setImage(ImageLoader.getImage("abrir.png"));
	toolItem[1].setToolTipText("Abrir");
	toolItem[1].addSelectionListener(new OpenDiagramAction());

	toolItem[2] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[2].setImage(ImageLoader.getImage("save.png"));
	toolItem[2].setToolTipText("Guardar");
	toolItem[2].addSelectionListener(new SaveDiagramAction(mainWindow));
	
	new ToolItem(toolbar, SWT.SEPARATOR);

	toolItem[3] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[3].setImage(ImageLoader.getImage("undo.png"));
	toolItem[3].setToolTipText(" Deshacer ");
	toolItem[3].addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent event) {
		TabItem item = (TabItem) MainWindow.getComponentes().diagramas.getSeleccion();
		item.retroceder();
		MainWindow.getComponentes().diagramas.getTabItem().getSave().setSave(false);
	    }
	});
	toolItem[4] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[4].setImage(ImageLoader.getImage("cortar.ico"));
	toolItem[4].setToolTipText(" Cortar ");
	toolItem[4].setEnabled(false);
	toolItem[4].addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			Figura fig = MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(MainWindow._selectionAdministrator.getFiguraSeleccionada());
			new ContextualMenuActions().Cortar(fig);
			MainWindow.getComponentes().toolBarDisable();
		}
	});
	toolItem[5] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[5].setImage(ImageLoader.getImage("copiar.png"));
	toolItem[5].setToolTipText(" Copiar ");
	toolItem[5].setEnabled(false);
	toolItem[5].addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			Figura fig = MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(
					MainWindow._selectionAdministrator
							.getFiguraSeleccionada());
			new ContextualMenuActions().Copiar(fig);
			MainWindow.getComponentes().toolBarDisable();
		}
	});
	toolItem[6] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[6].setImage(ImageLoader.getImage("pegar.ico"));
	toolItem[6].setToolTipText(" Pegar ");
	toolItem[6].setEnabled(false);
	toolItem[6].addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			Figura fig = MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(
					MainWindow._selectionAdministrator
							.getFiguraSeleccionada());
			new ContextualMenuActions().Pegar(fig);
			MainWindow.getComponentes().toolBarDisable();
		}
	});
	
	
	toolItem[7] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[7].setImage(ImageLoader.getImage("eliminar.png"));
	toolItem[7].setToolTipText(" Eliminar ");
	toolItem[7].setEnabled(false);
	toolItem[7].addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			Figura fig = MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(
					MainWindow._selectionAdministrator
							.getFiguraSeleccionada());
			new ContextualMenuActions().Eliminar(fig);
		}
	});
	new ToolItem(toolbar, SWT.SEPARATOR);

	toolItem[8] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[8].setImage(ImageLoader.getImage("codigo.png"));
	toolItem[8].setToolTipText("Generar Codigo");
	toolItem[8].addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			/*Instruccion codigo = new Instruccion();
			codigo.main(diagramas.getHoja().getDiagrama(), true);
			codigo.ventana(MainWindow.display);*/
		    Manager manager = new Manager(MainWindow.getComponentes().diagramas.getHoja().getDiagrama());
		    manager.formatCodeC();
		    System.out.println(manager.getInstructionsFormat());
		}
	});
	
	
	toolItem[9] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[9].setImage(ImageLoader.getImage("codigoCpp.png"));
	toolItem[9].setToolTipText("Generar Codigo C++");
	toolItem[9].addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			/*Instruccion codigo = new Instruccion();
			codigo.main(diagramas.getHoja().getDiagrama(), false);
			codigo.ventana(MainWindow.display);*/
		    Manager manager = new Manager(MainWindow.getComponentes().diagramas.getHoja().getDiagrama());
		    manager.formatCodeCpp();
		    System.out.println(manager.getInstructionsFormat());
		}
	});
	new ToolItem(toolbar, SWT.SEPARATOR);

	toolItem[10] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[10].setImage(ImageLoader.getImage("run.png"));
	toolItem[10].setToolTipText("Compilar/Ejecutar");
	toolItem[10].addSelectionListener(new CompileAction());
	
	toolItem[11] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[11].setImage(ImageLoader.getImage("check.png"));
	toolItem[11].setToolTipText("Paso A Paso");
	toolItem[11].addSelectionListener(new StepByStepAction(mainWindow));
	
	toolItem[12] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[12].setImage(ImageLoader.getImage("next.png"));
	toolItem[12].setEnabled(false);
	toolItem[12].setToolTipText("Paso Siguiente");
	toolItem[12].addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			if (MainWindow.getComponentes().paso.ventanaLeer) {
			    MainWindow.getComponentes().paso.ventanaLeer();
			} else {
			    MainWindow.getComponentes().next();
			}
		}
	});
	toolItem[13] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[13].setImage(ImageLoader.getImage("Stop.png"));
	toolItem[13].setEnabled(false);
	toolItem[13].setToolTipText("Terminar Ejecucion");
	toolItem[13].addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			if (MainWindow.getComponentes().isPasoAPaso) {
				MainWindow.getComponentes().disablePasoAPaso(false);
			}
			MainWindow.getComponentes().stopEjecucion();
		}
	});
	
	new ToolItem(toolbar, SWT.SEPARATOR);

	toolItem[14] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[14].setImage(ImageLoader.getImage("exportV.png"));
	toolItem[14].setToolTipText("Exportar a C");
	toolItem[14].addSelectionListener(new ExportToCAction(mainWindow));
	
	toolItem[15] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[15].setImage(ImageLoader.getImage("cpp.png"));
	toolItem[15].setToolTipText("Exportar a C++");
	toolItem[15].addSelectionListener(new ExportToCPPAction(mainWindow));
	
	toolItem[16] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[16].setImage(ImageLoader.getImage("exportExe.png"));
	toolItem[16].setToolTipText("Exportar a .exe");
	toolItem[16].addSelectionListener(new ExportToEXEAction(mainWindow));
	
	new ToolItem(toolbar, SWT.SEPARATOR);
	
	toolItem[17] = new ToolItem(toolbar, SWT.PUSH);
	toolItem[17].setImage(ImageLoader.getImage("Donate.png"));
	toolItem[17].setToolTipText("Donaciones");
	toolItem[17].addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent event) {
	    }
	});
    }
    public void disablePasoAPaso(boolean disable) {
	if (disable) {
	    toolItem[3].setEnabled(false);
	    toolItem[4].setEnabled(false);
	    toolItem[5].setEnabled(false);
	    toolItem[6].setEnabled(false);
	    toolItem[7].setEnabled(false);
	    toolItem[10].setEnabled(false);
	    toolItem[11].setEnabled(false);
	    toolItem[13].setEnabled(true);
	    toolItem[14].setEnabled(false);
	    toolItem[15].setEnabled(false);
	    toolItem[16].setEnabled(false);
	} else {
	    toolItem[3].setEnabled(true);
	    toolItem[10].setEnabled(true);
	    toolItem[11].setEnabled(true);
	    toolItem[13].setEnabled(false);
	    toolItem[14].setEnabled(true);
	    toolItem[15].setEnabled(true);
	    toolItem[16].setEnabled(true);
	}
    }
    public void disableAll(boolean disable) {
	toolItem[3].setEnabled(disable);
	toolItem[8].setEnabled(disable);
	toolItem[9].setEnabled(disable);
	toolItem[10].setEnabled(disable);
	toolItem[11].setEnabled(disable);
	toolItem[14].setEnabled(disable);
	toolItem[15].setEnabled(disable);
	toolItem[16].setEnabled(disable);
    }
}
