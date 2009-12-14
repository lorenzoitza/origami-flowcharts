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
import ui.actions.BuildCCodeAction;
import ui.actions.CompileAction;
import ui.actions.ExportToCAction;
import ui.actions.ExportToCPPAction;
import ui.actions.ExportToEXEAction;
import ui.actions.ExportToImageAction;
import ui.actions.NewDiagramAction;
import ui.actions.OpenDiagramAction;
import ui.actions.ResetDiagramAction;
import ui.actions.SaveDiagramAction;
import ui.actions.SaveDiagramAsAction;
import ui.actions.StepByStepAction;
import ui.actions.ViewAboutAction;
import ui.actions.ViewConsoleAction;
import ui.actions.ViewExamplesAction;
import ui.actions.ViewFiguresBarAction;
import ui.actions.ViewHelpContentsAction;
import ui.actions.ViewTabsAction;
import ui.actions.ViewToolbarAction;
import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Eventos.EventoMenuContextual;
import Administracion.Funcionalidad.CodeCompiler;
import Administracion.Funcionalidad.Codigo.Instruccion;
import Grafico.Figuras.CircleFigure;
import Grafico.Help.AboutWindow;
import Grafico.Help.HelpWindow;


public class CustomMenu {
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
    
    public CustomMenu(Shell shell, Display display,MainWindow mainWindow, AdminSeleccion _selectionAdministrator ){
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
	newDiagramMenuItem.addSelectionListener(new NewDiagramAction());
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

	toolbarMenuItem.addSelectionListener(new ViewToolbarAction(mainWindow));
	
	figuresBarMenuItem.addSelectionListener(new ViewFiguresBarAction(mainWindow));
	
	tabsMenuItem.addSelectionListener(new ViewTabsAction(mainWindow));
	
	consoleMenuItem.addSelectionListener(new ViewConsoleAction(mainWindow));
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

	stepByStepMenuItem
	.addSelectionListener(new StepByStepAction(MainWindow._diagrams, mainWindow));

	buildCodeMenuItem.addSelectionListener(new BuildCCodeAction(mainWindow._diagrams, mainWindow));
	compileMenuItem.addSelectionListener(new CompileAction(mainWindow._diagrams, mainWindow));

	resetDiagramMenuItem.addSelectionListener(new ResetDiagramAction(mainWindow._diagrams, mainWindow));

    }

    private void initHelpMenu() {
	MenuItem helpMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
	helpMenuItem.setText("Ayuda");
	Menu helpMenu = new Menu(_shell, SWT.DROP_DOWN);
	
	MenuItem helpContentsMenuItem = new MenuItem(helpMenu, SWT.PUSH);
	helpContentsMenuItem
		.setText("Contenidos de Ayuda                             F1");
	helpContentsMenuItem.addSelectionListener(new ViewHelpContentsAction());
	
	MenuItem examplesMenuItem = new MenuItem(helpMenu, SWT.PUSH);
	examplesMenuItem.setText("Ejemplos");
	examplesMenuItem.addSelectionListener(new ViewExamplesAction(mainWindow._diagrams,mainWindow));

	
	new MenuItem(helpMenu, SWT.SEPARATOR);
	
	
	MenuItem aboutMenuItem = new MenuItem(helpMenu, SWT.PUSH);
	aboutMenuItem.setText("Acerca de Origami...      F2");
	aboutMenuItem.addSelectionListener(new ViewAboutAction(mainWindow));

	helpMenuItem.setMenu(helpMenu);
	
    }
    public void disablePasoAPaso(boolean disable) {
	if (disable) {
		decisionMenuItem.setEnabled(false);
		sentenceMenuItem.setEnabled(false);
		inputMenuItem.setEnabled(false);
		outputMenuItem.setEnabled(false);
		forMenuItem.setEnabled(false);
		whileMenuItem.setEnabled(false);
		exportMenuItem.setEnabled(false);
		compileMenuItem.setEnabled(false);
		resetDiagramMenuItem.setEnabled(false);
		stepByStepMenuItem.setEnabled(false);
	} else {
		decisionMenuItem.setEnabled(true);
		sentenceMenuItem.setEnabled(true);
		inputMenuItem.setEnabled(true);
		outputMenuItem.setEnabled(true);
		forMenuItem.setEnabled(true);
		whileMenuItem.setEnabled(true);
		exportMenuItem.setEnabled(true);
		compileMenuItem.setEnabled(true);
		resetDiagramMenuItem.setEnabled(true);
		stepByStepMenuItem.setEnabled(true);
	}
    }
    public void disableAll(boolean disable) {
	decisionMenuItem.setEnabled(disable);
	sentenceMenuItem.setEnabled(disable);
	inputMenuItem.setEnabled(disable);
	outputMenuItem.setEnabled(disable);
	forMenuItem.setEnabled(disable);
	whileMenuItem.setEnabled(disable);
	exportMenuItem.setEnabled(disable);
	compileMenuItem.setEnabled(disable);
	resetDiagramMenuItem.setEnabled(disable);
	stepByStepMenuItem.setEnabled(disable);
	saveAsMenuItem.setEnabled(disable);
	buildCodeMenuItem.setEnabled(disable);
    }
}
