package Grafico.Help;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import Grafico.Ventana;
import Imagenes.CargarImagenes;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class VentanaHelp{
	private Tree tree;
	public static Browser browser;
	public Shell shell;
	private int seleccion;
	private Directorio dir;
	
	public void getBotones(ToolBar bar){
		Button b1 = new Button(bar, SWT.PUSH);
	    //Image atras = new Image(Ventana.display, "imagenes\\back.png");
	    b1.setImage(CargarImagenes.getImagen("back.png"));
	    b1.setSize(30,30);
	    b1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				seleccion=dir.getItm();
				if(seleccion>0){
					seleccion--;
				}
				cambiarDir();
			}
		});
	    Button b2 = new Button(bar, SWT.PUSH);
	    //Image adelante = new Image(Ventana.display, "imagenes\\next.png");
	    b2.setImage(CargarImagenes.getImagen("next.png"));
	    b2.setBounds(30,0,30,30);    
	    b2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				seleccion=dir.getItm();
				if(seleccion<19){
					seleccion++;
				}
				cambiarDir();
			}
		});
	}
	public void cambiarDir(){
		for(int x=0;x<tree.getItemCount();x++){
			tree.getItem(x).setExpanded(true);
			for(int y=0;y<tree.getItem(x).getItemCount();y++){
				tree.getItem(x).getItem(y).setExpanded(true);	
			}
		}
		Contenido simbol = new Contenido();
		if(seleccion==0){
        	simbol.crearContenido();
        }
        else if(seleccion==1){
        	simbol.crearContenidoEstructuras();  	
        }
        else if(seleccion==2){
        	simbol.crearContenidoES();
        }
        else if(seleccion==3){
        	simbol.crearContenidoProceso();
        }
        else if(seleccion==4){
        	simbol.crearContenidoIf();
        }
        else if(seleccion==5){
        	simbol.crearContenidoWhile();
        }
        else if(seleccion==6){
        	simbol.crearContenidoFor();
        }
		dir.setItm(seleccion);
	}	
	public void ventana(){
	    shell = new Shell(Ventana.display);
	    shell.setLayout(new FillLayout());
	    Color white = Ventana.display.getSystemColor(SWT.COLOR_WHITE);
	    
	    ScrolledComposite sc = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	    Composite c1 = new Composite(sc,SWT.NONE);
	    c1.setBounds(0,0,1020,708);
	    final Composite c2 = new Composite(c1,SWT.NONE);
	    c2.setBounds(0,30,1020,678);

	    ToolBar bar = new ToolBar (c1,SWT.HORIZONTAL | SWT.FLAT);
		bar.setSize(1020,30);
		getBotones(bar);
		
	    tree = new Tree(c2, SWT.BORDER);
	    tree.setLayoutData(new GridData(GridData.FILL_BOTH));
	    dir = new Directorio();
	    dir.crearContenido(tree);
		tree.setBounds(0,0,197,678);
		GridData grid = new GridData(GridData.FILL_VERTICAL);
		grid.grabExcessHorizontalSpace = true;
		grid.grabExcessVerticalSpace = true;
		browser = new Browser(c2, SWT.NONE);
		browser.setLayoutData(grid);
		browser.setBackground(white);
		browser.setBounds(200,0,819,678);
		
		final Sash sash = new Sash (c2, SWT.VERTICAL);
		sash.setBounds(197,0,3,658);
		final FormLayout form = new FormLayout ();
		c2.setLayout(form);
		FormData button1Data = new FormData ();
		button1Data.left = new FormAttachment (0, 0);
		button1Data.right = new FormAttachment (sash, 0);
		button1Data.top = new FormAttachment (0, 0);
		button1Data.bottom = new FormAttachment (100, 0);
		tree.setLayoutData(button1Data);

		final int limit = 20, percent = 50;
		final FormData sashData = new FormData ();
		sashData.left = new FormAttachment (percent, 0);
		sashData.top = new FormAttachment (0, 0);
		sashData.bottom = new FormAttachment (100, 0);
		sash.setLayoutData (sashData);
		sash.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				Rectangle sashRect = sash.getBounds ();
				Rectangle shellRect = c2.getClientArea ();
				int right = shellRect.width - sashRect.width - limit;
				e.x = Math.max (Math.min (e.x, right), limit);
				if (e.x != sashRect.x) {
					sashData.left = new FormAttachment (0, e.x);
					c2.layout ();
				}
			}
		});
		
		FormData button2Data = new FormData ();
		button2Data.left = new FormAttachment (sash, 0);
		button2Data.right = new FormAttachment (100, 0);
		button2Data.top = new FormAttachment (0, 0);
		button2Data.bottom = new FormAttachment (100, 0);
		browser.setLayoutData(button2Data);
	    
	    sc.setContent(c1);
	    shell.setMaximized(true);
	    shell.open();
	}
}
