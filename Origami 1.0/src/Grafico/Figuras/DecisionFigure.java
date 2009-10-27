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
	PointList polygonPoints = new PointList();
		
	int x1Coord = rectangle.x + (rectangle.width / 2);
		
	int x2Coord = rectangle.x + rectangle.width;
		
	int x3Coord = rectangle.x;
		
	int y1Coord = rectangle.y;
		
	int y2Coord = rectangle.y + (rectangle.height / 2);
		
	int y3Coord = rectangle.y + rectangle.height;
		
	polygonPoints.addPoint(x1Coord, y1Coord);
	polygonPoints.addPoint(x2Coord, y2Coord);
	polygonPoints.addPoint(x1Coord, y3Coord);
	polygonPoints.addPoint(x3Coord, y2Coord);
		
	return polygonPoints;
    }

    private void drawInstruction(Graphics graphics) {
    	if (isInstruction()) {
    	    int xCoord = rectangle.x - 16 + rectangle.width / 2;
    	
    	    int yCoord = rectangle.y + 13;
    	    
    	    String instructionText = constructInstructionText();
    	    
    	    graphics.drawText(instructionText, xCoord, yCoord);
    	} else {
    	    drawDefaultInstruction(graphics);
	}
    }
    
    private String constructInstructionText(){
	 int maxLenght = 8;
 	    
 	 int instructionLenght = getInstructionLenght();
 	    
 	 int beginIndex = 3;
 	
 	 String instructionCode = getInstructionCode();
 	    
 	 String instructionText;
 	    
 	 if (instructionLenght <= maxLenght) {
 	     instructionText = instructionCode.substring(beginIndex, instructionLenght);		
 	 } else {
 	     instructionText = instructionCode.substring(beginIndex, maxLenght) + "....";
 	 }
 	 
 	 return instructionText;
    }
    
    private void drawDefaultInstruction(Graphics graphics) {
	String defaultInstrutionText = "Decision";
	
	int xCoord = rectangle.x - 22 + rectangle.width / 2;
    	
    	int yCoord = rectangle.y + 13;
    	
	graphics.setFont(DEFAULT_FONT);
	graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
	graphics.drawText(defaultInstrutionText, xCoord, yCoord);
    }
    
    private int getInstructionLenght() {
    	return getInstructionCode().length() - 2;
    }

    private String getInstructionCode() {
    	return instruction.instruccion.firstElement().getInstruccionSimple();
    }

    private boolean isInstruction() {
	boolean isNull = getInstructionCode() == null;
	
	boolean isEmpty = instruction.instruccion.size() <= 1;
	
	return (!isEmpty) && (!isNull);
    }
}