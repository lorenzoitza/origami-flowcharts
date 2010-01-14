package origami.administration;

import java.util.Vector;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.ScalableLayeredPane;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ScrollBar;

import origami.administration.funtionality.DiagramFileManager;
import origami.graphics.BaseDeDiagrama;
import origami.graphics.MainWindow;
import origami.graphics.PaintDiagram;
import origami.graphics.StepByStepComponents;
import origami.graphics.figures.CircleFigure;
import origami.graphics.listeners.AddFigureListener;
import origami.graphics.listeners.ChangeCursorListener;
import origami.graphics.listeners.ContextualMenuListener;
import origami.graphics.widgets.TabFolder;




public class CustomLeaf {
    
    private PaintDiagram chart;
    private AdminDiagrama diagrama;
    private Conexion connection;
    
    
    private AdminSeleccion seleccion; 
    private TabFolder tab;
    public boolean pasoInicio=false;
	
    public CustomLeaf(TabFolder tabfolder,AdminSeleccion seleccion){
	this.seleccion = seleccion;
	tab = tabfolder;
	chart = new PaintDiagram(seleccion,tab);
	diagrama = new AdminDiagrama(seleccion);
	connection = new Conexion(tab);
    }

	public void disabledCustomLeaf(){
	    this.chart.setOpaque(false);
	}
	public void enabledCustomLeaf(){
	    this.chart.setOpaque(true);
	    if(diagrama.diagram.size()==0){
		CircleFigure inicio = new CircleFigure();
		inicio.setMessage(" Inicio");
		CircleFigure fin = new CircleFigure();
		fin.setMessage("  Fin");
		diagrama.diagram.add(inicio);
		diagrama.diagram.add(fin);
		new ContextualMenuListener(diagrama.diagram.elementAt(0));
		BaseDeDiagrama.getInstance().resetScrollBar();
		addPropiedades();
	 }
	 BaseDeDiagrama.getInstance().addPaintDiagram(chart);
	 addFigure();
	 guardarRetroceso();
	 if(tab.getItemCount()==1){
		MainWindow.getComponents().diagramData.exclude=false;
		MainWindow.shell.layout();
		BaseDeDiagrama.getInstance().resetScrollBar();
	 }
	}
	
	/**
	 * 
	 * Mover metodo
	 */
	public void paso(int paso){
		if(paso==0){
			this.pasoInicio =true;
		}
		else{
			this.pasoInicio =false;
		}
		diagrama.diagram.elementAt(paso).setPasoAPaso(true);
		addFigure();
	}
	
	public void addFigure(){
		cambiarInicio();
		chart.agregarFiguras(diagrama.diagram,chart);
		connection.crearConexiones(diagrama.diagram);
		chart.agregarConexiones(connection.getConexion(),chart);
		if(MainWindow.getComponents().getByStepComponents().getPaso()!=null && MainWindow.getComponents().getByStepComponents().getPaso().colaConexiones.size()!=0
				&& MainWindow.getComponents().getByStepComponents().getPaso().a.GetId() == tab.getSelectedTabItemId()){
			for(int x=0;x<MainWindow.getComponents().getByStepComponents().getPaso().colaConexiones.size();x++){
				connection.getConexion().elementAt(MainWindow.getComponents().getByStepComponents().getPaso().colaConexiones.get(x)).setForegroundColor(MainWindow.display.getSystemColor(SWT.COLOR_RED));
			}
		}
	}
	/**
	 * Mover metodo
	 */
	public void guardarRetroceso(){
		tab.addUndo(diagrama.diagram);
	}
	public void addPropiedades(){
		new AddFigureListener(chart,seleccion,tab);
		new ChangeCursorListener(chart,tab);
	}
	public Conexion getConexion(){
		return connection;
	}
	public int getSizeDiagrama(){
		return diagrama.diagram.size();
	}
	public Figura getFigureIndexOf(int i){
		return diagrama.diagram.elementAt(i);
	}
	public void removeFigureIndexOf(int i){
		 diagrama.diagram.removeElementAt(i);
	}
	public void removeFigureAllElements(){
		diagrama.diagram.removeAllElements();
	}
	public void addFigureIndexOf(int i,Figura figura){
		diagrama.diagram.add(i, figura);
	}
	public void insertFigureIndexOf(Figura figura,int i){
		diagrama.diagram.insertElementAt(figura, i);
	}
	public PaintDiagram getDibujarDiagrama(){
		return chart;
	}
	/**
	 * Mover metodo
	 */
	public void openFile(String archivo,DiagramFileManager ser){
		Rectangle r = diagrama.diagram.firstElement().getBounds();
        	diagrama.diagram.removeAllElements();
        	chart.removeAll();
        	CustomFile aux = ser.recoverDiagram(archivo);
        	diagrama.diagram = aux.getDiagrama();
        	tab.getTabItem().getInformation().setInfo(aux.getInfo());
        	tab.getTabItem().getInformation().upDateTime();
        	BaseDeDiagrama.getInstance().resetScrollBar();
        	diagrama.diagram.firstElement().setBounds(r);
        	chart.agregarFiguras(diagrama.diagram,chart);
        	connection.crearConexiones(diagrama.diagram);
        	chart.agregarConexiones(connection.getConexion(),chart);
	}
	//este metodo es usado cuando no hay tabs y por lo tanto se llamaba a addDiagram el cual
	//agregaba todo de nuevo a los vectores para que no ocurriese ningun error.
	public void openNewFile(String archivo,DiagramFileManager ser ){
		BaseDeDiagrama.getInstance().addPaintDiagram(chart);
		addPropiedades();
		if(tab.getItemCount()==1){
			MainWindow.getComponents().diagramData.exclude=false;
			MainWindow.shell.layout();
		}
		chart.setOpaque(true);
        	Rectangle r = new Rectangle(1090,100,80,50);
        	diagrama.diagram.removeAllElements();
        	chart.removeAll();
        	CustomFile aux = ser.recoverDiagram(archivo);
        	diagrama.diagram = aux.getDiagrama();
        	tab.getTabItem().getInformation().setInfo(aux.getInfo());
        	tab.getTabItem().getInformation().upDateTime();
        	BaseDeDiagrama.getInstance().resetScrollBar();
        	diagrama.diagram.firstElement().setBounds(r);
        	chart.agregarFiguras(diagrama.diagram,chart);
        	connection.crearConexiones(diagrama.diagram);
        	chart.agregarConexiones(connection.getConexion(),chart);
	}
	public Vector<Figura> getDiagrama(){
		return diagrama.diagram;
	}
	public AdminDiagrama getAdminDiagrama(){
		return diagrama;
	}
	public PaintDiagram getChart(){
		return chart;
	}
	public void cambiarInicio(){
		CircleFigure inicio = new CircleFigure();
		inicio.setMessage(" Inicio");
		inicio.setLocation(diagrama.diagram.elementAt(0).getLocation());
		diagrama.diagram.removeElementAt(0);
		diagrama.diagram.insertElementAt(inicio,0);
		new ContextualMenuListener(diagrama.diagram.elementAt(0));
		if(pasoInicio){
			diagrama.diagram.elementAt(0).setPasoAPaso(true);
		}
	}

}
