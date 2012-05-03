package origami.graphics.listeners;

import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

import origami.graphics.WindowWidgets;
import origami.graphics.view.CloseWindowView;
import origami.graphics.widgets.TabFolder;

public class CloseWindowListener extends ShellAdapter{
    private TabFolder currentTab;
    private WindowWidgets console;

    public CloseWindowListener(TabFolder tabfolder,WindowWidgets consola){
	this.currentTab = tabfolder;
	this.console = consola;
    }
    public void shellClosed(ShellEvent event){
	CloseWindowView ev = new CloseWindowView(currentTab,console);
	ev.shellClosed(event);
    }
}
