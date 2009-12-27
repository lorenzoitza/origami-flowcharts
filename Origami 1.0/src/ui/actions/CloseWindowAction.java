package ui.actions;

import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

import Administracion.TabFolder;
import Grafico.Componentes;
import Grafico.view.CloseWindowView;


public class CloseWindowAction extends ShellAdapter{
    public TabFolder tab;
    public Componentes consola;

    public CloseWindowAction(TabFolder tabfolder,Componentes consola){
	tab = tabfolder;
	this.consola = consola;
    }
    public void shellClosed(ShellEvent e){
	CloseWindowView ev = new CloseWindowView(tab,consola);
	ev.shellClosed(e);
    }
}
