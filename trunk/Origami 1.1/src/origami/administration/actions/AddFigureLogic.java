package origami.administration.actions;

import origami.administration.ApplicationState;
import origami.administration.funtionality.code.InstructionSimple;
import origami.graphics.Componentes;
import origami.graphics.figures.DecisionFigure;
import origami.graphics.figures.ForFigure;
import origami.graphics.figures.InputFigure;
import origami.graphics.figures.OutputFigure;
import origami.graphics.figures.SentenceFigure;
import origami.graphics.figures.WhileFigure;


public class AddFigureLogic {
    
    public void disableCursor() {
	Componentes._diagrams.getTabItem().getLeaf().getChart().disableCursor(
	Componentes._diagrams.getTabItem().getLeaf().getDiagrama(),
	Componentes._diagrams.getTabItem().getLeaf().getChart());
    }
    
    public void addInput() {
	InputFigure inputFigure = new InputFigure();
	inputFigure.instruction.simpleInstruction = "null";
	ApplicationState.mainFigure = null;
	ApplicationState.mainFigure = inputFigure;
    }
    public void addDecision() {
	DecisionFigure decisionFigure = new DecisionFigure();
	InstructionSimple codigo = new InstructionSimple();
	codigo.setInstruccionSimple("null");
	decisionFigure.instructionComposed.simpleInstructionList.add(0,codigo);
	ApplicationState.mainFigure = null;
	ApplicationState.mainFigure = decisionFigure;
    }
    public void addFor() {
	ForFigure forFigure = new ForFigure();
	InstructionSimple codigo = new InstructionSimple();
	codigo.setInstruccionSimple("null");
	forFigure.instructionComposed.simpleInstructionList.add(0,codigo);
	ApplicationState.mainFigure = null;
	ApplicationState.mainFigure = forFigure;
    }
    public void addOutput() {
	OutputFigure outPutFigure = new OutputFigure();
	outPutFigure.instruction.simpleInstruction = "null";
	ApplicationState.mainFigure = null;
	ApplicationState.mainFigure = outPutFigure;
    }
    public void addSentence() {
	SentenceFigure sentenceFigure = new SentenceFigure();
	sentenceFigure.instruction.simpleInstruction = "null";
	ApplicationState.mainFigure = null;
	ApplicationState.mainFigure = sentenceFigure;
    }
    public void addWhile() {
	WhileFigure whileFigure = new WhileFigure();
	InstructionSimple instructionCode = new InstructionSimple();
	instructionCode.setInstruccionSimple("null");
	whileFigure.instructionComposed.simpleInstructionList.add(0,instructionCode);
	ApplicationState.mainFigure = null;
	ApplicationState.mainFigure = whileFigure;
    }
}
