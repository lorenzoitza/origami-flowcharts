package origami.administration.functionality.code.targets;

import java.util.Vector;

import origami.administration.functionality.code.AbstractInstructionFormatter;


public class RubyCodeFormatter extends AbstractInstructionFormatter {
    
    	private Vector<String> instrucciones = new Vector<String>();
    	private Vector<String> outVector = new Vector<String>();
    	private Vector<String> variables = new Vector<String>();
    	
    public RubyCodeFormatter(Vector<String> codeOfFigure,
	    Vector<String> TableOfVariable) {
	super();
    		instrucciones =  codeOfFigure;
    		variables =  TableOfVariable;

	setCodeOfFigure(codeOfFigure);
	setTableOfVariable(TableOfVariable);
    }
    @Override
    public void applyFormat() {
	convertir();
	pasarString();

    }
    	
    	public Vector<String> convertir(){
    		String aux;
    		String numFor;
    		for(int i = 0; i < instrucciones.size(); i++){
    			if(instrucciones.get(i).contains("Leer: ")){
    				aux = instrucciones.get(i).substring(0, instrucciones.get(i).indexOf("Leer: ")) + instrucciones.get(i).substring(instrucciones.get(i).indexOf("Leer: "),instrucciones.get(i).length()-1);
    				if(aux.contains("int " )){
    					aux = instrucciones.get(i).substring(0, instrucciones.get(i).indexOf("Leer: ")) + aux.substring(aux.indexOf("int ") + 4, aux.length());
    					outVector.add(instrucciones.get(i).substring(0, instrucciones.get(i).indexOf("Leer: ")) + "STDOUT");
    					outVector.add( aux + " = gets.chomp");
    				}
    				else if(aux.contains("float ")){
    					aux = instrucciones.get(i).substring(0, instrucciones.get(i).indexOf("Leer: ")) + aux.substring(aux.indexOf("float ") + 6, aux.length());
    					outVector.add(instrucciones.get(i).substring(0, instrucciones.get(i).indexOf("Leer: ")) + "STDOUT");
    					outVector.add( aux + " = gets.chomp");
    				}
    				else if(aux.contains("char ")){
    					aux = instrucciones.get(i).substring(0, instrucciones.get(i).indexOf("Leer: ")) + aux.substring(aux.indexOf("char ") + 5, aux.length());
    					outVector.add(instrucciones.get(i).substring(0, instrucciones.get(i).indexOf("Leer: ")) + "STDOUT");
    					outVector.add( aux + " = gets.chomp");
    				}
    				else if(aux.contains("double ")){
    					aux = instrucciones.get(i).substring(0, instrucciones.get(i).indexOf("Leer: ")) + aux.substring(aux.indexOf("double ") + 7, aux.length());
    					outVector.add(instrucciones.get(i).substring(0, instrucciones.get(i).indexOf("Leer: ")) + "STDOUT");
    					outVector.add( aux + " = gets.chomp");
    				}
    				else{
    					outVector.add(instrucciones.get(i).substring(0,instrucciones.get(i).indexOf("Leer: ")) + "STDOUT");
    					outVector.add(instrucciones.get(i).substring(0,instrucciones.get(i).indexOf("Leer: ")) + aux.substring(aux.indexOf("Leer: ")+6,aux.length()) + " = gets.chomp");
    				}	
    			}
    			else if(instrucciones.get(i).contains("for(")){
    				aux = instrucciones.get(i).substring(0, instrucciones.get(i).indexOf("for")) + "for ";
    				numFor = instrucciones.get(i).substring(instrucciones.get(i).indexOf("for(") + 4,instrucciones.get(i).indexOf("=")) + " in " + instrucciones.get(i).substring(instrucciones.get(i).indexOf("=")+1,instrucciones.get(i).indexOf(";")) + "..";
    				aux = aux + numFor;
    				numFor = instrucciones.get(i).substring(instrucciones.get(i).indexOf(";") + 1);
    				numFor =  numFor.substring(0,numFor.indexOf(";"));
    				if(numFor.contains("<=")){
    					aux = aux + numFor.substring(numFor.indexOf("<=")+2);
    				}
    				else{
    					String a = numFor.substring(numFor.indexOf("<")+1);
    					int h = Integer.parseInt(a)-1;
    					aux = aux + h;
    				}
    				outVector.add(aux);
    			}
    			else if(instrucciones.get(i).contains("\\p")){
    				aux ="puts " + instrucciones.get(i).substring(instrucciones.get(i).indexOf("\\p") + 2,instrucciones.get(i).length()-1);
    				outVector.add(aux);
    			}
    			else if(instrucciones.get(i).contains("}")){
    					outVector.add(instrucciones.get(i).substring(instrucciones.get(i).indexOf("}")-1, instrucciones.get(i).indexOf("}")) + "end ");
    					}
    				else 
    					outVector.add(instrucciones.get(i).substring(0, instrucciones.get(i).length()-1));
    			}
    		outVector = quitarLLaves(outVector);
    		return outVector;
    }
    	
    	public static Vector<String> quitarLLaves(Vector<String> vectorIn){
    		int i = 0, pila = 0;
    		Vector<String> temp = new Vector<String>();
    		boolean band1 = false ;
    		while(i < vectorIn.size()){
    			if(vectorIn.get(i).contains("if(")){
    				temp.add(vectorIn.get(i));
    				band1 = true;
    				i++;
    				}
    				else if((vectorIn.get(i).contains("for ") || vectorIn.get(i).contains("while(")|| vectorIn.get(i).contains("else")) && band1 == true){
    					temp.add(vectorIn.get(i));
    					pila++;
    					i++;
    				}
    				else if((vectorIn.get(i).contains("for ") || vectorIn.get(i).contains("while(") || vectorIn.get(i).contains("else")) && band1 == false){
    					temp.add(vectorIn.get(i));
    					i++;
    				}
    				else if(vectorIn.get(i).contains("end ") && pila == 0){
    					i++;
    				}
    				else if(vectorIn.get(i).contains("end ") && pila != 0){
    					temp.add(vectorIn.get(i));
    					i++;
    					pila--;
    				}
    				else{
    					temp.add(vectorIn.get(i));
    					i++;
    				}
    			}
    		return temp;
    		}
    	
    
    	
    	private void pasarString() {
	    for(String current: outVector){
		sourceCode = sourceCode + "\n"+current;
	    }
	}
    	



}
