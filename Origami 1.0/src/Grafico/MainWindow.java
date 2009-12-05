package Grafico;

import java.io.InputStream;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;

import ui.events.KeyEvent;
import ui.events.WindowEvent;


import Administracion.*;
import Administracion.Eventos.EventoVentana;
import Administracion.Funcionalidad.DiagramFileManager;
import Grafico.Figuras.SelectionSquare;
import Imagenes.ImageLoader;

/**
 * Esta clase es la interfaz y crea la ventana principal asi como sus
 * componentes.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez benjamin chuy
 */
public class MainWindow {

    public static Display display = new Display();

    public static Shell shell = new Shell(display);

    public static AdminDiagrama _diagramAdministrator; // TODO: Cambiar la clase
						       // a DiagramAdministrator

    public static Figura mainFigure = null;

    private static DiagramFileManager _serializer = new DiagramFileManager();

    public static Vector<SelectionSquare> seleccion =
	    new Vector<SelectionSquare>();

    public static AdminSeleccion _selectionAdministrator = new AdminSeleccion(); // TODO:
										 // Cambiar
										 // la
										 // clase
								 // a
    public static boolean isCut = false;
    
    public static TabFolder _diagrams;

    public static KeyEvent _keyEvent;

    public static boolean dispToolItem = false; // TODO: �?

    private static Componentes _components;

    public static CustomMenu menu;
    
    public void disableCursor() {
	_diagrams.getHoja().getChart().disableCursor(
		_diagrams.getHoja().getDiagrama(),
		_diagrams.getHoja().getChart());
    }

    public static Componentes getComponentes() {
	return getComponents();
    }

    public void cargarImagenes() {
	InputStream iconStream = this.getClass().getResourceAsStream("");
	Image image = new Image(display, iconStream);
    }

    /**
     * Este metodo crea la ventana de inicio.
     * 
     * @param args
     * @throws OrigamiException
     */

    public MainWindow() {
	initWindow();
	
	menu.createMenu();
	
	initControllers();
	addListeners();
    }

    private void initWindow() {
	menu = new CustomMenu(shell, display, this, _selectionAdministrator);
	
	setComponents(new Componentes(this));
	getComponents().agregarComponentes(_selectionAdministrator);
	getComponents().setDiagrama(_diagrams);
	shell.setText("Origami");
	shell.setMaximized(true);
	shell.setImage(ImageLoader.getImage("icono.GIF"));
	shell.addShellListener(new WindowEvent(_diagrams, getComponents()));
	shell.setMenuBar(menu.mainMenu);
	shell.setLayout(getComponents().layout);
	shell.pack();
    }

    private void addListeners() {
	shell.addShellListener(new ShellAdapter() {

	    public void shellDeactivated(ShellEvent e) {
		Cursor[] cursor = new Cursor[1];
		Grafico.MainWindow.mainFigure = null;
		Cursor oldCursor = cursor[0];
		cursor[0] = new Cursor(null, SWT.CURSOR_ARROW);
		_diagrams.getHoja().getDibujarDiagrama().setCursor(cursor[0]);
		if (oldCursor != null) {
		    oldCursor.dispose();
		}
		if (_diagrams.getItemCount() != 0) {
		    _diagrams.getHoja().addFigure();
		}
	    }
	});

	_keyEvent = new KeyEvent(_selectionAdministrator, _diagrams);

    }

    private void initControllers() {
	_diagramAdministrator = new AdminDiagrama(_selectionAdministrator);
    }

    private void show() {
	shell.open();
    }

    public Shell getShell() {
	return shell;
    }

    /**
     * @param serializer
     *            the serializer to set
     */
    public static void setSerializer(DiagramFileManager serializer) {
	_serializer = serializer;
    }

    /**
     * @return the serializer
     */
    public static DiagramFileManager getSerializer() {
	return _serializer;
    }

    /**
     * @param components
     *            the components to set
     */
    public static void setComponents(Componentes components) {
	_components = components;
    }

    /**
     * @return the components
     */
    public static Componentes getComponents() {
	return _components;
    }

    public static void main(String args[]) throws OrigamiException {
	try {
	    MainWindow mainWindow = new MainWindow();
	    mainWindow.show();
	    // TODO: �Para que sirve esta bandera?
	    int bandera = 0;

	    while (!shell.isDisposed() && bandera <= 15) {
		while (!display.readAndDispatch()) {
		    _diagrams.getHoja().resetScrollBar();
		    bandera++;
		}
	    }
	    while (!shell.isDisposed()) {
		while (!display.readAndDispatch()) {
		    display.sleep();
		}
	    }
	} catch (Exception e) {
	    throw new OrigamiException(e);
	}
    }
}