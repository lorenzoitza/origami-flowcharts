package Grafico.Figuras;

import org.eclipse.draw2d.Graphics;
import Administracion.*;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Esta clase es la que crea y dibujar a la figura de Proceso.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class SentenceFigure extends Figura {

    public InstruccionSimple instruccion = new InstruccionSimple();

    public SentenceFigure() {
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
		drawRectangleFigure(graphics);
		drawInstruction(graphics);
    }
    
    private void drawRectangleFigure(Graphics graphics){
    	int rectangleArg0 = rectangle.x + 1;
    	
    	int rectangleArg1 = rectangle.y + 1;
    	
    	int rectangleArg2 = rectangle.width - 2;
    	
    	int rectangleArg3 = rectangle.height - 2;
    	
    	graphics.drawRectangle(rectangleArg0, rectangleArg1, rectangleArg2, rectangleArg3);
    }

    private void drawDefaultInstruction(Graphics graphics) {
		int xCoord = rectangle.x - 26 + rectangle.width / 2;
		
		int yCoord = rectangle.y + 13;
		
		String text = "Expresión";
    	
    	graphics.setFont(DEFAULT_FONT);
		graphics.setForegroundColor(DEFAULT_TEXTCOLOR);
		graphics.drawText(text, xCoord, yCoord);
    }

    private boolean isInstruction() {
		return instruccion.instruccion != "null"
			&& instruccion.instruccion.compareTo("null") != 0;
    }

    private void drawInstruction(Graphics graphics) {
		if (isInstruction()) {
		    
		    String _instruction = instruccion.instruccion;
		    
		    int xCoord = rectangle.x + rectangle.width / 8;
		    
		    int yCoord = rectangle.y + 15;
		    
		    if (_instruction.length() <= 10) {
				String subStr = _instruction.substring(0, _instruction.length() - 1);
				graphics.drawText(subStr, xCoord, yCoord);
			    } else {
				int maxDisplayCharacters = _instruction.length() - 9;
				String subStr = _instruction.substring(0, _instruction.length() -
					maxDisplayCharacters);
				graphics.drawText(subStr + "...", xCoord, yCoord);
		    }
		} else {
		    	drawDefaultInstruction(graphics);
		}
    }
}