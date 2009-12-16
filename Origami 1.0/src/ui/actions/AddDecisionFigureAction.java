package ui.actions;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;

import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.MainWindow;
import Grafico.Figuras.DecisionFigure;
import Imagenes.ImageLoader;

public class AddDecisionFigureAction implements SelectionListener{
    
    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent event) {
	MainWindow.getComponents().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorIf.png").getImageData(), 0, 0);
	DecisionFigure decisionFigure = new DecisionFigure();
	InstruccionSimple codigo = new InstruccionSimple();
	codigo.setInstruccionSimple("null");
	decisionFigure.instruction.instruccion.add(0,codigo);
	MainWindow.mainFigure = null;
	MainWindow.mainFigure = decisionFigure;
	MainWindow.getComponents().disableCursor();
    }

}
