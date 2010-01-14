package origami.graphics.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import origami.administration.ApplicationState;
import origami.administration.funtionality.DiagramFileManager;
import origami.graphics.MainWindow;
import origami.graphics.WindowWidgets;

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
	    if (WindowWidgets.tabFolder.getTabItem().getLeaf()
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
	    WindowWidgets.tabFolder.changeName(name);
	    WindowWidgets.tabFolder.getTabItem().getSave()
		    .setSave(true);
	    WindowWidgets.tabFolder.openDiagram(address, _serializer);
	    WindowWidgets.tabFolder.getTabItem().getSave().setDir(
		    address);
	    break;
	case OPENEXAMPLE:
	    position = fileName.indexOf('.');
	    String name2 = fileName.substring(0, position);
	    WindowWidgets.tabFolder.changeName(name2);
	    WindowWidgets.tabFolder.openDiagram(address, _serializer);
	    break;
	default:
	    break;
	}
    }

    public void openDiagram(String nomArchivo, String address) {
	switch (openType) {
	case OPEN:
	    ApplicationState._selectionAdministrator.setSelectedFigure(0);
	    WindowWidgets.tabFolder.getTabItem().getLeaf()
		    .openFile(address, _serializer);
	    WindowWidgets.tabFolder.getTabItem().getSave().setDir(
		    address);
	    int pos = nomArchivo.indexOf('.');
	    String name = nomArchivo.substring(0, pos);
	    WindowWidgets.tabFolder.changeName(name);
	    WindowWidgets.tabFolder.getTabItem().getSave()
		    .setSave(true);
	    WindowWidgets.tabFolder.getTabItem().resetUndo();
	    WindowWidgets.tabFolder.getTabItem().addUndo(
		    WindowWidgets.tabFolder.getTabItem().getLeaf()
			    .getDiagrama(),
		    WindowWidgets.tabFolder.getAdminSelection());
	    break;
	case OPENEXAMPLE:
	    ApplicationState._selectionAdministrator.setSelectedFigure(0);
	    WindowWidgets.tabFolder.getTabItem().getLeaf()
		    .openFile(address, _serializer);
	    int pos2 = nomArchivo.indexOf('.');
	    String name2 = nomArchivo.substring(0, pos2);
	    WindowWidgets.tabFolder.changeName(name2);
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
