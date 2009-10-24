package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import Administracion.*;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.Ventana;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

/**
 * Esta clase es la que crea y dibujar a la figura de imprimir.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class OutputFigure extends Figura {

    public InstruccionSimple instruccion = new InstruccionSimple();

    public OutputFigure() {
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
	graphics.drawPolygon(getPolygon());
	putInstruction(graphics, getVariables());
    }

    private PointList getPolygon() {
	PointList _polygon = new PointList();
	_polygon.addPoint(rectangle.x + 1, rectangle.y + 1);
	_polygon.addPoint(rectangle.x + (rectangle.width / 5) * 3,
		rectangle.y + 1);
	_polygon.addPoint(rectangle.x + rectangle.width - 1,
		rectangle.y +rectangle.height / 2 - 1);
	_polygon.addPoint(rectangle.x + (rectangle.width / 5) * 3,
		rectangle.y + rectangle.height - 1);
	_polygon.addPoint(rectangle.x + 1, rectangle.y + rectangle.height - 1);
	return _polygon;
    }

    private boolean isInstruction() {
	return instruccion.getInstruccionSimple().compareTo("null") != 0
		&& instruccion.getInstruccionSimple().compareTo("") != 0;
    }

    private void defaultInstruction(Graphics graphics) {
	graphics.setFont(DEFAULT_FONT);
	graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
	graphics.drawText("Salida", rectangle.x - 22 + rectangle.width / 2,
		rectangle.y + 13);
    }
    
    private String[] getVariables() {
	String[] _variables = new String[50];

	_variables = instruccion.getInstruccionSimple().split(";");

	String[] _finalVariables = new String[50];

	int cont = 0;

	for (int _variableIndex = 0; _variableIndex < _variables.length;
	_variableIndex++) {

	    _variables[_variableIndex] = _variables[_variableIndex].
	    replace("\\", "");
	    
	    _variables[_variableIndex] = _variables[_variableIndex].
	    replaceFirst("p", "");
	    
	    if (_variables[_variableIndex].compareTo("") != 0) {

		_finalVariables[cont] = _variables[_variableIndex];
		cont++;
	    }
	}
	for (int _variableIndex = 0; _variableIndex < cont; _variableIndex++) {

	    if (_finalVariables[_variableIndex].compareTo("") != 0) {

		_variables[_variableIndex] = _finalVariables[_variableIndex];
	    }
	}
	return _variables;
    }

    private void putInstruction(Graphics graphics, String[] variables) {
	if (isInstruction()) {

	    String[] _allVariables = instruccion.instruccion.split(";");

	    String _instruction;

	    int _letters = variables[0].length();

	    if (variables[0].length() > 6 || _allVariables.length > 1) {

		if (_letters > 5) {

		    _instruction = variables[0].substring(0, 5);
		} else {
		    _instruction = variables[0].substring(0, _letters);
		}
		graphics.drawText(_instruction + "...", rectangle.x +
			rectangle.width / 8, rectangle.y + 8);
	    } else {
		_instruction = variables[0].substring(0, _letters);
		graphics.drawText(_instruction, rectangle.x + rectangle.width / 8,
			rectangle.y + 8);
	    }
	} else {
	   defaultInstruction(graphics);
	}
    }
}