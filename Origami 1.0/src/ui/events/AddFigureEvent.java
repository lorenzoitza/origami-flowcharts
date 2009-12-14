package ui.events;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;

import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Eventos.EventoAgregarFigura;

public class AddFigureEvent extends MouseMotionListener.Stub implements MouseListener{
    private EventoAgregarFigura ev;
	
    /**
     * Da la propiedad de Drag & Drop a la figura recibida.
     * @param figure
     */
    public AddFigureEvent(Figure figure, AdminSeleccion selecc, TabFolder tabfolder) {
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
