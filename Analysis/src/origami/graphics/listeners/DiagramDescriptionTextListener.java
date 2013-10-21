package origami.graphics.listeners;

import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Composite;

import origami.graphics.WindowWidgets;


public class DiagramDescriptionTextListener implements FocusListener{
    
    private Composite composite;
    
    public DiagramDescriptionTextListener(Composite composite){
	this.composite = composite;
    }
    public DiagramDescriptionTextListener(){
	
    }
    
    @Override
    public void focusGained(FocusEvent event) {
	System.out.println("Seleccionado");
	WindowWidgets.tabFolder.getTabItem().getInformation().secondRepresentation();
	System.out.println(WindowWidgets.tabFolder.getTabItem().getInformation().getInformation());
      }

      public void focusLost(FocusEvent event) {
        
      }
    
}