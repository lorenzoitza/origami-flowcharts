package Administracion.Funcionalidad.Codigo;

import java.util.Vector;
/**
 * Esta clase es para almacenar el codigo
 * generado en el diagrama y son para figuras de ciclos.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class InstruccionCompuesta  extends Instruccion{
	public Vector<InstruccionSimple> instruccion = new Vector<InstruccionSimple>();
}
