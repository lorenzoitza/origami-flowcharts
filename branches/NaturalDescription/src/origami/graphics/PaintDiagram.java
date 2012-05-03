package origami.graphics;

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

import origami.administration.AdminSelection;
import origami.administration.FigureConnections;
import origami.administration.DibujarDiagrama;
import origami.administration.FigureStructure;
import origami.graphics.figures.SelectionSquare;
import origami.graphics.widgets.CustomMenu;
import origami.graphics.widgets.CustomTabFolder;



public class PaintDiagram extends Figure {
    
    private AdminSelection selec;
    
    private CustomTabFolder tab;
    
    private boolean dispToolItem = false; 
    
    private Vector<SelectionSquare> seleccion = new Vector<SelectionSquare>();
    
    private DibujarDiagrama dibujarDiagrama;
    
    public PaintDiagram(AdminSelection selecc,CustomTabFolder tabfolder){
	setBorder(new MarginBorder(5));
	setBackgroundColor(ColorConstants.white);
	setOpaque(true);
	setBounds(new Rectangle(0,0,4500,2500));
	selec = selecc;
	tab = tabfolder;
	addMouseListener(new MouseListener(){
	    public void mouseDoubleClicked(MouseEvent arg0) {
		tab.getTabFolder().forceFocus();
	    }
	    public void mousePressed(MouseEvent arg0) {
		tab.getTabFolder().forceFocus();
		if(MainWindow.getComponents().customConsole.isHide()){
		    if(MainWindow.getComponents().customConsole.isHide()){
			if(CustomMenu.getConsoleMenuItem().getSelection()){
			    MainWindow.getComponents().restoreConsole(false);
			    CustomMenu.getConsoleMenuItem().setSelection(false);
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
    
    public void agregarFiguras(Vector<FigureStructure> diagrama,Figure chart){
	dibujarDiagrama = new DibujarDiagrama(selec, tab);
	pintar(dibujarDiagrama.agregarFiguras(diagrama),chart);
    }
    public void agregarFigurasExportar(Vector<FigureStructure> diagrama,Figure chart,int lugar,int alt){
	dibujarDiagrama = new DibujarDiagrama(selec, tab);
	pintar(dibujarDiagrama.agregarFigurasExportar(diagrama,lugar,alt),chart);
    }
    public void pintar(Vector<FigureStructure> diagramaFinalTotal, Figure chart){
	chart.removeAll();

	
	for(int xx =0;xx<diagramaFinalTotal.size();xx++){
	    chart.add(diagramaFinalTotal.elementAt(xx));
	}
	
	
	if(selec.getSelectedFigure()!=-1){
		agregarSeleccion(diagramaFinalTotal,chart);
		CustomMenu.get_editMenu().setMenuAvailable();
	}
	if(dispToolItem){
//		MainWindow.getComponents().customToolBar.disableToolBar();
		MainWindow.getComponents().customToolBar.updateEnabledItems();
	}
	dispToolItem = true;
    }
    
    private void agregarSeleccion(Vector<FigureStructure> diagrama,Figure chart){
	int x,y,width,height;
	x = diagrama.elementAt(selec.getSelectedFigure()).getBounds().x;
	y = diagrama.elementAt(selec.getSelectedFigure()).getBounds().y;
	height = diagrama.elementAt(selec.getSelectedFigure()).getBounds().height;
	width = diagrama.elementAt(selec.getSelectedFigure()).getBounds().width;
	
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
	CustomMenu.get_editMenu().setMenuAvailable();
	diagrama.elementAt(selec.getSelectedFigure()).setSeleccion(true);
    }
    
    public void disableCursor(Vector<FigureStructure> diagrama, Figure chart){
	agregarFiguras(diagrama,chart);
	
	FigureConnections conexion = new FigureConnections(tab);
	conexion.createConnections(diagrama);
	if(MainWindow.getComponents().getByStepComponents().getStepByStep()!=null && MainWindow.getComponents().getByStepComponents().getStepByStep().colaConexiones.size()!=0
		&& MainWindow.getComponents().getByStepComponents().getStepByStep().a.GetId() == tab.getSelectedTabItemId()){
	    for(int y=0;y<MainWindow.getComponents().getByStepComponents().getStepByStep().colaConexiones.size();y++){
		conexion.getConexion().elementAt(MainWindow.getComponents().getByStepComponents().getStepByStep().colaConexiones.get(y)).setForegroundColor(MainWindow.display.getSystemColor(SWT.COLOR_RED));
	    }
	}
	agregarConexiones(conexion.getConexion(),chart);
    }
    
    public void agregarConexiones(Vector <PolylineConnection> conexion,Figure chart){
	for(int x=0;x<conexion.size();x++){
	    chart.add(conexion.elementAt(x)); 
	}
    }
    public void setAdminSelec(AdminSelection selec){
	this.selec = selec;
    }
}
