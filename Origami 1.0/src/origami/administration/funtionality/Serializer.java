package origami.administration.funtionality;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import origami.administration.CustomFile;



public class Serializer {

    public void writeFile(CustomFile seriliazableFile, String fileName)
	    throws IOException {
	
	System.out.println("aaaaa");
	System.out.println("el contenido es "+fileName);
	FileOutputStream fileStream = new FileOutputStream(fileName);

	ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

	objectStream.writeObject(seriliazableFile);
	objectStream.close();
    }

    public CustomFile readFile(String diagramPath) throws IOException,
	    ClassNotFoundException {

	CustomFile fileToRecover = null;
	FileInputStream fileStream = new FileInputStream(diagramPath);
	
	ObjectInputStream objectStream = new ObjectInputStream(fileStream);
	
	fileToRecover = (CustomFile) objectStream.readObject();
	objectStream.close();
	fileStream.close();
	return fileToRecover;
    }

}
