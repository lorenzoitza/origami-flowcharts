package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import Administracion.Figura;
import Administracion.Funcionalidad.Codigo.*;

/**
 * Esta clase es la que crea y dibujar a la figura del While.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class WhileFigure extends Figura {

    public InstruccionCompuesta instruccion = new InstruccionCompuesta();

    public WhileFigure() {
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
		graphics.drawPolygon(constructPolygonPoints());
		drawInstruction(graphics);
    }

    private void drawDefaultInstruction(Graphics graphics) {
		String defaultText = "Mientras";
		
		int xCoord = rectangle.x - 22 + rectangle.width / 2;
		
		int yCoord = rectangle.y + 13;
		
    	graphics.setFont(DEFAULT_FONT);
		graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
		graphics.drawText(defaultText, xCoord, yCoord);
    }

    private boolean isInstruction() {
    	boolean isNull = instruccion.instruccion.firstElement().getInstruccionSimple() == null;
    	
    	//boolean variable qu necesito saber exactamente para que querian.
		return instruccion.instruccion.size() > 1 && (!isNull);
    }

    private PointList constructPolygonPoints() {
		PointList _polygonPoints = new PointList();
		
		int x1Coord = rectangle.x + rectangle.width / 2;
		
		int x2Coord = rectangle.x + rectangle.width;
		
		int x3Coord = rectangle.x;
		
		int y1Coord = rectangle.y;
		
		int y2Coord = rectangle.y + rectangle.height / 2;
		
		int y3Coord = rectangle.y + rectangle.height;
		
		_polygonPoints.addPoint(x1Coord, y1Coord);
		_polygonPoints.addPoint(x2Coord, y2Coord);
		_polygonPoints.addPoint(x1Coord, y3Coord);
		_polygonPoints.addPoint(x3Coord, y2Coord);
		return _polygonPoints;
    }

    private void drawInstruction(Graphics graphics) {
		if (isInstruction()) {
		    String _instruction = instruccion.instruccion.elementAt(0).getInstruccionSimple();
	
		    if (_instruction.length() <= 11) {
	
			String _subInstruction = _instruction.substring(6,
				_instruction.length() - 2);
			graphics.drawText(_subInstruction, rectangle.x - 16 + rectangle.width
				/ 2, rectangle.y + 13);
		    } else {
			String _subInstruction = _instruction.substring(6, 9);
			graphics.drawText(_subInstruction + "...", rectangle.x - 16
				+ rectangle.width / 2, rectangle.y + 13);
		    }
	
		} else {
		    drawDefaultInstruction(graphics);
		}
    }
}
