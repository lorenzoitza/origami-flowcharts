package origami.administration;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import origami.graphics.figures.*;
import origami.graphics.view.SaveFileView;

/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
@SuppressWarnings("serial")
public class Information implements Serializable {

    private Vector<String> information;

    private String format = "dd/MM/yyyy HH:mm:ss";
    private String formatToday = "dd/MM/yyyy";
    
    private String[] FileManagement = {"NEW","OPEN","SAVE","CLOSE"};
    private String[] FigureManagement = {"ADD","DELETE","EDIT"};
    private String[] ExecutionManagement = {"COMPIL","TEST","STOP","FULL"};
    
    private String secondRepresentation="SECOND";
    private String instructionExecution="";
    private String textNotSave="<NotSave>";
    
    public static int OPEN=1;
    public static int SAVE=2;
    public static int CLOSE=3;
    
    public static int ADD=0;
    public static int DELETE=1;
    public static int EDIT=2;
    
    public static int COMPIL=0;
    public static int TEST=1;
    public static int STOP=2;
    public static int FULL=3;
    
    public Information() {
	information = new Vector<String>();
    }
    public void initDiagram(){
	Calendar calendar = Calendar.getInstance();
	information.add(getDate(calendar,this.format)+" "+FileManagement[0]);
    }
    public void updateFile(int action){
	Calendar calendar = Calendar.getInstance();
	information.add(getDate(calendar,this.format)+" "+FileManagement[action]);
	if(action==this.SAVE){
	    deleteInfoNotSave();
	}
    }
    public void secondRepresentation(){
	Calendar calendar = Calendar.getInstance();
	information.add(getDate(calendar,this.format)+" "+ secondRepresentation );
    }
    
    public void updateFileAndNotSave(int action){
	Calendar calendar = Calendar.getInstance();
	information.add(getDate(calendar,this.format)+" "+FileManagement[action]);
	//guardar unicamente el log...
	saveLog();
    }
    public void addInformationExecution(int action,String message){
	Calendar calendar = Calendar.getInstance();
	if(action==this.STOP || action==this.FULL){
	    information.add(this.instructionExecution);
	    information.add(getDate(calendar,this.format)+" "+ExecutionManagement[action]+" "+message);
	    save();
	}
	else if(action==this.TEST){
	    this.instructionExecution = getDate(calendar,this.format)+" "+ExecutionManagement[action]+" ; ";
	}
	else{
	    information.add(getDate(calendar,this.format)+" "+ExecutionManagement[action]+" "+message);
	    save();
	}
    }
    public void save(){
	SaveFileView save = new SaveFileView();
	save.saveAction();
    }
    public void saveLog(){
	SaveFileView save = new SaveFileView();
	save.saveLog();
    }
    public void addInformationFigure(FigureStructure figure,int action) {
	Calendar calendar = Calendar.getInstance();
	if (figure instanceof DecisionFigure) {
	    information.add(getDate(calendar,this.format)+" "+FigureManagement[action]+" Decision "+figure.getId()+" "+((DecisionFigure)figure).getInstructionCode()+" "+this.textNotSave);
	} else if (figure instanceof ForFigure) {
	    information.add(getDate(calendar,this.format)+" "+FigureManagement[action]+" For "+figure.getId()+" "+((ForFigure)figure).getInstructionCode()+" "+this.textNotSave);
	} else if (figure instanceof WhileFigure) {
	    information.add(getDate(calendar,this.format)+" "+FigureManagement[action]+" While "+figure.getId()+" "+((WhileFigure)figure).getInstructionCode()+" "+this.textNotSave);
	} else if (figure instanceof InputFigure) {
	    information.add(getDate(calendar,this.format)+" "+FigureManagement[action]+" Input "+figure.getId()+" "+((InputFigure)figure).getInstructionCode()+" "+this.textNotSave);
	} else if (figure instanceof OutputFigure) {
	    information.add(getDate(calendar,this.format)+" "+FigureManagement[action]+" Output "+figure.getId()+" "+((OutputFigure)figure).getInstructionCode()+" "+this.textNotSave);
	} else if (figure instanceof SentenceFigure) {
	    information.add(getDate(calendar,this.format)+" "+FigureManagement[action]+" Expression "+figure.getId()+" "+((SentenceFigure)figure).getInstructionCode()+" "+this.textNotSave);
	}
    }
    
    public static String getDate(Calendar date,String format){
//	"yyyy-MM-dd HH:mm:ss"
	SimpleDateFormat dateFormat = new SimpleDateFormat(format);

	return dateFormat.format(date.getTime());
    }
    
    private void deleteInfoNotSave(){
	Calendar calendar = Calendar.getInstance();
	for(int index=0; index<information.size(); index++){
	    String[] info = information.elementAt(index).split(" ");
	    System.out.println(info[info.length-1]);
	    if(info[0].startsWith(getDate(calendar,this.formatToday)) && info[info.length-1].endsWith(this.textNotSave)){
		System.out.println("entroooo");
		String temp = information.elementAt(index);
		information.remove(index);
		information.insertElementAt(temp.substring(0,temp.length()-11), index);
	    }
	}
    }
    
    public void addInstructionExecutionInput(String input){
	int index = this.instructionExecution.lastIndexOf(";");
	this.instructionExecution = this.instructionExecution.substring(0,index-1)+" "+input+" ;"+this.instructionExecution.substring(index+1);
    }
    
    public void addInstructionExecutionOutput(String output){
	int index = this.instructionExecution.lastIndexOf(";");
	this.instructionExecution = this.instructionExecution.substring(0,index-1)+"; "+output+" "+this.instructionExecution.substring(index+1);
    }
    
    
    private int aux = 0;

    private int hour;

    private int minute;

    private int second;

    private String beforeTime;

    private String startTime;

    private String endTime;

    private Date date;

//    public Information() {
//	information = new Vector<String>();
//	date = new Date();
//	hour = date.getHours();
//	minute = date.getMinutes();
//	second = date.getSeconds();
//	startTime =
//		String.valueOf(hour) + ":" + String.valueOf(minute) + ":"
//			+ String.valueOf(second);
//    }

//    public void upDateTime() {
//	date = new Date();
//	hour = date.getHours();
//	minute = date.getMinutes();
//	second = date.getSeconds();
//	information.add("/O - Se abrio el archivo....\n");
//	startTime =
//		String.valueOf(hour) + ":" + String.valueOf(minute) + ":"
//			+ String.valueOf(second);
//    }

    public Vector<String> getInformation() {
	return information;
    }

    public void setInformation(Vector<String> info) {
	this.information = info;
    }

//    public void addInformation(String information) {
//	this.information.add(information);
//    }

//    public void setFigure(FigureStructure figure) {
//	if (figure instanceof DecisionFigure) {
//	    information.add("La figura agregada de tipo \"si\"");
//	} else if (figure instanceof ForFigure) {
//	    information.add("La figura agregada de tipo \"para\"");
//	} else if (figure instanceof WhileFigure) {
//	    information.add("La figura agregada de tipo \"mientras\"");
//	} else if (figure instanceof InputFigure) {
//	    information.add("La figura agregada de tipo \"entrada\"");
//	} else if (figure instanceof OutputFigure) {
//	    information.add("La figura agregada de tipo \"salida\"");
//	} else if (figure instanceof SentenceFigure) {
//	    information.add("La figura agregada de tipo \"proceso\"");
//	}
//    }

//    public void setDiagram(Vector<FigureStructure> diagram) {
//	String information = "";
//	String instruction;
//	String tab = "";
//
//	for (int index = 0; index < diagram.size(); index++) {
//	    if (diagram.elementAt(index) instanceof DecisionFigure) {
//		DecisionFigure temporalFigure =
//			(DecisionFigure) diagram.elementAt(index);
//		instruction =
//			temporalFigure.instructionComposed.getFirstInstructionSimple();
//		if (instruction.compareTo("null") != 0) {
//		    instruction =
//			    instruction.substring(3, instruction.length() - 2);
//		}
//		information =
//			information + "\n" + tab + "If( " + instruction + " ){";
//		tab = tab + "	";
//		information =
//			nestedDesition(information, tab, diagram, index + 2);
//		// .............................................................
//		if (tab.startsWith("\t")) {
//		    tab = tab.substring(tab.indexOf("\t") + 1);
//		}
//		index = this.aux;
//		information = information + "\n" + tab + "}\n" + tab + "Else{";
//		tab = tab + "	";
//	    } else if (diagram.elementAt(index) instanceof ForFigure) {
//		ForFigure temporalFigure = (ForFigure) diagram.elementAt(index);
//		instruction =
//			temporalFigure.instructionComposed.getFirstInstructionSimple();
//		if (instruction.compareTo("null") != 0) {
//		    instruction =
//			    instruction.substring(4, instruction.length() - 2);
//		}
//		information =
//			information + "\n" + tab + "For( " + instruction
//				+ " ){";
//		tab = tab + "	";
//		information =
//			nestedCicles(information, tab, diagram, index + 1);
//		index = this.aux;
//		// .............................................................
//		if (tab.startsWith("\t")) {
//		    tab = tab.substring(tab.indexOf("\t") + 1);
//		}
//		information = information + "\n" + tab + "}";
//		tab = "";
//	    } else if (diagram.elementAt(index) instanceof WhileFigure) {
//		WhileFigure temporalFigure =
//			(WhileFigure) diagram.elementAt(index);
//		instruction =
//			temporalFigure.instructionComposed.getFirstInstructionSimple();
//		if (instruction.compareTo("null") != 0) {
//		    instruction =
//			    instruction.substring(6, instruction.length() - 2);
//		}
//		information =
//			information + "\n" + tab + "while(" + instruction
//				+ " ){";
//		tab = tab + "	";
//		information =
//			nestedCicles(information, tab, diagram, index + 1);
//		index = this.aux;
//		// ..............................................................
//		if (tab.startsWith("\t")) {
//		    tab = tab.substring(tab.indexOf("\t") + 1);
//		}
//		information = information + "\n" + tab + "}";
//		tab = "";
//	    } else if (diagram.elementAt(index) instanceof SentenceFigure) {
//		SentenceFigure temporalFigure =
//			(SentenceFigure) diagram.elementAt(index);
//		instruction = temporalFigure.instruction.simpleInstruction;
//		if (instruction.compareTo("null") != 0) {
//		    instruction =
//			    instruction.substring(0, instruction.length() - 1);
//		}
//		information =
//			information + "\n" + tab + "Proceso( " + instruction
//				+ " )";
//	    } else if (diagram.elementAt(index) instanceof InputFigure) {
//		InputFigure temporalFigure =
//			(InputFigure) diagram.elementAt(index);
//		instruction = temporalFigure.instruction.simpleInstruction;
//		if (instruction.compareTo("null") != 0
//			&& !instruction.isEmpty()) {
//		    instruction =
//			    instruction.substring(0, instruction.length() - 1);
//		}
//		information =
//			information + "\n" + tab + "Entrada( " + instruction
//				+ " )";
//	    } else if (diagram.elementAt(index) instanceof OutputFigure) {
//		OutputFigure temporalFigure =
//			(OutputFigure) diagram.elementAt(index);
//		instruction = temporalFigure.instruction.simpleInstruction;
//		if (instruction.compareTo("null") != 0) {
//		    instruction =
//			    instruction.substring(2, instruction.length() - 1);
//		}
//		information =
//			information + "\n" + tab + "Salida( " + instruction
//				+ " )";
//	    } else if (diagram.elementAt(index) instanceof EllipseFigure) {
//		if (diagram.elementAt(index + 1) instanceof DecisionFigureEnd) {
//		    if (tab.startsWith("\t")) {
//			tab = tab.substring(tab.indexOf("\t") + 1);
//		    }
//		    information = information + "\n" + tab + "}";
//		    index++;
//		    tab = "";
//		}
//	    } else if (diagram.elementAt(index) instanceof CircleFigure) {
//		if (index == 0) {
//		    information = information + "\n\nInicio";
//		} else {
//		    information = information + "\nFin\n";
//		}
//
//	    }
//	}
//	this.information.add(information);
//    }
//
//    private String nestedCicles(String information, String tabulation,
//	    Vector<FigureStructure> diagram, int position) {
//	int count = 0;
//	String tab = tabulation;
//	String instruction;
//	for (int index = position; index < diagram.size(); index++) {
//
//	    if (diagram.elementAt(index) instanceof DecisionFigure) {
//
//		DecisionFigure temporalFigureaux =
//			(DecisionFigure) diagram.elementAt(index);
//		instruction =
//			temporalFigureaux.instructionComposed.getFirstInstructionSimple();
//		if (instruction.compareTo("null") != 0) {
//		    instruction =
//			    instruction.substring(3, instruction.length() - 2);
//		}
//		information =
//			information + "\n" + tab + "If( " + instruction + " ){";
//		tab = tab + "	";
//		information =
//			nestedDesition(information, tab, diagram, index + 2);
//		// .............................................................
//		if (tab.startsWith("\t")) {
//		    tab = tab.substring(tab.indexOf("\t") + 1);
//		}
//		index = this.aux;
//		information = information + "\n" + tab + "}\n" + tab + "Else{";
//		tab = tab + "	";
//	    } else if (diagram.elementAt(index) instanceof ForFigure) {
//
//		ForFigure temporalFigure = (ForFigure) diagram.elementAt(index);
//		instruction =
//			temporalFigure.instructionComposed.getFirstInstructionSimple();
//		if (instruction.compareTo("null") != 0) {
//		    instruction =
//			    instruction.substring(4, instruction.length() - 2);
//		}
//		information =
//			information + "\n" + tab + "For( " + instruction
//				+ " ){";
//		tab = tab + "	";
//		information =
//			nestedCicles(information, tab, diagram, index + 1);
//		index = this.aux;
//		// ..............................................................
//		if (tab.startsWith("\t")) {
//		    tab = tab.substring(tab.indexOf("\t") + 1);
//		}
//		information = information + "\n" + tab + "}";
//	    } else if (diagram.elementAt(index) instanceof WhileFigure) {
//
//		WhileFigure temporalFigure =
//			(WhileFigure) diagram.elementAt(index);
//		instruction =
//			temporalFigure.instructionComposed.getFirstInstructionSimple();
//		if (instruction.compareTo("null") != 0) {
//		    instruction =
//			    instruction.substring(6, instruction.length() - 2);
//		}
//		information =
//			information + "\n" + tab + "while(" + instruction
//				+ " ){";
//		tab = tab + "	";
//		information =
//			nestedCicles(information, tab, diagram, index + 1);
//		index = this.aux;
//		// ..............................................................
//		if (tab.startsWith("\t")) {
//		    tab = tab.substring(tab.indexOf("\t") + 1);
//		}
//		information = information + "\n" + tab + "}";
//	    } else if (diagram.elementAt(index) instanceof SentenceFigure) {
//
//		SentenceFigure temporalFigure =
//			(SentenceFigure) diagram.elementAt(index);
//		instruction = temporalFigure.instruction.simpleInstruction;
//		if (instruction.compareTo("null") != 0) {
//		    instruction =
//			    instruction.substring(0, instruction.length() - 1);
//		}
//		information =
//			information + "\n" + tab + "Proceso( " + instruction
//				+ " )";
//	    } else if (diagram.elementAt(index) instanceof InputFigure) {
//
//		InputFigure temporalFigure =
//			(InputFigure) diagram.elementAt(index);
//		instruction = temporalFigure.instruction.simpleInstruction;
//		if (instruction.compareTo("null") != 0) {
//		    instruction =
//			    instruction.substring(0, instruction.length() - 1);
//		}
//		information =
//			information + "\n" + tab + "Entrada( " + instruction
//				+ " )";
//	    } else if (diagram.elementAt(index) instanceof OutputFigure) {
//
//		OutputFigure temporalFigure =
//			(OutputFigure) diagram.elementAt(index);
//		instruction = temporalFigure.instruction.simpleInstruction;
//		if (instruction.compareTo("null") != 0) {
//		    instruction =
//			    instruction.substring(2, instruction.length() - 1);
//		}
//		information =
//			information + "\n" + tab + "Salida( " + instruction
//				+ " )";
//	    } else if (diagram.elementAt(index) instanceof EllipseFigure) {
//
//		if (diagram.elementAt(index + 1) instanceof DecisionFigureEnd) {
//
//		    if (tab.startsWith("\t")) {
//			tab = tab.substring(tab.indexOf("\t") + 1);
//		    }
//		    information = information + "\n" + tab + "}";
//		    index++;
//
//		} else {
//		    count++;
//		    if (count == 6) {
//			this.aux = index;
//			index = diagram.size() + 1;
//			break;
//		    }
//		}
//	    }
//	}
//	return information;
//    }
//
//    private String nestedDesition(String information, String tabulation,
//	    Vector<FigureStructure> diagram, int position) {
//
//	String tab = tabulation;
//	String instruccion;
//	for (int index = position; index < diagram.size(); index++) {
//
//	    if (diagram.elementAt(index) instanceof DecisionFigure) {
//
//		DecisionFigure temporalFigure =
//			(DecisionFigure) diagram.elementAt(index);
//		instruccion =
//			temporalFigure.instructionComposed.getFirstInstructionSimple();
//		if (instruccion.compareTo("null") != 0) {
//		    instruccion =
//			    instruccion.substring(3, instruccion.length() - 2);
//		}
//		information =
//			information + "\n" + tab + "If( " + instruccion + " ){";
//		tab = tab + "	";
//		information =
//			nestedDesition(information, tab, diagram, index + 2);
//		// .............................................................
//		if (tab.startsWith("\t")) {
//		    tab = tab.substring(tab.indexOf("\t") + 1);
//		}
//		index = this.aux;
//		information = information + "\n" + tab + "}\n" + tab + "Else{";
//		tab = tab + "	";
//	    } else if (diagram.elementAt(index) instanceof ForFigure) {
//
//		ForFigure temporalFigure = (ForFigure) diagram.elementAt(index);
//		instruccion =
//			temporalFigure.instructionComposed.getFirstInstructionSimple();
//		if (instruccion.compareTo("null") != 0) {
//		    instruccion =
//			    instruccion.substring(4, instruccion.length() - 2);
//		}
//		information =
//			information + "\n" + tab + "For( " + instruccion
//				+ " ){";
//		tab = tab + "	";
//		information =
//			nestedCicles(information, tab, diagram, index + 1);
//		index = this.aux;
//		// ..............................................................
//		if (tab.startsWith("\t")) {
//		    tab = tab.substring(tab.indexOf("\t") + 1);
//		}
//		information = information + "\n" + tab + "}";
//	    } else if (diagram.elementAt(index) instanceof WhileFigure) {
//
//		WhileFigure temporalFigure =
//			(WhileFigure) diagram.elementAt(index);
//		instruccion =
//			temporalFigure.instructionComposed.getFirstInstructionSimple();
//		if (instruccion.compareTo("null") != 0) {
//		    instruccion =
//			    instruccion.substring(6, instruccion.length() - 2);
//		}
//		information =
//			information + "\n" + tab + "while(" + instruccion
//				+ " ){";
//		tab = tab + "	";
//		information =
//			nestedCicles(information, tab, diagram, index + 1);
//		index = this.aux;
//		// ..............................................................
//		if (tab.startsWith("\t")) {
//		    tab = tab.substring(tab.indexOf("\t") + 1);
//		}
//		information = information + "\n" + tab + "}";
//	    } else if (diagram.elementAt(index) instanceof SentenceFigure) {
//
//		SentenceFigure temporalFigure =
//			(SentenceFigure) diagram.elementAt(index);
//		instruccion = temporalFigure.instruction.simpleInstruction;
//		if (instruccion.compareTo("null") != 0) {
//		    instruccion =
//			    instruccion.substring(0, instruccion.length() - 1);
//		}
//		information =
//			information + "\n" + tab + "Proceso( " + instruccion
//				+ " )";
//	    } else if (diagram.elementAt(index) instanceof InputFigure) {
//
//		InputFigure temporalFigure =
//			(InputFigure) diagram.elementAt(index);
//		instruccion = temporalFigure.instruction.simpleInstruction;
//		if (instruccion.compareTo("null") != 0) {
//		    instruccion =
//			    instruccion.substring(0, instruccion.length() - 1);
//		}
//		information =
//			information + "\n" + tab + "Entrada( " + instruccion
//				+ " )";
//	    } else if (diagram.elementAt(index) instanceof OutputFigure) {
//
//		OutputFigure temporalFigure =
//			(OutputFigure) diagram.elementAt(index);
//		instruccion = temporalFigure.instruction.simpleInstruction;
//		if (instruccion.compareTo("null") != 0) {
//		    instruccion =
//			    instruccion.substring(2, instruccion.length() - 1);
//		}
//		information =
//			information + "\n" + tab + "Salida( " + instruccion
//				+ " )";
//	    } else if (diagram.elementAt(index) instanceof EllipseFigure) {
//
//		if (diagram.elementAt(index + 1) instanceof EllipseFigure) {
//		    index++;
//		    this.aux = index;
//		    index = diagram.size() + 1;
//		    break;
//		} else if (diagram.elementAt(index + 1) instanceof DecisionFigureEnd) {
//		    if (tab.startsWith("\t")) {
//			tab = tab.substring(tab.indexOf("\t") + 1);
//		    }
//		    information = information + "\n" + tab + "}";
//		    index++;
//
//		}
//	    }
//	}
//	return information;
//    }
//
//    public void addTime() {
//	date = new Date();
//	int hora = date.getHours();
//	int minuto = date.getMinutes();
//	int segundo = date.getSeconds();
//
//	endTime =
//		String.valueOf(hora) + ":" + String.valueOf(minuto) + ":"
//			+ String.valueOf(segundo);
//
//	String time = "Tiempo total = " + startTime + "-" + endTime;
//	information.add(time);
//    }
//
//    public void removeTime() {
//	information.removeElementAt(information.size() - 1);
//    }

}
