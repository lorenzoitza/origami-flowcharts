package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import Administracion.*;
import Grafico.MainWindow;

/**
 * Esta clase es la que crea y dibujar a la figura de Inicio y del Fin del
 * diagrama vistas en la pantalla.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class CircleFigure extends Figura {

    private String mesagge;

    public CircleFigure() {
	seleccion = false;
	setBounds(new Rectangle(1090, 100, 80, 50));
	this.rectangle = bounds;
    }

    /**
     * Este metodo coloca el mensaje de inicio o fin.
     * 
     * @param mensaje
     */

    public void setMesagge(String mensaje) {
    	this.mesagge = mensaje;
    }

    public String getMesagge() {
    	return mesagge;
    }

    
    /**
     * Este metodo es el encargado de dibujar la figura.
     * 
     * @param Graphics
     */
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
		
	graphics.drawText(mesagge, xCoord, yCoord);
    }
}