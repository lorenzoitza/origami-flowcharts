package origami.administration.funtionality.code;

public class SimpleInstruction extends Instruction{
	public String simpleInstruction;

	public void setInstruccionSimple(String instruccion){
		this.simpleInstruction = instruccion;
	}

	public String getInstruccionSimple(){		
		return this.simpleInstruction;
	}
}
