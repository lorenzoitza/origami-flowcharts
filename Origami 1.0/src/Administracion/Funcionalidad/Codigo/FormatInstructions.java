package Administracion.Funcionalidad.Codigo;

import java.util.Vector;


public abstract class FormatInstructions {

    protected Vector<String> codeOfFigure;

    protected Vector<String> TableOfVariable;

    protected String code;
    
    public FormatInstructions(){
		this.codeOfFigure = new Vector<String>();
		this.TableOfVariable = new Vector<String>();
		this.code = new String();
    }
    
    public abstract void applyFormat();
    
    public String getInstructionsFormat(){
    	return this.code;
    }
    
    public void setTableOfVariable(Vector<String> tableOfVariable) {
        TableOfVariable = tableOfVariable;
    }
    
    protected void setCodeOfFigure(Vector<String> codeOfFigure) {
    	this.codeOfFigure = codeOfFigure;
    }
    
    protected boolean isDeclarada(String data) {
	for (int i = 0; i < this.TableOfVariable.size(); i++) {
	    if (this.TableOfVariable.elementAt(i).contains(data)) {
		return true;
	    }
	}
	return false;
    }
    
    protected String getTypeOfData(String variable) {
	for (int i = 0; i < TableOfVariable.size(); i++) {
	    if (TableOfVariable.elementAt(i).lastIndexOf(variable) > 0) {
		if (TableOfVariable.elementAt(i).lastIndexOf("int") >= 0) {
		    return "int";
		}
		if (TableOfVariable.elementAt(i).lastIndexOf("char") >= 0) {
		    return "char";
		}
		if (TableOfVariable.elementAt(i).lastIndexOf("float") >= 0) {
		    return "float";
		}
	    }
	}
	return null;
    }
    
    protected String getCodeGDB(String codigoTotal){
	CharSequence valor = "=";
	CharSequence entero = "int";
	CharSequence flotante = "float";
	CharSequence caracter = "char";
	CharSequence impresion = "cout";
	String[] lineas = codigoTotal.split("\n");
	codigoTotal="";
	for(int i=0; i<lineas.length; i++){
		if(i>3 && (!lineas[i].contains(impresion)) && (lineas[i].contains(entero) || lineas[i].contains(flotante) || lineas[i].contains(caracter)) ){
			if(!lineas[i].contains(valor)){
				while(lineas[i].startsWith(" ")){
					lineas[i] = lineas[i].substring(1);
				}
				if(lineas[i].startsWith("int")){
					String[] copia = lineas[i].split(";");
					lineas[i] = copia[0] + " = 0;";
				}
				else if(lineas[i].startsWith("char")){
					String[] copia = lineas[i].split(";");
					lineas[i] = copia[0] + " = '0';";
				}
				else if (lineas[i].startsWith("float")){
					String[] copia = lineas[i].split(";");
					lineas[i] = copia[0] + " = 0.0;";
				}
			}
		}
		else if(lineas[i].contains(impresion)){
			while(lineas[i].startsWith(" ")){
				lineas[i] = lineas[i].substring(1);
			}					
			lineas[i] = lineas[i].replaceFirst("endl;", "\".,.\"<<endl;");
		}
		codigoTotal=codigoTotal + lineas[i]+"\n";
	}
	return codigoTotal;
    }
}
