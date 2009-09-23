package Imagenes;

import org.eclipse.swt.graphics.Image;

import Grafico.Ventana;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class CargarImagenes {

	public static Image getImagen(String NombreDeLaimagen){
		Image imagen = new Image(Ventana.display, CargarImagenes.class.getResourceAsStream(NombreDeLaimagen));
		return imagen;
	}
}
