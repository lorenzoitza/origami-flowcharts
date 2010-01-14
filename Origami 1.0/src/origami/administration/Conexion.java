package origami.administration;

import java.util.Vector;
import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import origami.graphics.figures.*;
import origami.graphics.widgets.TabFolder;

public class Conexion extends Figure{
    private Vector <PolylineConnection> connections = new Vector <PolylineConnection>();
    
    private final Color BLACK_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
    
    private TabFolder tabFolder;

    private Label yesMessage = new Label("Si");

    private Label noMessage = new Label("No");
	
    public Conexion(TabFolder tabfolder){
	tabFolder = tabfolder;
    }

    public void crearConexiones(Vector<Figura> diagrama){
	int bottomRightPoint, upLeftPoint, index, cicleIndex;
	connections.removeAllElements();
	for(index=0;index<tabFolder.getTabItem().getLeaf().getSizeDiagrama()-1;index++){	
	    if(diagrama.elementAt(index+1) instanceof CircleFigure){
		PolylineConnection coneccion = new PolylineConnection();
		coneccion.setForegroundColor(BLACK_COLOR);
		coneccion.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(index)));
		coneccion.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		PolygonDecoration decoration = new PolygonDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(0,0);
		decorationPointList.addPoint(-2,2);
		decorationPointList.addPoint(-2,-2);
		decoration.setTemplate(decorationPointList);
		coneccion.setTargetDecoration(decoration);
		connections.addElement(coneccion);
		break;
	    }
	    PolylineConnection coneccion = new PolylineConnection();
	    coneccion.setForegroundColor(BLACK_COLOR);
	    coneccion.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(index)));
	    if(diagrama.elementAt(index+1) instanceof DecisionFigure){
		PolylineConnection coneccionpunto1 = new PolylineConnection();
		coneccionpunto1.setForegroundColor(BLACK_COLOR);
		PolylineConnection coneccionpunto2 = new PolylineConnection();
		coneccionpunto2.setForegroundColor(BLACK_COLOR);
		PolygonDecoration decoration = new PolygonDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(0,0);
		decorationPointList.addPoint(-2,2);
		decorationPointList.addPoint(-2,-2);
		decoration.setTemplate(decorationPointList);
		coneccion.setTargetDecoration(decoration);
		coneccion.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		connections.addElement(coneccion);
		
		PolylineConnection coneccion1 = new PolylineConnection();
		coneccion1.setForegroundColor(BLACK_COLOR);
		coneccion1.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		coneccion1.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+2)));
		connections.addElement(coneccion1);
		
		bottomRightPoint = conectarCiclo(diagrama,index+2); 
		
		ConnectionEndpointLocator localizacion = new ConnectionEndpointLocator(coneccion1, false);
		localizacion.setVDistance(-10);
		localizacion.setUDistance(20);
		coneccion1.add(yesMessage, localizacion);
		
		coneccionpunto1.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(bottomRightPoint)));
		
		PolylineConnection coneccion2 = new PolylineConnection();
		coneccion2.setForegroundColor(BLACK_COLOR);
		coneccion2.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		coneccion2.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(bottomRightPoint+1)));
		connections.addElement(coneccion2);
		ConnectionEndpointLocator localizacion2 = new ConnectionEndpointLocator(coneccion2, false);
		localizacion2.setVDistance(-10);
		localizacion2.setUDistance(20);
		coneccion2.add(noMessage, localizacion2);
		
		upLeftPoint = conectarCiclo(diagrama,bottomRightPoint+1); 
		
		index=upLeftPoint;
		PolygonDecoration decoration2 = new PolygonDecoration();
		PointList decorationPointList2 = new PointList();
		decorationPointList2.addPoint(4,0);
		decorationPointList2.addPoint(2,2);
		decorationPointList2.addPoint(2,-2);
		decoration2.setTemplate(decorationPointList2);
		coneccionpunto1.setTargetDecoration(decoration2);
		PolygonDecoration decoration3 = new PolygonDecoration();
		PointList decorationPointList3 = new PointList();
		decorationPointList3.addPoint(4,0);
		decorationPointList3.addPoint(2,2);
		decorationPointList3.addPoint(2,-2);
		decoration3.setTemplate(decorationPointList3);
		coneccionpunto2.setTargetDecoration(decoration3);
		coneccionpunto2.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(upLeftPoint)));
		coneccionpunto1.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		coneccionpunto2.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		connections.addElement(coneccionpunto1);
		connections.addElement(coneccionpunto2);
	    }
	    else if(diagrama.elementAt(index+1) instanceof ForFigure || diagrama.elementAt(index+1) instanceof WhileFigure){
		PolygonDecoration decoration = new PolygonDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(0,0);
		decorationPointList.addPoint(-2,2);
		decorationPointList.addPoint(-2,-2);
		decoration.setTemplate(decorationPointList);
		coneccion.setTargetDecoration(decoration);
		coneccion.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		connections.addElement(coneccion);
		
		cicleIndex=conectarCiclo(diagrama,index+1);
		
		PolylineConnection coneccionpunto1 = new PolylineConnection();
		coneccionpunto1.setForegroundColor(BLACK_COLOR);
		
		coneccionpunto1.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(cicleIndex)));
		coneccionpunto1.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(cicleIndex+1)));
		connections.addElement(coneccionpunto1);
		
		PolylineConnection coneccionpunto2 = new PolylineConnection();
		coneccionpunto2.setForegroundColor(BLACK_COLOR);
		
		coneccionpunto2.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(cicleIndex+1)));
		coneccionpunto2.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(cicleIndex+2)));
		connections.addElement(coneccionpunto2);
		
		PolylineConnection coneccionpunto3 = new PolylineConnection();
		coneccionpunto3.setForegroundColor(BLACK_COLOR);
		
		coneccionpunto3.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(cicleIndex+2)));
		coneccionpunto3.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		connections.addElement(coneccionpunto3);
		
		PolylineConnection coneccionpunto4 = new PolylineConnection();
		coneccionpunto4.setForegroundColor(BLACK_COLOR);
		PolygonDecoration decoration2 = new PolygonDecoration();
		PointList decorationPointList2 = new PointList();
		decorationPointList2.addPoint(0,0);
		decorationPointList2.addPoint(-2,2);
		decorationPointList2.addPoint(-2,-2);
		decoration2.setTemplate(decorationPointList2);
		coneccionpunto3.setTargetDecoration(decoration2);
		coneccionpunto4.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(cicleIndex+3)));
		coneccionpunto4.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		
		ConnectionEndpointLocator localizacion2 = new ConnectionEndpointLocator(coneccionpunto4, true);
		localizacion2.setVDistance(-5);
		localizacion2.setUDistance(25);
		coneccionpunto4.add(noMessage, localizacion2);
		
		connections.addElement(coneccionpunto4);
		
		PolylineConnection coneccionpunto5 = new PolylineConnection();
		coneccionpunto5.setForegroundColor(BLACK_COLOR);
		
		coneccionpunto5.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(cicleIndex+3)));
		coneccionpunto5.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(cicleIndex+4)));
		connections.addElement(coneccionpunto5);
		
		PolylineConnection coneccionpunto6 = new PolylineConnection();
		coneccionpunto6.setForegroundColor(BLACK_COLOR);
		
		coneccionpunto6.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(cicleIndex+4)));
		coneccionpunto6.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(cicleIndex+5)));
		connections.addElement(coneccionpunto6);
		
		index=cicleIndex+4;	
	    }
	    else{
		PolygonDecoration decoration = new PolygonDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(0,0);
		decorationPointList.addPoint(-2,2);
		decorationPointList.addPoint(-2,-2);
		decoration.setTemplate(decorationPointList);
		coneccion.setTargetDecoration(decoration);
		coneccion.setForegroundColor(BLACK_COLOR);
		coneccion.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		connections.addElement(coneccion);
	    }
	}	
    }
    /**
     * Este metodo establece las conexiones en los ciclos
     * y te devuelve la localizacion del final de ciclo.
     * @param diagrama
     * @param index
     * @return int
     */
    public int conectarCiclo(Vector<Figura> diagrama,int cicleIndex){
	int index = cicleIndex;
	int upRightPoint, upLeftPoint, recursiveIndex;
	while(true){
	    PolylineConnection conector = new PolylineConnection();
	    conector.setForegroundColor(BLACK_COLOR);
	    conector.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(index)));
	    if(diagrama.elementAt(index) instanceof ForFigure || diagrama.elementAt(index) instanceof WhileFigure){
		ConnectionEndpointLocator localizacion2 = new ConnectionEndpointLocator(conector, true);
		localizacion2.setVDistance(10);
		localizacion2.setUDistance(30);
		conector.add(yesMessage, localizacion2);
	    }
	    if(diagrama.elementAt(index+1) instanceof DecisionFigure){
		PolygonDecoration decoration = new PolygonDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(0,0);
		decorationPointList.addPoint(-2,2);
		decorationPointList.addPoint(-2,-2);
		decoration.setTemplate(decorationPointList);
		conector.setTargetDecoration(decoration);
		PolylineConnection coneccionpunto1 = new PolylineConnection();
		coneccionpunto1.setForegroundColor(BLACK_COLOR);
		PolylineConnection coneccionpunto2 = new PolylineConnection();
		coneccionpunto2.setForegroundColor(BLACK_COLOR);
		
		conector.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		connections.addElement(conector);
		
		PolylineConnection coneccion1 = new PolylineConnection();
		coneccion1 .setForegroundColor(BLACK_COLOR);
		coneccion1.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		coneccion1.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+2)));
		connections.addElement(coneccion1);
		ConnectionEndpointLocator localizacion = new ConnectionEndpointLocator(coneccion1, false);
		localizacion.setVDistance(-10);
		localizacion.setUDistance(20);
		
		coneccion1.add(yesMessage, localizacion);
		upRightPoint = conectarCiclo(diagrama,index+2); 
		
		coneccionpunto1.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(upRightPoint)));
		
		PolylineConnection coneccion2 = new PolylineConnection();
		coneccion2.setForegroundColor(BLACK_COLOR);
		coneccion2.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		coneccion2.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(upRightPoint+1)));
		connections.addElement(coneccion2);
		ConnectionEndpointLocator localizacion2 = new ConnectionEndpointLocator(coneccion2, false);
		localizacion2.setVDistance(-10);
		localizacion2.setUDistance(20);
		
		coneccion2.add(noMessage, localizacion2);
		
		upLeftPoint = conectarCiclo(diagrama,upRightPoint+1); 
		
		index=upLeftPoint;
		PolygonDecoration decoration2 = new PolygonDecoration();
		PointList decorationPointList2 = new PointList();
		decorationPointList2.addPoint(4,0);
		decorationPointList2.addPoint(2,2);
		decorationPointList2.addPoint(2,-2);
		decoration2.setTemplate(decorationPointList2);
		coneccionpunto1.setTargetDecoration(decoration2);
		PolygonDecoration decoration3 = new PolygonDecoration();
		PointList decorationPointList3 = new PointList();
		decorationPointList3.addPoint(4,0);
		decorationPointList3.addPoint(2,2);
		decorationPointList3.addPoint(2,-2);
		decoration3.setTemplate(decorationPointList3);
		coneccionpunto2.setTargetDecoration(decoration3);
		coneccionpunto2.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(upLeftPoint)));
		coneccionpunto1.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		coneccionpunto2.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		connections.addElement(coneccionpunto1);
		connections.addElement(coneccionpunto2);
	    }
	    else if(diagrama.elementAt(index+1) instanceof ForFigure || diagrama.elementAt(index+1) instanceof WhileFigure){
		PolygonDecoration decoration = new PolygonDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(0,0);
		decorationPointList.addPoint(-2,2);
		decorationPointList.addPoint(-2,-2);
		decoration.setTemplate(decorationPointList);
		conector.setTargetDecoration(decoration);
		conector.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		connections.addElement(conector);
		
		recursiveIndex=conectarCiclo(diagrama,index+1);
		
		PolylineConnection coneccionpunto1 = new PolylineConnection();
		coneccionpunto1 .setForegroundColor(BLACK_COLOR);
				
		coneccionpunto1.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(recursiveIndex)));
		coneccionpunto1.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(recursiveIndex+1)));
		connections.addElement(coneccionpunto1);
		
		PolylineConnection coneccionpunto2 = new PolylineConnection();
		coneccionpunto2 .setForegroundColor(BLACK_COLOR);
		
		coneccionpunto2.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(recursiveIndex+1)));
		coneccionpunto2.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(recursiveIndex+2)));
		connections.addElement(coneccionpunto2);
		
		PolylineConnection coneccionpunto3 = new PolylineConnection();
		coneccionpunto3 .setForegroundColor(BLACK_COLOR);
		PolygonDecoration decoration2 = new PolygonDecoration();
		PointList decorationPointList2 = new PointList();
		decorationPointList2.addPoint(0,0);
		decorationPointList2.addPoint(-2,2);
		decorationPointList2.addPoint(-2,-2);
		decoration2.setTemplate(decorationPointList2);
		coneccionpunto3.setTargetDecoration(decoration2);
		
		coneccionpunto3.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(recursiveIndex+2)));
		coneccionpunto3.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		connections.addElement(coneccionpunto3);
		
		PolylineConnection coneccionpunto4 = new PolylineConnection();
		coneccionpunto4 .setForegroundColor(BLACK_COLOR);
		
		coneccionpunto4.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(recursiveIndex+3)));
		coneccionpunto4.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		ConnectionEndpointLocator localizacion2 = new ConnectionEndpointLocator(coneccionpunto4, true);
		localizacion2.setVDistance(-5);
		localizacion2.setUDistance(25);
		coneccionpunto4.add(noMessage, localizacion2);
		connections.addElement(coneccionpunto4);
		
		PolylineConnection coneccionpunto5 = new PolylineConnection();
		coneccionpunto5 .setForegroundColor(BLACK_COLOR);
		
		coneccionpunto5.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(recursiveIndex+3)));
		coneccionpunto5.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(recursiveIndex+4)));
		connections.addElement(coneccionpunto5);
		
		PolylineConnection coneccionpunto6 = new PolylineConnection();
		coneccionpunto6.setForegroundColor(BLACK_COLOR);
		
		coneccionpunto6.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(recursiveIndex+4)));
		coneccionpunto6.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(recursiveIndex+5)));
		connections.addElement(coneccionpunto6);
		
		index=recursiveIndex+4;	
	    }
	    else if(diagrama.elementAt(index+1) instanceof EllipseFigure){
		conector.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		connections.addElement(conector);
		return index+1;
	    }
	    else{
		PolygonDecoration decoration = new PolygonDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(0,0);
		decorationPointList.addPoint(-2,2);
		decorationPointList.addPoint(-2,-2);
		decoration.setTemplate(decorationPointList);
		conector.setTargetDecoration(decoration);
		conector.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(index+1)));
		connections.addElement(conector);
	    }
	    index++;
	}
    }
    
    public Vector<PolylineConnection> getConexion() {
	return connections;
    }	 	
}