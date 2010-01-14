package origami.administration.funtionality;

import origami.graphics.Componentes;
import origami.graphics.MainWindow;


public class ConsoleUpdater implements Runnable{
    
    private Componentes consoleComponent;
    private String commandLine;
    
    public ConsoleUpdater(Componentes consoleComponent,String commandLine){
	this.consoleComponent = consoleComponent;
	this.commandLine = commandLine;
    }
    
    public void run () {
	if(!MainWindow.getComponents().getByStepComponents().isSeleccion()){
		String linea = MainWindow.getComponents().getByStepComponents().getPaso().texto(commandLine);					
		if(linea.compareTo("") != 0){
			linea = linea +"\n";
			consoleComponent.console.getTextField().append(linea);
		}
	}
	else{
		consoleComponent.console.getTextField().append(commandLine);
	}
	consoleComponent.console.getTextField().setDragDetect(true);
	consoleComponent.console.setMaxCaretPosition(consoleComponent.console.getTextField().getText().length());
    }


}
