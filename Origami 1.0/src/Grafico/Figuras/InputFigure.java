package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import Administracion.*;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;

/**
 * Esta clase es la que crea y dibujar a la figura de entrada.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class InputFigure extends Figura {

    public InstruccionSimple instruccion = new InstruccionSimple();

    public InputFigure() {
		setBounds(new Rectangle(100, 100, 80, 40));
		this.rectangle = bounds;
    }
    
    /**
     * Este metodo es el encargado de dibujar la figura.
     * 
     * @param Graphics
     */
    public void paint(Graphics graphics) {
		selectLineTipe(graphics);
		
		PointList polygonPoints = constructPolygonPoints();
		graphics.drawPolygon(polygonPoints);
		drawInstruction(graphics);
    }

    private PointList constructPolygonPoints() {
		PointList _polygonPoints = new PointList();
		
		int x1Coord = rectangle.x + 25;
		
		int y1Coord = rectangle.y + 1;
		
		int x2Coord = rectangle.x + rectangle.width;
		
		int x3Coord = rectangle.x + rectangle.width - 25;
		
		int y2Coord = rectangle.height + rectangle.y - 1;
		
		_polygonPoints.addPoint(x1Coord, y1Coord);
		_polygonPoints.addPoint(x2Coord, y1Coord);
		_polygonPoints.addPoint(x3Coord, y2Coord);
		_polygonPoints.addPoint(rectangle.x, y2Coord);
		return _polygonPoints;
    }

    private boolean isInstruction() {
		return instruccion.getInstruccionSimple().compareTo("null") != 0
			&& instruccion.getInstruccionSimple().compareTo("") != 0;
    }

    private void drawDefaultInstruction(Graphics graphics) {
		int xCoord = rectangle.x - 24 + rectangle.width / 2;
		
		int yCoord = rectangle.y + 13;
		
    	graphics.setFont(DEFAULT_FONT);
		graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
		graphics.drawText("Entrada", xCoord, yCoord);
    }

    private void drawInstruction(Graphics g) {
		if (isInstruction()) {
	
		    String _variables = instruccion.instruccion;
	
		    String [] _allVariables = instruccion.instruccion.split(";");
	
		    int _number = _variables.indexOf(";");
	
		    String _subInstruction = _variables.substring(0, _number);
		    
		    int xCoord = rectangle.x + 10 + rectangle.width / 5;
		    
		    int yCoord = rectangle.y + 8;
	
		    if (_allVariables[0].length() > 6) {
	
				_subInstruction = _allVariables[0].substring(0, 5) + "...";
				g.drawText(_subInstruction , xCoord, yCoord);
		    } else if (_allVariables.length > 1) {
	
				_subInstruction = _allVariables[0] + "...";
				g.drawText(_subInstruction , xCoord, yCoord);
		    } else {
				g.drawText(_subInstruction, xCoord, yCoord);
		    }
		} else {
		    drawDefaultInstruction(g);
		}
    }
}