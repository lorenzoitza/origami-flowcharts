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

	numHorizComponents=2;
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
	informationLabel = new Label(dialog, SWT.HORIZONTAL);
	informationLabel.setText("AGREGAR UNA VARIABLE MÁS");
	informationLabel.setSize(150, 30);
	informationLabel.setLocation(165, 155);
	
	information2Label = new Label(dialog, SWT.HORIZONTAL);
	information2Label.setText("ESCRIBE LAS VARIABLES DESEADAS");
	information2Label.setSize(180, 30);
	information2Label.setLocation(5, 5);
	
	exampleLabel = new Label(dialog, SWT.HORIZONTAL);
	exampleLabel.setText("EJEMPLO: \"El valor de x es:\", x");
	exampleLabel.setSize(180, 40);
	exampleLabel.setLocation(195, 5);
    }
    @Override
    public void initTextFields() {
	if (abstractFigure.instruccion.getInstruccionSimple().compareTo("null") != 0
		&& abstractFigure.instruccion.getInstruccionSimple().compareTo("") != 0) {
	    variables = abstractFigure.instruccion.getInstruccionSimple().split(";");
	    cleanTextField();
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
		    addTextComponent(i);
		}
		textos = composite.getChildren();
		Text text = (Text) textos[cont * 2];
		text.forceFocus();
	    } else {
		addTextComponent(composite.getChildren().length / numHorizComponents);
		
		textos = composite.getChildren();
		Text text = (Text) textos[textos.length - numHorizComponents];
		text.forceFocus();
	    }
	} else {
	    for (int i = 0; i <= 3; i++) {
		addTextComponent(i);
		
		textos = composite.getChildren();
	    }
	}
    }
    @Override
    protected void initScrollComposite(){
	GridData grid = new GridData(GridData.FILL_VERTICAL);
	grid.grabExcessHorizontalSpace = true;
	grid.grabExcessVerticalSpace = true;
	sComposite.setLayoutData(grid);
	composite.setLayoutData(grid);

	sComposite.setContent(composite);
	sComposite.setExpandHorizontal(true);
	sComposite.setExpandVertical(true);
	sComposite.setBounds(0, 20, 345, 120);
	
	sComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT,
		false));
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
    private void cleanTextField() {
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
    @Override
    protected void addTextComponent(int position) {
	addTextField(position);
	
	addDeleteButton(position);
    }
}