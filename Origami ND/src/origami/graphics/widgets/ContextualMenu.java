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
import origami.graphics.WindowWidgets;
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
	cutMenuItem.setEnabled(false);
	cutMenuItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new ContextualMenuActions().Cortar(WindowWidgets.tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getSelectedFigure()));
		MainWindow.getComponents().customToolBar.updateEnabledItems();
	    }
	});
	
	copyMenuItem = new MenuItem(getMenu(),SWT.CASCADE);
	copyMenuItem.setText("Copiar                  Ctrl+C");
	copyMenuItem.setEnabled(false);
	copyMenuItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new ContextualMenuActions().Copiar(WindowWidgets.tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getSelectedFigure()));
		MainWindow.getComponents().customToolBar.updateEnabledItems();
	    }
	});
	
	pasteMenuItem = new MenuItem(getMenu(),SWT.CASCADE);
	pasteMenuItem.setText("Pegar                   Ctrl+V");
	pasteMenuItem.setEnabled(false);
	pasteMenuItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new ContextualMenuActions().Pegar(WindowWidgets.tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getSelectedFigure()));
		MainWindow.getComponents().customToolBar.updateEnabledItems();
	    }
	});
	
	deleteMenuItem = new MenuItem(getMenu(),SWT.CASCADE);
	deleteMenuItem.setText("Eliminar                 Supr");
	deleteMenuItem.setEnabled(false);
	deleteMenuItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new ContextualMenuActions().Eliminar(WindowWidgets.tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getSelectedFigure()));
		MainWindow.getComponents().customToolBar.updateEnabledItems();
	    }
	});
	
	new MenuItem(getMenu(),SWT.SEPARATOR);
	
	addMenuItem = new MenuItem(getMenu(),SWT.CASCADE);
	addMenuItem.setText("Agregar Codigo");
	addMenuItem.setEnabled(false);
	addMenuItem.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		new ContextualMenuActions().agregar(WindowWidgets.tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getSelectedFigure()));
		MainWindow.getComponents().customToolBar.updateEnabledItems();
	    }
	});
	
	createInsertFigureMenu();
    }
    
    private void createInsertFigureMenu(){
	Menu insertFigureMenu = new Menu(getMenu()); 
	
	insertFigureMenuItem = new MenuItem(getMenu(),SWT.CASCADE);
	insertFigureMenuItem.setText("Insertar Figura");
	insertFigureMenuItem.setEnabled(false);
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
//	if(ApplicationState._selectionAdministrator.getSelectedFigure() != -1){
//	    if(WindowWidgets.tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getSelectedFigure()) instanceof CircleFigure){
//		setEnabledEditMenuItems(false);
//	    }
//	    else{
//		setEnabledEditMenuItems(true);
//	    }
//	    insertFigureMenuItem.setEnabled(true);
//	}
//	else{	
//	    setEnabledAllMenuItems(false);
//	}
	
	
	
	if (ApplicationState._selectionAdministrator.getSelectedFigure() == -1) {
	    setEnabledAllMenuItems(false, false);
	}
	else if (ApplicationState._selectionAdministrator.getSelectedFigure() == 0) {
	    if (ApplicationState._diagramAdministrator.diagram.size() != 0) {
		setEnabledAllMenuItems(false, true);
	    }
	    else{
		setEnabledAllMenuItems(false, false);
	    }
	    insertFigureMenuItem.setEnabled(true);
	}
	else {
	    if (ApplicationState._diagramAdministrator.diagram.size() != 0) {
		setEnabledAllMenuItems(true, true);
	    }
	    else{
		setEnabledAllMenuItems(true, false);
	    }
	}
	
	
    }
    
    private void setEnabledAllMenuItems(boolean isEnabled,boolean cmdPaste){
	pasteMenuItem.setEnabled(cmdPaste);
	copyMenuItem.setEnabled(isEnabled);
	cutMenuItem.setEnabled(isEnabled);
	deleteMenuItem.setEnabled(isEnabled);
	addMenuItem.setEnabled(isEnabled);
	insertFigureMenuItem.setEnabled(isEnabled);
    }
    
    public void setEnabledEditMenuItems(boolean isEnabled,boolean cmdPaste){
	if(ApplicationState._diagramAdministrator.diagram.size()==0){
	    pasteMenuItem.setEnabled(cmdPaste);
	}
	else{
	    pasteMenuItem.setEnabled(true);
	}
	copyMenuItem.setEnabled(isEnabled);
	cutMenuItem.setEnabled(isEnabled);
	deleteMenuItem.setEnabled(isEnabled);
	addMenuItem.setEnabled(isEnabled);
    }
    public void setEnabledAllItems(boolean enabled){
	setMenuAvailable();
	insertFigureMenuItem.setEnabled(enabled);
    }
    public void setMenu(Menu menu) {
	this.menu = menu;
    }

    public Menu getMenu() {
	return menu;
    }
    
}