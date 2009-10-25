package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import Administracion.Figura;
import Administracion.Funcionalidad.Codigo.InstruccionCompuesta;

/**
 * Esta clase es la que crea y dibujar la figura del For.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class ForFigure extends Figura {

    public InstruccionCompuesta instruccion = new InstruccionCompuesta();

    public ForFigure() {
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
	graphics.drawPolygon(getPolygonPoints());
	drawInstruction(graphics);
    }

    private PointList getPolygonPoints() {
	PointList _polygon = new PointList();
	_polygon.addPoint(rectangle.x + (rectangle.width / 16 * 3),
		rectangle.y + 5);
	_polygon.addPoint(rectangle.x + rectangle.width
		- (rectangle.width / 16 * 3), rectangle.y + 5);
	_polygon.addPoint(rectangle.x + rectangle.width, rectangle.y
		+ rectangle.height / 2);
	_polygon.addPoint(rectangle.x + rectangle.width
		- (rectangle.width / 16 * 3), rectangle.y + rectangle.height
		- 5);
	_polygon.addPoint(rectangle.x + (rectangle.width / 16 * 3), rectangle.y
		+ rectangle.height - 5);
	_polygon.addPoint(rectangle.x, rectangle.y + rectangle.height / 2);
	return _polygon;
    }

    private void defaultInstruction(Graphics graphics) {
	graphics.setFont(DEFAULT_FONT);
	graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
	graphics.drawText("Para", rectangle.x - 13 + rectangle.width / 2,
		rectangle.y + 13);
    }

    private void drawInstruction(Graphics g) {
	String _code =
		instruccion.instruccion.firstElement().getInstruccionSimple();
	_code = _code.replaceFirst("for", "");
	_code = _code.replace("(", "");
	_code = _code.replace(")", "");
	_code = _code.replace("{", "");
	if (_code.compareTo("null") != 0 && _code.compareTo("") != 0) {

	    String[] _variables = _code.split(";");

	    String _subInstruction = "";

	    if (_code.length() - 7 <= 2) {

		for (int x = 0; x < 3; x++) {

		    _subInstruction = _subInstruction + _variables[x] + ";";
		}
		g.drawText(_subInstruction, rectangle.x + rectangle.width / 5,
			rectangle.y + 13);
	    } else {
		_subInstruction = _code.substring(0, _code.length() - 7);
		g.drawText(_subInstruction + "...", rectangle.x
			+ rectangle.width / 5, rectangle.y + 13);
	    }
	} else {
	    defaultInstruction(g);
	}
    }
}
