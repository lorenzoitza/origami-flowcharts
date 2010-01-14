package origami.administration.funtionality.code;

import java.io.Serializable;
import java.util.Vector;

import origami.administration.Figura;
import origami.graphics.figures.DecisionFigure;
import origami.graphics.figures.ForFigure;
import origami.graphics.figures.InputFigure;
import origami.graphics.figures.OutputFigure;
import origami.graphics.figures.SentenceFigure;
import origami.graphics.figures.WhileFigure;


/**
 * Esta clase es la base administra el codigo agregado por el usuario.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 * 
 */
public class Instruction implements Serializable {

    private Vector<String> code = new Vector<String>();

    private Vector<String> variablesTable = new Vector<String>();

    private String identator = "      ";

    public String totalCode = new String();

    public Vector<String> getVariablesTable() {
	return variablesTable;
    }

    public Vector<String> getInstructionOfDiagram(Vector<Figura> diagrama) {
	addFiguresInstructionToString(diagrama, 1);
	return code;
    }


    private boolean isValidInstruction(String decision) {
	boolean notIsNull = decision.compareTo("null") != 0;
	boolean notIsEmpty = decision.compareTo("") != 0;
	return notIsNull && notIsEmpty;
    }

    /**
     * Este metodo recorre el vector de figuras y agrega cada instruccion de
     * cada figura a un vector de string.
     * 
     * @param diagram
     * @param x
     * @return int
     */
    private int addFiguresInstructionToString(Vector<Figura> figures,
	    int startIndex) {

	int index;

	for (index = startIndex; index < figures.size(); index++) {

	    if (figures.elementAt(index) instanceof DecisionFigure) {

		index = addDecisionFigureInstructionToString(figures, index);
	    } else if (figures.elementAt(index) instanceof ForFigure) {

		index = addForFigureInstructionToString(figures, index);
	    } else if (figures.elementAt(index) instanceof WhileFigure) {

		index = addWhileFigureInstructionToString(figures, index);
	    } else if (figures.elementAt(index) instanceof OutputFigure) {

		index = addOutputFigureInstructionToString(figures, index);
	    } else if (figures.elementAt(index) instanceof InputFigure) {

		index = addInputFigureInstructionToString(figures, index);
	    } else if (figures.elementAt(index) instanceof SentenceFigure) {

		addSentenceFigureInstructionToString(figures, index);
	    } else {
		break;
	    }
	}
	return index;
    }
    
    private int addDecisionFigureInstructionToString(Vector<Figura> figures,
	    int index) {

	DecisionFigure figure = (DecisionFigure) figures.elementAt(index);

	int outputIndex = index;

	int codeSize = 0;

	if (figure.instructionComposed.simpleInstructionList.elementAt(0) != null) {

	    if (isValidInstruction(figure.instructionComposed.simpleInstructionList.firstElement()
		    .getInstruccionSimple())) {

		code.add(identator

			+ figure.instructionComposed.simpleInstructionList.elementAt(0)
				.getInstruccionSimple());
	    } else {
		code.add(identator + "if(){");
	    }
	}
	tabula(true);
	outputIndex = addFiguresInstructionToString(figures, index + 2);

	tabula(false);
	code.add(identator + "}");
	codeSize = code.size();

	tabula(true);
	outputIndex =
		addFiguresInstructionToString(figures, outputIndex + 2) + 1;

	tabula(false);
	if (code.size() > codeSize) {

	    String copy = new String(identator + "else{");
	    code.insertElementAt(copy, codeSize);
	    code.add(identator + "}");
	}
	return outputIndex;
    }

    private int addForFigureInstructionToString(Vector<Figura> figures,
	    int index) {

	ForFigure figure = (ForFigure) figures.elementAt(index);

	int outputIndex = index;

	if (figure.instructionComposed.simpleInstructionList.elementAt(0) != null) {

	    if (isValidInstruction(figure.instructionComposed.simpleInstructionList.firstElement()

		    .getInstruccionSimple())) {

		code.add(identator
			+ figure.instructionComposed.simpleInstructionList.elementAt(0)
				.getInstruccionSimple());
	    } else {
		code.add(identator + "for(){");
	    }
	}
	tabula(true);
	outputIndex = addFiguresInstructionToString(figures, index + 1);

	tabula(false);
	code.add(identator + "}");
	outputIndex += 5;

	return outputIndex;
    }

    private int addWhileFigureInstructionToString(Vector<Figura> figures,
	    int index) {

	WhileFigure figure = (WhileFigure) figures.elementAt(index);

	int outputIndex = index;


	if (figure.instructionComposed.simpleInstructionList.elementAt(0) != null) {
	    if (isValidInstruction(figure.instructionComposed.simpleInstructionList.firstElement()
	    		.getInstruccionSimple())) {

		code.add(identator
			+ figure.instructionComposed.simpleInstructionList.elementAt(0));
			
	    } else {
		code.add(identator + "while(){");
	    }
	}
	tabula(true);
	outputIndex = addFiguresInstructionToString(figures, index + 1);

	tabula(false);
	code.add(identator + "}");
	outputIndex += 5;

	return outputIndex;
    }

    private int addOutputFigureInstructionToString(Vector<Figura> figures,
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

    private int addInputFigureInstructionToString(Vector<Figura> figures,
	    int index) {

	InputFigure figure = ((InputFigure) figures.elementAt(index));

	int outputIndex = index;

	if (figure.instruction.getInstruccionSimple() != null) {
	    String[] instructions;
	    String instruction = figure.instruction.getInstruccionSimple();
	    instructions = instruction.split(";");

	    for (int instructionIndex = 0; instructionIndex < instructions.length; instructionIndex++) {

		if (instructions[instructionIndex].indexOf(",") != -1) {

		    System.out.println("before: "+instructions[instructionIndex]);
		    Vector<String> moreVariables =
			    moreVariables(instructions[instructionIndex], null);
		    for(int i=0; i<moreVariables.size(); i++){
			System.out.println("after: "+moreVariables.elementAt(i));
		    }
		    
		    if (moreVariables.elementAt(0).indexOf("Leer:") >= 0) {

			int indexAux =
				moreVariables.elementAt(0).indexOf("Leer:") + 5;
			String previousDeclaration =
				moreVariables.elementAt(0).substring(indexAux);

			previousDeclaration = getDataTipe(previousDeclaration);

			for (int variableIndex = 1; variableIndex < moreVariables
				.size(); variableIndex++) {

			    changeVariablesContent(moreVariables,
				    previousDeclaration, variableIndex);
			}
		    }
		    for (int variableIndex = 0; variableIndex < moreVariables
			    .size(); variableIndex++) {

			code.add(identator
				+ moreVariables.elementAt(variableIndex) + ";");
			int readIndex =
				moreVariables.elementAt(variableIndex).indexOf(
					"Leer:");

			if (readIndex == -1) {

			    variablesTable.add(moreVariables
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

    private int addSentenceFigureInstructionToString(Vector<Figura> figures,
	    int index) {

	SentenceFigure figure = (SentenceFigure) figures.elementAt(index);

	int outputIndex = index;

	if (figure.instruction.getInstruccionSimple() != null) {
	    code.add(identator + figure.instruction.getInstruccionSimple());
	}
	return outputIndex;
    }

    private String getDataTipe(String previousDeclaration) {
	if (previousDeclaration.contains("int ")) {

	    return "int ";
	} else if (previousDeclaration.contains("float ")) {

	    return "float ";
	} else if (previousDeclaration.contains("char ")) {

	    return "char ";
	}
	return "";
    }

    private String changeVariablesContent(Vector<String> moreVariables,
	    String previousDeclaration, int variableIndex) {

	String variable = moreVariables.elementAt(variableIndex);

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
	moreVariables.remove(variableIndex);
	moreVariables.insertElementAt(variable, variableIndex);
	return declaration;
    }

    /*
     * arreglar posterior mente este metodo que es el que separa los datos de entrada
     * declaraciones de arrays junto con variables
     * Ejemplo:
     * int array[2] = {0,1},j,float k
     * 
     */
    private Vector<String> moreVariables(String line, String tipe) {
	Vector<String> variables = new Vector<String>();
	
	if(line.lastIndexOf("Leer:")!=-1){
	    if(line.lastIndexOf("{")!=-1){
		//coloco la palabre Leer: y tiene inicializado un arreglo???
	    }
	    else{
		//entrada
		String lines[]=line.split(",");
		String data = lines[0].substring(lines[0].indexOf(":"));
		String previousTipe = getDataTipe(data);
		variables.add(lines[0]);
		for(int i=1; i<lines.length; i++){
		    String type = getDataTipe(lines[i]);
		    if(type!=""){
			previousTipe = type;
			variables.add("Leer: "+lines[i]);
		    }
		    else{
			variables.add("Leer: "+previousTipe+" "+lines[i]);
		    }
		}
	    }
	}
	else{
	    if(line.lastIndexOf("{")!=-1){
		//array inicializado
		variables.add(line);
	    }
	    else{
		//declaraciones normales
		 String lines[]=line.split(",");
		 String previousTipe = getDataTipe(lines[0]);
		 variables.add(lines[0]);
		 for(int i=1; i<lines.length; i++){
		     String type = getDataTipe(lines[i]);
		     if(type!=""){
			 previousTipe = type;
			 variables.add(lines[i]);
		     }
		     else{
			 variables.add(previousTipe+lines[i]);
		     }
		 }
	    }
	}
	return variables;
    }
    private void tabula(boolean isMayor) {
	if (isMayor) {
	    
	    identator = identator + "    ";
	} else {
	    identator = identator.replaceFirst("    ", "");
	}
    }
}