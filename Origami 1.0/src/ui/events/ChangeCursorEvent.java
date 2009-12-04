package ui.events;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Cursor;

import Administracion.Figura;
import Administracion.TabFolder;
import Administracion.Eventos.EventoCambiarCursor;
import Grafico.CustomeMenu;
import Grafico.MainWindow;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.WhileFigure;


public class ChangeCursorEvent  extends MouseMotionListener.Stub implements MouseListener{
	public boolean bandera=false;
	private EventoCambiarCursor ev;
	public TabFolder tab;
	/**
	 * Da la propiedad de Drag & Drop 
	 * a la figura recibida.
	 * @param figure
	 */
	public ChangeCursorEvent(IFigure figure,TabFolder tabfolder){
        	figure.addMouseMotionListener(this);
        	figure.addMouseListener(this);
        	tab = tabfolder;
        	ev = new EventoCambiarCursor(figure,tabfolder);
	}
	/**
	 * Recorre el diagrama de figuras y verifica si
	 * la figura fue liberada en un area disponible.
	 * @param MouseEvent 
	 */
	public void mouseReleased(MouseEvent e){
	    CustomeMenu._editMenu.menuDisponibleFigura();
	}
	public void mouseClicked(MouseEvent e) {
  	}
	public void mouseDoubleClicked(MouseEvent e) {
  	}
	public void mouseMoved(MouseEvent me) {
		ev.mouseMoveds(me);
	}
	/**
	 * Obtiene la localizacion en la que la figura fue seleccionada.
	 * @param MouseEvent 
	 */
	public void mousePressed(MouseEvent e) {
	}
	/**
	 * Obtiene la diferencia en la que la figura fue seleccionada 
	 * y la distancia a donde fue movida para actualizar su posicion.
	 * @param MouseEvent 
	 */
	public void mouseDragged(MouseEvent e) {
	}
}
