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
import Grafico.Figuras.TerminationFigure;
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
public class Ventana{
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
	public static Vector<Informacion> info;
	public static MenuItem Consola;
	public static MenuItem BarraFiguras;
	public static MenuItem If;
	public static MenuItem Proceso;
	public static MenuItem Entrada;
	public static MenuItem Salida;
	public static MenuItem For;
	public static MenuItem While;
	public static MenuItem exportar;
	public static MenuItem Eliminar;
	public static MenuItem pasoApaso;
	public static MenuItem compilar;
	public static MenuItem guardar;
	public static MenuItem guardarComo;
	public static MenuItem generarCodigo;
	
	public void getMenu(){
		Menu menu = new Menu(shell, SWT.BAR);
		Menu menuVer = new Menu(shell, SWT.DROP_DOWN);
		Menu menuFiguras = new Menu(shell, SWT.DROP_DOWN);
		Menu menuAyuda = new Menu(shell, SWT.DROP_DOWN);
		Menu menuAyuda3 = new Menu(shell, SWT.DROP_DOWN);
		Menu menuAyuda4 = new Menu(shell, SWT.DROP_DOWN);
		Menu menuExportar = new Menu(shell, SWT.DROP_DOWN);
		generarCodigo = new MenuItem(menuAyuda4,SWT.PUSH);
		compilar= new MenuItem(menuAyuda4,SWT.PUSH);
		shell.setMenuBar(menu);
		compilar.setText("Compilar/Ejecutar      F5");
		generarCodigo.setText("Codigo Fuente C        F4");
		MenuItem Archivo = new MenuItem(menu,SWT.CASCADE);
		Archivo.setText("Archivo ");
		Archivo.setMenu(menuExportar);
		Eliminar = new MenuItem(menuAyuda4,SWT.PUSH);
		Eliminar.setText("Limpiar Pantalla          F3");
		pasoApaso = new MenuItem(menuAyuda4,SWT.PUSH);
		pasoApaso.setText("Paso A Paso");
		MenuItem abrir = new MenuItem(menuExportar,SWT.PUSH);
		abrir.setText("Abrir...                      Ctrl+A");	
		guardar = new MenuItem(menuExportar,SWT.PUSH);
		guardar.setText("Guardar                     Ctrl+G");
		guardarComo = new MenuItem(menuExportar,SWT.PUSH);
		guardarComo.setText("Guardar como...                 ");	
		MenuItem nuevo = new MenuItem(menuExportar,SWT.PUSH);
		nuevo.setText("Nuevo Diagrama        Ctrl+N");
		new MenuItem(menuExportar,SWT.SEPARATOR);
		Menu menu2 = new Menu(menuExportar); 
		exportar = new MenuItem(menuExportar,SWT.CASCADE);
		exportar.setText("Exportar");
		exportar.setMenu(menu2);
		MenuItem codigoC = new MenuItem(menu2,SWT.CASCADE);
		codigoC.setText("Codigo C");
		MenuItem codigoCpp = new MenuItem(menu2,SWT.CASCADE);
		codigoCpp.setText("Codigo C++");
		MenuItem ejecutable = new MenuItem(menu2,SWT.CASCADE);
		ejecutable.setText("Ejecutable");
		MenuItem imagen = new MenuItem(menu2,SWT.CASCADE);
		imagen.setText("Imagen JPG");
		new MenuItem(menuExportar,SWT.SEPARATOR);
		MenuItem salir = new MenuItem(menuExportar,SWT.PUSH);
		salir.setText("Salir                            Alt+F4");
		MenuItem Edicion = new MenuItem(menu,SWT.CASCADE);
		Edicion.setText("Edicion ");
		Edicion.setMenu(menuAyuda3);
		menuEdicion = new EventoMenuContextual();
		menuEdicion.menu(menuAyuda3);
		menu.setVisible(true);
		
		MenuItem figuras = new MenuItem(menu,SWT.CASCADE);
		figuras.setText("Figuras ");
		figuras.setMenu(menuFiguras);
		Entrada = new MenuItem(menuFiguras,SWT.PUSH);
		Entrada.setText("Entrada   ");
		Proceso = new MenuItem(menuFiguras,SWT.PUSH);
		Proceso.setText("Expresin     ");
		If = new MenuItem(menuFiguras,SWT.PUSH);
		If.setText("Decisin   ");
		While = new MenuItem(menuFiguras,SWT.PUSH);
		While.setText("Ciclo Mientras");
		For = new MenuItem(menuFiguras,SWT.PUSH);
		For.setText("Ciclo Para  ");
		Salida = new MenuItem(menuFiguras,SWT.PUSH);
		Salida.setText("Salida   ");
		
		MenuItem Ver = new MenuItem(menu,SWT.CASCADE);
		Ver.setText("Ver ");
		Ver.setMenu(menuVer);
		MenuItem BarraHerramientas = new MenuItem(menuVer,SWT.CHECK);
		BarraHerramientas.setText(" Barra De Herramientas   ");
		BarraHerramientas.setSelection(true);
		MenuItem BarraTab = new MenuItem(menuVer,SWT.CHECK);
		BarraTab.setText(" Barra De Diagramas       ");
		BarraTab.setSelection(true);
		BarraFiguras = new MenuItem(menuVer,SWT.CHECK);
		BarraFiguras.setText(" Barra De Figuras       ");
		BarraFiguras.setSelection(true);
		Consola = new MenuItem(menuVer,SWT.CHECK);
		Consola.setText(" Consola     ");
		
		MenuItem opciones = new MenuItem(menu,SWT.CASCADE);
		opciones.setText("Opciones ");
		opciones.setMenu(menuAyuda4);
		MenuItem ayuda = new MenuItem(menuAyuda,SWT.PUSH);
		ayuda.setText("Ayuda                             F1");	
		MenuItem ejemplos = new MenuItem(menuAyuda,SWT.PUSH);
		ejemplos.setText("Ejemplos");	
		new MenuItem(menuAyuda,SWT.SEPARATOR);
		MenuItem acerca = new MenuItem(menuAyuda,SWT.PUSH);
		acerca.setText("Acerca de Origami...      F2");		
		MenuItem acercaDe = new MenuItem(menu,SWT.CASCADE);
		acercaDe.setText("Ayuda");	
		acercaDe.setMenu(menuAyuda);
		ayuda.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				HelpWindow help = new HelpWindow();
				help.createWindow();
				help.showWindow();
			}
		});
		acerca.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				AboutWindow acercade = new AboutWindow();
				acercade.createWindow(display);
				acercade.showWindow();
			}
		});
		pasoApaso.addSelectionListener(new SelectionAdapter() {
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
		nuevo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				diagramas.addTabItem();
				componentes.guardarDisable(true);
				Ventana.componentes.disableAll(true);
			}
		});
		generarCodigo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Instruccion codigo = new Instruccion();
				codigo.main(diagramas.getHoja().getDiagrama(),true);
				codigo.ventana(display);
			}
		});
		compilar.addSelectionListener(new SelectionAdapter() {
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
				if(!Ventana.Consola.getSelection()){
					Ventana.Consola.setSelection(true);
					componentes.moverConsola(true);
				}
			}
		});
		codigoC.addSelectionListener(new SelectionAdapter() {
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
			    			MessageBox messageBox = new MessageBox(Ventana.shell, SWT.ICON_ERROR| SWT.OK);
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
			    				MessageBox messageBox = new MessageBox(Ventana.shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
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
		codigoCpp.addSelectionListener(new SelectionAdapter() {
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
			    			MessageBox messageBox = new MessageBox(Ventana.shell, SWT.ICON_ERROR| SWT.OK);
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
			    				MessageBox messageBox = new MessageBox(Ventana.shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
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
		ejecutable.addSelectionListener(new SelectionAdapter() {
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
			    			MessageBox messageBox = new MessageBox(Ventana.shell, SWT.ICON_ERROR| SWT.OK);
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
			    				MessageBox messageBox = new MessageBox(Ventana.shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
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
		imagen.addSelectionListener(new SelectionAdapter() {
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
		    			MessageBox messageBox = new MessageBox(Ventana.shell, SWT.ICON_ERROR| SWT.OK);
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
		    				MessageBox messageBox = new MessageBox(Ventana.shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
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
		ejemplos.addSelectionListener(new SelectionAdapter() {
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
		Eliminar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selec.setFiguraSeleccionada(0);
				for(int y=diagramas.getHoja().getSizeDiagrama()-1;y>0;y--){
					diagramas.getHoja().removeFigureIndexOf(y);
				}
				TerminationFigure fin = new TerminationFigure();
				diagramas.getHoja().getDiagrama().add(fin);
				fin.setMensagge("  Fin");
				diagramas.getHoja().resetScrollBar();
				diagramas.getHoja().addFigure();
				diagramas.getHoja().guardarRetroceso();
				diagramas.getTabItem().getSave().setSave(false);
			}
		});
		salir.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		abrir.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(Ventana.componentes.eje != null && Ventana.componentes.getEnEjecucion()
						&& diagramas.getSelectedTabItemId() == Ventana.componentes.eje.a.GetId()){
					Ventana.componentes.stopEjecucion();
				}
				else if(Ventana.componentes.paso != null && Ventana.componentes.getEnEjecucion()
						&& diagramas.getSelectedTabItemId() == Ventana.componentes.paso.a.GetId()){
					Ventana.componentes.stopEjecucion();
				}
				FileDialog dialog = new FileDialog(Ventana.shell,SWT.OPEN);
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
				    		Ventana.selec.setFiguraSeleccionada(0);
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
				    	Ventana.getComponentes().disablePasoAPaso(false);
				    	Ventana.componentes.disableAll(true);
			    	}
			     }
			}
		});
		guardar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(diagramas.getTabItem().getSave().getDir()=="null"){
					FileDialog dialog = new FileDialog(Ventana.shell,SWT.SAVE);
				    dialog.setFilterExtensions(new String[] { "*.Org"});
				    String archivo = dialog.open();
				    if(archivo!=null){
				    	if(dialog.getFileName().contains("\\") || dialog.getFileName().contains("/") ||
			    				dialog.getFileName().contains(":") || dialog.getFileName().contains("*") ||
			    						dialog.getFileName().contains("?") || dialog.getFileName().contains("<") ||
			    						dialog.getFileName().contains(">") || dialog.getFileName().contains("|") ||
			    						dialog.getFileName().contains("\"")){
			    			MessageBox messageBox = new MessageBox(Ventana.shell, SWT.ICON_ERROR| SWT.OK);
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
			    				MessageBox messageBox = new MessageBox(Ventana.shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
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
		guardarComo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(Ventana.componentes.eje != null && Ventana.componentes.getEnEjecucion()
						&& diagramas.getSelectedTabItemId() == Ventana.componentes.eje.a.GetId()){
					Ventana.componentes.stopEjecucion();
				}
				else if(Ventana.componentes.paso != null && Ventana.componentes.getEnEjecucion()
						&& diagramas.getSelectedTabItemId() == Ventana.componentes.paso.a.GetId()){
					Ventana.componentes.stopEjecucion();
				}
				Ventana.getComponentes().disablePasoAPaso(false);
				FileDialog dialog = new FileDialog(shell,SWT.SAVE);
			    dialog.setFilterExtensions(new String[] { "*.Org"});
			    String archivo = dialog.open();
			    if(archivo!=null){
			    	if(dialog.getFileName().contains("\\") || dialog.getFileName().contains("/") ||
		    				dialog.getFileName().contains(":") || dialog.getFileName().contains("*") ||
		    						dialog.getFileName().contains("?") || dialog.getFileName().contains("<") ||
		    						dialog.getFileName().contains(">") || dialog.getFileName().contains("|") ||
		    						dialog.getFileName().contains("\"")){
			    		MessageBox messageBox = new MessageBox(Ventana.shell, SWT.ICON_ERROR| SWT.OK);
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
		    				MessageBox messageBox = new MessageBox(Ventana.shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
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
		BarraHerramientas.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MenuItem widget = (MenuItem)e.widget;
				componentes.addBarraDeHerramientas(widget.getSelection());
			}
		});
		BarraFiguras.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MenuItem widget = (MenuItem)e.widget;
				componentes.addBarraFiguras(widget.getSelection());
			}
		});
		BarraTab.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MenuItem widget = (MenuItem)e.widget;
				componentes.addTabFolder(widget.getSelection());
			}
		});
		Consola.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MenuItem widget = (MenuItem)e.widget;
				componentes.moverConsola(widget.getSelection());
			}
		});
		Entrada.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//String name = "imagenes\\cursorEntrada.png";
				//ImageData image = new ImageData(name);
				componentes.cursor[0] = new Cursor(Ventana.display, ImageLoader.getImage("cursorEntrada.png").getImageData(), 0, 0);
			    InputFigure entrada2 = new InputFigure();
				entrada2.instruccion.instruccion = "null";
				Ventana.figuraPrincipal = null;
				Ventana.figuraPrincipal = entrada2;
				Ventana.bandera = false;
			    disableCursor();
			}
		});
		If.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//String name = "imagenes\\cursorIf.png";
			    //ImageData image = new ImageData(name);
			    componentes.cursor[0] = new Cursor(Ventana.display, ImageLoader.getImage("cursorIf.png").getImageData(), 0, 0);
			    DecisionFigure decision2 = new DecisionFigure();
			    InstruccionSimple codigo = new InstruccionSimple();
				codigo.setInstruccionSimple("null");
				decision2.instruction.instruccion.add(0,codigo);
				Ventana.figuraPrincipal = null;
				Ventana.figuraPrincipal = decision2;
				Ventana.bandera = false;
			    disableCursor();
			}
		});
		Proceso.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//String name = "imagenes\\cursorProceso.png";
			   //ImageData image = new ImageData(name);
			    componentes.cursor[0] = new Cursor(Ventana.display, ImageLoader.getImage("cursorProceso.png").getImageData(), 0, 0);
			    SentenceFigure proceso2 = new SentenceFigure();
				proceso2.instruccion.instruccion = "null";
				Ventana.figuraPrincipal = null;
				Ventana.figuraPrincipal = proceso2;
				Ventana.bandera = false;
			    disableCursor();
			}
		});
		Salida.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//String name = "imagenes\\cursorSalida.png";
			   //ImageData image = new ImageData(name);
			    componentes.cursor[0] = new Cursor(Ventana.display, ImageLoader.getImage("cursorSalida.png").getImageData(), 0, 0);
			    OutputFigure salida2 = new OutputFigure();
				salida2.instruccion.instruccion = "null";
				Ventana.figuraPrincipal = null;
				Ventana.figuraPrincipal = salida2;
				Ventana.bandera = false;
			    disableCursor();
			}
		});
		For.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//String name = "imagenes\\cursorFor.png";
			    //ImageData image = new ImageData(name);
			    componentes.cursor[0] = new Cursor(Ventana.display, ImageLoader.getImage("cursorFor.png").getImageData(), 0, 0);
			    ForFigure For2 = new ForFigure();
			    InstruccionSimple codigo = new InstruccionSimple();
			    codigo.setInstruccionSimple("null");
			    For2.instruccion.instruccion.add(0,codigo);
			    Ventana.figuraPrincipal = null;
			    Ventana.figuraPrincipal = For2;
			    Ventana.bandera = false;
			    disableCursor();
			}
		});
		While.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//String name = "imagenes\\cursorWhile.png";
			   // ImageData image = new ImageData(name);
			    componentes.cursor[0] = new Cursor(Ventana.display, ImageLoader.getImage("cursorWhile.png").getImageData(), 0, 0);
			    WhileFigure While2 = new WhileFigure();
			    InstruccionSimple codigo = new InstruccionSimple();
				codigo.setInstruccionSimple("null");
				While2.instruccion.instruccion.add(0,codigo);
				Ventana.figuraPrincipal = null;
				Ventana.figuraPrincipal = While2;
				Ventana.bandera = false;
			    disableCursor();
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
	public static void main(String args[]) throws OrigamiException{
		try{
		    componentes = new Componentes();
		    componentes.agregarComponentes(selec);
		   
			key = new EventoKey(selec,diagramas);
			diagramaEnMemoria = new AdminDiagrama(selec);
			
			shell.setText("Origami");
			shell.setMaximized(true);
			shell.setImage(ImageLoader.getImage("icono.GIF"));
			shell.addShellListener(new EventoVentana(diagramas,componentes)); 
			new Ventana().getMenu();
			int bandera = 0;
			shell.setLayout(componentes.layout);
			shell.pack();
			shell.open();
			shell.addShellListener(new ShellAdapter(){
				public void shellDeactivated(ShellEvent e){
					Cursor[] cursor = new Cursor[1];
					Grafico.Ventana.figuraPrincipal = null;
					Cursor oldCursor = cursor[0];
				    cursor[0] = new Cursor(null, SWT.CURSOR_ARROW);
				    diagramas.getHoja().getDibujarDiagrama().setCursor(cursor[0]);
				    if(oldCursor != null){
				    	oldCursor.dispose();
				    }
				    Ventana.bandera = true;
				    if(diagramas.getItemCount()!=0){
				    	diagramas.getHoja().addFigure();
					}
			    }
			});
			componentes.setDiagrama(diagramas);
			info = new Vector<Informacion>();
			shell.open();
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
	class MouseDetect implements MouseListener{
		public void mouseDoubleClick(MouseEvent arg0) {
		}
		public void mouseDown(MouseEvent arg0) {
			Cursor[] cursor = new Cursor[1];
			Grafico.Ventana.figuraPrincipal = null;
			Cursor oldCursor = cursor[0];
		    cursor[0] = new Cursor(null, SWT.CURSOR_ARROW);
		    diagramas.getHoja().getDibujarDiagrama().setCursor(cursor[0]);
		    if(oldCursor != null){
		    	oldCursor.dispose();
		    }
		    Ventana.bandera = true;
		    diagramas.getHoja().addFigure();
		}
		public void mouseUp(MouseEvent arg0) {
		}
	}
}