package origami.administration.actions;

import origami.administration.ApplicationState;
import origami.administration.functionality.code.SimpleInstruction;
import origami.graphics.WindowWidgets;
import origami.graphics.figures.DecisionFigure;
import origami.graphics.figures.ForFigure;
import origami.graphics.figures.InputFigure;
import origami.graphics.figures.OutputFigure;
import origami.graphics.figures.SentenceFigure;
import origami.graphics.figures.WhileFigure;


public class AddFigureLogic {
    
    public void disableCursor() {
	WindowWidgets.tabFolder.getTabItem().getLeaf().getChart().disableCursor(
	WindowWidgets.tabFolder.getTabItem().getLeaf().getDiagrama(),
	WindowWidgets.tabFolder.getTabItem().getLeaf().getChart());
    }
    
    public void addInput() {
	InputFigure inputFigure = new InputFigure();
	inputFigure.instruction.simpleInstruction = "null";
	ApplicationState.mainFigure = null;
	ApplicationState.mainFigure = inputFigure;
    }
    public void addDecision() {
	DecisionFigure decisionFigure = new DecisionFigure();
	SimpleInstruction codigo = new SimpleInstruction();
	codigo.setInstruccionSimple("null");
	decisionFigure.instructionComposed.addFirstSimpleInstruction(codigo);
	ApplicationState.mainFigure = null;
	ApplicationState.mainFigure = decisionFigure;
    }
    public void addFor() {
	ForFigure forFigure = new ForFigure();
	SimpleInstruction codigo = new SimpleInstruction();
	codigo.setInstruccionSimple("null");
	forFigure.instructionComposed.addFirstSimpleInstruction(codigo);
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
	SimpleInstruction instructionCode = new SimpleInstruction();
	instructionCode.setInstruccionSimple("null");
	whileFigure.instructionComposed.addFirstSimpleInstruction(instructionCode);
	ApplicationState.mainFigure = null;
	ApplicationState.mainFigure = whileFigure;
    }
    public void changeCursotTo(String figure){
	if(figure.contentEquals("cursorEntrada.png")){
	    addInput();
	}
	else if(figure.contentEquals("cursorIf.png")){
	    addDecision();
	}
	else if(figure.contentEquals("cursorFor.png")){
	    addFor();
	}
	else if(figure.contentEquals("cursorSalida.png")){
	    addOutput();
	}
	else if(figure.contentEquals("cursorProceso.png")){
	    addSentence();
	}
	else if(figure.contentEquals("cursorWhile.png")){
	    addWhile();
	}
    }
}
