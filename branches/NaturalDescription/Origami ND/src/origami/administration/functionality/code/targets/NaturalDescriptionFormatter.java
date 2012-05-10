package origami.administration.functionality.code.targets;

import java.util.Vector;

import origami.administration.functionality.code.AbstractInstructionFormatter;


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
	    System.out.println(codeOfFigure.size());
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
	    newLine+=linea;
	    
	    return newLine;
	}
	/**
	 * Seccion para traducir un renglon que contenga el codigo origami "\p" a Pseudocodigo "Escribir "
	 */
	if(linea.indexOf("\\p") != -1){
	    for(int i = 0; i < linea.length(); i++){
		if(i == linea.indexOf("\\p")){
		    newLine += "Escribir ";
		    i++;
		}
		else{
		    newLine += linea.charAt(i);
		}
	    }
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
	    for(int i = 0; i < linea.length(); i++){
		if(i == linea.indexOf("if")){
		    newLine += "Si";
		    i++;
		}
		else if(linea.charAt(i) == '('){
		    if(linea.indexOf("!=") != -1){
			newLine += " ~(";
		    }
		    else{
			newLine += " ";
		    }
		}
		else if(linea.charAt(i) == ')'){
		    if(linea.indexOf("!=") != -1){
			newLine += ") ";
		    }
		    else{
			newLine += " ";
		    }
		}
		else if(linea.charAt(i) == '!'){
		    newLine += "";
		}
		else if(linea.charAt(i) == '{'){
		    newLine += "Entonces";
		}
		else if(i == linea.indexOf("==")){
		    newLine += "=";
		    i++;
		}
		else{
		    newLine += linea.charAt(i);
		}
	    }
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
		    newLine += "Sino";
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
	    for(int i = 0; i < linea.length(); i++){
		if(i == linea.indexOf("while")){
		    newLine += "Mientras";
		    i += 4;
		}
		else if(linea.charAt(i) == '('){
		    if(linea.indexOf("!=") != -1){
			newLine += " ~(";
		    }
		    else{
			newLine += " ";
		    }
		}
		else if(linea.charAt(i) == ')'){
		    if(linea.indexOf("!=") != -1){
			newLine += ") ";
		    }
		    else{
			newLine += " ";
		    }
		}
		else if(linea.charAt(i) == '!'){
		    newLine += "";
		}
		else if(i == linea.indexOf("==")){
		    newLine += "=";
		    i++;
		}
		else if(linea.charAt(i) == '{'){
		    newLine += "Hacer";
		}
		else{
		    newLine += linea.charAt(i);
		}
	    }
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
	    boolean punCom = true, sig = true, verNum = false;
	    String variable = "";
	    for(int i = 0; i < linea.length(); i ++){
		if(i == linea.indexOf("for")){
		    newLine += "Para";
		    i += 2;
		    if(linea.indexOf("()") != -1){
			return newLine += "   Hasta   Con Paso   Hacer";
		    }
		}
		else if(linea.charAt(i) == '('){
		    newLine += " ";
		    i++;
		    while(linea.charAt(i) != '='){
			if(linea.charAt(i) != ' ')
			    variable += linea.charAt(i);
			i++;
		    }
		    i--;
		    newLine += variable;
		}
		else if(linea.charAt(i) == ')'){
		    newLine += " ";
		}
		else if(linea.charAt(i) == ';'){
		    if(punCom){
			newLine += " Hasta ";
			punCom = false;
		    }
		    else{
			newLine += " Con Paso ";
		    }
		    i += variable.length();
		    //eliminar espacios
		    i++;
		    while(linea.charAt(i) == ' '){
			i++;
		    }
		    i--;
		}
		else if(linea.charAt(i) == '=' && sig){
		    newLine += "<-";
		    i++;
		    while(linea.charAt(i) == ' '){
			i++;
		    }
		    i--;
		    sig = false;
		}
		else if(i == linea.indexOf("<=") || i == linea.indexOf(">=")){
		    i+=2;
		    while(linea.charAt(i) == ' '){
			i++;
		    }
		    i--;
		}
		else if(i == linea.indexOf("++")){
		    newLine += "1";
		    i++;
		}
		else if(i == linea.indexOf("--")){
		    newLine += "-1";
		    i++;
		}
		else if(linea.charAt(i) == '<'){
		    i++;
		    while(linea.charAt(i) == ' '){
			i++;
		    }
		    i--;
		    int c = i;
		    while(isNumber(linea.charAt(++c))){
			newLine += linea.charAt(c);
		    }
		    newLine += "-1";
		    i++;
		    verNum = true;
		}
		else if(linea.charAt(i) == '>'){
		    i++;
		    while(linea.charAt(i) == ' '){
			i++;
		    }
		    i--;
		    int c = i;
		    while(isNumber(linea.charAt(++c))){
			newLine += linea.charAt(c);
		    }
		    newLine += "+1";
		    i++;
		    verNum = true;
		}
		else if(linea.charAt(i) == '{'){
		    newLine += "Hacer";
		}
		else if(isNumber(linea.charAt(i)) && verNum){}
		else{
		    newLine += linea.charAt(i);
		}
	    }
	    return newLine;
	}
	
	
	/**
	 * Seccion para traducir un renglon que contenga el codigo origami "X++;" a Pseudocodigo "X <- X + 1;"
	 */
	if(linea.indexOf("++") != -1){
	    String variable = "";
	    for(int i = 0; i < linea.length(); i++){
		if (i == linea.indexOf("++")){
		    newLine += variable+"<-"+variable+"+1;";
		}
		else if(linea.charAt(i) != '\t'){
		    variable += linea.charAt(i);
		}
		else{
		    newLine += linea.charAt(i);
		}
	    }
	    return newLine;
	}
	/**
	 * Seccion para traducir un renglon que contenga el codigo origami "X--;" a Pseudocodigo "X <- X - 1;"
	 */
	if(linea.indexOf("--") != -1){
	    String variable = "";
	    for(int i = 0; i < linea.length(); i++){
		if (i == linea.indexOf("--")){
		    newLine += variable+"<-"+variable+"-1;";
		}
		else if(linea.charAt(i) != '\t'){
		    variable += linea.charAt(i);
		}
		else{
		    newLine += linea.charAt(i);
		}
	    }
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
