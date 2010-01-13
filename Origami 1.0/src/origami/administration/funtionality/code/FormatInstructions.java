package origami.administration.funtionality.code;

import java.util.Vector;


public abstract class FormatInstructions {

    protected Vector<String> codeOfFigure;

    protected Vector<String> TableOfVariable;

    protected String codeSource;
    
    protected String dataInput = "Leer:";
    
    protected String dataOutput = "\\p";
    
    public FormatInstructions(){
		this.codeOfFigure = new Vector<String>();
		this.TableOfVariable = new Vector<String>();
		this.codeSource = new String();
    }
    
    public abstract void applyFormat();
    
    public String getInstructionsFormat(){
    	return this.codeSource;
    }
    
    public void setTableOfVariable(Vector<String> tableOfVariable) {
        TableOfVariable = tableOfVariable;
    }
    
    protected void setCodeOfFigure(Vector<String> codeOfFigure) {
    	this.codeOfFigure = codeOfFigure;
    }
    
    protected boolean isDeclarada(String data) {
	for (int index = 0; index < this.TableOfVariable.size(); index++) {
	    if (this.TableOfVariable.elementAt(index).contains(data)) {
		return true;
	    }
	}
	return false;
    }
    
    protected String getTypeOfData(String variable) {
	for (int index = 0; index < TableOfVariable.size(); index++) {
	    if (TableOfVariable.elementAt(index).lastIndexOf(variable) > 0) {
		if (TableOfVariable.elementAt(index).lastIndexOf("int") >= 0) {
		    return "int";
		}
		if (TableOfVariable.elementAt(index).lastIndexOf("char") >= 0) {
		    return "char";
		}
		if (TableOfVariable.elementAt(index).lastIndexOf("float") >= 0) {
		    return "float";
		}
	    }
	}
	return null;
    }
    
//    protected String getCodeGDB(String codigoTotal){
//	CharSequence valor = "=";
//	CharSequence entero = "int";
//	CharSequence flotante = "float";
//	CharSequence caracter = "char";
//	CharSequence impresion = "cout";
//	String[] lineas = codigoTotal.split("\n");
//	codigoTotal="";
//	for(int index=0; index<lineas.length; index++){
//		if(index>3 && (!lineas[index].contains(impresion)) && (lineas[index].contains(entero) || lineas[index].contains(flotante) || lineas[index].contains(caracter)) ){
//			if(!lineas[index].contains(valor)){
//				while(lineas[index].startsWith(" ")){
//					lineas[index] = lineas[index].substring(1);
//				}
//				if(lineas[index].startsWith("int")){
//					String[] copia = lineas[index].split(";");
//					lineas[index] = copia[0] + " = 0;";
//				}
//				else if(lineas[index].startsWith("char")){
//					String[] copia = lineas[index].split(";");
//					lineas[index] = copia[0] + " = '0';";
//				}
//				else if (lineas[index].startsWith("float")){
//					String[] copia = lineas[index].split(";");
//					lineas[index] = copia[0] + " = 0.0;";
//				}
//			}
//		}
//		else if(lineas[index].contains(impresion)){
//			while(lineas[index].startsWith(" ")){
//				lineas[index] = lineas[index].substring(1);
//			}					
//			lineas[index] = lineas[index].replaceFirst("endl;", "\".,.\"<<endl;");
//		}
//		codigoTotal=codigoTotal + lineas[index]+"\n";
//	}
//	return codigoTotal;
//    }
}
