package Administracion;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import Administracion.Eventos.EventoKey;
import Administracion.Funcionalidad.Guardar;
import Administracion.Funcionalidad.Serializar;
import Grafico.MainWindow;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class TabFolder {
	private CTabFolder tabFolder;
	private int contador = -1;
	private EventoKey key;
	private Hoja hoja;
	public AdminSeleccion selec;
	
	public TabFolder(Display display,AdminSeleccion seleccion){
		selec = seleccion;
		key = new EventoKey(selec,this);
		tabFolder = new CTabFolder(MainWindow._shell,SWT.BORDER | SWT.CLOSE);
		hoja = new Hoja(display,this,selec);
		addTabItem();
		tabFolder.forceFocus();
		tabFolder.pack();
		tabFolder.setCursor(new Cursor(null,SWT.CURSOR_ARROW));
		tabFolder.setSimple(false);
		tabFolder.setTabHeight(24);
		Color title = display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
		Color title2 = display.getSystemColor(SWT.COLOR_TITLE_FOREGROUND);
		Color title3 = display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
		tabFolder.setSelectionForeground(title);
		tabFolder.setSelectionBackground(new Color[]{title2,title3},new int []{100},true);
		tabFolder.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				key.setKey(e);
				key.Accion();
			}
		});
		tabFolder.addSelectionListener(new SelectionAdapter() {
			 public void widgetSelected(SelectionEvent event) {
				 TabItem a = (TabItem)tabFolder.getItem(tabFolder.getSelectionIndex());
				 cambiar(a); 
				 a.getSave().verificarCambio();
			}
		});
		tabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			public void close(CTabFolderEvent event) {
				TabItem a = (TabItem)event.item;
				if(MainWindow._components.eje != null && MainWindow._components.getEnEjecucion()
						&& a.GetId() == MainWindow._components.eje.a.GetId()){
					MainWindow._components.stopEjecucion();
				}
				else if(MainWindow._components.paso != null && MainWindow._components.getEnEjecucion()
						&& a.GetId() == MainWindow._components.paso.a.GetId()){
					MainWindow._components.stopEjecucion();
					MainWindow.getComponentes().disablePasoAPaso(false);
				}
				if(!a.getSave().isSave()){
					a.getSave().GuardarDiagrama(event);
				}
				else{
					Guardar.cerrar(a.GetId());
				}
			}
		});
	}
	public void agregarRetroceso(Vector<Figura> diagrama){
		TabItem panel = (TabItem)getSeleccion();
		panel.agregarRetroceso(diagrama,selec);
	}
	public void retroceder(Vector<AdminDiagrama> diagrama,int pos,int seleccion){
		Hoja hoja = getHoja();
		hoja.resetScrollBar();
		for(int y=hoja.getDiagrama().size()-1;y>0;y--){
			hoja.getDiagrama().removeElementAt(y);
		}
		for(int i=1; i<diagrama.elementAt(pos).diagrama.size(); i++){
			hoja.getDiagrama().add(diagrama.elementAt(pos).diagrama.elementAt(i));
		}
		selec.setFiguraSeleccionada(seleccion);
		hoja.addFigure();
	}
	public final void cambiar(TabItem a){
		selec.setSeleccionDiagrama(a.GetId());
		hoja.cambiarPanel(this);
		hoja.resetScrollBar();
	}
	public int obtenId(TabItem a){
		TabItem tab;
		int id=0;
		for(int x=0;x<tabFolder.getItemCount();x++){
			tab = (TabItem)tabFolder.getItem(x);
			if(tab.GetId()==a.GetId()){
				id=x;
				selec.setSeleccionDiagrama(tab.GetId());
				hoja.cambiarPanel(this);
				return id;
			}
		}
		return 0;
	}
	public void addTabItem(){
		contador++;
		int aux = contador +1;
		TabItem item = new TabItem(tabFolder, SWT.NONE);
		item.setTabFolder(this);
		item.setText("Diagrama "+ aux);
		item.SetId(contador);
		selec.setSeleccionDiagrama(contador);
		hoja.addDiagrama();
		seleccionarTabItem();
		hoja.cambiarPanel(this);
		item.addInfo();
	}
	private void seleccionarTabItem(){
		tabFolder.setSelection(tabFolder.getItemCount()-1);
	}
	public void setSeleccion(int i){
		tabFolder.setSelection(i);
	}
	public void cambiarNombre(String name){
		if(tabFolder.getSelectionIndex()!=-1){
		TabItem item = (TabItem)tabFolder.getItem(tabFolder.getSelectionIndex());
		item.setText(name);
		}
		else{
			contador++;
			TabItem item = new TabItem(tabFolder, SWT.NONE);
			item.setTabFolder(this);
			item.SetId(contador);
			item.setText(name);
			selec.setSeleccionDiagrama(contador);
			selec.setFiguraSeleccionada(0);
			seleccionarTabItem();
		}
	}
	public TabItem getTabItem(){
		TabItem item = (TabItem)tabFolder.getItem(tabFolder.getSelectionIndex());
		return item;
	}
	public String getNombre(){
		TabItem item = (TabItem)tabFolder.getItem(tabFolder.getSelectionIndex());
		return item.getText();
	}
	public Hoja getHoja(){
		return hoja;
	}
	public void abrir(String archivo,Serializar ser ){
		hoja.openNewFile(archivo,ser);
		setSeleccion(0);
		selec.setFiguraSeleccionada(-1);	
	}
	public int getSelectedTabItemId(){
		TabItem a = (TabItem)tabFolder.getItem(tabFolder.getSelectionIndex());
		return 	a.GetId();
	}
	public int getindexSeleccionTabfolder(){
		return tabFolder.getSelectionIndex();
	}
	public int getItemCount(){
		return tabFolder.getItemCount();
	}
	public CTabItem getSeleccion(){
		return tabFolder.getSelection();
	}
	public CTabFolder getTabFolder() {
		return tabFolder;
	}
}
