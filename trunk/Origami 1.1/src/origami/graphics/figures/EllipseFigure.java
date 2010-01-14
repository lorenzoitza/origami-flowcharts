package origami.graphics.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import origami.administration.*;



public class EllipseFigure extends Figura {
    
    private Rectangle rectangle = bounds;

    public EllipseFigure() {
	int x=100;
	int y=100;
	int heigth=1;
	int width=1;
    	setBounds(new Rectangle(x, y, width, heigth));
    }
    
    @ Override
    public void paint(Graphics g) {
    	g.drawOval(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
