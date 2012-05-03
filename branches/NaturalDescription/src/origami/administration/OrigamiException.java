package origami.administration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
@SuppressWarnings("serial")
public class OrigamiException extends Exception {

    private String message = "";

    private static int numberOfErrors = 0;

    public OrigamiException(Exception e) {
	numberOfErrors++;
	validateFile();
	updateStats();
	getErrorMessage(e);
	saveErrorMessage();
    }

    private void validateFile() {
	File file = new File("configuracion/buglog.txt");
	if (!file.exists()) {
	    numberOfErrors = 1;
	}
    }

    private void updateStats() {
	FileReader file;
	BufferedReader buffered;
	String line = "";
	message = "";
	try {
	    file = new FileReader("configuracion/buglog.txt");
	    buffered = new BufferedReader(file);
	    int count = 0;
	    while ((line = buffered.readLine()) != null) {
		if (count >= 1) {
		    message += line + "\r\n";
		} else {
		    String number = "";
		    for (int index = 15; index < line.length(); index++) {
			if (line.substring(index, index + 1) == " ") {
			    break;
			} else {
			    number += line.substring(index, index + 1);
			}
		    }
		    numberOfErrors = Integer.valueOf(number) + 1;
		}
		count++;
	    }
	    buffered.close();
	    file.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
	FileWriter writer;
	PrintWriter print;
	String stats = "TOTAL ERRORES: " + numberOfErrors + "\r\n";
	message = stats + message;
	try {
	    writer = new FileWriter("configuracion/buglog.txt", false);
	    print = new PrintWriter(writer);
	    print.println(message);
	    print.close();
	    writer.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private void getErrorMessage(Exception e) {
	System.err.println(e.getMessage() + "\n");
	e.printStackTrace();

	StackTraceElement[] trackingElement = e.getStackTrace();
	message =
		"************************************************************"
			+ "************************************************************"
			+ "****";
	message += "\r\nRastreo de pila proveniente de getStackTrace:\r\n";
	message += "Clase\t\t\t\t\t\t\tArchivo\t\t\tLinea\tMetodo\r\n";
	for (int index = 0; index < trackingElement.length; index++) {
	    
	    StackTraceElement elementoActual = trackingElement[index];
	    message += elementoActual.getClassName();
	    for (int x = 0; x < 56 - elementoActual.getClassName().length(); x++) {
		
		message += " ";
	    }
	    message += elementoActual.getFileName();
	    for (int x = 0; x < 24 - elementoActual.getFileName().length(); x++) {
		message += " ";
	    }
	    message += elementoActual.getLineNumber();
	    for (int x = 0; x < 8 - Integer.toString(
		    
		    elementoActual.getLineNumber()).length(); x++) {
		message += " ";
	    }
	    message += elementoActual.getMethodName() + "\r\n";
	}
    }

    private void saveErrorMessage() {
	FileWriter file;
	PrintWriter print;
	try {
	    file = new FileWriter("configuracion/buglog.txt", true);
	    print = new PrintWriter(file);
	    print.println(message);
	    print.close();
	    file.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
