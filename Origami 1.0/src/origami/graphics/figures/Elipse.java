package origami.graphics.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import origami.administration.*;



public class Elipse extends Figura {
    
    private Rectangle rectangle = bounds;

    public Elipse() {
    	setBounds(new Rectangle(100, 100, 1, 1));
    }
    
    @ Override
    public void paint(Graphics g) {
    	g.drawOval(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
