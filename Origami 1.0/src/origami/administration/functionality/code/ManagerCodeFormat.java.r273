package origami.administration.functionality.code;

import java.util.Vector;

import origami.administration.FigureStructure;



public class ManagerCodeFormat {
    private Vector<FigureStructure> figures;
	
	private AbstractInstructionFormatter format;
	
	private String instructionsFormat;
	
	public ManagerCodeFormat(Vector<FigureStructure> figures){
		this.figures = figures;
	}
	
	public void setFigures(Vector<FigureStructure> figures){
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
	public void formatCodeJava(){
	    format = new JavaCodeFormatter();
	    format.applyFormat();
	    this.instructionsFormat = format.getInstructionsFormat();
	}
	public String getInstructionsFormat(){
		return instructionsFormat;
	}
}
