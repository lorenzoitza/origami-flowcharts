package Administracion;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;
import Grafico.Figuras.*;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Informacion implements Serializable{
	private Vector<String> info;
	private int aux=0,hora,minuto,segundo;
	private String tiempoAnterior,tiempoI,tiempoF;
	private Date date;
	
	public Informacion(){
		info = new Vector<String>();
		date = new Date();
		hora = date.getHours();
		minuto = date.getMinutes();
		segundo = date.getSeconds();
		tiempoI = String.valueOf(hora)+":"+String.valueOf(minuto)+":"+String.valueOf(segundo);
	}
	public void upDateTime(){
		date = new Date();
		hora = date.getHours();
		minuto = date.getMinutes();
		segundo = date.getSeconds();
		info.add("/O - Se abrio el archivo....\n");
		tiempoI = String.valueOf(hora)+":"+String.valueOf(minuto)+":"+String.valueOf(segundo);
	}
	public Vector<String> getInfo() {
		return info;
	}
	public void setInfo(Vector<String> info) {
		this.info = info;
	}
	public void setInformacion(String informacion) {	
		info.add(informacion);
		//System.out.println(informacion);
	}
	public void addInformation(String information){
		info.add(information);
	}
	public void setFigura(Figura figura){
		if(figura instanceof DecisionFigure){
			info.add("La figura agregada de tipo \"si\"");
			//System.out.println("La figura agregada de tipo \"si\"");
		}
		else if(figura instanceof ForFigure){
			info.add("La figura agregada de tipo \"para\"");
			//System.out.println("La figura agregada de tipo \"para\"");
		}
		else if(figura instanceof WhileFigure){
			info.add("La figura agregada de tipo \"mientras\"");
			//System.out.println("La figura agregada de tipo \"mientras\"");
		}
		else if(figura instanceof InputFigure){
			info.add("La figura agregada de tipo \"entrada\"");
			//System.out.println("La figura agregada de tipo \"entrada\"");
		}
		else if(figura instanceof OutputFigure){
			info.add("La figura agregada de tipo \"salida\"");
			//System.out.println("La figura agregada de tipo \"salida\"");
		}
		else if(figura instanceof SentenceFigure){
			info.add("La figura agregada de tipo \"proceso\"");
			//System.out.println("La figura agregada de tipo \"proceso\"");
		}
	}
	public void setDiagrama(Vector<Figura> diagrama) {
		String informacion="";
		String instruccion;
		String tab="";
		for(int i=0; i<diagrama.size(); i++){
			if(diagrama.elementAt(i) instanceof DecisionFigure){
				DecisionFigure aux = (DecisionFigure)diagrama.elementAt(i);
				instruccion = aux.instruction.instruccion.elementAt(0).instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(3,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"If( "+instruccion+" ){";
				//llamar a otra funcion que coloque lo de en medio con un tab..
				//el del if solo va a trabajar la parte derecha y me regresa el siguiente punto de la izquierda
				tab=tab+"	";
				informacion = anidarIf(informacion,tab,diagrama,i+2);
				//.............................................................
				if(tab.startsWith("\t")){
					tab = tab.substring(tab.indexOf("\t")+1);
				}
				i= this.aux;
				informacion = informacion +"\n"+tab+"}\n"+tab+"Else{";
				tab=tab+"	";
			}
			else if(diagrama.elementAt(i) instanceof ForFigure){
				ForFigure aux = (ForFigure)diagrama.elementAt(i);
				instruccion = aux.instruccion.instruccion.elementAt(0).instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(4,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"For( "+instruccion+" ){";
				//llamar a otra funcion que coloque lo de en medio con un tab..
				tab=tab +"	";
				informacion = anidarCiclos(informacion,tab,diagrama,i+1);
				i= this.aux;
				//.............................................................
				if(tab.startsWith("\t")){
					tab = tab.substring(tab.indexOf("\t")+1);
				}
				informacion = informacion +"\n"+tab+"}";
				tab="";
			}
			else if(diagrama.elementAt(i) instanceof WhileFigure){
				WhileFigure aux = (WhileFigure)diagrama.elementAt(i);
				instruccion = aux.instruccion.instruccion.elementAt(0).instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(6,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"while("+instruccion+" ){";
				//llamar a otra funcion que coloque lo de en medio con un tab..
				tab=tab +"	";
				informacion = anidarCiclos(informacion,tab,diagrama,i+1);
				i= this.aux;
				//..............................................................
				if(tab.startsWith("\t")){
					tab = tab.substring(tab.indexOf("\t")+1);
				}
				informacion = informacion +"\n"+tab+"}";
				tab="";
			}
			else if(diagrama.elementAt(i) instanceof SentenceFigure){
				SentenceFigure aux = (SentenceFigure)diagrama.elementAt(i);
				instruccion = aux.instruccion.instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(0,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Proceso( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof InputFigure){
				InputFigure aux = (InputFigure)diagrama.elementAt(i);
				instruccion = aux.instruccion.instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(0,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Entrada( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof OutputFigure){
				OutputFigure aux = (OutputFigure)diagrama.elementAt(i);
				instruccion = aux.instruccion.instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(2,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Salida( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof Elipse){
				if(diagrama.elementAt(i+1) instanceof DecisionFigureEnd){
					if(tab.startsWith("\t")){
						tab = tab.substring(tab.indexOf("\t")+1);
					}
					informacion = informacion +"\n"+tab+"}";
					i++;
					tab = "";
				}
			}
			else if(diagrama.elementAt(i) instanceof TerminationFigure){
				if(i==0){
					informacion = informacion+"\n\nInicio";
				}
				else{
					informacion = informacion+"\nFin\n";
				}
				
			}
		}
		info.add(informacion);
	}
	
	private String anidarCiclos(String informacion,String tabulacion,Vector<Figura> diagrama, int posicion){
		int contador=0;
		String tab = tabulacion;
		String instruccion;
		for(int i=posicion; i<diagrama.size(); i++){
			if(diagrama.elementAt(i) instanceof DecisionFigure){
				DecisionFigure aux = (DecisionFigure)diagrama.elementAt(i);
				instruccion = aux.instruction.instruccion.elementAt(0).instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(3,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"If( "+instruccion+" ){";
				//llamar a otra funcion que coloque lo de en medio con un tab..
				//el del if solo va a trabajar la parte derecha y me regresa el siguiente punto de la izquierda
				tab=tab+"	";
				informacion = anidarIf(informacion,tab,diagrama,i+2);
				//.............................................................
				if(tab.startsWith("\t")){
					tab = tab.substring(tab.indexOf("\t")+1);
				}
				i= this.aux;
				informacion = informacion +"\n"+tab+"}\n"+tab+"Else{";
				tab=tab+"	";
			}
			else if(diagrama.elementAt(i) instanceof ForFigure){
				ForFigure aux = (ForFigure)diagrama.elementAt(i);
				instruccion = aux.instruccion.instruccion.elementAt(0).instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(4,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"For( "+instruccion+" ){";
				//llamar a otra funcion que coloque lo de en medio con un tab..
				tab=tab+"	";
				informacion = anidarCiclos(informacion,tab,diagrama,i+1);
				i= this.aux;
				//..............................................................
				if(tab.startsWith("\t")){
					tab = tab.substring(tab.indexOf("\t")+1);
				}
				informacion = informacion +"\n"+tab+"}";
			}
			else if(diagrama.elementAt(i) instanceof WhileFigure){
				WhileFigure aux = (WhileFigure)diagrama.elementAt(i);
				instruccion = aux.instruccion.instruccion.elementAt(0).instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(6,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"while("+instruccion+" ){";
				//llamar a otra funcion que coloque lo de en medio con un tab..
				tab=tab+"	";
				informacion = anidarCiclos(informacion,tab,diagrama,i+1);
				i= this.aux;
				//..............................................................
				if(tab.startsWith("\t")){
					tab = tab.substring(tab.indexOf("\t")+1);
				}
				informacion = informacion +"\n"+tab+"}";
			}
			else if(diagrama.elementAt(i) instanceof SentenceFigure){
				SentenceFigure aux = (SentenceFigure)diagrama.elementAt(i);
				instruccion = aux.instruccion.instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(0,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Proceso( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof InputFigure){
				InputFigure aux = (InputFigure)diagrama.elementAt(i);
				instruccion = aux.instruccion.instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(0,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Entrada( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof OutputFigure){
				OutputFigure aux = (OutputFigure)diagrama.elementAt(i);
				instruccion = aux.instruccion.instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(2,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Salida( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof Elipse){
				if(diagrama.elementAt(i+1) instanceof DecisionFigureEnd){
					
					if(tab.startsWith("\t")){
						tab = tab.substring(tab.indexOf("\t")+1);
					}
					informacion = informacion +"\n"+tab+"}";
					i++;
					
					//informacion = informacion+"\n"+tab+"}";
					//i++;
				}
				else{
					contador++;
					if(contador==6){
						//salir del ciclo
						this.aux=i;
						i= diagrama.size()+1;
						break;
					}
				}
			}
		}
		return informacion;
	}
	
	private String anidarIf(String informacion,String tabulacion,Vector<Figura> diagrama, int posicion){
		String tab = tabulacion;
		String instruccion;
		for(int i=posicion; i<diagrama.size(); i++){
			if(diagrama.elementAt(i) instanceof DecisionFigure){
				DecisionFigure aux = (DecisionFigure)diagrama.elementAt(i);
				instruccion = aux.instruction.instruccion.elementAt(0).instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(3,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"If( "+instruccion+" ){";
				//llamar a otra funcion que coloque lo de en medio con un tab..
				//el del if solo va a trabajar la parte derecha y me regresa el siguiente punto de la izquierda
				tab=tab+"	";
				informacion = anidarIf(informacion,tab,diagrama,i+2);
				//.............................................................
				if(tab.startsWith("\t")){
					tab = tab.substring(tab.indexOf("\t")+1);
				}
				i= this.aux;
				informacion = informacion +"\n"+tab+"}\n"+tab+"Else{";
				tab=tab+"	";
			}
			else if(diagrama.elementAt(i) instanceof ForFigure){
				ForFigure aux = (ForFigure)diagrama.elementAt(i);
				instruccion = aux.instruccion.instruccion.elementAt(0).instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(4,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"For( "+instruccion+" ){";
				//llamar a otra funcion que coloque lo de en medio con un tab..
				tab=tab+"	";
				informacion = anidarCiclos(informacion,tab,diagrama,i+1);
				i= this.aux;
				//..............................................................
				if(tab.startsWith("\t")){
					tab = tab.substring(tab.indexOf("\t")+1);
				}
				informacion = informacion +"\n"+tab+"}";
			}
			else if(diagrama.elementAt(i) instanceof WhileFigure){
				WhileFigure aux = (WhileFigure)diagrama.elementAt(i);
				instruccion = aux.instruccion.instruccion.elementAt(0).instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(6,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"while("+instruccion+" ){";
				//llamar a otra funcion que coloque lo de en medio con un tab..
				tab=tab+"	";
				informacion = anidarCiclos(informacion,tab,diagrama,i+1);
				i= this.aux;
				//..............................................................
				if(tab.startsWith("\t")){
					tab = tab.substring(tab.indexOf("\t")+1);
				}
				informacion = informacion +"\n"+tab+"}";
			}
			else if(diagrama.elementAt(i) instanceof SentenceFigure){
				SentenceFigure aux = (SentenceFigure)diagrama.elementAt(i);
				instruccion = aux.instruccion.instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(0,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Proceso( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof InputFigure){
				InputFigure aux = (InputFigure)diagrama.elementAt(i);
				instruccion = aux.instruccion.instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(0,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Entrada( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof OutputFigure){
				OutputFigure aux = (OutputFigure)diagrama.elementAt(i);
				instruccion = aux.instruccion.instruccion;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(2,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Salida( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof Elipse){
				if(diagrama.elementAt(i+1) instanceof Elipse){
					//informacion = informacion +tab+"\n}";
					i++;
					this.aux=i;
					i= diagrama.size()+1;
					break;
				}
				else if(diagrama.elementAt(i+1) instanceof DecisionFigureEnd){
					//informacion = informacion +tab+"\n}";
					if(tab.startsWith("\t")){
						tab = tab.substring(tab.indexOf("\t")+1);
					}
					informacion = informacion +"\n"+tab+"}";
					i++;
					
					//informacion = informacion+"\n"+tab+"}";
					//i++;
				}
			}
		}
		return informacion;
	}
	public void addTime(){
		date = new Date();
		int hora = date.getHours();
		int minuto = date.getMinutes();
		int segundo = date.getSeconds();
		
		tiempoF = String.valueOf(hora)+":"+String.valueOf(minuto)+":"+String.valueOf(segundo);
		/*if(hora>=this.hora){
			hora = hora-this.hora;
		}
		else{
			hora = 12-this.hora+hora;
		}
		if(minuto>=this.minuto){
			minuto = minuto-this.minuto;
		}
		else{
			minuto = 60-this.minuto+minuto;
		}
		if(segundo>=this.segundo){
			segundo = segundo-this.segundo;
		}
		else{
			segundo = 60-this.segundo+segundo;
		}
		if(tiempoAnterior!=null){
			String aux = tiempoAnterior;
			aux = aux.substring(aux.indexOf("=")+1);
			
			String tiempoH = aux.substring(1,aux.indexOf(":"));
			aux = aux.substring(aux.indexOf(":")+1);
			String tiempoM = aux.substring(0,aux.indexOf(":"));
			aux = aux.substring(aux.indexOf(":")+1);
			String tiempoS = aux.substring(0,aux.length());
			int H = Integer.valueOf(tiempoH);
			int M = Integer.valueOf(tiempoM);
			int S = Integer.valueOf(tiempoS);
			hora = hora+H;
			minuto = minuto+M;
			segundo = segundo+S;
			
			if(segundo>59){
				int min = segundo/60;
				segundo = segundo%60;
				minuto = minuto+min;
				if(minuto>59){
					int hor = minuto/60;
					minuto = minuto%60;
					hora = hora + hor;
				}
			}
			if(minuto>59){
				int hor = minuto/60;
				minuto = minuto%60;
				hora = hora + hor;
			}
		}
		String time = String.valueOf(hora)+":"+String.valueOf(minuto)+":"+String.valueOf(segundo);
		time = "Tiempo total = "+time;*/
		String time = "Tiempo total = "+tiempoI+"-"+tiempoF;
		info.add(time);
		//System.out.println(time);
	}
	public void removeTime(){
		info.removeElementAt(info.size()-1);
	}
	/*
	 * Claves para contar:
	 *  /D - Elimino								*
	 *  /A - Agrego									*
	 *  /C - compilo								*
	 *  /M - modifico o agrego una instruccion		
	 *  /Ec- Error al compilar						*
	 *  /P - paso a paso							*
	 *  /Ep- error en el paso a paso				*
	 *  /O - abrio el archivo 						*
	 */
}
