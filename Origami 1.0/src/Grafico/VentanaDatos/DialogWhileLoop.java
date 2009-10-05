package Grafico.VentanaDatos;

import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Eventos.EventoKey;
import Administracion.Funcionalidad.Codigo.*;
import Grafico.Ventana;
import Grafico.Figuras.While;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
/**
 * Crea la ventana para introducir los datos de un While.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class DialogWhileLoop {
	private Shell shell;
	public While whileFigure;
	public Text conditionTextField ;
	public EventoKey key;
	public TabFolder tabbedPaneSelected;
	
	public DialogWhileLoop(TabFolder tabfolder){
		tabbedPaneSelected = tabfolder;
	}
	/**
	 * Crea la ventana junto con sus componentes.
	 * @param display
	 * @param selectedFigure
	 */
	public void showDialog(Display display,While selectedFigure,AdminSeleccion selectionAdmin) {
		key = new EventoKey(selectionAdmin,tabbedPaneSelected);
		shell = new Shell(Ventana.shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.whileFigure = selectedFigure;
		String instructionCode;
		String conditionOfWhile = "";
		
		shell.setSize(250, 150);
		shell.setLocation(300, 200);
		shell.setText("Datos While");
		
		
			
		if ((selectedFigure.instruccion.instruccion.firstElement()
		     .getInstruccionSimple().compareTo("") != 0) 
		       && selectedFigure.instruccion.instruccion.firstElement()
		          .getInstruccionSimple().compareTo("null") != 0) {		
			instructionCode = selectedFigure.instruccion.instruccion
			                  .firstElement().getInstruccionSimple();
			
			for (int charIndex = 0; charIndex < instructionCode.length(); 
			     charIndex++) {
				if((charIndex > 5) && (charIndex < instructionCode.length()-2)){
					conditionOfWhile += instructionCode.charAt(charIndex);
				}
			}
			conditionTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			conditionTextField.setBounds(15, 25, 180, 20);
			conditionTextField.setText(conditionOfWhile);
		} else {
			conditionTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			conditionTextField.setBounds(15, 25, 180, 20);
		}
		
		conditionTextField.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				key.setKey(e);
				if(key.PresentEnter()){
					deleteCode(true);
					shell.close();
				}
			}
		}); 
		
		Label labelInfo = new Label(shell, SWT.NONE);
		labelInfo.setLocation(15,5);
		labelInfo.setSize(250,15);
		labelInfo.setText("Introduce la condicion");
		
		Label labelExampleInfo = new Label(shell, SWT.NONE);
		labelExampleInfo.setLocation(15,55);
		labelExampleInfo.setSize(250,15);
		labelExampleInfo.setText("EJEMPLO:  suma<=condicion");
		
		Button okButton = new Button(shell,SWT.FLAT);
		okButton.setBounds(25,85,75,25);
		okButton.setText("ACEPTAR");
		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { 
				deleteCode(true);
				shell.close();
			}
		});
		
		Button cancelButton = new Button(shell,SWT.FLAT);
		cancelButton.setBounds(135,85,75,25);
		cancelButton.setText("CANCELAR");
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				deleteCode(false);
				shell.close();
			}
		});
		
		shell.open();
		while (!shell.isDisposed()){
			if (!display.readAndDispatch()){
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
			if(conditionTextField.getText() != ""){
				InstruccionSimple codigo = new InstruccionSimple();
				String instruc = "while(" + conditionTextField.getText() + "){";
				codigo.setInstruccionSimple(instruc);
				if(whileFigure.instruccion.instruccion.size()>0){
					if(!whileFigure.instruccion.instruccion.elementAt(0).instruccion.equals(instruc)){
						tabbedPaneSelected.getTabItem().getSave().setSave(false);
						tabbedPaneSelected.getTabItem().getInfo().setInformacion("/M - Se agrego o modifico una instruccion en una figura de tipo \"mientras\"\n");
						cambio=true;
					}
				}
				whileFigure.instruccion.instruccion.add(0, codigo);
				tabbedPaneSelected.getHoja().addFigure();
				tabbedPaneSelected.getHoja().guardarRetroceso();
				if(cambio){
					tabbedPaneSelected.getTabItem().getInfo().setDiagrama(tabbedPaneSelected.getHoja().getDiagrama());
				}
			}
		}
	}
}