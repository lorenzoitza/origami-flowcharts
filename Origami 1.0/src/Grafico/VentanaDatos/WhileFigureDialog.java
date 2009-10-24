package Grafico.VentanaDatos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import Administracion.*;
import Administracion.TabFolder;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.Figuras.WhileFigure;

public class WhileFigureDialog extends AbstractDialog<WhileFigure> {

    private Text conditionTextField;
    
    private Label informationLabel;

    public WhileFigureDialog(Shell shell, TabFolder tabFolder, WhileFigure figure,
	    AdminSeleccion selectionAdmin) {
	super(shell, tabFolder, figure, selectionAdmin);
    }

    @Override
    public void validate(boolean band) {
	boolean isChanged = false;

	if (band) {
	    if (conditionTextField.getText() != "") {
		InstruccionSimple whileInstruction = new InstruccionSimple();

		String instructionCode =
			"while(" + conditionTextField.getText() + "){";

		whileInstruction.setInstruccionSimple(instructionCode);
		if (abstractFigure.instruccion.instruccion.size() > 0) {

		    if (!abstractFigure.instruccion.instruccion.elementAt(0).
			    instruccion.equals(instructionCode)) {

			tabbedPaneSelected.getTabItem().getSave()
				.setSave(false);
			tabbedPaneSelected.getTabItem().getInfo()
				.setInformacion(
					"/M - Se agrego o modifico una "
						+ "instruccion "
						+ "en una figura de tipo "
						+ "\"mientras\"\n");
			isChanged = true;
		    }
		}
		abstractFigure.instruccion.instruccion.add(0, whileInstruction);
		tabbedPaneSelected.getHoja().addFigure();
		tabbedPaneSelected.getHoja().guardarRetroceso();
		if (isChanged) {
		    tabbedPaneSelected.getTabItem().getInfo().setDiagrama(
			    tabbedPaneSelected.getHoja().getDiagrama());
		}
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
	if (abstractFigure.instruccion.instruccion.firstElement()
		.getInstruccionSimple().compareTo("") != 0
		&& abstractFigure.instruccion.instruccion.firstElement()
			.getInstruccionSimple().compareTo("null") != 0) {
	    String conditionOfWhile = "";

	    String instructionCode =
		    abstractFigure.instruccion.instruccion.firstElement()
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
