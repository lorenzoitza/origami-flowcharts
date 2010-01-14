package origami.graphics.listeners;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;

import origami.administration.AdminSelection;
import origami.administration.Figura;
import origami.graphics.MainWindow;
import origami.graphics.widgets.TabFolder;

public class SelectionListener extends MouseListener.Stub implements
	MouseMotionListener {

    private AdminSelection seleccion;
    

    private TabFolder currentTab;

    public SelectionListener(Figura figure, AdminSelection selec,
	    TabFolder tabfolder) {
	figure.setCursor(new Cursor(MainWindow.display, SWT.CURSOR_HAND));
	figure.addMouseListener(this);
	figure.addMouseMotionListener(this);
	seleccion = selec;
	currentTab = tabfolder;
    }

    public void mousePressed(MouseEvent e) {
	int leftClick = 1;
	int rightClick = 3;
	if (e.button == leftClick || e.button == rightClick) {
	    Figura figure = ((Figura) e.getSource());
	    seleccion.setFiguraSeleccionada(getFigureIndex(figure));
	    if (seleccion.getFiguraSeleccionada() != -1) {
		currentTab.getTabItem().getLeaf().addFigure();
	    }
	}
    }

    private int getFigureIndex(Figura fig) {
	for (int figureIndex = 0; figureIndex < currentTab.getTabItem()
		.getLeaf().getSizeDiagrama(); figureIndex++) {
	    if (fig.getBounds() == currentTab.getTabItem().getLeaf()
		    .getFigureIndexOf(figureIndex).getBounds()) {
		return figureIndex;
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
