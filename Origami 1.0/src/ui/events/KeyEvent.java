package ui.events;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import Administracion.AdminSeleccion;
import Administracion.TabFolder;
import Administracion.TabItem;
import Administracion.Funcionalidad.CodeCompiler;
import Administracion.Funcionalidad.DiagramFileManager;
import Administracion.Funcionalidad.Codigo.Instruccion;
import Administracion.actions.AddFigureLogic;
import Administracion.actions.ContextualMenuActions;
import Grafico.MainWindow;
import Grafico.Figuras.CircleFigure;
import Grafico.Help.AboutWindow;
import Grafico.Help.HelpWindow;
import Imagenes.ImageLoader;


public class KeyEvent {
    private int key,key2=0;
    private AdminSeleccion selec;
    private TabFolder tab;
    private DiagramFileManager ser = new DiagramFileManager();
    
    public KeyEvent(AdminSeleccion seleccion, TabFolder tabfolder){
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
		    MainWindow.getComponents().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorEntrada.png").getImageData(), 0, 0);
		    new AddFigureLogic().addInput();
		    MainWindow.getComponents().disableCursor();
		}
		break;
	case 50:
		if(!MainWindow.getComponentes().isPasoAPaso){
		    MainWindow.getComponents().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorProceso.png").getImageData(), 0, 0);
		    new AddFigureLogic().addSentence();
		    MainWindow.getComponents().disableCursor();
		}
		break;
	case 51:
		if(!MainWindow.getComponentes().isPasoAPaso){
		    MainWindow.getComponents().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorIf.png").getImageData(), 0, 0);
		    new AddFigureLogic().addDecision();
		    MainWindow.getComponents().disableCursor();
		}
		break;
	case 52:
		if(!MainWindow.getComponentes().isPasoAPaso){
		    MainWindow.getComponents().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorWhile.png").getImageData(), 0, 0);
		    new AddFigureLogic().addWhile();
		    MainWindow.getComponents().disableCursor();
		}
		break;
	case 53:
		if(!MainWindow.getComponentes().isPasoAPaso){
		    MainWindow.getComponents().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorFor.png").getImageData(), 0, 0);
		    new AddFigureLogic().addFor();
		    MainWindow.getComponents().disableCursor();
		}
		break;
	case 54:
		if(!MainWindow.getComponentes().isPasoAPaso){
		    MainWindow.getComponents().cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorSalida.png").getImageData(), 0, 0);
		    new AddFigureLogic().addOutput();
		    MainWindow.getComponents().disableCursor();
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
		       tab.getHoja().addFigure();
		       tab.getHoja().guardarRetroceso();
		}
       break;
	case 127:
		if(selec.getFiguraSeleccionada()!=-1){
			if(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()) instanceof CircleFigure){
			}
			else{
			    new ContextualMenuActions().Eliminar(tab.getHoja().getDiagrama().elementAt(selec.getFiguraSeleccionada()));
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
		codigo7.main(tab.getHoja().getDiagrama());
		codigo7.createWindow(MainWindow.display);
		break;
	case 16777230:
		if(!MainWindow.getComponentes().isPasoAPaso){
			CodeCompiler codigo = new CodeCompiler(MainWindow.getComponents()._diagrams);
			if(MainWindow.getComponents().getEnEjecucion()){
				MainWindow.getComponents().stopEjecucion();
			}
			codigo.main(false,true);
			if(codigo.isError){
				int aux = MainWindow.getComponents().console.text.getText().length();
				if(aux>=0){
					MainWindow.getComponents().console.text.setText("");
				}
				MainWindow.getComponents().console.text.setText(codigo.errorTipe);
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
		    		messageBox.setMessage("El nombre de archivo, directorio o etiqueta del volum�n no es v�lido");
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
			    		messageBox.setMessage("El archivo ya existe. �Desea reemplazarlo?");
			    		int seleccion = messageBox.open();
			    		switch(seleccion){
			    			case 64:
						    	ser.setFile(archivo);
						    	tab.getTabItem().getSave().setDir(archivo);
						    	ser.saveDiagram(tab);
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
				    	ser.setFile(archivo);
				    	tab.getTabItem().getSave().setDir(archivo);
				    	boolean error = ser.saveDiagram(tab);
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
	    	ser.setFile(tab.getTabItem().getSave().getDir());
	    	ser.saveDiagram(tab);
	    	tab.getTabItem().getSave().setSave(true);
		}
	}
	else if(key+key2 == 262264){
		if(selec.getFiguraSeleccionada()!=-1){
			if(tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()) instanceof CircleFigure || selec.getFiguraSeleccionada() == -1){
			}
			else{
			    new ContextualMenuActions().Cortar(tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()));	
				MainWindow.getComponentes().toolBarDisable();
			}
		}
	}
	else if(key+key2 == 262243){
		if(selec.getFiguraSeleccionada()!=-1){
			if(tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()) instanceof CircleFigure || selec.getFiguraSeleccionada() == -1){
			}
			else{
			    new ContextualMenuActions().Copiar(tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()));
				MainWindow.getComponentes().toolBarDisable();
			}
		}
	}
	else if(key+key2 == 262262){
		if(selec.getFiguraSeleccionada()!=-1){
			if(MainWindow._diagramAdministrator.diagrama.size()>0){
			    new ContextualMenuActions().Pegar(tab.getHoja().getFigureIndexOf(selec.getFiguraSeleccionada()));
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
