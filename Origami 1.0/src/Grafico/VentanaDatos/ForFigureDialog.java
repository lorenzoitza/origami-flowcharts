package Grafico.VentanaDatos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import Administracion.actions.ValidateDialog;
import Grafico.Figuras.ForFigure;

public class ForFigureDialog extends AbstractDialog<ForFigure> {
    
    private Label indexExpressionLabel;

    private Label conditionExpressionLabel;
    
    private Label couterExpressionLabel;
    
    private Text indexExpressionTextField;

    private Text conditionExpressionTextField;

    private Text counterExpressionTextField;

    public ForFigureDialog(Shell shell, ForFigure figura) {

	super(shell, figura);
    }
    
    @Override
    protected void initButtons() {
	acceptButton = new Button(dialog, SWT.FLAT);
	acceptButton.setBounds(20,215,75,25);
	acceptButton.setText("ACEPTAR");
	addSelectionListener(acceptButton, true);

	cancelButton = new Button(dialog, SWT.FLAT);
	cancelButton.setBounds(110,215,75,25);
	cancelButton.setText("CANCELAR");
	addSelectionListener(cancelButton, false);
    }

    @Override
    protected void create() {
	dialog.setSize(210, 280);
	dialog.setLocation(300, 200);
	dialog.setText("Datos For");
    }

    @Override
    protected void validate(boolean band) {
	String instructionCode = "";

	if (band) {
	    if ((indexExpressionTextField.getText() != "")
		    && (conditionExpressionTextField.getText() != "")
		    && (counterExpressionTextField.getText() != "")) {

		instructionCode += "for(" + indexExpressionTextField.getText();
		instructionCode += ";" + conditionExpressionTextField.getText();
		instructionCode += ";" + counterExpressionTextField.getText();
		instructionCode += "){";
		
		new ValidateDialog().validate(instructionCode, abstractFigure,"para");
	    }
	}
    }

    @Override
    public void initLabels() {
	indexExpressionLabel = new Label(dialog, SWT.NONE);
	indexExpressionLabel.setLocation(20, 10);
	indexExpressionLabel.setSize(50, 15);
	indexExpressionLabel.setText("Inicio");

	conditionExpressionLabel = new Label(dialog, SWT.NONE);
	conditionExpressionLabel.setLocation(20, 65);
	conditionExpressionLabel.setSize(50, 15);
	conditionExpressionLabel.setText("Condición");

	couterExpressionLabel = new Label(dialog, SWT.NONE);
	couterExpressionLabel.setLocation(20, 115);
	couterExpressionLabel.setSize(50, 15);
	couterExpressionLabel.setText("Iteración");

	exampleLabel = new Label(dialog, SWT.NONE);
	exampleLabel.setLocation(30, 155);
	exampleLabel.setSize(100, 45);
	exampleLabel.setText("EJEMPLO: x=0\n                 "
		+ "x<=5\n                 x++");
    }

    @Override
    public void initTextFields() {
	String instructionCode;
	String indexExpression = "";

	String counterExpression = "";

	instructionCode =
		abstractFigure.instruction.instruccion.firstElement()
			.getInstruccionSimple();
	instructionCode = instructionCode.replaceFirst("for", "");
	instructionCode = instructionCode.replace("(", "");
	instructionCode = instructionCode.replace(")", "");
	instructionCode = instructionCode.replace("{", "");

	if ((instructionCode.compareTo("null") != 0)
		&& (instructionCode.compareTo("") != 0)) {
	    String[] forExpressions =
		    abstractFigure.instruction.instruccion.firstElement()
			    .getInstruccionSimple().split(";");
	    for (int charIndex = 0; 
	    	charIndex < forExpressions[0].length(); 
	    	charIndex++) {

		if (charIndex > 3) {

		    indexExpression += forExpressions[0].
		    			charAt(charIndex);
		}
	    }
	    int couterExpressionLength = forExpressions[2].length();
	    for (int charPositionOfCounterExpression = 0; 
	    	charPositionOfCounterExpression < forExpressions[2].length(); 
	    	charPositionOfCounterExpression++) {

		if (((couterExpressionLength - 2) != charPositionOfCounterExpression)
			&& ((couterExpressionLength - 1) != charPositionOfCounterExpression)) {

		    counterExpression +=forExpressions[2].
		    			charAt(charPositionOfCounterExpression);
		}
	    }
	    indexExpressionTextField = new Text(dialog, SWT.SINGLE | SWT.BORDER);
	    indexExpressionTextField.setBounds(15, 25, 170, 20);
	    indexExpressionTextField.setText(indexExpression);

	    conditionExpressionTextField = new Text(dialog, SWT.SINGLE | SWT.BORDER);
	    conditionExpressionTextField.setBounds(15, 80, 170, 20);
	    conditionExpressionTextField.setText(forExpressions[1]);

	    counterExpressionTextField = new Text(dialog, SWT.SINGLE | SWT.BORDER);
	    counterExpressionTextField.setBounds(15, 130, 170, 20);
	    counterExpressionTextField.setText(counterExpression);
	} else {
	    indexExpressionTextField = new Text(dialog, SWT.SINGLE | SWT.BORDER);
	    indexExpressionTextField.setBounds(15, 25, 170, 20);

	    conditionExpressionTextField = new Text(dialog, SWT.SINGLE | SWT.BORDER);
	    conditionExpressionTextField.setBounds(15, 80, 170, 20);

	    counterExpressionTextField = new Text(dialog, SWT.SINGLE | SWT.BORDER);
	    counterExpressionTextField.setBounds(15, 130, 170, 20);
	}
	addKeyListener(indexExpressionTextField);
	addKeyListener(conditionExpressionTextField);
	addKeyListener(counterExpressionTextField);
    }
}
