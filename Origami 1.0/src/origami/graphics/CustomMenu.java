package origami.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import origami.ui.listeners.AddDecisionFigureAction;
import origami.ui.listeners.AddForFigureAction;
import origami.ui.listeners.AddInputFigureAction;
import origami.ui.listeners.AddOutputFigureAction;
import origami.ui.listeners.AddSentenceFigureAction;
import origami.ui.listeners.AddWhileFigureAction;
import origami.ui.listeners.CompileAction;
import origami.ui.listeners.ContextualMenuEvent;
import origami.ui.listeners.ExportToCAction;
import origami.ui.listeners.ExportToCPPAction;
import origami.ui.listeners.ExportToEXEAction;
import origami.ui.listeners.ExportToImageAction;
import origami.ui.listeners.NewDiagramAction;
import origami.ui.listeners.OpenDiagramAction;
import origami.ui.listeners.ResetDiagramAction;
import origami.ui.listeners.SaveDiagramAction;
import origami.ui.listeners.SaveDiagramAsAction;
import origami.ui.listeners.StepByStepAction;
import origami.ui.listeners.ViewAboutAction;
import origami.ui.listeners.ViewCodeCAction;
import origami.ui.listeners.ViewConsoleAction;
import origami.ui.listeners.ViewExamplesAction;
import origami.ui.listeners.ViewFiguresBarAction;
import origami.ui.listeners.ViewHelpContentsAction;
import origami.ui.listeners.ViewTabsAction;
import origami.ui.listeners.ViewToolbarAction;


public class CustomMenu {
    private Shell shell;
    
    private Display display;
    
    private Menu mainMenu;
    
    private MenuItem figuresBarMenuItem;

    private MenuItem decisionMenuItem;

    private MenuItem sentenceMenuItem;

    private MenuItem inputMenuItem;

    private MenuItem outputMenuItem;

    private MenuItem forMenuItem;

    private MenuItem whileMenuItem;

    private MenuItem exportMenuItem;

    private MenuItem resetDiagramMenuItem;

    private MenuItem stepByStepMenuItem;

    private MenuItem compileMenuItem;

    private MenuItem saveMenuItem;

    private MenuItem saveAsMenuItem;

    private MenuItem buildCodeMenuItem;

    private static MenuItem consoleMenuItem;

    private static ContextualMenuEvent _editMenu;

    public CustomMenu(Shell shell, Display display){
	this.shell = shell;
	
	this.display = display;
	
	mainMenu = new Menu(shell, SWT.BAR);
	
	createMenu();
    }

    private void createMenu(){
	initFileMenu();
	
	initEditMenu();
	
	initFiguresMenu();
	
	initViewMenu();
	
	initOptionsMenu();
	
	initHelpMenu();
    }
    
    private void initFileMenu() {
	Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
	
	MenuItem fileMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
	fileMenuItem.setText("Archivo ");
	fileMenuItem.setMenu(fileMenu);
	
	MenuItem openMenuItem = new MenuItem(fileMenu, SWT.PUSH);
	openMenuItem.setText("Abrir...                      Ctrl+A");
	openMenuItem.addSelectionListener(new OpenDiagramAction());
	
	saveMenuItem = new MenuItem(fileMenu, SWT.PUSH);
	saveMenuItem.setText("Guardar                     Ctrl+G");
	saveMenuItem.addSelectionListener(new SaveDiagramAction());
	
	saveAsMenuItem = new MenuItem(fileMenu, SWT.PUSH);
	saveAsMenuItem.setText("Guardar como...                 ");
	saveAsMenuItem.addSelectionListener(new SaveDiagramAsAction());
	
	MenuItem newDiagramMenuItem = new MenuItem(fileMenu, SWT.PUSH);
	newDiagramMenuItem.setText("Nuevo Diagrama        Ctrl+N");
	newDiagramMenuItem.addSelectionListener(new NewDiagramAction());
	
	new MenuItem(fileMenu, SWT.SEPARATOR);
	
	Menu exportMenu = new Menu(fileMenu);
	
	exportMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
	exportMenuItem.setText("Exportar");
	exportMenuItem.setMenu(exportMenu);
	
	MenuItem exportToCMenuItem = new MenuItem(exportMenu, SWT.CASCADE);
	exportToCMenuItem.setText("Codigo C");
	exportToCMenuItem.addSelectionListener(new ExportToCAction());
	
	MenuItem exportToCPPMenuItem = new MenuItem(exportMenu, SWT.CASCADE);
	exportToCPPMenuItem.setText("Codigo C++");
	exportToCPPMenuItem.addSelectionListener(new ExportToCPPAction());
	
	MenuItem exportToEXEMenuItem = new MenuItem(exportMenu, SWT.CASCADE);
	exportToEXEMenuItem.setText("Ejecutable");
	exportToEXEMenuItem.addSelectionListener(new ExportToEXEAction());
	
	MenuItem exportToImageMenuItem = new MenuItem(exportMenu, SWT.CASCADE);
	exportToImageMenuItem.setText("Imagen JPG");
	exportToImageMenuItem.addSelectionListener(new ExportToImageAction());
	
	new MenuItem(fileMenu, SWT.SEPARATOR);
	
	MenuItem exitMenuItem = new MenuItem(fileMenu, SWT.PUSH);
	exitMenuItem.setText("Salir                            Alt+F4");
	exitMenuItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		shell.close();
	    }
	});
    }

    private void initEditMenu() {
	MenuItem editMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
	editMenuItem.setText("Edicion ");
	Menu editMenu = new Menu(shell, SWT.DROP_DOWN);
	_editMenu = new ContextualMenuEvent();
	_editMenu.menu(editMenu);
	editMenuItem.setMenu(editMenu);
    }

    private void initFiguresMenu() {
	Menu figuresMenu = new Menu(shell, SWT.DROP_DOWN);
	
	MenuItem figuresMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
	figuresMenuItem.setText("Figuras ");
	figuresMenuItem.setMenu(figuresMenu);
	
	inputMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	inputMenuItem.setText("Entrada   ");
	inputMenuItem.addSelectionListener(new AddInputFigureAction(display));
	
	sentenceMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	sentenceMenuItem.setText("Expresion     ");
	sentenceMenuItem.addSelectionListener(new AddSentenceFigureAction(display));
	
	decisionMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	decisionMenuItem.setText("Decisin   ");
	decisionMenuItem.addSelectionListener(new AddDecisionFigureAction(display));
	
	whileMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	whileMenuItem.setText("Ciclo Mientras");
	whileMenuItem.addSelectionListener(new AddWhileFigureAction(display));
	
	forMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	forMenuItem.setText("Ciclo Para  ");
	forMenuItem.addSelectionListener(new AddForFigureAction(display));
	
	outputMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	outputMenuItem.setText("Salida   ");
	outputMenuItem.addSelectionListener(new AddOutputFigureAction(display));
    }

    private void initViewMenu() {
	Menu viewMenu = new Menu(shell, SWT.DROP_DOWN);
	
	MenuItem viewMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
	viewMenuItem.setText("Ver ");
	viewMenuItem.setMenu(viewMenu);
	
	MenuItem toolbarMenuItem = new MenuItem(viewMenu, SWT.CHECK);
	toolbarMenuItem.setText(" Barra De Herramientas   ");
	toolbarMenuItem.setSelection(true);
	toolbarMenuItem.addSelectionListener(new ViewToolbarAction());
	
	MenuItem tabsMenuItem = new MenuItem(viewMenu, SWT.CHECK);
	tabsMenuItem.setText(" Barra De Diagramas       ");
	tabsMenuItem.setSelection(true);
	tabsMenuItem.addSelectionListener(new ViewTabsAction());
	
	figuresBarMenuItem = new MenuItem(viewMenu, SWT.CHECK);
	figuresBarMenuItem.setText(" Barra De Figuras       ");
	figuresBarMenuItem.setSelection(true);
	figuresBarMenuItem.addSelectionListener(new ViewFiguresBarAction());
	
	consoleMenuItem = new MenuItem(viewMenu, SWT.CHECK);
	consoleMenuItem.setText(" Consola     ");
	consoleMenuItem.addSelectionListener(new ViewConsoleAction());
    }

    private void initOptionsMenu() {
	Menu optionsMenu = new Menu(shell, SWT.DROP_DOWN);
	
	MenuItem optionsMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
	optionsMenuItem.setText("Opciones ");
	optionsMenuItem.setMenu(optionsMenu);
	
	buildCodeMenuItem = new MenuItem(optionsMenu, SWT.PUSH);
	buildCodeMenuItem.setText("Codigo Fuente C        F4");
	buildCodeMenuItem.addSelectionListener(new ViewCodeCAction());
	
	compileMenuItem = new MenuItem(optionsMenu, SWT.PUSH);
	compileMenuItem.setText("Compilar/Ejecutar      F5");
	compileMenuItem.addSelectionListener(new CompileAction());
	
	resetDiagramMenuItem = new MenuItem(optionsMenu, SWT.PUSH);
	resetDiagramMenuItem.setText("Restablecer diagrama          F3");
	resetDiagramMenuItem.addSelectionListener(new ResetDiagramAction());
	
	stepByStepMenuItem = new MenuItem(optionsMenu, SWT.PUSH);
	stepByStepMenuItem.setText("Paso A Paso");
	stepByStepMenuItem.addSelectionListener(new StepByStepAction());
    }

    private void initHelpMenu() {
	Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
	
	MenuItem helpMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
	helpMenuItem.setText("Ayuda");
	helpMenuItem.setMenu(helpMenu);
	
	MenuItem helpContentsMenuItem = new MenuItem(helpMenu, SWT.PUSH);
	helpContentsMenuItem.setText("Contenidos de Ayuda                             F1");
	helpContentsMenuItem.addSelectionListener(new ViewHelpContentsAction());
	
	MenuItem examplesMenuItem = new MenuItem(helpMenu, SWT.PUSH);
	examplesMenuItem.setText("Ejemplos");
	examplesMenuItem.addSelectionListener(new ViewExamplesAction());

	new MenuItem(helpMenu, SWT.SEPARATOR);	
	
	MenuItem aboutMenuItem = new MenuItem(helpMenu, SWT.PUSH);
	aboutMenuItem.setText("Acerca de Origami...      F2");
	aboutMenuItem.addSelectionListener(new ViewAboutAction());
    }
    
    public void setEnabledStepByStepMenuItems(boolean isEnabled) {
	decisionMenuItem.setEnabled(isEnabled);
	sentenceMenuItem.setEnabled(isEnabled);
	inputMenuItem.setEnabled(isEnabled);
	outputMenuItem.setEnabled(isEnabled);
	forMenuItem.setEnabled(isEnabled);
	whileMenuItem.setEnabled(isEnabled);
	exportMenuItem.setEnabled(isEnabled);
	compileMenuItem.setEnabled(isEnabled);
	resetDiagramMenuItem.setEnabled(isEnabled);
	stepByStepMenuItem.setEnabled(isEnabled);
    }
    
    public void setEnabledAllMenuItems(boolean isEnabled) {
	decisionMenuItem.setEnabled(isEnabled);
	sentenceMenuItem.setEnabled(isEnabled);
	inputMenuItem.setEnabled(isEnabled);
	outputMenuItem.setEnabled(isEnabled);
	forMenuItem.setEnabled(isEnabled);
	whileMenuItem.setEnabled(isEnabled);
	exportMenuItem.setEnabled(isEnabled);
	compileMenuItem.setEnabled(isEnabled);
	resetDiagramMenuItem.setEnabled(isEnabled);
	stepByStepMenuItem.setEnabled(isEnabled);
	saveAsMenuItem.setEnabled(isEnabled);
	buildCodeMenuItem.setEnabled(isEnabled);
    }
    
    public Menu getMainMenu() {
        return mainMenu;
    }

    public MenuItem getSaveMenuItem() {
        return saveMenuItem;
    }

    public static MenuItem getConsoleMenuItem() {
        return consoleMenuItem;
    }

    public static ContextualMenuEvent get_editMenu() {
        return _editMenu;
    }
    
}
