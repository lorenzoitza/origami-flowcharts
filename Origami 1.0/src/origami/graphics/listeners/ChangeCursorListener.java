package origami.graphics.listeners;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;

import origami.administration.actions.EventoCambiarCursor;
import origami.graphics.widgets.CustomMenu;
import origami.graphics.widgets.TabFolder;

public class ChangeCursorListener extends MouseMotionListener.Stub implements
	MouseListener {

    public boolean bandera = false;

    private EventoCambiarCursor event;

    public TabFolder tab;

    public ChangeCursorListener(IFigure figure, TabFolder tabfolder) {
	figure.addMouseMotionListener(this);
	figure.addMouseListener(this);
	tab = tabfolder;
	event = new EventoCambiarCursor(figure, tabfolder);
    }

    public void mouseReleased(MouseEvent e) {
	CustomMenu.get_editMenu().setMenuAvailable();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseDoubleClicked(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent me) {
	event.mouseMoveds(me);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }
}
