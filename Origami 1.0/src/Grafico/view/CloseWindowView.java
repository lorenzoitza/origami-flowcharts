package Grafico.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.MessageBox;
import Administracion.TabFolder;
import Administracion.TabItem;
import Grafico.Componentes;
import Grafico.MainWindow;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class CloseWindowView{
	public TabFolder tab;
	public Componentes consola;
	
	public CloseWindowView(TabFolder tabfolder,Componentes consola){
		tab = tabfolder;
		this.consola = consola;
	}
	public void shellClosed(ShellEvent e){
		int selec = 0;
		CTabItem diagramas[] = tab.getTabFolder().getItems();
		TabItem aux;
		boolean mensaje = false;
		for(int i=0;i<diagramas.length; i++){
			aux = (TabItem)diagramas[i];
			if(!aux.getSave().isSave()){
				mensaje = true;
				break;
			}
		}
		if(mensaje){
			MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_WARNING | SWT.YES | SWT.NO |  SWT.CANCEL);
			messageBox.setText("Origami");
			messageBox.setMessage("Existen diagramas sin guardar, ¿desea guardarlos?");
				selec = messageBox.open();
				switch(selec){
				case 0:
					break;
				case 64:
					e.doit = false;
					break;
				case 128:
					if(consola.getEnEjecucion()){
						consola.stopEjecucion();
					}
					e.doit = true;
					break;
				case 256:
					e.doit = false;
					break;
				}
		}
			if(consola.getEnEjecucion()){
				consola.stopEjecucion();
		}
	}
	
} 