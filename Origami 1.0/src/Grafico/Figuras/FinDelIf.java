package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;


import Administracion.*;
import Grafico.Ventana;
/**
 * Esta clase es la que crea y 
 * dibujar a la figura que va al final del If.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class FinDelIf extends Figura {
	public FinDelIf(){
		setBounds(new Rectangle(475,50,80,40));
	}
	/**
	 * Este metodo es el encargado de dibujar la figura.
	 * @param Graphics
	 */
	public void paint(Graphics g) {
		Rectangle r =bounds;
		PointList izquierdo = new PointList();
		izquierdo.addPoint(r. x, r.y +r.height/2);
		izquierdo.addPoint(r.x+r.width/4, r.y +r.height/2);
		
		
		PointList derecho = new PointList();
		derecho.addPoint(r.x+3*r.width/4, r.y +r.height/2);
		derecho.addPoint(r.x+r.width, r.y +r.height/2);
		
		
		PointList abajo = new PointList();
		abajo.addPoint(r.x+r.width/2, 1+r.y +3*r.height/4);
		abajo.addPoint(r.x+r.width/2, r.y +r.height);
		
		if(isPasoAPaso()){
			g.setLineWidth(1);
			g.setLineStyle(SWT.LINE_CUSTOM);
			Color c = Ventana.display.getSystemColor(SWT.COLOR_RED);
			g.setForegroundColor(c);
			
			g.drawOval(r.x+30,r.y+15,r.width-60,r.height-25);
			g.drawPolygon(abajo);
			if(isDerIzqFin()){//si es true la variable DerIzqFin se pinta el lado derecho
				g.drawPolygon(derecho);
				g.setForegroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE));
				g.drawPolygon(izquierdo);
			}
			else{	//si no el lado izquierdo...
				g.drawPolygon(izquierdo);
				g.setForegroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE));
				g.drawPolygon(derecho);
			}
		}
		else{
			g.setForegroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE));
			g.drawPolygon(izquierdo);
			g.drawPolygon(derecho);
			g.drawPolygon(abajo);
			g.drawOval(r.x+30,r.y+15,r.width-60,r.height-25);
		}
		/*PointList izquierdo = new PointList();
		izquierdo.addPoint(r. x, r.y +r.height/2);
		izquierdo.addPoint(r.x+r.width/4, r.y +r.height/2);
		
		
		PointList derecho = new PointList();
		derecho.addPoint(r.x+3*r.width/4, r.y +r.height/2);
		derecho.addPoint(r.x+r.width, r.y +r.height/2);
		
		
		PointList abajo = new PointList();
		abajo.addPoint(r.x+r.width/2, 1+r.y +3*r.height/4);
		abajo.addPoint(r.x+r.width/2, r.y +r.height);*/
		
		/*g.drawPolygon(izquierdo);
		g.drawPolygon(derecho);
		g.drawPolygon(abajo);
		
		g.drawOval(r.x+30,r.y+15,r.width-60,r.height-25);*/
	}
}