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
import org.eclipse.swt.widgets.Display;

public class If extends Figura {

    private Rectangle rectangle = bounds;

    private int color;

    public InstruccionCompuesta instruction = new InstruccionCompuesta();

    public If(int color) {
	setBounds(new Rectangle(100, 100, 80, 40));
	this.color = color;
    }

    public void paint(Graphics graphics) {
	selectLineTipe(graphics);
	graphics.drawPolygon(getPolygon(graphics));
	putInstruction(graphics);
    }

    private PointList getPolygon(Graphics graphics) {
	PointList polygon = new PointList();
	polygon.addPoint(rectangle.x + rectangle.width / 2, rectangle.y);
	polygon.addPoint(rectangle.x + rectangle.width, rectangle.y
		+ rectangle.height / 2);
	polygon.addPoint(rectangle.x + rectangle.width / 2, rectangle.y
		+ rectangle.height);
	polygon.addPoint(rectangle.x, rectangle.y + rectangle.height / 2);
	return polygon;
    }

    private Font getDefaultFont() {
	return new Font(Ventana.display, "Arial", 10, SWT.ITALIC);
    }

    private Color getDefaultTextColor() {
	return new Color(null, 6, 93, 6);
    }

    private void defaultInstruction(Graphics graphics) {
	graphics.setFont(getDefaultFont());
	graphics.setForegroundColor(getDefaultTextColor());
	graphics.drawText("Decision", rectangle.x - 22 + rectangle.width / 2,
		rectangle.y + 13);
    }

    private int maxDisplayChars(String instruction) {
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

	    if (maxDisplayChars(baseInstruction()) <= 8) {

		graphics.drawText(baseInstruction().substring(3,
			maxDisplayChars(baseInstruction())), rectangle.x - 16
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

    private void selectLineTipe(Graphics graphics) {
	if (isPasoAPaso()) {

	    graphics.setLineWidth(3);
	    graphics.setLineStyle(SWT.LINE_CUSTOM);
	    graphics.setForegroundColor(Ventana.display.
		    getSystemColor(SWT.COLOR_RED));
	} else {
	    if (seleccion) {

		graphics.setLineWidth(3);
		graphics.setLineStyle(SWT.LINE_DASH);
		graphics.setForegroundColor(Ventana.display.
			getSystemColor(SWT.COLOR_GRAY));
	    } else {
		graphics.setLineWidth(2);
		graphics.setForegroundColor(Display.getCurrent()
			.getSystemColor(color));
	    }
	}
    }
}