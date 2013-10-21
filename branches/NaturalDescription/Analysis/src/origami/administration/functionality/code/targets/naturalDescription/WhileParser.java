package origami.administration.functionality.code.targets.naturalDescription;


public class WhileParser {
    	private String lineInput;
	private String lineOutput;
	
	
	public WhileParser(String lineInput){
	    this.lineInput = lineInput;
	    parsingInputLine();
	    
	}
	
	private void parsingInputLine(){
	    lineOutput= "MIENTRAS ";
	    String condition = new ConditionParser(lineInput).getLineOutput().trim();
	    if (condition.compareTo("") == 0){
		lineOutput+= "la ( CONDICION ) ";
	    }
	    else{ 
		lineOutput+= "( "+ condition + " )";
	    }
	    
	    
	    lineOutput+= " es verdadera, ENTONCES";
	    
	}

	
	public String getLineOutput() {
	    return lineOutput;
	}

	
	public void setLineOutput(String lineOutput) {
	    this.lineOutput = lineOutput;
	}
}
