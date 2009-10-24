package Administracion;

import java.util.Vector;
import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import Grafico.Figuras.*;
/**
 * Esta clase establece las conexiones
 * entre las figuras.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 *
 */
public class Conexion extends Figure{
	private Vector <PolylineConnection> conexion = new Vector <PolylineConnection>();
	private int color;
	public TabFolder tab;
	
	public Conexion(TabFolder tabfolder){
		tab = tabfolder;
		color = SWT.COLOR_BLACK;
	}
	/**
	 * Este metodo crea las conexiones entre las figuras.
	 * @param diagrama
	 */
	public void crearConexiones(Vector<Figura> diagrama){
		int pda,pia,j;
		conexion.removeAllElements();
		for(int i=0;i<tab.getHoja().getSizeDiagrama()-1;i++){	
			if(diagrama.elementAt(i+1) instanceof TerminationFigure){
				PolylineConnection coneccion = new PolylineConnection();
				coneccion.setForegroundColor(Display.getCurrent().getSystemColor(color));
				coneccion.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(i)));
				coneccion.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				PolygonDecoration decoration = new PolygonDecoration();
				PointList decorationPointList = new PointList();
				decorationPointList.addPoint(0,0);
				decorationPointList.addPoint(-2,2);
				decorationPointList.addPoint(-2,-2);
				decoration.setTemplate(decorationPointList);
				coneccion.setTargetDecoration(decoration);
				conexion.addElement(coneccion);
				break;
			}
			PolylineConnection coneccion = new PolylineConnection();
			coneccion.setForegroundColor(Display.getCurrent().getSystemColor(color));
			coneccion.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(i)));
			if(diagrama.elementAt(i+1) instanceof DecisionFigure){
				PolylineConnection coneccionpunto1 = new PolylineConnection();
				coneccionpunto1.setForegroundColor(Display.getCurrent().getSystemColor(color));
				PolylineConnection coneccionpunto2 = new PolylineConnection();
				coneccionpunto2.setForegroundColor(Display.getCurrent().getSystemColor(color));
				PolygonDecoration decoration = new PolygonDecoration();
				PointList decorationPointList = new PointList();
				decorationPointList.addPoint(0,0);
				decorationPointList.addPoint(-2,2);
				decorationPointList.addPoint(-2,-2);
				decoration.setTemplate(decorationPointList);
				coneccion.setTargetDecoration(decoration);
				coneccion.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				conexion.addElement(coneccion);

				//derecha de arriba 
				PolylineConnection coneccion1 = new PolylineConnection();
				coneccion1.setForegroundColor(Display.getCurrent().getSystemColor(color));
				coneccion1.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				coneccion1.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+2)));
				conexion.addElement(coneccion1);
				
				pda = conectarCiclo(diagrama,i+2); 
				
				ConnectionEndpointLocator localizacion = new ConnectionEndpointLocator(coneccion1, false);
				localizacion.setVDistance(-10);
				localizacion.setUDistance(20);
				Label mensaje = new Label("Si");
				coneccion1.add(mensaje, localizacion);
				
				//dereche de abajo 
				coneccionpunto1.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(pda)));

				//izquierda arriba	
				PolylineConnection coneccion2 = new PolylineConnection();
				 coneccion2.setForegroundColor(Display.getCurrent().getSystemColor(color));
				coneccion2.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				coneccion2.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(pda+1)));
				conexion.addElement(coneccion2);
				ConnectionEndpointLocator localizacion2 = new ConnectionEndpointLocator(coneccion2, false);
				localizacion2.setVDistance(-10);
				localizacion2.setUDistance(20);
				Label mensaje2 = new Label("No");
				coneccion2.add(mensaje2, localizacion2);
				
				pia = conectarCiclo(diagrama,pda+1); 
				
				//izquierda abajo	
				i=pia;
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
				coneccionpunto2.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(pia)));
				coneccionpunto1.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				coneccionpunto2.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				conexion.addElement(coneccionpunto1);
				conexion.addElement(coneccionpunto2);
			}
			else if(diagrama.elementAt(i+1) instanceof ForFigure || diagrama.elementAt(i+1) instanceof WhileFigure){
				PolygonDecoration decoration = new PolygonDecoration();
				PointList decorationPointList = new PointList();
				decorationPointList.addPoint(0,0);
				decorationPointList.addPoint(-2,2);
				decorationPointList.addPoint(-2,-2);
				decoration.setTemplate(decorationPointList);
				coneccion.setTargetDecoration(decoration);
				coneccion.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				conexion.addElement(coneccion);
				
				j=conectarCiclo(diagrama,i+1);
				
				PolylineConnection coneccionpunto1 = new PolylineConnection();
				coneccionpunto1.setForegroundColor(Display.getCurrent().getSystemColor(color));
				
				coneccionpunto1.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(j)));
				coneccionpunto1.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(j+1)));
				conexion.addElement(coneccionpunto1);
				
				PolylineConnection coneccionpunto2 = new PolylineConnection();
				coneccionpunto2.setForegroundColor(Display.getCurrent().getSystemColor(color));
				
				coneccionpunto2.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(j+1)));
				coneccionpunto2.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(j+2)));
				conexion.addElement(coneccionpunto2);
				
				PolylineConnection coneccionpunto3 = new PolylineConnection();
				coneccionpunto3.setForegroundColor(Display.getCurrent().getSystemColor(color));
				
				coneccionpunto3.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(j+2)));
				coneccionpunto3.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				conexion.addElement(coneccionpunto3);
				
				PolylineConnection coneccionpunto4 = new PolylineConnection();
				coneccionpunto4.setForegroundColor(Display.getCurrent().getSystemColor(color));
				PolygonDecoration decoration2 = new PolygonDecoration();
				PointList decorationPointList2 = new PointList();
				decorationPointList2.addPoint(0,0);
				decorationPointList2.addPoint(-2,2);
				decorationPointList2.addPoint(-2,-2);
				decoration2.setTemplate(decorationPointList2);
				coneccionpunto3.setTargetDecoration(decoration2);
				coneccionpunto4.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(j+3)));
				coneccionpunto4.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				
				ConnectionEndpointLocator localizacion2 = new ConnectionEndpointLocator(coneccionpunto4, true);
				localizacion2.setVDistance(-5);
				localizacion2.setUDistance(25);
				Label mensaje2 = new Label("No");
				coneccionpunto4.add(mensaje2, localizacion2);
				
				conexion.addElement(coneccionpunto4);
				
				PolylineConnection coneccionpunto5 = new PolylineConnection();
				coneccionpunto5.setForegroundColor(Display.getCurrent().getSystemColor(color));
				
				coneccionpunto5.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(j+3)));
				coneccionpunto5.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(j+4)));
				conexion.addElement(coneccionpunto5);
				
				PolylineConnection coneccionpunto6 = new PolylineConnection();
				coneccionpunto6.setForegroundColor(Display.getCurrent().getSystemColor(color));
				
				coneccionpunto6.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(j+4)));
				coneccionpunto6.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(j+5)));
				conexion.addElement(coneccionpunto6);
				
				i=j+4;	
			}
			else{
				PolygonDecoration decoration = new PolygonDecoration();
				PointList decorationPointList = new PointList();
				decorationPointList.addPoint(0,0);
				decorationPointList.addPoint(-2,2);
				decorationPointList.addPoint(-2,-2);
				decoration.setTemplate(decorationPointList);
				coneccion.setTargetDecoration(decoration);
				coneccion.setForegroundColor(Display.getCurrent().getSystemColor(color));
				coneccion.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				conexion.addElement(coneccion);
			}
		}
		
	}
	/**
	 * Este metodo establece las conexiones en los ciclos
	 * y te devuelve la localizacion del final de ciclo.
	 * @param diagrama
	 * @param i
	 * @return int
	 */
	public int conectarCiclo(Vector<Figura> diagrama,int i){
		int pda,pia,j;
		while(true){
			PolylineConnection conector = new PolylineConnection();
			conector.setForegroundColor(Display.getCurrent().getSystemColor(color));
			conector.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(i)));
			if(diagrama.elementAt(i) instanceof ForFigure || diagrama.elementAt(i) instanceof WhileFigure){
				ConnectionEndpointLocator localizacion2 = new ConnectionEndpointLocator(conector, true);
				localizacion2.setVDistance(10);
				localizacion2.setUDistance(30);
				Label mensaje2 = new Label("Si");
				conector.add(mensaje2, localizacion2);
			}
			if(diagrama.elementAt(i+1) instanceof DecisionFigure){
				PolygonDecoration decoration = new PolygonDecoration();
				PointList decorationPointList = new PointList();
				decorationPointList.addPoint(0,0);
				decorationPointList.addPoint(-2,2);
				decorationPointList.addPoint(-2,-2);
				decoration.setTemplate(decorationPointList);
				conector.setTargetDecoration(decoration);
				PolylineConnection coneccionpunto1 = new PolylineConnection();
				coneccionpunto1.setForegroundColor(Display.getCurrent().getSystemColor(color));
				PolylineConnection coneccionpunto2 = new PolylineConnection();
				coneccionpunto2.setForegroundColor(Display.getCurrent().getSystemColor(color));
					
				conector.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				conexion.addElement(conector);

				//derecha de arriba 
				PolylineConnection coneccion1 = new PolylineConnection();
				coneccion1 .setForegroundColor(Display.getCurrent().getSystemColor(color));
				coneccion1.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				coneccion1.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+2)));
				conexion.addElement(coneccion1);
				ConnectionEndpointLocator localizacion = new ConnectionEndpointLocator(coneccion1, false);
				localizacion.setVDistance(-10);
				localizacion.setUDistance(20);
				Label mensaje = new Label("Si");
				coneccion1.add(mensaje, localizacion);
				pda = conectarCiclo(diagrama,i+2); 
				
				//dereche de abajo 
				coneccionpunto1.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(pda)));

				//izquierda arriba	
				PolylineConnection coneccion2 = new PolylineConnection();
				coneccion2.setForegroundColor(Display.getCurrent().getSystemColor(color));
				coneccion2.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				coneccion2.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(pda+1)));
				conexion.addElement(coneccion2);
				ConnectionEndpointLocator localizacion2 = new ConnectionEndpointLocator(coneccion2, false);
				localizacion2.setVDistance(-10);
				localizacion2.setUDistance(20);
				Label mensaje2 = new Label("No");
				coneccion2.add(mensaje2, localizacion2);
				
				pia = conectarCiclo(diagrama,pda+1); 
   
				//izquierda abajo	
				i=pia;
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
				coneccionpunto2.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(pia)));
				coneccionpunto1.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				coneccionpunto2.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				conexion.addElement(coneccionpunto1);
				conexion.addElement(coneccionpunto2);
			}
			else if(diagrama.elementAt(i+1) instanceof ForFigure || diagrama.elementAt(i+1) instanceof WhileFigure){
				PolygonDecoration decoration = new PolygonDecoration();
				PointList decorationPointList = new PointList();
				decorationPointList.addPoint(0,0);
				decorationPointList.addPoint(-2,2);
				decorationPointList.addPoint(-2,-2);
				decoration.setTemplate(decorationPointList);
				conector.setTargetDecoration(decoration);
				conector.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				conexion.addElement(conector);
				
				j=conectarCiclo(diagrama,i+1);
				
				PolylineConnection coneccionpunto1 = new PolylineConnection();
				 coneccionpunto1 .setForegroundColor(Display.getCurrent().getSystemColor(color));
				
				coneccionpunto1.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(j)));
				coneccionpunto1.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(j+1)));
				conexion.addElement(coneccionpunto1);
				
				PolylineConnection coneccionpunto2 = new PolylineConnection();
				 coneccionpunto2 .setForegroundColor(Display.getCurrent().getSystemColor(color));
				
				coneccionpunto2.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(j+1)));
				coneccionpunto2.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(j+2)));
				conexion.addElement(coneccionpunto2);
				
				PolylineConnection coneccionpunto3 = new PolylineConnection();
				coneccionpunto3 .setForegroundColor(Display.getCurrent().getSystemColor(color));
				PolygonDecoration decoration2 = new PolygonDecoration();
				PointList decorationPointList2 = new PointList();
				decorationPointList2.addPoint(0,0);
				decorationPointList2.addPoint(-2,2);
				decorationPointList2.addPoint(-2,-2);
				decoration2.setTemplate(decorationPointList2);
				coneccionpunto3.setTargetDecoration(decoration2);
				
				coneccionpunto3.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(j+2)));
				coneccionpunto3.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				conexion.addElement(coneccionpunto3);
				
				PolylineConnection coneccionpunto4 = new PolylineConnection();
				 coneccionpunto4 .setForegroundColor(Display.getCurrent().getSystemColor(color));
				
				coneccionpunto4.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(j+3)));
				coneccionpunto4.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				ConnectionEndpointLocator localizacion2 = new ConnectionEndpointLocator(coneccionpunto4, true);
				localizacion2.setVDistance(-5);
				localizacion2.setUDistance(25);
				Label mensaje2 = new Label("No");
				coneccionpunto4.add(mensaje2, localizacion2);
				conexion.addElement(coneccionpunto4);
				
				PolylineConnection coneccionpunto5 = new PolylineConnection();
				 coneccionpunto5 .setForegroundColor(Display.getCurrent().getSystemColor(color));
				
				coneccionpunto5.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(j+3)));
				coneccionpunto5.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(j+4)));
				conexion.addElement(coneccionpunto5);
				
				PolylineConnection coneccionpunto6 = new PolylineConnection();
				 coneccionpunto6.setForegroundColor(Display.getCurrent().getSystemColor(color));
				
				coneccionpunto6.setSourceAnchor(new ChopboxAnchor(diagrama.elementAt(j+4)));
				coneccionpunto6.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(j+5)));
				conexion.addElement(coneccionpunto6);
				
				i=j+4;	
			}
			else if(diagrama.elementAt(i+1) instanceof Elipse){
				conector.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				conexion.addElement(conector);
				return i+1;
			}
			else{
				PolygonDecoration decoration = new PolygonDecoration();
				PointList decorationPointList = new PointList();
				decorationPointList.addPoint(0,0);
				decorationPointList.addPoint(-2,2);
				decorationPointList.addPoint(-2,-2);
				decoration.setTemplate(decorationPointList);
				conector.setTargetDecoration(decoration);
				conector.setTargetAnchor(new ChopboxAnchor(diagrama.elementAt(i+1)));
				conexion.addElement(conector);
			}
			i++;
		 }
	}
	public Vector<PolylineConnection> getConexion() {
		return conexion;
	}	 	
}