package origami.administration.functionality;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.widgets.MessageBox;

import origami.graphics.DiagramStructure;
import origami.graphics.MainWindow;
import origami.graphics.view.SaveFileView;
import origami.graphics.view.SaveType;
import origami.graphics.widgets.TabFolder;
import origami.graphics.widgets.TabItem;


/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class SaveDiagramController {

    public static TabFolder tabFolder;

    private String dir = "null";

    private boolean isSaved = true;

    public void setTabFolder(TabFolder tabfolder) {
	tabFolder = tabfolder;
    }

    public boolean isDiagrama(int selec) {
	return !(tabFolder.getTabItem().getLeaf().getSizeDiagrama() == 2);
    }


    public void saveDiagramHandler(CTabFolderEvent cTabFolderEvent) {
	TabItem tabItem = (TabItem) cTabFolderEvent.item;
	int tabSelectedIndex = tabItem.GetId() + 1;
	MessageBox messageBox =
		new MessageBox(MainWindow.shell, SWT.ICON_WARNING | SWT.YES
			| SWT.NO | SWT.CANCEL);
	messageBox.setText("Origami");
	if (tabItem.getText() == "*Diagrama " + tabSelectedIndex) {
	    messageBox
		    .setMessage("Deseas guardar los cambios efectuados en el diagrama "
			    + tabSelectedIndex + "?");
	} else {
	    messageBox
		    .setMessage("Deseas guardar los cambios efectuados en el diagrama "
			    + tabItem.getText().substring(1) + "?");
	}
	tabSelectedIndex = messageBox.open();
	switch (tabSelectedIndex) {
	case 64: 
	    SaveFileView save = new SaveFileView();
	    if (tabFolder.getTabItem().getSave().getDir() == "null") {
		save.setSaveType(SaveType.SAVE);
		boolean success = save.createWindow();
		if(success){
		    cTabFolderEvent.doit = false;
		}
	    } else {
		save.saveAction();
	    }
	    break;
	case 128:
	    cTabFolderEvent.doit = true;
	    closeCTabFolder(tabItem.GetId());
	    break;
	case 256:
	    cTabFolderEvent.doit = false;
	    break;
	}
    }

    public static void closeCTabFolder(int i) {
	if (tabFolder.getItemCount() == 1) {
	    DiagramStructure.getInstance().getPanel().removeAll();	    
	    tabFolder.getTabItem().getLeaf().getDibujarDiagrama().setOpaque(false);
	    MainWindow.getComponents().diagramData.exclude = true;
	    DiagramStructure.getInstance().setBoundsToZero();
	    tabFolder.getTabItem().getLeaf().getDiagrama().removeAllElements();
//	    MainWindow.getComponents().disableAll(false);
	    MainWindow.getComponents().customToolBar.setEnabledItemsCloseAllTabItem(false);
	    MainWindow.getComponents().getFiguresToolBar().setEnabledAllButtons(false);
	    MainWindow.getComponents().getCustomMenu().setEnabledItemsCloseAllTabItem(false);
	}
    }

    public void updateUI() {
	if (isSaved) {
	    String nombre = tabFolder.getCurrentTabItemName();
	    if (nombre.startsWith("*")) {
		nombre = nombre.substring(1);
		tabFolder.changeName(nombre);
//		MainWindow.getComponents().setEnabledSaveItems(false);
		MainWindow.getComponents().customToolBar.setEnabledItemSave(false);
	    } else {
//		MainWindow.getComponents().setEnabledSaveItems(false);
		MainWindow.getComponents().customToolBar.setEnabledItemSave(false);
	    }
	} else {
	    String nombre = tabFolder.getCurrentTabItemName();
	    if (!nombre.startsWith("*")) {
		nombre = "*" + nombre;
		tabFolder.changeName(nombre);
//		MainWindow.getComponents().setEnabledSaveItems(true);
		MainWindow.getComponents().customToolBar.setEnabledItemSave(true);
	    }
	}
    }

    public void checkChanges() {
	if (isSaved) {
	    if (tabFolder.getTabItem().getLeaf().getDiagrama().size() == 2) {
//		MainWindow.getComponents().setEnabledSaveItems(true);
		MainWindow.getComponents().customToolBar.setEnabledItemSave(true);
	    } else {
//		MainWindow.getComponents().setEnabledSaveItems(false);
		MainWindow.getComponents().customToolBar.setEnabledItemSave(false);
	    }
	} else {
//	    MainWindow.getComponents().setEnabledSaveItems(true);
	    MainWindow.getComponents().customToolBar.setEnabledItemSave(true);
	}
    }

    public String getDir() {
	return dir;
    }

    public void setDir(String dir) {
	this.dir = dir;
    }

    public boolean isSave() {
	return isSaved;
    }

    public void setSave(boolean save) {
	this.isSaved = save;
	updateUI();
    }
}
