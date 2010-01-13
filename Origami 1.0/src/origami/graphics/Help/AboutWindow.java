package Grafico.Help;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import Imagenes.ImageLoader;

/**
 * Esta clase crea la ventana informacion
 * de los autores.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */

public class AboutWindow{
    private Shell aboutShell;

    public void showWindow() {
	aboutShell.open();	
    }
    
    public void createWindow(Display display){
	aboutShell = new Shell(display);
	aboutShell.setSize(300, 400);
	aboutShell.setLocation(300, 200);
	aboutShell.setText("Acerca de Origami");
	aboutShell.setImage(ImageLoader.getImage("icono.GIF"));
	
	createInfoLabels();
    }
    
    private void createInfoLabels(){
	Label aboutOrigami = new Label(aboutShell, SWT.NONE);
	aboutOrigami.setLocation(45,45);
	aboutOrigami.setSize(250,15);
	aboutOrigami.setText("Origami By ..");
	
	Label firstAuthor = new Label(aboutShell, SWT.NONE);
	firstAuthor.setLocation(75,105);
	firstAuthor.setSize(250,15);
	firstAuthor.setText("Juan Itzae Ku Quintana");
	
	Label secondAuthor = new Label(aboutShell, SWT.NONE);
	secondAuthor.setLocation(75,125);
	secondAuthor.setSize(250,15);
	secondAuthor.setText("Rodriguez Camara Victor");
    }
}