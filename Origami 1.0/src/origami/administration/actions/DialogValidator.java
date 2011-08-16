package origami.administration.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import origami.administration.FigureStructure;
import origami.administration.Information;
import origami.administration.functionality.code.SimpleInstruction;
import origami.graphics.WindowWidgets;

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
			
			
			addInstructionSimpleMethod.invoke(abstractFigure, code);
			
			if(!isEmpyInstructionList && !equalInstructions){
				
				
				WindowWidgets.tabFolder.getTabItem().getSave().setSave(false);
				
//				WindowWidgets.tabFolder.getTabItem().getInformation().addInformation(
//							"/M - Se agrego"
//							+ " o modifico una "
//							+ "instruccion en una figura de tipo \""+info+"\"\n");
				
				WindowWidgets.tabFolder.getTabItem().getInformation().addInformationFigure((FigureStructure)abstractFigure, Information.EDIT);
				
				isChanged = true;
			}
			
			WindowWidgets.tabFolder.getTabItem().getLeaf().addFigure();
			
			WindowWidgets.tabFolder.getTabItem().getLeaf().guardarRetroceso();
			
			if (isChanged) {
//			    WindowWidgets.tabFolder.getTabItem().getInformation().setDiagram(
//				WindowWidgets.tabFolder.getTabItem().getLeaf().getDiagrama());
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
