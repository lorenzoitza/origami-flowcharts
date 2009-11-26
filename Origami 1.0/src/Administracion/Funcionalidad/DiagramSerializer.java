package Administracion.Funcionalidad;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import Administracion.Archivo;
import Administracion.TabFolder;
import Grafico.MainWindow;

/**
 * Esta clase guarda y abre el diagrama realizado.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class DiagramSerializer {

    private Serializer serializer = new Serializer();

    private String fileName;

    /**
     * Este metodo es el encargado de guardar el diagrama y recibe el diagrama y
     * la direccion a guardar.
     * 
     * @param figuras
     * @param fileDir
     */
    public boolean saveDiagram(TabFolder selectedTab) {

	try {
	    Archivo seriliazableFile =
		    new Archivo(selectedTab.getHoja().getDiagrama());
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

    /**
     * Este metodo es el encargado de abrir el diagrama guardado.
     * 
     * @param diagramPath
     * @return
     */
    public Archivo recoverDiagram(String diagramPath) {

	try {
	    return serializer.recoverFile(diagramPath);
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
