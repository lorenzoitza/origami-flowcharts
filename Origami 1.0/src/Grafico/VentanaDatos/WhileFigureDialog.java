package Grafico.VentanaDatos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import Administracion.*;
import Administracion.TabFolder;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.Figuras.While;


public class WhileFigureDialog extends AbstractDialog<While> {
	
	private Text conditionTextField ;
	
	public WhileFigureDialog(Shell shell, TabFolder tabFolder, 
			While figure, AdminSeleccion selectionAdmin) {
		super (shell, tabFolder, figure, selectionAdmin);
	}

	@Override
	public void close() {
		dialog.close();
	}

	@Override
	public void initComponents() {
	    if(abstractFigure.instruccion.instruccion.firstElement()
		    .getInstruccionSimple().compareTo("") != 0 && abstractFigure
		    .instruccion.instruccion.firstElement().getInstruccionSimple()
		    .compareTo("null") != 0) {
		String conditionOfWhile = "";
	
		String instructionCode = abstractFigure.instruccion.instruccion.
			firstElement().getInstruccionSimple();
		
		for (int charIndex = 0; charIndex < instructionCode.length();
			charIndex++) {
		    
		    	if((charIndex > 5) && 
		    		(charIndex < instructionCode.length()-2)){
		    	    conditionOfWhile += instructionCode.charAt(charIndex);
			}
		}
		conditionTextField = new Text(dialog,SWT.SINGLE | SWT.BORDER);
		conditionTextField.setBounds(15, 25, 180, 20);
		conditionTextField.setText(conditionOfWhile);
	    } else {
		conditionTextField = new Text(dialog,SWT.SINGLE | SWT.BORDER);
		conditionTextField.setBounds(15, 25, 180, 20);
	    }
	    addKeyListener(conditionTextField);
	    Label informationLabel = new Label(dialog, SWT.NONE);
	    informationLabel.setLocation(15,5);
	    informationLabel.setSize(250,15);
	    informationLabel.setText("Introduce la condicion");
		
	    Label examplelabel = new Label(dialog, SWT.NONE);
	    examplelabel.setLocation(15,55);
	    examplelabel.setSize(250,15);
	    examplelabel.setText("EJEMPLO:  suma<=condicion");
		
	    acceptButton = new Button(dialog,SWT.FLAT);
	    acceptButton.setBounds(25,85,75,25);
	    acceptButton.setText("ACEPTAR");
	    addSelectionListener(acceptButton,true);
		
	    cancelButton = new Button(dialog,SWT.FLAT);
	    cancelButton.setBounds(135,85,75,25);
	    cancelButton.setText("CANCELAR");
	    addSelectionListener(cancelButton,false);
	}

	@Override
	public void open() {
		dialog.open();
	}

	@Override
	public void validate(boolean band) {
	    boolean isChanged = false;
	    
	    if (band) {
		if (conditionTextField.getText() != "") {
		    InstruccionSimple whileInstruction = new InstruccionSimple();	
		    
		    String instructionCode = "while(" + conditionTextField.
		    getText() + "){";
				
		    whileInstruction.setInstruccionSimple(instructionCode);	
		    if (abstractFigure.instruccion.instruccion.size() > 0) {
			
			if (!abstractFigure.instruccion.instruccion.elementAt(0)
				.instruccion.equals(instructionCode)){
			    
			    tabbedPaneSelected.getTabItem().getSave().setSave(false);
			    tabbedPaneSelected.getTabItem().getInfo()
			    .setInformacion("/M - Se agrego o modifico una instruccion " +
				    "en una figura de tipo \"mientras\"\n");
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
}
