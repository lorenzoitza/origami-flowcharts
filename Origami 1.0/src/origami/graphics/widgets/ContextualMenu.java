package origami.graphics.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import origami.administration.ApplicationState;
import origami.administration.actions.AddFigureLogic;
import origami.administration.actions.ContextualMenuActions;
import origami.graphics.MainWindow;
import origami.graphics.figures.CircleFigure;

public class ContextualMenu{
    private Menu menu;
    
    private MenuItem cutMenuItem;
	
    private MenuItem copyMenuItem;
    
    private MenuItem pasteMenuItem;
    
    private MenuItem addMenuItem;
    
    private MenuItem deleteMenuItem;
    
    private MenuItem insertFigureMenuItem;

    public ContextualMenu(){
	this.menu = new Menu(MainWindow.shell,SWT.POP_UP);
    }
    
    public ContextualMenu(Menu menu){
	setMenu(menu);
    }
    
    public void createMenu(){
	cutMenuItem = new MenuItem(getMenu(),SWT.CASCADE);
	cutMenuItem.setText("Cortar                  Ctrl+X");
	cutMenuItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new ContextualMenuActions().Cortar(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getFiguraSeleccionada()));
	    }
	});
	
	copyMenuItem = new MenuItem(getMenu(),SWT.CASCADE);
	copyMenuItem.setText("Copiar                  Ctrl+C");
	copyMenuItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new ContextualMenuActions().Copiar(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getFiguraSeleccionada()));
	    }
	});
	
	pasteMenuItem = new MenuItem(getMenu(),SWT.CASCADE);
	pasteMenuItem.setText("Pegar                   Ctrl+V");
	pasteMenuItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new ContextualMenuActions().Pegar(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getFiguraSeleccionada()));
	    }
	});
	
	deleteMenuItem = new MenuItem(getMenu(),SWT.CASCADE);
	deleteMenuItem.setText("Eliminar                 Supr");
	deleteMenuItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new ContextualMenuActions().Eliminar(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getFiguraSeleccionada()));
	    }
	});
	
	new MenuItem(getMenu(),SWT.SEPARATOR);
	
	addMenuItem = new MenuItem(getMenu(),SWT.CASCADE);
	addMenuItem.setText("Agregar Codigo");
	addMenuItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new ContextualMenuActions().agregar(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getFiguraSeleccionada()));
	    }
	});
	
	createInsertFigureMenu();
    }
    
    private void createInsertFigureMenu(){
	Menu insertFigureMenu = new Menu(getMenu()); 
	
	insertFigureMenuItem = new MenuItem(getMenu(),SWT.CASCADE);
	insertFigureMenuItem.setText("Insertar Figura");
	insertFigureMenuItem.setMenu(insertFigureMenu);
	
	MenuItem inputMenuItem = new MenuItem(insertFigureMenu,SWT.CASCADE);
	inputMenuItem.setText("Entrada");
	inputMenuItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new AddFigureLogic().addInput();
		new ContextualMenuActions().insertarFigura(ApplicationState.mainFigure);
		ApplicationState.mainFigure = null;
	    }
	});
	
	MenuItem sentenceMenuItem = new MenuItem(insertFigureMenu,SWT.CASCADE);
	sentenceMenuItem.setText("Proceso");
	sentenceMenuItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new AddFigureLogic().addSentence();
		new ContextualMenuActions().insertarFigura(ApplicationState.mainFigure);
		ApplicationState.mainFigure = null;
	    }
	});
	
	MenuItem decisionMenuItem = new MenuItem(insertFigureMenu,SWT.CASCADE);
	decisionMenuItem.setText("Decision");
	decisionMenuItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new AddFigureLogic().addDecision();
		new ContextualMenuActions().insertarFigura(ApplicationState.mainFigure);
		ApplicationState.mainFigure = null;	
	    }
	});
	
	MenuItem whileMenuItem = new MenuItem(insertFigureMenu,SWT.CASCADE);
	whileMenuItem.setText("Mientras");
	whileMenuItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new AddFigureLogic().addWhile();
		new ContextualMenuActions().insertarFigura(ApplicationState.mainFigure);
		ApplicationState.mainFigure = null;
	    }
	});
	
	MenuItem forMenuItem = new MenuItem(insertFigureMenu,SWT.CASCADE);
	forMenuItem.setText("Para");
	forMenuItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new AddFigureLogic().addFor();
		new ContextualMenuActions().insertarFigura(ApplicationState.mainFigure);
		ApplicationState.mainFigure = null;
	    }
	});
	
	MenuItem outputMenuItem = new MenuItem(insertFigureMenu,SWT.CASCADE);
	outputMenuItem.setText("Impresion");
	outputMenuItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new AddFigureLogic().addOutput();
		new ContextualMenuActions().insertarFigura(ApplicationState.mainFigure);
		ApplicationState.mainFigure = null;
	    }
	});
    }
    
    public void setMenuAvailable(){
	if(ApplicationState._selectionAdministrator.getFiguraSeleccionada() != -1){
	    if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getFiguraSeleccionada()) instanceof CircleFigure){
		setEnabledEditMenuItems(false);
	    }
	    else{
		setEnabledEditMenuItems(true);
	    }
	    insertFigureMenuItem.setEnabled(true);
	}
	else{	
	    setEnabledAllMenuItems(false);
	}
    }
    
    private void setEnabledAllMenuItems(boolean isEnabled){
	pasteMenuItem.setEnabled(isEnabled);
	copyMenuItem.setEnabled(isEnabled);
	cutMenuItem.setEnabled(isEnabled);
	deleteMenuItem.setEnabled(isEnabled);
	addMenuItem.setEnabled(isEnabled);
    }
    
    public void setEnabledEditMenuItems(boolean isEnabled){
	if(ApplicationState._diagramAdministrator.diagram.size()==0){
	    pasteMenuItem.setEnabled(false);
	}
	else{
	    pasteMenuItem.setEnabled(true);
	}
	copyMenuItem.setEnabled(isEnabled);
	cutMenuItem.setEnabled(isEnabled);
	deleteMenuItem.setEnabled(isEnabled);
	addMenuItem.setEnabled(isEnabled);
    }

    public void setMenu(Menu menu) {
	this.menu = menu;
    }

    public Menu getMenu() {
	return menu;
    }
    
}