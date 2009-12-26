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
	protected ExecHelper execHelper;
	public Componentes console;
	private CodeCompiler compiler;
	private int Exit=0;
	public TabItem a;
	
	private void updateTextArea(final Componentes consola, final String line) {
		MainWindow.display.syncExec(new Runnable () {
			public void run () {
				if(!consola.seleccion){
					String linea = consola.paso.texto(line);					
					if(linea.compareTo("") != 0){
						linea = linea +"\n";
						consola.console.text.append(linea);
					}
				}
				else{
					consola.console.text.append(line);
				}
				consola.console.text.setDragDetect(true);
				consola.console.setInformation(consola.console.text.getText().length());
			}
		});
	}
	public void processNewInput(String input) {
		updateTextArea(console,input);
	}
	public void processNewError(String error) {
		updateTextArea(console,error);
	}
	public void processEnded(int exitValue) {
		execHelper = null;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
		}
		compiler.deleteMainFiles();
	}
	void runCommandActionPerformed(String instruccion) {
		if (execHelper == null) {
			try {
				execHelper = ExecHelper.exec(this, instruccion);
			} 
			catch (IOException ex) {
				processNewError(ex.getMessage());
			}
		}
	}
	public void inputActionPerformed(String text) {
		if (execHelper != null) {
			execHelper.println(text);
		}
	}
	public void ejecutar(Componentes consola,String instruccion, CodeCompiler codigo) {
		this.a = (TabItem)MainWindow.getComponents()._diagrams.getSeleccion();
		this.compiler = codigo;
		this.console = consola;
		this.console.console.text.setText("");
		runCommandActionPerformed(instruccion);
	}
	public void stopEjecutar(){
		MainWindow.getComponents().barraHerramientas.toolItem[12].setEnabled(false);
		if(execHelper != null){
			execHelper.stopEjecucion();	
		}
	}
	public void processExit(int exit) {
		exit(exit);
	}
	private void exit(int exit){
		Exit = Exit + exit;
		if(Exit == 3){
			MainWindow.display.syncExec(new Runnable () {
				public void run () {
					console.setEnEjecucion(false);
					console.ejecucionDisable();
					MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_INFORMATION | SWT.YES );
					messageBox.setText("Origami");
					messageBox.setMessage("La ejecuci�n ha terminado.");
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