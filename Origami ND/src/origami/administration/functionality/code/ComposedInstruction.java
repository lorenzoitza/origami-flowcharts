package origami.administration.functionality.code;

import java.util.Vector;

@SuppressWarnings("serial")
public class ComposedInstruction  extends Instruction{
    
    private Vector<SimpleInstruction> simpleInstructionList = new Vector<SimpleInstruction>();
	
    public String getFirstInstructionSimple(){
	return getSimpleInstructionList().firstElement().getInstruccionSimple();
    }
	
    public void addFirstSimpleInstruction(SimpleInstruction simpleInstruction){
	getSimpleInstructionList().add(0, simpleInstruction);
    }
	
    public void addSimpleInstruction(SimpleInstruction simpleInstruction){
	getSimpleInstructionList().add(simpleInstruction);
    }
	
    public void addSimpleInstruccion(int index,SimpleInstruction simpleInstruction){
	getSimpleInstructionList().add(index,simpleInstruction);
    }
	
    public SimpleInstruction getSimpleInstructionAt(int index){
	return getSimpleInstructionList().elementAt(index);
    }
	
    public boolean isEmptyList(){
	return getSimpleInstructionList().isEmpty();
    }
	
    public int getListSize(){
	return getSimpleInstructionList().size();
    }

    public void setSimpleInstructionList(Vector<SimpleInstruction> simpleInstructionList) {
	this.simpleInstructionList = simpleInstructionList;
    }

    public Vector<SimpleInstruction> getSimpleInstructionList() {
	return simpleInstructionList;
    }
}
