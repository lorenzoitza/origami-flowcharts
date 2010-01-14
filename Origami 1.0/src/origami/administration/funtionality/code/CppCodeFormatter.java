package origami.administration.funtionality.code;

import java.util.Vector;

public class CppCodeFormatter extends FormatInstructions {

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

	    if (codeOfFigure.elementAt(indexCodeFigure).lastIndexOf(dataInput) >= 0) {

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

	int codeLengthOfFigure = codeOfFigure.elementAt(indexCodeFigure).indexOf("L");

	cppCodeOfFigure = cppCodeOfFigure.substring(0, codeLengthOfFigure);

	removeLinesRepeated(formattedLines);

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
		lineToBeFormatted.substring(lineToBeFormatted.lastIndexOf(dataInput) + 5, lineToBeFormatted.length());

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
	 if (!isDeclarada(formattedLines)) {
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

	String dataTokensOfCode[] = formattedLines.split(",");

	String formmatedInstruction = coutTag;

	for (int tokenIndex = 0; tokenIndex < dataTokensOfCode.length; tokenIndex++) {
	    formmatedInstruction = formmatedInstruction + delimeterTag + dataTokensOfCode[tokenIndex];
	}

	formmatedInstruction = formmatedInstruction + endlTag;

	formattedInstructions.add(formmatedInstruction);

	return formattedInstructions;
    }
}
