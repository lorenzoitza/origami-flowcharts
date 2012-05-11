package origami.administration.functionality.code.targets.naturalDescription;

import java.util.Vector;


public class InputParser {
    
    private String lineInput;
    private Vector<Variable> tableVariable;
    private String lineOutput;
    
    public InputParser(String lineInput){
	this.lineInput = lineInput;
	tableVariable = new Vector<Variable>();
	lineOutput="";
	parsingLineInput();
    }
    
    public void parsingLineInput(){
	//Spliting the line by semicolon
	String [] splitedLineInput =lineInput.split(";");
	
	//Going throught the vector of lines 
	//int declarationIndex = 0;
        for(int i = 0; i < splitedLineInput.length; i++){
	  if(splitedLineInput[i].trim().compareTo("") != 0){
            if(splitedLineInput[i].indexOf("Leer:") != -1){
		lineOutput += "Lees ";
		
		splitedLineInput[i] = splitedLineInput[i].replaceAll("Leer:", "");
	    }
            else {
		lineOutput += "Declaras ";
				
	    }
            //Spliting the line by comma
            String [] splitedLine = splitedLineInput[i].split(","); 
            // for each segment in the line
             String type="";
	     Variable variable;
	     
	     int k = 0;
             while(k < splitedLine.length){
        	String name="";
   	     	boolean initialization = false;
   	     	String initValue ="";
        	if(splitedLine[k].trim().compareTo("") != 0){
		    String segment = splitedLine[k].trim();
		    //check for type
		    type = getDataType(segment);
		    variable= new Variable(type);
		    segment = segment.replaceAll(type, "").trim();
		    int indexInitialization = segment.indexOf("=");
		    if(indexInitialization != -1){
			name = segment.substring(0, indexInitialization - 1 ).trim();
			initValue = segment.substring(indexInitialization + 1 , segment.length()).trim();
			initialization = true;
		    }
		    else{
			name = segment;
		    }
		    variable.addListVariablesDetaials(name, initialization, initValue);
		    //Checking if there is a new line, without type and no empty
		    while(k + 1 < splitedLine.length && getDataType(splitedLine[k+1]).compareTo("") ==0 ){
			k++;
			segment = splitedLine[k].trim();
			if (segment.trim().compareTo("") !=0){
			    name="";
		   	    initialization = false;
		   	    initValue ="";    
		   	    indexInitialization = segment.indexOf("=");
			   if(indexInitialization != -1){
			       name = segment.substring(0, indexInitialization - 1 ).trim();
			       initValue = segment.substring(indexInitialization + 1 , segment.length()).trim();
			       initialization = true;
			    }
			    else{
				name = segment;
			    }
			   variable.addListVariablesDetaials(name, initialization, initValue);
			}//if line is no empty
		    }//while checking new line
		    tableVariable.add(variable);
		    lineOutput+= naturalDescriptionVariable(variable);
		}//if segment is not empty
		k++;
            }//while k
            //Before to take other line complete the output!!!
             //System.out.println(tableVariable.get(tableVariable.size() - 1).toString());
             lineOutput = removeLastComma(lineOutput);
             //Add a new line
             lineOutput+= ".";
	  }//if the splited line by ; is not empty.
        
        }//for i
        System.out.println(tableVariable.size());
	
    }//parsinLineInput
    
    public String printTableVariable(){
	String str="";
	for(int i = 0; i < tableVariable.size(); i++){
	    str+= tableVariable.get(i).toString() + "\n";
	}
	return str;
    }
    private String removeLastComma(String str){
	str = str.trim();
	str = str.substring(0,str.length()-1).trim();
	return str;
    }
    
    private String getDataType(String dataType) {
	String type = "";
	if (dataType.contains("int ")) {
	    type = "int";
	} else if (dataType.contains("float ")) {
	    type = "float";
	} else if (dataType.contains("char ")) {
	    type = "char";
	}
	return type;
    }
    
    private String naturalDescriptionVariable(Variable variable){
	String str="";
	//Checking if is one o more variables, inner check verify if type defined
	Vector<VariableDetails> listVariables = variable.getListVariables();
	if(listVariables.size()==1){
	    String type = variable.getType();
	    if(type.compareTo("") != 0){
		 String typeSingular= getTypeSingular(type);
		 str+=typeSingular;
	    }
	}
	else{
	    String type = variable.getType();
	    if(type.compareTo("") != 0){
		 String typePlural= getTypePlural(type);
		 str+=typePlural;
	    }
	}
	//Then go throught variableList
	for(int i =0; i < listVariables.size(); i++ ){
	    String varDescription ="";
	    String initialization=" inicializado en ";
	    VariableDetails varDetails = listVariables.get(i);
	    varDescription+= varDetails.getName();
	    if(varDetails.isInitialization()){
		varDescription+= initialization + varDetails.getInitValue(); 
	    }
	    str+=varDescription+", ";
	}
	return str;
    } 
    
    private String getTypeSingular(String dataType){
	String str="";
	if (dataType.equals("int")) {
	    str = "el entero ";
	} else if (dataType.equals("float")) {
	    str = "el real ";
	} else if (dataType.equals("char")) {
	    str = "el caracter ";
	}
	return str;
	
    }
    private String getTypePlural(String dataType){
	String str="";
	if (dataType.equals("int")) {
	    str = "los enteros ";
	} else if (dataType.equals("float")) {
	    str = "los reales ";
	} else if (dataType.equals("char")) {
	    str = "los caracteres ";
	}
	return str;
    }
    
    public String getLineInput() {
        return lineInput;
    }
    
    public void setLineInput(String lineInput) {
        this.lineInput = lineInput;
    }
    
    public Vector<Variable> getTableVariable() {
        return tableVariable;
    }
    
    public void setTableVariable(Vector<Variable> tableVariable) {
        this.tableVariable = tableVariable;
    }

    public String getLineOutput() {
        return lineOutput;
    }

    public void setLineOutput(String lineOutput) {
        this.lineOutput = lineOutput;
    }
    
}
