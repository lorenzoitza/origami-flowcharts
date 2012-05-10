package origami.administration.functionality.code.targets.naturalDescription;

import java.util.Vector;

public class Variable {

	private String type;
	private Vector<VariableDetails> listVariables;
	
	public Variable(String type){
		this.type = type;
		listVariables = new Vector<VariableDetails>();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Vector<VariableDetails> getListVariables() {
		return listVariables;
	}

	public void addListVariables(VariableDetails variable) {
		listVariables.add(variable);
	}
	
	
}
