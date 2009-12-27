package ui.listener;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import Administracion.Figura;
import Administracion.actions.AddFigureLogic;
import Administracion.actions.ContextualMenuActions;
import Grafico.Componentes;
import Grafico.MainWindow;
import Grafico.Figuras.CircleFigure;
/**
 * 
 * Esta clase establece la propiedad de eliminar.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class ContextualMenuEvent extends MouseListener.Stub{
	private MenuItem cortar;
	private MenuItem copiar;
	private MenuItem pegar;
	private MenuItem agregar;
	private MenuItem eliminar;
	private MenuItem nuevo;
	
	public ContextualMenuEvent(){
		//MainWindow.first=true; //eliminado agregado una llamada al metodo mouseReleased() en la clase EventoCambiarCursor
	}
	public ContextualMenuEvent(Figura figure){
		figure.addMouseListener(this);
	}
	public void menu(Menu menu){
		cortar = new MenuItem(menu,SWT.CASCADE);
		cortar.setText("Cortar                  Ctrl+X");
		copiar = new MenuItem(menu,SWT.CASCADE);
		copiar.setText("Copiar                  Ctrl+C");
		pegar = new MenuItem(menu,SWT.CASCADE);
		pegar.setText("Pegar                   Ctrl+V");
		eliminar = new MenuItem(menu,SWT.CASCADE);
		eliminar.setText("Eliminar                 Supr");
		new MenuItem(menu,SWT.SEPARATOR);
		agregar = new MenuItem(menu,SWT.CASCADE);
		agregar.setText("Agregar Codigo");
		pegar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				new ContextualMenuActions().Pegar(MainWindow.getComponents()._diagrams.getHoja().getDiagrama().elementAt(Componentes._selectionAdministrator.getFiguraSeleccionada()));
			}
		});
		cortar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			    new ContextualMenuActions().Cortar(MainWindow.getComponents()._diagrams.getHoja().getDiagrama().elementAt(Componentes._selectionAdministrator.getFiguraSeleccionada()));
			}
		});
		copiar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			    new ContextualMenuActions().Copiar(MainWindow.getComponents()._diagrams.getHoja().getDiagrama().elementAt(Componentes._selectionAdministrator.getFiguraSeleccionada()));
			}
		});
		eliminar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			    new ContextualMenuActions().Eliminar(MainWindow.getComponents()._diagrams.getHoja().getDiagrama().elementAt(Componentes._selectionAdministrator.getFiguraSeleccionada()));
			}
		});
		agregar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			    new ContextualMenuActions().agregar(MainWindow.getComponents()._diagrams.getHoja().getDiagrama().elementAt(Componentes._selectionAdministrator.getFiguraSeleccionada()));
			}
		});
		Menu menu2 = new Menu(menu); 
		nuevo = new MenuItem(menu,SWT.CASCADE);
		nuevo.setText("Insertar Figura");
		nuevo.setMenu(menu2);
		MenuItem entrada = new MenuItem(menu2,SWT.CASCADE);
		entrada.setText("Entrada");
		MenuItem proceso = new MenuItem(menu2,SWT.CASCADE);
		proceso.setText("Proceso");
		MenuItem decision = new MenuItem(menu2,SWT.CASCADE);
		decision.setText("Decision");
		MenuItem mientras = new MenuItem(menu2,SWT.CASCADE);
		mientras.setText("Mientras");
		MenuItem para = new MenuItem(menu2,SWT.CASCADE);
		para.setText("Para");
		MenuItem salida = new MenuItem(menu2,SWT.CASCADE);
		salida.setText("Impresion");
		decision.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			    new AddFigureLogic().addDecision();
			    new ContextualMenuActions().insertarFigura(Componentes.mainFigure);
			    Componentes.mainFigure = null;
				
			}
		});
		proceso.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			    new AddFigureLogic().addSentence();
				 new ContextualMenuActions().insertarFigura(Componentes.mainFigure);
				 Componentes.mainFigure = null;
			}
		});
		entrada.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			    new AddFigureLogic().addInput();
			    new ContextualMenuActions().insertarFigura(Componentes.mainFigure);
			    Componentes.mainFigure = null;
			}
		});
		salida.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			    new AddFigureLogic().addOutput();
			    new ContextualMenuActions().insertarFigura(Componentes.mainFigure);
			    Componentes.mainFigure = null;
			}
		});
		para.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			    new AddFigureLogic().addFor();
			    new ContextualMenuActions().insertarFigura(Componentes.mainFigure);
			    Componentes.mainFigure = null;
			}
		});
		mientras.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			    new AddFigureLogic().addWhile();
			    new ContextualMenuActions().insertarFigura(Componentes.mainFigure);
			    Componentes.mainFigure = null;
			}
		});
//		menuDisponibleFigura();
	}
	public void menuDisponibleFigura(){
		if(Componentes._selectionAdministrator.getFiguraSeleccionada() != -1){
			if(MainWindow.getComponents()._diagrams.getHoja().getDiagrama().elementAt(Componentes._selectionAdministrator.getFiguraSeleccionada()) instanceof CircleFigure){
				if(Componentes._diagramAdministrator.diagrama.size()!=0){
					pegar.setEnabled(true);
				}
				else{
					pegar.setEnabled(false);
				}
				nuevo.setEnabled(true);
				copiar.setEnabled(false);
				cortar.setEnabled(false);
				eliminar.setEnabled(false);
				agregar.setEnabled(false);
			}
			else{
				if(Componentes._diagramAdministrator.diagrama.size()==0){
					pegar.setEnabled(false);
				}
				else{
					pegar.setEnabled(true);
				}
				copiar.setEnabled(true);
				cortar.setEnabled(true);
				eliminar.setEnabled(true);
				agregar.setEnabled(true);
				nuevo.setEnabled(true);
			}
		}
		else{
			pegar.setEnabled(false);
			copiar.setEnabled(false);
			cortar.setEnabled(false);
			eliminar.setEnabled(false);
			agregar.setEnabled(false);
			nuevo.setEnabled(false);
		}
	}
	public void mousePressed(MouseEvent e){
	    Figura fig = ((Figura) e.getSource());
		if(e.button == 3){
			Menu menu = new Menu(MainWindow.shell,SWT.POP_UP);
			menu(menu);
			menu.setVisible(true);
			
			if(fig instanceof CircleFigure){
				if(Componentes._diagramAdministrator.diagrama.size()!=0){
					pegar.setEnabled(true);
				}
				else{
					pegar.setEnabled(false);
				}
				copiar.setEnabled(false);
				cortar.setEnabled(false);
				eliminar.setEnabled(false);
				agregar.setEnabled(false);
			}
			else{
				if(Componentes._diagramAdministrator.diagrama.size()==0){
					pegar.setEnabled(false);
				}
				else{
					pegar.setEnabled(true);
				}
				copiar.setEnabled(true);
				cortar.setEnabled(true);
				eliminar.setEnabled(true);
				agregar.setEnabled(true);
			}
		}
	}

}