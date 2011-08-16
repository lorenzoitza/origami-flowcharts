package origami.graphics.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import origami.administration.ApplicationState;
import origami.administration.actions.NewDiagramLogic;
import origami.administration.functionality.DiagramFileManager;
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
	    System.out.println(WindowWidgets.tabFolder.getItemCount());
	    openNewDiagram(dialog.getFileName(), fileURL);
	}
    }

    public void openNewDiagram(String fileName, String address) {
	switch (openType) {
	case OPEN:
	    new NewDiagramLogic().addTab();
	    int position = fileName.indexOf('.');
	    String name = fileName.substring(0, position);
	    WindowWidgets.tabFolder.changeName(name);
	    WindowWidgets.tabFolder.getTabItem().getSave()
		    .setSave(true);
	    //El problema esta aqui....
	    WindowWidgets.tabFolder.openDiagram(address, _serializer);
	    //aaaaaaaaaaaaaaaaaaaaa
	    WindowWidgets.tabFolder.getTabItem().getSave().setDir(
		    address);
	    WindowWidgets.tabFolder.getTabItem().resetUndo();
	    WindowWidgets.tabFolder.getTabItem().addUndo(
		    WindowWidgets.tabFolder.getTabItem().getLeaf()
			    .getDiagrama(),
		    WindowWidgets.tabFolder.getAdminSelection());
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

    public void setOpenType(OpenType openType) {
	this.openType = openType;
    }

    public OpenType getOpenType() {
	return openType;
    }
}
