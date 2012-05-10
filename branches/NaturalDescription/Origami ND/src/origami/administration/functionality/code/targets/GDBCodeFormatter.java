package origami.administration.functionality.code.targets;

import origami.administration.functionality.code.AbstractInstructionFormatter;
import origami.administration.functionality.code.ManagerCodeFormat;
import origami.graphics.WindowWidgets;

public class GDBCodeFormatter extends AbstractInstructionFormatter {

    private final String IF_IDENTIFIER = "A5i9I";

    private final String WHILE_IDENTIFIER = "A5i9W";

    private CharSequence valueTag = "=";

    private CharSequence integerTag = "int";

    private CharSequence floatTag = "float";

    private CharSequence characterTag = "char";

    private CharSequence coutTag = "cout";

    
    private void fixStepByStep() {
	String setencesOfCode[] = this.sourceCode.split("\n");

	String copyOfSentences;

	int firsIterator;

	int fristIteratorHelper = 0;

	for (int indexSentence = 0; indexSentence < setencesOfCode.length; indexSentence++) {

	    if (indexSentence == 3) {

		setencesOfCode[indexSentence] =
			"{int " + IF_IDENTIFIER + "=0," + WHILE_IDENTIFIER
				+ "=0" + ";";
	    }
	    copyOfSentences = setencesOfCode[indexSentence];
	    while (copyOfSentences.startsWith(" ")) {

		copyOfSentences = copyOfSentences.substring(1);
	    }
	    if (copyOfSentences.startsWith("if(")
		    || copyOfSentences.startsWith("while(")
		    || copyOfSentences.startsWith("for(")) {

		firsIterator = setencesOfCode[indexSentence].indexOf(")");
		while (firsIterator != -1) {

		    fristIteratorHelper = firsIterator;
		    firsIterator =
			    setencesOfCode[indexSentence].indexOf(")",
				    fristIteratorHelper + 1);
		}
		String firstShortCircuitStatement =
			setencesOfCode[indexSentence].substring(0,
				fristIteratorHelper);

		String seconShortCircuitPiece = "&&" + IF_IDENTIFIER + "==0)";

		String tirthShortCircuitStatement =
			setencesOfCode[indexSentence]
				.substring(fristIteratorHelper + 1);

		setencesOfCode[indexSentence] =
			firstShortCircuitStatement + seconShortCircuitPiece
				+ tirthShortCircuitStatement;

		if (copyOfSentences.startsWith("if(")) {

		    keyHandler(indexSentence, setencesOfCode, "FinDelIf");
		} else {
		    keyHandler(indexSentence, setencesOfCode, "FinDelWhile");
		}
	    } else {

	    }
	}
	this.sourceCode = "";
	for (int setenceIndex = 0; setenceIndex < setencesOfCode.length; setenceIndex++) {

	    this.sourceCode =
		    this.sourceCode + setencesOfCode[setenceIndex] + "\n";
	}
    }

    private void keyHandler(int position, String[] setencesOfCode,
	    String typeOfSentence) {

	String secondSegmentOfCode;

	int isNotAkeyCounter = 0;

	for (int setenceIndex = position + 1; setenceIndex < setencesOfCode.length; setenceIndex++) {

	    if (setencesOfCode[setenceIndex].indexOf("for(") != -1) {

		isNotAkeyCounter++;
	    }
	    if (setencesOfCode[setenceIndex].indexOf("if(") != -1
		    || setencesOfCode[setenceIndex].indexOf("while(") != -1) {

		isNotAkeyCounter++;
	    }
	    if (setencesOfCode[setenceIndex].indexOf("else{") != -1) {

		isNotAkeyCounter++;
	    } else if (setencesOfCode[setenceIndex].indexOf("}") != -1
		    && isNotAkeyCounter == 0) {

		int actualPosition = setencesOfCode[setenceIndex].indexOf("}");

		String fristSegmentOfCode =
			setencesOfCode[setenceIndex].substring(0,
				actualPosition);

		if (typeOfSentence == "FinDelIf") {

		    secondSegmentOfCode = IF_IDENTIFIER + " = " + "0;}";
		} else {
		    secondSegmentOfCode = WHILE_IDENTIFIER + " = " + "0;}";
		}
		String thirdSegmentOfCode =
			setencesOfCode[setenceIndex]
				.substring(actualPosition + 1);
		setencesOfCode[setenceIndex] =
			fristSegmentOfCode + secondSegmentOfCode
				+ thirdSegmentOfCode;
		break;
	    } else if (setencesOfCode[setenceIndex].indexOf("}") != -1) {

		isNotAkeyCounter--;
	    }
	}

    }

    private boolean isValidSetence(int index, String[] sentencesOfCode) {

	return (index > 3 && (!sentencesOfCode[index].contains(coutTag)) && (sentencesOfCode[index]
		.contains(integerTag)
		|| sentencesOfCode[index].contains(floatTag) || sentencesOfCode[index]
		.contains(characterTag)));

    }

    private void initializedVariablesOfCode(int index, String[] sentencesOfCode) {
	String[] temporalSetence;
	if (sentencesOfCode[index].startsWith("int")) {

	    temporalSetence = sentencesOfCode[index].split(";");
	    sentencesOfCode[index] = temporalSetence[0] + " = 0;";
	} else if (sentencesOfCode[index].startsWith("char")) {

	    temporalSetence = sentencesOfCode[index].split(";");
	    sentencesOfCode[index] = temporalSetence[0] + " = '0';";

	} else if (sentencesOfCode[index].startsWith("float")) {

	    temporalSetence = sentencesOfCode[index].split(";");
	    sentencesOfCode[index] = temporalSetence[0] + " = 0.0;";
	}
    }

    private String initFormattedCode(String totalCodeToInitializade) {

	String[] sentencesOfCode = totalCodeToInitializade.split("\n");
	totalCodeToInitializade = "";

	for (int index = 0; index < sentencesOfCode.length; index++) {
	    if (isValidSetence(index, sentencesOfCode)) {
		if (!sentencesOfCode[index].contains(valueTag)) {
		    while (sentencesOfCode[index].startsWith(" ")) {
			sentencesOfCode[index] =
				sentencesOfCode[index].substring(1);
		    }
		    initializedVariablesOfCode(index, sentencesOfCode);
		}
	    } else if (sentencesOfCode[index].contains(coutTag)) {
		while (sentencesOfCode[index].startsWith(" ")) {

		    sentencesOfCode[index] =
			    sentencesOfCode[index].substring(1);
		}
		sentencesOfCode[index] =
			sentencesOfCode[index].replaceFirst("endl;",
				"\".,.\"<<endl;");
	    }
	    totalCodeToInitializade =
		    totalCodeToInitializade + sentencesOfCode[index] + "\n";
	}
	return totalCodeToInitializade;
    }

    @Override
    public void applyFormat() {
	ManagerCodeFormat manager =
		new ManagerCodeFormat(WindowWidgets.tabFolder
			.getTabItem().getLeaf().getDiagrama());
	manager.formatCodeCpp();
	this.sourceCode = initFormattedCode(manager.getInstructionsFormat());
	fixStepByStep();
    }
}
