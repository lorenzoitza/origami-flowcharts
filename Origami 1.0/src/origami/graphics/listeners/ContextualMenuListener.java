package origami.graphics.listeners;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;

import origami.administration.AdminSelection;
import origami.administration.ApplicationState;
import origami.administration.FigureStructure;
import origami.graphics.figures.CircleFigure;
import origami.graphics.widgets.ContextualMenu;
import origami.graphics.widgets.TabFolder;

public class ContextualMenuListener extends MouseListener.Stub{
    private ContextualMenu contextualMenu;
    private AdminSelection seleccion;
    private TabFolder currentTab;
	
    public ContextualMenuListener(FigureStructure figure, AdminSelection selec,
	    TabFolder tabfolder){
	figure.addMouseListener(this);
	contextualMenu = new ContextualMenu();
	seleccion = selec;
	currentTab = tabfolder;
    }
	
    public void mousePressed(MouseEvent event){
	FigureStructure fig = ((FigureStructure) event.getSource());
	int rightClick = 3;
	if(event.button == rightClick){
	    ApplicationState._selectionAdministrator.setSelectedFigure(getFigureIndex(fig));
//	    seleccion.setSelectedFigure(getFigureIndex(fig));
	    
	    contextualMenu.createMenu();
	    contextualMenu.getMenu().setVisible(true);
//	    if(fig instanceof CircleFigure){
//		contextualMenu.setEnabledEditMenuItems(false);
//		contextualMenu.setMenuAvailable();
//	    }	
//	    else{
//		contextualMenu.setEnabledEditMenuItems(true);
		contextualMenu.setMenuAvailable();
//	    }
	}
    }
    
    private int getFigureIndex(FigureStructure fig) {
	for (int figureIndex = 0; figureIndex < currentTab.getTabItem()
		.getLeaf().getSizeDiagrama(); figureIndex++) {
	    if (fig.getBounds() == currentTab.getTabItem().getLeaf()
		    .getFigureIndexOf(figureIndex).getBounds()) {
		return figureIndex;
	    }
	}
	return -1;
    }
}