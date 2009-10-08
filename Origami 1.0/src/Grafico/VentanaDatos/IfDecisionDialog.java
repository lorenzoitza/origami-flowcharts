package Grafico.VentanaDatos;

import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Eventos.EventoKey;
import Administracion.Funcionalidad.Codigo.*;
import Grafico.Ventana;
import Grafico.Figuras.If;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
/**
 * Crea la ventana para introducir los datos de un If.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class IfDecisionDialog {
	private Shell shell;
	
	public If ifFigure;
	
	public Text conditionTextField ;
	
	public EventoKey key;
	
	public TabFolder tab;
	
	public IfDecisionDialog(TabFolder tabfolder){
		tab = tabfolder;
	}
	/**
	 * Crea la ventana junto con sus componentes.
	 * @param d
	 * @param selectedFigure
	 */
	public void showDialog(Display display,If selectedFigure,
			AdminSeleccion adminSelection) {
		this.ifFigure = selectedFigure;
		key = new EventoKey(adminSelection,tab);	
		shell = new Shell(Ventana.shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		
		shell.setSize(250, 150);
		shell.setLocation(300, 200);
		shell.setText("Datos Decision");
		
		String conditionIfFigure="";
		
		if(selectedFigure.instruccion.instruccion.firstElement()
				.getInstruccionSimple() != "null"){
			
			String instructionCode = selectedFigure.instruccion.
			instruccion.firstElement().getInstruccionSimple();
			
			for(int charIndex=0;charIndex<instructionCode.length();charIndex++){
				if(charIndex>=3 && charIndex<instructionCode.length()-2){
					conditionIfFigure += instructionCode.charAt(charIndex);
				}
			}
			
			conditionTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			conditionTextField.setBounds(15, 25, 180, 20);
			conditionTextField.setText(conditionIfFigure);
			
		}
		else{
			
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
		
		Label exampleLabel = new Label(shell, SWT.NONE);
		exampleLabel.setLocation(15,55);
		exampleLabel.setSize(250,15);
		exampleLabel.setText("EJEMPLO:  suma<=condicion");
		
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
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}
	}
	/**
	 * Falta revisar este metodo  mientras lo dejo como deleteCode().
	 * Este metodo es el encargado de validar la informacion introducida.
	 * @param mostrar
	 */	
	public void deleteCode(boolean mostrar){
		boolean cambio=false;
		InstruccionSimple codigo = new InstruccionSimple();
		
		if(mostrar){
			if(conditionTextField.getText() != ""){	
				String instructionCode = "if(" + conditionTextField.getText() + "){";
				codigo.setInstruccionSimple(instructionCode);
				
				if(ifFigure.instruccion.instruccion.size()>0){
					if(!ifFigure.instruccion.instruccion.elementAt(0)
							.instruccion.equals(instructionCode)){	
						tab.getTabItem().getSave().setSave(false);
						tab.getTabItem().getInfo().setInformacion("/M - Se agrego"+
					" o modifico una instruccion en una figura de tipo \"si\"\n");
						cambio=true;
						
					}
				}
				ifFigure.instruccion.instruccion.add(0, codigo);
				tab.getHoja().addFigure();
				tab.getHoja().guardarRetroceso();
				
				if(cambio){
				          tab.getTabItem().getInfo().setDiagrama(tab.getHoja()
				        		  .getDiagrama());
				}
			}
		}
	}
}