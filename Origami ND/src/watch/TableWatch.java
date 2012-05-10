package watch;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 * 
 * @author Eduardo
 *
 */
public class TableWatch {
	private static Display display;
	private static Shell shell;
	private Table table;
	private String[] variables;
	private int[] valorDeVariables;
	
	/**
	 * Constructor que inicializa las variable de clase y toda la interface de la tabla Watch
	 */
	public TableWatch(){
		display = new Display();
		shell = new Shell(display);
		table = new Table(shell, SWT.BORDER | SWT.MULTI);
		variables = new String[]{"uno","dos","tres","cuatro","cinco","seis"};
		valorDeVariables = new int[]{1,2,3,4,5,6};
		initGUI();
	}
	
	/**
	 * Metodo para inicializar y mostrar la interface grafica
	 */
	public void initGUI(){
		shell.setText("Watch");
		shell.setBounds((display.getClientArea().width/2)-150,(display.getClientArea().height/2)-100,300,200);
		initTable();
	    //Boton continuar
		Button btnContinue = new Button(shell, SWT.PUSH);
    	btnContinue.setText("Continue");
    	btnContinue.setBounds(200, 25, 84,50);
    	btnContinue.addSelectionListener(new SelectionAdapter() {
    		public void widgetSelected(SelectionEvent e) {
    			update();
    		}
    	});
    	//Boton limpiar
		Button btnClean = new Button(shell, SWT.PUSH);
    	btnClean.setText("Limpiar Tabla");
    	btnClean.setBounds(200, 80, 84,50);
    	btnClean.addSelectionListener(new SelectionAdapter() {
    		public void widgetSelected(SelectionEvent e) {
    			limpiarTabla();
    		}
    	});
	    shell.open();
	}
	
	/**
	 * Metodo para inicializar la interface grafica de la tabla
	 */
	private void initTable(){
		//Tabla
	    table.setHeaderVisible(true);
	    table.setLinesVisible(true);
	    table.setBounds(0,0,200,400);
	    //Columnas
	    TableColumn[] column = new TableColumn[2];
	    column[0] = new TableColumn(table, SWT.NONE);
	    column[0].setText("Variable");
	    column[0].setWidth(100);
	    column[1] = new TableColumn(table, SWT.NONE);
	    column[1].setText("Valor");
	    column[1].setWidth(100);
	    //Filas
	    for (int i = 0; i < variables.length; i++) {
	    	TableItem item = new TableItem(table, SWT.NONE);
	    	item.setText(new String[] {"",""});
	    }
	    //Editor de tabla con eventos
	    final TableEditor editor = new TableEditor(table);
	    editor.horizontalAlignment = SWT.LEFT;
	    editor.grabHorizontal = true;
	    table.addListener(SWT.MouseDown, new Listener() {
	    	public void handleEvent(Event event) {
	    		Rectangle clientArea = table.getClientArea();
	    		Point pt = new Point(event.x, event.y);
	    		int index = table.getTopIndex();
	    		while (index < table.getItemCount()) {
	    			boolean visible = false;
	    			final TableItem item = table.getItem(index);
	    			for (int i = 0; i < table.getColumnCount()-1; i++) {
	    				Rectangle rect = item.getBounds(i);
	    				if (rect.contains(pt)) {
	    					final int column = i;
	    					final Text text = new Text(table, SWT.NONE);
	    					Listener textListener = new Listener() {
	    						public void handleEvent(final Event e) {
	    							switch (e.type) {
	    								case SWT.FocusOut:
	    									item.setText(column, text.getText());
	    									text.dispose();
	    								break;
	    								case SWT.Traverse:
	    									switch (e.detail) {
	    										case SWT.TRAVERSE_RETURN:
	    											if(!variableExisteEnLaTabla(text.getText()))
	    												item.setText(column, text.getText());
	    										case SWT.TRAVERSE_ESCAPE:
	    											item.setText(column+1, getValor(item.getText(column)));
	    											text.dispose();
	    											e.doit = false;
	    									}
	    								break;
	    							}
	    						}
	    					};
	    					text.addListener(SWT.FocusOut, textListener);
	    					text.addListener(SWT.Traverse, textListener);
	    					editor.setEditor(text, item, i);
	    					text.setText(item.getText(i));
	    					text.selectAll();
	    					text.setFocus();
	    					return;
	    				}
	    				if (!visible && rect.intersects(clientArea)) {
	    					visible = true;
	    				}
	    			}
	    			if (!visible)
	    				return;
	    			index++;
	    		}
	    	}
	    });
	}
	
	/**
	 * Metodo para limpiar la tabla de variables y sus valores
	 */
	private void limpiarTabla(){
		for(int i = 0; i < variables.length; i++){
			table.getItem(i).setText(0, "");
			table.getItem(i).setText(1, "");
		}
	}
	
	/**
	 * Metodo para verificar si la varable que se desea ingresar existe en la tabla o si ya fue llamada anteriormente
	 * @param variable String variable a verificar
	 * @return boolean true en caso de existir o false en caso de no existir
	 */
	private boolean variableExisteEnLaTabla(String variable){
		for(int i = 0; i < variables.length; i++){
			if(variable.compareTo(table.getItem(i).getText(0)) == 0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Metodo para actualizar el valor de la variable en cada watch
	 */
	private void update(){
		for(int i = 0; i < variables.length; i++){
			for(int j = 0; j < variables.length; j++){
				if(table.getItem(i).getText(0).compareTo(variables[j]) == 0){
					table.getItem(i).setText(1, ""+(++valorDeVariables[j]));
				}
			}
		}
	}
	
	/**
	 * Metodo para obtener el valor de la variable escrita y verificar si la vaariable fue declarada
	 * antes de la ejecucion del Watch
	 * @param variable String variable que se desea ver su valor
	 * @return String con el valor actual de la variable
	 */
	private String getValor(String variable){
		for(int i = 0; i < variables.length; i++){
			if(variable.compareTo(variables[i]) == 0){
				return ""+valorDeVariables[i];
			}
		}
		if(variable.compareTo("") != 0)
			return "Variable no declarada";
		else
			return "";
	}
	
	public static void main(String[] args) {
		new TableWatch();
		
		while(!shell.isDisposed()){
			if(display.readAndDispatch()){
				display.sleep();
			}
		}
		display.dispose();
	}
}
