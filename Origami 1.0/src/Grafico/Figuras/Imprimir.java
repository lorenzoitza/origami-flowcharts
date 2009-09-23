package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import Administracion.*;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.Ventana;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
/**
 * Esta clase es la que crea y 
 * dibujar a la figura de imprimir.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Imprimir extends Figura {
	public int color;
	public InstruccionSimple instruccion = new InstruccionSimple();
	public Imprimir(int color){
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
		PointList pl = new PointList();
		pl.addPoint(r.x+1,r.y+1);
		pl.addPoint(r.x + (r.width/5)*3,r.y+1);
		pl.addPoint(r.x + r.width -1,r.y + r.height/2 -1);
		pl.addPoint(r.x + (r.width/5)*3,r.y +r.height -1);
	    pl.addPoint(r.x+1, r.y + r.height-1);
	    g.drawPolygon(pl);    
	    
	    String[] variables = new String[50];
	    variables = instruccion.getInstruccionSimple().split(";");
	    String[] variables2 = new String[50];
		int cont =0;
		for(int x=0;x<variables.length;x++){
			variables[x] = variables[x].replace("\\", "");
			variables[x] = variables[x].replaceFirst("p", "");
			if(variables[x].compareTo("") != 0){
				variables2[cont] = variables[x];
				cont++;
			}
		}
		for(int x=0;x<cont;x++){
			if(variables2[x].compareTo("") != 0){
				variables[x] = variables2[x];
			}
		}
	    if(instruccion.getInstruccionSimple().compareTo("null") != 0 && instruccion.getInstruccionSimple().compareTo("") != 0){
			String[] cantidad = instruccion.instruccion.split(";");
			String subStr1;
			int letras = variables[0].length();
			if(variables[0].length()>6 || cantidad.length>1){
				if(letras>5){
					subStr1 = variables[0].substring(0,5);
				}
				else{
					subStr1 = variables[0].substring(0,letras);
				}
				g.drawText(subStr1+"...", r.x + r.width/8, r.y+8);
			}
			else{
				subStr1 = variables[0].substring(0,letras);
				g.drawText(subStr1, r.x + r.width/8, r.y+8);	
			}
	    }
	    else{
	    	int i=1;
	    	Font font = new Font(Ventana.display,"Arial", 10,SWT.ITALIC);
	    	Color colorText = new Color(null, i*6,84+i*9,i*6);
	    	g.setFont(font);
	    	g.setForegroundColor(colorText);
	    	String subStr = "Salida";
	    	g.drawText(subStr, r.x -22+r.width/2, r.y + 13);
	    }
	}
	public void setColor(int color){
		this.color = color;
	}
}