package Administracion.actions;

import java.beans.DesignMode;

import Administracion.Figura;
import Administracion.TabFolder;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;

public class ValidateDialog {
    
    public void validate(String instructionCode,TabFolder tabbedPaneSelected, DecisionFigure abstractFigure,
	    String info) {
	
	boolean isChanged = false;
	
	
	InstruccionSimple code = new InstruccionSimple();
	
	code.setInstruccionSimple(instructionCode);

	if (abstractFigure.instruction.instruccion.size() > 0) {

	    if (!abstractFigure.instruction.instruccion.elementAt(0).
		    instruccion.equals(instructionCode)) {

		tabbedPaneSelected.getTabItem().getSave().setSave(false);
		tabbedPaneSelected.getTabItem().getInfo().setInformacion(
					"/M - Se agrego"
					+ " o modifico una "
					+ "instruccion en una figura de tipo \""+info+"\"\n");
		isChanged = true;
	    }
	}
	abstractFigure.instruction.instruccion.add(0, code);
	tabbedPaneSelected.getHoja().addFigure();
	tabbedPaneSelected.getHoja().guardarRetroceso();
	if (isChanged) {
	    tabbedPaneSelected.getTabItem().getInfo().setDiagrama(
		    tabbedPaneSelected.getHoja().getDiagrama());
	}
    }
    public void validate(String instructionCode,TabFolder tabbedPaneSelected, ForFigure abstractFigure,
	    String info) {
	
	boolean isChanged = false;
	
	
	InstruccionSimple code = new InstruccionSimple();
	
	code.setInstruccionSimple(instructionCode);

	if (abstractFigure.instruction.instruccion.size() > 0) {

	    if (!abstractFigure.instruction.instruccion.elementAt(0).
		    instruccion.equals(instructionCode)) {

		tabbedPaneSelected.getTabItem().getSave().setSave(false);
		tabbedPaneSelected.getTabItem().getInfo().setInformacion(
					"/M - Se agrego"
					+ " o modifico una "
					+ "instruccion en una figura de tipo \""+info+"\"\n");
		isChanged = true;
	    }
	}
	abstractFigure.instruction.instruccion.add(0, code);
	tabbedPaneSelected.getHoja().addFigure();
	tabbedPaneSelected.getHoja().guardarRetroceso();
	if (isChanged) {
	    tabbedPaneSelected.getTabItem().getInfo().setDiagrama(
		    tabbedPaneSelected.getHoja().getDiagrama());
	}
    }
    public void validate(String instructionCode,TabFolder tabbedPaneSelected, SentenceFigure abstractFigure,
	    String info) {
	
	boolean isChanged = false;
	
	if (!abstractFigure.instruccion.instruccion.equals(instructionCode)) {

	    tabbedPaneSelected.getTabItem().getSave().setSave(false);
	    tabbedPaneSelected.getTabItem().getInfo().setInformacion(
		    "/M - Se agrego " + "o modifico una "
			    + "instruccion en una figura de tipo "
			    + "\"proceso\"\n");
	    isChanged = true;
	    abstractFigure.instruccion.setInstruccionSimple(instructionCode);
	}
	abstractFigure.instruccion.setInstruccionSimple(instructionCode);
	tabbedPaneSelected.getHoja().addFigure();
	tabbedPaneSelected.getHoja().guardarRetroceso();

	if (isChanged) {
	    tabbedPaneSelected.getTabItem().getInfo().setDiagrama(
		    tabbedPaneSelected.getHoja().getDiagrama());
	}
    }
    public void validate(String instructionCode,TabFolder tabbedPaneSelected, WhileFigure abstractFigure,
	    String info) {
	
	boolean isChanged = false;
	
	
	InstruccionSimple code = new InstruccionSimple();
	
	code.setInstruccionSimple(instructionCode);
	if (abstractFigure.instruccion.instruccion.size() > 0) {

	    if (!abstractFigure.instruccion.instruccion.elementAt(0).
		    instruccion.equals(instructionCode)) {

		tabbedPaneSelected.getTabItem().getSave()
			.setSave(false);
		tabbedPaneSelected.getTabItem().getInfo()
			.setInformacion(
				"/M - Se agrego o modifico una "
					+ "instruccion "
					+ "en una figura de tipo "
					+ "\"mientras\"\n");
		isChanged = true;
	    }
	}
	abstractFigure.instruccion.instruccion.add(0, code);
	tabbedPaneSelected.getHoja().addFigure();
	tabbedPaneSelected.getHoja().guardarRetroceso();
	if (isChanged) {
	    tabbedPaneSelected.getTabItem().getInfo().setDiagrama(
		    tabbedPaneSelected.getHoja().getDiagrama());
	}
    }
}
