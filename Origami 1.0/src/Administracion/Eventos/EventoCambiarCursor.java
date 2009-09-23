package Administracion.Eventos;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.*;

import Administracion.AdminDiagrama;
import Administracion.Figura;
import Administracion.TabFolder;
import Grafico.*;
import Grafico.Figuras.Entrada;
import Grafico.Figuras.For;
import Grafico.Figuras.If;
import Grafico.Figuras.Imprimir;
import Grafico.Figuras.Proceso;
import Grafico.Figuras.While;
import Grafico.Figuras.ellipse;
import Imagenes.CargarImagenes;

/**
 * Esta clase establece la propiedad de Drag & Drop 
 * y administra los espacios disponibles para agregar nuevas 
 * figuras al diagrama.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class EventoCambiarCursor extends MouseMotionListener.Stub implements MouseListener{
	private Point start;
	public boolean bandera=false;
	private Figura figuraPrincipal;
	private IFigure cuadro;
	public final Cursor[] cursor = Ventana.getComponentes().cursor;
	
	public TabFolder tab;
	/**
	 * Da la propiedad de Drag & Drop 
	 * a la figura recibida.
	 * @param figure
	 */
	public EventoCambiarCursor(IFigure figure,TabFolder tabfolder){
		cuadro = figure;
		figure.addMouseMotionListener(this);
		figure.addMouseListener(this);
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
	public void mouseMoved(MouseEvent me) {
		figuraPrincipal = Ventana.figuraPrincipal;
		int a;
		start = me.getLocation();
		for(int z=0;z<tab.getHoja().getSizeDiagrama()-1;z++){
			if(Verificar(z,z+1)){
				break;
			}
			if(tab.getHoja().getFigureIndexOf(z+1) instanceof If){
				a = verificarDerecha(z+1);
				if(bandera){
					break;
				}
				z = verificarDerecha(a);
				if(bandera){
					break;
				}
		 	 }
			if(tab.getHoja().getFigureIndexOf(z+1) instanceof For || tab.getHoja().getFigureIndexOf(z+1) instanceof While){
				a = verificarAbajo(z+1);
				z=a+4;
				if(bandera){
					break;
				}
			}
		}
		if(!bandera){
			Remarcar(false);
		}
		tooltip(tab.getHoja().getAdminDiagrama());
	}
	/**
	 * Obtiene la localizacion en la que la figura fue seleccionada.
	 * @param MouseEvent 
	 */
	public void mousePressed(MouseEvent e) {
	}
	/**
	 * Obtiene la diferencia en la que la figura fue seleccionada 
	 * y la distancia a donde fue movida para actualizar su posicion.
	 * @param MouseEvent 
	 */
	public void mouseDragged(MouseEvent e) {
	}
	/**
	 * 
	 * Este metodo hace un recorrido por la derecha
	 * de un if del diagrama para identificar si alguna 
	 * figura fue añadida al diagrama, si no regresa la pocision
	 * del final del lado derecho.
	 * 
	 * @param z
	 * @param fig
	 * @return int
	 */
	public int verificarDerecha(int z){
		int x = z+1;//elipse
		int a;
		z = z+2;//sig figura
		
		while(tab.getHoja().getFigureIndexOf(z)!=null){
			if(Verificar(x,z)){
				break;
			}
			if(tab.getHoja().getFigureIndexOf(z) instanceof If){
				a = verificarDerecha(z);
				if(bandera){
					break;
				}
				z = verificarDerecha(a);
				if(bandera){
					break;
				}
			}
			else if(tab.getHoja().getFigureIndexOf(z) instanceof ellipse){
				break;
			}
			else if(tab.getHoja().getFigureIndexOf(z) instanceof For || tab.getHoja().getFigureIndexOf(z) instanceof While){
				a = verificarAbajo(z);
				z=a+5;
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
	 * Este metodo hace un recorrido por la abajo
	 * de un For o While del diagrama para identificar si alguna 
	 * figura fue añadida al diagrama, si no regresa la pocision
	 * del final de dicha figura.
	 * 
	 * @param z
	 * @param fig
	 * @return int
	 */
	public int verificarAbajo(int z){
		int x = z;//for
		int a;
		z = z+1;//sig figura
		while(tab.getHoja().getFigureIndexOf(z)!=null){
			if(Verificar(x,z)){
				break;
			}
			if(tab.getHoja().getFigureIndexOf(z) instanceof If){
				a = verificarDerecha(z);
				if(bandera){
					break;
				}
				z = verificarDerecha(a)+1;
				if(bandera){
					break;
				}
			}
			else if(tab.getHoja().getFigureIndexOf(z) instanceof ellipse){
				break;
			}
			else if(tab.getHoja().getFigureIndexOf(z) instanceof For || tab.getHoja().getFigureIndexOf(z) instanceof While){
				a = verificarAbajo(z);
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
	public boolean Verificar(int i,int j){ 
		bandera = false;
		if(tab.getHoja().getFigureIndexOf(i) instanceof ellipse && tab.getHoja().getFigureIndexOf(j) instanceof ellipse){
			if(start.x >= tab.getHoja().getFigureIndexOf(i).getBounds().x-15 && start.x <= tab.getHoja().getFigureIndexOf(i).getBounds().x+15 && 
					start.y >= tab.getHoja().getFigureIndexOf(i).getBounds().y && start.y <= tab.getHoja().getFigureIndexOf(j).getBounds().y){
				Remarcar(true);
				bandera = true;
				return true;
			}
		}
		else if(tab.getHoja().getFigureIndexOf(i).getBounds().x + tab.getHoja().getFigureIndexOf(i).getBounds().width-1>= start.x && start.x>= tab.getHoja().getFigureIndexOf(i).getBounds().x+1 && 
				tab.getHoja().getFigureIndexOf(j).getBounds().y-1 >=start.y &&start.y>=tab.getHoja().getFigureIndexOf(i).getBounds().y+tab.getHoja().getFigureIndexOf(i).getBounds().height+1){
			Remarcar(true);
			bandera = true;
			return true;
		}
		else if(tab.getHoja().getFigureIndexOf(i) instanceof ellipse ){ 
			if(start.x>=tab.getHoja().getFigureIndexOf(i).getBounds().x-75 && start.x<=tab.getHoja().getFigureIndexOf(i).getBounds().x+75 
					&& start.y>=tab.getHoja().getFigureIndexOf(i).getBounds().y && start.y<=tab.getHoja().getFigureIndexOf(j).getBounds().y){
				Remarcar(true);
				bandera = true;
				return true;
			}
		}
		return bandera;
	}
	public void Remarcar(boolean remarcar){
		if(remarcar){
			if(figuraPrincipal instanceof If || figuraPrincipal instanceof For || figuraPrincipal instanceof While
					|| figuraPrincipal instanceof Imprimir || figuraPrincipal instanceof Entrada || figuraPrincipal instanceof Proceso){
				 //String name = "cursor.PNG";
			     //ImageData image = new ImageData(name);
			     cursor[0] = new Cursor(Ventana.display, CargarImagenes.getImagen("cursor.PNG").getImageData(), 0, 0);
			     cuadro.setCursor(cursor[0]);
			}
		}
		else{
			String name = "";
			if(figuraPrincipal instanceof If){
				 name =  "cursorIf.png";
			}
			else if(figuraPrincipal instanceof For){
				 name = "cursorFor.png";
			}
			else if(figuraPrincipal instanceof While){
				 name = "cursorWhile.png";
			}
			else if(figuraPrincipal instanceof Imprimir){
				 name = "cursorSalida.png";
			}
			else if(figuraPrincipal instanceof Entrada){
				 name = "cursorEntrada.png";
			}
			else if(figuraPrincipal instanceof Proceso){
				 name = "cursorProceso.png";
			}
			if(name != ""){
				ImageData image = CargarImagenes.getImagen(name).getImageData();
			    image.transparentPixel = image.palette.getPixel(new RGB(255, 255, 255));
			    cursor[0] = new Cursor(Ventana.display,image,60,35);
			    cuadro.setCursor(cursor[0]);
			}
		}
	}
	public void tooltip(AdminDiagrama diagrama){
		String dato = "null", defaul= "Doble Clic Para Agregar Instrucciones";
		String subStr;
		int i;		
		for(int k=0; k<diagrama.diagrama.size(); k++){
			if(diagrama.diagrama.elementAt(k) instanceof If){
				If a = (If)diagrama.diagrama.elementAt(k);
				dato = a.instruccion.instruccion.elementAt(0).instruccion;
				i = dato.length();
				subStr = dato.substring(0, i-1);
				if(dato.compareToIgnoreCase("null")!=0 && a.instruccion.instruccion.size() > 1){
					subStr=subStr.substring(2,subStr.length());
					subStr="Si"+subStr;
					diagrama.diagrama.elementAt(k).setToolTip(new Label(subStr));
				}
				else{
					diagrama.diagrama.elementAt(k).setToolTip(new Label(defaul));
				}
			}
			else if(diagrama.diagrama.elementAt(k) instanceof For){
				For a = (For)diagrama.diagrama.elementAt(k);
				dato = a.instruccion.instruccion.elementAt(0).instruccion;
				i = dato.length();
				subStr = dato.substring(0, i-1);
				if(dato.compareToIgnoreCase("null")!=0 && a.instruccion.instruccion.size() > 1){
					subStr=subStr.substring(3,subStr.length());
					subStr="Para"+subStr;
					diagrama.diagrama.elementAt(k).setToolTip(new Label(subStr));
				}
				else{
					diagrama.diagrama.elementAt(k).setToolTip(new Label(defaul));
				}
			}
			else if(diagrama.diagrama.elementAt(k) instanceof While){
				While a = (While)diagrama.diagrama.elementAt(k);
				dato = a.instruccion.instruccion.elementAt(0).instruccion;
				i = dato.length();
				subStr = dato.substring(0, i-1);
				if(dato.compareToIgnoreCase("null")!=0 && a.instruccion.instruccion.size() > 1){
					subStr=subStr.substring(5,subStr.length());
					subStr="Mientras"+subStr;
					diagrama.diagrama.elementAt(k).setToolTip(new Label(subStr));
				}
				else{
					diagrama.diagrama.elementAt(k).setToolTip(new Label(defaul));
				}
			}
			else if(diagrama.diagrama.elementAt(k) instanceof Proceso){
				Proceso a = (Proceso)diagrama.diagrama.elementAt(k);
				dato = a.instruccion.instruccion;
				i = dato.length();
				subStr = dato.substring(0, i-1);
				if(dato != "null" && dato.compareTo("null")!=0){
					diagrama.diagrama.elementAt(k).setToolTip(new Label(subStr));
				}
				else{
					diagrama.diagrama.elementAt(k).setToolTip(new Label(defaul));
				}
			}
			else if(diagrama.diagrama.elementAt(k) instanceof Imprimir){
				Imprimir a = (Imprimir)diagrama.diagrama.elementAt(k);
				dato = "";
				String[] variables2 = new String[50];
				String[] variables = new String[50];
				variables =  a.instruccion.instruccion.split(";");
				int cont =0;
				for(int x=0;x<variables.length;x++){
					variables[x] = variables[x].replace("\\", "");
					variables[x] = variables[x].replaceFirst("p", "");
					if(variables[x].compareTo("") != 0){
						variables2[cont] = variables[x];
						cont++;
					}
				}
				int cont2=0;
				for(int x=0;x<cont;x++){
					if(variables2[x].compareTo("") != 0){
						if(cont2==0){
							dato = variables2[x];
						}
						else{
							dato = dato + "," + variables2[x];
						}
						cont2++;
					}
				}
				if(dato.length()>0 && !(dato.startsWith("null")) && dato.compareTo("null")!=0){
					diagrama.diagrama.elementAt(k).setToolTip(new Label(dato));
				}
				else{
					diagrama.diagrama.elementAt(k).setToolTip(new Label(defaul));
				}
			}
			else if(diagrama.diagrama.elementAt(k) instanceof Entrada){
				Entrada a = (Entrada)diagrama.diagrama.elementAt(k);
				dato = a.instruccion.instruccion;
				dato = dato.replaceAll(";", ",");
				if(dato.length()>0){
					dato = dato.substring(0, dato.length()-1);
				}
				if(!(dato.startsWith("nul")) && dato.compareTo("")!=0){
					diagrama.diagrama.elementAt(k).setToolTip(new Label(dato));	
				}
				else{
					diagrama.diagrama.elementAt(k).setToolTip(new Label(defaul));
				}
			}
		}
	}
}