package origami.graphics.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import origami.administration.ApplicationState;
import origami.administration.funtionality.DiagramFileManager;
import origami.graphics.Componentes;
import origami.graphics.MainWindow;

public class OpenFileView {

    private OpenType openType;

    private static DiagramFileManager _serializer = new DiagramFileManager();

    public void createWindow() {
	FileDialog dialog = new FileDialog(MainWindow.shell, SWT.OPEN);
	dialog.setFilterExtensions(new String[] { "*.Org", "*.*" });
	switch (openType) {
	case OPENEXAMPLE:
	    dialog.setFilterPath("ejemplos\\");
	    break;
	default:
	    break;
	}
	String fileURL = dialog.open();
	if (fileURL != null) {
	    if (MainWindow.getComponents()._diagrams.getTabItem().getLeaf()
		    .getSizeDiagrama() == 0) {
		openNewDiagram(dialog.getFileName(), fileURL);
	    } else {
		openDiagram(dialog.getFileName(), fileURL);
	    }
	}
    }

    public void openNewDiagram(String fileName, String address) {
	switch (openType) {
	case OPEN:
	    int position = fileName.indexOf('.');
	    String name = fileName.substring(0, position);
	    MainWindow.getComponents()._diagrams.changeName(name);
	    MainWindow.getComponents()._diagrams.getTabItem().getSave()
		    .setSave(true);
	    MainWindow.getComponents()._diagrams.openDiagram(address, _serializer);
	    MainWindow.getComponents()._diagrams.getTabItem().getSave().setDir(
		    address);
	    break;
	case OPENEXAMPLE:
	    position = fileName.indexOf('.');
	    String name2 = fileName.substring(0, position);
	    MainWindow.getComponents()._diagrams.changeName(name2);
	    MainWindow.getComponents()._diagrams.openDiagram(address, _serializer);
	    break;
	default:
	    break;
	}
    }

    public void openDiagram(String nomArchivo, String address) {
	switch (openType) {
	case OPEN:
	    ApplicationState._selectionAdministrator.setFiguraSeleccionada(0);
	    MainWindow.getComponents()._diagrams.getTabItem().getLeaf()
		    .openFile(address, _serializer);
	    MainWindow.getComponents()._diagrams.getTabItem().getSave().setDir(
		    address);
	    int pos = nomArchivo.indexOf('.');
	    String name = nomArchivo.substring(0, pos);
	    MainWindow.getComponents()._diagrams.changeName(name);
	    MainWindow.getComponents()._diagrams.getTabItem().getSave()
		    .setSave(true);
	    MainWindow.getComponents()._diagrams.getTabItem().resetUndo();
	    MainWindow.getComponents()._diagrams.getTabItem().addUndo(
		    MainWindow.getComponents()._diagrams.getTabItem().getLeaf()
			    .getDiagrama(),
		    MainWindow.getComponents()._diagrams.getAdminSelection());
	    break;
	case OPENEXAMPLE:
	    ApplicationState._selectionAdministrator.setFiguraSeleccionada(0);
	    MainWindow.getComponents()._diagrams.getTabItem().getLeaf()
		    .openFile(address, _serializer);
	    // MainWindow.getComponents()._diagrams.getHoja().openFile(address,_serializer);
	    int pos2 = nomArchivo.indexOf('.');
	    String name2 = nomArchivo.substring(0, pos2);
	    MainWindow.getComponents()._diagrams.changeName(name2);
	    break;
	default:
	    break;
	}
    }

    public void setOpenType(OpenType openType) {
	this.openType = openType;
    }

    public OpenType getOpenType() {
	return openType;
    }
}
