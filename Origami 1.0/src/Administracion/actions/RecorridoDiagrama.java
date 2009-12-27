package Administracion.actions;

import java.util.Vector;

import Administracion.Figura;
import Grafico.Figuras.Elipse;


public class RecorridoDiagrama {
    
    /**
     * Este metodo recibe un if y devuelve la
     * localizacion de la parte no del if.
     * @param diagrama
     * @param i
     * @return int
     */
    public static int recorridoCiclo(Vector<Figura> diagrama,int i){
    	int x=diagrama.elementAt(i+1).getBounds().x-(diagrama.elementAt(i).getBounds().x+diagrama.elementAt(i).getBounds().width);
    	x=diagrama.elementAt(i).getBounds().x-x;
    	int y=diagrama.elementAt(i).getBounds().y+diagrama.elementAt(i).getBounds().height/2;
    	i++;
    	while(true){
    	    if(diagrama.elementAt(i) instanceof Elipse && diagrama.elementAt(i).getBounds().x==x && diagrama.elementAt(i).getBounds().y==y){
    		break;
    	    }
    	    i++;
    	}
    	return i;
    }
    /**
     * Este metodo recibe la localizacion del principio de la parte No del if
     * y te devuelve la localizacion del final de dicha parte.
     * @param diagrama
     * @param i
     * @return int
     */
    public static int recorridoCiclo2(Vector<Figura> diagrama,int i){
    	int x=diagrama.elementAt(i).getBounds().x;
    	int y=diagrama.elementAt(i-1).getBounds().y;
    	i++;
    	while(true){
    	    if(diagrama.elementAt(i) instanceof Elipse && diagrama.elementAt(i).getBounds().x==x && diagrama.elementAt(i).getBounds().y==y){
    		break;
    	    }
    	    i++;
    	}
    	return i;
    }
    /**
     * Este metodo recibe un For o While
     * y te devuelve la localizacion del final de dicha figura.
     * @param diagrama
     * @param i
     * @return int
     */
    public static int recorridoCiclo3(Vector<Figura> diagrama,int i){
    	int x=diagrama.elementAt(i).getBounds().x + diagrama.elementAt(i).getBounds().width/2;
    	int y=diagrama.elementAt(i).getBounds().y + diagrama.elementAt(i).getBounds().height/2;;
    	i++;
    	while(true){
    	    if(diagrama.elementAt(i) instanceof Elipse && diagrama.elementAt(i).getBounds().x==x
    		    && diagrama.elementAt(i+1) instanceof Elipse && diagrama.elementAt(i+2) instanceof Elipse
    		    && diagrama.elementAt(i+1).getBounds().y == diagrama.elementAt(i).getBounds().y
    		    && diagrama.elementAt(i+2).getBounds().y == y ){
    		break;
    	    }
    	    i++;
    	}
   	return i;
    }
}
