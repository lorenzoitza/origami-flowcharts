package Administracion.Funcionalidad.Codigo;

import java.util.Vector;

public class FormatCodeCpp extends FormatInstructions {

    public FormatCodeCpp(Vector<String> codeOfFigure,
	    Vector<String> TableOfVariable) {
	super();
	setCodeOfFigure(codeOfFigure);
	setTableOfVariable(TableOfVariable);
    }

    @Override
    public void applyFormat() {
	divideDataInputForCodeCpp();
	codeSource =
		("#include <iostream>\n" + "using namespace std;\n"
			+ "int main(int argc, char argv[])\n{\n");
	for (int indexCodeFigure = 0; indexCodeFigure < codeOfFigure.size(); indexCodeFigure++) {
	    
	    String str = codeOfFigure.elementAt(indexCodeFigure);
	    int pos = str.indexOf("null");
	    if (pos < 0) {
		
		codeSource = codeSource + codeOfFigure.elementAt(indexCodeFigure) + "\n";
	    }
	}
	codeSource = codeSource + "      return 0;\n" + "}\n";
    }
    //identificar que nombre debe ser el correcto
    private void divideDataInputForCodeCpp() {

	for (int indexCode = 0; indexCode < codeOfFigure.size(); indexCode++) {

	    if (codeOfFigure.elementAt(indexCode).lastIndexOf(dataInput) >= 0) {

		Vector<String> lineas =
			changeInstructionOfDataInputForCodeCpp(codeOfFigure
				.elementAt(indexCode));

		String tab = codeOfFigure.elementAt(indexCode);

		int j = codeOfFigure.elementAt(indexCode).indexOf("L");

		tab = tab.substring(0, j);

		removeLinesRepeated(lineas);

		codeOfFigure.removeElementAt(indexCode);

		for (int k = 0; k < lineas.size(); k++) {

		    String lin = tab + lineas.elementAt(k);

		    codeOfFigure.insertElementAt(lin, indexCode);

		    indexCode++;

		}

		indexCode--;
	    } else if (codeOfFigure.elementAt(indexCode).lastIndexOf(dataOutput) >= 0) {

		Vector<String> lineas =
			changeInstructionOfDataOutputForCodeCpp(codeOfFigure
				.elementAt(indexCode));

		int indexCharacterP = codeOfFigure.elementAt(indexCode).indexOf("p");

		String space =
			codeOfFigure.elementAt(indexCode).substring(0,
				indexCharacterP - 1);

		codeOfFigure.removeElementAt(indexCode);

		for (int k = 0; k < lineas.size(); k++) {

		    String lin = space + lineas.elementAt(k);

		    codeOfFigure.insertElementAt(lin, indexCode);

		    indexCode++;
		}
		indexCode--;
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

    private Vector<String> changeInstructionOfDataInputForCodeCpp(String linea) {
	Vector<String> datos = new Vector<String>();
	String scan = "cin>>";
	String ultimo = ";";
	String lin = "";
	String dato = "";

	String data =
		linea.substring(linea.lastIndexOf(dataInput) + 5, linea.length());

	while (data.startsWith(" ")) {
	    data = data.substring(1);
	}

	if (data.lastIndexOf("int ") >= 0) {
	    dato =
		    data.substring(data.lastIndexOf("int") + 3, data
			    .lastIndexOf(";"));
	    if (!isDeclarada(data)) {
		this.TableOfVariable.add(data);
		datos.add(data);
	    }
	} else if (data.lastIndexOf("float ") >= 0) {
	    dato =
		    data.substring(data.lastIndexOf("float") + 5, data
			    .lastIndexOf(";"));
	    if (!isDeclarada(data)) {
		this.TableOfVariable.add(data);
		datos.add(data);
	    }
	} else if (data.lastIndexOf("char ") >= 0) {
	    dato =
		    data.substring(data.lastIndexOf("char") + 4, data
			    .lastIndexOf(";"));
	    if (!isDeclarada(data)) {
		this.TableOfVariable.add(data);
		datos.add(data);
	    }
	}

	lin = scan + dato + ultimo;

	datos.add(lin);

	return datos;
    }

    private Vector<String> changeInstructionOfDataOutputForCodeCpp(
	    String linea) {
	Vector<String> imprimir = new Vector<String>();
	String print = "cout ";
	String separador = "<< ";
	String ultimo = "<< endl;";

	String data =
		linea.substring(linea.lastIndexOf(dataOutput) + 2,
			linea.length() - 1);

	while (data.startsWith(" ")) {
	    data = data.substring(1);
	}

	String datos[] = data.split(",");

	String instruccion = print;

	for (int i = 0; i < datos.length; i++) {
	    instruccion = instruccion + separador + datos[i];
	}

	instruccion = instruccion + ultimo;

	imprimir.add(instruccion);

	return imprimir;
    }
}
