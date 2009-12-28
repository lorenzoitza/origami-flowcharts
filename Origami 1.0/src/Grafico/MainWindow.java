package Grafico;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ui.listener.CloseWindowAction;
import Administracion.OrigamiException;
import Imagenes.ImageLoader;

public class MainWindow {

    public static Display display = new Display();

    public static Shell shell = new Shell(display);

    private static Componentes _components;
    
    public static CustomMenu menu;

    public static final Cursor[] cursor = new Cursor[1];
    
    public MainWindow() {
	initWindow();
	
	menu.createMenu();
	
	
	addListeners();
    }

    private void initWindow() {
	menu = new CustomMenu(shell, display);
	
	_components = new Componentes(display);
	_components.agregarComponentes();
	
	shell.setText("Origami");
	shell.setMaximized(true);
	shell.setImage(ImageLoader.getImage("icono.GIF"));
	shell.addShellListener(new CloseWindowAction(getComponents()._diagrams, getComponents()));
	shell.setMenuBar(menu.mainMenu);
	shell.setLayout(getComponents().layout);
	shell.pack();
    }

    private void addListeners() {
	shell.addShellListener(new ShellAdapter() {
	    public void shellDeactivated(ShellEvent e) {
		Cursor[] cursor = new Cursor[1];
		Componentes.mainFigure = null;
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
    }

    private void show() {
	shell.open();
    }

    public Shell getShell() {
	return shell;
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
