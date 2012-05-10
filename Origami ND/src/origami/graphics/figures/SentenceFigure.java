package origami.graphics.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import origami.administration.*;
import origami.administration.functionality.code.SimpleInstruction;

/**
 * Esta clase es la que crea y dibujar a la figura de Proceso.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
@SuppressWarnings("serial")
public class SentenceFigure extends FigureStructure {

    public SimpleInstruction instruction = new SimpleInstruction();

    public SentenceFigure() {
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
	drawRectangleFigure(graphics);
	drawInstruction(graphics);
    }

    private void drawRectangleFigure(Graphics graphics) {
	int rectangleArg0 = rectangle.x + 1;

	int rectangleArg1 = rectangle.y + 1;

	int rectangleArg2 = rectangle.width - 2;

	int rectangleArg3 = rectangle.height - 2;

	graphics.drawRectangle(rectangleArg0, rectangleArg1, rectangleArg2,
		rectangleArg3);
    }

    private void drawDefaultInstruction(Graphics graphics) {
	int xCoord = rectangle.x - 26 + rectangle.width / 2;

	int yCoord = rectangle.y + 13;

	String text = "Expresión";

	graphics.setFont(DEFAULT_FONT);
	graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
	graphics.drawText(text, xCoord, yCoord);
    }

    private boolean isInstruction() {
	return instruction.simpleInstruction != "null"
		&& instruction.simpleInstruction.compareTo("null") != 0;
    }

    private void drawInstruction(Graphics graphics) {
	if (isInstruction()) {
	    int xCoord = rectangle.x + rectangle.width / 8;

	    int yCoord = rectangle.y + 15;

	    String instructionText = construcInstructionText();

	    graphics.drawText(instructionText, xCoord, yCoord);
	} else {
	    drawDefaultInstruction(graphics);
	}
    }

    private String construcInstructionText() {
	String instructionCode = instruction.getInstruccionSimple();

	int instructionLenght = instructionCode.length();

	int maxLenght = 10;

	int maxDisplayCharacters = instructionLenght - 9;

	int minLenght = 0;

	String instructionText;

	if (instructionLenght <= maxLenght) {
	    instructionText =
		    instructionCode.substring(minLenght, instructionLenght - 1);
	} else {

	    instructionText =
		    instructionCode.substring(minLenght, instructionLenght
			    - maxDisplayCharacters)
			    + "...";
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