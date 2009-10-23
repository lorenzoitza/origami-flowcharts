package Grafico.VentanaDatos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Grafico.Figuras.Imprimir;

/**
 * Crea la ventana para introducir los datos de una figura de salida.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class OutputFigureDialog extends AbstractInputOutputDialog<Imprimir> {

    public OutputFigureDialog(Shell shell, TabFolder tabFolder,
	    Imprimir figura, AdminSeleccion selectionAdmin) {
	super(shell, tabFolder, figura, selectionAdmin);

	numHorizComponents = 2;
    }

    @Override
    public void close() {
	dialog.close();
    }

    @Override
    protected void create() {
	dialog.setSize(370, 214);
	dialog.setText("Datos Imprimir");
	dialog.setLocation(380, 230);
    }

    @Override
    public void initLabels() {
	addVariableLabel = new Label(dialog, SWT.HORIZONTAL);
	addVariableLabel.setText("AGREGAR UNA VARIABLE MÁS");
	addVariableLabel.setSize(150, 30);
	addVariableLabel.setLocation(165, 155);

	writeVariablesLabel = new Label(dialog, SWT.HORIZONTAL);
	writeVariablesLabel.setText("ESCRIBE LAS VARIABLES DESEADAS");
	writeVariablesLabel.setSize(180, 30);
	writeVariablesLabel.setLocation(5, 5);

	exampleLabel = new Label(dialog, SWT.HORIZONTAL);
	exampleLabel.setText("EJEMPLO: \"El valor de x es:\", x");
	exampleLabel.setSize(180, 40);
	exampleLabel.setLocation(195, 5);
    }

    private boolean isInstruction() {
	return (abstractFigure.instruccion.getInstruccionSimple().compareTo(
		"null") != 0 && abstractFigure.instruccion
		.getInstruccionSimple().compareTo("") != 0);
    }

    @Override
    public void initTextFields() {
	if (isInstruction()) {

	    variables = abstractFigure.instruccion.getInstruccionSimple().split(";");

	    cleanTextField();
	    int cont = 0;

	    for (int i = 0; i < variables.length
		    && variables[i].compareTo("") != 0; i++) {

		textos = composite.getChildren();

		Text text = new Text(composite, SWT.FLAT | SWT.BORDER);
		text.setBounds(0, 0 + i * 25, 250, 20);
		text.setText(variables[i]);

		addDeleteButton(i);
		addKeyListener(text);
		cont++;
	    }
	    if (cont < 4) {

		for (int i = cont; i < 4; i++) {
		    
		    addTextComponent(i);
		}
		textos = composite.getChildren();

		Text text = (Text) textos[cont * 2];
		text.forceFocus();
	    } else {
		addTextComponent(composite.getChildren().length / numHorizComponents);
		textos = composite.getChildren();

		Text text = (Text) textos[textos.length - numHorizComponents];
		text.forceFocus();
	    }
	} else {
	    for (int i = 0; i <= 3; i++) {

		addTextComponent(i);
		textos = composite.getChildren();
	    }
	}
    }

    @Override
    protected void initScrollComposite() {
	GridData grid = new GridData(GridData.FILL_VERTICAL);
	grid.grabExcessHorizontalSpace = true;
	grid.grabExcessVerticalSpace = true;
	sComposite.setLayoutData(grid);
	composite.setLayoutData(grid);

	sComposite.setContent(composite);
	sComposite.setExpandHorizontal(true);
	sComposite.setExpandVertical(true);
	sComposite.setBounds(0, 20, 345, 120);

	sComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT,
		false));
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

    private boolean isChange(int total) {
	boolean cambio = false;

	String codigo = "";

	for (int x = 0; x < total; x++) {
	    codigo = codigo + variables[x] + ";";
	}
	if (!abstractFigure.instruccion.instruccion.equals(codigo)) {

	    tabbedPaneSelected.getTabItem().getSave().setSave(false);
	    tabbedPaneSelected.getTabItem().getInfo().setInformacion( "/M - Se " 
		    + "agrego o modifico una instruccion en una"
		    + " figura de tipo \"salida\"\n");
	    cambio = true;
	}
	abstractFigure.instruccion.setInstruccionSimple(codigo);
	tabbedPaneSelected.getHoja().addFigure();
	tabbedPaneSelected.getHoja().guardarRetroceso();
	return cambio;
    }

    @Override
    protected void validate(boolean band) {
	int total = 0;
	
	if (band) {
	    textos = composite.getChildren();

	    variables = new String[50];

	    for (int x = 0; x < textos.length; x += 2) {

		String copia = ((Text) textos[x]).getText();

		while (copia.startsWith(" ")) {

		    copia = copia.replaceFirst(" ", "");
		}
		if (!copia.startsWith("Escribe") && copia != "null"
			&& copia != "") {

		    variables[total] = copia;
		    total++;
		}
	    }
	    if (isChange(total)) {
		
		tabbedPaneSelected.getTabItem().getInfo().setDiagrama(
			tabbedPaneSelected.getHoja().getDiagrama());
	    }
	}
    }

    private void cleanTextField() {
	String[] variables2 = new String[50];
	
	int cont = 0;
	
	for (int x = 0; x < variables.length; x++) {
	    
	    variables[x] = variables[x].replace("\\", "");
	    variables[x] = variables[x].replaceFirst("p", "");
	    
	    if (variables[x].compareTo("") != 0) {
		
		variables2[cont] = variables[x];
		cont++;
	    }
	}
	for (int x = 0; x < cont; x++) {
	    
	    if (variables2[x].compareTo("") != 0) {
		
		variables[x] = variables2[x];
	    }
	}
    }

    @Override
    protected void addTextComponent(int position) {
	addTextField(position);
	addDeleteButton(position);
    }
}