package Administracion.Funcionalidad;

import java.util.LinkedList;
import java.util.Vector;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.*;

import ui.listener.KeyEvent;

import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.TabItem;
import Administracion.Funcionalidad.Codigo.Instruccion;
import Administracion.actions.RecorridoDiagrama;
import Grafico.MainWindow;
import Grafico.Figuras.*;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class PasoAPaso extends Ejecutar{
	public TabFolder tab;
	public TabItem a;
	public int contador = 0;
	public int figuraAnterior = 0;
	private int contCiclos=0;
	public int conexion=0;
	public LinkedList<Integer> colaConexiones= new LinkedList<Integer>();
	private LinkedList<Integer> colaDiagramas = new LinkedList<Integer>();
	private LinkedList<Integer> colaPintaFinIf= new LinkedList<Integer>();
	private LinkedList<Integer> colaFinDiagramas = new LinkedList<Integer>();
	private Vector<Integer> NoEstaIf = new Vector<Integer>();
	private Vector<Integer> NoEstaWhile = new Vector<Integer>();
	private Vector<String> variablesEntrada = new Vector<String>();
	public boolean ventanaLeer;
	
	public PasoAPaso(TabFolder tabfolder, AdminSeleccion seleccion){
		tab = tabfolder;
		a = (TabItem)tab.getSeleccion();
	}
	public void main(){
		figuraAnterior = 0;
		contador=0;
		contCiclos=0;
		conexion=0;
		colaConexiones.clear();
		colaDiagramas.clear();
		colaFinDiagramas.clear();
		inputActionPerformed("file main");
		inputActionPerformed("break 1");
		inputActionPerformed("run");
		recorrido();
		MainWindow.getComponents().paso = this;
		tab.getHoja().paso(0);
	}
	public String texto(String texto){
		String salida= "";
		String[] lineas = texto.split("\n");
		CharSequence impresion = ".,.";
		CharSequence endl = "<<endl;";
		String lineaNumero = "";
		if(contador>3){
			for(int x=0;x<lineas.length;x++){
				while(lineas[x].startsWith(" ")){
					lineas[x] = lineas[x].substring(1);
				}
				if(lineas[x].startsWith("(gdb)")){
					lineas[x] = lineas[x].substring(5);
				}
				if(lineas[x].contains(impresion) && !lineas[x].contains(endl)){
					for(int i=0;i<4;i++){
						int length = lineas[x].length();
						lineas[x] = lineas[x].substring(0,length-1);
					}
					salida = salida + lineas[x];
				}
				else if(lineas[x].compareTo(" ") != 0 && lineas[x].compareTo("") != 0 && lineas[x].compareTo("\n") != 0){
					String[] numero = lineas[x].split("\t");
					lineaNumero = numero[0];
				}
			}
			texto="";
		}
		else{
			if(texto.indexOf(".cpp:4")!=-1){
				contador=4;
			}
			texto="";
		}
		if(lineaNumero.compareTo("")!=0 ){
			while(lineaNumero.endsWith(" ")){
				int length = lineaNumero.length();
				lineaNumero = lineaNumero.substring(0,length-1);
			}
			if(verificar(lineaNumero)){
	    		sendNext();
			}
			else{
				int id=tab.obtenId(a);
				tab.setSeleccion(id);
				if(verificarLectura(Integer.parseInt(lineaNumero))){
					limpiarPasoAnterior();
					pasoApaso(Integer.parseInt(lineaNumero));
				}
			}
		}
		if(salida.compareTo("")!=0){
			return salida;
		}
		else{
			return texto;
		}
	}
	public boolean verificar(String aux){
		int linea = -1;
		try{
			linea = Integer.parseInt(aux);
    	}catch(NumberFormatException ex){
        	return true;
    	}
    	for(int i=0; i<NoEstaIf.size(); i++){
    		if(linea==NoEstaIf.elementAt(i)){
    			colaFinDiagramas.add(linea);
    			return true;
    		}
    	}
    	for(int i=0; i<NoEstaWhile.size(); i++){
    		if(linea==NoEstaWhile.elementAt(i)){
    			return true;
    		}
    	}
    	return false;
	}
	public void sendNext(){
		inputActionPerformed("next");
	}
	public void limpiarPasoAnterior(){
		if(colaPintaFinIf.size()>0){
			for(int i=0; i<colaPintaFinIf.size(); i++){
				tab.getHoja().getDiagrama().elementAt(i).setPasoAPaso(false);
			}
		}
		for(int i=0; i<tab.getHoja().getSizeDiagrama(); i++){
			tab.getHoja().getDiagrama().elementAt(i).setPasoAPaso(false);
		}
	}
	public void ventanaLeer(){
		final Shell capturar = new Shell(MainWindow.shell);
		final Text text = new Text(capturar, SWT.BORDER | SWT.MULTI);
		text.setBounds(10,10,255,110);
		text.addKeyListener(new org.eclipse.swt.events.KeyAdapter(){ 
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				KeyEvent key = new KeyEvent();
				key.setKey(e);
				if(key.PresentEnter()){
					inputActionPerformed("next");
					inputActionPerformed(text.getText());
					capturar.close();
					ventanaLeer = false;
				}
			}
		}); 
		Button aceptar = new Button(capturar,SWT.FLAT);
		aceptar.setText("Aceptar ");
		aceptar.setBounds(40,130,80,30);
		aceptar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				inputActionPerformed("next");
				inputActionPerformed(text.getText());
				capturar.close();
				ventanaLeer = false;
			}
		});
		Button cancelar = new Button(capturar,SWT.FLAT);
		cancelar.setText("Cancelar ");
		cancelar.setBounds(150,130,80,30);
		cancelar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				capturar.close();
				ventanaLeer = true;
			}
		});
		capturar.addShellListener(new ShellAdapter(){
			public void shellClosed(ShellEvent e){
				ventanaLeer = true;
			}
		}); 
		capturar.setBounds(100,150,280,200);
		capturar.setText("Entrada de datos por teclado");
		capturar.open();
	}
	public boolean verificarLectura(int linea){
		Instruccion codigo = new Instruccion();
		codigo.generateGDB(tab.getHoja().getDiagrama());
		String auxCodigo[] = codigo.totalCode.split("\n");
		for(int i=0; i<auxCodigo.length; i++){
			if(i+1==linea){
				if(auxCodigo[i].indexOf("cin>>")!=-1){
					ventanaLeer();
					return false;
				}
			}
		}
		return true;
	}
	public void pasoApaso(int linea){
		colaConexiones.clear();
		Color blue = MainWindow.display.getSystemColor(SWT.COLOR_RED);
		int i=0;
		tab.getHoja().getDiagrama().elementAt(figuraAnterior).setPasoAPaso(false);
		for(int figura=0; figura<tab.getHoja().getDiagrama().size(); figura++){
			for(int lineass=0; lineass<tab.getHoja().getDiagrama().elementAt(figura).getPosicion().size(); lineass++){
				if(tab.getHoja().getDiagrama().elementAt(figura).getPosicion().elementAt(lineass)==linea){
					i=figura;
				}
			}
		}
		if(figuraAnterior==i){
			sendNext();							
		}
		else{
			if(tab.getHoja().getDiagrama().elementAt(figuraAnterior) instanceof DecisionFigure){
				if(!revisaFin(i)){
					tab.getHoja().paso(i);
					int fig=RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),figuraAnterior))+1;
					//IF VACIO
					if(i>fig || (i<figuraAnterior && (tab.getHoja().getDiagrama().elementAt(i) instanceof WhileFigure || tab.getHoja().getDiagrama().elementAt(i) instanceof ForFigure))){
						colaDiagramas.push(figuraAnterior);
						if(colaFinDiagramas.size()!=0 && tab.getHoja().getDiagrama().elementAt(figuraAnterior).getPosicion().elementAt(0)+1==colaFinDiagramas.get(0)){
							colaConexiones.add(conexion);
							tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
							colaConexiones.add(conexion);
							tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
							fig=RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),figuraAnterior)-2;
							dibujaFinIf(fig,blue);
						}
						else{
							obtieneContCiclos(fig,fig);	
							conexion=fig-4+contCiclos;
							colaConexiones.add(conexion);
							tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
							colaConexiones.add(conexion);
							tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
							dibujaFinIf(fig-2,blue);
						}
						if((i<figuraAnterior) && (tab.getHoja().getDiagrama().elementAt(i) instanceof WhileFigure || tab.getHoja().getDiagrama().elementAt(i) instanceof ForFigure)){
							colaConexiones.add(conexion);
							tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
							colaConexiones.add(conexion);
							tab.getHoja().getConexion().getConexion().elementAt((conexion++)).setForegroundColor(blue);
							colaConexiones.add(conexion);
							tab.getHoja().getConexion().getConexion().elementAt((conexion++)).setForegroundColor(blue);
						}
						contCiclos++;
					}//INICIO DE IF CON ELEMENTOS
					else{
						if(i!=figuraAnterior+2){
							obtieneContCiclos(i,i);	
							conexion=i-2+contCiclos;
						}
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
						colaDiagramas.push(figuraAnterior);
					}
					setScroll(i);
				}
			}
			else{
				if(!revisaFin(i)){
					tab.getHoja().paso(i);
					if(colaDiagramas.size()!=0 && ((figuraAnterior == RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst())-2)
							|| (figuraAnterior == RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst()))-1))){
						int fig=figuraAnterior;//LADO DERECHO
						if(figuraAnterior == RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst())-2){
							fig=RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst())-2;
							colaConexiones.add(conexion);
							tab.getHoja().getConexion().getConexion().elementAt(conexion).setForegroundColor(blue);
						}//LADO IZQUIERDO
						else if((figuraAnterior == RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst()))-1)){							
							fig=RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst()))-1;
							colaConexiones.add(conexion);
							tab.getHoja().getConexion().getConexion().elementAt(conexion).setForegroundColor(blue);
						}
						dibujaFinIf(fig,blue);
						if((i<figuraAnterior) && (tab.getHoja().getDiagrama().elementAt(i) instanceof WhileFigure || tab.getHoja().getDiagrama().elementAt(i) instanceof ForFigure)){
							colaConexiones.add(conexion);
							tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
							colaConexiones.add(conexion);
							tab.getHoja().getConexion().getConexion().elementAt((conexion++)).setForegroundColor(blue);
							colaConexiones.add(conexion);
							tab.getHoja().getConexion().getConexion().elementAt((conexion++)).setForegroundColor(blue);
						}
						contCiclos++;
					}//CUANDO SALE DE UN CICLO FOR O WHILE Y REGRESA EN UN CICLO FOR O WHILE (ANIDADO)
					else if((tab.getHoja().getDiagrama().elementAt(figuraAnterior) instanceof ForFigure || tab.getHoja().getDiagrama().elementAt(figuraAnterior) instanceof WhileFigure) && 
							(tab.getHoja().getDiagrama().elementAt(i) instanceof ForFigure || tab.getHoja().getDiagrama().elementAt(i) instanceof WhileFigure) && i<figuraAnterior){
						int lastFig = RecorridoDiagrama.recorridoCiclo3(tab.getHoja().getDiagrama(),figuraAnterior)+3;
						obtieneContCiclos(lastFig,lastFig);	
						conexion=lastFig-1+contCiclos;
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
						dibujaFinIf(RecorridoDiagrama.recorridoCiclo3(tab.getHoja().getDiagrama(),figuraAnterior)+5,blue);
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
						contCiclos++;
					}//CUANDO REGRESA EN UN CICLO FOR O WHILE
					else if(((tab.getHoja().getDiagrama().elementAt(i) instanceof ForFigure || tab.getHoja().getDiagrama().elementAt(i) instanceof WhileFigure) && i<figuraAnterior)
							|| ((tab.getHoja().getDiagrama().elementAt(i) instanceof ForFigure || tab.getHoja().getDiagrama().elementAt(i) instanceof WhileFigure) && 
									(tab.getHoja().getDiagrama().elementAt(figuraAnterior) instanceof ForFigure || tab.getHoja().getDiagrama().elementAt(figuraAnterior) instanceof WhileFigure)
									&& i==figuraAnterior)){
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt((conexion++)).setForegroundColor(blue);
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt((conexion++)).setForegroundColor(blue);
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt((conexion++)).setForegroundColor(blue);
						if(((tab.getHoja().getDiagrama().elementAt(i) instanceof ForFigure || tab.getHoja().getDiagrama().elementAt(i) instanceof WhileFigure) && 
								(tab.getHoja().getDiagrama().elementAt(figuraAnterior) instanceof ForFigure || tab.getHoja().getDiagrama().elementAt(figuraAnterior) instanceof WhileFigure)
								&& i==figuraAnterior)){
							conexion-=4;
						}
					}//CUANDO SALE DE UN CICLO FOR O WHILE
					else if((tab.getHoja().getDiagrama().elementAt(figuraAnterior) instanceof ForFigure || tab.getHoja().getDiagrama().elementAt(figuraAnterior) instanceof WhileFigure) && i==RecorridoDiagrama.recorridoCiclo3(tab.getHoja().getDiagrama(),figuraAnterior)+6){
						obtieneContCiclos(i,i);
						conexion=i-4+contCiclos;
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt((conexion++)).setForegroundColor(blue);
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt((conexion++)).setForegroundColor(blue);
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt((conexion++)).setForegroundColor(blue);
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt((conexion++)).setForegroundColor(blue);
					}//CUANDO SALE DE UN CICLO FOR O WHILE AL FINAL DE UN IF
					else if((tab.getHoja().getDiagrama().elementAt(figuraAnterior) instanceof ForFigure || tab.getHoja().getDiagrama().elementAt(figuraAnterior) instanceof WhileFigure) && (colaDiagramas.size()!=0) 
							&& ((RecorridoDiagrama.recorridoCiclo3(tab.getHoja().getDiagrama(),figuraAnterior)+6 == RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst())-1)
									|| (RecorridoDiagrama.recorridoCiclo3(tab.getHoja().getDiagrama(),figuraAnterior)+6 == RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst()))))
									&& i>=RecorridoDiagrama.recorridoCiclo3(tab.getHoja().getDiagrama(),figuraAnterior)+6){
						
						obtieneContCiclos(RecorridoDiagrama.recorridoCiclo3(tab.getHoja().getDiagrama(),figuraAnterior)+6,RecorridoDiagrama.recorridoCiclo3(tab.getHoja().getDiagrama(),figuraAnterior)+6);
						conexion=RecorridoDiagrama.recorridoCiclo3(tab.getHoja().getDiagrama(),figuraAnterior)+6-4+contCiclos;
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt((conexion++)).setForegroundColor(blue);
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt((conexion++)).setForegroundColor(blue);
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt((conexion++)).setForegroundColor(blue);
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt((conexion++)).setForegroundColor(blue);
						dibujaFinIf(RecorridoDiagrama.recorridoCiclo3(tab.getHoja().getDiagrama(),figuraAnterior)+5,blue);
					}//CUANDO SE CONECTAN DOS FIGURAS SIMPLES
					else{
						if((figuraAnterior+1==i && (tab.getHoja().getDiagrama().elementAt(figuraAnterior) instanceof ForFigure 
								|| tab.getHoja().getDiagrama().elementAt(figuraAnterior) instanceof WhileFigure)) 
								|| (i>=2 && tab.getHoja().getDiagrama().elementAt(i-2) instanceof DecisionFigureEnd)){
							obtieneContCiclos(figuraAnterior,figuraAnterior);
							conexion=i-1+contCiclos;
						}
						colaConexiones.add(conexion);
						tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
					}
					setScroll(i);
				}
			}
		}
		figuraAnterior=i;
	}
	private boolean revisaFin(int i){
		if(i+1==tab.getHoja().getSizeDiagrama()){//si es el final del diagrama
			MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_INFORMATION | SWT.YES );
			messageBox.setText("Origami");
			messageBox.setMessage("La ejecución ha terminado.");
			int selec = messageBox.open();
			switch(selec){
				case 0:
					break;
				case 64:
					break;
				default:
					break;
			}
			tab.getHoja().resetScrollBar();
			colaConexiones.clear();
			MainWindow.getComponents().stopEjecucion();
			return true;
		}
		return false;
	}
	private void setScroll(int fig){
		int vertical=580;
		if(!MainWindow.getComponents().consolaData.exclude){
			vertical=400;
		}
		int x=0,y=0;
		if(tab.getHoja().getDiagrama().elementAt(fig).getBounds().x < tab.getHoja().getHScrollBar().getSelection()){
			x=tab.getHoja().getDiagrama().elementAt(fig).getBounds().x-400;
		}
		else if(tab.getHoja().getDiagrama().elementAt(fig).getBounds().x - tab.getHoja().getHScrollBar().getSelection() >= 830){ 
			x=tab.getHoja().getHScrollBar().getSelection()+tab.getHoja().getDiagrama().elementAt(fig).getBounds().x-400;
		}
		else{
			x = tab.getHoja().getHScrollBar().getSelection();
		}
		if(tab.getHoja().getDiagrama().elementAt(fig).getBounds().y < tab.getHoja().getVScrollBar().getSelection()){
			y=tab.getHoja().getDiagrama().elementAt(fig).getBounds().y-100;
		}
		else if(tab.getHoja().getDiagrama().elementAt(fig).getBounds().y - tab.getHoja().getVScrollBar().getSelection() >= vertical){ 
			y=tab.getHoja().getDiagrama().elementAt(fig).getBounds().y-150;
		}
		else{
			y=tab.getHoja().getVScrollBar().getSelection();
		}
		tab.getHoja().setScrollBar(x, y);
	}
	/**
	 * Dibuja los finales de ifs aunque esten anidados.
	 * @param fig La ultima figura antes de llegar a la elipse de cada lado junto a el FinDelIf.
	 * @param blue Color azul para conexiones.
	 */
	private void dibujaFinIf(int fig,Color blue){
		while(colaDiagramas.size()!=0 && ((fig == RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst())-2)
				|| (fig == RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst()))-1))){
			int alcance=RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst()));
			obtieneContCiclos(alcance-1,alcance);
			if(fig == RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst())-2){
				tab.getHoja().paso(RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst()))+1);
				tab.getHoja().getDiagrama().elementAt(RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst()))+1).setDerIzqFin(true);
				colaPintaFinIf.add(RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst()))+1);
				fig=RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst()))+1;
				conexion=contCiclos+RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.pop()));
				colaConexiones.add(conexion);
				tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
				tab.getHoja().getConexion().getConexion().elementAt(++conexion).setForegroundColor(blue);
				colaConexiones.add(conexion);
				conexion++;
				colaFinDiagramas.pop();
			}//LADO IZQUIERDO
			else if((fig == RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst()))-1)){							
				tab.getHoja().paso(RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst()))+1);
				tab.getHoja().getDiagrama().elementAt(RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst()))+1).setDerIzqFin(false);
				colaPintaFinIf.add(RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst()))+1);
				fig=RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.peekFirst()))+1;
				conexion=contCiclos+RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),colaDiagramas.pop()))+1;
				colaConexiones.add(conexion);
				tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
				colaConexiones.add(conexion);
				tab.getHoja().getConexion().getConexion().elementAt(conexion++).setForegroundColor(blue);
			}	
		}
	}
	private void obtieneContCiclos(int distancia,int var){
		contCiclos=0;
		for(int element=0;element<=distancia;element++){
			if(tab.getHoja().getDiagrama().elementAt(element) instanceof DecisionFigure){
				int finCicloIf=RecorridoDiagrama.recorridoCiclo2(tab.getHoja().getDiagrama(),RecorridoDiagrama.recorridoCiclo(tab.getHoja().getDiagrama(),element))+1;
				if(var>=finCicloIf){
					contCiclos++;
				}
			}
			else if(tab.getHoja().getDiagrama().elementAt(element) instanceof WhileFigure ||
					tab.getHoja().getDiagrama().elementAt(element) instanceof ForFigure){
				int finCicloFW = RecorridoDiagrama.recorridoCiclo3(tab.getHoja().getDiagrama(),element);
				if(var>=finCicloFW){
					contCiclos++;
				}
			}
		}
	}
	public int analizarLeer(String declaracion){
		String variable;
		int contador = 0;
		if(declaracion.indexOf("Leer:")!=-1){
			//hay leer
			if(declaracion.indexOf(",")!=-1){
				//multiples secciones de leer:
				declaracion = declaracion.substring(declaracion.indexOf(":")+1);
				//quito espacios al inicio
				while(declaracion.startsWith(" ")){
					declaracion = declaracion.substring(1);
				}
				while(declaracion.indexOf(",")!=-1){
					String vari = declaracion.substring(0,declaracion.indexOf(","));
					boolean bandera = true;
					if(vari.indexOf("int ")!=-1 || vari.indexOf("char ")!=-1 || vari.indexOf("float ")!=-1){
						for(int i=0; i<variablesEntrada.size(); i++){
							if(variablesEntrada.elementAt(i)==vari){
								contador++;
								bandera = false;
								break;
							}
						}
						if(bandera){
							contador++;
							contador++;
							variablesEntrada.add(vari);
						}
						else{
							bandera = true;
						}
					}
					else{
						contador++;
					}
					declaracion = declaracion.substring(declaracion.indexOf(",")+1);
				}
				//me queda uno en la variable declaracion por que al final tiene un ; ...
				declaracion = declaracion.substring(0,declaracion.length()-1);
				boolean bandera = true;
				if(declaracion.indexOf("int ")!=-1 || declaracion.indexOf("char ")!=-1 || declaracion.indexOf("float ")!=-1){
					for(int i=0; i<variablesEntrada.size(); i++){
						if(variablesEntrada.elementAt(i)==declaracion){
							contador++;
							bandera = false;
							break;
						}
					}
					if(bandera){
						contador++;
						contador++;
						variablesEntrada.add(declaracion);
					}
				}
				else{
					contador++;
				}
			}
			else{
				//solo una variable
				int pos = declaracion.indexOf(":");
				variable = declaracion.substring(pos+1);
				//quito espacios al inicio
				while(variable.startsWith(" ")){
					variable = variable.substring(1);
				}
				//quito espacios al final
				while(variable.endsWith(" ")){
					variable = variable.substring(0,variable.length()-1);
				}
				//checar si tiene tipo si no tiene buscar uno
				if(variable.indexOf("int ")!=-1 || variable.indexOf("char ")!=-1 || variable.indexOf("float ")!=-1){
					for(int i=0; i<variablesEntrada.size(); i++){
						if(variablesEntrada.elementAt(i)==variable){
							//return 0;
							break;
						}
					}
					variablesEntrada.add(variable);
					//return 1;
					contador =1;
				}
			}
		}
		else{
			//****************************
			//   Analizar el problema de int i,j,k,float y
			//   para agregarlos al vector de variablesEntrada
			//****************************
			//no hay palabra reservada leer:
			if(declaracion.indexOf(",")!=-1){
				//hay mas de una declaracion.
				String prefijo = declaracion.substring(0,declaracion.indexOf(" "));
				while(declaracion.indexOf(",")!=-1){
					String variab = declaracion.substring(0,declaracion.indexOf(","));
					//quito espacios al inicio
					while(variab.startsWith(" ")){
						variab = variab.substring(1);
					}
					//termina sin espacios al principio
					//checo que tipo es y actualizo el prefijo
					if(variab.indexOf("int ")!=-1){
						prefijo = "int";
					}
					else if(variab.indexOf("char ")!=-1){
						prefijo = "char";
					}
					else if(variab.indexOf("float ")!=-1){
						prefijo = "float";
					}
					else{
						//si no tiene tipo se agarra el prefijo anterior.
						variab = prefijo +" "+ variab;
					}
					//se agregan las variables al vector mientras se cuentan por cada coma
					//debe de ser una linea mas...
					variablesEntrada.add(variab);
					contador++;
					//actualizo la lista
					declaracion = declaracion.substring(declaracion.indexOf(",")+1);
				}
				//cuando termina el while queda una variable la cual tiene el final un ; ...
				//realizo la misma accion que esta dentro del while con la ultima variable
				String variab = declaracion.substring(0,declaracion.length()-1);
				while(variab.startsWith(" ")){
					variab = variab.substring(1);
				}
				if(variab.indexOf("int ")!=-1){
					prefijo = "int";
				}
				else if(variab.indexOf("char ")!=-1){
					prefijo = "char";
				}
				else if(variab.indexOf("float ")!=-1){
					prefijo = "float";
				}
				else{
					variab = prefijo +" "+ variab;
				}
				variablesEntrada.add(variab);
				contador++;
				//termian la accion ....
			}
			else{
				int inicializado = declaracion.indexOf("=");
				if(inicializado!=-1){
					variable = declaracion.substring(0,inicializado);
				}
				else{
					variable = declaracion;
				}
				//quito espacios al inicio
				while(variable.startsWith(" ")){
					variable = variable.substring(1);
				}
				//quito espacios al final
				while(variable.endsWith(" ")){
					variable = variable.substring(0,variable.length()-1);
				}	
				variablesEntrada.add(variable);
			}
		}
		return contador;
	}
	/* El casoA es cuando en un if hay solo informacion del lado derecho
	 * en el caso contrario quiere decir que no hay nada dentro del if si 
	 * no dentro del else...
	 */
	public void recorrido(){
		String aux = ";";
		int fin = tab.getHoja().getSizeDiagrama();
		int cont = 4;
		boolean casoA = true;
		int contadorDeLlaves =0;
		for(int i=0; i<fin;i++){
			tab.getHoja().getFigureIndexOf(i).getPosicion().removeAllElements();
		}
		for(int i=0; i<fin;i++){
			if(tab.getHoja().getFigureIndexOf(i) instanceof InputFigure){
				InputFigure entrada = (InputFigure)tab.getHoja().getFigureIndexOf(i);
				String str = entrada.instruction.instruccion;
				int pos=str.indexOf(aux);
				while(true){
					if(pos!=-1){
						tab.getHoja().getFigureIndexOf(i).setPosicion(cont);
						//analizar si hay que poner uno mas...
						String analizar = str.substring(0,pos);
						int mas = analizarLeer(analizar);
						if(mas!=0){
							//cont = cont + analizarLeer(analizar);
							if(mas==1){
								cont++;
								tab.getHoja().getFigureIndexOf(i).setPosicion(cont);
							}
							else{
								while(mas!=1){
									cont++;
									tab.getHoja().getFigureIndexOf(i).setPosicion(cont);
									mas--;
								}
							}
						}
						str = str.substring(pos+1);
						pos = str.indexOf(aux);
						cont++;
					}
					else{
						break;
					}
				}
			}
			else if(tab.getHoja().getFigureIndexOf(i) instanceof OutputFigure){
				OutputFigure salida = (OutputFigure)tab.getHoja().getFigureIndexOf(i);
				String str = salida.instruction.instruccion;		
				int pos=str.indexOf(aux);
				while(true){
					if(pos!=-1){
						tab.getHoja().getFigureIndexOf(i).setPosicion(cont);
						pos = salida.instruction.instruccion.indexOf(aux,pos+1);
						cont++;
					}
					else{
						break;
					}
				}
			}
			else if(tab.getHoja().getFigureIndexOf(i) instanceof DecisionFigure){
				tab.getHoja().getFigureIndexOf(i).setPosicion(cont);
				cont++;
				if(tab.getHoja().getFigureIndexOf(i+1) instanceof Elipse && tab.getHoja().getFigureIndexOf(i+2) instanceof Elipse){
					casoA = false;
				}
				else{
					casoA = true;
				}
			}
			else if(tab.getHoja().getFigureIndexOf(i) instanceof DecisionFigureEnd){
				cont++;
			}
			else if(tab.getHoja().getFigureIndexOf(i) instanceof Elipse){
				contadorDeLlaves = 1;
				i++;
				while(tab.getHoja().getFigureIndexOf(i) instanceof Elipse){
					contadorDeLlaves++;
					i++;
					if(contadorDeLlaves==6){
						break;
					}
				}
				switch(contadorDeLlaves){
				case 1:
					i=i-1;
					casoA = true;
					break;
				case 2:
					//hay un else por lo tanto por mientras solo aumento el contador
					cont++;
					cont++;
					i=i-1;
					break;
				case 3:
					//caso en el que no hay nada en el if si no en el else 
					//o caso en el que no hay nada en el else y solo en el if
					if(casoA){
						//cont++;
					}
					else{
						cont++;
						cont++;
						casoA = true;
					}
					i=i-1;
					break;
				case 4:
					//cont++;
					//i=i-1;
					//aparentemente no hago nada por que el fin del If lo va a poner
					if(!casoA){
						casoA=true;
					}
					i=i-1;
					break;
				case 6:
					cont++;
					i=i-1;
					break;
				case 8://problemas aqui
					cont++;
					cont++;
					cont++;
					i=i-1;
					break;
				default:
					int corchetes = contadorDeLlaves/6;
					cont = cont +corchetes;
					i = i-1;
					break;
				}
				//cont++;
				contadorDeLlaves=0;
			}
			else{
				if(tab.getHoja().getFigureIndexOf(i) instanceof CircleFigure){
					tab.getHoja().getFigureIndexOf(i).setPosicion(cont);
					cont++;
					/**
					 * caso especial debido a que el primer corchete despues del main esta abajo por lo que ocupa una 
					 * linea que no habia tomado en cuenta.
					 */
				}
				else{
					tab.getHoja().getFigureIndexOf(i).setPosicion(cont);
					cont++;
				}
			}
		}
		tab.getHoja().getFigureIndexOf(0).getPosicion().removeAllElements();
		tab.getHoja().getFigureIndexOf(0).setPosicion(4);

		Instruccion codigo = new Instruccion();
		codigo.generateGDB(tab.getHoja().getDiagrama());
		String auxCodigo[] = codigo.totalCode.split("\n");	
		NoEstaIf.removeAllElements();
		NoEstaWhile.removeAllElements();
		for(int x=0;x<auxCodigo.length;x++){
			if(auxCodigo[x].contains("A5i9I = 0")){
				NoEstaIf.add(x+1);
			}
			else if(auxCodigo[x].contains("A5i9W = 0")){
				NoEstaWhile.add(x+1);
			}
		}
	}
}