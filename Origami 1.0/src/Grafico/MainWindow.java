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
	public static Display display = new Display();
	public static Shell shell = new Shell(display);
	public static AdminDiagrama diagramaEnMemoria;
	public static Figura figuraPrincipal = null;
	public static Cursor cursor2 = new Cursor(null,SWT.CURSOR_ARROW);
	public static Serializar ser = new Serializar();
	public static boolean bandera = false;
	public static boolean isCortar = false;
	public static Vector<SelectionSquare> seleccion = new Vector<SelectionSquare>();
	public static AdminSeleccion selec = new AdminSeleccion();
	public static TabFolder diagramas;
	public static EventoKey key;
	public static EventoMenuContextual menuEdicion;
	public static boolean first = false;
	public static boolean dispToolItem = false;
	public static Componentes componentes;
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
		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
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
		MenuItem cCodeMenuItem = new MenuItem(exportMenu,SWT.CASCADE);
		cCodeMenuItem.setText("Codigo C");
		MenuItem cppCodeMenuItem = new MenuItem(exportMenu,SWT.CASCADE);
		cppCodeMenuItem.setText("Codigo C++");
		MenuItem executableMenuItem = new MenuItem(exportMenu,SWT.CASCADE);
		executableMenuItem.setText("Ejecutable");
		MenuItem imageMenuItem = new MenuItem(exportMenu,SWT.CASCADE);
		imageMenuItem.setText("Imagen JPG");
		new MenuItem(fileMenu,SWT.SEPARATOR);
		MenuItem exitMenuItem = new MenuItem(fileMenu,SWT.PUSH);
		exitMenuItem.setText("Salir                            Alt+F4");
		
		newDiagramMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				diagramas.addTabItem();
				componentes.guardarDisable(true);
				MainWindow.componentes.disableAll(true);
			}
		});
		
		cCodeMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				 FileDialog dialog = new FileDialog(shell,SWT.SAVE);
			     dialog.setFilterExtensions(new String[] { "*.c"});
			     String archivo = dialog.open();
			     if(archivo!=null){
			    	 if(dialog.getFileName().contains("\\") || dialog.getFileName().contains("/") ||
			    				dialog.getFileName().contains(":") || dialog.getFileName().contains("*") ||
			    						dialog.getFileName().contains("?") || dialog.getFileName().contains("<") ||
			    						dialog.getFileName().contains(">") || dialog.getFileName().contains("|") ||
			    						dialog.getFileName().contains("\"")){
			    			MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_ERROR| SWT.OK);
				    		messageBox.setText("Origami");
				    		messageBox.setMessage("El nombre de archivo, directorio o etiqueta del volumn no es vlido");
				    		int seleccion = messageBox.open();
				    		switch(seleccion){
				    			case 64:
				    				break;
				    			case 128:			    	
				    				break;
				    		}
			    		}
			    		else{
			    			boolean existe = false;
			    			try{
			    				File arch = new File(archivo);
			    				if(arch.exists()){
			    					existe = true;
			    				}
			    			}
			    			catch(Exception e1){
			    			}		    	
			    			if(existe){
			    				MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
					    		messageBox.setText("Origami");
					    		messageBox.setMessage("El archivo ya existe. Desea reemplazarlo?");
					    		int seleccion = messageBox.open();
					    		switch(seleccion){
					    			case 64:
					    				 Exportar expor = new Exportar(diagramas);
								    	 expor.exportarCodigoC(archivo);					    	
					    				break;
					    			case 128:							
					    				break;
					    		}
					    	}
					    	else{
					    		 Exportar expor = new Exportar(diagramas);
						    	 expor.exportarCodigoC(archivo);
					    	}
			    		}
			    }
			}
		});
		
		cppCodeMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				 FileDialog dialog = new FileDialog(shell,SWT.SAVE);
			     dialog.setFilterExtensions(new String[] { "*.cpp"});
			     String archivo = dialog.open();
				 if(archivo!=null){
					 if(dialog.getFileName().contains("\\") || dialog.getFileName().contains("/") ||
			    				dialog.getFileName().contains(":") || dialog.getFileName().contains("*") ||
			    						dialog.getFileName().contains("?") || dialog.getFileName().contains("<") ||
			    						dialog.getFileName().contains(">") || dialog.getFileName().contains("|") ||
			    						dialog.getFileName().contains("\"")){
			    			MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_ERROR| SWT.OK);
				    		messageBox.setText("Origami");
				    		messageBox.setMessage("El nombre de archivo, directorio o etiqueta del volumn no es vlido");
				    		int seleccion = messageBox.open();
				    		switch(seleccion){
				    			case 64:
				    				break;
				    			case 128:			    	
				    				break;
				    		}
			    		}
			    		else{
			    			boolean existe = false;
			    			try{
			    				File arch = new File(archivo);
			    				if(arch.exists()){
			    					existe = true;
			    				}
			    			}
			    			catch(Exception e1){
			    			}		    	
			    			if(existe){
			    				MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
					    		messageBox.setText("Origami");
					    		messageBox.setMessage("El archivo ya existe. Desea reemplazarlo?");
					    		int seleccion = messageBox.open();
					    		switch(seleccion){
					    			case 64:
					    				Exportar expor = new Exportar(diagramas);
								    	expor.exportarCodigoCpp(archivo);						    	
					    				break;
					    			case 128:							
					    				break;
					    		}
					    	}
					    	else{
					    		Exportar expor = new Exportar(diagramas);
						    	expor.exportarCodigoCpp(archivo);
					    	}
			    		}
			    }
			}
		});
		executableMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell,SWT.SAVE);
			    dialog.setFilterExtensions(new String[] { "*.exe"});
			    String archivo = dialog.open();
			    if(archivo!=null){
			    	if(dialog.getFileName().contains("\\") || dialog.getFileName().contains("/") ||
		    				dialog.getFileName().contains(":") || dialog.getFileName().contains("*") ||
		    						dialog.getFileName().contains("?") || dialog.getFileName().contains("<") ||
		    						dialog.getFileName().contains(">") || dialog.getFileName().contains("|") ||
		    						dialog.getFileName().contains("\"")){
			    			MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_ERROR| SWT.OK);
				    		messageBox.setText("Origami");
				    		messageBox.setMessage("El nombre de archivo, directorio o etiqueta del volumn no es vlido");
				    		int seleccion = messageBox.open();
				    		switch(seleccion){
				    			case 64:
				    				break;
				    			case 128:			    	
				    				break;
				    		}
			    		}
			    		else{
			    			boolean existe = false;
			    			try{
			    				File arch = new File(archivo);
			    				if(arch.exists()){
			    					existe = true;
			    				}
			    			}
			    			catch(Exception e1){
			    			}		    	
			    			if(existe){
			    				MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
					    		messageBox.setText("Origami");
					    		messageBox.setMessage("El archivo ya existe. Desea reemplazarlo?");
					    		int seleccion = messageBox.open();
					    		switch(seleccion){
					    			case 64:
					    				 String nombre = dialog.getFileName();
								    	 nombre = nombre.substring(0,nombre.indexOf("."));
								    	 Exportar expor = new Exportar(diagramas);
								    	 expor.exportarEjecutable(archivo,nombre);						    	
					    				break;
					    			case 128:							
					    				break;
					    		}
					    	}
					    	else{
					    		 String nombre = dialog.getFileName();
						    	 nombre = nombre.substring(0,nombre.indexOf("."));
						    	 Exportar expor = new Exportar(diagramas);
						    	 expor.exportarEjecutable(archivo,nombre);
					    	}
			    		}
			    }
			}
		});
		imageMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell,SWT.SAVE);
				dialog.setFilterExtensions(new String[] { "*.jpg","*.bmp","*.png"});
				String archivo = dialog.open(); 
				if(archivo!=null){
					if(dialog.getFileName().contains("\\") || dialog.getFileName().contains("/") ||
		    				dialog.getFileName().contains(":") || dialog.getFileName().contains("*") ||
		    						dialog.getFileName().contains("?") || dialog.getFileName().contains("<") ||
		    						dialog.getFileName().contains(">") || dialog.getFileName().contains("|") ||
		    						dialog.getFileName().contains("\"")){
		    			MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_ERROR| SWT.OK);
			    		messageBox.setText("Origami");
			    		messageBox.setMessage("El nombre de archivo, directorio o etiqueta del volumn no es vlido");
			    		int seleccion = messageBox.open();
			    		switch(seleccion){
			    			case 64:
			    				break;
			    			case 128:			    	
			    				break;
			    		}
		    		}
		    		else{
		    			boolean existe = false;
		    			try{
		    				File arch = new File(archivo);
		    				if(arch.exists()){
		    					existe = true;
		    				}
		    			}
		    			catch(Exception e1){
		    			}		    	
		    			if(existe){
		    				MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
				    		messageBox.setText("Origami");
				    		messageBox.setMessage("El archivo ya existe. Desea reemplazarlo?");
				    		int seleccion = messageBox.open();
				    		switch(seleccion){
				    			case 64:
				    				String nombre = dialog.getFileName();
							    	nombre = nombre.substring(0,nombre.indexOf("."));
							    	Exportar expor = new Exportar(diagramas);
							    	expor.exportarJpg(archivo,diagramas.getHoja().getDiagrama(),diagramas.getHoja().getConexion());					    	
				    				break;
				    			case 128:							
				    				break;
				    		}
				    	}
				    	else{
				    		String nombre = dialog.getFileName();
					    	nombre = nombre.substring(0,nombre.indexOf("."));
					    	Exportar expor = new Exportar(diagramas);
					    	expor.exportarJpg(archivo,diagramas.getHoja().getDiagrama(),diagramas.getHoja().getConexion());
				    	}
		    		}
			    }
		}});
		
		exitMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		openMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(MainWindow.componentes.eje != null && MainWindow.componentes.getEnEjecucion()
						&& diagramas.getSelectedTabItemId() == MainWindow.componentes.eje.a.GetId()){
					MainWindow.componentes.stopEjecucion();
				}
				else if(MainWindow.componentes.paso != null && MainWindow.componentes.getEnEjecucion()
						&& diagramas.getSelectedTabItemId() == MainWindow.componentes.paso.a.GetId()){
					MainWindow.componentes.stopEjecucion();
				}
				FileDialog dialog = new FileDialog(MainWindow.shell,SWT.OPEN);
			    dialog.setFilterExtensions(new String[] { "*.Org","*.*" });
			    String archivo = dialog.open();
			    
			    if(archivo!=null){
			    	File file = new File(archivo);
			    	if(file.exists()){
			    		if(diagramas.getHoja().getSizeDiagrama()==0){
					    	String archivo2 = dialog.getFileName();
					    	int pos = archivo2.indexOf('.');
					    	String name = archivo2.substring(0, pos);
					    	diagramas.cambiarNombre("*"+name);
					    	diagramas.getTabItem().getSave().setSave(true);
					    	diagramas.abrir(archivo,ser);
					    	diagramas.getTabItem().getSave().setDir(archivo);
					    	
				    	}
				    	else{
				    		MainWindow.selec.setFiguraSeleccionada(0);
						    diagramas.getHoja().openFile(archivo);
						    diagramas.getTabItem().getSave().setDir(archivo);
						    archivo = dialog.getFileName();
						    int pos = archivo.indexOf('.');
						    String name = archivo.substring(0, pos);
						    diagramas.cambiarNombre("*"+name);
					    	diagramas.getTabItem().getSave().setSave(true);
					    	diagramas.getTabItem().resetRetroceso();		 
					    	diagramas.getTabItem().agregarRetroceso(diagramas.getHoja().getDiagrama(), diagramas.selec);
				    	}
				    	MainWindow.getComponentes().disablePasoAPaso(false);
				    	MainWindow.componentes.disableAll(true);
			    	}
			     }
			}
		});
		saveMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(diagramas.getTabItem().getSave().getDir()=="null"){
					FileDialog dialog = new FileDialog(MainWindow.shell,SWT.SAVE);
				    dialog.setFilterExtensions(new String[] { "*.Org"});
				    String archivo = dialog.open();
				    if(archivo!=null){
				    	if(dialog.getFileName().contains("\\") || dialog.getFileName().contains("/") ||
			    				dialog.getFileName().contains(":") || dialog.getFileName().contains("*") ||
			    						dialog.getFileName().contains("?") || dialog.getFileName().contains("<") ||
			    						dialog.getFileName().contains(">") || dialog.getFileName().contains("|") ||
			    						dialog.getFileName().contains("\"")){
			    			MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_ERROR| SWT.OK);
				    		messageBox.setText("Origami");
				    		messageBox.setMessage("El nombre de archivo, directorio o etiqueta del volumn no es vlido");
				    		int seleccion = messageBox.open();
				    		switch(seleccion){
				    			case 64:
				    				break;
				    			case 128:			    	
				    				break;
				    		}
			    		}
			    		else{
			    			boolean existe = false;
			    			try{
			    				File arch = new File(archivo);
			    				if(arch.exists()){
			    					existe = true;
			    				}
			    			}
			    			catch(Exception e1){
			    			}		    	
			    			if(existe){
			    				MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
					    		messageBox.setText("Origami");
					    		messageBox.setMessage("El archivo ya existe. Desea reemplazarlo?");
					    		int seleccion = messageBox.open();
					    		switch(seleccion){
					    			case 64:
								    	ser.SetFil(archivo);
								    	diagramas.getTabItem().getSave().setDir(archivo);
								    	ser.guardar(diagramas);
								    	archivo = dialog.getFileName();
								    	int pos = archivo.indexOf('.');
								    	String name = archivo.substring(0, pos);
								    	diagramas.getTabItem().getSave().setSave(true);
								    	diagramas.cambiarNombre(name);
					    				break;
					    			case 128:							
					    				break;
					    		}
					    	}
					    	else{
						    	ser.SetFil(archivo);
						    	diagramas.getTabItem().getSave().setDir(archivo);
						    	boolean error = ser.guardar(diagramas);
						    	if(error){
						    		archivo = dialog.getFileName();
						    		int pos = archivo.indexOf('.');
							    	String name = archivo.substring(0, pos);
							    	diagramas.getTabItem().getSave().setSave(true);
							    	diagramas.cambiarNombre(name);
						    	}
					    	}
			    		}
				    }
				}
				else{
			    	ser.SetFil(diagramas.getTabItem().getSave().getDir());
			    	ser.guardar(diagramas);
			    	diagramas.getTabItem().getSave().setSave(true);
				}
			}
		});
		saveAsMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(MainWindow.componentes.eje != null && MainWindow.componentes.getEnEjecucion()
						&& diagramas.getSelectedTabItemId() == MainWindow.componentes.eje.a.GetId()){
					MainWindow.componentes.stopEjecucion();
				}
				else if(MainWindow.componentes.paso != null && MainWindow.componentes.getEnEjecucion()
						&& diagramas.getSelectedTabItemId() == MainWindow.componentes.paso.a.GetId()){
					MainWindow.componentes.stopEjecucion();
				}
				MainWindow.getComponentes().disablePasoAPaso(false);
				FileDialog dialog = new FileDialog(shell,SWT.SAVE);
			    dialog.setFilterExtensions(new String[] { "*.Org"});
			    String archivo = dialog.open();
			    if(archivo!=null){
			    	if(dialog.getFileName().contains("\\") || dialog.getFileName().contains("/") ||
		    				dialog.getFileName().contains(":") || dialog.getFileName().contains("*") ||
		    						dialog.getFileName().contains("?") || dialog.getFileName().contains("<") ||
		    						dialog.getFileName().contains(">") || dialog.getFileName().contains("|") ||
		    						dialog.getFileName().contains("\"")){
			    		MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_ERROR| SWT.OK);
			    		messageBox.setText("Origami");
			    		messageBox.setMessage("El nombre de archivo, directorio o etiqueta del volumn no es vlido");
			    		int seleccion = messageBox.open();
			    		switch(seleccion){
			    			case 64:
			    				break;
			    			case 128:			    	
			    				break;
			    		}
		    		}
		    		else{
		    			boolean existe = false;
		    			try{
		    				File arch = new File(archivo);
		    				if(arch.exists()){
		    					existe = true;
		    				}
		    			}
		    			catch(Exception e1){
		    			}		    	
		    			if(existe){
		    				MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
				    		messageBox.setText("Origami");
				    		messageBox.setMessage("El archivo ya existe. Desea reemplazarlo?");
				    		int seleccion = messageBox.open();
				    		switch(seleccion){
				    			case 64:
							    	ser.SetFil(archivo);
							    	ser.guardar(diagramas);
							    	archivo = dialog.getFileName();
							    	int pos = archivo.indexOf('.');
							    	String name = archivo.substring(0, pos);
							    	diagramas.cambiarNombre(name);							    	
				    				break;
				    			case 128:							
				    				break;
				    		}
				    	}
				    	else{
					    	ser.SetFil(archivo);
					    	boolean error = ser.guardar(diagramas);
					    	if(error){
					    		archivo = dialog.getFileName();
					    		int pos = archivo.indexOf('.');
						    	String name = archivo.substring(0, pos);
						    	diagramas.cambiarNombre(name);
					    	}			    	
				    	}
		    		}
			    }
			}
		});
	}
	
	private void initEditMenu(){
	    MenuItem editMenuItem = new MenuItem(mainMenu,SWT.CASCADE);
	    editMenuItem.setText("Edicion ");
	    Menu editMenu = new Menu(shell, SWT.DROP_DOWN);
	    menuEdicion = new EventoMenuContextual();
	    menuEdicion.menu(editMenu);
	    editMenuItem.setMenu(editMenu);
	    
	}
	
	private void initFiguresMenu(){
	    MenuItem figuresMenuItem = new MenuItem(mainMenu,SWT.CASCADE);
	    figuresMenuItem.setText("Figuras ");
	    Menu figuresMenu = new Menu(shell, SWT.DROP_DOWN);
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
			componentes.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorEntrada.png").getImageData(), 0, 0);
		    InputFigure entrada2 = new InputFigure();
			entrada2.instruction.instruccion = "null";
			MainWindow.figuraPrincipal = null;
			MainWindow.figuraPrincipal = entrada2;
			MainWindow.bandera = false;
		    disableCursor();
		}
	});
	ifMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			//String name = "imagenes\\cursorIf.png";
		    //ImageData image = new ImageData(name);
		    componentes.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorIf.png").getImageData(), 0, 0);
		    DecisionFigure decision2 = new DecisionFigure();
		    InstruccionSimple codigo = new InstruccionSimple();
			codigo.setInstruccionSimple("null");
			decision2.instruction.instruccion.add(0,codigo);
			MainWindow.figuraPrincipal = null;
			MainWindow.figuraPrincipal = decision2;
			MainWindow.bandera = false;
		    disableCursor();
		}
	});
	sentenceMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			//String name = "imagenes\\cursorProceso.png";
		   //ImageData image = new ImageData(name);
		    componentes.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorProceso.png").getImageData(), 0, 0);
		    SentenceFigure proceso2 = new SentenceFigure();
			proceso2.instruccion.instruccion = "null";
			MainWindow.figuraPrincipal = null;
			MainWindow.figuraPrincipal = proceso2;
			MainWindow.bandera = false;
		    disableCursor();
		}
	});
	outputMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			//String name = "imagenes\\cursorSalida.png";
		   //ImageData image = new ImageData(name);
		    componentes.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorSalida.png").getImageData(), 0, 0);
		    OutputFigure salida2 = new OutputFigure();
			salida2.instruction.instruccion = "null";
			MainWindow.figuraPrincipal = null;
			MainWindow.figuraPrincipal = salida2;
			MainWindow.bandera = false;
		    disableCursor();
		}
	});
	forMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			//String name = "imagenes\\cursorFor.png";
		    //ImageData image = new ImageData(name);
		    componentes.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorFor.png").getImageData(), 0, 0);
		    ForFigure For2 = new ForFigure();
		    InstruccionSimple codigo = new InstruccionSimple();
		    codigo.setInstruccionSimple("null");
		    For2.instruction.instruccion.add(0,codigo);
		    MainWindow.figuraPrincipal = null;
		    MainWindow.figuraPrincipal = For2;
		    MainWindow.bandera = false;
		    disableCursor();
		}
	});
	whileMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			//String name = "imagenes\\cursorWhile.png";
		   // ImageData image = new ImageData(name);
		    componentes.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorWhile.png").getImageData(), 0, 0);
		    WhileFigure While2 = new WhileFigure();
		    InstruccionSimple codigo = new InstruccionSimple();
			codigo.setInstruccionSimple("null");
			While2.instruccion.instruccion.add(0,codigo);
			MainWindow.figuraPrincipal = null;
			MainWindow.figuraPrincipal = While2;
			MainWindow.bandera = false;
		    disableCursor();
		}
	});
	
	
	}
	
	private void initViewMenu(){
		MenuItem viewMenuItem = new MenuItem(mainMenu,SWT.CASCADE);
		viewMenuItem.setText("Ver ");
		Menu viewMenu = new Menu(shell, SWT.DROP_DOWN);
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
				componentes.addBarraDeHerramientas(widget.getSelection());
			}
		});
		figuresBarMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MenuItem widget = (MenuItem)e.widget;
				componentes.addBarraFiguras(widget.getSelection());
			}
		});
		tabsMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MenuItem widget = (MenuItem)e.widget;
				componentes.addTabFolder(widget.getSelection());
			}
		});
		consoleMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MenuItem widget = (MenuItem)e.widget;
				componentes.moverConsola(widget.getSelection());
			}
		});
	}
	
	private void initOptionsMenu(){
	    MenuItem optionsMenuItem = new MenuItem(mainMenu,SWT.CASCADE);
	    optionsMenuItem.setText("Opciones ");
	    Menu optionsMenu = new Menu(shell, SWT.DROP_DOWN);
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
			if(!diagramas.getTabItem().getSave().isSave()){
				if(componentes.guardar()){
					Compilar codigo = new Compilar(diagramas);
					codigo.main(false,false);
					if(codigo.errorBandera){
						int aux = componentes.text.getText().length();
						if(aux>=0){
							componentes.text.setText("");
						}
						//moverConsola(true);
						componentes.text.setText(codigo.error);
						diagramas.getTabItem().getInfo().addInformation("/Ep - Error en el paso a paso:");
						diagramas.getTabItem().getInfo().addInformation(codigo.error);
						codigo.eliminarArchivosCompilar();
					}
					else{
						//moverConsola(true);
						componentes.disablePasoAPaso(true);
						componentes.ejecutar(false,codigo);
						diagramas.getTabItem().getInfo().addInformation("/P - Se inicio el paso a paso de manera correcta");
					}
				}
			}
			else{
				Compilar codigo = new Compilar(diagramas);
				codigo.main(false,false);
				if(codigo.errorBandera){
					int aux = componentes.text.getText().length();
					if(aux>=0){
						componentes.text.setText("");
					}
					//moverConsola(true);
					componentes.text.setText(codigo.error);
					diagramas.getTabItem().getInfo().addInformation("/Ep - Error en el paso a paso:");
					diagramas.getTabItem().getInfo().addInformation(codigo.error);
					codigo.eliminarArchivosCompilar();
				}
				else{
					//moverConsola(true);
					componentes.disablePasoAPaso(true);
					componentes.ejecutar(false,codigo);
					diagramas.getTabItem().getInfo().addInformation("/P - Se inicio el paso a paso de manera correcta");
				}
			}
		}
	});
	
	buildCodeMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			Instruccion codigo = new Instruccion();
			codigo.main(diagramas.getHoja().getDiagrama(),true);
			codigo.ventana(display);
		}
	});
	compileMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			Compilar codigo = new Compilar(diagramas);
			if(componentes.getEnEjecucion()){
				componentes.stopEjecucion();
			}
			codigo.main(false,true);
			if(codigo.errorBandera){
				int aux = componentes.text.getText().length();
				if(aux>=0){
					componentes.text.setText("");
				}
				componentes.text.setText(codigo.error);
				diagramas.getTabItem().getInfo().addInformation("/Ec - Error en la compilacion:");
				diagramas.getTabItem().getInfo().addInformation(codigo.error);
				codigo.eliminarArchivosCompilar();
			}
			else{
				componentes.ejecutar(true,codigo);
				diagramas.getTabItem().getInfo().addInformation("/C - Se Compilo el diagrama de manera correcta");
			}
			if(!MainWindow.consoleMenuItem.getSelection()){
				MainWindow.consoleMenuItem.setSelection(true);
				componentes.moverConsola(true);
			}
		}
	});
	
	resetDiagramMenuItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			selec.setFiguraSeleccionada(0);
			for(int y=diagramas.getHoja().getSizeDiagrama()-1;y>0;y--){
				diagramas.getHoja().removeFigureIndexOf(y);
			}
			CircleFigure fin = new CircleFigure();
			diagramas.getHoja().getDiagrama().add(fin);
			fin.setMensagge("  Fin");
			diagramas.getHoja().resetScrollBar();
			diagramas.getHoja().addFigure();
			diagramas.getHoja().guardarRetroceso();
			diagramas.getTabItem().getSave().setSave(false);
		}
	});
	
	    
	}
	
	private void initHelpMenu(){
		MenuItem helpMenuItem = new MenuItem(mainMenu,SWT.CASCADE);
		helpMenuItem.setText("Ayuda");	
		Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
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
				acercade.createWindow(display);
				acercade.showWindow();
			}
		});
		
		
		examplesMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell,SWT.OPEN);
			    dialog.setFilterExtensions(new String[] { "*.Org","*.*" });
			    dialog.setFilterPath("ejemplos\\");
			    String archivo = dialog.open();
			    if(archivo != null){
			    	if(diagramas.getHoja().getSizeDiagrama()==0){
			    		String archivo2 = dialog.getFileName();
					    int pos = archivo2.indexOf('.');
					    String name = archivo2.substring(0, pos);
					    diagramas.cambiarNombre(name);
					    diagramas.abrir(archivo,ser);
			    	}
				    else{
				    	selec.setFiguraSeleccionada(0);
						diagramas.getHoja().openFile(archivo);
						archivo = dialog.getFileName();
						int pos = archivo.indexOf('.');
						String name = archivo.substring(0, pos);
						diagramas.cambiarNombre(name);
				    }
			    }
			}
		});
	    
	}
	
	public void disableCursor(){
		diagramas.getHoja().getChart().disableCursor(diagramas.getHoja().getDiagrama(),diagramas.getHoja().getChart());
	}
	public static Componentes getComponentes(){
		return componentes;
	}
	public void cargarImagenes(){
		InputStream iconStream = this.getClass().getResourceAsStream("");
		Image image = new Image(display, iconStream);
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
	    mainMenu = new Menu(shell, SWT.BAR);
	    componentes = new Componentes();
	    componentes.agregarComponentes(selec);
	    componentes.setDiagrama(diagramas);
	    shell.setText("Origami");
	    shell.setMaximized(true);
	    shell.setImage(ImageLoader.getImage("icono.GIF"));
	    shell.addShellListener(new EventoVentana(diagramas,componentes)); 
	    shell.setMenuBar(mainMenu);
	    shell.setLayout(componentes.layout);
	    shell.pack();
	}
	
	private void addListeners(){
	    shell.addShellListener(new ShellAdapter(){
		public void shellDeactivated(ShellEvent e){
			Cursor[] cursor = new Cursor[1];
			Grafico.MainWindow.figuraPrincipal = null;
			Cursor oldCursor = cursor[0];
		    cursor[0] = new Cursor(null, SWT.CURSOR_ARROW);
		    diagramas.getHoja().getDibujarDiagrama().setCursor(cursor[0]);
		    if(oldCursor != null){
		    	oldCursor.dispose();
		    }
		    MainWindow.bandera = true;
		    if(diagramas.getItemCount()!=0){
		    	diagramas.getHoja().addFigure();
			}
	    }
	});
	    
	    key = new EventoKey(selec,diagramas);
		
	}
	
	private void initControllers(){
	    diagramaEnMemoria = new AdminDiagrama(selec);
	}
	
	private void show(){
	    shell.open();
	}
	
	
	
	public static void main(String args[]) throws OrigamiException{
		try{
		    	MainWindow mainWindow = new MainWindow();
		    	mainWindow.show();
		    	//TODO: ÀPara que sirve esta bandera?
			int bandera = 0;
			
			while(!shell.isDisposed() && bandera<=15){
				 while(!display.readAndDispatch()){
					 diagramas.getHoja().resetScrollBar();
					 bandera++;
				 } 
			 }
			 while(!shell.isDisposed()){
				 while(!display.readAndDispatch()){
					 display.sleep();
		         }  
			 }
		}
		catch(Exception e){
			throw new OrigamiException(e);
		}
	}
	
	//TODO: ÀSe usa esta clase?
	class MouseDetect implements MouseListener{
		public void mouseDoubleClick(MouseEvent arg0) {
		}
		public void mouseDown(MouseEvent arg0) {
			Cursor[] cursor = new Cursor[1];
			Grafico.MainWindow.figuraPrincipal = null;
			Cursor oldCursor = cursor[0];
		    cursor[0] = new Cursor(null, SWT.CURSOR_ARROW);
		    diagramas.getHoja().getDibujarDiagrama().setCursor(cursor[0]);
		    if(oldCursor != null){
		    	oldCursor.dispose();
		    }
		    MainWindow.bandera = true;
		    diagramas.getHoja().addFigure();
		}
		public void mouseUp(MouseEvent arg0) {
		}
	}
}