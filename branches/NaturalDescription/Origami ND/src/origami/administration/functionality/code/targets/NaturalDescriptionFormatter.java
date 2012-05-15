package origami.administration.functionality.code.targets;

import java.util.Vector;

import origami.administration.functionality.code.AbstractInstructionFormatter;
import origami.administration.functionality.code.targets.naturalDescription.DecisionParser;
import origami.administration.functionality.code.targets.naturalDescription.ExpressionParser;
import origami.administration.functionality.code.targets.naturalDescription.ForParser;
import origami.administration.functionality.code.targets.naturalDescription.InputParser;
import origami.administration.functionality.code.targets.naturalDescription.OutputParser;
import origami.administration.functionality.code.targets.naturalDescription.WhileParser;


public class NaturalDescriptionFormatter extends AbstractInstructionFormatter {
    private Vector<String> naturalDescription;
    private Vector<String> prePseudocodigo;
    private Vector<String> pila;
   
    public NaturalDescriptionFormatter(Vector<String> codeOfFigure, 
	    			       Vector<String> TableOfVariable){
	super();
	setCodeOfFigure(codeOfFigure);
	setTableOfVariable(TableOfVariable);
	naturalDescription = new Vector<String>();
	prePseudocodigo = new Vector<String>();
	pila = new Vector<String>();
    }

    @Override
    public void applyFormat() {
	// TODO Auto-generated method stub
	produceNaturalDescription();

    }

    private void produceNaturalDescription(){
	naturalDescription.add("Inicio del Algoritmo");
	    System.out.println(codeOfFigure.toString());
	for(int i = 0; i < codeOfFigure.size(); i++){
	    naturalDescription.add(translateLine(codeOfFigure.get(i)));
	
	}
	naturalDescription.add("Fin del Algoritmo");
	filtroFinal();
	vectorToString();
    }
    
    private void filtroFinal(){
	
	for(int i = naturalDescription.size()-1; i >= 0; i--){
	    if(naturalDescription.get(i).indexOf("Sino") != -1){// si hay "Sino" en la linea
		prePseudocodigo.add(naturalDescription.get(i--));
	    }
	    else{
		prePseudocodigo.add(naturalDescription.get(i));
	    }
	}
	naturalDescription = new Vector<String>();
	for(int i = prePseudocodigo.size()-1; i >= 0; i--){
	    naturalDescription.add(prePseudocodigo.get(i));
	}
    }
    
    private void vectorToString(){
	for(int i = 0; i < naturalDescription.size();i++){
	    sourceCode += naturalDescription.get(i)+"\n";
	}
    }
    
    private String translateLine(String linea){
	String newLine = "";
	/**
	 * Seccion para traducir un renglon que contenga el codigo origami "Leer:" a Pseudocodigo "Leer "
	 */
	if(linea.indexOf("Leer:") != -1 || linea.indexOf("int ") != -1 || linea.indexOf("float") != -1
		|| linea.indexOf("float") != -1 ){
	    //this returns the formating input block
	    newLine+= new InputParser(linea).getLineOutput();
	    
	    return newLine;
	}
	
	/**
	 * Seccion para traducir un renglon que contenga el codigo origami:
	 * if(expresion_logica){
	 * 		acciones_por_verdadero
	 * }
	 * a Pseudocodigo:
	 * Si expresion_logica Entonces
	 * 		acciones_por_verdadero
	 * FinSi
	 */
	if(linea.indexOf("if") != -1){
	    pila.add("FinSi");
	    newLine+= new DecisionParser(linea).getLineOutput();
	    
	    return newLine;
	}
	/**
	 * Seccion para traducir un renglon que contenga el codigo origami:
	 * else{
	 * 		acciones_por_falso
	 * }
	 * a Pseudocodigo:
	 * Sino
	 * 		acciones_por_falso
	 * FinSi
	 */
	if(linea.indexOf("else") != -1){
	    pila.add("FinSi");
	    for(int i = 0; i < linea.length(); i++){
		if(i == linea.indexOf("else")){
		    newLine += "SINO";
		    i += 3;
		}
		else if(linea.charAt(i) == '{'){
		    newLine += "";
		}
		else{
		    newLine += linea.charAt(i);
		}
	    }
	    return newLine;
	}
	/**
	 * Seccion para traducir un renglon que contenga el codigo origami:
	 * while(expresion_logica){
	 * 		secuencia_de_acciones
	 * }
	 * a Pseudocodigo:
	 * Mientras expresion_logica Hacer 
	 * 		secuencia_de_acciones
	 * FinMientras
	 */
	if(linea.indexOf("while") != -1){
	    pila.add("FinMientras");
	    newLine+= new WhileParser(linea).getLineOutput();
	    return newLine;
	}
	/**
	 * Seccion para traducir un renglon que contenga el codigo origami:
	 * for(variable_numerica = valor_inicial;valor_final;paso){
	 * 		secuencia_de_acciones
	 * }
	 * a Pseudocodigo:
	 * Para variable_numerica<-valor_inicial Hasta valor_final Con Paso paso Hacer 
	 * 		secuencia_de_acciones
	 * FinPara
	 */
	if(linea.indexOf("for") != -1){
	    pila.add("FinPara");
	    newLine=new ForParser(linea).getLineOutput();
	    System.out.println(linea);
	    
	    return newLine;
	}
	
	/**
	 * Seccion para traducir un renglon que contenga el codigo origami "X = Y;" a Pseudocodigo "X <- Y;"
	 */
	if(linea.indexOf("=") != -1){
	    newLine+=new ExpressionParser(linea).getLineOutput();
	    
	    return newLine;
	}
	/**
	 * Seccion para traducir un renglon que contenga el codigo origami "\p" a Pseudocodigo "Escribir "
	 */
	if(linea.indexOf("\\p") != -1){
	    System.out.println(linea);
	    newLine+= new OutputParser(linea).getLineOutput();
	    return newLine;
	}
	
	/**
	 * Seccion para traducir las "}" del codigo origami a sus repectivos "FinSi", "FinMientras" y "FinPara" en Pseudocodigo
	 */
	if(linea.indexOf("}") != -1){
	    for(int i = 0; i < linea.length(); i++){
		if(i == linea.indexOf("}")){
		    if(!pila.isEmpty()){
			newLine += pila.get(pila.size()-1);
			pila.remove(pila.size()-1);
		    }
		}
		else{
		    newLine += linea.charAt(i);
		}
	    }
	    return newLine;
	}
	return linea;
    }
	
    private boolean isNumber(char c){
	String numeros = "0123456789";
	for(int i = 0; i < numeros.length(); i++){
	    if(numeros.charAt(i) == c){
		return true;
	    }
	}
	return false;
    }
    
}
