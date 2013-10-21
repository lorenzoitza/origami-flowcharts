package origami.graphics.listeners;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;

import origami.administration.AdminSelection;
import origami.administration.actions.AddFigureVerification;
import origami.graphics.widgets.CustomTabFolder;

public class AddFigureListener extends MouseMotionListener.Stub implements MouseListener{
    
    private AddFigureVerification addFigureVerification;
	
    public AddFigureListener(Figure figure, AdminSelection selecc, CustomTabFolder tabfolder) {
	figure.addMouseMotionListener(this);
	figure.addMouseListener(this);
	addFigureVerification = new AddFigureVerification(selecc,tabfolder);
    }

    public void mouseReleased(MouseEvent e){
    }
	
    public void mouseClicked(MouseEvent e) {
    }

    public void mouseDoubleClicked(MouseEvent e) {
    }
	
    public void mousePressed(MouseEvent e) {
	addFigureVerification.mousePressedCoordinate(e);
    }
	
    public void mouseDragged(MouseEvent e) {
    }
}
