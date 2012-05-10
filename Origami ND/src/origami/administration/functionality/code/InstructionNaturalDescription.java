package origami.administration.functionality.code;

import java.io.Serializable;
import java.util.Vector;

import origami.administration.FigureStructure;
import origami.graphics.figures.InputFigure;




public class InstructionNaturalDescription extends Instruction implements Serializable{

        
    /**
     * 
     */
    public InstructionNaturalDescription(){
	super();
	
    }
 

    protected int getCodeFromInputFigure(Vector<FigureStructure> figures,
	    int index) {

	InputFigure figure = ((InputFigure) figures.elementAt(index));

	int outputIndex = index;

	if (figure.instruction.getInstruccionSimple() != null) {
	    String[] instructions;
	    //This instruction brings all the block's content without formatting
	    String instruction = figure.instruction.getInstruccionSimple();
	    instructions = instruction.split(";");

	    for (int instructionIndex = 0; instructionIndex < instructions.length; instructionIndex++) {

		/*if (instructions[instructionIndex].indexOf(",") != -1) {

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
		} else {*/
		    code.add(identator + instructions[instructionIndex] + ";");
		    int readIndex =
			    instructions[instructionIndex].indexOf("Leer:");

		    if (readIndex == -1) {

			variablesTable
				.add(instructions[instructionIndex] + ";");
		    }
		}
	    
	}//end if instruction is not null
	return outputIndex;
    }

    
}