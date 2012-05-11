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
	    parsingInputLine();
	    
	}
	
	private void parsingInputLine(){
	    //Assuming it's possible to input more than one assignment from the block
	    String [] splitedLineInput =lineInput.split(";");
		
		//Going throught the vector of lines 
		//int declarationIndex = 0;
	        for(int i = 0; i < splitedLineInput.length; i++){
	            if(splitedLineInput[i].trim().compareTo("") != 0){
	        	
	        	String [] splitedLine = splitedLineInput[i].split("=");
	        	leftSide = splitedLine[0].trim();
	        	rightSide = splitedLine[1].trim();
	        	if (leftSide.compareTo("") != 0 && rightSide.compareTo("") != 0){
        	        	if(isValidVariableName(leftSide)){
        	        	    String[] splitedRightSide = rightSide.split("[\\+\\-\\*/\\(\\)]",-1);
        	        	    int ids = splitedRightSide.length;
        	        	    if(ids == 1){
        	        		rightSide = splitedRightSide[0].trim();
        	        		if(isValidVariableName(rightSide) || isValidConstant(rightSide)){
        	        		    //Calling method for SET
        	        		    System.out.println("Set");
        	        		    
        	        		    }
        	        		else{
        	        		    //Generic Assignment
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
        	        			System.out.println("Increment");
        	        			break;
        	        		    	}
        	        		    case 1:{
        	        			//Calling decrement
        	        			System.out.println("Decrement");
        	        			break;
        	        		    	}
        	        		    case 2:{
        	        			//Calling multiply
        	        			System.out.println("Multiply");
        	        			break;
    	        		           	}
        	        		    case 3:{
        	        			//Calling division
        	        			System.out.println("Division");
        	        			break;
        	        		    	}
        	        		    //Changing 
        	        		    case 4:{
        	        			System.out.println("Change");
        	        			break;
        	        		    }
        	        		    default:{
        	        			//Calling generic assigment
        	        			break;
        	        		    	}
        	        		  }//switch
        	        		}
        	        		else{
        	        		    //Generic Assigment
        	        		}
        	        		
        	        	    }
        	        	    else if(ids >=3){
        	        		if (areValidTokens(splitedRightSide)){
        	        		    if (findLeftSideInRight(splitedRightSide)){
        	        			//Updating
        	        			System.out.println("3 updating");
        	        		    }
        	        		    else{
        	        			//Changing
        	        			System.out.println("3 changing");
        	        		    }
        	        		}
        	        	    }
        	        	    else{
        	        		//Generic assignment
        	        	    }
        	        	}
        	        	//Generic assignment
        	        	else{
        	        	    
        	        	}
	        	}//Both sides have info
	            }//if splitedLine is not empty
	            
	        }// for i
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
	private boolean isIncrement(String str){
	   boolean  flag = false;
	    if (str.indexOf("+")!=-1){
		String[] splitedRight = str.split("\\+");
		String beforeOp =splitedRight[0].trim();
		String afterOp =splitedRight[1].trim();
		if(beforeOp.compareTo("") != 0 && afterOp.compareTo("") != 0){
		    if((beforeOp.compareTo(leftSide)==0 && (isValidVariableName(afterOp)|| isValidConstant(afterOp))) ||
			(afterOp.compareTo(leftSide)==0 && (isValidVariableName(beforeOp)|| isValidConstant(beforeOp))))
			{
			flag = true;
		    }
		}
	    }
	    return flag;
	}
	
	private boolean isDecrement(String str){
		   boolean  flag = false;
		    if (str.indexOf("-")!=-1){
			String[] splitedRight = str.split("\\-");
			String beforeOp =splitedRight[0].trim();
			String afterOp =splitedRight[1].trim();
			if(beforeOp.compareTo("") != 0 && afterOp.compareTo("") != 0){
			    if((beforeOp.compareTo(leftSide)==0 && (isValidVariableName(afterOp)|| isValidConstant(afterOp))) ||
				(afterOp.compareTo(leftSide)==0 && (isValidVariableName(beforeOp)|| isValidConstant(beforeOp))))
				{
				flag = true;
			    }
			}
		    }
		    return flag;
		}
	
	
}
