package origami.administration;

import java.util.Vector;

import origami.graphics.figures.DecisionFigure;
import origami.graphics.figures.DecisionFigureEnd;
import origami.graphics.figures.EllipseFigure;
import origami.graphics.figures.ForFigure;
import origami.graphics.figures.WhileFigure;


/**
 * Esta clase administra las figuras del diagrama.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez, hudy y rodrigo estuvo aqui chuy
 */
public class AdminDiagrama {
	public Vector<Figura> diagrama = new Vector<Figura>();
	public AdminSeleccion selec;
	
	public AdminDiagrama(AdminSeleccion seleccion){
		selec = seleccion;
	}
	/**
	 * Este metodo ordena las figuras, y las coloca en la 
	 * posicion en las que deben de estar en el diagrama.
	 * @param i
	 * @param figura
	 */
	public void ordenar(int i,Figura figura){
		
		Vector<Figura> temporal = new Vector<Figura>();
		int x = 0;
		for(int u =0; u<diagrama.size(); u++){
			temporal.add(u,diagrama.elementAt(u));
		}
		diagrama.removeAllElements();
		while(x!=i+1){
			diagrama.add(x,temporal.elementAt(x));
			x++;
		}
		diagrama.add(x,figura);
		selec.setFiguraSeleccionada(x);
		x++;
		if(diagrama.elementAt(x-1) instanceof DecisionFigure){
			EllipseFigure punto1= new EllipseFigure();
			EllipseFigure punto2= new EllipseFigure();
			EllipseFigure punto3= new EllipseFigure();
			EllipseFigure punto4= new EllipseFigure();
			diagrama.add(x,punto1);
			x++;
			diagrama.add(x,punto2);
			x++;  
			diagrama.add(x,punto3);
			x++;
			diagrama.add(x,punto4);
			x++;
			diagrama.add(x,new DecisionFigureEnd());
			x++;
			for(int u=x;u<temporal.size()+6;u++){
				diagrama.add(u,temporal.elementAt(u-6));
			}
		}
		else if(diagrama.elementAt(x-1) instanceof ForFigure){	
			EllipseFigure punto1= new EllipseFigure();
			EllipseFigure punto2= new EllipseFigure();
			EllipseFigure punto3= new EllipseFigure();
			EllipseFigure punto4= new EllipseFigure();
			EllipseFigure punto5= new EllipseFigure();
			EllipseFigure punto6= new EllipseFigure();
			diagrama.add(x,punto1);
			x++;
			diagrama.add(x,punto2);
			x++;  
			diagrama.add(x,punto3);
			x++;
			diagrama.add(x,punto4);
			x++;
			diagrama.add(x,punto5);
			x++;
			diagrama.add(x,punto6);
			x++;
			for(int u=x;u<temporal.size()+7;u++){
				diagrama.add(u,temporal.elementAt(u-7));
			}
		}
		else if(diagrama.elementAt(x-1) instanceof WhileFigure){
			EllipseFigure punto1= new EllipseFigure();
			EllipseFigure punto2= new EllipseFigure();
			EllipseFigure punto3= new EllipseFigure();
			EllipseFigure punto4= new EllipseFigure();
			EllipseFigure punto5= new EllipseFigure();
			EllipseFigure punto6= new EllipseFigure();
			diagrama.add(x,punto1);
			x++;
			diagrama.add(x,punto2);
			x++;  
			diagrama.add(x,punto3);
			x++;
			diagrama.add(x,punto4);
			x++;
			diagrama.add(x,punto5);
			x++;
			diagrama.add(x,punto6);
			x++;
			for(int u=x;u<temporal.size()+7;u++){
				diagrama.add(u,temporal.elementAt(u-7));
			}
		}
		else{
			for(int u=x;u<temporal.size()+1;u++){
				diagrama.add(u,temporal.elementAt(u-1));
			}
		}
		
		
	}
}
