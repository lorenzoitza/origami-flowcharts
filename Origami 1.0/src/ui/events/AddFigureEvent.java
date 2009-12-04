package ui.events;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;

import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Eventos.EventoAgregarFigura;


public class AddFigureEvent extends MouseMotionListener.Stub implements MouseListener{
	private Point start;
	public boolean bandera=false;
	public final Cursor[] cursor = new Cursor[1];
	public int cursorPrincipal = SWT.CURSOR_ARROW;
	public AdminSeleccion selec;
	public TabFolder tab;
	public EventoAgregarFigura ev;
	/**
	 * Da la propiedad de Drag & Drop 
	 * a la figura recibida.
	 * @param figure
	 */
	public AddFigureEvent(Figure figure,AdminSeleccion selecc,TabFolder tabfolder) {
		figure.addMouseMotionListener(this);
		figure.addMouseListener(this);
		selec = selecc;
		tab = tabfolder;
		ev = new EventoAgregarFigura(selecc,tabfolder);
	}
	/**
	 * Recorre el diagrama de figuras y verifica si
	 * la figura fue liberada en un area disponible.
	 * @param MouseEvent 
	 */
	public void mouseReleased(MouseEvent e){
	}
	public void mouseClicked(MouseEvent e) {
  	}
	public void mouseDoubleClicked(MouseEvent e) {
  	}
	/**
	 * Obtiene la localizacion en la que la figura fue seleccionada.
	 * @param MouseEvent 
	 */
	public void mousePressed(MouseEvent e) {
	    ev.mousePresseds(e);
	}
	public void mouseDragged(MouseEvent e) {
	}
}
