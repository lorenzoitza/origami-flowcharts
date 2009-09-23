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
public class CuadroSeleccion extends Figura {
	public boolean remarcar;
	public int color;
	public CuadroSeleccion(boolean remarcar, int color){
		setBounds(new Rectangle(100,100,5,10));
		this.remarcar = remarcar;
		this.color = color;
	}
	/**
	 * Este metodo es el encargado de dibujar la figura.
	 * @param Graphics
	 */
	public void paint (Graphics g){
		Rectangle r = bounds;
		if(remarcar){
			g.setLineStyle(SWT.LINE_DOT);
		}
		else{
			g.setLineWidth(5);
		}
		g.setForegroundColor(Display.getCurrent().getSystemColor(color));
		PointList p = new PointList();
		p.addPoint(r.x+ (r.width/16 * 3),r.y+5);
		p.addPoint(r.x + r.width - (r.width/16 * 3),r.y+5);
		p.addPoint(r.x + r.width,r.y+r.height/2 );
		p.addPoint(r.x+ r.width - (r.width/16 * 3),r.y+r.height-5);
		p.addPoint(r.x+ (r.width/16 * 3),r.y+r.height-5 );
		p.addPoint(r.x,r.y+r.height/2);
		g.drawPolygon(p);
	}
}
