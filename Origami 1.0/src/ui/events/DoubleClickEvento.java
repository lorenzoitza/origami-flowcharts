package ui.events;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;

import Administracion.AdminSeleccion;
import Administracion.Figura;
import Administracion.TabFolder;
import Grafico.MainWindow;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;
import Grafico.VentanaDatos.DecisionFigureDialog;
import Grafico.VentanaDatos.ForFigureDialog;
import Grafico.VentanaDatos.InputFigureDialog;
import Grafico.VentanaDatos.OutputFigureDialog;
import Grafico.VentanaDatos.SentenceFigureDialog;
import Grafico.VentanaDatos.WhileFigureDialog;


public class DoubleClickEvento  extends MouseListener.Stub{
	public AdminSeleccion selectionAdmin;
	public TabFolder tabFolder;
	/**
	 * Da la propiedad de Doble click 
	 * a la figura recibida.
	 * @param figure
	 */
	public  DoubleClickEvento(Figura figure,AdminSeleccion selectionAdmin,TabFolder tabFolder) {
		figure.addMouseListener(this);  
		this.selectionAdmin = selectionAdmin;
		this.tabFolder = tabFolder;
	}
	/**
	 * Este metodo reconoce en que figura
	 * el evento se disparo y crea la ventana de datos.
	 * @param MouseEvent
	 */
	    public void mouseDoubleClicked(MouseEvent event) {
		Figura figure = ((Figura) event.getSource());
		
		if(figure instanceof SentenceFigure){
			SentenceFigure setenceFigure = (SentenceFigure)figure;
			
			new SentenceFigureDialog(MainWindow.shell,tabFolder
				,setenceFigure,selectionAdmin).open();
		}
		else if(figure instanceof DecisionFigure){
			DecisionFigure decisionFigure = ((DecisionFigure)figure);
			new DecisionFigureDialog(MainWindow.shell,tabFolder,
				decisionFigure,selectionAdmin).open();
		}
		else if(figure instanceof InputFigure){
			InputFigure inputFigure = ((InputFigure)figure);
			
			new InputFigureDialog(MainWindow.shell,tabFolder
				,inputFigure,selectionAdmin).open();
		}
		else if(figure instanceof OutputFigure){
			OutputFigure outputFigure = ((OutputFigure)figure);
			
			new OutputFigureDialog(MainWindow.shell,tabFolder
				,outputFigure,selectionAdmin).open();
		}
		else if(figure instanceof ForFigure){
			ForFigure forFigure = ((ForFigure)figure);
			
			new ForFigureDialog(MainWindow.shell,tabFolder
				,forFigure,selectionAdmin).open();
		}
		else if(figure instanceof WhileFigure){
			WhileFigure whileFigure = ((WhileFigure)figure);
			
			new WhileFigureDialog(MainWindow.shell,tabFolder
				,whileFigure,selectionAdmin).open();
			
		}
	}
}
