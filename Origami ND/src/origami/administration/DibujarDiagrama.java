package origami.administration;

import java.util.Vector;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import origami.administration.actions.DiagramCiclePath;
import origami.graphics.MainWindow;
import origami.graphics.figures.CircleFigure;
import origami.graphics.figures.DecisionFigure;
import origami.graphics.figures.DecisionFigureEnd;
import origami.graphics.figures.EllipseFigure;
import origami.graphics.figures.ForFigure;
import origami.graphics.figures.InputFigure;
import origami.graphics.figures.OutputFigure;
import origami.graphics.figures.SentenceFigure;
import origami.graphics.figures.WhileFigure;
import origami.graphics.listeners.ContextualMenuListener;
import origami.graphics.listeners.DoubleClickListener;
import origami.graphics.listeners.SelectionListener;
import origami.graphics.widgets.CustomTabFolder;

/**
 * Esta clase es el area en el que se dibuja el diagrama y 
 * el que dibuja y actualiza el diagrama conforme se va 
 * modificando el diagrama.
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class DibujarDiagrama{
    private Point punto = new Point();
    private int derecha, izquierda;
    public static AdminSelection selec;
    public CustomTabFolder tab;
   	
	
    public DibujarDiagrama(AdminSelection selecc,CustomTabFolder tabfolder){
	selec = selecc;
	tab = tabfolder;
    }
    /**
     * Recibe el vector de figuras, y el panel; recorre
     * el vector y llama a cada metodo que administra la 
     * localizacion de las figuras en el panel.
     * @param diagrama
     * @param chart
     */
    public Vector<FigureStructure> agregarFiguras(Vector<FigureStructure> diagrama){
	int x;
	getFigura(diagrama,true);
	diagrama.firstElement().setSeleccion(false);
	
	for(int i=1;i<diagrama.size();i++){
	    if(diagrama.elementAt(i) instanceof DecisionFigure){
		i= anidarIf(diagrama,i)-1;
	    }
	    else if(diagrama.elementAt(i) instanceof ForFigure || diagrama.elementAt(i) instanceof WhileFigure){
		i= anidarFW(diagrama,i)-1;
	    }
	    else{
		if(diagrama.elementAt(i-1) instanceof EllipseFigure){
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
	    }	
	}
	
	return diagrama;
    }
    public Vector<FigureStructure> agregarFigurasExportar(Vector<FigureStructure> diagrama,int lugar,int alt){   
	int x;
	diagrama.firstElement().getBounds().x = lugar;
	diagrama.firstElement().getBounds().y = alt;
	
	diagrama.firstElement().setSeleccion(false);
	
	
	for(int i=1;i<diagrama.size();i++){
	    if(diagrama.elementAt(i) instanceof DecisionFigure){
		i= anidarIf(diagrama,i)-1;
	    }
	    else if(diagrama.elementAt(i) instanceof ForFigure || diagrama.elementAt(i) instanceof WhileFigure){
		i= anidarFW(diagrama,i)-1;
	    }
	    else{
		if(diagrama.elementAt(i-1) instanceof EllipseFigure){
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
	    }	
	}
	
	return diagrama;
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
	public int anidarIf(Vector<FigureStructure> diagrama,int i){
		int pda,pia,x,der2=0,der3=0,masDerecha,masIzquierda,total;
		while(true){
			if(diagrama.elementAt(i-1) instanceof EllipseFigure){
				x = diagrama.elementAt(i-1).getBounds().x-40;
			}
			else{
				x = diagrama.elementAt(i-1).getBounds().x;
			}		
			if(diagrama.elementAt(i) instanceof DecisionFigure){
				pda= i+2;
				punto.setLocation(x,diagrama.elementAt(i-1).getBounds().y+diagrama.elementAt(i-1).getBounds().height+50);
				diagrama.elementAt(i).setLocation(punto);
				
				
				
				punto.setLocation(diagrama.elementAt(i).getBounds().x+120,diagrama.elementAt(i).getBounds().y+diagrama.elementAt(i).getBounds().height/2);
				diagrama.elementAt(i+1).setLocation(punto);
				
				
				
				while(diagrama.elementAt(pda) instanceof InputFigure || diagrama.elementAt(pda) instanceof SentenceFigure || diagrama.elementAt(pda) instanceof OutputFigure ||
						diagrama.elementAt(pda) instanceof WhileFigure || diagrama.elementAt(pda) instanceof ForFigure || diagrama.elementAt(pda) instanceof DecisionFigure){
					pda = anidarIf(diagrama,pda); 
				}

				if(diagrama.elementAt(pda-1) instanceof EllipseFigure){
					punto.setLocation(diagrama.elementAt(i).getBounds().x+120,diagrama.elementAt(pda-1).getBounds().y+diagrama.elementAt(pda-1).getBounds().height+70);
					diagrama.elementAt(pda).setLocation(punto);
				}
				else{
					punto.setLocation(diagrama.elementAt(i).getBounds().x+120,diagrama.elementAt(pda-1).getBounds().y+diagrama.elementAt(pda-1).getBounds().height+50);
					diagrama.elementAt(pda).setLocation(punto);

				}
				
				//izquierda arriba
				punto.setLocation(diagrama.elementAt(i).getBounds().x-40,diagrama.elementAt(i+1).getBounds().y);
				diagrama.elementAt(pda+1).setLocation(punto);
				
				
				pia = pda+2; 
				while(diagrama.elementAt(pia) instanceof InputFigure || diagrama.elementAt(pia) instanceof SentenceFigure || diagrama.elementAt(pia) instanceof OutputFigure ||
						diagrama.elementAt(pia) instanceof WhileFigure || diagrama.elementAt(pia) instanceof ForFigure || diagrama.elementAt(pia) instanceof DecisionFigure){
					pia = anidarIf(diagrama,pia); 
				}
				
				//izquierda abajo
				if(diagrama.elementAt(pia-1) instanceof EllipseFigure){
					punto.setLocation(diagrama.elementAt(i).getBounds().x-40,diagrama.elementAt(pia-1).getBounds().y+diagrama.elementAt(pia-1).getBounds().height+70);
					diagrama.elementAt(pia).setLocation(punto);
					
				}
				else{
					punto.setLocation(diagrama.elementAt(i).getBounds().x-40,diagrama.elementAt(pia-1).getBounds().y+diagrama.elementAt(pia-1).getBounds().height+50);
					diagrama.elementAt(pia).setLocation(punto);
					
					
				}
				
				derecha = diagrama.elementAt(pda).getLocation().y;
				izquierda = diagrama.elementAt(pia).getLocation().y;			
				if(derecha>izquierda){
					punto.setLocation(diagrama.elementAt(pia).getLocation().x,derecha);
					diagrama.elementAt(pia).setLocation(punto);
									
				}
				else{
					punto.setLocation(diagrama.elementAt(pda).getLocation().x,izquierda);
					diagrama.elementAt(pda).setLocation(punto);
					
					
					
				}
				punto.setLocation(diagrama.elementAt(i).getBounds().x,diagrama.elementAt(pia).getBounds().y-20);
				diagrama.elementAt(pia+1).setBounds(new Rectangle(punto.x, punto.y,80,40));
				
				
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
					
					
					
					
					punto.setLocation(diagrama.elementAt(pda).getBounds().x+der2,diagrama.elementAt(pda).getBounds().y);
					diagrama.elementAt(pda).setLocation(punto);
					
					
					
					
					punto.setLocation(diagrama.elementAt(pda+1).getBounds().x-der2,diagrama.elementAt(pda+1).getBounds().y);
					diagrama.elementAt(pda+1).setLocation(punto);
					
					
					
					punto.setLocation(diagrama.elementAt(pia).getBounds().x-der2,diagrama.elementAt(pia).getBounds().y);
					diagrama.elementAt(pia).setLocation(punto);
					
					
					pda=i+2;
					while(diagrama.elementAt(pda) instanceof InputFigure || diagrama.elementAt(pda) instanceof SentenceFigure || diagrama.elementAt(pda) instanceof OutputFigure ||
							diagrama.elementAt(pda) instanceof WhileFigure || diagrama.elementAt(pda) instanceof ForFigure || diagrama.elementAt(pda) instanceof DecisionFigure){
						pda = anidarIf(diagrama,pda); 
					}
					pia = pda+2; 
					while(diagrama.elementAt(pia) instanceof InputFigure || diagrama.elementAt(pia) instanceof SentenceFigure || diagrama.elementAt(pia) instanceof OutputFigure ||
							diagrama.elementAt(pia) instanceof WhileFigure || diagrama.elementAt(pia) instanceof ForFigure || diagrama.elementAt(pia) instanceof DecisionFigure){
						pia = anidarIf(diagrama,pia); 
					}
				}
				i=pia+2;
				break;
			}
			else if(diagrama.elementAt(i) instanceof ForFigure || diagrama.elementAt(i) instanceof WhileFigure){
				i = anidarFW(diagrama,i)-1;
			}
			else if(diagrama.elementAt(i) instanceof EllipseFigure){
				break;
			}
			else{
				punto.setLocation(x,diagrama.elementAt(i-1).getBounds().y+diagrama.elementAt(i-1).getBounds().height+50);
				diagrama.elementAt(i).setLocation(punto);
				
				
				
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
	 * @param 
	 * @param i
	 * @return int
	 */
	public int anidarFW(Vector<FigureStructure> diagrama ,int i){
	    int x,pda;
		while(true){
			if(diagrama.elementAt(i-1) instanceof EllipseFigure){
				x = diagrama.elementAt(i-1).getBounds().x-40;
			}
			else{
				x = diagrama.elementAt(i-1).getBounds().x;
			}	
			if(diagrama.elementAt(i) instanceof ForFigure || diagrama.elementAt(i) instanceof WhileFigure){
				pda=i+1;
				punto.setLocation(x,diagrama.elementAt(i-1).getBounds().y+diagrama.elementAt(i-1).getBounds().height+50);
				diagrama.elementAt(i).setLocation(punto);
				
				
				
				while(diagrama.elementAt(pda) instanceof InputFigure || diagrama.elementAt(pda) instanceof SentenceFigure || diagrama.elementAt(pda) instanceof OutputFigure ||
						diagrama.elementAt(pda) instanceof WhileFigure || diagrama.elementAt(pda) instanceof ForFigure || diagrama.elementAt(pda) instanceof DecisionFigure){
					pda = anidarFW(diagrama,pda);
				}
				
				//abajo
				punto.setLocation(diagrama.elementAt(pda-1).getBounds().x +diagrama.elementAt(pda-1).getBounds().width/2 ,diagrama.elementAt(pda-1).getBounds().y+90);
				diagrama.elementAt(pda).setLocation(punto);
				
				
				
				//abajo izquierda
				punto.setLocation(diagrama.elementAt(i).getBounds().x-35,diagrama.elementAt(pda).getBounds().y);
				diagrama.elementAt(pda+1).setLocation(punto);
				
				
				
				//izquierda arriba
				punto.setLocation(diagrama.elementAt(pda+1).getBounds().x,diagrama.elementAt(i).getBounds().y + diagrama.elementAt(i).getBounds().height/2);
				diagrama.elementAt(pda+2).setLocation(punto);
				
				
				//derecha arriba
				punto.setLocation(diagrama.elementAt(i).getBounds().x+diagrama.elementAt(i).getBounds().width+35,diagrama.elementAt(i).getBounds().y + diagrama.elementAt(i).getBounds().height/2);
				diagrama.elementAt(pda+3).setLocation(punto);
				
				
				//derecha abajo
				punto.setLocation(diagrama.elementAt(pda+3).getBounds().x,diagrama.elementAt(pda).getBounds().y + 30);
				diagrama.elementAt(pda+4).setLocation(punto);
				
				//abajo abajo
				punto.setLocation(diagrama.elementAt(pda).getBounds().x,diagrama.elementAt(pda+4).getBounds().y);
				diagrama.elementAt(pda+5).setLocation(punto);
				
				int der = (int)recorridoFW(diagrama,i+1);
               
				if(der>0.0){
					der-=40;
					//abajo izquierda
					punto.setLocation(diagrama.elementAt(pda+1).getBounds().x-der,diagrama.elementAt(pda).getBounds().y);
					diagrama.elementAt(pda+1).setLocation(punto);
					
					
					
					
					//izquierda arriba
					punto.setLocation(diagrama.elementAt(pda+1).getBounds().x,diagrama.elementAt(i).getBounds().y + diagrama.elementAt(i).getBounds().height/2);
					diagrama.elementAt(pda+2).setLocation(punto);
					
					
					//derecha arriba
					punto.setLocation(diagrama.elementAt(pda+3).getBounds().x+der,diagrama.elementAt(i).getBounds().y + diagrama.elementAt(i).getBounds().height/2);
					diagrama.elementAt(pda+3).setLocation(punto);
					
					
					//derecha abajo
					punto.setLocation(diagrama.elementAt(pda+3).getBounds().x,diagrama.elementAt(pda).getBounds().y + 30);
					diagrama.elementAt(pda+4).setLocation(punto);
					
				}
				else{
					//abajo izquierda
					punto.setLocation(diagrama.elementAt(pda+1).getBounds().x+der,diagrama.elementAt(pda).getBounds().y);
					diagrama.elementAt(pda+1).setLocation(punto);
					
					
					
					//izquierda arriba
					punto.setLocation(diagrama.elementAt(pda+1).getBounds().x,diagrama.elementAt(i).getBounds().y + diagrama.elementAt(i).getBounds().height/2);
					diagrama.elementAt(pda+2).setLocation(punto);
					
					
					
					//derecha arriba
					punto.setLocation(diagrama.elementAt(pda+3).getBounds().x-der,diagrama.elementAt(i).getBounds().y + diagrama.elementAt(i).getBounds().height/2);
					diagrama.elementAt(pda+3).setLocation(punto);
					
					
					//derecha abajo
					punto.setLocation(diagrama.elementAt(pda+3).getBounds().x,diagrama.elementAt(pda).getBounds().y + 30);
					diagrama.elementAt(pda+4).setLocation(punto);
					
				}
				i= pda+6;
				break;
			}
			else if(diagrama.elementAt(i) instanceof DecisionFigure){
				i = anidarIf(diagrama,i)-1;
			}
			else if(diagrama.elementAt(i) instanceof EllipseFigure){
				break;
			}
			else{
				punto.setLocation(x,diagrama.elementAt(i-1).getBounds().y+diagrama.elementAt(i-1).getBounds().height+50);
				diagrama.elementAt(i).setLocation(punto);
				
				
			}
			i++;
		}
		return i;
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
	public int ifIzquierda(Vector<FigureStructure> diagrama,int i,int fin){
		int cont=0,cont2=0;
		boolean bandera = true;
		while(true){	
			if(diagrama.elementAt(i) instanceof DecisionFigure){
				cont++;
				i=DiagramCiclePath.getDecisionEndPointIndex(diagrama,i);
			}
			else if(diagrama.elementAt(i) instanceof ForFigure || diagrama.elementAt(i) instanceof WhileFigure && bandera){
				if(bandera){cont++;}
				i=DiagramCiclePath.getLastForPointIndex(diagrama,i)+4;
				if(cont>cont2){
					cont2=cont;  
				}
				cont-=1;
				bandera = false;
			}
			else if(diagrama.elementAt(i) instanceof EllipseFigure){
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
	public int ifIzquierda2(Vector<FigureStructure> diagrama,int i,int fin){
		int cont=0,cont2=0;
		while(true){
			if(diagrama.elementAt(i) instanceof DecisionFigure){
				cont =cont + diagrama.elementAt(i+1).getBounds().x-(diagrama.elementAt(i).getBounds().x+diagrama.elementAt(i).getBounds().width);
				cont-=35;
				i=DiagramCiclePath.getDecisionEndPointIndex(diagrama,i);
			}
			else if(diagrama.elementAt(i) instanceof ForFigure || diagrama.elementAt(i) instanceof WhileFigure){
				cont =cont + diagrama.elementAt(DiagramCiclePath.getLastForPointIndex(diagrama,i)+3).getBounds().x-(diagrama.elementAt(i).getBounds().x+diagrama.elementAt(i).getBounds().width);
				i = DiagramCiclePath.getLastForPointIndex(diagrama,i)+5;
				cont-=35;
			}
			else if(diagrama.elementAt(i) instanceof EllipseFigure){
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
	 * Recibe un vector de figuras y la localizacion del
	 * inicio y el fin del lado derecho de un if
	 * y establece la localizacion de las figuras que contiene.
	 * @param diagrama
	 * @param i
	 * @param fin
	 * @return int
	 */
	public int ifDerecha(Vector<FigureStructure> diagrama,int i,int fin){
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
				i=DiagramCiclePath.getLastForPointIndex(diagrama,i)+5;
				bandera = false;
				if(cont>cont2){
					cont2=cont;  
				}
				cont-=1;
			}
			else if(diagrama.elementAt(i) instanceof EllipseFigure){
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
	public int ifDerecha2(Vector<FigureStructure> diagrama,int i,int fin){
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
				cont =cont + diagrama.elementAt(DiagramCiclePath.getLastForPointIndex(diagrama,i)+3).getBounds().x-(diagrama.elementAt(i).getBounds().x+diagrama.elementAt(i).getBounds().width);
				cont-=35;
				if(cont>cont2){
					cont2=cont;  
				}
				i = DiagramCiclePath.getLastForPointIndex(diagrama,i)+5;
			}
			else if(diagrama.elementAt(i) instanceof EllipseFigure){
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
	 * Este metodo recorre toda la
	 * anidacion de un For o While y 
	 * regresa la distancia de la figura
	 * mas alejada del ciclo.
	 * 
	 * @param diagrama
	 * @param i
	 * @return doble
	 */
	public double recorridoFW(Vector<FigureStructure> diagrama,int i){
		double x=0.0,x2=0.0,xDer=0.0,xIzq=0.0;
		boolean bandera=false,bandera2=false;
		int fin = DiagramCiclePath.getLastForPointIndex(diagrama,i-1);
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
	/**
	 * 
	 * Este metodo establece las nuevas
	 * propiedades de las figuras del diagrama
	 * de flujo.
	 * 
	 * @param diagrama
	 */
	public void getFigura(Vector<FigureStructure> diagrama,boolean eventos){
		Point pt = new Point();
		if(eventos){
			if(!MainWindow.getComponents().getByStepComponents().isStepByStep()){
				new SelectionListener(diagrama.elementAt(0),selec,tab);
			}
			for(int x =1;x<diagrama.size();x++ ){
				if(diagrama.elementAt(x) instanceof DecisionFigure){
					pt = diagrama.elementAt(x).getLocation();
					DecisionFigure figuras = (DecisionFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					DecisionFigure figura = new DecisionFigure();
					figura.instructionComposed = figuras.instructionComposed;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setPasoAPaso(figuras.isPasoAPaso());
					figura.setId(figuras.getId());
					figura.setLocation(pt);
					diagrama.insertElementAt(figura,x);
					if(!MainWindow.getComponents().getByStepComponents().isStepByStep()){
						new DoubleClickListener(diagrama.elementAt(x),selec,tab);
						new ContextualMenuListener(diagrama.elementAt(x),selec,tab);
						new SelectionListener(diagrama.elementAt(x),selec,tab);
					}
				}
				else if(diagrama.elementAt(x) instanceof ForFigure){
					pt = diagrama.elementAt(x).getLocation();
					ForFigure figuras = (ForFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					ForFigure figura = new ForFigure();
					figura.instructionComposed = figuras.instructionComposed;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setPasoAPaso(figuras.isPasoAPaso());
					figura.setId(figuras.getId());
					figura.setLocation(pt);
					diagrama.insertElementAt(figura,x);
					if(!MainWindow.getComponents().getByStepComponents().isStepByStep()){
						new DoubleClickListener(diagrama.elementAt(x),selec,tab);
						new ContextualMenuListener(diagrama.elementAt(x),selec,tab);
						new SelectionListener(diagrama.elementAt(x),selec,tab);
					}
				}
				else if(diagrama.elementAt(x) instanceof WhileFigure){
					pt = diagrama.elementAt(x).getLocation();
					WhileFigure figuras = (WhileFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					WhileFigure figura = new WhileFigure();
					figura.instructionComposed = figuras.instructionComposed;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setPasoAPaso(figuras.isPasoAPaso());
					figura.setId(figuras.getId());
					figura.setLocation(pt);
					diagrama.insertElementAt(figura,x);
					if(!MainWindow.getComponents().getByStepComponents().isStepByStep()){
						new DoubleClickListener(diagrama.elementAt(x),selec,tab);
						new ContextualMenuListener(diagrama.elementAt(x),selec,tab);
						new SelectionListener(diagrama.elementAt(x),selec,tab);
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
					figura.setId(figuras.getId());
					figura.setLocation(pt);
					diagrama.insertElementAt(figura,x);
					if(!MainWindow.getComponents().getByStepComponents().isStepByStep()){
						new origami.graphics.listeners.DoubleClickListener(diagrama.elementAt(x),selec,tab);
						new ContextualMenuListener(diagrama.elementAt(x),selec,tab);
						new SelectionListener(diagrama.elementAt(x),selec,tab);
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
					figura.setId(figuras.getId());
					figura.setLocation(pt);
					diagrama.insertElementAt(figura,x);
					if(!MainWindow.getComponents().getByStepComponents().isStepByStep()){
						new origami.graphics.listeners.DoubleClickListener(diagrama.elementAt(x),selec,tab);
						new ContextualMenuListener(diagrama.elementAt(x),selec,tab);
						new SelectionListener(diagrama.elementAt(x),selec,tab);
					}
				}
				else if(diagrama.elementAt(x) instanceof SentenceFigure){
					pt = diagrama.elementAt(x).getLocation();
					SentenceFigure figuras = (SentenceFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					SentenceFigure figura = new SentenceFigure();
					figura.instruction = figuras.instruction;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setPasoAPaso(figuras.isPasoAPaso());
					figura.setId(figuras.getId());
					figura.setLocation(pt);
					diagrama.insertElementAt(figura,x);
					if(!MainWindow.getComponents().getByStepComponents().isStepByStep()){
						new DoubleClickListener(diagrama.elementAt(x),selec,tab);
						new ContextualMenuListener(diagrama.elementAt(x),selec,tab);
						new SelectionListener(diagrama.elementAt(x),selec,tab);
					}
				}
				else if(diagrama.elementAt(x) instanceof DecisionFigureEnd){
					pt = diagrama.elementAt(x).getLocation();
					diagrama.removeElementAt(x);
					DecisionFigureEnd ini = new DecisionFigureEnd();
					ini.setLocation(pt);
					diagrama.insertElementAt(ini,x);
				}
				else if(diagrama.elementAt(x) instanceof CircleFigure){
					pt = diagrama.elementAt(x).getLocation();
					CircleFigure fig = (CircleFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					CircleFigure ini = new CircleFigure();
					ini.setMessage(fig.getMessage());
					ini.setListaPosicion(fig.getPosicion());
					ini.setLocation(pt);
					diagrama.insertElementAt(ini,x);
				}
				else if(diagrama.elementAt(x) instanceof EllipseFigure){
				    	pt = diagrama.elementAt(x).getLocation();
					diagrama.removeElementAt(x);
					EllipseFigure ini = new EllipseFigure();
					ini.setLocation(pt);
					diagrama.insertElementAt(ini,x);
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
					figura.instructionComposed = figuras.instructionComposed;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setId(figuras.getId());
					figura.setLocation(pt);
					figura.setPasoAPaso(figuras.isPasoAPaso());
					diagrama.insertElementAt(figura,x);
				}
				else if(diagrama.elementAt(x) instanceof ForFigure){
					pt = diagrama.elementAt(x).getLocation();
					ForFigure figuras = (ForFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					ForFigure figura = new ForFigure();
					figura.instructionComposed = figuras.instructionComposed;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setId(figuras.getId());
					figura.setLocation(pt);
					figura.setPasoAPaso(figuras.isPasoAPaso());
					diagrama.insertElementAt(figura,x);
				}
				else if(diagrama.elementAt(x) instanceof WhileFigure){
					pt = diagrama.elementAt(x).getLocation();
					WhileFigure figuras = (WhileFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					WhileFigure figura = new WhileFigure();
					figura.instructionComposed = figuras.instructionComposed;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setId(figuras.getId());
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
					figura.setId(figuras.getId());
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
					figura.setId(figuras.getId());
					figura.setLocation(pt);
					figura.setPasoAPaso(figuras.isPasoAPaso());
					diagrama.insertElementAt(figura,x);
				}
				else if(diagrama.elementAt(x) instanceof SentenceFigure){
					pt = diagrama.elementAt(x).getLocation();
					SentenceFigure figuras = (SentenceFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					SentenceFigure figura = new SentenceFigure();
					figura.instruction = figuras.instruction;
					figura.setListaPosicion(figuras.getPosicion());
					figura.setId(figuras.getId());
					figura.setLocation(pt);
					figura.setPasoAPaso(figuras.isPasoAPaso());
					diagrama.insertElementAt(figura,x);
				}
				else if(diagrama.elementAt(x) instanceof CircleFigure){
					pt = diagrama.elementAt(x).getLocation();
					CircleFigure fig = (CircleFigure)diagrama.elementAt(x);
					diagrama.removeElementAt(x);
					CircleFigure ini = new CircleFigure();
					ini.setMessage(fig.getMessage());
					ini.setListaPosicion(fig.getPosicion());
					ini.setLocation(pt);
					diagrama.insertElementAt(ini,x);
				}
			}
		}
	}

}