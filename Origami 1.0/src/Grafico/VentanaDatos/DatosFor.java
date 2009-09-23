package Grafico.VentanaDatos;

import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Eventos.EventoKey;
import Administracion.Funcionalidad.Codigo.*;
import Grafico.Ventana;
import Grafico.Figuras.For;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
/**
 * Crea la ventana para introducir los datos de un For.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */

public class DatosFor {
	private Shell shell;
	public For fig;
	public Text text,text2,text3;
	public EventoKey key;
	public TabFolder tab;
	
	public DatosFor(TabFolder tabfolder){
		tab = tabfolder;
	}
	/**
	 * Crea la ventana junto con sus componentes.
	 * @param d
	 * @param fig
	 */
	public void ventana(Display d,For fig,AdminSeleccion selec) {
		key = new EventoKey(selec,tab);
		shell = new Shell(Ventana.shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		//Image i = new Image(Ventana.display,"imagenes\\ventana.ico");
		//shell.setImage(i);
		shell.setSize(210, 280);
		shell.setLocation(300, 200);
		shell.setText("Datos For");
		this.fig = fig;
		
		String codigo = fig.instruccion.instruccion.firstElement().getInstruccionSimple();
		codigo = codigo.replaceFirst("for","");
		codigo = codigo.replace("(","");
		codigo = codigo.replace(")","");
		codigo = codigo.replace("{","");
		
		if(codigo.compareTo("null") != 0 && codigo.compareTo("") != 0){
			String[] inst = fig.instruccion.instruccion.firstElement().getInstruccionSimple().split(";");
			String correcto="";
			for(int x=0;x<inst[0].length();x++){
				if(x>3){
					correcto += inst[0].charAt(x);
				}
			}
			String correcto2="";
			int y=inst[2].length();
			for(int x=0;x<inst[2].length();x++){
				if((y-2 != x) && (y-1!=x)){
					correcto2 += inst[2].charAt(x);
				}
			}
			text = new Text(shell,SWT.SINGLE | SWT.BORDER);
			text.setBounds(15, 25, 170, 20);
			text2 = new Text(shell,SWT.SINGLE | SWT.BORDER);
			text2.setBounds(15, 80, 170, 20);
			text3 = new Text(shell,SWT.SINGLE | SWT.BORDER);
			text3.setBounds(15, 130, 170, 20);
			text.setText(correcto);
			text2.setText(inst[1]);
			text3.setText(correcto2);
		}
		else{
			text = new Text(shell,SWT.SINGLE | SWT.BORDER);
			text.setBounds(15, 25, 170, 20);
			text2 = new Text(shell,SWT.SINGLE | SWT.BORDER);
			text2.setBounds(15, 80, 170, 20);
			text3 = new Text(shell,SWT.SINGLE | SWT.BORDER);
			text3.setBounds(15, 130, 170, 20);
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
		
		text3.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				key.setKey(e);
				if(key.PresentEnter()){
					BorrarCodigo(true);
					shell.close();
				}
			}
		}); 
		
		Label label = new Label(shell, SWT.NONE);
		label.setLocation(20,10);
		label.setSize(50,15);
		label.setText("Inicio");
		
		Label label2 = new Label(shell, SWT.NONE);
		label2.setLocation(20,65);
		label2.setSize(50,15);
		label2.setText("Condición");
		
		Label label3 = new Label(shell, SWT.NONE);
		label3.setLocation(20,115);
		label3.setSize(50,15);
		label3.setText("Iteración");
		
		Label label4 = new Label(shell, SWT.NONE);
		label4.setLocation(30,155);
		label4.setSize(100,45);
		label4.setText("EJEMPLO: x=0\n                 x<=5\n                 x++");
		
		Button boton = new Button(shell,SWT.FLAT);
		boton.setBounds(20,215,75,25);
		boton.setText("ACEPTAR");
		boton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) { 
				BorrarCodigo(true);
				shell.close();
			}
		});
		Button boton2 = new Button(shell,SWT.FLAT);
		boton2.setBounds(110,215,75,25);
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
			if(text.getText() != "" && text2.getText() != "" && text3.getText() != "" ){
				InstruccionSimple codigo = new InstruccionSimple();
				String instruc = "for("+text.getText()+";"+text2.getText()+";"+text3.getText()+"){";
				codigo.setInstruccionSimple(instruc);
				if(fig.instruccion.instruccion.size()>0){
					if(!fig.instruccion.instruccion.elementAt(0).instruccion.equals(instruc)){
						tab.getTabItem().getSave().setSave(false);
						tab.getTabItem().getInfo().setInformacion("/M Se agrego o modifico una instruccion en una figura de tipo \"para\"\n");
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