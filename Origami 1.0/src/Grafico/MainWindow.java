package Grafico;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ui.events.KeyEvent;
import ui.events.WindowEvent;
import Administracion.AdminDiagrama;
import Administracion.AdminSeleccion;
import Administracion.Figura;
import Administracion.OrigamiException;
import Administracion.Funcionalidad.DiagramFileManager;
import Imagenes.ImageLoader;

public class MainWindow {

    public static Display display = new Display();

    public static Shell shell = new Shell(display);

    public static KeyEvent _keyEvent;
    
    private static Componentes _components;
    
    public static CustomMenu menu;
    
    

    private static DiagramFileManager _serializer = new DiagramFileManager();
    
    public static AdminDiagrama _diagramAdministrator;     

    public static AdminSeleccion _selectionAdministrator = new AdminSeleccion(); 
    
    public static Figura mainFigure = null;
    
    
    
    public static Componentes getComponentes() {
	return getComponents();
    }
    
    public MainWindow() {
	initWindow();
	
	menu.createMenu();
	
	initControllers();
	addListeners();
    }

    private void initWindow() {
	menu = new CustomMenu(shell, this);
	
	setComponents(new Componentes(this));
	
	getComponents().agregarComponentes(_selectionAdministrator);
	getComponents().setDiagrama(getComponents()._diagrams);
	shell.setText("Origami");
	shell.setMaximized(true);
	shell.setImage(ImageLoader.getImage("icono.GIF"));
	shell.addShellListener(new WindowEvent(getComponents()._diagrams, getComponents()));
	shell.setMenuBar(menu.mainMenu);
	shell.setLayout(getComponents().layout);
	shell.pack();
    }

    private void addListeners() {
	shell.addShellListener(new ShellAdapter() {
	    public void shellDeactivated(ShellEvent e) {
		Cursor[] cursor = new Cursor[1];
		mainFigure = null;
		Cursor oldCursor = cursor[0];
		cursor[0] = new Cursor(null, SWT.CURSOR_ARROW);
		Componentes._diagrams.getHoja().getDibujarDiagrama().setCursor(cursor[0]);
		if (oldCursor != null) {
		    oldCursor.dispose();
		}
		if (Componentes._diagrams.getItemCount() != 0) {
		    Componentes._diagrams.getHoja().addFigure();
		}
	    }
	});

	_keyEvent = new KeyEvent();
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

    public static void setSerializer(DiagramFileManager serializer) {
	_serializer = serializer;
    }

    public static DiagramFileManager getSerializer() {
	return _serializer;
    }

    public static void setComponents(Componentes components) {
	_components = components;
    }

    public static Componentes getComponents() {
	return _components;
    }

    public static void main(String args[]) throws OrigamiException {
	try {
	    MainWindow mainWindow = new MainWindow();
	    mainWindow.show();
	    
	    int bandera = 0;

	    while (!shell.isDisposed() && bandera <= 15) {
		while (!display.readAndDispatch()) {
		    Componentes._diagrams.getHoja().resetScrollBar();
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