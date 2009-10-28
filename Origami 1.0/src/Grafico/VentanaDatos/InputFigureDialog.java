package Grafico.VentanaDatos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;
import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Grafico.Figuras.InputFigure;
import Imagenes.ImageLoader;

/**
 * Crea la ventana para introducir los datos de una figura de entrada.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class InputFigureDialog extends AbstractInputOutputDialog<InputFigure> {

    public InputFigureDialog(Shell shell, TabFolder tabFolder, InputFigure figura,
	    AdminSeleccion selectionAdmin) {
	super(shell, tabFolder, figura, selectionAdmin);

	numHorizComponents = 3;
    }
    @Override
    protected void create() {
	dialog.setSize(370, 214);
	dialog.setText("Datos Entrada");
	dialog.setLocation(380, 230);
    }

    @Override
    public void initLabels() {
	addVariableLabel = new Label(dialog, SWT.HORIZONTAL);
	addVariableLabel.setText("AGREGAR UNA VARIABLE MÁS");
	addVariableLabel.setSize(150, 30);
	addVariableLabel.setLocation(165, 155);

	writeVariableLabel = new Label(dialog, SWT.HORIZONTAL);
	writeVariableLabel.setText("ESCRIBE LAS VARIABLES DESEADAS");
	writeVariableLabel.setSize(180, 30);
	writeVariableLabel.setLocation(20, 5);

	exampleLabel = new Label(dialog, SWT.HORIZONTAL);
	exampleLabel.setText("EJEMPLO:   int x");
	exampleLabel.setSize(180, 30);
	exampleLabel.setLocation(220, 5);
    }

    private boolean isInstruction() {
	return (abstractFigure.instruction.getInstruccionSimple().compareTo(
		"null") != 0 && abstractFigure.instruction
		.getInstruccionSimple().compareTo("") != 0);
    }

    @Override
    public void initTextFields() {
	if (isInstruction()) {

	    textBoxContent = abstractFigure.instruction.getInstruccionSimple().split(";");

	    for (int i = 0; i < textBoxContent.length; i++) {

		scrolledCompositeContent = composite.getChildren();

		Text text = new Text(composite, SWT.FLAT | SWT.BORDER);
		text.setBounds(0, 0 + i * 25, 250, 20);
		text.setText(textBoxContent[i]);

		addReadButton(i);
		addDeleteButton(i);
		addKeyListener(text);
	    }
	    if (textBoxContent.length < 4) {

		for (int i = textBoxContent.length; i < 4; i++) {

		    addTextComponent(i);
		}
		scrolledCompositeContent = composite.getChildren();

		Text text =
			(Text) scrolledCompositeContent[textBoxContent.length * numHorizComponents];
		text.forceFocus();
	    } else {
		addTextComponent(scrolledCompositeContent.length / numHorizComponents);
		scrolledCompositeContent = composite.getChildren();

		Text text = (Text) scrolledCompositeContent[scrolledCompositeContent.length - numHorizComponents];
		text.forceFocus();
	    }
	} else {
	    for (int i = 0; i <= 3; i++) {
		addTextComponent(i);

		scrolledCompositeContent = composite.getChildren();
	    }
	}
    }

    
    public void addReadButton(int location) {
	final Button readButton = new Button(composite, SWT.PUSH);
	readButton.setImage(ImageLoader.getImage("teclado.ico"));
	readButton.setBounds(255, location * 25, 20, 20);
	readButton.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		for (int x = 0; x < scrolledCompositeContent.length; x += numHorizComponents) {
		    if (scrolledCompositeContent[x].getBounds().y == readButton.getBounds().y) {
			Text text = (Text) scrolledCompositeContent[x];
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
    
    private boolean isChange(int total) {
	boolean cambio = false;
	
	String codigo = "";
	
	for (int x = 0; x < total; x++) {
	    codigo = codigo + textBoxContent[x] + ";";
	}
	if (!abstractFigure.instruction.instruccion.equals(codigo)) {
	    
	    tabbedPaneSelected.getTabItem().getSave().setSave(false);
	    tabbedPaneSelected.getTabItem().getInfo().setInformacion("/M - Se " +
	    		"agrego o modifico una instruccion en una" +
	    		" figura de tipo \"entrada\"\n");
	    cambio = true;
	}
	abstractFigure.instruction.setInstruccionSimple(codigo);
	tabbedPaneSelected.getHoja().addFigure();
	tabbedPaneSelected.getHoja().guardarRetroceso();
	return cambio;
    }
    
    @Override
    protected void validate(boolean band) {
	int total = 0;
	
	if (band) {
	    scrolledCompositeContent = composite.getChildren();

	    textBoxContent = new String[50];

	    for (int x = 0; x < scrolledCompositeContent.length; x += 3) {

		String copia = ((Text) scrolledCompositeContent[x]).getText();

		while (copia.startsWith(" ")) {
		    
		    copia = copia.replaceFirst(" ", "");
		}
		if (!copia.startsWith("Escribe") && copia != "null"
			&& copia != "") {

		    if (copia.compareToIgnoreCase("leer:")==0) {
			
			String copia2 = copia.substring(5);
			
			while (copia2.startsWith(" ")) {
			    copia2 = copia2.replaceFirst(" ", "");
			}
			if (copia2.length() > 0) {
			    
			    copia = "Leer: " + copia2;
			    textBoxContent[total] = copia;
			    total++;
			}
		    } else {
			textBoxContent[total] = copia;
			total++;
		    }
		}
	    }
	    if (isChange(total)) {
		tabbedPaneSelected.getTabItem().getInfo().setDiagrama(
			tabbedPaneSelected.getHoja().getDiagrama());
	    }
	}
    }

}