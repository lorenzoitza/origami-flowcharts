package origami.administration.funtionality;

import java.io.*;

import origami.graphics.Componentes;
import origami.graphics.MainWindow;
import origami.graphics.widgets.TabItem;

/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez, Hudy Chan
 */
public class ConsoleController implements ExecProcessor {
	
    protected ExecHelper execHelper;
	
    public Componentes console;
	
    private CodeCompiler compiler;
	
    private int exitCode= 0;
	
    public TabItem tabItemSelected;
	
	
    public void processNewInput(String input) {
	MainWindow.display.syncExec(new ConsoleUpdater(console,input));
    }
	
    public void processNewError(String error) {
	MainWindow.display.syncExec(new ConsoleUpdater(console,error));
    }
	
    public void processEnded(int exitValue) {
	execHelper = null;
	try {
	    Thread.sleep(1000);
	} catch (InterruptedException ex) {
	    ex.printStackTrace();
	}
	compiler.deleteMainFiles();
    }
	
    void runCommandActionPerformed(String instruction) {
	if (execHelper == null) {
	    try {
		execHelper = ExecHelper.exec(this, instruction);
	    } catch (IOException ex) {
		processNewError(ex.getMessage());			}
	    }
    }

    public void inputActionPerformed(String text) {
	if (execHelper != null) {
	    execHelper.println(text);
	}
    }

    public void execute(Componentes console,String command, CodeCompiler codeCompiler) {
	this.tabItemSelected = (TabItem)MainWindow.getComponents()._diagrams.getSeleccion();
	this.compiler = codeCompiler;
	this.console = console;
	this.console.console.getTextField().setText("");
	runCommandActionPerformed(command);
    }

    public void stopExecution(){
	MainWindow.getComponents().barraHerramientas.getToolItems().get(12).setEnabled(false);
	if(execHelper != null){
	    execHelper.stopEjecucion();	
	}
    }

    public void processExit(int exitCode) {
	exit(exitCode);
    }
    
    private void exit(int exit){
	exitCode = exitCode + exit;
	if(exitCode == 3){
	    MainWindow.display.syncExec(new ConsoleExitProcess(console));
	}
    }
}