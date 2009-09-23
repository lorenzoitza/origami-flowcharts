package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import Administracion.*;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.Ventana;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
/**
 * Esta clase es la que crea y 
 * dibujar a la figura de Proceso.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Proceso extends Figura {
	public int color;
	public InstruccionSimple instruccion = new InstruccionSimple();
	public Proceso(int color){
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
		g.drawRectangle(r.x+1, r.y+1, r.width - 2, r.height - 2);
		if(instruccion.instruccion != "null" && instruccion.instruccion.compareTo("null")!=0){
			String str = instruccion.instruccion;
			int numero = str.length();
			if(numero <= 10){
				String subStr = str.substring(0,numero - 1);
				g.drawText(subStr, r.x + r.width/8, r.y+15);
			}
			else{
				int h = numero-9;
				String subStr = str.substring(0,numero - h);
				g.drawText(subStr+"...", r.x + r.width/8, r.y+15);
			}
		}
		else{
			int i=1;
			Font font = new Font(Ventana.display,"Arial", 10,SWT.ITALIC);
			Color colorText = new Color(null, i*6,84+i*9,i*6);
			g.setFont(font);
			g.setForegroundColor(colorText);
			String subStr = "Expresión";
			g.drawText(subStr, r.x -26+r.width/2, r.y + 13); 
		}
	}
	public void setColor(int color){
		this.color = color;
	}
}