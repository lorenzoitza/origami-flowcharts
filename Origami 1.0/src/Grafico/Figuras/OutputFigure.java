package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import Administracion.*;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

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
		
		PointList polygonPoints = constructPolygonPoints();
		graphics.drawPolygon(polygonPoints);
		drawInstruction(graphics, unformatOutputVariables());
    }

    private PointList constructPolygonPoints() {
		PointList _polygonPoints = new PointList();
		
		int x1Coord = rectangle.x + 1;
		
		int x2Coord = rectangle.x + (rectangle.width / 5) * 3;
		
		int x3Coord = rectangle.x + rectangle.width - 1;
		
		int y1Coord = rectangle.y + 1;
		
		int y2Coord = rectangle.y +rectangle.height / 2 - 1;
		
		int y3Coord = rectangle.y + rectangle.height - 1;
		
		_polygonPoints.addPoint(x1Coord, y1Coord);
		_polygonPoints.addPoint(x2Coord, y1Coord);
		_polygonPoints.addPoint(x3Coord, y2Coord);
		_polygonPoints.addPoint(x2Coord, y3Coord);
		_polygonPoints.addPoint(x1Coord, y3Coord);
		return _polygonPoints;
    }

    private boolean isInstruction() {
		return instruccion.getInstruccionSimple().compareTo("null") != 0
			&& instruccion.getInstruccionSimple().compareTo("") != 0;
    }

    private void drawDefaultInstruction(Graphics graphics) {
		graphics.setFont(DEFAULT_FONT);
		
		int xCoord = rectangle.x - 22 + rectangle.width / 2;
		
		int yCoord = rectangle.y + 13;
		
		graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
		graphics.drawText("Salida", xCoord, yCoord);
    }
    
    private String[] unformatOutputVariables() {
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

    private void drawInstruction(Graphics graphics, String[] variables) {
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
		   drawDefaultInstruction(graphics);
		}
    }
}