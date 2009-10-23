package Grafico.VentanaDatos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Eventos.EventoKey;
import Imagenes.ImageLoader;

public abstract class AbstractInputOutputDialog<Figure> extends
	AbstractDialog<Figure> {

    protected Control[] textos;

    protected String[] variables = new String[50];

    protected ScrolledComposite sComposite;

    protected Composite composite;

    protected Button addTextFieldButton;

    protected int numHorizComponents;

    protected Label addVariableLabel;

    protected Label writeVariablesLabel;

    public AbstractInputOutputDialog(Shell shell, TabFolder tabFolder,
	    Figure figura, AdminSeleccion selectionAdmin) {
	super();
	this.dialog = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

	this.tabbedPaneSelected = tabFolder;

	this.display = shell.getDisplay();

	this.abstractFigure = figura;

	this.selectionAdmin = selectionAdmin;

	this.key = new EventoKey(selectionAdmin, tabFolder);

	this.sComposite =
		new ScrolledComposite(dialog, SWT.BORDER | SWT.H_SCROLL
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

    protected void initScrollComposite() {
	GridData grid = new GridData(GridData.FILL_VERTICAL);
	grid.grabExcessHorizontalSpace = true;
	grid.grabExcessVerticalSpace = true;
	sComposite.setLayoutData(grid);
	composite.setLayoutData(grid);

	sComposite.setContent(composite);
	sComposite.setExpandHorizontal(true);
	sComposite.setExpandVertical(true);
	sComposite.setBounds(0, 20, 325, 120);

	sComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT,
		false));
    }

    @Override
    protected void initButtons() {
	acceptButton = new Button(dialog, SWT.FLAT);
	acceptButton.setBounds(5, 145, 70, 25);
	acceptButton.setText("ACEPTAR");
	addSelectionListener(acceptButton, true);

	cancelButton = new Button(dialog, SWT.FLAT);
	cancelButton.setBounds(85, 145, 70, 25);
	cancelButton.setText("CANCELAR");
	addSelectionListener(cancelButton, false);

	addTextFieldButton = new Button(dialog, SWT.PUSH);
	addTextFieldButton.setSize(45, 40);
	addTextFieldButton.setLocation(318, 140);
	addTextFieldButton.setImage(ImageLoader.getImage("suma.png"));
	addTextFieldButton.pack();
	addTextSelectionListener();
    }

    protected void addTextListener(Text textField) {
	textField.addListener(SWT.FocusIn, getFocusListener(textField));
    }

    private Listener getFocusListener(final Text text) {
	return new Listener() {

	    public void handleEvent(Event e) {
		if (text.getText().startsWith("Escribe")) {
		    text.setText("");
		}
	    }
	};
    }

    protected void addDeleteButton(int location) {
	final Button deleteButton = new Button(composite, SWT.PUSH);
	deleteButton.setBounds(280, location * 25, 20, 20);
	deleteButton.setImage(ImageLoader.getImage("borrar.gif"));
	deleteButton.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		for (int x = 0; x < textos.length; x += numHorizComponents) {
		    if (textos[x].getBounds().y == deleteButton.getBounds().y) {
			Text text = (Text) textos[x];
			text.setText("");
		    }
		}
	    }
	});
    }

    protected abstract void addTextComponent(int position);

    protected void addTextField(int position) {
	int verticalSpace = 25;
	Text text = new Text(composite, SWT.FLAT | SWT.BORDER);
	text.setBounds(0, position * verticalSpace, 250, 20);
	text.setText("Escribe aqui");

	addTextListener(text);

	addKeyListener(text);
    }

    protected void addTextSelectionListener() {
	addTextFieldButton.addSelectionListener(getTextSelectionAdapter());
    }

    private SelectionAdapter getTextSelectionAdapter() {
	return new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent e) {
		addTextComponent(composite.getChildren().length
			/ numHorizComponents);
		textos = composite.getChildren();
		sComposite.setMinSize(composite.computeSize(SWT.DEFAULT,
			SWT.DEFAULT, false));
	    }
	};
    }
}
