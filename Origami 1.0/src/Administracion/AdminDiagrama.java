package Administracion;

import java.util.Vector;

import Administracion.Funcionalidad.Codigo.Instruccion;
import Grafico.Ventana;
import Grafico.Figuras.FinDelIf;
import Grafico.Figuras.For;
import Grafico.Figuras.If;
import Grafico.Figuras.While;
import Grafico.Figuras.ellipse;

/**
 * Esta clase administra las figuras del diagrama.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez, hudy estuvo aqui
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
		/*System.out.println("Se agrego...");
		System.out.println(i+1);
		System.out.println(figura);*/
		
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
		if(diagrama.elementAt(x-1) instanceof If){
			ellipse punto1= new ellipse();
			ellipse punto2= new ellipse();
			ellipse punto3= new ellipse();
			ellipse punto4= new ellipse();
			diagrama.add(x,punto1);
			x++;
			diagrama.add(x,punto2);
			x++;  
			diagrama.add(x,punto3);
			x++;
			diagrama.add(x,punto4);
			x++;
			diagrama.add(x,new FinDelIf());
			x++;
			for(int u=x;u<temporal.size()+6;u++){
				diagrama.add(u,temporal.elementAt(u-6));
			}
		}
		else if(diagrama.elementAt(x-1) instanceof For){	
			ellipse punto1= new ellipse();
			ellipse punto2= new ellipse();
			ellipse punto3= new ellipse();
			ellipse punto4= new ellipse();
			ellipse punto5= new ellipse();
			ellipse punto6= new ellipse();
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
		else if(diagrama.elementAt(x-1) instanceof While){
			ellipse punto1= new ellipse();
			ellipse punto2= new ellipse();
			ellipse punto3= new ellipse();
			ellipse punto4= new ellipse();
			ellipse punto5= new ellipse();
			ellipse punto6= new ellipse();
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
		//******************************************
		//	Seccion donde se guarda la informacion//
		//******************************************
		/*Informacion inf = new Informacion();
		
		Instruccion codigo = new Instruccion();
		codigo.main(diagrama,false);
		String[] lineas = codigo.codigoTotal.split("\n");
		inf.setInfo(lineas);
		Ventana.info.add(inf);
		System.out.println("Se agrego...");
		for(int j=0; j<lineas.length; j++)
		System.out.println(lineas[j]);*/
		//********************************************
		
	}
}
