package origami.administration.actions;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

import origami.administration.AdminDiagram;
import origami.administration.ApplicationState;
import origami.administration.FigureStructure;
import origami.graphics.*;
import origami.graphics.figures.DecisionFigure;
import origami.graphics.figures.EllipseFigure;
import origami.graphics.figures.ForFigure;
import origami.graphics.figures.InputFigure;
import origami.graphics.figures.OutputFigure;
import origami.graphics.figures.SentenceFigure;
import origami.graphics.figures.WhileFigure;
import origami.graphics.widgets.TabFolder;
import origami.images.ImageLoader;


public class ChangeCursorVerification{
    private Point start;
    
    public boolean flag = false;
    
    private FigureStructure figureStructure;
    
    private IFigure iFigure;
    
    public final Cursor[] cursor = ApplicationState.cursor;
	
    public TabFolder tabFolder;
    
    public ChangeCursorVerification(IFigure figure,TabFolder tabfolder){
	iFigure = figure;
	tabFolder = tabfolder;
    }
    
    public void mouseMovedCoordinate(MouseEvent event) {
	figureStructure = ApplicationState.mainFigure;
	int a;
	start = event.getLocation();
	for(int z=0;z<tabFolder.getTabItem().getLeaf().getSizeDiagrama()-1;z++){
	    if(verify(z,z+1)){
		break;
	    }
	    if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(z+1) instanceof DecisionFigure){
		a = verifyRight(z+1);
		if(flag){
		    break;
		}
		z = verifyRight(a);
		if(flag){
		    break;
		}
	    }
	    if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(z+1) instanceof ForFigure || tabFolder.getTabItem().getLeaf().getFigureIndexOf(z+1) instanceof WhileFigure){
		a = verifyDown(z+1);
		z=a+4;
		if(flag){
		    break;
		}
	    }
	}
	if(!flag){
	    remarkFigure(false);
	}
	tooltip(tabFolder.getTabItem().getLeaf().getAdminDiagrama());
    }
    
    /**
     * Este metodo hace un recorrido por la derecha
     * de un if del diagrama para identificar si alguna 
     * figura fue añadida al diagrama, si no regresa la pocision
     * del final del lado derecho.
     * @param z
     * @param fig
     * @return int
     */
    public int verifyRight(int z){
	int x = z+1;//elipse
	int a;
	z = z+2;//sig figura
	
	while(tabFolder.getTabItem().getLeaf().getFigureIndexOf(z)!=null){
	    if(verify(x,z)){
		break;
	    }
	    if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(z) instanceof DecisionFigure){
		a = verifyRight(z);
		if(flag){
		    break;
		}
		z = verifyRight(a);
		if(flag){
		    break;
		}
	    }
	    else if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(z) instanceof EllipseFigure){
		break;
	    }
	    else if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(z) instanceof ForFigure || tabFolder.getTabItem().getLeaf().getFigureIndexOf(z) instanceof WhileFigure){
		a = verifyDown(z);
		z=a+5;
		if(flag){
		    break;
		}
	    }
	    x=z;
	    z++;
	}
	return z;
    }
    /**
     * Este metodo hace un recorrido por la abajo
     * de un For o While del diagrama para identificar si alguna 
     * figura fue añadida al diagrama, si no regresa la pocision
     * del final de dicha figura.
     * 
     * @param z
     * @param fig
     * @return int
     */
    public int verifyDown(int z){
	int x = z;//for
	int a;
	z = z+1;//sig figura
	while(tabFolder.getTabItem().getLeaf().getFigureIndexOf(z)!=null){
	    if(verify(x,z)){
		break;
	    }
	    if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(z) instanceof DecisionFigure){
		a = verifyRight(z);
		if(flag){
		    break;
		}
		z = verifyRight(a)+1;
		if(flag){
		    break;
		}
	    }
	    else if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(z) instanceof EllipseFigure){
		break;
	    }
	    else if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(z) instanceof ForFigure || tabFolder.getTabItem().getLeaf().getFigureIndexOf(z) instanceof WhileFigure){
		a = verifyDown(z);
		z=a+4;
		x=z;
		z++;
		if(flag){
		    break;
		}
	    }
	    x=z;
	    z++;
	}
	return z;
    }
    /**
     * Este metodo revisa si en los espacios 
     * disponibles en el diagrama fue agregado 
     * una nueva figura, si no devuelve falso.
     * @param i
     * @param j
     * @param fig
     * @return boolean
     */
    public boolean verify(int i,int j){ 
	flag = false;
	if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(i) instanceof EllipseFigure && tabFolder.getTabItem().getLeaf().getFigureIndexOf(j) instanceof EllipseFigure){
	    if(start.x >= tabFolder.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().x-15 && start.x <= tabFolder.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().x+15 && 
		    start.y >= tabFolder.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().y && start.y <= tabFolder.getTabItem().getLeaf().getFigureIndexOf(j).getBounds().y){
		remarkFigure(true);
		flag = true;
		return true;
	    }
	}
	else if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().x + tabFolder.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().width-1>= start.x && start.x>= tabFolder.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().x+1 && 
		tabFolder.getTabItem().getLeaf().getFigureIndexOf(j).getBounds().y-1 >=start.y &&start.y>=tabFolder.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().y+tabFolder.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().height+1){
	    remarkFigure(true);
	    flag = true;
	    return true;
	}
	else if(tabFolder.getTabItem().getLeaf().getFigureIndexOf(i) instanceof EllipseFigure ){ 
	    if(start.x>=tabFolder.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().x-75 && start.x<=tabFolder.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().x+75 
		    && start.y>=tabFolder.getTabItem().getLeaf().getFigureIndexOf(i).getBounds().y && start.y<=tabFolder.getTabItem().getLeaf().getFigureIndexOf(j).getBounds().y){
		remarkFigure(true);
		flag = true;
		return true;
	    }
	}
	return flag;
    }
    public void remarkFigure(boolean isRemark){
	if(isRemark){
	    if(figureStructure instanceof DecisionFigure || figureStructure instanceof ForFigure || figureStructure instanceof WhileFigure
		    || figureStructure instanceof OutputFigure || figureStructure instanceof InputFigure || figureStructure instanceof SentenceFigure){
		cursor[0] = new Cursor(MainWindow.display, ImageLoader.getImage("cursor.PNG").getImageData(), 0, 0);
		iFigure.setCursor(cursor[0]);
	    }
	}
	else{
	    String name = "";
	    if(figureStructure instanceof DecisionFigure){
		name =  "cursorIf.png";
	    }
	    else if(figureStructure instanceof ForFigure){
		name = "cursorFor.png";
	    }
	    else if(figureStructure instanceof WhileFigure){
		name = "cursorWhile.png";
	    }
	    else if(figureStructure instanceof OutputFigure){
		name = "cursorSalida.png";
	    }
	    else if(figureStructure instanceof InputFigure){
		name = "cursorEntrada.png";
	    }
	    else if(figureStructure instanceof SentenceFigure){
		name = "cursorProceso.png";
	    }
	    if(name != ""){
		ImageData image = ImageLoader.getImage(name).getImageData();
		image.transparentPixel = image.palette.getPixel(new RGB(255, 255, 255));
		cursor[0] = new Cursor(MainWindow.display,image,60,35);
		iFigure.setCursor(cursor[0]);
	    }
	}
    }
    public void tooltip(AdminDiagram diagrama){
	String dato = "null", defaul= "Doble Clic Para Agregar Instrucciones";
	String subStr;
	int i;		
	for(int k=0; k<diagrama.diagram.size(); k++){
	    if(diagrama.diagram.elementAt(k) instanceof DecisionFigure){
		DecisionFigure a = (DecisionFigure)diagrama.diagram.elementAt(k);
		dato = a.instructionComposed.getFirstInstructionSimple();
		i = dato.length();
		subStr = dato.substring(0, i-1);
		if(dato.compareToIgnoreCase("null")!=0 && a.instructionComposed.getListSize() > 1){
		    subStr=subStr.substring(2,subStr.length());
		    subStr="Si"+subStr;
		    diagrama.diagram.elementAt(k).setToolTip(new Label(subStr));
		}
		else{
		    diagrama.diagram.elementAt(k).setToolTip(new Label(defaul));
		}
	    }
	    else if(diagrama.diagram.elementAt(k) instanceof ForFigure){
		ForFigure a = (ForFigure)diagrama.diagram.elementAt(k);
		dato = a.instructionComposed.getFirstInstructionSimple();
		i = dato.length();
		subStr = dato.substring(0, i-1);
		if(dato.compareToIgnoreCase("null")!=0 && a.instructionComposed.getListSize() > 1){
		    subStr=subStr.substring(3,subStr.length());
		    subStr="Para"+subStr;
		    diagrama.diagram.elementAt(k).setToolTip(new Label(subStr));
		}
		else{
		    diagrama.diagram.elementAt(k).setToolTip(new Label(defaul));
		}
	    }
	    else if(diagrama.diagram.elementAt(k) instanceof WhileFigure){
		WhileFigure a = (WhileFigure)diagrama.diagram.elementAt(k);
		dato = a.instructionComposed.getFirstInstructionSimple();
		i = dato.length();
		subStr = dato.substring(0, i-1);
		if(dato.compareToIgnoreCase("null")!=0 && a.instructionComposed.getListSize() > 1){
		    subStr=subStr.substring(5,subStr.length());
		    subStr="Mientras"+subStr;
		    diagrama.diagram.elementAt(k).setToolTip(new Label(subStr));
		}
		else{
		    diagrama.diagram.elementAt(k).setToolTip(new Label(defaul));
		}
	    }
	    else if(diagrama.diagram.elementAt(k) instanceof SentenceFigure){
		SentenceFigure a = (SentenceFigure)diagrama.diagram.elementAt(k);
		dato = a.instruction.simpleInstruction;
		i = dato.length();
		subStr = dato.substring(0, i-1);
		if(dato != "null" && dato.compareTo("null")!=0){
		    diagrama.diagram.elementAt(k).setToolTip(new Label(subStr));
		}
		else{
		    diagrama.diagram.elementAt(k).setToolTip(new Label(defaul));
		}
	    }
	    else if(diagrama.diagram.elementAt(k) instanceof OutputFigure){
		OutputFigure a = (OutputFigure)diagrama.diagram.elementAt(k);
		dato = "";
		String[] variables2 = new String[50];
		String[] variables = new String[50];
		variables =  a.instruction.simpleInstruction.split(";");
		int cont =0;
		for(int x=0;x<variables.length;x++){
		    variables[x] = variables[x].replace("\\", "");
		    variables[x] = variables[x].replaceFirst("p", "");
		    if(variables[x].compareTo("") != 0){
			variables2[cont] = variables[x];
			cont++;
		    }
		}
		int cont2=0;
		for(int x=0;x<cont;x++){
		    if(variables2[x].compareTo("") != 0){
			if(cont2==0){
			    dato = variables2[x];
			}
			else{
			    dato = dato + "," + variables2[x];
			}
			cont2++;
		    }
		}
		if(dato.length()>0 && !(dato.startsWith("null")) && dato.compareTo("null")!=0){
		    diagrama.diagram.elementAt(k).setToolTip(new Label(dato));
		}
		else{
		    diagrama.diagram.elementAt(k).setToolTip(new Label(defaul));
		}
	    }
	    else if(diagrama.diagram.elementAt(k) instanceof InputFigure){
		InputFigure a = (InputFigure)diagrama.diagram.elementAt(k);
		dato = a.instruction.simpleInstruction;
		dato = dato.replaceAll(";", ",");
		if(dato.length()>0){
		    dato = dato.substring(0, dato.length()-1);
		}
		if(!(dato.startsWith("nul")) && dato.compareTo("")!=0){
		    diagrama.diagram.elementAt(k).setToolTip(new Label(dato));	
		}
		else{
		    diagrama.diagram.elementAt(k).setToolTip(new Label(defaul));
		}
	    }
	}
    }

}