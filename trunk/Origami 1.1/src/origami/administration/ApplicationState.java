package origami.administration;

import org.eclipse.swt.graphics.Cursor;


public class ApplicationState {

    public static final Cursor[] cursor = new Cursor[1];
    public static Figura mainFigure = null;
    public static AdminSeleccion _selectionAdministrator = new AdminSeleccion();
    public static AdminDiagrama _diagramAdministrator;

    public static void init(){
	_diagramAdministrator = new AdminDiagrama(_selectionAdministrator);
    }
}
