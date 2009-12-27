package Administracion.Funcionalidad.Codigo;

import java.io.Serializable;
import java.util.Vector;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;
import Administracion.Figura;
import Administracion.Funcionalidad.Exporter;
import Grafico.*;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;
import Imagenes.ImageLoader;

/**
 * Esta clase es la base administra el codigo agregado por el usuario. TODO
 * Extract Class de parte grafica
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 * 
 */
public class Instruccion implements Serializable {

    private Vector<String> code = new Vector<String>();

    private Vector<String> variablesTable = new Vector<String>();

    private String identator = "      ";

    public String totalCode = new String();

    public Vector<String> getVariablesTable() {
	return variablesTable;
    }

    public Vector<String> main(Vector<Figura> diagrama) {
	addFiguresInstructionToString(diagrama, 1);
	return code;

	// ME TIENE QUE REGRESAR EL DIAGRAMA PARA QUE LO VUELVA A METER
	// vectorCiclos(diagrama);
    }

    public void generateGDB(Vector<Figura> diagrama) {
	// addFiguresInstructionToString(diagrama,1);
	// codigoCoCmasMas.setInstrucciones(codigo,TablaVariables);
	// codigoTotal = codigoCoCmasMas.getCodigoCmasmas();
	// vectorCiclos(diagrama);
	// codigoTotal = codigoCoCmasMas.getCodigoGDB(codigoTotal);
	// ArreglarPasoAPaso();
    }

    private boolean isValidInstruction(String decision) {
	boolean notIsNull = decision.compareTo("null") != 0;
	boolean notIsEmpty = decision.compareTo("") != 0;
	return notIsNull && notIsEmpty;
    }

    private int addDecisionFigureInstructionToString(Vector<Figura> figures,
	    int index) {

	DecisionFigure f = (DecisionFigure) figures.elementAt(index);

	int outputIndex = index;

	int codeSize = 0;

	if (f.instruction.instruccion.elementAt(0) != null) {

	    if (isValidInstruction(f.instruction.instruccion.firstElement()
		    .getInstruccionSimple())) {

		code.add(identator
			+ f.instruction.instruccion.elementAt(0)
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

	ForFigure f = (ForFigure) figures.elementAt(index);

	int outputIndex = index;

	if (f.instruction.instruccion.elementAt(0) != null) {

	    if (isValidInstruction(f.instruction.instruccion.firstElement()
		    .getInstruccionSimple())) {

		code.add(identator
			+ f.instruction.instruccion.elementAt(0)
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

	WhileFigure f = (WhileFigure) figures.elementAt(index);

	int outputIndex = index;

	if (f.instruccion.instruccion.elementAt(0) != null) {

	    if (isValidInstruction(f.instruccion.instruccion.firstElement()
		    .getInstruccionSimple())) {

		code.add(identator
			+ f.instruccion.instruccion.elementAt(0)
				.getInstruccionSimple());
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

	InputFigure f = ((InputFigure) figures.elementAt(index));

	int outputIndex = index;

	if (f.instruction.getInstruccionSimple() != null) {
	    String[] instructions;
	    String instruction = f.instruction.getInstruccionSimple();
	    instructions = instruction.split(";");

	    for (int instructionIndex = 0; instructionIndex < instructions.length; instructionIndex++) {

		if (instructions[instructionIndex].indexOf(",") != -1) {

		    Vector<String> moreVariables =
			    moreVariables(instructions[instructionIndex], null);

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

	SentenceFigure f = (SentenceFigure) figures.elementAt(index);

	int outputIndex = index;

	if (f.instruccion.getInstruccionSimple() != null) {
	    code.add(identator + f.instruccion.getInstruccionSimple());
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
	return previousDeclaration;
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
	// aux = "Leer: " + aux;
	moreVariables.remove(variableIndex);
	moreVariables.insertElementAt(variable, variableIndex);
	return declaration;
    }

    /**
     * Este metodo recorre el vector de figuras y agrega cada instruccion de
     * cada figura a un vector de string.
     * 
     * @param diagrama
     * @param x
     * @return int
     */
    public int addFiguresInstructionToString(Vector<Figura> figures,
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

    /*
     * este es el metodo que tengo que checar para separar los arreglos con las
     * comas..
     */
    public Vector<String> moreVariables(String line, String tipe) {
	Vector<String> variables = new Vector<String>();

	String previousTipe = tipe;

	String aux;

	int beforeComa = line.indexOf(",");

	int afterComa = line.indexOf(",", beforeComa + 1);

	// en caso de que solo halla una coma
	if (afterComa == -1) {
	    // analizar la variable aux para ver si no es una declaracion de un
	    // array
	    if (beforeComa == -1) { // caso especial donde solo hay dos un
		// array y una variable
		aux = line;
		while (aux.startsWith(" ")) {
		    aux = aux.substring(1);
		}
		if (aux.startsWith("int")) {
		    previousTipe = "int";
		} else if (aux.startsWith("char")) {
		    previousTipe = "char";
		} else if (aux.startsWith("float")) {
		    previousTipe = "float";
		}
		if (previousTipe != null) {
		    aux = previousTipe + " " + aux;
		}
		variables.add(aux);

	    } else {
		aux = line.substring(0, beforeComa);
		int array = aux.indexOf("{");
		// aca checo si es un arreglo de dos posiciones.
		if (array != -1) {
		    aux = line;
		    while (aux.startsWith(" ")) {
			aux = aux.substring(1);
		    }
		    // checar si tiene tipo si no agregar
		    // ********************************************no se si deba
		    // ir
		    if (aux.startsWith("int")) {
			previousTipe = "int";
		    } else if (aux.startsWith("char")) {
			previousTipe = "char";
		    } else if (aux.startsWith("float")) {
			previousTipe = "float";
		    } else {
			if (previousTipe != null) {
			    aux = previousTipe + " " + aux;
			}
		    }
		    // agrego //********************************************no
		    // se si deba ir
		    variables.add(aux);
		} else {
		    while (aux.startsWith(" ")) {
			aux = aux.substring(1);
		    }
		    if (aux.startsWith("int")) {
			previousTipe = "int";
		    } else if (aux.startsWith("char")) {
			previousTipe = "char";
		    } else if (aux.startsWith("float")) {
			previousTipe = "float";
		    } else {
			if (previousTipe != null) {
			    aux = previousTipe + " " + aux;
			}
		    }
		    variables.add(aux);
		    aux = line.substring(beforeComa + 1);
		    while (aux.startsWith(" ")) {
			aux = aux.substring(1);
		    }
		    if (aux.startsWith("int") || aux.startsWith("char")
			    || aux.startsWith("float")) {
			variables.add(aux);
		    } else {
			if (previousTipe != null) {
			    aux = previousTipe + " " + aux;
			}
			variables.add(aux);
		    }
		}
	    }
	} else {
	    // aca debo de checar si hay un array
	    aux = line.substring(0, beforeComa);
	    //System.out.println("<" + aux + ">");

	    // ********************************************************************
	    int array = aux.indexOf("{");
	    // aca checo si es un arreglo de dos posiciones.
	    int llaves = 0;
	    String declaracion;
	    if (array != -1) { // es un array
		llaves++;
		declaracion = aux;
		// ***** leo todos las llaves abiertas
		array = aux.indexOf("{", array + 1);
		while (array != -1) {
		    llaves++;
		    array = aux.indexOf("{", array + 1);
		}

		while (llaves != 0) {
		    if (afterComa == -1) {
			aux = line.substring(beforeComa, line.length());
			declaracion = declaracion + aux;
			break;
		    }
		    aux = line.substring(beforeComa, afterComa);
		    // **************
		    array = -1;
		    array = aux.indexOf("{", array + 1);
		    while (array != -1) {
			llaves++;
			array = aux.indexOf("{", array + 1);
		    }

		    array = -1;
		    array = aux.indexOf("}", array + 1);
		    while (array != -1) {
			llaves--;
			array = aux.indexOf("}", array + 1);
		    }
		    /*
		     * //**************** array=aux.indexOf("{"); if(array!=-1){
		     * llaves++; } array=aux.indexOf("}"); if(array!=-1){
		     * llaves--; }
		     */
		    // actualizar las comas
		    beforeComa = afterComa;
		    afterComa = line.indexOf(",", beforeComa + 1);
		    declaracion = declaracion + aux;
		}

		while (declaracion.startsWith(" ")) {
		    declaracion = declaracion.substring(1);
		}
		String auu = declaracion;
		if (declaracion.startsWith("int")) {
		    previousTipe = "int";
		} else if (declaracion.startsWith("char")) {
		    previousTipe = "char";
		} else if (declaracion.startsWith("float")) {
		    previousTipe = "float";
		} else {
		    if (previousTipe != null) {
			declaracion = previousTipe + " " + declaracion;
		    }
		}
		variables.add(declaracion);

		if (afterComa != -1 || line.indexOf(",", auu.length()) != -1) {
		    // System.out.println(linea.substring(comaAnterior+1));
		    Vector<String> variables2 =
			    moreVariables(line.substring(beforeComa + 1),
				    previousTipe);
		    for (int i = 0; i < variables2.size(); i++) {
			variables.add(variables2.elementAt(i));
		    }
		} else {
		}

	    } else {
		// no es un array...
		// ***********************************************************************
		while (aux.startsWith(" ")) {
		    aux = aux.substring(1);
		}
		if (aux.startsWith("int")) {
		    previousTipe = "int";
		} else if (aux.startsWith("char")) {
		    previousTipe = "char";
		} else if (aux.startsWith("float")) {
		    previousTipe = "float";
		} else {
		    if (previousTipe != null) {
			aux = previousTipe + " " + aux;
		    }
		}
		variables.add(aux);
		Vector<String> variables2 =
			moreVariables(line.substring(beforeComa + 1),
				previousTipe);
		for (int i = 0; i < variables2.size(); i++) {
		    variables.add(variables2.elementAt(i));
		}
	    }

	}
	return variables;
    }

    /**
     * Este metodo se encarga de encontrar el final de un ciclo If.
     * 
     * @param cont
     * @return int
     */
    public int[] findFigureEnd(int cont, String controlFigure,
	    String firstFigure, String secondFigure) {

	int inicio = 0;

	int fin = 0;

	int index;

	int[] iniFin = new int[2];

	char ciclo = '{';

	char finCiclo = '}';

	boolean bandera = false;

	for (index = 0; index < code.size(); index++) {

	    if ((inicio == fin) && (inicio != 0)) {

		break;
	    }
	    if (code.elementAt(index).indexOf(ciclo) != -1
		    && code.elementAt(index).indexOf(controlFigure) != -1) {

		inicio++;
		if (cont == inicio) {

		    cont = 0;
		    inicio = 1;
		    bandera = true;
		    iniFin[0] = index;
		}
	    } else if (code.elementAt(index).indexOf(finCiclo) != -1 && bandera) {

		fin++;
	    } else if ((code.elementAt(index).indexOf("else") != -1
		    || code.elementAt(index).indexOf(firstFigure) != -1 || code
		    .elementAt(index).indexOf(secondFigure) != -1)
		    && bandera) {

		fin--;
	    }
	}
	iniFin[1] = index - 1;
	return iniFin;
    }

    /**
     * Este metodo se encarga de agregar a cada instruccion de cada ciclo el
     * codigo que es parte de su bloque.
     * 
     * @param diagrama
     */
    public void ciclesVector(Vector<Figura> diagrama) {
	int countIf = 0;

	int countFor = 0;

	int countWhile = 0;

	int[] endPosition = new int[2];

	for (int index = 1; index < diagrama.size(); index++) {

	    if (diagrama.elementAt(index) instanceof DecisionFigure) {

		countIf++;
		endPosition = findFigureEnd(countIf, "if", "while", "for");
		method(endPosition, index, diagrama);
	    } else if (diagrama.elementAt(index) instanceof ForFigure) {

		countFor++;
		endPosition = findFigureEnd(countFor, "for", "if", "while");
		method(endPosition, index, diagrama);
	    } else if (diagrama.elementAt(index) instanceof WhileFigure) {

		countWhile++;
		endPosition = findFigureEnd(countWhile, "while", "if", "for");
		method(endPosition, index, diagrama);
	    } else if (diagrama.elementAt(index) instanceof OutputFigure) {

		break;
	    }
	}
    }

    private void removeElements(int index, Vector<Figura> diagram) {
	if (diagram.elementAt(index) instanceof DecisionFigure) {

	    DecisionFigure f = (DecisionFigure) diagram.elementAt(index);
	    f.instruction.instruccion.removeAllElements();
	} else if (diagram.elementAt(index) instanceof ForFigure) {

	    ForFigure f = (ForFigure) diagram.elementAt(index);
	    f.instruction.instruccion.removeAllElements();
	} else {

	    WhileFigure f = (WhileFigure) diagram.elementAt(index);
	    f.instruccion.instruccion.removeAllElements();
	}
    }

    private void setElements(int index, Vector<Figura> diagram) {
	if (diagram.elementAt(index) instanceof DecisionFigure) {

	    DecisionFigure f = (DecisionFigure) diagram.elementAt(index);
	    diagram.setElementAt(f, index);
	} else if (diagram.elementAt(index) instanceof ForFigure) {

	    ForFigure f = (ForFigure) diagram.elementAt(index);
	    diagram.setElementAt(f, index);
	} else {

	    WhileFigure f = (WhileFigure) diagram.elementAt(index);
	    diagram.setElementAt(f, index);
	}
    }

    private void setInstruction(int index, Vector<Figura> diagram,
	    InstruccionSimple instruction) {
	if (diagram.elementAt(index) instanceof DecisionFigure) {

	    DecisionFigure f = (DecisionFigure) diagram.elementAt(index);
	    f.instruction.instruccion.add(instruction);
	} else if (diagram.elementAt(index) instanceof ForFigure) {

	    ForFigure f = (ForFigure) diagram.elementAt(index);
	    f.instruction.instruccion.add(instruction);
	} else {

	    WhileFigure f = (WhileFigure) diagram.elementAt(index);
	    f.instruccion.instruccion.add(instruction);
	}
    }

    public void method(int[] position, int index, Vector<Figura> diagram) {
	removeElements(index, diagram);
	for (int m = 0; m < code.size(); m++) {

	    if (m >= position[0] && m <= position[1]) {

		InstruccionSimple instruction = new InstruccionSimple();

		int maxSize = 0;

		String copia = code.elementAt(m);

		while (code.elementAt(m).length() > maxSize) {

		    if (copia.startsWith(" ")) {

			copia = copia.replaceFirst(" ", "");
		    }
		    maxSize++;
		}
		code.setElementAt(copia, m);
		instruction.setInstruccionSimple(code.elementAt(m));
		setInstruction(index, diagram, instruction);
	    }
	}
	setElements(index, diagram);
    }

    /**
     * Este metodo se encarga de definir el tabulado que se usara en la
     * siguiente linea.
     * 
     * @param x
     */
    private void tabula(boolean isMayor) {
	if (isMayor) {
	    
	    identator = identator + "    ";
	} else {
	    identator = identator.replaceFirst("    ", "");
	}
    }

    public void fixStepByStep() {
	String code[] = totalCode.split("\n");
	
	String codeCopy;
	
	String variable = "A5i9I";
	
	String variable2 = "A5i9W";
	
	int firstCount;
	
	int secondCount = 0;
	
	for (int index = 0; index < code.length; index++) {
	    
	    if (index == 3) {
		
		code[index] = "{int " + variable + "=0," + variable2 + "=0" + ";";
	    }
	    codeCopy = code[index];
	    while (codeCopy.startsWith(" ")) {
		
		codeCopy = codeCopy.substring(1);
	    }
	    if (codeCopy.startsWith("if(") || codeCopy.startsWith("while(")
		    || codeCopy.startsWith("for(")) {
		
		// busco la ultima ")"
		firstCount = code[index].indexOf(")");
		while (firstCount != -1) {
		    
		    secondCount = firstCount;
		    firstCount = code[index].indexOf(")", secondCount + 1);
		}
		String firstPiece = code[index].substring(0, secondCount);
		
		String secondPiece = "&&" + variable + "==0)";
		
		String thirtPiece = code[index].substring(secondCount + 1);
		
		code[index] = firstPiece + secondPiece + thirtPiece;
		
		if (codeCopy.startsWith("if(")) {
		    
		    keys(index, code, "FinDelIf");
		} else {
		    keys(index, code, "FinDelWhile");
		}
	    } else {

	    }
	}
	totalCode = "";
	for (int i = 0; i < code.length; i++) {
	    
	    totalCode = totalCode + code[i] + "\n";
	}
    }

    public void keys(int position, String[] aux, String print) {
	String variable = "A5i9I";
	
	String variable2 = "A5i9W";
	
	String seg;
	
	int notIsKeyCount = 0;
	
	for (int i = position + 1; i < aux.length; i++) {
	    
	    if (aux[i].indexOf("for(") != -1) {
		
		notIsKeyCount++;
	    }
	    if (aux[i].indexOf("if(") != -1 || aux[i].indexOf("while(") != -1) {
		
		notIsKeyCount++;
	    }
	    if (aux[i].indexOf("else{") != -1) {
		
		notIsKeyCount++;
	    } else if (aux[i].indexOf("}") != -1 && notIsKeyCount == 0) {
		
		int actualPosition = aux[i].indexOf("}");
		
		String prim = aux[i].substring(0, actualPosition);
		
		// String seg = "cout<<"+"\""+imprimir+"\""+"<< endl;}";
		if (print == "FinDelIf") {
		    
		    seg = variable + " = " + "0;}";
		} else {
		    seg = variable2 + " = " + "0;}";
		}
		String ter = aux[i].substring(actualPosition + 1);
		aux[i] = prim + seg + ter;
		break;
	    } else if (aux[i].indexOf("}") != -1) {
		
		notIsKeyCount--;
	    }
	}

    }
}