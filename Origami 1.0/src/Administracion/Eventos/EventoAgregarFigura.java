package Administracion.Eventos;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.Cursor;
import Administracion.AdminSeleccion;
import Administracion.Figura;
import Administracion.TabFolder;
import Grafico.*;
import Grafico.Figuras.IfEnd;
import Grafico.Figuras.For;
import Grafico.Figuras.If;
import Grafico.Figuras.While;
import Grafico.Figuras.Elipse;

/**
 * Esta clase establece la propiedad de Drag & Drop 
 * y administra los espacios disponibles para agregar nuevas 
 * figuras al diagrama.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */

public class EventoAgregarFigura extends MouseMotionListener.Stub implements MouseListener{
	private Point start;
	public boolean bandera=false;
	public final Cursor[] cursor = new Cursor[1];
	public int cursorPrincipal = SWT.CURSOR_ARROW;
	public AdminSeleccion selec;
	public TabFolder tab;
	/**
	 * Da la propiedad de Drag & Drop 
	 * a la figura recibida.
	 * @param figure
	 */
	public EventoAgregarFigura(Figure figure,AdminSeleccion selecc,TabFolder tabfolder) {
		figure.addMouseMotionListener(this);
		figure.addMouseListener(this);
		selec = selecc;
		tab = tabfolder;
	}
	/**
	 * Recorre el diagrama de figuras y verifica si
	 * la figura fue liberada en un area disponible.
	 * @param MouseEvent 
	 */
	public void mouseReleased(MouseEvent e){
	}
	public void mouseClicked(MouseEvent e) {
  	}
	public void mouseDoubleClicked(MouseEvent e) {
  	}
	/**
	 * Obtiene la localizacion en la que la figura fue seleccionada.
	 * @param MouseEvent 
	 */
	public void mousePressed(MouseEvent e) {
		bandera = false;
		start = e.getLocation();
		Figura fig = Ventana.figuraPrincipal;
		if(fig!=null){
			int a;
			for(int z=0;z<tab.getHoja().getSizeDiagrama()-1;z++){
				if(Verificar(z,z+1,fig)){
					break;
				}
				if(tab.getHoja().getFigureIndexOf(z+1) instanceof If){
					a = verificarDerecha(z+1,fig);
					z = verificarDerecha(a,fig); 
					if(bandera){
						break;
					}
			 	 }
				if(tab.getHoja().getFigureIndexOf(z+1) instanceof For || tab.getHoja().getFigureIndexOf(z+1) instanceof While){
					a = verificarAbajo(z+1,fig);
					z=a+4;
					if(bandera){
						break;
					}
				}
			}
		}
		else{
			selec.setFiguraSeleccionada(-1);
			if(tab.getItemCount()!=0){
				tab.getHoja().addFigure();
			}
		}
	}
	public void mouseDragged(MouseEvent e) {
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
		while(tab.getHoja().getFigureIndexOf(z)!=null){
			if(Verificar(x,z,fig)){
				break;
			}
			if(tab.getHoja().getFigureIndexOf(z) instanceof If){
				a = verificarDerecha(z,fig);
				z = verificarDerecha(a,fig)+1;
				if(bandera){
					break;
				}
			}
			else if(tab.getHoja().getFigureIndexOf(z) instanceof Elipse){
				break;
			}
			else if(tab.getHoja().getFigureIndexOf(z) instanceof For || tab.getHoja().getFigureIndexOf(z) instanceof While){
				a = verificarAbajo(z,fig);
				z=a+5;
				if(bandera){
					break;
				}
			}
			x=z;
			z++;
			if(tab.getHoja().getFigureIndexOf(z) instanceof IfEnd){
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
		while(tab.getHoja().getFigureIndexOf(z)!=null){
			if(Verificar(x,z,fig)){
				break;
			}
			if(tab.getHoja().getFigureIndexOf(z) instanceof If){
				a = verificarDerecha(z,fig);
				z = verificarDerecha(a,fig)+1;
				if(bandera){
					break;
				}
			}
			else if(tab.getHoja().getFigureIndexOf(z) instanceof Elipse){
				break;
			}
			else if(tab.getHoja().getFigureIndexOf(z) instanceof For || tab.getHoja().getFigureIndexOf(z) instanceof While){
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
		if(tab.getHoja().getFigureIndexOf(i) instanceof Elipse && tab.getHoja().getFigureIndexOf(j) instanceof Elipse){
			if(start.x >= tab.getHoja().getFigureIndexOf(i).getBounds().x-15 && start.x <= tab.getHoja().getFigureIndexOf(i).getBounds().x+15 && 
					start.y >= tab.getHoja().getFigureIndexOf(i).getBounds().y && start.y <= tab.getHoja().getFigureIndexOf(j).getBounds().y){
				tab.getHoja().getAdminDiagrama().ordenar(i, fig);
				tab.getTabItem().getSave().setSave(false);
				tab.getTabItem().getInfo().setInformacion("/A - Se agrego una nueva figura al diagrama\n");
				tab.getTabItem().getInfo().setFigura(fig);
				tab.getTabItem().getInfo().setDiagrama(tab.getHoja().getDiagrama());
				tab.getHoja().addFigure();
				tab.getHoja().guardarRetroceso();
				cambiarCursor();
				bandera = true;
				return true;
			}
		}
		else if(tab.getHoja().getFigureIndexOf(i).getBounds().x + tab.getHoja().getFigureIndexOf(i).getBounds().width-1>= start.x && start.x>= tab.getHoja().getFigureIndexOf(i).getBounds().x+1 && 
				tab.getHoja().getFigureIndexOf(j).getBounds().y-1 >=start.y &&start.y>=tab.getHoja().getFigureIndexOf(i).getBounds().y+tab.getHoja().getFigureIndexOf(i).getBounds().height+1){
			tab.getHoja().getAdminDiagrama().ordenar(i, fig);
			tab.getTabItem().getSave().setSave(false);
			tab.getTabItem().getInfo().setInformacion("/A - Se agrego una nueva figura al diagrama\n");
			tab.getTabItem().getInfo().setFigura(fig);
			tab.getTabItem().getInfo().setDiagrama(tab.getHoja().getDiagrama());
			tab.getHoja().addFigure();
			tab.getHoja().guardarRetroceso();
			cambiarCursor();
			bandera = true;
			return true;
		}
		else if(tab.getHoja().getFigureIndexOf(i) instanceof Elipse ){ 
			if(start.x>=tab.getHoja().getFigureIndexOf(i).getBounds().x-75 && start.x<=tab.getHoja().getFigureIndexOf(i).getBounds().x+75 
					&& start.y>=tab.getHoja().getFigureIndexOf(i).getBounds().y && start.y<=tab.getHoja().getFigureIndexOf(j).getBounds().y){
				tab.getHoja().getAdminDiagrama().ordenar(i, fig);
				tab.getTabItem().getSave().setSave(false);
				tab.getTabItem().getInfo().setInformacion("/A - Se agrego una nueva figura al diagrama\n");
				tab.getTabItem().getInfo().setFigura(fig);
				tab.getTabItem().getInfo().setDiagrama(tab.getHoja().getDiagrama());
				tab.getHoja().addFigure();
				tab.getHoja().guardarRetroceso();
				cambiarCursor();
				bandera = true;
				return true;
			}
		}
		bandera=false;
		return false;
	}
	public void cambiarCursor(){
		Ventana.figuraPrincipal = null;
		Cursor oldCursor = cursor[0];
		cursor[0] = new Cursor(null, cursorPrincipal);
		tab.getHoja().getChart().setCursor(cursor[0]);
		if (oldCursor != null){
			oldCursor.dispose();
		}
		Ventana.bandera = true;
	}
}