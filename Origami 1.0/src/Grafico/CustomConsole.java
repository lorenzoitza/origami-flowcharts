package Grafico;

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

import Imagenes.ImageLoader;


public class CustomConsole {
    public CTabFolder tabFolder;
    public Text text;
    public CTabItem item;
    private int sizeTab;
    public ToolItem bot;
    public boolean hide = true;
    
    public void agregarConsola(GridData consolaData) {
	tabFolder = new CTabFolder(MainWindow.shell, SWT.BORDER);
	tabFolder.pack();
	tabFolder.setLayoutData(consolaData);
	consolaData.exclude = true;
	tabFolder.setBounds(0, 0, 0, 0);
	tabFolder.setSimple(false);
	tabFolder.setTabHeight(24);
	Color title = MainWindow.display
			.getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
	Color title2 = MainWindow.display
			.getSystemColor(SWT.COLOR_TITLE_FOREGROUND);
	Color title3 = MainWindow.display
			.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
	tabFolder.setSelectionForeground(title);
	tabFolder.setSelectionBackground(new Color[] { title2, title3 },
			new int[] { 100 }, true);
	createTab();
	tabFolder.setMinimizeVisible(true);
	tabFolder.setMaximizeVisible(true);
	tabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {
		public void minimize(CTabFolderEvent event) {
			MainWindow.menu.consoleMenuItem.setSelection(false);
			MainWindow.getComponents().moverConsola(false);
			tabFolder.setMaximized(false);
		}

		public void maximize(CTabFolderEvent event) {
		    MainWindow.getComponents().maxConsola(true);
			tabFolder.forceFocus();
			text.forceFocus();
			tabFolder.setMaximized(true);
		}

		public void restore(CTabFolderEvent event) {
		    MainWindow.getComponents().moverConsola(true);
			tabFolder.forceFocus();
			text.forceFocus();
			tabFolder.setMinimized(false);
			tabFolder.setMaximized(false);
		}
	});
    }
    
    public void createTab() {
	item = new CTabItem(tabFolder, SWT.CLOSE);
	item.setText(" Consola   ");
	text = new Text(tabFolder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
	text.addKeyListener(new KeyListener() {
		public void keyPressed(KeyEvent e) {
			if ((sizeTab >= text.getCaretPosition()) && (e.keyCode == 8)) {
				e.doit = false;
			} else if (sizeTab > text.getCaretPosition()) {
				e.doit = false;
			}
			if (e.keyCode == 13 || e.keyCode == 16777296) {
			    MainWindow.getComponents().setText(MainWindow.getComponents().texto());
			}
		}

		public void keyReleased(KeyEvent arg0) {
		}
	});
	ToolBar toolBarFolder = new ToolBar(tabFolder, SWT.LEFT);
	tabFolder.setTopRight(toolBarFolder);
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
	item.setControl(text);
	item.setImage(ImageLoader.getImage("consola.ico"));
    }
    public void setInformation(int size) {
	this.sizeTab = size;
}
}
