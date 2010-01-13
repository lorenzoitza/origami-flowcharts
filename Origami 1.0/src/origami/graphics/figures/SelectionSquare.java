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

    private PointList constructPolygonPoints(){
		PointList _polygonPoints = new PointList();
		
		int x1Coord = rectangle.x + (rectangle.width / 16 * 3);
		
		int x2Coord = rectangle.x + rectangle.width - (rectangle.width / 16 * 3);
		
		int x3Coord = rectangle.x + rectangle.width;
		
		int x4Coord = rectangle.x;
		
		int y1Coord = rectangle.y + 5;
		
		int y2Coord = rectangle.y + rectangle.height / 2;
		
		int y3Coord = rectangle.y + rectangle.height - 5;
		
		_polygonPoints.addPoint(x1Coord, y1Coord);
		_polygonPoints.addPoint(x2Coord, y1Coord);
		_polygonPoints.addPoint(x3Coord, y2Coord);
		_polygonPoints.addPoint(x2Coord, y3Coord);
		_polygonPoints.addPoint(x1Coord, y3Coord);
		_polygonPoints.addPoint(x4Coord, y2Coord);
		return _polygonPoints;
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
		
		PointList polygonPoints = constructPolygonPoints();
		
		graphics.drawPolygon(polygonPoints);
    }
}
