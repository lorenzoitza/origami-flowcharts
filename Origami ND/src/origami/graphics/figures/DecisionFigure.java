package origami.graphics.figures;

import org.eclipse.draw2d.Graphics;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

import origami.administration.*;
import origami.administration.functionality.code.ComposedInstruction;
import origami.administration.functionality.code.SimpleInstruction;


@SuppressWarnings("serial")
public class DecisionFigure extends FigureStructure {

    public ComposedInstruction instructionComposed = new ComposedInstruction();

    public DecisionFigure() {
	int x=100;
	int y=100;
	int heigth=40;
	int width=80;
	setBounds(new Rectangle(x, y, width, heigth));
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

    public String getInstructionCode() {
    	return instructionComposed.getFirstInstructionSimple();
    }

    private boolean isInstruction() {
	boolean isNull = getInstructionCode() == null;
	
	boolean isEmpty = instructionComposed.getListSize() <= 1;
	
	return (!isEmpty) && (!isNull);
    }

    
    public boolean equalInstructions(String instructionCode) {
	return getInstructionCode().equals(instructionCode);
    }

    public void addInstructionSimple(SimpleInstruction simpleInstruction) {
	instructionComposed.addFirstSimpleInstruction(simpleInstruction);
    }

    public boolean isEmpyInstructionList() {
	return instructionComposed.isEmptyList();
    }
}