package origami.administration.actions;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.Cursor;

import origami.administration.AdminSelection;
import origami.administration.ApplicationState;
import origami.administration.Figura;
import origami.graphics.*;
import origami.graphics.figures.DecisionFigure;
import origami.graphics.figures.DecisionFigureEnd;
import origami.graphics.figures.EllipseFigure;
import origami.graphics.figures.ForFigure;
import origami.graphics.figures.WhileFigure;
import origami.graphics.widgets.TabFolder;


/**
 * Esta clase establece la propiedad de Drag & Drop 
 * y administra los espacios disponibles para agregar nuevas 
 * figuras al diagrama.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class EventoAgregarFigura{
    	private Point start;
	public boolean bandera=false;
	public final Cursor[] cursor = new Cursor[1];
	public int cursorPrincipal = SWT.CURSOR_ARROW;
	public AdminSelection selec;
	public TabFolder tab;
	/**
	 * Da la propiedad de Drag & Drop 
	 * a la figura recibida.
	 * @param figure
	 */
	public EventoAgregarFigura(AdminSelection selecc,TabFolder tabfolder) {
		selec = selecc;
		tab = tabfolder;
	}
	/**
	 * Obtiene la localizacion en la que la figura fue seleccionada.
	 * @param MouseEvent 
	 */
	public void mousePresseds(MouseEvent e) {
		bandera = false;
		start = e.getLocation();
		Figura fig = ApplicationState.mainFigure;
		if(fig!=null){
			int a;
			for(int z=0;z<tab.getTabItem().getLeaf().getSizeDiagrama()-1;z++){
				if(Verificar(z,z+1,fig)){
					break;
				}
				if(tab.getTabItem().getLeaf().getFigureIndexOf(z+1) instanceof DecisionFigure){
					a = verificarDerecha(z+1,fig);
					z = verificarDerecha(a,fig); 
					if(bandera){
						break;
					}
			 	 }
				if(tab.getTabItem().getLeaf().getFigureIndexOf(z+1) instanceof ForFigure || tab.getTabItem().getLeaf().getFigureIndexOf(z+1) instanceof WhileFigure){
					a = verificarAbajo(z+1,fig);
					z=a+4;
					if(bandera){
						break;
					}
				}
			}
		}
		else{
			selec.setSelectedFigure(-1);
			if(tab.getItemCount()!=0){
				tab.getTabItem().getLeaf().addFigure();
			}
		}
	}
	/**
	 * Este metodo hace un recorrido por la derecha
	 * de un if del diagrama para identificar si alguna 
	 * figura fue añadida al diagrama, si no regresa la pocision
	 * del final del lado derecho.
	 * 
	 * @param z
	 * @param fig
	 * @return int
	 */
	public int verificarDerecha(int z,Figura fig){
		int x = z+1;
		int a;
		z = z+2;
		while(tab.getTabItem().getLeaf().getFigureIndexOf(z)!=null){
			if(Verificar(x,z,fig)){
				break;
			}
			if(tab.getTabItem().getLeaf().getFigureIndexOf(z) instanceof DecisionFigure){
				a = verificarDerecha(z,fig);
				z = verificarDerecha(a,fig)+1;
				if(bandera){
					break;
				}
			}
			else if(tab.getTabItem().getLeaf().getFigureIndexOf(z) instanceof EllipseFigure){
				break;
			}
			else if(tab.getTabItem().getLeaf().getFigureIndexOf(z) instanceof ForFigure || tab.getTabItem().getLeaf().getFigureIndexOf(z) instanceof WhileFigure){
				a = verificarAbajo(z,fig);
				z=a+5;
				if(bandera){
					break;
				}
			}
			x=z;
			z++;
			if(tab.getTabItem().getLeaf().getFigureIndexOf(z) instanceof DecisionFigureEnd){
				break;
			}
		}
		return z;
	}
	/**
	 * Este metodo hace un recorrido por la abajo
	 * de un For o While del diagrama para identificar si alguna 
	 * figura fue añadida al diagrama, si no regresa la pocision
	 * del final de dicha figura.
	 * 
	 * @param z
	 * @param fig
	 * @return int
	 */
	public int verificarAbajo(int z,Figura fig){
		int x = z;//for
		int a;
		z = z+1;//sig figura
		while(tab.getTabItem().getLeaf().getFigureIndexOf(z)!=null){
			if(Verificar(x,z,fig)){
				break;
			}
			if(tab.getTabItem().getLeaf().getFigureIndexOf(z) instanceof DecisionFigure){
				a = verificarDerecha(z,fig);
				z = verificarDerecha(a,fig)+1;
				if(bandera){
					break;
				}
			}
			else if(tab.getTabItem().getLeaf().getFigureIndexOf(z) instanceof EllipseFigure){
				break;
			}
			else if(tab.getTabItem().getLeaf().getFigureIndexOf(z) instanceof ForFigure || tab.getTabItem().getLeaf().getFigureIndexOf(z) instanceof WhileFigure){
				a = verificarAbajo(z,fig);
				z=a+4;
				x=z;
				z++;
				if(bandera){
					break;
				}
			}
			x=z;
			z++;
		}
		return z;
	}
	/**
	 * 
	 * Este metodo revisa si en los espacios 
	 * disponibles en el diagrama fue agregado 
	 * una nueva figura, si no devuelve falso.
	 * 
	 * @param i
	 * @param j
	 * @param fig
	 * @return boolean
	 */
	public boolean Verificar(int i,int j,Figura fig){
		if(tab.getTabItem().getLeaf().getFigureIndexOf(i) instanceof EllipseFigure && tab.getTabItem().getLeaf().getFigureIndexOf(j) instanceof EllipseFigure){
			if(start.x >= tab.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().x-15 && start.x <= tab.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().x+15 && 
					start.y >= tab.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().y && start.y <= tab.getTabItem().getLeaf().getFigureIndexOf(j).getBounds().y){
				tab.getTabItem().getLeaf().getAdminDiagrama().orderDiagram(i, fig);
				tab.getTabItem().getSave().setSave(false);
				tab.getTabItem().getInformation().addInformation("/A - Se agrego una nueva figura al diagrama\n");
				tab.getTabItem().getInformation().setFigure(fig);
				tab.getTabItem().getInformation().setDiagram(tab.getTabItem().getLeaf().getDiagrama());
				tab.getTabItem().getLeaf().addFigure();
				tab.getTabItem().getLeaf().guardarRetroceso();
				cambiarCursor();
				bandera = true;
				return true;
			}
		}
		else if(tab.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().x + tab.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().width-1>= start.x && start.x>= tab.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().x+1 && 
				tab.getTabItem().getLeaf().getFigureIndexOf(j).getBounds().y-1 >=start.y &&start.y>=tab.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().y+tab.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().height+1){
			tab.getTabItem().getLeaf().getAdminDiagrama().orderDiagram(i, fig);
			tab.getTabItem().getSave().setSave(false);
			tab.getTabItem().getInformation().addInformation("/A - Se agrego una nueva figura al diagrama\n");
			tab.getTabItem().getInformation().setFigure(fig);
			tab.getTabItem().getInformation().setDiagram(tab.getTabItem().getLeaf().getDiagrama());
			tab.getTabItem().getLeaf().addFigure();
			tab.getTabItem().getLeaf().guardarRetroceso();
			cambiarCursor();
			bandera = true;
			return true;
		}
		else if(tab.getTabItem().getLeaf().getFigureIndexOf(i) instanceof EllipseFigure ){ 
			if(start.x>=tab.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().x-75 && start.x<=tab.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().x+75 
					&& start.y>=tab.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().y && start.y<=tab.getTabItem().getLeaf().getFigureIndexOf(j).getBounds().y){
				tab.getTabItem().getLeaf().getAdminDiagrama().orderDiagram(i, fig);
				tab.getTabItem().getSave().setSave(false);
				tab.getTabItem().getInformation().addInformation("/A - Se agrego una nueva figura al diagrama\n");
				tab.getTabItem().getInformation().setFigure(fig);
				tab.getTabItem().getInformation().setDiagram(tab.getTabItem().getLeaf().getDiagrama());
				tab.getTabItem().getLeaf().addFigure();
				tab.getTabItem().getLeaf().guardarRetroceso();
				cambiarCursor();
				bandera = true;
				return true;
			}
		}
		bandera=false;
		return false;
	}
	public void cambiarCursor(){
		ApplicationState.mainFigure = null;
		Cursor oldCursor = cursor[0];
		cursor[0] = new Cursor(null, cursorPrincipal);
		tab.getTabItem().getLeaf().getChart().setCursor(cursor[0]);
		if (oldCursor != null){
			oldCursor.dispose();
		}
	}
}