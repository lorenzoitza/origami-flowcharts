package Grafico;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import ui.actions.AddDecisionFigureAction;
import ui.actions.AddForFigureAction;
import ui.actions.AddInputFigureAction;
import ui.actions.AddOutputFigureAction;
import ui.actions.AddSentenceFigureAction;
import ui.actions.AddWhileFigureAction;
import ui.actions.ExportToCAction;
import ui.actions.ExportToCPPAction;
import ui.actions.ExportToEXEAction;
import ui.actions.ExportToImageAction;
import ui.actions.NewDiagramAction;
import ui.actions.OpenDiagramAction;
import ui.actions.SaveDiagramAction;
import ui.actions.SaveDiagramAsAction;
import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Eventos.EventoMenuContextual;
import Administracion.Funcionalidad.CodeCompiler;
import Administracion.Funcionalidad.Codigo.Instruccion;
import Grafico.Figuras.CircleFigure;
import Grafico.Help.AboutWindow;
import Grafico.Help.HelpWindow;


public class CustomeMenu {
    public static MenuItem consoleMenuItem;

    public static MenuItem figuresBarMenuItem;

    public static MenuItem decisionMenuItem;

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

    public static EventoMenuContextual _editMenu;
    
    public static Menu mainMenu;
    
    private Shell _shell;
    
    private Display _display;
    
    private MainWindow mainWindow;
    
    public static AdminSeleccion _selectionAdministrator;
    
    public CustomeMenu(Shell shell, Display display,MainWindow mainWindow, AdminSeleccion _selectionAdministrator ){
	_shell = shell;
	_display = display;
	mainMenu = new Menu(_shell, SWT.BAR);
	this.mainWindow = mainWindow;
	this._selectionAdministrator = _selectionAdministrator;
    }

    public void createMenu(){
	initFileMenu();
	initEditMenu();
	initFiguresMenu();
	initViewMenu();
	initOptionsMenu();
	initHelpMenu();
    }
    
    private void initFileMenu() {
	MenuItem fileMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
	fileMenuItem.setText("Archivo ");
	Menu fileMenu = new Menu(_shell, SWT.DROP_DOWN);
	fileMenuItem.setMenu(fileMenu);
	MenuItem openMenuItem = new MenuItem(fileMenu, SWT.PUSH);
	openMenuItem.setText("Abrir...                      Ctrl+A");
	saveMenuItem = new MenuItem(fileMenu, SWT.PUSH);
	saveMenuItem.setText("Guardar                     Ctrl+G");
	saveAsMenuItem = new MenuItem(fileMenu, SWT.PUSH);
	saveAsMenuItem.setText("Guardar como...                 ");
	MenuItem newDiagramMenuItem = new MenuItem(fileMenu, SWT.PUSH);
	newDiagramMenuItem.setText("Nuevo Diagrama        Ctrl+N");
	new MenuItem(fileMenu, SWT.SEPARATOR);
	Menu exportMenu = new Menu(fileMenu);
	exportMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
	exportMenuItem.setText("Exportar");
	exportMenuItem.setMenu(exportMenu);
	MenuItem exportToCMenuItem = new MenuItem(exportMenu, SWT.CASCADE);
	exportToCMenuItem.setText("Codigo C");
	MenuItem exportToCPPMenuItem = new MenuItem(exportMenu, SWT.CASCADE);
	exportToCPPMenuItem.setText("Codigo C++");
	MenuItem exportToEXEMenuItem = new MenuItem(exportMenu, SWT.CASCADE);
	exportToEXEMenuItem.setText("Ejecutable");
	MenuItem exportToImageMenuItem = new MenuItem(exportMenu, SWT.CASCADE);
	exportToImageMenuItem.setText("Imagen JPG");
	new MenuItem(fileMenu, SWT.SEPARATOR);
	MenuItem exitMenuItem = new MenuItem(fileMenu, SWT.PUSH);
	exitMenuItem.setText("Salir                            Alt+F4");

	openMenuItem
		.addSelectionListener(new OpenDiagramAction(MainWindow._diagrams, mainWindow));
	saveMenuItem
		.addSelectionListener(new SaveDiagramAction(MainWindow._diagrams, mainWindow));
	saveAsMenuItem.addSelectionListener(new SaveDiagramAsAction(MainWindow._diagrams,
		mainWindow));
	newDiagramMenuItem.addSelectionListener(new NewDiagramAction(MainWindow._diagrams,
		mainWindow.getComponents(), mainWindow));
	exportToCMenuItem.addSelectionListener(new ExportToCAction(MainWindow._diagrams,
		mainWindow));
	exportToCPPMenuItem.addSelectionListener(new ExportToCPPAction(
		MainWindow._diagrams, mainWindow));
	exportToEXEMenuItem.addSelectionListener(new ExportToEXEAction(
		MainWindow._diagrams, mainWindow));
	exportToImageMenuItem.addSelectionListener(new ExportToImageAction(
		MainWindow._diagrams, mainWindow));
	exitMenuItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent e) {
		_shell.close();
	    }
	});
    }

    private void initEditMenu() {
	MenuItem editMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
	editMenuItem.setText("Edicion ");
	Menu editMenu = new Menu(_shell, SWT.DROP_DOWN);
	_editMenu = new EventoMenuContextual();
	_editMenu.menu(editMenu);
	editMenuItem.setMenu(editMenu);

    }

    private void initFiguresMenu() {
	MenuItem figuresMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
	figuresMenuItem.setText("Figuras ");
	Menu figuresMenu = new Menu(_shell, SWT.DROP_DOWN);
	figuresMenuItem.setMenu(figuresMenu);
	inputMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	inputMenuItem.setText("Entrada   ");
	sentenceMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	sentenceMenuItem.setText("Expresion     ");
	decisionMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	decisionMenuItem.setText("Decisin   ");
	whileMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	whileMenuItem.setText("Ciclo Mientras");
	forMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	forMenuItem.setText("Ciclo Para  ");
	outputMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	outputMenuItem.setText("Salida   ");

	inputMenuItem.addSelectionListener(new AddInputFigureAction(mainWindow));
	decisionMenuItem
		.addSelectionListener(new AddDecisionFigureAction(mainWindow));
	sentenceMenuItem
		.addSelectionListener(new AddSentenceFigureAction(mainWindow));
	outputMenuItem.addSelectionListener(new AddOutputFigureAction(mainWindow));
	forMenuItem.addSelectionListener(new AddForFigureAction(mainWindow));
	whileMenuItem.addSelectionListener(new AddWhileFigureAction(mainWindow));

    }

    private void initViewMenu() {
	MenuItem viewMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
	viewMenuItem.setText("Ver ");
	Menu viewMenu = new Menu(_shell, SWT.DROP_DOWN);
	viewMenuItem.setMenu(viewMenu);
	MenuItem toolbarMenuItem = new MenuItem(viewMenu, SWT.CHECK);
	toolbarMenuItem.setText(" Barra De Herramientas   ");
	toolbarMenuItem.setSelection(true);
	MenuItem tabsMenuItem = new MenuItem(viewMenu, SWT.CHECK);
	tabsMenuItem.setText(" Barra De Diagramas       ");
	tabsMenuItem.setSelection(true);
	figuresBarMenuItem = new MenuItem(viewMenu, SWT.CHECK);
	figuresBarMenuItem.setText(" Barra De Figuras       ");
	figuresBarMenuItem.setSelection(true);
	consoleMenuItem = new MenuItem(viewMenu, SWT.CHECK);
	consoleMenuItem.setText(" Consola     ");

	toolbarMenuItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent e) {
		MenuItem widget = (MenuItem) e.widget;
		mainWindow.getComponents().addBarraDeHerramientas(widget.getSelection());
	    }
	});
	figuresBarMenuItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent e) {
		MenuItem widget = (MenuItem) e.widget;
		mainWindow.getComponents().addBarraFiguras(widget.getSelection());
	    }
	});
	tabsMenuItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent e) {
		MenuItem widget = (MenuItem) e.widget;
		mainWindow.getComponents().addTabFolder(widget.getSelection());
	    }
	});
	consoleMenuItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent e) {
		MenuItem widget = (MenuItem) e.widget;
		mainWindow.getComponents().moverConsola(widget.getSelection());
	    }
	});
    }

    private void initOptionsMenu() {
	MenuItem optionsMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
	optionsMenuItem.setText("Opciones ");
	Menu optionsMenu = new Menu(_shell, SWT.DROP_DOWN);
	buildCodeMenuItem = new MenuItem(optionsMenu, SWT.PUSH);
	buildCodeMenuItem.setText("Codigo Fuente C        F4");
	compileMenuItem = new MenuItem(optionsMenu, SWT.PUSH);
	compileMenuItem.setText("Compilar/Ejecutar      F5");
	resetDiagramMenuItem = new MenuItem(optionsMenu, SWT.PUSH);
	resetDiagramMenuItem.setText("Restablecer diagrama          F3");
	stepByStepMenuItem = new MenuItem(optionsMenu, SWT.PUSH);
	stepByStepMenuItem.setText("Paso A Paso");
	optionsMenuItem.setMenu(optionsMenu);

	stepByStepMenuItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent e) {
		if (!MainWindow._diagrams.getTabItem().getSave().isSave()) {
		    if (mainWindow.getComponents().guardar()) {
			CodeCompiler codigo = new CodeCompiler(MainWindow._diagrams);
			codigo.main(false, false);
			if (codigo.isError) {
			    int aux = mainWindow.getComponents().text.getText().length();
			    if (aux >= 0) {
				mainWindow.getComponents().text.setText("");
			    }
			    // moverConsola(true);
			    mainWindow.getComponents().text.setText(codigo.errorTipe);
			    MainWindow._diagrams.getTabItem().getInfo().addInformation(
				    "/Ep - Error en el paso a paso:");
			    MainWindow._diagrams.getTabItem().getInfo().addInformation(
				    codigo.errorTipe);
			    codigo.deleteMainFiles();
			} else {
			    // moverConsola(true);
			    mainWindow.getComponents().disablePasoAPaso(true);
			    mainWindow.getComponents().ejecutar(false, codigo);
			    MainWindow._diagrams
				    .getTabItem()
				    .getInfo()
				    .addInformation(
					    "/P - Se inicio el paso a paso de manera correcta");
			}
		    }
		} else {
		    CodeCompiler codigo = new CodeCompiler(MainWindow._diagrams);
		    codigo.main(false, false);
		    if (codigo.isError) {
			int aux = mainWindow.getComponents().text.getText().length();
			if (aux >= 0) {
			    mainWindow.getComponents().text.setText("");
			}
			// moverConsola(true);
			mainWindow.getComponents().text.setText(codigo.errorTipe);
			MainWindow._diagrams.getTabItem().getInfo().addInformation(
				"/Ep - Error en el paso a paso:");
			MainWindow._diagrams.getTabItem().getInfo().addInformation(
				codigo.errorTipe);
			codigo.deleteMainFiles();
		    } else {
			// moverConsola(true);
			mainWindow.getComponents().disablePasoAPaso(true);
			mainWindow.getComponents().ejecutar(false, codigo);
			MainWindow._diagrams
				.getTabItem()
				.getInfo()
				.addInformation(
					"/P - Se inicio el paso a paso de manera correcta");
		    }
		}
	    }
	});

	buildCodeMenuItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent e) {
		Instruccion codigo = new Instruccion();
		codigo.main(MainWindow._diagrams.getHoja().getDiagrama(), true);
		codigo.ventana(_display);
	    }
	});
	compileMenuItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent e) {
		CodeCompiler codigo = new CodeCompiler(MainWindow._diagrams);
		if (mainWindow.getComponents().getEnEjecucion()) {
		    mainWindow.getComponents().stopEjecucion();
		}
		codigo.main(false, true);
		if (codigo.isError) {
		    int aux = mainWindow.getComponents().text.getText().length();
		    if (aux >= 0) {
			mainWindow.getComponents().text.setText("");
		    }
		    mainWindow.getComponents().text.setText(codigo.errorTipe);
		    MainWindow._diagrams.getTabItem().getInfo().addInformation(
			    "/Ec - Error en la compilacion:");
		    MainWindow._diagrams.getTabItem().getInfo().addInformation(
			    codigo.errorTipe);
		    codigo.deleteMainFiles();
		} else {
		    mainWindow.getComponents().ejecutar(true, codigo);
		    MainWindow._diagrams.getTabItem().getInfo().addInformation(
			    "/C - Se Compilo el diagrama de manera correcta");
		}
		if (!consoleMenuItem.getSelection()) {
		    consoleMenuItem.setSelection(true);
		    mainWindow.getComponents().moverConsola(true);
		}
	    }
	});

	resetDiagramMenuItem.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent e) {
		_selectionAdministrator.setFiguraSeleccionada(0);
		for (int y = MainWindow._diagrams.getHoja().getSizeDiagrama() - 1; y > 0; y--) {
		    MainWindow._diagrams.getHoja().removeFigureIndexOf(y);
		}
		CircleFigure fin = new CircleFigure();
		MainWindow._diagrams.getHoja().getDiagrama().add(fin);
		fin.setMesagge("  Fin");
		MainWindow._diagrams.getHoja().resetScrollBar();
		MainWindow._diagrams.getHoja().addFigure();
		MainWindow._diagrams.getHoja().guardarRetroceso();
		MainWindow._diagrams.getTabItem().getSave().setSave(false);
	    }
	});

    }

    private void initHelpMenu() {
	MenuItem helpMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
	helpMenuItem.setText("Ayuda");
	Menu helpMenu = new Menu(_shell, SWT.DROP_DOWN);
	MenuItem helpContentsMenuItem = new MenuItem(helpMenu, SWT.PUSH);
	helpContentsMenuItem
		.setText("Contenidos de Ayuda                             F1");
	MenuItem examplesMenuItem = new MenuItem(helpMenu, SWT.PUSH);
	examplesMenuItem.setText("Ejemplos");
	new MenuItem(helpMenu, SWT.SEPARATOR);
	MenuItem aboutMenuItem = new MenuItem(helpMenu, SWT.PUSH);
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
		FileDialog dialog = new FileDialog(_shell, SWT.OPEN);
		dialog.setFilterExtensions(new String[] { "*.Org", "*.*" });
		dialog.setFilterPath("ejemplos\\");
		String archivo = dialog.open();
		if (archivo != null) {
		    if (MainWindow._diagrams.getHoja().getSizeDiagrama() == 0) {
			String archivo2 = dialog.getFileName();
			int pos = archivo2.indexOf('.');
			String name = archivo2.substring(0, pos);
			MainWindow._diagrams.cambiarNombre(name);
			MainWindow._diagrams.abrir(archivo, mainWindow.getSerializer());
		    } else {
			_selectionAdministrator.setFiguraSeleccionada(0);
			MainWindow._diagrams.getHoja().openFile(archivo);
			archivo = dialog.getFileName();
			int pos = archivo.indexOf('.');
			String name = archivo.substring(0, pos);
			MainWindow._diagrams.cambiarNombre(name);
		    }
		}
	    }
	});

    }
}
