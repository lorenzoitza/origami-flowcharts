package Grafico;

import java.io.File;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import Administracion.*;
import Administracion.Eventos.EventoMenuContextual;
import Administracion.Funcionalidad.Compilar;
import Administracion.Funcionalidad.Ejecutar;
import Administracion.Funcionalidad.Exportar;
import Administracion.Funcionalidad.PasoAPaso;
import Administracion.Funcionalidad.Serializar;
import Administracion.Funcionalidad.Codigo.Instruccion;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.Figuras.Entrada;
import Grafico.Figuras.For;
import Grafico.Figuras.If;
import Grafico.Figuras.Imprimir;
import Grafico.Figuras.Proceso;
import Grafico.Figuras.While;
import Imagenes.CargarImagenes;
/**
 * @version Origami 1.0.0.0.0.0.0.0.0.000.0.0.0.0.0..0.0.1
 * @author Juan Ku, Victor Rodriguez
 */
public class Componentes {
	public TabFolder diagramas;
	private ToolBar barraFiguras;
	private ToolBar barraHerramientas;
	private final GridData toolData =new GridData(SWT.FILL, SWT.FILL, true, false,2,1);
	private final GridData tabData =new GridData(SWT.FILL,SWT.FILL,true,false,2,1);
    private final GridData figurasData =new GridData(SWT.FILL, SWT.FILL, false, true,1,1);
    public final GridData diagramaData =new GridData(SWT.FILL,SWT.FILL,true,true,1,1);
    public final GridData consolaData =new GridData(SWT.FILL, SWT.FILL, false, false,2,1);
    private boolean boolTool=true;
    private boolean boolPestaas=true;
    private boolean boolFiguras=true;
    private boolean consolaMax=false;
	public final GridLayout layout = new GridLayout(2, false);
    final GridLayout layout2 = new GridLayout(1, false);
	public Serializar ser = new Serializar();
	private Button boton[] = new Button[7];
	public ToolItem toolItem[] = new ToolItem[20];
	public final Cursor[] cursor = new Cursor[1];
	public CTabFolder tabFolder;
	public Text text;
	private CTabItem item;
	private int sizeTab;
	public Ejecutar eje;
	public PasoAPaso paso;
	private boolean enEjecucion = false;
	private ToolItem bot;
	public boolean hide=true;
	public boolean seleccion;
	public boolean isPasoAPaso = false;

	public void agregarComponentes(AdminSeleccion selec){
	    layout.horizontalSpacing =layout.verticalSpacing=0;
	    layout.marginWidth = layout.marginHeight = 0;
	    layout.numColumns = 2;
		agregarBarraDeHerramientas();
		agregarTabFolder(selec);
		agregarConsola();
	}
	private void agregarBarraDeHerramientas(){
		barraHerramientas = new ToolBar(Ventana.shell,SWT.HORIZONTAL | SWT.FLAT);
		barraHerramientas.setLayoutData(toolData);
		toolData.heightHint=23;
		barraHerramientas.setCursor(new Cursor(null,SWT.CURSOR_ARROW));
		getToolItems(barraHerramientas);
	}
	private void agregarTabFolder(AdminSeleccion selec){
		Ventana.diagramas = new TabFolder(Ventana.display,selec);
		tabData.heightHint=0;
		Ventana.diagramas.getTabFolder().setLayoutData(tabData);
	}
	public void agregarBarraFiguras(){
		GridLayout layout2 = new GridLayout();
	    layout2.numColumns = 1;
	    layout2.horizontalSpacing = 0;
	    layout2.verticalSpacing=3;
	    layout2.marginWidth = layout2.marginHeight = 0;
		barraFiguras = new ToolBar(Ventana.shell, SWT.LEFT | SWT.FLAT | SWT.BORDER);
		barraFiguras.setLayoutData(figurasData);
		figurasData.widthHint=62;
		barraFiguras.setCursor(new Cursor(null,SWT.CURSOR_ARROW));
		getBotones(barraFiguras);
		barraFiguras.setLayout(layout2);
	}
	public void addBarraDeHerramientas(boolean seleccion){
		if(!consolaMax){
			if(!seleccion){
				toolData.exclude=true;
				barraHerramientas.setBounds(0,0,0,0);
			}
			else{
				toolData.exclude=false;
			}
			Ventana.shell.layout();
		}
		boolTool=seleccion;
	}
	public void addTabFolder(boolean seleccion){
		if(!consolaMax){
			if(!seleccion){
				tabData.exclude=true;
				Ventana.diagramas.getTabFolder().setBounds(0,0,0,0);
			}
			else{
				tabData.exclude=false;
			}
			Ventana.shell.layout();
		}
		boolPestaas=seleccion;
	}
	public void addBarraFiguras(boolean seleccion){
		if(!consolaMax){
			if(!seleccion){
				figurasData.exclude=true;
				barraFiguras.setBounds(0,0,0,0);
			}
			else{
				figurasData.exclude=false;
			}
			Ventana.shell.layout();
		}
		boolFiguras=seleccion;
	}
	public void setDiagrama(TabFolder diagramas){
		this.diagramas = diagramas;
	}
	public void moverConsola(boolean seleccionado){
		if(seleccionado){
			if(boolTool){
				toolData.exclude=false;
			}
			if(boolPestaas){
				tabData.exclude=false;
			}
			if(boolFiguras){
				figurasData.exclude=false;
			}
			diagramaData.exclude=false;
			consolaData.grabExcessHorizontalSpace=false;
			consolaData.grabExcessVerticalSpace=false;
			consolaMax=false;
			
			if(item.isDisposed()){
				createTab();
			}
			tabFolder.forceFocus();
			item.setControl(text);
			text.forceFocus();
			consolaData.exclude=false;
			consolaData.heightHint=150;
		}
		else{
			if(boolTool){
				toolData.exclude=false;
			}
			if(boolPestaas){
				tabData.exclude=false;
			}
			if(boolFiguras){
				figurasData.exclude=false;
			}
			diagramaData.exclude=false;
			consolaData.grabExcessHorizontalSpace=false;
			consolaData.grabExcessVerticalSpace=false;
			consolaData.exclude=true;
			consolaMax=false;
			tabFolder.setBounds(0,0,0,0);
		}
		Ventana.shell.layout();
	}
	public void maxConsola(boolean seleccionado){
		if(seleccionado){
			if(toolData.exclude){
				boolTool=false;
			}
			if(tabData.exclude){
				boolPestaas=false;
			}
			if(figurasData.exclude){
				boolFiguras=false;
			}
			toolData.exclude=true;
			barraHerramientas.setBounds(0,0,0,0);
			tabData.exclude=true;
			Ventana.diagramas.getTabFolder().setBounds(0,0,0,0);
			figurasData.exclude=true;
			barraFiguras.setBounds(0,0,0,0);
			diagramaData.exclude=true;
			diagramas.getHoja().setBoundsToZero();
			consolaData.exclude=false;
			consolaData.grabExcessHorizontalSpace=true;
			consolaData.grabExcessVerticalSpace=true;
			consolaMax=true;
			if(item.isDisposed()){
				createTab();
			}
			tabFolder.forceFocus();
			item.setControl(text);
			text.forceFocus();
		}
		Ventana.shell.layout();
	}
	public void agregarConsola(){
		tabFolder = new CTabFolder(Ventana.shell, SWT.BORDER);
		tabFolder.pack();
		tabFolder.setLayoutData(consolaData);
		consolaData.exclude=true;
		tabFolder.setBounds(0,0,0,0);
		tabFolder.setSimple(false);
		tabFolder.setTabHeight(24);
		Color title = Ventana.display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
		Color title2 = Ventana.display.getSystemColor(SWT.COLOR_TITLE_FOREGROUND);
		Color title3 = Ventana.display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
		tabFolder.setSelectionForeground(title);
		tabFolder.setSelectionBackground(new Color[]{title2,title3},new int []{100},true);
		createTab();
		tabFolder.setMinimizeVisible(true);
		tabFolder.setMaximizeVisible(true);
		tabFolder.addCTabFolder2Listener(new CTabFolder2Adapter(){
			public void minimize(CTabFolderEvent event) {
				Ventana.Consola.setSelection(false);
				moverConsola(false);
				tabFolder.setMaximized(false);
			}
			public void maximize(CTabFolderEvent event){
				maxConsola(true);
				tabFolder.forceFocus();
				text.forceFocus();
				tabFolder.setMaximized(true);
			}
			public void restore(CTabFolderEvent event){
				moverConsola(true);
				tabFolder.forceFocus();
				text.forceFocus();
				tabFolder.setMinimized(false);
				tabFolder.setMaximized(false);
			}
		});
	}
	public boolean guardar(){
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
		    		return false;
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
						    	diagramas.cambiarNombre("*"+name);
						    	diagramas.getTabItem().getSave().setSave(true);
						    	return true;
			    			case 128:
			    				return false;
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
					    	diagramas.cambiarNombre("*"+name);
					    	diagramas.getTabItem().getSave().setSave(true);
				    	}
				    	return true;
			    	}
	    		}
		    }
		    return false;
		}
		else{
	    	ser.SetFil(diagramas.getTabItem().getSave().getDir());
	    	ser.guardar(diagramas);
	    	diagramas.getTabItem().getSave().setSave(true);
	    	return true;
		}
	}
	public void getToolItems(ToolBar toolbar){
		toolItem[0] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenNuevo = new Image(Ventana.display, "imagenes\\nuevo.png");
		toolItem[0].setImage(CargarImagenes.getImagen("nuevo.png"));
		toolItem[0].setToolTipText("Nuevo");
		toolItem[0].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				diagramas.addTabItem();
				guardarDisable(true);
				Ventana.componentes.disableAll(true);
			}
		});
		toolItem[1] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenAbrir = new Image(Ventana.display, "imagenes\\abrir.png");
		toolItem[1].setImage(CargarImagenes.getImagen("abrir.png"));
		toolItem[1].setToolTipText("Abrir");
		toolItem[1].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
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
		toolItem[2] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenSave = new Image(Ventana.display, "imagenes\\save.png");
		toolItem[2].setImage(CargarImagenes.getImagen("save.png"));
		toolItem[2].setToolTipText("Guardar");
		toolItem[2].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
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
								    	diagramas.cambiarNombre("*"+name);
								    	diagramas.getTabItem().getSave().setSave(true);
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
							    	diagramas.cambiarNombre("*"+name);
							    	diagramas.getTabItem().getSave().setSave(true);
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
		new ToolItem(toolbar, SWT.SEPARATOR);
		
		toolItem[3] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenDeshacer = new Image(Ventana.display, "imagenes\\undo.png");
		toolItem[3].setImage(CargarImagenes.getImagen("undo.png"));
		toolItem[3].setToolTipText(" Deshacer ");
		toolItem[3].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				TabItem item = (TabItem)diagramas.getSeleccion();
				item.retroceder();
				diagramas.getTabItem().getSave().setSave(false);
			}
		});
		toolItem[4] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenCortar = new Image(Ventana.display, "imagenes\\cortar.ico");
		toolItem[4].setImage(CargarImagenes.getImagen("cortar.ico"));
		toolItem[4].setToolTipText(" Cortar ");
		toolItem[4].setEnabled(false);
		toolItem[4].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Figura fig = diagramas.getHoja().getFigureIndexOf(Ventana.selec.getFiguraSeleccionada());
				EventoMenuContextual.Cortar(fig);
				toolBarDisable();
			}
		});
		toolItem[5] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenCopiar = new Image(Ventana.display, "imagenes\\copiar.png");
		toolItem[5].setImage(CargarImagenes.getImagen("copiar.png"));
		toolItem[5].setToolTipText(" Copiar ");
		toolItem[5].setEnabled(false);
		toolItem[5].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Figura fig = diagramas.getHoja().getFigureIndexOf(Ventana.selec.getFiguraSeleccionada());
				EventoMenuContextual.Copiar(fig);
				toolBarDisable();
			}
		});
		toolItem[6] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenPegar = new Image(Ventana.display, "imagenes\\pegar.ico");
		toolItem[6].setImage(CargarImagenes.getImagen("pegar.ico"));
		toolItem[6].setToolTipText(" Pegar ");
		toolItem[6].setEnabled(false);
		toolItem[6].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Figura fig = diagramas.getHoja().getFigureIndexOf(Ventana.selec.getFiguraSeleccionada());
				EventoMenuContextual.Pegar(fig);
				toolBarDisable();
			}
		});
		toolItem[7] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenEliminar = new Image(Ventana.display, "imagenes\\eliminar.png");
		toolItem[7].setImage(CargarImagenes.getImagen("eliminar.png"));
		toolItem[7].setToolTipText(" Eliminar ");
		toolItem[7].setEnabled(false);
		toolItem[7].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Figura fig = diagramas.getHoja().getFigureIndexOf(Ventana.selec.getFiguraSeleccionada());
				EventoMenuContextual.Eliminar(fig);
			}
		});
		new ToolItem(toolbar, SWT.SEPARATOR);
		
		toolItem[8] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenCodigo = new Image(Ventana.display, "imagenes\\codigo.png");
		toolItem[8].setImage(CargarImagenes.getImagen("codigo.png"));
		toolItem[8].setToolTipText("Generar Codigo");
		toolItem[8].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Instruccion codigo = new Instruccion();
				codigo.main(diagramas.getHoja().getDiagrama(),true);
				codigo.ventana(Ventana.display);
			}
		});
		toolItem[9] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenCodigoCpp = new Image(Ventana.display, "imagenes\\codigoCpp.png");
		toolItem[9].setImage(CargarImagenes.getImagen("codigoCpp.png"));
		toolItem[9].setToolTipText("Generar Codigo");
		toolItem[9].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Instruccion codigo = new Instruccion();
				codigo.main(diagramas.getHoja().getDiagrama(),false);
				codigo.ventana(Ventana.display);
			}
		});
		new ToolItem(toolbar, SWT.SEPARATOR);
		
		toolItem[10] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenRun = new Image(Ventana.display, "imagenes\\run.png");
		toolItem[10].setImage(CargarImagenes.getImagen("run.png"));
		toolItem[10].setToolTipText("Compilar/Ejecutar");
		toolItem[10].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if(!diagramas.getTabItem().getSave().isSave()){
					//llamo abrir la ventana para guardar
					if(guardar()){
						Compilar codigo = new Compilar(diagramas);
						if(getEnEjecucion()){
							stopEjecucion();
						}
						codigo.main(false,true);
						if(codigo.errorBandera){
							int aux = text.getText().length();
							if(aux>=0){
								text.setText("");
							}
							text.setText(codigo.error);
							diagramas.getTabItem().getInfo().addInformation("/Ec - Error en la compilacion:");
							diagramas.getTabItem().getInfo().addInformation(codigo.error);
							codigo.eliminarArchivosCompilar();
						}
						else{
							ejecutar(true,codigo);
							diagramas.getTabItem().getInfo().addInformation("/C - Se Compilo el diagrama de manera correcta");
						}
						if(!Ventana.Consola.getSelection()){
							Ventana.Consola.setSelection(true);
							moverConsola(true);
						}
					}
				}
				else{
					Compilar codigo = new Compilar(diagramas);
					if(getEnEjecucion()){
						stopEjecucion();
					}
					codigo.main(false,true);
					if(codigo.errorBandera){
						int aux = text.getText().length();
						if(aux>=0){
							text.setText("");
						}
						text.setText(codigo.error);
						codigo.eliminarArchivosCompilar();
					}
					else{
						ejecutar(true,codigo);
					}
					if(!Ventana.Consola.getSelection()){
						Ventana.Consola.setSelection(true);
						moverConsola(true);
					}
				}
			}
		});
		toolItem[11] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenPaso = new Image(Ventana.display, "imagenes\\check.png");
		toolItem[11].setImage(CargarImagenes.getImagen("check.png"));
		toolItem[11].setToolTipText("Paso A Paso");
		toolItem[11].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event){
				if(!diagramas.getTabItem().getSave().isSave()){
					if(guardar()){
						Compilar codigo = new Compilar(diagramas);
						codigo.main(false,false);
						if(codigo.errorBandera){
							int aux = text.getText().length();
							if(aux>=0){
								text.setText("");
							}
							//moverConsola(true);
							text.setText(codigo.error);
							diagramas.getTabItem().getInfo().addInformation("/Ep - Error en el paso a paso:");
							diagramas.getTabItem().getInfo().addInformation(codigo.error);
							codigo.eliminarArchivosCompilar();
						}
						else{
							//moverConsola(true);
							disablePasoAPaso(true);
							ejecutar(false,codigo);
							diagramas.getTabItem().getInfo().addInformation("/P - Se inicio el paso a paso de manera correcta");
						}
					}
				}
				else{
					Compilar codigo = new Compilar(diagramas);
					codigo.main(false,false);
					if(codigo.errorBandera){
						int aux = text.getText().length();
						if(aux>=0){
							text.setText("");
						}
						//moverConsola(true);
						text.setText(codigo.error);
						diagramas.getTabItem().getInfo().addInformation("/Ep - Error en el paso a paso:");
						diagramas.getTabItem().getInfo().addInformation(codigo.error);
						codigo.eliminarArchivosCompilar();
					}
					else{
						//moverConsola(true);
						disablePasoAPaso(true);
						ejecutar(false,codigo);
						diagramas.getTabItem().getInfo().addInformation("/P - Se inicio el paso a paso de manera correcta");
					}
				}
			}
		});
		
		toolItem[12] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenNext = new Image(Ventana.display, "imagenes\\next.png");
		toolItem[12].setImage(CargarImagenes.getImagen("next.png"));
		toolItem[12].setEnabled(false);
		toolItem[12].setToolTipText("Paso Siguiente");
		toolItem[12].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if(paso.ventanaLeer){
					paso.ventanaLeer();
				}
				else{
					next();
				}
			}
		});
		toolItem[13] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenStop = new Image(Ventana.display, "imagenes\\Stop.png");
		toolItem[13].setImage(CargarImagenes.getImagen("Stop.png"));
		toolItem[13].setEnabled(false);
		toolItem[13].setToolTipText("Terminar Ejecucion");
		toolItem[13].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if(Ventana.getComponentes().isPasoAPaso){
					Ventana.getComponentes().disablePasoAPaso(false);
				}
				stopEjecucion();
			}
		});
		new ToolItem(toolbar, SWT.SEPARATOR);
		
		toolItem[14] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenExportC = new Image(Ventana.display, "imagenes\\exportV.png");
		toolItem[14].setImage(CargarImagenes.getImagen("exportV.png"));
		toolItem[14].setToolTipText("Exportar a C");
		toolItem[14].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				 FileDialog dialog = new FileDialog(Ventana.shell,SWT.SAVE);
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
		toolItem[15] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenCpp = new Image(Ventana.display, "imagenes\\cpp.png");
		toolItem[15].setImage(CargarImagenes.getImagen("cpp.png"));
		toolItem[15].setToolTipText("Exportar a C++");
		toolItem[15].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				 FileDialog dialog = new FileDialog(Ventana.shell,SWT.SAVE);
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
		toolItem[16] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenExportExe = new Image(Ventana.display, "imagenes\\exportExe.png");
		toolItem[16].setImage(CargarImagenes.getImagen("exportExe.png"));
		toolItem[16].setToolTipText("Exportar a .exe");
		toolItem[16].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event){
				FileDialog dialog = new FileDialog(Ventana.shell,SWT.SAVE);
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
		new ToolItem(toolbar, SWT.SEPARATOR);
		
		toolItem[17] = new ToolItem (toolbar, SWT.PUSH);
		//Image imagenDonate = new Image(Ventana.display, "imagenes\\Donate.png");
		toolItem[17].setImage(CargarImagenes.getImagen("Donate.png"));
		toolItem[17].setToolTipText("Donaciones");
		toolItem[17].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event){
			}
		});
	}
	public void guardarDisable(boolean disable){
		toolItem[2].setEnabled(disable);
		Ventana.guardar.setEnabled(disable);
	}
	public void toolBarDisable(){
		if(Ventana.selec.getFiguraSeleccionada()==-1){
			for(int i=4; i<=7; i++){
				toolItem[i].setEnabled(false);
			}
		}
		else{
			if(Ventana.selec.getFiguraSeleccionada()!=0){
				for(int i=4; i<=7; i++){
					toolItem[i].setEnabled(true);
				}
				if(Ventana.diagramaEnMemoria.diagrama.size()==0){
					toolItem[6].setEnabled(false);
				}
			}
			else{
				for(int i=4; i<=7; i++){
					toolItem[i].setEnabled(false);
				}
				if(Ventana.diagramaEnMemoria.diagrama.size()!=0){
					toolItem[6].setEnabled(true);
				}
			}
		}
	}
	public void getBotones(ToolBar toolbar){
		boton[0] = new Button(toolbar, SWT.FLAT);
		//Image imagenEntrada = new Image(Ventana.display, "imagenes\\Entrada.png");
		//Image imagenEntrada = new Image(Ventana.display, Ventana.class.getClassLoader().getResourceAsStream("imagenes\\Entrada.png"));
		boton[0].setImage(CargarImagenes.getImagen("Entrada.png"));
		boton[0].pack();
		boton[0].setToolTipText("Entrada");
		boton[0].addSelectionListener(new SelectionAdapter() {
			 public void widgetSelected(SelectionEvent event) {
				//String name = "imagenes\\cursorEntrada.png";
				//ImageData image = new ImageData(name);
				cursor[0] = new Cursor(Ventana.display, CargarImagenes.getImagen("cursorEntrada.png").getImageData(), 0, 0);
			    Entrada entrada2 = new Entrada(SWT.COLOR_DARK_BLUE);
				entrada2.instruccion.instruccion = "null";
				Ventana.figuraPrincipal = null;
				Ventana.figuraPrincipal = entrada2;
				Ventana.bandera = false;
			    disableCursor();
			 }
		});
		boton[1] = new Button(toolbar, SWT.FLAT);
		//Image imagenProceso = new Image(Ventana.display, "imagenes\\Proceso.png");
		boton[1].setImage(CargarImagenes.getImagen("Proceso.png"));
		boton[1].pack();
		boton[1].setToolTipText("Expresin");
		boton[1].addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event){
				//String name = "imagenes\\cursorProceso.png";
			   //ImageData image = new ImageData(name);
			    cursor[0] = new Cursor(Ventana.display, CargarImagenes.getImagen("cursorProceso.png").getImageData(), 0, 0);
			    Proceso proceso2 = new Proceso(SWT.COLOR_DARK_BLUE);
				proceso2.instruccion.instruccion = "null";
				Ventana.figuraPrincipal = null;
				Ventana.figuraPrincipal = proceso2;
				Ventana.bandera = false;
			    disableCursor();
			}	
		});
		boton[2] = new Button(toolbar, SWT.FLAT);
		//Image imagenIf = new Image(Ventana.display, "imagenes\\If.png");
		boton[2].setImage(CargarImagenes.getImagen("If.png"));
		boton[2].pack();
		boton[2].setToolTipText("Decisin");
		boton[2].addSelectionListener(new SelectionAdapter() {
			 public void widgetSelected(SelectionEvent event) {
				//String name = "imagenes\\cursorIf.png";
			    //ImageData image = new ImageData(name);
			    cursor[0] = new Cursor(Ventana.display, CargarImagenes.getImagen("cursorIf.png").getImageData(), 0, 0);
			    If decision2 = new If(SWT.COLOR_DARK_BLUE);
			    InstruccionSimple codigo = new InstruccionSimple();
				codigo.setInstruccionSimple("null");
				decision2.instruccion.instruccion.add(0,codigo);
				Ventana.figuraPrincipal = null;
				Ventana.figuraPrincipal = decision2;
				Ventana.bandera = false;
			    disableCursor();
			}
		});
		boton[3] = new Button(toolbar, SWT.FLAT);
		//Image imagenWhile = new Image(Ventana.display, "imagenes\\While.png");
		boton[3].setImage(CargarImagenes.getImagen("While.png"));
		boton[3].pack();
		boton[3].setToolTipText("Ciclo Mientras");
		boton[3].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				//String name = "imagenes\\cursorWhile.png";
			    //ImageData image = new ImageData(name);
			    cursor[0] = new Cursor(Ventana.display, CargarImagenes.getImagen("cursorWhile.png").getImageData(), 0, 0);
			    While While2 = new While(SWT.COLOR_DARK_BLUE);
			    InstruccionSimple codigo = new InstruccionSimple();
				codigo.setInstruccionSimple("null");
				While2.instruccion.instruccion.add(0,codigo);
				Ventana.figuraPrincipal = null;
				Ventana.figuraPrincipal = While2;
				Ventana.bandera = false;
			    disableCursor();
			}
		});
		boton[4] = new Button(toolbar, SWT.FLAT);
		//Image imagenFor = new Image(Ventana.display, "imagenes\\For.png");
		boton[4].setImage(CargarImagenes.getImagen("For.png"));
		boton[4].pack();
		boton[4].setToolTipText("Ciclo Para");
		boton[4].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				//String name = "imagenes\\cursorFor.png";
			    //ImageData image = new ImageData(name);
			    cursor[0] = new Cursor(Ventana.display, CargarImagenes.getImagen("cursorFor.png").getImageData(), 0, 0);
			    For For2 = new For(SWT.COLOR_DARK_BLUE);
			    InstruccionSimple codigo = new InstruccionSimple();
			    codigo.setInstruccionSimple("null");
			    For2.instruccion.instruccion.add(0,codigo);
			    Ventana.figuraPrincipal = null;
			    Ventana.figuraPrincipal = For2;
			    Ventana.bandera = false;
			    disableCursor();
			}
		});
		boton[5] = new Button(toolbar, SWT.FLAT);
		//Image imagenSalida = new Image(Ventana.display, "imagenes\\Salida.png");
		boton[5].setImage(CargarImagenes.getImagen("Salida.png"));
		boton[5].pack();
		boton[5].setToolTipText("Salida");
		boton[5].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				//String name = "imagenes\\cursorSalida.png";
			    //ImageData image = new ImageData(name);
			    cursor[0] = new Cursor(Ventana.display, CargarImagenes.getImagen("cursorSalida.png").getImageData(), 0, 0);
			    Imprimir salida2 = new Imprimir(SWT.COLOR_DARK_BLUE);
				salida2.instruccion.instruccion = "null";
				Ventana.figuraPrincipal = null;
				Ventana.figuraPrincipal = salida2;
				Ventana.bandera = false;
			    disableCursor();
			}
		});
		Label label = new Label(toolbar, SWT.NONE);
	    label.setVisible(false);
	    GridData labelData = new GridData();
	    labelData.grabExcessVerticalSpace = true;
	    label.setLayoutData(labelData);
	    
		Button botonConsola = new Button(toolbar, SWT.FLAT);
		botonConsola.setBounds(40,600,50,30);
		//Image imagen = new Image(Ventana.display,"imagenes\\consola.ico");
		botonConsola.setImage(CargarImagenes.getImagen("consola.ico"));
		botonConsola.pack();
		botonConsola.setToolTipText("Consola ");
		botonConsola.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if(Ventana.Consola.getSelection()){
					Ventana.Consola.setSelection(false);
					moverConsola(false);
				}
				else{
					moverConsola(true);
					Ventana.Consola.setSelection(true);
				}
			}
		});
		for(int i=0; i<=5; i++){
			boton[i].addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
				public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
					Ventana.key.setKey(e);
					Ventana.key.Accion();
				}
			}); 
		}
	}
	public void disableCursor(){
		Ventana.diagramas.getHoja().getChart().disableCursor(Ventana.diagramas.getHoja().getDiagrama(),Ventana.diagramas.getHoja().getChart());
	}
	public void createTab(){
		item = new CTabItem(tabFolder, SWT.CLOSE);
		item.setText(" Consola   ");
		text = new Text(tabFolder,SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		text.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e) {
				if((sizeTab>=text.getCaretPosition()) && (e.keyCode==8)){
					e.doit=false;
				}
				else if(sizeTab>text.getCaretPosition()){
					e.doit=false;
				}
				if(e.keyCode == 13 || e.keyCode == 16777296){
					setText(texto());
				}
			}
			public void keyReleased(KeyEvent arg0) {
			}
		});
		ToolBar toolBarFolder = new ToolBar(tabFolder, SWT.LEFT );
		tabFolder.setTopRight(toolBarFolder);
		bot = new ToolItem(toolBarFolder,SWT.PUSH);
		final ToolItem bot2 = new ToolItem(toolBarFolder,SWT.PUSH);
		bot.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if(Ventana.getComponentes().isPasoAPaso){
					Ventana.getComponentes().disablePasoAPaso(false);
				}
				stopEjecucion();
			}
		});
		bot.setEnabled(false);
		bot.setToolTipText("Terminar Ejecucion");
		//Image imagen = new Image(Ventana.display, "imagenes\\Stop.png");
		bot.setImage(CargarImagenes.getImagen("Stop.png"));
		//Image imagen2 = new Image(Ventana.display, "imagenes\\monitor.gif");
		bot2.setImage(CargarImagenes.getImagen("monitor.gif"));
		bot2.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event){
				//Image imagen3;
				if(hide){
					//imagen3 = new Image(Ventana.display, "imagenes\\network.gif");
					bot2.setImage(CargarImagenes.getImagen("network.gif"));
					hide = false;
				}
				else{
					//imagen3 = new Image(Ventana.display, "imagenes\\monitor.gif");
					bot2.setImage(CargarImagenes.getImagen("monitor.gif"));
					hide=true;
				}
			}
		});
		item.setControl(text);
		//Image i = new Image(Ventana.display,"imagenes\\consola.ico");
		item.setImage(CargarImagenes.getImagen("consola.ico"));
	}
	public void ejecucionDisable(){
		if(enEjecucion){
			bot.setEnabled(true);
			toolItem[13].setEnabled(true);
		}
		else{
			bot.setEnabled(false);
			toolItem[13].setEnabled(false);
		}
	}
	public String texto(){
		String texto = text.getText();
		String[] linea = text.getText().split("\n");
		texto = linea[linea.length-1].substring(0,linea[linea.length-1].length());
		return texto;
	}
	public void setText(String text){
		if(seleccion){
			eje.inputActionPerformed(text);	
		}
		else{
			paso.inputActionPerformed(text);
		}
	}
	public void setEnEjecucion(boolean ejecucion){
		enEjecucion = ejecucion; 
	}
	//ejecuta el paso a paso cuando bandera es false...
	public void ejecutar(boolean bandera,Compilar codigo){
		enEjecucion = true; 
		ejecucionDisable();
		if(bandera){
			eje = new Ejecutar();
			eje.ejecutar(this,"main.exe",codigo);
			seleccion = true;
		}
		else{
			if(diagramas.getHoja().getSizeDiagrama()==2){
				MessageBox messageBox = new MessageBox(Ventana.shell, SWT.ICON_INFORMATION | SWT.YES );
				messageBox.setText("Origami");
				messageBox.setMessage("La ejecucin ha terminado.");
				int selec = messageBox.open();
				switch(selec){
					case 0:
						break;
					case 64:
						break;
					default:
						break;
				}
				enEjecucion = false;
				ejecucionDisable();
				disablePasoAPaso(false);
			}
			else{
				paso = new PasoAPaso(Ventana.diagramas,Ventana.selec);
				paso.ejecutar(this,"gdb",codigo);
				paso.main();
				seleccion = false;
				Ventana.componentes.toolItem[12].setEnabled(true);
				text.setEditable(false);
				text.setBackground(Ventana.display.getSystemColor(SWT.COLOR_WHITE));
			}
		}
	}
	public void setInformation(int size){
		this.sizeTab = size;
	}
	public boolean getEnEjecucion(){
		return enEjecucion;
	}
	public void stopEjecucion(){
		toolItem[12].setEnabled(false);
		enEjecucion = false; 
		ejecucionDisable();
		if(seleccion){
			eje.stopEjecutar();
		}
		else{
			disablePasoAPaso(false);
			paso.stopEjecutar();
			paso.colaConexiones.clear();
			int diag = diagramas.selec.getSeleccionDigrama();
			paso.tab.selec.setSeleccionDiagrama(paso.a.GetId());
			paso.limpiarPasoAnterior();
			diagramas.getHoja().pasoInicio=false;
			diagramas.getHoja().addFigure();
			paso.tab.selec.setSeleccionDiagrama(diag);
			text.setEditable(true);
		}
		eliminarArchivos();
	}
	public void eliminarArchivos(){
		try{
			File file = new File("main.exe");
			File file2 = new File("main.cpp");
			while(file.exists()){
				file.delete();	
			}
			while(file2.exists()){
				file2.delete();
			}
        } 
        catch(Exception e){
        }
	}
	public void next(){
		if(enEjecucion){
			toolItem[12].setEnabled(false);
			paso.sendNext();
		}
		final Timer timer = new Timer();
		TimerTask timerTask = new TimerTask(){
			public void run() {
				Ventana.display.syncExec (new Runnable () {
					public void run () {
						Ventana.componentes.toolItem[12].setEnabled(true);
					}
				});
	        	timer.cancel();
	        }
	     };
	     timer.schedule(timerTask,100);
	}
	public void disablePasoAPaso(boolean disable){
		if(disable){
			isPasoAPaso = true;
			diagramas.selec.setFiguraSeleccionada(-1);
			diagramas.getHoja().addFigure();
			toolItem[3].setEnabled(false);
			toolItem[4].setEnabled(false);
			toolItem[5].setEnabled(false);
			toolItem[6].setEnabled(false);
			toolItem[7].setEnabled(false);
			toolItem[10].setEnabled(false);
			toolItem[11].setEnabled(false);
			toolItem[13].setEnabled(true);
			toolItem[14].setEnabled(false);
			toolItem[15].setEnabled(false);
			toolItem[16].setEnabled(false);
			boton[0].setEnabled(false);
			boton[1].setEnabled(false);
			boton[2].setEnabled(false);
			boton[3].setEnabled(false);
			boton[4].setEnabled(false);
			boton[5].setEnabled(false);
			Ventana.If.setEnabled(false);
			Ventana.Proceso.setEnabled(false);
			Ventana.Entrada.setEnabled(false);
			Ventana.Salida.setEnabled(false);
			Ventana.For.setEnabled(false);
			Ventana.While.setEnabled(false);
			Ventana.exportar.setEnabled(false);
			Ventana.compilar.setEnabled(false);
			Ventana.Eliminar.setEnabled(false);
			Ventana.pasoApaso.setEnabled(false);
			
		}
		else{
			isPasoAPaso = false;
			diagramas.getHoja().addFigure();
			toolItem[3].setEnabled(true);
			toolItem[10].setEnabled(true);
			toolItem[11].setEnabled(true);
			toolItem[13].setEnabled(false);
			toolItem[14].setEnabled(true);
			toolItem[15].setEnabled(true);
			toolItem[16].setEnabled(true);
			boton[0].setEnabled(true);
			boton[1].setEnabled(true);
			boton[2].setEnabled(true);
			boton[3].setEnabled(true);
			boton[4].setEnabled(true);
			boton[5].setEnabled(true);
			Ventana.If.setEnabled(true);
			Ventana.Proceso.setEnabled(true);
			Ventana.Entrada.setEnabled(true);
			Ventana.Salida.setEnabled(true);
			Ventana.For.setEnabled(true);
			Ventana.While.setEnabled(true);
			Ventana.exportar.setEnabled(true);
			Ventana.compilar.setEnabled(true);
			Ventana.Eliminar.setEnabled(true);
			Ventana.pasoApaso.setEnabled(true);
		}
	}
	public void disableAll(boolean disable){
		toolItem[3].setEnabled(disable);
		toolItem[8].setEnabled(disable);
		toolItem[9].setEnabled(disable);
		toolItem[10].setEnabled(disable);
		toolItem[11].setEnabled(disable);
		toolItem[14].setEnabled(disable);
		toolItem[15].setEnabled(disable);
		toolItem[16].setEnabled(disable);
		boton[0].setEnabled(disable);
		boton[1].setEnabled(disable);
		boton[2].setEnabled(disable);
		boton[3].setEnabled(disable);
		boton[4].setEnabled(disable);
		boton[5].setEnabled(disable);
		Ventana.If.setEnabled(disable);
		Ventana.Proceso.setEnabled(disable);
		Ventana.Entrada.setEnabled(disable);
		Ventana.Salida.setEnabled(disable);
		Ventana.For.setEnabled(disable);
		Ventana.While.setEnabled(disable);
		Ventana.exportar.setEnabled(disable);
		Ventana.compilar.setEnabled(disable);
		Ventana.Eliminar.setEnabled(disable);
		Ventana.pasoApaso.setEnabled(disable);
		Ventana.guardarComo.setEnabled(disable);
		Ventana.generarCodigo.setEnabled(disable);
		guardarDisable(disable);
	}
}
