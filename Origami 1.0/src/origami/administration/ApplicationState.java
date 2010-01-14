package origami.administration;

import org.eclipse.swt.graphics.Cursor;


public class ApplicationState {

    public static final Cursor[] cursor = new Cursor[1];
    public static FigureStructure mainFigure = null;
    public static AdminSelection _selectionAdministrator = new AdminSelection();
    public static AdminDiagram _diagramAdministrator;

    public static void init(){
	_diagramAdministrator = new AdminDiagram(_selectionAdministrator);
    }
}
