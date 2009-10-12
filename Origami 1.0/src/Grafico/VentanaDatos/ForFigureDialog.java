package Grafico.VentanaDatos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.Figuras.For;

public class ForFigureDialog extends AbstractDialog<For>{
	
	private Text indexExpressionTextField;
	
	private Text conditionExpressionTextField;
	
	private Text counterExpressionTextField;
	

	public ForFigureDialog(Shell shell, TabFolder tabFolder, For figura,
			AdminSeleccion selectionAdmin) {
	    
	    super(shell, tabFolder, figura, selectionAdmin);	
	}

	@Override
	public void close() {
	    dialog.close();
	}

	@Override
	protected void create() {
	    dialog.setSize(210, 280);
	    dialog.setLocation(300, 200);
	    dialog.setText("Datos For");
	}

	@Override
	protected void initComponents() {
	    String instructionCode;
	    String indexExpression = "";
	    
	    String counterExpression = "";
		
	    instructionCode = abstractFigure.instruccion.instruccion.firstElement()
	    			.getInstruccionSimple();
	    instructionCode = instructionCode.replaceFirst("for","");
	    instructionCode = instructionCode.replace("(","");
	    instructionCode = instructionCode.replace(")","");
	    instructionCode = instructionCode.replace("{","");
		
	    if ((instructionCode.compareTo("null") != 0) && 
		    (instructionCode.compareTo("") != 0)){
		String[] forExpressions = abstractFigure.instruccion.instruccion
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
		 
		 if(((couterExpressionLength - 2) != charPositionOfCounterExpression) && 
		     ((couterExpressionLength - 1) != charPositionOfCounterExpression)){
		     
		     counterExpression += forExpressions[2]
		                       .charAt(charPositionOfCounterExpression);
		 }
	     }
	     indexExpressionTextField = new Text(dialog,SWT.SINGLE | SWT.BORDER);
	     indexExpressionTextField.setBounds(15, 25, 170, 20);
	     indexExpressionTextField.setText(indexExpression);
			
	     conditionExpressionTextField = new Text(dialog,SWT.SINGLE | SWT.BORDER);
	     conditionExpressionTextField.setBounds(15, 80, 170, 20);
	     conditionExpressionTextField.setText(forExpressions[1]);
			
	     counterExpressionTextField = new Text(dialog,SWT.SINGLE | SWT.BORDER);
	     counterExpressionTextField.setBounds(15, 130, 170, 20);
	     counterExpressionTextField.setText(counterExpression);
	    } else { 
	     indexExpressionTextField = new Text(dialog,SWT.SINGLE | SWT.BORDER);
	     indexExpressionTextField.setBounds(15, 25, 170, 20);
			
	     conditionExpressionTextField = new Text(dialog,SWT.SINGLE | SWT.BORDER);
	     conditionExpressionTextField.setBounds(15, 80, 170, 20);
			
	     counterExpressionTextField = new Text(dialog,SWT.SINGLE | SWT.BORDER);
	     counterExpressionTextField.setBounds(15, 130, 170, 20);
	    }
	    addKeyListener(indexExpressionTextField);
	    addKeyListener(conditionExpressionTextField);
	    addKeyListener(counterExpressionTextField);
		
	    Label indexExpressionLabel = new Label(dialog, SWT.NONE);	
	    indexExpressionLabel.setLocation(20,10);
	    indexExpressionLabel.setSize(50,15);
	    indexExpressionLabel.setText("Inicio");
		
	    Label conditionExpressionLabel = new Label(dialog, SWT.NONE);
	    conditionExpressionLabel.setLocation(20,65);
	    conditionExpressionLabel.setSize(50,15);
	    conditionExpressionLabel.setText("Condición");
		
	    Label couterExpressionLabel = new Label(dialog, SWT.NONE);
	    couterExpressionLabel.setLocation(20,115);
	    couterExpressionLabel.setSize(50,15);
	    couterExpressionLabel.setText("Iteración");
		
	    Label exampleForLabel = new Label(dialog, SWT.NONE);
	    exampleForLabel.setLocation(30,155);
	    exampleForLabel.setSize(100,45);
	    exampleForLabel.setText("EJEMPLO: x=0\n                 " +
		    			"x<=5\n                 x++");
		
	    Button okButton = new Button(dialog,SWT.FLAT);
	    okButton.setBounds(20,215,75,25);
	    okButton.setText("ACEPTAR");
	    addSelectionListener(okButton,true);
		
	    Button cancelButton = new Button(dialog,SWT.FLAT);
	    cancelButton.setBounds(110,215,75,25);
	    cancelButton.setText("CANCELAR");
	    addSelectionListener(cancelButton,false);
	}

	@Override
	public void open() {
	    dialog.open();
	    while(!dialog.isDisposed()){
	        if(!display.readAndDispatch()){
		    display.sleep();
		}
	    }	
	}

	@Override
	protected void validate(boolean band) {
	    InstruccionSimple forInstrution = new InstruccionSimple();
		
	    String instructionCode = "";
		
	    boolean cambio = false;
	    if (band) {
		if ((indexExpressionTextField.getText() != "") && 
			(conditionExpressionTextField.getText() != "") && 
			(counterExpressionTextField.getText() != "")) {
		    
		    instructionCode += "for("+indexExpressionTextField.getText();
		    instructionCode += ";" + conditionExpressionTextField.getText();
		    instructionCode += ";" + counterExpressionTextField.getText();
		    instructionCode += "){";
		    forInstrution.setInstruccionSimple(instructionCode);	
		    if (abstractFigure.instruccion.instruccion.size() > 0) {
			
			if (!abstractFigure.instruccion.instruccion.elementAt(0)
				.instruccion.equals(instructionCode)) {
						
			    tabbedPaneSelected.getTabItem().getSave().setSave(false);
			    tabbedPaneSelected.getTabItem().getInfo()
			    .setInformacion("/M Se agrego o modifico una instruccion"
				    +"en una figura de tipo \"para\"\n");	
			    cambio = true;
			}
		    }
		    abstractFigure.instruccion.instruccion.add(0, forInstrution);
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
