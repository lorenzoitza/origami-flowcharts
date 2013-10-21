package origami.administration.functionality.code.targets;

import java.util.Vector;

import origami.administration.functionality.code.AbstractInstructionFormatter;

public class CppCodeFormatter extends AbstractInstructionFormatter {

    public CppCodeFormatter(Vector<String> codeOfFigure,
	    Vector<String> TableOfVariable) {
	super();
	setCodeOfFigure(codeOfFigure);
	setTableOfVariable(TableOfVariable);
    }

    @Override
    public void applyFormat() {
	divideDataInputForCodeCpp();
	sourceCode =
		("#include <iostream>\n" + "using namespace std;\n"
			+ "int main(int argc, char argv[])\n{\n");
	for (int indexCodeFigure = 0; indexCodeFigure < codeOfFigure.size(); indexCodeFigure++) {
	    
	    String str = codeOfFigure.elementAt(indexCodeFigure);
	    int pos = str.indexOf("null");
	    if (pos < 0) {
		
		sourceCode = sourceCode + codeOfFigure.elementAt(indexCodeFigure) + "\n";
	    }
	}
	sourceCode = sourceCode + "      return 0;\n" + "}\n";
    }
    
    private void divideDataInputForCodeCpp() {

	for (int indexCodeFigure = 0; indexCodeFigure < codeOfFigure.size(); indexCodeFigure++) {

	    if (codeOfFigure.elementAt(indexCodeFigure).toLowerCase().lastIndexOf(dataInput) >= 0) {

		processInputData(indexCodeFigure);
		
	    } else if (codeOfFigure.elementAt(indexCodeFigure).lastIndexOf(dataOutput) >= 0) {
		processOutputData(indexCodeFigure);

	    }
	}
    }
    
    
    private void processInputData(int indexCodeFigure)
    {
	
	Vector<String> formattedLines =
		changeInstructionOfDataInputForCodeCpp(codeOfFigure
			.elementAt(indexCodeFigure));

	String cppCodeOfFigure = codeOfFigure.elementAt(indexCodeFigure);

	int codeLengthOfFigure = codeOfFigure.elementAt(indexCodeFigure).toLowerCase().indexOf("l");

	cppCodeOfFigure = cppCodeOfFigure.substring(0, codeLengthOfFigure);

	removeRepeatedLines(formattedLines);

	codeOfFigure.removeElementAt(indexCodeFigure);

	for (int indexData = 0; indexData < formattedLines.size(); indexData++) {

	    String indexLine = cppCodeOfFigure + formattedLines.elementAt(indexData);

	    codeOfFigure.insertElementAt(indexLine, indexCodeFigure);

	    indexCodeFigure++;

	}

	indexCodeFigure--;
    }
    
    private void processOutputData(int indexCodeFigure)
    {
	Vector<String> formattedLines =
		changeInstructionOfDataOutputForCodeCpp(codeOfFigure
			.elementAt(indexCodeFigure));

	int indexCharacterP = codeOfFigure.elementAt(indexCodeFigure).indexOf("p");

	String spaceBetweenCode =
		codeOfFigure.elementAt(indexCodeFigure).substring(0,
			indexCharacterP - 1);

	codeOfFigure.removeElementAt(indexCodeFigure);

	for (int dataIndex = 0; dataIndex < formattedLines.size(); dataIndex++) {

	    String indexLine = spaceBetweenCode + formattedLines.elementAt(dataIndex);

	    codeOfFigure.insertElementAt(indexLine, indexCodeFigure);

	    indexCodeFigure++;
	}
	indexCodeFigure--;
	
    }

    private Vector<String> changeInstructionOfDataInputForCodeCpp(String lineToBeFormatted) {
	Vector<String> formattedInstructionCode = new Vector<String>();
	String cinTag = "cin>>";
	String finalFormattedline = "";
	String dataToEmbbed = "";

	String formattedLines =
		lineToBeFormatted.substring(lineToBeFormatted.toLowerCase().lastIndexOf(dataInput) + 5, lineToBeFormatted.length());

	while (formattedLines.startsWith(" ")) {
	    formattedLines = formattedLines.substring(1);
	}

	if (formattedLines.lastIndexOf("int ") >= 0) {
	    dataToEmbbed =
		    formattedLines.substring(formattedLines.lastIndexOf("int") + 3, formattedLines
			    .lastIndexOf(";"));
	    
	} else if (formattedLines.lastIndexOf("float ") >= 0) {
	    dataToEmbbed =
		    formattedLines.substring(formattedLines.lastIndexOf("float") + 5, formattedLines
			    .lastIndexOf(";"));

	} else if (formattedLines.lastIndexOf("char ") >= 0) {
	    dataToEmbbed =
		    formattedLines.substring(formattedLines.lastIndexOf("char") + 4, formattedLines
			    .lastIndexOf(";"));
	}
	else{
	    dataToEmbbed =
		    formattedLines.substring(0, formattedLines
			    .lastIndexOf(";"));
	}
	 if (!isDeclared(formattedLines)) {
	this.TableOfVariable.add(formattedLines);
	formattedInstructionCode.add(formattedLines);
       }

	finalFormattedline = cinTag + dataToEmbbed + ";";

	formattedInstructionCode.add(finalFormattedline);

	return formattedInstructionCode;
    
    
    
    }

    private Vector<String> changeInstructionOfDataOutputForCodeCpp(
	    String lineToBeFormatted) {
	Vector<String> formattedInstructions = new Vector<String>();
	String coutTag = "cout ";
	String delimeterTag = "<< ";
	String endlTag = "<< endl;";

	String formattedLines =
		lineToBeFormatted.substring(lineToBeFormatted.lastIndexOf(dataOutput) + 2,
			lineToBeFormatted.length() - 1);

	while (formattedLines.startsWith(" ")) {
	    formattedLines = formattedLines.substring(1);
	}
	while (formattedLines.endsWith(" ")) {
	    formattedLines = formattedLines.substring(0,formattedLines.length()-1);
	}
	
	String formmatedInstruction = coutTag;
	
	
	int indexPosition = 0;
	while(indexPosition<formattedLines.length()){
	    String instruction = formattedLines.substring(indexPosition);
	    while (instruction.startsWith(" ")) {
		instruction = instruction.substring(1);
		indexPosition++;
	    }
	    
	    
	    if(instruction.startsWith("\"")){
		int initString = instruction.indexOf("\"");
		int endString = instruction.indexOf("\"",initString+1);
		if(endString!=-1){
		    formmatedInstruction = formmatedInstruction + delimeterTag + instruction.substring(initString, endString+1);
		    
		    indexPosition = indexPosition+endString+1;
		    
		    String rest = instruction.substring(endString+1);
		    if(rest.length()!=0){
			 while (rest.startsWith(" ")) {
			     rest = rest.substring(1);
			     indexPosition++;
			 }
			 if(rest.startsWith(",")){
			     indexPosition++;
			 }
			 else if(rest.contains(",")){
			     int position = rest.indexOf(",");
			     formmatedInstruction = formmatedInstruction + rest.substring(0,position);
			     indexPosition = indexPosition + position+1;
			 }
		    }
		}
		else{
		    if(instruction.contains(",")){
			int separatePosition = instruction.indexOf(",");
			formmatedInstruction = formmatedInstruction + delimeterTag + instruction.substring(0, separatePosition);
			indexPosition = indexPosition+separatePosition+1;
		    }
		    else{
			formmatedInstruction = formmatedInstruction + delimeterTag + instruction;
			indexPosition = indexPosition+instruction.length();
		    }
		}
		
	    }
	    else if(instruction.contains(",")){
		int separatePosition = instruction.indexOf(",");
		formmatedInstruction = formmatedInstruction + delimeterTag + instruction.substring(0, separatePosition);
		indexPosition = indexPosition+separatePosition+1;
	    }
	    else{
		formmatedInstruction = formmatedInstruction + delimeterTag + instruction;
		indexPosition = indexPosition+instruction.length();
	    }
	}

	formmatedInstruction = formmatedInstruction + endlTag;

	formattedInstructions.add(formmatedInstruction);

	return formattedInstructions;
    }
}
