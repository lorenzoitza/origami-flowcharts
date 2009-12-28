package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import Administracion.Figura;
import Administracion.Funcionalidad.Codigo.*;

/**
 * Esta clase es la que crea y dibujar a la figura del While.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class WhileFigure extends Figura {

    public InstructionComposed instruccion = new InstructionComposed();

    public WhileFigure() {
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
		graphics.drawPolygon(constructPolygonPoints());
		drawInstruction(graphics);
    }

    private void drawDefaultInstruction(Graphics graphics) {
		String defaultText = "Mientras";
		
		int xCoord = rectangle.x - 22 + rectangle.width / 2;
		
		int yCoord = rectangle.y + 13;
		
    	graphics.setFont(DEFAULT_FONT);
		graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
		graphics.drawText(defaultText, xCoord, yCoord);
    }

    private boolean isInstruction() {
    	boolean isNull = instruccion.instruccion.firstElement().getInstruccionSimple() == null;
    	
    	//boolean variable qu necesito saber exactamente para que querian.
		return instruccion.instruccion.size() > 1 && (!isNull);
    }

    private PointList constructPolygonPoints() {
		PointList _polygonPoints = new PointList();
		
		int x1Coord = rectangle.x + rectangle.width / 2;
		
		int x2Coord = rectangle.x + rectangle.width;
		
		int x3Coord = rectangle.x;
		
		int y1Coord = rectangle.y;
		
		int y2Coord = rectangle.y + rectangle.height / 2;
		
		int y3Coord = rectangle.y + rectangle.height;
		
		_polygonPoints.addPoint(x1Coord, y1Coord);
		_polygonPoints.addPoint(x2Coord, y2Coord);
		_polygonPoints.addPoint(x1Coord, y3Coord);
		_polygonPoints.addPoint(x3Coord, y2Coord);
		return _polygonPoints;
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
    	String instructionCode = instruccion.instruccion.firstElement().getInstruccionSimple();
    	
    	int maxLenght = 11;
    	
    	int beginIndex = 6;
    	
    	int endIndex = 9;
    	
    	int instructionLenght = instructionCode.length();
    	
    	String instructionText = "";
	    
    	if (instructionLenght <= maxLenght) {
    		instructionText = instructionCode.substring(beginIndex, instructionLenght - 2);
	    } else {
	    	instructionText = instructionCode.substring(beginIndex, endIndex);
	    }
	    
	    return instructionText;
	    
    }
}
