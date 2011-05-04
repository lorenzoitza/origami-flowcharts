package origami.graphics.listeners;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;

import origami.administration.actions.ChangeCursorVerification;
import origami.graphics.widgets.CustomMenu;
import origami.graphics.widgets.TabFolder;

public class ChangeCursorListener extends MouseMotionListener.Stub implements
	MouseListener {

    private ChangeCursorVerification event;

    public TabFolder currentTab;

    public ChangeCursorListener(IFigure figure, TabFolder tabfolder) {
	figure.addMouseMotionListener(this);
	figure.addMouseListener(this);
	currentTab = tabfolder;
	event = new ChangeCursorVerification(figure, tabfolder);
    }

    public void mouseReleased(MouseEvent e) {
	CustomMenu.get_editMenu().setMenuAvailable();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseDoubleClicked(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent me) {
	event.mouseMovedCoordinate(me);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }
}
