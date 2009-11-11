package Administracion.Funcionalidad;

import java.io.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import Administracion.TabItem;
import Grafico.Componentes;
import Grafico.MainWindow;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Ejecutar implements ExecProcessor {
	protected ExecHelper exh;
	public Componentes consola;
	public Compilar codigo;
	private int Exit=0;
	public TabItem a;
	
	private void updateTextArea(final Componentes consola, final String line) {
		MainWindow._display.syncExec(new Runnable () {
			public void run () {
				if(!consola.seleccion){
					String linea = consola.paso.texto(line);					
					if(linea.compareTo("") != 0){
						linea = linea +"\n";
						consola.text.append(linea);
					}
				}
				else{
					consola.text.append(line);
				}
				consola.text.setDragDetect(true);
				consola.setInformation(consola.text.getText().length());
			}
		});
	}
	public void processNewInput(String input) {
		updateTextArea(consola,input);
	}
	public void processNewError(String error) {
		updateTextArea(consola,error);
	}
	public void processEnded(int exitValue) {
		exh = null;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
		}
		codigo.eliminarArchivosCompilar();
	}
	void runCommandActionPerformed(String instruccion) {
		if (exh == null) {
			try {
				exh = ExecHelper.exec(this, instruccion);
			} 
			catch (IOException ex) {
				processNewError(ex.getMessage());
			}
		}
	}
	public void inputActionPerformed(String text) {
		if (exh != null) {
			exh.println(text);
		}
	}
	public void ejecutar(Componentes consola,String instruccion,Compilar codigo) {
		this.a = (TabItem)MainWindow.getComponents().diagramas.getSeleccion();
		this.codigo = codigo;
		this.consola = consola;
		this.consola.text.setText("");
		runCommandActionPerformed(instruccion);
	}
	public void stopEjecutar(){
		MainWindow.getComponents().toolItem[12].setEnabled(false);
		if(exh != null){
			exh.stopEjecucion();	
		}
	}
	public void processExit(int exit) {
		exit(exit);
	}
	private void exit(int exit){
		Exit = Exit + exit;
		if(Exit == 3){
			MainWindow._display.syncExec(new Runnable () {
				public void run () {
					consola.setEnEjecucion(false);
					consola.ejecucionDisable();
					MessageBox messageBox = new MessageBox(MainWindow._shell, SWT.ICON_INFORMATION | SWT.YES );
					messageBox.setText("Origami");
					messageBox.setMessage("La ejecución ha terminado.");
					int selec = messageBox.open();
					switch(selec){
						case 0:
							break;
						case 64:
							break;
						default:
							break;
					}
				}
			});
		}
	}
}