package Administracion.Eventos;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.TabItem;
import Administracion.Funcionalidad.Compilar;
import Administracion.Funcionalidad.Exportar;
import Administracion.Funcionalidad.Serializar;
import Administracion.Funcionalidad.Codigo.Instruccion;
import Administracion.Funcionalidad.Codigo.InstruccionSimple;
import Grafico.Ventana;
import Grafico.Figuras.*;
import Grafico.Help.*;
import Imagenes.CargarImagenes;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class EventoKey {
	private int key,key2=0;
	private AdminSeleccion selec;
	private TabFolder tab;
	private Serializar ser = new Serializar();
	
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
			if(!Ventana.getComponentes().isPasoAPaso){
				//String name5 = "imagenes//cursorEntrada.png";
				//ImageData image5 = new ImageData(name5);
				Ventana.getComponentes().cursor[0] = new Cursor(Ventana.display, CargarImagenes.getImagen("cursorEntrada.png").getImageData(), 0, 0);
			    Entrada entrada2 = new Entrada(SWT.COLOR_DARK_BLUE);
			    InstruccionSimple codigo5 = new InstruccionSimple();
			    codigo5.setInstruccionSimple("null");
				entrada2.instruccion.instruccion = "null";
			    Ventana.figuraPrincipal = null;
			    Ventana.figuraPrincipal = entrada2;
			    Ventana.bandera = false;
			}
			break;
		case 50:
			if(!Ventana.getComponentes().isPasoAPaso){
				//String name4 = "imagenes//cursorProceso.png";
			    //ImageData image4 = new ImageData(name4);
			    Ventana.getComponentes().cursor[0] = new Cursor(Ventana.display, CargarImagenes.getImagen("cursorProceso.png").getImageData(), 0, 0);
			    Proceso proceso2 = new Proceso(SWT.COLOR_DARK_BLUE);
			    InstruccionSimple codigo4 = new InstruccionSimple();
			    codigo4.setInstruccionSimple("null");
				proceso2.instruccion.instruccion = "null";
			    Ventana.figuraPrincipal = null;
			    Ventana.figuraPrincipal = proceso2;
			    Ventana.bandera = false;
			}
			break;
		case 51:
			if(!Ventana.getComponentes().isPasoAPaso){
				//String name = "imagenes//cursorIf.png";
			    //ImageData image = new ImageData(name);
			    Ventana.getComponentes().cursor[0] = new Cursor(Ventana.display, CargarImagenes.getImagen("cursorIf.png").getImageData(), 0, 0);
			    If decision2 = new If(SWT.COLOR_DARK_BLUE);
			    InstruccionSimple codigo = new InstruccionSimple();
				codigo.setInstruccionSimple("null");
				decision2.instruccion.instruccion.add(0,codigo);
				Ventana.figuraPrincipal = null;
			    Ventana.figuraPrincipal = decision2;
			    Ventana.bandera = false;
			}
			break;
		case 52:
			if(!Ventana.getComponentes().isPasoAPaso){
				//String name3 = "imagenes//cursorWhile.png";
			    //ImageData image3 = new ImageData(name3);
			    Ventana.getComponentes().cursor[0] = new Cursor(Ventana.display, CargarImagenes.getImagen("cursorWhile.png").getImageData(), 0, 0);
			    While While2 = new While(SWT.COLOR_DARK_BLUE);
			    InstruccionSimple codigo3 = new InstruccionSimple();
				codigo3.setInstruccionSimple("null");
				While2.instruccion.instruccion.add(0,codigo3);
			    Ventana.figuraPrincipal = null;
			    Ventana.figuraPrincipal = While2;
			    Ventana.bandera = false;
			}
			break;
		case 53:
			if(!Ventana.getComponentes().isPasoAPaso){
				//String name2 = "imagenes//cursorFor.png";
			    //ImageData image2 = new ImageData(name2);
			    Ventana.getComponentes().cursor[0] = new Cursor(Ventana.display, CargarImagenes.getImagen("cursorFor.png").getImageData(), 0, 0);
			    For For2 = new For(SWT.COLOR_DARK_BLUE);
			    InstruccionSimple codigo2 = new InstruccionSimple();
			    codigo2.setInstruccionSimple("null");
			    For2.instruccion.instruccion.add(0,codigo2);
			    Ventana.figuraPrincipal = null;
			    Ventana.figuraPrincipal = For2;
			    Ventana.bandera = false;
			}
			break;
		case 54:
			if(!Ventana.getComponentes().isPasoAPaso){
				//String name6 = "imagenes//cursorSalida.png";
			    //ImageData image6 = new ImageData(name6);
			    Ventana.getComponentes().cursor[0] = new Cursor(Ventana.display, CargarImagenes.getImagen("cursorSalida.png").getImageData(), 0, 0);
			    Imprimir salida2 = new Imprimir(SWT.COLOR_DARK_BLUE);
			    InstruccionSimple codigo6 = new InstruccionSimple();
				codigo6.setInstruccionSimple("null");
				salida2.instruccion.instruccion = "null";
			    Ventana.figuraPrincipal = null;
			    Ventana.figuraPrincipal = salida2;
			    Ventana.bandera = false;
			}
			break;
		case 27:
			if(!Ventana.getComponentes().isPasoAPaso){
				 Cursor[] cursor = new Cursor[1];
				   Grafico.Ventana.figuraPrincipal = null;
				   Cursor oldCursor = cursor[0];
			       cursor[0] = new Cursor(null, SWT.CURSOR_ARROW);
			       tab.getHoja().getDibujarDiagrama().setCursor(cursor[0]);
			       if(oldCursor != null){
			    	   oldCursor.dispose();
			       }
			       Ventana.bandera = true;
			       tab.getHoja().addFigure();
			       tab.getHoja().guardarRetroceso();
			}
	       break;
		case 127:
			if(selec.getFiguraSeleccionada()!=-1){
				if(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()) instanceof InicioFin){
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
			acercade.createWindow(Ventana.display);
			acercade.showWindow();
			break;
		case 16777228:
			if(!Ventana.getComponentes().isPasoAPaso){
				for(int y=tab.getHoja().getSizeDiagrama()-1;y>0;y--){
					tab.getHoja().removeFigureIndexOf(y);
				}
				InicioFin fin = new InicioFin();
				selec.setFiguraSeleccionada(0);
				tab.getHoja().getDiagrama().add(fin);
				fin.setMensaje("  Fin");
				tab.getHoja().resetScrollBar();
				tab.getHoja().addFigure();
				tab.getHoja().guardarRetroceso();
				tab.getTabItem().getSave().setSave(false);
			}
			break;
		case 16777229:
			Instruccion codigo7 = new Instruccion();
			codigo7.main(tab.getHoja().getDiagrama(),true);
			codigo7.ventana(Ventana.display);
			break;
		case 16777230:
			if(!Ventana.getComponentes().isPasoAPaso){
				Compilar codigo = new Compilar(Ventana.diagramas);
				if(Ventana.componentes.getEnEjecucion()){
					Ventana.componentes.stopEjecucion();
				}
				codigo.main(false,true);
				if(codigo.errorBandera){
					int aux = Ventana.componentes.text.getText().length();
					if(aux>=0){
						Ventana.componentes.text.setText("");
					}
					Ventana.componentes.text.setText(codigo.error);
					tab.getTabItem().getInfo().addInformation("/Ec - Error en la compilacion:");
					tab.getTabItem().getInfo().addInformation(codigo.error);
					codigo.eliminarArchivosCompilar();
				}
				else{
					Ventana.componentes.ejecutar(true,codigo);
					tab.getTabItem().getInfo().addInformation("/C - Se Compilo el diagrama de manera correcta");
				}
				if(!Ventana.Consola.getSelection()){
					Ventana.Consola.setSelection(true);
					Ventana.componentes.moverConsola(true);
				}
			}
			break;
		}
		if(key+key2 == 262241){
			if(Ventana.componentes.eje != null && Ventana.componentes.getEnEjecucion()
					&& tab.getSelectedTabItemId() == Ventana.componentes.eje.a.GetId()){
				Ventana.componentes.stopEjecucion();
			}
			else if(Ventana.componentes.paso != null && Ventana.componentes.getEnEjecucion()
					&& tab.getSelectedTabItemId() == Ventana.componentes.paso.a.GetId()){
				Ventana.componentes.stopEjecucion();
			}
			FileDialog dialog = new FileDialog(Ventana.shell,SWT.OPEN);
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
			    		Ventana.selec.setFiguraSeleccionada(0);
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
			    	Ventana.getComponentes().disablePasoAPaso(false);
			    	Ventana.componentes.disableAll(true);
		    	}
		     }
		}
		else if(key+key2 == 262247){
			if(tab.getTabItem().getSave().getDir()=="null"){
				FileDialog dialog = new FileDialog(Ventana.shell,SWT.SAVE);
			    dialog.setFilterExtensions(new String[] { "*.Org"});
			    String archivo = dialog.open();
			    if(archivo!=null){
			    	if(dialog.getFileName().contains("\\") || dialog.getFileName().contains("/") ||
		    				dialog.getFileName().contains(":") || dialog.getFileName().contains("*") ||
		    						dialog.getFileName().contains("?") || dialog.getFileName().contains("<") ||
		    						dialog.getFileName().contains(">") || dialog.getFileName().contains("|") ||
		    						dialog.getFileName().contains("\"")){
		    			MessageBox messageBox = new MessageBox(Ventana.shell, SWT.ICON_ERROR| SWT.OK);
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
		    				MessageBox messageBox = new MessageBox(Ventana.shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
				    		messageBox.setText("Origami");
				    		messageBox.setMessage("El archivo ya existe. ¿Desea reemplazarlo?");
				    		int seleccion = messageBox.open();
				    		switch(seleccion){
				    			case 64:
							    	ser.SetFil(archivo);
							    	tab.getTabItem().getSave().setDir(archivo);
							    	ser.guardar(tab);
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
					    	boolean error = ser.guardar(tab);
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
		    	ser.guardar(tab);
		    	tab.getTabItem().getSave().setSave(true);
			}
		}
		else if(key+key2 == 262264){
			if(selec.getFiguraSeleccionada()!=-1){
				if(tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()) instanceof InicioFin || selec.getFiguraSeleccionada() == -1){
				}
				else{
					EventoMenuContextual.Cortar(tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()));	
					Ventana.getComponentes().toolBarDisable();
				}
			}
		}
		else if(key+key2 == 262243){
			if(selec.getFiguraSeleccionada()!=-1){
				if(tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()) instanceof InicioFin || selec.getFiguraSeleccionada() == -1){
				}
				else{
					EventoMenuContextual.Copiar(tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()));
					Ventana.getComponentes().toolBarDisable();
				}
			}
		}
		else if(key+key2 == 262262){
			if(selec.getFiguraSeleccionada()!=-1){
				if(Ventana.diagramaEnMemoria.diagrama.size()>0){
					EventoMenuContextual.Pegar(tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()));
					Ventana.getComponentes().toolBarDisable();
				}
			}
		}
		else if(key+key2 == 262254){
			tab.addTabItem();
			Ventana.getComponentes().guardarDisable(true);
			Ventana.componentes.disableAll(true);
		}
		else if(key+key2 == 262266){
			if(!Ventana.getComponentes().isPasoAPaso){
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