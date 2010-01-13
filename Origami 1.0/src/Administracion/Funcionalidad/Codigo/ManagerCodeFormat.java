package Administracion.Funcionalidad.Codigo;

import java.util.Vector;

import Administracion.Figura;

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
		format = new FormatCodeC(result,getCodeOfFigures.getVariablesTable());
		format.applyFormat();
		this.instructionsFormat = format.getInstructionsFormat();
	}
	
	public void formatCodeCpp(){
		Instruction getCodeOfFigures = new Instruction();
		Vector<String> result = getCodeOfFigures.getInstructionOfDiagram(figures);
		System.out.println(result);
		format = new FormatCodeCpp(result,getCodeOfFigures.getVariablesTable());
		format.applyFormat();
		this.instructionsFormat = format.getInstructionsFormat();
	}
	
	public void formatCodeGDB(){
	    	format = new FormatCodeGDB();
	    	format.applyFormat();
	    	this.instructionsFormat = format.getInstructionsFormat();
	}
	
	public String getInstructionsFormat(){
		return instructionsFormat;
	}
}
