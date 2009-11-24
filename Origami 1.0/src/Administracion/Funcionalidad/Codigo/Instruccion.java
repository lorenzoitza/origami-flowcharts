package Administracion.Funcionalidad.Codigo;

import java.io.Serializable;
import java.util.Vector;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;
import Administracion.Figura;
import Administracion.Funcionalidad.Exporter;
import Grafico.*;
import Grafico.Figuras.CircleFigure;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;
import Grafico.Figuras.Elipse;
import Imagenes.ImageLoader;

/**
 * Esta clase es la base administra el codigo agregado por el usuario. TODO
 * Extract Class de parte grafica
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Instruccion implements Serializable {
	private Shell shell;
	private Vector<String> codigo = new Vector<String>();
	private Vector<String> TablaVariables = new Vector<String>();
	private String identador = "      ";
	public String codigoTotal = new String();

	public Vector<String> getTablaVariables() {
		return TablaVariables;
	}

	public Vector<String> main(Vector<Figura> diagrama) {
		addFiguresInstructionToString(diagrama, 1);
		return codigo;

		// ME TIENE QUE REGRESAR EL DIAGRAMA PARA QUE LO VUELVA A METER
		// vectorCiclos(diagrama);
	}

	public void generarGDB(Vector<Figura> diagrama) {
		// addFiguresInstructionToString(diagrama,1);
		// codigoCoCmasMas.setInstrucciones(codigo,TablaVariables);
		// codigoTotal = codigoCoCmasMas.getCodigoCmasmas();
		// vectorCiclos(diagrama);
		// codigoTotal = codigoCoCmasMas.getCodigoGDB(codigoTotal);
		// ArreglarPasoAPaso();
	}

	/**
	 * Crea la ventana en la cual el codigo se representara.
	 * 
	 * @param Display
	 *            d
	 */
	public void createWindow(Display display) {
		shell = new Shell(MainWindow.shell);
		shell.setSize(500, 387);
		shell.setLocation(300, 200);
		shell.setText("Codigo Fuente");
		getTable(codigoTotal);
		getToolbar();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public void getTable(String codigo) {
		Table table = new Table(shell, SWT.BORDER);
		table.setBounds(0, 0, 496, 320);

		TableColumn lineColumn = new TableColumn(table, SWT.CENTER);
		lineColumn.setWidth(20);
		lineColumn.setText("Linea");

		TableColumn instructionColumn = new TableColumn(table, SWT.LEFT);
		instructionColumn.setWidth(415);
		instructionColumn.setText("Instruccion");

		addRowsContent(table, codigo);

		table.setLinesVisible(true);
		table.setHeaderVisible(true);
	}

	public void addRowsContent(Table table, String codigo) {
		String[] lineas = codigo.split("\n");
		for (int x = 0; x < lineas.length; x++) {
			if (lineas[x] != null) {
				TableItem item1 = new TableItem(table, SWT.NONE);
				for (int y = 1; y <= 2; y++) {
					if (y == 1) {
						item1.setText(1, lineas[x]);
					} else {
						item1.setText(0, Integer.toString(x + 1));
					}
				}
			}
		}
	}

	public void getToolbar() {
		ToolBar toolBar = new ToolBar(shell, SWT.DOWN | SWT.FLAT | SWT.BORDER);
		toolBar.setBounds(0, 320, 500, 40);

		Button exportCButton = new Button(toolBar, SWT.FLAT);
		exportCButton.setImage(ImageLoader.getImage("export.png"));
		exportCButton.setBounds(0, 0, 33, 33);
		exportCButton.setToolTipText("Exportar a C");
		exportCButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
				dialog.setFilterExtensions(new String[] { "*.c" });
				String archivo = dialog.open();
				if (archivo != null) {
					Exporter expor = new Exporter(MainWindow._diagrams);
					expor.codeCExport(archivo);
				}
			}
		});

		Button exportCPlusPlusButton = new Button(toolBar, SWT.FLAT);
		exportCPlusPlusButton.setImage(ImageLoader.getImage("export.png"));
		exportCPlusPlusButton.setBounds(35, 0, 33, 33);
		exportCPlusPlusButton.setText(".cpp");
		exportCPlusPlusButton.setToolTipText("Exportar a C++");
		exportCPlusPlusButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
				dialog.setFilterExtensions(new String[] { "*.cpp" });
				String archivo = dialog.open();
				if (archivo != null) {
					Exporter expor = new Exporter(MainWindow._diagrams);
					expor.codeCppExport(archivo);
				}
			}
		});

		Button exportExeButton = new Button(toolBar, SWT.FLAT);
		exportExeButton.setText(".exe");
		exportExeButton.setBounds(70, 0, 33, 33);
		exportExeButton.setToolTipText("Exportar a .exe");
		exportExeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
				dialog.setFilterExtensions(new String[] { "*.exe" });
				String archivo = dialog.open();
				if (archivo != null) {
					String nombre = dialog.getFileName();
					nombre = nombre.substring(0, nombre.indexOf("."));
					Exporter expor = new Exporter(MainWindow._diagrams);
					expor.executeFileExport(archivo, nombre);
				}
			}
		});
	}

	/**
	 * Este metodo recorre el vector de figuras y agrega cada instruccion de
	 * cada figura a un vector de string.
	 * 
	 * @param diagrama
	 * @param x
	 * @return int
	 */
	public int addFiguresInstructionToString(Vector<Figura> flowchartFigures,
			int indexStart) {
		int index;

		for (index = indexStart; index < flowchartFigures.size(); index++) {

			if (flowchartFigures.elementAt(index) instanceof DecisionFigure) {
				DecisionFigure f = (DecisionFigure) flowchartFigures
						.elementAt(index);
				if (f.instruction.instruccion.elementAt(0) != null) {
					if (f.instruction.instruccion.firstElement()
							.getInstruccionSimple().compareTo("null") != 0
							&& f.instruction.instruccion.firstElement()
									.getInstruccionSimple().compareTo("") != 0) {
						codigo.add(identador
								+ f.instruction.instruccion.elementAt(0)
										.getInstruccionSimple());
						tabula(true);
						index = addFiguresInstructionToString(flowchartFigures,
								index + 2);
						tabula(false);
						codigo.add(identador + "}");
						int y = codigo.size();
						tabula(true);
						index = addFiguresInstructionToString(flowchartFigures,
								index + 2) + 1;
						tabula(false);
						if (codigo.size() > y) {
							String copia2 = new String(identador + "else{");
							codigo.insertElementAt(copia2, y);
							codigo.add(identador + "}");
						}
					} else {
						codigo.add(identador + "if(){");
						tabula(true);
						index = addFiguresInstructionToString(flowchartFigures,
								index + 2);
						tabula(false);
						codigo.add(identador + "}");
						int y = codigo.size();
						tabula(true);
						index = addFiguresInstructionToString(flowchartFigures,
								index + 2) + 1;
						tabula(false);
						if (codigo.size() > y) {
							String copia2 = new String(identador + "else{");
							codigo.insertElementAt(copia2, y);
							codigo.add(identador + "}");
						}
					}
				}
			} else if (flowchartFigures.elementAt(index) instanceof ForFigure) {
				ForFigure f = (ForFigure) flowchartFigures.elementAt(index);
				if (f.instruction.instruccion.elementAt(0) != null) {
					if (f.instruction.instruccion.firstElement()
							.getInstruccionSimple().compareTo("null") != 0
							&& f.instruction.instruccion.firstElement()
									.getInstruccionSimple().compareTo("") != 0) {
						codigo.add(identador
								+ f.instruction.instruccion.elementAt(0)
										.getInstruccionSimple());
						tabula(true);
						index = addFiguresInstructionToString(flowchartFigures,
								index + 1);
						tabula(false);
						codigo.add(identador + "}");
					} else {
						codigo.add(identador + "for(){");
						tabula(true);
						index = addFiguresInstructionToString(flowchartFigures,
								index + 1);
						tabula(false);
						codigo.add(identador + "}");
					}
				}
				index += 5;
			} else if (flowchartFigures.elementAt(index) instanceof WhileFigure) {
				WhileFigure f = (WhileFigure) flowchartFigures.elementAt(index);
				if (f.instruccion.instruccion.elementAt(0) != null) {
					if (f.instruccion.instruccion.firstElement()
							.getInstruccionSimple().compareTo("null") != 0
							&& f.instruccion.instruccion.firstElement()
									.getInstruccionSimple().compareTo("") != 0) {
						codigo.add(identador
								+ f.instruccion.instruccion.elementAt(0)
										.getInstruccionSimple());
						tabula(true);
						index = addFiguresInstructionToString(flowchartFigures,
								index + 1);
						tabula(false);
						codigo.add(identador + "}");
					} else {
						codigo.add(identador + "while(){");
						tabula(true);
						index = addFiguresInstructionToString(flowchartFigures,
								index + 1);
						tabula(false);
						codigo.add(identador + "}");
					}
				}
				index += 5;
			} else if (flowchartFigures.elementAt(index) instanceof OutputFigure) {
				OutputFigure figure = ((OutputFigure) flowchartFigures
						.elementAt(index));
				if (figure.instruction.getInstruccionSimple() != null) {
					String[] ins = new String[3];
					String inst = figure.instruction.getInstruccionSimple();
					ins = inst.split(";");
					for (int d = 0; d < ins.length; d++) {
						while (ins[d].startsWith(" ")) {
							ins[d] = ins[d].replaceFirst(" ", "");
						}
						codigo.add(identador + ins[d] + ";");
					}
				}
			} else if (flowchartFigures.elementAt(index) instanceof InputFigure) {
				InputFigure f = ((InputFigure) flowchartFigures
						.elementAt(index));
				if (f.instruction.getInstruccionSimple() != null) {
					String[] ins;
					String inst = f.instruction.getInstruccionSimple();
					ins = inst.split(";");
					for (int d = 0; d < ins.length; d++) {
						int coma = ins[d].indexOf(",");
						if (coma != -1) {
							Vector<String> masVariables = masVariables(ins[d],
									null);
							if (masVariables.elementAt(0).indexOf("Leer:") >= 0) {
								for (int i = 1; i < masVariables.size(); i++) {
									String aux = masVariables.elementAt(i);
									aux = "Leer: " + aux;
									masVariables.remove(i);
									masVariables.insertElementAt(aux, i);
								}
							}
							for (int i = 0; i < masVariables.size(); i++) {
								codigo.add(identador
										+ masVariables.elementAt(i) + ";");
								int u = masVariables.elementAt(i).indexOf(
										"Leer:");
								if (u == -1) {
									TablaVariables.add(masVariables
											.elementAt(i)
											+ ";");
								}
							}
						} else {
							codigo.add(identador + ins[d] + ";");
							int i = ins[d].indexOf("Leer:");
							if (i == -1) {
								TablaVariables.add(ins[d] + ";");
							}
						}
					}
				}
			} else if (flowchartFigures.elementAt(index) instanceof SentenceFigure) {
				SentenceFigure f = (SentenceFigure) flowchartFigures
						.elementAt(index);
				if (f.instruccion.getInstruccionSimple() != null) {
					codigo
							.add(identador
									+ f.instruccion.getInstruccionSimple());
				}
			} else if (flowchartFigures.elementAt(index) instanceof Elipse) {
				break;
			} else if (flowchartFigures.elementAt(index) instanceof CircleFigure) {
				break;
			}
		}
		return index;
	}

	/*
	 * este es el metodo que tengo que checar para separar los arreglos con las
	 * comas..
	 */
	public Vector<String> masVariables(String linea, String tipo) {
		Vector<String> variables = new Vector<String>();
		String tipoAnterior = tipo, aux;
		int comaAnterior = linea.indexOf(",");
		int comaPosterior = linea.indexOf(",", comaAnterior + 1);
		// en caso de que solo halla una coma
		if (comaPosterior == -1) {
			// analizar la variable aux para ver si no es una declaracion de un
			// array
			if (comaAnterior == -1) { // caso especial donde solo hay dos un
										// array y una variable
				aux = linea;
				while (aux.startsWith(" ")) {
					aux = aux.substring(1);
				}
				if (aux.startsWith("int")) {
					tipoAnterior = "int";
				} else if (aux.startsWith("char")) {
					tipoAnterior = "char";
				} else if (aux.startsWith("float")) {
					tipoAnterior = "float";
				}
				if (tipoAnterior != null) {
					aux = tipoAnterior + " " + aux;
				}
				variables.add(aux);

			} else {
				aux = linea.substring(0, comaAnterior);
				int array = aux.indexOf("{");
				// aca checo si es un arreglo de dos posiciones.
				if (array != -1) {
					aux = linea;
					while (aux.startsWith(" ")) {
						aux = aux.substring(1);
					}
					// checar si tiene tipo si no agregar
					// ********************************************no se si deba
					// ir
					if (aux.startsWith("int")) {
						tipoAnterior = "int";
					} else if (aux.startsWith("char")) {
						tipoAnterior = "char";
					} else if (aux.startsWith("float")) {
						tipoAnterior = "float";
					} else {
						if (tipoAnterior != null) {
							aux = tipoAnterior + " " + aux;
						}
					}
					// agrego //********************************************no
					// se si deba ir
					variables.add(aux);
				} else {
					while (aux.startsWith(" ")) {
						aux = aux.substring(1);
					}
					if (aux.startsWith("int")) {
						tipoAnterior = "int";
					} else if (aux.startsWith("char")) {
						tipoAnterior = "char";
					} else if (aux.startsWith("float")) {
						tipoAnterior = "float";
					} else {
						if (tipoAnterior != null) {
							aux = tipoAnterior + " " + aux;
						}
					}
					variables.add(aux);
					aux = linea.substring(comaAnterior + 1);
					while (aux.startsWith(" ")) {
						aux = aux.substring(1);
					}
					if (aux.startsWith("int") || aux.startsWith("char")
							|| aux.startsWith("float")) {
						variables.add(aux);
					} else {
						if (tipoAnterior != null) {
							aux = tipoAnterior + " " + aux;
						}
						variables.add(aux);
					}
				}
			}
		} else {
			// aca debo de checar si hay un array
			aux = linea.substring(0, comaAnterior);

			// ********************************************************************
			int array = aux.indexOf("{");
			// aca checo si es un arreglo de dos posiciones.
			int llaves = 0;
			String declaracion;
			if (array != -1) { // es un array
				llaves++;
				declaracion = aux;
				// ***** leo todos las llaves abiertas
				array = aux.indexOf("{", array + 1);
				while (array != -1) {
					llaves++;
					array = aux.indexOf("{", array + 1);
				}

				while (llaves != 0) {
					if (comaPosterior == -1) {
						aux = linea.substring(comaAnterior, linea.length());
						declaracion = declaracion + aux;
						break;
					}
					aux = linea.substring(comaAnterior, comaPosterior);
					// **************
					array = -1;
					array = aux.indexOf("{", array + 1);
					while (array != -1) {
						llaves++;
						array = aux.indexOf("{", array + 1);
					}

					array = -1;
					array = aux.indexOf("}", array + 1);
					while (array != -1) {
						llaves--;
						array = aux.indexOf("}", array + 1);
					}
					/*
					 * //**************** array=aux.indexOf("{"); if(array!=-1){
					 * llaves++; } array=aux.indexOf("}"); if(array!=-1){
					 * llaves--; }
					 */
					// actualizar las comas
					comaAnterior = comaPosterior;
					comaPosterior = linea.indexOf(",", comaAnterior + 1);
					declaracion = declaracion + aux;
				}

				while (declaracion.startsWith(" ")) {
					declaracion = declaracion.substring(1);
				}
				String auu = declaracion;
				if (declaracion.startsWith("int")) {
					tipoAnterior = "int";
				} else if (declaracion.startsWith("char")) {
					tipoAnterior = "char";
				} else if (declaracion.startsWith("float")) {
					tipoAnterior = "float";
				} else {
					if (tipoAnterior != null) {
						declaracion = tipoAnterior + " " + declaracion;
					}
				}
				variables.add(declaracion);

				if (comaPosterior != -1
						|| linea.indexOf(",", auu.length()) != -1) {
					// System.out.println(linea.substring(comaAnterior+1));
					Vector<String> variables2 = masVariables(linea
							.substring(comaAnterior + 1), tipoAnterior);
					for (int i = 0; i < variables2.size(); i++) {
						variables.add(variables2.elementAt(i));
					}
				} else {
				}

			} else {
				// no es un array...
				// ***********************************************************************
				while (aux.startsWith(" ")) {
					aux = aux.substring(1);
				}
				if (aux.startsWith("int")) {
					tipoAnterior = "int";
				} else if (aux.startsWith("char")) {
					tipoAnterior = "char";
				} else if (aux.startsWith("float")) {
					tipoAnterior = "float";
				} else {
					if (tipoAnterior != null) {
						aux = tipoAnterior + " " + aux;
					}
				}
				variables.add(aux);
				Vector<String> variables2 = masVariables(linea
						.substring(comaAnterior + 1), tipoAnterior);
				for (int i = 0; i < variables2.size(); i++) {
					variables.add(variables2.elementAt(i));
				}
			}

		}
		return variables;
	}

	/**
	 * Este metodo se encarga de encontrar el final de un ciclo If.
	 * 
	 * @param cont
	 * @return int
	 */
	public int[] findFigureEnd(int cont, String controlFigure,
			String firstFigure, String secondFigure) {
		int inicio = 0, fin = 0, x;
		int[] iniFin = new int[2];
		char ciclo = '{';
		char finCiclo = '}';
		boolean bandera = false;
		for (x = 0; x < codigo.size(); x++) {
			if ((inicio == fin) && (inicio != 0)) {
				break;
			}
			if (codigo.elementAt(x).indexOf(ciclo) != -1
					&& codigo.elementAt(x).indexOf(controlFigure) != -1) {
				inicio++;
				if (cont == inicio) {
					cont = 0;
					inicio = 1;
					bandera = true;
					iniFin[0] = x;
				}
			} else if (codigo.elementAt(x).indexOf(finCiclo) != -1 && bandera) {
				fin++;
			} else if ((codigo.elementAt(x).indexOf("else") != -1
					|| codigo.elementAt(x).indexOf(firstFigure) != -1 || codigo
					.elementAt(x).indexOf(secondFigure) != -1)
					&& bandera) {
				fin--;
			}
		}
		iniFin[1] = x - 1;
		return iniFin;
	}

	/**
	 * Este metodo se encarga de agregar a cada instruccion de cada ciclo el
	 * codigo que es parte de su bloque.
	 * 
	 * @param diagrama
	 */
	public void vectorCiclos(Vector<Figura> diagrama) {
		int contIf = 0, contFor = 0, contWhile = 0;
		int[] u = new int[2];
		for (int index = 1; index < diagrama.size(); index++) {
			if (diagrama.elementAt(index) instanceof DecisionFigure) {
				contIf++;
				u = findFigureEnd(contIf, "if", "while", "for");
				metodo(u, index, diagrama);
			} else if (diagrama.elementAt(index) instanceof ForFigure) {
				contFor++;
				u = findFigureEnd(contFor, "for", "if", "while");
				metodo(u, index, diagrama);
			} else if (diagrama.elementAt(index) instanceof WhileFigure) {
				contWhile++;
				u = findFigureEnd(contWhile, "while", "if", "for");
				metodo(u, index, diagrama);
			} else if (diagrama.elementAt(index) instanceof OutputFigure) {
				break;
			}
		}
	}

	public void metodo(int[] u, int x, Vector<Figura> diagrama) {
		if (diagrama.elementAt(x) instanceof DecisionFigure) {
			DecisionFigure f = (DecisionFigure) diagrama.elementAt(x);
			f.instruction.instruccion.removeAllElements();
		} else if (diagrama.elementAt(x) instanceof ForFigure) {
			ForFigure f = (ForFigure) diagrama.elementAt(x);
			f.instruction.instruccion.removeAllElements();
		} else {
			WhileFigure f = (WhileFigure) diagrama.elementAt(x);
			f.instruccion.instruccion.removeAllElements();
		}
		for (int m = 0; m < codigo.size(); m++) {
			if (m >= u[0] && m <= u[1]) {
				InstruccionSimple inst = new InstruccionSimple();
				int d = 0;
				String copia = codigo.elementAt(m);
				while (codigo.elementAt(m).length() > d) {
					if (copia.startsWith(" ")) {
						copia = copia.replaceFirst(" ", "");
					}
					d++;
				}
				codigo.setElementAt(copia, m);
				inst.setInstruccionSimple(codigo.elementAt(m));
				if (diagrama.elementAt(x) instanceof DecisionFigure) {
					DecisionFigure f = (DecisionFigure) diagrama.elementAt(x);
					f.instruction.instruccion.add(inst);
				} else if (diagrama.elementAt(x) instanceof ForFigure) {
					ForFigure f = (ForFigure) diagrama.elementAt(x);
					f.instruction.instruccion.add(inst);
				} else {
					WhileFigure f = (WhileFigure) diagrama.elementAt(x);
					f.instruccion.instruccion.add(inst);
				}
			}
		}
		if (diagrama.elementAt(x) instanceof DecisionFigure) {
			DecisionFigure f = (DecisionFigure) diagrama.elementAt(x);
			diagrama.setElementAt(f, x);
		} else if (diagrama.elementAt(x) instanceof ForFigure) {
			ForFigure f = (ForFigure) diagrama.elementAt(x);
			diagrama.setElementAt(f, x);
		} else {
			WhileFigure f = (WhileFigure) diagrama.elementAt(x);
			diagrama.setElementAt(f, x);
		}

	}

	/**
	 * Este metodo se encarga de definir el tabulado que se usara en la
	 * siguiente linea.
	 * 
	 * @param x
	 */
	private void tabula(boolean isMayor) {
		if (isMayor) {
			identador = identador + "    ";
		} else {
			identador = identador.replaceFirst("    ", "");
		}
	}

	public void ArreglarPasoAPaso() {
		String aux[] = codigoTotal.split("\n"), aux2;
		String variable = "A5i9I", variable2 = "A5i9W";
		int contador, contador2 = 0;
		for (int i = 0; i < aux.length; i++) {
			if (i == 3) {
				aux[i] = "{int " + variable + "=0," + variable2 + "=0" + ";";
			}
			aux2 = aux[i];
			while (aux2.startsWith(" ")) {
				aux2 = aux2.substring(1);
			}
			if (aux2.startsWith("if(") || aux2.startsWith("while(")
					|| aux2.startsWith("for(")) {
				// busco la ultima ")"
				contador = aux[i].indexOf(")");
				while (contador != -1) {
					contador2 = contador;
					contador = aux[i].indexOf(")", contador2 + 1);
				}
				String primeraParte = aux[i].substring(0, contador2);
				String segundaParte = "&&" + variable + "==0)";
				String terceraParte = aux[i].substring(contador2 + 1);
				aux[i] = primeraParte + segundaParte + terceraParte;
				if (aux2.startsWith("if(")) {
					llave(i, aux, "FinDelIf");
				} else {
					llave(i, aux, "FinDelWhile");
				}
			} else {

			}
		}
		codigoTotal = "";
		for (int i = 0; i < aux.length; i++) {
			codigoTotal = codigoTotal + aux[i] + "\n";
		}
	}

	public void llave(int posicion, String[] aux, String imprimir) {
		String variable = "A5i9I", variable2 = "A5i9W", seg;
		int contadorNoEs = 0;
		for (int i = posicion + 1; i < aux.length; i++) {
			if (aux[i].indexOf("for(") != -1) {
				contadorNoEs++;
			}
			if (aux[i].indexOf("if(") != -1 || aux[i].indexOf("while(") != -1) {
				contadorNoEs++;
			}
			if (aux[i].indexOf("else{") != -1) {
				contadorNoEs++;
			} else if (aux[i].indexOf("}") != -1 && contadorNoEs == 0) {
				int pos = aux[i].indexOf("}");
				String prim = aux[i].substring(0, pos);
				// String seg = "cout<<"+"\""+imprimir+"\""+"<< endl;}";
				if (imprimir == "FinDelIf") {
					seg = variable + " = " + "0;}";
				} else {
					seg = variable2 + " = " + "0;}";
				}
				String ter = aux[i].substring(pos + 1);
				aux[i] = prim + seg + ter;
				break;
			} else if (aux[i].indexOf("}") != -1) {
				contadorNoEs--;
			}
		}

	}
}