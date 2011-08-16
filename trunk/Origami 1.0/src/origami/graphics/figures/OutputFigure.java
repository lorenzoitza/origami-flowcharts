package origami.graphics.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

import origami.administration.*;
import origami.administration.functionality.code.SimpleInstruction;


/**
 * Esta clase es la que crea y dibujar a la figura de imprimir.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
@SuppressWarnings("serial")
public class OutputFigure extends FigureStructure {

    public SimpleInstruction instruction = new SimpleInstruction();

    public OutputFigure() {
	int x = 100;
	int y = 100;
	int heigth = 40;
	int width = 80;
	setBounds(new Rectangle(x, y, width, heigth));
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

	int y2Coord = rectangle.y + rectangle.height / 2 - 1;

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
	String[] variables = new String[50];

	variables = instruction.getInstruccionSimple().split(";");

	String[] finalVariables = new String[50];

	int cont = 0;
	for (int variableIndex = 0; variableIndex < variables.length;
		variableIndex++) {
	    variables[variableIndex] = variables[variableIndex].replace("\\", "");
	    
	    variables[variableIndex] = variables[variableIndex].replaceFirst("p", "");
	    
	    
	    if (!variables[variableIndex].isEmpty()) {
		
		finalVariables[cont] = variables[variableIndex];
		
		cont++;
	    }
	}
	
	for (int variableIndex = 0; variableIndex < cont; variableIndex++) {

	    if (!finalVariables[variableIndex].isEmpty()) {

		variables[variableIndex] = finalVariables[variableIndex];
		
	    }
	}
	return variables;
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

    private String constructInstructionText() {
	String[] unformatInstructions = unformatOutputVariables();

	String instructionText;

	int instructionLenght = unformatInstructions[0].length();
	
	int maxLenght = 6;

	int minNumberOfVariable = 1;

	if (instructionLenght > maxLenght && unformatInstructions.length > minNumberOfVariable) {

	    instructionText = unformatInstructions[0].substring(0, maxLenght)
			    + "...";

	} else {
	    instructionText =
		    unformatInstructions[0].substring(0, instructionLenght);
	}
	return instructionText;
    }
    
    public String getInstructionCode() {
	return instruction.getInstruccionSimple();
    }

    public boolean equalInstructions(String instructionCode) {
	return getInstructionCode().equals(instructionCode);
    }

    public void addInstructionSimple(SimpleInstruction instructionSimple) {
	instruction.setInstruccionSimple(instructionSimple
		.getInstruccionSimple());
    }

    public boolean isEmpyInstructionList() {
	return false;
    }
}