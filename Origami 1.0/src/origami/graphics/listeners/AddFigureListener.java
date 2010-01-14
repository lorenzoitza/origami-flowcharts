package origami.graphics.listeners;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;

import origami.administration.AdminSeleccion;
import origami.administration.actions.EventoAgregarFigura;
import origami.graphics.widgets.TabFolder;



public class AddFigureListener extends MouseMotionListener.Stub implements MouseListener{
    private EventoAgregarFigura ev;
	
    /**
     * Da la propiedad de Drag & Drop a la figura recibida.
     * @param figure
     */
    public AddFigureListener(Figure figure, AdminSeleccion selecc, TabFolder tabfolder) {
	figure.addMouseMotionListener(this);
	figure.addMouseListener(this);
	ev = new EventoAgregarFigura(selecc,tabfolder);
    }

    public void mouseReleased(MouseEvent e){
    }
	
    public void mouseClicked(MouseEvent e) {
    }

    public void mouseDoubleClicked(MouseEvent e) {
    }
	
    public void mousePressed(MouseEvent e) {
	ev.mousePresseds(e);
    }
	
    public void mouseDragged(MouseEvent e) {
    }
}
