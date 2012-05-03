package origami.administration;

import java.util.Vector;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import origami.administration.functionality.DiagramFileManager;
import origami.graphics.DiagramStructure;
import origami.graphics.MainWindow;
import origami.graphics.PaintDiagram;
import origami.graphics.figures.CircleFigure;
import origami.graphics.listeners.AddFigureListener;
import origami.graphics.listeners.ChangeCursorListener;
import origami.graphics.listeners.ContextualMenuListener;
import origami.graphics.widgets.TabFolder;

public class CustomLeaf {
    
    private PaintDiagram chart;
    private AdminDiagram diagrama;
    private FigureConnections connection;
    
    
    private AdminSelection seleccion; 
    private TabFolder tab;
    public boolean pasoInicio=false;
	
    public CustomLeaf(TabFolder tabfolder,AdminSelection seleccion){
	this.seleccion = seleccion;
	tab = tabfolder;
	chart = new PaintDiagram(seleccion,tab);
	diagrama = new AdminDiagram(seleccion);
	connection = new FigureConnections(tab);
    }

	public void disabledCustomLeaf(){
	    this.chart.setOpaque(false);
	}
	public void enabledCustomLeaf(){
	    this.chart.setOpaque(true);
	    if(diagrama.diagram.size()==0){
		CircleFigure inicio = new CircleFigure();
		inicio.setMessage(" Inicio");
		int centerPosition = (this.chart.getBounds().width-inicio.getBounds().width)/2;
		inicio.setBounds(new Rectangle(centerPosition,inicio.getBounds().y,inicio.getBounds().width,inicio.getBounds().height));
		
		CircleFigure fin = new CircleFigure();
		fin.setMessage("  Fin");
		diagrama.diagram.add(inicio);
		diagrama.diagram.add(fin);
		new ContextualMenuListener(diagrama.diagram.elementAt(0),this.seleccion,this.tab);
		DiagramStructure.getInstance().resetScrollBar();
		addPropiedades();
	 }
	 DiagramStructure.getInstance().addPaintDiagram(chart);
	 addFigure();
	 guardarRetroceso();
	 if(tab.getItemCount()==1){
		MainWindow.getComponents().diagramData.exclude=false;
		MainWindow.shell.layout();
		DiagramStructure.getInstance().resetScrollBar();
	 }
	 tab.getTabItem().getLeaf().getChart().setCursor(ApplicationState.cursor[0]);
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
		connection.createConnections(diagrama.diagram);
		chart.agregarConexiones(connection.getConexion(),chart);
		if(MainWindow.getComponents().getByStepComponents().getStepByStep()!=null && MainWindow.getComponents().getByStepComponents().getStepByStep().colaConexiones.size()!=0
				&& MainWindow.getComponents().getByStepComponents().getStepByStep().a.GetId() == tab.getSelectedTabItemId()){
			for(int x=0;x<MainWindow.getComponents().getByStepComponents().getStepByStep().colaConexiones.size();x++){
				connection.getConexion().elementAt(MainWindow.getComponents().getByStepComponents().getStepByStep().colaConexiones.get(x)).setForegroundColor(MainWindow.display.getSystemColor(SWT.COLOR_RED));
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
	public FigureConnections getConexion(){
		return connection;
	}
	public int getSizeDiagrama(){
		return diagrama.diagram.size();
	}
	public FigureStructure getFigureIndexOf(int i){
		return diagrama.diagram.elementAt(i);
	}
	public void removeFigureIndexOf(int i){
		 diagrama.diagram.removeElementAt(i);
	}
	public void removeFigureAllElements(){
		diagrama.diagram.removeAllElements();
	}
	public void addFigureIndexOf(int i,FigureStructure figura){
		diagrama.diagram.add(i, figura);
	}
	public void insertFigureIndexOf(FigureStructure figura,int i){
		diagrama.diagram.insertElementAt(figura, i);
	}
	public PaintDiagram getDibujarDiagrama(){
		return chart;
	}
	/**
	 * Mover metodo
	 */
//	public void openFile(String archivo,DiagramFileManager ser){
//		Rectangle r = diagrama.diagram.firstElement().getBounds();
//        	diagrama.diagram.removeAllElements();
//        	chart.removeAll();
//        	CustomFile aux = ser.recoverDiagram(archivo);
//        	diagrama.diagram = aux.getDiagrama();        	
//        	tab.getTabItem().getInformation().setInformation(aux.getInfo());
//        	tab.getTabItem().getInformation().upDateTime();
//        	DiagramStructure.getInstance().resetScrollBar();
//        	diagrama.diagram.firstElement().setBounds(r);
//        	
////        	addFigure();
//        	
//        	chart.agregarFiguras(diagrama.diagram,chart);
//        	connection.createConnections(diagrama.diagram);
//        	chart.agregarConexiones(connection.getConexion(),chart);
//	}
	//este metodo es usado cuando no hay tabs y por lo tanto se llamaba a addDiagram el cual
	//agregaba todo de nuevo a los vectores para que no ocurriese ningun error.
	public void openNewFile(String archivo,DiagramFileManager ser ){
		DiagramStructure.getInstance().addPaintDiagram(chart);
		//No deberia agregar esta propiedad por que ya fue agregada al momento
		//de agregarse un TabItem
//		addPropiedades();
		if(tab.getItemCount()==1){
			MainWindow.getComponents().diagramData.exclude=false;
			MainWindow.shell.layout();
		}
		chart.setOpaque(true);
		int centerPosition = (this.chart.getBounds().width-80)/2;
        	Rectangle r = new Rectangle(centerPosition,100,80,50);
        	diagrama.diagram.removeAllElements();
        	chart.removeAll();
        	CustomFile aux = ser.recoverDiagram(archivo);
        	diagrama.diagram = aux.getDiagrama();
        	tab.getTabItem().getInformation().setInformation(aux.getInfo());
        	tab.getTabItem().getInformation().updateFile(Information.OPEN);
        	DiagramStructure.getInstance().resetScrollBar();
        	diagrama.diagram.firstElement().setBounds(r);
        	
        	chart.agregarFiguras(diagrama.diagram,chart);
        	connection.createConnections(diagrama.diagram);
        	chart.agregarConexiones(connection.getConexion(),chart);
        	
        	addFigure();
        	
	}
	public Vector<FigureStructure> getDiagrama(){
		return diagrama.diagram;
	}
	public AdminDiagram getAdminDiagrama(){
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
		new ContextualMenuListener(diagrama.diagram.elementAt(0),this.seleccion,this.tab);
		if(pasoInicio){
			diagrama.diagram.elementAt(0).setPasoAPaso(true);
		}
	}

}
