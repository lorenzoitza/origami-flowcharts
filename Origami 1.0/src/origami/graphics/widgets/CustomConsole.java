package origami.graphics.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import origami.graphics.MainWindow;
import origami.images.ImageLoader;

public class CustomConsole {

    private CTabFolder cTabFolder;

    private Text textField;

    private CTabItem cTabItem;

    private int maxCaretPosition;

    private ToolItem stopExecutionButton;

    private ToolItem blockConsoleButton;

    private boolean hide = true;

    public CustomConsole(GridData consolaData) {

	setCTabFolder(new CTabFolder(MainWindow.shell, SWT.BORDER));

	addConsoleProperties(consolaData);
    }

    private void addConsoleProperties(GridData consolaData) {
	getCtabFolder().pack();
	getCtabFolder().setLayoutData(consolaData);
	int x = 0;
	int y = 0;
	int width = 0;
	int height = 0;
	getCtabFolder().setBounds(x, y, width, height);
	getCtabFolder().setSimple(false);
	getCtabFolder().setTabHeight(24);
	getCtabFolder().setMinimizeVisible(true);
	getCtabFolder().setMaximizeVisible(true);

	Color background =
		MainWindow.display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
	Color foreground =
		MainWindow.display.getSystemColor(SWT.COLOR_TITLE_FOREGROUND);
	Color gradient =
		MainWindow.display
			.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);

	getCtabFolder().setSelectionForeground(background);
	getCtabFolder().setSelectionBackground(
		new Color[] { foreground, gradient }, new int[] { 100 }, true);

	addListeners();

	addConsoleTab();
    }

    private void addListeners() {
	getCtabFolder().addCTabFolder2Listener(new CTabFolder2Adapter() {

	    public void minimize(CTabFolderEvent event) {
		CustomMenu.getConsoleMenuItem().setSelection(false);
		MainWindow.getComponents().restoreConsole(false);
		getCtabFolder().setMaximized(false);
	    }

	    public void maximize(CTabFolderEvent event) {
		MainWindow.getComponents().maximizeConsole(true);
		getCtabFolder().forceFocus();
		getTextField().forceFocus();
		getCtabFolder().setMaximized(true);
	    }

	    public void restore(CTabFolderEvent event) {
		MainWindow.getComponents().restoreConsole(true);
		getCtabFolder().forceFocus();
		getTextField().forceFocus();
		getCtabFolder().setMinimized(false);
		getCtabFolder().setMaximized(false);
	    }
	});
    }

    public void addConsoleTab() {
	setCTabItem(new CTabItem(getCtabFolder(), SWT.CLOSE));
	getCTabItem().setText(" Consola   ");
	setTextField(new Text(getCtabFolder(), SWT.MULTI | SWT.V_SCROLL
		| SWT.H_SCROLL));
	getTextField().addKeyListener(getKeyListener());
	ToolBar toolBarFolder = new ToolBar(getCtabFolder(), SWT.LEFT);
	getCtabFolder().setTopRight(toolBarFolder);
	setStopExecutionButton(new ToolItem(toolBarFolder, SWT.PUSH));
	setBlockConsoleButton(new ToolItem(toolBarFolder, SWT.PUSH));
	getStopExecutionButton().addSelectionListener(
		getStopExecutionListener());
	getStopExecutionButton().setEnabled(false);
	getStopExecutionButton().setToolTipText("Terminar Ejecucion");
	getStopExecutionButton().setImage(ImageLoader.getImage("Stop.png"));
	getBlockConsoleButton().setImage(ImageLoader.getImage("monitor.gif"));
	getBlockConsoleButton().addSelectionListener(getBlockConsoleListener());
	getCTabItem().setControl(getTextField());
	getCTabItem().setImage(ImageLoader.getImage("consola.ico"));
    }

    private String getTextContent() {
	String texto = getTextField().getText();
	String[] linea = getTextField().getText().split("\n");
	texto =
		linea[linea.length - 1].substring(0, linea[linea.length - 1]
			.length());
	return texto;
    }

    public void createConsole() {
	if (getCTabItem().isDisposed()) {
	    addConsoleTab();
	}
	getCtabFolder().forceFocus();
	getCTabItem().setControl(getTextField());
	getTextField().forceFocus();
    }

    private SelectionListener getBlockConsoleListener() {
	return new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		if (isHide()) {
		    getBlockConsoleButton().setImage(
			    ImageLoader.getImage("network.gif"));
		    setHide(false);
		} else {
		    getBlockConsoleButton().setImage(
			    ImageLoader.getImage("monitor.gif"));
		    setHide(true);
		}
	    }
	};
    }

    private SelectionListener getStopExecutionListener() {
	return new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		if (MainWindow.getComponents().getByStepComponents()
			.isPasoAPaso()) {
		    MainWindow
			    .getComponents()
			    .getByStepComponents()
			    .disablePasoAPaso(MainWindow.getComponents(), false);
		}
		MainWindow.getComponents().getByStepComponents().stopEjecucion(
			MainWindow.getComponents());
	    }
	};
    }

    private KeyListener getKeyListener() {
	return new KeyListener() {

	    public void keyPressed(KeyEvent e) {
		if ((maxCaretPosition >= getTextField().getCaretPosition())
			&& (e.keyCode == 8)) {
		    e.doit = false;
		} else if (maxCaretPosition > getTextField().getCaretPosition()) {
		    e.doit = false;
		}
		int enterKey=13;
		int introKey=16777296;
		if (e.keyCode == enterKey || e.keyCode == introKey) {
		    MainWindow.getComponents().getByStepComponents().setText(
			    MainWindow.getComponents(), getTextContent());
		}
	    }

	    public void keyReleased(KeyEvent arg0) {
	    }
	};
    }

    public void setMaxCaretPosition(int size) {
	this.maxCaretPosition = size;
    }

    public void setCTabFolder(CTabFolder cTabFolder) {
	this.cTabFolder = cTabFolder;
    }

    public CTabFolder getCtabFolder() {
	return cTabFolder;
    }

    public void setTextField(Text text) {
	this.textField = text;
    }

    public Text getTextField() {
	return textField;
    }

    public void setCTabItem(CTabItem cTabItem) {
	this.cTabItem = cTabItem;
    }

    public CTabItem getCTabItem() {
	return cTabItem;
    }

    public void setStopExecutionButton(ToolItem bot) {
	this.stopExecutionButton = bot;
    }

    public ToolItem getStopExecutionButton() {
	return stopExecutionButton;
    }

    public void setHide(boolean hide) {
	this.hide = hide;
    }

    public boolean isHide() {
	return hide;
    }

    public void setBlockConsoleButton(ToolItem blockConsoleButton) {
	this.blockConsoleButton = blockConsoleButton;
    }

    public ToolItem getBlockConsoleButton() {
	return blockConsoleButton;
    }
}
