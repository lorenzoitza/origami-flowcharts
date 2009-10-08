package Grafico.VentanaDatos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Eventos.EventoKey;
import Grafico.Ventana;
import Grafico.Figuras.Proceso;
/**
 * Crea la ventana para introducir los datos de una figura de proceso.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class SentenceFigureDialog {
	private Shell shell;
	
	public Proceso sentenceFigure;   //figura proceso?? wtf 
	
	public Text variableTextField;
	
	public Text dataTextField;
	
	public EventoKey key;
	
	public TabFolder tab;
	
	public SentenceFigureDialog(TabFolder tabfolder){
		tab = tabfolder;
	}
	/**
	 * Crea la ventana junto con sus componentes.
	 * @param display
	 * @param selectedFigure
	 */
	public void ventana(Display display,Proceso selectedFigure,
			AdminSeleccion adminSelection) {
		key = new EventoKey(adminSelection,tab);
		shell = new Shell(Ventana.shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.sentenceFigure = selectedFigure;
		
		shell.setSize(320, 150);
		shell.setLocation(300, 200);
		shell.setText("Datos Proceso");
		
		if(selectedFigure.instruccion.getInstruccionSimple().compareTo("null") != 0 
			&& selectedFigure.instruccion.getInstruccionSimple().compareTo("") != 0){
			
			String[] instructionCode = selectedFigure.instruccion.getInstruccionSimple().split("=");
			String[] dataInstructionCode = instructionCode[1].split(";");
			
			variableTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			variableTextField.setBounds(15, 35, 80, 20);
			variableTextField.setText(instructionCode[0]);
			
			dataTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			dataTextField.setBounds(145, 35, 145, 20);
			dataTextField.setText(dataInstructionCode[0]);
		}
		else{
			variableTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			variableTextField.setBounds(15, 35, 80, 20);
			variableTextField.setText("");
			
			dataTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			dataTextField.setBounds(145, 35, 145, 20);
			dataTextField.setText("");
		}
		variableTextField.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				key.setKey(e);
				if(key.PresentEnter()){
					deleteCode(true);
					shell.close();
				}
			}
		}); 
		dataTextField.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				key.setKey(e);
				if(key.PresentEnter()){
					deleteCode(true);
					shell.close();
				}
			}
		}); 
		
		Label informationLabel = new Label(shell, SWT.NONE);
		informationLabel.setLocation(15,15);
		informationLabel.setSize(250,15);
		informationLabel.setText("Introduce la actividad deseada");
		
		Label equalOperator = new Label(shell, SWT.NONE);
		equalOperator.setBounds(125,35,10,15);
		equalOperator.setText("=");
		
		Label exampleLabel = new Label(shell, SWT.NONE);
		exampleLabel.setBounds(20,60,160,15);
		exampleLabel.setText("EJEMPLO:  x               =        x - 5");
		
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
			if(!display.readAndDispatch())
				display.sleep();
		}
	}
	/**
	 * Este metodo es el encargado de validar la informacion introducida.
	 * @param mostrar
	 */
	public void deleteCode(boolean mostrar){
		boolean cambio = false;
		
		if(mostrar){
			String codigo = null;		
			if(variableTextField.getText() != "" && dataTextField.getText() != ""){
				codigo = variableTextField.getText() 
				       + " = " + dataTextField.getText() + ";";
				
				String codigo2 = variableTextField.getText() + "=" 
				                + dataTextField.getText() + ";";	
				
				if(!sentenceFigure.instruccion.instruccion.equals(codigo2)){
					tab.getTabItem().getSave().setSave(false);
					tab.getTabItem().getInfo().setInformacion("/M - Se agrego " +
							"o modifico una instruccion en una figura de tipo " +
							"\"proceso\"\n");
					cambio=true;
				}
				sentenceFigure.instruccion.setInstruccionSimple(codigo);
				tab.getHoja().addFigure();
				tab.getHoja().guardarRetroceso();
				
				if(cambio){
					tab.getTabItem().getInfo().setDiagrama(tab.getHoja().getDiagrama());
				}
			}
		}
	}
}