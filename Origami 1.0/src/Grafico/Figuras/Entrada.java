package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

import Administracion.*;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.Ventana;
/**
 * Esta clase es la que crea y 
 * dibujar a la figura de entrada.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Entrada extends Figura {
	public int color;
	public InstruccionSimple instruccion = new InstruccionSimple();
	
	public Entrada(int color){
		setBounds(new Rectangle(100,100,80,40));
		this.color = color;
	}
	/**
	 * Este metodo es el encargado de dibujar la figura.
	 * @param Graphics
	 */
	public void paint(Graphics g){
		Rectangle r = bounds;
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
					g.setForegroundColor(Display.getCurrent().getSystemColor(color));
				}
		 }
		PointList p = new PointList();
		p.addPoint(r.x+25,r.y+1);
		p.addPoint(r.x+r.width,r.y+1);
		p.addPoint(r.x+r.width-25,r.height+r.y-1);
		p.addPoint(r.x,r.y+r.height-1);
		g.drawPolygon(p);
		if(instruccion.getInstruccionSimple().compareTo("null") != 0 && instruccion.getInstruccionSimple().compareTo("") != 0){
			String variables = instruccion.instruccion;
			String[] cantidad = instruccion.instruccion.split(";");
			int numero = variables.indexOf(";");
			String subStr1 = variables.substring(0,numero);
			if(cantidad[0].length()>6){
				subStr1 = cantidad[0].substring(0,5);
				g.drawText(subStr1+"...", r.x +10+ r.width/5, r.y+8);
			}
			else if(cantidad.length>1){
				subStr1 = cantidad[0];
				g.drawText(subStr1+"...", r.x +10+ r.width/5, r.y+8);
			}
			else{
				g.drawText(subStr1, r.x +10+ r.width/5, r.y+8);	
			}
		}
		else{
			int i=1;
			Font font = new Font(Ventana.display,"Arial", 10,SWT.ITALIC);
			Color colorText = new Color(null, i*6,84+i*9,i*6);
			g.setFont(font);
			g.setForegroundColor(colorText);
			String subStr = "Entrada";
			g.drawText(subStr, r.x -22+r.width/2, r.y + 13);
		}
	}
	public void setColor(int color){
		this.color = color;
	}
}