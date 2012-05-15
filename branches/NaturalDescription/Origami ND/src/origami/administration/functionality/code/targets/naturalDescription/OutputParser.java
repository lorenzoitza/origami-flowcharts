package origami.administration.functionality.code.targets.naturalDescription;


public class OutputParser {
    private String lineInput;
	private String lineOutput;
	
	
	public OutputParser(String lineInput){
	    this.lineInput = lineInput;
	    this.lineOutput = "";
	    parsingInputLine();
	    
	}
	
	private void parsingInputLine(){
	    lineOutput="SALIDA";
	    String[] splittedLineInput = lineInput.split(";");
	    String output = "";
	    for(int j = 0; j < splittedLineInput.length; j++){
		String splittedLineOutput = splittedLineInput[j].trim(); 
		//cropping the label \p
		splittedLineOutput = splittedLineOutput.substring(2, splittedLineOutput.length());
		if(splittedLineOutput.compareTo("") != 0){
		       
		      lineOutput="Imprimir ";
			output=splittedLineOutput;
			for (int i = 0; i< output.length(); i++){
			    char character = output.charAt(i);
			    if(character == '"'){
				String text = "\"";
				while(i+ 1 < output.length() && output.charAt(i +1) != '"'){
				    text+= output.charAt(i +1);
				    i++;
				}
				lineOutput+= text;
			    }//
			    else{
				lineOutput+=character;
			    }
			    
			}//for
		      }//splittedLineInput not null
		  //string output no well formed
		  else
		  {
		      lineOutput+= output;
		  }
		lineOutput+=". ";
	    }// for j, for each splitted line
	    
	}

	
	public String getLineOutput() {
	    return lineOutput;
	}

	
	public void setLineOutput(String lineOutput) {
	    this.lineOutput = lineOutput;
	}
}
