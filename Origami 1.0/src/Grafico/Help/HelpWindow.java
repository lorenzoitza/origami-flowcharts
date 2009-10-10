package Grafico.Help;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import Grafico.Ventana;
import Imagenes.ImageLoader;

public class HelpWindow{
	private Shell helpShell;
	private Directory directory;
	private Tree directoryTree;
	private Browser browser;
	private HelpContents helpContent;
	private int treeItemSelection;
	private final Color WHITE_COLOR = Ventana.display.getSystemColor(
			SWT.COLOR_WHITE);
	private final int MAX_TREE_ITEMS = 19;
	
	public HelpWindow(){
		createWindow();
		initComponents();
	}
	
	public void setToolBarBottons(ToolBar bar){
		Button backButton = new Button(bar, SWT.PUSH);

		backButton.setImage(ImageLoader.getImage("back.png"));
	    backButton.setSize(30, 30);
	    backButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				treeItemSelection=directory.getNumSelectedItem();
				if (treeItemSelection > 0) {
					treeItemSelection--;
				}
				changeBrowserURL();
			}
		});
	    
	    Button nextButton = new Button(bar, SWT.PUSH);
	    
	    nextButton.setImage(ImageLoader.getImage("next.png"));
	    nextButton.setBounds(30, 0, 30, 30);    
	    nextButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				treeItemSelection=directory.getNumSelectedItem();
				if (treeItemSelection < MAX_TREE_ITEMS) {
					treeItemSelection++;
				}
				changeBrowserURL();
			}
		});
	}
	
	private void changeBrowserURL(){
		for (int itemIndex = 0; itemIndex < directoryTree.getItemCount(); itemIndex++) {
			directoryTree.getItem(itemIndex).setExpanded(true);
			for (int nestedItemIndex = 0; 
				nestedItemIndex < directoryTree.getItem(itemIndex).getItemCount(); 
				nestedItemIndex++) {
				directoryTree.getItem(itemIndex).getItem(nestedItemIndex).setExpanded(true);	
			}
		}
		switch(treeItemSelection){
		case 0:
			helpContent.goHelpPage("Origami.html"); 
			break;
		case 2:
			helpContent.goHelpPage("Entrada-Salida.html"); 
			break;
		case 3:
			helpContent.goHelpPage("Proceso.html"); 
			break;
		case 4:
			helpContent.goHelpPage("Decision.html"); 
			break;
		case 5:
			helpContent.goHelpPage("CicloWhile.html");
			break;
		case 6:
			helpContent.goHelpPage("CicloFor.html");
			break;
		case 17:	
		    helpContent.goHelpPage("Contactanos.html");
		    break;
		    
		}
		directory.setNumSelectedItem(treeItemSelection);
	}
	
	private void initToolBar(Composite topComposite){
		ToolBar toolBar = new ToolBar(topComposite,SWT.HORIZONTAL | SWT.FLAT);
		toolBar.setSize(1020,30);
		setToolBarBottons(toolBar);
	}
	
	private void initTree(Composite bottomComposite){
		directoryTree = new Tree(bottomComposite, SWT.BORDER);
	    directoryTree.setLayoutData(new GridData(GridData.FILL_BOTH));
	    directory = new Directory(helpContent);
	    directory.crearContenido(directoryTree);
		directoryTree.setBounds(0,0,197,678);
	}
	
	private void initBrowser(Composite bottomComposite){
		GridData gridData = new GridData(GridData.FILL_VERTICAL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		browser = new Browser(bottomComposite, SWT.NONE);
		browser.setLayoutData(gridData);
		browser.setBackground(WHITE_COLOR);
		browser.setBounds(200,0,819,678);
		
		
	}
	
	private void setSashProperties(Composite topComposite, 
			final Composite bottomComposite){
		final Sash sash = new Sash (bottomComposite, SWT.VERTICAL);
		
		sash.setBounds(197,0,3,658);
		final FormLayout formLayout = new FormLayout ();
		
		bottomComposite.setLayout(formLayout);
		FormData treeFormData = new FormData ();
		
		treeFormData.left = new FormAttachment (0, 0);
		treeFormData.right = new FormAttachment (sash, 0);
		treeFormData.top = new FormAttachment (0, 0);
		treeFormData.bottom = new FormAttachment (100, 0);
		directoryTree.setLayoutData(treeFormData);
		final FormData sashFormData = new FormData ();
		
		sashFormData.left = new FormAttachment (50, 0);
		sashFormData.top = new FormAttachment (0, 0);
		sashFormData.bottom = new FormAttachment (100, 0);
		sash.setLayoutData(sashFormData);
		sash.addListener(SWT.Selection,new Listener(){
			public void handleEvent(Event e){
				int right = (bottomComposite.getClientArea().width - 
						sash.getBounds().width - 20);
				
				e.x = Math.max (Math.min (e.x, right), 20);
				if (e.x != sash.getBounds().x) {
					sashFormData.left = new FormAttachment (0, e.x);
					bottomComposite.layout ();
				}
			}
		});
		
		FormData browserFormData = new FormData ();
		
		browserFormData.left = new FormAttachment (sash, 0);
		browserFormData.right = new FormAttachment (100, 0);
		browserFormData.top = new FormAttachment (0, 0);
		browserFormData.bottom = new 
		FormAttachment (100, 0);
		browser.setLayoutData(browserFormData);
	}
	
	private void initComponents(){
	    ScrolledComposite scrolledComposite = new ScrolledComposite(helpShell, 
	    		SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	    Composite topComposite = new Composite(scrolledComposite,SWT.NONE);
	    
	    topComposite.setBounds(0,0,1020,708);
	    final Composite bottomComposite = new Composite(topComposite,SWT.NONE);
	    
	    bottomComposite.setBounds(0,30,1020,678);
	    initToolBar(topComposite);
		initBrowser(bottomComposite);
		helpContent = new HelpContents(browser);
		initTree(bottomComposite);
		helpContent.goHelpPage("Origami.html"); 
		setSashProperties(topComposite, bottomComposite);
		scrolledComposite.setContent(topComposite);
	}
	
	public void createWindow(){
		this.helpShell = new Shell(Ventana.display);
		this.helpShell.setLayout(new FillLayout());
		
	    
	}
	
	public void showWindow(){
		this.helpShell.setMaximized(true);
	    this.helpShell.open();
	}
}
