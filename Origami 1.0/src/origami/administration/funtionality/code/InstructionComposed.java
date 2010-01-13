package origami.administration.funtionality.code;

import java.util.Vector;
/**
 * Esta clase es para almacenar el codigo
 * generado en el diagrama y son para figuras de ciclos.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class InstructionComposed  extends Instruction{
	public Vector<InstructionSimple> simpleInstructionList = new Vector<InstructionSimple>();
}
