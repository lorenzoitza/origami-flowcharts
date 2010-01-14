package origami.graphics.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import origami.graphics.listeners.AddDecisionFigureListener;
import origami.graphics.listeners.AddForFigureListener;
import origami.graphics.listeners.AddInputFigureListener;
import origami.graphics.listeners.AddOutputFigureListener;
import origami.graphics.listeners.AddSentenceFigureListener;
import origami.graphics.listeners.AddWhileFigureListener;
import origami.graphics.listeners.CompileListener;
import origami.graphics.listeners.ExportToCListener;
import origami.graphics.listeners.ExportToCPPListener;
import origami.graphics.listeners.ExportToEXEListener;
import origami.graphics.listeners.ExportToImageListener;
import origami.graphics.listeners.NewDiagramListener;
import origami.graphics.listeners.OpenDiagramListener;
import origami.graphics.listeners.ResetDiagramListener;
import origami.graphics.listeners.SaveAsDiagramListener;
import origami.graphics.listeners.SaveDiagramListener;
import origami.graphics.listeners.StepByStepListener;
import origami.graphics.listeners.ViewAboutListener;
import origami.graphics.listeners.ViewCodeCListener;
import origami.graphics.listeners.ViewConsoleListener;
import origami.graphics.listeners.ViewExamplesListener;
import origami.graphics.listeners.ViewFiguresBarListener;
import origami.graphics.listeners.ViewHelpContentsListener;
import origami.graphics.listeners.ViewTabsListener;
import origami.graphics.listeners.ViewToolbarListener;


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

    private static ContextualMenu _editMenu;

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
	openMenuItem.addSelectionListener(new OpenDiagramListener());
	
	saveMenuItem = new MenuItem(fileMenu, SWT.PUSH);
	saveMenuItem.setText("Guardar                     Ctrl+G");
	saveMenuItem.addSelectionListener(new SaveDiagramListener());
	
	saveAsMenuItem = new MenuItem(fileMenu, SWT.PUSH);
	saveAsMenuItem.setText("Guardar como...                 ");
	saveAsMenuItem.addSelectionListener(new SaveAsDiagramListener());
	
	MenuItem newDiagramMenuItem = new MenuItem(fileMenu, SWT.PUSH);
	newDiagramMenuItem.setText("Nuevo Diagrama        Ctrl+N");
	newDiagramMenuItem.addSelectionListener(new NewDiagramListener());
	
	new MenuItem(fileMenu, SWT.SEPARATOR);
	
	Menu exportMenu = new Menu(fileMenu);
	
	exportMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
	exportMenuItem.setText("Exportar");
	exportMenuItem.setMenu(exportMenu);
	
	MenuItem exportToCMenuItem = new MenuItem(exportMenu, SWT.CASCADE);
	exportToCMenuItem.setText("Codigo C");
	exportToCMenuItem.addSelectionListener(new ExportToCListener());
	
	MenuItem exportToCPPMenuItem = new MenuItem(exportMenu, SWT.CASCADE);
	exportToCPPMenuItem.setText("Codigo C++");
	exportToCPPMenuItem.addSelectionListener(new ExportToCPPListener());
	
	MenuItem exportToEXEMenuItem = new MenuItem(exportMenu, SWT.CASCADE);
	exportToEXEMenuItem.setText("Ejecutable");
	exportToEXEMenuItem.addSelectionListener(new ExportToEXEListener());
	
	MenuItem exportToImageMenuItem = new MenuItem(exportMenu, SWT.CASCADE);
	exportToImageMenuItem.setText("Imagen JPG");
	exportToImageMenuItem.addSelectionListener(new ExportToImageListener());
	
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
	_editMenu = new ContextualMenu(editMenu);
	_editMenu.createMenu();
	editMenuItem.setMenu(editMenu);
    }

    private void initFiguresMenu() {
	Menu figuresMenu = new Menu(shell, SWT.DROP_DOWN);
	
	MenuItem figuresMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
	figuresMenuItem.setText("Figuras ");
	figuresMenuItem.setMenu(figuresMenu);
	
	inputMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	inputMenuItem.setText("Entrada   ");
	inputMenuItem.addSelectionListener(new AddInputFigureListener(display));
	
	sentenceMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	sentenceMenuItem.setText("Expresion     ");
	sentenceMenuItem.addSelectionListener(new AddSentenceFigureListener(display));
	
	decisionMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	decisionMenuItem.setText("Decisin   ");
	decisionMenuItem.addSelectionListener(new AddDecisionFigureListener(display));
	
	whileMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	whileMenuItem.setText("Ciclo Mientras");
	whileMenuItem.addSelectionListener(new AddWhileFigureListener(display));
	
	forMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	forMenuItem.setText("Ciclo Para  ");
	forMenuItem.addSelectionListener(new AddForFigureListener(display));
	
	outputMenuItem = new MenuItem(figuresMenu, SWT.PUSH);
	outputMenuItem.setText("Salida   ");
	outputMenuItem.addSelectionListener(new AddOutputFigureListener(display));
    }

    private void initViewMenu() {
	Menu viewMenu = new Menu(shell, SWT.DROP_DOWN);
	
	MenuItem viewMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
	viewMenuItem.setText("Ver ");
	viewMenuItem.setMenu(viewMenu);
	
	MenuItem toolbarMenuItem = new MenuItem(viewMenu, SWT.CHECK);
	toolbarMenuItem.setText(" Barra De Herramientas   ");
	toolbarMenuItem.setSelection(true);
	toolbarMenuItem.addSelectionListener(new ViewToolbarListener());
	
	MenuItem tabsMenuItem = new MenuItem(viewMenu, SWT.CHECK);
	tabsMenuItem.setText(" Barra De Diagramas       ");
	tabsMenuItem.setSelection(true);
	tabsMenuItem.addSelectionListener(new ViewTabsListener());
	
	figuresBarMenuItem = new MenuItem(viewMenu, SWT.CHECK);
	figuresBarMenuItem.setText(" Barra De Figuras       ");
	figuresBarMenuItem.setSelection(true);
	figuresBarMenuItem.addSelectionListener(new ViewFiguresBarListener());
	
	consoleMenuItem = new MenuItem(viewMenu, SWT.CHECK);
	consoleMenuItem.setText(" Consola     ");
	consoleMenuItem.addSelectionListener(new ViewConsoleListener());
    }

    private void initOptionsMenu() {
	Menu optionsMenu = new Menu(shell, SWT.DROP_DOWN);
	
	MenuItem optionsMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
	optionsMenuItem.setText("Opciones ");
	optionsMenuItem.setMenu(optionsMenu);
	
	buildCodeMenuItem = new MenuItem(optionsMenu, SWT.PUSH);
	buildCodeMenuItem.setText("Codigo Fuente C        F4");
	buildCodeMenuItem.addSelectionListener(new ViewCodeCListener());
	
	compileMenuItem = new MenuItem(optionsMenu, SWT.PUSH);
	compileMenuItem.setText("Compilar/Ejecutar      F5");
	compileMenuItem.addSelectionListener(new CompileListener());
	
	resetDiagramMenuItem = new MenuItem(optionsMenu, SWT.PUSH);
	resetDiagramMenuItem.setText("Restablecer diagrama          F3");
	resetDiagramMenuItem.addSelectionListener(new ResetDiagramListener());
	
	stepByStepMenuItem = new MenuItem(optionsMenu, SWT.PUSH);
	stepByStepMenuItem.setText("Paso A Paso");
	stepByStepMenuItem.addSelectionListener(new StepByStepListener());
    }

    private void initHelpMenu() {
	Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
	
	MenuItem helpMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
	helpMenuItem.setText("Ayuda");
	helpMenuItem.setMenu(helpMenu);
	
	MenuItem helpContentsMenuItem = new MenuItem(helpMenu, SWT.PUSH);
	helpContentsMenuItem.setText("Contenidos de Ayuda                             F1");
	helpContentsMenuItem.addSelectionListener(new ViewHelpContentsListener());
	
	MenuItem examplesMenuItem = new MenuItem(helpMenu, SWT.PUSH);
	examplesMenuItem.setText("Ejemplos");
	examplesMenuItem.addSelectionListener(new ViewExamplesListener());

	new MenuItem(helpMenu, SWT.SEPARATOR);	
	
	MenuItem aboutMenuItem = new MenuItem(helpMenu, SWT.PUSH);
	aboutMenuItem.setText("Acerca de Origami...      F2");
	aboutMenuItem.addSelectionListener(new ViewAboutListener());
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

    public static ContextualMenu get_editMenu() {
        return _editMenu;
    }
    
}
