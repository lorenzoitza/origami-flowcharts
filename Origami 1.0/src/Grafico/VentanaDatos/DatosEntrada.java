package Grafico.VentanaDatos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Eventos.EventoKey;
import Grafico.Ventana;
import Grafico.Figuras.Entrada;
import Grafico.Figuras.If;
import Imagenes.ImageLoader;

/**
 * Crea la ventana para introducir los datos de una figura de entrada.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class DatosEntrada extends AbstractInputOutputDialog<Entrada> {

    public DatosEntrada(Shell shell, TabFolder tabFolder, Entrada figura,
	    AdminSeleccion selectionAdmin) {
	super(shell, tabFolder, figura, selectionAdmin);
	
	numHorizComponents=3;
    }
    @Override
    public void close() {
	dialog.close();
    }
    @Override
    protected void create() {
	dialog.setSize(370, 214);
	dialog.setText("Datos Entrada");
	dialog.setLocation(380, 230);
    }
    @Override
    public void initLabels() {
	informationLabel = new Label(dialog, SWT.HORIZONTAL);
	informationLabel.setText("AGREGAR UNA VARIABLE MÁS");
	informationLabel.setSize(150, 30);
	informationLabel.setLocation(165, 155);
	
	information2Label= new Label(dialog, SWT.HORIZONTAL);
	information2Label.setText("ESCRIBE LAS VARIABLES DESEADAS");
	information2Label.setSize(180, 30);
	information2Label.setLocation(20, 5);
	
	exampleLabel = new Label(dialog, SWT.HORIZONTAL);
	exampleLabel.setText("EJEMPLO:   int x");
	exampleLabel.setSize(180, 30);
	exampleLabel.setLocation(220, 5);
    }
    @Override
    public void initTextFields() {
	if (abstractFigure.instruccion.getInstruccionSimple().compareTo("null") != 0
		&& abstractFigure.instruccion.getInstruccionSimple().compareTo("") != 0) {
	    variables = abstractFigure.instruccion.getInstruccionSimple().split(";");
	    for (int i = 0; i < variables.length; i++) {
		textos = composite.getChildren();
		Text text = new Text(composite, SWT.FLAT | SWT.BORDER);
		text.setBounds(0, 0 + i * 25, 250, 20);
		text.setText(variables[i]);
		
		addReadButton(i);
		
		addDeleteButton(i);
		
		addKeyListener(text);

	    }
	    if (variables.length < 4) {
		int i;
		for (i = variables.length; i < 4; i++) {
		    addTextComponent(i);
		}
		textos = composite.getChildren();
		Text text = (Text) textos[variables.length * numHorizComponents];
		text.forceFocus();
	    } else {		
		addTextComponent(textos.length / numHorizComponents);

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
	int total = 0;
	if(band){
		textos = composite.getChildren();
		variables = new String[50];
		for (int x = 0; x < textos.length; x += 3) {
		    Text text = (Text) textos[x];
		    String copia = text.getText();
		    while (copia.startsWith(" ")) {
			copia = copia.replaceFirst(" ", "");
		    }
		    if (!copia.startsWith("Escribe") && copia != "null"
			    && copia != "") {
			if (copia.startsWith("Leer:")
				|| copia.startsWith("leer:")) {
			    String copia2 = copia.substring(5);
			    while (copia2.startsWith(" ")) {
				copia2 = copia2.replaceFirst(" ", "");
			    }
			    if (copia2.length() > 0) {
				copia = "Leer: " + copia2;
				variables[total] = copia;
				total++;
			    }
			} else {
			    variables[total] = copia;
			    total++;
			}
		    }
		}
	}

	
	boolean cambio = false;
	if (band) {
	    String codigo = "";
	    for (int x = 0; x < total; x++) {
		codigo = codigo + variables[x] + ";";
	    }
	    if (!abstractFigure.instruccion.instruccion.equals(codigo)) {
		tabbedPaneSelected.getTabItem().getSave().setSave(false);
		tabbedPaneSelected
			.getTabItem()
			.getInfo()
			.setInformacion(
				"/M - Se agrego o modifico una instruccion en una figura de tipo \"entrada\"\n");
		cambio = true;
	    }
	    abstractFigure.instruccion.setInstruccionSimple(codigo);
	    tabbedPaneSelected.getHoja().addFigure();
	    tabbedPaneSelected.getHoja().guardarRetroceso();
	    if (cambio) {
		tabbedPaneSelected.getTabItem().getInfo().setDiagrama(
			tabbedPaneSelected.getHoja().getDiagrama());
	    }
	}
    }
    public void addReadButton(int location){
	final Button readButton = new Button(composite, SWT.PUSH);
	readButton.setImage(ImageLoader.getImage("teclado.ico"));
	readButton.setBounds(255, location * 25, 20, 20);
	readButton.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent event) {
		for (int x = 0; x < textos.length; x += numHorizComponents) {
		    if (textos[x].getBounds().y == readButton.getBounds().y) {
			Text text = (Text) textos[x];
			String texto = addReadString(text.getText());
			text.setText(texto);
			text.setSelection(texto.length());
			text.setFocus();
		    }
		}
	    }
	});
    }

    private String addReadString(String textString) {
	while (textString.startsWith(" ")) {
	    textString = textString.replaceFirst(" ", "");
	}
	if (!textString.startsWith("Leer:") || (textString.compareTo("") == 0)) {
	    if (textString.startsWith("Escribe")) {
		textString = "Leer: ";
	    } else {
		textString = "Leer: " + textString;
	    }
	}
	return textString;
    }
    @Override
    protected void addTextComponent(int position) {
	addTextField(position);
	
	addReadButton(position);
	
	addDeleteButton(position);
    }
}