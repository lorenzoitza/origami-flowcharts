package Administracion.Eventos;


import Administracion.Figura;
import Administracion.TabFolder;

/**
 * 
 * Esta clase establece la propiedad de eliminar.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class EventoSeleccionar{
	public static TabFolder tab;

	public static int NumeroDelDiagrama(Figura fig,TabFolder tab){
		for(int i=0; i<tab.getHoja().getSizeDiagrama();i++){
			if(fig.getBounds() == tab.getHoja().getFigureIndexOf(i).getBounds()){
				return i;
			}
		}
		return -1;
	}
}