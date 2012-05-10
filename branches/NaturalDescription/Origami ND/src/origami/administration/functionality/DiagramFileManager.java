package origami.administration.functionality;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import origami.administration.CustomFile;
import origami.graphics.MainWindow;
import origami.graphics.widgets.CustomTabFolder;


public class DiagramFileManager {

    private Serializer serializer;

    private String fileName;
    
    public DiagramFileManager() {
	serializer = new Serializer();
    }

    public boolean saveDiagram(CustomTabFolder selectedTab) {
	try {
	    CustomFile seriliazableFile =
		    new CustomFile(selectedTab.getTabItem().getLeaf().getDiagrama());
	    seriliazableFile.setInfo(selectedTab.getTabItem().getInformation()
		    .getInformation());
	    
	    serializer.writeFile(seriliazableFile, fileName);
	} catch (Exception e) {
	    errorMessage();
	    e.printStackTrace();
	    return false;
	}
	return true;
    }
    public void saveLog(CustomTabFolder selectedTab){
	try {
	    CustomFile seriliazableFile=(CustomFile)serializer.readFile(fileName);
	    seriliazableFile.setInfo(selectedTab.getTabItem().getInformation()
		    .getInformation());
	    serializer.writeFile(seriliazableFile, fileName);
	} catch (Exception e) {
	    errorMessage();
	    e.printStackTrace();
	}
    }
    private void errorMessage() {
	MessageBox messageBox =
		new MessageBox(MainWindow.shell, SWT.ICON_ERROR | SWT.OK);
	messageBox.setText("Origami");
	messageBox.setMessage("El nombre de archivo, directorio o etiqueta del"
		+ " volum�n no es v�lido");

    }

    public CustomFile recoverDiagram(String diagramPath) {

	try {
	    return serializer.readFile(diagramPath);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    public void setFile(String fileName) {
	this.fileName = fileName;
    }

    public String getFile() {
	return fileName;
    }
}
