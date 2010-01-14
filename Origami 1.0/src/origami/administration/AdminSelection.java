package origami.administration;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class AdminSelection {
    public int  [] selectedFigure = new int [100];
    
    public int diagramSelection=0;
	
    public AdminSelection(){
	for(int i=0; i<100; i++){
	    selectedFigure [i]=-1;
	}
    }
    public int getSelectedFigure(){
	return selectedFigure[diagramSelection];
    }
    public void setSelectedFigure(int figuraSelec){
	selectedFigure[diagramSelection] = figuraSelec;
    }
    public void setDiagramSelection(int selec){
	diagramSelection = selec;
    }
    public int getDiagramSelection(){
	return diagramSelection;
    }
}
