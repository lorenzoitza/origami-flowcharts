package origami.graphics.listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.MessageBox;

import origami.administration.Information;
import origami.administration.functionality.CodeCompiler;
import origami.graphics.WindowWidgets;
import origami.graphics.MainWindow;
import origami.graphics.view.SaveFileView;
import origami.graphics.view.SaveType;
import origami.graphics.widgets.CustomMenu;

public class CompileListener implements SelectionListener{
    
    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
	// TODO Auto-generated method stub
    }
    
    public boolean isSave() {
	SaveFileView save = new SaveFileView();
	if(WindowWidgets.tabFolder.getTabItem().getSave().getDir() == "null") {
	    save.setSaveType(SaveType.SAVE);
	    return save.createWindow();
	} else {
	    return save.saveAction();
	}
    }
    
    @Override
    public void widgetSelected(SelectionEvent arg0) {
	
	if (!WindowWidgets.tabFolder.getTabItem().getSave().isSave()) {
		if (isSave()) {
			CodeCompiler code = new CodeCompiler(WindowWidgets.tabFolder);
			if (MainWindow.getComponents().getByStepComponents().getEnEjecucion(MainWindow.getComponents())) {
			    MainWindow.getComponents().getByStepComponents().stopExecution(MainWindow.getComponents());
			}
			code.main(false, true);
			if (code.isError) {
				int aux = MainWindow.getComponents().customConsole.getTextField().getText().length();
				if (aux >= 0) {
				    MainWindow.getComponents().customConsole.getTextField().setText("");
				}
				MainWindow.getComponents().customConsole.getTextField().setText(code.errorTipe);
				WindowWidgets.tabFolder.getTabItem().getInformation().addInformationExecution(Information.COMPIL, "Error:"+code.errorTipe);
				code.deleteMainFiles();
			} else {
			    WindowWidgets.tabFolder.getTabItem().getInformation().addInformationExecution(Information.COMPIL, "successful compilation");
			    WindowWidgets.tabFolder.getTabItem().getInformation().addInformationExecution(Information.TEST, "");
			    MainWindow.getComponents().getByStepComponents().execute(MainWindow.getComponents(), true, code);
			}
			if (!CustomMenu.getConsoleMenuItem().getSelection()) {
			    CustomMenu.getConsoleMenuItem().setSelection(true);
				MainWindow.getComponents().restoreConsole(true);
			}
		}
	} else {
		CodeCompiler code = new CodeCompiler(WindowWidgets.tabFolder);
		if (MainWindow.getComponents().getByStepComponents().getEnEjecucion(MainWindow.getComponents())) {
		    MainWindow.getComponents().getByStepComponents().stopExecution(MainWindow.getComponents());
		}
		code.main(false, true);
		if (code.isError) {
			int aux = MainWindow.getComponents().customConsole.getTextField().getText().length();
			if (aux >= 0) {
			    MainWindow.getComponents().customConsole.getTextField().setText("");
			}
			MainWindow.getComponents().customConsole.getTextField().setText(code.errorTipe);
			WindowWidgets.tabFolder.getTabItem().getInformation().addInformationExecution(Information.COMPIL, "Error:"+code.errorTipe);
			code.deleteMainFiles();
		} else {
		    WindowWidgets.tabFolder.getTabItem().getInformation().addInformationExecution(Information.COMPIL, "successful compilation");
		    WindowWidgets.tabFolder.getTabItem().getInformation().addInformationExecution(Information.TEST, "");
		    MainWindow.getComponents().getByStepComponents().execute(MainWindow.getComponents(), true, code);
		}
		if (!CustomMenu.getConsoleMenuItem().getSelection()) {
		    CustomMenu.getConsoleMenuItem().setSelection(true);
			MainWindow.getComponents().restoreConsole(true);
		}
	}
    }
}
