package origami.graphics.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import origami.administration.FigureStructure;



/**
 * Esta clase es la que crea y dibujar a la figura de Inicio y del Fin del
 * diagrama vistas en la pantalla.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
@SuppressWarnings("serial")
public class CircleFigure extends FigureStructure {

    private String message;

    public CircleFigure() {
	seleccion = false;
	int x=1090;
	int y=100;
	int heigth=50;
	int width=80;
	setBounds(new Rectangle(x, y,width, heigth));
	this.rectangle = bounds;
    }

    public void setMessage(String mensaje) {
    	this.message = mensaje;
    }

    public String getMessage() {
    	return message;
    }

    public void paint(Graphics graphics) {
    	
	selectLineTipe(graphics);
		
	drawCircle(graphics);
		
	drawTextLabel(graphics);
		
    }
    
    private void drawCircle(Graphics graphics){
    	int ovalArg0 = rectangle.x + rectangle.width / 6 + 1;
    	
    	int ovalArg1 = rectangle.y + 2;
    	
    	int ovalArg2 = (rectangle.width / 8) * 5;
    	
    	int ovalArg3 = (rectangle.height / 5) * 4 + 5;
    	
    	graphics.drawOval(ovalArg0, ovalArg1, ovalArg2, ovalArg3);
    }
    
    private void drawTextLabel(Graphics graphics){
    	int xCoord = rectangle.x + rectangle.width / 4 + 5;
		
	int yCoord = rectangle.y + rectangle.height / 3;
		
	graphics.drawText(message, xCoord, yCoord);
    }
}