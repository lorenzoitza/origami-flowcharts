package origami.administration.actions;

import origami.graphics.MainWindow;

public class NewDiagramLogic {
    
    public void action() {
	MainWindow.getComponents()._diagrams.addTabItem();
	MainWindow.getComponents().guardarDisable(true);
//	MainWindow.getComponents().disableAll(true);
    }
}
