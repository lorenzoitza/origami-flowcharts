package origami.administration.functionality;

import java.io.IOException;



public class WatchController implements ExecProcessor{
    
    protected ExecHelper execHelper;

    
    public void processEnded(int exitValue) {
	execHelper = null;
	try {
	    Thread.sleep(1000);
	} catch (InterruptedException ex) {
	    ex.printStackTrace();
	}
    }
    
    void runCommandActionPerformed(String instruction) {
	if (execHelper == null) {
	    try {
		execHelper = ExecHelper.exec(this, instruction);
	    } catch (IOException ex) {
		processNewError(ex.getMessage());}
	    }
    }
    
    public void inputActionPerformed(String text) {
	if (execHelper != null) {
	    execHelper.println(text);
	}
    }
   
    public void execute(String command) {
	runCommandActionPerformed(command);
    }

    public void processExit(int exit) {
	// TODO Auto-generated method stub
	
    }
   
    public void processNewError(String error) {
	System.out.println(error);
	
    }

    public void processNewInput(String input) {
	System.out.println(input);
    }
}
