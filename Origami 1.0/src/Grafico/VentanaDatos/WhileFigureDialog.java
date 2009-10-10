package Grafico.VentanaDatos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import Administracion.*;
import Administracion.TabFolder;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.Figuras.While;


public class WhileFigureDialog extends AbstractDialog<While> {
	
	public Text conditionTextField ;
	
	public WhileFigureDialog(Shell shell, TabFolder tabFolder, While figure,AdminSeleccion selectionAdmin) {
		super(shell, tabFolder, figure, selectionAdmin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		dialog.close();
	}

	@Override
	public void initComponents() {
		// TODO Auto-generated method stub	
		
		if(abstractFigure.instruccion.instruccion.firstElement().getInstruccionSimple().compareTo("") != 0 && abstractFigure.instruccion.instruccion.firstElement().getInstruccionSimple().compareTo("null") != 0){
			String inst = abstractFigure.instruccion.instruccion.firstElement().getInstruccionSimple();
			String correcto="";
			for(int x=0;x<inst.length();x++){
				if(x>5 && x<inst.length()-2){
					correcto += inst.charAt(x);
				}
			}
		    conditionTextField = new Text(dialog,SWT.SINGLE | SWT.BORDER);
			conditionTextField.setBounds(15, 25, 180, 20);
			conditionTextField.setText(correcto);
		}
		else{
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
		
		Button acceptButton = new Button(dialog,SWT.FLAT);
		acceptButton.setBounds(25,85,75,25);
		acceptButton.setText("ACEPTAR");
		addSelectionListener(acceptButton,true);
		
		Button cancelButton = new Button(dialog,SWT.FLAT);
		cancelButton.setBounds(135,85,75,25);
		cancelButton.setText("CANCELAR");
		addSelectionListener(cancelButton,false);
	}

	@Override
	public void open() {
		// TODO Auto-generated method stub
		dialog.open();
	}

	@Override
	public void validate(boolean band) {
		// TODO Auto-generated method stub
		boolean cambio=false;
		if(band){
			if(conditionTextField.getText() != ""){
				InstruccionSimple codigo = new InstruccionSimple();
				String instruc = "while(" + conditionTextField.getText() + "){";
				codigo.setInstruccionSimple(instruc);
				if(abstractFigure.instruccion.instruccion.size()>0){
					if(!abstractFigure.instruccion.instruccion.elementAt(0).instruccion.equals(instruc)){
						tabbedPaneSelected.getTabItem().getSave().setSave(false);
						tabbedPaneSelected.getTabItem().getInfo().setInformacion("/M - Se agrego o modifico una instruccion en una figura de tipo \"mientras\"\n");
						cambio=true;
					}
				}
				abstractFigure.instruccion.instruccion.add(0, codigo);
				tabbedPaneSelected.getHoja().addFigure();
				tabbedPaneSelected.getHoja().guardarRetroceso();
				if(cambio){
					tabbedPaneSelected.getTabItem().getInfo().setDiagrama(tabbedPaneSelected.getHoja().getDiagrama());
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
