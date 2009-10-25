package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import Administracion.*;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;

/**
 * Esta clase es la que crea y dibujar a la figura de entrada.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class InputFigure extends Figura {

    public InstruccionSimple instruccion = new InstruccionSimple();

    public InputFigure() {
	setBounds(new Rectangle(100, 100, 80, 40));
	this.rectangle = bounds;
    }
    
    /**
     * Este metodo es el encargado de dibujar la figura.
     * 
     * @param Graphics
     */
    public void paint(Graphics graphics) {
	selectLineTipe(graphics);
	graphics.drawPolygon(getPointsOfPolygon());
	drawInstruction(graphics);
    }

    private PointList getPointsOfPolygon() {
	PointList _polygon = new PointList();
	_polygon.addPoint(rectangle.x + 25, rectangle.y + 1);
	_polygon.addPoint(rectangle.x + rectangle.width, rectangle.y + 1);
	_polygon.addPoint(rectangle.x + rectangle.width - 25, 
		rectangle.height + rectangle.y - 1);
	_polygon.addPoint(rectangle.x, rectangle.y + rectangle.height - 1);
	return _polygon;
    }

    private boolean isInstruction() {
	return instruccion.getInstruccionSimple().compareTo("null") != 0
		&& instruccion.getInstruccionSimple().compareTo("") != 0;
    }

    private void defaultInstruction(Graphics graphics) {
	graphics.setFont(DEFAULT_FONT);
	graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
	graphics.drawText("Entrada", rectangle.x - 24 + rectangle.width / 2,
		rectangle.y + 13);
    }

    private void drawInstruction(Graphics g) {
	if (isInstruction()) {

	    String _variables = instruccion.instruccion;

	    String[] _allVariables = instruccion.instruccion.split(";");

	    int _number = _variables.indexOf(";");

	    String _subInstruction = _variables.substring(0, _number);

	    if (_allVariables[0].length() > 6) {

		_subInstruction = _allVariables[0].substring(0, 5);
		g.drawText(_subInstruction + "...", rectangle.x + 10 +
			rectangle.width / 5, rectangle.y + 8);
	    } else if (_allVariables.length > 1) {

		_subInstruction = _allVariables[0];
		g.drawText(_subInstruction + "...", rectangle.x + 10 +
			rectangle.width / 5, rectangle.y + 8);
	    } else {
		g.drawText(_subInstruction, rectangle.x + 10 + 
			rectangle.width / 5, rectangle.y + 8);
	    }
	} else {
	    defaultInstruction(g);
	}
    }
}