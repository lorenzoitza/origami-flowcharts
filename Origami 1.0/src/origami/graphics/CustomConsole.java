package origami.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import origami.images.ImageLoader;


public class CustomConsole {
    public CTabFolder cTabFolder;
    
    public Text text;
    
    private CTabItem cTabItem;
    
    private int sizeTab;
    
    public ToolItem bot;
    
    public boolean hide = true;
    
    public CustomConsole(GridData consolaData){
	cTabFolder = new CTabFolder(MainWindow.shell, SWT.BORDER);
	
	addConsoleProperties(consolaData);
    }
    
    private void addConsoleProperties(GridData consolaData) {
	cTabFolder.pack();
	cTabFolder.setLayoutData(consolaData);
	cTabFolder.setBounds(0, 0, 0, 0);
	cTabFolder.setSimple(false);
	cTabFolder.setTabHeight(24);
	cTabFolder.setMinimizeVisible(true);
	cTabFolder.setMaximizeVisible(true);
	
	Color title = MainWindow.display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
	Color title2 = MainWindow.display.getSystemColor(SWT.COLOR_TITLE_FOREGROUND);
	Color title3 = MainWindow.display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
	cTabFolder.setSelectionForeground(title);
	cTabFolder.setSelectionBackground(new Color[] { title2, title3 },
			new int[] { 100 }, true);
	
	addListeners();
	
	addConsoleTab();
    }
    
    private void addListeners() {
	cTabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {
	    public void minimize(CTabFolderEvent event) {
		CustomMenu.getConsoleMenuItem().setSelection(false);
		MainWindow.getComponents().moverConsola(false);
		cTabFolder.setMaximized(false);
	    }
	    public void maximize(CTabFolderEvent event) {
		MainWindow.getComponents().maxConsola(true);
		cTabFolder.forceFocus();
		text.forceFocus();
		cTabFolder.setMaximized(true);
	    }
	    public void restore(CTabFolderEvent event) {
		MainWindow.getComponents().moverConsola(true);
		cTabFolder.forceFocus();
		text.forceFocus();
		cTabFolder.setMinimized(false);
		cTabFolder.setMaximized(false);
	    }
	});
    }
    
    public void addConsoleTab() {
	cTabItem = new CTabItem(cTabFolder, SWT.CLOSE);
	cTabItem.setText(" Consola   ");
	text = new Text(cTabFolder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
	text.addKeyListener(new KeyListener() {
		public void keyPressed(KeyEvent e) {
			if ((sizeTab >= text.getCaretPosition()) && (e.keyCode == 8)) {
				e.doit = false;
			} else if (sizeTab > text.getCaretPosition()) {
				e.doit = false;
			}
			if (e.keyCode == 13 || e.keyCode == 16777296) {
			    MainWindow.getComponents().setText(texto());
			}
		}

		public void keyReleased(KeyEvent arg0) {
		}
	});
	ToolBar toolBarFolder = new ToolBar(cTabFolder, SWT.LEFT);
	cTabFolder.setTopRight(toolBarFolder);
	bot = new ToolItem(toolBarFolder, SWT.PUSH);
	final ToolItem bot2 = new ToolItem(toolBarFolder, SWT.PUSH);
	bot.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			if (MainWindow.getComponents().isPasoAPaso) {
				MainWindow.getComponents().disablePasoAPaso(false);
			}
			MainWindow.getComponents().stopEjecucion();
		}
	});
	bot.setEnabled(false);
	bot.setToolTipText("Terminar Ejecucion");
	bot.setImage(ImageLoader.getImage("Stop.png"));
	bot2.setImage(ImageLoader.getImage("monitor.gif"));
	bot2.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			if (hide) {
				bot2.setImage(ImageLoader.getImage("network.gif"));
				hide = false;
			} else {
				bot2.setImage(ImageLoader.getImage("monitor.gif"));
				hide = true;
			}
		}
	});
	cTabItem.setControl(text);
	cTabItem.setImage(ImageLoader.getImage("consola.ico"));
    }
    
    public String texto() {
	String texto = text.getText();
	String[] linea = text.getText().split("\n");
	texto = linea[linea.length - 1].substring(0, linea[linea.length - 1].length());
	return texto;
    }
    
    public void setInformation(int size) {
	this.sizeTab = size;
    }

    public void createConsole() {
	if (cTabItem.isDisposed()) {
	    addConsoleTab();
	}
	cTabFolder.forceFocus();
	cTabItem.setControl(text);
	text.forceFocus();
    }
}
