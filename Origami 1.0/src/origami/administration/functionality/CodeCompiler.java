package origami.administration.functionality;

import java.io.*;

import origami.administration.functionality.code.Instruction;
import origami.administration.functionality.code.ManagerCodeFormat;
import origami.graphics.WindowWidgets;
import origami.graphics.widgets.TabFolder;


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
	    e.printStackTrace();
	} finally {
	    source.delete();
	}
    }

    public boolean canCreateExecuteFile(String fileName) {
	CommandFile commandFile = new CommandFile();
	
//	commandFile.setComand("cmd /c gcc -g -o " + fileName + " " + fileName + ".c");
	commandFile.setComand("MinGW1.1/bin/gcc.exe -g -o " + fileName + " " + fileName + ".c");
	
	commandFile.setSourcerFile(fileName+ ".c");
	
	Instruction code = new Instruction();

	code.getInstructionOfDiagram(selectedTab.getTabItem().getLeaf().getDiagrama());
	
	boolean error = false;

	try {
	    
	    source = new File(commandFile.getSourcerFile() );

	    PrintWriter writer = new PrintWriter(source);
	    writer.write(code.totalCode);
	    writer.close();

	    while (true) {

		if (source.exists()) {

		    Process process = Runtime.getRuntime().exec(commandFile.getComand());

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
	ManagerCodeFormat managerCode = new ManagerCodeFormat(WindowWidgets.tabFolder.getTabItem().getLeaf().getDiagrama());
	
	if (isCCode) {

	    source = new File("main.c");
	    managerCode.formatCodeC();
	} else if (!isCCode && inExecution) {

	    source = new File("main.cpp");
	    managerCode.formatCodeCpp();
	} else if (!isCCode && !inExecution) {

	    source = new File("main.cpp");
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
    private CommandFile initMainTypeCommandFile(boolean isCCode){
	CommandFile commandFile = new CommandFile();
	if (isCCode) {
	    commandFile.setSourcerFile("main.c");
	    commandFile.setComand(CommandFile.COMMAND_MAIN_C);
	} else {
	    commandFile.setSourcerFile("main.cpp");
	    commandFile.setComand(CommandFile.COMMAND_MAIN_CPP);
	}
	
	return commandFile;
    }

    public void compileCode(boolean isCCode) {
	
	CommandFile commandFile = initMainTypeCommandFile(isCCode);
	
	try {
	    source = new File(commandFile.getSourcerFile());
	    while (true) {

		if (source.exists()) {
		    System.out.println("exist code ");
		    
		    Process process = Runtime.getRuntime().exec(commandFile.getComand());

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