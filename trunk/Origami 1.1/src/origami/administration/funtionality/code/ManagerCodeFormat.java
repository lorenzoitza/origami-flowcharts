package origami.administration.funtionality.code;

import java.util.Vector;

import origami.administration.Figura;



public class ManagerCodeFormat {
    private Vector<Figura> figures;
	
	private FormatInstructions format;
	
	private String instructionsFormat;
	
	public ManagerCodeFormat(Vector<Figura> figures){
		this.figures = figures;
	}
	
	public void setFigures(Vector<Figura> figures){
		this.figures = figures;
	}
	
	public void formatCodeC(){
		Instruction getCodeOfFigures = new Instruction();
		Vector<String> result = getCodeOfFigures.getInstructionOfDiagram(figures);
		System.out.println(result);
		format = new CcodeFormatter(result,getCodeOfFigures.getVariablesTable());
		format.applyFormat();
		this.instructionsFormat = format.getInstructionsFormat();
	}
	
	public void formatCodeCpp(){
		Instruction getCodeOfFigures = new Instruction();
		Vector<String> result = getCodeOfFigures.getInstructionOfDiagram(figures);
		System.out.println(result);
		format = new CppCodeFormatter(result,getCodeOfFigures.getVariablesTable());
		format.applyFormat();
		this.instructionsFormat = format.getInstructionsFormat();
	}
	
	public void formatCodeGDB(){
	    	format = new GDBCodeFormatter();
	    	format.applyFormat();
	    	this.instructionsFormat = format.getInstructionsFormat();
	}
	
	public String getInstructionsFormat(){
		return instructionsFormat;
	}
}
