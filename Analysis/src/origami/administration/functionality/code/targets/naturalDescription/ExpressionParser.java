package origami.administration.functionality.code.targets.naturalDescription;

/*Assignment is splited and four cases are considered
 * Replacing:
    Set
    <var_name> =constant/variable 
    Change
    <var_name> = <arithmetic expression>
    Updating:
    Increment/Decrement
    <var_name> =<var_name> <+|-> constant 
    Update
    <var_name> =<var_name> <arithmectic_oper> <arithmetic expression>
    Any other type of assignment will be described as "'left' side is assigned to 'right side'"
 * */

public class ExpressionParser {

	private String lineInput;
	private String lineOutput;
	private String leftSide;
	private String rightSide;
	
	public ExpressionParser(String lineInput){
	    this.lineInput = lineInput;
	    this.lineOutput = "";
	    parsingInputLine();
	    
	}
	
	private void parsingInputLine(){
	    //Assuming it's possible to input more than one assignment from the block
	    String [] splitedLineInput =lineInput.split(";");
		
		//Going throught the vector of lines 
		//int declarationIndex = 0;
	        for(int i = 0; i < splitedLineInput.length; i++){
	            boolean printed = false;
	            if(splitedLineInput[i].trim().compareTo("") != 0){
	        	
	        	String [] splitedLine = splitedLineInput[i].split("=");
	        	leftSide = splitedLine[0].trim();
	        	rightSide = splitedLine[1].trim();
	        	
	        	if (leftSide.compareTo("") != 0 && rightSide.compareTo("") != 0){
        	        	if(isValidVariableName(leftSide)){
        	        	    //splitting using arithmetic operators and parenthesis 
        	        	    String[] splitedRightSide = rightSide.split("[\\+\\-\\*/\\(\\)]",-1);
        	        	    int ids = splitedRightSide.length;
        	        	    if(ids == 1){
        	        		rightSide = splitedRightSide[0].trim();
        	        		if(isValidVariableName(rightSide) || isValidConstant(rightSide)){
        	        		    //Calling method for SET
        	        		    printed = true;
        	        		    //System.out.println("Set");
        	        		    //System.out.println(printSetExpression());
        	        		    lineOutput+=printSetExpression();
        	        		    }
        	        	   }
        	        	    else if (ids ==2){
        	        		String beforeOp =splitedRightSide[0].trim();
        	        		String afterOp =splitedRightSide[1].trim();
        	        		if(beforeOp.compareTo("") != 0 && afterOp.compareTo("") != 0){
        	        		    
        	        		    switch(typeBinaryExpress(beforeOp, afterOp)){
        	        		    //Updating
        	        		    case 0:{
        	        			//Calling increment
        	        			printed = true;
        	        			//System.out.println("Increment");
        	        			//System.out.println(printIncrement());
        	        			lineOutput+=printIncrement();
        	        			break;
        	        		    	}
        	        		    case 1:{
        	        			//Calling decrement
        	        			printed = true;
        	        			//System.out.println("Decrement");
        	        			//System.out.println(printDecrement());
        	        			lineOutput+= printDecrement();
        	        			break;
        	        		    	}
        	        		    case 2:{
        	        			//Calling multiply
        	        			printed = true;
        	        			//System.out.println("Multiply");
        	        			//System.out.println(printGeneralUpdating());
        	        			lineOutput+=printGeneralUpdating();
        	        			break;
    	        		           	}
        	        		    case 3:{
        	        			//Calling division
        	        			printed = true;
        	        			//System.out.println("Division");
        	        			//System.out.println(printGeneralUpdating());
        	        			break;
        	        		    	}
        	        		    //Changing 
        	        		    case 4:{
        	        			printed = true;
        	        			//System.out.println("Change");
        	        			//System.out.println(printGeneralChanging());
        	        			lineOutput+=printGeneralChanging();
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
        	        		    if (findLeftSideInRight(splitedRightSide)){
        	        			//Updating
        	        			printed = true;
        	        			//System.out.println("3 updating");
        	        			//System.out.println(printGeneralUpdating());
        	        			lineOutput+=printGeneralUpdating();
        	        		    }
        	        		    else{
        	        			//Changing
        	        			printed = true;
        	        			//System.out.println("3 changing");
        	        			//System.out.println(printGeneralChanging());
        	        			lineOutput+=printGeneralChanging();
        	        		    }
        	        		}
        	        	    }//ids == 3
        	        	    
        	        	}//leftSide is valid
        	        }//Both sides have info
	            }//if splitedLine is not empty
	            
	            if (printed == false){
	        	//System.out.println("Generic Printing");
	        	//System.out.println(printGenericExpression());
	        	lineOutput+=printGenericExpression();
	            }
	            
	        }// for i
	}
	
	
	public String getLineOutput() {
	    return lineOutput;
	}

	
	public void setLineOutput(String lineOutput) {
	    this.lineOutput = lineOutput;
	}

	private String printGenericExpression(){
	        
	    return "Asignas " + this.rightSide + " en " + this.leftSide;
	}
	
	private String printSetExpression(){
	    return "Asignas el valor " + this.rightSide + " a la variable " + this.leftSide;
	}
	
	private String printIncrement(){
	    String update= this.rightSide;
	    update = update.replace(this.leftSide, "");
	    update = update.replace("+", "");
	    update = update.trim();
	    return "Incrementas "+ this.leftSide + " en " + update;
	}
	
	private String printDecrement(){
	    String update= this.rightSide;
	    update = update.replace(this.leftSide, "");
	    update = update.replace("+", "");
	    update = update.trim();
	    return "Decrementas "+ this.leftSide + " en " + update;
	}
	
	private String printGeneralUpdating(){
	    return "Actualizas "+ this.leftSide + " con " + this.rightSide;
	}
	
	private String printGeneralChanging(){
	    return "Cambias el valor de "+ this.leftSide + " con " + this.rightSide;
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
	
	private boolean findLeftSideInRight(String[] tokens){
	    boolean flag = false;
	    for(int i = 0; i < tokens.length && flag == false; i++){
		if(tokens[i].compareTo(leftSide) == 0){
		    flag = true;
		}
	    }
	    
	    return flag;
	}
	
	private int typeBinaryExpress(String before, String after){
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
	
	
	
}
