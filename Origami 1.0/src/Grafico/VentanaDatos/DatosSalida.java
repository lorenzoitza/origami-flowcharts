package Grafico.VentanaDatos;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Eventos.EventoKey;
import Grafico.Ventana;
import Grafico.Figuras.Entrada;
import Grafico.Figuras.Imprimir;
import Imagenes.ImageLoader;

/**
 * Crea la ventana para introducir los datos de una figura de salida.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class DatosSalida extends AbstractInputOutputDialog<Imprimir>{

    public DatosSalida(Shell shell, TabFolder tabFolder, Imprimir figura,
	    AdminSeleccion selectionAdmin) {
	super(shell, tabFolder, figura, selectionAdmin);
	// TODO Auto-generated constructor stub
    }
    
    public void addDeleteButton(int i){
	final Button bot = new Button(composite, SWT.PUSH);
	bot.setBounds(280, i * 25, 20, 20);
	bot.setImage(ImageLoader.getImage("borrar.gif"));
	bot.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent event) {
		for (int x = 0; x < textos.length; x += 2) {
		    if (textos[x].getBounds().y == bot.getBounds().y) {
			Text text = (Text) textos[x];
			text.setText("");
		    }
		}
	    }
	});
    }
    @Override
    protected void cargarCodigo(Imprimir fig, Composite composite, Display d) {
	// TODO Auto-generated method stub
	if (fig.instruccion.getInstruccionSimple().compareTo("null") != 0
		&& fig.instruccion.getInstruccionSimple().compareTo("") != 0) {
	    variables = fig.instruccion.getInstruccionSimple().split(";");
	    limpiar();
	    int cont = 0;
	    for (int i = 0; i < variables.length
		    && variables[i].compareTo("") != 0; i++) {
		textos = composite.getChildren();
		Text text = new Text(composite, SWT.FLAT | SWT.BORDER);
		text.setBounds(0, 0 + i * 25, 250, 20);
		text.setText(variables[i]);
		
		addDeleteButton(i);
		
		addKeyListener(text);
		
		cont++;
	    }
	    if (cont < 4) {
		int i;
		for (i = cont; i < 4; i++) {
		    textos = composite.getChildren();
		    final Text text2 =
			    new Text(composite, SWT.FLAT | SWT.BORDER);
		    text2.setBounds(0, 0 + i * 25, 250, 20);
		    text2.setText("Escribe aqui");
		   
		    
		    addDeleteButton(i);
		    
		    addTextListener(text2);
		   
		    addKeyListener(text2);
		}
		textos = composite.getChildren();
		final Text text = (Text) textos[cont * 2];
		text.forceFocus();
	    } else {
		textos = composite.getChildren();
		final Text text2 = new Text(composite, SWT.FLAT | SWT.BORDER);
		text2.setBounds(0, 0 + textos.length / 2 * 25, 250, 20);
		text2.setText("Escribe aqui");

		
		addDeleteButton(textos.length / 2);
		
		addTextListener(text2);
		
		addKeyListener(text2);
		
		textos = composite.getChildren();
		final Text text = (Text) textos[textos.length - 2];
		text.forceFocus();
	    }
	} else {
	    for (int i = 0; i <= 3; i++) {
		textos = composite.getChildren();
		final Text text = new Text(composite, SWT.BORDER);
		text.setBounds(0, 0 + i * 25, 250, 20);
		text.setText("Escribe aqui");
		
		addDeleteButton(i);
		
		addTextListener(text);
		
		addKeyListener(text);
		
		textos = composite.getChildren();
	    }
	}
	
    }

    @Override
    public void close() {
	// TODO Auto-generated method stub
	dialog.close();
    }

    @Override
    protected void create() {
	// TODO Auto-generated method stub
	dialog.setSize(370, 214);
	dialog.setText("Datos Imprimir");
	dialog.setLocation(380, 230);
    }

    @Override
    public void initLabels() {
	// TODO Auto-generated method stub
	Label label = new Label(dialog, SWT.HORIZONTAL);
	label.setText("AGREGAR UNA VARIABLE MÁS");
	label.setSize(150, 30);
	label.setLocation(165, 155);
	
	Label label2 = new Label(dialog, SWT.HORIZONTAL);
	label2.setText("ESCRIBE LAS VARIABLES DESEADAS");
	label2.setSize(180, 30);
	label2.setLocation(5, 5);
	
	Label label3 = new Label(dialog, SWT.HORIZONTAL);
	label3.setText("EJEMPLO: \"El valor de x es:\", x");
	label3.setSize(180, 40);
	label3.setLocation(195, 5);
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
	addTextFieldButton.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent event) {
		textos = composite.getChildren();
		int numero = textos.length / 2;
		int y = 25;
		final Text text2 = new Text(composite, SWT.FLAT | SWT.BORDER);
		text2.setBounds(0, numero * y, 250, 20);
		text2.setText("Escribe aqui");
		
	
		
		addDeleteButton(numero);
		
		addTextListener(text2);
		    
		addKeyListener(text2);


		textos = composite.getChildren();
		sComposite.setMinSize(composite.computeSize(SWT.DEFAULT,
			SWT.DEFAULT, false));
	    }
	});
    }
    @Override
    public void initTextFields() {
	// TODO Auto-generated method stub
	Button button = new Button(dialog, SWT.PUSH);
	button.setSize(45, 40);
	button.setLocation(318, 140);
	// Image imagenSuma = new Image(d, "imagenes\\suma.png");
	button.setImage(ImageLoader.getImage("suma.png"));
	button.pack();
	button.addSelectionListener(new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		textos = composite.getChildren();
		int numero = textos.length / 2;
		int y = 25;
		final Text text2 = new Text(composite, SWT.FLAT | SWT.BORDER);
		text2.setBounds(0, numero * y, 250, 20);
		text2.setText("Escribe aqui");
		final Button bot = new Button(composite, SWT.PUSH);
		bot.setBounds(280, numero * y, 20, 20);
		// Image imagenBorrar = new Image(d,"imagenes\\borrar.gif");
		bot.setImage(ImageLoader.getImage("borrar.gif"));
		bot.addSelectionListener(new SelectionAdapter() {

		    public void widgetSelected(SelectionEvent event) {
			for (int x = 0; x < textos.length; x += 2) {
			    if (textos[x].getBounds().y == bot.getBounds().y) {
				Text text = (Text) textos[x];
				text.setText("");
			    }
			}
		    }
		});
		text2.addListener(SWT.FocusIn, new Listener() {

		    public void handleEvent(Event e) {
			if (text2.getText().startsWith("Escribe")) {
			    text2.setText("");
			}
		    }
		});
		text2.addKeyListener(new org.eclipse.swt.events.KeyAdapter() {

		    public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
			key.setKey(e);
			if (key.PresentEnter()) {
			    validate(true);
			    dialog.close();
			}
		    }
		});
		textos = composite.getChildren();
		sComposite.setMinSize(composite.computeSize(SWT.DEFAULT,
			SWT.DEFAULT, false));
	    }
	});
	
	sComposite.setContent(composite);
	sComposite.setExpandHorizontal(true);
	sComposite.setExpandVertical(true);
	sComposite.setBounds(0, 20, 345, 120);
    }

    @Override
    public void open() {
	// TODO Auto-generated method stub
	dialog.open();
	while (!dialog.isDisposed()) {
	    if (!display.readAndDispatch()) {
		display.sleep();
	    }
	}
    }

    @Override
    protected void validate(boolean band) {
	// TODO Auto-generated method stub
	int total = 0;
	if(band){
	    textos = composite.getChildren();
	    
	    variables = new String[50];
	    for (int x = 0; x < textos.length; x += 2) {
		Text text = (Text) textos[x];
		String copia = text.getText();
		while (copia.startsWith(" ")) {
		    copia = copia.replaceFirst(" ", "");
		}
		if (!copia.startsWith("Escribe")
			&& copia != "null" && copia != "") {
		    variables[total] = copia;
		    total++;
		}
	    }
	}
	
	boolean cambio = false;
	if (band) {
	    String codigo = "";
	    for (int x = 0; x < total; x++) {
		if (variables[x].startsWith("\\" + "p")) {
		    variables[x] =
			    variables[x]
				    .substring(variables[x].indexOf("\\") + 2);
		}
		codigo = codigo + "\\" + "p" + variables[x] + ";";
	    }
	    if (!abstractFigure.instruccion.instruccion.equals(codigo)) {
		tabbedPaneSelected.getTabItem().getSave().setSave(false);
		tabbedPaneSelected
			.getTabItem()
			.getInfo()
			.setInformacion(
				"/M - Se agrego o modifico una instruccion en una figura de tipo \"salida\"\n");
		cambio = true;
	    }
	    abstractFigure.instruccion.setInstruccionSimple(codigo);
	    tabbedPaneSelected.getHoja().addFigure();
	    tabbedPaneSelected.getHoja().guardarRetroceso();
	    if (cambio) {
		tabbedPaneSelected.getTabItem().getInfo().setDiagrama(
			tabbedPaneSelected.getHoja().getDiagrama());
	    }
	}
    }
    
    public void limpiar() {
	String[] variables2 = new String[50];
	int cont = 0;
	for (int x = 0; x < variables.length; x++) {
	    variables[x] = variables[x].replace("\\", "");
	    variables[x] = variables[x].replaceFirst("p", "");
	    if (variables[x].compareTo("") != 0) {
		variables2[cont] = variables[x];
		cont++;
	    }
	}
	for (int x = 0; x < cont; x++) {
	    if (variables2[x].compareTo("") != 0) {
		variables[x] = variables2[x];
	    }
	}
    }
}