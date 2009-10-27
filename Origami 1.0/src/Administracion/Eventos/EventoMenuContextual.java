package Administracion.Eventos;

import java.util.Vector;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.*;
import Administracion.AdminSeleccion;
import Administracion.Figura;
import Administracion.TabFolder;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.*;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.DecisionFigureEnd;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.CircleFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;
import Grafico.Figuras.Elipse;
import Grafico.VentanaDatos.InputFigureDialog;
import Grafico.VentanaDatos.ForFigureDialog;
import Grafico.VentanaDatos.OutputFigureDialog;
import Grafico.VentanaDatos.SentenceFigureDialog;
import Grafico.VentanaDatos.WhileFigureDialog;
import Grafico.VentanaDatos.DecisionFigureDialog;
import Imagenes.ImageLoader;
/**
 * 
 * Esta clase establece la propiedad de eliminar.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class EventoMenuContextual extends MouseListener.Stub{
	public static Figura fig;
	public static AdminSeleccion selec;
	public static TabFolder tab;
	private MenuItem cortar;
	private MenuItem copiar;
	private MenuItem pegar;
	private MenuItem agregar;
	private MenuItem eliminar;
	private MenuItem nuevo;
	
	/**
	 * Da la propiedad de eliminar 
	 * a la figura recibida.
	 * @param figure
	 */
	public EventoMenuContextual(){
		Ventana.first=true;
	}
	public EventoMenuContextual(Figura figure,TabFolder tabfolder,AdminSeleccion seleccion){
		figure.addMouseListener(this);
		fig = figure;
		tab = tabfolder;
		selec = seleccion;
	}
	public void menu(Menu menu){
		cortar = new MenuItem(menu,SWT.CASCADE);
		cortar.setText("Cortar                  Ctrl+X");
		copiar = new MenuItem(menu,SWT.CASCADE);
		copiar.setText("Copiar                  Ctrl+C");
		pegar = new MenuItem(menu,SWT.CASCADE);
		pegar.setText("Pegar                   Ctrl+V");
		eliminar = new MenuItem(menu,SWT.CASCADE);
		eliminar.setText("Eliminar                 Supr");
		new MenuItem(menu,SWT.SEPARATOR);
		agregar = new MenuItem(menu,SWT.CASCADE);
		agregar.setText("Agregar Codigo");
		pegar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Pegar(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()));
			}
		});
		cortar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Cortar(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()));
			}
		});
		copiar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Copiar(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()));
			}
		});
		eliminar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Eliminar(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()));
			}
		});
		agregar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				agregar(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()));
			}
		});
		Menu menu2 = new Menu(menu); 
		nuevo = new MenuItem(menu,SWT.CASCADE);
		nuevo.setText("Insertar Figura");
		nuevo.setMenu(menu2);
		MenuItem entrada = new MenuItem(menu2,SWT.CASCADE);
		entrada.setText("Entrada");
		MenuItem proceso = new MenuItem(menu2,SWT.CASCADE);
		proceso.setText("Proceso");
		MenuItem decision = new MenuItem(menu2,SWT.CASCADE);
		decision.setText("Decision");
		MenuItem mientras = new MenuItem(menu2,SWT.CASCADE);
		mientras.setText("Mientras");
		MenuItem para = new MenuItem(menu2,SWT.CASCADE);
		para.setText("Para");
		MenuItem salida = new MenuItem(menu2,SWT.CASCADE);
		salida.setText("Impresion");
		decision.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DecisionFigure decision = new DecisionFigure();
			    InstruccionSimple codigo = new InstruccionSimple();
				codigo.setInstruccionSimple("null");
				decision.instruction.instruccion.add(0,codigo);
				Ventana.figuraPrincipal = null;
			    Ventana.figuraPrincipal = decision;
			    insertarFigura(decision);
			    Ventana.figuraPrincipal = null;
				
			}
		});
		proceso.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				 SentenceFigure proceso = new SentenceFigure();
				 InstruccionSimple codigo = new InstruccionSimple();
				 codigo.setInstruccionSimple("null");
				 proceso.instruccion.instruccion = "null";
				 Ventana.figuraPrincipal = null;
				 Ventana.figuraPrincipal = proceso;
				 insertarFigura(proceso);
				 Ventana.figuraPrincipal = null;
			}
		});
		entrada.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				 InputFigure entrada = new InputFigure();
				 InstruccionSimple codigo = new InstruccionSimple();
				 codigo.setInstruccionSimple("null");
				 entrada.instruccion.instruccion = "null";
				 Ventana.figuraPrincipal = null;
				 Ventana.figuraPrincipal = entrada;
				 insertarFigura(entrada);
				 Ventana.figuraPrincipal = null;
			}
		});
		salida.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				OutputFigure salida = new OutputFigure();
			    InstruccionSimple codigo = new InstruccionSimple();
				codigo.setInstruccionSimple("null");
				salida.instruccion.instruccion = "null";
			    Ventana.figuraPrincipal = null;
			    Ventana.figuraPrincipal = salida;
			    insertarFigura(salida);
			    Ventana.figuraPrincipal = null;
			}
		});
		para.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ForFigure For = new ForFigure();
			    InstruccionSimple codigo = new InstruccionSimple();
			    codigo.setInstruccionSimple("null");
			    For.instruction.instruccion.add(0,codigo);
			    Ventana.figuraPrincipal = null;
			    Ventana.figuraPrincipal = For;
			    insertarFigura(For);
			    Ventana.figuraPrincipal = null;
			}
		});
		mientras.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				WhileFigure While = new WhileFigure();
			    InstruccionSimple codigo = new InstruccionSimple();
				codigo.setInstruccionSimple("null");
				While.instruccion.instruccion.add(0,codigo);
			    Ventana.figuraPrincipal = null;
			    Ventana.figuraPrincipal = While;
			    insertarFigura(While);
			    Ventana.figuraPrincipal = null;
			}
		});
		menuDisponibleFigura();
	}
	public void menuDisponibleFigura(){
		if(selec.getFiguraSeleccionada() != -1){
			if(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()) instanceof CircleFigure){
				if(Ventana.diagramaEnMemoria.diagrama.size()!=0){
					pegar.setEnabled(true);
				}
				else{
					pegar.setEnabled(false);
				}
				nuevo.setEnabled(true);
				copiar.setEnabled(false);
				cortar.setEnabled(false);
				eliminar.setEnabled(false);
				agregar.setEnabled(false);
			}
			else{
				if(Ventana.diagramaEnMemoria.diagrama.size()==0){
					pegar.setEnabled(false);
				}
				else{
					pegar.setEnabled(true);
				}
				copiar.setEnabled(true);
				cortar.setEnabled(true);
				eliminar.setEnabled(true);
				agregar.setEnabled(true);
				nuevo.setEnabled(true);
			}
		}
		else{
			pegar.setEnabled(false);
			copiar.setEnabled(false);
			cortar.setEnabled(false);
			eliminar.setEnabled(false);
			agregar.setEnabled(false);
			nuevo.setEnabled(false);
		}
	}
	public void mousePressed(MouseEvent e){
		Ventana.menuEdicion.menuDisponibleFigura();
		final Figura fig = ((Figura) e.getSource());
		if(e.button == 3){
			Menu menu = new Menu(Ventana.shell,SWT.POP_UP);
			MenuItem cortar = new MenuItem(menu,SWT.CASCADE);
			cortar.setText("Cortar");
			MenuItem copiar = new MenuItem(menu,SWT.CASCADE);
			copiar.setText("Copiar");
			MenuItem pegar = new MenuItem(menu,SWT.CASCADE);
			pegar.setText("Pegar");
			pegar.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					Pegar(fig);
				}
			});
			MenuItem eliminar = new MenuItem(menu,SWT.CASCADE);
			eliminar.setText("Eliminar                  ");
			new MenuItem(menu,SWT.SEPARATOR);
			MenuItem agregar = new MenuItem(menu,SWT.CASCADE);
			agregar.setText("Agregar Codigo");
			menu.setVisible(true);
			cortar.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					Cortar(fig);
				}
			});
			copiar.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					Copiar(fig);
				}
			});
			eliminar.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					Eliminar(fig);
				}
			});
			agregar.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					agregar(fig);
				}
			});
			if(fig instanceof CircleFigure){
				if(Ventana.diagramaEnMemoria.diagrama.size()!=0){
					pegar.setEnabled(true);
				}
				else{
					pegar.setEnabled(false);
				}
				copiar.setEnabled(false);
				cortar.setEnabled(false);
				eliminar.setEnabled(false);
				agregar.setEnabled(false);
			}
			else{
				if(Ventana.diagramaEnMemoria.diagrama.size()==0){
					pegar.setEnabled(false);
				}
				else{
					pegar.setEnabled(true);
				}
				copiar.setEnabled(true);
				cortar.setEnabled(true);
				eliminar.setEnabled(true);
				agregar.setEnabled(true);
			}
			Menu menu2 = new Menu(menu); 
			MenuItem nuevo = new MenuItem(menu,SWT.CASCADE);
			nuevo.setText("Insertar Figura");
			nuevo.setMenu(menu2);
			MenuItem entrada = new MenuItem(menu2,SWT.CASCADE);
			entrada.setText("Entrada");
			MenuItem proceso = new MenuItem(menu2,SWT.CASCADE);
			proceso.setText("Proceso");
			MenuItem decision = new MenuItem(menu2,SWT.CASCADE);
			decision.setText("Decision");
			MenuItem mientras = new MenuItem(menu2,SWT.CASCADE);
			mientras.setText("Mientras");
			MenuItem para = new MenuItem(menu2,SWT.CASCADE);
			para.setText("Para");
			MenuItem salida = new MenuItem(menu2,SWT.CASCADE);
			salida.setText("Impresion");
			decision.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					DecisionFigure decision = new DecisionFigure();
				    InstruccionSimple codigo = new InstruccionSimple();
					codigo.setInstruccionSimple("null");
					decision.instruction.instruccion.add(0,codigo);
					Ventana.figuraPrincipal = null;
				    Ventana.figuraPrincipal = decision;
				    insertarFigura(decision);
				    Ventana.figuraPrincipal = null;
					
				}
			});
			proceso.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					 SentenceFigure proceso = new SentenceFigure();
					 InstruccionSimple codigo = new InstruccionSimple();
					 codigo.setInstruccionSimple("null");
					 proceso.instruccion.instruccion = "null";
					 Ventana.figuraPrincipal = null;
					 Ventana.figuraPrincipal = proceso;
					 insertarFigura(proceso);
					 Ventana.figuraPrincipal = null;
				}
			});
			entrada.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					 InputFigure entrada = new InputFigure();
					 InstruccionSimple codigo = new InstruccionSimple();
					 codigo.setInstruccionSimple("null");
					 entrada.instruccion.instruccion = "null";
					 Ventana.figuraPrincipal = null;
					 Ventana.figuraPrincipal = entrada;
					 insertarFigura(entrada);
					 Ventana.figuraPrincipal = null;
				}
			});
			salida.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					OutputFigure salida = new OutputFigure();
				    InstruccionSimple codigo = new InstruccionSimple();
					codigo.setInstruccionSimple("null");
					salida.instruccion.instruccion = "null";
				    Ventana.figuraPrincipal = null;
				    Ventana.figuraPrincipal = salida;
				    insertarFigura(salida);
				    Ventana.figuraPrincipal = null;
				}
			});
			para.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					ForFigure For = new ForFigure();
				    InstruccionSimple codigo = new InstruccionSimple();
				    codigo.setInstruccionSimple("null");
				    For.instruction.instruccion.add(0,codigo);
				    Ventana.figuraPrincipal = null;
				    Ventana.figuraPrincipal = For;
				    insertarFigura(For);
				    Ventana.figuraPrincipal = null;
				}
			});
			mientras.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					WhileFigure While = new WhileFigure();
				    InstruccionSimple codigo = new InstruccionSimple();
					codigo.setInstruccionSimple("null");
					While.instruccion.instruccion.add(0,codigo);
				    Ventana.figuraPrincipal = null;
				    Ventana.figuraPrincipal = While;
				    insertarFigura(While);
				    Ventana.figuraPrincipal = null;
				}
			});
		}
	}
	public static void Cortar(Figura fig){
		int index = 0;
		for(int x=0;x<tab.getHoja().getSizeDiagrama();x++){
			if(x == selec.getFiguraSeleccionada()){
				int y=0,x2=0,cont=0;
				Ventana.diagramaEnMemoria.diagrama.removeAllElements();
				if(fig instanceof DecisionFigure){
					y = recorridoCiclo(tab.getHoja().getDiagrama(),x);
					y = recorridoCiclo2(tab.getHoja().getDiagrama(),y);
					x2=y-x;
					while(cont<x2+2){
						Ventana.diagramaEnMemoria.diagrama.add(index,tab.getHoja().getFigureIndexOf(x));
						tab.getHoja().removeFigureIndexOf(x);
						cont++;
						index++;
					}
				} 
				else if(fig instanceof ForFigure){
					y = recorridoCiclo3(tab.getHoja().getDiagrama(),x);
					x2=y-x;
					while(cont<x2+6){
						Ventana.diagramaEnMemoria.diagrama.add(index,tab.getHoja().getFigureIndexOf(x));
						tab.getHoja().removeFigureIndexOf(x);
						cont++;
						index++;
					}
				}
				else if(fig instanceof WhileFigure){
					y = recorridoCiclo3(tab.getHoja().getDiagrama(),x);
					x2=y-x;
					while(cont<x2+6){
						Ventana.diagramaEnMemoria.diagrama.add(index,tab.getHoja().getFigureIndexOf(x));
						tab.getHoja().removeFigureIndexOf(x);
						cont++;
						index++;
					}
				}
				else{
					Ventana.diagramaEnMemoria.diagrama.add(index,tab.getHoja().getFigureIndexOf(x));
					tab.getHoja().removeFigureIndexOf(x);
				}
				Ventana.isCortar = true;
				selec.setFiguraSeleccionada(-1);
				Repintar();	
				break;
			}
		}
	}
	public static void Copiar(Figura fig){
		if(Ventana.isCortar){
			Ventana.diagramaEnMemoria.diagrama.removeAllElements();
			Ventana.isCortar = false;
		}
		int index = 0;
		for(int x=0;x<tab.getHoja().getSizeDiagrama();x++){
			if(x == selec.getFiguraSeleccionada()){
				int y=0,x2=0,cont=0;
				Ventana.diagramaEnMemoria.diagrama.removeAllElements();
				if(fig instanceof DecisionFigure){
					y = recorridoCiclo(tab.getHoja().getDiagrama(),x);
					y = recorridoCiclo2(tab.getHoja().getDiagrama(),y);
					x2=y-x;
					while(cont<x2+2){
						if(tab.getHoja().getFigureIndexOf(x) instanceof DecisionFigure){
							DecisionFigure copia = new DecisionFigure();
							DecisionFigure actual = (DecisionFigure)tab.getHoja().getFigureIndexOf(x);
							for(int j=0; j<actual.instruction.instruccion.size(); j++){
								copia.instruction.instruccion.add(actual.instruction.instruccion.elementAt(j));	
							}
							Ventana.diagramaEnMemoria.diagrama.add(index,copia);
						}
						else if(tab.getHoja().getFigureIndexOf(x) instanceof ForFigure){
							ForFigure copia = new ForFigure();
							ForFigure actual = (ForFigure)tab.getHoja().getFigureIndexOf(x);
							for(int j=0; j<actual.instruction.instruccion.size(); j++){
								copia.instruction.instruccion.add(actual.instruction.instruccion.elementAt(j));	
							}
							Ventana.diagramaEnMemoria.diagrama.add(index,copia);
						}
						else if(tab.getHoja().getFigureIndexOf(x) instanceof WhileFigure){
							WhileFigure copia = new WhileFigure();
							WhileFigure actual = (WhileFigure)tab.getHoja().getFigureIndexOf(x);
							for(int j=0; j<actual.instruccion.instruccion.size(); j++){
								copia.instruccion.instruccion.add(actual.instruccion.instruccion.elementAt(j));	
							}
							Ventana.diagramaEnMemoria.diagrama.add(index,copia);
						}
						else if(tab.getHoja().getFigureIndexOf(x) instanceof SentenceFigure){
							SentenceFigure copia = new SentenceFigure();
							SentenceFigure actual = (SentenceFigure)tab.getHoja().getFigureIndexOf(x);
							copia.instruccion.instruccion = actual.instruccion.instruccion;
							Ventana.diagramaEnMemoria.diagrama.add(index,copia);
						}
						else if(tab.getHoja().getFigureIndexOf(x) instanceof InputFigure){
							InputFigure copia = new InputFigure();
							InputFigure actual = (InputFigure)tab.getHoja().getFigureIndexOf(x);
							copia.instruccion.instruccion = actual.instruccion.instruccion;
							Ventana.diagramaEnMemoria.diagrama.add(index,copia);
						}
						else if(tab.getHoja().getFigureIndexOf(x) instanceof OutputFigure){
							OutputFigure copia = new OutputFigure();
							OutputFigure actual = (OutputFigure)tab.getHoja().getFigureIndexOf(x);
							copia.instruccion.instruccion = actual.instruccion.instruccion;
							Ventana.diagramaEnMemoria.diagrama.add(index,copia);
						}
						else{
							Ventana.diagramaEnMemoria.diagrama.add(index,tab.getHoja().getFigureIndexOf(x));
						}
						cont++;
						index++;
						x++;
					}
				} 
				else if(fig instanceof ForFigure || fig instanceof WhileFigure){
					y = recorridoCiclo3(tab.getHoja().getDiagrama(),x);
					x2=y-x;
					while(cont<x2+6){
						if(tab.getHoja().getFigureIndexOf(x) instanceof DecisionFigure){
							DecisionFigure copia = new DecisionFigure();
							DecisionFigure actual = (DecisionFigure)tab.getHoja().getFigureIndexOf(x);
							for(int j=0; j<actual.instruction.instruccion.size(); j++){
								copia.instruction.instruccion.add(actual.instruction.instruccion.elementAt(j));	
							}
							Ventana.diagramaEnMemoria.diagrama.add(index,copia);
						}
						else if(tab.getHoja().getFigureIndexOf(x) instanceof ForFigure){
							ForFigure copia = new ForFigure();
							ForFigure actual = (ForFigure)tab.getHoja().getFigureIndexOf(x);
							for(int j=0; j<actual.instruction.instruccion.size(); j++){
								copia.instruction.instruccion.add(actual.instruction.instruccion.elementAt(j));	
							}
							Ventana.diagramaEnMemoria.diagrama.add(index,copia);
						}
						else if(tab.getHoja().getFigureIndexOf(x) instanceof WhileFigure){
							WhileFigure copia = new WhileFigure();
							WhileFigure actual = (WhileFigure)tab.getHoja().getFigureIndexOf(x);
							for(int j=0; j<actual.instruccion.instruccion.size(); j++){
								copia.instruccion.instruccion.add(actual.instruccion.instruccion.elementAt(j));	
							}
							Ventana.diagramaEnMemoria.diagrama.add(index,copia);
						}
						else if(tab.getHoja().getFigureIndexOf(x) instanceof SentenceFigure){
							SentenceFigure copia = new SentenceFigure();
							SentenceFigure actual = (SentenceFigure)tab.getHoja().getFigureIndexOf(x);
							copia.instruccion.instruccion = actual.instruccion.instruccion;
							Ventana.diagramaEnMemoria.diagrama.add(index,copia);
						}
						else if(tab.getHoja().getFigureIndexOf(x) instanceof InputFigure){
							InputFigure copia = new InputFigure();
							InputFigure actual = (InputFigure)tab.getHoja().getFigureIndexOf(x);
							copia.instruccion.instruccion = actual.instruccion.instruccion;
							Ventana.diagramaEnMemoria.diagrama.add(index,copia);
						}
						else if(tab.getHoja().getFigureIndexOf(x) instanceof OutputFigure){
							OutputFigure copia = new OutputFigure();
							OutputFigure actual = (OutputFigure)tab.getHoja().getFigureIndexOf(x);
							copia.instruccion.instruccion = actual.instruccion.instruccion;
							Ventana.diagramaEnMemoria.diagrama.add(index,copia);
						}
						else{
							Ventana.diagramaEnMemoria.diagrama.add(index,tab.getHoja().getFigureIndexOf(x));
						}
						cont++;
						index++;
						x++;
					}
				}
				else{
					if(tab.getHoja().getFigureIndexOf(x) instanceof InputFigure){
						InputFigure figura = new InputFigure();
						InputFigure figura2 = (InputFigure)tab.getHoja().getFigureIndexOf( x );
						figura.instruccion.instruccion = figura2.instruccion.instruccion;
						Ventana.diagramaEnMemoria.diagrama.add(index,figura);
					}
					else if(tab.getHoja().getFigureIndexOf(x) instanceof OutputFigure){
						OutputFigure figura = new OutputFigure();
						OutputFigure figura2 = (OutputFigure)tab.getHoja().getFigureIndexOf(x );
						figura.instruccion.instruccion = figura2.instruccion.instruccion;
						Ventana.diagramaEnMemoria.diagrama.add(index,figura);

					}
					else{
						SentenceFigure figura = new SentenceFigure();
						SentenceFigure figura2 = (SentenceFigure)tab.getHoja().getFigureIndexOf( x);
						figura.instruccion.instruccion = figura2.instruccion.instruccion;
						Ventana.diagramaEnMemoria.diagrama.add(index,figura);
					}
				}
				break;
			}
		}
	}
	public static void Pegar(Figura fig){
		int w = 0, y=0 ,lim=0;
		final Shell shell = new Shell(Ventana.shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		if(!Ventana.isCortar){
			for(int i=0; i<Ventana.diagramaEnMemoria.diagrama.size(); i++){
				if(Ventana.diagramaEnMemoria.diagrama.elementAt(i) instanceof Elipse){
					Elipse nueva = new Elipse();
					Ventana.diagramaEnMemoria.diagrama.remove(i);
					Ventana.diagramaEnMemoria.diagrama.insertElementAt(nueva,i);
				}
				else if(Ventana.diagramaEnMemoria.diagrama.elementAt(i) instanceof DecisionFigureEnd){
					DecisionFigureEnd nueva = new DecisionFigureEnd();
					Ventana.diagramaEnMemoria.diagrama.remove(i);
					Ventana.diagramaEnMemoria.diagrama.insertElementAt(nueva,i);
				}
				else if(Ventana.diagramaEnMemoria.diagrama.elementAt(i) instanceof DecisionFigure){
					DecisionFigure nueva = new DecisionFigure();
					DecisionFigure aux = (DecisionFigure)Ventana.diagramaEnMemoria.diagrama.elementAt(i);
					for(int x=0;x<aux.instruction.instruccion.size(); x++){
						nueva.instruction.instruccion.add(x,aux.instruction.instruccion.elementAt(x));
					}
					Ventana.diagramaEnMemoria.diagrama.remove(i);
					Ventana.diagramaEnMemoria.diagrama.insertElementAt(nueva,i);
				}
				else if(Ventana.diagramaEnMemoria.diagrama.elementAt(i) instanceof ForFigure){
					ForFigure nueva = new ForFigure();
					ForFigure aux = (ForFigure)Ventana.diagramaEnMemoria.diagrama.elementAt(i);
					for(int x=0;x<aux.instruction.instruccion.size(); x++){
						nueva.instruction.instruccion.add(x,aux.instruction.instruccion.elementAt(x));
					}
					Ventana.diagramaEnMemoria.diagrama.remove(i);
					Ventana.diagramaEnMemoria.diagrama.insertElementAt(nueva,i);
				}
				else if(Ventana.diagramaEnMemoria.diagrama.elementAt(i) instanceof WhileFigure){
					WhileFigure nueva = new WhileFigure();
					WhileFigure aux = (WhileFigure)Ventana.diagramaEnMemoria.diagrama.elementAt(i);
					for(int x=0;x<aux.instruccion.instruccion.size(); x++){
						nueva.instruccion.instruccion.add(x,aux.instruccion.instruccion.elementAt(x));
					}
					Ventana.diagramaEnMemoria.diagrama.remove(i);
					Ventana.diagramaEnMemoria.diagrama.insertElementAt(nueva,i);
				}
				else if(Ventana.diagramaEnMemoria.diagrama.elementAt(i) instanceof OutputFigure){
					OutputFigure nueva = new OutputFigure();
					OutputFigure aux = (OutputFigure)Ventana.diagramaEnMemoria.diagrama.elementAt(i);
					nueva.instruccion.instruccion=aux.instruccion.instruccion;
					Ventana.diagramaEnMemoria.diagrama.remove(i);
					Ventana.diagramaEnMemoria.diagrama.insertElementAt(nueva,i);
				}
				else if(Ventana.diagramaEnMemoria.diagrama.elementAt(i) instanceof InputFigure){
					InputFigure nueva = new InputFigure();
					InputFigure aux = (InputFigure)Ventana.diagramaEnMemoria.diagrama.elementAt(i);
					nueva.instruccion.instruccion=aux.instruccion.instruccion;
					Ventana.diagramaEnMemoria.diagrama.remove(i);
					Ventana.diagramaEnMemoria.diagrama.insertElementAt(nueva,i);
				}
				else if(Ventana.diagramaEnMemoria.diagrama.elementAt(i) instanceof SentenceFigure){
					SentenceFigure nueva = new SentenceFigure();
					SentenceFigure aux = (SentenceFigure)Ventana.diagramaEnMemoria.diagrama.elementAt(i);
					nueva.instruccion.instruccion=aux.instruccion.instruccion;
					Ventana.diagramaEnMemoria.diagrama.remove(i);
					Ventana.diagramaEnMemoria.diagrama.insertElementAt(nueva,i);
				}
			}
		}
		Vector<Figura> temporal = new Vector<Figura>();
		for(int x=0;x<tab.getHoja().getSizeDiagrama();x++){
			if(x == selec.getFiguraSeleccionada()){
				if(tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()) instanceof DecisionFigure){
					shell.setBounds(315, 260, 300, 140);
					Button izquierda = new Button(shell,SWT.PUSH);
					izquierda.setBounds(5, 75, 75, 25);
					izquierda.setText("Izquierda");
					Button derecha = new Button(shell,SWT.PUSH);
					derecha.setBounds(215, 75, 75, 25);
					derecha.setText("Derecha");
					Button cancelar = new Button(shell,SWT.PUSH);
					cancelar.setBounds(110, 75,75, 25);
					cancelar.setText("Cancelar");
					Label pregunta = new Label(shell, SWT.NONE); 
					FontData fontData = new FontData();
					fontData.setHeight(11);
					Font newFont = new Font(Ventana.display, fontData);
					pregunta.setBounds(85, 30, 340, 30);
					pregunta.setText("¿De que lado deseas pegar?");
					pregunta.setFont(newFont);
					Label imagen = new Label(shell, SWT.NONE); 
					//Image question = new Image(Ventana.display,"imagenes\\Pregunta.PNG");
					imagen.setImage(ImageLoader.getImage("Pregunta.png"));
					imagen.setBounds(25,10,50,50);
					shell.setMaximized(false);
					shell.setMinimized(false);
					shell.open();
					final int copiax=x;
					izquierda.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent event) {
							Vector<Figura> temporal = new Vector<Figura>();
							int x = copiax,y =0,lim=0;
							for(int u =0; u<tab.getHoja().getSizeDiagrama(); u++){
								temporal.add(u,tab.getHoja().getFigureIndexOf(u));
							}
							//*******************************************
							int posicion = tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()).getBounds().x+tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()).getBounds().width;
							posicion = tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()+1).getBounds().x-posicion;
							//*******************************************
							tab.getHoja().removeFigureAllElements();
							for(int i =0; i<=x; i++){
								tab.getHoja().addFigureIndexOf(i,temporal.elementAt(i));
								y = i;
							 }
							y++;
							int distancia =  temporal.elementAt(y-1).getBounds().x + temporal.elementAt(y-1).getBounds().height;
							distancia =  temporal.elementAt(y).getBounds().x - distancia;
							int cont=0, punto=0;
							for(int i =selec.getFiguraSeleccionada(); cont<2; i++){
									if(tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()).getBounds().x - temporal.elementAt(i).getBounds().x == posicion){
										cont++;
									}
									punto = i;
							}
							for(int i =y; i<punto; i++){
								tab.getHoja().insertFigureIndexOf(temporal.elementAt(i), i);
								y = i;
							}
							int j =0;
							//este es el que se debe de seleccionar punto
							selec.setFiguraSeleccionada(punto);
							for(int i =punto; j<Ventana.diagramaEnMemoria.diagrama.size(); i++){
								tab.getHoja().insertFigureIndexOf(Ventana.diagramaEnMemoria.diagrama.elementAt(j), i);
								j++;
								y = i;
							}
							y++;
							lim =temporal.size()-punto;
							j=0;
							for(int i =y; j<lim; i++){
								tab.getHoja().insertFigureIndexOf(temporal.elementAt(punto), i);
								j++;
								punto++;
							}
							if(Ventana.isCortar){
								Ventana.diagramaEnMemoria.diagrama.removeAllElements();
								Ventana.isCortar = false;
							}
							shell.close();
							Repintar();
							
						}
					});
					derecha.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent event) {
							Vector<Figura> temporal = new Vector<Figura>();
							int x = copiax,y =0,lim=0,w=0;
							for(int u =0; u<tab.getHoja().getSizeDiagrama(); u++){
								temporal.add(u,tab.getHoja().getFigureIndexOf(u));
							}
							tab.getHoja().removeFigureAllElements();
							for(int i =0; i<=x; i++){
								tab.getHoja().addFigureIndexOf(i,temporal.elementAt(i));
								y = i;
							 }
								y++;
								tab.getHoja().insertFigureIndexOf(temporal.elementAt(y), y);
							y=0;
							lim =temporal.size()-x-1;
							w=x+1;
							//este es el que se debe de seleccionar w
							selec.setFiguraSeleccionada(w+1);
							for(int i =w; i<x+1+Ventana.diagramaEnMemoria.diagrama.size(); i++){
								tab.getHoja().addFigureIndexOf(i+1,Ventana.diagramaEnMemoria.diagrama.elementAt(y));
								y++;
								w++;
							}
							y = 0;
							x++;
							for(int i =w; y<lim-1; i++){
								tab.getHoja().addFigureIndexOf(i+1,temporal.elementAt(x+1));
								y++;
								x++;
							}
							if(Ventana.isCortar){
								Ventana.diagramaEnMemoria.diagrama.removeAllElements();
								Ventana.isCortar = false;
							}
							shell.close();
							Repintar();
						}
					});
					cancelar.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent event) {
							shell.close();
						}
					});
				}
				else{
					for(int u =0; u<tab.getHoja().getSizeDiagrama(); u++){
						temporal.add(u,tab.getHoja().getFigureIndexOf(u));
					}
					tab.getHoja().removeFigureAllElements();
					for(int i =0; i<=x; i++){
						tab.getHoja().addFigureIndexOf(i,temporal.elementAt(i));
						y = i;
					}
					y++;
					y=0;
					lim =temporal.size()-x-1;
					w=x+1;
					//esta es la que se debe de seleccionar w
					selec.setFiguraSeleccionada(w);
					for(int i =w; i<x+1+Ventana.diagramaEnMemoria.diagrama.size(); i++){
						tab.getHoja().addFigureIndexOf(i,Ventana.diagramaEnMemoria.diagrama.elementAt(y));
						y++;
						w++;
					}
					y = 0;
					x++;
					for(int i =w; y<lim; i++){
						tab.getHoja().addFigureIndexOf(i,temporal.elementAt(x));
						y++;
						x++;
					}
					if(Ventana.isCortar){
						Ventana.diagramaEnMemoria.diagrama.removeAllElements();
						Ventana.isCortar = false;
					}
					Repintar();
				}
			}
		}
	}
	public static void insertarFigura(final Figura inser){
		final int i = selec.getFiguraSeleccionada();
		if(tab.getHoja().getAdminDiagrama().diagrama.elementAt(i) instanceof DecisionFigure){
			final Shell shell = new Shell(Ventana.shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL); 
			shell.setBounds(315, 260, 400, 140);
			Button izquierda = new Button(shell,SWT.PUSH);
			izquierda.setBounds(5, 75, 75, 25);
			izquierda.setText("Izquierda");
			Button derecha = new Button(shell,SWT.PUSH);
			derecha.setBounds(215, 75, 75, 25);
			derecha.setText("Derecha");
			Button cancelar = new Button(shell,SWT.PUSH);
			cancelar.setBounds(110, 75,75, 25);
			cancelar.setText("Cancelar");
			Label pregunta = new Label(shell, SWT.NONE); 
			FontData fontData = new FontData();
			fontData.setHeight(11);
			Font newFont = new Font(Ventana.display, fontData);
			pregunta.setBounds(85, 30, 340, 30);
			pregunta.setText("¿De que lado deseas insertar la nueva figura?");
			pregunta.setFont(newFont);
			Label imagen = new Label(shell, SWT.NONE); 
			//Image question = new Image(Ventana.display,CargarImagenes.getImagen("Pregunta.PNG"));
			imagen.setImage(ImageLoader.getImage("Pregunta.png"));
			imagen.setBounds(25,10,50,50);
			shell.setMaximized(false);
			shell.setMinimized(false);
			shell.open();
			izquierda.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					int distan = tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()+1).getBounds().x-(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()).getBounds().x+
					tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()).getBounds().width);
					for(int j=selec.getFiguraSeleccionada()+1; j<tab.getHoja().getSizeDiagrama(); j++){
						if(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()).getBounds().x-tab.getHoja().getDiagrama().elementAt(j).getBounds().x == distan){
							tab.getHoja().getAdminDiagrama().ordenar(j, inser);
							selec.setFiguraSeleccionada(j+1);
							tab.getHoja().addFigure();
							break;
						}
					}
					shell.close();
					Repintar();
					
				}
			});
			derecha.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					tab.getHoja().getAdminDiagrama().ordenar(i+1, inser);
					selec.setFiguraSeleccionada(i+2);
					tab.getHoja().addFigure();
					shell.close();
					Repintar();
				}
			});
			cancelar.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					shell.close();
				}
			});
		}
		else{
			tab.getHoja().getAdminDiagrama().ordenar(i, inser);
			tab.getHoja().addFigure();
			tab.getHoja().guardarRetroceso();
			tab.getTabItem().getSave().setSave(false);
		}
	}
	public static void Eliminar(Figura fig){
		String tipo ="";
		for(int x=0;x<tab.getHoja().getSizeDiagrama();x++){
			if(x == selec.getFiguraSeleccionada()){
				int y=0,x2=0,cont=0;
				if(fig instanceof DecisionFigure){
					tipo="si";
					y = recorridoCiclo(tab.getHoja().getDiagrama(),x);
					y = recorridoCiclo2(tab.getHoja().getDiagrama(),y);
					x2=y-x;
					while(cont<x2+2){
						tab.getHoja().removeFigureIndexOf(x);
						cont++;
					}
				} 
				else if(fig instanceof ForFigure){
					tipo="para";
					y = recorridoCiclo3(tab.getHoja().getDiagrama(),x);
					x2=y-x;
					while(cont<x2+6){
						tab.getHoja().removeFigureIndexOf(x);
						cont++;
					}
				}
				else if(fig instanceof WhileFigure){
					tipo="mientras";
					y = recorridoCiclo3(tab.getHoja().getDiagrama(),x);
					x2=y-x;
					while(cont<x2+6){
						tab.getHoja().removeFigureIndexOf(x);
						cont++;
					}
				}
				else{
					tab.getHoja().removeFigureIndexOf(x);
					if(fig instanceof OutputFigure){
						tipo="Salida";
					}
					else if(fig instanceof InputFigure){
						tipo="Entrada";
					}
					else{
						tipo="Proceso";
					}
				}
				tab.getTabItem().getInfo().addInformation("/D - Se elimino una figura de tipo \" "+tipo+"\"");
				tab.getTabItem().getInfo().setDiagrama(tab.getHoja().getDiagrama());
				selec.setFiguraSeleccionada(-1);
				Repintar();
				break;
			}
		}
	}
	public void agregar(Figura fig){
		if(fig instanceof SentenceFigure){
			SentenceFigure f = (SentenceFigure)fig;
			new SentenceFigureDialog(Ventana.shell,tab,f,selec).open();
		}
		else if(fig instanceof DecisionFigure){
			DecisionFigure f = ((DecisionFigure)fig);
			new DecisionFigureDialog(Ventana.shell,tab,f,selec).open();
		}
		else if(fig instanceof InputFigure){
			InputFigure f = ((InputFigure)fig);
			new InputFigureDialog(Ventana.shell,tab,f,selec).open();
		}
		else if(fig instanceof OutputFigure){
			OutputFigure f = ((OutputFigure)fig);
			new OutputFigureDialog(Ventana.shell,tab,f,selec).open();
		}
		else if(fig instanceof ForFigure){
			ForFigure f = ((ForFigure)fig);
			new ForFigureDialog(Ventana.shell,tab,f,selec).open();
		}
		else if(fig instanceof WhileFigure){
			WhileFigure f = ((WhileFigure)fig);
			new WhileFigureDialog(Ventana.shell,tab,f,selec).open();
		}
	}
	public static void Repintar(){
		//selec.setFiguraSeleccionada(-1);
		tab.getTabItem().getSave().setSave(false);
		tab.getHoja().addFigure();
		tab.getHoja().guardarRetroceso();
	}
	/**
	 * 
	 * Este metodo recibe un if y devuelve la
	 * localiozacion de la parte no del if.
	 * 
	 * @param diagrama
	 * @param i
	 * @return int
	 */
	public static int recorridoCiclo(Vector<Figura> diagrama,int i){
		int x=diagrama.elementAt(i+1).getBounds().x-(diagrama.elementAt(i).getBounds().x+diagrama.elementAt(i).getBounds().width);
		x=diagrama.elementAt(i).getBounds().x-x;
		int y=diagrama.elementAt(i).getBounds().y+diagrama.elementAt(i).getBounds().height/2;
		i++;
		while(true){
			if(diagrama.elementAt(i) instanceof Elipse && diagrama.elementAt(i).getBounds().x==x && diagrama.elementAt(i).getBounds().y==y){
				break;
			}
			i++;
		}
		return i;
	}
	/**
	 * 
	 * Este metodo recibe la localizacion 
	 * del principio de la parte No del if
	 * y te devuelve la localizacion del final
	 * de dicha parte.
	 * 
	 * @param diagrama
	 * @param i
	 * @return int
	 */
	public static int recorridoCiclo2(Vector<Figura> diagrama,int i){
		int x=diagrama.elementAt(i).getBounds().x;
		int y=diagrama.elementAt(i-1).getBounds().y;
		i++;
		while(true){
			if(diagrama.elementAt(i) instanceof Elipse && diagrama.elementAt(i).getBounds().x==x && diagrama.elementAt(i).getBounds().y==y){
				break;
			}
			i++;
		}
		return i;
	}
	/**
	 * 
	 * Este metodo recibe un For o While
	 * y te devuelve la localizacion del 
	 * final de dicha figura.
	 * 
	 * @param diagrama
	 * @param i
	 * @return int
	 */
	public static int recorridoCiclo3(Vector<Figura> diagrama,int i){
        int x=diagrama.elementAt(i).getBounds().x + diagrama.elementAt(i).getBounds().width/2;
        int y=diagrama.elementAt(i).getBounds().y + diagrama.elementAt(i).getBounds().height/2;;
        i++;
        while(true){
            if(diagrama.elementAt(i) instanceof Elipse && diagrama.elementAt(i).getBounds().x==x
                    && diagrama.elementAt(i+1) instanceof Elipse && diagrama.elementAt(i+2) instanceof Elipse
                    && diagrama.elementAt(i+1).getBounds().y == diagrama.elementAt(i).getBounds().y
                    && diagrama.elementAt(i+2).getBounds().y == y ){
                break;
            }
            i++;
        }
        return i;
    }
}