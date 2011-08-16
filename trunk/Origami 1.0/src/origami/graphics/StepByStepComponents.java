package origami.graphics;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import origami.administration.ApplicationState;
import origami.administration.functionality.CodeCompiler;
import origami.administration.functionality.ProcessConsole;
import origami.administration.functionality.PasoAPaso;
import origami.graphics.widgets.CustomFiguresToolBar;
import origami.graphics.widgets.CustomMenu;


public class StepByStepComponents {
    private ProcessConsole consoleController;
    
    private PasoAPaso stepByStep;
    
    private boolean isStepByStep;
    
    private boolean isExecution;
    
    private boolean isSelected;

    private CustomMenu customMenu;
    
    private CustomFiguresToolBar customFiguresToolBar;
    
    public StepByStepComponents(CustomMenu menu, CustomFiguresToolBar figuresToolBar){
	this.customMenu = menu;
	
	this.customFiguresToolBar = figuresToolBar;
	
	isStepByStep = false;
	    
	isExecution = false;
    }
    
    public void disableExecution(WindowWidgets windowWidgets) {
    	if(isExecuted()) {
    	    windowWidgets.customConsole.getStopExecutionButton().setEnabled(true);
    	    windowWidgets.customToolBar.getToolItems().get(13).setEnabled(true);
    	} else {
    	    windowWidgets.customConsole.getStopExecutionButton().setEnabled(false);
    	    windowWidgets.customToolBar.getToolItems().get(13).setEnabled(false);
    	}
    }

    public void setText(WindowWidgets windowComponents, String consoleText) {
    	if (isSelected()) {
    	    getExecution().inputActionPerformed(consoleText);
    	} else {
    	    getStepByStep().inputActionPerformed(consoleText);
    	}
    }

    public void setExecution(WindowWidgets componentes, boolean isExecuted) {
    	this.isExecution = isExecuted;
    }

    public boolean getEnEjecucion(WindowWidgets componentes) {
    	return isExecuted();
    }

    public void stopExecution(WindowWidgets componentes) {
        componentes.customToolBar.getToolItems().get(12).setEnabled(false);
    	setIsExecuted(false);
    	disableExecution(componentes);
    	if (isSelected()) {
    	    getExecution().stopExecution();
    	} else {
    	    componentes.getByStepComponents().disableStepByStep(componentes, false);
    	    getStepByStep().stopExecution();
    	    getStepByStep().colaConexiones.clear();
    	    int diag = WindowWidgets.tabFolder.getAdminSelection().getDiagramSelection();
    	    getStepByStep().tab.getAdminSelection().setDiagramSelection(getStepByStep().a.GetId());
    	    getStepByStep().limpiarPasoAnterior();
    	    WindowWidgets.tabFolder.getTabItem().getLeaf().pasoInicio = false;
    	    WindowWidgets.tabFolder.getTabItem().getLeaf().addFigure();
    	    getStepByStep().tab.getAdminSelection().setDiagramSelection(diag);
    	    componentes.customConsole.getTextField().setEditable(true);
    	}
    	componentes.getByStepComponents().deleteFiles();
    }

    public void deleteFiles() {
    	try {
    	    File exeFile = new File("main.exe");
    	    File cppFile = new File("main.cpp");
    	    while (exeFile.exists()) {
    		exeFile.delete();
    	    }
    	    while (cppFile.exists()) {
    		cppFile.delete();
    	    }
    	} catch (Exception e) {
    	}
    }

    public void next(WindowWidgets windowWidgets) {
    	if (isExecuted()) {
    	    windowWidgets.customToolBar.getToolItems().get(12).setEnabled(false);
    	    getStepByStep().sendNext();
    	}
    	final Timer timer = new Timer();
    	TimerTask timerTask = new TimerTask() {
    	    public void run() {
    		MainWindow.display.syncExec(new Runnable() {
    		    public void run() {
    			MainWindow.getComponents().customToolBar.getToolItems().get(12)
    			.setEnabled(true);
    		    }
    		});
    		timer.cancel();
    	    }
    	};
    	timer.schedule(timerTask, 100);
    }

    public void execute(WindowWidgets windowWidgets, boolean isSaved, CodeCompiler codeCompiler) {
    	setIsExecuted(true);
    	disableExecution(windowWidgets);
    	if (isSaved) {
    	    setConsoleController(new ProcessConsole());
    	    getExecution().execute(windowWidgets, "main.exe", codeCompiler);
    	    setSeleccion(true);
    	} else {
    	    if (WindowWidgets.tabFolder.getTabItem().getLeaf().getSizeDiagrama() == 2) {
    		MessageBox messageBox = new MessageBox(MainWindow.shell,
    			SWT.ICON_INFORMATION | SWT.YES);
    		messageBox.setText("Origami");
    		messageBox.setMessage("La ejecucion ha terminado.");
    		int selection = messageBox.open();
    		switch (selection) {
    		case 0:
    		    break;
    		case 64:
    		    break;
    		default:
    		    break;
    		}
    		setIsExecuted(false);
    		disableExecution(windowWidgets);
    		windowWidgets.getByStepComponents().disableStepByStep(windowWidgets, false);
    	    } else {
    		setStepByStep(new PasoAPaso(WindowWidgets.tabFolder,ApplicationState._selectionAdministrator));
//    		getStepByStep().execute(windowWidgets, "gdb", codeCompiler);
    		getStepByStep().execute(windowWidgets, "MinGW1.1/bin/gdb.exe", codeCompiler);
    		getStepByStep().main();
    		setSeleccion(false);
    		MainWindow.getComponents().customToolBar.getToolItems().get(12).setEnabled(true);
    		windowWidgets.customConsole.getTextField().setEditable(false);
    		windowWidgets.customConsole.getTextField().setBackground(MainWindow.display
    			.getSystemColor(SWT.COLOR_WHITE));
    	    }
    	}
    }

    public void disableStepByStep(WindowWidgets windowWidgets, boolean isEnable) {
    	if (isEnable) {
    	    setIsStepByStep(true);
    	    WindowWidgets.tabFolder.getAdminSelection().setSelectedFigure(-1);
    	    WindowWidgets.tabFolder.getTabItem().getLeaf().addFigure();
    		
    	    //revisar
//    	    windowWidgets.customToolBar.setEnabledStepByStepToolItems(isEnable);
//    		
//    	    customFiguresToolBar.setEnabledAllButtons(!isEnable);
//    	    
//    	    customMenu.setEnabledStepByStepMenuItems(!isEnable);
    
    	} else {
    	    setIsStepByStep(false);
    	    WindowWidgets.tabFolder.getTabItem().getLeaf().addFigure();
    		
    	    //revisar
//    	    windowWidgets.customToolBar.setEnabledStepByStepToolItems(isEnable);
//    		
//    	    customFiguresToolBar.setEnabledAllButtons(!isEnable);
//    
//    	    customMenu.setEnabledStepByStepMenuItems(!isEnable);
    	}
    }

    public void setConsoleController(ProcessConsole consoleController) {
	this.consoleController = consoleController;
    }

    public ProcessConsole getExecution() {
	return consoleController;
    }

    public void setStepByStep(PasoAPaso stepByStep) {
	this.stepByStep = stepByStep;
    }

    public PasoAPaso getStepByStep() {
	return stepByStep;
    }

    public void setIsStepByStep(boolean isStepByStep) {
	this.isStepByStep = isStepByStep;
    }

    public boolean isStepByStep() {
	return isStepByStep;
    }

    public void setIsExecuted(boolean isExecuted) {
	this.isExecution = isExecuted;
    }

    public boolean isExecuted() {
	return isExecution;
    }

    public void setSeleccion(boolean seleccion) {
	this.isSelected = seleccion;
    }

    public boolean isSelected() {
	return isSelected;
    }

}
