package Grafico.VentanaDatos;

import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Eventos.EventoKey;
import Administracion.Funcionalidad.Codigo.*;
import Grafico.Ventana;
import Grafico.Figuras.For;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
/**
 * Crea la ventana para introducir los datos de un For.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */

public class ForLoopDialog {
	private Shell shell;
	public For forFigure;
	public Text indexLoopTextField;
	public Text conditionTextField;
	public Text counterTextField;
	public EventoKey key;
	public TabFolder tabbedPaneSelected;
	
	public ForLoopDialog(TabFolder tabfolder){
		tabbedPaneSelected = tabfolder;
	}
	/**
	 * Crea la ventana junto con sus componentes.
	 * @param display
	 * @param selectedFigure
	 */
	public void showDialog(Display display, For selectedFigure, AdminSeleccion selectionAdmin) {
		
		
		key = new EventoKey(selectionAdmin,tabbedPaneSelected);
		shell = new Shell(Ventana.shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		//Image i = new Image(Ventana.display,"imagenes\\ventana.ico");
		//shell.setImage(i);
		shell.setSize(210, 280);
		shell.setLocation(300, 200);
		shell.setText("Datos For");
		this.forFigure = selectedFigure;
		
		String codigo = selectedFigure.instruccion.instruccion.firstElement().getInstruccionSimple();
		codigo = codigo.replaceFirst("for","");
		codigo = codigo.replace("(","");
		codigo = codigo.replace(")","");
		codigo = codigo.replace("{","");
		
		if(codigo.compareTo("null") != 0 && codigo.compareTo("") != 0){
			String[] inst = selectedFigure.instruccion.instruccion.firstElement().getInstruccionSimple().split(";");
			String correcto="";
			for(int x=0;x<inst[0].length();x++){
				if(x>3){
					correcto += inst[0].charAt(x);
				}
			}
			String correcto2="";
			int y=inst[2].length();
			for(int x=0;x<inst[2].length();x++){
				if((y-2 != x) && (y-1!=x)){
					correcto2 += inst[2].charAt(x);
				}
			}
			indexLoopTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			indexLoopTextField.setBounds(15, 25, 170, 20);
			conditionTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			conditionTextField.setBounds(15, 80, 170, 20);
			counterTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			counterTextField.setBounds(15, 130, 170, 20);
			indexLoopTextField.setText(correcto);
			conditionTextField.setText(inst[1]);
			counterTextField.setText(correcto2);
		}
		else{
			indexLoopTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			indexLoopTextField.setBounds(15, 25, 170, 20);
			conditionTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			conditionTextField.setBounds(15, 80, 170, 20);
			counterTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			counterTextField.setBounds(15, 130, 170, 20);
		}
		indexLoopTextField.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				key.setKey(e);
				if(key.PresentEnter()){
					deleteCode(true);
					shell.close();
				}
			}
		}); 
		conditionTextField.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				key.setKey(e);
				if(key.PresentEnter()){
					deleteCode(true);
					shell.close();
				}
			}
		}); 
		
		counterTextField.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				key.setKey(e);
				if(key.PresentEnter()){
					deleteCode(true);
					shell.close();
				}
			}
		}); 
		
		Label label = new Label(shell, SWT.NONE);
		label.setLocation(20,10);
		label.setSize(50,15);
		label.setText("Inicio");
		
		Label label2 = new Label(shell, SWT.NONE);
		label2.setLocation(20,65);
		label2.setSize(50,15);
		label2.setText("Condición");
		
		Label label3 = new Label(shell, SWT.NONE);
		label3.setLocation(20,115);
		label3.setSize(50,15);
		label3.setText("Iteración");
		
		Label label4 = new Label(shell, SWT.NONE);
		label4.setLocation(30,155);
		label4.setSize(100,45);
		label4.setText("EJEMPLO: x=0\n                 x<=5\n                 x++");
		
		Button boton = new Button(shell,SWT.FLAT);
		boton.setBounds(20,215,75,25);
		boton.setText("ACEPTAR");
		boton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { 
				deleteCode(true);
				shell.close();
			}
		});
		Button boton2 = new Button(shell,SWT.FLAT);
		boton2.setBounds(110,215,75,25);
		boton2.setText("CANCELAR");
		boton2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				deleteCode(false);
				shell.close();
			}
		});
		
		shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}
	}
	/**
	 * Este metodo es el encargado de validar la informacion introducida.
	 * @param mostrar
	 */
	public void deleteCode(boolean mostrar){
		boolean cambio=false;
		if(mostrar){
			if(indexLoopTextField.getText() != "" && conditionTextField.getText() != "" && counterTextField.getText() != "" ){
				InstruccionSimple codigo = new InstruccionSimple();
				String instruc = "for("+indexLoopTextField.getText()+";"+conditionTextField.getText()+";"+counterTextField.getText()+"){";
				codigo.setInstruccionSimple(instruc);
				if(forFigure.instruccion.instruccion.size()>0){
					if(!forFigure.instruccion.instruccion.elementAt(0).instruccion.equals(instruc)){
						tabbedPaneSelected.getTabItem().getSave().setSave(false);
						tabbedPaneSelected.getTabItem().getInfo().setInformacion("/M Se agrego o modifico una instruccion en una figura de tipo \"para\"\n");
						cambio=true;
					}
				}
				forFigure.instruccion.instruccion.add(0, codigo);
				tabbedPaneSelected.getHoja().addFigure();
				tabbedPaneSelected.getHoja().guardarRetroceso();
				if(cambio){
					tabbedPaneSelected.getTabItem().getInfo().setDiagrama(tabbedPaneSelected.getHoja().getDiagrama());
				}
			}
		}
	}
}