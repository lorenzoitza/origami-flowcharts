package Administracion.Funcionalidad.Codigo;

import java.io.Serializable;
import java.util.Vector;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;
import Administracion.Figura;
import Administracion.Funcionalidad.Exportar;
import Grafico.*;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.TerminationFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;
import Grafico.Figuras.Elipse;
import Imagenes.ImageLoader;

/**
 * Esta clase es la base administra el codigo 
 * agregado por el usuario. 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Instruccion implements Serializable{
	private Shell shell;
	private Vector<String> codigo = new Vector<String>();
	private Vector<String> TablaVariables = new Vector<String>();
	private String espacio = "      ";
	public String codigoTotal = new String();
	public GenerarCodigo codigoCoCmasMas = new GenerarCodigo();
	
	public void main(Vector<Figura> diagrama, boolean esCodigoC){
		codigoFiguras(diagrama,esCodigoC);
	}
	public void generarGDB(Vector<Figura> diagrama){
		recorrido(diagrama,1);
		codigoCoCmasMas.setInstrucciones(codigo,TablaVariables);
		codigoTotal = codigoCoCmasMas.getCodigoCmasmas();
		vectorCiclos(diagrama);
		codigoTotal = codigoCoCmasMas.getCodigoGDB(codigoTotal);
		//llamar al metodo que agregara a los if y while "&& VaRiAbLe==0"
		//para que asi lo cambie el codigoTotal antes de que se mande a compilar y ejecutar.
		ArreglarPasoAPaso();
	}
	/**
	 * Crea la ventana en la cual el codigo
	 * se representara.
	 * @param Display d 
	 */
	public void ventana(Display d) {
		shell = new Shell(Ventana.shell);
		shell.setSize(500,387);
		shell.setLocation(300, 200);
		shell.setText("Codigo Fuente");
		getTable(codigoTotal);
		getToolbar(d);
		shell.open();
		while(!shell.isDisposed()){
			if(!d.readAndDispatch())
				d.sleep();
		}
	}
	public void getTable(String codigo){
		Table t = new Table(shell, SWT.BORDER);
		t.setBounds(0,0,496,320);
	    TableColumn tc1 = new TableColumn(t, SWT.CENTER);
	    tc1.setWidth(20);
	    tc1.setText("Linea");
	    TableColumn tc2 = new TableColumn(t, SWT.LEFT);
	    tc2.setWidth(415);
	    tc2.setText("Instruccion");
	    String[] lineas = codigo.split("\n");
	    for(int x =0;x<lineas.length;x++){
	    	if(lineas[x]!=null){
	    		TableItem item1 = new TableItem(t, SWT.NONE);
		    	for(int y=1;y<=2;y++){
		    		if(y==1){
		    			item1.setText(1,lineas[x]);
		    		}
		    		else{
		    			item1.setText(0,Integer.toString(x+1));	
		    		}	
		    	}
	    	}
		} 
	    t.setLinesVisible(true);
	    t.setHeaderVisible(true);
	}
	public void getToolbar(Display d){
		ToolBar bar = new ToolBar(shell, SWT.DOWN | SWT.FLAT | SWT.BORDER);
		bar.setBounds(0,320,500,40);
		Button boton = new Button(bar, SWT.FLAT);
		//Image imagenExport = new Image(d, "imagenes\\export.png");
		boton.setImage(ImageLoader.getImage("export.png"));
		boton.setBounds(0,0,33,33);
		boton.setToolTipText("Exportar a C");
		boton.addSelectionListener(new SelectionAdapter() {
			 public void widgetSelected(SelectionEvent event) {
				 FileDialog dialog = new FileDialog(shell,SWT.SAVE);
			     dialog.setFilterExtensions(new String[] { "*.c"});
			     String archivo = dialog.open();
			     if(archivo != null){
			    	 Exportar expor = new Exportar(Ventana.diagramas);
			    	 expor.exportarCodigoC(archivo);
				 }
			}
		});
		Button boton2 = new Button(bar, SWT.FLAT);
		//Image imagenIf = new Image(d, "imagenes\\export.png");
		boton2.setImage(ImageLoader.getImage("export.png"));
		boton2.setBounds(35,0,33,33);
		boton2.setText(".cpp");
		boton2.setToolTipText("Exportar a C++");
		boton2.addSelectionListener(new SelectionAdapter() {
			 public void widgetSelected(SelectionEvent event) {
				 FileDialog dialog = new FileDialog(shell,SWT.SAVE);
			     dialog.setFilterExtensions(new String[] { "*.cpp"});
			     String archivo = dialog.open();
			     if(archivo != null){
			    	 Exportar expor = new Exportar(Ventana.diagramas);
			    	 expor.exportarCodigoCpp(archivo);
				 }
			}
		});
		Button boton3 = new Button(bar, SWT.FLAT);
		//Image imagenIf = new Image(d, "imagenes\\export.png");
		//boton3.setImage(imagenIf);
		boton3.setText(".exe");
		boton3.setBounds(70,0,33,33);
		boton3.setToolTipText("Exportar a .exe");
		boton3.addSelectionListener(new SelectionAdapter() {
			 public void widgetSelected(SelectionEvent event) {
				 FileDialog dialog = new FileDialog(shell,SWT.SAVE);
			     dialog.setFilterExtensions(new String[] { "*.exe"});
			     String archivo = dialog.open();
			     if(archivo != null){
			    	 String nombre = dialog.getFileName();
			    	 nombre = nombre.substring(0,nombre.indexOf("."));
			    	 Exportar expor = new Exportar(Ventana.diagramas);
			    	 expor.exportarEjecutable(archivo,nombre);
				 }
			}
		});
	}
	public String getCodigoC(Vector<Figura> diagrama){
		codigoFiguras(diagrama,true);
		return codigoTotal;
	}
	public String getCodigoCpp(Vector<Figura> diagrama){
		codigoFiguras(diagrama,false);
		return codigoTotal;
	}
	/**
	 * Este metodo agrega el codigo de todas las figuras en un string.
	 * @param diagrama
	 */
	public void codigoFiguras(Vector<Figura> diagrama, boolean esCodigoC){
		recorrido(diagrama,1);
		codigoCoCmasMas.setInstrucciones(codigo,TablaVariables);
		if(esCodigoC){
			codigoTotal = codigoCoCmasMas.getCodigoC();
		}
		else{
			codigoTotal = codigoCoCmasMas.getCodigoCmasmas();
		}
		vectorCiclos(diagrama);
	}
	/*
	 * este es el metodo que tengo que checar
	 * para separar los arreglos con las comas..
	 */
	public Vector<String> masVariables(String linea,String tipo){
		Vector<String> variables = new Vector<String>();
		String tipoAnterior=tipo,aux;
		int comaAnterior = linea.indexOf(",");
		int comaPosterior = linea.indexOf(",",comaAnterior+1);
		//en caso de que solo halla una coma
		if(comaPosterior==-1){
			//analizar la variable aux para ver si no es una declaracion de un array
			if(comaAnterior==-1){			//caso especial donde solo hay dos un array y una variable
				aux =linea;
				while(aux.startsWith(" ")){
					aux = aux.substring(1);
				}
				if(aux.startsWith("int")){
					tipoAnterior = "int";
				}
				else if(aux.startsWith("char")){
					tipoAnterior = "char";
				}
				else if (aux.startsWith("float")){
					tipoAnterior = "float";
				}
				if(tipoAnterior!=null){
					aux = tipoAnterior +" "+ aux;
				}
				variables.add(aux);
				
			}
			else{
			aux = linea.substring(0,comaAnterior);
			int array = aux.indexOf("{");
			//aca checo si es un arreglo de dos posiciones.
			if(array!=-1){
				aux = linea;
				while(aux.startsWith(" ")){
					aux = aux.substring(1);
				}
				//checar si tiene tipo si no agregar
				//********************************************no se si deba ir
				if(aux.startsWith("int")){
					tipoAnterior = "int";
				}
				else if(aux.startsWith("char")){
					tipoAnterior = "char";
				}
				else if (aux.startsWith("float")){
					tipoAnterior = "float";
				}
				else{
					if(tipoAnterior!=null){
						aux = tipoAnterior +" "+ aux;
					}
				}
				//agrego //********************************************no se si deba ir
				variables.add(aux);
			}
			else{
			while(aux.startsWith(" ")){
				aux = aux.substring(1);
			}
			if(aux.startsWith("int")){
				tipoAnterior = "int";
			}
			else if(aux.startsWith("char")){
				tipoAnterior = "char";
			}
			else if (aux.startsWith("float")){
				tipoAnterior = "float";
			}
			else{
				if(tipoAnterior!=null){
					aux = tipoAnterior +" "+ aux;
				}
			}
			variables.add(aux);
			aux = linea.substring(comaAnterior+1);
			while(aux.startsWith(" ")){
				aux = aux.substring(1);
			}
			if(aux.startsWith("int") || aux.startsWith("char") || aux.startsWith("float")){
				variables.add(aux);
			}
			else{
				if(tipoAnterior!=null){
					aux = tipoAnterior +" "+ aux;
				}
				variables.add(aux);
			}
		  }
		}
		}
		else{
			//aca debo de checar si hay un array
			aux = linea.substring(0,comaAnterior);
			
			//********************************************************************
			int array = aux.indexOf("{");
			//aca checo si es un arreglo de dos posiciones.
			int llaves=0;
			String declaracion;
			if(array!=-1){			//es un array
				llaves++;
				declaracion = aux;
				//***** leo todos las llaves abiertas
				array = aux.indexOf("{",array+1);
				while(array!=-1){
					llaves++;
					array = aux.indexOf("{",array+1);
				}
				
				while(llaves!=0){
					if(comaPosterior==-1){
						aux = linea.substring(comaAnterior, linea.length());
						declaracion = declaracion + aux;
						break;
					}
					aux = linea.substring(comaAnterior, comaPosterior);
					//**************
					array=-1;
					array = aux.indexOf("{",array+1);
					while(array!=-1){
						llaves++;
						array = aux.indexOf("{",array+1);
					}
					
					array=-1;
					array = aux.indexOf("}",array+1);
					while(array!=-1){
						llaves--;
						array = aux.indexOf("}",array+1);
					}
					/*//****************
					array=aux.indexOf("{");
					if(array!=-1){
						llaves++;
					}
					array=aux.indexOf("}");
					if(array!=-1){
						llaves--;
					}*/
					//actualizar las comas
					comaAnterior = comaPosterior;
					comaPosterior = linea.indexOf(",",comaAnterior+1);
					declaracion = declaracion + aux;
				}
				
				
				while(declaracion.startsWith(" ")){
					declaracion = declaracion.substring(1);
				}
				String auu = declaracion;
				if(declaracion.startsWith("int")){
					tipoAnterior = "int";
				}
				else if(declaracion.startsWith("char")){
					tipoAnterior = "char";
				}
				else if (declaracion.startsWith("float")){
					tipoAnterior = "float";
				}
				else{
					if(tipoAnterior!=null){
						declaracion = tipoAnterior +" "+ declaracion;
					}
				}
				variables.add(declaracion);
				
				if(comaPosterior!=-1 || linea.indexOf(",",auu.length())!=-1){
					//System.out.println(linea.substring(comaAnterior+1));
					Vector<String> variables2 = masVariables(linea.substring(comaAnterior+1),tipoAnterior);
					for(int i=0; i<variables2.size(); i++){
						variables.add(variables2.elementAt(i));
					}
				}
				else{
				}
				
			}
			else{
				//no es un array...
				//***********************************************************************
				while(aux.startsWith(" ")){
					aux = aux.substring(1);
				}
				if(aux.startsWith("int")){
					tipoAnterior = "int";
				}
				else if(aux.startsWith("char")){
					tipoAnterior = "char";
				}
				else if (aux.startsWith("float")){
					tipoAnterior = "float";
				}
				else{
					if(tipoAnterior!=null){
						aux = tipoAnterior +" "+ aux;
					}
				}
				variables.add(aux);
				Vector<String> variables2 = masVariables(linea.substring(comaAnterior+1),tipoAnterior);
				for(int i=0; i<variables2.size(); i++){
					variables.add(variables2.elementAt(i));
				}
			}
			
		}
		return variables;
	}
	/**
	 * Este metodo se encarga de encontrar el final de un 
	 * ciclo If.
	 * @param cont
	 * @return int
	 */
	public int[] ciclos(int cont){
		int inicio=0,fin=0,x;
		int[] iniFin = new int[2]; 
		char ciclo = '{';
		char finCiclo = '}';
		boolean bandera = false;
		for(x=0;x<codigo.size();x++){
			if((inicio==fin) && (inicio != 0) ){
				break;
			}
			if(codigo.elementAt(x).indexOf(ciclo) != -1 && codigo.elementAt(x).indexOf("if") != -1){
				inicio++;
				if(cont==inicio){
					cont=0;
					inicio=1;
					bandera=true;
					iniFin[0] = x;
				}
			}
			else if(codigo.elementAt(x).indexOf(finCiclo) != -1 && bandera){
				fin++;
			}
			else if((codigo.elementAt(x).indexOf("else") != -1 || codigo.elementAt(x).indexOf("while") != -1 || codigo.elementAt(x).indexOf("for") != -1)&& bandera){
				fin--;
			}
		}
		iniFin[1]=x-1;
		return iniFin;
	}
	/**
	 * Este metodo se encarga de encontrar el final de un 
	 * ciclo For.
	 * @param cont
	 * @return int
	 */
	public int[] ciclosF(int cont){
		int inicio=0,fin=0,x;
		int[] iniFin = new int[2]; 
		char ciclo = '{';
		char finCiclo = '}';
		boolean bandera = false;
		for(x=0;x<codigo.size();x++){
			if((inicio==fin) && (inicio != 0) ){
				break;
			}
			if(codigo.elementAt(x).indexOf(ciclo) != -1 && codigo.elementAt(x).indexOf("for") != -1){
				inicio++;
				if(cont==inicio){
					cont=0;
					inicio=1;
					bandera=true;
					iniFin[0] = x;
				}
			}
			else if(codigo.elementAt(x).indexOf(finCiclo) != -1 && bandera){
				fin++;
			}
			else if((codigo.elementAt(x).indexOf("else") != -1 || codigo.elementAt(x).indexOf("while") != -1 || codigo.elementAt(x).indexOf("if") != -1) && bandera){
				fin--;
			}
		}
		iniFin[1]=x-1;
		return iniFin;
	}
	/**
	 * Este metodo se encarga de encontrar el final de un 
	 * ciclo While.
	 * @param cont
	 * @return int
	 */
	public int[] ciclosW(int cont){
		int inicio=0,fin=0,x;
		int[] iniFin = new int[2]; 
		char ciclo = '{';
		char finCiclo = '}';
		boolean bandera = false;
		for(x=0;x<codigo.size();x++){
			if((inicio==fin) && (inicio != 0) ){
				break;
			}
			if(codigo.elementAt(x).indexOf(ciclo) != -1 && codigo.elementAt(x).indexOf("while") != -1){
				inicio++;
				if(cont==inicio){
					cont=0;
					inicio=1;
					bandera=true;
					iniFin[0] = x;
				}
			}
			else if(codigo.elementAt(x).indexOf(finCiclo) != -1 && bandera){
				fin++;
			}
			else if((codigo.elementAt(x).indexOf("else") != -1 || codigo.elementAt(x).indexOf("for") != -1 || codigo.elementAt(x).indexOf("if") != -1) && bandera){
				fin--;
			}
		}
		iniFin[1]=x-1;
		return iniFin;
	}
	/**
	 * Este metodo se encarga de agregar a cada instruccion de cada ciclo 
	 * el codigo que es parte de su bloque.
	 * @param diagrama
	 */
	public void vectorCiclos(Vector<Figura> diagrama){
		int x=1,contIf=0,contFor=0,contWhile=0;
		int[] u= new int[2];
		while(true){		
			if(diagrama.elementAt(x) instanceof DecisionFigure){
				DecisionFigure f = (DecisionFigure)diagrama.elementAt(x);
				contIf++;
				u = ciclos(contIf);
				f.instruction.instruccion.removeAllElements();
				for(int m=0;m<codigo.size();m++){
					if(m>=u[0] && m<=u[1]){
						InstruccionSimple inst = new InstruccionSimple();
						int d=0;
						String copia = codigo.elementAt(m);
						while(codigo.elementAt(m).length()>d){
							if(copia.startsWith(" ")){
								copia = copia.replaceFirst(" ", "");
							}
							d++;
						}
						codigo.setElementAt(copia, m);
						inst.setInstruccionSimple(codigo.elementAt(m));
						f.instruction.instruccion.add(inst);
					}
				}
				diagrama.setElementAt(f, x);
			}
			else if(diagrama.elementAt(x) instanceof ForFigure){
				ForFigure f = (ForFigure)diagrama.elementAt(x);
				contFor++;
				u = ciclosF(contFor);
				f.instruction.instruccion.removeAllElements();
				for(int m=0;m<codigo.size();m++){
					if(m>=u[0] && m<=u[1]){
						InstruccionSimple inst = new InstruccionSimple();
						int d=0;
						String copia = codigo.elementAt(m);
						while(codigo.elementAt(m).length()>d){
							if(copia.startsWith(" ")){
								copia = copia.replaceFirst(" ", "");
							}
							d++;
						}
						codigo.setElementAt(copia, m);
						inst.setInstruccionSimple(codigo.elementAt(m));
						f.instruction.instruccion.add(inst);
					}
				}
				diagrama.setElementAt(f, x);
			}
			else if(diagrama.elementAt(x) instanceof WhileFigure){
				WhileFigure f = (WhileFigure)diagrama.elementAt(x);
				contWhile++;
				u = ciclosW(contWhile);
				f.instruccion.instruccion.removeAllElements();
				for(int m=0;m<codigo.size();m++){
					if(m>=u[0] && m<=u[1]){
						InstruccionSimple inst = new InstruccionSimple();
						int d=0;
						String copia = codigo.elementAt(m);
						while(codigo.elementAt(m).length()>d){
							if(copia.startsWith(" ")){
								copia = copia.replaceFirst(" ", "");
							}
							d++;
						}
						codigo.setElementAt(copia, m);
						inst.setInstruccionSimple(codigo.elementAt(m));
						f.instruccion.instruccion.add(inst);
					}
				}
				diagrama.setElementAt(f, x);
			}
			else if(diagrama.elementAt(x) instanceof TerminationFigure){
				break;
			}
			else{
			}
			x++;
		}
	}
	/**
	 * Este metodo se encarga de definir el tabulado que 
	 * se usara en la siguiente linea.
	 * @param x
	 */
	public void tabula(int x){
		if(x>0){
			for(int y=0;y<x;y++){
				espacio = espacio + " ";
			}
		}
		else{
			for(int y=0;y>x;y--){
				espacio = espacio.replaceFirst(" ", "");
			}
		}
	}
	/**
	 * Este metodo recorre el vector de figuras y agrega cada instruccion
	 * de cada figura a un vector de string.
	 * @param diagrama
	 * @param x
	 * @return int
	 */
	public int recorrido(Vector<Figura> diagrama,int x){
		while(true){		
			if(diagrama.elementAt(x) instanceof DecisionFigure){
				DecisionFigure f = (DecisionFigure)diagrama.elementAt(x);
				if(f.instruction.instruccion.elementAt(0) != null){
					if(f.instruction.instruccion.firstElement().getInstruccionSimple().compareTo("null") != 0 && f.instruction.instruccion.firstElement().getInstruccionSimple().compareTo("") != 0){
						codigo.add(espacio + f.instruction.instruccion.elementAt(0).getInstruccionSimple());
						tabula(4);
						x = recorrido(diagrama,x+2);
						tabula(-4);
						codigo.add(espacio+"}");
						int y = codigo.size();
						tabula(4);
						x = recorrido(diagrama,x+2)+1;
						tabula(-4);
						if(codigo.size()>y){
							String copia2 = new String(espacio + "else{");
							codigo.insertElementAt(copia2, y);
							codigo.add(espacio + "}");
						}
					}
					else{
						codigo.add(espacio + "if(){");
						tabula(4);
						x = recorrido(diagrama,x+2);
						tabula(-4);
						codigo.add(espacio+"}");
						int y = codigo.size();
						tabula(4);
						x = recorrido(diagrama,x+2)+1;
						tabula(-4);
						if(codigo.size()>y){
							String copia2 = new String(espacio + "else{");
							codigo.insertElementAt(copia2, y);
							codigo.add(espacio + "}");
						}
					}
				}
			}
			else if(diagrama.elementAt(x) instanceof ForFigure){
				ForFigure f = (ForFigure)diagrama.elementAt(x);
				if(f.instruction.instruccion.elementAt(0) != null){
					if(f.instruction.instruccion.firstElement().getInstruccionSimple().compareTo("null") != 0 && f.instruction.instruccion.firstElement().getInstruccionSimple().compareTo("") != 0){
						codigo.add(espacio + f.instruction.instruccion.elementAt(0).getInstruccionSimple());
						tabula(4);
						x = recorrido(diagrama,x+1);
						tabula(-4);
						codigo.add(espacio+"}");
					}
					else{
						codigo.add(espacio + "for(){");
						tabula(4);
						x = recorrido(diagrama,x+1);
						tabula(-4);
						codigo.add(espacio+"}");
					}
				}
				x += 5;
			}
			else if(diagrama.elementAt(x) instanceof WhileFigure){
				WhileFigure f = (WhileFigure)diagrama.elementAt(x);
				if(f.instruccion.instruccion.elementAt(0) != null){
					if(f.instruccion.instruccion.firstElement().getInstruccionSimple().compareTo("null") != 0 && f.instruccion.instruccion.firstElement().getInstruccionSimple().compareTo("") != 0){
						codigo.add(espacio + f.instruccion.instruccion.elementAt(0).getInstruccionSimple());
						tabula(4);
						x = recorrido(diagrama,x+1);
						tabula(-4);
						codigo.add(espacio+"}");
					}
					else{
						codigo.add(espacio + "while(){");
						tabula(4);
						x = recorrido(diagrama,x+1);
						tabula(-4);
						codigo.add(espacio+"}");
					}
				}
				x += 5;
			}
			else if(diagrama.elementAt(x) instanceof OutputFigure){
				OutputFigure f = ((OutputFigure)diagrama.elementAt(x));
				if(f.instruccion.getInstruccionSimple() != null){
					String[] ins = new String[3];
					String inst = f.instruccion.getInstruccionSimple();
					ins = inst.split(";");
					for(int d=0;d<ins.length;d++){
						while(ins[d].startsWith(" ")){
							ins[d] = ins[d].replaceFirst(" ","");
						}
						codigo.add(espacio + ins[d] + ";");
					}
				}
			}
			else if(diagrama.elementAt(x) instanceof InputFigure){
				InputFigure f = ((InputFigure)diagrama.elementAt(x));
				if(f.instruccion.getInstruccionSimple() != null){
					String[] ins;
					String inst = f.instruccion.getInstruccionSimple();
					ins = inst.split(";");
					for(int d=0;d<ins.length;d++){
						int coma = ins[d].indexOf(",");
						if(coma!=-1){
							Vector<String> masVariables = masVariables(ins[d],null);
							if(masVariables.elementAt(0).indexOf("Leer:")>=0){
								for(int i=1; i<masVariables.size(); i++){
									String aux = masVariables.elementAt(i);
									aux = "Leer: " +aux;
									masVariables.remove(i);
									masVariables.insertElementAt(aux, i);
								}
							}
							for(int i=0; i<masVariables.size(); i++){
								codigo.add(espacio + masVariables.elementAt(i) + ";"); 
								int u = masVariables.elementAt(i).indexOf("Leer:");
								if(u==-1){
									TablaVariables.add(masVariables.elementAt(i)+";");
								}
							}
							
						}
						else{
							codigo.add(espacio + ins[d] + ";"); 
							int i = ins[d].indexOf("Leer:");
							if(i==-1){
								TablaVariables.add(ins[d]+";");
							}
						}
					}
				}
			}
			else if(diagrama.elementAt(x) instanceof SentenceFigure){
				SentenceFigure f = (SentenceFigure)diagrama.elementAt(x);
				if(f.instruccion.getInstruccionSimple() != null){
					codigo.add(espacio + f.instruccion.getInstruccionSimple());
				}
			}
			else if(diagrama.elementAt(x) instanceof Elipse){
				break;
			}
			else if(diagrama.elementAt(x) instanceof TerminationFigure){
				break;
			}
			else{
			}
			x++;
		}
		return x;
	}
	public void ArreglarPasoAPaso(){
		String aux[] = codigoTotal.split("\n"),aux2;
		String variable = "A5i9I",variable2 = "A5i9W";
		int contador,contador2=0;
		for(int i=0; i<aux.length; i++){
			if(i==3){
				aux[i] = "{int "+variable+"=0,"+ variable2+"=0"+";";
			}
			aux2 = aux[i]; 
			while(aux2.startsWith(" ")){
				aux2 = aux2.substring(1);
			}
			if(aux2.startsWith("if(") || aux2.startsWith("while(") || aux2.startsWith("for(")){
				//busco la ultima ")"
				contador = aux[i].indexOf(")");
				while(contador!=-1){
					contador2=contador;
					contador = aux[i].indexOf(")",contador2+1);
				}
				String primeraParte = aux[i].substring(0,contador2);
				String segundaParte = "&&"+variable +"==0)";
				String terceraParte = aux[i].substring(contador2+1);
				aux[i] = primeraParte+segundaParte+terceraParte;
				if(aux2.startsWith("if(")){
					llave(i,aux,"FinDelIf");
				}
				else{
					llave(i,aux,"FinDelWhile");
				}
			}
			else{
				
			}
		}
		codigoTotal = "";
		for(int i=0;i<aux.length; i++){
			codigoTotal = codigoTotal+aux[i]+"\n";
		}
	}
	public void llave(int posicion, String[]aux,String imprimir){
		String variable = "A5i9I",variable2="A5i9W",seg;
		int contadorNoEs = 0;
		for(int i=posicion+1; i<aux.length; i++){
			if(aux[i].indexOf("for(")!=-1){
				contadorNoEs++;
			}
			if(aux[i].indexOf("if(")!=-1 || aux[i].indexOf("while(")!=-1){
				contadorNoEs++;
			}
			if(aux[i].indexOf("else{")!=-1){
				contadorNoEs++;
			}
			else if(aux[i].indexOf("}")!=-1 && contadorNoEs==0){
				int pos = aux[i].indexOf("}");
				String prim = aux[i].substring(0, pos);
				//String seg = "cout<<"+"\""+imprimir+"\""+"<< endl;}";
				if(imprimir=="FinDelIf"){
					seg = variable +" = "+"0;}";
				}
				else{
					seg = variable2 +" = "+"0;}";
				}
				//String seg = variable +" = "+"0;}";
				String ter = aux[i].substring(pos+1);
				aux[i] = prim+seg+ter;
				break;
			}
			else if(aux[i].indexOf("}")!=-1){
				contadorNoEs--;
			}
		}
		
	}
}