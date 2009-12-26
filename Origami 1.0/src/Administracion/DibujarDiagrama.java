package Administracion;

import java.util.Vector;
import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;
import org.eclipse.swt.SWT;

import ui.events.ContextualMenuEvent;
import ui.events.SelectEvent;
import ui.events.DoubleClickEvento;
import Grafico.*;
import Grafico.Figuras.SelectionSquare;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.DecisionFigureEnd;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.CircleFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;
import Grafico.Figuras.Elipse;

/**
 * Esta clase es el area en el que se dibuja el diagrama y 
 * el que dibuja y actualiza el diagrama conforme se va 
 * modificando el diagrama.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class DibujarDiagrama extends Figure{
	private Point punto = new Point();
	private int derecha, izquierda;
	public static AdminSeleccion selec;
	public TabFolder tab;
	public static boolean dispToolItem = false; 
	public static Vector<SelectionSquare> seleccion = new Vector<SelectionSquare>();
	
    /**
     * Define varias propiedades como el color y tama�o
     * de la clase Cuadro.
     */
	public DibujarDiagrama(AdminSeleccion selecc,TabFolder tabfolder){
		setBorder(new MarginBorder(5));
		setBackgroundColor(ColorConstants.white);
		setOpaque(true);
		setBounds(new Rectangle(0,0,2250,2500));
		selec = selecc;
		tab = tabfolder;
		addMouseListener(new MouseListener(){
			public void mouseDoubleClicked(MouseEvent arg0) {
				tab.getTabFolder().forceFocus();
			}
			public void mousePressed(MouseEvent arg0) {
				tab.getTabFolder().forceFocus();
				if(MainWindow.getComponents().console.hide){
					if(MainWindow.getComponents().console.hide){
						if(MainWindow.menu.consoleMenuItem.getSelection()){
							MainWindow.getComponents().moverConsola(false);
							MainWindow.menu.consoleMenuItem.setSelection(false);
						}
					}
				}			
			}
			public void mouseReleased(MouseEvent arg0) {
				tab.getTabFolder().forceFocus();
			}
		});
	}
	public DibujarDiagrama(int weight,int height){
		if(weight<=0){
			weight=200;
		}
		else{
			weight=(int)(weight*1.5);
		}
		setBackgroundColor(ColorConstants.white);
		setOpaque(true);
		setBounds(new Rectangle(0,0,weight,height+200));
	}
	/**
	 * Recibe el vector de figuras, y el panel; recorre
	 * el vector y llama a cada metodo que administra la 
	 * localizacion de las figuras en el panel.
	 * @param diagrama
	 * @param chart
	 */
	public void agregarFiguras(Vector<Figura> diagrama,Figure chart){
		int x;
		getFigura(diagrama,true);
		chart.removeAll();
		diagrama.firstElement().setSeleccion(false);
		chart.add(diagrama.firstElement());
		for(int i=1;i<diagrama.size();i++){
			if(diagrama.elementAt(i) instanceof DecisionFigure){
				i= anidarIf(diagrama,chart,i)-1;
			}
			else if(diagrama.elementAt(i) instanceof ForFigure || diagrama.elementAt(i) instanceof WhileFigure){
				i= anidarFW(diagrama,chart,i)-1;
			}
			else{
				if(diagrama.elementAt(i-1) instanceof Elipse){
					x = diagrama.elementAt(i-1).getBounds().x-40;
				}
				else{
					x = diagrama.elementAt(i-1).getBounds().x;
				}
				if(diagrama.elementAt(i) instanceof CircleFigure){
					punto.setLocation(x,diagrama.elementAt(i-1).getBounds().y+diagrama.elementAt(i-1).getBounds().height+50);
					diagrama.elementAt(i).setBounds(new Rectangle(punto.x, punto.y,80,50));
				}
				else{
					punto.setLocation(x,diagrama.elementAt(i-1).getBounds().y+diagrama.elementAt(i-1).getBounds().height+50);
					diagrama.elementAt(i).setLocation(punto);
				}
				chart.add(diagrama.elementAt(i));
			}	
		}
		if(selec.getFiguraSeleccionada()!=-1){
			agregarSeleccion(diagrama,chart);
			MainWindow.menu._editMenu.menuDisponibleFigura();
		}
		/*else if(MainWindow.first){		//quitado del MainWindow y no se encontro error aun se corrigio agregando una linea en la clase 
			MainWindow._editMenu.menuDisponibleFigura();	//EventoCambiarCursor en el metodo mouseReleased
		}*/
		if(dispToolItem){
			MainWindow.getComponents().toolBarDisable();
		}
		dispToolItem = true;
	}
	public void agregarFigurasExportar(Vector<Figura> diagrama,Figure chart,int lugar,int alt){
		int x;
		chart.removeAll();
		diagrama.firstElement().setSeleccion(false);
		diagrama.firstElement().getBounds().x = lugar;
		diagrama.firstElement().getBounds().y = alt;
		chart.add(diagrama.firstElement());
		for(int i=1;i<diagrama.size();i++){
			if(diagrama.elementAt(i) instanceof DecisionFigure){
				i= anidarIf(diagrama,chart,i)-1;
			}
			else if(diagrama.elementAt(i) instanceof ForFigure || diagrama.elementAt(i) instanceof WhileFigure){
				i= anidarFW(diagrama,chart,i)-1;
			}
			else{
				if(diagrama.elementAt(i-1) instanceof Elipse){
					x = diagrama.elementAt(i-1).getBounds().x-40;
				}
				else{
					x = diagrama.elementAt(i-1).getBounds().x;
				}
				if(diagrama.elementAt(i) instanceof CircleFigure){
					punto.setLocation(x,diagrama.elementAt(i-1).getBounds().y+diagrama.elementAt(i-1).getBounds().height+50);
					diagrama.elementAt(i).setBounds(new Rectangle(punto.x, punto.y,80,50));
				}
				else{
					punto.setLocation(x,diagrama.elementAt(i-1).getBounds().y+diagrama.elementAt(i-1).getBounds().height+50);
					diagrama.elementAt(i).setLocation(punto);
				}
				chart.add(diagrama.elementAt(i));
			}	
		}
	}
	/**
	 * Recibe un if y administra la localizacion
	 * de las figuras que contiene el if, y devuelve
	 * la localizacion del final del if.
	 * @param diagrama
	 * @param chart
	 * @param i
	 * @return int 
	 */
	public int anidarIf(Vector<Figura> diagrama,Figure chart,int i){
		int pda,pia,x,der2=0,der3=0,masDerecha,masIzquierda,total;
		while(true){
			if(diagrama.elementAt(i-1) instanceof Elipse){
				x = diagrama.elementAt(i-1).getBounds().x-40;
			}
			else{
				x = diagrama.elementAt(i-1).getBounds().x;
			}		
			if(diagrama.elementAt(i) instanceof DecisionFigure){
				pda= i+2;
				punto.setLocation(x,diagrama.elementAt(i-1).getBounds().y+diagrama.elementAt(i-1).getBounds().height+50);
				diagrama.elementAt(i).setLocation(punto);
				chart.add(diagrama.elementAt(i));
				
				//derecha arriba 
				punto.setLocation(diagrama.elementAt(i).getBounds().x+120,diagrama.elementAt(i).getBounds().y+diagrama.elementAt(i).getBounds().height/2);
				diagrama.elementAt(i+1).setLocation(punto);
				chart.add(diagrama.elementAt(i+1));
				
				while(diagrama.elementAt(pda) instanceof InputFigure || diagrama.elementAt(pda) instanceof SentenceFigure || diagrama.elementAt(pda) instanceof OutputFigure ||
						diagrama.elementAt(pda) instanceof WhileFigure || diagrama.elementAt(pda) instanceof ForFigure || diagrama.elementAt(pda) instanceof DecisionFigure){
					pda = anidarIf(diagrama,chart,pda); 
				}

				//derecha abajo
				if(diagrama.elementAt(pda-1) instanceof Elipse){
					punto.setLocation(diagrama.elementAt(i).getBounds().x+120,diagrama.elementAt(pda-1).getBounds().y+diagrama.elementAt(pda-1).getBounds().height+70);
					diagrama.elementAt(pda).setLocation(punto);
					chart.add(diagrama.elementAt(pda));
				}
				else{
					punto.setLocation(diagrama.elementAt(i).getBounds().x+120,diagrama.elementAt(pda-1).getBounds().y+diagrama.elementAt(pda-1).getBounds().height+50);
					diagrama.elementAt(pda).setLocation(punto);
					chart.add(diagrama.elementAt(pda));
				}
				
				//izquierda arriba
				punto.setLocation(diagrama.elementAt(i).getBounds().x-40,diagrama.elementAt(i+1).getBounds().y);
				diagrama.elementAt(pda+1).setLocation(punto);
				chart.add(diagrama.elementAt(pda+1));
				
				pia = pda+2; 
				while(diagrama.elementAt(pia) instanceof InputFigure || diagrama.elementAt(pia) instanceof SentenceFigure || diagrama.elementAt(pia) instanceof OutputFigure ||
						diagrama.elementAt(pia) instanceof WhileFigure || diagrama.elementAt(pia) instanceof ForFigure || diagrama.elementAt(pia) instanceof DecisionFigure){
					pia = anidarIf(diagrama,chart,pia); 
				}
				
				//izquierda abajo
				if(diagrama.elementAt(pia-1) instanceof Elipse){
					punto.setLocation(diagrama.elementAt(i).getBounds().x-40,diagrama.elementAt(pia-1).getBounds().y+diagrama.elementAt(pia-1).getBounds().height+70);
					diagrama.elementAt(pia).setLocation(punto);
					chart.add(diagrama.elementAt(pia));
				}
				else{
					punto.setLocation(diagrama.elementAt(i).getBounds().x-40,diagrama.elementAt(pia-1).getBounds().y+diagrama.elementAt(pia-1).getBounds().height+50);
					diagrama.elementAt(pia).setLocation(punto);
					chart.add(diagrama.elementAt(pia));
				}
				
				derecha = diagrama.elementAt(pda).getLocation().y;
				izquierda = diagrama.elementAt(pia).getLocation().y;			
				if(derecha>izquierda){
					punto.setLocation(diagrama.elementAt(pia).getLocation().x,derecha);
					diagrama.elementAt(pia).setLocation(punto);
					chart.add(diagrama.elementAt(pia));
					
				}
				else{
					punto.setLocation(diagrama.elementAt(pda).getLocation().x,izquierda);
					diagrama.elementAt(pda).setLocation(punto);
					chart.add(diagrama.elementAt(pda));
					
				}
				punto.setLocation(diagrama.elementAt(i).getBounds().x,diagrama.elementAt(pia).getBounds().y-20);
				diagrama.elementAt(pia+1).setBounds(new Rectangle(punto.x, punto.y,80,40));
				chart.add(diagrama.elementAt(pia+1));
				
				der2 = ifIzquierda(diagrama,i+2,pda);
				der3 = ifDerecha(diagrama,pda+2,pia);
				
				der2 += der3;
				
				masDerecha = ifIzquierda2(diagrama,i+2,pda);
				masIzquierda = ifDerecha2(diagrama,pda+2,pia);
				if(masIzquierda>masDerecha){
					total = masIzquierda;
				}
				else{
					total = masDerecha;
				}
				
				if(der2>=2){
					int k=0;
					for(int j=der2; j>=0; j--){
						k=k+j*15;
					}
					der2=k;
				}
				der2=der2+total;
				if(der2!=1){
					punto.setLocation(diagrama.elementAt(i+1).getBounds().x+der2,diagrama.elementAt(i+1).getBounds().y);
					diagrama.elementAt(i+1).setLocation(punto);
					chart.add(diagrama.elementAt(i+1));
					
					punto.setLocation(diagrama.elementAt(pda).getBounds().x+der2,diagrama.elementAt(pda).getBounds().y);
					diagrama.elementAt(pda).setLocation(punto);
					chart.add(diagrama.elementAt(pda));
					
					punto.setLocation(diagrama.elementAt(pda+1).getBounds().x-der2,diagrama.elementAt(pda+1).getBounds().y);
					diagrama.elementAt(pda+1).setLocation(punto);
					chart.add(diagrama.elementAt(pda+1));
					
					punto.setLocation(diagrama.elementAt(pia).getBounds().x-der2,diagrama.elementAt(pia).getBounds().y);
					diagrama.elementAt(pia).setLocation(punto);
					chart.add(diagrama.elementAt(pia));
					pda=i+2;
					while(diagrama.elementAt(pda) instanceof InputFigure || diagrama.elementAt(pda) instanceof SentenceFigure || diagrama.elementAt(pda) instanceof OutputFigure ||
							diagrama.elementAt(pda) instanceof WhileFigure || diagrama.elementAt(pda) instanceof ForFigure || diagrama.elementAt(pda) instanceof DecisionFigure){
						pda = anidarIf(diagrama,chart,pda); 
					}
					pia = pda+2; 
					while(diagrama.elementAt(pia) instanceof InputFigure || diagrama.elementAt(pia) instanceof SentenceFigure || diagrama.elementAt(pia) instanceof OutputFigure ||
							diagrama.elementAt(pia) instanceof WhileFigure || diagrama.elementAt(pia) instanceof ForFigure || diagrama.elementAt(pia) instanceof DecisionFigure){
						pia = anidarIf(diagrama,chart,pia); 
					}
				}
				i=pia+2;
				break;
			}
			else if(diagrama.elementAt(i) instanceof ForFigure || diagrama.elementAt(i) instanceof WhileFigure){
				i = anidarFW(diagrama,chart,i)-1;
			}
			else if(diagrama.elementAt(i) instanceof Elipse){
				break;
			}
			else{
				punto.setLocation(x,diagrama.elementAt(i-1).getBounds().y+diagrama.elementAt(i-1).getBounds().height+50);
				diagrama.elementAt(i).setLocation(punto);
				chart.add(diagrama.elementAt(i));
			}
			i++;
		}
		return i;
	}
	/**
	 * Recibe un for o while y administra la localizacion
	 * de las figuras que contiene el for o while, y devuelve
	 * la localizacion del final del for o while.
	 * @param diagrama
	 * @param chart
	 * @param i
	 * @return int
	 */
	public int anidarFW(Vector<Figura> diagrama,Figure chart,int i){
		int x,pda;
		while(true){
			if(diagrama.elementAt(i-1) instanceof Elipse){
				x = diagrama.elementAt(i-1).getBounds().x-40;
			}
			else{
				x = diagrama.elementAt(i-1).getBounds().x;
			}	
			if(diagrama.elementAt(i) instanceof ForFigure || diagrama.elementAt(i) instanceof WhileFigure){
				pda=i+1;
				punto.setLocation(x,diagrama.elementAt(i-1).getBounds().y+diagrama.elementAt(i-1).getBounds().height+50);
				diagrama.elementAt(i).setLocation(punto);
				chart.add(diagrama.elementAt(i));
				
				while(diagrama.elementAt(pda) instanceof InputFigure || diagrama.elementAt(pda) instanceof SentenceFigure || diagrama.elementAt(pda) instanceof OutputFigure ||
						diagrama.elementAt(pda) instanceof WhileFigure || diagrama.elementAt(pda) instanceof ForFigure || diagrama.elementAt(pda) instanceof DecisionFigure){
					pda = anidarFW(diagrama,chart,pda);
				}
				
				//abajo
				punto.setLocation(diagrama.elementAt(pda-1).getBounds().x +diagrama.elementAt(pda-1).getBounds().width/2 ,diagrama.elementAt(pda-1).getBounds().y+90);
				diagrama.elementAt(pda).setLocation(punto);
				chart.add(diagrama.elementAt(pda));
				
				//abajo izquierda
				punto.setLocation(diagrama.elementAt(i).getBounds().x-35,diagrama.elementAt(pda).getBounds().y);
				diagrama.elementAt(pda+1).setLocation(punto);
				chart.add(diagrama.elementAt(pda+1));
				
				//izquierda arriba
				punto.setLocation(diagrama.elementAt(pda+1).getBounds().x,diagrama.elementAt(i).getBounds().y + diagrama.elementAt(i).getBounds().height/2);
				diagrama.elementAt(pda+2).setLocation(punto);
				chart.add(diagrama.elementAt(pda+2));
				
				//derecha arriba
				punto.setLocation(diagrama.elementAt(i).getBounds().x+diagrama.elementAt(i).getBounds().width+35,diagrama.elementAt(i).getBounds().y + diagrama.elementAt(i).getBounds().height/2);
				diagrama.elementAt(pda+3).setLocation(punto);
				chart.add(diagrama.elementAt(pda+3));
				
				//derecha abajo
				punto.setLocation(diagrama.elementAt(pda+3).getBounds().x,diagrama.elementAt(pda).getBounds().y + 30);
				diagrama.elementAt(pda+4).setLocation(punto);
				chart.add(diagrama.elementAt(pda+4));
				
				//abajo abajo
				punto.setLocation(diagrama.elementAt(pda).getBounds().x,diagrama.elementAt(pda+4).getBounds().y);
				diagrama.elementAt(pda+5).setLocation(punto);
				chart.add(diagrama.elementAt(pda+5));
				
				int der = (int)recorridoFW(diagrama,i+1);
               
				if(der>0.0){
					der-=40;
					//abajo izquierda
					punto.setLocation(diagrama.elementAt(pda+1).getBounds().x-der,diagrama.elementAt(pda).getBounds().y);
					diagrama.elementAt(pda+1).setLocation(punto);
					chart.add(diagrama.elementAt(pda+1));
					
					//izquierda arriba
					punto.setLocation(diagrama.elementAt(pda+1).getBounds().x,diagrama.elementAt(i).getBounds().y + diagrama.elementAt(i).getBounds().height/2);
					diagrama.elementAt(pda+2).setLocation(punto);
					chart.add(diagrama.elementAt(pda+2));
					
					//derecha arriba
					punto.setLocation(diagrama.elementAt(pda+3).getBounds().x+der,diagrama.elementAt(i).getBounds().y + diagrama.elementAt(i).getBounds().height/2);
					diagrama.elementAt(pda+3).setLocation(punto);
					chart.add(diagrama.elementAt(pda+3));
					
					//derecha abajo
					punto.setLocation(diagrama.elementAt(pda+3).getBounds().x,diagrama.elementAt(pda).getBounds().y + 30);
					diagrama.elementAt(pda+4).setLocation(punto);
					chart.add(diagrama.elementAt(pda+4));
				}
				else{
					//abajo izquierda
					punto.setLocation(diagrama.elementAt(pda+1).getBounds().x+der,diagrama.elementAt(pda).getBounds().y);
					diagrama.elementAt(pda+1).setLocation(punto);
					chart.add(diagrama.elementAt(pda+1));
					
					//izquierda arriba
					punto.setLocation(diagrama.elementAt(pda+1).getBounds().x,diagrama.elementAt(i).getBounds().y + diagrama.elementAt(i).getBounds().height/2);
					diagrama.elementAt(pda+2).setLocation(punto);
					chart.add(diagrama.elementAt(pda+2));
					
					//derecha arriba
					punto.setLocation(diagrama.elementAt(pda+3).getBounds().x-der,diagrama.elementAt(i).getBounds().y + diagrama.elementAt(i).getBounds().height/2);
					diagrama.elementAt(pda+3).setLocation(punto);
					chart.add(diagrama.elementAt(pda+3));
					
					//derecha abajo
					punto.setLocation(diagrama.elementAt(pda+3).getBounds().x,diagrama.elementAt(pda).getBounds().y + 30);
					diagrama.elementAt(pda+4).setLocation(punto);
					chart.add(diagrama.elementAt(pda+4));
				}
				i= pda+6;
				break;
			}
			else if(diagrama.elementAt(i) instanceof DecisionFigure){
				i = anidarIf(diagrama,chart,i)-1;
			}
			else if(diagrama.elementAt(i) instanceof Elipse){
				break;
			}
			else{
				punto.setLocation(x,diagrama.elementAt(i-1).getBounds().y+diagrama.elementAt(i-1).getBounds().height+50);
				diagrama.elementAt(i).setLocation(punto);
				chart.add(diagrama.elementAt(i));
			}
			i++;
		}
		return i;
	}
	/**
	 * Recibe el vector de conexiones y las dibuja
	 * en el panel.
	 * @param conexion
	 * @param chart
	 */
	public void agregarConexiones(Vector <PolylineConnection> conexion,Figure chart){
		for(int x=0;x<conexion.size();x++){	
			chart.add(conexion.elementAt(x)); 
		}
	}
	/**
	 * Recibe un vector de figuras y la localizacion del
	 * inicio y el fin del lado izquierdo de un if
	 * y establece la localizacion de las figuras que contiene.
	 * @param diagrama
	 * @param i
	 * @param fin
	 * @return
	 */
	public int ifIzquierda(Vector<Figura> diagrama,int i,int fin){
		int cont=0,cont2=0;
		boolean bandera = true;
		while(true){	
			if(diagrama.elementAt(i) instanceof DecisionFigure){
				cont++;
				i=recorridoCiclo(diagrama,i);
			}
			else if(diagrama.elementAt(i) instanceof ForFigure || diagrama.elementAt(i) instanceof WhileFigure && bandera){
				if(bandera){cont++;}
				i=recorridoCiclo3(diagrama,i)+4;
				if(cont>cont2){
					cont2=cont;  
				}
				cont-=1;
				bandera = false;
			}
			else if(diagrama.elementAt(i) instanceof Elipse){
				if(cont>cont2){
					cont2=cont;  
				}
			}
			else if(diagrama.elementAt(i) instanceof DecisionFigureEnd){
				cont--;
			}
			if(i==fin){
				break;
			}
			i++;
		}
		return cont2;
	}
	public int ifIzquierda2(Vector<Figura> diagrama,int i,int fin){
		int cont=0,cont2=0;
		while(true){	
			if(diagrama.elementAt(i) instanceof DecisionFigure){
				cont =cont + diagrama.elementAt(i+1).getBounds().x-(diagrama.elementAt(i).getBounds().x+diagrama.elementAt(i).getBounds().width);
				cont-=35;
				i=recorridoCiclo(diagrama,i);
			}
			else if(diagrama.elementAt(i) instanceof ForFigure || diagrama.elementAt(i) instanceof WhileFigure){
				cont =cont + diagrama.elementAt(recorridoCiclo3(diagrama,i)+3).getBounds().x-(diagrama.elementAt(i).getBounds().x+diagrama.elementAt(i).getBounds().width);
				i = recorridoCiclo3(diagrama,i)+5;
				cont-=35;
			}
			else if(diagrama.elementAt(i) instanceof Elipse){
				if(cont>cont2){
					cont2=cont;  
				}
			}
			else if(diagrama.elementAt(i) instanceof DecisionFigureEnd){
			}
			if(i==fin){
				break;
			}
			i++;
		}
		return cont2;
	}
	/**
	 * 
	 * Recibe un vector de figuras y la localizacion del
	 * inicio y el fin del lado derecho de un if
	 * y establece la localizacion de las figuras que contiene.
	 * 
	 * @param diagrama
	 * @param i
	 * @param fin
	 * @return int
	 */
	public int ifDerecha(Vector<Figura> diagrama,int i,int fin){
		int cont=0,cont2=0;
		boolean bandera= true;
		while(true){	
			if(i==fin){
				break;
			}
			if(diagrama.elementAt(i) instanceof DecisionFigure){
				cont++;
				i++;
			}
			else if(diagrama.elementAt(i) instanceof ForFigure || diagrama.elementAt(i) instanceof WhileFigure && bandera){
				if(bandera){cont++;}
				i=recorridoCiclo3(diagrama,i)+5;
				bandera = false;
				if(cont>cont2){
					cont2=cont;  
				}
				cont-=1;
			}
			else if(diagrama.elementAt(i) instanceof Elipse){
				//i = recorridoCiclo2(diagrama,i+1);
				if(cont>cont2){
					cont2=cont;  
				}
			}
			else if(diagrama.elementAt(i) instanceof DecisionFigureEnd){
				cont--;
			}	
			i++;
		}
		return cont2;
	}
	public int ifDerecha2(Vector<Figura> diagrama,int i,int fin){
		int cont=0,cont2=0;
		while(true){	
			if(i==fin){
				break;
			}
			if(diagrama.elementAt(i) instanceof DecisionFigure){
				cont =cont + diagrama.elementAt(i+1).getBounds().x-(diagrama.elementAt(i).getBounds().x+diagrama.elementAt(i).getBounds().width);
				cont-=35;
				i++;
			}
			else if(diagrama.elementAt(i) instanceof ForFigure || diagrama.elementAt(i) instanceof WhileFigure){
				cont =cont + diagrama.elementAt(recorridoCiclo3(diagrama,i)+3).getBounds().x-(diagrama.elementAt(i).getBounds().x+diagrama.elementAt(i).getBounds().width);
				cont-=35;
				if(cont>cont2){
					cont2=cont;  
				}
				i = recorridoCiclo3(diagrama,i)+5;
			}
			else if(diagrama.elementAt(i) instanceof Elipse){
				if(cont>cont2){
					cont2=cont;  
				}
				//i = recorridoCiclo2(diagrama,i+1);
			}
			else if(diagrama.elementAt(i) instanceof DecisionFigureEnd){
			}	
			i++;
		}
		return cont2;
	}
	/**
	 * 
	 * Este metodo recibe un if y devuelve la
	 * localiozacion de la parte no del if.
	 * 
	 * @param diagrama
	 * @param i
	 * @return int
	 */
	public int recorridoCiclo(Vector<Figura> diagrama,int i){
		int x=diagrama.elementAt(i+1).getBounds().x-(diagrama.elementAt(i).getBounds().x+diagrama.elementAt(i).getBounds().width);
		x=diagrama.elementAt(i).getBounds().x-x;
		int y=diagrama.elementAt(i).getBounds().y+diagrama.elementAt(i).getBounds().height/2;
		i++;
		while(true){
			if(diagrama.elementAt(i) instanceof Elipse && diagrama.elementAt(i).getBounds().x==x && diagrama.elementAt(i).getBounds().y==y){
				break;
			}
			i++;
		}
		return i;
	}
	/**
	 * 
	 * Este metodo recibe la localizacion 
	 * del principio de la parte No del if
	 * y te devuelve la localizacion del final
	 * de dicha parte.
	 * 
	 * @param diagrama
	 * @param i
	 * @return int
	 */
	public int recorridoCiclo2(Vector<Figura> diagrama,int i){
		int x=diagrama.elementAt(i).getBounds().x;
		int y=diagrama.elementAt(i-1).getBounds().y;
		i++;
		while(true){
			if(diagrama.elementAt(i) instanceof Elipse && diagrama.elementAt(i).getBounds().x==x && diagrama.elementAt(i).getBounds().y==y){
				break;
			}
			i++;
		}
		return i;
	}
	/**
	 * 
	 * Este metodo recibe un For o While
	 * y te devuelve la localizacion del 
	 * final de dicha figura.
	 * 
	 * @param diagrama
	 * @param i
	 * @return int
	 */
	public int recorridoCiclo3(Vector<Figura> diagrama,int i){
        int x=diagrama.elementAt(i).getBounds().x + diagrama.elementAt(i).getBounds().width/2;
        int y=diagrama.elementAt(i).getBounds().y + diagrama.elementAt(i).getBounds().height/2;;
        i++;
        while(true){
            if(diagrama.elementAt(i) instanceof Elipse && diagrama.elementAt(i).getBounds().x==x
                    && diagrama.elementAt(i+1) instanceof Elipse && diagrama.elementAt(i+2) instanceof Elipse
                    && diagrama.elementAt(i+1).getBounds().y == diagrama.elementAt(i).getBounds().y
                    && diagrama.elementAt(i+2).getBounds().y == y ){
                break;
            }
            i++;
        }
        return i;
    }
	/**
	 * 
	 * Este metodo recorre toda la
	 * anidacion de un For o While y 
	 * regresa la distancia de la figura
	 * mas alejada del ciclo.
	 * 
	 * @param diagrama
	 * @param i
	 * @return doble
	 */
	public double recorridoFW(Vector<Figura> diagrama,int i){
		double x=0.0,x2=0.0,xDer=0.0,xIzq=0.0;
		boolean bandera=false,bandera2=false;
		int fin = recorridoCiclo3(diagrama,i-1);
		double lugar = (double)diagrama.elementAt(i-1).getBounds().x + diagrama.elementAt(i-1).getBounds().width/2.0;
		double X = (double)(diagrama.elementAt(i-1).getBounds().x + diagrama.elementAt(i-1).getBounds().width/2.0)/lugar;
		if(lugar>0.0){
			while(true){
				if(i==fin){
					break;
				}
				x2 = (double)(diagrama.elementAt(i).getBounds().x + diagrama.elementAt(i).getBounds().width)/lugar;
				if(x2>X){
					if(x2>xDer){
						xDer=x2;
						x2=0;
					}
				}
				x2 = (double)diagrama.elementAt(i).getBounds().x/lugar;
				if(x2<X){
					if(bandera==false){
						xIzq=x2;
						x2=0;
						bandera=true;
					}
					else if(bandera){
						if(x2<xIzq){
							xIzq=x2;
							x2=0;
						}
					}
				}
				i++;
			}
			double dist = xDer -X;
			double dist2 = X - xIzq;
			if(dist>=dist2){
				x = xDer;
			}
			else{
				x = xIzq;
			}
			if(x>=X){
				x = (x-X)*lugar;
			}
			else if(x<X && x!= 0.0){
				x = (X-x)*lugar;
			}
			return x;
		}
		else{
			while(true){
				if(i==fin){
					break;
				}
				x2 = (double)(diagrama.elementAt(i).getBounds().x + diagrama.elementAt(i).getBounds().width)/lugar;
				if(x2>X){
					if(bandera==false){//se puede quitar
						xDer=x2;
						x2=0;
						bandera=true;
					}
					else if(bandera){
						if(x2>xDer){
							xDer=x2;
							x2=0;
						}
					}
				}
				x2 = (double)diagrama.elementAt(i).getBounds().x/lugar;
				if(x2<X){
					if(bandera2==false){
						xIzq=x2;
						x2=0;
						bandera2=true;
					}
					else if(bandera2){
						if(x2<xIzq){
							xIzq=x2;
							x2=0;
						}
					}
				}
				i++;
			}
			double dist = X - xDer;
			double dist2 = xIzq - X;
			if((-dist) >= (-dist2)){
				x = xDer;
			}
			else{
				x = xIzq;
			}
			if(x>=X){
				x = (x-X)*lugar;
			}
			else if(x<X && x!= 0.0){
				x = (X-x)*lugar;
			}
			return x;
		}
	}
	public static void agregarSeleccion(Vector<Figura> diagrama,Figure chart){
		int x,y,width,height;
		x = diagrama.elementAt(selec.getFiguraSeleccionada()).getBounds().x;
		y = diagrama.elementAt(selec.getFiguraSeleccionada()).getBounds().y;
		height = diagrama.elementAt(selec.getFiguraSeleccionada()).getBounds().height;
		width = diagrama.elementAt(selec.getFiguraSeleccionada()).getBounds().width;
		
		for(int i=0; i<4; i++){
			SelectionSquare uno = new SelectionSquare(false,SWT.COLOR_DARK_GRAY);
			seleccion.insertElementAt(uno, i);
		}
		seleccion.elementAt(0).setLocation(new Point(x-8,y-10));
		seleccion.elementAt(1).setLocation(new Point(x+width+2,y-10));
		seleccion.elementAt(2).setLocation(new Point(x-8,y+height-2));
		seleccion.elementAt(3).setLocation(new Point(x+width+2,y+height-2));
		
		for(int i=0; i<4; i++){
			chart.add(seleccion.elementAt(i));
		}
		MainWindow.menu._editMenu.menuDisponibleFigura();
		diagrama.elementAt(selec.getFiguraSeleccionada()).setSeleccion(true);
	}
	/**
	 * 
	 * Este metodo establece las nuevas
	 * propiedades de las figuras del diagrama
	 * de flujo.
	 * 
	 * @param diagrama
	 */
	public void getFigura(Vector<Figura> diagrama,boolean eventos){
		Point pt = new Point();
		if(eventos){
			if(!MainWindow.getComponents().isPasoAPaso){
				new SelectEvent(diagrama.elementAt(0),selec,tab);
			}
			for(int x =1;x<diagrama.size()-1;x++ ){
				if(diagrama.elementAt(x) instanceof DecisionFigure){
					pt = diagrama.elementAt(x).getLocation();
					DecisionFigure figuras = (DecisionFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					DecisionFigure figura = new DecisionFigure();
					figura.instruction = figuras.instruction;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setPasoAPaso(figuras.isPasoAPaso());
					figura.setLocation(pt);
					diagrama.insertElementAt(figura,x);
					if(!MainWindow.getComponents().isPasoAPaso){
						new DoubleClickEvento(diagrama.elementAt(x),selec,tab);
						new ContextualMenuEvent(diagrama.elementAt(x));
						new SelectEvent(diagrama.elementAt(x),selec,tab);
					}
				}
				else if(diagrama.elementAt(x) instanceof ForFigure){
					pt = diagrama.elementAt(x).getLocation();
					ForFigure figuras = (ForFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					ForFigure figura = new ForFigure();
					figura.instruction = figuras.instruction;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setPasoAPaso(figuras.isPasoAPaso());
					figura.setLocation(pt);
					diagrama.insertElementAt(figura,x);
					if(!MainWindow.getComponents().isPasoAPaso){
						new DoubleClickEvento(diagrama.elementAt(x),selec,tab);
						new ContextualMenuEvent(diagrama.elementAt(x));
						new SelectEvent(diagrama.elementAt(x),selec,tab);
					}
				}
				else if(diagrama.elementAt(x) instanceof WhileFigure){
					pt = diagrama.elementAt(x).getLocation();
					WhileFigure figuras = (WhileFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					WhileFigure figura = new WhileFigure();
					figura.instruccion = figuras.instruccion;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setPasoAPaso(figuras.isPasoAPaso());
					figura.setLocation(pt);
					diagrama.insertElementAt(figura,x);
					if(!MainWindow.getComponents().isPasoAPaso){
						new DoubleClickEvento(diagrama.elementAt(x),selec,tab);
						new ContextualMenuEvent(diagrama.elementAt(x));
						new SelectEvent(diagrama.elementAt(x),selec,tab);
					}
				}
				else if(diagrama.elementAt(x) instanceof InputFigure){
					pt = diagrama.elementAt(x).getLocation();
					InputFigure figuras = (InputFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					InputFigure figura = new InputFigure();
					figura.instruction = figuras.instruction;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setPasoAPaso(figuras.isPasoAPaso());
					figura.setLocation(pt);
					diagrama.insertElementAt(figura,x);
					if(!MainWindow.getComponents().isPasoAPaso){
						new ui.events.DoubleClickEvento(diagrama.elementAt(x),selec,tab);
						new ContextualMenuEvent(diagrama.elementAt(x));
						new SelectEvent(diagrama.elementAt(x),selec,tab);
					}
				}
				else if(diagrama.elementAt(x) instanceof OutputFigure){
					pt = diagrama.elementAt(x).getLocation();
					OutputFigure figuras = (OutputFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					OutputFigure figura = new OutputFigure();
					figura.instruction = figuras.instruction;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setPasoAPaso(figuras.isPasoAPaso());
					figura.setLocation(pt);
					diagrama.insertElementAt(figura,x);
					if(!MainWindow.getComponents().isPasoAPaso){
						new ui.events.DoubleClickEvento(diagrama.elementAt(x),selec,tab);
						new ContextualMenuEvent(diagrama.elementAt(x));
						new SelectEvent(diagrama.elementAt(x),selec,tab);
					}
				}
				else if(diagrama.elementAt(x) instanceof SentenceFigure){
					pt = diagrama.elementAt(x).getLocation();
					SentenceFigure figuras = (SentenceFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					SentenceFigure figura = new SentenceFigure();
					figura.instruccion = figuras.instruccion;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setPasoAPaso(figuras.isPasoAPaso());
					figura.setLocation(pt);
					diagrama.insertElementAt(figura,x);
					if(!MainWindow.getComponents().isPasoAPaso){
						new DoubleClickEvento(diagrama.elementAt(x),selec,tab);
						new ContextualMenuEvent(diagrama.elementAt(x));
						new SelectEvent(diagrama.elementAt(x),selec,tab);
					}
				}
			}
		}
		else{
			for(int x =0;x<diagrama.size();x++ ){
				if(diagrama.elementAt(x) instanceof DecisionFigure){
					pt = diagrama.elementAt(x).getLocation();
					DecisionFigure figuras = (DecisionFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					DecisionFigure figura = new DecisionFigure();
					figura.instruction = figuras.instruction;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setLocation(pt);
					figura.setPasoAPaso(figuras.isPasoAPaso());
					diagrama.insertElementAt(figura,x);
				}
				else if(diagrama.elementAt(x) instanceof ForFigure){
					pt = diagrama.elementAt(x).getLocation();
					ForFigure figuras = (ForFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					ForFigure figura = new ForFigure();
					figura.instruction = figuras.instruction;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setLocation(pt);
					figura.setPasoAPaso(figuras.isPasoAPaso());
					diagrama.insertElementAt(figura,x);
				}
				else if(diagrama.elementAt(x) instanceof WhileFigure){
					pt = diagrama.elementAt(x).getLocation();
					WhileFigure figuras = (WhileFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					WhileFigure figura = new WhileFigure();
					figura.instruccion = figuras.instruccion;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setLocation(pt);
					figura.setPasoAPaso(figuras.isPasoAPaso());
					diagrama.insertElementAt(figura,x);
				}
				else if(diagrama.elementAt(x) instanceof InputFigure){
					pt = diagrama.elementAt(x).getLocation();
					InputFigure figuras = (InputFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					InputFigure figura = new InputFigure();
					figura.instruction = figuras.instruction;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setLocation(pt);
					figura.setPasoAPaso(figuras.isPasoAPaso());
					diagrama.insertElementAt(figura,x);
				}
				else if(diagrama.elementAt(x) instanceof OutputFigure){
					pt = diagrama.elementAt(x).getLocation();
					OutputFigure figuras = (OutputFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					OutputFigure figura = new OutputFigure();
					figura.instruction = figuras.instruction;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setLocation(pt);
					figura.setPasoAPaso(figuras.isPasoAPaso());
					diagrama.insertElementAt(figura,x);
				}
				else if(diagrama.elementAt(x) instanceof SentenceFigure){
					pt = diagrama.elementAt(x).getLocation();
					SentenceFigure figuras = (SentenceFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					SentenceFigure figura = new SentenceFigure();
					figura.instruccion = figuras.instruccion;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setLocation(pt);
					figura.setPasoAPaso(figuras.isPasoAPaso());
					diagrama.insertElementAt(figura,x);
				}
				else if(diagrama.elementAt(x) instanceof CircleFigure){
					pt = diagrama.elementAt(x).getLocation();
					CircleFigure fig = (CircleFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					CircleFigure ini = new CircleFigure();
					ini.setMesagge(fig.getMesagge());
					ini.setListaPosicion(fig.getPosicion());
					ini.setLocation(pt);
					diagrama.insertElementAt(ini,x);
				}
			}
		}
	}
	public void disableCursor(Vector<Figura> diagrama, DibujarDiagrama chart){
		int x;
		getFigura(diagrama,false);
		chart.removeAll();
		chart.add(diagrama.firstElement());
		for(int i=1;i<diagrama.size();i++){
			if(diagrama.elementAt(i) instanceof DecisionFigure){
				i= anidarIf(diagrama,chart,i)-1;
			}
			else if(diagrama.elementAt(i) instanceof ForFigure || diagrama.elementAt(i) instanceof WhileFigure){
				i= anidarFW(diagrama,chart,i)-1;
			}
			else{
				if(diagrama.elementAt(i-1) instanceof Elipse){
					x = diagrama.elementAt(i-1).getBounds().x-40;
				}
				else{
					x = diagrama.elementAt(i-1).getBounds().x;
				}
				if(diagrama.elementAt(i) instanceof CircleFigure){
					punto.setLocation(x,diagrama.elementAt(i-1).getBounds().y+diagrama.elementAt(i-1).getBounds().height+50);
					diagrama.elementAt(i).setBounds(new Rectangle(punto.x, punto.y,80,50));
				}
				else{
					punto.setLocation(x,diagrama.elementAt(i-1).getBounds().y+diagrama.elementAt(i-1).getBounds().height+50);
					diagrama.elementAt(i).setLocation(punto);
				}
				chart.add(diagrama.elementAt(i));
			}	
		}
		if(selec.getFiguraSeleccionada()!=-1){
			agregarSeleccion(diagrama,chart);
			MainWindow.menu._editMenu.menuDisponibleFigura();
		}
		/*else if(MainWindow.first){	igual que el metodo agregarFiguras() de la misma clase.
			MainWindow._editMenu.menuDisponibleFigura();
		}*/
		if(dispToolItem){
			MainWindow.getComponents().toolBarDisable();
		}
		dispToolItem = true;
		Conexion conexion = new Conexion(tab);
		conexion.crearConexiones(diagrama);
		if(MainWindow.getComponents().paso!=null && MainWindow.getComponents().paso.colaConexiones.size()!=0
				&& MainWindow.getComponents().paso.a.GetId() == tab.getSelectedTabItemId()){
			for(int y=0;y<MainWindow.getComponents().paso.colaConexiones.size();y++){
				conexion.getConexion().elementAt(MainWindow.getComponents().paso.colaConexiones.get(y)).setForegroundColor(MainWindow.display.getSystemColor(SWT.COLOR_RED));
			}
		}
		agregarConexiones(conexion.getConexion(),chart);
	}
}