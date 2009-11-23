package Grafico;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import Administracion.*;
import Administracion.Eventos.EventoMenuContextual;
import Administracion.Funcionalidad.CodeCompiler;
import Administracion.Funcionalidad.Ejecutar;
import Administracion.Funcionalidad.Exporter;
import Administracion.Funcionalidad.PasoAPaso;
import Administracion.Funcionalidad.SerializeFile;
import Administracion.Funcionalidad.Codigo.Instruccion;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Administracion.Funcionalidad.Codigo.Manager;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;
import Imagenes.ImageLoader;

/**
 * @version Origami 1.0.0.0.0.0.0.0.0.000.0.0.0.0.0..0.0.1
 * @author Juan Ku, Victor Rodriguez
 */
public class Componentes {
	public TabFolder diagramas;
	private ToolBar barraFiguras;
	private ToolBar barraHerramientas;
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
	final GridLayout layout2 = new GridLayout(1, false);
	public SerializeFile ser = new SerializeFile();
	private Button boton[] = new Button[7];
	public ToolItem toolItem[] = new ToolItem[20];
	public final Cursor[] cursor = new Cursor[1];
	public CTabFolder tabFolder;
	public Text text;
	private CTabItem item;
	private int sizeTab;
	public Ejecutar eje;
	public PasoAPaso paso;
	private boolean enEjecucion = false;
	private ToolItem bot;
	public boolean hide = true;
	public boolean seleccion;
	public boolean isPasoAPaso = false;

	public void agregarComponentes(AdminSeleccion selec) {
		layout.horizontalSpacing = layout.verticalSpacing = 0;
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 2;
		agregarBarraDeHerramientas();
		agregarTabFolder(selec);
		agregarConsola();
	}

	private void agregarBarraDeHerramientas() {
		barraHerramientas = new ToolBar(MainWindow.shell, SWT.HORIZONTAL
				| SWT.FLAT);
		barraHerramientas.setLayoutData(toolData);
		toolData.heightHint = 23;
		barraHerramientas.setCursor(new Cursor(null, SWT.CURSOR_ARROW));
		getToolItems(barraHerramientas);
	}

	private void agregarTabFolder(AdminSeleccion selec) {
		MainWindow._diagrams = new TabFolder(MainWindow.display, selec);
		tabData.heightHint = 0;
		MainWindow._diagrams.getTabFolder().setLayoutData(tabData);
	}

	public void agregarBarraFiguras() {
		GridLayout layout2 = new GridLayout();
		layout2.numColumns = 1;
		layout2.horizontalSpacing = 0;
		layout2.verticalSpacing = 3;
		layout2.marginWidth = layout2.marginHeight = 0;
		barraFiguras = new ToolBar(MainWindow.shell, SWT.LEFT | SWT.FLAT
				| SWT.BORDER);
		barraFiguras.setLayoutData(figurasData);
		figurasData.widthHint = 62;
		barraFiguras.setCursor(new Cursor(null, SWT.CURSOR_ARROW));
		getBotones(barraFiguras);
		barraFiguras.setLayout(layout2);
	}

	public void addBarraDeHerramientas(boolean seleccion) {
		if (!consolaMax) {
			if (!seleccion) {
				toolData.exclude = true;
				barraHerramientas.setBounds(0, 0, 0, 0);
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
				MainWindow._diagrams.getTabFolder().setBounds(0, 0, 0, 0);
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
				barraFiguras.setBounds(0, 0, 0, 0);
			} else {
				figurasData.exclude = false;
			}
			MainWindow.shell.layout();
		}
		boolFiguras = seleccion;
	}

	public void setDiagrama(TabFolder diagramas) {
		this.diagramas = diagramas;
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

			if (item.isDisposed()) {
				createTab();
			}
			tabFolder.forceFocus();
			item.setControl(text);
			text.forceFocus();
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
			tabFolder.setBounds(0, 0, 0, 0);
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
			barraHerramientas.setBounds(0, 0, 0, 0);
			tabData.exclude = true;
			MainWindow._diagrams.getTabFolder().setBounds(0, 0, 0, 0);
			figurasData.exclude = true;
			barraFiguras.setBounds(0, 0, 0, 0);
			diagramaData.exclude = true;
			diagramas.getHoja().setBoundsToZero();
			consolaData.exclude = false;
			consolaData.grabExcessHorizontalSpace = true;
			consolaData.grabExcessVerticalSpace = true;
			consolaMax = true;
			if (item.isDisposed()) {
				createTab();
			}
			tabFolder.forceFocus();
			item.setControl(text);
			text.forceFocus();
		}
		MainWindow.shell.layout();
	}

	public void agregarConsola() {
		tabFolder = new CTabFolder(MainWindow.shell, SWT.BORDER);
		tabFolder.pack();
		tabFolder.setLayoutData(consolaData);
		consolaData.exclude = true;
		tabFolder.setBounds(0, 0, 0, 0);
		tabFolder.setSimple(false);
		tabFolder.setTabHeight(24);
		Color title = MainWindow.display
				.getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
		Color title2 = MainWindow.display
				.getSystemColor(SWT.COLOR_TITLE_FOREGROUND);
		Color title3 = MainWindow.display
				.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
		tabFolder.setSelectionForeground(title);
		tabFolder.setSelectionBackground(new Color[] { title2, title3 },
				new int[] { 100 }, true);
		createTab();
		tabFolder.setMinimizeVisible(true);
		tabFolder.setMaximizeVisible(true);
		tabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			public void minimize(CTabFolderEvent event) {
				MainWindow.menu.consoleMenuItem.setSelection(false);
				moverConsola(false);
				tabFolder.setMaximized(false);
			}

			public void maximize(CTabFolderEvent event) {
				maxConsola(true);
				tabFolder.forceFocus();
				text.forceFocus();
				tabFolder.setMaximized(true);
			}

			public void restore(CTabFolderEvent event) {
				moverConsola(true);
				tabFolder.forceFocus();
				text.forceFocus();
				tabFolder.setMinimized(false);
				tabFolder.setMaximized(false);
			}
		});
	}

	public boolean guardar() {
		if (diagramas.getTabItem().getSave().getDir() == "null") {
			FileDialog dialog = new FileDialog(MainWindow.shell, SWT.SAVE);
			dialog.setFilterExtensions(new String[] { "*.Org" });
			String archivo = dialog.open();
			if (archivo != null) {
				if (dialog.getFileName().contains("\\")
						|| dialog.getFileName().contains("/")
						|| dialog.getFileName().contains(":")
						|| dialog.getFileName().contains("*")
						|| dialog.getFileName().contains("?")
						|| dialog.getFileName().contains("<")
						|| dialog.getFileName().contains(">")
						|| dialog.getFileName().contains("|")
						|| dialog.getFileName().contains("\"")) {
					MessageBox messageBox = new MessageBox(MainWindow.shell,
							SWT.ICON_ERROR | SWT.OK);
					messageBox.setText("Origami");
					messageBox
							.setMessage("El nombre de archivo, directorio o etiqueta del volumn no es vlido");
					int seleccion = messageBox.open();
					switch (seleccion) {
					case 64:
						break;
					case 128:
						break;
					}
					return false;
				} else {
					boolean existe = false;
					try {
						File arch = new File(archivo);
						if (arch.exists()) {
							existe = true;
						}
					} catch (Exception e1) {
					}
					if (existe) {
						MessageBox messageBox = new MessageBox(
								MainWindow.shell, SWT.ICON_WARNING | SWT.YES
										| SWT.NO);
						messageBox.setText("Origami");
						messageBox
								.setMessage("El archivo ya existe. Desea reemplazarlo?");
						int seleccion = messageBox.open();
						switch (seleccion) {
						case 64:
							ser.SetFil(archivo);
							diagramas.getTabItem().getSave().setDir(archivo);
							ser.saveFile(diagramas);
							archivo = dialog.getFileName();
							int pos = archivo.indexOf('.');
							String name = archivo.substring(0, pos);
							diagramas.cambiarNombre("*" + name);
							diagramas.getTabItem().getSave().setSave(true);
							return true;
						case 128:
							return false;
						}
					} else {
						ser.SetFil(archivo);
						diagramas.getTabItem().getSave().setDir(archivo);
						boolean error = ser.saveFile(diagramas);
						if (error) {
							archivo = dialog.getFileName();
							int pos = archivo.indexOf('.');
							String name = archivo.substring(0, pos);
							diagramas.cambiarNombre("*" + name);
							diagramas.getTabItem().getSave().setSave(true);
						}
						return true;
					}
				}
			}
			return false;
		} else {
			ser.SetFil(diagramas.getTabItem().getSave().getDir());
			ser.saveFile(diagramas);
			diagramas.getTabItem().getSave().setSave(true);
			return true;
		}
	}

	public void getToolItems(ToolBar toolbar) {
		toolItem[0] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenNuevo = new Image(Ventana.display,
		// "imagenes\\nuevo.png");
		toolItem[0].setImage(ImageLoader.getImage("nuevo.png"));
		toolItem[0].setToolTipText("Nuevo");
		toolItem[0].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				diagramas.addTabItem();
				guardarDisable(true);
				MainWindow.getComponents().disableAll(true);
			}
		});
		toolItem[1] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenAbrir = new Image(Ventana.display,
		// "imagenes\\abrir.png");
		toolItem[1].setImage(ImageLoader.getImage("abrir.png"));
		toolItem[1].setToolTipText("Abrir");
		toolItem[1].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (MainWindow.getComponents().eje != null
						&& MainWindow.getComponents().getEnEjecucion()
						&& diagramas.getSelectedTabItemId() == MainWindow
								.getComponents().eje.a.GetId()) {
					MainWindow.getComponents().stopEjecucion();
				} else if (MainWindow.getComponents().paso != null
						&& MainWindow.getComponents().getEnEjecucion()
						&& diagramas.getSelectedTabItemId() == MainWindow
								.getComponents().paso.a.GetId()) {
					MainWindow.getComponents().stopEjecucion();
				}
				FileDialog dialog = new FileDialog(MainWindow.shell, SWT.OPEN);
				dialog.setFilterExtensions(new String[] { "*.Org", "*.*" });
				String archivo = dialog.open();
				if (archivo != null) {
					File file = new File(archivo);
					if (file.exists()) {
						if (diagramas.getHoja().getSizeDiagrama() == 0) {
							String archivo2 = dialog.getFileName();
							int pos = archivo2.indexOf('.');
							String name = archivo2.substring(0, pos);
							diagramas.cambiarNombre("*" + name);
							diagramas.getTabItem().getSave().setSave(true);
							diagramas.abrir(archivo, ser);
							diagramas.getTabItem().getSave().setDir(archivo);
						} else {
							MainWindow._selectionAdministrator
									.setFiguraSeleccionada(0);
							diagramas.getHoja().openFile(archivo);
							diagramas.getTabItem().getSave().setDir(archivo);
							archivo = dialog.getFileName();
							int pos = archivo.indexOf('.');
							String name = archivo.substring(0, pos);
							diagramas.cambiarNombre("*" + name);
							diagramas.getTabItem().getSave().setSave(true);
							diagramas.getTabItem().resetRetroceso();
							diagramas.getTabItem().agregarRetroceso(
									diagramas.getHoja().getDiagrama(),
									diagramas.selec);
						}
						MainWindow.getComponentes().disablePasoAPaso(false);
						MainWindow.getComponents().disableAll(true);
					}
				}
			}
		});
		toolItem[2] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenSave = new Image(Ventana.display, "imagenes\\save.png");
		toolItem[2].setImage(ImageLoader.getImage("save.png"));
		toolItem[2].setToolTipText("Guardar");
		toolItem[2].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (diagramas.getTabItem().getSave().getDir() == "null") {
					FileDialog dialog = new FileDialog(MainWindow.shell,
							SWT.SAVE);
					dialog.setFilterExtensions(new String[] { "*.Org" });
					String archivo = dialog.open();
					if (archivo != null) {
						if (dialog.getFileName().contains("\\")
								|| dialog.getFileName().contains("/")
								|| dialog.getFileName().contains(":")
								|| dialog.getFileName().contains("*")
								|| dialog.getFileName().contains("?")
								|| dialog.getFileName().contains("<")
								|| dialog.getFileName().contains(">")
								|| dialog.getFileName().contains("|")
								|| dialog.getFileName().contains("\"")) {
							MessageBox messageBox = new MessageBox(
									MainWindow.shell, SWT.ICON_ERROR | SWT.OK);
							messageBox.setText("Origami");
							messageBox
									.setMessage("El nombre de archivo, directorio o etiqueta del volumn no es vlido");
							int seleccion = messageBox.open();
							switch (seleccion) {
							case 64:
								break;
							case 128:
								break;
							}
						} else {
							boolean existe = false;
							try {
								File arch = new File(archivo);
								if (arch.exists()) {
									existe = true;
								}
							} catch (Exception e1) {
							}
							if (existe) {
								MessageBox messageBox = new MessageBox(
										MainWindow.shell, SWT.ICON_WARNING
												| SWT.YES | SWT.NO);
								messageBox.setText("Origami");
								messageBox
										.setMessage("El archivo ya existe. Desea reemplazarlo?");
								int seleccion = messageBox.open();
								switch (seleccion) {
								case 64:
									ser.SetFil(archivo);
									diagramas.getTabItem().getSave().setDir(
											archivo);
									ser.saveFile(diagramas);
									archivo = dialog.getFileName();
									int pos = archivo.indexOf('.');
									String name = archivo.substring(0, pos);
									diagramas.cambiarNombre("*" + name);
									diagramas.getTabItem().getSave().setSave(
											true);
									break;
								case 128:
									break;
								}
							} else {
								ser.SetFil(archivo);
								diagramas.getTabItem().getSave()
										.setDir(archivo);
								boolean error = ser.saveFile(diagramas);
								if (error) {
									archivo = dialog.getFileName();
									int pos = archivo.indexOf('.');
									String name = archivo.substring(0, pos);
									diagramas.cambiarNombre("*" + name);
									diagramas.getTabItem().getSave().setSave(
											true);
								}
							}
						}
					}
				} else {
					ser.SetFil(diagramas.getTabItem().getSave().getDir());
					ser.saveFile(diagramas);
					diagramas.getTabItem().getSave().setSave(true);
				}
			}
		});
		new ToolItem(toolbar, SWT.SEPARATOR);

		toolItem[3] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenDeshacer = new Image(Ventana.display,
		// "imagenes\\undo.png");
		toolItem[3].setImage(ImageLoader.getImage("undo.png"));
		toolItem[3].setToolTipText(" Deshacer ");
		toolItem[3].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				TabItem item = (TabItem) diagramas.getSeleccion();
				item.retroceder();
				diagramas.getTabItem().getSave().setSave(false);
			}
		});
		toolItem[4] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenCortar = new Image(Ventana.display,
		// "imagenes\\cortar.ico");
		toolItem[4].setImage(ImageLoader.getImage("cortar.ico"));
		toolItem[4].setToolTipText(" Cortar ");
		toolItem[4].setEnabled(false);
		toolItem[4].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Figura fig = diagramas.getHoja().getFigureIndexOf(
						MainWindow._selectionAdministrator
								.getFiguraSeleccionada());
				EventoMenuContextual.Cortar(fig);
				toolBarDisable();
			}
		});
		toolItem[5] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenCopiar = new Image(Ventana.display,
		// "imagenes\\copiar.png");
		toolItem[5].setImage(ImageLoader.getImage("copiar.png"));
		toolItem[5].setToolTipText(" Copiar ");
		toolItem[5].setEnabled(false);
		toolItem[5].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Figura fig = diagramas.getHoja().getFigureIndexOf(
						MainWindow._selectionAdministrator
								.getFiguraSeleccionada());
				EventoMenuContextual.Copiar(fig);
				toolBarDisable();
			}
		});
		toolItem[6] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenPegar = new Image(Ventana.display,
		// "imagenes\\pegar.ico");
		toolItem[6].setImage(ImageLoader.getImage("pegar.ico"));
		toolItem[6].setToolTipText(" Pegar ");
		toolItem[6].setEnabled(false);
		toolItem[6].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Figura fig = diagramas.getHoja().getFigureIndexOf(
						MainWindow._selectionAdministrator
								.getFiguraSeleccionada());
				EventoMenuContextual.Pegar(fig);
				toolBarDisable();
			}
		});
		toolItem[7] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenEliminar = new Image(Ventana.display,
		// "imagenes\\eliminar.png");
		toolItem[7].setImage(ImageLoader.getImage("eliminar.png"));
		toolItem[7].setToolTipText(" Eliminar ");
		toolItem[7].setEnabled(false);
		toolItem[7].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Figura fig = diagramas.getHoja().getFigureIndexOf(
						MainWindow._selectionAdministrator
								.getFiguraSeleccionada());
				EventoMenuContextual.Eliminar(fig);
			}
		});
		new ToolItem(toolbar, SWT.SEPARATOR);

		toolItem[8] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenCodigo = new Image(Ventana.display,
		// "imagenes\\codigo.png");
		toolItem[8].setImage(ImageLoader.getImage("codigo.png"));
		toolItem[8].setToolTipText("Generar Codigo");
		toolItem[8].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				/*Instruccion codigo = new Instruccion();
				codigo.main(diagramas.getHoja().getDiagrama(), true);
				codigo.ventana(MainWindow.display);*/
			    Manager manager = new Manager(diagramas.getHoja().getDiagrama());
			    manager.formatCodeC();
			    System.out.println(manager.getInstructionsFormat());
			}
		});
		toolItem[9] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenCodigoCpp = new Image(Ventana.display,
		// "imagenes\\codigoCpp.png");
		toolItem[9].setImage(ImageLoader.getImage("codigoCpp.png"));
		toolItem[9].setToolTipText("Generar Codigo");
		toolItem[9].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				/*Instruccion codigo = new Instruccion();
				codigo.main(diagramas.getHoja().getDiagrama(), false);
				codigo.ventana(MainWindow.display);*/
			    Manager manager = new Manager(diagramas.getHoja().getDiagrama());
			    manager.formatCodeCpp();
			    System.out.println(manager.getInstructionsFormat());
			}
		});
		new ToolItem(toolbar, SWT.SEPARATOR);

		toolItem[10] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenRun = new Image(Ventana.display, "imagenes\\run.png");
		toolItem[10].setImage(ImageLoader.getImage("run.png"));
		toolItem[10].setToolTipText("Compilar/Ejecutar");
		toolItem[10].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (!diagramas.getTabItem().getSave().isSave()) {
					// llamo abrir la ventana para guardar
					if (guardar()) {
						CodeCompiler codigo = new CodeCompiler(diagramas);
						if (getEnEjecucion()) {
							stopEjecucion();
						}
						codigo.main(false, true);
						if (codigo.isError) {
							int aux = text.getText().length();
							if (aux >= 0) {
								text.setText("");
							}
							text.setText(codigo.errorTipe);
							diagramas.getTabItem().getInfo().addInformation(
									"/Ec - Error en la compilacion:");
							diagramas.getTabItem().getInfo().addInformation(
									codigo.errorTipe);
							codigo.deleteMainFiles();
						} else {
							ejecutar(true, codigo);
							diagramas
									.getTabItem()
									.getInfo()
									.addInformation(
											"/C - Se Compilo el diagrama de manera correcta");
						}
						if (!MainWindow.menu.consoleMenuItem.getSelection()) {
							MainWindow.menu.consoleMenuItem.setSelection(true);
							moverConsola(true);
						}
					}
				} else {
					CodeCompiler codigo = new CodeCompiler(diagramas);
					if (getEnEjecucion()) {
						stopEjecucion();
					}
					codigo.main(false, true);
					if (codigo.isError) {
						int aux = text.getText().length();
						if (aux >= 0) {
							text.setText("");
						}
						text.setText(codigo.errorTipe);
						codigo.deleteMainFiles();
					} else {
						ejecutar(true, codigo);
					}
					if (!MainWindow.menu.consoleMenuItem.getSelection()) {
						MainWindow.menu.consoleMenuItem.setSelection(true);
						moverConsola(true);
					}
				}
			}
		});
		toolItem[11] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenPaso = new Image(Ventana.display, "imagenes\\check.png");
		toolItem[11].setImage(ImageLoader.getImage("check.png"));
		toolItem[11].setToolTipText("Paso A Paso");
		toolItem[11].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (!diagramas.getTabItem().getSave().isSave()) {
					if (guardar()) {
						CodeCompiler codigo = new CodeCompiler(diagramas);
						codigo.main(false, false);
						if (codigo.isError) {
							int aux = text.getText().length();
							if (aux >= 0) {
								text.setText("");
							}
							// moverConsola(true);
							text.setText(codigo.errorTipe);
							diagramas.getTabItem().getInfo().addInformation(
									"/Ep - Error en el paso a paso:");
							diagramas.getTabItem().getInfo().addInformation(
									codigo.errorTipe);
							codigo.deleteMainFiles();
						} else {
							// moverConsola(true);
							disablePasoAPaso(true);
							ejecutar(false, codigo);
							diagramas
									.getTabItem()
									.getInfo()
									.addInformation(
											"/P - Se inicio el paso a paso de manera correcta");
						}
					}
				} else {
					CodeCompiler codigo = new CodeCompiler(diagramas);
					codigo.main(false, false);
					if (codigo.isError) {
						int aux = text.getText().length();
						if (aux >= 0) {
							text.setText("");
						}
						// moverConsola(true);
						text.setText(codigo.errorTipe);
						diagramas.getTabItem().getInfo().addInformation(
								"/Ep - Error en el paso a paso:");
						diagramas.getTabItem().getInfo().addInformation(
								codigo.errorTipe);
						codigo.deleteMainFiles();
					} else {
						// moverConsola(true);
						disablePasoAPaso(true);
						ejecutar(false, codigo);
						diagramas
								.getTabItem()
								.getInfo()
								.addInformation(
										"/P - Se inicio el paso a paso de manera correcta");
					}
				}
			}
		});

		toolItem[12] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenNext = new Image(Ventana.display, "imagenes\\next.png");
		toolItem[12].setImage(ImageLoader.getImage("next.png"));
		toolItem[12].setEnabled(false);
		toolItem[12].setToolTipText("Paso Siguiente");
		toolItem[12].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (paso.ventanaLeer) {
					paso.ventanaLeer();
				} else {
					next();
				}
			}
		});
		toolItem[13] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenStop = new Image(Ventana.display, "imagenes\\Stop.png");
		toolItem[13].setImage(ImageLoader.getImage("Stop.png"));
		toolItem[13].setEnabled(false);
		toolItem[13].setToolTipText("Terminar Ejecucion");
		toolItem[13].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (MainWindow.getComponentes().isPasoAPaso) {
					MainWindow.getComponentes().disablePasoAPaso(false);
				}
				stopEjecucion();
			}
		});
		new ToolItem(toolbar, SWT.SEPARATOR);

		toolItem[14] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenExportC = new Image(Ventana.display,
		// "imagenes\\exportV.png");
		toolItem[14].setImage(ImageLoader.getImage("exportV.png"));
		toolItem[14].setToolTipText("Exportar a C");
		toolItem[14].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				FileDialog dialog = new FileDialog(MainWindow.shell, SWT.SAVE);
				dialog.setFilterExtensions(new String[] { "*.c" });
				String archivo = dialog.open();
				if (archivo != null) {
					if (dialog.getFileName().contains("\\")
							|| dialog.getFileName().contains("/")
							|| dialog.getFileName().contains(":")
							|| dialog.getFileName().contains("*")
							|| dialog.getFileName().contains("?")
							|| dialog.getFileName().contains("<")
							|| dialog.getFileName().contains(">")
							|| dialog.getFileName().contains("|")
							|| dialog.getFileName().contains("\"")) {
						MessageBox messageBox = new MessageBox(
								MainWindow.shell, SWT.ICON_ERROR | SWT.OK);
						messageBox.setText("Origami");
						messageBox
								.setMessage("El nombre de archivo, directorio o etiqueta del volumn no es vlido");
						int seleccion = messageBox.open();
						switch (seleccion) {
						case 64:
							break;
						case 128:
							break;
						}
					} else {
						boolean existe = false;
						try {
							File arch = new File(archivo);
							if (arch.exists()) {
								existe = true;
							}
						} catch (Exception e1) {
						}
						if (existe) {
							MessageBox messageBox = new MessageBox(
									MainWindow.shell, SWT.ICON_WARNING
											| SWT.YES | SWT.NO);
							messageBox.setText("Origami");
							messageBox
									.setMessage("El archivo ya existe. Desea reemplazarlo?");
							int seleccion = messageBox.open();
							switch (seleccion) {
							case 64:
								Exporter expor = new Exporter(diagramas);
								expor.codeCExport(archivo);
								break;
							case 128:
								break;
							}
						} else {
							Exporter expor = new Exporter(diagramas);
							expor.codeCExport(archivo);
						}
					}
				}
			}
		});
		toolItem[15] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenCpp = new Image(Ventana.display, "imagenes\\cpp.png");
		toolItem[15].setImage(ImageLoader.getImage("cpp.png"));
		toolItem[15].setToolTipText("Exportar a C++");
		toolItem[15].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				FileDialog dialog = new FileDialog(MainWindow.shell, SWT.SAVE);
				dialog.setFilterExtensions(new String[] { "*.cpp" });
				String archivo = dialog.open();
				if (archivo != null) {
					if (dialog.getFileName().contains("\\")
							|| dialog.getFileName().contains("/")
							|| dialog.getFileName().contains(":")
							|| dialog.getFileName().contains("*")
							|| dialog.getFileName().contains("?")
							|| dialog.getFileName().contains("<")
							|| dialog.getFileName().contains(">")
							|| dialog.getFileName().contains("|")
							|| dialog.getFileName().contains("\"")) {
						MessageBox messageBox = new MessageBox(
								MainWindow.shell, SWT.ICON_ERROR | SWT.OK);
						messageBox.setText("Origami");
						messageBox
								.setMessage("El nombre de archivo, directorio o etiqueta del volumn no es vlido");
						int seleccion = messageBox.open();
						switch (seleccion) {
						case 64:
							break;
						case 128:
							break;
						}
					} else {
						boolean existe = false;
						try {
							File arch = new File(archivo);
							if (arch.exists()) {
								existe = true;
							}
						} catch (Exception e1) {
						}
						if (existe) {
							MessageBox messageBox = new MessageBox(
									MainWindow.shell, SWT.ICON_WARNING
											| SWT.YES | SWT.NO);
							messageBox.setText("Origami");
							messageBox
									.setMessage("El archivo ya existe. Desea reemplazarlo?");
							int seleccion = messageBox.open();
							switch (seleccion) {
							case 64:
								Exporter expor = new Exporter(diagramas);
								expor.codeCppExport(archivo);
								break;
							case 128:
								break;
							}
						} else {
							Exporter expor = new Exporter(diagramas);
							expor.codeCppExport(archivo);
						}
					}
				}
			}
		});
		toolItem[16] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenExportExe = new Image(Ventana.display,
		// "imagenes\\exportExe.png");
		toolItem[16].setImage(ImageLoader.getImage("exportExe.png"));
		toolItem[16].setToolTipText("Exportar a .exe");
		toolItem[16].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				FileDialog dialog = new FileDialog(MainWindow.shell, SWT.SAVE);
				dialog.setFilterExtensions(new String[] { "*.exe" });
				String archivo = dialog.open();
				if (archivo != null) {
					if (dialog.getFileName().contains("\\")
							|| dialog.getFileName().contains("/")
							|| dialog.getFileName().contains(":")
							|| dialog.getFileName().contains("*")
							|| dialog.getFileName().contains("?")
							|| dialog.getFileName().contains("<")
							|| dialog.getFileName().contains(">")
							|| dialog.getFileName().contains("|")
							|| dialog.getFileName().contains("\"")) {
						MessageBox messageBox = new MessageBox(
								MainWindow.shell, SWT.ICON_ERROR | SWT.OK);
						messageBox.setText("Origami");
						messageBox
								.setMessage("El nombre de archivo, directorio o etiqueta del volumn no es vlido");
						int seleccion = messageBox.open();
						switch (seleccion) {
						case 64:
							break;
						case 128:
							break;
						}
					} else {
						boolean existe = false;
						try {
							File arch = new File(archivo);
							if (arch.exists()) {
								existe = true;
							}
						} catch (Exception e1) {
						}
						if (existe) {
							MessageBox messageBox = new MessageBox(
									MainWindow.shell, SWT.ICON_WARNING
											| SWT.YES | SWT.NO);
							messageBox.setText("Origami");
							messageBox
									.setMessage("El archivo ya existe. Desea reemplazarlo?");
							int seleccion = messageBox.open();
							switch (seleccion) {
							case 64:
								String nombre = dialog.getFileName();
								nombre = nombre.substring(0, nombre
										.indexOf("."));
								Exporter expor = new Exporter(diagramas);
								expor.executeFileExport(archivo, nombre);
								break;
							case 128:
								break;
							}
						} else {
							String nombre = dialog.getFileName();
							nombre = nombre.substring(0, nombre.indexOf("."));
							Exporter expor = new Exporter(diagramas);
							expor.executeFileExport(archivo, nombre);
						}
					}
				}
			}
		});
		new ToolItem(toolbar, SWT.SEPARATOR);

		toolItem[17] = new ToolItem(toolbar, SWT.PUSH);
		// Image imagenDonate = new Image(Ventana.display,
		// "imagenes\\Donate.png");
		toolItem[17].setImage(ImageLoader.getImage("Donate.png"));
		toolItem[17].setToolTipText("Donaciones");
		toolItem[17].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
			}
		});
	}

	public void guardarDisable(boolean disable) {
		toolItem[2].setEnabled(disable);
		MainWindow.menu.saveMenuItem.setEnabled(disable);
	}

	public void toolBarDisable() {
		if (MainWindow._selectionAdministrator.getFiguraSeleccionada() == -1) {
			for (int i = 4; i <= 7; i++) {
				toolItem[i].setEnabled(false);
			}
		} else {
			if (MainWindow._selectionAdministrator.getFiguraSeleccionada() != 0) {
				for (int i = 4; i <= 7; i++) {
					toolItem[i].setEnabled(true);
				}
				if (MainWindow._diagramAdministrator.diagrama.size() == 0) {
					toolItem[6].setEnabled(false);
				}
			} else {
				for (int i = 4; i <= 7; i++) {
					toolItem[i].setEnabled(false);
				}
				if (MainWindow._diagramAdministrator.diagrama.size() != 0) {
					toolItem[6].setEnabled(true);
				}
			}
		}
	}

	public void getBotones(ToolBar toolbar) {
		boton[0] = new Button(toolbar, SWT.FLAT);
		// Image imagenEntrada = new Image(Ventana.display,
		// "imagenes\\Entrada.png");
		// Image imagenEntrada = new Image(Ventana.display,
		// Ventana.class.getClassLoader().getResourceAsStream("imagenes\\Entrada.png"));
		boton[0].setImage(ImageLoader.getImage("Entrada.png"));
		boton[0].pack();
		boton[0].setToolTipText("Entrada");
		boton[0].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// String name = "imagenes\\cursorEntrada.png";
				// ImageData image = new ImageData(name);
				cursor[0] = new Cursor(MainWindow.display, ImageLoader
						.getImage("cursorEntrada.png").getImageData(), 0, 0);
				InputFigure entrada2 = new InputFigure();
				entrada2.instruction.instruccion = "null";
				MainWindow.mainFigure = null;
				MainWindow.mainFigure = entrada2;
				disableCursor();
			}
		});
		boton[1] = new Button(toolbar, SWT.FLAT);
		// Image imagenProceso = new Image(Ventana.display,
		// "imagenes\\Proceso.png");
		boton[1].setImage(ImageLoader.getImage("Proceso.png"));
		boton[1].pack();
		boton[1].setToolTipText("Expresin");
		boton[1].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// String name = "imagenes\\cursorProceso.png";
				// ImageData image = new ImageData(name);
				cursor[0] = new Cursor(MainWindow.display, ImageLoader
						.getImage("cursorProceso.png").getImageData(), 0, 0);
				SentenceFigure proceso2 = new SentenceFigure();
				proceso2.instruccion.instruccion = "null";
				MainWindow.mainFigure = null;
				MainWindow.mainFigure = proceso2;
				disableCursor();
			}
		});
		boton[2] = new Button(toolbar, SWT.FLAT);
		// Image imagenIf = new Image(Ventana.display, "imagenes\\If.png");
		boton[2].setImage(ImageLoader.getImage("If.png"));
		boton[2].pack();
		boton[2].setToolTipText("Decisin");
		boton[2].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// String name = "imagenes\\cursorIf.png";
				// ImageData image = new ImageData(name);
				cursor[0] = new Cursor(MainWindow.display, ImageLoader
						.getImage("cursorIf.png").getImageData(), 0, 0);
				DecisionFigure decision2 = new DecisionFigure();
				InstruccionSimple codigo = new InstruccionSimple();
				codigo.setInstruccionSimple("null");
				decision2.instruction.instruccion.add(0, codigo);
				MainWindow.mainFigure = null;
				MainWindow.mainFigure = decision2;
				disableCursor();
			}
		});
		boton[3] = new Button(toolbar, SWT.FLAT);
		// Image imagenWhile = new Image(Ventana.display,
		// "imagenes\\While.png");
		boton[3].setImage(ImageLoader.getImage("While.png"));
		boton[3].pack();
		boton[3].setToolTipText("Ciclo Mientras");
		boton[3].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// String name = "imagenes\\cursorWhile.png";
				// ImageData image = new ImageData(name);
				cursor[0] = new Cursor(MainWindow.display, ImageLoader
						.getImage("cursorWhile.png").getImageData(), 0, 0);
				WhileFigure While2 = new WhileFigure();
				InstruccionSimple codigo = new InstruccionSimple();
				codigo.setInstruccionSimple("null");
				While2.instruccion.instruccion.add(0, codigo);
				MainWindow.mainFigure = null;
				MainWindow.mainFigure = While2;
				disableCursor();
			}
		});
		boton[4] = new Button(toolbar, SWT.FLAT);
		// Image imagenFor = new Image(Ventana.display, "imagenes\\For.png");
		boton[4].setImage(ImageLoader.getImage("For.png"));
		boton[4].pack();
		boton[4].setToolTipText("Ciclo Para");
		boton[4].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// String name = "imagenes\\cursorFor.png";
				// ImageData image = new ImageData(name);
				cursor[0] = new Cursor(MainWindow.display, ImageLoader
						.getImage("cursorFor.png").getImageData(), 0, 0);
				ForFigure For2 = new ForFigure();
				InstruccionSimple codigo = new InstruccionSimple();
				codigo.setInstruccionSimple("null");
				For2.instruction.instruccion.add(0, codigo);
				MainWindow.mainFigure = null;
				MainWindow.mainFigure = For2;
				disableCursor();
			}
		});
		boton[5] = new Button(toolbar, SWT.FLAT);
		// Image imagenSalida = new Image(Ventana.display,
		// "imagenes\\Salida.png");
		boton[5].setImage(ImageLoader.getImage("Salida.png"));
		boton[5].pack();
		boton[5].setToolTipText("Salida");
		boton[5].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// String name = "imagenes\\cursorSalida.png";
				// ImageData image = new ImageData(name);
				cursor[0] = new Cursor(MainWindow.display, ImageLoader
						.getImage("cursorSalida.png").getImageData(), 0, 0);
				OutputFigure salida2 = new OutputFigure();
				salida2.instruction.instruccion = "null";
				MainWindow.mainFigure = null;
				MainWindow.mainFigure = salida2;
				disableCursor();
			}
		});
		Label label = new Label(toolbar, SWT.NONE);
		label.setVisible(false);
		GridData labelData = new GridData();
		labelData.grabExcessVerticalSpace = true;
		label.setLayoutData(labelData);

		Button botonConsola = new Button(toolbar, SWT.FLAT);
		botonConsola.setBounds(40, 600, 50, 30);
		// Image imagen = new Image(Ventana.display,"imagenes\\consola.ico");
		botonConsola.setImage(ImageLoader.getImage("consola.ico"));
		botonConsola.pack();
		botonConsola.setToolTipText("Consola ");
		botonConsola.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (MainWindow.menu.consoleMenuItem.getSelection()) {
					MainWindow.menu.consoleMenuItem.setSelection(false);
					moverConsola(false);
				} else {
					moverConsola(true);
					MainWindow.menu.consoleMenuItem.setSelection(true);
				}
			}
		});
		for (int i = 0; i <= 5; i++) {
			boton[i].addKeyListener(new org.eclipse.swt.events.KeyAdapter() {
				public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
					MainWindow._keyEvent.setKey(e);
					MainWindow._keyEvent.Accion();
				}
			});
		}
	}

	public void disableCursor() {
		MainWindow._diagrams.getHoja().getChart().disableCursor(
				MainWindow._diagrams.getHoja().getDiagrama(),
				MainWindow._diagrams.getHoja().getChart());
	}

	public void createTab() {
		item = new CTabItem(tabFolder, SWT.CLOSE);
		item.setText(" Consola   ");
		text = new Text(tabFolder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		text.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if ((sizeTab >= text.getCaretPosition()) && (e.keyCode == 8)) {
					e.doit = false;
				} else if (sizeTab > text.getCaretPosition()) {
					e.doit = false;
				}
				if (e.keyCode == 13 || e.keyCode == 16777296) {
					setText(texto());
				}
			}

			public void keyReleased(KeyEvent arg0) {
			}
		});
		ToolBar toolBarFolder = new ToolBar(tabFolder, SWT.LEFT);
		tabFolder.setTopRight(toolBarFolder);
		bot = new ToolItem(toolBarFolder, SWT.PUSH);
		final ToolItem bot2 = new ToolItem(toolBarFolder, SWT.PUSH);
		bot.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (MainWindow.getComponentes().isPasoAPaso) {
					MainWindow.getComponentes().disablePasoAPaso(false);
				}
				stopEjecucion();
			}
		});
		bot.setEnabled(false);
		bot.setToolTipText("Terminar Ejecucion");
		// Image imagen = new Image(Ventana.display, "imagenes\\Stop.png");
		bot.setImage(ImageLoader.getImage("Stop.png"));
		// Image imagen2 = new Image(Ventana.display, "imagenes\\monitor.gif");
		bot2.setImage(ImageLoader.getImage("monitor.gif"));
		bot2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// Image imagen3;
				if (hide) {
					// imagen3 = new Image(Ventana.display,
					// "imagenes\\network.gif");
					bot2.setImage(ImageLoader.getImage("network.gif"));
					hide = false;
				} else {
					// imagen3 = new Image(Ventana.display,
					// "imagenes\\monitor.gif");
					bot2.setImage(ImageLoader.getImage("monitor.gif"));
					hide = true;
				}
			}
		});
		item.setControl(text);
		// Image i = new Image(Ventana.display,"imagenes\\consola.ico");
		item.setImage(ImageLoader.getImage("consola.ico"));
	}

	public void ejecucionDisable() {
		if (enEjecucion) {
			bot.setEnabled(true);
			toolItem[13].setEnabled(true);
		} else {
			bot.setEnabled(false);
			toolItem[13].setEnabled(false);
		}
	}

	public String texto() {
		String texto = text.getText();
		String[] linea = text.getText().split("\n");
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
			if (diagramas.getHoja().getSizeDiagrama() == 2) {
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
				paso = new PasoAPaso(MainWindow._diagrams,
						MainWindow._selectionAdministrator);
				paso.ejecutar(this, "gdb", codigo);
				paso.main();
				seleccion = false;
				MainWindow.getComponents().toolItem[12].setEnabled(true);
				text.setEditable(false);
				text.setBackground(MainWindow.display
						.getSystemColor(SWT.COLOR_WHITE));
			}
		}
	}

	public void setInformation(int size) {
		this.sizeTab = size;
	}

	public boolean getEnEjecucion() {
		return enEjecucion;
	}

	public void stopEjecucion() {
		toolItem[12].setEnabled(false);
		enEjecucion = false;
		ejecucionDisable();
		if (seleccion) {
			eje.stopEjecutar();
		} else {
			disablePasoAPaso(false);
			paso.stopEjecutar();
			paso.colaConexiones.clear();
			int diag = diagramas.selec.getSeleccionDigrama();
			paso.tab.selec.setSeleccionDiagrama(paso.a.GetId());
			paso.limpiarPasoAnterior();
			diagramas.getHoja().pasoInicio = false;
			diagramas.getHoja().addFigure();
			paso.tab.selec.setSeleccionDiagrama(diag);
			text.setEditable(true);
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
			toolItem[12].setEnabled(false);
			paso.sendNext();
		}
		final Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			public void run() {
				MainWindow.display.syncExec(new Runnable() {
					public void run() {
						MainWindow.getComponents().toolItem[12]
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
			diagramas.selec.setFiguraSeleccionada(-1);
			diagramas.getHoja().addFigure();
			toolItem[3].setEnabled(false);
			toolItem[4].setEnabled(false);
			toolItem[5].setEnabled(false);
			toolItem[6].setEnabled(false);
			toolItem[7].setEnabled(false);
			toolItem[10].setEnabled(false);
			toolItem[11].setEnabled(false);
			toolItem[13].setEnabled(true);
			toolItem[14].setEnabled(false);
			toolItem[15].setEnabled(false);
			toolItem[16].setEnabled(false);
			boton[0].setEnabled(false);
			boton[1].setEnabled(false);
			boton[2].setEnabled(false);
			boton[3].setEnabled(false);
			boton[4].setEnabled(false);
			boton[5].setEnabled(false);
			MainWindow.menu.decisionMenuItem.setEnabled(false);
			MainWindow.menu.sentenceMenuItem.setEnabled(false);
			MainWindow.menu.inputMenuItem.setEnabled(false);
			MainWindow.menu.outputMenuItem.setEnabled(false);
			MainWindow.menu.forMenuItem.setEnabled(false);
			MainWindow.menu.whileMenuItem.setEnabled(false);
			MainWindow.menu.exportMenuItem.setEnabled(false);
			MainWindow.menu.compileMenuItem.setEnabled(false);
			MainWindow.menu.resetDiagramMenuItem.setEnabled(false);
			MainWindow.menu.stepByStepMenuItem.setEnabled(false);

		} else {
			isPasoAPaso = false;
			diagramas.getHoja().addFigure();
			toolItem[3].setEnabled(true);
			toolItem[10].setEnabled(true);
			toolItem[11].setEnabled(true);
			toolItem[13].setEnabled(false);
			toolItem[14].setEnabled(true);
			toolItem[15].setEnabled(true);
			toolItem[16].setEnabled(true);
			boton[0].setEnabled(true);
			boton[1].setEnabled(true);
			boton[2].setEnabled(true);
			boton[3].setEnabled(true);
			boton[4].setEnabled(true);
			boton[5].setEnabled(true);
			MainWindow.menu.decisionMenuItem.setEnabled(true);
			MainWindow.menu.sentenceMenuItem.setEnabled(true);
			MainWindow.menu.inputMenuItem.setEnabled(true);
			MainWindow.menu.outputMenuItem.setEnabled(true);
			MainWindow.menu.forMenuItem.setEnabled(true);
			MainWindow.menu.whileMenuItem.setEnabled(true);
			MainWindow.menu.exportMenuItem.setEnabled(true);
			MainWindow.menu.compileMenuItem.setEnabled(true);
			MainWindow.menu.resetDiagramMenuItem.setEnabled(true);
			MainWindow.menu.stepByStepMenuItem.setEnabled(true);
		}
	}

	public void disableAll(boolean disable) {
		toolItem[3].setEnabled(disable);
		toolItem[8].setEnabled(disable);
		toolItem[9].setEnabled(disable);
		toolItem[10].setEnabled(disable);
		toolItem[11].setEnabled(disable);
		toolItem[14].setEnabled(disable);
		toolItem[15].setEnabled(disable);
		toolItem[16].setEnabled(disable);
		boton[0].setEnabled(disable);
		boton[1].setEnabled(disable);
		boton[2].setEnabled(disable);
		boton[3].setEnabled(disable);
		boton[4].setEnabled(disable);
		boton[5].setEnabled(disable);
		MainWindow.menu.decisionMenuItem.setEnabled(disable);
		MainWindow.menu.sentenceMenuItem.setEnabled(disable);
		MainWindow.menu.inputMenuItem.setEnabled(disable);
		MainWindow.menu.outputMenuItem.setEnabled(disable);
		MainWindow.menu.forMenuItem.setEnabled(disable);
		MainWindow.menu.whileMenuItem.setEnabled(disable);
		MainWindow.menu.exportMenuItem.setEnabled(disable);
		MainWindow.menu.compileMenuItem.setEnabled(disable);
		MainWindow.menu.resetDiagramMenuItem.setEnabled(disable);
		MainWindow.menu.stepByStepMenuItem.setEnabled(disable);
		MainWindow.menu.saveAsMenuItem.setEnabled(disable);
		MainWindow.menu.buildCodeMenuItem.setEnabled(disable);
		guardarDisable(disable);
	}
}
