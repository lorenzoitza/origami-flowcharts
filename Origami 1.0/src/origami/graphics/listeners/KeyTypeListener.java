package origami.graphics.listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Cursor;

import origami.administration.ApplicationState;
import origami.administration.actions.AddFigureLogic;
import origami.administration.actions.ContextualMenuActions;
import origami.administration.funtionality.CodeCompiler;
import origami.administration.funtionality.code.ManagerCodeFormat;
import origami.graphics.DiagramStructure;
import origami.graphics.MainWindow;
import origami.graphics.figures.CircleFigure;
import origami.graphics.help.AboutWindow;
import origami.graphics.help.HelpWindow;
import origami.graphics.view.DiagramCodeView;
import origami.graphics.view.Keys;
import origami.graphics.view.OpenFileView;
import origami.graphics.view.OpenType;
import origami.graphics.view.SaveFileView;
import origami.graphics.view.SaveType;
import origami.graphics.widgets.CustomMenu;
import origami.graphics.widgets.TabItem;
import origami.images.ImageLoader;

public class KeyTypeListener implements KeyListener {

    private int keyPressed, keyPressedSecond = 0;

    public void setKey(org.eclipse.swt.events.KeyEvent eventKey) {
	keyPressed = eventKey.keyCode;
	keyPressedSecond = eventKey.stateMask;
    }

    public void Accion() {

	switch (keyPressed) {

	case Keys.KEYPAD_1:
	    changeCursotTo("cursorEntrada.png");
	    break;
	case Keys.KEYPAD_2:
	    changeCursotTo("cursorProceso.png");
	    break;
	case Keys.KEYPAD_3:
	    changeCursotTo("cursorIf.png");
	    break;
	case Keys.KEYPAD_4:
	    changeCursotTo("cursorWhile.png");
	    break;
	case Keys.KEYPAD_5:
	    changeCursotTo("cursorFor.png");
	    break;
	case Keys.KEYPAD_6:
	    changeCursotTo("cursorSalida.png");
	    break;
	case Keys.Esc:
	    restablishCursor();
	    break;
	case Keys.Supr:
	    deleteFigureSelection();
	    break;
	case Keys.F1:
	    openWindowHelp();
	    break;
	case Keys.F2:
	    openWindowAbout();
	    break;
	case Keys.F3:
	    cleanDiagram();
	    break;
	case Keys.F4:
	    openViewCCode();
	    break;
	case Keys.F5:
	    executeDiagram();
	    break;
	}
	if (keyPressed + keyPressedSecond == Keys.Ctrl + Keys.A) {
	    openDiagram();
	} else if (keyPressed + keyPressedSecond == Keys.Ctrl + Keys.G) {
	    saveDiagram();
	} else if (keyPressed + keyPressedSecond == Keys.Ctrl + Keys.X) {
	    cutSelectionFigure();
	} else if (keyPressed + keyPressedSecond == Keys.Ctrl + Keys.C) {
	    copySelectionFigure();
	} else if (keyPressed + keyPressedSecond == Keys.Ctrl + Keys.V) {
	    pasteFigure();
	} else if (keyPressed + keyPressedSecond == Keys.Ctrl + Keys.N) {
	    openNewTabItem();
	} else if (keyPressed + keyPressedSecond == Keys.Ctrl + Keys.Z) {
	    applyUndo();
	}
    }
    
    private void cleanDiagram() {
	if (!MainWindow.getComponents().getByStepComponents().isStepByStep()) {
	    for (int y =
		    MainWindow.getComponents().tabFolder.getTabItem().getLeaf()
			    .getSizeDiagrama() - 1; y > 0; y--) {
		MainWindow.getComponents().tabFolder.getTabItem().getLeaf()
			.removeFigureIndexOf(y);
	    CircleFigure fin = new CircleFigure();
	    ApplicationState._selectionAdministrator.setSelectedFigure(0);
	    MainWindow.getComponents().tabFolder.getTabItem().getLeaf()
		    .getDiagrama().add(fin);
	    fin.setMessage("  Fin");
	    DiagramStructure.getInstance().resetScrollBar();
	    MainWindow.getComponents().tabFolder.getTabItem().getLeaf()
		    .addFigure();
	    MainWindow.getComponents().tabFolder.getTabItem().getLeaf()
		    .guardarRetroceso();
	    MainWindow.getComponents().tabFolder.getTabItem().getSave()
		    .setSave(false);
	    }
	}
    }

    private void applyUndo() {
	if (!MainWindow.getComponents().getByStepComponents().isStepByStep()) {
	    TabItem item =
		    (TabItem) MainWindow.getComponents().tabFolder
			    .getSeleccion();
	    item.undo();
	    MainWindow.getComponents().tabFolder.getTabItem().getSave()
		    .setSave(false);
	}
    }

    private void openNewTabItem() {
	MainWindow.getComponents().tabFolder.addTabItem();
	MainWindow.getComponents().setEnabledSaveItems(true);
	MainWindow.getComponents().disableAll(true);
    }

    private void pasteFigure() {
	if (ApplicationState._selectionAdministrator.getSelectedFigure() != -1) {
	    if (ApplicationState._diagramAdministrator.diagram.size() > 0) {
		new ContextualMenuActions()
			.Pegar(MainWindow.getComponents().tabFolder
				.getTabItem()
				.getLeaf()
				.getFigureIndexOf(
					ApplicationState._selectionAdministrator
						.getSelectedFigure()));
		MainWindow.getComponents().customToolBar.disableToolBar();
	    }
	}
    }

    private void copySelectionFigure() {
	if (ApplicationState._selectionAdministrator.getSelectedFigure() != -1) {
	    if (MainWindow.getComponents().tabFolder.getTabItem().getLeaf()
		    .getFigureIndexOf(
			    ApplicationState._selectionAdministrator
				    .getSelectedFigure()) instanceof CircleFigure
		    || ApplicationState._selectionAdministrator
			    .getSelectedFigure() == -1) {
	    } else {
		new ContextualMenuActions()
			.Copiar(MainWindow.getComponents().tabFolder
				.getTabItem()
				.getLeaf()
				.getFigureIndexOf(
					ApplicationState._selectionAdministrator
						.getSelectedFigure()));
		MainWindow.getComponents().customToolBar.disableToolBar();
	    }
	}
    }

    private void cutSelectionFigure() {
	if (ApplicationState._selectionAdministrator.getSelectedFigure() != -1) {
	    if (MainWindow.getComponents().tabFolder.getTabItem().getLeaf()
		    .getFigureIndexOf(
			    ApplicationState._selectionAdministrator
				    .getSelectedFigure()) instanceof CircleFigure
		    || ApplicationState._selectionAdministrator
			    .getSelectedFigure() == -1) {
	    } else {
		new ContextualMenuActions()
			.Cortar(MainWindow.getComponents().tabFolder
				.getTabItem()
				.getLeaf()
				.getFigureIndexOf(
					ApplicationState._selectionAdministrator
						.getSelectedFigure()));
		MainWindow.getComponents().customToolBar.disableToolBar();
	    }
	}
    }

    private void saveDiagram() {
	SaveFileView save = new SaveFileView();
	if (MainWindow.getComponents().tabFolder.getTabItem().getSave()
		.getDir() == "null") {
	    save.setSaveType(SaveType.SAVE);
	    save.createWindow();
	} else {
	    save.saveAction();

	}
    }

    private void openDiagram() {
	if (MainWindow.getComponents().getByStepComponents().getExecution()!= null
		&& MainWindow.getComponents().getByStepComponents()
			.getEnEjecucion(MainWindow.getComponents())
		&& MainWindow.getComponents().tabFolder.getSelectedTabItemId() == MainWindow
			.getComponents().getByStepComponents().getExecution().tabItemSelected
			.GetId()) {
	    MainWindow.getComponents().getByStepComponents().stopExecution(
		    MainWindow.getComponents());
	} else if (MainWindow.getComponents().getByStepComponents().getExecution()!= null
		&& MainWindow.getComponents().getByStepComponents()
			.getEnEjecucion(MainWindow.getComponents())
		&& MainWindow.getComponents().tabFolder.getSelectedTabItemId() == MainWindow
			.getComponents().getByStepComponents().getExecution().tabItemSelected
			.GetId()) {
	    MainWindow.getComponents().getByStepComponents().stopExecution(
		    MainWindow.getComponents());
	}

	OpenFileView open = new OpenFileView();
	open.setOpenType(OpenType.OPEN);
	open.createWindow();
    }

    private void executeDiagram() {
	if (!MainWindow.getComponents().getByStepComponents().isStepByStep()) {
	    CodeCompiler codigo =
		    new CodeCompiler(MainWindow.getComponents().tabFolder);
	    if (MainWindow.getComponents().getByStepComponents()
		    .getEnEjecucion(MainWindow.getComponents())) {
		MainWindow.getComponents().getByStepComponents().stopExecution(
			MainWindow.getComponents());
	    }
	    codigo.main(false, true);
	    if (codigo.isError) {
		int aux =
			MainWindow.getComponents().customConsole.getTextField()
				.getText().length();
		if (aux >= 0) {
		    MainWindow.getComponents().customConsole.getTextField()
			    .setText("");
		}
		MainWindow.getComponents().customConsole.getTextField()
			.setText(codigo.errorTipe);
		MainWindow.getComponents().tabFolder.getTabItem()
			.getInformation().addInformation(
				"/Ec - Error en la compilacion:");
		MainWindow.getComponents().tabFolder.getTabItem()
			.getInformation().addInformation(codigo.errorTipe);
		codigo.deleteMainFiles();
	    } else {
		MainWindow.getComponents().getByStepComponents().execute(
			MainWindow.getComponents(), true, codigo);
		MainWindow.getComponents().tabFolder
			.getTabItem()
			.getInformation()
			.addInformation(
				"/C - Se Compilo el diagrama de manera correcta");
	    }
	    if (!CustomMenu.getConsoleMenuItem().getSelection()) {
		CustomMenu.getConsoleMenuItem().setSelection(true);
		MainWindow.getComponents().restoreConsole(true);
	    }
	}
    }

    private void openViewCCode() {
	ManagerCodeFormat manager =
		new ManagerCodeFormat(MainWindow.getComponents().tabFolder
			.getTabItem().getLeaf().getDiagrama());
	manager.formatCodeC();
	DiagramCodeView view =
		new DiagramCodeView(manager.getInstructionsFormat());
	view.createWindow();
	view.show();
    }

    private void changeCursotTo(String figure) {
	if (!MainWindow.getComponents().getByStepComponents().isStepByStep()) {
	    ApplicationState.cursor[0] =
		    new Cursor(MainWindow.display, ImageLoader.getImage(figure)
			    .getImageData(), 0, 0);
	    new AddFigureLogic().changeCursotTo(figure);
	    new AddFigureLogic().disableCursor();
	}

    }

    private void restablishCursor() {
	if (!MainWindow.getComponents().getByStepComponents().isStepByStep()) {
	    Cursor[] cursor = new Cursor[1];
	    origami.administration.ApplicationState.mainFigure = null;
	    Cursor oldCursor = cursor[0];
	    cursor[0] = new Cursor(null, SWT.CURSOR_ARROW);
	    MainWindow.getComponents().tabFolder.getTabItem().getLeaf()
		    .getDibujarDiagrama().setCursor(cursor[0]);
	    if (oldCursor != null) {
		oldCursor.dispose();
	    }
	    MainWindow.getComponents().tabFolder.getTabItem().getLeaf()
		    .addFigure();
	    MainWindow.getComponents().tabFolder.getTabItem().getLeaf()
		    .guardarRetroceso();
	}
    }

    private void openWindowHelp() {
	HelpWindow help = new HelpWindow();
	help.showWindow();
    }

    private void openWindowAbout() {
	AboutWindow acercade = new AboutWindow();
	acercade.createWindow(MainWindow.display);
	acercade.showWindow();
    }

    private void deleteFigureSelection() {
	if (ApplicationState._selectionAdministrator.getSelectedFigure() != -1) {
	    if (MainWindow.getComponents().tabFolder.getTabItem().getLeaf()
		    .getDiagrama().elementAt(
			    ApplicationState._selectionAdministrator
				    .getSelectedFigure()) instanceof CircleFigure) {
	    } else {
		new ContextualMenuActions()
			.Eliminar(MainWindow.getComponents().tabFolder
				.getTabItem()
				.getLeaf()
				.getDiagrama()
				.elementAt(
					ApplicationState._selectionAdministrator
						.getSelectedFigure()));
	    }
	}
    }

    public boolean PresentEnter() {
	if (keyPressed == Keys.Enter || keyPressed == Keys.Intro) {
	    return true;
	}
	return false;
    }

    @Override
    public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
	keyPressed = e.keyCode;
	keyPressedSecond = e.stateMask;
	Accion();
    }

    @Override
    public void keyReleased(org.eclipse.swt.events.KeyEvent arg0) {

    }
}
