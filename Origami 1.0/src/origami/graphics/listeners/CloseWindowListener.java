package origami.graphics.listeners;

import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

import origami.graphics.Componentes;
import origami.graphics.view.CloseWindowView;
import origami.graphics.widgets.TabFolder;



public class CloseWindowListener extends ShellAdapter{
    public TabFolder tab;
    public Componentes consola;

    public CloseWindowListener(TabFolder tabfolder,Componentes consola){
	tab = tabfolder;
	this.consola = consola;
    }
    public void shellClosed(ShellEvent e){
	CloseWindowView ev = new CloseWindowView(tab,consola);
	ev.shellClosed(e);
    }
}
