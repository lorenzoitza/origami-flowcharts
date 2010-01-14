package origami.administration.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


import origami.administration.funtionality.code.SimpleInstruction;
import origami.debug.Debugger;
import origami.graphics.MainWindow;
import origami.graphics.figures.DecisionFigure;
import origami.graphics.figures.ForFigure;
import origami.graphics.figures.InputFigure;
import origami.graphics.figures.OutputFigure;
import origami.graphics.figures.SentenceFigure;
import origami.graphics.figures.WhileFigure;


/**
 * 
 * @author Hudy
 *
 */
public class DialogValidator {
	
	public void validate(String instructionCode, Object abstractFigure, String info){
		SimpleInstruction code = new SimpleInstruction();
		
		code.setInstruccionSimple(instructionCode);
		
		boolean isChanged = false;
		
		Class<?> figureClass = abstractFigure.getClass();
		
		try {
			
			Method isEmpyInstructionListMethod = figureClass.getMethod("isEmpyInstructionList");
			
			
			Method equalInstructionsMethod = figureClass.getMethod("equalInstructions",String.class);
			
			
			Method addInstructionSimpleMethod = figureClass.getMethod("addInstructionSimple",SimpleInstruction.class);
			
			
			boolean isEmpyInstructionList = (Boolean)isEmpyInstructionListMethod.invoke(abstractFigure);
			
			
			boolean equalInstructions = (Boolean)equalInstructionsMethod.invoke(abstractFigure,instructionCode);
			
			if(!isEmpyInstructionList && !equalInstructions){
				
				
				MainWindow.getComponents().tabFolder.getTabItem().getSave().setSave(false);
				
				MainWindow.getComponents().tabFolder.getTabItem().getInformation().setInformacion(
							"/M - Se agrego"
							+ " o modifico una "
							+ "instruccion en una figura de tipo \""+info+"\"\n");
				isChanged = true;
			}
			
			
			addInstructionSimpleMethod.invoke(abstractFigure, code);
			
			MainWindow.getComponents().tabFolder.getTabItem().getLeaf().addFigure();
			
			MainWindow.getComponents().tabFolder.getTabItem().getLeaf().guardarRetroceso();
			
			if (isChanged) {
			    MainWindow.getComponents().tabFolder.getTabItem().getInformation().setDiagrama(
				MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama());
			}
			
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
    /*
	    
    public void validate(InputFigure abstractFigure, String inputCode,String info) {
		boolean cambio = false;
		if (!abstractFigure.instruction.simpleInstruction.equals(inputCode)) {
		    MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
		    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setInformacion("/M - Se " +
		    		"agrego o modifico una instruccion en una" +
		    		" figura de tipo \""+info+"\"\n");
		    cambio = true;
		}
		
		abstractFigure.instruction.setInstruccionSimple(inputCode);
		
		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigure();
		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().guardarRetroceso();
		if (cambio) {
		    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setDiagrama(
			    MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
		 }
    }
    
    
   
    
    public void validate(OutputFigure abstractFigure, String inputCode, String info) {
	boolean cambio = false;
	
	if (!abstractFigure.instruction.simpleInstruction.equals(inputCode)) {
	    MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
	    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setInformacion("/M - Se " +
	    		"agrego o modifico una instruccion en una" +
	    		" figura de tipo \""+info+"\"\n");
	    cambio = true;
	}
	
	abstractFigure.instruction.setInstruccionSimple(inputCode);

	MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigure();
	MainWindow.getComponents()._diagrams.getTabItem().getLeaf().guardarRetroceso();
	if (cambio) {
	    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setDiagrama(
		    MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
	 }
    }*/
}
