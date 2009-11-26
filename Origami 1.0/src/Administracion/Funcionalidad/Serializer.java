package Administracion.Funcionalidad;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Administracion.Archivo;
import Administracion.TabFolder;


public class Serializer {
    
    
    public void writeFile(TabFolder selectedTab,String fileName) throws IOException
    {
            FileOutputStream fileStream = new FileOutputStream(fileName);
	    
	    ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            //Archivo esta en admin
	    Archivo seriliazableFile = new Archivo(selectedTab.getHoja().getDiagrama());
	    
	    seriliazableFile.setInfo(selectedTab.getTabItem().getInfo().getInfo());
	    objectStream.writeObject(seriliazableFile);
	    selectedTab.getTabItem().getInfo().removeTime();
	    objectStream.close();
    }
    public Archivo recoverFile(String diagramPath) throws IOException, ClassNotFoundException
    {
	    Archivo file = null;
	    FileInputStream fileStream = new FileInputStream(diagramPath);
	    ObjectInputStream objectStream = new ObjectInputStream(fileStream);
	    file = (Archivo) objectStream.readObject();
	    objectStream.close();
	return file;
    }

}
