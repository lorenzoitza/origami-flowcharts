package origami.administration;

import java.util.Vector;

import origami.graphics.figures.DecisionFigure;
import origami.graphics.figures.DecisionFigureEnd;
import origami.graphics.figures.EllipseFigure;
import origami.graphics.figures.ForFigure;
import origami.graphics.figures.WhileFigure;

/**
 * Esta clase administra las figuras del diagrama.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez, hudy y rodrigo estuvo aqui chuy
 */
public class AdminDiagram {

    public Vector<FigureStructure> diagram = new Vector<FigureStructure>();

    public AdminSelection adminSelection;

    public AdminDiagram(AdminSelection seleccion) {
	adminSelection = seleccion;
    }

    private void addDecisionFigure(int currentPosition, Vector<FigureStructure> figures) {
	EllipseFigure coord1 = new EllipseFigure();
	
	EllipseFigure coord2 = new EllipseFigure();
	
	EllipseFigure coord3 = new EllipseFigure();
	
	EllipseFigure coord4 = new EllipseFigure();
	
	diagram.add(currentPosition, coord1);
	
	currentPosition++;
	
	diagram.add(currentPosition, coord2);
	
	currentPosition++;
	
	diagram.add(currentPosition, coord3);
	currentPosition++;
	diagram.add(currentPosition, coord4);
	currentPosition++;
	diagram.add(currentPosition, new DecisionFigureEnd());
	currentPosition++;
	for (int u = currentPosition; u < figures.size() + 6; u++) {
	    diagram.add(u, figures.elementAt(u - 6));
	}
    }

    private void addForAndWhileFigure(int currentPosition, Vector<FigureStructure> figures) {
	EllipseFigure coord1 = new EllipseFigure();
	EllipseFigure coord2 = new EllipseFigure();
	EllipseFigure coord3 = new EllipseFigure();
	EllipseFigure coord4 = new EllipseFigure();
	EllipseFigure coord5 = new EllipseFigure();
	EllipseFigure coord6 = new EllipseFigure();
	diagram.add(currentPosition, coord1);
	currentPosition++;
	diagram.add(currentPosition, coord2);
	currentPosition++;
	diagram.add(currentPosition, coord3);
	currentPosition++;
	diagram.add(currentPosition, coord4);
	currentPosition++;
	diagram.add(currentPosition, coord5);
	currentPosition++;
	diagram.add(currentPosition, coord6);
	currentPosition++;
	for (int u = currentPosition; u < figures.size() + 7; u++) {
	    diagram.add(u, figures.elementAt(u - 7));
	}
    }

    /**
     * Este metodo ordena las figuras, y las coloca en la posicion en las que
     * deben de estar en el diagrama.
     * 
     * @param position
     * @param figure
     */
    public void orderDiagram(int position, FigureStructure figure) {

	Vector<FigureStructure> temporalDiagram = new Vector<FigureStructure>();
	int currentPosition = 0;
	for (int index = 0; index < diagram.size(); index++) {
	    temporalDiagram.add(index, diagram.elementAt(index));
	}
	diagram.removeAllElements();
	while (currentPosition != position + 1) {
	    diagram.add(currentPosition, temporalDiagram
		    .elementAt(currentPosition));
	    currentPosition++;
	}
	diagram.add(currentPosition, figure);
	adminSelection.setSelectedFigure(currentPosition);
	currentPosition++;
	if (diagram.elementAt(currentPosition - 1) instanceof DecisionFigure) {
	    addDecisionFigure(currentPosition, temporalDiagram);
	} else if (diagram.elementAt(currentPosition - 1) instanceof ForFigure ||
		diagram.elementAt(currentPosition - 1) instanceof WhileFigure) {
	    addForAndWhileFigure(currentPosition, temporalDiagram);
	} else {
	    for (int u = currentPosition; u < temporalDiagram.size() + 1; u++) {
		diagram.add(u, temporalDiagram.elementAt(u - 1));
	    }
	}
    }
}
