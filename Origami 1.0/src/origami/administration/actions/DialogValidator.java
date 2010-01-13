package Administracion.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.origami.debug.Debugger;

import Administracion.Funcionalidad.Codigo.InstructionSimple;
import Grafico.MainWindow;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;
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
    
    /*public void validate(String instructionCode, DecisionFigure abstractFigure,
		    String info) {
		this.validate(instructionCode, (Object)abstractFigure, info);
		boolean isChanged = false;
		
		
		InstructionSimple code = new InstructionSimple();
		
		code.setInstruccionSimple(instructionCode);
	
		if (!abstractFigure.isEmpyInstructionList()) { 
			//generar un metodo en figura y heredarlo en DecisionFigure para pedir si esta vacia la lista
	
		    if (!abstractFigure.equalInstructions(instructionCode)) {
			MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
			MainWindow.getComponents()._diagrams.getTabItem().getInfo().setInformacion(
						"/M - Se agrego"
						+ " o modifico una "
						+ "instruccion en una figura de tipo \""+info+"\"\n");
			isChanged = true;
		    }
		}
		abstractFigure.addInstructionSimple(code);
		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigure();
		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().guardarRetroceso();
		if (isChanged) {
		    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setDiagrama(
			MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
		}
    }
    
    public void validate(String instructionCode, ForFigure abstractFigure,
		    String info) {
		
		boolean isChanged = false;
		
		
		InstructionSimple code = new InstructionSimple();
		
		code.setInstruccionSimple(instructionCode);
	
		if (abstractFigure.instructionComposed.simpleInstructionList.size() > 0) {
	
		    if (!abstractFigure.instructionComposed.simpleInstructionList.elementAt(0).
			    simpleInstruction.equals(instructionCode)) {
	
			MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
			MainWindow.getComponents()._diagrams.getTabItem().getInfo().setInformacion(
						"/M - Se agrego"
						+ " o modifico una "
						+ "instruccion en una figura de tipo \""+info+"\"\n");
			isChanged = true;
		    }
		}
		abstractFigure.instructionComposed.simpleInstructionList.add(0, code);
		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigure();
		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().guardarRetroceso();
		if (isChanged) {
		    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setDiagrama(
			    MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
		}
    }
    
    public void validate(String instructionCode, SentenceFigure abstractFigure,
	    String info) {
		
		boolean isChanged = false;
		
		if (!abstractFigure.instruccion.simpleInstruction.equals(instructionCode)) {
	
		    MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
		    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setInformacion(
			    "/M - Se agrego " + "o modifico una "
				    + "instruccion en una figura de tipo "
				    + "\"proceso\"\n");
		    isChanged = true;
		    abstractFigure.instruccion.setInstruccionSimple(instructionCode);
		}
		abstractFigure.instruccion.setInstruccionSimple(instructionCode);
		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigure();
		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().guardarRetroceso();
	
		if (isChanged) {
		    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setDiagrama(
			    MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
		}
	}
    
    public void validate(String instructionCode, WhileFigure abstractFigure,
		    String info) {
		
		boolean isChanged = false;
		
		
		InstructionSimple code = new InstructionSimple();
		
		code.setInstruccionSimple(instructionCode);
		if (abstractFigure.instructionComposed.simpleInstructionList.size() > 0) {
		
		    if (!abstractFigure.instructionComposed.simpleInstructionList.elementAt(0).
			    simpleInstruction.equals(instructionCode)) {
		
			MainWindow.getComponents()._diagrams.getTabItem().getSave()
				.setSave(false);
			MainWindow.getComponents()._diagrams.getTabItem().getInfo()
				.setInformacion(
					"/M - Se agrego o modifico una "
						+ "instruccion "
						+ "en una figura de tipo "
						+ "\"mientras\"\n");
			isChanged = true;
		    }
		}
		abstractFigure.instructionComposed.simpleInstructionList.add(0, code);
		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigure();
		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().guardarRetroceso();
		if (isChanged) {
		    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setDiagrama(
			    MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
		}
    }*/
	    
    public void validate(InputFigure abstractFigure, ArrayList<String> copia) {
		String[] textBoxContent2 = new String[50];
		int total=0;
		
		for(int x=0;x<copia.size();x++){
		    while (copia.get(x).startsWith(" ")) {
			    String nuevo = copia.get(x).replaceFirst(" ", "");
			    copia.set(x, nuevo);
			}
			if (!copia.get(x).startsWith("Escribe") && copia.get(x) != "null"
				&& copia.get(x) != "") {
	
			    if (copia.get(x).compareToIgnoreCase("leer:")==0) {
				
				String copia2 = copia.get(x).substring(5);
				
				while (copia2.startsWith(" ")) {
				    copia2 = copia2.replaceFirst(" ", "");
				}
				if (copia2.length() > 0) {
				    String nuevo2 = "Leer: " + copia2;
				    copia.set(x, nuevo2);
				    textBoxContent2[total] = copia.get(x);
				    total++;
				}
			    } else {
				textBoxContent2[total] = copia.get(x);
				total++;
			    }
			}
		}
		if (isChange(total,textBoxContent2,abstractFigure)) {
		    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setDiagrama(
			    MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
		 }
    }
    
    
    private boolean isChange(int total,String[] texbox,InputFigure abstractFigure) {
		boolean cambio = false;
		
		String codigo = "";
		
		for (int x = 0; x < total; x++) {
		    codigo = codigo + texbox[x] + ";";
		}
		if (!abstractFigure.instruction.simpleInstruction.equals(codigo)) {
		    
		    MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
		    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setInformacion("/M - Se " +
		    		"agrego o modifico una instruccion en una" +
		    		" figura de tipo \"entrada\"\n");
		    cambio = true;
		}
		abstractFigure.instruction.setInstruccionSimple(codigo);
		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigure();
		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().guardarRetroceso();
		return cambio;
    }
    public void validate(OutputFigure abstractFigure, ArrayList<String> copia) {
		int total = 0;
	
		String[] textBoxContent2 = new String[50];
	
		for(int x=0;x<copia.size();x++){
		    while (copia.get(x).startsWith(" ")) {
			String nuevo =copia.get(x).replaceFirst(" ", "");
			copia.set(x, nuevo);
		    }
		    if (!copia.get(x).startsWith("Escribe") && copia.get(x) != "null"
			&& copia.get(x) != "") {
			
			textBoxContent2[total] = copia.get(x);
			total++;
		    }
		}
		if (isChange2(total,textBoxContent2,abstractFigure)) {    
		    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setDiagrama(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
		}
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
