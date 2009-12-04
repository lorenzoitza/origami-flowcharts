package ui.events;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import Administracion.AdminSeleccion;
import Administracion.Figura;
import Administracion.TabFolder;
import Administracion.Eventos.EventoMenuContextual;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.CustomeMenu;
import Grafico.MainWindow;
import Grafico.Figuras.CircleFigure;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;


public class ContextualMenuEvent  extends MouseListener.Stub{
	public static Figura fig;
	public static AdminSeleccion selec;
	public static TabFolder tab;
	private MenuItem cortar;
	private MenuItem copiar;
	private MenuItem pegar;
	private MenuItem agregar;
	private MenuItem eliminar;
	private MenuItem nuevo;
	private EventoMenuContextual ev;
	/**
	 * Da la propiedad de eliminar 
	 * a la figura recibida.
	 * @param figure
	 */
	public ContextualMenuEvent(){
		//MainWindow.first=true; //eliminado agregado una llamada al metodo mouseReleased() en la clase EventoCambiarCursor
	}
	public ContextualMenuEvent(Figura figure,TabFolder tabfolder,AdminSeleccion seleccion){
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
			    ev.Pegar(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()));
			}
		});
		cortar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			    ev.Cortar(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()));
			}
		});
		copiar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			    ev.Copiar(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()));
			}
		});
		eliminar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			    ev.Eliminar(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()));
			}
		});
		agregar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			    ev.agregar(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()));
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
				MainWindow.mainFigure = null;
			    MainWindow.mainFigure = decision;
			    ev.insertarFigura(decision);
			    MainWindow.mainFigure = null;
				
			}
		});
		proceso.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				 SentenceFigure proceso = new SentenceFigure();
				 InstruccionSimple codigo = new InstruccionSimple();
				 codigo.setInstruccionSimple("null");
				 proceso.instruccion.instruccion = "null";
				 MainWindow.mainFigure = null;
				 MainWindow.mainFigure = proceso;
				 ev.insertarFigura(proceso);
				 MainWindow.mainFigure = null;
			}
		});
		entrada.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				 InputFigure entrada = new InputFigure();
				 InstruccionSimple codigo = new InstruccionSimple();
				 codigo.setInstruccionSimple("null");
				 entrada.instruction.instruccion = "null";
				 MainWindow.mainFigure = null;
				 MainWindow.mainFigure = entrada;
				 ev.insertarFigura(entrada);
				 MainWindow.mainFigure = null;
			}
		});
		salida.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				OutputFigure salida = new OutputFigure();
			    InstruccionSimple codigo = new InstruccionSimple();
				codigo.setInstruccionSimple("null");
				salida.instruction.instruccion = "null";
			    MainWindow.mainFigure = null;
			    MainWindow.mainFigure = salida;
			    ev.insertarFigura(salida);
			    MainWindow.mainFigure = null;
			}
		});
		para.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ForFigure For = new ForFigure();
			    InstruccionSimple codigo = new InstruccionSimple();
			    codigo.setInstruccionSimple("null");
			    For.instruction.instruccion.add(0,codigo);
			    MainWindow.mainFigure = null;
			    MainWindow.mainFigure = For;
			    ev.insertarFigura(For);
			    MainWindow.mainFigure = null;
			}
		});
		mientras.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				WhileFigure While = new WhileFigure();
			    InstruccionSimple codigo = new InstruccionSimple();
				codigo.setInstruccionSimple("null");
				While.instruccion.instruccion.add(0,codigo);
			    MainWindow.mainFigure = null;
			    MainWindow.mainFigure = While;
			    ev.insertarFigura(While);
			    MainWindow.mainFigure = null;
			}
		});
		menuDisponibleFigura();
	}
	public void menuDisponibleFigura(){
		if(selec.getFiguraSeleccionada() != -1){
			if(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()) instanceof CircleFigure){
				if(MainWindow._diagramAdministrator.diagrama.size()!=0){
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
				if(MainWindow._diagramAdministrator.diagrama.size()==0){
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
	    CustomeMenu._editMenu.menuDisponibleFigura();
		final Figura fig = ((Figura) e.getSource());
		if(e.button == 3){
			Menu menu = new Menu(MainWindow.shell,SWT.POP_UP);
			MenuItem cortar = new MenuItem(menu,SWT.CASCADE);
			cortar.setText("Cortar");
			MenuItem copiar = new MenuItem(menu,SWT.CASCADE);
			copiar.setText("Copiar");
			MenuItem pegar = new MenuItem(menu,SWT.CASCADE);
			pegar.setText("Pegar");
			pegar.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
				    ev.Pegar(fig);
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
				    ev.Cortar(fig);
				}
			});
			copiar.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
				    ev.Copiar(fig);
				}
			});
			eliminar.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
				    ev.Eliminar(fig);
				}
			});
			agregar.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
				    ev.agregar(fig);
				}
			});
			if(fig instanceof CircleFigure){
				if(MainWindow._diagramAdministrator.diagrama.size()!=0){
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
				if(MainWindow._diagramAdministrator.diagrama.size()==0){
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
					MainWindow.mainFigure = null;
				    MainWindow.mainFigure = decision;
				    ev.insertarFigura(decision);
				    MainWindow.mainFigure = null;
					
				}
			});
			proceso.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					 SentenceFigure proceso = new SentenceFigure();
					 InstruccionSimple codigo = new InstruccionSimple();
					 codigo.setInstruccionSimple("null");
					 proceso.instruccion.instruccion = "null";
					 MainWindow.mainFigure = null;
					 MainWindow.mainFigure = proceso;
					 ev.insertarFigura(proceso);
					 MainWindow.mainFigure = null;
				}
			});
			entrada.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					 InputFigure entrada = new InputFigure();
					 InstruccionSimple codigo = new InstruccionSimple();
					 codigo.setInstruccionSimple("null");
					 entrada.instruction.instruccion = "null";
					 MainWindow.mainFigure = null;
					 MainWindow.mainFigure = entrada;
					 ev.insertarFigura(entrada);
					 MainWindow.mainFigure = null;
				}
			});
			salida.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					OutputFigure salida = new OutputFigure();
				    InstruccionSimple codigo = new InstruccionSimple();
					codigo.setInstruccionSimple("null");
					salida.instruction.instruccion = "null";
				    MainWindow.mainFigure = null;
				    MainWindow.mainFigure = salida;
				    ev.insertarFigura(salida);
				    MainWindow.mainFigure = null;
				}
			});
			para.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					ForFigure For = new ForFigure();
				    InstruccionSimple codigo = new InstruccionSimple();
				    codigo.setInstruccionSimple("null");
				    For.instruction.instruccion.add(0,codigo);
				    MainWindow.mainFigure = null;
				    MainWindow.mainFigure = For;
				    ev.insertarFigura(For);
				    MainWindow.mainFigure = null;
				}
			});
			mientras.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					WhileFigure While = new WhileFigure();
				    InstruccionSimple codigo = new InstruccionSimple();
					codigo.setInstruccionSimple("null");
					While.instruccion.instruccion.add(0,codigo);
				    MainWindow.mainFigure = null;
				    MainWindow.mainFigure = While;
				    ev.insertarFigura(While);
				    MainWindow.mainFigure = null;
				}
			});
		}
	}
}
