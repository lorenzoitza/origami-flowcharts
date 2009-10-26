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
		
		int x1_coord = rectangle.x + (rectangle.width / 16 * 3);
		
		int x2_coord = rectangle.x + rectangle.width 
			- (rectangle.width / 16 * 3);
		
		int x3_coord = rectangle.x + rectangle.width;
		
		int y1_coord = rectangle.y + 5;
		
		int y2_coord = rectangle.y + rectangle.height / 2;
		
		int y3_coord = rectangle.y + rectangle.height - 5;
		
		
		_polygonPoints.addPoint(x1_coord, y1_coord);
		_polygonPoints.addPoint(x2_coord, y1_coord);
		_polygonPoints.addPoint(x3_coord, y2_coord);
		_polygonPoints.addPoint(x2_coord, y3_coord);
		_polygonPoints.addPoint(x1_coord, y3_coord);
		_polygonPoints.addPoint(rectangle.x, y2_coord);
		
		return _polygonPoints;
    }
    
    
    private void drawDefaultInstruction(Graphics graphics) {
    	int textXCoord = rectangle.x - 13 + rectangle.width / 2;
    	int textYCoord = rectangle.y + 13;
    	
		graphics.setFont(DEFAULT_FONT);
		
		graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
		
		graphics.drawText("Para", textXCoord, textYCoord);
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
			
			int subInstructionXCoord = rectangle.x + rectangle.width / 5;
			
			int subInstructionYCoord = rectangle.y + 13;
	
		    if ((_code.length() - 7) <= 2) {
				for (int indexOfVariable = 0; indexOfVariable < 3; indexOfVariable++) {
				    _subInstruction = _subInstruction + _variables[indexOfVariable] + ";";
				}
				g.drawText(_subInstruction, subInstructionXCoord, subInstructionYCoord);
		    } else {
				_subInstruction = _code.substring(0, _code.length() - 7) + "...";
				g.drawText(_subInstruction , subInstructionXCoord, subInstructionYCoord);
		    }
		} else {
		    drawDefaultInstruction(g);
		}
    }
}
