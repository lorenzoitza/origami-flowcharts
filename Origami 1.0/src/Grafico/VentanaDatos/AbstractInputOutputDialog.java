package Grafico.VentanaDatos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Eventos.EventoKey;
import Grafico.Figuras.Entrada;
import Grafico.Figuras.If;
import Imagenes.ImageLoader;


public abstract class AbstractInputOutputDialog<Figure> extends AbstractDialog<Figure>{
    
    protected Control[] textos;

    protected String[] variables = new String[50];

    protected ScrolledComposite sComposite;
    
    protected Composite composite;
    
    protected Button addTextFieldButton;
    
    
    public AbstractInputOutputDialog(Shell shell, TabFolder tabFolder,
	    Figure figura, AdminSeleccion selectionAdmin) {
	super();
	this.dialog = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

	this.tabbedPaneSelected = tabFolder;

	this.display = shell.getDisplay();

	this.abstractFigure = figura;

	this.selectionAdmin = selectionAdmin;

	this.key = new EventoKey(selectionAdmin, tabFolder);
	
	this.sComposite = new ScrolledComposite(dialog, SWT.BORDER | SWT.H_SCROLL
		| SWT.V_SCROLL);
	
	this.composite = new Composite(sComposite, SWT.NONE);
	
	this.create();
	this.initComponents();
    }
    protected void initComponents() {
	initScrollComposite();
	
	initLabels();
	
	initButtons();
	
	initTextFields();
    }
    protected void initScrollComposite(){
	GridData grid = new GridData(GridData.FILL_VERTICAL);
	grid.grabExcessHorizontalSpace = true;
	grid.grabExcessVerticalSpace = true;
	sComposite.setLayoutData(grid);
	composite.setLayoutData(grid);
	cargarCodigo(abstractFigure, composite, display);
	sComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT,
		false));
    }
    protected abstract void cargarCodigo(Figure fig, final Composite composite,
	    final Display d);
}
