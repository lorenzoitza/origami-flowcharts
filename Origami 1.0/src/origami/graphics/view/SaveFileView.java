package origami.graphics.view;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import origami.administration.funtionality.DiagramExporter;
import origami.administration.funtionality.DiagramFileManager;
import origami.graphics.MainWindow;

public class SaveFileView {

    private SaveType saveType;

    private static DiagramFileManager serializer = new DiagramFileManager();

    public SaveType getSaveType() {
	return saveType;
    }

    public void setSaveType(SaveType saveType) {
	this.saveType = saveType;
    }

    public boolean createWindow() {
	FileDialog dialog = new FileDialog(MainWindow.shell, SWT.SAVE);
	switch (saveType) {
	case SAVE:
	    dialog.setFilterExtensions(new String[] { "*.Org" });
	    break;
	case SAVEAS:
	    dialog.setFilterExtensions(new String[] { "*.Org" });
	    break;
	case EXPORTC:
	    dialog.setFilterExtensions(new String[] { "*.c" });
	case EXPORTCPP:
	    dialog.setFilterExtensions(new String[] { "*.cpp" });
	    break;
	case EXPORTEXE:
	    dialog.setFilterExtensions(new String[] { "*.exe" });
	    break;
	case EXPORTIMAGE:
	    dialog
		    .setFilterExtensions(new String[] { "*.jpg", "*.bmp",
			    "*.png" });
	    break;
	default:
	    break;
	}
	String file = dialog.open();
	if (file != null) {
	    if (containSpecialCharacter(dialog.getFileName())) {

		return isNewFile();
	    } else {
		return existFile(file, dialog.getFileName());
	    }
	} else {
	    return false;
	}
    }

    public boolean saveAction() {
	serializer.setFile(MainWindow.getComponents().tabFolder.getTabItem()
		.getSave().getDir());
	serializer.saveDiagram(MainWindow.getComponents().tabFolder);
	MainWindow.getComponents().tabFolder.getTabItem().getSave().setSave(
		true);
	return true;
    }

    private void action(String fileName, String fileAdress) {
	switch (saveType) {
	case SAVE:
	    save(fileName,fileAdress);
	    break;
	case SAVEAS:
	    saveAs(fileName,fileAdress);
	    break;
	case EXPORTC:
	    exportC(fileName);
	    break;
	case EXPORTCPP:
	    exportCpp(fileName);
	    break;
	case EXPORTEXE:
	    exportExe(fileName, fileAdress);
	    break;
	case EXPORTIMAGE:
	    exportImage(fileAdress);
	    break;
	default:
	    break;
	}
    }
    
    private boolean containSpecialCharacter(String fileName) {
	return fileName.contains("\\") || fileName.contains("/")
		|| fileName.contains(":") || fileName.contains("*")
		|| fileName.contains("?") || fileName.contains("<")
		|| fileName.contains(">") || fileName.contains("|")
		|| fileName.contains("\"");
    }

    private boolean isNewFile() {
	MessageBox messageBox =
		new MessageBox(MainWindow.shell, SWT.ICON_ERROR | SWT.OK);
	messageBox.setText("Origami");
	messageBox
		.setMessage("El nombre de archivo, directorio o etiqueta del volumn no es vlido");
	int seleccion = messageBox.open();
	switch (seleccion) {
	case 64:
	    break;
	case 128:
	    break;
	}
	return false;
    }

    private boolean existFile(String fileURL, String fileName) {
	boolean isExist = false;
	try {
	    File file = new File(fileURL);
	    if (file.exists()) {
		isExist = true;
	    }
	} catch (Exception e1) {
	}
	if (isExist) {
	    MessageBox messageBox =
		    new MessageBox(MainWindow.shell, SWT.ICON_WARNING | SWT.YES
			    | SWT.NO);
	    messageBox.setText("Origami");
	    messageBox.setMessage("El archivo ya existe. ¿Desea reemplazarlo?");
	    int selection = messageBox.open();
	    switch (selection) {
	    case 64:
		action(fileName, fileURL);
		return true;
	    case 128:
		return false;
	    }
	} else {
	    action(fileName, fileURL);
	    return true;
	}
	return false;
    }

    private void save(String fileName,String fileAdress) {
	MainWindow.getComponents().tabFolder.getTabItem().getSave().setDir(
		fileName);
	serializer.setFile(fileAdress);
	boolean isError =
		serializer.saveDiagram(MainWindow.getComponents().tabFolder);
	if (isError) {
	    int pos = fileName.indexOf('.');
	    String name = fileName.substring(0, pos);
	    MainWindow.getComponents().tabFolder.getTabItem().getSave()
		    .setSave(true);
	    MainWindow.getComponents().tabFolder.changeName(name);
	}
    }

    private void saveAs(String fileName,String fileAdress) {
	serializer.setFile(fileName);
	serializer.setFile(fileAdress);
	boolean isError =
		serializer.saveDiagram(MainWindow.getComponents().tabFolder);
	if (isError) {
	    int pos = fileName.indexOf('.');
	    String name = fileName.substring(0, pos);
	    MainWindow.getComponents().tabFolder.getTabItem().getSave()
		    .setSave(true);
	    MainWindow.getComponents().tabFolder.changeName(name);
	}
    }

    public void exportC(String fileName) {
	DiagramExporter exporter = new DiagramExporter(MainWindow.getComponents().tabFolder);
	exporter.codeCExport(fileName);
    }

    public void exportCpp(String fileName) {
	DiagramExporter exporter = new DiagramExporter(MainWindow.getComponents().tabFolder);
	exporter.codeCppExport(fileName);
    }

    public void exportExe(String fileName, String fileAdress) {
	fileName = fileName.substring(0, fileName.indexOf("."));
	DiagramExporter exporter = new DiagramExporter(MainWindow.getComponents().tabFolder);
	exporter.executeFileExport(fileAdress, fileName);
    }

    public void exportImage(String fileAdress) {
	DiagramExporter exporter = new DiagramExporter(MainWindow.getComponents().tabFolder);
	exporter
		.exportJPGFile(fileAdress, MainWindow.getComponents().tabFolder
			.getTabItem().getLeaf().getDiagrama(), MainWindow
			.getComponents().tabFolder.getTabItem().getLeaf()
			.getConexion());
    }
}
