package origami.graphics.help;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import origami.graphics.MainWindow;
import origami.images.ImageLoader;

public class HelpWindow{
    private Shell helpShell;
    
    private Directory directory;
    
    private Tree directoryTree;
    
    private Browser browser;
    
    private HelpContents helpContent;
    
    private int treeItemSelection;
    
    private final Color WHITE_COLOR = MainWindow.display.getSystemColor(SWT.COLOR_WHITE);
    
    private final int MAX_TREE_ITEMS = 19;
	
    public HelpWindow(){
	createWindow();
	initComponents();
    }
	
    public void setToolBarBottons(ToolBar bar){
	Button backButton = new Button(bar, SWT.PUSH);
	backButton.setImage(ImageLoader.getImage("back.png"));
	int backButtonWidth = 30;
	int backButtonHeight = 30;
	backButton.setSize(backButtonWidth, backButtonHeight);
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
	int xCoord = 30;
	int yCoord = 0;
	int nextButtonWidth = 30;
	int nextButtonHeight = 30;
	nextButton.setBounds(xCoord, yCoord, nextButtonWidth, nextButtonHeight);    
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
	int itemIndex;
	for (itemIndex = 0; itemIndex < directoryTree.getItemCount(); itemIndex++) {
	    directoryTree.getItem(itemIndex).setExpanded(true);
	    int nestedItemIndex;
	    for (nestedItemIndex = 0; nestedItemIndex < directoryTree.getItem(itemIndex).getItemCount(); 
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
	int width = 1020;
	int height = 30;
	toolBar.setSize(width, height);
	setToolBarBottons(toolBar);
    }
	
    private void initTree(Composite bottomComposite){
	directoryTree = new Tree(bottomComposite, SWT.BORDER);
	directoryTree.setLayoutData(new GridData(GridData.FILL_BOTH));
	directory = new Directory(helpContent);
	directory.crearContenido(directoryTree);
	int xCoord = 0;
	int yCoord = 0;
	int width = 197;
	int height = 678;
	directoryTree.setBounds(xCoord,yCoord,width,height);
    }
	
    private void initBrowser(Composite bottomComposite){
	GridData gridData = new GridData(GridData.FILL_VERTICAL);
	gridData.grabExcessHorizontalSpace = true;
	gridData.grabExcessVerticalSpace = true;
	browser = new Browser(bottomComposite, SWT.NONE);
	browser.setLayoutData(gridData);
	browser.setBackground(WHITE_COLOR);
	int xCoord = 200;
	int yCoord = 0;
	int width = 819;
	int height = 678;
	browser.setBounds(xCoord,yCoord,width,height);
    }
	
    private void setSashProperties(Composite topComposite, final Composite bottomComposite){
	final Sash sash = new Sash (bottomComposite, SWT.VERTICAL);

	int xCoord = 197;
	int yCoord = 0;
	int width = 3;
	int height = 658;
	sash.setBounds(xCoord,yCoord,width,height);
	final FormLayout formLayout = new FormLayout ();
		
	bottomComposite.setLayout(formLayout);
	
	int zeroOffset = 0;
	int zeroNumerator = 0;
	int hundredNumerator = 100;
	int fiftyNumerator = 100;
	FormData treeFormData = new FormData ();
	treeFormData.left = new FormAttachment (zeroNumerator, zeroOffset);
	treeFormData.right = new FormAttachment (sash, zeroOffset);
	treeFormData.top = new FormAttachment (zeroNumerator, zeroOffset);
	treeFormData.bottom = new FormAttachment (hundredNumerator, zeroOffset);
	directoryTree.setLayoutData(treeFormData);
	
	final FormData sashFormData = new FormData ();
	sashFormData.left = new FormAttachment (fiftyNumerator, zeroOffset);
	sashFormData.top = new FormAttachment (zeroNumerator, zeroOffset);
	sashFormData.bottom = new FormAttachment (hundredNumerator, zeroOffset);
	sash.setLayoutData(sashFormData);
	sash.addListener(SWT.Selection,new Listener(){
	    public void handleEvent(Event e){
		int rightOffset = 20;
		int right = (bottomComposite.getClientArea().width - 
			sash.getBounds().width - rightOffset);
		
		e.x = Math.max (Math.min (e.x, right), rightOffset);
		if (e.x != sash.getBounds().x) {
		    sashFormData.left = new FormAttachment (0, e.x);
		    bottomComposite.layout ();
		}
	    }
	});
		
	FormData browserFormData = new FormData ();
	
	browserFormData.left = new FormAttachment (sash, zeroOffset);
	browserFormData.right = new FormAttachment (hundredNumerator, zeroOffset);
	browserFormData.top = new FormAttachment (zeroNumerator, zeroOffset);
	browserFormData.bottom = new FormAttachment (hundredNumerator,zeroOffset);
	browser.setLayoutData(browserFormData);
    }
	
    private void initComponents(){
	ScrolledComposite scrolledComposite = new ScrolledComposite(helpShell, 
		SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	
	Composite topComposite = new Composite(scrolledComposite,SWT.NONE);
	int topCompositeXCoord = 0;
	int topCompositeYCoord = 0;
	int topCompositeWidth = 1020;
	int topCompositeHeight = 708;
	topComposite.setBounds(topCompositeXCoord,topCompositeYCoord,topCompositeWidth,topCompositeHeight);    
	
	final Composite bottomComposite = new Composite(topComposite,SWT.NONE);
	int bottomCompositeXCoord = 0;
	int bottomCompositeYCoord = 30;
	int bottomCompositeWidth = 1020;
	int bottomCompositeHeight = 678;
	bottomComposite.setBounds(bottomCompositeXCoord,bottomCompositeYCoord,bottomCompositeWidth,bottomCompositeHeight);
	
	initToolBar(topComposite);
	
	initBrowser(bottomComposite);
	
	helpContent = new HelpContents(browser);
	
	initTree(bottomComposite);
	
	helpContent.goHelpPage("Origami.html"); 
	setSashProperties(topComposite, bottomComposite);
	scrolledComposite.setContent(topComposite);
    }
	
    public void createWindow(){
	this.helpShell = new Shell(MainWindow.display);
	this.helpShell.setLayout(new FillLayout());
    }
	
    public void showWindow(){
	this.helpShell.setMaximized(true);
	this.helpShell.open();
    }
    
}
