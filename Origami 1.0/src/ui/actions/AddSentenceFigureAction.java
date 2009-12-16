package ui.actions;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;

import Grafico.MainWindow;
import Grafico.Figuras.SentenceFigure;
import Imagenes.ImageLoader;

public class AddSentenceFigureAction implements SelectionListener{
    
    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent event) {
	MainWindow.getComponents().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorProceso.png").getImageData(), 0, 0);
	SentenceFigure sentenceFigure = new SentenceFigure();
	sentenceFigure.instruccion.instruccion = "null";
	MainWindow.mainFigure = null;
	MainWindow.mainFigure = sentenceFigure;
	MainWindow.getComponents().disableCursor();
    }

}
