package origami.administration;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class AdminSelection {
    public int  [] figuraSeleccionada = new int [100];
    
    public int seleccionDiagrama=0;
	
    public AdminSelection(){
	for(int i=0; i<100; i++){
	    figuraSeleccionada [i]=-1;
	}
    }
    public int getFiguraSeleccionada(){
	return figuraSeleccionada[seleccionDiagrama];
    }
    public void setFiguraSeleccionada(int figuraSelec){
	figuraSeleccionada[seleccionDiagrama] = figuraSelec;
    }
    public void setSeleccionDiagrama(int selec){
	seleccionDiagrama = selec;
    }
    public int getSeleccionDigrama(){
	return seleccionDiagrama;
    }
}
