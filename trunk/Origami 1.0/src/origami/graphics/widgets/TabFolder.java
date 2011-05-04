package origami.graphics.widgets;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;

import origami.administration.AdminDiagram;
import origami.administration.AdminSelection;
import origami.administration.FigureStructure;
import origami.administration.functionality.DiagramFileManager;
import origami.administration.functionality.SaveDiagramController;
import origami.graphics.DiagramStructure;
import origami.graphics.MainWindow;
import origami.graphics.listeners.KeyTypeListener;

/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class TabFolder {

    private CTabFolder tabFolder;

    private int counter = -1;

    private KeyTypeListener key;

    private AdminSelection adminSelection;

    public TabFolder(Display display, AdminSelection seleccion) {
	this.setAdminSelection(seleccion);
	this.key = new KeyTypeListener();
	this.tabFolder =
		new CTabFolder(MainWindow.shell, SWT.BORDER | SWT.CLOSE);

	initTabFolder();
    }

    private void initTabFolder() {
	this.tabFolder.forceFocus();
	this.tabFolder.pack();
	this.tabFolder.setCursor(new Cursor(null, SWT.CURSOR_ARROW));
	this.tabFolder.setSimple(false);
	this.tabFolder.setTabHeight(24);
	Color title =
		this.tabFolder.getDisplay().getSystemColor(
			SWT.COLOR_TITLE_BACKGROUND);
	Color title2 =
		this.tabFolder.getDisplay().getSystemColor(
			SWT.COLOR_TITLE_FOREGROUND);
	Color title3 =
		this.tabFolder.getDisplay().getSystemColor(
			SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
	this.tabFolder.setSelectionForeground(title);
	this.tabFolder.setSelectionBackground(new Color[] { title2, title3 },
		new int[] { 100 }, true);
	this.tabFolder.addKeyListener(getCTabKeyListener());
	this.tabFolder.addSelectionListener(getSelectionLister());
	this.tabFolder.addCTabFolder2Listener(getCTabFolderListener());
    }

    public void addUndo(Vector<FigureStructure> diagrama) {
	TabItem panel = (TabItem) getSeleccion();
	panel.addUndo(diagrama, getAdminSelection());
    }

    public void applyUndo(Vector<AdminDiagram> diagrama, int pos,
	    int seleccion) {
	DiagramStructure.getInstance().resetScrollBar();
	for (int index = getTabItem().getLeaf().getDiagrama().size() - 1; index > 0; index--) {
	    getTabItem().getLeaf().getDiagrama().removeElementAt(index);
	}
	for (int index = 1; index < diagrama.elementAt(pos).diagram.size(); index++) {
	    getTabItem().getLeaf().getDiagrama().add(
		    diagrama.elementAt(pos).diagram.elementAt(index));
	}
	getAdminSelection().setSelectedFigure(seleccion);
	getTabItem().getLeaf().addFigure();
    }

    public int getIdOfTabItem(TabItem a) {
	TabItem tab;
	int id = 0;
	for (int index = 0; index < tabFolder.getItemCount(); index++) {
	    tab = (TabItem) tabFolder.getItem(index);
	    if (tab.GetId() == a.GetId()) {
		id = index;
		getAdminSelection().setDiagramSelection(tab.GetId());
		getTabItem().getLeaf().enabledCustomLeaf();
		return id;
	    }
	}
	return 0;
    }

    public void addTabItem() {
	counter++;
	int aux = counter + 1;
	TabItem item = new TabItem(tabFolder, SWT.NONE, getAdminSelection());
	item.setTabFolder(this);
	item.setText("Diagrama " + aux);
	item.SetId(counter);
	getAdminSelection().setDiagramSelection(counter);
	item.initLeaf();
	selectionTabItem();
	item.enabledLeaf();
	item.addInformation();
    }

    private KeyListener getCTabKeyListener() {
	return new org.eclipse.swt.events.KeyAdapter() {

	    public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
		key.setKey(e);
		key.Accion();
	    }
	};
    }

    private CTabFolder2Listener getCTabFolderListener() {
	return new CTabFolder2Adapter() {

	    public void close(CTabFolderEvent event) {
		TabItem a = (TabItem) event.item;
		if (MainWindow.getComponents().getByStepComponents().getExecution() != null
			&& MainWindow.getComponents().getByStepComponents()
				.getEnEjecucion(MainWindow.getComponents())
			&& a.GetId() == MainWindow.getComponents()
				.getByStepComponents().getExecution().tabItemSelected.GetId()) {
		    MainWindow.getComponents().getByStepComponents()
			    .stopExecution(MainWindow.getComponents());
		} else if (MainWindow.getComponents().getByStepComponents()
			.getStepByStep() != null
			&& MainWindow.getComponents().getByStepComponents()
				.getEnEjecucion(MainWindow.getComponents())
			&& a.GetId() == MainWindow.getComponents()
				.getByStepComponents().getStepByStep().a.GetId()) {
		    MainWindow.getComponents().getByStepComponents()
			    .stopExecution(MainWindow.getComponents());
		    MainWindow
			    .getComponents()
			    .getByStepComponents()
			    .disableStepByStep(MainWindow.getComponents(), false);
		}
		if (!a.getSave().isSave()) {
		    a.getSave().saveDiagramHandler(event);
		} else {
		    SaveDiagramController.closeCTabFolder(a.GetId());
		}
	    }
	};
    }

    private SelectionListener getSelectionLister() {
	return new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {

		TabItem a =
			(TabItem) tabFolder.getItem(tabFolder
				.getSelectionIndex());
		getAdminSelection().setDiagramSelection(a.GetId());
		a.getLeaf().enabledCustomLeaf();
		DiagramStructure.getInstance().resetScrollBar();
		a.getSave().checkChanges();
	    }
	};
    }
    
    public void changeName(String name) {
	if (tabFolder.getSelectionIndex() != -1) {
	    TabItem item =
		    (TabItem) tabFolder.getItem(tabFolder.getSelectionIndex());
	    item.setText(name);
	} else {
	    counter++;
	    TabItem item = new TabItem(tabFolder, SWT.NONE, getAdminSelection());
	    item.setTabFolder(this);
	    item.SetId(counter);
	    item.setText(name);
	    getAdminSelection().setDiagramSelection(counter);
	    getAdminSelection().setSelectedFigure(0);
	    selectionTabItem();
	}
    }
    
    private void selectionTabItem() {
	tabFolder.setSelection(tabFolder.getItemCount() - 1);
    }

    public void setSelectionTabItem(int indexTabItem) {
	tabFolder.setSelection(indexTabItem);
    }
    
    public TabItem getTabItem() {
	TabItem item =
		(TabItem) tabFolder.getItem(tabFolder.getSelectionIndex());
	return item;
    }

    public String getCurrentTabItemName() {
	TabItem item =
		(TabItem) tabFolder.getItem(tabFolder.getSelectionIndex());
	return item.getText();
    }

    public void openDiagram(String archivo, DiagramFileManager ser) {
	getTabItem().getLeaf().openNewFile(archivo, ser);
	setSelectionTabItem(0);
	getAdminSelection().setSelectedFigure(-1);
    }

    public int getSelectedTabItemId() {
	TabItem a = (TabItem) tabFolder.getItem(tabFolder.getSelectionIndex());
	return a.GetId();
    }

    public int getindexSeleccionTabfolder() {
	return tabFolder.getSelectionIndex();
    }

    public int getItemCount() {
	return tabFolder.getItemCount();
    }

    public CTabItem getSeleccion() {
	return tabFolder.getSelection();
    }

    public CTabFolder getTabFolder() {
	return tabFolder;
    }

    public void setAdminSelection(AdminSelection adminSelection) {
	this.adminSelection = adminSelection;
    }

    public AdminSelection getAdminSelection() {
	return adminSelection;
    }
}
