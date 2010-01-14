package origami.graphics;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import origami.administration.ApplicationState;
import origami.administration.funtionality.CodeCompiler;
import origami.administration.funtionality.ConsoleController;
import origami.administration.funtionality.PasoAPaso;
import origami.graphics.widgets.CustomFiguresToolBar;
import origami.graphics.widgets.CustomMenu;


public class StepByStepComponents {

    private ConsoleController eje;
    private PasoAPaso paso;
    private boolean isPasoAPaso = false;
    private boolean enEjecucion = false;
    private boolean seleccion;

    private CustomMenu menu;
    
    private CustomFiguresToolBar figuresToolBar;
    
    public StepByStepComponents(CustomMenu menu, CustomFiguresToolBar figuresToolBar){
	this.menu = menu;
	this.figuresToolBar = figuresToolBar;
    }
    
    public void ejecucionDisable(WindowWidgets componentes) {
    	if (isEnEjecucion()) {
    	    componentes.customConsole.getStopExecutionButton().setEnabled(true);
    		componentes.customToolBar.getToolItems().get(13).setEnabled(true);
    	} else {
    		componentes.customConsole.getStopExecutionButton().setEnabled(false);
    		componentes.customToolBar.getToolItems().get(13).setEnabled(false);
    	}
    }

    public void setText(WindowWidgets componentes, String text) {
    	if (isSeleccion()) {
    		getEje().inputActionPerformed(text);
    	} else {
    		getPaso().inputActionPerformed(text);
    	}
    }

    public void setEnEjecucion(WindowWidgets componentes, boolean ejecucion) {
    	this.enEjecucion = ejecucion;
    }

    public boolean getEnEjecucion(WindowWidgets componentes) {
    	return isEnEjecucion();
    }

    public void stopEjecucion(WindowWidgets componentes) {
        componentes.customToolBar.getToolItems().get(12).setEnabled(false);
    	setEnEjecucion(false);
    	ejecucionDisable(componentes);
    	if (isSeleccion()) {
    		getEje().stopExecution();
    	} else {
    		componentes.getByStepComponents().disablePasoAPaso(componentes, false);
    		getPaso().stopExecution();
    		getPaso().colaConexiones.clear();
    		int diag = WindowWidgets.tabFolder.getAdminSelection().getSeleccionDigrama();
    		getPaso().tab.getAdminSelection().setSeleccionDiagrama(getPaso().a.GetId());
    		getPaso().limpiarPasoAnterior();
    		WindowWidgets.tabFolder.getTabItem().getLeaf().pasoInicio = false;
    		WindowWidgets.tabFolder.getTabItem().getLeaf().addFigure();
    		getPaso().tab.getAdminSelection().setSeleccionDiagrama(diag);
    		componentes.customConsole.getTextField().setEditable(true);
    	}
    	componentes.getByStepComponents().eliminarArchivos();
    }

    public void eliminarArchivos() {
    	try {
    		File file = new File("main.exe");
    		File file2 = new File("main.cpp");
    		while (file.exists()) {
    			file.delete();
    		}
    		while (file2.exists()) {
    			file2.delete();
    		}
    	} catch (Exception e) {
    	}
    }

    public void next(WindowWidgets componentes) {
    	if (isEnEjecucion()) {
    	    componentes.customToolBar.getToolItems().get(12).setEnabled(false);
    		getPaso().sendNext();
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

    public void ejecutar(WindowWidgets componentes, boolean bandera, CodeCompiler codigo) {
    	setEnEjecucion(true);
    	ejecucionDisable(componentes);
    	if (bandera) {
    		setEje(new ConsoleController());
    		getEje().execute(componentes, "main.exe", codigo);
    		setSeleccion(true);
    	} else {
    		if (WindowWidgets.tabFolder.getTabItem().getLeaf().getSizeDiagrama() == 2) {
    			MessageBox messageBox = new MessageBox(MainWindow.shell,
    					SWT.ICON_INFORMATION | SWT.YES);
    			messageBox.setText("Origami");
    			messageBox.setMessage("La ejecucin ha terminado.");
    			int selec = messageBox.open();
    			switch (selec) {
    			case 0:
    				break;
    			case 64:
    				break;
    			default:
    				break;
    			}
    			setEnEjecucion(false);
    			ejecucionDisable(componentes);
    			componentes.getByStepComponents().disablePasoAPaso(componentes, false);
    		} else {
    			setPaso(new PasoAPaso(WindowWidgets.tabFolder,ApplicationState._selectionAdministrator));
    			getPaso().execute(componentes, "gdb", codigo);
    			getPaso().main();
    			setSeleccion(false);
    			MainWindow.getComponents().customToolBar.getToolItems().get(12).setEnabled(true);
    			componentes.customConsole.getTextField().setEditable(false);
    			componentes.customConsole.getTextField().setBackground(MainWindow.display
    					.getSystemColor(SWT.COLOR_WHITE));
    		}
    	}
    }

    public void disablePasoAPaso(WindowWidgets componentes, boolean disable) {
    	if (disable) {
    		setPasoAPaso(true);
    		WindowWidgets.tabFolder.getAdminSelection().setFiguraSeleccionada(-1);
    		WindowWidgets.tabFolder.getTabItem().getLeaf().addFigure();
    		
    		componentes.customToolBar.disableComponentStepByStep(disable);
    		
    		figuresToolBar.setEnabledAllButtons(!disable);
    		
    		
    		menu.setEnabledStepByStepMenuItems(!disable);
    
    	} else {
    		setPasoAPaso(false);
    		WindowWidgets.tabFolder.getTabItem().getLeaf().addFigure();
    		
    		componentes.customToolBar.disableComponentStepByStep(disable);
    		
    		figuresToolBar.setEnabledAllButtons(!disable);
    
    		menu.setEnabledStepByStepMenuItems(!disable);
    	}
    }

    public void setEje(ConsoleController eje) {
	this.eje = eje;
    }

    public ConsoleController getEje() {
	return eje;
    }

    public void setPaso(PasoAPaso paso) {
	this.paso = paso;
    }

    public PasoAPaso getPaso() {
	return paso;
    }

    public void setPasoAPaso(boolean isPasoAPaso) {
	this.isPasoAPaso = isPasoAPaso;
    }

    public boolean isPasoAPaso() {
	return isPasoAPaso;
    }

    public void setEnEjecucion(boolean enEjecucion) {
	this.enEjecucion = enEjecucion;
    }

    public boolean isEnEjecucion() {
	return enEjecucion;
    }

    public void setSeleccion(boolean seleccion) {
	this.seleccion = seleccion;
    }

    public boolean isSeleccion() {
	return seleccion;
    }

}
