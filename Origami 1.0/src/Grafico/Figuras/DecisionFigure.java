package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import Administracion.*;
import Administracion.Funcionalidad.Codigo.InstruccionCompuesta;
import Grafico.Ventana;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

public class DecisionFigure extends Figura {

    public InstruccionCompuesta instruction = new InstruccionCompuesta();

    public DecisionFigure() {
	setBounds(new Rectangle(100, 100, 80, 40));
	this.rectangle = bounds;
    }

    public void paint(Graphics graphics) {
	selectLineTipe(graphics);
	graphics.drawPolygon(getPolygon(graphics));
	putInstruction(graphics);
    }

    private PointList getPolygon(Graphics graphics) {
	PointList _polygon = new PointList();
	_polygon.addPoint(rectangle.x + rectangle.width / 2, rectangle.y);
	_polygon.addPoint(rectangle.x + rectangle.width, rectangle.y
		+ rectangle.height / 2);
	_polygon.addPoint(rectangle.x + rectangle.width / 2, rectangle.y
		+ rectangle.height);
	_polygon.addPoint(rectangle.x, rectangle.y + rectangle.height / 2);
	return _polygon;
    }

    private void defaultInstruction(Graphics graphics) {
	graphics.setFont(DEFAULT_FONT);
	graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
	graphics.drawText("Decision", rectangle.x - 22 + rectangle.width / 2,
		rectangle.y + 13);
    }

    private int maxDisplayCharacters(String instruction) {
	return instruction.length() - 2;
    }

    private String baseInstruction() {
	return instruction.instruccion.elementAt(0).getInstruccionSimple();
    }

    private boolean isInstruction() {
	return instruction.instruccion.size() > 1
		&& instruction.instruccion.firstElement().
		getInstruccionSimple().compareTo("null") != 0;
    }

    private void putInstruction(Graphics graphics) {
	if (isInstruction()) {

	    if (maxDisplayCharacters(baseInstruction()) <= 8) {

		graphics.drawText(baseInstruction().substring(3,
			maxDisplayCharacters(baseInstruction())), rectangle.x - 16
			+ rectangle.width / 2, rectangle.y + 13);
	    } else {
		graphics.drawText(baseInstruction().substring(3, 8) + "....",
			rectangle.x - 16 + rectangle.width / 2,
			rectangle.y + 13);
	    }
	} else {
	    defaultInstruction(graphics);
	}
    }
}