package Administracion.Funcionalidad.Codigo;
/**
 * Esta clase es la que almacena
 * el codigo generado de cualquier figura
 * del diagrama.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class InstruccionSimple extends Instruccion{
	public String instruccion;
	/**
	 * Este metodo cambia el parametro 
	 * existente del codigo de la figura del diagrama.
	 * @param instruccion
	 */
	public void setInstruccionSimple(String instruccion){
		this.instruccion = instruccion;
	}
	/**
	 * Este metodo devuelve el contenido del 
	 * codigo de cada figura del diagrama.
	 * @return  String
	 */
	public String getInstruccionSimple(){		
		return this.instruccion;
	}
}
