package origami.graphics.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.MessageBox;

import origami.graphics.WindowWidgets;
import origami.graphics.MainWindow;
import origami.graphics.widgets.CustomTabFolder;
import origami.graphics.widgets.CustomTabItem;

/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class CloseWindowView {

    public CustomTabFolder tab;

    public WindowWidgets console;

    public CloseWindowView(CustomTabFolder tabfolder, WindowWidgets console) {
	tab = tabfolder;
	this.console = console;
    }

    public void shellClosed(ShellEvent shellEvent) {
	int optionSelected = 0;

	CTabItem tabItems[] = tab.getTabFolder().getItems();

	CustomTabItem actualTab;
	boolean diagramNotSave = false;

	for (int tabIndex = 0; tabIndex < tabItems.length; tabIndex++) {

	    actualTab = (CustomTabItem) tabItems[tabIndex];

	    if (!actualTab.getSave().isSave()) {

		diagramNotSave = true;
		break;
	    }
	}
	if (diagramNotSave) {

	    MessageBox messageBox =
		    new MessageBox(MainWindow.shell, SWT.ICON_WARNING | SWT.YES
			    | SWT.NO | SWT.CANCEL);

	    messageBox.setText("Origami");
	    messageBox
		    .setMessage("Existen diagramas sin guardar. ¿Desea guardarlos?");
	    optionSelected = messageBox.open();
	    switch (optionSelected) {
    	    case 64:
    		shellEvent.doit = false;
    		break;
    	    case 128:
    		if (MainWindow.getComponents().getByStepComponents()
    			.getEnEjecucion(MainWindow.getComponents())) {
    		    MainWindow.getComponents().getByStepComponents()
    			    .stopExecution(MainWindow.getComponents());
    		}
    //		if (MainWindow.getComponents().getByStepComponents()
    //			.getEnEjecucion(MainWindow.getComponents())) {
    //		    MainWindow.getComponents().getByStepComponents()
    //			    .stopExecution(MainWindow.getComponents());
    //		}
    		break;
    	    case 256:
    		shellEvent.doit = false;
    		break;
	    }
	}
    }

}