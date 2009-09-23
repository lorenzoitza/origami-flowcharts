package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import Administracion.*;
import Administracion.Funcionalidad.Codigo.InstruccionCompuesta;
import Grafico.Ventana;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
/**
 * Esta clase es la que crea y 
 * dibujar a la figura del If.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class If extends Figura {
	public boolean remarcar;
	public int color;
	public InstruccionCompuesta instruccion = new InstruccionCompuesta();
	public If(int color){
		setBounds(new Rectangle(100,100,80,40));
		this.color = color;
	}
	/**
	 * Este metodo es el encargado de dibujar la figura.
	 * @param Graphics
	 */
	public void paint (Graphics g){
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
		p.addPoint(r.x+r.width/2,r.y);
		p.addPoint(r.x + r.width,r.y + r.height/2);
		p.addPoint(r.x+r.width/2,r.y+r.height);
		p.addPoint(r.x,r.y+r.height/2);
		g.drawPolygon(p);
		
		if((instruccion.instruccion.size() > 1 && instruccion.instruccion.firstElement().getInstruccionSimple().compareTo("null")!=0)){
			if(instruccion.instruccion.firstElement().instruccion.length() <= 5){
				int i=1;
				Font font = new Font(Ventana.display,"Arial", 10,SWT.ITALIC);
				Color colorText = new Color(null, i*6,84+i*9,i*6);
				g.setFont(font);
				g.setForegroundColor(colorText);
				String subStr = "Decision";
				g.drawText(subStr, r.x -22+r.width/2, r.y + 13); 
			}
			else{
				String str = instruccion.instruccion.elementAt(0).getInstruccionSimple();
				int numero = str.length()-2;
				if(numero+2 <=10){
					String subStr = str.substring(3,numero);
				    g.drawText(subStr, r.x -16+r.width/2, r.y + 13);
				}
				else{
					int h = numero-8;
					String subStr = str.substring(3,numero - h);
				    g.drawText(subStr+"....", r.x -16+r.width/2, r.y + 13);
				}
			}
		}	
		else{
			int i=1;
			Font font = new Font(Ventana.display,"Arial", 10,SWT.ITALIC);
			Color colorText = new Color(null, i*6,84+i*9,i*6);
			g.setFont(font);
			g.setForegroundColor(colorText);
			String subStr = "Decisión";
			g.drawText(subStr, r.x -22+r.width/2, r.y + 13); 
		}
	}
	public void setColor(int color){
		this.color = color;
	}
}