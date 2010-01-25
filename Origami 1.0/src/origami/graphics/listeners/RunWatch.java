package origami.graphics.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import origami.graphics.widgets.watch;


public class RunWatch implements SelectionListener {
    
    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
	watch v=new watch();
	v.insertString("suma Old value = 3 New value = 5");
    	v.insertString("multiplicacion Old value = 3 New value = 5");
    	v.insertString("suma Old value = 5 New value = 4"); 
    	v.insertString("suma Old value = 4 New value = 6"); 
    	v.insertString("multiplicacion Old value = 6 New value = 9"); 
    	v.insertString("multiplicacion Old value = 9 New value = 1");     	
    }

}
