package origami.administration;

import java.io.Serializable;
import java.util.Vector;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

import origami.graphics.MainWindow;


/**
 * Esta es la clase base de la cual todas las figuras heredan.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Figura extends Figure implements Serializable {

    private Vector<Integer> posicion = new Vector<Integer>();

    private boolean PasoAPaso = false;

    private boolean DerIzqFin = false;

    protected Rectangle rectangle;

    protected boolean seleccion = false;

    protected static final Font DEFAULT_FONT =
	    new Font(MainWindow.display, "Arial", 10, SWT.ITALIC);

    protected static final Color DEFAULT_TEXTCOLOR = new Color(null, 6, 93, 6);

    protected static final Color RED =
	    MainWindow.display.getSystemColor(SWT.COLOR_RED);

    protected static final Color GRAY =
	    MainWindow.display.getSystemColor(SWT.COLOR_GRAY);

    protected static final Color DARKBLUE =
	    MainWindow.display.getSystemColor(SWT.COLOR_DARK_BLUE);

    public Figura() {
	addMouseListener(new MouseListener() {

	    public void mouseDoubleClicked(MouseEvent arg0) {
		MainWindow.getComponents().tabFolder.getTabFolder().forceFocus();
	    }

	    public void mousePressed(MouseEvent arg0) {
		MainWindow.getComponents().tabFolder.getTabFolder().forceFocus();
	    }

	    public void mouseReleased(MouseEvent arg0) {
		MainWindow.getComponents().tabFolder.getTabFolder().forceFocus();
	    }
	});
    }

    protected void selectLineTipe(Graphics graphics) {
	if (isPasoAPaso()) {

	    graphics.setLineWidth(3);
	    graphics.setLineStyle(SWT.LINE_CUSTOM);
	    graphics.setForegroundColor(RED);
	} else {
	    if (seleccion) {

		graphics.setLineWidth(3);
		graphics.setLineStyle(SWT.LINE_DASH);
		graphics.setForegroundColor(GRAY);
	    } else {
		graphics.setLineWidth(2);
		graphics.setForegroundColor(DARKBLUE);
	    }
	}
    }

    public void setPosicion(int posicion) {
	this.posicion.add(posicion);
    }

    public Vector<Integer> getPosicion() {
	return posicion;
    }

    public void setListaPosicion(Vector<Integer> posicion) {
	this.posicion = posicion;
    }

    public void setSeleccion(boolean seleccion) {
	this.seleccion = seleccion;
    }

    public boolean isPasoAPaso() {
	return PasoAPaso;
    }

    public void setPasoAPaso(boolean pasoAPaso) {
	PasoAPaso = pasoAPaso;
    }

    public boolean isDerIzqFin() {
	return DerIzqFin;
    }

    public void setDerIzqFin(boolean derIzqFin) {
	DerIzqFin = derIzqFin;
    }
    
    
}