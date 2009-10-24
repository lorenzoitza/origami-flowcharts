package Administracion.Eventos;

import org.eclipse.draw2d.*;
import Administracion.AdminSeleccion;
import Administracion.Figura;
import Grafico.*;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;
import Grafico.VentanaDatos.InputFigureDialog;
import Grafico.VentanaDatos.ForFigureDialog;
import Grafico.VentanaDatos.OutputFigureDialog;
import Grafico.VentanaDatos.DecisionFigureDialog;
import Grafico.VentanaDatos.SentenceFigureDialog;
import Grafico.VentanaDatos.WhileFigureDialog;
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
		if(fig instanceof SentenceFigure){
			SentenceFigure f = (SentenceFigure)fig;
			new SentenceFigureDialog(Ventana.shell,tab,f,selec).open();
		}
		else if(fig instanceof DecisionFigure){
			DecisionFigure f = ((DecisionFigure)fig);
			new DecisionFigureDialog(Ventana.shell,tab,f,selec).open();
		}
		else if(fig instanceof InputFigure){
			InputFigure f = ((InputFigure)fig);
			new InputFigureDialog(Ventana.shell,tab,f,selec).open();
		}
		else if(fig instanceof OutputFigure){
			OutputFigure f = ((OutputFigure)fig);
			new OutputFigureDialog(Ventana.shell,tab,f,selec).open();
		}
		else if(fig instanceof ForFigure){
			ForFigure f = ((ForFigure)fig);
			new ForFigureDialog(Ventana.shell,tab,f,selec).open();
		}
		else if(fig instanceof WhileFigure){
			WhileFigure f = ((WhileFigure)fig);
			new WhileFigureDialog(Ventana.shell,tab,f,selec).open();
			
		}
	}
}
