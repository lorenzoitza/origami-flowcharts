package origami.graphics.Help42;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

public class Directory{
    private ArrayList<TreeItem> treeItems = new ArrayList<TreeItem>();
	
    private int numSelectedItem;
	
    private HelpContents helpContent;
    
    public Directory(HelpContents helpContent){
	this.helpContent = helpContent;
    }
    
    public void crearContenido(final Tree tree){
	TreeItem treeItem = new TreeItem(tree, SWT.NULL);	
	treeItem.setText("ORIGAMI");
	createSubTreeSimbols(tree,treeItem);
	createSubTreeCode(tree,treeItem);
	createSubTreeHelp(tree,treeItem); 
	tree.addListener(SWT.Selection, new Listener(){
	    public void handleEvent(Event event) {
		String itemText = "";
		TreeItem[] selectedItem = tree.getSelection();
		for (int itemIndex = 0; itemIndex < selectedItem.length; itemIndex++){
		    itemText += selectedItem[itemIndex] + " ";
		}
		int startIndex = 10;
		String subItemText = itemText.substring(startIndex);
		changeBrowserURL(subItemText);
	    }
	});
	treeItems.add(treeItem);
    }
	
    private void changeBrowserURL(String subItemText){
	if(subItemText.compareTo("ORIGAMI} ") == 0){
	    helpContent.goHelpPage("Origami.html");
	    numSelectedItem = 0;
	}else if(subItemText.compareTo("Entrada/Salida} ") == 0){
	    helpContent.goHelpPage("Entrada-Salida.html");
	    numSelectedItem = 2;
	}else if(subItemText.compareTo("Proceso} ") == 0){
	    helpContent.goHelpPage("Proceso.html");
	    numSelectedItem = 3;
	}else if(subItemText.compareTo("If} ") == 0){
	    helpContent.goHelpPage("Decision.html");
	    numSelectedItem = 4;
	}else if(subItemText.compareTo("While} ") == 0){
	    helpContent.goHelpPage("CicloWhile.html");
	    numSelectedItem = 5;
	}else if(subItemText.compareTo("For} ") == 0){
	    helpContent.goHelpPage("CicloFor.html");
	    numSelectedItem= 6;
	}else if(subItemText.compareTo("Contactanos} ") == 0){	
	    helpContent.goHelpPage("Contactanos.html");
	    numSelectedItem= 18;
	}
    }
	
    private void createSubTreeSimbols(Tree tree,TreeItem item){
	TreeItem root = new TreeItem(item, SWT.NULL);
	root.setText("Estructuras/Simbolos");
	
	TreeItem ioItem = new TreeItem(root, SWT.NULL);
	ioItem.setText("Entrada/Salida");
	
	TreeItem processItem = new TreeItem(root, SWT.NULL);
	processItem.setText("Proceso");
	
	TreeItem decisionItem = new TreeItem(root, SWT.NULL);
	decisionItem.setText("If");
	
	TreeItem whileItem = new TreeItem(root, SWT.NULL);
	whileItem.setText("While");
		
	TreeItem forItem = new TreeItem(root, SWT.NULL);
	forItem.setText("For");
    }
	
    private void createSubTreeCode(Tree tree,TreeItem item){
	TreeItem root = new TreeItem(item, SWT.NULL);
	root.setText("Codigo Fuente");
	
	TreeItem dataTipesItem = new TreeItem(root, SWT.NULL);
	dataTipesItem.setText("Tipo de datos");
		
	TreeItem declarationsItem = new TreeItem(root, SWT.NULL);
	declarationsItem.setText("Declaraciones");
	
	TreeItem ioWindowItem = new TreeItem(declarationsItem, SWT.NULL);
	ioWindowItem.setText("Ventana Entrada/Salida");
	
	TreeItem processWindowItem = new TreeItem(declarationsItem, SWT.NULL);
	processWindowItem.setText("Ventana Proceso");
	
	TreeItem decisionWindowItem = new TreeItem(declarationsItem, SWT.NULL);
	decisionWindowItem.setText("Ventana If");
	
	TreeItem whileWindowItem = new TreeItem(declarationsItem, SWT.NULL);
	whileWindowItem.setText("Ventana While");
	
	TreeItem forWindowItem = new TreeItem(declarationsItem, SWT.NULL);
	forWindowItem.setText("Ventana For");
	
	TreeItem operatorItem = new TreeItem(root, SWT.NULL);
	operatorItem.setText("Operadores");
    }
	
    public void createSubTreeCompile(Tree tree,TreeItem item){
	TreeItem root = new TreeItem(item, SWT.NULL);
	root.setText("Compilar/Ejecutar");
    }
	
    public void createSubTreeHelp(Tree tree,TreeItem item){
	TreeItem root = new TreeItem(item, SWT.NULL);
	root.setText("Obtener Ayuda");
	
	TreeItem contactItem = new TreeItem(root, SWT.NULL);
	contactItem.setText("Contactanos");
	
	TreeItem faqItem = new TreeItem(root, SWT.NULL);
	faqItem.setText("FAQ");
    }
	
    public int getNumSelectedItem() {
	return numSelectedItem;
    }
	
    public void setNumSelectedItem(int numSelectedItem) {
	this.numSelectedItem = numSelectedItem;
    }
}
