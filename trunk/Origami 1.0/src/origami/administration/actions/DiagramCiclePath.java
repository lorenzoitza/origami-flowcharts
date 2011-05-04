package origami.administration.actions;

import java.util.Vector;

import origami.administration.FigureStructure;
import origami.graphics.figures.EllipseFigure;


public class DiagramCiclePath {
    
    /**
     * Este metodo recibe un if y devuelve la localizacion de la parte no del if.
     * @param diagram
     * @param index
     * @return int
     */
    public static int getDecisionEndPointIndex(Vector<FigureStructure> diagram,int figureIndex){
	int index = figureIndex;
    	int xCoord = diagram.elementAt(index+1).getBounds().x-(diagram.elementAt(index).getBounds().x+diagram.elementAt(index).getBounds().width);
    	xCoord = diagram.elementAt(index).getBounds().x - xCoord;
    	int yCoord = diagram.elementAt(index).getBounds().y+diagram.elementAt(index).getBounds().height/2;
    	index++;
    	while(true){
    	    if(diagram.elementAt(index) instanceof EllipseFigure && diagram.elementAt(index).getBounds().x==xCoord && diagram.elementAt(index).getBounds().y==yCoord){
    		break;
    	    }
    	    index++;
    	}
    	return index;
    }
    /**
     * Este metodo recibe la localizacion del principio de la parte No del if
     * y te devuelve la localizacion del final de dicha parte.
     * @param diagram
     * @param index
     * @return int
     */
    public static int getFirstForPointIndex(Vector<FigureStructure> diagram,int figureIndex){
	int index = figureIndex;
	int xCoord = diagram.elementAt(index).getBounds().x;
    	int yCoord = diagram.elementAt(index-1).getBounds().y;
    	index++;
    	while(true){
    	    if(diagram.elementAt(index) instanceof EllipseFigure && diagram.elementAt(index).getBounds().x==xCoord && diagram.elementAt(index).getBounds().y==yCoord){
    		break;
    	    }
    	    index++;
    	}
    	return index;
    }
    /**
     * Este metodo recibe un For o While
     * y te devuelve la localizacion del final de dicha figura.
     * @param diagram
     * @param index
     * @return int
     */
    public static int getLastForPointIndex(Vector<FigureStructure> diagram,int figureIndex){
	int index = figureIndex;
    	int xCoord = diagram.elementAt(index).getBounds().x + diagram.elementAt(index).getBounds().width / 2;
    	int yCoord = diagram.elementAt(index).getBounds().y + diagram.elementAt(index).getBounds().height / 2;;
    	index++;
    	while(true){
    	    if(diagram.elementAt(index) instanceof EllipseFigure && diagram.elementAt(index).getBounds().x==xCoord
    		    && diagram.elementAt(index+1) instanceof EllipseFigure && diagram.elementAt(index + 2) instanceof EllipseFigure
    		    && diagram.elementAt(index+1).getBounds().y == diagram.elementAt(index).getBounds().y
    		    && diagram.elementAt(index+2).getBounds().y == yCoord ){
    		break;
    	    }
    	    index++;
    	}
   	return index;
    }
}
