package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import Administracion.*;
import Grafico.Ventana;

public class DecisionFigureEnd extends Figura {

    private Rectangle rectangle;

    public DecisionFigureEnd() {
	setBounds(new Rectangle(475, 50, 80, 40));
	rectangle = bounds;
    }

    @Override
    public void paint(Graphics graphics) {
	graphics.drawOval(getOval());
	drawPaths(graphics);
    }

    private void drawPaths(Graphics graphics) {
	if (!isPasoAPaso()) {
	    
	    graphics.setForegroundColor(DARKBLUE);
	    graphics.drawPolygon(getLeftPath());
	    graphics.drawPolygon(getRigthPath());
	} else {
	    graphics.setLineWidth(1);
	    graphics.setLineStyle(SWT.LINE_CUSTOM);
	    graphics.setForegroundColor(RED);
	    if (isDerIzqFin()) {

		graphics.drawPolygon(getRigthPath());
		graphics.setForegroundColor(DARKBLUE);
		graphics.drawPolygon(getLeftPath());
	    } else {
		graphics.drawPolygon(getLeftPath());
		graphics.setForegroundColor(DARKBLUE);
		graphics.drawPolygon(getRigthPath());
	    }
	}
	graphics.drawPolygon(getDonwnPath());
    }

    private PointList getRigthPath() {
	PointList rigthPath = new PointList();
	rigthPath.addPoint(rectangle.x + 3 * rectangle.width / 4, rectangle.y
		+ rectangle.height / 2);
	rigthPath.addPoint(rectangle.x + rectangle.width, rectangle.y
		+ rectangle.height / 2);
	return rigthPath;
    }

    private PointList getLeftPath() {
	PointList leftPath = new PointList();
	leftPath.addPoint(rectangle.x, rectangle.y + rectangle.height / 2);
	leftPath.addPoint(rectangle.x + rectangle.width / 4, rectangle.y
		+ rectangle.height / 2);
	return leftPath;
    }

    private PointList getDonwnPath() {
	PointList downPath = new PointList();
	downPath.addPoint(rectangle.x + rectangle.width / 2, 1 + rectangle.y
		+ 3 * rectangle.height / 4);
	downPath.addPoint(rectangle.x + rectangle.width / 2, rectangle.y
		+ rectangle.height);
	return downPath;
    }

    private Rectangle getOval() {
	return new Rectangle(rectangle.x + 30, rectangle.y + 15,
		rectangle.width - 60, rectangle.height - 25);
    }
}