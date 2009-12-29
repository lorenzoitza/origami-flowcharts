package Grafico;

import java.util.Vector;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import Administracion.AdminSeleccion;
import Administracion.Conexion;
import Administracion.DibujarDiagrama;
import Administracion.Figura;
import Administracion.TabFolder;
import Grafico.Figuras.SelectionSquare;

public class PaintDiagram extends Figure {
    public static AdminSeleccion selec;
    public TabFolder tab;
    public static boolean dispToolItem = false; 
    public static Vector<SelectionSquare> seleccion = new Vector<SelectionSquare>();
    DibujarDiagrama dibujarDiagrama;
    
    public PaintDiagram(AdminSeleccion selecc,TabFolder tabfolder){
	setBorder(new MarginBorder(5));
	setBackgroundColor(ColorConstants.white);
	setOpaque(true);
	setBounds(new Rectangle(0,0,2250,2500));
	selec = selecc;
	tab = tabfolder;
	addMouseListener(new MouseListener(){
	    public void mouseDoubleClicked(MouseEvent arg0) {
		tab.getTabFolder().forceFocus();
	    }
	    public void mousePressed(MouseEvent arg0) {
		tab.getTabFolder().forceFocus();
		if(MainWindow.getComponents().console.hide){
		    if(MainWindow.getComponents().console.hide){
			if(MainWindow.menu.consoleMenuItem.getSelection()){
			    MainWindow.getComponents().moverConsola(false);
			    MainWindow.menu.consoleMenuItem.setSelection(false);
			}
		    }
		}			
	    }
	    public void mouseReleased(MouseEvent arg0) {
		tab.getTabFolder().forceFocus();
	    }
	});
    }
    public PaintDiagram(int weight,int height){
	if(weight<=0){
	    weight=200;
	}
	else{
	    weight=(int)(weight*1.5);
	}
	setBackgroundColor(ColorConstants.white);
	setOpaque(true);
	setBounds(new Rectangle(0,0,weight,height+200));
    }
    
    public void agregarFiguras(Vector<Figura> diagrama,Figure chart){
	dibujarDiagrama = new DibujarDiagrama(selec, tab);
	pintar(dibujarDiagrama.agregarFiguras(diagrama),chart);
    }
    public void agregarFigurasExportar(Vector<Figura> diagrama,Figure chart,int lugar,int alt){
	dibujarDiagrama = new DibujarDiagrama(selec, tab);
	pintar(dibujarDiagrama.agregarFigurasExportar(diagrama,lugar,alt),chart);
    }
    public void pintar(Vector<Figura> diagramaFinalTotal, Figure chart){
	chart.removeAll();

	
	for(int xx =0;xx<diagramaFinalTotal.size();xx++){
	    chart.add(diagramaFinalTotal.elementAt(xx));
	}
	
	
	if(selec.getFiguraSeleccionada()!=-1){
		agregarSeleccion(diagramaFinalTotal,chart);
		MainWindow.menu._editMenu.menuDisponibleFigura();
	}
	/*else if(MainWindow.first){		//quitado del MainWindow y no se encontro error aun se corrigio agregando una linea en la clase 
		MainWindow._editMenu.menuDisponibleFigura();	//EventoCambiarCursor en el metodo mouseReleased
	}*/
	if(dispToolItem){
		MainWindow.getComponents().barraHerramientas.toolBarDisable();
	}
	dispToolItem = true;
    }
    
    public static void agregarSeleccion(Vector<Figura> diagrama,Figure chart){
	int x,y,width,height;
	x = diagrama.elementAt(selec.getFiguraSeleccionada()).getBounds().x;
	y = diagrama.elementAt(selec.getFiguraSeleccionada()).getBounds().y;
	height = diagrama.elementAt(selec.getFiguraSeleccionada()).getBounds().height;
	width = diagrama.elementAt(selec.getFiguraSeleccionada()).getBounds().width;
	
	for(int i=0; i<4; i++){
		SelectionSquare uno = new SelectionSquare(false,SWT.COLOR_DARK_GRAY);
		seleccion.insertElementAt(uno, i);
	}
	seleccion.elementAt(0).setLocation(new Point(x-8,y-10));
	seleccion.elementAt(1).setLocation(new Point(x+width+2,y-10));
	seleccion.elementAt(2).setLocation(new Point(x-8,y+height-2));
	seleccion.elementAt(3).setLocation(new Point(x+width+2,y+height-2));
	
	for(int i=0; i<4; i++){
		chart.add(seleccion.elementAt(i));
	}
	MainWindow.menu._editMenu.menuDisponibleFigura();
	diagrama.elementAt(selec.getFiguraSeleccionada()).setSeleccion(true);
    }
    
    public void disableCursor(Vector<Figura> diagrama, Figure chart){
	agregarFiguras(diagrama,chart);
	
	Conexion conexion = new Conexion(tab);
	conexion.crearConexiones(diagrama);
	if(MainWindow.getComponents().paso!=null && MainWindow.getComponents().paso.colaConexiones.size()!=0
		&& MainWindow.getComponents().paso.a.GetId() == tab.getSelectedTabItemId()){
	    for(int y=0;y<MainWindow.getComponents().paso.colaConexiones.size();y++){
		conexion.getConexion().elementAt(MainWindow.getComponents().paso.colaConexiones.get(y)).setForegroundColor(MainWindow.display.getSystemColor(SWT.COLOR_RED));
	    }
	}
	agregarConexiones(conexion.getConexion(),chart);
    }
    
    public void agregarConexiones(Vector <PolylineConnection> conexion,Figure chart){
	for(int x=0;x<conexion.size();x++){	
	    chart.add(conexion.elementAt(x)); 
	}
    }
}
