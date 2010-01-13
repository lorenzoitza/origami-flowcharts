package origami.graphics;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

import origami.administration.AdminDiagrama;
import origami.administration.AdminSeleccion;
import origami.administration.Figura;
import origami.administration.funtionality.CodeCompiler;
import origami.administration.funtionality.Ejecutar;
import origami.administration.funtionality.PasoAPaso;



/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Componentes {
    public static AdminDiagrama _diagramAdministrator;
    public static AdminSeleccion _selectionAdministrator = new AdminSeleccion(); 
    
    
    
    private Display display;
    
    private CustomMenu menu;
    
    private CustomFiguresToolBar figuresToolBar;
    
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

    public Ejecutar eje;
    public PasoAPaso paso;
    private boolean enEjecucion = false;

    public boolean seleccion;
    public boolean isPasoAPaso = false;
    public CustomConsole console;

    public static Figura mainFigure = null;


    
    public Componentes(Display display, CustomMenu menu) {
	this.display = display;
	this.menu = menu;
	initControllers();
    }
    private void initControllers() {
	_diagramAdministrator = new AdminDiagrama(_selectionAdministrator);
    }
    public void agregarComponentes(GridLayout layout) {
		layout.horizontalSpacing = layout.verticalSpacing = 0;
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 2;
		
		agregarBarraDeHerramientas();
		agregarTabFolder(_selectionAdministrator);
		
		

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
			console.cTabFolder.setBounds(0, 0, 0, 0);
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

	public void ejecucionDisable() {
		if (enEjecucion) {
		    console.bot.setEnabled(true);
			barraHerramientas.getToolItems().get(13).setEnabled(true);
		} else {
			console.bot.setEnabled(false);
			barraHerramientas.getToolItems().get(13).setEnabled(false);
		}
	}

	public void setText(String text) {
		if (seleccion) {
			eje.inputActionPerformed(text);
		} else {
			paso.inputActionPerformed(text);
		}
	}

	public void setEnEjecucion(boolean ejecucion) {
		enEjecucion = ejecucion;
	}

	// ejecuta el paso a paso cuando bandera es false...
	public void ejecutar(boolean bandera, CodeCompiler codigo) {
		enEjecucion = true;
		ejecucionDisable();
		if (bandera) {
			eje = new Ejecutar();
			eje.ejecutar(this, "main.exe", codigo);
			seleccion = true;
		} else {
			if (_diagrams.getTabItem().getLeaf().getSizeDiagrama() == 2) {
				MessageBox messageBox = new MessageBox(MainWindow.shell,
						SWT.ICON_INFORMATION | SWT.YES);
				messageBox.setText("Origami");
				messageBox.setMessage("La ejecucin ha terminado.");
				int selec = messageBox.open();
				switch (selec) {
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
			} else {
				paso = new PasoAPaso(_diagrams,_selectionAdministrator);
				paso.ejecutar(this, "gdb", codigo);
				paso.main();
				seleccion = false;
				MainWindow.getComponents().barraHerramientas.getToolItems().get(12).setEnabled(true);
				console.text.setEditable(false);
				console.text.setBackground(MainWindow.display
						.getSystemColor(SWT.COLOR_WHITE));
			}
		}
	}


	public boolean getEnEjecucion() {
		return enEjecucion;
	}

	public void stopEjecucion() {
	    barraHerramientas.getToolItems().get(12).setEnabled(false);
		enEjecucion = false;
		ejecucionDisable();
		if (seleccion) {
			eje.stopEjecutar();
		} else {
			disablePasoAPaso(false);
			paso.stopEjecutar();
			paso.colaConexiones.clear();
			int diag = _diagrams.selec.getSeleccionDigrama();
			paso.tab.selec.setSeleccionDiagrama(paso.a.GetId());
			paso.limpiarPasoAnterior();
			_diagrams.getTabItem().getLeaf().pasoInicio = false;
			//_diagrams.getHoja().pasoInicio = false;
			_diagrams.getTabItem().getLeaf().addFigure();
			paso.tab.selec.setSeleccionDiagrama(diag);
			console.text.setEditable(true);
		}
		eliminarArchivos();
	}

	public void eliminarArchivos() {
		try {
			File file = new File("main.exe");
			File file2 = new File("main.cpp");
			while (file.exists()) {
				file.delete();
			}
			while (file2.exists()) {
				file2.delete();
			}
		} catch (Exception e) {
		}
	}

	public void next() {
		if (enEjecucion) {
		    barraHerramientas.getToolItems().get(12).setEnabled(false);
			paso.sendNext();
		}
		final Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			public void run() {
				MainWindow.display.syncExec(new Runnable() {
					public void run() {
						MainWindow.getComponents().barraHerramientas.getToolItems().get(12)
								.setEnabled(true);
					}
				});
				timer.cancel();
			}
		};
		timer.schedule(timerTask, 100);
	}

	public void disablePasoAPaso(boolean disable) {
		if (disable) {
			isPasoAPaso = true;
			_diagrams.selec.setFiguraSeleccionada(-1);
			_diagrams.getTabItem().getLeaf().addFigure();
			
			barraHerramientas.disablePasoAPaso(disable);
			
			figuresToolBar.setEnabledAllButtons(!disable);
			
			
			menu.setEnabledStepByStepMenuItems(!disable);

		} else {
			isPasoAPaso = false;
			_diagrams.getTabItem().getLeaf().addFigure();
			
			barraHerramientas.disablePasoAPaso(disable);
			
			figuresToolBar.setEnabledAllButtons(!disable);

			menu.setEnabledStepByStepMenuItems(!disable);
		}
	}

	public void disableAll(boolean disable) {
	    barraHerramientas.disablePasoAPaso(disable);
	    
	    figuresToolBar.setEnabledAllButtons(disable);
	    
	    menu.setEnabledAllMenuItems(disable);
	    
	    guardarDisable(disable);
	}
}
