package Grafico;

import java.io.File;
import java.io.InputStream;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;

import ui.actions.ExportToCAction;
import ui.actions.ExportToCPPAction;
import ui.actions.ExportToEXEAction;
import ui.actions.ExportToImageAction;
import ui.actions.NewDiagramAction;
import ui.actions.OpenDiagramAction;
import ui.actions.SaveDiagramAction;
import ui.actions.SaveDiagramAsAction;
import Administracion.*;
import Administracion.Eventos.EventoKey;
import Administracion.Eventos.EventoMenuContextual;
import Administracion.Eventos.EventoVentana;
import Administracion.Funcionalidad.Compilar;
import Administracion.Funcionalidad.Exportar;
import Administracion.Funcionalidad.Serializar;
import Administracion.Funcionalidad.Codigo.Instruccion;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.Figuras.SelectionSquare;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.CircleFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;
import Grafico.Help.AboutWindow;
import Grafico.Help.HelpWindow;
import Imagenes.ImageLoader;

/**
 * Esta clase es la interfaz y crea la ventana principal
 * asi como sus componentes.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez benjamin chuy
 */
public class MainWindow{
	public static Display _display = new Display();
	public static Shell _shell = new Shell(_display);
	public static AdminDiagrama _diagramAdministrator; //TODO: Cambiar la clase a DiagramAdministrator
	public static Figura _mainFigure = null;
	private static Serializar _serializer = new Serializar();
	public static boolean bandera = false; //TODO: ÀPara que sirve esta bandera?
	public static boolean isCut = false;
	public static Vector<SelectionSquare> seleccion = new Vector<SelectionSquare>();
	public static AdminSeleccion _selectionAdministrator = new AdminSeleccion();  //TODO: Cambiar la clase a SelectionAdministrator
	public static TabFolder _diagrams;
	public static EventoKey _keyEvent;
	public static EventoMenuContextual _editMenu;
	public static boolean first = false; //TODO: À?
	public static boolean dispToolItem = false; //TODO: À?
	public static Componentes _components;
	public static MenuItem consoleMenuItem;
	public static MenuItem figuresBarMenuItem;
	public static MenuItem ifMenuItem;
	public static MenuItem sentenceMenuItem;
	public static MenuItem inputMenuItem;
	public static MenuItem outputMenuItem;
	public static MenuItem forMenuItem;
	public static MenuItem whileMenuItem;
	public static MenuItem exportMenuItem;
	public static MenuItem resetDiagramMenuItem;
	public static MenuItem stepByStepMenuItem;
	public static MenuItem compileMenuItem;
	public static MenuItem saveMenuItem;
	public static MenuItem saveAsMenuItem;
	public static MenuItem buildCodeMenuItem;
	private Menu mainMenu;
	
	private void initFileMenu(){
		MenuItem fileMenuItem = new MenuItem(mainMenu,SWT.CASCADE);
		fileMenuItem.setText("Archivo ");
		Menu fileMenu = new Menu(_shell, SWT.DROP_DOWN);
		fileMenuItem.setMenu(fileMenu);
		MenuItem openMenuItem = new MenuItem(fileMenu,SWT.PUSH);
		openMenuItem.setText("Abrir...                      Ctrl+A");	
		saveMenuItem = new MenuItem(fileMenu,SWT.PUSH);
		saveMenuItem.setText("Guardar                     Ctrl+G");
		saveAsMenuItem = new MenuItem(fileMenu,SWT.PUSH);
		saveAsMenuItem.setText("Guardar como...                 ");	
		MenuItem newDiagramMenuItem = new MenuItem(fileMenu,SWT.PUSH);
		newDiagramMenuItem.setText("Nuevo Diagrama        Ctrl+N");
		new MenuItem(fileMenu,SWT.SEPARATOR);
		Menu exportMenu = new Menu(fileMenu); 
		exportMenuItem = new MenuItem(fileMenu,SWT.CASCADE);
		exportMenuItem.setText("Exportar");
		exportMenuItem.setMenu(exportMenu);
		MenuItem exportToCMenuItem = new MenuItem(exportMenu,SWT.CASCADE);
		exportToCMenuItem.setText("Codigo C");
		MenuItem exportToCPPMenuItem = new MenuItem(exportMenu,SWT.CASCADE);
		exportToCPPMenuItem.setText("Codigo C++");
		MenuItem exportToEXEMenuItem = new MenuItem(exportMenu,SWT.CASCADE);
		exportToEXEMenuItem.setText("Ejecutable");
		MenuItem exportToImageMenuItem = new MenuItem(exportMenu,SWT.CASCADE);
		exportToImageMenuItem.setText("Imagen JPG");
		new MenuItem(fileMenu,SWT.SEPARATOR);
		MenuItem exitMenuItem = new MenuItem(fileMenu,SWT.PUSH);
		exitMenuItem.setText("Salir                            Alt+F4");
		
		openMenuItem.addSelectionListener(new OpenDiagramAction(_diagrams, this));
		saveMenuItem.addSelectionListener(new SaveDiagramAction(_diagrams, this));
		saveAsMenuItem.addSelectionListener(new SaveDiagramAsAction(_diagrams, this));
		newDiagramMenuItem.addSelectionListener(new NewDiagramAction(_diagrams, _components, this));
		exportToCMenuItem.addSelectionListener(new ExportToCAction(_diagrams, this));
		exportToCPPMenuItem.addSelectionListener(new ExportToCPPAction(_diagrams, this));
		exportToEXEMenuItem.addSelectionListener(new ExportToEXEAction(_diagrams, this));
		exportToImageMenuItem.addSelectionListener(new ExportToImageAction(_diagrams, this));
		exitMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				_shell.close();
			}
		});
	}
	
	private void initEditMenu(){
	    MenuItem editMenuItem = new MenuItem(mainMenu,SWT.CASCADE);
	    editMenuItem.setText("Edicion ");
	    Menu editMenu = new Menu(_shell, SWT.DROP_DOWN);
	    _editMenu = new EventoMenuContextual();
	    _editMenu.menu(editMenu);
	    editMenuItem.setMenu(editMenu);
	    
	}
	
	private void initFiguresMenu(){
	    MenuItem figuresMenuItem = new MenuItem(mainMenu,SWT.CASCADE);
	    figuresMenuItem.setText("Figuras ");
	    Menu figuresMenu = new Menu(_shell, SWT.DROP_DOWN);
	    figuresMenuItem.setMenu(figuresMenu);
	    inputMenuItem = new MenuItem(figuresMenu,SWT.PUSH);
	    inputMenuItem.setText("Entrada   ");
	    sentenceMenuItem = new MenuItem(figuresMenu,SWT.PUSH);
	    sentenceMenuItem.setText("Expresion     ");
	    ifMenuItem = new MenuItem(figuresMenu,SWT.PUSH);
	    ifMenuItem.setText("Decisin   ");
	    whileMenuItem = new MenuItem(figuresMenu,SWT.PUSH);
	    whileMenuItem.setText("Ciclo Mientras");
	    forMenuItem = new MenuItem(figuresMenu,SWT.PUSH);
	    forMenuItem.setText("Ciclo Para  ");
	    outputMenuItem = new MenuItem(figuresMenu,SWT.PUSH);
	    outputMenuItem.setText("Salida   ");
	    
	    
	    inputMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			//String name = "imagenes\\cursorEntrada.png";
			//ImageData image = new ImageData(name);
			_components.cursor[0] = new Cursor(MainWindow._display, ImageLoader.getImage("cursorEntrada.png").getImageData(), 0, 0);
		    InputFigure entrada2 = new InputFigure();
			entrada2.instruction.instruccion = "null";
			MainWindow._mainFigure = null;
			MainWindow._mainFigure = entrada2;
			MainWindow.bandera = false;
		    disableCursor();
		}
	});
	ifMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			//String name = "imagenes\\cursorIf.png";
		    //ImageData image = new ImageData(name);
		    _components.cursor[0] = new Cursor(MainWindow._display, ImageLoader.getImage("cursorIf.png").getImageData(), 0, 0);
		    DecisionFigure decision2 = new DecisionFigure();
		    InstruccionSimple codigo = new InstruccionSimple();
			codigo.setInstruccionSimple("null");
			decision2.instruction.instruccion.add(0,codigo);
			MainWindow._mainFigure = null;
			MainWindow._mainFigure = decision2;
			MainWindow.bandera = false;
		    disableCursor();
		}
	});
	sentenceMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			//String name = "imagenes\\cursorProceso.png";
		   //ImageData image = new ImageData(name);
		    _components.cursor[0] = new Cursor(MainWindow._display, ImageLoader.getImage("cursorProceso.png").getImageData(), 0, 0);
		    SentenceFigure proceso2 = new SentenceFigure();
			proceso2.instruccion.instruccion = "null";
			MainWindow._mainFigure = null;
			MainWindow._mainFigure = proceso2;
			MainWindow.bandera = false;
		    disableCursor();
		}
	});
	outputMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			//String name = "imagenes\\cursorSalida.png";
		   //ImageData image = new ImageData(name);
		    _components.cursor[0] = new Cursor(MainWindow._display, ImageLoader.getImage("cursorSalida.png").getImageData(), 0, 0);
		    OutputFigure salida2 = new OutputFigure();
			salida2.instruction.instruccion = "null";
			MainWindow._mainFigure = null;
			MainWindow._mainFigure = salida2;
			MainWindow.bandera = false;
		    disableCursor();
		}
	});
	forMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			//String name = "imagenes\\cursorFor.png";
		    //ImageData image = new ImageData(name);
		    _components.cursor[0] = new Cursor(MainWindow._display, ImageLoader.getImage("cursorFor.png").getImageData(), 0, 0);
		    ForFigure For2 = new ForFigure();
		    InstruccionSimple codigo = new InstruccionSimple();
		    codigo.setInstruccionSimple("null");
		    For2.instruction.instruccion.add(0,codigo);
		    MainWindow._mainFigure = null;
		    MainWindow._mainFigure = For2;
		    MainWindow.bandera = false;
		    disableCursor();
		}
	});
	whileMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			//String name = "imagenes\\cursorWhile.png";
		   // ImageData image = new ImageData(name);
		    _components.cursor[0] = new Cursor(MainWindow._display, ImageLoader.getImage("cursorWhile.png").getImageData(), 0, 0);
		    WhileFigure While2 = new WhileFigure();
		    InstruccionSimple codigo = new InstruccionSimple();
			codigo.setInstruccionSimple("null");
			While2.instruccion.instruccion.add(0,codigo);
			MainWindow._mainFigure = null;
			MainWindow._mainFigure = While2;
			MainWindow.bandera = false;
		    disableCursor();
		}
	});
	
	
	}
	
	private void initViewMenu(){
		MenuItem viewMenuItem = new MenuItem(mainMenu,SWT.CASCADE);
		viewMenuItem.setText("Ver ");
		Menu viewMenu = new Menu(_shell, SWT.DROP_DOWN);
		viewMenuItem.setMenu(viewMenu);
		MenuItem toolbarMenuItem = new MenuItem(viewMenu,SWT.CHECK);
		toolbarMenuItem.setText(" Barra De Herramientas   ");
		toolbarMenuItem.setSelection(true);
		MenuItem tabsMenuItem = new MenuItem(viewMenu,SWT.CHECK);
		tabsMenuItem.setText(" Barra De Diagramas       ");
		tabsMenuItem.setSelection(true);
		figuresBarMenuItem = new MenuItem(viewMenu,SWT.CHECK);
		figuresBarMenuItem.setText(" Barra De Figuras       ");
		figuresBarMenuItem.setSelection(true);
		consoleMenuItem = new MenuItem(viewMenu,SWT.CHECK);
		consoleMenuItem.setText(" Consola     ");
		
		
		toolbarMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MenuItem widget = (MenuItem)e.widget;
				_components.addBarraDeHerramientas(widget.getSelection());
			}
		});
		figuresBarMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MenuItem widget = (MenuItem)e.widget;
				_components.addBarraFiguras(widget.getSelection());
			}
		});
		tabsMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MenuItem widget = (MenuItem)e.widget;
				_components.addTabFolder(widget.getSelection());
			}
		});
		consoleMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MenuItem widget = (MenuItem)e.widget;
				_components.moverConsola(widget.getSelection());
			}
		});
	}
	
	private void initOptionsMenu(){
	    MenuItem optionsMenuItem = new MenuItem(mainMenu,SWT.CASCADE);
	    optionsMenuItem.setText("Opciones ");
	    Menu optionsMenu = new Menu(_shell, SWT.DROP_DOWN);
	    buildCodeMenuItem = new MenuItem(optionsMenu,SWT.PUSH);
	    buildCodeMenuItem.setText("Codigo Fuente C        F4");
	    compileMenuItem= new MenuItem(optionsMenu,SWT.PUSH);
	    compileMenuItem.setText("Compilar/Ejecutar      F5");
	    resetDiagramMenuItem = new MenuItem(optionsMenu,SWT.PUSH);
	    resetDiagramMenuItem.setText("Restablecer diagrama          F3");
	    stepByStepMenuItem = new MenuItem(optionsMenu,SWT.PUSH);
	    stepByStepMenuItem.setText("Paso A Paso");
	    optionsMenuItem.setMenu(optionsMenu);
	    
	    stepByStepMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			if(!_diagrams.getTabItem().getSave().isSave()){
				if(_components.guardar()){
					Compilar codigo = new Compilar(_diagrams);
					codigo.main(false,false);
					if(codigo.errorBandera){
						int aux = _components.text.getText().length();
						if(aux>=0){
							_components.text.setText("");
						}
						//moverConsola(true);
						_components.text.setText(codigo.error);
						_diagrams.getTabItem().getInfo().addInformation("/Ep - Error en el paso a paso:");
						_diagrams.getTabItem().getInfo().addInformation(codigo.error);
						codigo.eliminarArchivosCompilar();
					}
					else{
						//moverConsola(true);
						_components.disablePasoAPaso(true);
						_components.ejecutar(false,codigo);
						_diagrams.getTabItem().getInfo().addInformation("/P - Se inicio el paso a paso de manera correcta");
					}
				}
			}
			else{
				Compilar codigo = new Compilar(_diagrams);
				codigo.main(false,false);
				if(codigo.errorBandera){
					int aux = _components.text.getText().length();
					if(aux>=0){
						_components.text.setText("");
					}
					//moverConsola(true);
					_components.text.setText(codigo.error);
					_diagrams.getTabItem().getInfo().addInformation("/Ep - Error en el paso a paso:");
					_diagrams.getTabItem().getInfo().addInformation(codigo.error);
					codigo.eliminarArchivosCompilar();
				}
				else{
					//moverConsola(true);
					_components.disablePasoAPaso(true);
					_components.ejecutar(false,codigo);
					_diagrams.getTabItem().getInfo().addInformation("/P - Se inicio el paso a paso de manera correcta");
				}
			}
		}
	});
	
	buildCodeMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			Instruccion codigo = new Instruccion();
			codigo.main(_diagrams.getHoja().getDiagrama(),true);
			codigo.ventana(_display);
		}
	});
	compileMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			Compilar codigo = new Compilar(_diagrams);
			if(_components.getEnEjecucion()){
				_components.stopEjecucion();
			}
			codigo.main(false,true);
			if(codigo.errorBandera){
				int aux = _components.text.getText().length();
				if(aux>=0){
					_components.text.setText("");
				}
				_components.text.setText(codigo.error);
				_diagrams.getTabItem().getInfo().addInformation("/Ec - Error en la compilacion:");
				_diagrams.getTabItem().getInfo().addInformation(codigo.error);
				codigo.eliminarArchivosCompilar();
			}
			else{
				_components.ejecutar(true,codigo);
				_diagrams.getTabItem().getInfo().addInformation("/C - Se Compilo el diagrama de manera correcta");
			}
			if(!MainWindow.consoleMenuItem.getSelection()){
				MainWindow.consoleMenuItem.setSelection(true);
				_components.moverConsola(true);
			}
		}
	});
	
	resetDiagramMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			_selectionAdministrator.setFiguraSeleccionada(0);
			for(int y=_diagrams.getHoja().getSizeDiagrama()-1;y>0;y--){
				_diagrams.getHoja().removeFigureIndexOf(y);
			}
			CircleFigure fin = new CircleFigure();
			_diagrams.getHoja().getDiagrama().add(fin);
			fin.setMensagge("  Fin");
			_diagrams.getHoja().resetScrollBar();
			_diagrams.getHoja().addFigure();
			_diagrams.getHoja().guardarRetroceso();
			_diagrams.getTabItem().getSave().setSave(false);
		}
	});
	
	    
	}
	
	private void initHelpMenu(){
		MenuItem helpMenuItem = new MenuItem(mainMenu,SWT.CASCADE);
		helpMenuItem.setText("Ayuda");	
		Menu helpMenu = new Menu(_shell, SWT.DROP_DOWN);
		MenuItem helpContentsMenuItem = new MenuItem(helpMenu,SWT.PUSH);
		helpContentsMenuItem.setText("Contenidos de Ayuda                             F1");	
		MenuItem examplesMenuItem = new MenuItem(helpMenu,SWT.PUSH);
		examplesMenuItem.setText("Ejemplos");	
		new MenuItem(helpMenu,SWT.SEPARATOR);
		MenuItem aboutMenuItem = new MenuItem(helpMenu,SWT.PUSH);
		aboutMenuItem.setText("Acerca de Origami...      F2");		
		helpMenuItem.setMenu(helpMenu);
		
		helpContentsMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				HelpWindow help = new HelpWindow();
				help.createWindow();
				help.showWindow();
			}
		});
		aboutMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				AboutWindow acercade = new AboutWindow();
				acercade.createWindow(_display);
				acercade.showWindow();
			}
		});
		
		
		examplesMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(_shell,SWT.OPEN);
			    dialog.setFilterExtensions(new String[] { "*.Org","*.*" });
			    dialog.setFilterPath("ejemplos\\");
			    String archivo = dialog.open();
			    if(archivo != null){
			    	if(_diagrams.getHoja().getSizeDiagrama()==0){
			    		String archivo2 = dialog.getFileName();
					    int pos = archivo2.indexOf('.');
					    String name = archivo2.substring(0, pos);
					    _diagrams.cambiarNombre(name);
					    _diagrams.abrir(archivo,getSerializer());
			    	}
				    else{
				    	_selectionAdministrator.setFiguraSeleccionada(0);
						_diagrams.getHoja().openFile(archivo);
						archivo = dialog.getFileName();
						int pos = archivo.indexOf('.');
						String name = archivo.substring(0, pos);
						_diagrams.cambiarNombre(name);
				    }
			    }
			}
		});
	    
	}
	
	public void disableCursor(){
		_diagrams.getHoja().getChart().disableCursor(_diagrams.getHoja().getDiagrama(),_diagrams.getHoja().getChart());
	}
	public static Componentes getComponentes(){
		return _components;
	}
	public void cargarImagenes(){
		InputStream iconStream = this.getClass().getResourceAsStream("");
		Image image = new Image(_display, iconStream);
	}
	/**
	 * Este metodo crea la ventana de inicio.
	 * @param args
	 * @throws OrigamiException 
	 */
	
	
	public MainWindow() {
	    	initWindow();
		initFileMenu();
		initEditMenu();
		initFiguresMenu();
		initViewMenu();
		initOptionsMenu();
		initHelpMenu();
		initControllers();
		addListeners();
	}
	
	private void initWindow(){
	    mainMenu = new Menu(_shell, SWT.BAR);
	    _components = new Componentes();
	    _components.agregarComponentes(_selectionAdministrator);
	    _components.setDiagrama(_diagrams);
	    _shell.setText("Origami");
	    _shell.setMaximized(true);
	    _shell.setImage(ImageLoader.getImage("icono.GIF"));
	    _shell.addShellListener(new EventoVentana(_diagrams,_components)); 
	    _shell.setMenuBar(mainMenu);
	    _shell.setLayout(_components.layout);
	    _shell.pack();
	}
	
	private void addListeners(){
	    _shell.addShellListener(new ShellAdapter(){
		public void shellDeactivated(ShellEvent e){
			Cursor[] cursor = new Cursor[1];
			Grafico.MainWindow._mainFigure = null;
			Cursor oldCursor = cursor[0];
		    cursor[0] = new Cursor(null, SWT.CURSOR_ARROW);
		    _diagrams.getHoja().getDibujarDiagrama().setCursor(cursor[0]);
		    if(oldCursor != null){
		    	oldCursor.dispose();
		    }
		    MainWindow.bandera = true;
		    if(_diagrams.getItemCount()!=0){
		    	_diagrams.getHoja().addFigure();
			}
	    }
	});
	    
	    _keyEvent = new EventoKey(_selectionAdministrator,_diagrams);
		
	}
	
	private void initControllers(){
	    _diagramAdministrator = new AdminDiagrama(_selectionAdministrator);
	}
	
	private void show(){
	    _shell.open();
	}
	
	public Shell getShell(){
	    return _shell;
	}
	
	
	
	/**
	 * @param serializer the serializer to set
	 */
	public static void setSerializer(Serializar serializer) {
	    _serializer = serializer;
	}

	/**
	 * @return the serializer
	 */
	public static Serializar getSerializer() {
	    return _serializer;
	}

	public static void main(String args[]) throws OrigamiException{
		try{
		    	MainWindow mainWindow = new MainWindow();
		    	mainWindow.show();
		    	//TODO: ÀPara que sirve esta bandera?
			int bandera = 0;
			
			while(!_shell.isDisposed() && bandera<=15){
				 while(!_display.readAndDispatch()){
					 _diagrams.getHoja().resetScrollBar();
					 bandera++;
				 } 
			 }
			 while(!_shell.isDisposed()){
				 while(!_display.readAndDispatch()){
					 _display.sleep();
		         }  
			 }
		}
		catch(Exception e){
			throw new OrigamiException(e);
		}
	}
	
	//TODO: Revisar si es util.
	class MouseDetect implements MouseListener{
		public void mouseDoubleClick(MouseEvent arg0) {
		}
		public void mouseDown(MouseEvent arg0) {
			Cursor[] cursor = new Cursor[1];
			Grafico.MainWindow._mainFigure = null;
			Cursor oldCursor = cursor[0];
		    cursor[0] = new Cursor(null, SWT.CURSOR_ARROW);
		    _diagrams.getHoja().getDibujarDiagrama().setCursor(cursor[0]);
		    if(oldCursor != null){
		    	oldCursor.dispose();
		    }
		    MainWindow.bandera = true;
		    _diagrams.getHoja().addFigure();
		}
		public void mouseUp(MouseEvent arg0) {
		}
	}
}