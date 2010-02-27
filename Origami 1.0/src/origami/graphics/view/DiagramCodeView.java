package origami.graphics.view;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;

import origami.administration.functionality.DiagramExporter;
import origami.graphics.MainWindow;
import origami.graphics.WindowWidgets;
import origami.graphics.listeners.ViewCodeBasicListener;
import origami.graphics.listeners.ViewCodeCListener;
import origami.graphics.listeners.ViewCodeCppListener;
import origami.graphics.listeners.ViewCodeCsharpListener;
import origami.graphics.listeners.ViewCodeJavaListener;
import origami.graphics.listeners.ViewCodePseudoListener;
import origami.graphics.listeners.ViewCodeRubyListener;
import origami.images.ImageLoader;

public class DiagramCodeView {

    private Shell shell;

    private String code;
    
    private Combo combo;
    
    private Table table ; 
    
    private static int selection;
    

    public DiagramCodeView(String code) {
	this.code = code;
    }
    public DiagramCodeView(){
	
    }
    public void createWindow() {
	
	shell = new Shell(MainWindow.shell);
	shell.setSize(600, 500);
	shell.setLocation(300, 200);
	shell.setText("Codigo Fuente");
	initTable(code);
	initToolBar();
    }

    public void show() {
	shell.open();
	while (!shell.isDisposed()) {

	    if (!MainWindow.shell.getDisplay().readAndDispatch()) {

		MainWindow.shell.getDisplay().sleep();
	    }
	}
	selection = 0;
    }

    
    public void initTable(String code) {
	table = new Table(shell, SWT.BORDER);
	table.setBounds(0,0, 496, 320);

	TableColumn lineColumn = new TableColumn(table, SWT.CENTER);
	lineColumn.setWidth(40);
	lineColumn.setText("Linea");

	TableColumn instructionColumn = new TableColumn(table, SWT.LEFT);
	instructionColumn.setWidth(415);
	instructionColumn.setText("Instruccion");

	addRowsContent(table, code);

	table.setLinesVisible(true);
	table.setHeaderVisible(true);
    }

    public void addRowsContent(Table table, String code) {
	String[] line = code.split("\n");

	for (int lineNumber = 0; lineNumber < line.length; lineNumber++) {

	    if (line[lineNumber] != null) {

		TableItem item = new TableItem(table, SWT.NONE);

		for (int position = 1; position <= 2; position++) {

		    if (position == 1) {

			item.setText(1, line[lineNumber]);
		    } else {
			item.setText(0, Integer.toString(lineNumber + 1));
		    }
		}
	    }
	}
    }

    private SelectionAdapter getExportCCodeSelectionAdapter() {
	return new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setFilterExtensions(new String[] { "*.c" });
		String archivo = dialog.open();
		if (archivo != null) {
		    DiagramExporter expor =
			    new DiagramExporter(WindowWidgets.tabFolder);
		    expor.codeCExport(archivo);
		}
	    }
	};
    }

    private SelectionAdapter getExportCppCodeSelectionAdapter() {
	return new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setFilterExtensions(new String[] { "*.cpp" });
		String archivo = dialog.open();
		if (archivo != null) {
		    DiagramExporter expor =
			    new DiagramExporter(WindowWidgets.tabFolder);
		    expor.codeCppExport(archivo);
		}
	    }
	};
    }

    private SelectionAdapter getExportExeSelectionAdapter() {
	return new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setFilterExtensions(new String[] { "*.exe" });
		String archivo = dialog.open();
		if (archivo != null) {
		    String nombre = dialog.getFileName();
		    nombre = nombre.substring(0, nombre.indexOf("."));

		    DiagramExporter expor =
			    new DiagramExporter(WindowWidgets.tabFolder);

		    expor.executeFileExport(archivo, nombre);
		}
	    }
	};
    }

    private void initExportCButton(ToolBar toolBar) {
	Button exportCButton = new Button(toolBar, SWT.FLAT);
	exportCButton.setImage(ImageLoader.getImage("export.png"));
	exportCButton.setBounds(0, 0, 33, 33);
	exportCButton.setToolTipText("Exportar a C");
	exportCButton.addSelectionListener(getExportCCodeSelectionAdapter());
    }

    private void initExportCppButton(ToolBar toolBar) {
	Button exportCppButton = new Button(toolBar, SWT.FLAT);
	exportCppButton.setImage(ImageLoader.getImage("export.png"));
	exportCppButton.setBounds(35, 0, 33, 33);
	exportCppButton.setText(".cpp");
	exportCppButton.setToolTipText("Exportar a C++");
	exportCppButton
		.addSelectionListener(getExportCppCodeSelectionAdapter());
    }

    private void initExportExeButton(ToolBar toolBar) {
	Button exportExeButton = new Button(toolBar, SWT.FLAT);
	exportExeButton.setText(".exe");
	exportExeButton.setBounds(70, 0, 33, 33);
	exportExeButton.setToolTipText("Exportar a .exe");
	exportExeButton.addSelectionListener(getExportExeSelectionAdapter());
    }

    private void initToolBarButtons(ToolBar toolBar) {
	initExportCButton(toolBar);
	initExportCppButton(toolBar);
	initExportExeButton(toolBar);
	initCombo(toolBar);
    }

    public void initToolBar() {
	ToolBar toolBar = new ToolBar(shell, SWT.DOWN | SWT.FLAT | SWT.BORDER);
	toolBar.setBounds(0, 320, 500, 40);
	initToolBarButtons(toolBar);
    }
    
 
    
    public void initCombo(ToolBar toolBar){
	combo = new Combo(toolBar,SWT.READ_ONLY | SWT.Selection);
	combo.setBounds(100, 0, 150, 100);
	String items[] = {"C","C++","C#","Java","Ruby","Pseudocodigo","Basic"};
	combo.setItems(items);
	if(selection != 0)
	    combo.select(selection);
	else
	    combo.select(0);
	combo.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent e){
		  shell.setVisible(false);
		  selection = combo.getSelectionIndex();
		  mostrarCodigo(e);
		      }
		    });
	}
	
	public void mostrarCodigo(SelectionEvent e){
	    if(combo.getText().equals("C")){
		new ViewCodeCListener().widgetSelected(e);
	}
	    else if(combo.getText().equals("C++")){
		new ViewCodeCppListener().widgetSelected(e);
	    }
	   else if(combo.getText().equals("C#")){
		new ViewCodeCsharpListener().widgetSelected(e);
	   	}
	    else if(combo.getText().equals("Pseudocodigo")){
		new ViewCodePseudoListener().widgetSelected(e);
		    }
	    else if(combo.getText().equals("Java")){
		new ViewCodeJavaListener().widgetSelected(e);
			    }
	    else if(combo.getText().equals("Basic")){
		new ViewCodeBasicListener().widgetSelected(e);
			    }
	    else {
		new ViewCodeRubyListener().widgetSelected(e);		
		    }
    }
}




