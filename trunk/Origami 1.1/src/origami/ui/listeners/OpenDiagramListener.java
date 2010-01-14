package origami.ui.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import origami.graphics.view.OpenFileView;
import origami.graphics.view.OpenType;


public class OpenDiagramListener implements SelectionListener{

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	OpenFileView open = new OpenFileView();
	open.setOpenType(OpenType.OPEN);
	open.createWindow();
    }

}
