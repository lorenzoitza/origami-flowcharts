package Grafico.Help;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Directorio{
	private TreeItem[] item = new TreeItem[1];
	private int itm;
	
	public void crearContenido(final Tree tree){
		item[0] = new TreeItem(tree, SWT.NULL);	
	    item[0].setText("ORIGAMI");
	    crearSimbolos(tree,item);
	    crearCodigo(tree,item);
	    crearAyuda(tree,item);
	    tree.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event) {
				String string = "";
		        TreeItem[] selection = tree.getSelection();
		        for (int i = 0; i < selection.length; i++){
		        	string += selection[i] + " ";
		        }
		        String limpio = limpiar(string);
		        Contenido simbol = new Contenido();
		        if(limpio.compareTo("ORIGAMI} ")==0){
		        	simbol.crearContenido();
		        	itm=0;
		        }
		        else if(limpio.compareTo("Estructuras/Simbolos} ")==0){
		        	simbol.crearContenidoEstructuras();
		        	itm=1;
		        }
		        else if(limpio.compareTo("Entrada/Salida} ")==0){
		        	simbol.crearContenidoES();
		        	itm=2;
		        }
		        else if(limpio.compareTo("Proceso} ")==0){
		        	simbol.crearContenidoProceso();
		        	itm=3;
		        }
		        else if(limpio.compareTo("If} ")==0){
		        	simbol.crearContenidoIf();
		        	itm=4;
		        }
		        else if(limpio.compareTo("While} ")==0){
		        	simbol.crearContenidoWhile();
		        	itm=5;
		        }
		        else if(limpio.compareTo("For} ")==0){
		        	simbol.crearContenidoFor();
		        	itm=6;
		        }
		        else if(limpio.compareTo("Codigo Fuente} ")==0){
		        	
		        }
		        else if(limpio.compareTo("Tipo de datos} ")==0){
		        	
		        }
		        else if(limpio.compareTo("Declaraciones} ")==0){
		        	
		        }
		        else if(limpio.compareTo("Operadores} ")==0){	
		        }
		        else if(limpio.compareTo("Obtener Ayuda} ")==0){
		        	simbol.crearContenidoAyuda();
		        }
		        else if(limpio.compareTo("Contactanos} ")==0){	
		        	simbol.crearContenidoHistoria();
		        }
		    }
		});
	}
	public void crearSimbolos(Tree tree,TreeItem[] item){
		TreeItem item0 = new TreeItem(item[0], SWT.NULL);
		item0.setText("Estructuras/Simbolos");
		TreeItem item1 = new TreeItem(item0, SWT.NULL);
		item1.setText("Entrada/Salida");
		TreeItem item5 = new TreeItem(item0, SWT.NULL);
		item5.setText("Proceso");
		TreeItem item6 = new TreeItem(item0, SWT.NULL);
		item6.setText("If");
		TreeItem item7 = new TreeItem(item0, SWT.NULL);
		item7.setText("While");
		TreeItem item8 = new TreeItem(item0, SWT.NULL);
		item8.setText("For");
	}
	public void crearCodigo(Tree tree,TreeItem[] item){
		TreeItem item0 = new TreeItem(item[0], SWT.NULL);
		item0.setText("Codigo Fuente");
		TreeItem item1 = new TreeItem(item0, SWT.NULL);
		item1.setText("Tipo de datos");
		TreeItem item2 = new TreeItem(item0, SWT.NULL);
		item2.setText("Declaraciones");
		TreeItem item4 = new TreeItem(item2, SWT.NULL);
		item4.setText("Ventana Entrada/Salida");
		TreeItem item5 = new TreeItem(item2, SWT.NULL);
		item5.setText("Ventana Proceso");
		TreeItem item6 = new TreeItem(item2, SWT.NULL);
		item6.setText("Ventana If");
		TreeItem item7 = new TreeItem(item2, SWT.NULL);
		item7.setText("Ventana While");
		TreeItem item8 = new TreeItem(item2, SWT.NULL);
		item8.setText("Ventana For");
		TreeItem item3 = new TreeItem(item0, SWT.NULL);
		item3.setText("Operadores");
	}
	public void crearCompilar(Tree tree,TreeItem[] item){
		TreeItem item0 = new TreeItem(item[0], SWT.NULL);
		item0.setText("Compilar/Ejecutar");
	}
	public void crearAyuda(Tree tree,TreeItem[] item){
		TreeItem item0 = new TreeItem(item[0], SWT.NULL);
		item0.setText("Obtener Ayuda");
		TreeItem item1 = new TreeItem(item0, SWT.NULL);
		item1.setText("Contactanos");
		TreeItem item2 = new TreeItem(item0, SWT.NULL);
		item2.setText("FAQ");
	}
	public String limpiar(String string){
		String limpio = string.substring(10);
		return limpio;
	}
	public TreeItem[] getItem() {
		return item;
	}
	public int getItm() {
		return itm;
	}
	public void setItm(int itm) {
		this.itm = itm;
	}
}
