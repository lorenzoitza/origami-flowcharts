package Administracion.Funcionalidad.Codigo;

import Grafico.MainWindow;

public class FormatCodeGDB extends FormatInstructions{
    
    //cambie codeTotal por this.code
    private void fixStepByStep() {
	String code[] = this.code.split("\n");
	
	String codeCopy;
	
	String variable = "A5i9I";
	
	String variable2 = "A5i9W";
	
	int firstCount;
	
	int secondCount = 0;
	
	for (int index = 0; index < code.length; index++) {
	    
	    if (index == 3) {
		
		code[index] = "{int " + variable + "=0," + variable2 + "=0" + ";";
	    }
	    codeCopy = code[index];
	    while (codeCopy.startsWith(" ")) {
		
		codeCopy = codeCopy.substring(1);
	    }
	    if (codeCopy.startsWith("if(") || codeCopy.startsWith("while(")
		    || codeCopy.startsWith("for(")) {
		
		// busco la ultima ")"
		firstCount = code[index].indexOf(")");
		while (firstCount != -1) {
		    
		    secondCount = firstCount;
		    firstCount = code[index].indexOf(")", secondCount + 1);
		}
		String firstPiece = code[index].substring(0, secondCount);
		
		String secondPiece = "&&" + variable + "==0)";
		
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
	this.code = "";
	for (int i = 0; i < code.length; i++) {
	    
	    this.code = this.code + code[i] + "\n";
	}
    }

    private void keys(int position, String[] aux, String print) {
	String variable = "A5i9I";
	
	String variable2 = "A5i9W";
	
	String seg;
	
	int notIsKeyCount = 0;
	
	for (int i = position + 1; i < aux.length; i++) {
	    
	    if (aux[i].indexOf("for(") != -1) {
		
		notIsKeyCount++;
	    }
	    if (aux[i].indexOf("if(") != -1 || aux[i].indexOf("while(") != -1) {
		
		notIsKeyCount++;
	    }
	    if (aux[i].indexOf("else{") != -1) {
		
		notIsKeyCount++;
	    } else if (aux[i].indexOf("}") != -1 && notIsKeyCount == 0) {
		
		int actualPosition = aux[i].indexOf("}");
		
		String prim = aux[i].substring(0, actualPosition);
		
		// String seg = "cout<<"+"\""+imprimir+"\""+"<< endl;}";
		if (print == "FinDelIf") {
		    
		    seg = variable + " = " + "0;}";
		} else {
		    seg = variable2 + " = " + "0;}";
		}
		String ter = aux[i].substring(actualPosition + 1);
		aux[i] = prim + seg + ter;
		break;
	    } else if (aux[i].indexOf("}") != -1) {
		
		notIsKeyCount--;
	    }
	}

    }
    
    @Override
    public void applyFormat() {
	 Manager manager = new Manager(MainWindow.getComponents()._diagrams.getHoja().getDiagrama());
	 manager.formatCodeCpp();
	 this.code = getCodeGDB(manager.getInstructionsFormat());
	 fixStepByStep();
    }
}
