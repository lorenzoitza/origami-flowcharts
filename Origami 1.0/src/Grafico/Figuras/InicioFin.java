package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import Administracion.*;
import Grafico.Ventana;
/**
 * Esta clase es la que crea y 
 * dibujar a la figura de Inicio
 * y del Fin del diagrama vistas en la pantalla.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class InicioFin extends Figura {
	private String mensaje;
	
	public InicioFin(){
		seleccion = false;
		setBounds(new Rectangle(1090,100,80,50));
	}
	/**
	 * Este metodo coloca el mensaje de inicio o fin.
	 * @param mensaje
	 */
	public void setMensaje(String mensaje){
		this.mensaje = mensaje;
	}
	public String getMensaje(){
		return mensaje;
	}
	/**
	 * Este metodo es el encargado de dibujar la figura.
	 * @param Graphics
	 */
	public void paint(Graphics g) {
		if(isPasoAPaso()){
			g.setLineWidth(3);
			g.setLineStyle(SWT.LINE_CUSTOM);
			Color c = Ventana.display.getSystemColor(SWT.COLOR_RED);
			g.setForegroundColor(c);
		}
		else{
			if(seleccion){
				g.setLineWidth(3);
				g.setLineStyle(SWT.LINE_DASH);
				Color c = Ventana.display.getSystemColor(SWT.COLOR_GRAY);
				g.setForegroundColor(c);
			}
			else{
				g.setLineWidth(2);
				Color c = Ventana.display.getSystemColor(SWT.COLOR_DARK_BLUE);
				g.setForegroundColor(c);
			}
		}
		Rectangle r = bounds;
		g.drawOval(r.x + r.width/6 +1, r.y +2, (r.width/8)*5, (r.height/5)*4+5);
	    g.drawText(mensaje, r.x + r.width / 4 + 5, r.y + r.height / 3);
	}
}