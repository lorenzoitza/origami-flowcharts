package origami.graphics.listeners;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;

import origami.administration.AdminSelection;
import origami.administration.FigureStructure;
import origami.graphics.MainWindow;
import origami.graphics.dialogs.DecisionFigureDialog;
import origami.graphics.dialogs.ForFigureDialog;
import origami.graphics.dialogs.InputFigureDialog;
import origami.graphics.dialogs.OutputFigureDialog;
import origami.graphics.dialogs.SentenceFigureDialog;
import origami.graphics.dialogs.WhileFigureDialog;
import origami.graphics.figures.DecisionFigure;
import origami.graphics.figures.ForFigure;
import origami.graphics.figures.InputFigure;
import origami.graphics.figures.OutputFigure;
import origami.graphics.figures.SentenceFigure;
import origami.graphics.figures.WhileFigure;
import origami.graphics.widgets.CustomTabFolder;

public class DoubleClickListener extends MouseListener.Stub {

    public DoubleClickListener(FigureStructure figure, AdminSelection selectionAdmin,
	    CustomTabFolder tabFolder) {
	figure.addMouseListener(this);
    }

    public void mouseDoubleClicked(MouseEvent event) {
	FigureStructure figure = ((FigureStructure) event.getSource());

	if (figure instanceof SentenceFigure) {
	    SentenceFigure setenceFigure = (SentenceFigure) figure;

	    new SentenceFigureDialog(MainWindow.shell, setenceFigure).open();
	} else if (figure instanceof DecisionFigure) {
	    DecisionFigure decisionFigure = ((DecisionFigure) figure);

	    new DecisionFigureDialog(MainWindow.shell, decisionFigure).open();
	} else if (figure instanceof InputFigure) {
	    InputFigure inputFigure = ((InputFigure) figure);

	    new InputFigureDialog(MainWindow.shell, inputFigure).open();
	} else if (figure instanceof OutputFigure) {
	    OutputFigure outputFigure = ((OutputFigure) figure);

	    new OutputFigureDialog(MainWindow.shell, outputFigure).open();
	} else if (figure instanceof ForFigure) {
	    ForFigure forFigure = ((ForFigure) figure);

	    new ForFigureDialog(MainWindow.shell, forFigure).open();
	} else if (figure instanceof WhileFigure) {
	    WhileFigure whileFigure = ((WhileFigure) figure);

	    new WhileFigureDialog(MainWindow.shell, whileFigure).open();
	}
    }
}

