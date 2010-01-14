package origami.administration.actions;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import origami.administration.ApplicationState;
import origami.administration.Figura;
import origami.graphics.MainWindow;
import origami.graphics.dialogs.DecisionFigureDialog;
import origami.graphics.dialogs.ForFigureDialog;
import origami.graphics.dialogs.InputFigureDialog;
import origami.graphics.dialogs.OutputFigureDialog;
import origami.graphics.dialogs.SentenceFigureDialog;
import origami.graphics.dialogs.WhileFigureDialog;
import origami.graphics.figures.DecisionFigure;
import origami.graphics.figures.DecisionFigureEnd;
import origami.graphics.figures.EllipseFigure;
import origami.graphics.figures.ForFigure;
import origami.graphics.figures.InputFigure;
import origami.graphics.figures.OutputFigure;
import origami.graphics.figures.SentenceFigure;
import origami.graphics.figures.WhileFigure;
import origami.images.ImageLoader;




public class ContextualMenuActions {
    public static boolean isCut = false;
    
    public void Cortar(Figura fig){
    	int index = 0;
    	for(int x=0;x<MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getSizeDiagrama();x++){
    		if(x == ApplicationState._selectionAdministrator.getFiguraSeleccionada()){
    			int y=0,x2=0,cont=0;
    			ApplicationState._diagramAdministrator.diagrama.removeAllElements();
    			if(fig instanceof DecisionFigure){
    				y = RecorridoDiagrama.recorridoCiclo(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama(),x);
    				y = RecorridoDiagrama.recorridoCiclo2(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama(),y);
    				x2=y-x;
    				while(cont<x2+2){
    				ApplicationState._diagramAdministrator.diagrama.add(index,MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x));
    					MainWindow.getComponents()._diagrams.getTabItem().getLeaf().removeFigureIndexOf(x);
    					cont++;
    					index++;
    				}
    			} 
    			else if(fig instanceof ForFigure){
    				y = RecorridoDiagrama.recorridoCiclo3(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama(),x);
    				x2=y-x;
    				while(cont<x2+6){
    				ApplicationState._diagramAdministrator.diagrama.add(index,MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x));
    					MainWindow.getComponents()._diagrams.getTabItem().getLeaf().removeFigureIndexOf(x);
    					cont++;
    					index++;
    				}
    			}
    			else if(fig instanceof WhileFigure){
    				y = RecorridoDiagrama.recorridoCiclo3(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama(),x);
    				x2=y-x;
    				while(cont<x2+6){
    				ApplicationState._diagramAdministrator.diagrama.add(index,MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x));
    					MainWindow.getComponents()._diagrams.getTabItem().getLeaf().removeFigureIndexOf(x);
    					cont++;
    					index++;
    				}
    			}
    			else{
    			ApplicationState._diagramAdministrator.diagrama.add(index,MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x));
    				MainWindow.getComponents()._diagrams.getTabItem().getLeaf().removeFigureIndexOf(x);
    			}
    			isCut = true;
    			ApplicationState._selectionAdministrator.setFiguraSeleccionada(-1);
    			Repintar();	
    			break;
    		}
    	}
    }
    public void Copiar(Figura fig){
    	if(isCut){
    	ApplicationState._diagramAdministrator.diagrama.removeAllElements();
    		isCut = false;
    	}
    	int index = 0;
    	for(int x=0;x<MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getSizeDiagrama();x++){
    		if(x == ApplicationState._selectionAdministrator.getFiguraSeleccionada()){
    			int y=0,x2=0,cont=0;
    			ApplicationState._diagramAdministrator.diagrama.removeAllElements();
    			if(fig instanceof DecisionFigure){
				y = RecorridoDiagrama.recorridoCiclo(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama(),x);
    				y = RecorridoDiagrama.recorridoCiclo2(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama(),y);
    				x2=y-x;
    				while(cont<x2+2){
    					if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x) instanceof DecisionFigure){
    						DecisionFigure copia = new DecisionFigure();
    						DecisionFigure actual = (DecisionFigure)MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x);
    						for(int j=0; j<actual.instructionComposed.simpleInstructionList.size(); j++){
    							copia.instructionComposed.simpleInstructionList.add(actual.instructionComposed.simpleInstructionList.elementAt(j));	
    						}
    						ApplicationState._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x) instanceof ForFigure){
    						ForFigure copia = new ForFigure();
    						ForFigure actual = (ForFigure)MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x);
    						for(int j=0; j<actual.instructionComposed.simpleInstructionList.size(); j++){
    							copia.instructionComposed.simpleInstructionList.add(actual.instructionComposed.simpleInstructionList.elementAt(j));	
    						}
    						ApplicationState._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x) instanceof WhileFigure){
    						WhileFigure copia = new WhileFigure();
    						WhileFigure actual = (WhileFigure)MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x);
    						for(int j=0; j<actual.instructionComposed.simpleInstructionList.size(); j++){
    							copia.instructionComposed.simpleInstructionList.add(actual.instructionComposed.simpleInstructionList.elementAt(j));	
    						}
    						ApplicationState._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x) instanceof SentenceFigure){
    						SentenceFigure copia = new SentenceFigure();
    						SentenceFigure actual = (SentenceFigure)MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x);
    						copia.instruction.simpleInstruction = actual.instruction.simpleInstruction;
    						ApplicationState._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x) instanceof InputFigure){
    						InputFigure copia = new InputFigure();
    						InputFigure actual = (InputFigure)MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x);
    						copia.instruction.simpleInstruction = actual.instruction.simpleInstruction;
    						ApplicationState._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x) instanceof OutputFigure){
    						OutputFigure copia = new OutputFigure();
    						OutputFigure actual = (OutputFigure)MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x);
    						copia.instruction.simpleInstruction = actual.instruction.simpleInstruction;
    						ApplicationState._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else{
    					ApplicationState._diagramAdministrator.diagrama.add(index,MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x));
    					}
    					cont++;
    					index++;
    					x++;
    				}
    			} 
    			else if(fig instanceof ForFigure || fig instanceof WhileFigure){
    				y = RecorridoDiagrama.recorridoCiclo3(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama(),x);
    				x2=y-x;
    				while(cont<x2+6){
    					if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x) instanceof DecisionFigure){
    						DecisionFigure copia = new DecisionFigure();
    						DecisionFigure actual = (DecisionFigure)MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x);
    						for(int j=0; j<actual.instructionComposed.simpleInstructionList.size(); j++){
    							copia.instructionComposed.simpleInstructionList.add(actual.instructionComposed.simpleInstructionList.elementAt(j));	
    						}
    						ApplicationState._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x) instanceof ForFigure){
    						ForFigure copia = new ForFigure();
    						ForFigure actual = (ForFigure)MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x);
    						for(int j=0; j<actual.instructionComposed.simpleInstructionList.size(); j++){
    							copia.instructionComposed.simpleInstructionList.add(actual.instructionComposed.simpleInstructionList.elementAt(j));	
    						}
    						ApplicationState._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x) instanceof WhileFigure){
    						WhileFigure copia = new WhileFigure();
    						WhileFigure actual = (WhileFigure)MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x);
    						for(int j=0; j<actual.instructionComposed.simpleInstructionList.size(); j++){
    							copia.instructionComposed.simpleInstructionList.add(actual.instructionComposed.simpleInstructionList.elementAt(j));	
    						}
    						ApplicationState._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x) instanceof SentenceFigure){
    						SentenceFigure copia = new SentenceFigure();
    						SentenceFigure actual = (SentenceFigure)MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x);
    						copia.instruction.simpleInstruction = actual.instruction.simpleInstruction;
    						ApplicationState._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x) instanceof InputFigure){
    						InputFigure copia = new InputFigure();
    						InputFigure actual = (InputFigure)MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x);
    						copia.instruction.simpleInstruction = actual.instruction.simpleInstruction;
    						ApplicationState._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x) instanceof OutputFigure){
    						OutputFigure copia = new OutputFigure();
    						OutputFigure actual = (OutputFigure)MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x);
    						copia.instruction.simpleInstruction = actual.instruction.simpleInstruction;
    						ApplicationState._diagramAdministrator.diagrama.add(index,copia);
    					}
    					else{
    					ApplicationState._diagramAdministrator.diagrama.add(index,MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x));
    					}
    					cont++;
    					index++;
    					x++;
    				}
    			}
    			else{
    				if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x) instanceof InputFigure){
    					InputFigure figura = new InputFigure();
    					InputFigure figura2 = (InputFigure)MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf( x );
    					figura.instruction.simpleInstruction = figura2.instruction.simpleInstruction;
    					ApplicationState._diagramAdministrator.diagrama.add(index,figura);
    				}
    				else if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x) instanceof OutputFigure){
    					OutputFigure figura = new OutputFigure();
    					OutputFigure figura2 = (OutputFigure)MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(x );
    					figura.instruction.simpleInstruction = figura2.instruction.simpleInstruction;
    					ApplicationState._diagramAdministrator.diagrama.add(index,figura);
    
    				}
    				else{
    					SentenceFigure figura = new SentenceFigure();
    					SentenceFigure figura2 = (SentenceFigure)MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf( x);
    					figura.instruction.simpleInstruction = figura2.instruction.simpleInstruction;
    					ApplicationState._diagramAdministrator.diagrama.add(index,figura);
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
    		for(int i=0; i<ApplicationState._diagramAdministrator.diagrama.size(); i++){
    			if(ApplicationState._diagramAdministrator.diagrama.elementAt(i) instanceof EllipseFigure){
    				EllipseFigure nueva = new EllipseFigure();
    				ApplicationState._diagramAdministrator.diagrama.remove(i);
    				ApplicationState._diagramAdministrator.diagrama.insertElementAt(nueva,i);
    			}
    			else if(ApplicationState._diagramAdministrator.diagrama.elementAt(i) instanceof DecisionFigureEnd){
    				DecisionFigureEnd nueva = new DecisionFigureEnd();
    				ApplicationState._diagramAdministrator.diagrama.remove(i);
    				ApplicationState._diagramAdministrator.diagrama.insertElementAt(nueva,i);
    			}
    			else if(ApplicationState._diagramAdministrator.diagrama.elementAt(i) instanceof DecisionFigure){
    				DecisionFigure nueva = new DecisionFigure();
    				DecisionFigure aux = (DecisionFigure)ApplicationState._diagramAdministrator.diagrama.elementAt(i);
    				for(int x=0;x<aux.instructionComposed.simpleInstructionList.size(); x++){
    					nueva.instructionComposed.simpleInstructionList.add(x,aux.instructionComposed.simpleInstructionList.elementAt(x));
    				}
    				ApplicationState._diagramAdministrator.diagrama.remove(i);
    				ApplicationState._diagramAdministrator.diagrama.insertElementAt(nueva,i);
    			}
    			else if(ApplicationState._diagramAdministrator.diagrama.elementAt(i) instanceof ForFigure){
    				ForFigure nueva = new ForFigure();
    				ForFigure aux = (ForFigure)ApplicationState._diagramAdministrator.diagrama.elementAt(i);
    				for(int x=0;x<aux.instructionComposed.simpleInstructionList.size(); x++){
    					nueva.instructionComposed.simpleInstructionList.add(x,aux.instructionComposed.simpleInstructionList.elementAt(x));
    				}
    				ApplicationState._diagramAdministrator.diagrama.remove(i);
    				ApplicationState._diagramAdministrator.diagrama.insertElementAt(nueva,i);
    			}
    			else if(ApplicationState._diagramAdministrator.diagrama.elementAt(i) instanceof WhileFigure){
    				WhileFigure nueva = new WhileFigure();
    				WhileFigure aux = (WhileFigure)ApplicationState._diagramAdministrator.diagrama.elementAt(i);
    				for(int x=0;x<aux.instructionComposed.simpleInstructionList.size(); x++){
    					nueva.instructionComposed.simpleInstructionList.add(x,aux.instructionComposed.simpleInstructionList.elementAt(x));
    				}
    				ApplicationState._diagramAdministrator.diagrama.remove(i);
    				ApplicationState._diagramAdministrator.diagrama.insertElementAt(nueva,i);
    			}
    			else if(ApplicationState._diagramAdministrator.diagrama.elementAt(i) instanceof OutputFigure){
    				OutputFigure nueva = new OutputFigure();
    				OutputFigure aux = (OutputFigure)ApplicationState._diagramAdministrator.diagrama.elementAt(i);
    				nueva.instruction.simpleInstruction=aux.instruction.simpleInstruction;
    				ApplicationState._diagramAdministrator.diagrama.remove(i);
    				ApplicationState._diagramAdministrator.diagrama.insertElementAt(nueva,i);
    			}
    			else if(ApplicationState._diagramAdministrator.diagrama.elementAt(i) instanceof InputFigure){
    				InputFigure nueva = new InputFigure();
    				InputFigure aux = (InputFigure)ApplicationState._diagramAdministrator.diagrama.elementAt(i);
    				nueva.instruction.simpleInstruction=aux.instruction.simpleInstruction;
    				ApplicationState._diagramAdministrator.diagrama.remove(i);
    				ApplicationState._diagramAdministrator.diagrama.insertElementAt(nueva,i);
    			}
    			else if(ApplicationState._diagramAdministrator.diagrama.elementAt(i) instanceof SentenceFigure){
    				SentenceFigure nueva = new SentenceFigure();
    				SentenceFigure aux = (SentenceFigure)ApplicationState._diagramAdministrator.diagrama.elementAt(i);
    				nueva.instruction.simpleInstruction=aux.instruction.simpleInstruction;
    				ApplicationState._diagramAdministrator.diagrama.remove(i);
    				ApplicationState._diagramAdministrator.diagrama.insertElementAt(nueva,i);
    			}
    		}
    	}
    	Vector<Figura> temporal = new Vector<Figura>();
    	for(int x=0;x<MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getSizeDiagrama();x++){
    		if(x == ApplicationState._selectionAdministrator.getFiguraSeleccionada()){
    			if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(ApplicationState._selectionAdministrator.getFiguraSeleccionada()) instanceof DecisionFigure){
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
    						for(int u =0; u<MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getSizeDiagrama(); u++){
    							temporal.add(u,MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(u));
    						}
    						//*******************************************
    						int posicion = MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(ApplicationState._selectionAdministrator.getFiguraSeleccionada()).getBounds().x+MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(ApplicationState._selectionAdministrator.getFiguraSeleccionada()).getBounds().width;
    						posicion = MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(ApplicationState._selectionAdministrator.getFiguraSeleccionada()+1).getBounds().x-posicion;
    						//*******************************************
    						MainWindow.getComponents()._diagrams.getTabItem().getLeaf().removeFigureAllElements();
    						for(int i =0; i<=x; i++){
    							MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigureIndexOf(i,temporal.elementAt(i));
    							y = i;
    						 }
    						y++;
    						int distancia =  temporal.elementAt(y-1).getBounds().x + temporal.elementAt(y-1).getBounds().height;
    						distancia =  temporal.elementAt(y).getBounds().x - distancia;
    						int cont=0, punto=0;
    						for(int i =ApplicationState._selectionAdministrator.getFiguraSeleccionada(); cont<2; i++){
    								if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(ApplicationState._selectionAdministrator.getFiguraSeleccionada()).getBounds().x - temporal.elementAt(i).getBounds().x == posicion){
    									cont++;
    								}
    								punto = i;
    						}
    						for(int i =y; i<punto; i++){
    							MainWindow.getComponents()._diagrams.getTabItem().getLeaf().insertFigureIndexOf(temporal.elementAt(i), i);
    							y = i;
    						}
    						int j =0;
    						//este es el que se debe de seleccionar punto
    						ApplicationState._selectionAdministrator.setFiguraSeleccionada(punto);
    						for(int i =punto; j<ApplicationState._diagramAdministrator.diagrama.size(); i++){
    							MainWindow.getComponents()._diagrams.getTabItem().getLeaf().insertFigureIndexOf(ApplicationState._diagramAdministrator.diagrama.elementAt(j), i);
    							j++;
    							y = i;
    						}
    						y++;
    						lim =temporal.size()-punto;
    						j=0;
    						for(int i =y; j<lim; i++){
    							MainWindow.getComponents()._diagrams.getTabItem().getLeaf().insertFigureIndexOf(temporal.elementAt(punto), i);
    							j++;
    							punto++;
    						}
    						if(isCut){
    						ApplicationState._diagramAdministrator.diagrama.removeAllElements();
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
    						for(int u =0; u<MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getSizeDiagrama(); u++){
    							temporal.add(u,MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(u));
    						}
    						MainWindow.getComponents()._diagrams.getTabItem().getLeaf().removeFigureAllElements();
    						for(int i =0; i<=x; i++){
    							MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigureIndexOf(i,temporal.elementAt(i));
    							y = i;
    						 }
    							y++;
    							MainWindow.getComponents()._diagrams.getTabItem().getLeaf().insertFigureIndexOf(temporal.elementAt(y), y);
    						y=0;
    						lim =temporal.size()-x-1;
    						w=x+1;
    						//este es el que se debe de seleccionar w
    						ApplicationState._selectionAdministrator.setFiguraSeleccionada(w+1);
    						for(int i =w; i<x+1+ApplicationState._diagramAdministrator.diagrama.size(); i++){
    							MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigureIndexOf(i+1,ApplicationState._diagramAdministrator.diagrama.elementAt(y));
    							y++;
    							w++;
    						}
    						y = 0;
    						x++;
    						for(int i =w; y<lim-1; i++){
    							MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigureIndexOf(i+1,temporal.elementAt(x+1));
    							y++;
    							x++;
    						}
    						if(isCut){
    						ApplicationState._diagramAdministrator.diagrama.removeAllElements();
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
    				for(int u =0; u<MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getSizeDiagrama(); u++){
    					temporal.add(u,MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getFigureIndexOf(u));
    				}
    				MainWindow.getComponents()._diagrams.getTabItem().getLeaf().removeFigureAllElements();
    				for(int i =0; i<=x; i++){
    					MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigureIndexOf(i,temporal.elementAt(i));
    					y = i;
    				}
    				y++;
    				y=0;
    				lim =temporal.size()-x-1;
    				w=x+1;
    				//esta es la que se debe de seleccionar w
    				ApplicationState._selectionAdministrator.setFiguraSeleccionada(w);
    				for(int i =w; i<x+1+ApplicationState._diagramAdministrator.diagrama.size(); i++){
    					MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigureIndexOf(i,ApplicationState._diagramAdministrator.diagrama.elementAt(y));
    					y++;
    					w++;
    				}
    				y = 0;
    				x++;
    				for(int i =w; y<lim; i++){
    					MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigureIndexOf(i,temporal.elementAt(x));
    					y++;
    					x++;
    				}
    				if(isCut){
    				ApplicationState._diagramAdministrator.diagrama.removeAllElements();
    					isCut = false;
    				}
    				Repintar();
    			}
    		}
    	}
    }
    public void insertarFigura(final Figura inser){
    	final int i = ApplicationState._selectionAdministrator.getFiguraSeleccionada();
    	if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getAdminDiagrama().diagrama.elementAt(i) instanceof DecisionFigure){
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
    		imagen.setImage(ImageLoader.getImage("Pregunta.png"));
    		imagen.setBounds(25,10,50,50);
    		shell.setMaximized(false);
    		shell.setMinimized(false);
    		shell.open();
    		izquierda.addSelectionListener(new SelectionAdapter() {
    			public void widgetSelected(SelectionEvent event) {
    				int distan = MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getFiguraSeleccionada()+1).getBounds().x-(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getFiguraSeleccionada()).getBounds().x+
    				MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getFiguraSeleccionada()).getBounds().width);
    				for(int j=ApplicationState._selectionAdministrator.getFiguraSeleccionada()+1; j<MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getSizeDiagrama(); j++){
    					if(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getFiguraSeleccionada()).getBounds().x-MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama().elementAt(j).getBounds().x == distan){
    						MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getAdminDiagrama().ordenar(j, inser);
    						ApplicationState._selectionAdministrator.setFiguraSeleccionada(j+1);
    						MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigure();
    						break;
    					}
    				}
    				shell.close();
    				Repintar();
    				
    			}
    		});
    		derecha.addSelectionListener(new SelectionAdapter() {
    			public void widgetSelected(SelectionEvent event) {
    				MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getAdminDiagrama().ordenar(i+1, inser);
    				ApplicationState._selectionAdministrator.setFiguraSeleccionada(i+2);
    				MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigure();
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
    		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getAdminDiagrama().ordenar(i, inser);
    		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigure();
    		MainWindow.getComponents()._diagrams.getTabItem().getLeaf().guardarRetroceso();
    		MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
    	}
    }
    public void Eliminar(Figura fig){
    	String tipo ="";
    	for(int x=0;x<MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getSizeDiagrama();x++){
    		if(x == ApplicationState._selectionAdministrator.getFiguraSeleccionada()){
    			int y=0,x2=0,cont=0;
    			if(fig instanceof DecisionFigure){
    				tipo="si";
    				y = RecorridoDiagrama.recorridoCiclo(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama(),x);
    				y = RecorridoDiagrama.recorridoCiclo2(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama(),y);
    				x2=y-x;
    				while(cont<x2+2){
    					MainWindow.getComponents()._diagrams.getTabItem().getLeaf().removeFigureIndexOf(x);
    					cont++;
    				}
    			} 
    			else if(fig instanceof ForFigure){
    				tipo="para";
    				y = RecorridoDiagrama.recorridoCiclo3(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama(),x);
    				x2=y-x;
    				while(cont<x2+6){
    					MainWindow.getComponents()._diagrams.getTabItem().getLeaf().removeFigureIndexOf(x);
    					cont++;
    				}
    			}
    			else if(fig instanceof WhileFigure){
    				tipo="mientras";
    				y = RecorridoDiagrama.recorridoCiclo3(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama(),x);
    				x2=y-x;
    				while(cont<x2+6){
    					MainWindow.getComponents()._diagrams.getTabItem().getLeaf().removeFigureIndexOf(x);
    					cont++;
    				}
    			}
    			else{
    				MainWindow.getComponents()._diagrams.getTabItem().getLeaf().removeFigureIndexOf(x);
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
    			MainWindow.getComponents()._diagrams.getTabItem().getInformation().addInformation("/D - Se elimino una figura de tipo \" "+tipo+"\"");
    			MainWindow.getComponents()._diagrams.getTabItem().getInformation().setDiagrama(MainWindow.getComponents()._diagrams.getTabItem().getLeaf().getDiagrama());
    			ApplicationState._selectionAdministrator.setFiguraSeleccionada(-1);
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
    	MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
    	MainWindow.getComponents()._diagrams.getTabItem().getLeaf().addFigure();
    	MainWindow.getComponents()._diagrams.getTabItem().getLeaf().guardarRetroceso();
    }
}
