package Grafico.VentanaDatos;

import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Funcionalidad.Codigo.*;
import Grafico.Figuras.If;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DecisionFigureDialog extends AbstractDialog<If> {

    private Text conditionTextField;
    
    private Label informationLabel;

    public DecisionFigureDialog(Shell shell, TabFolder tabFolder, If figura,
	    AdminSeleccion selectionAdmin) {

	super(shell, tabFolder, figura, selectionAdmin);
    }

    @Override
    public void close() {
	dialog.close();
    }

    @Override
    protected void create() {
	dialog.setSize(250, 150);
	dialog.setLocation(300, 200);
	dialog.setText("Datos Decision");
    }

    @Override
    public void open() {
	dialog.open();
	while (!dialog.isDisposed()) {
	    if (!display.readAndDispatch()) {
		display.sleep();
	    }
	}
    }

    @Override
    protected void validate(boolean band) {
	boolean isChanged = false;

	InstruccionSimple codigo = new InstruccionSimple();

	if (band) {
	    if (conditionTextField.getText() != "") {

		String instructionCode =
			"if(" + conditionTextField.getText() + "){";

		codigo.setInstruccionSimple(instructionCode);

		if (abstractFigure.instruccion.instruccion.size() > 0) {

		    if (!abstractFigure.instruccion.instruccion.elementAt(0).
			    instruccion.equals(instructionCode)) {

			tabbedPaneSelected.getTabItem().getSave().setSave(false);
			tabbedPaneSelected.getTabItem().getInfo().setInformacion(
						"/M - Se agrego"
						+ " o modifico una "
						+ "instruccion en una figura de tipo \"si\"\n");
			isChanged = true;
		    }
		}
		abstractFigure.instruccion.instruccion.add(0, codigo);
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
	String conditionIfFigure =
		abstractFigure.instruccion.instruccion.firstElement()
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