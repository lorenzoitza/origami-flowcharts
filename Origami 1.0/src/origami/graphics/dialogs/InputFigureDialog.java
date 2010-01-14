package origami.graphics.dialogs;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

import origami.administration.actions.DialogValidator;
import origami.debug.Debugger;
import origami.graphics.figures.InputFigure;
import origami.images.ImageLoader;


/**
 * Crea la ventana para introducir los datos de una figura de entrada.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class InputFigureDialog extends AbstractInputOutputDialog<InputFigure> {

    public InputFigureDialog(Shell shell, InputFigure figura) {
	super(shell, figura);

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
	addVariableLabel.setText("AGREGAR UNA VARIABLE M�S");
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

	    for (int contentBoxElement = 0; contentBoxElement < textBoxContent.length; contentBoxElement++) {

		scrolledCompositeContent = composite.getChildren();

		Text text = new Text(composite, SWT.FLAT | SWT.BORDER);
		text.setBounds(0, 0 + contentBoxElement * 25, 250, 20);
		text.setText(textBoxContent[contentBoxElement]);

		addReadButton(contentBoxElement);
		addDeleteButton(contentBoxElement);
		addKeyListener(text);
		
		
	    }
	    if (textBoxContent.length < 4) {

		for (int textBox = textBoxContent.length; textBox < 4; textBox++) {

		    addTextComponent(textBox);
		}
		scrolledCompositeContent = composite.getChildren();

		Text text =
			(Text) scrolledCompositeContent[textBoxContent.length * 3];
		text.forceFocus();
	    } else {
		addTextComponent(composite.getChildren().length / 3);
		scrolledCompositeContent = composite.getChildren();

		Text text = (Text) scrolledCompositeContent[scrolledCompositeContent.length - 3];
		text.forceFocus();
	    }
	} else {
	    for (int component = 0; component <= 3; component++) {
		addTextComponent(component);

		scrolledCompositeContent = composite.getChildren();
	    }
	}
    }

    //TODO: no se ha definido el valor de la X que sirve como indice.
    public void addReadButton(int location) {
	final Button readButton = new Button(composite, SWT.PUSH);
	readButton.setImage(ImageLoader.getImage("teclado.ico"));
	readButton.setBounds(255, location * 25, 20, 20);
	readButton.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		for (int x = 0; x < scrolledCompositeContent.length; x += 3) {
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
    
    @Override
    protected void validate(boolean band) {
	if (band) {
	    scrolledCompositeContent = composite.getChildren();
	    
	    String inputCode = "";
	    
	    for (int contentIndex = 0; contentIndex < scrolledCompositeContent.length; contentIndex += 3) {
		
		String inputText = ((Text) scrolledCompositeContent[contentIndex]).getText().trim();
		Debugger.debug(this.getClass(),"inputTex"+inputText);
		
		if(!inputText.isEmpty() && !inputText.startsWith("Escribe") && inputText.compareToIgnoreCase("null") != 0){
		    
		    inputCode += concatenatCode(inputText);
		    Debugger.debug(this.getClass(),"cconcate"+inputCode);
		}
	    }
	    
	    //new DialogValidator().validate(abstractFigure, inputCode,"entrada");
	    
	    new DialogValidator().validate(inputCode, abstractFigure, "entrada");
	}
    }
    
    private String concatenatCode(String inputCode){
	if(inputCode.startsWith("Leer:")){
	    String codeTempCopy = inputCode.substring(5).trim();
	    if (!codeTempCopy.isEmpty()) {//no es vacio
		inputCode = "Leer: " + codeTempCopy;
	    }
	}
	return inputCode+";";
	
	
	
	
    }

}