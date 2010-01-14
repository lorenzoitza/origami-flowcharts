package origami.administration.funtionality;

import origami.graphics.WindowWidgets;
import origami.graphics.MainWindow;


public class ConsoleUpdater implements Runnable{
    
    private WindowWidgets consoleComponent;
    private String commandLine;
    
    public ConsoleUpdater(WindowWidgets consoleComponent,String commandLine){
	this.consoleComponent = consoleComponent;
	this.commandLine = commandLine;
    }
    
    public void run () {
	if(!MainWindow.getComponents().getByStepComponents().isSelected()){
		String linea = MainWindow.getComponents().getByStepComponents().getStepByStep().texto(commandLine);					
		if(linea.compareTo("") != 0){
			linea = linea +"\n";
			consoleComponent.customConsole.getTextField().append(linea);
		}
	}
	else{
		consoleComponent.customConsole.getTextField().append(commandLine);
	}
	consoleComponent.customConsole.getTextField().setDragDetect(true);
	consoleComponent.customConsole.setMaxCaretPosition(consoleComponent.customConsole.getTextField().getText().length());
    }


}
