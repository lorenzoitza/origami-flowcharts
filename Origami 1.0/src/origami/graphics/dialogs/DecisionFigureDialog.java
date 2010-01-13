package Grafico.VentanaDatos;

import Administracion.actions.DialogValidator;
import Grafico.Figuras.DecisionFigure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.origami.debug.Debugger;

public class DecisionFigureDialog extends AbstractDialog<DecisionFigure> {

    private Text conditionTextField;
    
    private Label informationLabel;

    public DecisionFigureDialog(Shell shell, DecisionFigure decisionFigure) {

	super(shell, decisionFigure);
    }
    @Override
    protected void create() {
	dialog.setSize(250, 150);
	dialog.setLocation(300, 200);
	dialog.setText("Datos Decision");
    }

    @Override
    protected void validate(boolean band) {
	if (band) {
		Debugger.debug(this.getClass(), " entro a metodo validate"+band);
	    if ( !conditionTextField.getText().isEmpty() ) {
	    	Debugger.debug(this.getClass(), "cadena de condicion no vacia");
		String instructionCode =
			"if(" + conditionTextField.getText() + "){";

		new DialogValidator().validate(instructionCode, abstractFigure,"si");
	    }
	}
    }

    @Override
    protected  void initLabels() {
    	initInformationLabel();
    	initExampleLabel();
    }
    
    private void initInformationLabel(){
    	informationLabel = new Label(dialog, SWT.NONE);
    	informationLabel.setLocation(15, 5);
    	informationLabel.setSize(250, 15);
    	informationLabel.setText("Introduce la condicion");
    }
    
    private void initExampleLabel(){
    	exampleLabel = new Label(dialog, SWT.NONE);
    	exampleLabel.setLocation(15, 55);
    	exampleLabel.setSize(250, 15);
    	exampleLabel.setText("EJEMPLO:  suma<=condicion");
    }

    @Override
    public void initTextFields() {
	String conditionIfFigure =
		abstractFigure.instructionComposed.simpleInstructionList.firstElement()
			.getInstruccionSimple();

	if ((conditionIfFigure.compareTo("null") != 0)
		&& (conditionIfFigure.compareTo("") != 0)) {

	    String condition = "";

	    for (int charIndex = 0; charIndex < conditionIfFigure.length();
	    	charIndex++) {

		if (charIndex >= 3 && charIndex < conditionIfFigure.length() - 2) {
		    
		    condition += conditionIfFigure.charAt(charIndex);
		}
	    }
	    conditionTextField = new Text(dialog, SWT.SINGLE | SWT.BORDER);
	    conditionTextField.setBounds(15, 25, 180, 20);
	    conditionTextField.setText(condition);
	} else {
	    conditionTextField = new Text(dialog, SWT.SINGLE | SWT.BORDER);
	    conditionTextField.setBounds(15, 25, 180, 20);
	}
	addKeyListener(conditionTextField);
    }
}