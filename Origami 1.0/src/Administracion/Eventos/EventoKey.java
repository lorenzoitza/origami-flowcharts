package Administracion.Eventos;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.TabItem;
import Administracion.Funcionalidad.CodeCompiler;
import Administracion.Funcionalidad.SerializeFile;
import Administracion.Funcionalidad.Codigo.Instruccion;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.MainWindow;
import Grafico.Figuras.*;
import Grafico.Help.*;
import Imagenes.ImageLoader;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class EventoKey {
	private int key,key2=0;
	private AdminSeleccion selec;
	private TabFolder tab;
	private SerializeFile ser = new SerializeFile();
	
	public EventoKey(AdminSeleccion seleccion, TabFolder tabfolder){
		selec = seleccion;
		tab = tabfolder;
	}
	public void setKey(org.eclipse.swt.events.KeyEvent e){
		key = e.keyCode;
		key2 = e.stateMask;
	}
	public void Accion(){
		switch(key){
		case 49:
			if(!MainWindow.getComponentes().isPasoAPaso){
				//String name5 = "imagenes//cursorEntrada.png";
				//ImageData image5 = new ImageData(name5);
				MainWindow.getComponentes().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorEntrada.png").getImageData(), 0, 0);
			    InputFigure entrada2 = new InputFigure();
			    InstruccionSimple codigo5 = new InstruccionSimple();
			    codigo5.setInstruccionSimple("null");
				entrada2.instruction.instruccion = "null";
			    MainWindow.mainFigure = null;
			    MainWindow.mainFigure = entrada2;
			    MainWindow.isCut = false;
			}
			break;
		case 50:
			if(!MainWindow.getComponentes().isPasoAPaso){
				//String name4 = "imagenes//cursorProceso.png";
			    //ImageData image4 = new ImageData(name4);
			    MainWindow.getComponentes().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorProceso.png").getImageData(), 0, 0);
			    SentenceFigure proceso2 = new SentenceFigure();
			    InstruccionSimple codigo4 = new InstruccionSimple();
			    codigo4.setInstruccionSimple("null");
				proceso2.instruccion.instruccion = "null";
			    MainWindow.mainFigure = null;
			    MainWindow.mainFigure = proceso2;
			    MainWindow.isCut = false;
			}
			break;
		case 51:
			if(!MainWindow.getComponentes().isPasoAPaso){
				//String name = "imagenes//cursorIf.png";
			    //ImageData image = new ImageData(name);
			    MainWindow.getComponentes().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorIf.png").getImageData(), 0, 0);
			    DecisionFigure decision2 = new DecisionFigure();
			    InstruccionSimple codigo = new InstruccionSimple();
				codigo.setInstruccionSimple("null");
				decision2.instruction.instruccion.add(0,codigo);
				MainWindow.mainFigure = null;
			    MainWindow.mainFigure = decision2;
			    MainWindow.isCut = false;
			}
			break;
		case 52:
			if(!MainWindow.getComponentes().isPasoAPaso){
				//String name3 = "imagenes//cursorWhile.png";
			    //ImageData image3 = new ImageData(name3);
			    MainWindow.getComponentes().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorWhile.png").getImageData(), 0, 0);
			    WhileFigure While2 = new WhileFigure();
			    InstruccionSimple codigo3 = new InstruccionSimple();
				codigo3.setInstruccionSimple("null");
				While2.instruccion.instruccion.add(0,codigo3);
			    MainWindow.mainFigure = null;
			    MainWindow.mainFigure = While2;
			    MainWindow.isCut = false;
			}
			break;
		case 53:
			if(!MainWindow.getComponentes().isPasoAPaso){
				//String name2 = "imagenes//cursorFor.png";
			    //ImageData image2 = new ImageData(name2);
			    MainWindow.getComponentes().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorFor.png").getImageData(), 0, 0);
			    ForFigure For2 = new ForFigure();
			    InstruccionSimple codigo2 = new InstruccionSimple();
			    codigo2.setInstruccionSimple("null");
			    For2.instruction.instruccion.add(0,codigo2);
			    MainWindow.mainFigure = null;
			    MainWindow.mainFigure = For2;
			    MainWindow.isCut = false;
			}
			break;
		case 54:
			if(!MainWindow.getComponentes().isPasoAPaso){
				//String name6 = "imagenes//cursorSalida.png";
			    //ImageData image6 = new ImageData(name6);
			    MainWindow.getComponentes().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorSalida.png").getImageData(), 0, 0);
			    OutputFigure salida2 = new OutputFigure();
			    InstruccionSimple codigo6 = new InstruccionSimple();
				codigo6.setInstruccionSimple("null");
				salida2.instruction.instruccion = "null";
			    MainWindow.mainFigure = null;
			    MainWindow.mainFigure = salida2;
			    MainWindow.isCut = false;
			}
			break;
		case 27:
			if(!MainWindow.getComponentes().isPasoAPaso){
				 Cursor[] cursor = new Cursor[1];
				   Grafico.MainWindow.mainFigure = null;
				   Cursor oldCursor = cursor[0];
			       cursor[0] = new Cursor(null, SWT.CURSOR_ARROW);
			       tab.getHoja().getDibujarDiagrama().setCursor(cursor[0]);
			       if(oldCursor != null){
			    	   oldCursor.dispose();
			       }
			       MainWindow.isCut = true;
			       tab.getHoja().addFigure();
			       tab.getHoja().guardarRetroceso();
			}
	       break;
		case 127:
			if(selec.getFiguraSeleccionada()!=-1){
				if(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()) instanceof CircleFigure){
				}
				else{
					EventoMenuContextual.Eliminar(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()));
				}
			}
			break;
		case 16777226:
			HelpWindow help = new HelpWindow();
			help.createWindow();
			help.showWindow();
			break;
		case 16777227:
			AboutWindow acercade = new AboutWindow();
			acercade.createWindow(MainWindow.display);
			acercade.showWindow();
			break;
		case 16777228:
			if(!MainWindow.getComponentes().isPasoAPaso){
				for(int y=tab.getHoja().getSizeDiagrama()-1;y>0;y--){
					tab.getHoja().removeFigureIndexOf(y);
				}
				CircleFigure fin = new CircleFigure();
				selec.setFiguraSeleccionada(0);
				tab.getHoja().getDiagrama().add(fin);
				fin.setMesagge("  Fin");
				tab.getHoja().resetScrollBar();
				tab.getHoja().addFigure();
				tab.getHoja().guardarRetroceso();
				tab.getTabItem().getSave().setSave(false);
			}
			break;
		case 16777229:
			Instruccion codigo7 = new Instruccion();
			codigo7.main(tab.getHoja().getDiagrama(),true);
			codigo7.ventana(MainWindow.display);
			break;
		case 16777230:
			if(!MainWindow.getComponentes().isPasoAPaso){
				CodeCompiler codigo = new CodeCompiler(MainWindow._diagrams);
				if(MainWindow.getComponents().getEnEjecucion()){
					MainWindow.getComponents().stopEjecucion();
				}
				codigo.main(false,true);
				if(codigo.isError){
					int aux = MainWindow.getComponents().text.getText().length();
					if(aux>=0){
						MainWindow.getComponents().text.setText("");
					}
					MainWindow.getComponents().text.setText(codigo.errorTipe);
					tab.getTabItem().getInfo().addInformation("/Ec - Error en la compilacion:");
					tab.getTabItem().getInfo().addInformation(codigo.errorTipe);
					codigo.deleteMainFiles();
				}
				else{
					MainWindow.getComponents().ejecutar(true,codigo);
					tab.getTabItem().getInfo().addInformation("/C - Se Compilo el diagrama de manera correcta");
				}
				if(!MainWindow.menu.consoleMenuItem.getSelection()){
					MainWindow.menu.consoleMenuItem.setSelection(true);
					MainWindow.getComponents().moverConsola(true);
				}
			}
			break;
		}
		if(key+key2 == 262241){
			if(MainWindow.getComponents().eje != null && MainWindow.getComponents().getEnEjecucion()
					&& tab.getSelectedTabItemId() == MainWindow.getComponents().eje.a.GetId()){
				MainWindow.getComponents().stopEjecucion();
			}
			else if(MainWindow.getComponents().paso != null && MainWindow.getComponents().getEnEjecucion()
					&& tab.getSelectedTabItemId() == MainWindow.getComponents().paso.a.GetId()){
				MainWindow.getComponents().stopEjecucion();
			}
			FileDialog dialog = new FileDialog(MainWindow.shell,SWT.OPEN);
		    dialog.setFilterExtensions(new String[] { "*.Org","*.*" });
		    String archivo = dialog.open();
		    if(archivo!=null){
		    	File file = new File(archivo);
		    	if(file.exists()){
		    		if(tab.getHoja().getSizeDiagrama()==0){
				    	String archivo2 = dialog.getFileName();
				    	int pos = archivo2.indexOf('.');
				    	String name = archivo2.substring(0, pos);
				    	tab.cambiarNombre("*"+name);
				    	tab.getTabItem().getSave().setSave(true);
				    	tab.abrir(archivo,ser);
				    	tab.getTabItem().getSave().setDir(archivo);
				    	
			    	}
			    	else{
			    		MainWindow._selectionAdministrator.setFiguraSeleccionada(0);
					    tab.getHoja().openFile(archivo);
					    tab.getTabItem().getSave().setDir(archivo);
					    archivo = dialog.getFileName();
					    int pos = archivo.indexOf('.');
					    String name = archivo.substring(0, pos);
					    tab.cambiarNombre("*"+name);
				    	tab.getTabItem().getSave().setSave(true);
				    	tab.getTabItem().resetRetroceso();		 
				    	tab.getTabItem().agregarRetroceso(tab.getHoja().getDiagrama(), selec);
			    	}
			    	MainWindow.getComponentes().disablePasoAPaso(false);
			    	MainWindow.getComponents().disableAll(true);
		    	}
		     }
		}
		else if(key+key2 == 262247){
			if(tab.getTabItem().getSave().getDir()=="null"){
				FileDialog dialog = new FileDialog(MainWindow.shell,SWT.SAVE);
			    dialog.setFilterExtensions(new String[] { "*.Org"});
			    String archivo = dialog.open();
			    if(archivo!=null){
			    	if(dialog.getFileName().contains("\\") || dialog.getFileName().contains("/") ||
		    				dialog.getFileName().contains(":") || dialog.getFileName().contains("*") ||
		    						dialog.getFileName().contains("?") || dialog.getFileName().contains("<") ||
		    						dialog.getFileName().contains(">") || dialog.getFileName().contains("|") ||
		    						dialog.getFileName().contains("\"")){
		    			MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_ERROR| SWT.OK);
			    		messageBox.setText("Origami");
			    		messageBox.setMessage("El nombre de archivo, directorio o etiqueta del volumén no es válido");
			    		int seleccion = messageBox.open();
			    		switch(seleccion){
			    			case 64:
			    				break;
			    			case 128:			    	
			    				break;
			    		}
		    		}
		    		else{
		    			boolean existe = false;
		    			try{
		    				File arch = new File(archivo);
		    				if(arch.exists()){
		    					existe = true;
		    				}
		    			}
		    			catch(Exception e1){
		    			}		    	
		    			if(existe){
		    				MessageBox messageBox = new MessageBox(MainWindow.shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
				    		messageBox.setText("Origami");
				    		messageBox.setMessage("El archivo ya existe. ¿Desea reemplazarlo?");
				    		int seleccion = messageBox.open();
				    		switch(seleccion){
				    			case 64:
							    	ser.SetFil(archivo);
							    	tab.getTabItem().getSave().setDir(archivo);
							    	ser.saveFile(tab);
							    	archivo = dialog.getFileName();
							    	int pos = archivo.indexOf('.');
							    	String name = archivo.substring(0, pos);
							    	tab.cambiarNombre("*"+name);
							    	tab.getTabItem().getSave().setSave(true);
				    				break;
				    			case 128:							
				    				break;
				    		}
				    	}
				    	else{
					    	ser.SetFil(archivo);
					    	tab.getTabItem().getSave().setDir(archivo);
					    	boolean error = ser.saveFile(tab);
					    	if(error){
					    		archivo = dialog.getFileName();
					    		int pos = archivo.indexOf('.');
						    	String name = archivo.substring(0, pos);
						    	tab.cambiarNombre("*"+name);
						    	tab.getTabItem().getSave().setSave(true);
					    	}
				    	}
		    		}
			    }
			}
			else{
		    	ser.SetFil(tab.getTabItem().getSave().getDir());
		    	ser.saveFile(tab);
		    	tab.getTabItem().getSave().setSave(true);
			}
		}
		else if(key+key2 == 262264){
			if(selec.getFiguraSeleccionada()!=-1){
				if(tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()) instanceof CircleFigure || selec.getFiguraSeleccionada() == -1){
				}
				else{
					EventoMenuContextual.Cortar(tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()));	
					MainWindow.getComponentes().toolBarDisable();
				}
			}
		}
		else if(key+key2 == 262243){
			if(selec.getFiguraSeleccionada()!=-1){
				if(tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()) instanceof CircleFigure || selec.getFiguraSeleccionada() == -1){
				}
				else{
					EventoMenuContextual.Copiar(tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()));
					MainWindow.getComponentes().toolBarDisable();
				}
			}
		}
		else if(key+key2 == 262262){
			if(selec.getFiguraSeleccionada()!=-1){
				if(MainWindow._diagramAdministrator.diagrama.size()>0){
					EventoMenuContextual.Pegar(tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()));
					MainWindow.getComponentes().toolBarDisable();
				}
			}
		}
		else if(key+key2 == 262254){
			tab.addTabItem();
			MainWindow.getComponentes().guardarDisable(true);
			MainWindow.getComponents().disableAll(true);
		}
		else if(key+key2 == 262266){
			if(!MainWindow.getComponentes().isPasoAPaso){
				TabItem item = (TabItem)tab.getSeleccion();
				item.retroceder();
				tab.getTabItem().getSave().setSave(false);
			}
		}
		else if(key+key2 == 262245){
			/*//llevra a un txt la info
			 FileDialog dialog = new FileDialog(Ventana.shell,SWT.SAVE);
		     dialog.setFilterExtensions(new String[] { "*.txt","*.*"});
		     String archivo = dialog.open();
		     if(archivo!=null){
		    	 Exportar exp = new Exportar(tab);
					exp.exportarInfo(archivo);
		     }*/
		}
	}
	public boolean PresentEnter(){
		if(key == 13 || key == 16777296){
			return true;
		}
		else{
			return false;
		}
	}
}