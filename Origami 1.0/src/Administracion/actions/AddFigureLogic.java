package Administracion.actions;

import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.MainWindow;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;


public class AddFigureLogic {
    
    public void addInput() {
	InputFigure inputFigure = new InputFigure();
	inputFigure.instruction.instruccion = "null";
	MainWindow.mainFigure = null;
	MainWindow.mainFigure = inputFigure;
    }
    public void addDecision() {
	DecisionFigure decisionFigure = new DecisionFigure();
	InstruccionSimple codigo = new InstruccionSimple();
	codigo.setInstruccionSimple("null");
	decisionFigure.instruction.instruccion.add(0,codigo);
	MainWindow.mainFigure = null;
	MainWindow.mainFigure = decisionFigure;
    }
    public void addFor() {
	ForFigure forFigure = new ForFigure();
	InstruccionSimple codigo = new InstruccionSimple();
	codigo.setInstruccionSimple("null");
	forFigure.instruction.instruccion.add(0,codigo);
	MainWindow.mainFigure = null;
	MainWindow.mainFigure = forFigure;
    }
    public void addOutput() {
	OutputFigure outPutFigure = new OutputFigure();
	outPutFigure.instruction.instruccion = "null";
	MainWindow.mainFigure = null;
	MainWindow.mainFigure = outPutFigure;
    }
    public void addSentence() {
	SentenceFigure sentenceFigure = new SentenceFigure();
	sentenceFigure.instruccion.instruccion = "null";
	MainWindow.mainFigure = null;
	MainWindow.mainFigure = sentenceFigure;
    }
    public void addWhile() {
	WhileFigure whileFigure = new WhileFigure();
	InstruccionSimple instructionCode = new InstruccionSimple();
	instructionCode.setInstruccionSimple("null");
	whileFigure.instruccion.instruccion.add(0,instructionCode);
	MainWindow.mainFigure = null;
	MainWindow.mainFigure = whileFigure;
    }
}
