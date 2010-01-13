package Administracion.Funcionalidad.Codigo;

import java.util.Vector;

public class FormatCodeC extends FormatInstructions {

    public FormatCodeC(Vector<String> codeOfFigure,
	    Vector<String> TableOfVariable) {
	super();
	setCodeOfFigure(codeOfFigure);
	setTableOfVariable(TableOfVariable);
    }

    @Override
    public void applyFormat() {
	divideDataInputForCodeC();
	codeSource =
		("#include <stdio.h>\n" + "#include <stdlib.h>"
			+ "\nint main(int argc, char argv[])\n{\n");
	for (int indexCodeFigure = 0; indexCodeFigure < codeOfFigure.size(); indexCodeFigure++) {
	    
	    String str = codeOfFigure.elementAt(indexCodeFigure);
	    int pos = str.indexOf("null");
	    if (pos < 0) {
		
		codeSource = codeSource + codeOfFigure.elementAt(indexCodeFigure) + "\n";
	    }
	}
	codeSource = codeSource + "      system(\"PAUSE\");\n      return 0;\n" + "}\n";
    }
    //identificar que nombre debe ser el correcto
    private void divideDataInputForCodeC() {
	for (int indexCodeFigure = 0; indexCodeFigure < codeOfFigure.size(); indexCodeFigure++) {

	    if (codeOfFigure.elementAt(indexCodeFigure).lastIndexOf(dataInput) >= 0) {

		Vector<String> dataSeparate =
			changeInstructionOfDataInputForCodeC(codeOfFigure
				.elementAt(indexCodeFigure));

		int indexCharacterL =
			codeOfFigure.elementAt(indexCodeFigure).indexOf("L");

		String space =
			codeOfFigure.elementAt(indexCodeFigure).substring(0,
				indexCharacterL);

		removeLinesRepeated(dataSeparate);

		codeOfFigure.removeElementAt(indexCodeFigure);

		for (int k = 0; k < dataSeparate.size(); k++) {

		    String lin = space + dataSeparate.elementAt(k);

		    codeOfFigure.insertElementAt(lin, indexCodeFigure);

		    indexCodeFigure++;
		}
		indexCodeFigure--;
	    } else if (codeOfFigure.elementAt(indexCodeFigure).lastIndexOf(
		    dataOutput) >= 0) {

		Vector<String> dataSeparate =
			changeInstructionOfDataOutputForCodeC(codeOfFigure
				.elementAt(indexCodeFigure));

		int indexCharacterP =
			codeOfFigure.elementAt(indexCodeFigure).indexOf("p");

		String space =
			codeOfFigure.elementAt(indexCodeFigure).substring(0,
				indexCharacterP - 1);

		codeOfFigure.removeElementAt(indexCodeFigure);

		for (int k = 0; k < dataSeparate.size(); k++) {

		    String lin = space + dataSeparate.elementAt(k);

		    codeOfFigure.insertElementAt(lin, indexCodeFigure);

		    indexCodeFigure++;
		}
		indexCodeFigure--;
	    }
	}
    }

    public void removeLinesRepeated(Vector<String> lineas) {
	String aux, aux2, aux3;
	int pos;
	for (int i = 0; i < lineas.size(); i++) {
	    aux = lineas.elementAt(i);
	    while (aux.startsWith(" ")) {
		aux = aux.substring(1);
	    }
	    pos = aux.indexOf(" ");
	    while (pos != -1) {
		aux3 = aux.substring(pos + 1);
		aux = aux.substring(0, pos) + aux3;
		pos = aux.indexOf(" ");
	    }
	    for (int j = 0; j < codeOfFigure.size(); j++) {
		aux2 = codeOfFigure.elementAt(j);
		while (aux2.startsWith(" ")) {
		    aux2 = aux2.substring(1);
		}
		pos = aux2.indexOf(" ");
		while (pos != -1) {
		    aux3 = aux2.substring(pos + 1);
		    aux2 = aux2.substring(0, pos) + aux3;
		    pos = aux2.indexOf(" ");
		}
		if (aux.compareTo(aux2) == 0) {
		    if (aux.indexOf("cin>>") == -1
			    && aux.indexOf("scanf(") == -1) {
			lineas.remove(i);
			i--;
			break;
		    }
		}
	    }
	}
    }

    private Vector<String> changeInstructionOfDataInputForCodeC(String linea) {
	Vector<String> datos = new Vector<String>();
	String scanCaracter = "scanf(" + "\"" + "%" + "c" + "\"" + "," + "&";
	String scanEnteros = "scanf(" + "\"" + "%" + "d" + "\"" + "," + "&";
	String scanFlotante = "scanf(" + "\"" + "%" + "f" + "\"" + "," + "&";
	String scanDefaul = "scanf(" + "\"" + "%" + "d" + "\"" + "," + "&";
	String ultimo = ");";

	String data =
		linea.substring(linea.lastIndexOf(dataInput) + 5, linea.length());

	while (data.startsWith(" ")) {
	    data = data.substring(1);
	}


	if (data.lastIndexOf("int ") >= 0) {
	    String dato =
		    data.substring(data.lastIndexOf("int") + 3, data
			    .lastIndexOf(";"));

	    String lin = scanEnteros + dato + ultimo;

	    if (!isDeclarada(data)) {
		this.TableOfVariable.add(data);
		datos.add(data);
	    }
	    datos.add(lin);

	} else if (data.lastIndexOf("char ") >= 0) {
	    String dato =
		    data.substring(data.lastIndexOf("char") + 4, data
			    .lastIndexOf(";"));

	    String lin = scanCaracter + dato + ultimo;

	    if (!isDeclarada(data)) {
		this.TableOfVariable.add(data);
		datos.add(data);
	    }
	    datos.add(lin);

	} else if (data.lastIndexOf("float ") >= 0) {
	    String dato =
		    data.substring(data.lastIndexOf("float") + 5, data
			    .lastIndexOf(";"));

	    String lin = scanFlotante + dato + ultimo;

	    if (!isDeclarada(data)) {
		this.TableOfVariable.add(data);
		datos.add(data);
	    }
	    datos.add(lin);
	} else {

	    for (int i = 0; i < TableOfVariable.size(); i++) {
		if (TableOfVariable.elementAt(i).lastIndexOf(data) >= 0) {
		    if (TableOfVariable.elementAt(i).lastIndexOf("char") >= 0) {
			scanDefaul = scanCaracter;
		    } else if (TableOfVariable.elementAt(i)
			    .lastIndexOf("float") >= 0) {
			scanDefaul = scanFlotante;
		    }
		    break;
		}
	    }
	    String dato = data.substring(0, data.lastIndexOf(";"));

	    String lin = scanDefaul + dato + ultimo;

	    if (!isDeclarada(data)) {
		this.TableOfVariable.add(data);
		datos.add(data);
	    }
	    datos.add(lin);
	}
	return datos;
    }
    
    private Vector<String> changeInstructionOfDataOutputForCodeC(String linea) {
	Vector<String> imprimir = new Vector<String>();
	String print = "printf(" + "\" ";
	String entero = "%d ";
	String caracter = "%c ";
	String flotante = "%f ";
	String separador = ",";
	String ultimo = ");";

	String data =
		linea.substring(linea.lastIndexOf(dataOutput) + 2,
			linea.length() - 1);

	while (data.startsWith(" ")) {
	    data = data.substring(1);
	}

	String datos[] = data.split(",");

	String instruccion = print;

	String parteFinalDeLaInstruccion = "";

	for (int i = 0; i < datos.length; i++) {
	    String tipo = this.getTypeOfData(datos[i]);
	    if (tipo != null) {
		if (tipo == "int") {
		    instruccion = instruccion + entero;
		} else if (tipo == "float") {
		    instruccion = instruccion + flotante;
		} else {
		    instruccion = instruccion + caracter;
		}
		parteFinalDeLaInstruccion =
			parteFinalDeLaInstruccion + separador + datos[i];
	    } else {
		if (datos[i].contains("\"")) {
		    instruccion =
			    instruccion
				    + datos[i].substring(1,
					    datos[i].length() - 1);
		} else {
		    parteFinalDeLaInstruccion =
			    parteFinalDeLaInstruccion + separador + datos[i];
		}
	    }
	}

	instruccion = instruccion + "\"" + parteFinalDeLaInstruccion + ultimo;

	imprimir.add(instruccion);

	return imprimir;
    }

}
