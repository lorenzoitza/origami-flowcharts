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

	public void addListVariablesDetaials(VariableDetails variable) {
		listVariables.add(variable);
	}
	
	public void addListVariablesDetaials(String name, boolean initialization, String initValue) {
		
	    	listVariables.add(new VariableDetails(name, initialization, initValue));
	}
	
	public String toString(){
	    String var= "Type: " + type + "\n";
	    for(int i = 0; i < listVariables.size(); i++){
		var+= listVariables.get(i).toString() + "\n";		
	    }
	    return var;
	}
}
