package Grafico.Figuras;

import java.awt.Point;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import Administracion.Figura;
import Administracion.Funcionalidad.Codigo.InstructionComposed;
import Administracion.Funcionalidad.Codigo.InstructionSimple;

/**
 * Esta clase es la que crea y dibujar la figura del For.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class ForFigure extends Figura {

    public InstructionComposed instructionComposed = new InstructionComposed();

    public ForFigure() {
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
		PointList polygonPoints = new PointList();
			
		int x1Coord = rectangle.x + (rectangle.width / 16 * 3);
			
		int x2Coord = rectangle.x + rectangle.width - (rectangle.width / 16 * 3);
			
		int x3Coord = rectangle.x + rectangle.width;
			
		int y1Coord = rectangle.y + 5;
			
		int y2Coord = rectangle.y + (rectangle.height / 2);
			
		int y3Coord = rectangle.y + rectangle.height - 5;
			
			
		polygonPoints.addPoint(x1Coord, y1Coord);
		polygonPoints.addPoint(x2Coord, y1Coord);
		polygonPoints.addPoint(x3Coord, y2Coord);
		polygonPoints.addPoint(x2Coord, y3Coord);
		polygonPoints.addPoint(x1Coord, y3Coord);
		polygonPoints.addPoint(rectangle.x, y2Coord);
			
		return polygonPoints;
    }
    
    
    private void drawDefaultInstruction(Graphics graphics) {
    	String defaultInstructionText = "Para";
    	
		int xCoord = rectangle.x - 13 + rectangle.width / 2;
	    	
		int yCoord = rectangle.y + 13;
	    	
		graphics.setFont(DEFAULT_FONT);
			
		graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
			
		graphics.drawText(defaultInstructionText, xCoord, yCoord);
	    }
	
	    private void drawInstruction(Graphics g) {
		if (isInstruction()) {
		    int xCoord = rectangle.x + rectangle.width / 5;
			
		    int yCoord = rectangle.y + 13;
		    
		    String instructionText = constructInstructionText();
		    
		    g.drawText(instructionText, xCoord, yCoord); 
		} else {
		    drawDefaultInstruction(g);
		}
    }
    
    private String constructInstructionText(){
		String instructionCode = getInstructionCode();
	
		instructionCode = formatInstructionCode(instructionCode);
		
		String [] variables = instructionCode.split(";");
		    
		String instructionText = "";
		
		int instructionLenght = instructionCode.length() - 7;
		
		int maxLenght = 2;
		
		int maxVariables= 3;
		
		if (instructionLenght <= maxLenght) {
		    for (int index = 0; index < maxVariables; index++) {
			instructionText = instructionText + variables[index] + ";";
		    }
		} else {
			instructionText = instructionCode.substring(0, instructionLenght) + "...";
		}
	
		return instructionText;
    }
    
    private String getInstructionCode(){
	return instructionComposed.simpleInstructionList.firstElement().getInstruccionSimple();
    }
    
    
    private boolean isInstruction(){
	boolean isNull = getInstructionCode().compareTo("null")  == 0;
	
	boolean isEmpty = getInstructionCode().isEmpty();
	
	return (!isNull) && (!isEmpty);
    }
    
    private String formatInstructionCode(String instructionCode){
	instructionCode = instructionCode.replaceFirst("for", "");
	
	instructionCode = instructionCode.replace("(", "");
	
	instructionCode = instructionCode.replace(")", "");
	
	instructionCode = instructionCode.replace("{", "");
	
	return instructionCode;
    }
    
    public boolean equalInstructions(String instructionCode) {
		return getInstructionCode().equals(instructionCode);
	}
	
	public void addInstructionSimple(InstructionSimple instructionSimple){
		instructionComposed.simpleInstructionList.add(0, instructionSimple);
	}
	
	public boolean isEmpyInstructionList() {
		return instructionComposed.simpleInstructionList.isEmpty();
	}
}
