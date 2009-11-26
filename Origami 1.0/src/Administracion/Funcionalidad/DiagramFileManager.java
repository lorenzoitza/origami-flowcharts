package Administracion.Funcionalidad;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import Administracion.CustomFile;
import Administracion.TabFolder;
import Grafico.MainWindow;


public class DiagramFileManager {

    private Serializer serializer = new Serializer();

    private String fileName;


    public boolean saveDiagram(TabFolder selectedTab) {

	try {
	    CustomFile seriliazableFile =
		    new CustomFile(selectedTab.getHoja().getDiagrama());
	    seriliazableFile.setInfo(selectedTab.getTabItem().getInfo()
		    .getInfo());
	    
	    serializer.writeFile(seriliazableFile, fileName);
	    selectedTab.getTabItem().getInfo().removeTime();
	} catch (Exception e) {
	    errorMessage();
	    e.printStackTrace();
	    return false;
	}
	return true;
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
