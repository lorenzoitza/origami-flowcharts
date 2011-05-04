package origami.graphics.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import origami.administration.*;


@SuppressWarnings("serial")
public class DecisionFigureEnd extends FigureStructure {

    private Rectangle rectangle;

    public DecisionFigureEnd() {
	int x=475;
	int y=50;
	int heigth=40;
	int width=80;
	setBounds(new Rectangle(x, y,width, heigth));
	rectangle = bounds;
    }

    @Override
    public void paint(Graphics graphics) {
	graphics.drawOval(constructOval());
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

    private Rectangle constructOval() {
	int ovalArg0 = rectangle.x + 30;
		
	int ovalArg1 = rectangle.y + 15;
		
	int ovalArg2 = rectangle.width - 60;
		
	int ovalArg3 = rectangle.height - 25;
	
	return new Rectangle(ovalArg0, ovalArg1, ovalArg2, ovalArg3);
    }
}