package Grafico.VentanaDatos;

import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Eventos.EventoKey;
import Administracion.Funcionalidad.Codigo.*;
import Grafico.Ventana;
import Grafico.Figuras.While;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
/**
 * Crea la ventana para introducir los datos de un While.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class DialogWhileLoop {
	private Shell shell;
	public While fig;
	public Text text ;
	public EventoKey key;
	public TabFolder tab;
	
	public DialogWhileLoop(TabFolder tabfolder){
		tab = tabfolder;
	}
	/**
	 * Crea la ventana junto con sus componentes.
	 * @param d
	 * @param fig
	 */
	public void ventana(Display d,While fig,AdminSeleccion selec) {
		key = new EventoKey(selec,tab);
		shell = new Shell(Ventana.shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(250, 150);
		shell.setLocation(300, 200);
		shell.setText("Datos While");
		this.fig = fig;
		
		if(fig.instruccion.instruccion.firstElement().getInstruccionSimple().compareTo("") != 0 && fig.instruccion.instruccion.firstElement().getInstruccionSimple().compareTo("null") != 0){
			String inst = fig.instruccion.instruccion.firstElement().getInstruccionSimple();
			String correcto="";
			for(int x=0;x<inst.length();x++){
				if(x>5 && x<inst.length()-2){
					correcto += inst.charAt(x);
				}
			}
		    text = new Text(shell,SWT.SINGLE | SWT.BORDER);
			text.setBounds(15, 25, 180, 20);
			text.setText(correcto);
		}
		else{
			text = new Text(shell,SWT.SINGLE | SWT.BORDER);
			text.setBounds(15, 25, 180, 20);
		}
		text.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				key.setKey(e);
				if(key.PresentEnter()){
					BorrarCodigo(true);
					shell.close();
				}
			}
		}); 
		
		Label label = new Label(shell, SWT.NONE);
		label.setLocation(15,5);
		label.setSize(250,15);
		label.setText("Introduce la condicion");
		
		Label label2 = new Label(shell, SWT.NONE);
		label2.setLocation(15,55);
		label2.setSize(250,15);
		label2.setText("EJEMPLO:  suma<=condicion");
		
		Button boton = new Button(shell,SWT.FLAT);
		boton.setBounds(25,85,75,25);
		boton.setText("ACEPTAR");
		boton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { 
				BorrarCodigo(true);
				shell.close();
			}
		});
		Button boton2 = new Button(shell,SWT.FLAT);
		boton2.setBounds(135,85,75,25);
		boton2.setText("CANCELAR");
		boton2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				BorrarCodigo(false);
				shell.close();
			}
		});
		
		shell.open();
		while(!shell.isDisposed()){
			if(!d.readAndDispatch()){
				d.sleep();
			}
		}
	}
	/**
	 * Este metodo es el encargado de validar la informacion introducida.
	 * @param mostrar
	 */
	public void BorrarCodigo(boolean mostrar){
		boolean cambio=false;
		if(mostrar){
			if(text.getText() != ""){
				InstruccionSimple codigo = new InstruccionSimple();
				String instruc = "while(" + text.getText() + "){";
				codigo.setInstruccionSimple(instruc);
				if(fig.instruccion.instruccion.size()>0){
					if(!fig.instruccion.instruccion.elementAt(0).instruccion.equals(instruc)){
						tab.getTabItem().getSave().setSave(false);
						tab.getTabItem().getInfo().setInformacion("/M - Se agrego o modifico una instruccion en una figura de tipo \"mientras\"\n");
						cambio=true;
					}
				}
				fig.instruccion.instruccion.add(0, codigo);
				tab.getHoja().addFigure();
				tab.getHoja().guardarRetroceso();
				if(cambio){
					tab.getTabItem().getInfo().setDiagrama(tab.getHoja().getDiagrama());
				}
			}
		}
	}
}