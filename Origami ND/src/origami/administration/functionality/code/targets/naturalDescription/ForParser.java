package origami.administration.functionality.code.targets.naturalDescription;

//PARA que empieza en init. En cada iteracion verifica que (condicion) es verdadera, e incrementa var en value.
public class ForParser {
    	private String lineInput;
	private String lineOutput;
	
	
	public ForParser(String lineInput){
	    this.lineInput = lineInput;
	    parsingInputLine();
	    
	}
	
	private void parsingInputLine(){
	    int leftIndex = lineInput.indexOf("(");
	    int rightIndex = lineInput.lastIndexOf(")");
	    String elements = lineInput.substring( leftIndex + 1, rightIndex);
	    System.out.println(elements);
	    String[] splittedFor = elements.split(";");
	    System.out.println(new ExpressionParser(splittedFor[0].trim()).getLineOutput());
	     ConditionParser cp = new ConditionParser();
	     cp.parsingInputLine(splittedFor[1].trim());
	     System.out.println(cp.getLineOutput());
	    System.out.println(new ExpressionParser(splittedFor[2].trim()).getLineOutput());
	}

	
	public String getLineOutput() {
	    return lineOutput;
	}

	
	public void setLineOutput(String lineOutput) {
	    this.lineOutput = lineOutput;
	}
}
