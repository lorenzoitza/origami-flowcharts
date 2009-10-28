package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import Administracion.*;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Esta clase es la que crea y dibujar a la figura de imprimir.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class OutputFigure extends Figura {

    public InstruccionSimple instruction = new InstruccionSimple();

    public OutputFigure() {
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
		
		int x1Coord = rectangle.x + 1;
		
		int x2Coord = rectangle.x + (rectangle.width / 5) * 3;
		
		int x3Coord = rectangle.x + rectangle.width - 1;
		
		int y1Coord = rectangle.y + 1;
		
		int y2Coord = rectangle.y +rectangle.height / 2 - 1;
		
		int y3Coord = rectangle.y + rectangle.height - 1;
		
		_polygonPoints.addPoint(x1Coord, y1Coord);
		_polygonPoints.addPoint(x2Coord, y1Coord);
		_polygonPoints.addPoint(x3Coord, y2Coord);
		_polygonPoints.addPoint(x2Coord, y3Coord);
		_polygonPoints.addPoint(x1Coord, y3Coord);
		return _polygonPoints;
    }

    private boolean isInstruction() {
		return instruction.getInstruccionSimple().compareTo("null") != 0
			&& instruction.getInstruccionSimple().compareTo("") != 0;
    }

    private void drawDefaultInstruction(Graphics graphics) {
		graphics.setFont(DEFAULT_FONT);
		
		int xCoord = rectangle.x - 22 + rectangle.width / 2;
		
		int yCoord = rectangle.y + 13;
		
		graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
		graphics.drawText("Salida", xCoord, yCoord);
    }
    
    private String[] unformatOutputVariables() {
		String[] _variables = new String[50];
	
		_variables = instruction.getInstruccionSimple().split(";");
	
		String[] _finalVariables = new String[50];
	
		int cont = 0;
	
		for (int _variableIndex = 0; _variableIndex < _variables.length;
		_variableIndex++) {
	
		    _variables[_variableIndex] = _variables[_variableIndex].
		    replace("\\", "");
		    
		    _variables[_variableIndex] = _variables[_variableIndex].
		    replaceFirst("p", "");
		    
		    if (_variables[_variableIndex].compareTo("") != 0) {
	
			_finalVariables[cont] = _variables[_variableIndex];
			cont++;
		    }
		}
		for (int _variableIndex = 0; _variableIndex < cont; _variableIndex++) {
	
		    if (_finalVariables[_variableIndex].compareTo("") != 0) {
	
			_variables[_variableIndex] = _finalVariables[_variableIndex];
		    }
		}
		return _variables;
    }

    private void drawInstruction(Graphics graphics) {
		if (isInstruction()) {
			
			int xCoord = rectangle.x + rectangle.width / 8;
			
			int yCoord = rectangle.y + 8;
			
			String instructionText = constructInstructionText();
			graphics.drawText(instructionText, xCoord, yCoord);
			
		} else {
		   drawDefaultInstruction(graphics);
		}
    }
    
    private String constructInstructionText(){
    	String [] unformatInstructions = unformatOutputVariables();

	    String instructionText;

	    int instructionLenght = unformatInstructions[0].length();
	    
	    int maxLenght = 6;
	    
	    int minNumberOfVariable = 1;
	    			
	    if ( instructionLenght > maxLenght || unformatInstructions.length > minNumberOfVariable) {

			instructionText = unformatInstructions[0].substring(0, instructionLenght)+"...";
			
	    } else {
	    	instructionText = unformatInstructions[0].substring(0, instructionLenght);
	    }
	    return instructionText;
    }
}