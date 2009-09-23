package Administracion.Funcionalidad;

import java.io.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import Administracion.Archivo;
import Administracion.TabFolder;
import Grafico.Ventana;
/**
 * Esta clase guarda y abre el diagrama realizado.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Serializar {
	private String fil;
	/**
	 * Este metodo es el encargado de guardar el diagrama
	 * y recibe el diagrama y la direccion a guardar.
	 * @param figuras
	 * @param fileDir
	 */
	public boolean guardar(TabFolder tab) {
		try {
			tab.getTabItem().getInfo().addTime();
			FileOutputStream fs = new FileOutputStream(fil);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			Archivo archivo = new Archivo(tab.getHoja().getDiagrama());
			archivo.setInfo(tab.getTabItem().getInfo().getInfo());
			os.writeObject(archivo); // 3
			tab.getTabItem().getInfo().removeTime();
			os.close();
		} 
		catch(Exception e){ 
			MessageBox messageBox = new MessageBox(Ventana.shell, SWT.ICON_ERROR| SWT.OK);
    		messageBox.setText("Origami");
    		messageBox.setMessage("El nombre de archivo, directorio o etiqueta del volumén no es válido");
    		int seleccion = messageBox.open();
    		switch(seleccion){
    			case 64:
    				break;
    			case 128:			    	
    				break;
    		}
			e.printStackTrace(); 
			return false;
		}
		return true;
	}
	/**
	 * Este metodo es el encargado de abrir el diagrama guardado.
	 * @param fileDir
	 * @return
	 */
	public Archivo abrir(String fileDir){
		Archivo archivo=null;
		try {
			FileInputStream fis = new FileInputStream(fileDir);
			ObjectInputStream ois = new ObjectInputStream(fis);
			archivo = (Archivo) ois.readObject();
			ois.close();
		} catch (Exception e) { e.printStackTrace(); }
		return archivo;
	}
	public void SetFil(String fil){
		this.fil = fil;
	}
	public String GetFil(){
		return fil;
	}
}
