package origami.administration.functionality.code;

import java.util.Vector;


public abstract class AbstractInstructionFormatter {

    protected Vector<String> codeOfFigure;

    protected Vector<String> TableOfVariable;

    protected String sourceCode;
    
    protected String dataInput = "leer:";
    
    protected String dataOutput = "\\p";
    
    
    public AbstractInstructionFormatter(){
		this.codeOfFigure = new Vector<String>();
		this.TableOfVariable = new Vector<String>();
		this.sourceCode = new String();
    }
    
    //Method to be implemented in subclassses for specific code target
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
    
    protected boolean isDeclared(String dataToBeChecked) {
	for (int index = 0; index < this.TableOfVariable.size(); index++) {
	    if (this.TableOfVariable.elementAt(index).contains(dataToBeChecked)) {
		return true;
	    }
	}
	return false;
    }
    
    protected void removeRepeatedLines(Vector<String> lineas) {
	String temporalLine, temporalLine_, _temporalLine;
	int position;
	for (int indexElement = 0; indexElement < lineas.size(); indexElement++) {
	    temporalLine = lineas.elementAt(indexElement);
	    while (temporalLine.startsWith(" ")) {
		temporalLine = temporalLine.substring(1);
	    }
	    position = temporalLine.indexOf(" ");
	    while (position != -1) {
		_temporalLine = temporalLine.substring(position + 1);
		temporalLine = temporalLine.substring(0, position) + _temporalLine;
		position = temporalLine.indexOf(" ");
	    }
	    for (int figureWithCode = 0; figureWithCode < codeOfFigure.size(); figureWithCode++) {
		temporalLine_ = codeOfFigure.elementAt(figureWithCode);
		while (temporalLine_.startsWith(" ")) {
		    temporalLine_ = temporalLine_.substring(1);
		}
		position = temporalLine_.indexOf(" ");
		while (position != -1) {
		    _temporalLine = temporalLine_.substring(position + 1);
		    temporalLine_ = temporalLine_.substring(0, position) + _temporalLine;
		    position = temporalLine_.indexOf(" ");
		}
		if (temporalLine.compareTo(temporalLine_) == 0) {
		    if (temporalLine.indexOf("cin>>") == -1
			    && temporalLine.indexOf("scanf(") == -1) {
			lineas.remove(indexElement);
			indexElement--;
			break;
		    }
		}
	    }
	}
    }
    
    protected String getTypeOfData(String variable) {
	for (int variableInTable = 0; variableInTable < TableOfVariable.size(); variableInTable++) {
	    if (TableOfVariable.elementAt(variableInTable).lastIndexOf(variable) > 0) {
		if (TableOfVariable.elementAt(variableInTable).lastIndexOf("int") >= 0) {
		    return "int";
		}
		if (TableOfVariable.elementAt(variableInTable).lastIndexOf("char") >= 0) {
		    return "char";
		}
		if (TableOfVariable.elementAt(variableInTable).lastIndexOf("float") >= 0) {
		    return "float";
		}
	    }
	}
	return null;
    }
}
