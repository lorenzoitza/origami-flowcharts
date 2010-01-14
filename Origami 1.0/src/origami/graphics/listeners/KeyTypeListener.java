package origami.graphics.listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Cursor;

import origami.administration.ApplicationState;
import origami.administration.actions.AddFigureLogic;
import origami.administration.actions.ContextualMenuActions;
import origami.administration.funtionality.CodeCompiler;
import origami.graphics.BaseDeDiagrama;
import origami.graphics.MainWindow;
import origami.graphics.Help.AboutWindow;
import origami.graphics.Help.HelpWindow;
import origami.graphics.figures.CircleFigure;
import origami.graphics.view.OpenFileView;
import origami.graphics.view.OpenType;
import origami.graphics.view.SaveFileView;
import origami.graphics.view.SaveType;
import origami.graphics.widgets.CustomMenu;
import origami.graphics.widgets.TabItem;
import origami.images.ImageLoader;

public class KeyTypeListener implements KeyListener{
    private int key,key2=0;
    
    public void setKey(org.eclipse.swt.events.KeyEvent e){
    	key = e.keyCode;
    	key2 = e.stateMask;
    }
	public void Accion(){
	switch(key){
	case 49:
		if(!MainWindow.getComponents().getByStepComponents().isStepByStep()){
		    ApplicationState.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorEntrada.png").getImageData(), 0, 0);
		    new AddFigureLogic().addInput();
		    new AddFigureLogic().disableCursor();
		}
		break;
	case 50:
		if(!MainWindow.getComponents().getByStepComponents().isStepByStep()){
		    ApplicationState.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorProceso.png").getImageData(), 0, 0);
		    new AddFigureLogic().addSentence();
		    new AddFigureLogic().disableCursor();
		}
		break;
	case 51:
		if(!MainWindow.getComponents().getByStepComponents().isStepByStep()){
		    ApplicationState.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorIf.png").getImageData(), 0, 0);
		    new AddFigureLogic().addDecision();
		    new AddFigureLogic().disableCursor();
		}
		break;
	case 52:
		if(!MainWindow.getComponents().getByStepComponents().isStepByStep()){
		    ApplicationState.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorWhile.png").getImageData(), 0, 0);
		    new AddFigureLogic().addWhile();
		    new AddFigureLogic().disableCursor();
		}
		break;
	case 53:
		if(!MainWindow.getComponents().getByStepComponents().isStepByStep()){
		    ApplicationState.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorFor.png").getImageData(), 0, 0);
		    new AddFigureLogic().addFor();
		    new AddFigureLogic().disableCursor();
		}
		break;
	case 54:
		if(!MainWindow.getComponents().getByStepComponents().isStepByStep()){
		    ApplicationState.cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursorSalida.png").getImageData(), 0, 0);
		    new AddFigureLogic().addOutput();
		    new AddFigureLogic().disableCursor();
		}
		break;
	case 27:
		if(!MainWindow.getComponents().getByStepComponents().isStepByStep()){
			 Cursor[] cursor = new Cursor[1];
			   origami.administration.ApplicationState.mainFigure = null;
			   Cursor oldCursor = cursor[0];
		       cursor[0] = new Cursor(null, SWT.CURSOR_ARROW);
		       MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDibujarDiagrama().setCursor(cursor[0]);
		       if(oldCursor != null){
		    	   oldCursor.dispose();
		       }
		       MainWindow.getComponents().tabFolder.getTabItem().getLeaf().addFigure();
		       MainWindow.getComponents().tabFolder.getTabItem().getLeaf().guardarRetroceso();
		       //MainWindow.getComponents().tabFolder.getHoja().guardarRetroceso();
		}
       break;
	case 127:
		if(ApplicationState._selectionAdministrator.getFiguraSeleccionada()!=-1){
			if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getFiguraSeleccionada()) instanceof CircleFigure){
			}
			else{
			    new ContextualMenuActions().Eliminar(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama().elementAt(ApplicationState._selectionAdministrator.getFiguraSeleccionada()));
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
		if(!MainWindow.getComponents().getByStepComponents().isStepByStep()){
			for(int y=MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getSizeDiagrama()-1;y>0;y--){
				MainWindow.getComponents().tabFolder.getTabItem().getLeaf().removeFigureIndexOf(y);
			}
			CircleFigure fin = new CircleFigure();
			ApplicationState._selectionAdministrator.setFiguraSeleccionada(0);
			MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getDiagrama().add(fin);
			fin.setMessage("  Fin");
			BaseDeDiagrama.getInstance().resetScrollBar();
			//MainWindow.getComponents().tabFolder.getHoja().resetScrollBar();
			MainWindow.getComponents().tabFolder.getTabItem().getLeaf().addFigure();
			//MainWindow.getComponents().tabFolder.getHoja().guardarRetroceso();
			MainWindow.getComponents().tabFolder.getTabItem().getLeaf().guardarRetroceso();
			MainWindow.getComponents().tabFolder.getTabItem().getSave().setSave(false);
		}
		break;
	case 16777229:
//		Instruccion codigo7 = new Instruccion();
//		codigo7.main(MainWindow.getComponents().tabFolder.getHoja().getDiagrama());
//		codigo7.createWindow(MainWindow.display);
		break;
	case 16777230:
		if(!MainWindow.getComponents().getByStepComponents().isStepByStep()){
			CodeCompiler codigo = new CodeCompiler(MainWindow.getComponents().tabFolder);
			if(MainWindow.getComponents().getByStepComponents().getEnEjecucion(MainWindow.getComponents())){
				MainWindow.getComponents().getByStepComponents().stopExecution(MainWindow.getComponents());
			}
			codigo.main(false,true);
			if(codigo.isError){
				int aux = MainWindow.getComponents().customConsole.getTextField().getText().length();
				if(aux>=0){
					MainWindow.getComponents().customConsole.getTextField().setText("");
				}
				MainWindow.getComponents().customConsole.getTextField().setText(codigo.errorTipe);
				MainWindow.getComponents().tabFolder.getTabItem().getInformation().addInformation("/Ec - Error en la compilacion:");
				MainWindow.getComponents().tabFolder.getTabItem().getInformation().addInformation(codigo.errorTipe);
				codigo.deleteMainFiles();
			}
			else{
				MainWindow.getComponents().getByStepComponents().execute(MainWindow.getComponents(), true,codigo);
				MainWindow.getComponents().tabFolder.getTabItem().getInformation().addInformation("/C - Se Compilo el diagrama de manera correcta");
			}
			if(!CustomMenu.getConsoleMenuItem().getSelection()){
			    CustomMenu.getConsoleMenuItem().setSelection(true);
				MainWindow.getComponents().restoreConsole(true);
			}
		}
		break;
	}
	if(key+key2 == 262241){
	    if(MainWindow.getComponents().getByStepComponents().getExecution() != null && MainWindow.getComponents().getByStepComponents().getEnEjecucion(MainWindow.getComponents())
		&& MainWindow.getComponents().tabFolder.getSelectedTabItemId() == MainWindow.getComponents().getByStepComponents().getExecution().tabItemSelected.GetId()){
		MainWindow.getComponents().getByStepComponents().stopExecution(MainWindow.getComponents());
	    }
	    else if(MainWindow.getComponents().getByStepComponents().getStepByStep() != null && MainWindow.getComponents().getByStepComponents().getEnEjecucion(MainWindow.getComponents())
		    && MainWindow.getComponents().tabFolder.getSelectedTabItemId() == MainWindow.getComponents().getByStepComponents().getStepByStep().a.GetId()){
			MainWindow.getComponents().getByStepComponents().stopExecution(MainWindow.getComponents());
	    }
	    OpenFileView open = new OpenFileView();
	    open.setOpenType(OpenType.OPEN);
	    open.createWindow();
	}
	else if(key+key2 == 262247){
	    SaveFileView save = new SaveFileView();
	    if(MainWindow.getComponents().tabFolder.getTabItem().getSave().getDir()=="null"){
		save.setSaveType(SaveType.SAVE);
		save.createWindow();
	    }
	    else{
		save.saveAction();
	    }
	}
	else if(key+key2 == 262264){
		if(ApplicationState._selectionAdministrator.getFiguraSeleccionada()!=-1){
			if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(ApplicationState._selectionAdministrator.getFiguraSeleccionada()) instanceof CircleFigure || ApplicationState._selectionAdministrator.getFiguraSeleccionada() == -1){
			}
			else{
			    new ContextualMenuActions().Cortar(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(ApplicationState._selectionAdministrator.getFiguraSeleccionada()));	
				MainWindow.getComponents().customToolBar.disableToolBar();
			}
		}
	}
	else if(key+key2 == 262243){
		if(ApplicationState._selectionAdministrator.getFiguraSeleccionada()!=-1){
			if(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(ApplicationState._selectionAdministrator.getFiguraSeleccionada()) instanceof CircleFigure || ApplicationState._selectionAdministrator.getFiguraSeleccionada() == -1){
			}
			else{
			    new ContextualMenuActions().Copiar(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(ApplicationState._selectionAdministrator.getFiguraSeleccionada()));
				MainWindow.getComponents().customToolBar.disableToolBar();
			}
		}
	}
	else if(key+key2 == 262262){
		if(ApplicationState._selectionAdministrator.getFiguraSeleccionada()!=-1){
			if(ApplicationState._diagramAdministrator.diagram.size()>0){
			    new ContextualMenuActions().Pegar(MainWindow.getComponents().tabFolder.getTabItem().getLeaf().getFigureIndexOf(ApplicationState._selectionAdministrator.getFiguraSeleccionada()));
				MainWindow.getComponents().customToolBar.disableToolBar();
			}
		}
	}
	else if(key+key2 == 262254){
		MainWindow.getComponents().tabFolder.addTabItem();
		MainWindow.getComponents().setEnabledSaveItems(true);
		MainWindow.getComponents().disableAll(true);
	}
	else if(key+key2 == 262266){
		if(!MainWindow.getComponents().getByStepComponents().isStepByStep()){
			TabItem item = (TabItem)MainWindow.getComponents().tabFolder.getSeleccion();
			item.undo();
			MainWindow.getComponents().tabFolder.getTabItem().getSave().setSave(false);
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
