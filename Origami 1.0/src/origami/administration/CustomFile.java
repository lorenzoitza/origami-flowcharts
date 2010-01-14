package origami.administration;

import java.io.Serializable;
import java.util.Vector;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
@SuppressWarnings("serial")
public class CustomFile implements Serializable{
	private Vector<FigureStructure> diagrama;
	private Vector<String> info;
	
	public CustomFile(Vector<FigureStructure> diagrama){
		this.diagrama = diagrama;
	}

	public Vector<FigureStructure> getDiagrama() {
		return diagrama;
	}

	public void setDiagrama(Vector<FigureStructure> diagrama) {
		this.diagrama = diagrama;
	}

	public Vector<String> getInfo() {
		return info;
	}

	public void setInfo(Vector<String> info) {
		this.info = info;
	}

}
