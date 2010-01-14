package origami.ui.listeners;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;

import origami.administration.Figura;
import origami.graphics.figures.CircleFigure;
import origami.graphics.widgets.ContextualMenu;

public class ContextualMenuListener extends MouseListener.Stub{
    private ContextualMenu contextualMenu;
	
    public ContextualMenuListener(Figura figure){
	figure.addMouseListener(this);
	contextualMenu = new ContextualMenu();
    }
	
    public void mousePressed(MouseEvent event){
	Figura fig = ((Figura) event.getSource());
	int rightClickButton = 3;
	if(event.button == rightClickButton){
	    contextualMenu.createMenu();
	    contextualMenu.getMenu().setVisible(true);
	    if(fig instanceof CircleFigure){
		contextualMenu.setEnabledEditMenuItems(false);
	    }	
	    else{
		contextualMenu.setEnabledEditMenuItems(true);
	    }
	}
    }

}