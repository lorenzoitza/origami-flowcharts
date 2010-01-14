package origami.graphics.Help42;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;

import origami.images.ImageLoader;

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
	int width = 300;
	int height = 400;
	aboutShell.setSize(width, height);
	int xCoord = 300;
	int yCoord = 200;
	aboutShell.setLocation(xCoord, yCoord);
	aboutShell.setText("Acerca de Origami");
	aboutShell.setImage(ImageLoader.getImage("icono.GIF"));
	
	addInfoLabels();
    }
    
    private void addInfoLabels(){
	createOrigamiLabel();
	
	createFirstAuthorLabel();
	
	createSecondAuthorLabel();
    }
    private void createFirstAuthorLabel(){
	Label firstAuthor = new Label(aboutShell, SWT.NONE);
	int xCoord = 75;
	int yCoord = 105;
	firstAuthor.setLocation(xCoord, yCoord);
	int width = 250;
	int height = 15;
	firstAuthor.setSize(width,height);
	firstAuthor.setText("Juan Itzae Ku Quintana");
    }
    private void createSecondAuthorLabel(){
	Label secondAuthor = new Label(aboutShell, SWT.NONE);
	int xCoord = 75;
	int yCoord = 125;
	secondAuthor.setLocation(xCoord,yCoord);
	int width = 250;
	int height = 15;
	secondAuthor.setSize(width,height);
	secondAuthor.setText("Rodriguez Camara Victor");
    }
    private void createOrigamiLabel(){
	Label aboutOrigami = new Label(aboutShell, SWT.NONE);
	int xCoord = 45;
	int yCoord = 45;
	aboutOrigami.setLocation(xCoord,yCoord);
	int width = 250;
	int height = 15;
	aboutOrigami.setSize(width,height);
	aboutOrigami.setText("Origami By ..");
    }
}