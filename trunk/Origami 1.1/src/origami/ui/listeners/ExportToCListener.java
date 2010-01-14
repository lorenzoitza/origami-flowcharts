package origami.ui.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import origami.graphics.view.SaveFileView;
import origami.graphics.view.SaveType;




public class ExportToCListener implements SelectionListener{
    
    public ExportToCListener() {
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	SaveFileView save = new SaveFileView();
	save.setSaveType(SaveType.EXPORTC);
    }

}
