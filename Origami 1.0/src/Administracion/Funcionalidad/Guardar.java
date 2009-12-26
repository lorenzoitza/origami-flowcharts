package Administracion.Funcionalidad;

import java.io.File;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import Administracion.TabFolder;
import Administracion.TabItem;
import Grafico.*;
import Grafico.view.SaveFileView;
import Grafico.view.SaveType;

/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Guardar {

    public static TabFolder tab;

    private String dir = "null";

    private boolean save = true;

    public void setTabFolder(TabFolder tabfolder) {
	tab = tabfolder;
    }

    public boolean isDiagrama(int selec) {
	if (tab.getHoja().getSizeDiagrama() == 2) {
	    return false;
	} else {
	    return true;
	}
    }

    public void agregarMarca() {

    }

    public void GuardarDiagrama(CTabFolderEvent e) {
	TabItem a = (TabItem) e.item;
	int selec = a.GetId() + 1;
	MessageBox messageBox =
		new MessageBox(MainWindow.shell, SWT.ICON_WARNING | SWT.YES
			| SWT.NO | SWT.CANCEL);
	messageBox.setText("Origami");
	if (a.getText() == "*Diagrama " + selec) {
	    messageBox
		    .setMessage("Deseas guardar los cambios efectuados en el diagrama "
			    + selec + "?");
	} else {
	    messageBox
		    .setMessage("Deseas guardar los cambios efectuados en el diagrama "
			    + a.getText().substring(1) + "?");
	}
	selec = messageBox.open();
	switch (selec) {
	case 64: 
	    SaveFileView save = new SaveFileView();
	    if (tab.getTabItem().getSave().getDir() == "null") {
		save.setSaveType(SaveType.SAVE);
		boolean success = save.createWindow();
		if(success){
		    e.doit = false;
		}
	    } else {
		save.saveAction();
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

    public static void cerrar(int i) {
	if (tab.getItemCount() == 1) {
	    tab.getHoja().getPanel().removeAll();
	    tab.getHoja().getDibujarDiagrama().setOpaque(false);
	    MainWindow.getComponents().diagramaData.exclude = true;
	    MainWindow.getComponents()._diagrams.getHoja().setBoundsToZero();
	    tab.getHoja().getDiagrama().removeAllElements();
	    MainWindow.getComponents().disableAll(false);
	}
    }

    public void verificar() {
	if (save) {
	    String nombre = tab.getNombre();
	    if (nombre.startsWith("*")) {
		nombre = nombre.substring(1);
		tab.cambiarNombre(nombre);
		MainWindow.getComponents().guardarDisable(false);
	    } else {
		MainWindow.getComponents().guardarDisable(false);
	    }
	} else {
	    String nombre = tab.getNombre();
	    if (!nombre.startsWith("*")) {
		nombre = "*" + nombre;
		tab.cambiarNombre(nombre);
		MainWindow.getComponents().guardarDisable(true);
	    }
	}
    }

    public void verificarCambio() {
	if (save) {
	    if (tab.getHoja().getDiagrama().size() == 2) {
		MainWindow.getComponents().guardarDisable(true);
	    } else {
		MainWindow.getComponents().guardarDisable(false);
	    }
	} else {
	    MainWindow.getComponents().guardarDisable(true);
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
