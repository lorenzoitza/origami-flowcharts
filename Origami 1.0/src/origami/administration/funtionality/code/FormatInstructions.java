package origami.administration.funtionality.code;

import java.util.Vector;


public abstract class FormatInstructions {

    protected Vector<String> codeOfFigure;

    protected Vector<String> TableOfVariable;

    protected String sourceCode;
    
    protected String dataInput = "Leer:";
    
    protected String dataOutput = "\\p";
    
    public FormatInstructions(){
		this.codeOfFigure = new Vector<String>();
		this.TableOfVariable = new Vector<String>();
		this.sourceCode = new String();
    }
    
    public abstract void applyFormat();
    
    public String getInstructionsFormat(){
    	return this.sourceCode;
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
    
    protected void removeLinesRepeated(Vector<String> lineas) {
	String aux, aux2, aux3;
	int pos;
	for (int i = 0; i < lineas.size(); i++) {
	    aux = lineas.elementAt(i);
	    while (aux.startsWith(" ")) {
		aux = aux.substring(1);
	    }
	    pos = aux.indexOf(" ");
	    while (pos != -1) {
		aux3 = aux.substring(pos + 1);
		aux = aux.substring(0, pos) + aux3;
		pos = aux.indexOf(" ");
	    }
	    for (int j = 0; j < codeOfFigure.size(); j++) {
		aux2 = codeOfFigure.elementAt(j);
		while (aux2.startsWith(" ")) {
		    aux2 = aux2.substring(1);
		}
		pos = aux2.indexOf(" ");
		while (pos != -1) {
		    aux3 = aux2.substring(pos + 1);
		    aux2 = aux2.substring(0, pos) + aux3;
		    pos = aux2.indexOf(" ");
		}
		if (aux.compareTo(aux2) == 0) {
		    if (aux.indexOf("cin>>") == -1
			    && aux.indexOf("scanf(") == -1) {
			lineas.remove(i);
			i--;
			break;
		    }
		}
	    }
	}
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
}
