package origami.graphics.listeners;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;

import origami.administration.AdminSeleccion;
import origami.administration.Figura;
import origami.graphics.MainWindow;
import origami.graphics.view.SelectionEvent;
import origami.graphics.widgets.TabFolder;



/**
 * 
 * @author Hudy
 *
 */
public class SelectionListener extends MouseListener.Stub implements MouseMotionListener{
	public AdminSeleccion seleccion;
	public static TabFolder tab;
	/**
	 * Da la propiedad de eliminar 
	 * a la figura recibida.
	 * @param figure
	 */
	public SelectionListener(Figura figure,AdminSeleccion selec,TabFolder tabfolder){
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
			
			seleccion.setFiguraSeleccionada(SelectionEvent.getFigureIndex(fig,tab));
			if(seleccion.getFiguraSeleccionada()!=-1){
				tab.getTabItem().getLeaf().addFigure();
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
