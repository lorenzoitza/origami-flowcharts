package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import Administracion.*;
import Grafico.Ventana;

/**
 * Esta clase es la que crea y dibujar a la figura de Inicio y del Fin del
 * diagrama vistas en la pantalla.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class TerminationFigure extends Figura {

    private String mesagge;

    public TerminationFigure() {
	seleccion = false;
	setBounds(new Rectangle(1090, 100, 80, 50));
	this.rectangle = bounds;
    }

    /**
     * Este metodo coloca el mensaje de inicio o fin.
     * 
     * @param mensaje
     */

    public void setMensagge(String mensaje) {
	this.mesagge = mensaje;
    }

    public String getMensagge() {
	return mesagge;
    }

    
    /**
     * Este metodo es el encargado de dibujar la figura.
     * 
     * @param Graphics
     */
    public void paint(Graphics graphics) {
	selectLineTipe(graphics);
	graphics.drawOval(rectangle.x + rectangle.width / 6 + 1, rectangle.y + 2,
		(rectangle.width / 8) * 5, (rectangle.height / 5) * 4 + 5);
	graphics.drawText(mesagge, rectangle.x + rectangle.width / 4 + 5,
		rectangle.y + rectangle.height / 3);
    }
}