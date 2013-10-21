package origami.administration.functionality.code.targets.naturalDescription;

//PARA que empieza en init. En cada iteracion verifica que (condicion) es verdadera, e incrementa var en value.
public class ForParser {
    	private String lineInput;
	private String lineOutput;
	
	
	public ForParser(String lineInput){
	    this.lineInput = lineInput;
	    this.lineOutput = "";
	    parsingInputLine();
	    
	}
	
	private void parsingInputLine(){
	    int leftIndex = lineInput.indexOf("(");
	    int rightIndex = lineInput.lastIndexOf(")");
	    boolean print = false;
	    String elements = lineInput.substring( leftIndex + 1, rightIndex);
	    System.out.println(elements);
	    String[]  splittedFor= elements.split(";",-1);
	    
	    if(splittedFor.length > 0 && splittedFor[0].trim().compareTo("") != 0){
		lineOutput+="PARA empezando en " +  splittedFor[0].trim() + ". ";
		print= true;
	    }
		
	    if(splittedFor.length > 1 &&splittedFor[1].trim().compareTo("") != 0){
		lineOutput+="En cada iteraci\u00f3n verifica que (";
		ConditionParser cp = new ConditionParser();
		cp.parsingInputLine(splittedFor[1].trim());
		System.out.println(cp.getLineOutput());
		lineOutput+=cp.getLineOutput() + ") es verdadera, ";
		print = true;
	    }
	    if(splittedFor.length > 2 && splittedFor[2].trim().compareTo("") != 0){	
	    	String iteration = getIteration(splittedFor[2].trim());
		System.out.println(splittedFor[2].trim());
		lineOutput+= iteration +".";
		print = true;
	    }
	    if(print == false){
		lineOutput = "PARA (Valor inicial; condicion; iteracion)";
	    }
	    
	}

	private String getIteration(String str){
	    String iteration =" iterando con ";
	    if (str.compareTo("") != 0){
		if(str.indexOf("++") != -1){
		    iteration = "incrementando 1 en " + str.replace("++", "").trim();
		}
		else if(str.indexOf("--") != -1){
		    iteration = "decrementando 1 en " + str.replace("--", "").trim();
		}
		else if(str.indexOf("=") != -1){
		    String[] splittedIteration = str.split("=",-1);
		    String leftSide = splittedIteration[0].trim();
		    String rightSide = splittedIteration[1].trim();
		    
		    if (leftSide.compareTo("") != 0 && rightSide.compareTo("") != 0){
	        	if(isValidVariableName(leftSide)){
	        	    //splitting using arithmetic operators and parenthesis 
	        	    String[] splitedRightSide = rightSide.split("[\\+\\-\\*/\\(\\)]",-1);
	        	    int ids = splitedRightSide.length;
	        	    if(ids == 1){
	        		rightSide = splitedRightSide[0].trim();
	        		if(isValidVariableName(rightSide) || isValidConstant(rightSide)){
	        		    //Calling method for SET
	        		   
	        		    //System.out.println("Set");
	        		    //System.out.println(printSetExpression());
	        		    iteration=printSetIteration(leftSide, rightSide);
	        		    }
	        	   }
	        	    else if (ids ==2){
	        		String beforeOp =splitedRightSide[0].trim();
	        		String afterOp =splitedRightSide[1].trim();
	        		if(beforeOp.compareTo("") != 0 && afterOp.compareTo("") != 0){
	        		    
	        		    switch(typeBinaryExpress(leftSide, rightSide, beforeOp, afterOp)){
	        		    //Updating
	        		    case 0:{
	        			//Calling increment
	        			
	        			//System.out.println("Increment");
	        			//System.out.println(printIncrement());
	        			iteration=printIncrement(leftSide,rightSide);
	        			break;
	        		    	}
	        		    case 1:{
	        			//Calling decrement
	        			
	        			//System.out.println("Decrement");
	        			//System.out.println(printDecrement());
	        			iteration= printDecrement(leftSide,rightSide);
	        			break;
	        		    	}
	        		    case 2:{
	        			//Calling multiply
	        			
	        			//System.out.println("Multiply");
	        			//System.out.println(printGeneralUpdating());
	        			iteration=printGeneralUpdating(leftSide, rightSide);
	        			break;
	        		           	}
	        		    case 3:{
	        			//Calling division
	        			
	        			//System.out.println("Division");
	        			//System.out.println(printGeneralUpdating());
	        			break;
	        		    	}
	        		    //Changing 
	        		    case 4:{
	        			
	        			//System.out.println("Change");
	        			//System.out.println(printGeneralChanging());
	        			lineOutput+=printGeneralChanging(leftSide, rightSide);
	        			break;
	        		    }
	        		    default:{
	        			//Calling generic assingment
	        			break;
	        		    	}
	        		  }//switch
	        		}
	        	    }//ids == 2
	        	    else if(ids >=3){
	        		if (areValidTokens(splitedRightSide)){
	        		    if (findLeftSideInRight(splitedRightSide, leftSide)){
	        			//Updating
	        			
	        			//System.out.println("3 updating");
	        			//System.out.println(printGeneralUpdating());
	        			iteration=printGeneralUpdating(leftSide,rightSide);
	        		    }
	        		    else{
	        			//Changing
	        			
	        			//System.out.println("3 changing");
	        			//System.out.println(printGeneralChanging());
	        			iteration=printGeneralChanging(leftSide, rightSide);
	        		    }
	        		}
	        	    }//ids == 3
	        	    
	        	}//leftSide is valid
	        }//Both sides have info
		else{
		    iteration= printGenericIteration(leftSide, rightSide);
		}
	      }//assignment
	     //No ++,-- or = characters included in iteration
	      else{
		    iteration= printGenericIteration("", str);
		}	
	    }//iteration not null
	    
	    return iteration; 
	    
	}
	
	private String printSetIteration(String left, String right ){
	    return "asignando " + right + "en" + left;
	}
	
	private String printIncrement(String leftSide,String rightSide){
	    String increment = rightSide.replace("+", "");
	    increment = increment.replace(leftSide, ""). trim();
	    return "incrementando " + increment + " en " + leftSide;
	}
	
	private String printDecrement(String leftSide,String rightSide){
	    String decrement = rightSide.replace("-", "");
	     decrement = decrement.replace(leftSide, ""). trim();
	    return "decrementando " + decrement + " en " + leftSide;
	}
	
	private String printGeneralUpdating(String leftSide, String rightSide){
	    return "actualizando " + leftSide + " con " + rightSide;
	}
	
	private String printGeneralChanging(String leftSide, String rightSide){
	    return "cambiando " + leftSide + " con " + rightSide;
	}
	
	private String printGenericIteration(String leftSide, String rightSide){
	        
	    return "asignando " + rightSide + " en " + leftSide;
	}
	
	private boolean areValidTokens(String[] tokens){
	    boolean flag = true;
	    for(int i = 0; i < tokens.length && flag == true; i++){
		if(tokens[i].trim().compareTo("") == 0){
		    flag = false;
		}
	    }
	    
	    return flag;
	}
	
	private boolean findLeftSideInRight(String[] tokens, String leftSide){
	    boolean flag = false;
	    for(int i = 0; i < tokens.length && flag == false; i++){
		if(tokens[i].compareTo(leftSide) == 0){
		    flag = true;
		}
	    }
	    
	    return flag;
	}
	
	private int typeBinaryExpress(String leftSide, String rightSide, String before, String after){
	    int typeExpress = -1;
	    //Checking updating
	    if((before.compareTo(leftSide)==0 && (isValidVariableName(after)|| isValidConstant(after))) ||
			(after.compareTo(leftSide)==0 && (isValidVariableName(before)|| isValidConstant(before)))){
		if(rightSide.contains("+")){
		    typeExpress = 0;
		}
		else if(rightSide.contains("-")){
		    typeExpress = 1; 
		}
		else if(rightSide.contains("*")){
		    typeExpress = 2; 
		}
		else if(rightSide.contains("/")){
		    typeExpress = 3; 
		}
		
	    }
	    //Checking changing
	    else if (((isValidVariableName(after)|| isValidConstant(after))) && 
		     ((isValidVariableName(before)|| isValidConstant(before))) ) {
		typeExpress = 4;
	    }
	    
	    return typeExpress;
	}
	
	private boolean isValidVariableName(String name){
	    boolean flag = false;
	    if(name.matches("[a-zA-Z](\\W)*")){
		flag = true;
	    }
	    return flag;
	    
	}
	
	private boolean isValidConstant(String str){
	     boolean flag = false;
		    if(str.matches("(\\d)+|((\\d)+\\.(\\d)+)")){
			flag = true;
		    }
           return flag;
	 }
	
	public String getLineOutput() {
	    return lineOutput;
	}

	
	public void setLineOutput(String lineOutput) {
	    this.lineOutput = lineOutput;
	}
}
