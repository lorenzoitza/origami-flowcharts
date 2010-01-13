package Grafico.VentanaDatos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import Administracion.actions.DialogValidator;
import Grafico.Figuras.WhileFigure;

public class WhileFigureDialog extends AbstractDialog<WhileFigure> {

    private Text conditionTextField;
    
    private Label informationLabel;

    public WhileFigureDialog(Shell shell, WhileFigure figure) {
	super(shell, figure);
    }

    @Override
    public void validate(boolean band) {
	if (band) {
	    if (conditionTextField.getText() != "") {
		
		String instructionCode =
			"while(" + conditionTextField.getText() + "){";

		new DialogValidator().validate(instructionCode, abstractFigure,"mientras");
	    }
	}
    }

    @Override
    protected void create() {
	dialog.setSize(250, 150);
	dialog.setLocation(300, 200);
	dialog.setText("Datos While");
    }

    @Override
    public void initLabels() {
	informationLabel = new Label(dialog, SWT.NONE);
	informationLabel.setLocation(15, 5);
	informationLabel.setSize(250, 15);
	informationLabel.setText("Introduce la condicion");

	exampleLabel = new Label(dialog, SWT.NONE);
	exampleLabel.setLocation(15, 55);
	exampleLabel.setSize(250, 15);
	exampleLabel.setText("EJEMPLO:  suma<=condicion");
    }

    @Override
    public void initTextFields() {
	if (abstractFigure.instructionComposed.simpleInstructionList.firstElement()
		.getInstruccionSimple().compareTo("") != 0
		&& abstractFigure.instructionComposed.simpleInstructionList.firstElement()
			.getInstruccionSimple().compareTo("null") != 0) {
	    String conditionOfWhile = "";

	    String instructionCode =
		    abstractFigure.instructionComposed.simpleInstructionList.firstElement()
			    .getInstruccionSimple();

	    for (int charIndex = 0; charIndex < instructionCode.length();
	    	charIndex++) {

		if ((charIndex > 5)
			&& (charIndex < instructionCode.length() - 2)) {
		    conditionOfWhile += instructionCode.charAt(charIndex);
		}
	    }
	    conditionTextField = new Text(dialog, SWT.SINGLE | SWT.BORDER);
	    conditionTextField.setBounds(15, 25, 180, 20);
	    conditionTextField.setText(conditionOfWhile);
	} else {
	    conditionTextField = new Text(dialog, SWT.SINGLE | SWT.BORDER);
	    conditionTextField.setBounds(15, 25, 180, 20);
	}
	addKeyListener(conditionTextField);
    }

}
