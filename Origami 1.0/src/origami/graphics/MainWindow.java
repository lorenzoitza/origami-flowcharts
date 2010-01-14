package origami.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import origami.administration.ApplicationState;
import origami.administration.OrigamiException;
import origami.graphics.listeners.CloseWindowListener;
import origami.graphics.widgets.CustomMenu;
import origami.images.ImageLoader;

public class MainWindow {

    public static Display display;

    public static Shell shell;

    private static Componentes components;
    
    private CustomMenu menu;

    private GridLayout shellLayout;

    public MainWindow() {
	display = new Display();
	
	shell = new Shell(display);
	
	menu = new CustomMenu(shell, display);
	
	components = new Componentes(display, menu);
	
	shellLayout = new GridLayout(2, false);
	
	addWindowComponents();
	
	addShellProperties();
	
	addShellListeners();
    }

    private void addWindowComponents(){
	components.agregarComponentes(shellLayout);
    }
    
    private void addShellProperties() {
	shell.setText("Origami");
	shell.setMaximized(true);
	shell.setImage(ImageLoader.getImage("icono.GIF"));
	shell.addShellListener(new CloseWindowListener(Componentes._diagrams, getComponents()));
	shell.setMenuBar(menu.getMainMenu());
	shell.setLayout(shellLayout);
	shell.pack();
    }

    private void addShellListeners() {
	shell.addShellListener(new ShellAdapter() {
	    public void shellDeactivated(ShellEvent e) {
		Cursor[] cursor = new Cursor[1];
		ApplicationState.mainFigure = null;
		Cursor oldCursor = cursor[0];
		cursor[0] = new Cursor(null, SWT.CURSOR_ARROW);
		Componentes._diagrams.getTabItem().getLeaf().getDibujarDiagrama().setCursor(cursor[0]);
		if (oldCursor != null) {
		    oldCursor.dispose();
		}
		if (Componentes._diagrams.getItemCount() != 0) {
		    Componentes._diagrams.getTabItem().getLeaf().addFigure();
		}
	    }
	});
    }

    private void show() {
	shell.open();
    }

    public static Componentes getComponents() {
	return components;
    }

    public static void main(String args[]) throws OrigamiException {
	try {
	    MainWindow mainWindow = new MainWindow();
	    mainWindow.show();
	    
	    BaseDeDiagrama.getInstance().resetScrollBar();
	    
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
