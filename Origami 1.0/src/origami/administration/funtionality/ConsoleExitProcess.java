package origami.administration.funtionality;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import origami.graphics.Componentes;
import origami.graphics.MainWindow;


public class ConsoleExitProcess implements Runnable{
    
    private Componentes console;
    public ConsoleExitProcess(Componentes console){
	this.console = console;
    }
    
    public void run () {
	console.getByStepComponents().setEnEjecucion(console, false);
	console.getByStepComponents().ejecucionDisable(console);
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
