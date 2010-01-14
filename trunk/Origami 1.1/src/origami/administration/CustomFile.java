package origami.administration;

import java.io.Serializable;
import java.util.Vector;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class CustomFile implements Serializable{
	private Vector<Figura> diagrama;
	private Vector<String> info;
	
	public CustomFile(Vector<Figura> diagrama){
		this.diagrama = diagrama;
	}

	public Vector<Figura> getDiagrama() {
		return diagrama;
	}

	public void setDiagrama(Vector<Figura> diagrama) {
		this.diagrama = diagrama;
	}

	public Vector<String> getInfo() {
		return info;
	}

	public void setInfo(Vector<String> info) {
		this.info = info;
	}

}
