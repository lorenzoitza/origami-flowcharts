package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import Administracion.Figura;
import Administracion.Funcionalidad.Codigo.*;
import Grafico.Ventana;

/**
 * Esta clase es la que crea y dibujar a la figura del While.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class WhileFigure extends Figura {

    public InstruccionCompuesta instruccion = new InstruccionCompuesta();

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
	graphics.drawPolygon(getPolygon());
	putInstruction(graphics);
    }

    private void defaultInstruction(Graphics graphics) {
	graphics.setFont(DEFAULT_FONT);
	graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
	graphics.drawText("Mientras", rectangle.x - 22 + rectangle.width / 2,
		rectangle.y + 13);
    }

    private boolean isInstruction() {
	return instruccion.instruccion.size() > 1
		&& instruccion.instruccion.firstElement()
			.getInstruccionSimple().compareTo("null") != 0;
    }

    private PointList getPolygon() {
	PointList _polygon = new PointList();
	_polygon.addPoint(rectangle.x + rectangle.width / 2, rectangle.y);
	_polygon.addPoint(rectangle.x + rectangle.width, rectangle.y
		+ rectangle.height / 2);
	_polygon.addPoint(rectangle.x + rectangle.width / 2, rectangle.y
		+ rectangle.height);
	_polygon.addPoint(rectangle.x, rectangle.y + rectangle.height / 2);
	return _polygon;
    }

    private void putInstruction(Graphics graphics) {
	if (isInstruction()) {
	    String _instruction = instruccion.instruccion.elementAt(0).getInstruccionSimple();

	    if (_instruction.length() <= 11) {

		String _subInstruction = _instruction.substring(6,
			_instruction.length() - 2);
		graphics.drawText(_subInstruction, rectangle.x - 16 + rectangle.width
			/ 2, rectangle.y + 13);
	    } else {
		String _subInstruction = _instruction.substring(6, 9);
		graphics.drawText(_subInstruction + "...", rectangle.x - 16
			+ rectangle.width / 2, rectangle.y + 13);
	    }

	} else {
	    defaultInstruction(graphics);
	}
    }
}
