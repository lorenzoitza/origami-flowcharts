package origami.administration;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import origami.graphics.figures.*;
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
	}
	public void addInformation(String information){
		info.add(information);
	}
	public void setFigura(Figura figura){
		if(figura instanceof DecisionFigure){
			info.add("La figura agregada de tipo \"si\"");
		}
		else if(figura instanceof ForFigure){
			info.add("La figura agregada de tipo \"para\"");
		}
		else if(figura instanceof WhileFigure){
			info.add("La figura agregada de tipo \"mientras\"");
		}
		else if(figura instanceof InputFigure){
			info.add("La figura agregada de tipo \"entrada\"");
		}
		else if(figura instanceof OutputFigure){
			info.add("La figura agregada de tipo \"salida\"");
		}
		else if(figura instanceof SentenceFigure){
			info.add("La figura agregada de tipo \"proceso\"");
		}
	}
	public void setDiagrama(Vector<Figura> diagrama) {
		String informacion="";
		String instruccion;
		String tab="";
		for(int i=0; i<diagrama.size(); i++){
			if(diagrama.elementAt(i) instanceof DecisionFigure){
				DecisionFigure aux = (DecisionFigure)diagrama.elementAt(i);
				instruccion = aux.instructionComposed.simpleInstructionList.elementAt(0).simpleInstruction;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(3,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"If( "+instruccion+" ){";
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
				instruccion = aux.instructionComposed.simpleInstructionList.elementAt(0).simpleInstruction;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(4,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"For( "+instruccion+" ){";
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
				instruccion = aux.instructionComposed.simpleInstructionList.elementAt(0).simpleInstruction;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(6,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"while("+instruccion+" ){";
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
				instruccion = aux.instruction.simpleInstruction;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(0,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Proceso( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof InputFigure){
				InputFigure aux = (InputFigure)diagrama.elementAt(i);
				instruccion = aux.instruction.simpleInstruction;
				if(instruccion.compareTo("null")!=0 && !instruccion.isEmpty()){
					instruccion = instruccion.substring(0,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Entrada( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof OutputFigure){
				OutputFigure aux = (OutputFigure)diagrama.elementAt(i);
				instruccion = aux.instruction.simpleInstruction;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(2,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Salida( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof EllipseFigure){
				if(diagrama.elementAt(i+1) instanceof DecisionFigureEnd){
					if(tab.startsWith("\t")){
						tab = tab.substring(tab.indexOf("\t")+1);
					}
					informacion = informacion +"\n"+tab+"}";
					i++;
					tab = "";
				}
			}
			else if(diagrama.elementAt(i) instanceof CircleFigure){
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
				instruccion = aux.instructionComposed.simpleInstructionList.elementAt(0).simpleInstruction;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(3,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"If( "+instruccion+" ){";
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
				instruccion = aux.instructionComposed.simpleInstructionList.elementAt(0).simpleInstruction;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(4,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"For( "+instruccion+" ){";
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
				instruccion = aux.instructionComposed.simpleInstructionList.elementAt(0).simpleInstruction;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(6,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"while("+instruccion+" ){";
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
				instruccion = aux.instruction.simpleInstruction;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(0,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Proceso( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof InputFigure){
				InputFigure aux = (InputFigure)diagrama.elementAt(i);
				instruccion = aux.instruction.simpleInstruction;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(0,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Entrada( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof OutputFigure){
				OutputFigure aux = (OutputFigure)diagrama.elementAt(i);
				instruccion = aux.instruction.simpleInstruction;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(2,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Salida( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof EllipseFigure){
				if(diagrama.elementAt(i+1) instanceof DecisionFigureEnd){
					
					if(tab.startsWith("\t")){
						tab = tab.substring(tab.indexOf("\t")+1);
					}
					informacion = informacion +"\n"+tab+"}";
					i++;
					
					
				}
				else{
					contador++;
					if(contador==6){
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
				instruccion = aux.instructionComposed.simpleInstructionList.elementAt(0).simpleInstruction;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(3,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"If( "+instruccion+" ){";
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
				instruccion = aux.instructionComposed.simpleInstructionList.elementAt(0).simpleInstruction;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(4,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"For( "+instruccion+" ){";
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
				instruccion = aux.instructionComposed.simpleInstructionList.elementAt(0).simpleInstruction;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(6,instruccion.length()-2);
				}
				informacion = informacion+"\n"+tab+"while("+instruccion+" ){";
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
				instruccion = aux.instruction.simpleInstruction;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(0,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Proceso( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof InputFigure){
				InputFigure aux = (InputFigure)diagrama.elementAt(i);
				instruccion = aux.instruction.simpleInstruction;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(0,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Entrada( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof OutputFigure){
				OutputFigure aux = (OutputFigure)diagrama.elementAt(i);
				instruccion = aux.instruction.simpleInstruction;
				if(instruccion.compareTo("null")!=0){
					instruccion = instruccion.substring(2,instruccion.length()-1);
				}
				informacion = informacion+"\n"+tab+"Salida( "+instruccion+" )";
			}
			else if(diagrama.elementAt(i) instanceof EllipseFigure){
				if(diagrama.elementAt(i+1) instanceof EllipseFigure){
					i++;
					this.aux=i;
					i= diagrama.size()+1;
					break;
				}
				else if(diagrama.elementAt(i+1) instanceof DecisionFigureEnd){
					if(tab.startsWith("\t")){
						tab = tab.substring(tab.indexOf("\t")+1);
					}
					informacion = informacion +"\n"+tab+"}";
					i++;
					
					
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

		String time = "Tiempo total = "+tiempoI+"-"+tiempoF;
		info.add(time);
	}
	public void removeTime(){
		info.removeElementAt(info.size()-1);
	}
	
}
