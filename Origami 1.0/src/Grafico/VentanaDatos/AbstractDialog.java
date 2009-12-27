package Grafico.VentanaDatos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

import ui.actions.KeyEvent;

public abstract class AbstractDialog<Figure> {

    protected Shell dialog;
    
    protected Label exampleLabel;

    protected KeyEvent key;

    protected Display display;

    protected Figure abstractFigure;

    protected Button acceptButton;

    protected Button cancelButton;
    
    public AbstractDialog() {
    }
    
    public AbstractDialog(Shell shell, Figure figura) {
		this.dialog = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	
		this.display = shell.getDisplay();
	
		this.abstractFigure = figura;
	
		this.key = new KeyEvent();
	
		this.create();
		this.initComponents();
    }

    protected abstract void initLabels();

    public abstract void initTextFields();

    protected void initButtons() {
    	initAcceptButton();
    	initCancelButton();	
    }
    
    private void initAcceptButton(){
    	acceptButton = new Button(dialog, SWT.FLAT);
	acceptButton.setBounds(25, 85, 75, 25);
	acceptButton.setText("ACEPTAR");
	addSelectionListener(acceptButton, true);
    }
    
    private void initCancelButton(){
    	cancelButton = new Button(dialog, SWT.FLAT);
	cancelButton.setBounds(135, 85, 75, 25);
	cancelButton.setText("CANCELAR");
	addSelectionListener(cancelButton, false);
    }

    protected abstract void create();

    public void open(){
		dialog.open();
		while (!dialog.isDisposed()) {
		    
		    if (!display.readAndDispatch()) {
			
			display.sleep();
		    }
		}
    }

    protected void initComponents() {
	initLabels();
	initTextFields();
	initButtons();
    }
    
    protected abstract void validate(boolean band);

    protected void addKeyListener(Text textField) {
    	textField.addKeyListener(getKeyListener());
    }
    
    private KeyAdapter getKeyListener() {
		return new org.eclipse.swt.events.KeyAdapter() {
		    public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
			key.setKey(e);
			if (key.PresentEnter()) {
			    validate(true);
			    dialog.close();
			}
		    }
		};
    }
    
    protected void addSelectionListener(Button confirmButton, boolean band) {
    	confirmButton.addSelectionListener(getSelectionAdapter(band));
    }
    
    private SelectionAdapter getSelectionAdapter(final boolean band) {
	return new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		validate(band);
		dialog.close();
	    }
	};
    }
}
