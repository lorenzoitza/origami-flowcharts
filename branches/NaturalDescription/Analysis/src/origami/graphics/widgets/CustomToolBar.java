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
import origami.administration.FigureStructure;
import origami.administration.actions.ContextualMenuActions;
import origami.graphics.MainWindow;
import origami.graphics.WindowWidgets;
import origami.graphics.listeners.CompileListener;
import origami.graphics.listeners.ExportToCListener;
import origami.graphics.listeners.ExportToCPPListener;
import origami.graphics.listeners.ExportToEXEListener;
import origami.graphics.listeners.NewDiagramListener;
import origami.graphics.listeners.OpenDiagramListener;
import origami.graphics.listeners.RunWatch;
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
		CustomTabItem item =
			(CustomTabItem) WindowWidgets.tabFolder
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
		FigureStructure fig =
			WindowWidgets.tabFolder
				.getTabItem()
				.getLeaf()
				.getFigureIndexOf(
					ApplicationState._selectionAdministrator
						.getSelectedFigure());
		new ContextualMenuActions().Cortar(fig);
//		disableToolBar();
		updateEnabledItems();
	    }
	});
	toolItems.add(toolItem);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("copiar.png"));
	toolItem.setToolTipText(" Copiar ");
	toolItem.setEnabled(false);
	toolItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		FigureStructure figure =
			WindowWidgets.tabFolder
				.getTabItem()
				.getLeaf()
				.getFigureIndexOf(
					ApplicationState._selectionAdministrator
						.getSelectedFigure());
		new ContextualMenuActions().Copiar(figure);
//		disableToolBar();
		updateEnabledItems();
	    }
	});
	toolItems.add(toolItem);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("pegar.ico"));
	toolItem.setToolTipText(" Pegar ");
	toolItem.setEnabled(false);
	toolItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		FigureStructure fig =
			WindowWidgets.tabFolder
				.getTabItem()
				.getLeaf()
				.getFigureIndexOf(
					ApplicationState._selectionAdministrator
						.getSelectedFigure());
		new ContextualMenuActions().Pegar(fig);
//		disableToolBar();
		updateEnabledItems();
	    }
	});
	toolItems.add(toolItem);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("eliminar.png"));
	toolItem.setToolTipText(" Eliminar ");
	toolItem.setEnabled(false);
	toolItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		FigureStructure fig =
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

	/**toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("codigoCpp.png"));
	toolItem.setToolTipText("Generar Codigo C++");
	toolItem.addSelectionListener(new ViewCodeCppListener());
	toolItems.add(toolItem);**/

	new ToolItem(toolbar, SWT.SEPARATOR);

	toolItem = new ToolItem(toolbar, SWT.PUSH);
	toolItem.setImage(ImageLoader.getImage("run.png"));
	toolItem.setToolTipText("Compilar/Ejecutar");
	toolItem.addSelectionListener(new CompileListener());
	toolItems.add(toolItem);
	
//	toolItem = new ToolItem(toolbar, SWT.PUSH);
//	toolItem.setImage(ImageLoader.getImage("run.png"));
//	toolItem.setToolTipText("Watch");
//	toolItem.addSelectionListener(new RunWatch());
//	toolItems.add(toolItem);

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

//    public void disableToolBar() {
//	int startStandartButtons=4;
//	int endStandartButtons=7;
//	int paste=6;
//	if (ApplicationState._selectionAdministrator.getSelectedFigure() == -1) {
//	    for (int index = startStandartButtons; index <= endStandartButtons; index++) {
//		toolItems.get(index).setEnabled(false);
//	    }
//	} else {
//	    if (ApplicationState._selectionAdministrator
//		    .getSelectedFigure() != 0) {
//		for (int index = startStandartButtons; index <= endStandartButtons; index++) {
//		    toolItems.get(index).setEnabled(true);
//		}
//		if (ApplicationState._diagramAdministrator.diagram.size() == 0) {
//		    toolItems.get(paste).setEnabled(false);
//		}
//	    } else {
//		for (int index = startStandartButtons; index <= endStandartButtons; index++) {
//		    toolItems.get(index).setEnabled(false);
//		}
//		if (ApplicationState._diagramAdministrator.diagram.size() != 0) {
//		    toolItems.get(paste).setEnabled(true);
//		}
//	    }
//	}
//    }

    public void updateEnabledItems(){
	if (ApplicationState._selectionAdministrator.getSelectedFigure() == -1) {
	    setEnabledItemsEdition(false, false);
	}
	else if (ApplicationState._selectionAdministrator.getSelectedFigure() == 0) {
	    if (ApplicationState._diagramAdministrator.diagram.size() != 0) {
		setEnabledItemsEdition(false, true);
	    }
	    else{
		setEnabledItemsEdition(false, false);
	    }
	}
	else {
	    if (ApplicationState._diagramAdministrator.diagram.size() != 0) {
		setEnabledItemsEdition(true, true);
	    }
	    else{
		setEnabledItemsEdition(true, false);
	    }
	}
	
    }
    public void setEnabledItemsEdition(boolean enabled,boolean cmdPaste){
	int cut=4;
	int copy=5;
	int paste=6;
	int delete=7;
	
	toolItems.get(cut).setEnabled(enabled);
	toolItems.get(copy).setEnabled(enabled);
	toolItems.get(paste).setEnabled(cmdPaste);
	toolItems.get(delete).setEnabled(enabled);
    }
    public void setEnabledItemsExport(boolean enabled){
	int exportCodeC=13;
	int exportCodeCpp=14;
	int exportExe=15;
	
	toolItems.get(exportCodeC).setEnabled(enabled);
	toolItems.get(exportCodeCpp).setEnabled(enabled);
	toolItems.get(exportExe).setEnabled(enabled);
    }
    public void setEnabledItemsExecutions(boolean enabled){
	int run=9;
//	int watch=10;
	int stepByStep=10;
	int nextStep=11;
	int stopRun=12;
	
	if(enabled){
	    toolItems.get(run).setEnabled(enabled);
//	    toolItems.get(watch).setEnabled(enabled);
	    toolItems.get(stepByStep).setEnabled(enabled);
	    toolItems.get(nextStep).setEnabled(false);
	    toolItems.get(stopRun).setEnabled(false);
	}
	else{
	    toolItems.get(run).setEnabled(enabled);
//	    toolItems.get(watch).setEnabled(enabled);
	    toolItems.get(stepByStep).setEnabled(enabled);
	    toolItems.get(nextStep).setEnabled(enabled);
	    toolItems.get(stopRun).setEnabled(enabled);
	}
    }
    public void setEnabledItemSave(boolean enabled){
	int save=2;
	toolItems.get(save).setEnabled(enabled);
    }
    public void setEnabledItemUndo(boolean enabled){
	int undo=3;
	toolItems.get(undo).setEnabled(enabled);
    }
    public void setEnabledItemGenerateCode(boolean enabled){
	int generateCode=8;
	toolItems.get(generateCode).setEnabled(enabled);
    }
    /**
     * Probarlooooooooooo
     * @param enabled
     */
    public void setEnabledItemsCloseAllTabItem(boolean enabled){
	if(enabled){
	    setEnabledItemsEdition(false, false);
	    setEnabledItemsExport(true);
	    setEnabledItemsExecutions(true);
	    setEnabledItemSave(true);
	    setEnabledItemUndo(true);
	    setEnabledItemGenerateCode(true);
	}
	else{
	    setEnabledItemsEdition(false, false);
	    setEnabledItemsExport(false);
	    setEnabledItemsExecutions(false);
	    setEnabledItemSave(false);
	    setEnabledItemUndo(false);
	    setEnabledItemGenerateCode(false);
	}
    }
    
//    public void setEnabledStepByStepToolItems(boolean isEnable) {
//	int back=3;
//	int cut=4;
//	int copy=5;
//	int paste=6;
//	int delete=7;
//	int execute=10;
//	int executeStepByStep=11;
//	int stopExecute=13;
//	int exportCodeC=14;
//	int exportCodeCpp=15;
//	int exportExe=16;
//	if (isEnable) {
//	    toolItems.get(back).setEnabled(true);
//	    toolItems.get(cut).setEnabled(false);
//	    toolItems.get(copy).setEnabled(false);
//	    toolItems.get(paste).setEnabled(false);
//	    toolItems.get(delete).setEnabled(false);
//	    toolItems.get(execute).setEnabled(true);
//	    toolItems.get(executeStepByStep).setEnabled(true);
//	    toolItems.get(stopExecute).setEnabled(false);
//	    toolItems.get(exportCodeC).setEnabled(true);
//	    toolItems.get(exportCodeCpp).setEnabled(true);
//	    toolItems.get(exportExe).setEnabled(true);
//	} else {
//	    toolItems.get(back).setEnabled(true);
//	    toolItems.get(execute).setEnabled(true);
//	    toolItems.get(execute).setEnabled(true);
//	    toolItems.get(stopExecute).setEnabled(false);
//	    toolItems.get(exportCodeC).setEnabled(true);
//	    toolItems.get(exportCodeCpp).setEnabled(true);
//	    toolItems.get(exportExe).setEnabled(true);
//	}
//    }

    public ArrayList<ToolItem> getToolItems() {
	return toolItems;
    }

    public ToolBar getToolBar() {
	return toolBar;
    }
}
