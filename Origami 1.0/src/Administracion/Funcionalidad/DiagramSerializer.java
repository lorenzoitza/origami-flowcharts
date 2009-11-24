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
public class DiagramSerializer {

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
	    selectedTab.getTabItem().getInfo().addTime();

	    FileOutputStream fileStream = new FileOutputStream(fileName);
	    
	    ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            //Archivo esta en admin
	    Archivo seriliazableFile = new Archivo(selectedTab.getHoja().getDiagrama());
	    
	    seriliazableFile.setInfo(selectedTab.getTabItem().getInfo().getInfo());
	    objectStream.writeObject(seriliazableFile); 
	    
	    selectedTab.getTabItem().getInfo().removeTime();
	    objectStream.close();
	    
	} catch (Exception e) {
            errorMessage();
	    e.printStackTrace();
	    return false;
	}
	return true;
    }
    

    
    
    private void errorMessage()
    {
	    MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_ERROR | SWT.OK);
	    messageBox.setText("Origami");
	    messageBox.setMessage("El nombre de archivo, directorio o etiqueta del" +
		    		" volum�n no es v�lido");
    
    }

    /**
     * Este metodo es el encargado de abrir el diagrama guardado.
     * 
     * @param diagramPath
     * @return
     */
    public Archivo openDiagram(String diagramPath) {
	Archivo file = null;
	try {
	    FileInputStream fileStream = new FileInputStream(diagramPath);
	    ObjectInputStream objectStream = new ObjectInputStream(fileStream);
	    file = (Archivo) objectStream.readObject();
	    objectStream.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return file;
    }

    public void setFile(String fileName) {
	this.fileName = fileName;
    }

    public String getFile() {
	return fileName;
    }
}
