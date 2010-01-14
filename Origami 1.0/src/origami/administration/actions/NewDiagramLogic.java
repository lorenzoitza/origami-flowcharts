package origami.administration.actions;

import origami.graphics.MainWindow;

public class NewDiagramLogic {
    
    public void action() {
	MainWindow.getComponents().tabFolder.addTabItem();
	MainWindow.getComponents().setEnabledSaveItems(true);
//	MainWindow.getComponents().disableAll(true);
    }
}
