package Grafico.Help;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;

import Imagenes.CargarImagenes;

/**
 * Esta clase crea la ventana informacion
 * de los autores.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */

public class Acerca{
	private Shell acercaShell;
	/**
	 * Crea la ventana con sus datos.
	 * @param display
	 */
	public void info(Display display) {
		acercaShell = new Shell(display);
		acercaShell.setSize(300, 400);
		acercaShell.setLocation(300, 200);
		acercaShell.setText("Acerca de Origami");
		//Image i = new Image(display,"imagenes\\icono.gif");
		acercaShell.setImage(CargarImagenes.getImagen("icono.GIF"));
		Label label = new Label(acercaShell, SWT.NONE);
		label.setLocation(45,45);
		label.setSize(250,15);
		label.setText("Origami By ..");
		Label label3 = new Label(acercaShell, SWT.NONE);
		label3.setLocation(75,105);
		label3.setSize(250,15);
		label3.setText("Juan Itzae Ku Quintana");
		Label label2 = new Label(acercaShell, SWT.NONE);
		label2.setLocation(75,125);
		label2.setSize(250,15);
		label2.setText("Rodriguez Camara Victor");
		
		acercaShell.open();
		while(!acercaShell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
			}
	}
}
