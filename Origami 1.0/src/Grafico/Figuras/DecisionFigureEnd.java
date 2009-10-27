package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import Administracion.*;

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
		PointList rigthPathPoints = constructRigthPathPoints();
		
		PointList leftPathPoints = constructLeftPathPoints();
		
		PointList downPathPoints = constructDownPathPoints();
    	
    	
    	if (!isPasoAPaso()) {
		    
		    graphics.setForegroundColor(DARKBLUE);
		    graphics.drawPolygon(leftPathPoints);
		    graphics.drawPolygon(rigthPathPoints);
		} else {
		    graphics.setLineWidth(1);
		    graphics.setLineStyle(SWT.LINE_CUSTOM);
		    graphics.setForegroundColor(RED);
		    
		    if (isDerIzqFin()) {
		
				graphics.drawPolygon(rigthPathPoints);
				graphics.setForegroundColor(DARKBLUE);
				graphics.drawPolygon(leftPathPoints);
		    } else {
				graphics.drawPolygon(leftPathPoints);
				graphics.setForegroundColor(DARKBLUE);
				graphics.drawPolygon(rigthPathPoints);
		    }
		}
		graphics.drawPolygon(downPathPoints);
    }

    private PointList constructRigthPathPoints() {
		PointList rigthPath = new PointList();
		
		int x1Coord = rectangle.x + 3 * rectangle.width / 4;
		
		int x2Coord = rectangle.x + rectangle.width;
		
		int y1Coord = rectangle.y + rectangle.height / 2;
		
		rigthPath.addPoint(x1Coord, y1Coord);
		rigthPath.addPoint(x2Coord, y1Coord);
		return rigthPath;
    }

    private PointList constructLeftPathPoints() {
		PointList leftPath = new PointList();
		
		int x1Coord = rectangle.x + rectangle.width / 4;
		
		int y1Coord = rectangle.y + rectangle.height / 2;
		
		leftPath.addPoint(rectangle.x, y1Coord);
		leftPath.addPoint(x1Coord, y1Coord);
		return leftPath;
    }

    private PointList constructDownPathPoints() {
		PointList downPath = new PointList();
		
		int x1Coord = rectangle.x + rectangle.width / 2;
		
		int y1Coord = 1 + rectangle.y + 3 * rectangle.height / 4;
		
		int y2Coord = rectangle.y + rectangle.height;
		
		downPath.addPoint(x1Coord, y1Coord);
		downPath.addPoint(x1Coord, y2Coord);
		return downPath;
    }

    private Rectangle getOval() {
		return new Rectangle(rectangle.x + 30, rectangle.y + 15,
			rectangle.width - 60, rectangle.height - 25);
    }
}