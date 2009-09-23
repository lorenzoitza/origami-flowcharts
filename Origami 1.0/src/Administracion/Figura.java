package Administracion;

import java.io.Serializable;
import java.util.Vector;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;

import Grafico.Ventana;
/**
 * Esta es la clase base de la cual
 * todas las figuras heredan.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Figura extends Figure implements Serializable{
	private Vector<Integer> posicion = new Vector<Integer>();
	private boolean PasoAPaso=false;
	private boolean DerIzqFin=false;
	protected boolean seleccion=false;
	
	public Figura(){
		addMouseListener(new MouseListener(){
			public void mouseDoubleClicked(MouseEvent arg0) {
				Ventana.diagramas.getTabFolder().forceFocus();
			}
			public void mousePressed(MouseEvent arg0) {
				Ventana.diagramas.getTabFolder().forceFocus();
			}
			public void mouseReleased(MouseEvent arg0) {
				Ventana.diagramas.getTabFolder().forceFocus();
			}
		});
	}
	public void setPosicion(int posicion){
		this.posicion.add(posicion);
	}
	public Vector<Integer> getPosicion(){
		return posicion;
	}
	public void setListaPosicion( Vector<Integer> posicion){
		this.posicion = posicion;
	}
	protected void setSeleccion(boolean seleccion) {
		this.seleccion = seleccion;
	}
	public boolean isPasoAPaso() {
		return PasoAPaso;
	}
	public void setPasoAPaso(boolean pasoAPaso) {
		PasoAPaso = pasoAPaso;
	}
	public boolean isDerIzqFin() {
		return DerIzqFin;
	}
	public void setDerIzqFin(boolean derIzqFin) {
		DerIzqFin = derIzqFin;
	}
}