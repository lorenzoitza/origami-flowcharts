package origami.graphics.listeners;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;

import origami.administration.FigureStructure;
import origami.graphics.figures.CircleFigure;
import origami.graphics.widgets.ContextualMenu;

public class ContextualMenuListener extends MouseListener.Stub{
    private ContextualMenu contextualMenu;
	
    public ContextualMenuListener(FigureStructure figure){
	figure.addMouseListener(this);
	contextualMenu = new ContextualMenu();
    }
	
    public void mousePressed(MouseEvent event){
	FigureStructure fig = ((FigureStructure) event.getSource());
	int rightClick = 3;
	if(event.button == rightClick){
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