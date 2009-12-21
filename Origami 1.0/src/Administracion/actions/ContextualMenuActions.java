package Administracion.actions;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import Administracion.Figura;
import Grafico.MainWindow;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.DecisionFigureEnd;
import Grafico.Figuras.Elipse;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;
import Grafico.VentanaDatos.DecisionFigureDialog;
import Grafico.VentanaDatos.ForFigureDialog;
import Grafico.VentanaDatos.InputFigureDialog;
import Grafico.VentanaDatos.OutputFigureDialog;
import Grafico.VentanaDatos.SentenceFigureDialog;
import Grafico.VentanaDatos.WhileFigureDialog;
import Imagenes.ImageLoader;


public class ContextualMenuActions {
    public static boolean isCut = false;
    
    public void Cortar(Figura fig){
    	int index = 0;
    	for(int x=0;x<MainWindow.getComponentes().diagramas.getHoja().getSizeDiagrama();x++){
    		if(x == MainWindow._selectionAdministrator.getFiguraSeleccionada()){
    			int y=0,x2=0,cont=0;
    			MainWindow._diagramAdministrator.diagrama.removeAllElements();
    			if(fig instanceof DecisionFigure){
    				y = recorridoCiclo(MainWindow.getComponentes().diagramas.getHoja().getDiagrama(),x);
    				y = recorridoCiclo2(MainWindow.getComponentes().diagramas.getHoja().getDiagrama(),y);
    				x2=y-x;
    				while(cont<x2+2){
    					MainWindow._diagramAdministrator.diagrama.add(index,MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x));
    					MainWindow.getComponentes().diagramas.getHoja().removeFigureIndexOf(x);
    					cont++;
    					index++;
    				}
    			} 
    			else if(fig instanceof ForFigure){
    				y = recorridoCiclo3(MainWindow.getComponentes().diagramas.getHoja().getDiagrama(),x);
    				x2=y-x;
    				while(cont<x2+6){
    					MainWindow._diagramAdministrator.diagrama.add(index,MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x));
    					MainWindow.getComponentes().diagramas.getHoja().removeFigureIndexOf(x);
    					cont++;
    					index++;
    				}
    			}
    			else if(fig instanceof WhileFigure){
    				y = recorridoCiclo3(MainWindow.getComponentes().diagramas.getHoja().getDiagrama(),x);
    				x2=y-x;
    				while(cont<x2+6){
    					MainWindow._diagramAdministrator.diagrama.add(index,MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x));
    					MainWindow.getComponentes().diagramas.getHoja().removeFigureIndexOf(x);
    					cont++;
    					index++;
    				}
    			}
    			else{
    				MainWindow._diagramAdministrator.diagrama.add(index,MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x));
    				MainWindow.getComponentes().diagramas.getHoja().removeFigureIndexOf(x);
    			}
    			isCut = true;
    			MainWindow._selectionAdministrator.setFiguraSeleccionada(-1);
    			Repintar();	
    			break;
    		}
    	}
    }
    public void Copiar(Figura fig){
    	if(isCut){
    		MainWindow._diagramAdministrator.diagrama.removeAllElements();
    		isCut = false;
    	}
    	int index = 0;
    	for(int x=0;x<MainWindow.getComponentes().diagramas.getHoja().getSizeDiagrama();x++){
    		if(x == MainWindow._selectionAdministrator.getFiguraSeleccionada()){
    			int y=0,x2=0,cont=0;
    			MainWindow._diagramAdministrator.diagrama.removeAllElements();
    			if(fig instanceof DecisionFigure){
    				y = recorridoCiclo(MainWindow.getComponentes().diagramas.getHoja().getDiagrama(),x);
    				y = recorridoCiclo2(MainWindow.getComponentes().diagramas.getHoja().getDiagrama(),y);
    				x2=y-x;
    				while(cont<x2+2){
    					if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x) instanceof DecisionFigure){
    						DecisionFigure copia = new DecisionFigure();
    						DecisionFigure actual = (DecisionFigure)MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x);
    						for(int j=0; j<actual.instruction.instruccion.size(); j++){
    							copia.instruction.instruccion.add(actual.instruction.instruccion.elementAt(j));	
    						}
    						MainWindow._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x) instanceof ForFigure){
    						ForFigure copia = new ForFigure();
    						ForFigure actual = (ForFigure)MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x);
    						for(int j=0; j<actual.instruction.instruccion.size(); j++){
    							copia.instruction.instruccion.add(actual.instruction.instruccion.elementAt(j));	
    						}
    						MainWindow._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x) instanceof WhileFigure){
    						WhileFigure copia = new WhileFigure();
    						WhileFigure actual = (WhileFigure)MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x);
    						for(int j=0; j<actual.instruccion.instruccion.size(); j++){
    							copia.instruccion.instruccion.add(actual.instruccion.instruccion.elementAt(j));	
    						}
    						MainWindow._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x) instanceof SentenceFigure){
    						SentenceFigure copia = new SentenceFigure();
    						SentenceFigure actual = (SentenceFigure)MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x);
    						copia.instruccion.instruccion = actual.instruccion.instruccion;
    						MainWindow._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x) instanceof InputFigure){
    						InputFigure copia = new InputFigure();
    						InputFigure actual = (InputFigure)MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x);
    						copia.instruction.instruccion = actual.instruction.instruccion;
    						MainWindow._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x) instanceof OutputFigure){
    						OutputFigure copia = new OutputFigure();
    						OutputFigure actual = (OutputFigure)MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x);
    						copia.instruction.instruccion = actual.instruction.instruccion;
    						MainWindow._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else{
    						MainWindow._diagramAdministrator.diagrama.add(index,MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x));
    					}
    					cont++;
    					index++;
    					x++;
    				}
    			} 
    			else if(fig instanceof ForFigure || fig instanceof WhileFigure){
    				y = recorridoCiclo3(MainWindow.getComponentes().diagramas.getHoja().getDiagrama(),x);
    				x2=y-x;
    				while(cont<x2+6){
    					if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x) instanceof DecisionFigure){
    						DecisionFigure copia = new DecisionFigure();
    						DecisionFigure actual = (DecisionFigure)MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x);
    						for(int j=0; j<actual.instruction.instruccion.size(); j++){
    							copia.instruction.instruccion.add(actual.instruction.instruccion.elementAt(j));	
    						}
    						MainWindow._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x) instanceof ForFigure){
    						ForFigure copia = new ForFigure();
    						ForFigure actual = (ForFigure)MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x);
    						for(int j=0; j<actual.instruction.instruccion.size(); j++){
    							copia.instruction.instruccion.add(actual.instruction.instruccion.elementAt(j));	
    						}
    						MainWindow._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x) instanceof WhileFigure){
    						WhileFigure copia = new WhileFigure();
    						WhileFigure actual = (WhileFigure)MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x);
    						for(int j=0; j<actual.instruccion.instruccion.size(); j++){
    							copia.instruccion.instruccion.add(actual.instruccion.instruccion.elementAt(j));	
    						}
    						MainWindow._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x) instanceof SentenceFigure){
    						SentenceFigure copia = new SentenceFigure();
    						SentenceFigure actual = (SentenceFigure)MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x);
    						copia.instruccion.instruccion = actual.instruccion.instruccion;
    						MainWindow._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x) instanceof InputFigure){
    						InputFigure copia = new InputFigure();
    						InputFigure actual = (InputFigure)MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x);
    						copia.instruction.instruccion = actual.instruction.instruccion;
    						MainWindow._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x) instanceof OutputFigure){
    						OutputFigure copia = new OutputFigure();
    						OutputFigure actual = (OutputFigure)MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x);
    						copia.instruction.instruccion = actual.instruction.instruccion;
    						MainWindow._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else{
    						MainWindow._diagramAdministrator.diagrama.add(index,MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x));
    					}
    					cont++;
    					index++;
    					x++;
    				}
    			}
    			else{
    				if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x) instanceof InputFigure){
    					InputFigure figura = new InputFigure();
    					InputFigure figura2 = (InputFigure)MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf( x );
    					figura.instruction.instruccion = figura2.instruction.instruccion;
    					MainWindow._diagramAdministrator.diagrama.add(index,figura);
    				}
    				else if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x) instanceof OutputFigure){
    					OutputFigure figura = new OutputFigure();
    					OutputFigure figura2 = (OutputFigure)MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(x );
    					figura.instruction.instruccion = figura2.instruction.instruccion;
    					MainWindow._diagramAdministrator.diagrama.add(index,figura);
    
    				}
    				else{
    					SentenceFigure figura = new SentenceFigure();
    					SentenceFigure figura2 = (SentenceFigure)MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf( x);
    					figura.instruccion.instruccion = figura2.instruccion.instruccion;
    					MainWindow._diagramAdministrator.diagrama.add(index,figura);
    				}
    			}
    			break;
    		}
    	}
    }
    public void Pegar(Figura fig){
    	int w = 0, y=0 ,lim=0;
    	final Shell shell = new Shell(MainWindow.shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
    	if(!isCut){
    		for(int i=0; i<MainWindow._diagramAdministrator.diagrama.size(); i++){
    			if(MainWindow._diagramAdministrator.diagrama.elementAt(i) instanceof Elipse){
    				Elipse nueva = new Elipse();
    				MainWindow._diagramAdministrator.diagrama.remove(i);
    				MainWindow._diagramAdministrator.diagrama.insertElementAt(nueva,i);
    			}
    			else if(MainWindow._diagramAdministrator.diagrama.elementAt(i) instanceof DecisionFigureEnd){
    				DecisionFigureEnd nueva = new DecisionFigureEnd();
    				MainWindow._diagramAdministrator.diagrama.remove(i);
    				MainWindow._diagramAdministrator.diagrama.insertElementAt(nueva,i);
    			}
    			else if(MainWindow._diagramAdministrator.diagrama.elementAt(i) instanceof DecisionFigure){
    				DecisionFigure nueva = new DecisionFigure();
    				DecisionFigure aux = (DecisionFigure)MainWindow._diagramAdministrator.diagrama.elementAt(i);
    				for(int x=0;x<aux.instruction.instruccion.size(); x++){
    					nueva.instruction.instruccion.add(x,aux.instruction.instruccion.elementAt(x));
    				}
    				MainWindow._diagramAdministrator.diagrama.remove(i);
    				MainWindow._diagramAdministrator.diagrama.insertElementAt(nueva,i);
    			}
    			else if(MainWindow._diagramAdministrator.diagrama.elementAt(i) instanceof ForFigure){
    				ForFigure nueva = new ForFigure();
    				ForFigure aux = (ForFigure)MainWindow._diagramAdministrator.diagrama.elementAt(i);
    				for(int x=0;x<aux.instruction.instruccion.size(); x++){
    					nueva.instruction.instruccion.add(x,aux.instruction.instruccion.elementAt(x));
    				}
    				MainWindow._diagramAdministrator.diagrama.remove(i);
    				MainWindow._diagramAdministrator.diagrama.insertElementAt(nueva,i);
    			}
    			else if(MainWindow._diagramAdministrator.diagrama.elementAt(i) instanceof WhileFigure){
    				WhileFigure nueva = new WhileFigure();
    				WhileFigure aux = (WhileFigure)MainWindow._diagramAdministrator.diagrama.elementAt(i);
    				for(int x=0;x<aux.instruccion.instruccion.size(); x++){
    					nueva.instruccion.instruccion.add(x,aux.instruccion.instruccion.elementAt(x));
    				}
    				MainWindow._diagramAdministrator.diagrama.remove(i);
    				MainWindow._diagramAdministrator.diagrama.insertElementAt(nueva,i);
    			}
    			else if(MainWindow._diagramAdministrator.diagrama.elementAt(i) instanceof OutputFigure){
    				OutputFigure nueva = new OutputFigure();
    				OutputFigure aux = (OutputFigure)MainWindow._diagramAdministrator.diagrama.elementAt(i);
    				nueva.instruction.instruccion=aux.instruction.instruccion;
    				MainWindow._diagramAdministrator.diagrama.remove(i);
    				MainWindow._diagramAdministrator.diagrama.insertElementAt(nueva,i);
    			}
    			else if(MainWindow._diagramAdministrator.diagrama.elementAt(i) instanceof InputFigure){
    				InputFigure nueva = new InputFigure();
    				InputFigure aux = (InputFigure)MainWindow._diagramAdministrator.diagrama.elementAt(i);
    				nueva.instruction.instruccion=aux.instruction.instruccion;
    				MainWindow._diagramAdministrator.diagrama.remove(i);
    				MainWindow._diagramAdministrator.diagrama.insertElementAt(nueva,i);
    			}
    			else if(MainWindow._diagramAdministrator.diagrama.elementAt(i) instanceof SentenceFigure){
    				SentenceFigure nueva = new SentenceFigure();
    				SentenceFigure aux = (SentenceFigure)MainWindow._diagramAdministrator.diagrama.elementAt(i);
    				nueva.instruccion.instruccion=aux.instruccion.instruccion;
    				MainWindow._diagramAdministrator.diagrama.remove(i);
    				MainWindow._diagramAdministrator.diagrama.insertElementAt(nueva,i);
    			}
    		}
    	}
    	Vector<Figura> temporal = new Vector<Figura>();
    	for(int x=0;x<MainWindow.getComponentes().diagramas.getHoja().getSizeDiagrama();x++){
    		if(x == MainWindow._selectionAdministrator.getFiguraSeleccionada()){
    			if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(MainWindow._selectionAdministrator.getFiguraSeleccionada()) instanceof DecisionFigure){
    				shell.setBounds(315, 260, 300, 140);
    				Button izquierda = new Button(shell,SWT.PUSH);
    				izquierda.setBounds(5, 75, 75, 25);
    				izquierda.setText("Izquierda");
    				Button derecha = new Button(shell,SWT.PUSH);
    				derecha.setBounds(215, 75, 75, 25);
    				derecha.setText("Derecha");
    				Button cancelar = new Button(shell,SWT.PUSH);
    				cancelar.setBounds(110, 75,75, 25);
    				cancelar.setText("Cancelar");
    				Label pregunta = new Label(shell, SWT.NONE); 
    				FontData fontData = new FontData();
    				fontData.setHeight(11);
    				Font newFont = new Font(MainWindow.display, fontData);
    				pregunta.setBounds(85, 30, 340, 30);
    				pregunta.setText("¿De que lado deseas pegar?");
    				pregunta.setFont(newFont);
    				Label imagen = new Label(shell, SWT.NONE); 
    				//Image question = new Image(Ventana.display,"imagenes\\Pregunta.PNG");
    				imagen.setImage(ImageLoader.getImage("Pregunta.png"));
    				imagen.setBounds(25,10,50,50);
    				shell.setMaximized(false);
    				shell.setMinimized(false);
    				shell.open();
    				final int copiax=x;
    				izquierda.addSelectionListener(new SelectionAdapter() {
    					public void widgetSelected(SelectionEvent event) {
    						Vector<Figura> temporal = new Vector<Figura>();
    						int x = copiax,y =0,lim=0;
    						for(int u =0; u<MainWindow.getComponentes().diagramas.getHoja().getSizeDiagrama(); u++){
    							temporal.add(u,MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(u));
    						}
    						//*******************************************
    						int posicion = MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(MainWindow._selectionAdministrator.getFiguraSeleccionada()).getBounds().x+MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(MainWindow._selectionAdministrator.getFiguraSeleccionada()).getBounds().width;
    						posicion = MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(MainWindow._selectionAdministrator.getFiguraSeleccionada()+1).getBounds().x-posicion;
    						//*******************************************
    						MainWindow.getComponentes().diagramas.getHoja().removeFigureAllElements();
    						for(int i =0; i<=x; i++){
    							MainWindow.getComponentes().diagramas.getHoja().addFigureIndexOf(i,temporal.elementAt(i));
    							y = i;
    						 }
    						y++;
    						int distancia =  temporal.elementAt(y-1).getBounds().x + temporal.elementAt(y-1).getBounds().height;
    						distancia =  temporal.elementAt(y).getBounds().x - distancia;
    						int cont=0, punto=0;
    						for(int i =MainWindow._selectionAdministrator.getFiguraSeleccionada(); cont<2; i++){
    								if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(MainWindow._selectionAdministrator.getFiguraSeleccionada()).getBounds().x - temporal.elementAt(i).getBounds().x == posicion){
    									cont++;
    								}
    								punto = i;
    						}
    						for(int i =y; i<punto; i++){
    							MainWindow.getComponentes().diagramas.getHoja().insertFigureIndexOf(temporal.elementAt(i), i);
    							y = i;
    						}
    						int j =0;
    						//este es el que se debe de seleccionar punto
    						MainWindow._selectionAdministrator.setFiguraSeleccionada(punto);
    						for(int i =punto; j<MainWindow._diagramAdministrator.diagrama.size(); i++){
    							MainWindow.getComponentes().diagramas.getHoja().insertFigureIndexOf(MainWindow._diagramAdministrator.diagrama.elementAt(j), i);
    							j++;
    							y = i;
    						}
    						y++;
    						lim =temporal.size()-punto;
    						j=0;
    						for(int i =y; j<lim; i++){
    							MainWindow.getComponentes().diagramas.getHoja().insertFigureIndexOf(temporal.elementAt(punto), i);
    							j++;
    							punto++;
    						}
    						if(isCut){
    							MainWindow._diagramAdministrator.diagrama.removeAllElements();
    							isCut = false;
    						}
    						shell.close();
    						Repintar();
    						
    					}
    				});
    				derecha.addSelectionListener(new SelectionAdapter() {
    					public void widgetSelected(SelectionEvent event) {
    						Vector<Figura> temporal = new Vector<Figura>();
    						int x = copiax,y =0,lim=0,w=0;
    						for(int u =0; u<MainWindow.getComponentes().diagramas.getHoja().getSizeDiagrama(); u++){
    							temporal.add(u,MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(u));
    						}
    						MainWindow.getComponentes().diagramas.getHoja().removeFigureAllElements();
    						for(int i =0; i<=x; i++){
    							MainWindow.getComponentes().diagramas.getHoja().addFigureIndexOf(i,temporal.elementAt(i));
    							y = i;
    						 }
    							y++;
    							MainWindow.getComponentes().diagramas.getHoja().insertFigureIndexOf(temporal.elementAt(y), y);
    						y=0;
    						lim =temporal.size()-x-1;
    						w=x+1;
    						//este es el que se debe de seleccionar w
    						MainWindow._selectionAdministrator.setFiguraSeleccionada(w+1);
    						for(int i =w; i<x+1+MainWindow._diagramAdministrator.diagrama.size(); i++){
    							MainWindow.getComponentes().diagramas.getHoja().addFigureIndexOf(i+1,MainWindow._diagramAdministrator.diagrama.elementAt(y));
    							y++;
    							w++;
    						}
    						y = 0;
    						x++;
    						for(int i =w; y<lim-1; i++){
    							MainWindow.getComponentes().diagramas.getHoja().addFigureIndexOf(i+1,temporal.elementAt(x+1));
    							y++;
    							x++;
    						}
    						if(isCut){
    							MainWindow._diagramAdministrator.diagrama.removeAllElements();
    							isCut = false;
    						}
    						shell.close();
    						Repintar();
    					}
    				});
    				cancelar.addSelectionListener(new SelectionAdapter() {
    					public void widgetSelected(SelectionEvent event) {
    						shell.close();
    					}
    				});
    			}
    			else{
    				for(int u =0; u<MainWindow.getComponentes().diagramas.getHoja().getSizeDiagrama(); u++){
    					temporal.add(u,MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(u));
    				}
    				MainWindow.getComponentes().diagramas.getHoja().removeFigureAllElements();
    				for(int i =0; i<=x; i++){
    					MainWindow.getComponentes().diagramas.getHoja().addFigureIndexOf(i,temporal.elementAt(i));
    					y = i;
    				}
    				y++;
    				y=0;
    				lim =temporal.size()-x-1;
    				w=x+1;
    				//esta es la que se debe de seleccionar w
    				MainWindow._selectionAdministrator.setFiguraSeleccionada(w);
    				for(int i =w; i<x+1+MainWindow._diagramAdministrator.diagrama.size(); i++){
    					MainWindow.getComponentes().diagramas.getHoja().addFigureIndexOf(i,MainWindow._diagramAdministrator.diagrama.elementAt(y));
    					y++;
    					w++;
    				}
    				y = 0;
    				x++;
    				for(int i =w; y<lim; i++){
    					MainWindow.getComponentes().diagramas.getHoja().addFigureIndexOf(i,temporal.elementAt(x));
    					y++;
    					x++;
    				}
    				if(isCut){
    					MainWindow._diagramAdministrator.diagrama.removeAllElements();
    					isCut = false;
    				}
    				Repintar();
    			}
    		}
    	}
    }
    public void insertarFigura(final Figura inser){
    	final int i = MainWindow._selectionAdministrator.getFiguraSeleccionada();
    	if(MainWindow.getComponentes().diagramas.getHoja().getAdminDiagrama().diagrama.elementAt(i) instanceof DecisionFigure){
    		final Shell shell = new Shell(MainWindow.shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL); 
    		shell.setBounds(315, 260, 400, 140);
    		Button izquierda = new Button(shell,SWT.PUSH);
    		izquierda.setBounds(5, 75, 75, 25);
    		izquierda.setText("Izquierda");
    		Button derecha = new Button(shell,SWT.PUSH);
    		derecha.setBounds(215, 75, 75, 25);
    		derecha.setText("Derecha");
    		Button cancelar = new Button(shell,SWT.PUSH);
    		cancelar.setBounds(110, 75,75, 25);
    		cancelar.setText("Cancelar");
    		Label pregunta = new Label(shell, SWT.NONE); 
    		FontData fontData = new FontData();
    		fontData.setHeight(11);
    		Font newFont = new Font(MainWindow.display, fontData);
    		pregunta.setBounds(85, 30, 340, 30);
    		pregunta.setText("¿De que lado deseas insertar la nueva figura?");
    		pregunta.setFont(newFont);
    		Label imagen = new Label(shell, SWT.NONE); 
    		//Image question = new Image(Ventana.display,CargarImagenes.getImagen("Pregunta.PNG"));
    		imagen.setImage(ImageLoader.getImage("Pregunta.png"));
    		imagen.setBounds(25,10,50,50);
    		shell.setMaximized(false);
    		shell.setMinimized(false);
    		shell.open();
    		izquierda.addSelectionListener(new SelectionAdapter() {
    			public void widgetSelected(SelectionEvent event) {
    				int distan = MainWindow.getComponentes().diagramas.getHoja().getDiagrama().elementAt(MainWindow._selectionAdministrator.getFiguraSeleccionada()+1).getBounds().x-(MainWindow.getComponentes().diagramas.getHoja().getDiagrama().elementAt(MainWindow._selectionAdministrator.getFiguraSeleccionada()).getBounds().x+
    				MainWindow.getComponentes().diagramas.getHoja().getDiagrama().elementAt(MainWindow._selectionAdministrator.getFiguraSeleccionada()).getBounds().width);
    				for(int j=MainWindow._selectionAdministrator.getFiguraSeleccionada()+1; j<MainWindow.getComponentes().diagramas.getHoja().getSizeDiagrama(); j++){
    					if(MainWindow.getComponentes().diagramas.getHoja().getDiagrama().elementAt(MainWindow._selectionAdministrator.getFiguraSeleccionada()).getBounds().x-MainWindow.getComponentes().diagramas.getHoja().getDiagrama().elementAt(j).getBounds().x == distan){
    						MainWindow.getComponentes().diagramas.getHoja().getAdminDiagrama().ordenar(j, inser);
    						MainWindow._selectionAdministrator.setFiguraSeleccionada(j+1);
    						MainWindow.getComponentes().diagramas.getHoja().addFigure();
    						break;
    					}
    				}
    				shell.close();
    				Repintar();
    				
    			}
    		});
    		derecha.addSelectionListener(new SelectionAdapter() {
    			public void widgetSelected(SelectionEvent event) {
    				MainWindow.getComponentes().diagramas.getHoja().getAdminDiagrama().ordenar(i+1, inser);
    				MainWindow._selectionAdministrator.setFiguraSeleccionada(i+2);
    				MainWindow.getComponentes().diagramas.getHoja().addFigure();
    				shell.close();
    				Repintar();
    			}
    		});
    		cancelar.addSelectionListener(new SelectionAdapter() {
    			public void widgetSelected(SelectionEvent event) {
    				shell.close();
    			}
    		});
    	}
    	else{
    		MainWindow.getComponentes().diagramas.getHoja().getAdminDiagrama().ordenar(i, inser);
    		MainWindow.getComponentes().diagramas.getHoja().addFigure();
    		MainWindow.getComponentes().diagramas.getHoja().guardarRetroceso();
    		MainWindow.getComponentes().diagramas.getTabItem().getSave().setSave(false);
    	}
    }
    public void Eliminar(Figura fig){
    	String tipo ="";
    	for(int x=0;x<MainWindow.getComponentes().diagramas.getHoja().getSizeDiagrama();x++){
    		if(x == MainWindow._selectionAdministrator.getFiguraSeleccionada()){
    			int y=0,x2=0,cont=0;
    			if(fig instanceof DecisionFigure){
    				tipo="si";
    				y = recorridoCiclo(MainWindow.getComponentes().diagramas.getHoja().getDiagrama(),x);
    				y = recorridoCiclo2(MainWindow.getComponentes().diagramas.getHoja().getDiagrama(),y);
    				x2=y-x;
    				while(cont<x2+2){
    					MainWindow.getComponentes().diagramas.getHoja().removeFigureIndexOf(x);
    					cont++;
    				}
    			} 
    			else if(fig instanceof ForFigure){
    				tipo="para";
    				y = recorridoCiclo3(MainWindow.getComponentes().diagramas.getHoja().getDiagrama(),x);
    				x2=y-x;
    				while(cont<x2+6){
    					MainWindow.getComponentes().diagramas.getHoja().removeFigureIndexOf(x);
    					cont++;
    				}
    			}
    			else if(fig instanceof WhileFigure){
    				tipo="mientras";
    				y = recorridoCiclo3(MainWindow.getComponentes().diagramas.getHoja().getDiagrama(),x);
    				x2=y-x;
    				while(cont<x2+6){
    					MainWindow.getComponentes().diagramas.getHoja().removeFigureIndexOf(x);
    					cont++;
    				}
    			}
    			else{
    				MainWindow.getComponentes().diagramas.getHoja().removeFigureIndexOf(x);
    				if(fig instanceof OutputFigure){
    					tipo="Salida";
    				}
    				else if(fig instanceof InputFigure){
    					tipo="Entrada";
    				}
    				else{
    					tipo="Proceso";
    				}
    			}
    			MainWindow.getComponentes().diagramas.getTabItem().getInfo().addInformation("/D - Se elimino una figura de tipo \" "+tipo+"\"");
    			MainWindow.getComponentes().diagramas.getTabItem().getInfo().setDiagrama(MainWindow.getComponentes().diagramas.getHoja().getDiagrama());
    			MainWindow._selectionAdministrator.setFiguraSeleccionada(-1);
    			Repintar();
    			break;
    		}
    	}
    }
    public void agregar(Figura fig){
    	if(fig instanceof SentenceFigure){
    		SentenceFigure f = (SentenceFigure)fig;
    		new SentenceFigureDialog(MainWindow.shell,f).open();
    	}
    	else if(fig instanceof DecisionFigure){
    		DecisionFigure f = ((DecisionFigure)fig);
    		new DecisionFigureDialog(MainWindow.shell,f).open();
    	}
    	else if(fig instanceof InputFigure){
    		InputFigure f = ((InputFigure)fig);
    		new InputFigureDialog(MainWindow.shell,f).open();
    	}
    	else if(fig instanceof OutputFigure){
    		OutputFigure f = ((OutputFigure)fig);
    		new OutputFigureDialog(MainWindow.shell,f).open();
    	}
    	else if(fig instanceof ForFigure){
    		ForFigure f = ((ForFigure)fig);
    		new ForFigureDialog(MainWindow.shell,f).open();
    	}
    	else if(fig instanceof WhileFigure){
    		WhileFigure f = ((WhileFigure)fig);
    		new WhileFigureDialog(MainWindow.shell,f).open();
    	}
    }
    public void Repintar(){
    	//selec.setFiguraSeleccionada(-1);
    	MainWindow.getComponentes().diagramas.getTabItem().getSave().setSave(false);
    	MainWindow.getComponentes().diagramas.getHoja().addFigure();
    	MainWindow.getComponentes().diagramas.getHoja().guardarRetroceso();
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
}
