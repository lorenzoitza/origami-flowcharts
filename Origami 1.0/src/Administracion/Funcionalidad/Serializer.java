package Administracion.Funcionalidad;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Administracion.Archivo;

public class Serializer {

    public void writeFile(Archivo seriliazableFile, String fileName)
	    throws IOException {

	FileOutputStream fileStream = new FileOutputStream(fileName);

	ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
	// Archivo esta en admin

	objectStream.writeObject(seriliazableFile);

	objectStream.close();
    }

    public Archivo recoverFile(String diagramPath) throws IOException,
	    ClassNotFoundException {

	Archivo file = null;
	FileInputStream fileStream = new FileInputStream(diagramPath);
	ObjectInputStream objectStream = new ObjectInputStream(fileStream);
	file = (Archivo) objectStream.readObject();
	objectStream.close();
	fileStream.close();
	return file;
    }

}
