package Administracion.Eventos;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import Administracion.AdminDiagrama;
import Administracion.Figura;
import Administracion.TabFolder;
import Grafico.*;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;
import Grafico.Figuras.Elipse;
import Imagenes.ImageLoader;

/**
 * Esta clase establece la propiedad de Drag & Drop 
 * y administra los espacios disponibles para agregar nuevas 
 * figuras al diagrama.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class EventoCambiarCursor{
	private Point start;
	public boolean bandera=false;
	private Figura figuraPrincipal;
	private IFigure cuadro;
	public final Cursor[] cursor = MainWindow.cursor;
	
	public TabFolder tab;
	/**
	 * Da la propiedad de Drag & Drop 
	 * a la figura recibida.
	 * @param figure
	 */
	public EventoCambiarCursor(IFigure figure,TabFolder tabfolder){
		cuadro = figure;
		tab = tabfolder;
	}
	/**
	 * Recorre el diagrama de figuras y verifica si
	 * la figura fue liberada en un area disponible.
	 * @param MouseEvent 
	 */
	public void mouseMoveds(MouseEvent me) {
		figuraPrincipal = Componentes.mainFigure;
		int a;
		start = me.getLocation();
		for(int z=0;z<tab.getHoja().getSizeDiagrama()-1;z++){
			if(Verificar(z,z+1)){
				break;
			}
			if(tab.getHoja().getFigureIndexOf(z+1) instanceof DecisionFigure){
				a = verificarDerecha(z+1);
				if(bandera){
					break;
				}
				z = verificarDerecha(a);
				if(bandera){
					break;
				}
		 	 }
			if(tab.getHoja().getFigureIndexOf(z+1) instanceof ForFigure || tab.getHoja().getFigureIndexOf(z+1) instanceof WhileFigure){
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
			if(tab.getHoja().getFigureIndexOf(z) instanceof DecisionFigure){
				a = verificarDerecha(z);
				if(bandera){
					break;
				}
				z = verificarDerecha(a);
				if(bandera){
					break;
				}
			}
			else if(tab.getHoja().getFigureIndexOf(z) instanceof Elipse){
				break;
			}
			else if(tab.getHoja().getFigureIndexOf(z) instanceof ForFigure || tab.getHoja().getFigureIndexOf(z) instanceof WhileFigure){
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
			if(tab.getHoja().getFigureIndexOf(z) instanceof DecisionFigure){
				a = verificarDerecha(z);
				if(bandera){
					break;
				}
				z = verificarDerecha(a)+1;
				if(bandera){
					break;
				}
			}
			else if(tab.getHoja().getFigureIndexOf(z) instanceof Elipse){
				break;
			}
			else if(tab.getHoja().getFigureIndexOf(z) instanceof ForFigure || tab.getHoja().getFigureIndexOf(z) instanceof WhileFigure){
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
		if(tab.getHoja().getFigureIndexOf(i) instanceof Elipse && tab.getHoja().getFigureIndexOf(j) instanceof Elipse){
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
		else if(tab.getHoja().getFigureIndexOf(i) instanceof Elipse ){ 
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
			if(figuraPrincipal instanceof DecisionFigure || figuraPrincipal instanceof ForFigure || figuraPrincipal instanceof WhileFigure
					|| figuraPrincipal instanceof OutputFigure || figuraPrincipal instanceof InputFigure || figuraPrincipal instanceof SentenceFigure){
				 //String name = "cursor.PNG";
			     //ImageData image = new ImageData(name);
			     cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursor.PNG").getImageData(), 0, 0);
			     cuadro.setCursor(cursor[0]);
			}
		}
		else{
			String name = "";
			if(figuraPrincipal instanceof DecisionFigure){
				 name =  "cursorIf.png";
			}
			else if(figuraPrincipal instanceof ForFigure){
				 name = "cursorFor.png";
			}
			else if(figuraPrincipal instanceof WhileFigure){
				 name = "cursorWhile.png";
			}
			else if(figuraPrincipal instanceof OutputFigure){
				 name = "cursorSalida.png";
			}
			else if(figuraPrincipal instanceof InputFigure){
				 name = "cursorEntrada.png";
			}
			else if(figuraPrincipal instanceof SentenceFigure){
				 name = "cursorProceso.png";
			}
			if(name != ""){
				ImageData image = ImageLoader.getImage(name).getImageData();
			    image.transparentPixel = image.palette.getPixel(new RGB(255, 255, 255));
			    cursor[0] = new Cursor(MainWindow.display,image,60,35);
			    cuadro.setCursor(cursor[0]);
			}
		}
	}
	public void tooltip(AdminDiagrama diagrama){
		String dato = "null", defaul= "Doble Clic Para Agregar Instrucciones";
		String subStr;
		int i;		
		for(int k=0; k<diagrama.diagrama.size(); k++){
			if(diagrama.diagrama.elementAt(k) instanceof DecisionFigure){
				DecisionFigure a = (DecisionFigure)diagrama.diagrama.elementAt(k);
				dato = a.instruction.instruccion.elementAt(0).instruccion;
				i = dato.length();
				subStr = dato.substring(0, i-1);
				if(dato.compareToIgnoreCase("null")!=0 && a.instruction.instruccion.size() > 1){
					subStr=subStr.substring(2,subStr.length());
					subStr="Si"+subStr;
					diagrama.diagrama.elementAt(k).setToolTip(new Label(subStr));
				}
				else{
					diagrama.diagrama.elementAt(k).setToolTip(new Label(defaul));
				}
			}
			else if(diagrama.diagrama.elementAt(k) instanceof ForFigure){
				ForFigure a = (ForFigure)diagrama.diagrama.elementAt(k);
				dato = a.instruction.instruccion.elementAt(0).instruccion;
				i = dato.length();
				subStr = dato.substring(0, i-1);
				if(dato.compareToIgnoreCase("null")!=0 && a.instruction.instruccion.size() > 1){
					subStr=subStr.substring(3,subStr.length());
					subStr="Para"+subStr;
					diagrama.diagrama.elementAt(k).setToolTip(new Label(subStr));
				}
				else{
					diagrama.diagrama.elementAt(k).setToolTip(new Label(defaul));
				}
			}
			else if(diagrama.diagrama.elementAt(k) instanceof WhileFigure){
				WhileFigure a = (WhileFigure)diagrama.diagrama.elementAt(k);
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
			else if(diagrama.diagrama.elementAt(k) instanceof SentenceFigure){
				SentenceFigure a = (SentenceFigure)diagrama.diagrama.elementAt(k);
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
			else if(diagrama.diagrama.elementAt(k) instanceof OutputFigure){
				OutputFigure a = (OutputFigure)diagrama.diagrama.elementAt(k);
				dato = "";
				String[] variables2 = new String[50];
				String[] variables = new String[50];
				variables =  a.instruction.instruccion.split(";");
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
			else if(diagrama.diagrama.elementAt(k) instanceof InputFigure){
				InputFigure a = (InputFigure)diagrama.diagrama.elementAt(k);
				dato = a.instruction.instruccion;
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