package origami.administration.funtionality.code;

import origami.graphics.MainWindow;

public class FormatCodeGDB extends FormatInstructions{
    
    private void fixStepByStep() {
	String code[] = this.codeSource.split("\n");
	
	String codeCopy;
	
	String nameVariable = "A5i9I";
	
	String nameSecondVariable = "A5i9W";
	
	int firstCount;
	
	int secondCount = 0;
	
	for (int index = 0; index < code.length; index++) {
	    
	    if (index == 3) {
		
		code[index] = "{int " + nameVariable + "=0," + nameSecondVariable + "=0" + ";";
	    }
	    codeCopy = code[index];
	    while (codeCopy.startsWith(" ")) {
		
		codeCopy = codeCopy.substring(1);
	    }
	    if (codeCopy.startsWith("if(") || codeCopy.startsWith("while(")
		    || codeCopy.startsWith("for(")) {
		
		firstCount = code[index].indexOf(")");
		while (firstCount != -1) {
		    
		    secondCount = firstCount;
		    firstCount = code[index].indexOf(")", secondCount + 1);
		}
		String firstPiece = code[index].substring(0, secondCount);
		
		String secondPiece = "&&" + nameVariable + "==0)";
		
		String thirtPiece = code[index].substring(secondCount + 1);
		
		code[index] = firstPiece + secondPiece + thirtPiece;
		
		if (codeCopy.startsWith("if(")) {
		    
		    keys(index, code, "FinDelIf");
		} else {
		    keys(index, code, "FinDelWhile");
		}
	    } else {

	    }
	}
	this.codeSource = "";
	for (int i = 0; i < code.length; i++) {
	    
	    this.codeSource = this.codeSource + code[i] + "\n";
	}
    }

    private void keys(int position, String[] code, String print) {
	String variable = "A5i9I";
	
	String variable2 = "A5i9W";
	
	String seg;
	
	int notIsKeyCount = 0;
	
	for (int i = position + 1; i < code.length; i++) {
	    
	    if (code[i].indexOf("for(") != -1) {
		
		notIsKeyCount++;
	    }
	    if (code[i].indexOf("if(") != -1 || code[i].indexOf("while(") != -1) {
		
		notIsKeyCount++;
	    }
	    if (code[i].indexOf("else{") != -1) {
		
		notIsKeyCount++;
	    } else if (code[i].indexOf("}") != -1 && notIsKeyCount == 0) {
		
		int actualPosition = code[i].indexOf("}");
		
		String prim = code[i].substring(0, actualPosition);
		
		if (print == "FinDelIf") {
		    
		    seg = variable + " = " + "0;}";
		} else {
		    seg = variable2 + " = " + "0;}";
		}
		String ter = code[i].substring(actualPosition + 1);
		code[i] = prim + seg + ter;
		break;
	    } else if (code[i].indexOf("}") != -1) {
		
		notIsKeyCount--;
	    }
	}

    }
    
    private String initVariablesUninitialized(String codigoTotal){
	CharSequence valor = "=";
	CharSequence entero = "int";
	CharSequence flotante = "float";
	CharSequence caracter = "char";
	CharSequence impresion = "cout";
	String[] lineas = codigoTotal.split("\n");
	codigoTotal="";
	for(int index=0; index<lineas.length; index++){
		if(index>3 && (!lineas[index].contains(impresion)) && (lineas[index].contains(entero) || lineas[index].contains(flotante) || lineas[index].contains(caracter)) ){
			if(!lineas[index].contains(valor)){
				while(lineas[index].startsWith(" ")){
					lineas[index] = lineas[index].substring(1);
				}
				if(lineas[index].startsWith("int")){
					String[] copia = lineas[index].split(";");
					lineas[index] = copia[0] + " = 0;";
				}
				else if(lineas[index].startsWith("char")){
					String[] copia = lineas[index].split(";");
					lineas[index] = copia[0] + " = '0';";
				}
				else if (lineas[index].startsWith("float")){
					String[] copia = lineas[index].split(";");
					lineas[index] = copia[0] + " = 0.0;";
				}
			}
		}
		else if(lineas[index].contains(impresion)){
			while(lineas[index].startsWith(" ")){
				lineas[index] = lineas[index].substring(1);
			}					
			lineas[index] = lineas[index].replaceFirst("endl;", "\".,.\"<<endl;");
		}
		codigoTotal=codigoTotal + lineas[index]+"\n";
	}
	return codigoTotal;
    }
    
    @Override
    public void applyFormat() {
	 ManagerCodeFormat manager = new ManagerCodeFormat(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
	 manager.formatCodeCpp();
	 this.codeSource = initVariablesUninitialized(manager.getInstructionsFormat());
	 fixStepByStep();
    }
}
