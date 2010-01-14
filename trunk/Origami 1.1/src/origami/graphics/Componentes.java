package origami.graphics;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import origami.administration.AdminSeleccion;
import origami.administration.ApplicationState;
import origami.graphics.widgets.CustomConsole;
import origami.graphics.widgets.CustomFiguresToolBar;
import origami.graphics.widgets.CustomMenu;
import origami.graphics.widgets.CustomToolBar;
import origami.graphics.widgets.TabFolder;



/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Componentes {
    private Display display;
    
    CustomMenu menu;
    
    CustomFiguresToolBar figuresToolBar;
    
    private GridData toolBarData = new GridData(SWT.FILL, SWT.FILL, true,
			false, 2, 1);
    private GridData tabData = new GridData(SWT.FILL, SWT.FILL, true,
			false, 2, 1);
    private GridData figuresToolBarData = new GridData(SWT.FILL, SWT.FILL,
			false, true, 1, 1);
    
    private boolean isToolBarAvailable = true;
    
    private boolean isTabAvailable = true;
    
    private boolean isFigureToolBarAvailable = true;
    
    private boolean isConsoleMaximized = false;
    
    public static TabFolder _diagrams;
    
    public CustomToolBar barraHerramientas;
    
    public GridData diagramaData = new GridData(SWT.FILL, SWT.FILL, true,
			true, 1, 1);
    public GridData consolaData = new GridData(SWT.FILL, SWT.FILL, false,
			false, 2, 1);

    public CustomConsole console;

    public Componentes(Display display, CustomMenu menu) {
	this.display = display;
	this.menu = menu;
	initControllers();
    }
    private void initControllers() {
	ApplicationState.init();
    }
    public void agregarComponentes(GridLayout layout) {
	layout.horizontalSpacing = layout.verticalSpacing = 0;
	layout.marginWidth = layout.marginHeight = 0;
	layout.numColumns = 2;
	
	agregarBarraDeHerramientas();
	agregarTabFolder(ApplicationState._selectionAdministrator);
	
	consolaData.exclude = true;
	console = new CustomConsole(consolaData);
    }

    private void agregarBarraDeHerramientas() {
	toolBarData.heightHint = 23;
	barraHerramientas= new CustomToolBar(toolBarData);
    }

	private void agregarTabFolder(AdminSeleccion selec) {
		_diagrams = new TabFolder(MainWindow.display, selec);
		BaseDeDiagrama.getInstance();
		_diagrams.addTabItem();
		tabData.heightHint = 0;
		_diagrams.getTabFolder().setLayoutData(tabData);
	}

	public void agregarBarraFiguras() {
	    figuresToolBarData.widthHint = 62;
	    figuresToolBar= new CustomFiguresToolBar(figuresToolBarData, display);
	}

	public void addBarraDeHerramientas(boolean seleccion) {
		if (!isConsoleMaximized) {
			if (!seleccion) {
				toolBarData.exclude = true;
				barraHerramientas.getToolBar().setBounds(0, 0, 0, 0);
			} else {
				toolBarData.exclude = false;
			}
			MainWindow.shell.layout();
		}
		isToolBarAvailable = seleccion;
	}

	public void addTabFolder(boolean seleccion) {
		if (!isConsoleMaximized) {
			if (!seleccion) {
				tabData.exclude = true;
				_diagrams.getTabFolder().setBounds(0, 0, 0, 0);
			} else {
				tabData.exclude = false;
			}
			MainWindow.shell.layout();
		}
		isTabAvailable = seleccion;
	}

	public void addBarraFiguras(boolean seleccion) {
		if (!isConsoleMaximized) {
			if (!seleccion) {
				figuresToolBarData.exclude = true;
				figuresToolBar.getBarraFiguras().setBounds(0, 0, 0, 0);
			} else {
				figuresToolBarData.exclude = false;
			}
			MainWindow.shell.layout();
		}
		isFigureToolBarAvailable = seleccion;
	}

	public void moverConsola(boolean seleccionado) {
		if (seleccionado) {
			if (isToolBarAvailable) {
				toolBarData.exclude = false;
			}
			if (isTabAvailable) {
				tabData.exclude = false;
			}
			if (isFigureToolBarAvailable) {
				figuresToolBarData.exclude = false;
			}
			diagramaData.exclude = false;
			
			
			consolaData.grabExcessHorizontalSpace = false;
			consolaData.grabExcessVerticalSpace = false;
			
			console.createConsole();
			
			consolaData.exclude = false;
			consolaData.heightHint = 150;
			
			isConsoleMaximized = false;
		} else {
			if (isToolBarAvailable) {
				toolBarData.exclude = false;
			}
			if (isTabAvailable) {
				tabData.exclude = false;
			}
			if (isFigureToolBarAvailable) {
				figuresToolBarData.exclude = false;
			}
			diagramaData.exclude = false;
			
			
			consolaData.grabExcessHorizontalSpace = false;
			consolaData.grabExcessVerticalSpace = false;
			consolaData.exclude = true;
			isConsoleMaximized = false;
			console.getCtabFolder().setBounds(0, 0, 0, 0);
		}
		MainWindow.shell.layout();
	}

	public void maxConsola(boolean seleccionado) {
		if (seleccionado) {
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
			barraHerramientas.getToolBar().setBounds(0, 0, 0, 0);
			tabData.exclude = true;
			_diagrams.getTabFolder().setBounds(0, 0, 0, 0);
			figuresToolBarData.exclude = true;
			figuresToolBar.getBarraFiguras().setBounds(0, 0, 0, 0);
			diagramaData.exclude = true;
			BaseDeDiagrama.getInstance().setBoundsToZero();
			
			
			consolaData.exclude = false;
			consolaData.grabExcessHorizontalSpace = true;
			consolaData.grabExcessVerticalSpace = true;
			isConsoleMaximized = true;
			
			console.createConsole();
			
		}
		MainWindow.shell.layout();
	}

	public void guardarDisable(boolean disable) {
		barraHerramientas.getToolItems().get(2).setEnabled(disable);
		menu.getSaveMenuItem().setEnabled(disable);
	}
	
	private StepByStepComponents byStepComponents = new StepByStepComponents();
	
	public void disableAll(boolean disable) {
	    barraHerramientas.disableComponentStepByStep(disable);
	    
	    figuresToolBar.setEnabledAllButtons(disable);
	    
	    menu.setEnabledAllMenuItems(disable);
	    
	    guardarDisable(disable);
	}
	public void setByStepComponents(StepByStepComponents byStepComponents) {
	    this.byStepComponents = byStepComponents;
	}
	public StepByStepComponents getByStepComponents() {
	    return byStepComponents;
	}
}
