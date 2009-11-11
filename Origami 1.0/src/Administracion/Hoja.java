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
import Administracion.Eventos.EventoAgregarFigura;
import Administracion.Eventos.EventoCambiarCursor;
import Administracion.Eventos.EventoMenuContextual;
import Administracion.Funcionalidad.Serializar;
import Grafico.MainWindow;
import Grafico.Figuras.CircleFigure;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Hoja{
	private Vector<DibujarDiagrama> chart = new Vector<DibujarDiagrama>();
	private Vector<AdminDiagrama> diagrama = new Vector<AdminDiagrama>();
	private Vector<Conexion> connection = new Vector<Conexion>();
	private AdminSeleccion seleccion; 
	private Figure panel = new Figure();
	private FigureCanvas lws;
	private int contador=-1;
	private TabFolder tab;
	private ScalableLayeredPane scaledPane = new ScalableLayeredPane();
	public boolean pasoInicio=false;
	private Composite diagramaArea;
	
	public Hoja(Display display,TabFolder tabfolder,AdminSeleccion selec){
		MainWindow.getComponents().agregarBarraFiguras();
		diagramaArea = new Composite(MainWindow._shell,SWT.NONE);
		diagramaArea.setLayoutData(MainWindow.getComponents().diagramaData);
		GridLayout layoutComposite = new GridLayout(1, false);
		GridData diagramaGrid =new GridData(SWT.FILL,SWT.FILL,true,true,1,1);
		layoutComposite.horizontalSpacing =layoutComposite.verticalSpacing=0;
		layoutComposite.marginWidth = layoutComposite.marginHeight = 0;
		lws = new FigureCanvas(diagramaArea);
		org.eclipse.draw2d.GridLayout gridLayout = new org.eclipse.draw2d.GridLayout();
		gridLayout.numColumns = 1;
		panel.setLayoutManager(gridLayout);
		org.eclipse.draw2d.GridData gridData = new org.eclipse.draw2d.GridData(SWT.FILL,SWT.FILL,true,true,1,1);
		gridLayout.horizontalSpacing =gridLayout.verticalSpacing=0;
		gridLayout.marginWidth = gridLayout.marginHeight = 0;
		gridLayout.setConstraint(scaledPane, gridData);
		tab = tabfolder;
		seleccion = selec;
		MainWindow._shell.addMouseWheelListener(new MouseWheelListener(){
			public void mouseScrolled(MouseEvent arg0) {
				int direction=1;
				if(arg0.count>0){
					direction=-1;
				}
				lws.scrollToY(lws.getVerticalBar().getSelection()+direction*30);
			}
		});
		lws.getVerticalBar().addSelectionListener(new SelectionListener(){
			public void widgetSelected(SelectionEvent arg0) {
				int direction=1;
				if(arg0.detail==16777217){
					direction=-1;
				}
				lws.getVerticalBar().setSelection(lws.getVerticalBar().getSelection()+direction*30);
			}
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		diagramaArea.setLayout(layoutComposite);
		lws.setLayoutData(diagramaGrid);
	}
	public void addDiagrama(){
		contador++;
		DibujarDiagrama uno = new DibujarDiagrama(seleccion,tab);
		AdminDiagrama dos = new AdminDiagrama(seleccion);
		Conexion tres = new Conexion(tab);
		chart.insertElementAt(uno,contador);
		diagrama.insertElementAt(dos,contador);
		connection.insertElementAt(tres,contador);
		resetScrollBar();
	}
	public void setScroll(){
		lws.setContents(panel);
		panel.add(scaledPane);
		scaledPane.removeAll();
		scaledPane.add(chart.elementAt(seleccion.getSeleccionDigrama()));
	}
	public void cambiarPanel(TabFolder tab){
		for(int k=0; k<chart.size(); k++){
			 chart.elementAt(k).setOpaque(false);
		}
		if(diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.size()==0){
			CircleFigure inicio = new CircleFigure();
			inicio.setMensagge(" Inicio");
			CircleFigure fin = new CircleFigure();
			fin.setMensagge("  Fin");
			diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.add(inicio);
			diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.add(fin);
			new EventoMenuContextual(diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.elementAt(0),tab,seleccion);
			resetScrollBar();
			addPropiedades();
		 }
		 setScroll();
		 addFigure();
		 guardarRetroceso();
		 chart.elementAt(seleccion.getSeleccionDigrama()).setOpaque(true); 
		 if(tab.getItemCount()==1){
			MainWindow.getComponents().diagramaData.exclude=false;
			MainWindow._shell.layout();
			resetScrollBar();
		 }
	}
	public void paso(int paso){
		if(paso==0){
			this.pasoInicio =true;
		}
		else{
			this.pasoInicio =false;
		}
		diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.elementAt(paso).setPasoAPaso(true);
		addFigure();
	}
	public void addFigure(){
		cambiarInicio();
		chart.elementAt(seleccion.getSeleccionDigrama()).agregarFiguras(diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama,chart.elementAt(seleccion.getSeleccionDigrama()));
		connection.elementAt(seleccion.getSeleccionDigrama()).crearConexiones(diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama);
		chart.elementAt(seleccion.getSeleccionDigrama()).agregarConexiones(connection.elementAt(seleccion.getSeleccionDigrama()).getConexion(),chart.elementAt(seleccion.getSeleccionDigrama()));
		if(MainWindow.getComponents().paso!=null && MainWindow.getComponents().paso.colaConexiones.size()!=0
				&& MainWindow.getComponents().paso.a.GetId() == tab.getSelectedTabItemId()){
			for(int x=0;x<MainWindow.getComponents().paso.colaConexiones.size();x++){
				connection.elementAt(seleccion.getSeleccionDigrama()).getConexion().elementAt(MainWindow.getComponents().paso.colaConexiones.get(x)).setForegroundColor(MainWindow._display.getSystemColor(SWT.COLOR_RED));
			}
		}
	}
	public void guardarRetroceso(){
		tab.agregarRetroceso(diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama);
	}
	public void addPropiedades(){
		new EventoAgregarFigura(chart.elementAt(seleccion.getSeleccionDigrama()),seleccion,tab);
		new EventoCambiarCursor(chart.elementAt(seleccion.getSeleccionDigrama()),tab);
	}
	public Conexion getConexion(){
		return connection.elementAt(seleccion.getSeleccionDigrama());
	}
	public int getSizeDiagrama(){
		return diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.size();
	}
	public Figura getFigureIndexOf(int i){
		return diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.elementAt(i);
	}
	public void removeFigureIndexOf(int i){
		 diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.removeElementAt(i);
	}
	public void removeFigureAllElements(){
		diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.removeAllElements();
	}
	public void addFigureIndexOf(int i,Figura figura){
		diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.add(i, figura);
	}
	public void insertFigureIndexOf(Figura figura,int i){
		diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.insertElementAt(figura, i);
	}
	public DibujarDiagrama getDibujarDiagrama(){
		return chart.elementAt(seleccion.getSeleccionDigrama());
	}
	public Figure getPanel(){
		return panel;
	}
	public ScrollBar getHScrollBar(){
		return lws.getHorizontalBar();
	}
	public ScrollBar getVScrollBar(){
		return lws.getVerticalBar();
	}
	public void resetScrollBar(){
		lws.scrollToX((int)((lws.getHorizontalBar().getMaximum()-lws.getHorizontalBar().getSize().x)*50/100));
		lws.scrollToY(0);
	}
	public void setScrollBar(int x, int y){
		lws.scrollToX(x);
		lws.scrollToY(y);
	}
	public void setBoundsToZero(){
		diagramaArea.setBounds(0,0,0,0);
		lws.setBounds(0,0,0,0);
		panel.setBounds(new Rectangle(0,0,0,0));
		scaledPane.setBounds(new Rectangle(0,0,0,0));
	}
	public void openFile(String archivo){
		Rectangle r = diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.firstElement().getBounds();
    	diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.removeAllElements();
    	chart.elementAt(seleccion.getSeleccionDigrama()).removeAll();
    	Archivo aux = MainWindow.getSerializer().abrir(archivo);
    	diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama = aux.getDiagrama();
    	tab.getTabItem().getInfo().setInfo(aux.getInfo());
    	tab.getTabItem().getInfo().upDateTime();
    	resetScrollBar();
    	diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.firstElement().setBounds(r);
    	chart.elementAt(seleccion.getSeleccionDigrama()).agregarFiguras(diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama,chart.elementAt(seleccion.getSeleccionDigrama()));
    	connection.elementAt(seleccion.getSeleccionDigrama()).crearConexiones(diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama);
    	chart.elementAt(seleccion.getSeleccionDigrama()).agregarConexiones(connection.elementAt(seleccion.getSeleccionDigrama()).getConexion(),chart.elementAt(seleccion.getSeleccionDigrama()));
	}
	public void openNewFile(String archivo,Serializar ser ){
		addDiagrama();
		setScroll();
		addPropiedades();
		if(tab.getItemCount()==1){
			MainWindow.getComponents().diagramaData.exclude=false;
			MainWindow._shell.layout();
		}
		chart.elementAt(seleccion.getSeleccionDigrama()).setOpaque(true);
    	Rectangle r = new Rectangle(1090,100,80,50);
    	diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.removeAllElements();
    	chart.elementAt(seleccion.getSeleccionDigrama()).removeAll();
    	Archivo aux = MainWindow.getSerializer().abrir(archivo);
    	diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama = aux.getDiagrama();
    	tab.getTabItem().getInfo().setInfo(aux.getInfo());
    	tab.getTabItem().getInfo().upDateTime();
    	resetScrollBar();
    	diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.firstElement().setBounds(r);
    	chart.elementAt(seleccion.getSeleccionDigrama()).agregarFiguras(diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama,chart.elementAt(seleccion.getSeleccionDigrama()));
    	connection.elementAt(seleccion.getSeleccionDigrama()).crearConexiones(diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama);
    	chart.elementAt(seleccion.getSeleccionDigrama()).agregarConexiones(connection.elementAt(seleccion.getSeleccionDigrama()).getConexion(),chart.elementAt(seleccion.getSeleccionDigrama()));
	}
	public Vector<Figura> getDiagrama(){
		return diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama;
	}
	public AdminDiagrama getAdminDiagrama(){
		return diagrama.elementAt(seleccion.getSeleccionDigrama());
	}
	public AdminDiagrama getAdminDiagramaIndexOf(int i){
		return diagrama.elementAt(i);
	}
	public Vector<DibujarDiagrama> getCharts(){
		return chart;
	}
	public DibujarDiagrama getChart(){
		return chart.elementAt(seleccion.getSeleccionDigrama());
	}
	public ScalableLayeredPane getScaledPane() {
		return scaledPane;
	}
	public void setScaledPane(ScalableLayeredPane scaledPane) {
		this.scaledPane = scaledPane;
	}
	public void cambiarInicio(){
		CircleFigure inicio = new CircleFigure();
		inicio.setMensagge(" Inicio");
		inicio.setLocation(diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.elementAt(0).getLocation());
		diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.removeElementAt(0);
		diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.insertElementAt(inicio,0);
		new EventoMenuContextual(diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.elementAt(0),tab,seleccion);
		if(pasoInicio){
			diagrama.elementAt(seleccion.getSeleccionDigrama()).diagrama.elementAt(0).setPasoAPaso(true);
		}
	}
}
