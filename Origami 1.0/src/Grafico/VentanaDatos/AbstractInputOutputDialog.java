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
import Grafico.Figuras.Entrada;
import Grafico.Figuras.If;
import Imagenes.ImageLoader;


public abstract class AbstractInputOutputDialog<Figure> extends AbstractDialog<Figure>{
    
    protected Control[] textos;

    protected String[] variables = new String[50];

    protected ScrolledComposite sComposite;
    
    protected Composite composite;
    
    public AbstractInputOutputDialog(Shell shell, TabFolder tabFolder,
	    Figure figura, AdminSeleccion selectionAdmin) {
	super(shell, tabFolder, figura, selectionAdmin);
    }
    protected void initComponents() {
	
	initScrollComposite();
	
	initLabels();
	
	//initButtons();
	
	initTextFields();
    }
    protected void initScrollComposite(){
	sComposite = new ScrolledComposite(dialog, SWT.BORDER | SWT.H_SCROLL
			| SWT.V_SCROLL);
	GridData grid = new GridData(GridData.FILL_VERTICAL);
	grid.grabExcessHorizontalSpace = true;
	grid.grabExcessVerticalSpace = true;
	sComposite.setLayoutData(grid);
	composite = new Composite(sComposite, SWT.NONE);
	composite.setLayoutData(grid);
	cargarCodigo(abstractFigure, composite, display);
	sComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT,
		false));
    }
    protected abstract void cargarCodigo(Figure fig, final Composite composite,
	    final Display d);
    
    protected abstract void validate(boolean band, int total);
}
