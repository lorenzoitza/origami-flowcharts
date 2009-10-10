package Imagenes;

import org.eclipse.swt.graphics.Image;

import Grafico.Ventana;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class ImageLoader {

	public static Image getImage(String imageName){
		Image image = new Image(Ventana.display, 
				ImageLoader.class.getResourceAsStream(imageName));
		return image;
	}
}
