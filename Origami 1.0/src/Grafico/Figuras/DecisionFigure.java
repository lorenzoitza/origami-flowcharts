package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import Administracion.*;
import Administracion.Funcionalidad.Codigo.InstruccionCompuesta;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

public class DecisionFigure extends Figura {

    public InstruccionCompuesta instruction = new InstruccionCompuesta();

    public DecisionFigure() {
		setBounds(new Rectangle(100, 100, 80, 40));
		this.rectangle = bounds;
    }

    public void paint(Graphics graphics) {
		selectLineTipe(graphics);
		PointList polygonPoints = constructPolygonPoints();
		graphics.drawPolygon(polygonPoints);
		drawInstruction(graphics);
    }

    private PointList constructPolygonPoints() {
		PointList _polygonPoints = new PointList();
		
		int x1Coord = rectangle.x + (rectangle.width / 2);
		
		int x2Coord = rectangle.x + rectangle.width;
		
		int x3Coord = rectangle.x;
		
		int y1Coord = rectangle.y;
		
		int y2Coord = rectangle.y + (rectangle.height / 2);
		
		int y3Coord = rectangle.y + rectangle.height;
		
		_polygonPoints.addPoint(x1Coord, y1Coord);
		_polygonPoints.addPoint(x2Coord, y2Coord);
		_polygonPoints.addPoint(x1Coord, y3Coord);
		_polygonPoints.addPoint(x3Coord, y2Coord);
		
		return _polygonPoints;
    }

    private void drawDefaultInstruction(Graphics graphics) {
    	int textXCoord = rectangle.x - 22 + rectangle.width / 2;
    	
    	int textYCoord = rectangle.y + 13;
    	
		graphics.setFont(DEFAULT_FONT);
		graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
		graphics.drawText("Decision", textXCoord, textYCoord);
    }

    private int maxDisplayCharacters(String instruction) {
    	return instruction.length() - 2;
    }

    private String getBaseInstruction() {
    	return instruction.instruccion.elementAt(0).getInstruccionSimple();
    }

    private boolean isInstruction() {
		return (instruction.instruccion.size() > 1) && (instruction.instruccion
			.firstElement().getInstruccionSimple().compareTo("null") != 0);
    }

    private void drawInstruction(Graphics graphics) {
    	int instructionXCoord = rectangle.x - 16 + rectangle.width / 2;
    	
    	int instructionYCoord = rectangle.y + 13;
    	
    	String _instructionCode;
    	
    	if (isInstruction()) {
			
	    	
		    if (maxDisplayCharacters(getBaseInstruction()) <= 8) {
		    	
		    	_instructionCode = getBaseInstruction().substring(3,
						maxDisplayCharacters(getBaseInstruction()));
	
				graphics.drawText(_instructionCode, instructionXCoord, instructionYCoord);
				
		    } else {
		    	_instructionCode = getBaseInstruction().substring(3, 8) + "....";
		    	
				graphics.drawText(_instructionCode, instructionXCoord, instructionYCoord);
		    }
		} else {
		    drawDefaultInstruction(graphics);
		}
    }
}