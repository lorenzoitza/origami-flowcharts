package Administracion.Eventos;

import org.eclipse.draw2d.*;
import Administracion.AdminSeleccion;
import Administracion.Figura;
import Grafico.*;
import Grafico.Figuras.Entrada;
import Grafico.Figuras.For;
import Grafico.Figuras.If;
import Grafico.Figuras.Imprimir;
import Grafico.Figuras.Proceso;
import Grafico.Figuras.While;
import Grafico.VentanaDatos.DatosDecision;
import Grafico.VentanaDatos.DatosEntrada;
import Grafico.VentanaDatos.DatosFor;
import Grafico.VentanaDatos.DatosProceso;
import Grafico.VentanaDatos.DatosSalida;
import Grafico.VentanaDatos.DialogWhileLoop;
import Administracion.TabFolder;
/**
 * Esta clase da la propiedad de doble click
 * a las figuras del diagrama de flujo. 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class EventoDobleClick extends MouseListener.Stub{
	public AdminSeleccion selec;
	public TabFolder tab;
	/**
	 * Da la propiedad de Doble click 
	 * a la figura recibida.
	 * @param figure
	 */
	public  EventoDobleClick(Figura figure,AdminSeleccion seleccion,TabFolder tabfolder) {
		figure.addMouseListener(this);  
		selec = seleccion;
		tab = tabfolder;
	}
	/**
	 * Este metodo reconoce en que figura
	 * el evento se disparo y crea la ventana de datos.
	 * @param MouseEvent
	 */
	public void mouseDoubleClicked(MouseEvent e) {
		Figura fig = ((Figura) e.getSource());
		if(fig instanceof Proceso){
			Proceso f = (Proceso)fig;
			new DatosProceso(tab).ventana(Ventana.display,f,selec);
		}
		else if(fig instanceof If){
			If f = ((If)fig);
			new DatosDecision(tab).ventana(Ventana.display,f,selec);
		}
		else if(fig instanceof Entrada){
			Entrada f = ((Entrada)fig);
			new DatosEntrada(tab).ventana(Ventana.display,f,selec);
		}
		else if(fig instanceof Imprimir){
			Imprimir f = ((Imprimir)fig);
			new DatosSalida(tab).ventana(Ventana.display,f,selec);
		}
		else if(fig instanceof For){
			For f = ((For)fig);
			new DatosFor(tab).ventana(Ventana.display,f,selec);
		}
		else if(fig instanceof While){
			While f = ((While)fig);
			new DialogWhileLoop(tab).showDialog(Ventana.display,f,selec);
		}
	}
}
