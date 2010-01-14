package origami.graphics.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import origami.graphics.MainWindow;
import origami.graphics.view.SaveFileView;
import origami.graphics.view.SaveType;

public class SaveAsDiagramListener implements SelectionListener {

    public SaveAsDiagramListener() {
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	if (MainWindow.getComponents().getByStepComponents().getExecution() != null
		&& MainWindow.getComponents().getByStepComponents()
			.getEnEjecucion(MainWindow.getComponents())
		&& MainWindow.getComponents().tabFolder.getSelectedTabItemId() == MainWindow
			.getComponents().getByStepComponents().getExecution().tabItemSelected
			.GetId()) {
	    MainWindow.getComponents().getByStepComponents().stopExecution(
		    MainWindow.getComponents());
	} else if (MainWindow.getComponents().getByStepComponents().getStepByStep() != null
		&& MainWindow.getComponents().getByStepComponents()
			.getEnEjecucion(MainWindow.getComponents())
		&& MainWindow.getComponents().tabFolder.getSelectedTabItemId() == MainWindow
			.getComponents().getByStepComponents().getStepByStep().a
			.GetId()) {
	    MainWindow.getComponents().getByStepComponents().stopExecution(
		    MainWindow.getComponents());
	}
	MainWindow.getComponents().getByStepComponents().disableStepByStep(
		MainWindow.getComponents(), false);

	SaveFileView save = new SaveFileView();
	save.setSaveType(SaveType.SAVEAS);
	save.createWindow();

    }
}
