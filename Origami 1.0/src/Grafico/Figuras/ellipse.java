package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import Administracion.*;
/**
 * Esta clase es la que crea los puntos
 * en los que las conecciones de las figuras
 * de un diagrama se doblan.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class ellipse extends Figura{
	public ellipse(){
		setBounds(new Rectangle(100,100,1,1));
	}
	/**
	 * Este metodo es el encargado de dibujar la figura.
	 * @param Graphics
	 */
	public void paint(Graphics g) {
		Rectangle rect = bounds;
		g.drawOval(rect.x, rect.y, rect.width, rect.height);
	}
}
