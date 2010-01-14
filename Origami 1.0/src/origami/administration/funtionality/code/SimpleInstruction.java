package origami.administration.funtionality.code;
/**
 * Esta clase es la que almacena
 * el codigo generado de cualquier figura
 * del diagrama.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class SimpleInstruction extends Instruction{
	public String simpleInstruction;
	/**
	 * Este metodo cambia el parametro 
	 * existente del codigo de la figura del diagrama.
	 * @param instruccion
	 */
	public void setInstruccionSimple(String instruccion){
		this.simpleInstruction = instruccion;
	}
	/**
	 * Este metodo devuelve el contenido del 
	 * codigo de cada figura del diagrama.
	 * @return  String
	 */
	public String getInstruccionSimple(){		
		return this.simpleInstruction;
	}
}
