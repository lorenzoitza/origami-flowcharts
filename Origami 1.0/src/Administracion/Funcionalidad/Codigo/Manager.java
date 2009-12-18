package Administracion.Funcionalidad.Codigo;

import java.util.Vector;

import Administracion.Figura;

public class Manager {
	
	private Vector<Figura> figures;
	
	private FormatInstructions format;
	
	private String instructionsFormat;
	
	public Manager(Vector<Figura> figures){
		this.figures = figures;
	}
	
	public void setFigures(Vector<Figura> figures){
		this.figures = figures;
	}
	
	public void formatCodeC(){
		Instruccion getCodeOfFigures = new Instruccion();
		Vector<String> result = getCodeOfFigures.main(figures);
		System.out.println(result);
		format = new FormatCodeC(result,getCodeOfFigures.getTablaVariables());
		format.applyFormat();
		this.instructionsFormat = format.getInstructionsFormat();
	}
	
	public void formatCodeCpp(){
		Instruccion getCodeOfFigures = new Instruccion();
		Vector<String> result = getCodeOfFigures.main(figures);
		System.out.println(result);
		format = new FormatCodeCpp(result,getCodeOfFigures.getTablaVariables());
		format.applyFormat();
		this.instructionsFormat = format.getInstructionsFormat();
	}
	
	public String getInstructionsFormat(){
		return instructionsFormat;
	}
}
