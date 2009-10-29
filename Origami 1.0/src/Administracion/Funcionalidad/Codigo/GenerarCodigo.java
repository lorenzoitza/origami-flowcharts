package Administracion.Funcionalidad.Codigo;

import java.io.Serializable;
import java.util.Vector;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class GenerarCodigo implements Serializable{
	private Vector <String> codeOfFigure;
	private Vector<String> TableOfVariable;
	private String code;
	/**
	 * en lugar de setInstrucccion() llamar a
	 * crear la instancia
	 * setCodeOfFigure()
	 * generateCodeC() || generateCodeCpp()
	 * getCode()
	 */
	
	public GenerarCodigo(){
	    this.codeOfFigure = new Vector<String>();
	    this.TableOfVariable = new Vector<String>();
	    this.code = new String();
	}
	/*
	public void setInstrucciones(Vector <String> codigo,Vector <String> tablaVariables){
		this.codeOfFigure = new Vector<String>();
		this.TableOfVariable = new Vector<String>();
		for(int i=0; i<codigo.size(); i++){
			this.codeOfFigure.add(codigo.elementAt(i));
		}
		for(int i=0; i<tablaVariables.size(); i++){
			this.TableOfVariable.add(tablaVariables.elementAt(i));
		}
	}*/
	/*public String getCodigoC(){
		generarCodigoC();
		return code;
	}
	public String getCodigoCmasmas(){
		generarCodigoCmasmas();
		return code;
	}*/
	
	
	public void generateCodeC(){
	    	divideDataInputForCodeC();
	    	generateInstructionOfDataOutputForCodeC();
		code = ("#include <iostream>\n"+"using namespace std;\n"+"int main(int argc, char argv[])\n{\n");
		for(int indexCodeFigure=0;indexCodeFigure<codeOfFigure.size();indexCodeFigure++){
			String str = codeOfFigure.elementAt(indexCodeFigure);
			int pos = str.indexOf("null");
			if(pos<0){
				code = code + codeOfFigure.elementAt(indexCodeFigure) + "\n";
			}
		}
		code = code + "      return 0;\n" + "}\n";
	}
	public void generateCodeCpp(){
	    	divideDataInputForCodeCpp();
	    	generateInstructionOfDataOutputForCodeCpp();
		code = ("#include <stdio.h>\n" + "#include <stdlib.h>" +"\nint main(int argc, char argv[])\n{\n");
		for(int indexCodeFigure=0;indexCodeFigure<codeOfFigure.size();indexCodeFigure++){
			String str = codeOfFigure.elementAt(indexCodeFigure);
			int pos = str.indexOf("null");
			if(pos<0){
				code = code + codeOfFigure.elementAt(indexCodeFigure) + "\n";
			}
		}
		code = code + "      system(\"PAUSE\");\n      return 0;\n" + "}\n";
	}
	/*
	 private void generarCodigoCmasmas(){
		leerDatos(false);
		imprimirDatos(false);
		codigoTotal = ("#include <iostream>\n"+"using namespace std;\n"+"int main(int argc, char argv[])\n{\n");
		for(int x=0;x<codigo.size();x++){
			String str = codigo.elementAt(x);
			int pos = str.indexOf("null");
			if(pos<0){
				codigoTotal = codigoTotal + codigo.elementAt(x) + "\n";
			}
		}
		codigoTotal = codigoTotal + "      return 0;\n" + "}\n";
	}
	private void generarCodigoC(){
		leerDatos(true);
		imprimirDatos(true);
		codigoTotal = ("#include <stdio.h>\n" + "#include <stdlib.h>" +"\nint main(int argc, char argv[])\n{\n");
		for(int x=0;x<codigo.size();x++){
			String str = codigo.elementAt(x);
			int pos = str.indexOf("null");
			if(pos<0){
				codigoTotal = codigoTotal + codigo.elementAt(x) + "\n";
			}
		}
		codigoTotal = codigoTotal + "      system(\"PAUSE\");\n      return 0;\n" + "}\n";
	}
	 */
	/**
	 * Separa las entradas declaraciones separadas con coma o que dicen Leer:
	 * este metodo se separa en 2 diferentes para C y Cpp
	 */
	/*private void leerDatos(boolean esC){
		int j;
		for(int x=0;x<codeOfFigure.size();x++){
			if(codeOfFigure.elementAt(x).lastIndexOf("Leer:")>=0){
				Vector<String> lineas =leerDatosMultiples(codeOfFigure.elementAt(x), esC);
				String tab = codeOfFigure.elementAt(x);
				j = codeOfFigure.elementAt(x).indexOf("L");
				tab = tab.substring(0,j);
				removerLineasRepetidas(lineas);
				codeOfFigure.removeElementAt(x);
				for(int k=0;k<lineas.size();k++){
					String lin = tab + lineas.elementAt(k);
					codeOfFigure.insertElementAt(lin,x);
					x++;
				}
				x--;
			}
		}
	}*/
	
	private void divideDataInputForCodeC(){
		for(int indexCodeFigure=0;indexCodeFigure<codeOfFigure.size();indexCodeFigure++){
			if(codeOfFigure.elementAt(indexCodeFigure).lastIndexOf("Leer:")>=0){
				Vector<String> dataSeparate =generateInstructionOfDataInputForCodeC(codeOfFigure.elementAt(indexCodeFigure));
				int indexCharacterL = codeOfFigure.elementAt(indexCodeFigure).indexOf("L");
				String space = codeOfFigure.elementAt(indexCodeFigure).substring(0,indexCharacterL);
				removeLinesRepeated(dataSeparate);
				codeOfFigure.removeElementAt(indexCodeFigure);
				for(int k=0;k<dataSeparate.size();k++){
					String lin = space + dataSeparate.elementAt(k);
					codeOfFigure.insertElementAt(lin,indexCodeFigure);
					indexCodeFigure++;
				}
				indexCodeFigure--;
			}
		}
	}
	
	private void divideDataInputForCodeCpp(){
		int j;
		for(int x=0;x<codeOfFigure.size();x++){
			if(codeOfFigure.elementAt(x).lastIndexOf("Leer:")>=0){
				Vector<String> lineas =generateInstructionOfDataInputForCodeCpp(codeOfFigure.elementAt(x));
				String tab = codeOfFigure.elementAt(x);
				j = codeOfFigure.elementAt(x).indexOf("L");
				tab = tab.substring(0,j);
				removeLinesRepeated(lineas);
				codeOfFigure.removeElementAt(x);
				for(int k=0;k<lineas.size();k++){
					String lin = tab + lineas.elementAt(k);
					codeOfFigure.insertElementAt(lin,x);
					x++;
				}
				x--;
			}
		}
	}
	public void removeLinesRepeated(Vector<String> lineas){
		String aux,aux2,aux3;
		int pos;
		for(int i=0; i<lineas.size(); i++){
			aux = lineas.elementAt(i);
			while(aux.startsWith(" ")){
				aux = aux.substring(1);
			}
			pos = aux.indexOf(" ");
			while(pos!=-1){
				aux3 = aux.substring(pos+1);
				aux = aux.substring(0,pos)+ aux3;
				pos = aux.indexOf(" ");
			}
			for(int j=0; j<codeOfFigure.size(); j++){
				aux2 = codeOfFigure.elementAt(j);
				while(aux2.startsWith(" ")){
					aux2 = aux2.substring(1);
				}
				pos = aux2.indexOf(" ");
				while(pos!=-1){
					aux3 = aux2.substring(pos+1);
					aux2 = aux2.substring(0,pos)+ aux3;
					pos = aux2.indexOf(" ");
				}
				if(aux.compareTo(aux2)==0){
					if(aux.indexOf("cin>>")==-1 && aux.indexOf("scanf(")==-1){
						lineas.remove( i);
						i--;
						break;
					}
				}
			}
		}	
	}
	/*public void removerLineasRepetidas(Vector<String> lineas){
		String aux,aux2,aux3;
		int pos;
		for(int i=0; i<lineas.size(); i++){
			aux = lineas.elementAt(i);
			while(aux.startsWith(" ")){
				aux = aux.substring(1);
			}
			pos = aux.indexOf(" ");
			while(pos!=-1){
				aux3 = aux.substring(pos+1);
				aux = aux.substring(0,pos)+ aux3;
				pos = aux.indexOf(" ");
			}
			for(int j=0; j<codeOfFigure.size(); j++){
				aux2 = codeOfFigure.elementAt(j);
				while(aux2.startsWith(" ")){
					aux2 = aux2.substring(1);
				}
				pos = aux2.indexOf(" ");
				while(pos!=-1){
					aux3 = aux2.substring(pos+1);
					aux2 = aux2.substring(0,pos)+ aux3;
					pos = aux2.indexOf(" ");
				}
				if(aux.compareTo(aux2)==0){
					if(aux.indexOf("cin>>")==-1 && aux.indexOf("scanf(")==-1){
						lineas.remove( i);
						i--;
						break;
					}
				}
			}
		}	
	}*/
	/**
	 * Este metodo es para manejar multiples lecturas en una linea.
	 * @param linea
	 * @param j
	 * @return Vector<String>
	 */
	/*public Vector<String> leerDatosMultiples(String linea, boolean esC){
		Vector <String> datos = null;
		Vector <String> variables = new Vector<String>();
		String scanCaracter;
		String scanEnteros;
		String scanFlotante;
		String scanDefaul;
		String ultimo;
		if(esC){
			scanCaracter = "scanf(" + "\"" + "%" +"c"+ "\"" + "," +"&";
			scanEnteros = "scanf(" +"\""+"%"+"d"+"\""+","+"&";
			scanFlotante = "scanf(" +"\""+"%"+"f"+"\""+","+"&";
			scanDefaul = "scanf(" + "\"" + "%" +"d"+ "\"" + "," +"&";
			ultimo = ");";
		}
		else{
			scanCaracter = "cin>>";
			scanEnteros =  "cin>>";
			scanFlotante = "cin>>";
			scanDefaul =   "cin>>";
			ultimo = ";";
		}
		int i = linea.indexOf(",");
		if(i!=-1){
			datos = new Vector<String>();
			linea = linea.substring(linea.lastIndexOf("Leer:")+5,linea.length());
			variables = separarVariables(linea);
			for(int k=0; k<variables.size(); k++){
				datos.add(variables.elementAt(k));
			}
			for(int k=0; k<variables.size(); k++){
				if(variables.elementAt(k).lastIndexOf("int")>=0){
					String dato = variables.elementAt(k).substring(variables.elementAt(k).lastIndexOf("int")+3,variables.elementAt(k).lastIndexOf(";"));
					String lin = scanEnteros + dato + ultimo;
					datos.add(lin);
				}
				else if(variables.elementAt(k).lastIndexOf("char")>=0){
					String dato = variables.elementAt(k).substring(variables.elementAt(k).lastIndexOf("char")+4,variables.elementAt(k).lastIndexOf(";"));
					String lin = scanCaracter + dato + ultimo;
					datos.add(lin);
				}
				else if(variables.elementAt(k).lastIndexOf("float")>=0){
					String dato = variables.elementAt(k).substring(variables.elementAt(k).lastIndexOf("float")+5,variables.elementAt(k).lastIndexOf(";"));
					String lin = scanFlotante + dato + ultimo;
					datos.add(lin);
				}
				else{
					String dato = variables.elementAt(k).substring(0,variables.elementAt(k).lastIndexOf(";"));
					String lin = scanDefaul + dato + ultimo;
					datos.add(lin);
				}
			}
		}
		else{
			datos = new Vector<String>();
			if(linea.lastIndexOf("int")!=-1){
				String dato = linea.substring(linea.lastIndexOf("int"),linea.length()-1);
				TableOfVariable.add(dato +";");
				String lin = scanEnteros + dato.substring(3) + ultimo;
				datos.add(dato +";");
				datos.add(lin);
			}
			else if(linea.lastIndexOf("char")!=-1){
				String dato = linea.substring(linea.lastIndexOf("char"),linea.length()-1);
				TableOfVariable.add(dato+";");
				String lin2 = scanCaracter + dato.substring(4) + ultimo;
				datos.add(dato+";");
				datos.add(lin2);
			}
			else if(linea.lastIndexOf("float")!=-1){
				String dato = linea.substring(linea.lastIndexOf("float"),linea.length()-1);
				TableOfVariable.add(dato+";");
				String lin2 = scanFlotante + dato.substring(5) + ultimo;
				datos.add(dato+";");
				datos.add(lin2);
			}
			else{
				String tipo = tipoDeDato(linea.substring(linea.lastIndexOf("Leer:")+5,linea.length()-1));
				if(tipo!=null){
					if(tipo.lastIndexOf("int")>=0){
						datos.add(scanEnteros + linea.substring(linea.lastIndexOf("Leer:")+5,linea.length()-1)+ ultimo);
					}
					else if(tipo.lastIndexOf("char")>=0){
						datos.add(scanCaracter + linea.substring(linea.lastIndexOf("Leer:")+5,linea.length()-1)+ ultimo);
					}
					else{
						datos.add(scanFlotante + linea.substring(linea.lastIndexOf("Leer:")+5,linea.length()-1)+ ultimo);
					}
				}
				else{
					String lin2 = scanDefaul + linea.substring(linea.lastIndexOf("Leer:")+5,linea.length()-1)+ ultimo;
					datos.add(lin2);
				}
			}
		}
		return datos;
	}*/
	
	public Vector<String> generateInstructionOfDataInputForCodeC(String linea){
		Vector <String> datos = null;
		Vector <String> variables = new Vector<String>();
		String scanCaracter = "scanf(" + "\"" + "%" +"c"+ "\"" + "," +"&";
		String scanEnteros = "scanf(" +"\""+"%"+"d"+"\""+","+"&";
		String scanFlotante = "scanf(" +"\""+"%"+"f"+"\""+","+"&";
		String scanDefaul = "scanf(" + "\"" + "%" +"d"+ "\"" + "," +"&";
		String ultimo = ");";
		
		int i = linea.indexOf(",");
		if(i!=-1){
			datos = new Vector<String>();
			linea = linea.substring(linea.lastIndexOf("Leer:")+5,linea.length());
			variables = separateData(linea);
			for(int k=0; k<variables.size(); k++){
				datos.add(variables.elementAt(k));
			}
			for(int k=0; k<variables.size(); k++){
				if(variables.elementAt(k).lastIndexOf("int")>=0){
					String dato = variables.elementAt(k).substring(variables.elementAt(k).lastIndexOf("int")+3,variables.elementAt(k).lastIndexOf(";"));
					String lin = scanEnteros + dato + ultimo;
					datos.add(lin);
				}
				else if(variables.elementAt(k).lastIndexOf("char")>=0){
					String dato = variables.elementAt(k).substring(variables.elementAt(k).lastIndexOf("char")+4,variables.elementAt(k).lastIndexOf(";"));
					String lin = scanCaracter + dato + ultimo;
					datos.add(lin);
				}
				else if(variables.elementAt(k).lastIndexOf("float")>=0){
					String dato = variables.elementAt(k).substring(variables.elementAt(k).lastIndexOf("float")+5,variables.elementAt(k).lastIndexOf(";"));
					String lin = scanFlotante + dato + ultimo;
					datos.add(lin);
				}
				else{
					String dato = variables.elementAt(k).substring(0,variables.elementAt(k).lastIndexOf(";"));
					String lin = scanDefaul + dato + ultimo;
					datos.add(lin);
				}
			}
		}
		else{
			datos = new Vector<String>();
			if(linea.lastIndexOf("int")!=-1){
				String dato = linea.substring(linea.lastIndexOf("int"),linea.length()-1);
				TableOfVariable.add(dato +";");
				String lin = scanEnteros + dato.substring(3) + ultimo;
				datos.add(dato +";");
				datos.add(lin);
			}
			else if(linea.lastIndexOf("char")!=-1){
				String dato = linea.substring(linea.lastIndexOf("char"),linea.length()-1);
				TableOfVariable.add(dato+";");
				String lin2 = scanCaracter + dato.substring(4) + ultimo;
				datos.add(dato+";");
				datos.add(lin2);
			}
			else if(linea.lastIndexOf("float")!=-1){
				String dato = linea.substring(linea.lastIndexOf("float"),linea.length()-1);
				TableOfVariable.add(dato+";");
				String lin2 = scanFlotante + dato.substring(5) + ultimo;
				datos.add(dato+";");
				datos.add(lin2);
			}
			else{
				String tipo = getTypeOfData(linea.substring(linea.lastIndexOf("Leer:")+5,linea.length()-1));
				if(tipo!=null){
					if(tipo.lastIndexOf("int")>=0){
						datos.add(scanEnteros + linea.substring(linea.lastIndexOf("Leer:")+5,linea.length()-1)+ ultimo);
					}
					else if(tipo.lastIndexOf("char")>=0){
						datos.add(scanCaracter + linea.substring(linea.lastIndexOf("Leer:")+5,linea.length()-1)+ ultimo);
					}
					else{
						datos.add(scanFlotante + linea.substring(linea.lastIndexOf("Leer:")+5,linea.length()-1)+ ultimo);
					}
				}
				else{
					String lin2 = scanDefaul + linea.substring(linea.lastIndexOf("Leer:")+5,linea.length()-1)+ ultimo;
					datos.add(lin2);
				}
			}
		}
		return datos;
	}
	
	public Vector<String> generateInstructionOfDataInputForCodeCpp(String linea){
		Vector <String> datos = null;
		Vector <String> variables = new Vector<String>();
		String scanCaracter = "cin>>";
		String scanEnteros =  "cin>>";
		String scanFlotante =  "cin>>";
		String scanDefaul =  "cin>>";
		String ultimo = ";";
		
		int i = linea.indexOf(",");
		if(i!=-1){
			datos = new Vector<String>();
			linea = linea.substring(linea.lastIndexOf("Leer:")+5,linea.length());
			variables = separateData(linea);
			for(int k=0; k<variables.size(); k++){
				datos.add(variables.elementAt(k));
			}
			for(int k=0; k<variables.size(); k++){
				if(variables.elementAt(k).lastIndexOf("int")>=0){
					String dato = variables.elementAt(k).substring(variables.elementAt(k).lastIndexOf("int")+3,variables.elementAt(k).lastIndexOf(";"));
					String lin = scanEnteros + dato + ultimo;
					datos.add(lin);
				}
				else if(variables.elementAt(k).lastIndexOf("char")>=0){
					String dato = variables.elementAt(k).substring(variables.elementAt(k).lastIndexOf("char")+4,variables.elementAt(k).lastIndexOf(";"));
					String lin = scanCaracter + dato + ultimo;
					datos.add(lin);
				}
				else if(variables.elementAt(k).lastIndexOf("float")>=0){
					String dato = variables.elementAt(k).substring(variables.elementAt(k).lastIndexOf("float")+5,variables.elementAt(k).lastIndexOf(";"));
					String lin = scanFlotante + dato + ultimo;
					datos.add(lin);
				}
				else{
					String dato = variables.elementAt(k).substring(0,variables.elementAt(k).lastIndexOf(";"));
					String lin = scanDefaul + dato + ultimo;
					datos.add(lin);
				}
			}
		}
		else{
			datos = new Vector<String>();
			if(linea.lastIndexOf("int")!=-1){
				String dato = linea.substring(linea.lastIndexOf("int"),linea.length()-1);
				TableOfVariable.add(dato +";");
				String lin = scanEnteros + dato.substring(3) + ultimo;
				datos.add(dato +";");
				datos.add(lin);
			}
			else if(linea.lastIndexOf("char")!=-1){
				String dato = linea.substring(linea.lastIndexOf("char"),linea.length()-1);
				TableOfVariable.add(dato+";");
				String lin2 = scanCaracter + dato.substring(4) + ultimo;
				datos.add(dato+";");
				datos.add(lin2);
			}
			else if(linea.lastIndexOf("float")!=-1){
				String dato = linea.substring(linea.lastIndexOf("float"),linea.length()-1);
				TableOfVariable.add(dato+";");
				String lin2 = scanFlotante + dato.substring(5) + ultimo;
				datos.add(dato+";");
				datos.add(lin2);
			}
			else{
				String tipo = getTypeOfData(linea.substring(linea.lastIndexOf("Leer:")+5,linea.length()-1));
				if(tipo!=null){
					if(tipo.lastIndexOf("int")>=0){
						datos.add(scanEnteros + linea.substring(linea.lastIndexOf("Leer:")+5,linea.length()-1)+ ultimo);
					}
					else if(tipo.lastIndexOf("char")>=0){
						datos.add(scanCaracter + linea.substring(linea.lastIndexOf("Leer:")+5,linea.length()-1)+ ultimo);
					}
					else{
						datos.add(scanFlotante + linea.substring(linea.lastIndexOf("Leer:")+5,linea.length()-1)+ ultimo);
					}
				}
				else{
					String lin2 = scanDefaul + linea.substring(linea.lastIndexOf("Leer:")+5,linea.length()-1)+ ultimo;
					datos.add(lin2);
				}
			}
		}
		return datos;
	}
	/**
	 * Este metodo separa las multiples variables para de una linea
	 * en la cual se maneja el comando leer:
	 * @param linea
	 * @return Vector<String>
	 * 
	 */
	/**
	 * Este metodo mejora y sustituye a separateData y separarVariables2
	 */
	public Vector<String> separateDataNew(String linea){
		String tipoAnterior = null;
		Vector<String> variables = new Vector<String>();
		
		String[] data = linea.split(",");
		for(int i=0; i<data.length; i++){
		    if(data[i].contains("int ")){
			tipoAnterior = "int ";
			TableOfVariable.add(data[i]+";");
			variables.add(data[i]+";");
		    }
		    else if(data[i].contains("char ")){
			tipoAnterior = "char ";
			TableOfVariable.add(data[i]+";");
			variables.add(data[i]+";");
		    }
		    else if(data[i].contains("float ")){
			tipoAnterior = "float  ";
			TableOfVariable.add(data[i]+";");
			variables.add(data[i]+";");
		    }
		    else{
			if(tipoAnterior!=null){
			    TableOfVariable.add(tipoAnterior + data[i]+";");
				variables.add(tipoAnterior + data[i]+";");
			}
			else{
			    TableOfVariable.add(data[i]+";");
			    variables.add(data[i]+";");
			}
		    }
		}
		
		return variables;
	}
	
	public Vector<String> separateData(String linea){
		String tipoAnterior = null;
		Vector<String> variables = new Vector<String>();
		int comaAnterior = linea.indexOf(",");
		int comaPosterior = linea.indexOf(",", comaAnterior+1);
		if(comaPosterior == -1){
			int tipo = linea.indexOf("int");
			if(tipo!=-1 && tipo<comaAnterior){
				tipoAnterior = "int";
				TableOfVariable.add(linea.substring(linea.indexOf("int"),comaAnterior)+";");
				variables.add(linea.substring(linea.indexOf("int"),comaAnterior)+";");
				variables = separarVariables2(tipo,linea,comaAnterior,variables,tipoAnterior);
			}
			else{
				tipo = linea.indexOf("char");
				if(tipo!=-1 && tipo<comaAnterior){
					tipoAnterior = "char";
					TableOfVariable.add(linea.substring(linea.indexOf("char"),comaAnterior)+";");
					variables.add(linea.substring(linea.indexOf("char"),comaAnterior)+";");
					variables = separarVariables2(tipo,linea,comaAnterior,variables,tipoAnterior);
				}
				else{
					tipo = linea.indexOf("float");
					if(tipo!=-1 && tipo<comaAnterior){
						tipoAnterior = "float";
						TableOfVariable.add(linea.substring(linea.indexOf("float"),comaAnterior)+";");
						variables.add(linea.substring(linea.indexOf("float"),comaAnterior)+";");
						variables = separarVariables2(tipo,linea,comaAnterior,variables,tipoAnterior);
					}
					else{
						String tip = getTypeOfData(linea.substring(0,comaAnterior));
						if(tip!=null){
							variables.add(tip + linea.substring(0,comaAnterior)+";");
						}
						else{
							variables.add(linea.substring(0,comaAnterior)+";");
						}
						variables = separarVariables2(tipo,linea,comaAnterior,variables,tipoAnterior);
					}
				}
			}
			
		}
		else{
			Vector <String>variables2 = new Vector<String>();
				int tipo = linea.indexOf("int");
				if(tipo!=-1 && tipo<comaAnterior){
					tipoAnterior = "int";
					TableOfVariable.add(linea.substring(linea.indexOf("int"),comaAnterior)+";");
					variables.add(linea.substring(linea.indexOf("int"),comaAnterior)+";");
					linea = linea.substring(comaAnterior+1);
					variables2 = separateData(linea);
				}
				else{
					tipo = linea.indexOf("char");
					if(tipo!=-1 && tipo<comaAnterior){
						tipoAnterior = "char";
						TableOfVariable.add(linea.substring(linea.indexOf("char"),comaAnterior)+";");
						variables.add(linea.substring(linea.indexOf("char"),comaAnterior)+";");
						linea = linea.substring(comaAnterior+1);
						variables2 = separateData(linea);
					}
					else{
						tipo = linea.indexOf("float");
						if(tipo!=-1 && tipo<comaAnterior){
							tipoAnterior = "float";
							TableOfVariable.add(linea.substring(linea.indexOf("float"),comaAnterior)+";");
							variables.add(linea.substring(linea.indexOf("float"),comaAnterior)+";");
							linea = linea.substring(comaAnterior+1);
							variables2 = separateData(linea);
						}
						else{
							String tip = getTypeOfData(linea.substring(0,comaAnterior));
							if(tip!=null){
								variables.add(tip + linea.substring(0,comaAnterior)+";");
							}
							else{
								variables.add(linea.substring(0,comaAnterior)+";");
							}
							linea = linea.substring(comaAnterior+1);
							variables2 = separateData(linea);
						}
					}
				}
				for(int i=0; i<variables2.size(); i++){
					variables.add(variables2.elementAt(i));
				}
		}
		return variables;
	}
	
	public Vector<String> separarVariables2(int tipo,String linea,int comaAnterior, Vector<String> variables,String tipoAnterior){
		 tipo = linea.indexOf("int",comaAnterior);
		 if(tipo!=-1){
				TableOfVariable.add(linea.substring(tipo,linea.length()));
				variables.add(linea.substring(tipo,linea.length()));
			}
			else{
				tipo = linea.indexOf("char",comaAnterior);
				if(tipo!=-1){
					TableOfVariable.add(linea.substring(tipo,linea.length()));
					variables.add(linea.substring(tipo,linea.length()));
				}
				else{
					tipo = linea.indexOf("float",comaAnterior);
					if(tipo!=-1){
						TableOfVariable.add(linea.substring(tipo,linea.length()));
						variables.add(linea.substring(tipo,linea.length()));
					}
					else{
						String tip = getTypeOfData(linea.substring(comaAnterior+1,linea.length()-1));
						if(tip!=null){
							variables.add(tip + linea.substring(comaAnterior+1));
						}
						else{
							if(tipoAnterior!=null){
								variables.add(tipoAnterior + " " +linea.substring(comaAnterior+1));
							}
							else{
								variables.add(linea.substring(comaAnterior+1));
							}
						}
						
					}
				}
			}
		 return variables;
	}
	
	/*public void imprimirDatos(boolean esC){
		Vector <String> imprimir = new Vector<String>();
		String printEnteros;
		String printFlotantes;
		String printCaracteres;
		String printTexto;
		String printDefaul;
		String print;
		String entero;
		String caracter;
		String flotante;
		String separador;
		String ultimo;
		if(esC){
			printEnteros = "printf("+"\""+"%d"+"\""+",";
			printFlotantes = "printf("+"\""+"%f"+"\""+",";
			printCaracteres = "printf("+"\""+"%c"+"\""+",";
			printTexto = "printf("+"\"";
			printDefaul = "printf("+"\""+"%d"+"\""+",";
			print = "printf("+"\"";
			entero = "%d";
			caracter = "%c";
			flotante = "%f";
			separador = ",";
			ultimo = ");";
		}
		else{
			printEnteros = "cout <<";
			printFlotantes = "cout <<";
			printCaracteres = "cout <<";
			printTexto = "cout <<"+"\"";
			printDefaul = "cout <<";
			print = "cout <<";
			entero = "";
			caracter = "";
			flotante = "";
			separador = "<<";
			ultimo = "<< endl;";
		}
		for(int x=0;x<codeOfFigure.size();x++){
			String aux = codeOfFigure.elementAt(x);
			while(aux.startsWith(" ")){
				aux = aux.substring(1);
			}
			if(aux.startsWith("\\p")){
				String tab = codeOfFigure.elementAt(x).substring(0,codeOfFigure.elementAt(x).indexOf("\\"));
				if(aux.indexOf(",")>0){
					String inicio;
					String fin="";
					if(esC){
						fin=",";
					}
					String aux2;
					int comaAnterior = aux.indexOf(",");
					int comaPosterior = aux.indexOf(",",comaAnterior+1);
					aux2 = aux.substring(0,comaAnterior);
					while(aux2.endsWith(" ")){
						aux2 = aux2.substring(0,aux2.length()-1);
					}
					if(aux2.startsWith("\\p"+"\"") && aux2.endsWith("\"")){
						inicio = aux2.substring(aux.indexOf("\"")+1,aux2.length()-1);
						if(!esC){
							inicio="";
							fin = fin +aux2.substring(2)+separador;	
						}
					}
					else{
						String tipo = getTypeOfData(aux2.substring(2));
						if(tipo!=null){
							if(tipo.indexOf("int")==0){
								inicio = entero;
								fin = fin + aux2.substring(2)+ separador;
							}
							else if(tipo.indexOf("char")==0){
								inicio = caracter;
								fin = fin + aux2.substring(2)+ separador;
							}
							else{
								inicio = flotante;
								fin = fin + aux2.substring(2)+ separador;
							}
						}
						else{
							inicio = entero;
							fin = fin + aux2.substring(2)+ separador;
						}
					}
					while(comaPosterior!=-1){
						aux2 = aux.substring(comaAnterior+1, comaPosterior);
						while(aux2.startsWith(" ")){
							aux2 = aux2.substring(1);
						}
						while(aux2.endsWith(" ")){
							aux2 = aux2.substring(0,aux2.length()-1);
						}
						if(aux2.startsWith("\"") && aux2.endsWith("\"")){
							inicio = inicio + " " +aux2.substring(1,aux2.length()-1);
							if(!esC){
								inicio="";
								fin = fin +aux2+separador;
							}
						}
						else{
							String tipo = getTypeOfData(aux2);
							if(tipo!=null){
								if(tipo.indexOf("int")==0){
									inicio = inicio +" "+ entero;
									fin = fin +aux2+ separador;
								}
								else if(tipo.indexOf("char")==0){
									inicio = inicio +" "+ caracter;
									fin = fin +aux2+ separador;
								}
								else{
									inicio = inicio +" "+ flotante;
									fin = fin +aux2+ separador;
								}
							}
							else{
								inicio = inicio +" "+ entero;
								fin = fin +aux2+ separador;
							}
						}
						comaAnterior= comaPosterior;
						comaPosterior = aux.indexOf(",",comaAnterior+1);
					}
					aux2 = aux.substring(comaAnterior+1);
					while(aux2.startsWith(" ")){
						aux2 = aux2.substring(1);
					}
					if(aux2.endsWith(";")){
						aux2 = aux2.substring(0,aux2.length()-1);
					}
					if(aux2.startsWith("\"") && aux2.endsWith("\"")){
						inicio = inicio + " " +aux2.substring(1,aux2.length()-1);
						if(!esC){
							inicio="";
							fin = fin +aux2;
						}
					}
					else{
						String tipo = getTypeOfData(aux2);
						if(tipo!=null){
							if(tipo.indexOf("int")==0){
								inicio = inicio +" "+ entero;
								fin = fin + aux2;
							}
							else if(tipo.indexOf("char")==0){
								inicio = inicio +" "+ caracter;
								fin = fin + aux2;
							}
							else{
								inicio = inicio +" "+ flotante;
								fin = fin + aux2;
							}
						}
						else{
							inicio = inicio +" "+ entero;
							fin = fin + aux2;
						}
					}
					while(fin.endsWith(",")){
						fin = fin.substring(0,fin.length()-1);
					}
					if(esC){
						inicio = print+ inicio + "\""+ fin +");";
						inicio = tab + inicio;
						codeOfFigure.removeElementAt(x);
						codeOfFigure.insertElementAt(inicio,x);
						imprimir.removeAllElements();
					}
					else{
						inicio="";
						inicio = print+ inicio+ fin +"<< endl;";
						inicio = tab + inicio;
						codeOfFigure.removeElementAt(x);
						codeOfFigure.insertElementAt(inicio,x);
						imprimir.removeAllElements();
					}
				}
				else{
					if(aux.startsWith("\\p"+"\"") && aux.endsWith("\""+";")){
						int pos = aux.indexOf("\"");
						int fin = aux.indexOf("\"", pos+1);
						String imp = tab + printTexto + aux.substring(pos+1,fin)+"\""+ultimo;
						imprimir.add(imp);
						codeOfFigure.removeElementAt(x);
						codeOfFigure.insertElementAt(imprimir.elementAt(0),x);
						imprimir.removeAllElements();
					}
					else{
						String tipo = getTypeOfData(aux.substring(2));
						if(tipo!=null){
							if(tipo.indexOf("int")==0){
								int pos = aux.indexOf("p");
								int fin = aux.indexOf(";");
								if(fin!=-1){
									String imp = tab + printEnteros + 									aux.substring(pos+1,fin)+ultimo;
									imprimir.add(imp);
								}
								else{
									String imp = tab + printEnteros + 									aux.substring(pos+1,fin-1)+ultimo;
									imprimir.add(imp);
								}
							}
							else if(tipo.indexOf("char")==0){
								int pos = aux.indexOf("p");
								int fin = aux.indexOf(";");
								if(fin!=-1){
									String imp = tab + printCaracteres + 									aux.substring(pos+1,fin)+ultimo;
									imprimir.add(imp);
								}
								else{
									String imp = tab + printCaracteres + 									aux.substring(pos+1,fin-1)+ultimo;
									imprimir.add(imp);
								}
							}
							else {
								int pos = aux.indexOf("p");
								int fin = aux.indexOf(";");
								if(fin!=-1){
									String imp = tab + printFlotantes + 									aux.substring(pos+1,fin)+ultimo;
									imprimir.add(imp);
								}
								else{
									String imp = tab + printFlotantes + 									aux.substring(pos+1,fin-1)+ultimo;
									imprimir.add(imp);
								}
							}
							codeOfFigure.removeElementAt(x);
							codeOfFigure.insertElementAt(imprimir.elementAt(0),x);
							imprimir.removeAllElements();
						}
						else{
							int pos = aux.indexOf("p");
							int fin = aux.indexOf(";");
							if(fin!=-1){
								String imp = tab + printDefaul + 								aux.substring(pos+1,fin)+ultimo;
								imprimir.add(imp);
							}
							else{
								String imp = tab + printDefaul + 								aux.substring(pos+1,fin-1)+ultimo;
								imprimir.add(imp);
							}
							codeOfFigure.removeElementAt(x);
							codeOfFigure.insertElementAt(imprimir.elementAt(0),x);
							imprimir.removeAllElements();
						}
					}
				}
			}
		}
	}*/
	public void generateInstructionOfDataOutputForCodeC(){
		Vector <String> imprimir = new Vector<String>();
		String printEnteros = "printf("+"\""+"%d"+"\""+",";
		String printFlotantes = "printf("+"\""+"%f"+"\""+",";
		String printCaracteres = "printf("+"\""+"%c"+"\""+",";
		String printTexto = "printf("+"\"";
		String printDefaul  = "printf("+"\""+"%d"+"\""+",";
		String print = "printf("+"\"";
		String entero = "%d";
		String caracter = "%c";
		String flotante = "%f";
		String separador = ",";
		String ultimo = ");";
		
		for(int x=0;x<codeOfFigure.size();x++){
			String aux = codeOfFigure.elementAt(x);
			while(aux.startsWith(" ")){
				aux = aux.substring(1);
			}
			if(aux.startsWith("\\p")){
				String tab = codeOfFigure.elementAt(x).substring(0,codeOfFigure.elementAt(x).indexOf("\\"));
				if(aux.indexOf(",")>0){
					String inicio;
					String fin="";
					
					fin=",";
					
					String aux2;
					int comaAnterior = aux.indexOf(",");
					int comaPosterior = aux.indexOf(",",comaAnterior+1);
					aux2 = aux.substring(0,comaAnterior);
					while(aux2.endsWith(" ")){
						aux2 = aux2.substring(0,aux2.length()-1);
					}
					if(aux2.startsWith("\\p"+"\"") && aux2.endsWith("\"")){
						inicio = aux2.substring(aux.indexOf("\"")+1,aux2.length()-1);
					}
					else{
						String tipo = getTypeOfData(aux2.substring(2));
						if(tipo!=null){
							if(tipo.indexOf("int")==0){
								inicio = entero;
								fin = fin + aux2.substring(2)+ separador;
							}
							else if(tipo.indexOf("char")==0){
								inicio = caracter;
								fin = fin + aux2.substring(2)+ separador;
							}
							else{
								inicio = flotante;
								fin = fin + aux2.substring(2)+ separador;
							}
						}
						else{
							inicio = entero;
							fin = fin + aux2.substring(2)+ separador;
						}
					}
					while(comaPosterior!=-1){
						aux2 = aux.substring(comaAnterior+1, comaPosterior);
						while(aux2.startsWith(" ")){
							aux2 = aux2.substring(1);
						}
						while(aux2.endsWith(" ")){
							aux2 = aux2.substring(0,aux2.length()-1);
						}
						if(aux2.startsWith("\"") && aux2.endsWith("\"")){
							inicio = inicio + " " +aux2.substring(1,aux2.length()-1);
						}
						else{
							String tipo = getTypeOfData(aux2);
							if(tipo!=null){
								if(tipo.indexOf("int")==0){
									inicio = inicio +" "+ entero;
									fin = fin +aux2+ separador;
								}
								else if(tipo.indexOf("char")==0){
									inicio = inicio +" "+ caracter;
									fin = fin +aux2+ separador;
								}
								else{
									inicio = inicio +" "+ flotante;
									fin = fin +aux2+ separador;
								}
							}
							else{
								inicio = inicio +" "+ entero;
								fin = fin +aux2+ separador;
							}
						}
						comaAnterior= comaPosterior;
						comaPosterior = aux.indexOf(",",comaAnterior+1);
					}
					aux2 = aux.substring(comaAnterior+1);
					while(aux2.startsWith(" ")){
						aux2 = aux2.substring(1);
					}
					if(aux2.endsWith(";")){
						aux2 = aux2.substring(0,aux2.length()-1);
					}
					if(aux2.startsWith("\"") && aux2.endsWith("\"")){
						inicio = inicio + " " +aux2.substring(1,aux2.length()-1);
					}
					else{
						String tipo = getTypeOfData(aux2);
						if(tipo!=null){
							if(tipo.indexOf("int")==0){
								inicio = inicio +" "+ entero;
								fin = fin + aux2;
							}
							else if(tipo.indexOf("char")==0){
								inicio = inicio +" "+ caracter;
								fin = fin + aux2;
							}
							else{
								inicio = inicio +" "+ flotante;
								fin = fin + aux2;
							}
						}
						else{
							inicio = inicio +" "+ entero;
							fin = fin + aux2;
						}
					}
					while(fin.endsWith(",")){
						fin = fin.substring(0,fin.length()-1);
					}
					
					inicio = print+ inicio + "\""+ fin +");";
					inicio = tab + inicio;
					codeOfFigure.removeElementAt(x);
					codeOfFigure.insertElementAt(inicio,x);
					imprimir.removeAllElements();
					
				}
				else{
					if(aux.startsWith("\\p"+"\"") && aux.endsWith("\""+";")){
						int pos = aux.indexOf("\"");
						int fin = aux.indexOf("\"", pos+1);
						String imp = tab + printTexto + aux.substring(pos+1,fin)+"\""+ultimo;
						imprimir.add(imp);
						codeOfFigure.removeElementAt(x);
						codeOfFigure.insertElementAt(imprimir.elementAt(0),x);
						imprimir.removeAllElements();
					}
					else{
						String tipo = getTypeOfData(aux.substring(2));
						if(tipo!=null){
							if(tipo.indexOf("int")==0){
								int pos = aux.indexOf("p");
								int fin = aux.indexOf(";");
								if(fin!=-1){
									String imp = tab + printEnteros + 									aux.substring(pos+1,fin)+ultimo;
									imprimir.add(imp);
								}
								else{
									String imp = tab + printEnteros + 									aux.substring(pos+1,fin-1)+ultimo;
									imprimir.add(imp);
								}
							}
							else if(tipo.indexOf("char")==0){
								int pos = aux.indexOf("p");
								int fin = aux.indexOf(";");
								if(fin!=-1){
									String imp = tab + printCaracteres + 									aux.substring(pos+1,fin)+ultimo;
									imprimir.add(imp);
								}
								else{
									String imp = tab + printCaracteres + 									aux.substring(pos+1,fin-1)+ultimo;
									imprimir.add(imp);
								}
							}
							else {
								int pos = aux.indexOf("p");
								int fin = aux.indexOf(";");
								if(fin!=-1){
									String imp = tab + printFlotantes + 									aux.substring(pos+1,fin)+ultimo;
									imprimir.add(imp);
								}
								else{
									String imp = tab + printFlotantes + 									aux.substring(pos+1,fin-1)+ultimo;
									imprimir.add(imp);
								}
							}
							codeOfFigure.removeElementAt(x);
							codeOfFigure.insertElementAt(imprimir.elementAt(0),x);
							imprimir.removeAllElements();
						}
						else{
							int pos = aux.indexOf("p");
							int fin = aux.indexOf(";");
							if(fin!=-1){
								String imp = tab + printDefaul + 								aux.substring(pos+1,fin)+ultimo;
								imprimir.add(imp);
							}
							else{
								String imp = tab + printDefaul + 								aux.substring(pos+1,fin-1)+ultimo;
								imprimir.add(imp);
							}
							codeOfFigure.removeElementAt(x);
							codeOfFigure.insertElementAt(imprimir.elementAt(0),x);
							imprimir.removeAllElements();
						}
					}
				}
			}
		}
	}
	public void generateInstructionOfDataOutputForCodeCpp(){
		Vector <String> imprimir = new Vector<String>();
		String printEnteros = "cout <<";
		String printFlotantes = "cout <<";
		String printCaracteres = "cout <<";
		String printTexto = "cout <<"+"\"";
		String printDefaul = "cout <<";
		String print = "cout <<";
		String entero = "";
		String caracter = "";
		String flotante = "";
		String separador = "<<";
		String ultimo = "<< endl;";
		
		for(int x=0;x<codeOfFigure.size();x++){
			String aux = codeOfFigure.elementAt(x);
			while(aux.startsWith(" ")){
				aux = aux.substring(1);
			}
			if(aux.startsWith("\\p")){
				String tab = codeOfFigure.elementAt(x).substring(0,codeOfFigure.elementAt(x).indexOf("\\"));
				if(aux.indexOf(",")>0){
					String inicio;
					String fin="";
					String aux2;
					int comaAnterior = aux.indexOf(",");
					int comaPosterior = aux.indexOf(",",comaAnterior+1);
					aux2 = aux.substring(0,comaAnterior);
					while(aux2.endsWith(" ")){
						aux2 = aux2.substring(0,aux2.length()-1);
					}
					if(aux2.startsWith("\\p"+"\"") && aux2.endsWith("\"")){
						inicio = aux2.substring(aux.indexOf("\"")+1,aux2.length()-1);
						
						inicio="";
						fin = fin +aux2.substring(2)+separador;	
					}
					else{
						String tipo = getTypeOfData(aux2.substring(2));
						if(tipo!=null){
							if(tipo.indexOf("int")==0){
								inicio = entero;
								fin = fin + aux2.substring(2)+ separador;
							}
							else if(tipo.indexOf("char")==0){
								inicio = caracter;
								fin = fin + aux2.substring(2)+ separador;
							}
							else{
								inicio = flotante;
								fin = fin + aux2.substring(2)+ separador;
							}
						}
						else{
							inicio = entero;
							fin = fin + aux2.substring(2)+ separador;
						}
					}
					while(comaPosterior!=-1){
						aux2 = aux.substring(comaAnterior+1, comaPosterior);
						while(aux2.startsWith(" ")){
							aux2 = aux2.substring(1);
						}
						while(aux2.endsWith(" ")){
							aux2 = aux2.substring(0,aux2.length()-1);
						}
						if(aux2.startsWith("\"") && aux2.endsWith("\"")){
							inicio = inicio + " " +aux2.substring(1,aux2.length()-1);
							inicio="";
							fin = fin +aux2+separador;
							
						}
						else{
							String tipo = getTypeOfData(aux2);
							if(tipo!=null){
								if(tipo.indexOf("int")==0){
									inicio = inicio +" "+ entero;
									fin = fin +aux2+ separador;
								}
								else if(tipo.indexOf("char")==0){
									inicio = inicio +" "+ caracter;
									fin = fin +aux2+ separador;
								}
								else{
									inicio = inicio +" "+ flotante;
									fin = fin +aux2+ separador;
								}
							}
							else{
								inicio = inicio +" "+ entero;
								fin = fin +aux2+ separador;
							}
						}
						comaAnterior= comaPosterior;
						comaPosterior = aux.indexOf(",",comaAnterior+1);
					}
					aux2 = aux.substring(comaAnterior+1);
					while(aux2.startsWith(" ")){
						aux2 = aux2.substring(1);
					}
					if(aux2.endsWith(";")){
						aux2 = aux2.substring(0,aux2.length()-1);
					}
					if(aux2.startsWith("\"") && aux2.endsWith("\"")){
						inicio = inicio + " " +aux2.substring(1,aux2.length()-1);
						inicio="";
						fin = fin +aux2;
					}
					else{
						String tipo = getTypeOfData(aux2);
						if(tipo!=null){
							if(tipo.indexOf("int")==0){
								inicio = inicio +" "+ entero;
								fin = fin + aux2;
							}
							else if(tipo.indexOf("char")==0){
								inicio = inicio +" "+ caracter;
								fin = fin + aux2;
							}
							else{
								inicio = inicio +" "+ flotante;
								fin = fin + aux2;
							}
						}
						else{
							inicio = inicio +" "+ entero;
							fin = fin + aux2;
						}
					}
					while(fin.endsWith(",")){
						fin = fin.substring(0,fin.length()-1);
					}
					
					inicio="";
					inicio = print+ inicio+ fin +"<< endl;";
					inicio = tab + inicio;
					codeOfFigure.removeElementAt(x);
					codeOfFigure.insertElementAt(inicio,x);
					imprimir.removeAllElements();
					
				}
				else{
					if(aux.startsWith("\\p"+"\"") && aux.endsWith("\""+";")){
						int pos = aux.indexOf("\"");
						int fin = aux.indexOf("\"", pos+1);
						String imp = tab + printTexto + aux.substring(pos+1,fin)+"\""+ultimo;
						imprimir.add(imp);
						codeOfFigure.removeElementAt(x);
						codeOfFigure.insertElementAt(imprimir.elementAt(0),x);
						imprimir.removeAllElements();
					}
					else{
						String tipo = getTypeOfData(aux.substring(2));
						if(tipo!=null){
							if(tipo.indexOf("int")==0){
								int pos = aux.indexOf("p");
								int fin = aux.indexOf(";");
								if(fin!=-1){
									String imp = tab + printEnteros + 									aux.substring(pos+1,fin)+ultimo;
									imprimir.add(imp);
								}
								else{
									String imp = tab + printEnteros + 									aux.substring(pos+1,fin-1)+ultimo;
									imprimir.add(imp);
								}
							}
							else if(tipo.indexOf("char")==0){
								int pos = aux.indexOf("p");
								int fin = aux.indexOf(";");
								if(fin!=-1){
									String imp = tab + printCaracteres + 									aux.substring(pos+1,fin)+ultimo;
									imprimir.add(imp);
								}
								else{
									String imp = tab + printCaracteres + 									aux.substring(pos+1,fin-1)+ultimo;
									imprimir.add(imp);
								}
							}
							else {
								int pos = aux.indexOf("p");
								int fin = aux.indexOf(";");
								if(fin!=-1){
									String imp = tab + printFlotantes + 									aux.substring(pos+1,fin)+ultimo;
									imprimir.add(imp);
								}
								else{
									String imp = tab + printFlotantes + 									aux.substring(pos+1,fin-1)+ultimo;
									imprimir.add(imp);
								}
							}
							codeOfFigure.removeElementAt(x);
							codeOfFigure.insertElementAt(imprimir.elementAt(0),x);
							imprimir.removeAllElements();
						}
						else{
							int pos = aux.indexOf("p");
							int fin = aux.indexOf(";");
							if(fin!=-1){
								String imp = tab + printDefaul + 								aux.substring(pos+1,fin)+ultimo;
								imprimir.add(imp);
							}
							else{
								String imp = tab + printDefaul + 								aux.substring(pos+1,fin-1)+ultimo;
								imprimir.add(imp);
							}
							codeOfFigure.removeElementAt(x);
							codeOfFigure.insertElementAt(imprimir.elementAt(0),x);
							imprimir.removeAllElements();
						}
					}
				}
			}
		}
	}
	/*public String tipoDeDato(String variable){
		for(int i=0; i<TableOfVariable.size(); i++){
			if(TableOfVariable.elementAt(i).lastIndexOf(variable)>0){
				if(TableOfVariable.elementAt(i).lastIndexOf("int")>=0){
					return "int";
				}
				if(TableOfVariable.elementAt(i).lastIndexOf("char")>=0){
					return "char";
				}
				if(TableOfVariable.elementAt(i).lastIndexOf("float")>=0){
					return "float";
				}
			}
		}
		return null;
	}*/
	public String getTypeOfData(String variable){
		for(int i=0; i<TableOfVariable.size(); i++){
			if(TableOfVariable.elementAt(i).lastIndexOf(variable)>0){
				if(TableOfVariable.elementAt(i).lastIndexOf("int")>=0){
					return "int";
				}
				if(TableOfVariable.elementAt(i).lastIndexOf("char")>=0){
					return "char";
				}
				if(TableOfVariable.elementAt(i).lastIndexOf("float")>=0){
					return "float";
				}
			}
		}
		return null;
	}
	public String getCodeGDB(String codigoTotal){
		CharSequence valor = "=";
		CharSequence entero = "int";
		CharSequence flotante = "float";
		CharSequence caracter = "char";
		CharSequence impresion = "cout";
		String[] lineas = codigoTotal.split("\n");
		codigoTotal="";
		for(int i=0; i<lineas.length; i++){
			if(i>3 && (!lineas[i].contains(impresion)) && (lineas[i].contains(entero) || lineas[i].contains(flotante) || lineas[i].contains(caracter)) ){
				if(!lineas[i].contains(valor)){
					while(lineas[i].startsWith(" ")){
						lineas[i] = lineas[i].substring(1);
					}
					if(lineas[i].startsWith("int")){
						String[] copia = lineas[i].split(";");
						lineas[i] = copia[0] + " = 0;";
					}
					else if(lineas[i].startsWith("char")){
						String[] copia = lineas[i].split(";");
						lineas[i] = copia[0] + " = '0';";
					}
					else if (lineas[i].startsWith("float")){
						String[] copia = lineas[i].split(";");
						lineas[i] = copia[0] + " = 0.0;";
					}
				}
			}
			else if(lineas[i].contains(impresion)){
				while(lineas[i].startsWith(" ")){
					lineas[i] = lineas[i].substring(1);
				}					
				lineas[i] = lineas[i].replaceFirst("endl;", "\".,.\"<<endl;");
			}
			codigoTotal=codigoTotal + lineas[i]+"\n";
		}
		return codigoTotal;
	}
	
	public String getCode() {
	    return code;
	}
	
	public void setCodeOfFigure(Vector<String> codeOfFigure) {
	    this.codeOfFigure = codeOfFigure;
	}
}
