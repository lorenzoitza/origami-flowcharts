package Grafico.VentanaDatos;

//cursores de seleccionar feos
//el cursor cuesta trabajo seleleccionar botones
//mejorar ventanas de compilacion
//quitar seleccionar figura cuando el cursor es figura
//ventana donate
//bounds para resoluciones mas grandes

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.Eventos.EventoKey;
import Grafico.Ventana;
import Grafico.Figuras.Entrada;
import Imagenes.CargarImagenes;

/**
 * Crea la ventana para introducir los datos de una figura de entrada.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class DatosEntrada {
	private Shell shell;
	private Entrada fig;
	private Control[] textos;
	private String[] variables = new String[50];
	public EventoKey key;
	public TabFolder tab;
	
	public DatosEntrada(TabFolder tabfolder){
		tab = tabfolder;
	}
	/**
	 * Crea la ventana junto con sus componentes.
	 * @param d
	 * @param fig
	 */
	public void ventana(final Display d, Entrada fig,AdminSeleccion selec) {
		key = new EventoKey(selec,tab);
		shell = new Shell(Ventana.shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(370, 214);
		shell.setText("Datos Entrada");
		shell.setLocation(380, 230);
		this.fig = fig;
		
	    final ScrolledComposite sComposite = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	    GridData grid = new GridData(GridData.FILL_VERTICAL);
	    grid.grabExcessHorizontalSpace = true;
	    grid.grabExcessVerticalSpace = true;
	    sComposite.setLayoutData(grid);
	    final Composite composite = new Composite(sComposite, SWT.NONE);
	    composite.setLayoutData(grid);
	    cargarCodigo(fig,composite,d);
	    sComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT,false));
	    
		Label label = new Label(shell,SWT.HORIZONTAL);
		label.setText("AGREGAR UNA VARIABLE MÁS");
		label.setSize(150,30);
		label.setLocation(165,155);
		Label label2 = new Label(shell,SWT.HORIZONTAL);
		label2.setText("ESCRIBE LAS VARIABLES DESEADAS");
		label2.setSize(180,30);
		label2.setLocation(20,5);
		Label label3 = new Label(shell,SWT.HORIZONTAL);
		label3.setText("EJEMPLO:   int x");
		label3.setSize(180,30);
		label3.setLocation(220,5);
		
		Button button = new Button(shell, SWT.PUSH);
	    button.setSize(45,40);
	    button.setLocation(318,140);
	    //Image imagenSuma = new Image(d, "imagenes\\suma.png");
	    button.setImage(CargarImagenes.getImagen("suma.png"));
		button.pack();
	    button.addSelectionListener(new SelectionAdapter() {
	    	public void widgetSelected(SelectionEvent event){
	    		textos = composite.getChildren();
	    		int numero = textos.length/3;
	    		int y = 25;
	    		final Text text2 = new Text(composite, SWT.FLAT | SWT.BORDER);
	    		text2.setBounds(0,numero*y,250,20);
	    		text2.setText("Escribe aqui");
	    		final Button botLeer = new Button(composite, SWT.PUSH);
	    		botLeer.setBounds(255, numero*y, 20,20);
	    		//Image imagen = new Image(d, "imagenes\\teclado.ico");
	    		botLeer.setImage(CargarImagenes.getImagen("teclado.ico"));
	    		botLeer.addSelectionListener(new SelectionAdapter() {
	    	    	public void widgetSelected(SelectionEvent event){
	    	    		for(int x=0;x<textos.length;x+=3){
	    	    			if(textos[x].getBounds().y == botLeer.getBounds().y){
	    	    				Text text = (Text)textos[x];
	    	    				String texto = limpiarString(text.getText());
	    	    				text.setText(texto);
	    	    				text.setSelection(texto.length());
	    	    				text.setFocus();	
	    	    			}
	    	    		}
	    	    		
	    	    	}
	    		});
	    		final Button bot = new Button(composite, SWT.PUSH);
	    		bot.setBounds(280, numero*y, 20,20);
	    		//Image imagenBorrar = new Image(d,"imagenes\\borrar.gif");
	    		bot.setImage(CargarImagenes.getImagen("borrar.gif"));
	    		bot.addSelectionListener(new SelectionAdapter() {
	    	    	public void widgetSelected(SelectionEvent event){
	    	    		for(int x=0;x<textos.length;x+=3){
	    	    			if(textos[x].getBounds().y == bot.getBounds().y){
	    	    				Text text = (Text)textos[x];
	    	    				text.setText("");
	    	    			}
	    	    		}
	    	    	}
	    		});
		    	text2.addListener(SWT.FocusIn, new Listener() {
			    	public void handleEvent(Event e) {
			    		if(text2.getText().startsWith("Escribe")){
			    			text2.setText("");
			    		}
			        }
			    });
		    	text2.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
					public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
						key.setKey(e);
						if(key.PresentEnter()){
							textos = composite.getChildren();
							int total=0;
							variables = new String[50];
							for(int x=0;x<textos.length;x+=3){
								Text text = (Text)textos[x];
								String copia = text.getText();
								while(copia.startsWith(" ")){
									copia = copia.replaceFirst(" ","");
								}
								if(!copia.startsWith("Escribe") && copia != "null" && copia != "") {
									variables[total] = copia;
									total++;
								}
							}
							guardarCodigo(true,total);
							shell.close();
						}
					}
				}); 
		    	textos = composite.getChildren();
			    sComposite.setMinSize(composite.computeSize(SWT.DEFAULT,SWT.DEFAULT,false));
			}
		});
	    
		Button boton = new Button(shell,SWT.FLAT);
		boton.setBounds(5,145,70,25);
		boton.setText("ACEPTAR");
		boton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				textos = composite.getChildren();
				int total=0;
				variables = new String[50];
				for(int x=0;x<textos.length;x+=3){
					Text text = (Text)textos[x];
					String copia = text.getText();
					while(copia.startsWith(" ")){
						copia = copia.replaceFirst(" ","");
					}
					if(!copia.startsWith("Escribe") && copia != "null" && copia != "") {
						if(copia.startsWith("Leer:") || copia.startsWith("leer:")){
							String copia2 = copia.substring(5);
							while(copia2.startsWith(" ")){
								copia2 = copia2.replaceFirst(" ","");
							}
							if(copia2.length()>0){
								copia = "Leer: " + copia2;
								variables[total] = copia;
								total++;
							}
						}
						else{
							variables[total] = copia;
							total++;
						}
					}
				}
				guardarCodigo(true,total);
				shell.close();
			}
		});
		Button boton2 = new Button(shell,SWT.FLAT);
		boton2.setBounds(85,145,70,25);
		boton2.setText("CANCELAR");
		boton2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				guardarCodigo(false,0);
				shell.close();
			}
		});
		sComposite.setContent(composite);
	    sComposite.setExpandHorizontal(true);
	    sComposite.setExpandVertical(true);
	    sComposite.setBounds(0,20,325,120);
		shell.open();
		while(!shell.isDisposed()){
			if(!d.readAndDispatch())
				d.sleep();
		}
	}
	public void cargarCodigo(Entrada fig,final Composite composite,final Display d){
		if(fig.instruccion.getInstruccionSimple().compareTo("null") != 0 && fig.instruccion.getInstruccionSimple().compareTo("") != 0){
	    	variables = fig.instruccion.getInstruccionSimple().split(";");
	    	for(int i = 0; i < variables.length; i++){
	    		textos = composite.getChildren();
	    		Text text = new Text(composite, SWT.FLAT | SWT.BORDER);
	    		text.setBounds(0,0+i*25,250,20);
	    		text.setText(variables[i]);
	    		final Button botLeer = new Button(composite, SWT.PUSH);
	    		//Image imagen = new Image(d, "imagenes\\teclado.ico");
	    		botLeer.setImage(CargarImagenes.getImagen("teclado.ico"));
	    		botLeer.setBounds(255, i*25, 20,20);
	    		botLeer.addSelectionListener(new SelectionAdapter() {
	    	    	public void widgetSelected(SelectionEvent event){
	    	    		for(int x=0;x<textos.length;x+=3){
	    	    			if(textos[x].getBounds().y == botLeer.getBounds().y){
	    	    				Text text = (Text)textos[x];
	    	    				String texto = limpiarString(text.getText());
	    	    				text.setText(texto);
	    	    				text.setSelection(texto.length());
	    	    				text.setFocus();
	    	    			}
	    	    		}
	    	    	}
	    		});
	    		final Button bot = new Button(composite, SWT.PUSH);
	    		bot.setBounds(280, i*25, 20,20);
	    		//Image imagenBorrar = new Image(d,"imagenes\\borrar.gif");
	    		bot.setImage(CargarImagenes.getImagen("borrar.gif"));
	    		bot.addSelectionListener(new SelectionAdapter() {
	    	    	public void widgetSelected(SelectionEvent event){
	    	    		for(int x=0;x<textos.length;x+=3){
	    	    			if(textos[x].getBounds().y == bot.getBounds().y){
	    	    				Text text = (Text)textos[x];
	    	    				text.setText("");
	    	    			}
	    	    		}
	    	    	}
	    		});
	    		text.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
					public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
						key.setKey(e);
						if(key.PresentEnter()){
							textos = composite.getChildren();
							int total=0;
							variables = new String[50];
							for(int x=0;x<textos.length;x+=3){
								Text text = (Text)textos[x];
								String copia = text.getText();
								while(copia.startsWith(" ")){
									copia = copia.replaceFirst(" ","");
								}
								if(!copia.startsWith("Escribe") && copia != "null" && copia != "") {
									variables[total] = copia;
									total++;
								}
							}
							guardarCodigo(true,total);
							shell.close();
						}
					}
				}); 
		    }
	    	if(variables.length<4){
	    		int i;
	    		for (i = variables.length; i < 4; i++) {	    			
	    			textos = composite.getChildren();
	    			final Text text2 = new Text(composite, SWT.FLAT | SWT.BORDER);
	    			text2.setBounds(0,0+i*25,250,20);
	    			text2.setText("Escribe aqui");
	    			final Button botLeer = new Button(composite, SWT.PUSH);
		    		botLeer.setBounds(255, i*25, 20,20);
		    		//Image imagen = new Image(d, "imagenes\\teclado.ico");
		    		botLeer.setImage(CargarImagenes.getImagen("teclado.ico"));
		    		botLeer.addSelectionListener(new SelectionAdapter() {
		    	    	public void widgetSelected(SelectionEvent event){
		    	    		for(int x=0;x<textos.length;x+=3){
		    	    			if(textos[x].getBounds().y == botLeer.getBounds().y){
		    	    				Text text = (Text)textos[x];
		    	    				String texto = limpiarString(text.getText());
		    	    				text.setText(texto);
		    	    				text.setSelection(texto.length());
		    	    				text.setFocus();
		    	    			}
		    	    		}
		    	    	}
		    		});
	    			final Button bot = new Button(composite, SWT.PUSH);
		    		bot.setBounds(280, i*25, 20,20);
		    		//Image imagenBorrar = new Image(d,"imagenes\\borrar.gif");
		    		bot.setImage(CargarImagenes.getImagen("borrar.gif"));
		    		bot.addSelectionListener(new SelectionAdapter() {
		    	    	public void widgetSelected(SelectionEvent event){
		    	    		for(int x=0;x<textos.length;x+=3){
		    	    			if(textos[x].getBounds().y == bot.getBounds().y){
		    	    				Text text = (Text)textos[x];
		    	    				text.setText("");
		    	    			}
		    	    		}
		    	    	}
		    		});
			    	text2.addListener(SWT.FocusIn, new Listener() {
				    	public void handleEvent(Event e) {
				    		if(text2.getText().startsWith("Escribe")){
				    			text2.setText("");
				    		}
				    	}
				    });
			    	text2.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
						public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
							key.setKey(e);
							if(key.PresentEnter()){
								textos = composite.getChildren();
								int total=0;
								variables = new String[50];
								for(int x=0;x<textos.length;x+=3){
									Text text = (Text)textos[x];
									String copia = text.getText();
									while(copia.startsWith(" ")){
										copia = copia.replaceFirst(" ","");
									}
									if(!copia.startsWith("Escribe") && copia != "null" && copia != "") {
										variables[total] = copia;
										total++;
									}
								}
								guardarCodigo(true,total);
								shell.close();
							}
						}
					}); 
			    }
	    		textos = composite.getChildren();
			    final Text text = (Text)textos[variables.length*3];
			    text.forceFocus();
	    	}
	    	else{
	    		textos = composite.getChildren();
	    		final Text text2 = new Text(composite, SWT.FLAT | SWT.BORDER);
    			text2.setBounds(0,0+textos.length/3 *25,250,20);
    			text2.setText("Escribe aqui");
    			final Button botLeer = new Button(composite, SWT.PUSH);
	    		botLeer.setBounds(255, textos.length/3 *25, 20,20);
	    		//Image imagen = new Image(d, "imagenes\\teclado.ico");
	    		botLeer.setImage(CargarImagenes.getImagen("teclado.ico"));
	    		botLeer.addSelectionListener(new SelectionAdapter() {
	    	    	public void widgetSelected(SelectionEvent event){
	    	    		for(int x=0;x<textos.length;x+=3){
	    	    			if(textos[x].getBounds().y == botLeer.getBounds().y){
	    	    				Text text = (Text)textos[x];
	    	    				String texto = limpiarString(text.getText());
	    	    				text.setText(texto);
	    	    				text.setSelection(texto.length());
	    	    				text.setFocus();
	    	    			}
	    	    		}
	    	    	}
	    		});
    			final Button bot = new Button(composite, SWT.PUSH);
	    		bot.setBounds(280, textos.length/3 *25, 20,20);
	    		//Image imagenBorrar = new Image(d,"imagenes\\borrar.gif");
	    		bot.setImage(CargarImagenes.getImagen("borrar.gif"));
	    		bot.addSelectionListener(new SelectionAdapter() {
	    	    	public void widgetSelected(SelectionEvent event){
	    	    		for(int x=0;x<textos.length;x+=3){
	    	    			if(textos[x].getBounds().y == bot.getBounds().y){
	    	    				Text text = (Text)textos[x];
	    	    				text.setText("");
	    	    			}
	    	    		}
	    	    	}
	    		});
	    		text2.addListener(SWT.FocusIn, new Listener() {
			    	public void handleEvent(Event e) {
			    		if(text2.getText().startsWith("Escribe")){
			    			text2.setText("");
			    		}
			    	}
			    });
		    	text2.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
					public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
						key.setKey(e);
						if(key.PresentEnter()){
							textos = composite.getChildren();
							int total=0;
							variables = new String[50];
							for(int x=0;x<textos.length;x+=3){
								Text text = (Text)textos[x];
								String copia = text.getText();
								while(copia.startsWith(" ")){
									copia = copia.replaceFirst(" ","");
								}
								if(!copia.startsWith("Escribe") && copia != "null" && copia != "") {
									variables[total] = copia;
									total++;
								}
							}
							guardarCodigo(true,total);
							shell.close();
						}
					}
				}); 
	    		textos = composite.getChildren();
			    final Text text = (Text)textos[textos.length-3];
			    text.forceFocus();
		    }
	    }
	    else{
	    	for (int i = 0; i <= 3; i++) {
	    		textos = composite.getChildren();
	    		final Text text = new Text(composite, SWT.BORDER);
	    		text.setBounds(0,0+i*25,250,20);
	    		text.setText("Escribe aqui");
	    		final Button botLeer = new Button(composite, SWT.PUSH);
	    		botLeer.setBounds(255, i*25, 20,20);
	    		//Image imagen = new Image(d, "imagenes\\teclado.ico");
	    		botLeer.setImage(CargarImagenes.getImagen("teclado.ico"));
	    		botLeer.addSelectionListener(new SelectionAdapter() {
	    	    	public void widgetSelected(SelectionEvent event){
	    	    		for(int x=0;x<textos.length;x+=3){
	    	    			if(textos[x].getBounds().y == botLeer.getBounds().y){
	    	    				Text text = (Text)textos[x];
	    	    				String texto = limpiarString(text.getText());
	    	    				text.setText(texto);
	    	    				text.setSelection(texto.length());
	    	    				text.setFocus();
	    	    			}
	    	    		}
	    	    	}
	    		});
	    		final Button bot = new Button(composite, SWT.PUSH);
	    		bot.setBounds(280, i*25, 20,20);
	    		//Image imagenBorrar = new Image(d,"imagenes\\borrar.gif");
	    		bot.setImage(CargarImagenes.getImagen("borrar.gif"));
	    		bot.addSelectionListener(new SelectionAdapter() {
	    	    	public void widgetSelected(SelectionEvent event){
	    	    		textos = composite.getChildren();
	    	    		for(int x=0;x<textos.length;x+=3){
	    	    			if(textos[x].getBounds().y == bot.getBounds().y){
	    	    				Text text = (Text)textos[x];
	    	    				text.setText("");
	    	    			}
	    	    		}
	    	    	}
	    		});
	    		text.addListener(SWT.FocusIn, new Listener() {
			    	public void handleEvent(Event e) {
			    		if(text.getText().startsWith("Escribe")){
			    			text.setText("");
			    		}
			    	}
			    });
	    		text.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
					public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
						key.setKey(e);
						if(key.PresentEnter()){
							textos = composite.getChildren();
							int total=0;
							variables = new String[50];
							for(int x=0;x<textos.length;x+=3){
								Text text = (Text)textos[x];
								String copia = text.getText();
								while(copia.startsWith(" ")){
									copia = copia.replaceFirst(" ","");
								}
								if(!copia.startsWith("Escribe") && copia != "null" && copia != "") {
									variables[total] = copia;
									total++;
								}
							}
							guardarCodigo(true,total);
							shell.close();
						}
					}
				}); 
	    		textos = composite.getChildren();
		    }
	    }
	}
	public String limpiarString(String texto){
		while(texto.startsWith(" ")){
			texto = texto.replaceFirst(" ","");
		}
		if(!texto.startsWith("Leer:") || (texto.compareTo("")==0)){
			if(texto.startsWith("Escribe")){
				texto = "Leer: ";
			}
			else{
				texto = "Leer: " + texto;
			}
		}
		return texto;
	}
	/**
	 * Este metodo es el encargado de validar la informacion introducida.
	 * @param mostrar
	 */
	public void guardarCodigo(boolean mostrar,int total){
		boolean cambio=false;
		if(mostrar){
			String codigo = "";
			for(int x=0;x<total;x++){
				codigo = codigo + variables[x] + ";";
			}
			if(!fig.instruccion.instruccion.equals(codigo)){
				tab.getTabItem().getSave().setSave(false);
				tab.getTabItem().getInfo().setInformacion("/M - Se agrego o modifico una instruccion en una figura de tipo \"entrada\"\n");
				cambio=true;
			}
			fig.instruccion.setInstruccionSimple(codigo);
			tab.getHoja().addFigure();
			tab.getHoja().guardarRetroceso();
			if(cambio){
				tab.getTabItem().getInfo().setDiagrama(tab.getHoja().getDiagrama());
			}
		}
	}
}