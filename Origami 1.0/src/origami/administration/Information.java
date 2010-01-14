package origami.administration;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import origami.graphics.figures.*;

/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
@SuppressWarnings("serial")
public class Information implements Serializable {

    private Vector<String> information;

    private int aux = 0;

    private int hour;

    private int minute;

    private int second;

    private String beforeTime;

    private String startTime;

    private String endTime;

    private Date date;

    public Information() {
	information = new Vector<String>();
	date = new Date();
	hour = date.getHours();
	minute = date.getMinutes();
	second = date.getSeconds();
	startTime =
		String.valueOf(hour) + ":" + String.valueOf(minute) + ":"
			+ String.valueOf(second);
    }

    public void upDateTime() {
	date = new Date();
	hour = date.getHours();
	minute = date.getMinutes();
	second = date.getSeconds();
	information.add("/O - Se abrio el archivo....\n");
	startTime =
		String.valueOf(hour) + ":" + String.valueOf(minute) + ":"
			+ String.valueOf(second);
    }

    public Vector<String> getInformation() {
	return information;
    }

    public void setInformation(Vector<String> info) {
	this.information = info;
    }

    public void addInformation(String information) {
	this.information.add(information);
    }

    public void setFigure(FigureStructure figure) {
	if (figure instanceof DecisionFigure) {
	    information.add("La figura agregada de tipo \"si\"");
	} else if (figure instanceof ForFigure) {
	    information.add("La figura agregada de tipo \"para\"");
	} else if (figure instanceof WhileFigure) {
	    information.add("La figura agregada de tipo \"mientras\"");
	} else if (figure instanceof InputFigure) {
	    information.add("La figura agregada de tipo \"entrada\"");
	} else if (figure instanceof OutputFigure) {
	    information.add("La figura agregada de tipo \"salida\"");
	} else if (figure instanceof SentenceFigure) {
	    information.add("La figura agregada de tipo \"proceso\"");
	}
    }

    public void setDiagram(Vector<FigureStructure> diagram) {
	String information = "";
	String instruction;
	String tab = "";

	for (int index = 0; index < diagram.size(); index++) {
	    if (diagram.elementAt(index) instanceof DecisionFigure) {
		DecisionFigure temporalFigure =
			(DecisionFigure) diagram.elementAt(index);
		instruction =
			temporalFigure.instructionComposed.getFirstInstructionSimple();
		if (instruction.compareTo("null") != 0) {
		    instruction =
			    instruction.substring(3, instruction.length() - 2);
		}
		information =
			information + "\n" + tab + "If( " + instruction + " ){";
		tab = tab + "	";
		information =
			nestedDesition(information, tab, diagram, index + 2);
		// .............................................................
		if (tab.startsWith("\t")) {
		    tab = tab.substring(tab.indexOf("\t") + 1);
		}
		index = this.aux;
		information = information + "\n" + tab + "}\n" + tab + "Else{";
		tab = tab + "	";
	    } else if (diagram.elementAt(index) instanceof ForFigure) {
		ForFigure temporalFigure = (ForFigure) diagram.elementAt(index);
		instruction =
			temporalFigure.instructionComposed.getFirstInstructionSimple();
		if (instruction.compareTo("null") != 0) {
		    instruction =
			    instruction.substring(4, instruction.length() - 2);
		}
		information =
			information + "\n" + tab + "For( " + instruction
				+ " ){";
		tab = tab + "	";
		information =
			nestedCicles(information, tab, diagram, index + 1);
		index = this.aux;
		// .............................................................
		if (tab.startsWith("\t")) {
		    tab = tab.substring(tab.indexOf("\t") + 1);
		}
		information = information + "\n" + tab + "}";
		tab = "";
	    } else if (diagram.elementAt(index) instanceof WhileFigure) {
		WhileFigure temporalFigure =
			(WhileFigure) diagram.elementAt(index);
		instruction =
			temporalFigure.instructionComposed.getFirstInstructionSimple();
		if (instruction.compareTo("null") != 0) {
		    instruction =
			    instruction.substring(6, instruction.length() - 2);
		}
		information =
			information + "\n" + tab + "while(" + instruction
				+ " ){";
		tab = tab + "	";
		information =
			nestedCicles(information, tab, diagram, index + 1);
		index = this.aux;
		// ..............................................................
		if (tab.startsWith("\t")) {
		    tab = tab.substring(tab.indexOf("\t") + 1);
		}
		information = information + "\n" + tab + "}";
		tab = "";
	    } else if (diagram.elementAt(index) instanceof SentenceFigure) {
		SentenceFigure temporalFigure =
			(SentenceFigure) diagram.elementAt(index);
		instruction = temporalFigure.instruction.simpleInstruction;
		if (instruction.compareTo("null") != 0) {
		    instruction =
			    instruction.substring(0, instruction.length() - 1);
		}
		information =
			information + "\n" + tab + "Proceso( " + instruction
				+ " )";
	    } else if (diagram.elementAt(index) instanceof InputFigure) {
		InputFigure temporalFigure =
			(InputFigure) diagram.elementAt(index);
		instruction = temporalFigure.instruction.simpleInstruction;
		if (instruction.compareTo("null") != 0
			&& !instruction.isEmpty()) {
		    instruction =
			    instruction.substring(0, instruction.length() - 1);
		}
		information =
			information + "\n" + tab + "Entrada( " + instruction
				+ " )";
	    } else if (diagram.elementAt(index) instanceof OutputFigure) {
		OutputFigure temporalFigure =
			(OutputFigure) diagram.elementAt(index);
		instruction = temporalFigure.instruction.simpleInstruction;
		if (instruction.compareTo("null") != 0) {
		    instruction =
			    instruction.substring(2, instruction.length() - 1);
		}
		information =
			information + "\n" + tab + "Salida( " + instruction
				+ " )";
	    } else if (diagram.elementAt(index) instanceof EllipseFigure) {
		if (diagram.elementAt(index + 1) instanceof DecisionFigureEnd) {
		    if (tab.startsWith("\t")) {
			tab = tab.substring(tab.indexOf("\t") + 1);
		    }
		    information = information + "\n" + tab + "}";
		    index++;
		    tab = "";
		}
	    } else if (diagram.elementAt(index) instanceof CircleFigure) {
		if (index == 0) {
		    information = information + "\n\nInicio";
		} else {
		    information = information + "\nFin\n";
		}

	    }
	}
	this.information.add(information);
    }

    private String nestedCicles(String information, String tabulation,
	    Vector<FigureStructure> diagram, int position) {
	int count = 0;
	String tab = tabulation;
	String instruction;
	for (int index = position; index < diagram.size(); index++) {

	    if (diagram.elementAt(index) instanceof DecisionFigure) {

		DecisionFigure temporalFigureaux =
			(DecisionFigure) diagram.elementAt(index);
		instruction =
			temporalFigureaux.instructionComposed.getFirstInstructionSimple();
		if (instruction.compareTo("null") != 0) {
		    instruction =
			    instruction.substring(3, instruction.length() - 2);
		}
		information =
			information + "\n" + tab + "If( " + instruction + " ){";
		tab = tab + "	";
		information =
			nestedDesition(information, tab, diagram, index + 2);
		// .............................................................
		if (tab.startsWith("\t")) {
		    tab = tab.substring(tab.indexOf("\t") + 1);
		}
		index = this.aux;
		information = information + "\n" + tab + "}\n" + tab + "Else{";
		tab = tab + "	";
	    } else if (diagram.elementAt(index) instanceof ForFigure) {

		ForFigure temporalFigure = (ForFigure) diagram.elementAt(index);
		instruction =
			temporalFigure.instructionComposed.getFirstInstructionSimple();
		if (instruction.compareTo("null") != 0) {
		    instruction =
			    instruction.substring(4, instruction.length() - 2);
		}
		information =
			information + "\n" + tab + "For( " + instruction
				+ " ){";
		tab = tab + "	";
		information =
			nestedCicles(information, tab, diagram, index + 1);
		index = this.aux;
		// ..............................................................
		if (tab.startsWith("\t")) {
		    tab = tab.substring(tab.indexOf("\t") + 1);
		}
		information = information + "\n" + tab + "}";
	    } else if (diagram.elementAt(index) instanceof WhileFigure) {

		WhileFigure temporalFigure =
			(WhileFigure) diagram.elementAt(index);
		instruction =
			temporalFigure.instructionComposed.getFirstInstructionSimple();
		if (instruction.compareTo("null") != 0) {
		    instruction =
			    instruction.substring(6, instruction.length() - 2);
		}
		information =
			information + "\n" + tab + "while(" + instruction
				+ " ){";
		tab = tab + "	";
		information =
			nestedCicles(information, tab, diagram, index + 1);
		index = this.aux;
		// ..............................................................
		if (tab.startsWith("\t")) {
		    tab = tab.substring(tab.indexOf("\t") + 1);
		}
		information = information + "\n" + tab + "}";
	    } else if (diagram.elementAt(index) instanceof SentenceFigure) {

		SentenceFigure temporalFigure =
			(SentenceFigure) diagram.elementAt(index);
		instruction = temporalFigure.instruction.simpleInstruction;
		if (instruction.compareTo("null") != 0) {
		    instruction =
			    instruction.substring(0, instruction.length() - 1);
		}
		information =
			information + "\n" + tab + "Proceso( " + instruction
				+ " )";
	    } else if (diagram.elementAt(index) instanceof InputFigure) {

		InputFigure temporalFigure =
			(InputFigure) diagram.elementAt(index);
		instruction = temporalFigure.instruction.simpleInstruction;
		if (instruction.compareTo("null") != 0) {
		    instruction =
			    instruction.substring(0, instruction.length() - 1);
		}
		information =
			information + "\n" + tab + "Entrada( " + instruction
				+ " )";
	    } else if (diagram.elementAt(index) instanceof OutputFigure) {

		OutputFigure temporalFigure =
			(OutputFigure) diagram.elementAt(index);
		instruction = temporalFigure.instruction.simpleInstruction;
		if (instruction.compareTo("null") != 0) {
		    instruction =
			    instruction.substring(2, instruction.length() - 1);
		}
		information =
			information + "\n" + tab + "Salida( " + instruction
				+ " )";
	    } else if (diagram.elementAt(index) instanceof EllipseFigure) {

		if (diagram.elementAt(index + 1) instanceof DecisionFigureEnd) {

		    if (tab.startsWith("\t")) {
			tab = tab.substring(tab.indexOf("\t") + 1);
		    }
		    information = information + "\n" + tab + "}";
		    index++;

		} else {
		    count++;
		    if (count == 6) {
			this.aux = index;
			index = diagram.size() + 1;
			break;
		    }
		}
	    }
	}
	return information;
    }

    private String nestedDesition(String information, String tabulation,
	    Vector<FigureStructure> diagram, int position) {

	String tab = tabulation;
	String instruccion;
	for (int index = position; index < diagram.size(); index++) {

	    if (diagram.elementAt(index) instanceof DecisionFigure) {

		DecisionFigure temporalFigure =
			(DecisionFigure) diagram.elementAt(index);
		instruccion =
			temporalFigure.instructionComposed.getFirstInstructionSimple();
		if (instruccion.compareTo("null") != 0) {
		    instruccion =
			    instruccion.substring(3, instruccion.length() - 2);
		}
		information =
			information + "\n" + tab + "If( " + instruccion + " ){";
		tab = tab + "	";
		information =
			nestedDesition(information, tab, diagram, index + 2);
		// .............................................................
		if (tab.startsWith("\t")) {
		    tab = tab.substring(tab.indexOf("\t") + 1);
		}
		index = this.aux;
		information = information + "\n" + tab + "}\n" + tab + "Else{";
		tab = tab + "	";
	    } else if (diagram.elementAt(index) instanceof ForFigure) {

		ForFigure temporalFigure = (ForFigure) diagram.elementAt(index);
		instruccion =
			temporalFigure.instructionComposed.getFirstInstructionSimple();
		if (instruccion.compareTo("null") != 0) {
		    instruccion =
			    instruccion.substring(4, instruccion.length() - 2);
		}
		information =
			information + "\n" + tab + "For( " + instruccion
				+ " ){";
		tab = tab + "	";
		information =
			nestedCicles(information, tab, diagram, index + 1);
		index = this.aux;
		// ..............................................................
		if (tab.startsWith("\t")) {
		    tab = tab.substring(tab.indexOf("\t") + 1);
		}
		information = information + "\n" + tab + "}";
	    } else if (diagram.elementAt(index) instanceof WhileFigure) {

		WhileFigure temporalFigure =
			(WhileFigure) diagram.elementAt(index);
		instruccion =
			temporalFigure.instructionComposed.getFirstInstructionSimple();
		if (instruccion.compareTo("null") != 0) {
		    instruccion =
			    instruccion.substring(6, instruccion.length() - 2);
		}
		information =
			information + "\n" + tab + "while(" + instruccion
				+ " ){";
		tab = tab + "	";
		information =
			nestedCicles(information, tab, diagram, index + 1);
		index = this.aux;
		// ..............................................................
		if (tab.startsWith("\t")) {
		    tab = tab.substring(tab.indexOf("\t") + 1);
		}
		information = information + "\n" + tab + "}";
	    } else if (diagram.elementAt(index) instanceof SentenceFigure) {

		SentenceFigure temporalFigure =
			(SentenceFigure) diagram.elementAt(index);
		instruccion = temporalFigure.instruction.simpleInstruction;
		if (instruccion.compareTo("null") != 0) {
		    instruccion =
			    instruccion.substring(0, instruccion.length() - 1);
		}
		information =
			information + "\n" + tab + "Proceso( " + instruccion
				+ " )";
	    } else if (diagram.elementAt(index) instanceof InputFigure) {

		InputFigure temporalFigure =
			(InputFigure) diagram.elementAt(index);
		instruccion = temporalFigure.instruction.simpleInstruction;
		if (instruccion.compareTo("null") != 0) {
		    instruccion =
			    instruccion.substring(0, instruccion.length() - 1);
		}
		information =
			information + "\n" + tab + "Entrada( " + instruccion
				+ " )";
	    } else if (diagram.elementAt(index) instanceof OutputFigure) {

		OutputFigure temporalFigure =
			(OutputFigure) diagram.elementAt(index);
		instruccion = temporalFigure.instruction.simpleInstruction;
		if (instruccion.compareTo("null") != 0) {
		    instruccion =
			    instruccion.substring(2, instruccion.length() - 1);
		}
		information =
			information + "\n" + tab + "Salida( " + instruccion
				+ " )";
	    } else if (diagram.elementAt(index) instanceof EllipseFigure) {

		if (diagram.elementAt(index + 1) instanceof EllipseFigure) {
		    index++;
		    this.aux = index;
		    index = diagram.size() + 1;
		    break;
		} else if (diagram.elementAt(index + 1) instanceof DecisionFigureEnd) {
		    if (tab.startsWith("\t")) {
			tab = tab.substring(tab.indexOf("\t") + 1);
		    }
		    information = information + "\n" + tab + "}";
		    index++;

		}
	    }
	}
	return information;
    }

    public void addTime() {
	date = new Date();
	int hora = date.getHours();
	int minuto = date.getMinutes();
	int segundo = date.getSeconds();

	endTime =
		String.valueOf(hora) + ":" + String.valueOf(minuto) + ":"
			+ String.valueOf(segundo);

	String time = "Tiempo total = " + startTime + "-" + endTime;
	information.add(time);
    }

    public void removeTime() {
	information.removeElementAt(information.size() - 1);
    }

}
