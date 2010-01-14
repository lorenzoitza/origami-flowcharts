package origami.administration.funtionality.code;

import java.util.Vector;

public class CcodeFormatter extends FormatInstructions {

    public CcodeFormatter(Vector<String> codeOfFigure,
	    Vector<String> TableOfVariable) {
	super();
	setCodeOfFigure(codeOfFigure);
	setTableOfVariable(TableOfVariable);
    }

    @Override
    public void applyFormat() {
	divideDataInputForCodeC();
	sourceCode =
		("#include <stdio.h>\n" + "#include <stdlib.h>"
			+ "\nint main(int argc, char argv[])\n{\n");
	for (int indexCodeFigure = 0; indexCodeFigure < codeOfFigure.size(); indexCodeFigure++) {

	    String str = codeOfFigure.elementAt(indexCodeFigure);
	    int pos = str.indexOf("null");
	    if (pos < 0) {

		sourceCode =
			sourceCode + codeOfFigure.elementAt(indexCodeFigure)
				+ "\n";
	    }
	}
	sourceCode =
		sourceCode + "      system(\"PAUSE\");\n      return 0;\n"
			+ "}\n";
    }

    private void divideDataInputForCodeC() {
	for (int indexCodeFigure = 0; indexCodeFigure < codeOfFigure.size(); indexCodeFigure++) {

	    if (codeOfFigure.elementAt(indexCodeFigure).lastIndexOf(dataInput) >= 0) {

		processInputData(indexCodeFigure);

	    } else if (codeOfFigure.elementAt(indexCodeFigure).lastIndexOf(
		    dataOutput) >= 0) {

		processOutputData(indexCodeFigure);

	    }
	}
    }

    private void processInputData(int indexCodeOfFigure) {

	Vector<String> dataSeparate =
		changeInstructionOfDataInputForCodeC(codeOfFigure
			.elementAt(indexCodeOfFigure));

	int indexCharacterL =
		codeOfFigure.elementAt(indexCodeOfFigure).indexOf("L");

	String space =
		codeOfFigure.elementAt(indexCodeOfFigure).substring(0,
			indexCharacterL);

	removeLinesRepeated(dataSeparate);

	codeOfFigure.removeElementAt(indexCodeOfFigure);

	for (int indexOfData = 0; indexOfData < dataSeparate.size(); indexOfData++) {

	    String lin = space + dataSeparate.elementAt(indexOfData);

	    codeOfFigure.insertElementAt(lin, indexCodeOfFigure);

	    indexCodeOfFigure++;
	}
	indexCodeOfFigure--;
    }

    private void processOutputData(int indexCodeOfFigure) {
	Vector<String> dataSeparate =
		changeInstructionOfDataOutputForCodeC(codeOfFigure
			.elementAt(indexCodeOfFigure));

	int indexCharacterP =
		codeOfFigure.elementAt(indexCodeOfFigure).indexOf("p");

	String space =
		codeOfFigure.elementAt(indexCodeOfFigure).substring(0,
			indexCharacterP - 1);

	codeOfFigure.removeElementAt(indexCodeOfFigure);

	for (int indexOfData = 0; indexOfData < dataSeparate.size(); indexOfData++) {

	    String indexLine = space + dataSeparate.elementAt(indexOfData);

	    codeOfFigure.insertElementAt(indexLine, indexCodeOfFigure);

	    indexCodeOfFigure++;
	}
	indexCodeOfFigure--;
    }

    private Vector<String> changeInstructionOfDataInputForCodeC(String linea) {

	Vector<String> currentInstructionFormat = new Vector<String>();
	String characterScan = "scanf(" + "\"" + "%" + "c" + "\"" + "," + "&";
	String integerScan = "scanf(" + "\"" + "%" + "d" + "\"" + "," + "&";
	String floatScan = "scanf(" + "\"" + "%" + "f" + "\"" + "," + "&";
	String defaultScan = "scanf(" + "\"" + "%" + "d" + "\"" + "," + "&";

	String currentFormattedData =
		linea.substring(linea.lastIndexOf(dataInput) + 5, linea
			.length());

	while (currentFormattedData.startsWith(" ")) {
	    currentFormattedData = currentFormattedData.substring(1);
	}

	if (currentFormattedData.lastIndexOf("int ") >= 0) {
	    processInteger(currentInstructionFormat, integerScan,
		    currentFormattedData);

	} else if (currentFormattedData.lastIndexOf("char ") >= 0) {

	    processCharacter(currentInstructionFormat, characterScan,
		    currentFormattedData);

	} else if (currentFormattedData.lastIndexOf("float ") >= 0) {

	    processFloat(currentInstructionFormat, floatScan,
		    currentFormattedData);
	} else {

	    processDefaultData(currentInstructionFormat, currentFormattedData,
		    defaultScan, characterScan, floatScan);
	}

	return currentInstructionFormat;
    }

    private void processDefaultData(Vector<String> currentInstructionFormat,
	    String currentFormattedData, String scanDefault,
	    String scanCharacter, String scanFloat) {
	for (int indexOfVariable = 0; indexOfVariable < TableOfVariable.size(); indexOfVariable++) {
	    
	    if (TableOfVariable.elementAt(indexOfVariable).lastIndexOf(
		    currentFormattedData) >= 0) {
		
		if (TableOfVariable.elementAt(indexOfVariable).lastIndexOf(
			"char") >= 0) {
		    
		    scanDefault = scanCharacter;
		} else if (TableOfVariable.elementAt(indexOfVariable)
			.lastIndexOf("float") >= 0) {
		    
		    scanDefault = scanFloat;
		}
		break;
	    }
	}
	String dataToEmbed =
		currentFormattedData.substring(0, currentFormattedData
			.lastIndexOf(";"));

	String formattedLine = scanDefault + dataToEmbed + ")";

	if (!isDeclarada(currentFormattedData)) {
	    this.TableOfVariable.add(currentFormattedData);
	    currentInstructionFormat.add(currentFormattedData);
	}
	currentInstructionFormat.add(formattedLine);

    }

    private void processInteger(Vector<String> currentInstructionFormat,
	    String scanFormat, String currentDataInput) {
	String dataToEmbed =
		currentDataInput.substring(
			currentDataInput.lastIndexOf("int") + 3,
			currentDataInput.lastIndexOf(";"));

	String formattedLine = scanFormat + dataToEmbed + ");";

	if (!isDeclarada(currentDataInput)) {
	    this.TableOfVariable.add(currentDataInput);
	    currentInstructionFormat.add(currentDataInput);
	}
	currentInstructionFormat.add(formattedLine);
    }

    private void processCharacter(Vector<String> currentInstructionFormat,
	    String scanFormat, String currentDataInput) {
	String dataToEmbed =
		currentDataInput.substring(
			currentDataInput.lastIndexOf("char") + 4,
			currentDataInput.lastIndexOf(";"));

	String fromattedLine = scanFormat + dataToEmbed + ");";

	if (!isDeclarada(currentDataInput)) {
	    this.TableOfVariable.add(currentDataInput);
	    currentInstructionFormat.add(currentDataInput);
	}
	currentInstructionFormat.add(fromattedLine);

    }

    private void processFloat(Vector<String> currentInstructionFormat,
	    String scanFormat, String currentDataInput) {

	String dataToEmbed =
		currentDataInput.substring(currentDataInput
			.lastIndexOf("float") + 5, currentDataInput
			.lastIndexOf(";"));

	String fromattedLine = scanFormat + dataToEmbed + ");";

	if (!isDeclarada(currentDataInput)) {
	    this.TableOfVariable.add(currentDataInput);
	    currentInstructionFormat.add(currentDataInput);
	}
	currentInstructionFormat.add(fromattedLine);
    }

    private Vector<String> changeInstructionOfDataOutputForCodeC(String line) {
	Vector<String> toPrint = new Vector<String>();

	String data =
		line.substring(line.lastIndexOf(dataOutput) + 2,
			line.length() - 1);

	while (data.startsWith(" ")) {
	    data = data.substring(1);
	}

	toPrint.add(addPrinstSentence(data));

	return toPrint;
    }

    private String addPrinstSentence(String data) {
	String print = "printf(" + "\" ";
	
	String integer = "%d ";
	
	String character = "%c ";
	
	String flout = "%f ";
	
	String separator = ",";
	
	String close = ");";
	
	String datos[] = data.split(",");
	
	String instruction = print;

	String endOfInstruction = "";

	for (int i = 0; i < datos.length; i++) {
	    String tipo = this.getTypeOfData(datos[i]);
	    if (tipo != null) {
		if (tipo == "int") {
		    instruction = instruction + integer;
		} else if (tipo == "float") {
		    instruction = instruction + flout;
		} else {
		    instruction = instruction + character;
		}
		endOfInstruction =
			endOfInstruction + separator + datos[i];
	    } else {
		if (datos[i].contains("\"")) {
		    instruction =
			    instruction
				    + datos[i].substring(1,
					    datos[i].length() - 1);
		} else {
		    endOfInstruction =
			    endOfInstruction + separator + datos[i];
		}
	    }
	}

	instruction = instruction + "\"" + endOfInstruction + close;
	return instruction;
    }
}
