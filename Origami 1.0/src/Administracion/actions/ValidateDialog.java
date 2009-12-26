package Administracion.actions;

import java.util.ArrayList;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.MainWindow;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;

public class ValidateDialog {
    
    public void validate(String instructionCode, DecisionFigure abstractFigure,
	    String info) {
	
	boolean isChanged = false;
	
	
	InstruccionSimple code = new InstruccionSimple();
	
	code.setInstruccionSimple(instructionCode);

	if (abstractFigure.instruction.instruccion.size() > 0) {

	    if (!abstractFigure.instruction.instruccion.elementAt(0).
		    instruccion.equals(instructionCode)) {
		MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
		MainWindow.getComponents()._diagrams.getTabItem().getInfo().setInformacion(
					"/M - Se agrego"
					+ " o modifico una "
					+ "instruccion en una figura de tipo \""+info+"\"\n");
		isChanged = true;
	    }
	}
	abstractFigure.instruction.instruccion.add(0, code);
	MainWindow.getComponents()._diagrams.getHoja().addFigure();
	MainWindow.getComponents()._diagrams.getHoja().guardarRetroceso();
	if (isChanged) {
	    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setDiagrama(
		    MainWindow.getComponents()._diagrams.getHoja().getDiagrama());
	}
    }
    public void validate(String instructionCode, ForFigure abstractFigure,
	    String info) {
	
	boolean isChanged = false;
	
	
	InstruccionSimple code = new InstruccionSimple();
	
	code.setInstruccionSimple(instructionCode);

	if (abstractFigure.instruction.instruccion.size() > 0) {

	    if (!abstractFigure.instruction.instruccion.elementAt(0).
		    instruccion.equals(instructionCode)) {

		MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
		MainWindow.getComponents()._diagrams.getTabItem().getInfo().setInformacion(
					"/M - Se agrego"
					+ " o modifico una "
					+ "instruccion en una figura de tipo \""+info+"\"\n");
		isChanged = true;
	    }
	}
	abstractFigure.instruction.instruccion.add(0, code);
	MainWindow.getComponents()._diagrams.getHoja().addFigure();
	MainWindow.getComponents()._diagrams.getHoja().guardarRetroceso();
	if (isChanged) {
	    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setDiagrama(
		    MainWindow.getComponents()._diagrams.getHoja().getDiagrama());
	}
    }
    public void validate(String instructionCode, SentenceFigure abstractFigure,
	    String info) {
	
	boolean isChanged = false;
	
	if (!abstractFigure.instruccion.instruccion.equals(instructionCode)) {

	    MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
	    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setInformacion(
		    "/M - Se agrego " + "o modifico una "
			    + "instruccion en una figura de tipo "
			    + "\"proceso\"\n");
	    isChanged = true;
	    abstractFigure.instruccion.setInstruccionSimple(instructionCode);
	}
	abstractFigure.instruccion.setInstruccionSimple(instructionCode);
	MainWindow.getComponents()._diagrams.getHoja().addFigure();
	MainWindow.getComponents()._diagrams.getHoja().guardarRetroceso();

	if (isChanged) {
	    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setDiagrama(
		    MainWindow.getComponents()._diagrams.getHoja().getDiagrama());
	}
    }
    public void validate(String instructionCode, WhileFigure abstractFigure,
	    String info) {
	
	boolean isChanged = false;
	
	
	InstruccionSimple code = new InstruccionSimple();
	
	code.setInstruccionSimple(instructionCode);
	if (abstractFigure.instruccion.instruccion.size() > 0) {

	    if (!abstractFigure.instruccion.instruccion.elementAt(0).
		    instruccion.equals(instructionCode)) {

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
	abstractFigure.instruccion.instruccion.add(0, code);
	MainWindow.getComponents()._diagrams.getHoja().addFigure();
	MainWindow.getComponents()._diagrams.getHoja().guardarRetroceso();
	if (isChanged) {
	    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setDiagrama(
		    MainWindow.getComponents()._diagrams.getHoja().getDiagrama());
	}
    }
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
		    MainWindow.getComponents()._diagrams.getHoja().getDiagrama());
	 }
    }
    private boolean isChange(int total,String[] texbox,InputFigure abstractFigure) {
	boolean cambio = false;
	
	String codigo = "";
	
	for (int x = 0; x < total; x++) {
	    codigo = codigo + texbox[x] + ";";
	}
	if (!abstractFigure.instruction.instruccion.equals(codigo)) {
	    
	    MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
	    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setInformacion("/M - Se " +
	    		"agrego o modifico una instruccion en una" +
	    		" figura de tipo \"entrada\"\n");
	    cambio = true;
	}
	abstractFigure.instruction.setInstruccionSimple(codigo);
	MainWindow.getComponents()._diagrams.getHoja().addFigure();
	MainWindow.getComponents()._diagrams.getHoja().guardarRetroceso();
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
	    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setDiagrama(MainWindow.getComponents()._diagrams.getHoja().getDiagrama());
	}
    }
    private boolean isChange2(int total,String[] text,OutputFigure abstractFigure) {
	boolean cambio = false;

	String codigo = "";

	for (int x = 0; x < total; x++) {
	    codigo = codigo + "\\" + "p"+ text[x] + ";";
	}
	if (!abstractFigure.instruction.instruccion.equals(codigo)) {

	    MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
	    MainWindow.getComponents()._diagrams.getTabItem().getInfo().setInformacion( "/M - Se " 
		    + "agrego o modifico una instruccion en una"
		    + " figura de tipo \"salida\"\n");
	    cambio = true;
	}
	abstractFigure.instruction.setInstruccionSimple(codigo);
	MainWindow.getComponents()._diagrams.getHoja().addFigure();
	MainWindow.getComponents()._diagrams.getHoja().guardarRetroceso();
	return cambio;
    }

}
