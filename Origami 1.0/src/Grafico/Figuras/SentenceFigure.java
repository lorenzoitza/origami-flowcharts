package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import Administracion.*;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.Ventana;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

/**
 * Esta clase es la que crea y dibujar a la figura de Proceso.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class SentenceFigure extends Figura {

    public InstruccionSimple instruccion = new InstruccionSimple();

    public SentenceFigure() {
	setBounds(new Rectangle(100, 100, 80, 40));
	this.rectangle = bounds;
    }
    
    /**
     * Este metodo es el encargado de dibujar la figura.
     * 
     * @param Graphics
     */
    public void paint(Graphics g) {
	selectLineTipe(g);
	g.drawRectangle(rectangle.x + 1, rectangle.y + 1, rectangle.width - 2, rectangle.height - 2);
	putInstruction(g);
    }

    private void defaultInstruction(Graphics graphics) {
	graphics.setFont(DEFAULT_FONT);
	graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
	graphics.drawText("Expresión", rectangle.x - 26 + rectangle.width / 2,
		    rectangle.y + 13);
    }

    private boolean isInstruction() {
	return instruccion.instruccion != "null"
		&& instruccion.instruccion.compareTo("null") != 0;
    }

    private void putInstruction(Graphics graphics) {
	if (isInstruction()) {
	    
	    String _instruction = instruccion.instruccion;
	    
	    if (_instruction.length() <= 10) {
		String subStr = _instruction.substring(0, _instruction.length() - 1);
		graphics.drawText(subStr, rectangle.x + rectangle.width / 8,
			rectangle.y + 15);
	    } else {
		int maxDisplayCharacters = _instruction.length() - 9;
		String subStr = _instruction.substring(0, _instruction.length() -
			maxDisplayCharacters);
		graphics.drawText(subStr + "...", rectangle.x +
			rectangle.width / 8, rectangle.y + 15);
	    }
	} else {
	    	defaultInstruction(graphics);
	}
    }
}