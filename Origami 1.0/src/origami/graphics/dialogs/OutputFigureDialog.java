package origami.graphics.dialogs;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

import origami.administration.actions.DialogValidator;
import origami.debug.Debugger;
import origami.graphics.figures.OutputFigure;



/**
 * Crea la ventana para introducir los datos de una figura de salida.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class OutputFigureDialog extends AbstractInputOutputDialog<OutputFigure> {

    public OutputFigureDialog(Shell shell, OutputFigure figura) {
	super(shell, figura);

	numHorizComponents = 2;
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

	writeVariableLabel = new Label(dialog, SWT.HORIZONTAL);
	writeVariableLabel.setText("ESCRIBE LAS VARIABLES DESEADAS");
	writeVariableLabel.setSize(180, 30);
	writeVariableLabel.setLocation(5, 5);

	exampleLabel = new Label(dialog, SWT.HORIZONTAL);
	exampleLabel.setText("EJEMPLO: \"El valor de x es:\", x");
	exampleLabel.setSize(180, 40);
	exampleLabel.setLocation(195, 5);
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

	    cleanTextField();
	    int cont = 0;

	    for (int i = 0; i < textBoxContent.length
		    && textBoxContent[i].compareTo("") != 0; i++) {

		scrolledCompositeContent = composite.getChildren();

		Text text = new Text(composite, SWT.FLAT | SWT.BORDER);
		text.setBounds(0, 0 + i * 25, 250, 20);
		text.setText(textBoxContent[i]);

		addDeleteButton(i);
		addKeyListener(text);
		cont++;
	    }
	    if (cont < 4) {

		for (int i = cont; i < 4; i++) {
		    
		    addTextComponent(i);
		}
		scrolledCompositeContent = composite.getChildren();

		Text text = (Text) scrolledCompositeContent[cont * 2];
		text.forceFocus();
	    } else {
		addTextComponent(composite.getChildren().length / numHorizComponents);
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

    @Override
    protected void initScrollComposite() {
	GridData grid = new GridData(GridData.FILL_VERTICAL);
	grid.grabExcessHorizontalSpace = true;
	grid.grabExcessVerticalSpace = true;
	scrolledComposite.setLayoutData(grid);
	composite.setLayoutData(grid);

	scrolledComposite.setContent(composite);
	scrolledComposite.setExpandHorizontal(true);
	scrolledComposite.setExpandVertical(true);
	scrolledComposite.setBounds(0, 20, 345, 120);

	scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT,
		false));
    }

    private void cleanTextField() {
	String[] variables2 = new String[50];
	
	int cont = 0;
	
	for (int x = 0; x < textBoxContent.length; x++) {
	    
	    textBoxContent[x] = textBoxContent[x].replace("\\", "");
	    textBoxContent[x] = textBoxContent[x].replaceFirst("p", "");
	    
	    if (textBoxContent[x].compareTo("") != 0) {
		
		variables2[cont] = textBoxContent[x];
		cont++;
	    }
	}
	for (int x = 0; x < cont; x++) {
	    
	    if (variables2[x].compareTo("") != 0) {
		
		textBoxContent[x] = variables2[x];
	    }
	}
    }

    @Override
    protected void addTextComponent(int position) {
	addTextField(position);
	addDeleteButton(position);
    }
    
    @Override
    protected void validate(boolean band) {
	if (band) {
	    scrolledCompositeContent = composite.getChildren();
	    
	    String inputCode = "";
	    
	    for (int contentIndex = 0; contentIndex < scrolledCompositeContent.length; contentIndex += 2) {
		
		String inputText = ((Text) scrolledCompositeContent[contentIndex]).getText().trim();
		Debugger.debug(this.getClass(),"inputTex"+inputText);
		
		if(!inputText.isEmpty() && !inputText.startsWith("Escribe") && inputText.compareToIgnoreCase("null") != 0){
		    
		    inputCode += concatenatCode(inputText);
		    Debugger.debug(this.getClass(),"cconcate"+inputCode);
		}
	    }
	    
	    new DialogValidator().validate(abstractFigure, inputCode,"salida");
	    
	}
    }
    
    private String concatenatCode(String inputCode){
	return "\\" + "p"+inputCode+";";
    }

}