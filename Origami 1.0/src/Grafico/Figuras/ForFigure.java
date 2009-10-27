package Grafico.Figuras;

import java.awt.Point;

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

    public InstruccionCompuesta instruction = new InstruccionCompuesta();

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
		
		PointList polygonPoints = constructPolygonPoints();
		
		graphics.drawPolygon(polygonPoints);
		
		drawInstruction(graphics);
    }

    private PointList constructPolygonPoints() {
		PointList _polygonPoints = new PointList();
		
		int x1Coord = rectangle.x + (rectangle.width / 16 * 3);
		
		int x2Coord = rectangle.x + rectangle.width - (rectangle.width / 16 * 3);
		
		int x3Coord = rectangle.x + rectangle.width;
		
		int y1Coord = rectangle.y + 5;
		
		int y2Coord = rectangle.y + (rectangle.height / 2);
		
		int y3Coord = rectangle.y + rectangle.height - 5;
		
		
		_polygonPoints.addPoint(x1Coord, y1Coord);
		_polygonPoints.addPoint(x2Coord, y1Coord);
		_polygonPoints.addPoint(x3Coord, y2Coord);
		_polygonPoints.addPoint(x2Coord, y3Coord);
		_polygonPoints.addPoint(x1Coord, y3Coord);
		_polygonPoints.addPoint(rectangle.x, y2Coord);
		
		return _polygonPoints;
    }
    
    
    private void drawDefaultInstruction(Graphics graphics) {
    	int xCoord = rectangle.x - 13 + rectangle.width / 2;
    	int textYCoord = rectangle.y + 13;
    	
		graphics.setFont(DEFAULT_FONT);
		
		graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
		
		graphics.drawText("Para", xCoord, textYCoord);
    }

    private void drawInstruction(Graphics g) {
		String _code =
			instruction.instruccion.firstElement().getInstruccionSimple();
		
		_code = _code.replaceFirst("for", "");
		
		_code = _code.replace("(", "");
		
		_code = _code.replace(")", "");
		
		_code = _code.replace("{", "");
		
		if ((_code.compareTo("null") != 0) && (_code.compareTo("") != 0)) {
		    
			String[] _variables = _code.split(";");
		    
			String _subInstruction = "";
			
			int xCoord = rectangle.x + rectangle.width / 5;
			
			int yCoord = rectangle.y + 13;
	
		    if ((_code.length() - 7) <= 2) {
				for (int index = 0; index < 3; index++) {
				    _subInstruction = _subInstruction + _variables[index] + ";";
				}
				g.drawText(_subInstruction, xCoord, yCoord);
		    } else {
				_subInstruction = _code.substring(0, _code.length() - 7) + "...";
				g.drawText(_subInstruction , xCoord, yCoord);
		    }
		} else {
		    drawDefaultInstruction(g);
		}
    }
}
