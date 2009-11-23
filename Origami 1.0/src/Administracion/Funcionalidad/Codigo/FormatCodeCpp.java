package Administracion.Funcionalidad.Codigo;

import java.util.Vector;


public class FormatCodeCpp extends FormatInstructions{

    public FormatCodeCpp(Vector<String> codeOfFigure,Vector<String> TableOfVariable){
		super();
		setCodeOfFigure(codeOfFigure);
		setTableOfVariable(TableOfVariable);
    }
    
    @Override
	public void applyFormat() {
    	divideDataInputForCodeCpp();
    	generateInstructionOfDataOutputForCodeCpp();
    	code =
    		("#include <stdio.h>\n" + "#include <stdlib.h>"
    			+ "\nint main(int argc, char argv[])\n{\n");
    	for (int indexCodeFigure = 0; indexCodeFigure < codeOfFigure.size();indexCodeFigure++) {
    	    String str = codeOfFigure.elementAt(indexCodeFigure);
    	    int pos = str.indexOf("null");
    	    if (pos < 0) {
    	    	code = code + codeOfFigure.elementAt(indexCodeFigure) + "\n";
    	    }
    	}
    	code = code + "      system(\"PAUSE\");\n      return 0;\n" + "}\n";
	}
    
    /*public void generateCodeCpp() {
	divideDataInputForCodeCpp();
	generateInstructionOfDataOutputForCodeCpp();
	code =
		("#include <stdio.h>\n" + "#include <stdlib.h>"
			+ "\nint main(int argc, char argv[])\n{\n");
	for (int indexCodeFigure = 0; indexCodeFigure < codeOfFigure.size();
	indexCodeFigure++) {
	    String str = codeOfFigure.elementAt(indexCodeFigure);
	    int pos = str.indexOf("null");
	    if (pos < 0) {
		code = code + codeOfFigure.elementAt(indexCodeFigure) + "\n";
	    }
	}
	code = code + "      system(\"PAUSE\");\n      return 0;\n" + "}\n";
    }*/
    
    private void divideDataInputForCodeCpp() {
	int j;
	for (int x = 0; x < codeOfFigure.size(); x++) {
	    if (codeOfFigure.elementAt(x).lastIndexOf("Leer:") >= 0) {
		Vector<String> lineas =
			generateInstructionOfDataInputForCodeCpp(codeOfFigure
				.elementAt(x));
		String tab = codeOfFigure.elementAt(x);
		j = codeOfFigure.elementAt(x).indexOf("L");
		tab = tab.substring(0, j);
		removeLinesRepeated(lineas);
		codeOfFigure.removeElementAt(x);
		for (int k = 0; k < lineas.size(); k++) {
		    String lin = tab + lineas.elementAt(k);
		    codeOfFigure.insertElementAt(lin, x);
		    x++;
		}
		x--;
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
    
    public Vector<String> generateInstructionOfDataInputForCodeCpp(String linea) {
	Vector<String> datos = null;
	Vector<String> variables = new Vector<String>();
	String scanCaracter = "cin>>";
	String scanEnteros = "cin>>";
	String scanFlotante = "cin>>";
	String scanDefaul = "cin>>";
	String ultimo = ";";

	int i = linea.indexOf(",");
	if (i != -1) {
	    datos = new Vector<String>();
	    linea =
		    linea.substring(linea.lastIndexOf("Leer:") + 5, linea
			    .length());
	    variables = separateData(linea);
	    for (int k = 0; k < variables.size(); k++) {
		datos.add(variables.elementAt(k));
	    }
	    for (int k = 0; k < variables.size(); k++) {
		if (variables.elementAt(k).lastIndexOf("int") >= 0) {
		    String dato =
			    variables.elementAt(k)
				    .substring(
					    variables.elementAt(k).lastIndexOf(
						    "int") + 3,
					    variables.elementAt(k).lastIndexOf(
						    ";"));
		    String lin = scanEnteros + dato + ultimo;
		    datos.add(lin);
		} else if (variables.elementAt(k).lastIndexOf("char") >= 0) {
		    String dato =
			    variables.elementAt(k)
				    .substring(
					    variables.elementAt(k).lastIndexOf(
						    "char") + 4,
					    variables.elementAt(k).lastIndexOf(
						    ";"));
		    String lin = scanCaracter + dato + ultimo;
		    datos.add(lin);
		} else if (variables.elementAt(k).lastIndexOf("float") >= 0) {
		    String dato =
			    variables.elementAt(k)
				    .substring(
					    variables.elementAt(k).lastIndexOf(
						    "float") + 5,
					    variables.elementAt(k).lastIndexOf(
						    ";"));
		    String lin = scanFlotante + dato + ultimo;
		    datos.add(lin);
		} else {
		    String dato =
			    variables.elementAt(k).substring(0,
				    variables.elementAt(k).lastIndexOf(";"));
		    String lin = scanDefaul + dato + ultimo;
		    datos.add(lin);
		}
	    }
	} else {
	    datos = new Vector<String>();
	    if (linea.lastIndexOf("int") != -1) {
		String dato =
			linea.substring(linea.lastIndexOf("int"), linea
				.length() - 1);
		TableOfVariable.add(dato + ";");
		String lin = scanEnteros + dato.substring(3) + ultimo;
		datos.add(dato + ";");
		datos.add(lin);
	    } else if (linea.lastIndexOf("char") != -1) {
		String dato =
			linea.substring(linea.lastIndexOf("char"), linea
				.length() - 1);
		TableOfVariable.add(dato + ";");
		String lin2 = scanCaracter + dato.substring(4) + ultimo;
		datos.add(dato + ";");
		datos.add(lin2);
	    } else if (linea.lastIndexOf("float") != -1) {
		String dato =
			linea.substring(linea.lastIndexOf("float"), linea
				.length() - 1);
		TableOfVariable.add(dato + ";");
		String lin2 = scanFlotante + dato.substring(5) + ultimo;
		datos.add(dato + ";");
		datos.add(lin2);
	    } else {
		String tipo =
			getTypeOfData(linea.substring(linea
				.lastIndexOf("Leer:") + 5, linea.length() - 1));
		if (tipo != null) {
		    if (tipo.lastIndexOf("int") >= 0) {
			datos.add(scanEnteros
				+ linea.substring(
					linea.lastIndexOf("Leer:") + 5, linea
						.length() - 1) + ultimo);
		    } else if (tipo.lastIndexOf("char") >= 0) {
			datos.add(scanCaracter
				+ linea.substring(
					linea.lastIndexOf("Leer:") + 5, linea
						.length() - 1) + ultimo);
		    } else {
			datos.add(scanFlotante
				+ linea.substring(
					linea.lastIndexOf("Leer:") + 5, linea
						.length() - 1) + ultimo);
		    }
		} else {
		    String lin2 =
			    scanDefaul
				    + linea.substring(linea
					    .lastIndexOf("Leer:") + 5, linea
					    .length() - 1) + ultimo;
		    datos.add(lin2);
		}
	    }
	}
	return datos;
    }
    
    public void generateInstructionOfDataOutputForCodeCpp() {
	Vector<String> imprimir = new Vector<String>();
	String printEnteros = "cout <<";
	String printFlotantes = "cout <<";
	String printCaracteres = "cout <<";
	String printTexto = "cout <<" + "\"";
	String printDefaul = "cout <<";
	String print = "cout <<";
	String entero = "";
	String caracter = "";
	String flotante = "";
	String separador = "<<";
	String ultimo = "<< endl;";

	for (int x = 0; x < codeOfFigure.size(); x++) {
	    String aux = codeOfFigure.elementAt(x);
	    while (aux.startsWith(" ")) {
		aux = aux.substring(1);
	    }
	    if (aux.startsWith("\\p")) {
		String tab =
			codeOfFigure.elementAt(x).substring(0,
				codeOfFigure.elementAt(x).indexOf("\\"));
		if (aux.indexOf(",") > 0) {
		    String inicio;
		    String fin = "";
		    String aux2;
		    int comaAnterior = aux.indexOf(",");
		    int comaPosterior = aux.indexOf(",", comaAnterior + 1);
		    aux2 = aux.substring(0, comaAnterior);
		    while (aux2.endsWith(" ")) {
			aux2 = aux2.substring(0, aux2.length() - 1);
		    }
		    if (aux2.startsWith("\\p" + "\"") && aux2.endsWith("\"")) {
			inicio =
				aux2.substring(aux.indexOf("\"") + 1, aux2
					.length() - 1);

			inicio = "";
			fin = fin + aux2.substring(2) + separador;
		    } else {
			String tipo = getTypeOfData(aux2.substring(2));
			if (tipo != null) {
			    if (tipo.indexOf("int") == 0) {
				inicio = entero;
				fin = fin + aux2.substring(2) + separador;
			    } else if (tipo.indexOf("char") == 0) {
				inicio = caracter;
				fin = fin + aux2.substring(2) + separador;
			    } else {
				inicio = flotante;
				fin = fin + aux2.substring(2) + separador;
			    }
			} else {
			    inicio = entero;
			    fin = fin + aux2.substring(2) + separador;
			}
		    }
		    while (comaPosterior != -1) {
			aux2 = aux.substring(comaAnterior + 1, comaPosterior);
			while (aux2.startsWith(" ")) {
			    aux2 = aux2.substring(1);
			}
			while (aux2.endsWith(" ")) {
			    aux2 = aux2.substring(0, aux2.length() - 1);
			}
			if (aux2.startsWith("\"") && aux2.endsWith("\"")) {
			    inicio =
				    inicio
					    + " "
					    + aux2.substring(1,
						    aux2.length() - 1);
			    inicio = "";
			    fin = fin + aux2 + separador;

			} else {
			    String tipo = getTypeOfData(aux2);
			    if (tipo != null) {
				if (tipo.indexOf("int") == 0) {
				    inicio = inicio + " " + entero;
				    fin = fin + aux2 + separador;
				} else if (tipo.indexOf("char") == 0) {
				    inicio = inicio + " " + caracter;
				    fin = fin + aux2 + separador;
				} else {
				    inicio = inicio + " " + flotante;
				    fin = fin + aux2 + separador;
				}
			    } else {
				inicio = inicio + " " + entero;
				fin = fin + aux2 + separador;
			    }
			}
			comaAnterior = comaPosterior;
			comaPosterior = aux.indexOf(",", comaAnterior + 1);
		    }
		    aux2 = aux.substring(comaAnterior + 1);
		    while (aux2.startsWith(" ")) {
			aux2 = aux2.substring(1);
		    }
		    if (aux2.endsWith(";")) {
			aux2 = aux2.substring(0, aux2.length() - 1);
		    }
		    if (aux2.startsWith("\"") && aux2.endsWith("\"")) {
			inicio =
				inicio + " "
					+ aux2.substring(1, aux2.length() - 1);
			inicio = "";
			fin = fin + aux2;
		    } else {
			String tipo = getTypeOfData(aux2);
			if (tipo != null) {
			    if (tipo.indexOf("int") == 0) {
				inicio = inicio + " " + entero;
				fin = fin + aux2;
			    } else if (tipo.indexOf("char") == 0) {
				inicio = inicio + " " + caracter;
				fin = fin + aux2;
			    } else {
				inicio = inicio + " " + flotante;
				fin = fin + aux2;
			    }
			} else {
			    inicio = inicio + " " + entero;
			    fin = fin + aux2;
			}
		    }
		    while (fin.endsWith(",")) {
			fin = fin.substring(0, fin.length() - 1);
		    }

		    inicio = "";
		    inicio = print + inicio + fin + "<< endl;";
		    inicio = tab + inicio;
		    codeOfFigure.removeElementAt(x);
		    codeOfFigure.insertElementAt(inicio, x);
		    imprimir.removeAllElements();

		} else {
		    if (aux.startsWith("\\p" + "\"")
			    && aux.endsWith("\"" + ";")) {
			int pos = aux.indexOf("\"");
			int fin = aux.indexOf("\"", pos + 1);
			String imp =
				tab + printTexto + aux.substring(pos + 1, fin)
					+ "\"" + ultimo;
			imprimir.add(imp);
			codeOfFigure.removeElementAt(x);
			codeOfFigure.insertElementAt(imprimir.elementAt(0), x);
			imprimir.removeAllElements();
		    } else {
			String tipo = getTypeOfData(aux.substring(2));
			if (tipo != null) {
			    if (tipo.indexOf("int") == 0) {
				int pos = aux.indexOf("p");
				int fin = aux.indexOf(";");
				if (fin != -1) {
				    String imp =
					    tab
						    + printEnteros
						    + aux.substring(pos + 1,
							    fin) + ultimo;
				    imprimir.add(imp);
				} else {
				    String imp =
					    tab
						    + printEnteros
						    + aux.substring(pos + 1,
							    fin - 1) + ultimo;
				    imprimir.add(imp);
				}
			    } else if (tipo.indexOf("char") == 0) {
				int pos = aux.indexOf("p");
				int fin = aux.indexOf(";");
				if (fin != -1) {
				    String imp =
					    tab
						    + printCaracteres
						    + aux.substring(pos + 1,
							    fin) + ultimo;
				    imprimir.add(imp);
				} else {
				    String imp =
					    tab
						    + printCaracteres
						    + aux.substring(pos + 1,
							    fin - 1) + ultimo;
				    imprimir.add(imp);
				}
			    } else {
				int pos = aux.indexOf("p");
				int fin = aux.indexOf(";");
				if (fin != -1) {
				    String imp =
					    tab
						    + printFlotantes
						    + aux.substring(pos + 1,
							    fin) + ultimo;
				    imprimir.add(imp);
				} else {
				    String imp =
					    tab
						    + printFlotantes
						    + aux.substring(pos + 1,
							    fin - 1) + ultimo;
				    imprimir.add(imp);
				}
			    }
			    codeOfFigure.removeElementAt(x);
			    codeOfFigure.insertElementAt(imprimir.elementAt(0),
				    x);
			    imprimir.removeAllElements();
			} else {
			    int pos = aux.indexOf("p");
			    int fin = aux.indexOf(";");
			    if (fin != -1) {
				String imp =
					tab + printDefaul
						+ aux.substring(pos + 1, fin)
						+ ultimo;
				imprimir.add(imp);
			    } else {
				String imp =
					tab
						+ printDefaul
						+ aux.substring(pos + 1,
							fin - 1) + ultimo;
				imprimir.add(imp);
			    }
			    codeOfFigure.removeElementAt(x);
			    codeOfFigure.insertElementAt(imprimir.elementAt(0),
				    x);
			    imprimir.removeAllElements();
			}
		    }
		}
	    }
	}
    }
    
    public Vector<String> separateData(String linea) {
	String tipoAnterior = null;
	Vector<String> variables = new Vector<String>();
	int comaAnterior = linea.indexOf(",");
	int comaPosterior = linea.indexOf(",", comaAnterior + 1);
	if (comaPosterior == -1) {
	    int tipo = linea.indexOf("int");
	    if (tipo != -1 && tipo < comaAnterior) {
		tipoAnterior = "int";
		TableOfVariable.add(linea.substring(linea.indexOf("int"),
			comaAnterior)
			+ ";");
		variables.add(linea.substring(linea.indexOf("int"),
			comaAnterior)
			+ ";");
		variables =
			separarVariables2(tipo, linea, comaAnterior, variables,
				tipoAnterior);
	    } else {
		tipo = linea.indexOf("char");
		if (tipo != -1 && tipo < comaAnterior) {
		    tipoAnterior = "char";
		    TableOfVariable.add(linea.substring(linea.indexOf("char"),
			    comaAnterior)
			    + ";");
		    variables.add(linea.substring(linea.indexOf("char"),
			    comaAnterior)
			    + ";");
		    variables =
			    separarVariables2(tipo, linea, comaAnterior,
				    variables, tipoAnterior);
		} else {
		    tipo = linea.indexOf("float");
		    if (tipo != -1 && tipo < comaAnterior) {
			tipoAnterior = "float";
			TableOfVariable.add(linea.substring(linea
				.indexOf("float"), comaAnterior)
				+ ";");
			variables.add(linea.substring(linea.indexOf("float"),
				comaAnterior)
				+ ";");
			variables =
				separarVariables2(tipo, linea, comaAnterior,
					variables, tipoAnterior);
		    } else {
			String tip =
				getTypeOfData(linea.substring(0, comaAnterior));
			if (tip != null) {
			    variables.add(tip
				    + linea.substring(0, comaAnterior) + ";");
			} else {
			    variables.add(linea.substring(0, comaAnterior)
				    + ";");
			}
			variables =
				separarVariables2(tipo, linea, comaAnterior,
					variables, tipoAnterior);
		    }
		}
	    }

	} else {
	    Vector<String> variables2 = new Vector<String>();
	    int tipo = linea.indexOf("int");
	    if (tipo != -1 && tipo < comaAnterior) {
		tipoAnterior = "int";
		TableOfVariable.add(linea.substring(linea.indexOf("int"),
			comaAnterior)
			+ ";");
		variables.add(linea.substring(linea.indexOf("int"),
			comaAnterior)
			+ ";");
		linea = linea.substring(comaAnterior + 1);
		variables2 = separateData(linea);
	    } else {
		tipo = linea.indexOf("char");
		if (tipo != -1 && tipo < comaAnterior) {
		    tipoAnterior = "char";
		    TableOfVariable.add(linea.substring(linea.indexOf("char"),
			    comaAnterior)
			    + ";");
		    variables.add(linea.substring(linea.indexOf("char"),
			    comaAnterior)
			    + ";");
		    linea = linea.substring(comaAnterior + 1);
		    variables2 = separateData(linea);
		} else {
		    tipo = linea.indexOf("float");
		    if (tipo != -1 && tipo < comaAnterior) {
			tipoAnterior = "float";
			TableOfVariable.add(linea.substring(linea
				.indexOf("float"), comaAnterior)
				+ ";");
			variables.add(linea.substring(linea.indexOf("float"),
				comaAnterior)
				+ ";");
			linea = linea.substring(comaAnterior + 1);
			variables2 = separateData(linea);
		    } else {
			String tip =
				getTypeOfData(linea.substring(0, comaAnterior));
			if (tip != null) {
			    variables.add(tip
				    + linea.substring(0, comaAnterior) + ";");
			} else {
			    variables.add(linea.substring(0, comaAnterior)
				    + ";");
			}
			linea = linea.substring(comaAnterior + 1);
			variables2 = separateData(linea);
		    }
		}
	    }
	    for (int i = 0; i < variables2.size(); i++) {
		variables.add(variables2.elementAt(i));
	    }
	}
	return variables;
    }
    
    public Vector<String> separarVariables2(int tipo, String linea,
	    int comaAnterior, Vector<String> variables, String tipoAnterior) {
	tipo = linea.indexOf("int", comaAnterior);
	if (tipo != -1) {
	    TableOfVariable.add(linea.substring(tipo, linea.length()));
	    variables.add(linea.substring(tipo, linea.length()));
	} else {
	    tipo = linea.indexOf("char", comaAnterior);
	    if (tipo != -1) {
		TableOfVariable.add(linea.substring(tipo, linea.length()));
		variables.add(linea.substring(tipo, linea.length()));
	    } else {
		tipo = linea.indexOf("float", comaAnterior);
		if (tipo != -1) {
		    TableOfVariable.add(linea.substring(tipo, linea.length()));
		    variables.add(linea.substring(tipo, linea.length()));
		} else {
		    String tip =
			    getTypeOfData(linea.substring(comaAnterior + 1,
				    linea.length() - 1));
		    if (tip != null) {
			variables.add(tip + linea.substring(comaAnterior + 1));
		    } else {
			if (tipoAnterior != null) {
			    variables.add(tipoAnterior + " "
				    + linea.substring(comaAnterior + 1));
			} else {
			    variables.add(linea.substring(comaAnterior + 1));
			}
		    }

		}
	    }
	}
	return variables;
    }
}
