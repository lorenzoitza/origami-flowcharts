package Administracion.Funcionalidad;

import java.io.*;
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
public class SerializeFile {

    private String file;

    /**
     * Este metodo es el encargado de guardar el diagrama y recibe el diagrama y
     * la direccion a guardar.
     * 
     * @param figuras
     * @param fileDir
     */
    public boolean saveFile(TabFolder selectedTab) {
	try {
	    selectedTab.getTabItem().getInfo().addTime();

	    FileOutputStream fileStream = new FileOutputStream(file);
	    ObjectOutputStream objectStream =
		    new ObjectOutputStream(fileStream);

	    Archivo file = new Archivo(selectedTab.getHoja().getDiagrama());
	    file.setInfo(selectedTab.getTabItem().getInfo().getInfo());
	    objectStream.writeObject(file); // 3

	    selectedTab.getTabItem().getInfo().removeTime();
	    objectStream.close();
	} catch (Exception e) {
	    MessageBox messageBox = new MessageBox(MainWindow._shell, SWT.ICON_ERROR | SWT.OK);
	    messageBox.setText("Origami");
	    messageBox.setMessage("El nombre de archivo, directorio o etiqueta del" +
		    		" volumén no es válido");

	    e.printStackTrace();
	    return false;
	}
	return true;
    }

    /**
     * Este metodo es el encargado de abrir el diagrama guardado.
     * 
     * @param fileDir
     * @return
     */
    public Archivo openFile(String fileDir) {
	Archivo file = null;
	try {
	    FileInputStream fileStream = new FileInputStream(fileDir);
	    ObjectInputStream objectStream = new ObjectInputStream(fileStream);
	    file = (Archivo) objectStream.readObject();
	    objectStream.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return file;
    }

    public void SetFil(String fil) {
	this.file = fil;
    }

    public String GetFil() {
	return file;
    }
}
