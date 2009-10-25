package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import Administracion.*;

/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class SelectionSquare extends Figura {

    private boolean remarcar;

    private int color;
    
    private Rectangle rectangle;

    public SelectionSquare(boolean remarcar, int color) {
	setBounds(new Rectangle(100, 100, 5, 10));
	this.remarcar = remarcar;
	this.color = color;
	this.rectangle=bounds;
    }

    private PointList getPolygonPoints(){
	PointList _polygon = new PointList();
	_polygon.addPoint(rectangle.x + (rectangle.width / 16 * 3),
		rectangle.y + 5);
	_polygon.addPoint(rectangle.x + rectangle.width - (rectangle.width / 16 * 3),
		rectangle.y + 5);
	_polygon.addPoint(rectangle.x + rectangle.width, rectangle.y +
		rectangle.height / 2);
	_polygon.addPoint(rectangle.x + rectangle.width - (rectangle.width / 16 * 3),
		rectangle.y + rectangle.height - 5);
	_polygon.addPoint(rectangle.x + (rectangle.width / 16 * 3),
		rectangle.y + rectangle.height - 5);
	_polygon.addPoint(rectangle.x, rectangle.y + rectangle.height / 2);
	return _polygon;
    }
    
    /**
     * Este metodo es el encargado de dibujar la figura.
     * 
     * @param Graphics
     */
    public void paint(Graphics graphics) {
	if (remarcar) {
	    graphics.setLineStyle(SWT.LINE_DOT);
	} else {
	    graphics.setLineWidth(5);
	}
	graphics.setForegroundColor(Display.getCurrent().getSystemColor(color));
	
	graphics.drawPolygon(getPolygonPoints());
    }
}
