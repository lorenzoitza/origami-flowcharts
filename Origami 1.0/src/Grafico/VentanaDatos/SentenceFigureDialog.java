package Grafico.VentanaDatos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.actions.ValidateDialog;
import Grafico.Figuras.SentenceFigure;

public class SentenceFigureDialog extends AbstractDialog<SentenceFigure> {

    private Text variableTextField;

    private Text dataTextField;
    
    private Label informationLabel;
    
    private Label equalOperator;

    public SentenceFigureDialog(Shell shell, TabFolder tabFolder,
	    SentenceFigure figure, AdminSeleccion selectionAdmin) {

	super(shell, tabFolder, figure, selectionAdmin);
    }

    @Override
    public void validate(boolean band) {
	if (band) {

	    if (variableTextField.getText() != ""
		    && dataTextField.getText() != "") {

		String code =
			variableTextField.getText() + " = "
				+ dataTextField.getText() + ";";
		
		new ValidateDialog().validate(code, tabbedPaneSelected, abstractFigure,"si");
		
	    }
	}
    }

    @Override
    protected void create() {
	dialog.setSize(320, 150);
	dialog.setLocation(300, 200);
	dialog.setText("Datos Proceso");
    }

    @Override
    public void initLabels() {
	informationLabel = new Label(dialog, SWT.NONE);
	informationLabel.setLocation(15, 15);
	informationLabel.setSize(250, 15);
	informationLabel.setText("Introduce la actividad deseada");

	equalOperator = new Label(dialog, SWT.NONE);
	equalOperator.setBounds(125, 35, 10, 15);
	equalOperator.setText("=");

	exampleLabel = new Label(dialog, SWT.NONE);
	exampleLabel.setBounds(20, 60, 160, 15);
	exampleLabel.setText("EJEMPLO:  x               =        x - 5");
    }

    @Override
    public void initTextFields() {
	if (abstractFigure.instruccion.getInstruccionSimple().compareTo("null") != 0
		&& abstractFigure.instruccion.getInstruccionSimple().compareTo(
			"") != 0) {

	    String[] instructionCode =
		    abstractFigure.instruccion.getInstruccionSimple()
			    .split("=");
	    String[] dataInstructionCode = instructionCode[1].split(";");

	    variableTextField = new Text(dialog, SWT.SINGLE | SWT.BORDER);
	    variableTextField.setBounds(15, 35, 80, 20);
	    variableTextField.setText(instructionCode[0].trim());

	    dataTextField = new Text(dialog, SWT.SINGLE | SWT.BORDER);
	    dataTextField.setBounds(145, 35, 145, 20);
	    dataTextField.setText(dataInstructionCode[0].trim());
	} else {
	    variableTextField = new Text(dialog, SWT.SINGLE | SWT.BORDER);
	    variableTextField.setBounds(15, 35, 80, 20);
	    variableTextField.setText("");

	    dataTextField = new Text(dialog, SWT.SINGLE | SWT.BORDER);
	    dataTextField.setBounds(145, 35, 145, 20);
	    dataTextField.setText("");
	}
	addKeyListener(variableTextField);
	addKeyListener(dataTextField);
    }
}