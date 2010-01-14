package origami.administration.actions;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.Cursor;

import origami.administration.AdminSelection;
import origami.administration.ApplicationState;
import origami.administration.FigureStructure;
import origami.graphics.figures.DecisionFigure;
import origami.graphics.figures.DecisionFigureEnd;
import origami.graphics.figures.EllipseFigure;
import origami.graphics.figures.ForFigure;
import origami.graphics.figures.WhileFigure;
import origami.graphics.widgets.TabFolder;


public class AddFigureVerification{
    private Point start;
    	
    private boolean flag = false;
	
    private final Cursor[] cursor = new Cursor[1];
	
    private int mainCursor = SWT.CURSOR_ARROW;
	
    private AdminSelection adminSelection;
	
    private TabFolder tabFolder;

    public AddFigureVerification(AdminSelection selecc,TabFolder tabfolder) {
	adminSelection = selecc;
	tabFolder = tabfolder;
    }

    public void mousePressedCoordinate(MouseEvent event) {
	flag = false;
	start = event.getLocation();
	FigureStructure figure = ApplicationState.mainFigure;
	if(figure!=null){
	    int a;
	    for(int z=0;z<tabFolder.getTabItem().getLeaf().getSizeDiagrama()-1;z++){
		if(verifyAvailableSpace(z,z+1,figure)){
		    break;
		}
		if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(z+1) instanceof DecisionFigure){
		    a = verifyRight(z+1,figure);
		    z = verifyRight(a,figure); 
		    if(flag){
			break;
		    }
		}
		if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(z+1) instanceof ForFigure || tabFolder.getTabItem().getLeaf().getFigureIndexOf(z+1) instanceof WhileFigure){
		    a = verifyDown(z+1,figure);
		    z=a+4;
		    if(flag){
			break;
		    }
		}
	    }
	}
	else{
	    adminSelection.setSelectedFigure(-1);
	    if(tabFolder.getItemCount()!=0){
		tabFolder.getTabItem().getLeaf().addFigure();
	    }
	}
    }
    /**
     * Este metodo hace un recorrido por la derecha
     * de un if del diagrama para identificar si alguna 
     * figura fue añadida al diagrama, si no regresa la pocision
     * del final del lado derecho.
     * @param figureIndex
     * @param figure
     * @return int
     */
    public int verifyRight(int figureIndex,FigureStructure figure){
	int index = figureIndex+1;
	int cicleIndex;
	figureIndex = figureIndex + 2;
	while(tabFolder.getTabItem().getLeaf().getFigureIndexOf(figureIndex)!=null){
	    if(verifyAvailableSpace(index,figureIndex,figure)){
		break;
	    }
	    if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(figureIndex) instanceof DecisionFigure){
		cicleIndex = verifyRight(figureIndex,figure);
		figureIndex = verifyRight(cicleIndex,figure)+1;
		if(flag){
		    break;
		}
	    }
	    else if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(figureIndex) instanceof EllipseFigure){
		break;
	    }
	    else if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(figureIndex) instanceof ForFigure || tabFolder.getTabItem().getLeaf().getFigureIndexOf(figureIndex) instanceof WhileFigure){
		cicleIndex = verifyDown(figureIndex,figure);
		figureIndex=cicleIndex+5;
		if(flag){
		    break;
		}
	    }
	    index=figureIndex;
	    figureIndex++;
	    if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(figureIndex) instanceof DecisionFigureEnd){
		break;
	    }
	}
	return figureIndex;
    }
    /**
     * Este metodo hace un recorrido por la abajo
     * de un For o While del diagrama para identificar si alguna 
     * figura fue añadida al diagrama, si no regresa la pocision
     * del final de dicha figura.
     * 
     * @param figureIndex
     * @param figure
     * @return int
     */
    public int verifyDown(int figureIndex,FigureStructure figure){
	int index = figureIndex;
	int cicleIndex;
	figureIndex = figureIndex+1;
	while(tabFolder.getTabItem().getLeaf().getFigureIndexOf(figureIndex)!=null){
	    if(verifyAvailableSpace(index,figureIndex,figure)){
		break;
	    }
	    if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(figureIndex) instanceof DecisionFigure){
		cicleIndex = verifyRight(figureIndex,figure);
		figureIndex = verifyRight(cicleIndex,figure)+1;
		if(flag){
		    break;
		}
	    }
	    else if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(figureIndex) instanceof EllipseFigure){
		break;
	    }
	    else if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(figureIndex) instanceof ForFigure || tabFolder.getTabItem().getLeaf().getFigureIndexOf(figureIndex) instanceof WhileFigure){
		cicleIndex = verifyDown(figureIndex,figure);
		figureIndex=cicleIndex+4;
		index=figureIndex;
		figureIndex++;
		if(flag){
		    break;
		}
	    }
	    index=figureIndex;
	    figureIndex++;
	}
	return figureIndex;
    }
    /**
     * Este metodo revisa si en los espacios 
     * disponibles en el diagrama fue agregado 
     * una nueva figura, si no devuelve falso.
     * @param firstFigureIndex
     * @param secondFigureIndex
     * @param figure
     * @return boolean
     */
    public boolean verifyAvailableSpace(int firstFigureIndex, int secondFigureIndex, FigureStructure figure){
	if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(firstFigureIndex) instanceof EllipseFigure && tabFolder.getTabItem().getLeaf().getFigureIndexOf(secondFigureIndex) instanceof EllipseFigure){
	    if(start.x >= tabFolder.getTabItem().getLeaf().getFigureIndexOf(firstFigureIndex).getBounds().x-15 && start.x <= tabFolder.getTabItem().getLeaf().getFigureIndexOf(firstFigureIndex).getBounds().x+15 && 
		    start.y >= tabFolder.getTabItem().getLeaf().getFigureIndexOf(firstFigureIndex).getBounds().y && start.y <= tabFolder.getTabItem().getLeaf().getFigureIndexOf(secondFigureIndex).getBounds().y){
		tabFolder.getTabItem().getLeaf().getAdminDiagrama().orderDiagram(firstFigureIndex, figure);
		tabFolder.getTabItem().getSave().setSave(false);
		tabFolder.getTabItem().getInformation().addInformation("/A - Se agrego una nueva figura al diagrama\n");
		tabFolder.getTabItem().getInformation().setFigure(figure);
		tabFolder.getTabItem().getInformation().setDiagram(tabFolder.getTabItem().getLeaf().getDiagrama());
		tabFolder.getTabItem().getLeaf().addFigure();
		tabFolder.getTabItem().getLeaf().guardarRetroceso();
		changeCursor();
		flag = true;
		return true;
	    }
	}
	else if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(firstFigureIndex).getBounds().x + tabFolder.getTabItem().getLeaf().getFigureIndexOf(firstFigureIndex).getBounds().width-1>= start.x && start.x>= tabFolder.getTabItem().getLeaf().getFigureIndexOf(firstFigureIndex).getBounds().x+1 && 
		tabFolder.getTabItem().getLeaf().getFigureIndexOf(secondFigureIndex).getBounds().y-1 >=start.y &&start.y>=tabFolder.getTabItem().getLeaf().getFigureIndexOf(firstFigureIndex).getBounds().y+tabFolder.getTabItem().getLeaf().getFigureIndexOf(firstFigureIndex).getBounds().height+1){
	    tabFolder.getTabItem().getLeaf().getAdminDiagrama().orderDiagram(firstFigureIndex, figure);
	    tabFolder.getTabItem().getSave().setSave(false);
	    tabFolder.getTabItem().getInformation().addInformation("/A - Se agrego una nueva figura al diagrama\n");
	    tabFolder.getTabItem().getInformation().setFigure(figure);
	    tabFolder.getTabItem().getInformation().setDiagram(tabFolder.getTabItem().getLeaf().getDiagrama());
	    tabFolder.getTabItem().getLeaf().addFigure();
	    tabFolder.getTabItem().getLeaf().guardarRetroceso();
	    changeCursor();
	    flag = true;
	    return true;
	}
	else if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(firstFigureIndex) instanceof EllipseFigure ){ 
	    if(start.x>=tabFolder.getTabItem().getLeaf().getFigureIndexOf(firstFigureIndex).getBounds().x-75 && start.x<=tabFolder.getTabItem().getLeaf().getFigureIndexOf(firstFigureIndex).getBounds().x+75 
		    && start.y>=tabFolder.getTabItem().getLeaf().getFigureIndexOf(firstFigureIndex).getBounds().y && start.y<=tabFolder.getTabItem().getLeaf().getFigureIndexOf(secondFigureIndex).getBounds().y){
		tabFolder.getTabItem().getLeaf().getAdminDiagrama().orderDiagram(firstFigureIndex, figure);
		tabFolder.getTabItem().getSave().setSave(false);
		tabFolder.getTabItem().getInformation().addInformation("/A - Se agrego una nueva figura al diagrama\n");
		tabFolder.getTabItem().getInformation().setFigure(figure);
		tabFolder.getTabItem().getInformation().setDiagram(tabFolder.getTabItem().getLeaf().getDiagrama());
		tabFolder.getTabItem().getLeaf().addFigure();
		tabFolder.getTabItem().getLeaf().guardarRetroceso();
		changeCursor();
		flag = true;
		return true;
	    }
	}
	flag=false;
	return false;
    }
    
    private void changeCursor(){
	ApplicationState.mainFigure = null;
	Cursor oldCursor = cursor[0];
	cursor[0] = new Cursor(null, mainCursor);
	tabFolder.getTabItem().getLeaf().getChart().setCursor(cursor[0]);
	if (oldCursor != null){
	    oldCursor.dispose();
	}
    }
}