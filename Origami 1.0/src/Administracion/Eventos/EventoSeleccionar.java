package Administracion.Eventos;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import Administracion.AdminSeleccion;
import Administracion.Figura;
import Administracion.TabFolder;
import Grafico.*;
/**
 * 
 * Esta clase establece la propiedad de eliminar.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class EventoSeleccionar extends MouseListener.Stub implements MouseMotionListener{
	public final Cursor[] cursor = MainWindow.getComponentes().cursor;
	public static Figura a ;
	public AdminSeleccion seleccion;
	public static TabFolder tab;
	/**
	 * Da la propiedad de eliminar 
	 * a la figura recibida.
	 * @param figure
	 */
	public EventoSeleccionar(Figura figure,AdminSeleccion selec,TabFolder tabfolder){
		figure.setCursor(new Cursor(MainWindow._display,SWT.CURSOR_HAND));
		figure.addMouseListener(this);
		figure.addMouseMotionListener(this);
		seleccion = selec;
		tab = tabfolder;
	}
	/**
	 * Elimina la figura recibida y vuelve a dibujar el diagrama.
	 * @param MouseEvent
	 */
	public void mousePressed(MouseEvent e){
		if(e.button == 1|| e.button == 3){
			Figura fig = ((Figura) e.getSource());
			seleccion.setFiguraSeleccionada(NumeroDelDiagrama(fig));
			if(seleccion.getFiguraSeleccionada()!=-1){
				tab.getHoja().addFigure();
			}
		}
	}
	public static int NumeroDelDiagrama(Figura fig){
		for(int i=0; i<tab.getHoja().getSizeDiagrama();i++){
			if(fig.getBounds() == tab.getHoja().getFigureIndexOf(i).getBounds()){
				return i;
			}
		}
		return -1;
	}
	public void mouseDragged(MouseEvent arg0) {
	}
	public void mouseEntered(MouseEvent arg0) {
	}
	public void mouseExited(MouseEvent arg0) {	
	}
	public void mouseHover(MouseEvent arg0) {
	}
	public void mouseMoved(MouseEvent arg0) {	
	}
}