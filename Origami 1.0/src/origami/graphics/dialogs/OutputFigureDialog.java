package origami.graphics.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import origami.administration.actions.DialogValidator;
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

	setNumHorizComponents(2);
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
		"null") != 0 && !abstractFigure.instruction.getInstruccionSimple().isEmpty());
    }

    @Override
    public void initTextFields() {
	if (isInstruction()) {

	    textBoxContent = abstractFigure.instruction.getInstruccionSimple().split(";");

	    cleanTextField();
	    int inputTextCounter = 0;
	    
	    int maxInputText = 4;
	    
	    int numHorizComponents = 2;

	    for (int inputTextElement = 0; inputTextElement < textBoxContent.length
		    && !textBoxContent[inputTextElement].isEmpty(); inputTextElement++) {

		scrolledCompositeContent = composite.getChildren();

		Text tempInputText = new Text(composite, SWT.FLAT | SWT.BORDER);
		tempInputText.setBounds(0, 0 + inputTextElement * 25, 250, 20);
		tempInputText.setText(textBoxContent[inputTextElement]);

		addDeleteButton(inputTextElement,AbstractInputOutputDialog.OUTPUT);
		addKeyListener(tempInputText);
		inputTextCounter++;
	    }
	    
	    if (inputTextCounter < maxInputText) {

		for (int indexPosition = inputTextCounter; indexPosition < maxInputText; indexPosition++) {
		    
		    addTextComponent(indexPosition);
		}
		scrolledCompositeContent = composite.getChildren();

		Text newInputText = (Text) scrolledCompositeContent[inputTextCounter * numHorizComponents];
		newInputText.forceFocus();
	    } else {
		addTextComponent(composite.getChildren().length / numHorizComponents);
		scrolledCompositeContent = composite.getChildren();

		Text text = (Text) scrolledCompositeContent[scrolledCompositeContent.length - numHorizComponents];
		text.forceFocus();
		
		scrolledCompositeContent = composite.getChildren();
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT,
			SWT.DEFAULT, false));
	    }
	} else {
	    for (int indexPosition = 0; indexPosition <= 3; indexPosition++) {

		addTextComponent(indexPosition);
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
	String[] inputVariables = new String[50];
	
	int inputTextCounter = 0;
	
	for (int contentIndex = 0; contentIndex < textBoxContent.length; contentIndex++) {
	    
	    textBoxContent[contentIndex] = textBoxContent[contentIndex].replace("\\", "");
	    textBoxContent[contentIndex] = textBoxContent[contentIndex].replaceFirst("p", "");
	    
	    if (!textBoxContent[contentIndex].isEmpty()) {
		
		inputVariables[inputTextCounter] = textBoxContent[contentIndex];
		inputTextCounter++;
	    }
	}
	
	for (int contentIndex = 0; contentIndex < inputTextCounter; contentIndex++) {
	    
	    if (!inputVariables[contentIndex].isEmpty()) {
		
		textBoxContent[contentIndex] = inputVariables[contentIndex];
	    }
	}
    }

    @Override
    protected void addTextComponent(int position) {
	addTextField(position);
	addDeleteButton(position,AbstractInputOutputDialog.OUTPUT);
    }
    
    @Override
    protected void validate(boolean band) {
	if (band) {
	    scrolledCompositeContent = composite.getChildren();
	    
	    String inputCode = "";
	    
	    for (int contentIndex = 0; contentIndex < scrolledCompositeContent.length; contentIndex += 2) {
		
		String inputText = ((Text) scrolledCompositeContent[contentIndex]).getText().trim();
		
		if(!inputText.isEmpty() && !inputText.startsWith("Escribe") && inputText.compareToIgnoreCase("null") != 0){
		    
		    inputCode += concatenatCode(inputText);
		}
	    }
	    if(inputCode.compareToIgnoreCase("")==0){
		inputCode="null";
	    }
	    System.out.println(inputCode);
//	    new DialogValidator().validate(abstractFigure, inputCode,"salida");
//	    new DialogValidator().validate(inputCode, abstractFigure, "entrada");
	    new DialogValidator().validate(inputCode, abstractFigure, "salida");
	}
    }
    
    private String concatenatCode(String inputCode){
	return "\\" + "p"+inputCode+";";
    }

}