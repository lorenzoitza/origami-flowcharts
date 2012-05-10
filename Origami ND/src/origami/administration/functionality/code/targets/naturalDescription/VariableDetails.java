package origami.administration.functionality.code.targets.naturalDescription;

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
	
}
