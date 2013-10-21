package origami.administration.functionality.code.targets.naturalDescription;
/*
 * This class maintains the info corresponding to a variable
 * <type> <var_name> = <number>| <var_name>
 * if one of the elements appears it will considered valid, otherwise validDeclaration attribute will be false 
 * and the string name attribute will store it.
 * 
 * */

public class VariableDetails {
	
    	private String name;
	private boolean initialization;
	private String initValue;
	
	public VariableDetails(String name, boolean initialization, String initValue) {
		this.name = name;
		this.initialization = initialization;
		this.initValue = initValue;
		}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isInitialization() {
		return initialization;
	}

	public void setInitialization(boolean initialization) {
		this.initialization = initialization;
	}

	public String getInitValue() {
		return initValue;
	}

	public void setInitValue(String initValue) {
		this.initValue = initValue;
	}
	
	public String toString(){
	    return name + " "+ initialization + " "+ initValue;
	    
	}
}
