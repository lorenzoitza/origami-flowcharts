package origami.administration.functionality.code.targets.naturalDescription;


public class ConditionParser {
    	private String lineInput;
	private String lineOutput;
	
	
	public ConditionParser(String lineInput){
	    this.lineInput = lineInput;
	    parsingInputLine();
	}
	
	public ConditionParser(){
	    
	}
	
	private void parsingInputLine(){
	   lineOutput = replaceOperators(extractCondition());
	}
	
	public void parsingInputLine(String line){
		   lineOutput = replaceOperators(line);
		}
	
	
	public String getLineInput() {
	    return lineInput;
	}
	
	private String extractCondition(){
	    int leftIndex = lineInput.indexOf("(");
	    int rightIndex = lineInput.lastIndexOf(")");
	    String condition = lineInput.substring(leftIndex + 1, rightIndex).trim();
	    System.out.println(condition);
	    return condition;
	}
	
	private String replaceOperators(String str){
	    String replacement = "";
	    for(int i = 0; i< str.length(); i++){
		char character = str.charAt(i);
		if(character == '>'){
		    if(i+1 < str.length() && str.charAt(i+1) == '='){
			replacement += " es mayor o igual a ";
			i++;
		    }
		    else{
			replacement += " es mayor a ";
		    }
		}
		else if(character == '<'){
		    if(i+1 < str.length() && str.charAt(i+1) == '='){
			replacement += " es menor o igual a ";
			i++;
		    }
		    else{
			replacement+=" es menor a ";
		    }
		    
		}
		else if(character == '='){
		    if(i+1 < str.length() && str.charAt(i+1) == '='){
			replacement += " es igual a ";
			i++;
		    }
		    else{
			replacement+="=";
		    }
		}
		else if(character == '!'){
		    if(i+1 < str.length() && str.charAt(i+1) == '='){
			replacement += " es diferente a ";
			i++;
		    }
		    else{
			replacement+="NO";
		    }
		    
		}
		else if(character == '&'){
		    if(i+1 < str.length() && str.charAt(i+1) == '&'){
			replacement += " Y ";
			i++;
		    }
		    else{
			replacement+="&";
		    }
		}
		else if(character == '|'){
		    if(i+1 < str.length() && str.charAt(i+1) == '|'){
			//00F3 unicode from o with accutte 
			replacement += " \u00D3 ";
			i++;
		    }
		    else{
			replacement+="|";
		    }
		}
		else{
		    replacement+=""+ character;
		}
		
	    }
	    
	    return replacement;
	}
	
	public String getLineOutput() {
	    return lineOutput;
	}

	
	public void setLineOutput(String lineOutput) {
	    this.lineOutput = lineOutput;
	}

}
