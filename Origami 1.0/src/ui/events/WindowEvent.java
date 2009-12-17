package ui.events;

import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

import Administracion.TabFolder;
import Administracion.Eventos.EventoVentana;
import Grafico.Componentes;


public class WindowEvent extends ShellAdapter{
    public TabFolder tab;
    public Componentes consola;

    public WindowEvent(TabFolder tabfolder,Componentes consola){
	tab = tabfolder;
	this.consola = consola;
    }
    public void shellClosed(ShellEvent e){
	EventoVentana ev = new EventoVentana(tab,consola);
	ev.shellClosed(e);
    }
}
