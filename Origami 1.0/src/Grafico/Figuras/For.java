package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.SchemeBorder;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

import Administracion.Figura;
import Administracion.Funcionalidad.Codigo.InstruccionCompuesta;
import Grafico.Ventana;
/**
 * Esta clase es la que crea y 
 * dibujar la figura del For.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class For extends Figura{
	private int color;
	public InstruccionCompuesta instruccion = new InstruccionCompuesta();
	
	public For(int color){
		setBounds(new Rectangle(100,100,80,40));
		this.color =color;
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
		p.addPoint(r.x+ (r.width/16 * 3),r.y+5);
		p.addPoint(r.x+ r.width - (r.width/16 * 3),r.y+5);
		p.addPoint(r.x+ r.width,r.y+r.height/2 );
		p.addPoint(r.x+ r.width - (r.width/16 * 3),r.y+r.height-5);
		p.addPoint(r.x+ (r.width/16 * 3),r.y+r.height-5 );
		p.addPoint(r.x,r.y+r.height/2);
		g.drawPolygon(p);
		
		String codigo = instruccion.instruccion.firstElement().getInstruccionSimple();
		codigo = codigo.replaceFirst("for","");
		codigo = codigo.replace("(","");
		codigo = codigo.replace(")","");
		codigo = codigo.replace("{","");
		if(codigo.compareTo("null") != 0 && codigo.compareTo("") != 0){
			String[] variables = codigo.split(";");
			int numero = codigo.length();
			String subStr = "";
			if(numero <= 9){
				for(int x=0;x<3;x++){
					subStr = subStr + variables[x] + ";";
				}
				g.drawText(subStr, r.x + r.width/5, r.y + 13);
			}
			else{
				int h = numero-7;
				subStr = codigo.substring(0,numero-h);
				g.drawText(subStr+"...", r.x + r.width/5, r.y + 13);
			}	
		}
		else{
			int i=1;
			Font font = new Font(Ventana.display,"Arial", 10,SWT.ITALIC);
			Color colorText = new Color(null, i*6,84+i*9,i*6);
			g.setFont(font);
			g.setForegroundColor(colorText);
			String subStr = "Para";
			g.drawText(subStr, r.x -13+r.width/2, r.y + 13); 
		}
	}
	public void setColor(int color){
		this.color = color;
	}
}
