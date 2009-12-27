package ui.listener;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import Administracion.TabItem;
import Administracion.Funcionalidad.CodeCompiler;
import Administracion.Funcionalidad.DiagramFileManager;
import Administracion.Funcionalidad.Codigo.Instruccion;
import Administracion.actions.AddFigureLogic;
import Administracion.actions.ContextualMenuActions;
import Grafico.Componentes;
import Grafico.MainWindow;
import Grafico.Figuras.CircleFigure;
import Grafico.Help.AboutWindow;
import Grafico.Help.HelpWindow;
import Grafico.view.OpenFileView;
import Grafico.view.OpenType;
import Grafico.view.SaveFileView;
import Grafico.view.SaveType;
import Imagenes.ImageLoader;


public class KeyEvent implements KeyListener{
    private int key,key2=0;
    
    public void setKey(org.eclipse.swt.events.KeyEvent e){
    	key = e.keyCode;
    	key2 = e.stateMask;
    }
	public void Accion(){
	switch(key){
	case 49:
		if(!MainWindow.getComponents().isPasoAPaso){
		    MainWindow.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorEntrada.png").getImageData(), 0, 0);
		    new AddFigureLogic().addInput();
		    new AddFigureLogic().disableCursor();
		}
		break;
	case 50:
		if(!MainWindow.getComponents().isPasoAPaso){
		    MainWindow.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorProceso.png").getImageData(), 0, 0);
		    new AddFigureLogic().addSentence();
		    new AddFigureLogic().disableCursor();
		}
		break;
	case 51:
		if(!MainWindow.getComponents().isPasoAPaso){
		    MainWindow.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorIf.png").getImageData(), 0, 0);
		    new AddFigureLogic().addDecision();
		    new AddFigureLogic().disableCursor();
		}
		break;
	case 52:
		if(!MainWindow.getComponents().isPasoAPaso){
		    MainWindow.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorWhile.png").getImageData(), 0, 0);
		    new AddFigureLogic().addWhile();
		    new AddFigureLogic().disableCursor();
		}
		break;
	case 53:
		if(!MainWindow.getComponents().isPasoAPaso){
		    MainWindow.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorFor.png").getImageData(), 0, 0);
		    new AddFigureLogic().addFor();
		    new AddFigureLogic().disableCursor();
		}
		break;
	case 54:
		if(!MainWindow.getComponents().isPasoAPaso){
		    MainWindow.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorSalida.png").getImageData(), 0, 0);
		    new AddFigureLogic().addOutput();
		    new AddFigureLogic().disableCursor();
		}
		break;
	case 27:
		if(!MainWindow.getComponents().isPasoAPaso){
			 Cursor[] cursor = new Cursor[1];
			   Grafico.Componentes.mainFigure = null;
			   Cursor oldCursor = cursor[0];
		       cursor[0] = new Cursor(null, SWT.CURSOR_ARROW);
		       MainWindow.getComponents()._diagrams.getHoja().getDibujarDiagrama().setCursor(cursor[0]);
		       if(oldCursor != null){
		    	   oldCursor.dispose();
		       }
		       MainWindow.getComponents()._diagrams.getHoja().addFigure();
		       MainWindow.getComponents()._diagrams.getHoja().guardarRetroceso();
		}
       break;
	case 127:
		if(Componentes._selectionAdministrator.getFiguraSeleccionada()!=-1){
			if(MainWindow.getComponents()._diagrams.getHoja().getDiagrama().elementAt(Componentes._selectionAdministrator.getFiguraSeleccionada()) instanceof CircleFigure){
			}
			else{
			    new ContextualMenuActions().Eliminar(MainWindow.getComponents()._diagrams.getHoja().getDiagrama().elementAt(Componentes._selectionAdministrator.getFiguraSeleccionada()));
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
		if(!MainWindow.getComponents().isPasoAPaso){
			for(int y=MainWindow.getComponents()._diagrams.getHoja().getSizeDiagrama()-1;y>0;y--){
				MainWindow.getComponents()._diagrams.getHoja().removeFigureIndexOf(y);
			}
			CircleFigure fin = new CircleFigure();
			Componentes._selectionAdministrator.setFiguraSeleccionada(0);
			MainWindow.getComponents()._diagrams.getHoja().getDiagrama().add(fin);
			fin.setMesagge("  Fin");
			MainWindow.getComponents()._diagrams.getHoja().resetScrollBar();
			MainWindow.getComponents()._diagrams.getHoja().addFigure();
			MainWindow.getComponents()._diagrams.getHoja().guardarRetroceso();
			MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
		}
		break;
	case 16777229:
		Instruccion codigo7 = new Instruccion();
		codigo7.main(MainWindow.getComponents()._diagrams.getHoja().getDiagrama());
		codigo7.createWindow(MainWindow.display);
		break;
	case 16777230:
		if(!MainWindow.getComponents().isPasoAPaso){
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
				MainWindow.getComponents()._diagrams.getTabItem().getInfo().addInformation("/Ec - Error en la compilacion:");
				MainWindow.getComponents()._diagrams.getTabItem().getInfo().addInformation(codigo.errorTipe);
				codigo.deleteMainFiles();
			}
			else{
				MainWindow.getComponents().ejecutar(true,codigo);
				MainWindow.getComponents()._diagrams.getTabItem().getInfo().addInformation("/C - Se Compilo el diagrama de manera correcta");
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
		&& MainWindow.getComponents()._diagrams.getSelectedTabItemId() == MainWindow.getComponents().eje.a.GetId()){
		MainWindow.getComponents().stopEjecucion();
	    }
	    else if(MainWindow.getComponents().paso != null && MainWindow.getComponents().getEnEjecucion()
		    && MainWindow.getComponents()._diagrams.getSelectedTabItemId() == MainWindow.getComponents().paso.a.GetId()){
			MainWindow.getComponents().stopEjecucion();
	    }
	    OpenFileView open = new OpenFileView();
	    open.setOpenType(OpenType.OPEN);
	    open.createWindow();
	}
	else if(key+key2 == 262247){
	    SaveFileView save = new SaveFileView();
	    if(MainWindow.getComponents()._diagrams.getTabItem().getSave().getDir()=="null"){
		save.setSaveType(SaveType.SAVE);
		save.createWindow();
	    }
	    else{
		save.saveAction();
	    }
	}
	else if(key+key2 == 262264){
		if(Componentes._selectionAdministrator.getFiguraSeleccionada()!=-1){
			if(MainWindow.getComponents()._diagrams.getHoja().getFigureIndexOf(Componentes._selectionAdministrator.getFiguraSeleccionada()) instanceof CircleFigure || Componentes._selectionAdministrator.getFiguraSeleccionada() == -1){
			}
			else{
			    new ContextualMenuActions().Cortar(MainWindow.getComponents()._diagrams.getHoja().getFigureIndexOf(Componentes._selectionAdministrator.getFiguraSeleccionada()));	
				MainWindow.getComponents().barraHerramientas.toolBarDisable();
			}
		}
	}
	else if(key+key2 == 262243){
		if(Componentes._selectionAdministrator.getFiguraSeleccionada()!=-1){
			if(MainWindow.getComponents()._diagrams.getHoja().getFigureIndexOf(Componentes._selectionAdministrator.getFiguraSeleccionada()) instanceof CircleFigure || Componentes._selectionAdministrator.getFiguraSeleccionada() == -1){
			}
			else{
			    new ContextualMenuActions().Copiar(MainWindow.getComponents()._diagrams.getHoja().getFigureIndexOf(Componentes._selectionAdministrator.getFiguraSeleccionada()));
				MainWindow.getComponents().barraHerramientas.toolBarDisable();
			}
		}
	}
	else if(key+key2 == 262262){
		if(Componentes._selectionAdministrator.getFiguraSeleccionada()!=-1){
			if(Componentes._diagramAdministrator.diagrama.size()>0){
			    new ContextualMenuActions().Pegar(MainWindow.getComponents()._diagrams.getHoja().getFigureIndexOf(Componentes._selectionAdministrator.getFiguraSeleccionada()));
				MainWindow.getComponents().barraHerramientas.toolBarDisable();
			}
		}
	}
	else if(key+key2 == 262254){
		MainWindow.getComponents()._diagrams.addTabItem();
		MainWindow.getComponents().guardarDisable(true);
		MainWindow.getComponents().disableAll(true);
	}
	else if(key+key2 == 262266){
		if(!MainWindow.getComponents().isPasoAPaso){
			TabItem item = (TabItem)MainWindow.getComponents()._diagrams.getSeleccion();
			item.retroceder();
			MainWindow.getComponents()._diagrams.getTabItem().getSave().setSave(false);
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
    @Override
    public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
	key = e.keyCode;
    	key2 = e.stateMask;
    	Accion();
    }
    
    @Override
    public void keyReleased(org.eclipse.swt.events.KeyEvent arg0) {

    }
}
