package origami.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;


import origami.administration.AdminSelection;
import origami.administration.ApplicationState;
import origami.graphics.widgets.CustomConsole;
import origami.graphics.widgets.CustomFiguresToolBar;
import origami.graphics.widgets.CustomMenu;
import origami.graphics.widgets.CustomToolBar;
import origami.graphics.widgets.CustomTabFolder;

import org.eclipse.swt.custom.SashForm;

public class WindowWidgets {
    public static CustomTabFolder tabFolder;
    public CustomConsole customConsole;
    public CustomToolBar customToolBar;
   
    /*
     * GridData(int horizontalAlignment, int verticalAlignment, boolean grabExcessHorizontalSpace, 
     * 		boolean grabExcessVerticalSpace, int horizontalSpan, int verticalSpan) 
     * */
    public GridData consoleData = new GridData(SWT.FILL, SWT.FILL, false,false, 2, 1);
    
    public GridData diagramData = new GridData(SWT.LEFT, SWT.FILL, true,true, 1, 1);
    
    
    private GridData tabData = new GridData(SWT.FILL, SWT.FILL, true,false, 2, 1);
    private GridData toolBarData = new GridData(SWT.FILL, SWT.FILL, true,false, 2, 1);
    private GridData figuresToolBarData = new GridData(SWT.FILL, SWT.FILL,false, true, 1, 1);
    
    private CustomMenu customMenu;
    private Display display;
    private CustomFiguresToolBar figuresToolBar;

    private boolean isConsoleMaximized = false;
    private boolean isFigureToolBarAvailable = true;
    private boolean isTabAvailable = true;
    private boolean isToolBarAvailable = true;
    private StepByStepComponents stepByStepComponents;
    
       
    public WindowWidgets(Display display, CustomMenu menu) {
	this.display = display;
	this.customMenu = menu;
	initControllers();
    }
    //This method is called when a new DiagramStructure is created as part of the CustomLeaf
    public void addFiguresToolBar() {
	figuresToolBarData.widthHint = 62;
	figuresToolBar= new CustomFiguresToolBar(figuresToolBarData, display);

	stepByStepComponents = new StepByStepComponents(customMenu, figuresToolBar);
    }

    public void createWidgets(GridLayout layout) {
	layout.horizontalSpacing = layout.verticalSpacing = 0;
	layout.marginWidth = layout.marginHeight = 0;
	
	layout.numColumns = 2;
	
	addToolBar();
	
	addTabFolder(ApplicationState._selectionAdministrator);
	
	//Finally add the console at the end o
	customConsole = new CustomConsole(consoleData);
    }
    
  //This method call TabFolder where is included ToolBar and Diagram
    private void addTabFolder(AdminSelection selec) {
	//before to include sashForm
	
	tabFolder = new CustomTabFolder(MainWindow.display, selec);
	tabFolder.addTabItem();
	
	tabData.heightHint = 0;
	tabFolder.getTabFolder().setLayoutData(tabData);
	
    }
    
    private void addToolBar() {
	toolBarData.heightHint = 23;
	customToolBar= new CustomToolBar(toolBarData);
    }
    
    public StepByStepComponents getByStepComponents() {
	return stepByStepComponents;
    }

    
    public CustomMenu getCustomMenu() {
        return customMenu;
    }
    
    public CustomFiguresToolBar getFiguresToolBar() {
        return figuresToolBar;
    }
    
    public void maximizeConsole(boolean isEnabled) {
	if (isEnabled) {
	    if (toolBarData.exclude) {
		isToolBarAvailable = false;
	    }
	    if (tabData.exclude) {
		isTabAvailable = false;
	    }
	    if (figuresToolBarData.exclude) {
		isFigureToolBarAvailable = false;
	    }
	    toolBarData.exclude = true;
	    
	    int width = 0;
	    int height = 0;
	    int xCoord = 0;
	    int yCoord = 0;
	    customToolBar.getToolBar().setBounds(xCoord, yCoord, width, height);
	    tabData.exclude = true;
	    tabFolder.getTabFolder().setBounds(xCoord, yCoord, width, height);
	    figuresToolBarData.exclude = true;
	    figuresToolBar.getBarraFiguras().setBounds(xCoord, yCoord, width, height);
	    diagramData.exclude = true;
	    
	    DiagramStructure.getInstance().setBoundsToZero();
	    
	    consoleData.exclude = false;
	    consoleData.grabExcessHorizontalSpace = true;
	    consoleData.grabExcessVerticalSpace = true;
	    isConsoleMaximized = true;
	    
	    customConsole.createConsole();
	}
	MainWindow.shell.layout();
    }

    public void restoreConsole(boolean isEnable) {
	if (isToolBarAvailable) {
	    toolBarData.exclude = false;
	}
	if (isTabAvailable) {
	    tabData.exclude = false;
	}
	if (isFigureToolBarAvailable) {
	    figuresToolBarData.exclude = false;
	}
	diagramData.exclude = false;
	    
	consoleData.grabExcessHorizontalSpace = false;
	consoleData.grabExcessVerticalSpace = false;
	
	if (isEnable) {
	    customConsole.createConsole();
	    consoleData.exclude = false;
	    consoleData.heightHint = 150;
	    
	    isConsoleMaximized = false;
	} else {
	    consoleData.exclude = true;
	    isConsoleMaximized = false;
	    
	    int width = 0;
	    int height = 0;
	    int xCoord = 0;
	    int yCoord = 0;
	    customConsole.getCtabFolder().setBounds(xCoord, yCoord, width, height);
	}
	MainWindow.shell.layout();
    }

    public void setByStepComponents(StepByStepComponents byStepComponents) {
	this.stepByStepComponents = byStepComponents;
    }

    public void setCustomMenu(CustomMenu customMenu) {
        this.customMenu = customMenu;
    }
    
    public void setEnabledItemsToolbarDefault(){
	customToolBar.setEnabledItemGenerateCode(true);
	customToolBar.setEnabledItemSave(true);
	//verificar si hay un objeto en el copy
	customToolBar.updateEnabledItems();
//	customToolBar.setEnabledItemsEdition(false, false);
	customToolBar.setEnabledItemsExecutions(true);
	customToolBar.setEnabledItemsExport(true);
	customToolBar.setEnabledItemUndo(true);
    }

    public void setEnableTabFolder(boolean isEnable) {
	if (!isConsoleMaximized) {
	    if (!isEnable) {
		tabData.exclude = true;
		int width = 0;
		int height = 0;
		int xCoord = 0;
		int yCoord = 0;
		tabFolder.getTabFolder().setBounds(xCoord, yCoord, width, height);
	    } else {
		tabData.exclude = false;
	    }
	    MainWindow.shell.layout();
	}
	isTabAvailable = isEnable;
    }

    public void setEnableToolBar(boolean isEnable) {
	if (!isConsoleMaximized) {
	    if (!isEnable) {
		toolBarData.exclude = true;
		int width = 0;
		int height = 0;
		int xCoord = 0;
		int yCoord = 0;
		customToolBar.getToolBar().setBounds(xCoord, yCoord, width, height);
	    } else {
		toolBarData.exclude = false;
	    }
	    MainWindow.shell.layout();
	}
	isToolBarAvailable = isEnable;
    }

    public void setFiguresToolBar(boolean isEnable) {
	if (!isConsoleMaximized) {
	    if (!isEnable) {
		figuresToolBarData.exclude = true;
		int width = 0;
		int height = 0;
		int xCoord = 0;
		int yCoord = 0;
		figuresToolBar.getBarraFiguras().setBounds(xCoord, yCoord, width, height);
	    } else {
		figuresToolBarData.exclude = false;
	    }
	    MainWindow.shell.layout();
	}
	isFigureToolBarAvailable = isEnable;
    }

    public void setFiguresToolBar(CustomFiguresToolBar figuresToolBar) {
        this.figuresToolBar = figuresToolBar;
    }
	
//    public void disableAll(boolean isEnabled) {
//	customToolBar.setEnabledStepByStepToolItems(isEnabled);
//	    
//	figuresToolBar.setEnabledAllButtons(isEnabled);
//	
//	customMenu.setEnabledAllMenuItems(isEnabled);
//	
//	setEnabledSaveItems(isEnabled);
//    }
//    
//    public void setEnabledSaveItems(boolean isEnable) {
//	customToolBar.setEnabledStepByStepToolItems(isEnable);
//	customMenu.setEnabledSaveMenuItem(isEnable);
//    }
    
    
    private void initControllers() {
	ApplicationState.init();
    }
}
