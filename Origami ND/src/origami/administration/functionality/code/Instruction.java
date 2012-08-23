package origami.administration.functionality.code;

import java.io.Serializable;
import java.util.Vector;

import origami.administration.FigureStructure;
import origami.graphics.figures.DecisionFigure;
import origami.graphics.figures.ForFigure;
import origami.graphics.figures.InputFigure;
import origami.graphics.figures.OutputFigure;
import origami.graphics.figures.SentenceFigure;
import origami.graphics.figures.WhileFigure;


@SuppressWarnings("serial")
public class Instruction implements Serializable {
    protected final String  MARK_SELECTED= ""; 

    protected Vector<String> code = new Vector<String>();

    protected Vector<String> variablesTable = new Vector<String>();

    protected String identator = "\t";

    protected String totalCode = new String();

    
    public String getTotalCode() {
        return totalCode;
    }

    
    public void setTotalCode(String totalCode) {
        this.totalCode = totalCode;
    }

    public Vector<String> getVariablesTable() {
	return variablesTable;
    }

    public Vector<String> getInstructionOfDiagram(Vector<FigureStructure> diagrama) {
	getCodeFromFigures(diagrama, 1);
	return code;
    }


    private boolean isValidInstruction(String decision) {
	boolean isNotNull = decision.compareTo("null") != 0;
	boolean isNotEmpty = decision.compareTo("") != 0;
	return isNotNull && isNotEmpty;
    }


     
    protected int getCodeFromFigures(Vector<FigureStructure> figures,
	    int startIndex) {

	int index;

	for (index = startIndex; index < figures.size(); index++) {

	    if (figures.elementAt(index) instanceof DecisionFigure) {

		index = getCodeFromDecisionFigure(figures, index);
	    } else if (figures.elementAt(index) instanceof ForFigure) {

		index = getCodeFromForFigure(figures, index);
	    } else if (figures.elementAt(index) instanceof WhileFigure) {

		index = getCodeFromWhileFigure(figures, index);
	    } else if (figures.elementAt(index) instanceof OutputFigure) {

		index = getCodeFromOutputFigure(figures, index);
	    } else if (figures.elementAt(index) instanceof InputFigure) {

		index = getCodeFromInputFigure(figures, index);
	    } else if (figures.elementAt(index) instanceof SentenceFigure) {

		addSentenceFigureInstructionToString(figures, index);
	    } else {
		break;
	    }
	}
	return index;
    }
    
    private int getCodeFromDecisionFigure(Vector<FigureStructure> figures,
	    int index) {
	
	DecisionFigure figure = (DecisionFigure) figures.elementAt(index);
	/*
	String selectionMark = "";
	if(figure.getSeleccion()) {
	    selectionMark = MARK_SELECTED;
	}
	*/
	
	int outputIndex = index;

	int codeSize = 0;

	if (figure.instructionComposed.getFirstInstructionSimple() != null) {
	    
	    String simpleInstruction = figure.instructionComposed.getFirstInstructionSimple();

	    if (isValidInstruction(simpleInstruction)) {

		code.add(identator

			+ figure.instructionComposed.getFirstInstructionSimple());
	    } else {
		code.add(identator + "if(){");
	    }
	}
	changeIndetator(true);
	outputIndex = getCodeFromFigures(figures, index + 2);

	changeIndetator(false);
	code.add(identator + "}");
	codeSize = code.size();

	changeIndetator(true);
	outputIndex =
		getCodeFromFigures(figures, outputIndex + 2) + 1;

	changeIndetator(false);
	if (code.size() > codeSize) {

	    String copy = new String(identator + "else{");
	    code.insertElementAt(copy, codeSize);
	    code.add(identator + "}");
	}
	return outputIndex;
    }

    private int getCodeFromForFigure(Vector<FigureStructure> figures,
	    int index) {

	ForFigure figure = (ForFigure) figures.elementAt(index);
	
	/*String selectionMark = "";
	if(figure.getSeleccion()) {
	    selectionMark = "$";
	}
	*/
	
	int outputIndex = index;

	if (figure.instructionComposed.getFirstInstructionSimple() != null) {

	    if (isValidInstruction(figure.instructionComposed.getFirstInstructionSimple())) {

		code.add(identator
			+ figure.instructionComposed.getFirstInstructionSimple());
	    } else {
		code.add(identator + "for(){");
	    }
	}
	changeIndetator(true);
	outputIndex = getCodeFromFigures(figures, index + 1);

	changeIndetator(false);
	code.add(identator + "}");
	outputIndex += 5;

	return outputIndex;
    }

    private int getCodeFromWhileFigure(Vector<FigureStructure> figures,
	    int index) {

	WhileFigure figure = (WhileFigure) figures.elementAt(index);

	int outputIndex = index;


	if (figure.instructionComposed.getFirstInstructionSimple() != null) {
	    if (isValidInstruction(figure.instructionComposed.getFirstInstructionSimple())) {

		code.add(identator
			+ figure.instructionComposed.getFirstInstructionSimple());
			
	    } else {
		code.add(identator + "while(){");
	    }
	}
	changeIndetator(true);
	outputIndex = getCodeFromFigures(figures, index + 1);

	changeIndetator(false);
	code.add(identator + "}");
	outputIndex += 5;

	return outputIndex;
    }

    private int getCodeFromOutputFigure(Vector<FigureStructure> figures,
	    int index) {

	OutputFigure figure = ((OutputFigure) figures.elementAt(index));

	int outputIndex = index;

	if (figure.instruction.getInstruccionSimple() != null) {

	    String[] instructions = new String[3];
	    String instruction = figure.instruction.getInstruccionSimple();
	    instructions = instruction.split(";");
	    for (int instructionIndex = 0; instructionIndex < instructions.length; instructionIndex++) {

		while (instructions[instructionIndex].startsWith(" ")) {

		    instructions[instructionIndex] =
			    instructions[instructionIndex]
				    .replaceFirst(" ", "");
		}
		code.add(identator + instructions[instructionIndex] + ";");
	    }
	}
	return outputIndex;
    }

    protected int getCodeFromInputFigure(Vector<FigureStructure> figures,
	    int index) {

	InputFigure figure = ((InputFigure) figures.elementAt(index));

	int outputIndex = index;

	if (figure.instruction.getInstruccionSimple() != null) {
	    String[] instructions;
	    String instruction = figure.instruction.getInstruccionSimple();
	    //System.out.println(instruction);
	    instructions = instruction.split(";");

	    for (int instructionIndex = 0; instructionIndex < instructions.length; instructionIndex++) {

		if (instructions[instructionIndex].indexOf(",") != -1) {

		    System.out.println("before: "+instructions[instructionIndex]);
		    Vector<String> getSetencesWithFormattedVariables =
			    getFormattedVariables(instructions[instructionIndex], null);
		    for(int variableIndex=0; variableIndex<getSetencesWithFormattedVariables.size(); variableIndex++){
			System.out.println("after: "+getSetencesWithFormattedVariables.elementAt(variableIndex));
		    }
		    
		    if (getSetencesWithFormattedVariables.elementAt(0).indexOf("Leer:") >= 0) {

			int indexAux =
				getSetencesWithFormattedVariables.elementAt(0).indexOf("Leer:") + 5;
			String previousDeclaration =
				getSetencesWithFormattedVariables.elementAt(0).substring(indexAux);

			previousDeclaration = getDataType(previousDeclaration);

			for (int variableIndex = 1; variableIndex < getSetencesWithFormattedVariables
				.size(); variableIndex++) {

			    changeVariablesContent(getSetencesWithFormattedVariables,
				    previousDeclaration, variableIndex);
			}
		    }
		    for (int variableIndex = 0; variableIndex < getSetencesWithFormattedVariables
			    .size(); variableIndex++) {

			code.add(identator
				+ getSetencesWithFormattedVariables.elementAt(variableIndex) + ";");
			int readIndex =
				getSetencesWithFormattedVariables.elementAt(variableIndex).indexOf(
					"Leer:");

			if (readIndex == -1) {

			    variablesTable.add(getSetencesWithFormattedVariables
				    .elementAt(variableIndex)
				    + ";");
			}
		    }
		} else {
		    code.add(identator + instructions[instructionIndex] + ";");
		    int readIndex =
			    instructions[instructionIndex].indexOf("Leer:");

		    if (readIndex == -1) {

			variablesTable
				.add(instructions[instructionIndex] + ";");
		    }
		}
	    }
	}
	return outputIndex;
    }

    private int addSentenceFigureInstructionToString(Vector<FigureStructure> figures,
	    int index) {

	SentenceFigure figure = (SentenceFigure) figures.elementAt(index);

	int outputIndex = index;

	if (figure.instruction.getInstruccionSimple() != null) {
	    code.add(identator + figure.instruction.getInstruccionSimple());
	}
	return outputIndex;
    }

    private String getDataType(String dataType) {
	if (dataType.contains("int ")) {

	    return "int ";
	} else if (dataType.contains("float ")) {

	    return "float ";
	} else if (dataType.contains("char ")) {

	    return "char ";
	}
	return "";
    }

    private String changeVariablesContent(Vector<String> listVariables,
	    String previousDeclaration, int variableIndex) {

	String variable = listVariables.elementAt(variableIndex);

	String declaration = previousDeclaration;

	if (variable.contains("int ")) {

	    variable = "Leer: " + variable;
	    declaration = "int ";
	} else if (variable.contains("float ")) {

	    variable = "Leer: " + variable;
	    declaration = "float ";
	} else if (variable.contains("char ")) {

	    variable = "Leer: " + variable;
	    declaration = "char ";
	} else {

	    variable = "Leer: " + previousDeclaration + variable;
	}
	listVariables.remove(variableIndex);
	listVariables.insertElementAt(variable, variableIndex);
	return declaration;
    }


    private Vector<String> getFormattedVariables(String sentenceLine, String variableType) {
	Vector<String> variables = new Vector<String>();
	
	if(sentenceLine.toLowerCase().lastIndexOf("leer:")!=-1){
	    if(sentenceLine.lastIndexOf("{")!=-1){
		
	    }
	    else{
		
		String tokenizedSentences[] = sentenceLine.split(",");
		String data = tokenizedSentences[0].substring(tokenizedSentences[0].indexOf(":"));
		String previousDataType = getDataType(data);
		variables.add(tokenizedSentences[0]);
		for(int tokenizedSentence = 1; tokenizedSentence <tokenizedSentences.length; tokenizedSentence++){
		    String dataTypeOfVariable = getDataType(tokenizedSentences[tokenizedSentence ]);
		    if(dataTypeOfVariable!=""){
			previousDataType = dataTypeOfVariable;
			variables.add("leer: "+tokenizedSentences[tokenizedSentence ]);
		    }
		    else{
			variables.add("leer: "+previousDataType+" "+tokenizedSentences[tokenizedSentence]);
		    }
		}
	    }
	}
	else{
	    if(sentenceLine.lastIndexOf("{")!=-1){
		//array inicializado
		variables.add(sentenceLine);
	    }
	    else{
		//declaraciones normales
		 String tokenizedSentences[] = sentenceLine.split(",");
		 String dataTypeOfVariable = getDataType(tokenizedSentences[0]);
		 variables.add(tokenizedSentences[0]);
		 for(int tokenizedSentence = 1; tokenizedSentence <tokenizedSentences.length; tokenizedSentence++){
		     String newDatatype = getDataType(tokenizedSentences[tokenizedSentence ]);
		     if(newDatatype!=""){
			 dataTypeOfVariable = newDatatype;
			 variables.add(tokenizedSentences[tokenizedSentence ]);
		     }
		     else{
			 variables.add(dataTypeOfVariable+tokenizedSentences[tokenizedSentence ]);
		     }
		 }
	    }
	}
	return variables;
    }
    private void changeIndetator(boolean on) {
	if (on) {
	    
	    identator = identator + "    ";
	} else {
	    identator = identator.replaceFirst("    ", "");
	}
    }
}