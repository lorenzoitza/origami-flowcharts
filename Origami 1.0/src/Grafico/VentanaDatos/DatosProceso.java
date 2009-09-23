package Grafico.VentanaDatos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Eventos.EventoKey;
import Grafico.Ventana;
import Grafico.Figuras.Proceso;
/**
 * Crea la ventana para introducir los datos de una figura de proceso.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class DatosProceso {
	private Shell shell;
	public Proceso fig;
	public Text text ;
	public Text text2;
	public EventoKey key;
	public TabFolder tab;
	
	public DatosProceso(TabFolder tabfolder){
		tab = tabfolder;
	}
	/**
	 * Crea la ventana junto con sus componentes.
	 * @param d
	 * @param fig
	 */
	public void ventana(Display d,Proceso fig,AdminSeleccion selec) {
		key = new EventoKey(selec,tab);
		shell = new Shell(Ventana.shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(320, 150);
		shell.setLocation(300, 200);
		shell.setText("Datos Proceso");
		this.fig = fig;
		
		if(fig.instruccion.getInstruccionSimple().compareTo("null") != 0 && fig.instruccion.getInstruccionSimple().compareTo("") != 0){
			String[] inst = fig.instruccion.getInstruccionSimple().split("=");
			String[] ins = inst[1].split(";");
			text = new Text(shell,SWT.SINGLE | SWT.BORDER);
			text.setBounds(15, 35, 80, 20);
			text2 = new Text(shell,SWT.SINGLE | SWT.BORDER);
			text2.setBounds(145, 35, 145, 20);
			text.setText(inst[0]);
			text2.setText(ins[0]);
		}
		else{
			text = new Text(shell,SWT.SINGLE | SWT.BORDER);
			text.setBounds(15, 35, 80, 20);
			text2 = new Text(shell,SWT.SINGLE | SWT.BORDER);
			text2.setBounds(145, 35, 145, 20);
			text.setText("");
			text2.setText("");
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
		text2.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				key.setKey(e);
				if(key.PresentEnter()){
					BorrarCodigo(true);
					shell.close();
				}
			}
		}); 
		
		Label label = new Label(shell, SWT.NONE);
		label.setLocation(15,15);
		label.setSize(250,15);
		label.setText("Introduce la actividad deseada");
		Label label2 = new Label(shell, SWT.NONE);
		label2.setBounds(125,35,10,15);
		label2.setText("=");
		Label label3 = new Label(shell, SWT.NONE);
		label3.setBounds(20,60,160,15);
		label3.setText("EJEMPLO:  x               =        x - 5");
		
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
			if(!d.readAndDispatch())
				d.sleep();
		}
	}
	/**
	 * Este metodo es el encargado de validar la informacion introducida.
	 * @param mostrar
	 */
	public void BorrarCodigo(boolean mostrar){
		boolean cambio=false;
		if(mostrar){
			String codigo= null;
			if(text.getText() != "" && text2.getText() != ""){
				codigo = text.getText() + " = " + text2.getText() + ";";
				String codigo2 = text.getText() + "=" + text2.getText() + ";";	
				if(!fig.instruccion.instruccion.equals(codigo2)){
					tab.getTabItem().getSave().setSave(false);
					tab.getTabItem().getInfo().setInformacion("/M - Se agrego o modifico una instruccion en una figura de tipo \"proceso\"\n");
					cambio=true;
				}
				fig.instruccion.setInstruccionSimple(codigo);
				tab.getHoja().addFigure();
				tab.getHoja().guardarRetroceso();
				if(cambio){
					tab.getTabItem().getInfo().setDiagrama(tab.getHoja().getDiagrama());
				}
			}
		}
	}
}