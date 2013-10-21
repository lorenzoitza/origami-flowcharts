package origami.administration.functionality.code.targets;

import java.util.ArrayList;
import java.util.Vector;

import origami.administration.functionality.code.AbstractInstructionFormatter;


public class BasicCodeFormatter extends AbstractInstructionFormatter {
    private Vector<String> codigoTotal = new Vector<String>();
    private Vector<String> temporal=new Vector<String>();
    private ArrayList<String> pila = new ArrayList<String>();
    
    public BasicCodeFormatter(Vector<String> codeOfFigure,
	    Vector<String> TableOfVariable) {
	super();
	setCodeOfFigure(codeOfFigure);
	setTableOfVariable(TableOfVariable);
    }

    
    @Override
    public void applyFormat() {
	generarCodigoBasic();
    }

	/**
	 * Metodo para traducir el codigo origami y traducirlos  a codigo Basic
	 */
	private void generarCodigoBasic(){
		codigoTotal.add("CLS");
		declararVariables();
		leerInstrucciones();
		codigoTotal.add("END");
		filtroFinal();
		pasarString();
		
	}
	
	private void pasarString() {
	    for(String current: codigoTotal){
		sourceCode=sourceCode+"\n"+current;
	    }
	}


	/**
	 * Metodo para traducir la declaracion de las variables del codigo origami a codigo Basic
	 */
	private void declararVariables() {
		String lineaVar="";
		for(int i=0;i<TableOfVariable.size();i++){
			lineaVar=TableOfVariable.elementAt(i);
			if(lineaVar.indexOf("int")>=0){
				//codigoTotal.add("DIM "+lineaVar.charAt(4)+" AS INTEGER");
				codigoTotal.add(lineaVar.charAt(lineaVar.indexOf("int")+4)+"%=0");
				TableOfVariable.setElementAt(lineaVar.charAt(4)+"%", i);
			}else if(lineaVar.indexOf("float")>=0){
				//codigoTotal.add("DIM "+lineaVar.charAt(6)+" AS SINGLE");
				codigoTotal.add(lineaVar.charAt(lineaVar.indexOf("float")+6)+"!=0.0");
				TableOfVariable.setElementAt(lineaVar.charAt(6)+"!", i);
			}else if(lineaVar.indexOf("double")>=0){
				//codigoTotal.add("DIM "+lineaVar.charAt(7)+" AS DOUBLE");
				codigoTotal.add(lineaVar.charAt(lineaVar.indexOf("double")+7)+"#=0.0");
				TableOfVariable.setElementAt(lineaVar.charAt(7)+"#", i);
			}else if(lineaVar.indexOf("char")>=0){
				//codigoTotal.add("DIM "+lineaVar.charAt(5)+" AS STRING");
				codigoTotal.add(lineaVar.charAt(lineaVar.indexOf("char")+5)+"$=\"0\"");
				TableOfVariable.setElementAt(lineaVar.charAt(5)+"$", i);
			}
		}
	}

	/**
	 * Metodo para traducir una linea de codigo origami a codigo Basic
	 */
public void leerInstrucciones(){
	String lineaInstruccion="";
	String aux;
	for(int i=0;i<codeOfFigure.size();i++){
		lineaInstruccion=codeOfFigure.elementAt(i);
		/**
		 * Seccion para traducir un renglon que contenga el codigo origami "Leer:" a codigo "INPUT "
		 */
		if(lineaInstruccion.indexOf("Leer:")>=0){
					if(lineaInstruccion.indexOf("int")>=0){
						aux=lineaInstruccion.charAt(lineaInstruccion.indexOf("int")+4)+"%";
					}else if(lineaInstruccion.indexOf("float")>=0){
						aux=lineaInstruccion.charAt(lineaInstruccion.indexOf("float")+6)+"!";
					}else if(lineaInstruccion.indexOf("double")>=0){
						aux=lineaInstruccion.charAt(lineaInstruccion.indexOf("double")+7)+"#";
					}else if(lineaInstruccion.indexOf("char")>=0){
						aux=lineaInstruccion.charAt(lineaInstruccion.indexOf("char")+5)+"$";
					}else{
						aux=lineaInstruccion.charAt(lineaInstruccion.indexOf("Leer:")+6)+"";
					}
					codigoTotal.add("INPUT "+aux);
					continue;
		}
		/**
		 * Seccion para traducir un renglon que contenga el codigo origami "\p" a codigo "PRINT "
		 */
		if(lineaInstruccion.indexOf("\\p")>=0){
			if(lineaInstruccion.indexOf("\"")>=0 && lineaInstruccion.indexOf(",")>=0){
				aux=lineaInstruccion.substring(lineaInstruccion.indexOf("\\p")+2);
				aux=aux.replaceFirst(",", ";");
				aux=aux.substring(0, aux.length()-1);
				codigoTotal.add("PRINT "+aux);
			}
			else if(lineaInstruccion.indexOf("\"")>=0){
				aux=lineaInstruccion.substring(lineaInstruccion.indexOf("\\p")+2);
				aux=aux.substring(0, aux.length()-1);
				codigoTotal.add("PRINT "+aux);
			}else{
				aux=lineaInstruccion.charAt(2)+"";
				codigoTotal.add("PRINT "+aux);
				}
			continue;
			}
		/**
		 * Seccion para traducir un renglon que contenga el codigo origami:
		 * if(expresion_logica){
		 * 		acciones_por_verdadero
		 * }
		 *  else{
		 * 		acciones_por_verdadero
		 * }
		 * a codigo Basic:
		 * IF expresion_logica THEN
		 * 		acciones_por_verdadero
		 * ELSE
		 * 		acciones_por_verdadero
		 * END IF
		 */
		
	   if(lineaInstruccion.indexOf("if")>=0){
			pila.add("END IF");
			aux=lineaInstruccion.substring(lineaInstruccion.indexOf("if")+3, lineaInstruccion.length()-2);
			if(aux.indexOf("!=")>=0){
				aux=aux.replaceAll("!=", "<>");
			}if(aux.indexOf("==")>=0){
				aux=aux.replaceAll("==", "=");
			}
			codigoTotal.add("IF "+aux+" THEN");
			continue;
		}
	    
	   if(lineaInstruccion.indexOf("else")>=0){
			codigoTotal.add("ELSE");
			pila.add("END IF");
			continue;
		}
		/**
		 * Seccion para traducir un renglon que contenga el codigo origami:
		 * for(variable_numerica = valor_inicial;valor_final;paso){
		 * 		secuencia_de_acciones
		 * }
		 * a codigo Basic:
		 * FOR variable_numerica=valor_inicial TO valor_final STEP paso  
		 * 		secuencia_de_acciones
		 * NEXT
		 */
	   if(lineaInstruccion.indexOf("for")>=0){
		   pila.add("NEXT");
		   aux="";
		   lineaInstruccion=lineaInstruccion.replace("for", "FOR ");
		   if(lineaInstruccion.indexOf("int")>=0||lineaInstruccion.indexOf("double")>=0||lineaInstruccion.indexOf("float")>=0){
			   lineaInstruccion=lineaInstruccion.replace("int", "");
			   lineaInstruccion=lineaInstruccion.replace("float", "");
			   lineaInstruccion=lineaInstruccion.replace("double", "");
		   }
		   lineaInstruccion=lineaInstruccion.replace("{", "");
		   lineaInstruccion=lineaInstruccion.replace("(", "");
		   lineaInstruccion=lineaInstruccion.replace(")", "");
		   lineaInstruccion=lineaInstruccion.replaceFirst(";", " TO ");
		   lineaInstruccion=lineaInstruccion.replace(";", " STEP ");
		   if(lineaInstruccion.indexOf("<=")>=0){
			   aux=lineaInstruccion.substring(lineaInstruccion.indexOf("<=")+2, lineaInstruccion.indexOf(" STEP"));
		   }else if(lineaInstruccion.indexOf("<")>=0){
			   String num=lineaInstruccion.substring(lineaInstruccion.indexOf("<")+1, lineaInstruccion.indexOf(" STEP"));
			   int n=Integer.parseInt(num)-1;
			   aux=String.valueOf(n);
		   }
		   String temp=lineaInstruccion.substring(0, lineaInstruccion.indexOf("TO")+3)+aux;
		   lineaInstruccion=temp+lineaInstruccion.substring(lineaInstruccion.indexOf(" STEP"));
		   aux="0";
		   temp=null;
		   String temp2;
		   temp2= lineaInstruccion.substring(0,lineaInstruccion.indexOf("STEP")+5);
		   lineaInstruccion=lineaInstruccion.replace("++", "1");
		   lineaInstruccion=lineaInstruccion.replace("--", "-1");
		   lineaInstruccion=temp2+lineaInstruccion.substring(lineaInstruccion.indexOf("1"));
		   if(lineaInstruccion.indexOf("+")>=0){
			    aux=lineaInstruccion.substring(lineaInstruccion.indexOf("+")+1);
			    temp=lineaInstruccion.substring(0,lineaInstruccion.indexOf(" STEP "));
				   lineaInstruccion=temp+lineaInstruccion.substring(lineaInstruccion.indexOf(" STEP "))+aux;
		   }else if(lineaInstruccion.indexOf("-")>=0){
			    aux=lineaInstruccion.substring(lineaInstruccion.indexOf("-")+1);
			    temp=lineaInstruccion.substring(0,lineaInstruccion.indexOf(" STEP "))+6;
				lineaInstruccion=temp+lineaInstruccion.substring(lineaInstruccion.indexOf(" STEP "))+aux;
		   }
		   codigoTotal.add(lineaInstruccion);
		   continue;
		}
	  /**
		 * Seccion para traducir un renglon que contenga el codigo origami:
		 * while(expresion_logica){
		 * 		secuencia_de_acciones
		 * }
		 * a Pseudocodigo:
		 * DO WHILE expresion_logica Hacer 
		 * 		secuencia_de_acciones
		 * LOOP
		 */
		if(lineaInstruccion.indexOf("while")>=0){
			pila.add("LOOP");
			lineaInstruccion=lineaInstruccion.replace("{", "");
			lineaInstruccion=lineaInstruccion.replace("(", "");
			lineaInstruccion=lineaInstruccion.replace(")", "");
			aux=lineaInstruccion.substring(lineaInstruccion.indexOf("while")+5, lineaInstruccion.length());
			codigoTotal.add("DO WHILE "+aux);
			continue;
			}
		
	   /**
		 * Seccion para traducir las "}" del codigo origami a sus repectivos "END IF", "FinMientras" y "FinPara" en Pseudocodigo
		 */
		if(lineaInstruccion.indexOf("}") >=0){
					if(!pila.isEmpty()){
						codigoTotal.add(pila.get(pila.size()-1));
						pila.remove(pila.size()-1);
					}
					continue;
				}	


/**
 * Seccion para traducir un renglon que contenga el codigo origami "X += Y;" a Pseudocodigo "X = X + Y;"
 */
if(lineaInstruccion.indexOf("+=")>=0){
	aux=lineaInstruccion.substring(0, lineaInstruccion.indexOf("+="));
	lineaInstruccion=lineaInstruccion.replace("+=", "=");
	lineaInstruccion=lineaInstruccion.replace(";", "");
	lineaInstruccion=lineaInstruccion+"+"+aux;
	codigoTotal.add(lineaInstruccion);
	continue;
	}

/**
 * Seccion para traducir un renglon que contenga el codigo origami "X -= Y;" a Pseudocodigo "X = X - Y;"
 */
if(lineaInstruccion.indexOf("-=")>=0){
	aux=lineaInstruccion.substring(0, lineaInstruccion.indexOf("-="));
	lineaInstruccion=lineaInstruccion.replace(";", "");
	String temp=aux+"-"+lineaInstruccion.substring(lineaInstruccion.indexOf("-=")+2);
	lineaInstruccion=aux+"="+temp;
	codigoTotal.add(lineaInstruccion);
	continue;
	}

/**
 * Seccion para traducir un renglon que contenga el codigo origami "X *= Y;" a Pseudocodigo "X = X * Y;"
 */
if(lineaInstruccion.indexOf("*=")>=0){
	aux=lineaInstruccion.substring(0, lineaInstruccion.indexOf("*="));
	lineaInstruccion=lineaInstruccion.replace("*=", "=");
	lineaInstruccion=lineaInstruccion.replace(";", "");
	lineaInstruccion=lineaInstruccion+"*"+aux;
	codigoTotal.add(lineaInstruccion);
	continue;
	}

/**
 * Seccion para traducir un renglon que contenga el codigo origami "X /= Y;" a Pseudocodigo "X = X/Y;"
 */
if(lineaInstruccion.indexOf("/=")>=0){
	aux=lineaInstruccion.substring(0, lineaInstruccion.indexOf("/="));
	lineaInstruccion=lineaInstruccion.replace(";", "");
	String temp=aux+"/"+lineaInstruccion.substring(lineaInstruccion.indexOf("/=")+2);
	lineaInstruccion=aux+"="+temp;
	codigoTotal.add(lineaInstruccion);
	continue;
	}
/**
 * Seccion para traducir un renglon que contenga el codigo origami "X = Y;" a Pseudocodigo "X = Y"
 */
if(lineaInstruccion.indexOf("=")>=0){
	lineaInstruccion=lineaInstruccion.replace(";", "");
	codigoTotal.add(lineaInstruccion);
	continue;
	}
/**
 * Seccion para traducir un renglon que contenga el codigo origami "X++;" a Pseudocodigo "X =X + 1;"
 */
if(lineaInstruccion.indexOf("++")>=0){
	aux=lineaInstruccion.substring(0, lineaInstruccion.indexOf("++"));
	codigoTotal.add(aux+"="+aux+"+1");
	continue;
}
/**
 * Seccion para traducir un renglon que contenga el codigo origami "X--;" a Pseudocodigo "X =X - 1;"
 */
if(lineaInstruccion.indexOf("--")>=0){
	aux=lineaInstruccion.substring(0, lineaInstruccion.indexOf("--"));
	codigoTotal.add(aux+"="+aux+"-1");
	continue;
}

}
	
}
/**
 * Metodo para eliminar los "END IF" de los "IF", que contengan la condicion "ELSE"
 */
private void filtroFinal(){
		for(int i = codigoTotal.size()-1; i >= 0; i--){
			if(codigoTotal.get(i).indexOf("ELSE") >=0){
				temporal.add(codigoTotal.get(i--));
			}
			else{
				temporal.add(codigoTotal.get(i));
			}
		}
		codigoTotal = new Vector<String>();
		for(int i = temporal.size()-1; i >= 0; i--){
			codigoTotal.add(temporal.get(i));
		}
	}	
}

