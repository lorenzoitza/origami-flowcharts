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
public class WhileLoopDialog {
	
	private Shell shell;
	
	public While whileFigure;
	
	public Text conditionTextField ;
	
	public EventoKey key;
	
	public TabFolder tabbedPaneSelected;
	
	public WhileLoopDialog(TabFolder tabfolder){
		tabbedPaneSelected = tabfolder;
	}
	
	/**
	 * Crea la ventana junto con sus componentes.
	 * @param display
	 * @param selectedFigure
	 */
	public void showDialog(Display display, While selectedFigure, 
			AdminSeleccion selectionAdmin) {
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
		
		Label informationLabel = new Label(shell, SWT.NONE);
		
		informationLabel.setLocation(15,5);
		informationLabel.setSize(250,15);
		informationLabel.setText("Introduce la condicion");
		
		Label examplelabel = new Label(shell, SWT.NONE);
		
		examplelabel.setLocation(15,55);
		examplelabel.setSize(250,15);
		examplelabel.setText("EJEMPLO:  suma<=condicion");
		
		Button acceptButton = new Button(shell,SWT.FLAT);
		
		acceptButton.setBounds(25,85,75,25);
		acceptButton.setText("ACEPTAR");
		acceptButton.addSelectionListener(new SelectionAdapter() {
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
	 * @param isShowed
	 */
	public void deleteCode(boolean isShowed){
		InstruccionSimple whileInstruction = new InstruccionSimple();
		String instrutionCode = "";
		boolean cambio = false;
		
		if(isShowed){
			if(conditionTextField.getText() != ""){
				instrutionCode = "while(" + conditionTextField.getText() + "){";
				whileInstruction.setInstruccionSimple(instrutionCode);
				
				if(whileFigure.instruccion.instruccion.size()>0){
					if(!whileFigure.instruccion.instruccion.elementAt(0)
							.instruccion.equals(instrutionCode)){
						
						tabbedPaneSelected.getTabItem().getSave().setSave(false);
						tabbedPaneSelected.getTabItem().getInfo()
						  .setInformacion("/M - Se agrego o modifico una " +
						  "instruccion en una figura de tipo " +
						  "\"mientras\"\n");
						cambio = true;
					}
				}
				whileFigure.instruccion.instruccion.add(0, whileInstruction);
				tabbedPaneSelected.getHoja().addFigure();
				tabbedPaneSelected.getHoja().guardarRetroceso();
				if (cambio) {
					tabbedPaneSelected.getTabItem().getInfo()
						.setDiagrama(tabbedPaneSelected.getHoja().getDiagrama());
				}
			}
		}
	}
}