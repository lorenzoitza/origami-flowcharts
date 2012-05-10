package origami.graphics.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

import origami.administration.*;
import origami.administration.functionality.code.SimpleInstruction;

/**
 * Esta clase es la que crea y dibujar a la figura de entrada.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
@SuppressWarnings("serial")
public class InputFigure extends FigureStructure {

    public SimpleInstruction instruction = new SimpleInstruction();

    public InputFigure() {
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
	boolean isNull = instruction.getInstruccionSimple().compareTo("null") == 0;

	boolean isEmpty = instruction.getInstruccionSimple().isEmpty();

	return (!isNull) && (!isEmpty);
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

	    int xCoord = rectangle.x + 10 + rectangle.width / 5;

	    int yCoord = rectangle.y + 8;

	    String instructionText = constructInstructionText();

	    g.drawText(instructionText, xCoord, yCoord);
	} else {

	    drawDefaultInstruction(g);
	}
    }

    private String constructInstructionText() {
	String instructionCode = instruction.getInstruccionSimple();

	String[] variables = instructionCode.split(";");

	int number = instructionCode.indexOf(";");

	String _subInstruction = instructionCode.substring(0, number);

	int maxLenght = 6;

	int minNumberOfVariable = 1;

	if (variables[0].length() > maxLenght) {

	    _subInstruction = variables[0].substring(0, 5) + "...";
	} else if (variables.length > minNumberOfVariable) {

	    _subInstruction = variables[0] + "...";
	}

	return _subInstruction;
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