package origami.graphics.widgets;



import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import origami.graphics.MainWindow;

public class watch {
    private ArrayList<String> lineas= new ArrayList<String>();
    
    //private static Display display;
    
    private static Shell shell;
    
    private Table table;

	private Button button3;
    
	private int conta=0;
	
    public watch(){	
    	initWindow();
    }
    
    public void initWindow(){
    	//display = new Display();
		shell = new Shell(MainWindow.shell);
		shell.setText("Paso a paso");
		shell.setSize(300, 300);
		center(shell);
		createContents(shell);
		shell.open();
    }
    
    private void createContents(Composite composite) {
	    table = new Table(composite, SWT.NONE);
	    table.setBounds(0,0,180,300);
	    table.setHeaderVisible(true);
	    table.setLinesVisible(true);
	    TableColumn[] column = new TableColumn[2];
	    column[0] = new TableColumn(table, SWT.NONE);
	    column[0].setText("Variable");
	    column[0].setWidth(90);
	    column[1] = new TableColumn(table, SWT.NONE);
	    column[1].setText("Valor");
	    column[1].setWidth(90);
	    
    	button3 = new Button(composite, SWT.PUSH);
    	button3.setText("Continue");
    	button3.setBounds(200, 0, 100,50);
    	button3.addSelectionListener(new SelectionAdapter() {
    		public void widgetSelected(SelectionEvent e) {
    			fillTable();
    			conta = conta + 3;
    		}
    	});
    	
    }
   
    private void fillTable() {
//    	for(int i=conta*3; i<lineas.size(); i=i+2){
    		String l1= lineas.get(conta);
    		String l3= lineas.get(conta+2);
    		new TableItem(table, SWT.NONE).setText(new String[] { l1, l3});
//    	}
    }
		    
    public void insertString(String cadena){
    	int pos1= cadena.indexOf("Old");
		String variable=cadena.substring(0, pos1-1);
		int pos2= cadena.indexOf("New");
		String oldValue= getNewValue(cadena.substring(pos1, pos2-1));
		String newValue= getNewValue(cadena.substring(pos2));
//		ArrayList<String> lineas1= new ArrayList<String>();
		lineas.add(variable);
		lineas.add(oldValue);
		lineas.add(newValue);
//		insert(lineas1);
    }
    
//    public void insert(ArrayList<String> lineasEx){    
//    	if(!lineas.isEmpty()){
//    		for(int j=0; j<lineas.size(); j++){
//    			if(lineasEx.get(0).compareTo(lineas.get(j))!=0){
//    				for(int i=0; i<lineasEx.size(); i++){
//    					lineas.add(lineasEx.get(i));
//                    }
//    				j=lineas.size();
//    			}
//    			else{
//    				lineas.set(j+1, lineasEx.get(1));
//    				lineas.set(j+2, lineasEx.get(2));
//    				j=lineas.size();
//    			}
//		    }
//	    }
//	    else{
//			for(int k=0; k<lineasEx.size(); k++){
//			    lineas.add(lineasEx.get(k));
//			}
//	    }
//    }

    public void main(){
    	watch v=new watch();
    	v.insertString("suma Old value = 3 New value = 5");
    	v.insertString("multiplicacion Old value = 3 New value = 5");
    	v.insertString("suma Old value = 5 New value = 4"); 
    	v.insertString("suma Old value = 4 New value = 6"); 
    	v.insertString("multiplicacion Old value = 6 New value = 9"); 
    	v.insertString("multiplicacion Old value = 9 New value = 1"); 
    	shell.open();
	while (!shell.isDisposed()) {

	    if (!MainWindow.shell.getDisplay().readAndDispatch()) {

		MainWindow.shell.getDisplay().sleep();
	    }
	}
    }
        
    public String getNewValue(String l){
		String newValue="";
		newValue= l.substring(12);
		return newValue;
    }
    
    public void center(Shell shell) {
        Rectangle bds = shell.getDisplay().getBounds();

        Point p = shell.getSize();

        int nLeft = (bds.width - p.x) / 2;
        int nTop = (bds.height - p.y) / 2;

        shell.setBounds(nLeft, nTop, p.x, p.y);
    }
}
