 package origami.administration.functionality.code;

import java.util.Vector;

import origami.administration.FigureStructure;
import origami.administration.functionality.code.targets.BasicCodeFormatter;
import origami.administration.functionality.code.targets.CcodeFormatter;
import origami.administration.functionality.code.targets.CppCodeFormatter;
import origami.administration.functionality.code.targets.CsharpCodeFormatter;
import origami.administration.functionality.code.targets.GDBCodeFormatter;
import origami.administration.functionality.code.targets.JavaCodeFormatter;
import origami.administration.functionality.code.targets.NaturalDescriptionFormatter;
import origami.administration.functionality.code.targets.PseudocodigoCodeFormatter;
import origami.administration.functionality.code.targets.RubyCodeFormatter;



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
		//System.out.println(result);
		format = new CcodeFormatter(result,getCodeOfFigures.getVariablesTable());
		format.applyFormat();
		this.instructionsFormat = format.getInstructionsFormat();
	}
	
	public void formatCodeCpp(){
		Instruction getCodeOfFigures = new Instruction();
		Vector<String> result = getCodeOfFigures.getInstructionOfDiagram(figures);
		//System.out.println("---code---");
		//System.out.println(result);
		format = new CppCodeFormatter(result,getCodeOfFigures.getVariablesTable());
		format.applyFormat();
		this.instructionsFormat = format.getInstructionsFormat();
	}
	
	public void formatCodeBasic(){
	    	Instruction getCodeOfFigures = new Instruction();
		Vector<String> result = getCodeOfFigures.getInstructionOfDiagram(figures);
		//System.out.println(result);
		format = new BasicCodeFormatter(result,getCodeOfFigures.getVariablesTable());
		format.applyFormat();
		this.instructionsFormat = format.getInstructionsFormat();
	}
	public void formatCodeCsharp(){
	    	Instruction getCodeOfFigures = new Instruction();
	    	Vector<String> result = getCodeOfFigures.getInstructionOfDiagram(figures);
		//System.out.println(result);
		format = new CsharpCodeFormatter(result,getCodeOfFigures.getVariablesTable());
		format.applyFormat();
		this.instructionsFormat = format.getInstructionsFormat();
	}
	
	public void formatCodePseudocode(){
	    	Instruction getCodeOfFigures = new Instruction();
	    	Vector<String> result = getCodeOfFigures.getInstructionOfDiagram(figures);
		//System.out.println(result);
		format = new  PseudocodigoCodeFormatter(result,getCodeOfFigures.getVariablesTable());
		System.out.println(getCodeOfFigures.getVariablesTable().toString());
		format.applyFormat();
		this.instructionsFormat = format.getInstructionsFormat();
	}
	
	public void formatCodeRuby(){
	    	Instruction getCodeOfFigures = new Instruction();
	    	Vector<String> result = getCodeOfFigures.getInstructionOfDiagram(figures);
		//System.out.println(result);
		format = new RubyCodeFormatter(result,getCodeOfFigures.getVariablesTable());
		format.applyFormat();
		this.instructionsFormat = format.getInstructionsFormat();
	}
	public void formatCodeGDB(){
	    	format = new GDBCodeFormatter();
	    	format.applyFormat();
	    	this.instructionsFormat = format.getInstructionsFormat();
	}
	public void formatCodeJava(){
	    Instruction getCodeOfFigures = new Instruction();
	    Vector<String> result = getCodeOfFigures.getInstructionOfDiagram(figures);
	    //System.out.println(result);
	    format = new JavaCodeFormatter(result,getCodeOfFigures.getVariablesTable());
	    format.applyFormat();
	    this.instructionsFormat = format.getInstructionsFormat();
	}
	
	public void formatCodeNaturalDescription(){
	    	Instruction getCodeOfFigures = new InstructionNaturalDescription();
	    	Vector<String> result = getCodeOfFigures.getInstructionOfDiagram(figures);
		//System.out.println(result);
		format = new  NaturalDescriptionFormatter(result,getCodeOfFigures.getVariablesTable());
		System.out.println(getCodeOfFigures.getVariablesTable().toString());
		format.applyFormat();
		this.instructionsFormat = format.getInstructionsFormat();
	}
	public String getInstructionsFormat(){
		return instructionsFormat;
	}
}
