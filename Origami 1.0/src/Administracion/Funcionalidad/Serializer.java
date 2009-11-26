package Administracion.Funcionalidad;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Administracion.CustomFile;

public class Serializer {

    public void writeFile(CustomFile seriliazableFile, String fileName)
	    throws IOException {

	FileOutputStream fileStream = new FileOutputStream(fileName);

	ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
	// Archivo esta en admin

	objectStream.writeObject(seriliazableFile);

	objectStream.close();
    }

    public CustomFile readFile(String diagramPath) throws IOException,
	    ClassNotFoundException {

	CustomFile file = null;
	FileInputStream fileStream = new FileInputStream(diagramPath);
	ObjectInputStream objectStream = new ObjectInputStream(fileStream);
	file = (CustomFile) objectStream.readObject();
	objectStream.close();
	fileStream.close();
	return file;
    }

}
