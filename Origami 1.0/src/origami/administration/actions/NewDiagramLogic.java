package origami.administration.actions;

import origami.graphics.MainWindow;
import origami.graphics.WindowWidgets;

public class NewDiagramLogic {
    
    public void addTab() {
	WindowWidgets.tabFolder.addTabItem();
	MainWindow.getComponents().setEnabledSaveItems(true);
	MainWindow.getComponents().disableAll(true);
    }
}
