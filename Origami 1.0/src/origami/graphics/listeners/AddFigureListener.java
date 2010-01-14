package origami.graphics.listeners;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;

import origami.administration.AdminSeleccion;
import origami.administration.actions.EventoAgregarFigura;
import origami.graphics.widgets.TabFolder;

public class AddFigureListener extends MouseMotionListener.Stub implements MouseListener{
    
    private EventoAgregarFigura event;
	
    public AddFigureListener(Figure figure, AdminSeleccion selecc, TabFolder tabfolder) {
	figure.addMouseMotionListener(this);
	figure.addMouseListener(this);
	event = new EventoAgregarFigura(selecc,tabfolder);
    }

    public void mouseReleased(MouseEvent e){
    }
	
    public void mouseClicked(MouseEvent e) {
    }

    public void mouseDoubleClicked(MouseEvent e) {
    }
	
    public void mousePressed(MouseEvent e) {
	event.mousePresseds(e);
    }
	
    public void mouseDragged(MouseEvent e) {
    }
}
