package Administracion;

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

import ui.listener.AddFigureEvent;
import ui.listener.ChangeCursorEvent;
import ui.listener.ContextualMenuEvent;
import Administracion.Funcionalidad.DiagramFileManager;
import Grafico.BaseDeDiagrama;
import Grafico.MainWindow;
import Grafico.PaintDiagram;
import Grafico.TabFolder;
import Grafico.Figuras.CircleFigure;


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
	
	//resetScrollBar();
    }

	public void disabledCustomLeaf(){
	    this.chart.setOpaque(false);
	}
	public void enabledCustomLeaf(){
	    this.chart.setOpaque(true);
	    if(diagrama.diagrama.size()==0){
		CircleFigure inicio = new CircleFigure();
		inicio.setMesagge(" Inicio");
		CircleFigure fin = new CircleFigure();
		fin.setMesagge("  Fin");
		diagrama.diagrama.add(inicio);
		diagrama.diagrama.add(fin);
		new ContextualMenuEvent(diagrama.diagrama.elementAt(0));
		BaseDeDiagrama.getInstance().resetScrollBar();
		//resetScrollBar();
		addPropiedades();
	 }
	 BaseDeDiagrama.getInstance().addPaintDiagram(chart);
	 //setScroll();
	 addFigure();
	 guardarRetroceso();
	 if(tab.getItemCount()==1){
		MainWindow.getComponents().diagramaData.exclude=false;
		MainWindow.shell.layout();
		BaseDeDiagrama.getInstance().resetScrollBar();
		//resetScrollBar();
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
		diagrama.diagrama.elementAt(paso).setPasoAPaso(true);
		addFigure();
	}
	
	public void addFigure(){
		cambiarInicio();
		chart.agregarFiguras(diagrama.diagrama,chart);
		connection.crearConexiones(diagrama.diagrama);
		chart.agregarConexiones(connection.getConexion(),chart);
		if(MainWindow.getComponents().paso!=null && MainWindow.getComponents().paso.colaConexiones.size()!=0
				&& MainWindow.getComponents().paso.a.GetId() == tab.getSelectedTabItemId()){
			for(int x=0;x<MainWindow.getComponents().paso.colaConexiones.size();x++){
				connection.getConexion().elementAt(MainWindow.getComponents().paso.colaConexiones.get(x)).setForegroundColor(MainWindow.display.getSystemColor(SWT.COLOR_RED));
			}
		}
	}
	/**
	 * Mover metodo
	 */
	public void guardarRetroceso(){
		tab.agregarRetroceso(diagrama.diagrama);
	}
	public void addPropiedades(){
		new AddFigureEvent(chart,seleccion,tab);
		new ChangeCursorEvent(chart,tab);
	}
	public Conexion getConexion(){
		return connection;
	}
	public int getSizeDiagrama(){
		return diagrama.diagrama.size();
	}
	public Figura getFigureIndexOf(int i){
		return diagrama.diagrama.elementAt(i);
	}
	public void removeFigureIndexOf(int i){
		 diagrama.diagrama.removeElementAt(i);
	}
	public void removeFigureAllElements(){
		diagrama.diagrama.removeAllElements();
	}
	public void addFigureIndexOf(int i,Figura figura){
		diagrama.diagrama.add(i, figura);
	}
	public void insertFigureIndexOf(Figura figura,int i){
		diagrama.diagrama.insertElementAt(figura, i);
	}
	public PaintDiagram getDibujarDiagrama(){
		return chart;
	}
	/**
	 * Mover metodo
	 */
	public void openFile(String archivo,DiagramFileManager ser){
		Rectangle r = diagrama.diagrama.firstElement().getBounds();
        	diagrama.diagrama.removeAllElements();
        	chart.removeAll();
        	CustomFile aux = ser.recoverDiagram(archivo);
        	diagrama.diagrama = aux.getDiagrama();
        	tab.getTabItem().getInfo().setInfo(aux.getInfo());
        	tab.getTabItem().getInfo().upDateTime();
        	BaseDeDiagrama.getInstance().resetScrollBar();
        	//resetScrollBar();
        	diagrama.diagrama.firstElement().setBounds(r);
        	chart.agregarFiguras(diagrama.diagrama,chart);
        	connection.crearConexiones(diagrama.diagrama);
        	chart.agregarConexiones(connection.getConexion(),chart);
	}
	//este metodo es usado cuando no hay tabs y por lo tanto se llamaba a addDiagram el cual
	//agregaba todo de nuevo a los vectores para que no ocurriese ningun error.
	public void openNewFile(String archivo,DiagramFileManager ser ){
		//addDiagrama();
		BaseDeDiagrama.getInstance().addPaintDiagram(chart);
		//setScroll();
		addPropiedades();
		if(tab.getItemCount()==1){
			MainWindow.getComponents().diagramaData.exclude=false;
			MainWindow.shell.layout();
		}
		chart.setOpaque(true);
        	Rectangle r = new Rectangle(1090,100,80,50);
        	diagrama.diagrama.removeAllElements();
        	chart.removeAll();
        	CustomFile aux = ser.recoverDiagram(archivo);
        	diagrama.diagrama = aux.getDiagrama();
        	tab.getTabItem().getInfo().setInfo(aux.getInfo());
        	tab.getTabItem().getInfo().upDateTime();
        	BaseDeDiagrama.getInstance().resetScrollBar();
        	//resetScrollBar();
        	diagrama.diagrama.firstElement().setBounds(r);
        	chart.agregarFiguras(diagrama.diagrama,chart);
        	connection.crearConexiones(diagrama.diagrama);
        	chart.agregarConexiones(connection.getConexion(),chart);
	}
	public Vector<Figura> getDiagrama(){
		return diagrama.diagrama;
	}
	public AdminDiagrama getAdminDiagrama(){
		return diagrama;
	}
	public PaintDiagram getChart(){
		return chart;
	}
	public void cambiarInicio(){
		CircleFigure inicio = new CircleFigure();
		inicio.setMesagge(" Inicio");
		inicio.setLocation(diagrama.diagrama.elementAt(0).getLocation());
		diagrama.diagrama.removeElementAt(0);
		diagrama.diagrama.insertElementAt(inicio,0);
		new ContextualMenuEvent(diagrama.diagrama.elementAt(0));
		if(pasoInicio){
			diagrama.diagrama.elementAt(0).setPasoAPaso(true);
		}
	}

}
