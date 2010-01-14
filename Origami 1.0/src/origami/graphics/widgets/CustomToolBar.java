package origami.graphics.widgets;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import origami.administration.ApplicationState;
import origami.administration.Figura;
import origami.administration.actions.ContextualMenuActions;
import origami.graphics.MainWindow;
import origami.graphics.WindowWidgets;
import origami.graphics.listeners.CompileListener;
import origami.graphics.listeners.ExportToCListener;
import origami.graphics.listeners.ExportToCPPListener;
import origami.graphics.listeners.ExportToEXEListener;
import origami.graphics.listeners.NewDiagramListener;
import origami.graphics.listeners.OpenDiagramListener;
import origami.graphics.listeners.SaveDiagramListener;
import origami.graphics.listeners.StepByStepListener;
import origami.graphics.listeners.ViewCodeCListener;
import origami.graphics.listeners.ViewCodeCppListener;
import origami.images.ImageLoader;

public class CustomToolBar {

    private ToolBar toolBar;

    private ArrayList<ToolItem> toolItems;

    public CustomToolBar(GridData toolBarLayoutData) {
	toolBar = new ToolBar(MainWindow.shell, SWT.HORIZONTAL | SWT.FLAT);
	toolBar.setLayoutData(toolBarLayoutData);
	toolBar.setCursor(new Cursor(null, SWT.CURSOR_ARROW));

	toolItems = new ArrayList<ToolItem>();

	initToolItems(toolBar);
    }

    public void initToolItems(ToolBar toolbar) {
	ToolItem toolItem;

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("nuevo.png"));
	toolItem.setToolTipText("Nuevo");
	toolItem.addSelectionListener(new NewDiagramListener());
	toolItems.add(toolItem);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("abrir.png"));
	toolItem.setToolTipText("Abrir");
	toolItem.addSelectionListener(new OpenDiagramListener());
	toolItems.add(toolItem);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("save.png"));
	toolItem.setToolTipText("Guardar");
	toolItem.addSelectionListener(new SaveDiagramListener());
	toolItems.add(toolItem);

	new ToolItem(toolbar, SWT.SEPARATOR);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("undo.png"));
	toolItem.setToolTipText(" Deshacer ");
	toolItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		TabItem item =
			(TabItem) WindowWidgets.tabFolder
				.getSeleccion();
		item.undo();
		WindowWidgets.tabFolder.getTabItem().getSave()
			.setSave(false);
	    }
	});
	toolItems.add(toolItem);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("cortar.ico"));
	toolItem.setToolTipText(" Cortar ");
	toolItem.setEnabled(false);
	toolItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		Figura fig =
			WindowWidgets.tabFolder
				.getTabItem()
				.getLeaf()
				.getFigureIndexOf(
					ApplicationState._selectionAdministrator
						.getSelectedFigure());
		new ContextualMenuActions().Cortar(fig);
		disableToolBar();
	    }
	});
	toolItems.add(toolItem);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("copiar.png"));
	toolItem.setToolTipText(" Copiar ");
	toolItem.setEnabled(false);
	toolItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		Figura figure =
			WindowWidgets.tabFolder
				.getTabItem()
				.getLeaf()
				.getFigureIndexOf(
					ApplicationState._selectionAdministrator
						.getSelectedFigure());
		new ContextualMenuActions().Copiar(figure);
		disableToolBar();
	    }
	});
	toolItems.add(toolItem);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("pegar.ico"));
	toolItem.setToolTipText(" Pegar ");
	toolItem.setEnabled(false);
	toolItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		Figura fig =
			WindowWidgets.tabFolder
				.getTabItem()
				.getLeaf()
				.getFigureIndexOf(
					ApplicationState._selectionAdministrator
						.getSelectedFigure());
		new ContextualMenuActions().Pegar(fig);
		disableToolBar();
	    }
	});
	toolItems.add(toolItem);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("eliminar.png"));
	toolItem.setToolTipText(" Eliminar ");
	toolItem.setEnabled(false);
	toolItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		Figura fig =
			WindowWidgets.tabFolder
				.getTabItem()
				.getLeaf()
				.getFigureIndexOf(
					ApplicationState._selectionAdministrator
						.getSelectedFigure());
		new ContextualMenuActions().Eliminar(fig);
	    }
	});
	toolItems.add(toolItem);

	new ToolItem(toolbar, SWT.SEPARATOR);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("codigo.png"));
	toolItem.setToolTipText("Generar Codigo");
	toolItem.addSelectionListener(new ViewCodeCListener());
	toolItems.add(toolItem);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("codigoCpp.png"));
	toolItem.setToolTipText("Generar Codigo C++");
	toolItem.addSelectionListener(new ViewCodeCppListener());
	toolItems.add(toolItem);

	new ToolItem(toolbar, SWT.SEPARATOR);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("run.png"));
	toolItem.setToolTipText("Compilar/Ejecutar");
	toolItem.addSelectionListener(new CompileListener());
	toolItems.add(toolItem);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("check.png"));
	toolItem.setToolTipText("Paso A Paso");
	toolItem.addSelectionListener(new StepByStepListener());
	toolItems.add(toolItem);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("next.png"));
	toolItem.setEnabled(false);
	toolItem.setToolTipText("Paso Siguiente");
	toolItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		if (MainWindow.getComponents().getByStepComponents().getStepByStep().ventanaLeer) {
		    MainWindow.getComponents().getByStepComponents().getStepByStep()
			    .ventanaLeer();
		} else {
		    MainWindow.getComponents().getByStepComponents().next(
			    MainWindow.getComponents());
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
		if (MainWindow.getComponents().getByStepComponents()
			.isStepByStep()) {
		    MainWindow
			    .getComponents()
			    .getByStepComponents()
			    .disableStepByStep(MainWindow.getComponents(), false);
		}
		MainWindow.getComponents().getByStepComponents().stopExecution(
			MainWindow.getComponents());
	    }
	});
	toolItems.add(toolItem);

	new ToolItem(toolbar, SWT.SEPARATOR);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("exportV.png"));
	toolItem.setToolTipText("Exportar a C");
	toolItem.addSelectionListener(new ExportToCListener());
	toolItems.add(toolItem);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("cpp.png"));
	toolItem.setToolTipText("Exportar a C++");
	toolItem.addSelectionListener(new ExportToCPPListener());
	toolItems.add(toolItem);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("exportExe.png"));
	toolItem.setToolTipText("Exportar a .exe");
	toolItem.addSelectionListener(new ExportToEXEListener());
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

    public void disableToolBar() {
	int startStandartButtons=4;
	int endStandartButtons=7;
	int paste=6;
	if (ApplicationState._selectionAdministrator.getSelectedFigure() == -1) {
	    for (int index = startStandartButtons; index <= endStandartButtons; index++) {
		toolItems.get(index).setEnabled(false);
	    }
	} else {
	    if (ApplicationState._selectionAdministrator
		    .getSelectedFigure() != 0) {
		for (int index = startStandartButtons; index <= endStandartButtons; index++) {
		    toolItems.get(index).setEnabled(true);
		}
		if (ApplicationState._diagramAdministrator.diagram.size() == 0) {
		    toolItems.get(paste).setEnabled(false);
		}
	    } else {
		for (int index = startStandartButtons; index <= endStandartButtons; index++) {
		    toolItems.get(index).setEnabled(false);
		}
		if (ApplicationState._diagramAdministrator.diagram.size() != 0) {
		    toolItems.get(paste).setEnabled(true);
		}
	    }
	}
    }

    public void setEnabledStepByStepToolItems(boolean isEnable) {
	int back=3;
	int cut=4;
	int copy=5;
	int paste=6;
	int delete=7;
	int execute=10;
	int executeStepByStep=11;
	int stopExecute=13;
	int exportCodeC=14;
	int exportCodeCpp=15;
	int exportExe=16;
	if (isEnable) {
	    toolItems.get(back).setEnabled(true);
	    toolItems.get(cut).setEnabled(false);
	    toolItems.get(copy).setEnabled(false);
	    toolItems.get(paste).setEnabled(false);
	    toolItems.get(delete).setEnabled(false);
	    toolItems.get(execute).setEnabled(true);
	    toolItems.get(executeStepByStep).setEnabled(true);
	    toolItems.get(stopExecute).setEnabled(false);
	    toolItems.get(exportCodeC).setEnabled(true);
	    toolItems.get(exportCodeCpp).setEnabled(true);
	    toolItems.get(exportExe).setEnabled(true);
	} else {
	    toolItems.get(back).setEnabled(true);
	    toolItems.get(execute).setEnabled(true);
	    toolItems.get(execute).setEnabled(true);
	    toolItems.get(stopExecute).setEnabled(false);
	    toolItems.get(exportCodeC).setEnabled(true);
	    toolItems.get(exportCodeCpp).setEnabled(true);
	    toolItems.get(exportExe).setEnabled(true);
	}
    }

    public void setEnableCompileToolItems(boolean isEnabled) {
	int back=3;
	int generateCCode=8;
	int generateCppCode=9;
	int execute=10;
	int executeStepByStep=11;
	int exportCodeC=14;
	int exportCodeCpp=15;
	int exportExe=16;
	
	toolItems.get(back).setEnabled(isEnabled);
	toolItems.get(generateCCode).setEnabled(isEnabled);
	toolItems.get(generateCppCode).setEnabled(isEnabled);
	toolItems.get(execute).setEnabled(isEnabled);
	toolItems.get(executeStepByStep).setEnabled(isEnabled);
	toolItems.get(exportCodeC).setEnabled(isEnabled);
	toolItems.get(exportCodeCpp).setEnabled(isEnabled);
	toolItems.get(exportExe).setEnabled(isEnabled);
    }

    public ArrayList<ToolItem> getToolItems() {
	return toolItems;
    }

    public ToolBar getToolBar() {
	return toolBar;
    }
}
