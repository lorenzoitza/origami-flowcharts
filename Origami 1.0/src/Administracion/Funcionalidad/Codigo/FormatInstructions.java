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
    
    protected void setCodeOfFigure(Vector<String> codeOfFigure) {
    	this.codeOfFigure = codeOfFigure;
    }
    
    public abstract void applyFormat();
    
    public String getInstructionsFormat(){
    	return this.code;
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

    
    public void setTableOfVariable(Vector<String> tableOfVariable) {
        TableOfVariable = tableOfVariable;
    }
    
    
}
