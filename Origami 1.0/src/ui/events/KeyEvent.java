package ui.events;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
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
import Grafico.view.OpenFileView;
import Grafico.view.OpenType;
import Grafico.view.SaveFileView;
import Grafico.view.SaveType;
import Imagenes.ImageLoader;


public class KeyEvent {
    private int key,key2=0;
    private DiagramFileManager ser = new DiagramFileManager();
    
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
		       MainWindow.getComponentes().diagramas.getHoja().getDibujarDiagrama().setCursor(cursor[0]);
		       if(oldCursor != null){
		    	   oldCursor.dispose();
		       }
		       MainWindow.getComponentes().diagramas.getHoja().addFigure();
		       MainWindow.getComponentes().diagramas.getHoja().guardarRetroceso();
		}
       break;
	case 127:
		if(MainWindow._selectionAdministrator.getFiguraSeleccionada()!=-1){
			if(MainWindow.getComponentes().diagramas.getHoja().getDiagrama().elementAt(MainWindow._selectionAdministrator.getFiguraSeleccionada()) instanceof CircleFigure){
			}
			else{
			    new ContextualMenuActions().Eliminar(MainWindow.getComponentes().diagramas.getHoja().getDiagrama().elementAt(MainWindow._selectionAdministrator.getFiguraSeleccionada()));
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
			for(int y=MainWindow.getComponentes().diagramas.getHoja().getSizeDiagrama()-1;y>0;y--){
				MainWindow.getComponentes().diagramas.getHoja().removeFigureIndexOf(y);
			}
			CircleFigure fin = new CircleFigure();
			MainWindow._selectionAdministrator.setFiguraSeleccionada(0);
			MainWindow.getComponentes().diagramas.getHoja().getDiagrama().add(fin);
			fin.setMesagge("  Fin");
			MainWindow.getComponentes().diagramas.getHoja().resetScrollBar();
			MainWindow.getComponentes().diagramas.getHoja().addFigure();
			MainWindow.getComponentes().diagramas.getHoja().guardarRetroceso();
			MainWindow.getComponentes().diagramas.getTabItem().getSave().setSave(false);
		}
		break;
	case 16777229:
		Instruccion codigo7 = new Instruccion();
		codigo7.main(MainWindow.getComponentes().diagramas.getHoja().getDiagrama());
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
				MainWindow.getComponentes().diagramas.getTabItem().getInfo().addInformation("/Ec - Error en la compilacion:");
				MainWindow.getComponentes().diagramas.getTabItem().getInfo().addInformation(codigo.errorTipe);
				codigo.deleteMainFiles();
			}
			else{
				MainWindow.getComponents().ejecutar(true,codigo);
				MainWindow.getComponentes().diagramas.getTabItem().getInfo().addInformation("/C - Se Compilo el diagrama de manera correcta");
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
		&& MainWindow.getComponentes().diagramas.getSelectedTabItemId() == MainWindow.getComponents().eje.a.GetId()){
		MainWindow.getComponents().stopEjecucion();
	    }
	    else if(MainWindow.getComponents().paso != null && MainWindow.getComponents().getEnEjecucion()
		    && MainWindow.getComponentes().diagramas.getSelectedTabItemId() == MainWindow.getComponents().paso.a.GetId()){
			MainWindow.getComponents().stopEjecucion();
	    }
	    OpenFileView open = new OpenFileView();
	    open.setOpenType(OpenType.OPEN);
	    open.createWindow();
	}
	else if(key+key2 == 262247){
	    SaveFileView save = new SaveFileView();
	    if(MainWindow.getComponentes().diagramas.getTabItem().getSave().getDir()=="null"){
		save.setSaveType(SaveType.SAVE);
		save.createWindow();
	    }
	    else{
		save.saveAction();
	    }
	}
	else if(key+key2 == 262264){
		if(MainWindow._selectionAdministrator.getFiguraSeleccionada()!=-1){
			if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(MainWindow._selectionAdministrator.getFiguraSeleccionada()) instanceof CircleFigure || MainWindow._selectionAdministrator.getFiguraSeleccionada() == -1){
			}
			else{
			    new ContextualMenuActions().Cortar(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(MainWindow._selectionAdministrator.getFiguraSeleccionada()));	
				MainWindow.getComponentes().toolBarDisable();
			}
		}
	}
	else if(key+key2 == 262243){
		if(MainWindow._selectionAdministrator.getFiguraSeleccionada()!=-1){
			if(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(MainWindow._selectionAdministrator.getFiguraSeleccionada()) instanceof CircleFigure || MainWindow._selectionAdministrator.getFiguraSeleccionada() == -1){
			}
			else{
			    new ContextualMenuActions().Copiar(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(MainWindow._selectionAdministrator.getFiguraSeleccionada()));
				MainWindow.getComponentes().toolBarDisable();
			}
		}
	}
	else if(key+key2 == 262262){
		if(MainWindow._selectionAdministrator.getFiguraSeleccionada()!=-1){
			if(MainWindow._diagramAdministrator.diagrama.size()>0){
			    new ContextualMenuActions().Pegar(MainWindow.getComponentes().diagramas.getHoja().getFigureIndexOf(MainWindow._selectionAdministrator.getFiguraSeleccionada()));
				MainWindow.getComponentes().toolBarDisable();
			}
		}
	}
	else if(key+key2 == 262254){
		MainWindow.getComponentes().diagramas.addTabItem();
		MainWindow.getComponentes().guardarDisable(true);
		MainWindow.getComponents().disableAll(true);
	}
	else if(key+key2 == 262266){
		if(!MainWindow.getComponentes().isPasoAPaso){
			TabItem item = (TabItem)MainWindow.getComponentes().diagramas.getSeleccion();
			item.retroceder();
			MainWindow.getComponentes().diagramas.getTabItem().getSave().setSave(false);
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
