package origami.images;

import org.eclipse.swt.graphics.Image;

import origami.graphics.MainWindow;

/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class ImageLoader {

	public static Image getImage(String imageName){
		Image image = new Image(MainWindow.display, 
				ImageLoader.class.getResourceAsStream(imageName));
		return image;
	}
}
