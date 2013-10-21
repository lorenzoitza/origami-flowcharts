package origami.administration.functionality.code.targets;

import java.util.Vector;

import origami.administration.functionality.code.AbstractInstructionFormatter;


public class PseudocodigoCodeFormatter extends AbstractInstructionFormatter {
    private Vector<String> pseudocodigo;
    private Vector<String> prePseudocodigo;
    private Vector<String> pila;
    
    public PseudocodigoCodeFormatter(Vector<String> codeOfFigure,
	    Vector<String> TableOfVariable){
	super();
	setCodeOfFigure(codeOfFigure);
	setTableOfVariable(TableOfVariable);
	pseudocodigo = new Vector<String>();
	prePseudocodigo = new Vector<String>();
	pila = new Vector<String>();
    }
    
    @Override
    public void applyFormat() {
	traducirCodigo();
    }

    /**
     * Metodo para traducir recorrer los renglones del codigo origami y traducirlos  a Pseudocodigo
     */
    private void traducirCodigo(){
	pseudocodigo.add("Proceso main");
	for(int i = 0; i < codeOfFigure.size(); i++){
	    pseudocodigo.add(traducirLinea(codeOfFigure.get(i)));
	}
	pseudocodigo.add("FinProceso");
	filtroFinal();
	vectorToString();
    }

    /**
     * Metodo para traducir una linea de codigo origami a Pseudocodigo
     * @param linea String renglon de codigo origami
     * @return String renglon de Pseudocodigo
     */
    private String traducirLinea(String linea){
	String newLine = "";
	
	System.out.println(linea);
	if(linea.indexOf("null") != -1) {
	    //System.out.println("por aqui");
	return newLine;
	}
	
	/**
	 * Seccion para traducir un renglon que contenga el codigo origami "Leer:" a Pseudocodigo "Leer "
	 */
	if(linea.indexOf("Leer:") != -1){
	    for(int i = 0; i < linea.length(); i++){
		if(i == linea.indexOf("Leer:")){
		    newLine += "Leer ";
		    i += 4;
		}
		else if(i == linea.indexOf("int")){
		    newLine += "";
		    i += 3;
		}
		else if(i == linea.indexOf("double")){
		    newLine += "";
		    i += 6;
		}
		else if(i == linea.indexOf("float")){
		    newLine += "";
		    i += 5;
		}
		else if(i == linea.indexOf("char")){
		    newLine += "";
		    i += 4;
		}
		else{
		    newLine += linea.charAt(i);
		}
	    }
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
	 * Seccion para traducir un renglon que contenga el codigo origami "X += Y;" a Pseudocodigo "X <- X + Y;"
	 */
	if(linea.indexOf("+=") != -1){
	    String variable = "";
	    for(int i = 0; i < linea.length(); i++){
		if (i == linea.indexOf("+=")){
		    newLine += variable+"<-"+variable+"+";
		    i++;
		}
		else if(i < linea.indexOf("+=") && linea.charAt(i) != '\t'){
		    variable += linea.charAt(i);
		}
		else{
		    newLine += linea.charAt(i);
		}
	    }
	    return newLine;
	}
	/**
	 * Seccion para traducir un renglon que contenga el codigo origami "X -= Y;" a Pseudocodigo "X <- X - Y;"
	 */
	if(linea.indexOf("-=") != -1){
	    String variable = "";
	    for(int i = 0; i < linea.length(); i++){
		if (i == linea.indexOf("-=")){
		    newLine += variable+"<-"+variable+"-";
		    i++;
		}
		else if(i < linea.indexOf("-=") && linea.charAt(i) != '\t'){
		    variable += linea.charAt(i);
		}
		else{
		    newLine += linea.charAt(i);
		}
	    }
	    return newLine;
	}
	/**
	 * Seccion para traducir un renglon que contenga el codigo origami "X *= Y;" a Pseudocodigo "X <- X * Y;"
	 */
	if(linea.indexOf("*=") != -1){
	    String variable = "";
	    for(int i = 0; i < linea.length(); i++){
		if (i == linea.indexOf("*=")){
		    newLine += variable+"<-"+variable+"*";
		    i++;
		}
		else if(i < linea.indexOf("*=") && linea.charAt(i) != '\t'){
		    variable += linea.charAt(i);
		}
		else{
		    newLine += linea.charAt(i);
		}
	    }
	    return newLine;
	}
	/**
	 * Seccion para traducir un renglon que contenga el codigo origami "X /= Y;" a Pseudocodigo "X <- X / Y;"
	 */
	if(linea.indexOf("/=") != -1){
	    String variable = "";
	    for(int i = 0; i < linea.length(); i++){
		if (i == linea.indexOf("/=")){
		    newLine += variable+"<-"+variable+"/";
		    i++;
		}
		else if(i < linea.indexOf("/=") && linea.charAt(i) != '\t'){
		    variable += linea.charAt(i);
		}
		else{
		    newLine += linea.charAt(i);
		}
	    }
	    return newLine;
	}
	/**
	 * Seccion para traducir un renglon que contenga el codigo origami "X = Y;" a Pseudocodigo "X <- Y;"
	 */
	if(linea.indexOf("=") != -1){
	    
	    for(int i = 0; i < linea.length(); i++){
		if(i == linea.indexOf("=")){
		    newLine += "<-";
		}
		else if(i == linea.indexOf("int")){
		    newLine += "";
		    i += 3;
		}
		else if(i == linea.indexOf("double")){
		    newLine += "";
		    i += 6;
		}
		else if(i == linea.indexOf("float")){
		    newLine += "";
		    i += 5;
		}
		else if(i == linea.indexOf("char")){
		    newLine += "";
		    i += 4;
		}
		else{
		    newLine += linea.charAt(i);
		}
	    }
	    return newLine;
	}
	/**
	 * Seccion para traducir un renglon que contenga declaracion de variable de codigo origami como "int X;" a Pseudocodigo "X <- 0;"
	 */
	if(linea.indexOf("int") != -1){
	    for(int i = 0; i < linea.length(); i++){
		if (i == linea.indexOf("int")){
		    newLine += "";
		    i += 3;
		}
		else{
		    newLine += linea.charAt(i);
		}
	    }
	    return newLine.replace(';','<')+"-0;";
	}
	if(linea.indexOf("double") != -1){
	    for(int i = 0; i < linea.length(); i++){
		if (i == linea.indexOf("double")){
		    newLine += "";
		    i += 6;
		}
		else{
		    newLine += linea.charAt(i);
		}
	    }
	    return newLine.replace(';','<')+"-0;";
	}
	if(linea.indexOf("float") != -1){
	    for(int i = 0; i < linea.length(); i++){
		if (i == linea.indexOf("float")){
		    newLine += "";
		    i += 5;
		}
		else{
		    newLine += linea.charAt(i);
		}
	    }
	    return newLine.replace(';','<')+"-0;";
	}
	if(linea.indexOf("char") != -1){
	    for(int i = 0; i < linea.length(); i++){
		if (i == linea.indexOf("char")){
		    newLine += "";
		    i += 4;
		}
		else{
		    newLine += linea.charAt(i);
		}
	    }
	    return newLine.replace(';','<')+"-0;";
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
	
    /**
     * Metodo para eliminar los "FinSi" de los "Si", que contengan la condicion "sino"
     */
    private void filtroFinal(){
	for(int i = pseudocodigo.size()-1; i >= 0; i--){
	    if(pseudocodigo.get(i).indexOf("Sino") != -1){// si hay "Sino" en la linea
		prePseudocodigo.add(pseudocodigo.get(i--));
	    }
	    else{
		prePseudocodigo.add(pseudocodigo.get(i));
	    }
	}
	pseudocodigo = new Vector<String>();
	
	for(int i = prePseudocodigo.size()-1; i >= 0; i--){
	    String linea = prePseudocodigo.get(i);
	    // Just in case a I/O or expresion empty
	    if(!linea.equals("")){
		pseudocodigo.add(prePseudocodigo.get(i));
		}
	}
    }

    /**
     * Metodo para verificar si un char es un caracter numerico
     * @param c char caracter a verificar
     * @return true si el caracter es numerico y false si no lo es
     */
    private boolean isNumber(char c){
	String numeros = "0123456789";
	for(int i = 0; i < numeros.length(); i++){
	    if(numeros.charAt(i) == c){
		return true;
	    }
	}
	return false;
    }
    
    /**
     * Metodo para agregar cada renglo del Vector Pseudocodigo a un solo String sourceCode
     */
    private void vectorToString(){
	for(int i = 0; i < pseudocodigo.size();i++){
	    sourceCode += pseudocodigo.get(i)+"\n";
	}
    }
}

   
