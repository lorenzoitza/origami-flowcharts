package Administracion.actions;

import Administracion.Funcionalidad.Codigo.InstructionSimple;
import Grafico.Componentes;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;


public class AddFigureLogic {
    
    public void disableCursor() {
	Componentes._diagrams.getHoja().getChart().disableCursor(
	Componentes._diagrams.getHoja().getDiagrama(),
	Componentes._diagrams.getHoja().getChart());
    }
    
    public void addInput() {
	InputFigure inputFigure = new InputFigure();
	inputFigure.instruction.instruccion = "null";
	Componentes.mainFigure = null;
	Componentes.mainFigure = inputFigure;
    }
    public void addDecision() {
	DecisionFigure decisionFigure = new DecisionFigure();
	InstructionSimple codigo = new InstructionSimple();
	codigo.setInstruccionSimple("null");
	decisionFigure.instruction.instruccion.add(0,codigo);
	Componentes.mainFigure = null;
	Componentes.mainFigure = decisionFigure;
    }
    public void addFor() {
	ForFigure forFigure = new ForFigure();
	InstructionSimple codigo = new InstructionSimple();
	codigo.setInstruccionSimple("null");
	forFigure.instruction.instruccion.add(0,codigo);
	Componentes.mainFigure = null;
	Componentes.mainFigure = forFigure;
    }
    public void addOutput() {
	OutputFigure outPutFigure = new OutputFigure();
	outPutFigure.instruction.instruccion = "null";
	Componentes.mainFigure = null;
	Componentes.mainFigure = outPutFigure;
    }
    public void addSentence() {
	SentenceFigure sentenceFigure = new SentenceFigure();
	sentenceFigure.instruccion.instruccion = "null";
	Componentes.mainFigure = null;
	Componentes.mainFigure = sentenceFigure;
    }
    public void addWhile() {
	WhileFigure whileFigure = new WhileFigure();
	InstructionSimple instructionCode = new InstructionSimple();
	instructionCode.setInstruccionSimple("null");
	whileFigure.instruccion.instruccion.add(0,instructionCode);
	Componentes.mainFigure = null;
	Componentes.mainFigure = whileFigure;
    }
}
