package origami.administration.functionality.code.targets;

import java.util.Vector;

import origami.administration.functionality.code.AbstractInstructionFormatter;


public class JavaCodeFormatter extends AbstractInstructionFormatter {
	private Vector<String>	pseudocodigo = new Vector<String>();
	
    public JavaCodeFormatter(Vector<String> codeOfFigure,
	    Vector<String> TableOfVariable) {
	super();
	setCodeOfFigure(codeOfFigure);
	setTableOfVariable(TableOfVariable);
    }

    @Override
    public void applyFormat() {
	traducirCodigo();
	pasarString();
	}
	
	private void pasarString() {
	    for(String current: pseudocodigo){
		sourceCode=sourceCode+"\n"+current;
	    }
	}
		
       /**
	* Metodo para traducir recorrer los renglones del codigo origami y traducirlos  a Java
	*/
		public void traducirCodigo(){
			pseudocodigo.add("import java.util.Scanner;");
			pseudocodigo.add("class Clase{");
			pseudocodigo.add("public static void main(String [] args){");
			for(int i=0; i<TableOfVariable.size(); i++){
				pseudocodigo.add("private "+TableOfVariable.get(i));
			}
			for(int i = 0; i < codeOfFigure.size(); i++){
				pseudocodigo.add(traducirLinea(codeOfFigure.get(i),i));
			}
			pseudocodigo.add("	}");
			pseudocodigo.add("}");
		}
		
		/**
		 * Metodo para traducir una linea de codigo origami a Java
		 * @param linea String renglon de codigo origami
		 * @return String renglon de Pseudocodigo
		 */
		private String traducirLinea(String linea, int i){
			String newLine = "";
			int k=0;
			//Parte de leer
			if(linea.indexOf("Leer:")!=-1){
					newLine += "Scanner leer"+i+"= new Scanner(System.in); ";
					newLine +="\n";
					k += 6;
					if(k==linea.indexOf("int")){
						newLine += "int "+linea.charAt(k+4)+";";
						newLine +="\n";
						newLine += linea.charAt(k+=4)+"=leer"+i+".nextInt();";
					}
					else{
						if(k==linea.indexOf("float")){
							newLine += "float "+linea.charAt(k+6)+";";
							newLine +="\n";
							newLine +=  linea.charAt(k+=6)+"=leer"+i+".nextFloat();";
						}
						else{
							if(k==linea.indexOf("char")){
								newLine += "String" +linea.charAt(k+5)+";";
								newLine +="\n";
								newLine +=  linea.charAt(k+=5)+"=leer"+i+".next();";
							}
							else{
								int pos=linea.indexOf("Leer:")+6;
								String sub=linea.substring(pos);
								for(int j=0; j<pseudocodigo.size(); j++){
									if(pseudocodigo.get(j).indexOf(sub)!=-1){
										if(pseudocodigo.get(j).indexOf(linea.charAt(linea.length()-2))!=-1){
											if(pseudocodigo.get(j).indexOf("int")!=-1){
												newLine += linea.charAt(linea.length()-2)+"= leer"+i+".nextInt();";
											}
											else{
												if(pseudocodigo.get(j).indexOf("float")!=-1){
													newLine += linea.charAt(linea.length()-2)+"= leer"+i+".nextFloat();";
												}
												else{
													pseudocodigo.set(j, "String "+ linea.charAt(linea.length()-2)+";");
													newLine += linea.charAt(linea.length()-2)+"= leer"+i+".next();";
												}
											}
										}
									}
								}
							}
						}
					}
				}
			else{
				//Parte para imprimir
				if(linea.indexOf("\\p") != -1){
					char o=',';
					char u='+';
					int pos=linea.indexOf("\\p")+2;
					if(linea.substring(pos,linea.length()-1).indexOf(",")!=-1){
						String v=linea.substring(pos,linea.length()-1).replace(o, u);		
						newLine+="	System.out.println("+""+v+""+");";

					}
					else{
						pos=linea.indexOf("\\p")+2;
						newLine+="	System.out.println("+""+linea.substring(pos,linea.length()-1)+""+");"; 
					}
				}
				else{
				    if(linea.indexOf("int")==-1&&linea.indexOf("float")==-1&&linea.indexOf("char")==-1){
					newLine += linea;
				    }				    
				}
			}
			return newLine;
		}

}

