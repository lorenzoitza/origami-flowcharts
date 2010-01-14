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
    	for(int x=0;x<MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getSizeDiagrama();x++){
    		if(x == ApplicationState._selectionAdministrator.getSelectedFigure()){
    			int y=0,x2=0,cont=0;
    			ApplicationState._diagramAdministrator.diagram.removeAllElements();
    			if(fig instanceof DecisionFigure){
    				y = RecorridoDiagrama.recorridoCiclo(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama(),x);
    				y = RecorridoDiagrama.recorridoCiclo2(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama(),y);
    				x2=y-x;
    				while(cont<x2+2){
    				ApplicationState._diagramAdministrator.diagram.add(index,MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x));
    					MainWindow.getComponents().tabFolder.getTabItem().getLeaf().removeFigureIndexOf(x);
    					cont++;
    					index++;
    				}
    			} 
    			else if(fig instanceof ForFigure){
    				y = RecorridoDiagrama.recorridoCiclo3(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama(),x);
    				x2=y-x;
    				while(cont<x2+6){
    				ApplicationState._diagramAdministrator.diagram.add(index,MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x));
    					MainWindow.getComponents().tabFolder.getTabItem().getLeaf().removeFigureIndexOf(x);
    					cont++;
    					index++;
    				}
    			}
    			else if(fig instanceof WhileFigure){
    				y = RecorridoDiagrama.recorridoCiclo3(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama(),x);
    				x2=y-x;
    				while(cont<x2+6){
    				ApplicationState._diagramAdministrator.diagram.add(index,MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x));
    					MainWindow.getComponents().tabFolder.getTabItem().getLeaf().removeFigureIndexOf(x);
    					cont++;
    					index++;
    				}
    			}
    			else{
    			ApplicationState._diagramAdministrator.diagram.add(index,MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x));
    				MainWindow.getComponents().tabFolder.getTabItem().getLeaf().removeFigureIndexOf(x);
    			}
    			isCut = true;
    			ApplicationState._selectionAdministrator.setSelectedFigure(-1);
    			Repintar();	
    			break;
    		}
    	}
    }
    public void Copiar(Figura fig){
    	if(isCut){
    	ApplicationState._diagramAdministrator.diagram.removeAllElements();
    		isCut = false;
    	}
    	int index = 0;
    	for(int x=0;x<MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getSizeDiagrama();x++){
    		if(x == ApplicationState._selectionAdministrator.getSelectedFigure()){
    			int y=0,x2=0,cont=0;
    			ApplicationState._diagramAdministrator.diagram.removeAllElements();
    			if(fig instanceof DecisionFigure){
				y = RecorridoDiagrama.recorridoCiclo(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama(),x);
    				y = RecorridoDiagrama.recorridoCiclo2(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama(),y);
    				x2=y-x;
    				while(cont<x2+2){
    					if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x) instanceof DecisionFigure){
    						DecisionFigure copia = new DecisionFigure();
    						DecisionFigure actual = (DecisionFigure)MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x);
    						for(int j=0; j<actual.instructionComposed.getListSize(); j++){
    							copia.instructionComposed.addSimpleInstruction(actual.instructionComposed.getSimpleInstructionAt(j));	
    						}
    						ApplicationState._diagramAdministrator.diagram.add(index,copia);
    					}
    					else if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x) instanceof ForFigure){
    						ForFigure copia = new ForFigure();
    						ForFigure actual = (ForFigure)MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x);
    						for(int j=0; j<actual.instructionComposed.getListSize(); j++){
    							copia.instructionComposed.addSimpleInstruction(actual.instructionComposed.getSimpleInstructionAt(j));	
    						}
    						ApplicationState._diagramAdministrator.diagram.add(index,copia);
    					}
    					else if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x) instanceof WhileFigure){
    						WhileFigure copia = new WhileFigure();
    						WhileFigure actual = (WhileFigure)MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x);
    						for(int j=0; j<actual.instructionComposed.getListSize(); j++){
    							copia.instructionComposed.addSimpleInstruction(actual.instructionComposed.getSimpleInstructionAt(j));
    						}
    						ApplicationState._diagramAdministrator.diagram.add(index,copia);
    					}
    					else if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x) instanceof SentenceFigure){
    						SentenceFigure copia = new SentenceFigure();
    						SentenceFigure actual = (SentenceFigure)MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x);
    						copia.instruction.simpleInstruction = actual.instruction.simpleInstruction;
    						ApplicationState._diagramAdministrator.diagram.add(index,copia);
    					}
    					else if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x) instanceof InputFigure){
    						InputFigure copia = new InputFigure();
    						InputFigure actual = (InputFigure)MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x);
    						copia.instruction.simpleInstruction = actual.instruction.simpleInstruction;
    						ApplicationState._diagramAdministrator.diagram.add(index,copia);
    					}
    					else if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x) instanceof OutputFigure){
    						OutputFigure copia = new OutputFigure();
    						OutputFigure actual = (OutputFigure)MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x);
    						copia.instruction.simpleInstruction = actual.instruction.simpleInstruction;
    						ApplicationState._diagramAdministrator.diagram.add(index,copia);
    					}
    					else{
    					ApplicationState._diagramAdministrator.diagram.add(index,MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x));
    					}
    					cont++;
    					index++;
    					x++;
    				}
    			} 
    			else if(fig instanceof ForFigure || fig instanceof WhileFigure){
    				y = RecorridoDiagrama.recorridoCiclo3(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama(),x);
    				x2=y-x;
    				while(cont<x2+6){
    					if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x) instanceof DecisionFigure){
    						DecisionFigure copia = new DecisionFigure();
    						DecisionFigure actual = (DecisionFigure)MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x);
    						for(int j=0; j<actual.instructionComposed.getListSize(); j++){
							copia.instructionComposed.addSimpleInstruction(actual.instructionComposed.getSimpleInstructionAt(j));
    						}
    						ApplicationState._diagramAdministrator.diagram.add(index,copia);
    					}
    					else if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x) instanceof ForFigure){
    						ForFigure copia = new ForFigure();
    						ForFigure actual = (ForFigure)MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x);
    						for(int j=0; j<actual.instructionComposed.getListSize(); j++){
							copia.instructionComposed.addSimpleInstruction(actual.instructionComposed.getSimpleInstructionAt(j));
    						}
    						ApplicationState._diagramAdministrator.diagram.add(index,copia);
    					}
    					else if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x) instanceof WhileFigure){
    						WhileFigure copia = new WhileFigure();
    						WhileFigure actual = (WhileFigure)MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x);
    						for(int j=0; j<actual.instructionComposed.getListSize(); j++){
							copia.instructionComposed.addSimpleInstruction(actual.instructionComposed.getSimpleInstructionAt(j));
    						}
    						ApplicationState._diagramAdministrator.diagram.add(index,copia);
    					}
    					else if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x) instanceof SentenceFigure){
    						SentenceFigure copia = new SentenceFigure();
    						SentenceFigure actual = (SentenceFigure)MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x);
    						copia.instruction.simpleInstruction = actual.instruction.simpleInstruction;
    						ApplicationState._diagramAdministrator.diagram.add(index,copia);
    					}
    					else if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x) instanceof InputFigure){
    						InputFigure copia = new InputFigure();
    						InputFigure actual = (InputFigure)MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x);
    						copia.instruction.simpleInstruction = actual.instruction.simpleInstruction;
    						ApplicationState._diagramAdministrator.diagram.add(index,copia);
    					}
    					else if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x) instanceof OutputFigure){
    						OutputFigure copia = new OutputFigure();
    						OutputFigure actual = (OutputFigure)MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x);
    						copia.instruction.simpleInstruction = actual.instruction.simpleInstruction;
    						ApplicationState._diagramAdministrator.diagram.add(index,copia);
    					}
    					else{
    					ApplicationState._diagramAdministrator.diagram.add(index,MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x));
    					}
    					cont++;
    					index++;
    					x++;
    				}
    			}
    			else{
    				if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x) instanceof InputFigure){
    					InputFigure figura = new InputFigure();
    					InputFigure figura2 = (InputFigure)MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf( x );
    					figura.instruction.simpleInstruction = figura2.instruction.simpleInstruction;
    					ApplicationState._diagramAdministrator.diagram.add(index,figura);
    				}
    				else if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x) instanceof OutputFigure){
    					OutputFigure figura = new OutputFigure();
    					OutputFigure figura2 = (OutputFigure)MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(x );
    					figura.instruction.simpleInstruction = figura2.instruction.simpleInstruction;
    					ApplicationState._diagramAdministrator.diagram.add(index,figura);
    
    				}
    				else{
    					SentenceFigure figura = new SentenceFigure();
    					SentenceFigure figura2 = (SentenceFigure)MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf( x);
    					figura.instruction.simpleInstruction = figura2.instruction.simpleInstruction;
    					ApplicationState._diagramAdministrator.diagram.add(index,figura);
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
    		for(int i=0; i<ApplicationState._diagramAdministrator.diagram.size(); i++){
    			if(ApplicationState._diagramAdministrator.diagram.elementAt(i) instanceof EllipseFigure){
    				EllipseFigure nueva = new EllipseFigure();
    				ApplicationState._diagramAdministrator.diagram.remove(i);
    				ApplicationState._diagramAdministrator.diagram.insertElementAt(nueva,i);
    			}
    			else if(ApplicationState._diagramAdministrator.diagram.elementAt(i) instanceof DecisionFigureEnd){
    				DecisionFigureEnd nueva = new DecisionFigureEnd();
    				ApplicationState._diagramAdministrator.diagram.remove(i);
    				ApplicationState._diagramAdministrator.diagram.insertElementAt(nueva,i);
    			}
    			else if(ApplicationState._diagramAdministrator.diagram.elementAt(i) instanceof DecisionFigure){
    				DecisionFigure nueva = new DecisionFigure();
    				DecisionFigure aux = (DecisionFigure)ApplicationState._diagramAdministrator.diagram.elementAt(i);
    				for(int x=0;x<aux.instructionComposed.getListSize(); x++){
    					nueva.instructionComposed.addSimpleInstruccion(x, aux.instructionComposed.getSimpleInstructionAt(x));
    				}
    				ApplicationState._diagramAdministrator.diagram.remove(i);
    				ApplicationState._diagramAdministrator.diagram.insertElementAt(nueva,i);
    			}
    			else if(ApplicationState._diagramAdministrator.diagram.elementAt(i) instanceof ForFigure){
    				ForFigure nueva = new ForFigure();
    				ForFigure aux = (ForFigure)ApplicationState._diagramAdministrator.diagram.elementAt(i);
    				for(int x=0;x<aux.instructionComposed.getListSize(); x++){
					nueva.instructionComposed.addSimpleInstruccion(x, aux.instructionComposed.getSimpleInstructionAt(x));
    				}
    				ApplicationState._diagramAdministrator.diagram.remove(i);
    				ApplicationState._diagramAdministrator.diagram.insertElementAt(nueva,i);
    			}
    			else if(ApplicationState._diagramAdministrator.diagram.elementAt(i) instanceof WhileFigure){
    				WhileFigure nueva = new WhileFigure();
    				WhileFigure aux = (WhileFigure)ApplicationState._diagramAdministrator.diagram.elementAt(i);
    				for(int x=0;x<aux.instructionComposed.getListSize(); x++){
					nueva.instructionComposed.addSimpleInstruccion(x, aux.instructionComposed.getSimpleInstructionAt(x));
    				}
    				ApplicationState._diagramAdministrator.diagram.remove(i);
    				ApplicationState._diagramAdministrator.diagram.insertElementAt(nueva,i);
    			}
    			else if(ApplicationState._diagramAdministrator.diagram.elementAt(i) instanceof OutputFigure){
    				OutputFigure nueva = new OutputFigure();
    				OutputFigure aux = (OutputFigure)ApplicationState._diagramAdministrator.diagram.elementAt(i);
    				nueva.instruction.simpleInstruction=aux.instruction.simpleInstruction;
    				ApplicationState._diagramAdministrator.diagram.remove(i);
    				ApplicationState._diagramAdministrator.diagram.insertElementAt(nueva,i);
    			}
    			else if(ApplicationState._diagramAdministrator.diagram.elementAt(i) instanceof InputFigure){
    				InputFigure nueva = new InputFigure();
    				InputFigure aux = (InputFigure)ApplicationState._diagramAdministrator.diagram.elementAt(i);
    				nueva.instruction.simpleInstruction=aux.instruction.simpleInstruction;
    				ApplicationState._diagramAdministrator.diagram.remove(i);
    				ApplicationState._diagramAdministrator.diagram.insertElementAt(nueva,i);
    			}
    			else if(ApplicationState._diagramAdministrator.diagram.elementAt(i) instanceof SentenceFigure){
    				SentenceFigure nueva = new SentenceFigure();
    				SentenceFigure aux = (SentenceFigure)ApplicationState._diagramAdministrator.diagram.elementAt(i);
    				nueva.instruction.simpleInstruction=aux.instruction.simpleInstruction;
    				ApplicationState._diagramAdministrator.diagram.remove(i);
    				ApplicationState._diagramAdministrator.diagram.insertElementAt(nueva,i);
    			}
    		}
    	}
    	Vector<Figura> temporal = new Vector<Figura>();
    	for(int x=0;x<MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getSizeDiagrama();x++){
    		if(x == ApplicationState._selectionAdministrator.getSelectedFigure()){
    			if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(ApplicationState._selectionAdministrator.getSelectedFigure()) instanceof DecisionFigure){
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
    						for(int u =0; u<MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getSizeDiagrama(); u++){
    							temporal.add(u,MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(u));
    						}
    						//*******************************************
    						int posicion = MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(ApplicationState._selectionAdministrator.getSelectedFigure()).getBounds().x+MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(ApplicationState._selectionAdministrator.getSelectedFigure()).getBounds().width;
    						posicion = MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(ApplicationState._selectionAdministrator.getSelectedFigure()+1).getBounds().x-posicion;
    						//*******************************************
    						MainWindow.getComponents().tabFolder.getTabItem().getLeaf().removeFigureAllElements();
    						for(int i =0; i<=x; i++){
    							MainWindow.getComponents().tabFolder.getTabItem().getLeaf().addFigureIndexOf(i,temporal.elementAt(i));
    							y = i;
    						 }
    						y++;
    						int distancia =  temporal.elementAt(y-1).getBounds().x + temporal.elementAt(y-1).getBounds().height;
    						distancia =  temporal.elementAt(y).getBounds().x - distancia;
    						int cont=0, punto=0;
    						for(int i =ApplicationState._selectionAdministrator.getSelectedFigure(); cont<2; i++){
    								if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(ApplicationState._selectionAdministrator.getSelectedFigure()).getBounds().x - temporal.elementAt(i).getBounds().x == posicion){
    									cont++;
    								}
    								punto = i;
    						}
    						for(int i =y; i<punto; i++){
    							MainWindow.getComponents().tabFolder.getTabItem().getLeaf().insertFigureIndexOf(temporal.elementAt(i), i);
    							y = i;
    						}
    						int j =0;
    						//este es el que se debe de seleccionar punto
    						ApplicationState._selectionAdministrator.setSelectedFigure(punto);
    						for(int i =punto; j<ApplicationState._diagramAdministrator.diagram.size(); i++){
    							MainWindow.getComponents().tabFolder.getTabItem().getLeaf().insertFigureIndexOf(ApplicationState._diagramAdministrator.diagram.elementAt(j), i);
    							j++;
    							y = i;
    						}
    						y++;
    						lim =temporal.size()-punto;
    						j=0;
    						for(int i =y; j<lim; i++){
    							MainWindow.getComponents().tabFolder.getTabItem().getLeaf().insertFigureIndexOf(temporal.elementAt(punto), i);
    							j++;
    							punto++;
    						}
    						if(isCut){
    						ApplicationState._diagramAdministrator.diagram.removeAllElements();
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
    						for(int u =0; u<MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getSizeDiagrama(); u++){
    							temporal.add(u,MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(u));
    						}
    						MainWindow.getComponents().tabFolder.getTabItem().getLeaf().removeFigureAllElements();
    						for(int i =0; i<=x; i++){
    							MainWindow.getComponents().tabFolder.getTabItem().getLeaf().addFigureIndexOf(i,temporal.elementAt(i));
    							y = i;
    						 }
    							y++;
    							MainWindow.getComponents().tabFolder.getTabItem().getLeaf().insertFigureIndexOf(temporal.elementAt(y), y);
    						y=0;
    						lim =temporal.size()-x-1;
    						w=x+1;
    						//este es el que se debe de seleccionar w
    						ApplicationState._selectionAdministrator.setSelectedFigure(w+1);
    						for(int i =w; i<x+1+ApplicationState._diagramAdministrator.diagram.size(); i++){
    							MainWindow.getComponents().tabFolder.getTabItem().getLeaf().addFigureIndexOf(i+1,ApplicationState._diagramAdministrator.diagram.elementAt(y));
    							y++;
    							w++;
    						}
    						y = 0;
    						x++;
    						for(int i =w; y<lim-1; i++){
    							MainWindow.getComponents().tabFolder.getTabItem().getLeaf().addFigureIndexOf(i+1,temporal.elementAt(x+1));
    							y++;
    							x++;
    						}
    						if(isCut){
    						ApplicationState._diagramAdministrator.diagram.removeAllElements();
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
    				for(int u =0; u<MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getSizeDiagrama(); u++){
    					temporal.add(u,MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(u));
    				}
    				MainWindow.getComponents().tabFolder.getTabItem().getLeaf().removeFigureAllElements();
    				for(int i =0; i<=x; i++){
    					MainWindow.getComponents().tabFolder.getTabItem().getLeaf().addFigureIndexOf(i,temporal.elementAt(i));
    					y = i;
    				}
    				y++;
    				y=0;
    				lim =temporal.size()-x-1;
    				w=x+1;
    				//esta es la que se debe de seleccionar w
    				ApplicationState._selectionAdministrator.setSelectedFigure(w);
    				for(int i =w; i<x+1+ApplicationState._diagramAdministrator.diagram.size(); i++){
    					MainWindow.getComponents().tabFolder.getTabItem().getLeaf().addFigureIndexOf(i,ApplicationState._diagramAdministrator.diagram.elementAt(y));
    					y++;
    					w++;
    				}
    				y = 0;
    				x++;
    				for(int i =w; y<lim; i++){
    					MainWindow.getComponents().tabFolder.getTabItem().getLeaf().addFigureIndexOf(i,temporal.elementAt(x));
    					y++;
    					x++;
    				}
    				if(isCut){
    				ApplicationState._diagramAdministrator.diagram.removeAllElements();
    					isCut = false;
    				}
    				Repintar();
    			}
    		}
    	}
    }
    public void insertarFigura(final Figura inser){
    	final int i = ApplicationState._selectionAdministrator.getSelectedFigure();
    	if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getAdminDiagrama().diagram.elementAt(i) instanceof DecisionFigure){
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
    				int distan = MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getSelectedFigure()+1).getBounds().x-(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getSelectedFigure()).getBounds().x+
    				MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getSelectedFigure()).getBounds().width);
    				for(int j=ApplicationState._selectionAdministrator.getSelectedFigure()+1; j<MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getSizeDiagrama(); j++){
    					if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getSelectedFigure()).getBounds().x-MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(j).getBounds().x == distan){
    						MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getAdminDiagrama().orderDiagram(j, inser);
    						ApplicationState._selectionAdministrator.setSelectedFigure(j+1);
    						MainWindow.getComponents().tabFolder.getTabItem().getLeaf().addFigure();
    						break;
    					}
    				}
    				shell.close();
    				Repintar();
    				
    			}
    		});
    		derecha.addSelectionListener(new SelectionAdapter() {
    			public void widgetSelected(SelectionEvent event) {
    				MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getAdminDiagrama().orderDiagram(i+1, inser);
    				ApplicationState._selectionAdministrator.setSelectedFigure(i+2);
    				MainWindow.getComponents().tabFolder.getTabItem().getLeaf().addFigure();
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
    		MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getAdminDiagrama().orderDiagram(i, inser);
    		MainWindow.getComponents().tabFolder.getTabItem().getLeaf().addFigure();
    		MainWindow.getComponents().tabFolder.getTabItem().getLeaf().guardarRetroceso();
    		MainWindow.getComponents().tabFolder.getTabItem().getSave().setSave(false);
    	}
    }
    public void Eliminar(Figura fig){
    	String tipo ="";
    	for(int x=0;x<MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getSizeDiagrama();x++){
    		if(x == ApplicationState._selectionAdministrator.getSelectedFigure()){
    			int y=0,x2=0,cont=0;
    			if(fig instanceof DecisionFigure){
    				tipo="si";
    				y = RecorridoDiagrama.recorridoCiclo(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama(),x);
    				y = RecorridoDiagrama.recorridoCiclo2(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama(),y);
    				x2=y-x;
    				while(cont<x2+2){
    					MainWindow.getComponents().tabFolder.getTabItem().getLeaf().removeFigureIndexOf(x);
    					cont++;
    				}
    			} 
    			else if(fig instanceof ForFigure){
    				tipo="para";
    				y = RecorridoDiagrama.recorridoCiclo3(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama(),x);
    				x2=y-x;
    				while(cont<x2+6){
    					MainWindow.getComponents().tabFolder.getTabItem().getLeaf().removeFigureIndexOf(x);
    					cont++;
    				}
    			}
    			else if(fig instanceof WhileFigure){
    				tipo="mientras";
    				y = RecorridoDiagrama.recorridoCiclo3(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama(),x);
    				x2=y-x;
    				while(cont<x2+6){
    					MainWindow.getComponents().tabFolder.getTabItem().getLeaf().removeFigureIndexOf(x);
    					cont++;
    				}
    			}
    			else{
    				MainWindow.getComponents().tabFolder.getTabItem().getLeaf().removeFigureIndexOf(x);
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
    			MainWindow.getComponents().tabFolder.getTabItem().getInformation().addInformation("/D - Se elimino una figura de tipo \" "+tipo+"\"");
    			MainWindow.getComponents().tabFolder.getTabItem().getInformation().setDiagram(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama());
    			ApplicationState._selectionAdministrator.setSelectedFigure(-1);
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
    	MainWindow.getComponents().tabFolder.getTabItem().getSave().setSave(false);
    	MainWindow.getComponents().tabFolder.getTabItem().getLeaf().addFigure();
    	MainWindow.getComponents().tabFolder.getTabItem().getLeaf().guardarRetroceso();
    }
}
