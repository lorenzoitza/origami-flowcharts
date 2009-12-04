package ui.events;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;

import Administracion.AdminSeleccion;
import Administracion.Figura;
import Administracion.TabFolder;
import Administracion.Eventos.EventoSeleccionar;
import Grafico.MainWindow;


public class SelectEvent extends MouseListener.Stub implements MouseMotionListener{
	public final Cursor[] cursor = MainWindow.getComponentes().cursor;
	public static Figura a ;
	public AdminSeleccion seleccion;
	public static TabFolder tab;
	/**
	 * Da la propiedad de eliminar 
	 * a la figura recibida.
	 * @param figure
	 */
	public SelectEvent(Figura figure,AdminSeleccion selec,TabFolder tabfolder){
		figure.setCursor(new Cursor(MainWindow.display,SWT.CURSOR_HAND));
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
			
			seleccion.setFiguraSeleccionada(EventoSeleccionar.NumeroDelDiagrama(fig,tab));
			if(seleccion.getFiguraSeleccionada()!=-1){
				tab.getHoja().addFigure();
			}
		}
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
