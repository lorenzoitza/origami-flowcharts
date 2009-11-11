package Administracion.Funcionalidad;

import java.io.File;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import Administracion.TabFolder;
import Administracion.TabItem;
import Grafico.*;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Guardar {
	public static TabFolder tab;
	private String dir = "null";
	private boolean save=true;
	
	public void setTabFolder(TabFolder tabfolder){
		tab = tabfolder;
	}
	public boolean isDiagrama(int selec){
		if(tab.getHoja().getSizeDiagrama()==2){
			return false;
		}
		else{
			return true;
		}
	}
	public void agregarMarca(){
		
	}
	public void GuardarDiagrama(CTabFolderEvent e){
		TabItem a = (TabItem)e.item;
		int selec = a.GetId()+1;
		MessageBox messageBox = new MessageBox(MainWindow._shell, SWT.ICON_WARNING | SWT.YES | SWT.NO |  SWT.CANCEL);
		messageBox.setText("Origami");
		if(a.getText() == "*Diagrama "+ selec){
			messageBox.setMessage("Deseas guardar los cambios efectuados en el diagrama "+ selec+"?");
		}
		else{
			messageBox.setMessage("Deseas guardar los cambios efectuados en el diagrama "+a.getText().substring(1)+"?");
		}
		selec = messageBox.open();
		switch(selec){
		case 64:
			if(tab.getTabItem().getSave().getDir()=="null"){
				FileDialog dialog = new FileDialog(MainWindow._shell,SWT.SAVE);
			    dialog.setFilterExtensions(new String[] { "*.Org"});
			    String archivo = dialog.open();
			    if(archivo!=null){
			    	if(dialog.getFileName().contains("\\") || dialog.getFileName().contains("/") ||
		    				dialog.getFileName().contains(":") || dialog.getFileName().contains("*") ||
		    						dialog.getFileName().contains("?") || dialog.getFileName().contains("<") ||
		    						dialog.getFileName().contains(">") || dialog.getFileName().contains("|") ||
		    						dialog.getFileName().contains("\"")){
		    			MessageBox messageBox2 = new MessageBox(MainWindow._shell, SWT.ICON_ERROR| SWT.OK);
		    			messageBox2.setText("Origami");
		    			messageBox2.setMessage("El nombre de archivo, directorio o etiqueta del volumén no es válido");
			    		int seleccion = messageBox2.open();
			    		switch(seleccion){
			    			case 64:
			    				break;
			    			case 128:			    	
			    				break;
			    		}
		    		}
		    		else{
		    			boolean existe = false;
		    			try{
		    				File arch = new File(archivo);
		    				if(arch.exists()){
		    					existe = true;
		    				}
		    			}
		    			catch(Exception e1){
		    			}		    	
		    			if(existe){
		    				MessageBox messageBox3 = new MessageBox(MainWindow._shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
		    				messageBox3.setText("Origami");
		    				messageBox3.setMessage("El archivo ya existe. ¿Desea reemplazarlo?");
				    		int seleccion = messageBox.open();
				    		switch(seleccion){
				    			case 64:
							    	MainWindow.getSerializer().SetFil(archivo);
							    	tab.getTabItem().getSave().setDir(archivo);
							    	MainWindow.getSerializer().guardar(tab);
							    	archivo = dialog.getFileName();
							    	int pos = archivo.indexOf('.');
							    	String name = archivo.substring(0, pos);
							    	tab.cambiarNombre("*"+name);
							    	tab.getTabItem().getSave().setSave(true);
				    				break;
				    			case 128:							
				    				break;
				    		}
				    	}
				    	else{
					    	MainWindow.getSerializer().SetFil(archivo);
					    	tab.getTabItem().getSave().setDir(archivo);
					    	boolean error = MainWindow.getSerializer().guardar(tab);
					    	if(error){
					    		archivo = dialog.getFileName();
					    		int pos = archivo.indexOf('.');
						    	String name = archivo.substring(0, pos);
						    	tab.cambiarNombre("*"+name);
						    	tab.getTabItem().getSave().setSave(true);
					    	}
				    	}
		    		}
			    }
			    else{
			    	e.doit = false;
			    }
			}
			else{
		    	MainWindow.getSerializer().SetFil(tab.getTabItem().getSave().getDir());
		    	MainWindow.getSerializer().guardar(tab);
		    	tab.getTabItem().getSave().setSave(true);
			}
			break;
		case 128:
			e.doit = true;
			cerrar(a.GetId());
			break;
		case 256:
				e.doit = false;
			break;
		}
	}
	public static void cerrar(int i){
		if(tab.getItemCount()==1){
			tab.getHoja().getPanel().removeAll();
			tab.getHoja().getDibujarDiagrama().setOpaque(false);
			MainWindow.getComponents().diagramaData.exclude=true;
			MainWindow.getComponents().diagramas.getHoja().setBoundsToZero();
			tab.getHoja().getDiagrama().removeAllElements();
			MainWindow.getComponents().disableAll(false);
		}
	}
	public void verificar(){
		if(save){
			String nombre = tab.getNombre();
			if(nombre.startsWith("*")){
				nombre = nombre.substring(1);
				tab.cambiarNombre(nombre);
				MainWindow.getComponentes().guardarDisable(false);
			}
			else{
				MainWindow.getComponentes().guardarDisable(false);
			}
		}
		else{
			String nombre = tab.getNombre();
			if(!nombre.startsWith("*")){
				nombre = "*"+nombre;
				tab.cambiarNombre(nombre);
				MainWindow.getComponentes().guardarDisable(true);
			}
		}
	}
	public void verificarCambio(){
		if(save){
			if(tab.getHoja().getDiagrama().size()==2){
				MainWindow.getComponentes().guardarDisable(true);
			}
			else{
				MainWindow.getComponentes().guardarDisable(false);
			}
		}
		else{
			MainWindow.getComponentes().guardarDisable(true);
		}
	}
	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public boolean isSave() {
		return save;
	}

	public void setSave(boolean save) {
		this.save = save;
		verificar();
	}
}
