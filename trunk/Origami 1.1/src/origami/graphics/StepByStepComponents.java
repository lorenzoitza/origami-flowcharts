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


public class StepByStepComponents {

    private ConsoleController eje;
    private PasoAPaso paso;
    private boolean isPasoAPaso = false;
    private boolean enEjecucion = false;
    private boolean seleccion;

    public void ejecucionDisable(Componentes componentes) {
    	if (isEnEjecucion()) {
    	    componentes.console.getStopExecutionButton().setEnabled(true);
    		componentes.barraHerramientas.getToolItems().get(13).setEnabled(true);
    	} else {
    		componentes.console.getStopExecutionButton().setEnabled(false);
    		componentes.barraHerramientas.getToolItems().get(13).setEnabled(false);
    	}
    }

    public void setText(Componentes componentes, String text) {
    	if (isSeleccion()) {
    		getEje().inputActionPerformed(text);
    	} else {
    		getPaso().inputActionPerformed(text);
    	}
    }

    public void setEnEjecucion(Componentes componentes, boolean ejecucion) {
    	this.enEjecucion = ejecucion;
    }

    public boolean getEnEjecucion(Componentes componentes) {
    	return isEnEjecucion();
    }

    public void stopEjecucion(Componentes componentes) {
        componentes.barraHerramientas.getToolItems().get(12).setEnabled(false);
    	setEnEjecucion(false);
    	ejecucionDisable(componentes);
    	if (isSeleccion()) {
    		getEje().stopExecution();
    	} else {
    		componentes.getByStepComponents().disablePasoAPaso(componentes, false);
    		getPaso().stopExecution();
    		getPaso().colaConexiones.clear();
    		int diag = Componentes._diagrams.getAdminSelection().getSeleccionDigrama();
    		getPaso().tab.getAdminSelection().setSeleccionDiagrama(getPaso().a.GetId());
    		getPaso().limpiarPasoAnterior();
    		Componentes._diagrams.getTabItem().getLeaf().pasoInicio = false;
    		Componentes._diagrams.getTabItem().getLeaf().addFigure();
    		getPaso().tab.getAdminSelection().setSeleccionDiagrama(diag);
    		componentes.console.getTextField().setEditable(true);
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

    public void next(Componentes componentes) {
    	if (isEnEjecucion()) {
    	    componentes.barraHerramientas.getToolItems().get(12).setEnabled(false);
    		getPaso().sendNext();
    	}
    	final Timer timer = new Timer();
    	TimerTask timerTask = new TimerTask() {
    		public void run() {
    			MainWindow.display.syncExec(new Runnable() {
    				public void run() {
    					MainWindow.getComponents().barraHerramientas.getToolItems().get(12)
    							.setEnabled(true);
    				}
    			});
    			timer.cancel();
    		}
    	};
    	timer.schedule(timerTask, 100);
    }

    public void ejecutar(Componentes componentes, boolean bandera, CodeCompiler codigo) {
    	setEnEjecucion(true);
    	ejecucionDisable(componentes);
    	if (bandera) {
    		setEje(new ConsoleController());
    		getEje().execute(componentes, "main.exe", codigo);
    		setSeleccion(true);
    	} else {
    		if (Componentes._diagrams.getTabItem().getLeaf().getSizeDiagrama() == 2) {
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
    			setPaso(new PasoAPaso(Componentes._diagrams,ApplicationState._selectionAdministrator));
    			getPaso().execute(componentes, "gdb", codigo);
    			getPaso().main();
    			setSeleccion(false);
    			MainWindow.getComponents().barraHerramientas.getToolItems().get(12).setEnabled(true);
    			componentes.console.getTextField().setEditable(false);
    			componentes.console.getTextField().setBackground(MainWindow.display
    					.getSystemColor(SWT.COLOR_WHITE));
    		}
    	}
    }

    public void disablePasoAPaso(Componentes componentes, boolean disable) {
    	if (disable) {
    		setPasoAPaso(true);
    		Componentes._diagrams.getAdminSelection().setFiguraSeleccionada(-1);
    		Componentes._diagrams.getTabItem().getLeaf().addFigure();
    		
    		componentes.barraHerramientas.disableComponentStepByStep(disable);
    		
    		componentes.figuresToolBar.setEnabledAllButtons(!disable);
    		
    		
    		componentes.menu.setEnabledStepByStepMenuItems(!disable);
    
    	} else {
    		setPasoAPaso(false);
    		Componentes._diagrams.getTabItem().getLeaf().addFigure();
    		
    		componentes.barraHerramientas.disableComponentStepByStep(disable);
    		
    		componentes.figuresToolBar.setEnabledAllButtons(!disable);
    
    		componentes.menu.setEnabledStepByStepMenuItems(!disable);
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
