package Grafico;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

import Administracion.AdminDiagrama;
import Administracion.AdminSeleccion;
import Administracion.Figura;
import Administracion.Funcionalidad.CodeCompiler;
import Administracion.Funcionalidad.Ejecutar;
import Administracion.Funcionalidad.PasoAPaso;

/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Componentes {
    public static TabFolder _diagrams;
    
    public static AdminDiagrama _diagramAdministrator;
    
    public static AdminSeleccion _selectionAdministrator = new AdminSeleccion(); 
    
    public CustomToolBar barraHerramientas;
    public CustomFiguresToolBar barraFiguras;
    
    
    private final GridData toolData = new GridData(SWT.FILL, SWT.FILL, true,
			false, 2, 1);
    private final GridData tabData = new GridData(SWT.FILL, SWT.FILL, true,
			false, 2, 1);
    private final GridData figurasData = new GridData(SWT.FILL, SWT.FILL,
			false, true, 1, 1);
    public final GridData diagramaData = new GridData(SWT.FILL, SWT.FILL, true,
			true, 1, 1);
    public final GridData consolaData = new GridData(SWT.FILL, SWT.FILL, false,
			false, 2, 1);
    private boolean boolTool = true;
    private boolean boolPestaas = true;
    private boolean boolFiguras = true;
    private boolean consolaMax = false;
    public final GridLayout layout = new GridLayout(2, false);

    
    public Ejecutar eje;
    public PasoAPaso paso;
    private boolean enEjecucion = false;

    public boolean seleccion;
    public boolean isPasoAPaso = false;
    public CustomConsole console;

    public static Figura mainFigure = null;

    private Display display;
    
    public Componentes(Display display) {
	this.display = display;
	initControllers();
    }
    private void initControllers() {
	_diagramAdministrator = new AdminDiagrama(_selectionAdministrator);
    }
    public void agregarComponentes() {
//	    agregarBarraFiguras();
		layout.horizontalSpacing = layout.verticalSpacing = 0;
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 2;
		
		agregarBarraDeHerramientas();
		agregarTabFolder(_selectionAdministrator);
		
		
		
		console = new CustomConsole();
		console.agregarConsola(consolaData);
    }

    private void agregarBarraDeHerramientas() {
	barraHerramientas= new CustomToolBar(toolData);
    }

	private void agregarTabFolder(AdminSeleccion selec) {
		_diagrams = new TabFolder(MainWindow.display, selec);
		_diagrams.addTabItem();
		tabData.heightHint = 0;
		_diagrams.getTabFolder().setLayoutData(tabData);
	}

	public void agregarBarraFiguras() {
	    barraFiguras= new CustomFiguresToolBar(figurasData, display);
	}

	public void addBarraDeHerramientas(boolean seleccion) {
		if (!consolaMax) {
			if (!seleccion) {
				toolData.exclude = true;
				barraHerramientas.barraHerramientas.setBounds(0, 0, 0, 0);
			} else {
				toolData.exclude = false;
			}
			MainWindow.shell.layout();
		}
		boolTool = seleccion;
	}

	public void addTabFolder(boolean seleccion) {
		if (!consolaMax) {
			if (!seleccion) {
				tabData.exclude = true;
				_diagrams.getTabFolder().setBounds(0, 0, 0, 0);
			} else {
				tabData.exclude = false;
			}
			MainWindow.shell.layout();
		}
		boolPestaas = seleccion;
	}

	public void addBarraFiguras(boolean seleccion) {
		if (!consolaMax) {
			if (!seleccion) {
				figurasData.exclude = true;
				barraFiguras.barraFiguras.setBounds(0, 0, 0, 0);
			} else {
				figurasData.exclude = false;
			}
			MainWindow.shell.layout();
		}
		boolFiguras = seleccion;
	}

	public void moverConsola(boolean seleccionado) {
		if (seleccionado) {
			if (boolTool) {
				toolData.exclude = false;
			}
			if (boolPestaas) {
				tabData.exclude = false;
			}
			if (boolFiguras) {
				figurasData.exclude = false;
			}
			diagramaData.exclude = false;
			consolaData.grabExcessHorizontalSpace = false;
			consolaData.grabExcessVerticalSpace = false;
			consolaMax = false;

			if (console.item.isDisposed()) {
			    console.createTab();
			}
			console.tabFolder.forceFocus();
			console.item.setControl(console.text);
			console.text.forceFocus();
			consolaData.exclude = false;
			consolaData.heightHint = 150;
		} else {
			if (boolTool) {
				toolData.exclude = false;
			}
			if (boolPestaas) {
				tabData.exclude = false;
			}
			if (boolFiguras) {
				figurasData.exclude = false;
			}
			diagramaData.exclude = false;
			consolaData.grabExcessHorizontalSpace = false;
			consolaData.grabExcessVerticalSpace = false;
			consolaData.exclude = true;
			consolaMax = false;
			console.tabFolder.setBounds(0, 0, 0, 0);
		}
		MainWindow.shell.layout();
	}

	public void maxConsola(boolean seleccionado) {
		if (seleccionado) {
			if (toolData.exclude) {
				boolTool = false;
			}
			if (tabData.exclude) {
				boolPestaas = false;
			}
			if (figurasData.exclude) {
				boolFiguras = false;
			}
			toolData.exclude = true;
			barraHerramientas.barraHerramientas.setBounds(0, 0, 0, 0);
			tabData.exclude = true;
			_diagrams.getTabFolder().setBounds(0, 0, 0, 0);
			figurasData.exclude = true;
			barraFiguras.barraFiguras.setBounds(0, 0, 0, 0);
			diagramaData.exclude = true;
			_diagrams.getHoja().setBoundsToZero();
			consolaData.exclude = false;
			consolaData.grabExcessHorizontalSpace = true;
			consolaData.grabExcessVerticalSpace = true;
			consolaMax = true;
			if (console.item.isDisposed()) {
			    console.createTab();
			}
			console.tabFolder.forceFocus();
			console.item.setControl(console.text);
			console.text.forceFocus();
		}
		MainWindow.shell.layout();
	}

	public void guardarDisable(boolean disable) {
		barraHerramientas.toolItem[2].setEnabled(disable);
		MainWindow.menu.saveMenuItem.setEnabled(disable);
	}

	public void ejecucionDisable() {
		if (enEjecucion) {
		    console.bot.setEnabled(true);
			barraHerramientas.toolItem[13].setEnabled(true);
		} else {
			console.bot.setEnabled(false);
			barraHerramientas.toolItem[13].setEnabled(false);
		}
	}

	public String texto() {
		String texto = console.text.getText();
		String[] linea = console.text.getText().split("\n");
		texto = linea[linea.length - 1].substring(0, linea[linea.length - 1]
				.length());
		return texto;
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
			if (_diagrams.getHoja().getSizeDiagrama() == 2) {
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
				MainWindow.getComponents().barraHerramientas.toolItem[12].setEnabled(true);
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
	    barraHerramientas.toolItem[12].setEnabled(false);
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
			_diagrams.getHoja().pasoInicio = false;
			_diagrams.getHoja().addFigure();
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
		    barraHerramientas.toolItem[12].setEnabled(false);
			paso.sendNext();
		}
		final Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			public void run() {
				MainWindow.display.syncExec(new Runnable() {
					public void run() {
						MainWindow.getComponents().barraHerramientas.toolItem[12]
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
			_diagrams.getHoja().addFigure();
			
			barraHerramientas.disablePasoAPaso(disable);
			
			barraFiguras.disablePasoAPaso(disable);
			
			
			MainWindow.menu.disablePasoAPaso(disable);

		} else {
			isPasoAPaso = false;
			_diagrams.getHoja().addFigure();
			
			barraHerramientas.disablePasoAPaso(disable);
			
			barraFiguras.disablePasoAPaso(disable);

			MainWindow.menu.disablePasoAPaso(disable);
		}
	}

	public void disableAll(boolean disable) {
	    barraHerramientas.disablePasoAPaso(disable);
	    
	    barraFiguras.disablePasoAPaso(disable);
	    
	    MainWindow.menu.disablePasoAPaso(disable);
	    
	    guardarDisable(disable);
	}
}
