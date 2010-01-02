package Administracion.Funcionalidad;

import java.io.*;
import Administracion.Funcionalidad.Codigo.Instruction;
import Administracion.Funcionalidad.Codigo.Manager;
import Grafico.MainWindow;
import Grafico.TabFolder;

/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class CodeCompiler {

    private File source;

    public boolean isError = false;

    public String errorTipe;

    public TabFolder selectedTab;

    public CodeCompiler(TabFolder tabfolder) {
	selectedTab = tabfolder;
    }

    public void main(boolean cCode, boolean inExcution) {
	saveCode(cCode, inExcution);
	compileCode(cCode);
    }

    public void deleteMainFiles() {
	try {

	    new File("main.exe").delete();
	} catch (Exception e) {

	} finally {
	    source.delete();
	}
    }

    public boolean createExecuteFile(String fileName) {
	Instruction code = new Instruction();

	String comand = "cmd /c gcc -g -o " + fileName + " " + fileName + ".c";

	code.getInstructionOfDiagram(selectedTab.getTabItem().getLeaf().getDiagrama());
	boolean error = false;

	try {
	    source = new File(fileName + ".c");

	    PrintWriter writer = new PrintWriter(source);
	    writer.write(code.totalCode);
	    writer.close();

	    while (true) {

		if (source.exists()) {

		    Process process = Runtime.getRuntime().exec(comand);

		    InputStream errorStream = process.getErrorStream();

		    InputStreamReader inputStream = new InputStreamReader(errorStream);

		    BufferedReader reader = new BufferedReader(inputStream);

		    while ((reader.readLine()) != null) {

			error = true;

			break;
		    }
		    break;
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	source.delete();
	return error;
    }

    public void saveCode(boolean isCCode, boolean inExecution) {
	Manager managerCode = new Manager(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
	//Instruction codigo = new Instruction();

	if (isCCode) {

	    source = new File("main.c");
	    //codigo.main(selectedTab.getHoja().getDiagrama());
	    managerCode.formatCodeC();
	} else if (!isCCode && inExecution) {

	    source = new File("main.cpp");
	    //codigo.main(selectedTab.getHoja().getDiagrama());
	    managerCode.formatCodeCpp();
	} else if (!isCCode && !inExecution) {

	    source = new File("main.cpp");
	    //codigo.generateGDB(selectedTab.getHoja().getDiagrama());
	    managerCode.formatCodeGDB();
	}
	try {

	    PrintWriter pw = new PrintWriter(source);
	    pw.write(managerCode.getInstructionsFormat());
	    pw.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void compileCode(boolean cCode) {
	String sourcerFile;
	String comand;
	if (cCode) {

	    sourcerFile = "main.c";
	    comand = "cmd /c gcc -g -o main main.c";
	} else {
	    sourcerFile = "main.cpp";
	    comand = "cmd /c g++ -g -o main main.cpp";
	}
	try {
	    source = new File(sourcerFile);

	    while (true) {

		if (source.exists()) {

		    Process process = Runtime.getRuntime().exec(comand);

		    InputStream errorStream = process.getErrorStream();

		    InputStreamReader inputStream =
			    new InputStreamReader(errorStream);

		    BufferedReader reader = new BufferedReader(inputStream);

		    String linea = null;

		    errorTipe = "<ERROR>\n";

		    while ((linea = reader.readLine()) != null) {

			errorTipe += linea + "\n";

			isError = true;
		    }
		    break;
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}