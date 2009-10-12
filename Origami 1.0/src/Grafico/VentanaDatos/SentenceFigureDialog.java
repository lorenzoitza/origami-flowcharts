package Grafico.VentanaDatos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Grafico.Figuras.Proceso;

public class SentenceFigureDialog extends AbstractDialog<Proceso>{
	
	public Text variableTextField;
	
	public Text dataTextField;
	
	public SentenceFigureDialog(Shell shell, TabFolder tabFolder, 
		Proceso figure, AdminSeleccion selectionAdmin) {
	    
	    super (shell, tabFolder, figure, selectionAdmin);
	}
	
	@Override
	public void close() {
		dialog.close();
	}

	@Override
	public void initComponents() {
	    if(abstractFigure.instruccion.getInstruccionSimple().compareTo("null") 
		    != 0 && abstractFigure.instruccion.getInstruccionSimple().
		    compareTo("") != 0){
			
		String[] instructionCode = abstractFigure.instruccion.
		getInstruccionSimple().split("=");
		String[] dataInstructionCode = instructionCode[1].split(";");
			
		variableTextField = new Text(dialog,SWT.SINGLE | SWT.BORDER);
		variableTextField.setBounds(15, 35, 80, 20);
		variableTextField.setText(instructionCode[0].trim());
			
		dataTextField = new Text(dialog,SWT.SINGLE | SWT.BORDER);
		dataTextField.setBounds(145, 35, 145, 20);
		dataTextField.setText(dataInstructionCode[0].trim());
	    }else{
		variableTextField = new Text(dialog,SWT.SINGLE | SWT.BORDER);
		variableTextField.setBounds(15, 35, 80, 20);
		variableTextField.setText("");
			
		dataTextField = new Text(dialog,SWT.SINGLE | SWT.BORDER);
		dataTextField.setBounds(145, 35, 145, 20);
		dataTextField.setText("");
	    }
	    addKeyListener(variableTextField);
	    addKeyListener(dataTextField);
		
	    Label informationLabel = new Label(dialog, SWT.NONE);
	    informationLabel.setLocation(15,15);
	    informationLabel.setSize(250,15);
	    informationLabel.setText("Introduce la actividad deseada");
		
	    Label equalOperator = new Label(dialog, SWT.NONE);
	    equalOperator.setBounds(125,35,10,15);
	    equalOperator.setText("=");
		
	    Label exampleLabel = new Label(dialog, SWT.NONE);
	    exampleLabel.setBounds(20,60,160,15);
	    exampleLabel.setText("EJEMPLO:  x               =        x - 5");
		
	    Button acceptButton = new Button(dialog,SWT.FLAT);
	    acceptButton.setBounds(25,85,75,25);
	    acceptButton.setText("ACEPTAR");
	    addSelectionListener(acceptButton, true);
		
	    Button cancelButton = new Button(dialog,SWT.FLAT);
	    cancelButton.setBounds(135,85,75,25);
	    cancelButton.setText("CANCELAR");
	    addSelectionListener(cancelButton, false);
	}

	@Override
	public void open() {
	    dialog.open();
	}

	@Override
	public void validate(boolean band) {
	    boolean isChanged = false;
		
	    if(band){	
		
		if(variableTextField.getText() != "" && dataTextField.getText() != ""){
		
		    String code = variableTextField.getText()+ " = " + 
		    		dataTextField.getText()+ ";";
							
		    if(!abstractFigure.instruccion.instruccion.equals(code)){
			
			tabbedPaneSelected.getTabItem().getSave().setSave(false);
			tabbedPaneSelected.getTabItem().getInfo().
			setInformacion("/M - Se agrego " + "o modifico una " +
					"instruccion en una figura de tipo " +
					"\"proceso\"\n");
			isChanged=true;
			abstractFigure.instruccion.setInstruccionSimple(code);
		    }
		    abstractFigure.instruccion.setInstruccionSimple(code);
		    tabbedPaneSelected.getHoja().addFigure();
		    tabbedPaneSelected.getHoja().guardarRetroceso();
				
		    if(isChanged){
			tabbedPaneSelected.getTabItem().getInfo().
			setDiagrama(tabbedPaneSelected.getHoja().getDiagrama());
		    }
		}
	    }
	}

	@Override
	protected void create() {
	    dialog.setSize(320, 150);
	    dialog.setLocation(300, 200);
	    dialog.setText("Datos Proceso");
	}
}