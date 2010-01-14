package origami.administration.funtionality;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import origami.graphics.WindowWidgets;
import origami.graphics.MainWindow;


public class ConsoleExitProcess implements Runnable{
    
    private WindowWidgets console;
    public ConsoleExitProcess(WindowWidgets console){
	this.console = console;
    }
    
    public void run () {
	console.getByStepComponents().setExecution(console, false);
	console.getByStepComponents().disableExecution(console);
	MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_INFORMATION | SWT.YES );
	messageBox.setText("Origami");
	messageBox.setMessage("La ejecuci�n ha terminado.");
	int selec = messageBox.open();
	switch(selec){
		case 0:
			break;
		case 64:
			break;
		default:
			break;
	}
    }

}
