package origami.administration;

import java.util.Vector;
import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import origami.graphics.figures.*;
import origami.graphics.widgets.TabFolder;

public class FigureConnections extends Figure{
    private Vector <PolylineConnection> connections = new Vector <PolylineConnection>();
    
    private final Color BLACK_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
    
    private TabFolder tabFolder;

    private String yesMessage = "Si";

    private String noMessage = "No";
	
    public FigureConnections(TabFolder tabfolder){
	tabFolder = tabfolder;
    }

    public void createConnections(Vector<FigureStructure> diagram){
	int bottomRightPoint, upLeftPoint, index, cicleIndex;
	connections.removeAllElements();
	for(index=0;index<tabFolder.getTabItem().getLeaf().getSizeDiagrama()-1;index++){
	    if(diagram.elementAt(index+1) instanceof CircleFigure){
		PolylineConnection coneccion = new PolylineConnection();
		coneccion.setForegroundColor(BLACK_COLOR);
		coneccion.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(index)));
		coneccion.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
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
	    coneccion.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(index)));
	    if(diagram.elementAt(index+1) instanceof DecisionFigure){
		PolylineConnection firstConnection = new PolylineConnection();
		firstConnection.setForegroundColor(BLACK_COLOR);
		
		PolylineConnection secondConnection = new PolylineConnection();
		secondConnection.setForegroundColor(BLACK_COLOR);
		
		PolygonDecoration decoration = new PolygonDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(0,0);
		decorationPointList.addPoint(-2,2);
		decorationPointList.addPoint(-2,-2);
		decoration.setTemplate(decorationPointList);
		coneccion.setTargetDecoration(decoration);
		coneccion.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		connections.addElement(coneccion);
		
		PolylineConnection thirdConnection = new PolylineConnection();
		thirdConnection.setForegroundColor(BLACK_COLOR);
		thirdConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		thirdConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+2)));
		connections.addElement(thirdConnection);
		
		bottomRightPoint = connectCicle(diagram,index+2); 
		
		ConnectionEndpointLocator localizacion = new ConnectionEndpointLocator(thirdConnection, false);
		localizacion.setVDistance(-10);
		localizacion.setUDistance(20);
		thirdConnection.add(new Label(yesMessage), localizacion);
		
		firstConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(bottomRightPoint)));
		
		PolylineConnection fourthConnection = new PolylineConnection();
		fourthConnection.setForegroundColor(BLACK_COLOR);
		fourthConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		fourthConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(bottomRightPoint+1)));
		connections.addElement(fourthConnection);
		ConnectionEndpointLocator localizacion2 = new ConnectionEndpointLocator(fourthConnection, false);
		localizacion2.setVDistance(-10);
		localizacion2.setUDistance(20);
		fourthConnection.add(new Label(noMessage), localizacion2);
		
		upLeftPoint = connectCicle(diagram,bottomRightPoint+1); 
		
		index=upLeftPoint;
		PolygonDecoration decoration2 = new PolygonDecoration();
		PointList decorationPointList2 = new PointList();
		decorationPointList2.addPoint(4,0);
		decorationPointList2.addPoint(2,2);
		decorationPointList2.addPoint(2,-2);
		decoration2.setTemplate(decorationPointList2);
		firstConnection.setTargetDecoration(decoration2);
		PolygonDecoration decoration3 = new PolygonDecoration();
		PointList decorationPointList3 = new PointList();
		decorationPointList3.addPoint(4,0);
		decorationPointList3.addPoint(2,2);
		decorationPointList3.addPoint(2,-2);
		decoration3.setTemplate(decorationPointList3);
		secondConnection.setTargetDecoration(decoration3);
		secondConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(upLeftPoint)));
		firstConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		secondConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		connections.addElement(firstConnection);
		connections.addElement(secondConnection);
	    }
	    else if(diagram.elementAt(index+1) instanceof ForFigure || diagram.elementAt(index+1) instanceof WhileFigure){
		PolygonDecoration decoration = new PolygonDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(0,0);
		decorationPointList.addPoint(-2,2);
		decorationPointList.addPoint(-2,-2);
		decoration.setTemplate(decorationPointList);
		coneccion.setTargetDecoration(decoration);
		coneccion.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		connections.addElement(coneccion);
		
		cicleIndex=connectCicle(diagram,index+1);
		
		PolylineConnection firstConnection = new PolylineConnection();
		firstConnection.setForegroundColor(BLACK_COLOR);
		
		firstConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(cicleIndex)));
		firstConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(cicleIndex+1)));
		connections.addElement(firstConnection);
		
		PolylineConnection secondConnection = new PolylineConnection();
		secondConnection.setForegroundColor(BLACK_COLOR);
		
		secondConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(cicleIndex+1)));
		secondConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(cicleIndex+2)));
		connections.addElement(secondConnection);
		
		PolylineConnection thirdConnection = new PolylineConnection();
		thirdConnection.setForegroundColor(BLACK_COLOR);
		
		thirdConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(cicleIndex+2)));
		thirdConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		connections.addElement(thirdConnection);
		
		PolylineConnection fourthConnection = new PolylineConnection();
		fourthConnection.setForegroundColor(BLACK_COLOR);
		PolygonDecoration decoration2 = new PolygonDecoration();
		PointList decorationPointList2 = new PointList();
		decorationPointList2.addPoint(0,0);
		decorationPointList2.addPoint(-2,2);
		decorationPointList2.addPoint(-2,-2);
		decoration2.setTemplate(decorationPointList2);
		thirdConnection.setTargetDecoration(decoration2);
		fourthConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(cicleIndex+3)));
		fourthConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		
		ConnectionEndpointLocator localizacion2 = new ConnectionEndpointLocator(fourthConnection, true);
		localizacion2.setVDistance(-5);
		localizacion2.setUDistance(25);
		fourthConnection.add(new Label(noMessage), localizacion2);
		
		connections.addElement(fourthConnection);
		
		PolylineConnection fifthConnection = new PolylineConnection();
		fifthConnection.setForegroundColor(BLACK_COLOR);
		
		fifthConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(cicleIndex+3)));
		fifthConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(cicleIndex+4)));
		connections.addElement(fifthConnection);
		
		PolylineConnection sixthConnection = new PolylineConnection();
		sixthConnection.setForegroundColor(BLACK_COLOR);
		
		sixthConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(cicleIndex+4)));
		sixthConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(cicleIndex+5)));
		connections.addElement(sixthConnection);
		
		int numFigureVertexPoints = 4;
		index = cicleIndex + numFigureVertexPoints;	
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
		coneccion.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		connections.addElement(coneccion);
	    }
	}	
    }
    /**
     * Este metodo establece las conexiones en los ciclos
     * y te devuelve la localizacion del final de ciclo.
     * @param diagram
     * @param index
     * @return int
     */
    public int connectCicle(Vector<FigureStructure> diagram,int cicleIndex){
	int index = cicleIndex;
	int upRightPoint, upLeftPoint, recursiveIndex;
	while(true){
	    PolylineConnection conector = new PolylineConnection();
	    conector.setForegroundColor(BLACK_COLOR);
	    conector.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(index)));
	    if(diagram.elementAt(index) instanceof ForFigure || diagram.elementAt(index) instanceof WhileFigure){
		ConnectionEndpointLocator localizacion2 = new ConnectionEndpointLocator(conector, true);
		localizacion2.setVDistance(10);
		localizacion2.setUDistance(30);
		conector.add(new Label(yesMessage), localizacion2);
	    }
	    if(diagram.elementAt(index+1) instanceof DecisionFigure){
		PolygonDecoration decoration = new PolygonDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(0,0);
		decorationPointList.addPoint(-2,2);
		decorationPointList.addPoint(-2,-2);
		decoration.setTemplate(decorationPointList);
		conector.setTargetDecoration(decoration);
		PolylineConnection firstConnection = new PolylineConnection();
		firstConnection.setForegroundColor(BLACK_COLOR);
		PolylineConnection secondConnection = new PolylineConnection();
		secondConnection.setForegroundColor(BLACK_COLOR);
		
		conector.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		connections.addElement(conector);
		
		PolylineConnection thirdConnection = new PolylineConnection();
		thirdConnection .setForegroundColor(BLACK_COLOR);
		thirdConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		thirdConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+2)));
		connections.addElement(thirdConnection);
		ConnectionEndpointLocator localizacion = new ConnectionEndpointLocator(thirdConnection, false);
		localizacion.setVDistance(-10);
		localizacion.setUDistance(20);
		
		thirdConnection.add(new Label(yesMessage), localizacion);
		upRightPoint = connectCicle(diagram,index+2); 
		
		firstConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(upRightPoint)));
		
		PolylineConnection fourthConnection = new PolylineConnection();
		fourthConnection.setForegroundColor(BLACK_COLOR);
		fourthConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		fourthConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(upRightPoint+1)));
		connections.addElement(fourthConnection);
		ConnectionEndpointLocator localizacion2 = new ConnectionEndpointLocator(fourthConnection, false);
		localizacion2.setVDistance(-10);
		localizacion2.setUDistance(20);
		
		fourthConnection.add(new Label(noMessage), localizacion2);
		
		upLeftPoint = connectCicle(diagram,upRightPoint+1); 
		
		index=upLeftPoint;
		PolygonDecoration decoration2 = new PolygonDecoration();
		PointList decorationPointList2 = new PointList();
		decorationPointList2.addPoint(4,0);
		decorationPointList2.addPoint(2,2);
		decorationPointList2.addPoint(2,-2);
		decoration2.setTemplate(decorationPointList2);
		firstConnection.setTargetDecoration(decoration2);
		PolygonDecoration decoration3 = new PolygonDecoration();
		PointList decorationPointList3 = new PointList();
		decorationPointList3.addPoint(4,0);
		decorationPointList3.addPoint(2,2);
		decorationPointList3.addPoint(2,-2);
		decoration3.setTemplate(decorationPointList3);
		secondConnection.setTargetDecoration(decoration3);
		secondConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(upLeftPoint)));
		firstConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		secondConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		
		connections.addElement(firstConnection);
		connections.addElement(secondConnection);
	    }
	    else if(diagram.elementAt(index+1) instanceof ForFigure || diagram.elementAt(index+1) instanceof WhileFigure){
		PolygonDecoration decoration = new PolygonDecoration();
		PointList decorationPointList = new PointList();
		decorationPointList.addPoint(0,0);
		decorationPointList.addPoint(-2,2);
		decorationPointList.addPoint(-2,-2);
		decoration.setTemplate(decorationPointList);
		conector.setTargetDecoration(decoration);
		conector.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		connections.addElement(conector);
		
		recursiveIndex=connectCicle(diagram,index+1);
		
		PolylineConnection firstConnection = new PolylineConnection();
		firstConnection .setForegroundColor(BLACK_COLOR);
				
		firstConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(recursiveIndex)));
		firstConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(recursiveIndex+1)));
		connections.addElement(firstConnection);
		
		PolylineConnection secondConnection = new PolylineConnection();
		secondConnection .setForegroundColor(BLACK_COLOR);
		
		secondConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(recursiveIndex+1)));
		secondConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(recursiveIndex+2)));
		connections.addElement(secondConnection);
		
		PolylineConnection thirdConnection = new PolylineConnection();
		thirdConnection .setForegroundColor(BLACK_COLOR);
		PolygonDecoration decoration2 = new PolygonDecoration();
		PointList decorationPointList2 = new PointList();
		decorationPointList2.addPoint(0,0);
		decorationPointList2.addPoint(-2,2);
		decorationPointList2.addPoint(-2,-2);
		decoration2.setTemplate(decorationPointList2);
		thirdConnection.setTargetDecoration(decoration2);
		
		thirdConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(recursiveIndex+2)));
		thirdConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		connections.addElement(thirdConnection);
		
		PolylineConnection fourthConnection = new PolylineConnection();
		fourthConnection .setForegroundColor(BLACK_COLOR);
		
		fourthConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(recursiveIndex+3)));
		fourthConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		ConnectionEndpointLocator localizacion2 = new ConnectionEndpointLocator(fourthConnection, true);
		localizacion2.setVDistance(-5);
		localizacion2.setUDistance(25);
		fourthConnection.add(new Label(noMessage), localizacion2);
		connections.addElement(fourthConnection);
		
		PolylineConnection fifthConnection = new PolylineConnection();
		fifthConnection .setForegroundColor(BLACK_COLOR);
		
		fifthConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(recursiveIndex+3)));
		fifthConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(recursiveIndex+4)));
		connections.addElement(fifthConnection);
		
		PolylineConnection sixthConnection = new PolylineConnection();
		sixthConnection.setForegroundColor(BLACK_COLOR);
		
		sixthConnection.setSourceAnchor(new ChopboxAnchor(diagram.elementAt(recursiveIndex+4)));
		sixthConnection.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(recursiveIndex+5)));
		connections.addElement(sixthConnection);
		
		index=recursiveIndex+4;	
	    }
	    else if(diagram.elementAt(index+1) instanceof EllipseFigure){
		conector.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
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
		conector.setTargetAnchor(new ChopboxAnchor(diagram.elementAt(index+1)));
		connections.addElement(conector);
	    }
	    index++;
	}
    }
    
    public Vector<PolylineConnection> getConexion() {
	return connections;
    }	 	
}