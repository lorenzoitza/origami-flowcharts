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
	
	public Text indexExpressionTextField;
	
	public Text conditionExpressionTextField;
	
	public Text counterExpressionTextField;
	
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
	public void showDialog(Display display, For selectedFigure, 
			               AdminSeleccion selectionAdmin) {
		
		key = new EventoKey(selectionAdmin,tabbedPaneSelected);
		shell = new Shell(Ventana.shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		//Image i = new Image(Ventana.display,"imagenes\\ventana.ico");
		//shell.setImage(i);
		shell.setSize(210, 280);
		shell.setLocation(300, 200);
		shell.setText("Datos For");
		
		this.forFigure = selectedFigure;
		String instructionCode;
		String indexExpression = "";
		String counterExpression = "";
		
		instructionCode = selectedFigure.instruccion.instruccion.firstElement()
		         		  .getInstruccionSimple();
		instructionCode = instructionCode.replaceFirst("for","");
		instructionCode = instructionCode.replace("(","");
		instructionCode = instructionCode.replace(")","");
		instructionCode = instructionCode.replace("{","");
		
		if ((instructionCode.compareTo("null") != 0) && 
		(instructionCode.compareTo("") != 0)){
			String[] forExpressions = selectedFigure.instruccion.instruccion
			                .firstElement().getInstruccionSimple().split(";");
			for (int charPositionOfIndexExpression = 0; 
					charPositionOfIndexExpression < forExpressions[0].length(); 
					charPositionOfIndexExpression++){
				
				if (charPositionOfIndexExpression > 3){
					indexExpression += forExpressions[0]
					                   .charAt(charPositionOfIndexExpression);
				}
			}
			
			int couterExpressionLength = forExpressions[2].length();
			for (int charPositionOfCounterExpression = 0; 
					charPositionOfCounterExpression < forExpressions[2].length(); 
					charPositionOfCounterExpression++){
				
				if(((couterExpressionLength - 2) != charPositionOfCounterExpression) 
					&& ((couterExpressionLength - 1) != charPositionOfCounterExpression)){
					counterExpression += forExpressions[2]
					                   .charAt(charPositionOfCounterExpression);
				}
			}
			
			indexExpressionTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			indexExpressionTextField.setBounds(15, 25, 170, 20);
			indexExpressionTextField.setText(indexExpression);
			
			conditionExpressionTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			conditionExpressionTextField.setBounds(15, 80, 170, 20);
			conditionExpressionTextField.setText(forExpressions[1]);
			
			counterExpressionTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			counterExpressionTextField.setBounds(15, 130, 170, 20);
			counterExpressionTextField.setText(counterExpression);
		} else { 
			indexExpressionTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			indexExpressionTextField.setBounds(15, 25, 170, 20);
			
			conditionExpressionTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			conditionExpressionTextField.setBounds(15, 80, 170, 20);
			
			counterExpressionTextField = new Text(shell,SWT.SINGLE | SWT.BORDER);
			counterExpressionTextField.setBounds(15, 130, 170, 20);
		}
		
		indexExpressionTextField.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				key.setKey(e);
				if (key.PresentEnter()){
					deleteCode(true);
					shell.close();
				}
			}
		}); 
		
		conditionExpressionTextField.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				key.setKey(e);
				if(key.PresentEnter()){
					deleteCode(true);
					shell.close();
				}
			}
		}); 
		
		counterExpressionTextField.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				key.setKey(e);
				if(key.PresentEnter()){
					deleteCode(true);
					shell.close();
				}
			}
		}); 
		
		Label indexExpressionLabel = new Label(shell, SWT.NONE);
		
		indexExpressionLabel.setLocation(20,10);
		indexExpressionLabel.setSize(50,15);
		indexExpressionLabel.setText("Inicio");
		
		Label conditionExpressionLabel = new Label(shell, SWT.NONE);
		
		conditionExpressionLabel.setLocation(20,65);
		conditionExpressionLabel.setSize(50,15);
		conditionExpressionLabel.setText("Condición");
		
		Label couterExpressionLabel = new Label(shell, SWT.NONE);
		
		couterExpressionLabel.setLocation(20,115);
		couterExpressionLabel.setSize(50,15);
		couterExpressionLabel.setText("Iteración");
		
		Label exampleForLabel = new Label(shell, SWT.NONE);
		
		exampleForLabel.setLocation(30,155);
		exampleForLabel.setSize(100,45);
		exampleForLabel.setText("EJEMPLO: x=0\n                 " +
				                "x<=5\n                 x++");
		
		Button okButton = new Button(shell,SWT.FLAT);
		
		okButton.setBounds(20,215,75,25);
		okButton.setText("ACEPTAR");
		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { 
				deleteCode(true);
				shell.close();
			}
		});
		
		Button cancelButton = new Button(shell,SWT.FLAT);
		
		cancelButton.setBounds(110,215,75,25);
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
	 * Este metodo es el encargado de validar la informacion introducida.
	 * @param isShowed
	 */
	public void deleteCode(boolean isShowed){
		InstruccionSimple forInstrution = new InstruccionSimple();
		String instructionCode = "";
		boolean cambio = false;
		
		if (isShowed) {
			if ((indexExpressionTextField.getText() != "") && 
				(conditionExpressionTextField.getText() != "") && 
				(counterExpressionTextField.getText() != "")) {
				
				instructionCode += "for("+indexExpressionTextField.getText();
				instructionCode += ";" + conditionExpressionTextField.getText();
				instructionCode += ";" + counterExpressionTextField.getText();
				instructionCode += "){";
				
				forInstrution.setInstruccionSimple(instructionCode);
				
				if (forFigure.instruccion.instruccion.size() > 0) {
					
					if (!forFigure.instruccion.instruccion.elementAt(0)
							.instruccion.equals(instructionCode)) {
						
						tabbedPaneSelected.getTabItem().getSave().setSave(false);
						tabbedPaneSelected.getTabItem().getInfo()
						.setInformacion("/M Se agrego o modifico una instruccion" 
								+"en una figura de tipo \"para\"\n");
						
						cambio = true;
					}
				}
				forFigure.instruccion.instruccion.add(0, forInstrution);
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