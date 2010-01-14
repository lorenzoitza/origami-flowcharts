package origami.administration.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import origami.administration.funtionality.code.SimpleInstruction;
import origami.graphics.MainWindow;

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
				
				MainWindow.getComponents().tabFolder.getTabItem().getInformation().addInformation(
							"/M - Se agrego"
							+ " o modifico una "
							+ "instruccion en una figura de tipo \""+info+"\"\n");
				isChanged = true;
			}
			
			
			addInstructionSimpleMethod.invoke(abstractFigure, code);
			
			MainWindow.getComponents().tabFolder.getTabItem().getLeaf().addFigure();
			
			MainWindow.getComponents().tabFolder.getTabItem().getLeaf().guardarRetroceso();
			
			if (isChanged) {
			    MainWindow.getComponents().tabFolder.getTabItem().getInformation().setDiagram(
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
}
