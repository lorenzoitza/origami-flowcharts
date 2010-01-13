package origami.administration.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


import origami.administration.funtionality.code.InstructionSimple;
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
		InstructionSimple code = new InstructionSimple();
		
		code.setInstruccionSimple(instructionCode);
		
		boolean isChanged = false;
		
		Class<?> figureClass = abstractFigure.getClass();
		
		Debugger.debug(this.getClass(), "Reflexion --> Sobre la clase: "+figureClass.getCanonicalName());
		try {
			Debugger.debug(this.getClass(), "Reflexion --> obteniendo un metodo: isEmpyInstructionList");
			
			Method isEmpyInstructionListMethod = figureClass.getMethod("isEmpyInstructionList");
			
			Debugger.debug(this.getClass(), "Reflexion --> obteniendo un metodo: equalInstructions");
			
			Method equalInstructionsMethod = figureClass.getMethod("equalInstructions",String.class);
			
			Debugger.debug(this.getClass(), "Reflexion --> obteniendo un metodo: addInstructionSimple");
			
			Method addInstructionSimpleMethod = figureClass.getMethod("addInstructionSimple",InstructionSimple.class);
			
			Debugger.debug(this.getClass(), "Reflexion --> Invocando un metodo: isEmpyInstructionList");
			
			boolean isEmpyInstructionList = (Boolean)isEmpyInstructionListMethod.invoke(abstractFigure);
			
			Debugger.debug(this.getClass(), "Reflexion --> Invocando un metodo: equalInstructions");
			
			boolean equalInstructions = (Boolean)equalInstructionsMethod.invoke(abstractFigure,instructionCode);
			
			if(!isEmpyInstructionList && !equalInstructions){
				
				Debugger.debug(this.getClass(), "Lista no vacia - instrucciones no iguales");
				
				MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
				
				MainWindow.getComponents()._diagrams.getTabItem().getInfo().setInformacion(
							"/M - Se agrego"
							+ " o modifico una "
							+ "instruccion en una figura de tipo \""+info+"\"\n");
				isChanged = true;
			}
			
			Debugger.debug(this.getClass(), "Reflexion --> Invocando un metodo: addInstructionSimple");
			
			addInstructionSimpleMethod.invoke(abstractFigure, code);
			
			MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigure();
			
			MainWindow.getComponents()._diagrams.getTabItem().getLeaf().guardarRetroceso();
			
			if (isChanged) {
				Debugger.debug(this.getClass(), "isChanged - true");
			    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setDiagrama(
				MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
			}
			
		} catch (SecurityException e) {
			Debugger.error(this.getClass(), "Reflexion --> invocando un metodo privado");
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			Debugger.error(this.getClass(), "Reflexion --> No existe el metodo que se trata de invocar");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			Debugger.error(this.getClass(), "Reflexion --> El argumento del metodo es incorrecto");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Debugger.error(this.getClass(), "Reflexion --> No existe acceso a ese metodo");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			Debugger.error(this.getClass(), "Reflexion --> No se pudo invocar un metodo");
			e.printStackTrace();
		}
	}
    
	    
    public void validate(InputFigure abstractFigure, String inputCode,String info) {
		boolean cambio = false;
		
		if (!abstractFigure.instruction.simpleInstruction.equals(inputCode)) {
		    Debugger.debug(this.getClass(),"cambio en algo el codigo solo sirve para el info...");
		    MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
		    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setInformacion("/M - Se " +
		    		"agrego o modifico una instruccion en una" +
		    		" figura de tipo \""+info+"\"\n");
		    cambio = true;
		}
		Debugger.debug(this.getClass(),"cambio:"+cambio);
		
		abstractFigure.instruction.setInstruccionSimple(inputCode);
		
		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigure();
		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().guardarRetroceso();
		if (cambio) {
		    Debugger.debug(this.getClass(),"cambio en algo el ");
		    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setDiagrama(
			    MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
		 }
    }
    
    
   
    
    public void validate(OutputFigure abstractFigure, String inputCode, String info) {
	boolean cambio = false;
	
	if (!abstractFigure.instruction.simpleInstruction.equals(inputCode)) {
	    Debugger.debug(this.getClass(),"cambio en algo el codigo solo sirve para el info...");
	    MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
	    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setInformacion("/M - Se " +
	    		"agrego o modifico una instruccion en una" +
	    		" figura de tipo \""+info+"\"\n");
	    cambio = true;
	}
	Debugger.debug(this.getClass(),"cambio:"+cambio);
	
	abstractFigure.instruction.setInstruccionSimple(inputCode);

	MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigure();
	MainWindow.getComponents()._diagrams.getTabItem().getLeaf().guardarRetroceso();
	if (cambio) {
	    Debugger.debug(this.getClass(),"cambio en algo el ");
	    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setDiagrama(
		    MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
	 }
	
		/*int total = 0;
	
		String[] textBoxContent2 = new String[50];
	
		for(int x=0;x<copia.size();x++){
		    while (copia.get(x).startsWith(" ")) {//trime
			String nuevo =copia.get(x).replaceFirst(" ", "");
			copia.set(x, nuevo);
		    }
		    if (!copia.get(x).startsWith("Escribe") && copia.get(x) != "null"
			&& copia.get(x) != "") {//validacion escribe
			
			textBoxContent2[total] = copia.get(x);
			total++;
		    }
		}
		if (isChange2(total,textBoxContent2,abstractFigure)) {    
		    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setDiagrama(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
		}*/
    }
    
    private boolean isChange2(int total,String[] text,OutputFigure abstractFigure) {
		boolean cambio = false;
	
		String codigo = "";
	
		for (int x = 0; x < total; x++) {
		    codigo = codigo + "\\" + "p"+ text[x] + ";";
		}
		if (!abstractFigure.instruction.simpleInstruction.equals(codigo)) {
	
		    MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
		    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setInformacion( "/M - Se " 
			    + "agrego o modifico una instruccion en una"
			    + " figura de tipo \"salida\"\n");
		    cambio = true;
		}
		abstractFigure.instruction.setInstruccionSimple(codigo);
		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigure();
		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().guardarRetroceso();
		return cambio;
    }

}
