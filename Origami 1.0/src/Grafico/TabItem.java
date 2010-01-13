package Grafico;

import java.util.Vector;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

import Administracion.AdminDiagrama;
import Administracion.AdminSeleccion;
import Administracion.CustomLeaf;
import Administracion.Figura;
import Administracion.Informacion;
import Administracion.Funcionalidad.Guardar;
import Grafico.Figuras.InputFigure;
import Grafico.Figuras.ForFigure;
import Grafico.Figuras.DecisionFigure;
import Grafico.Figuras.OutputFigure;
import Grafico.Figuras.SentenceFigure;
import Grafico.Figuras.WhileFigure;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class TabItem extends CTabItem{
	private int Id;
	private Vector<AdminDiagrama> retroseso = new Vector<AdminDiagrama>();
	private int []seleccion = new int[5];
	private int posicionRetroceso=-1;
	private Guardar save = new Guardar();
	private TabFolder tab;
	public Informacion info = new Informacion();
	public AdminSeleccion selec;
	
	private CustomLeaf leaf;
	
	public TabItem(CTabFolder arg0, int arg1,AdminSeleccion selec) {
		super(arg0, arg1);
		this.selec = selec;
	}
	public void setTabFolder(TabFolder tabfolder){
		tab = tabfolder;
		save.setTabFolder(tab);
	}
	public void initLeaf(){
		leaf = new CustomLeaf(tab,selec);
	}
	public CustomLeaf getLeaf(){
	    return leaf;
	}
	public void enabledLeaf(){
	    leaf.enabledCustomLeaf();
	}
	public void SetId(int id){
		this.Id = id;
	}
	public int GetId(){
		return this.Id;
	}
	public void agregarRetroceso(Vector<Figura> diagrama,AdminSeleccion selec){
		if(posicionRetroceso==4){
			retroseso.remove(0);
			posicionRetroceso--;
			for(int pos=0; pos<=3; pos++){
				seleccion[pos] = seleccion[pos+1];
			}
		}
		posicionRetroceso++;
		retroseso.add(new AdminDiagrama(selec));
		retroseso.elementAt(posicionRetroceso).diagrama.removeAllElements();
		for(int i=0; i<diagrama.size(); i++){
			if(diagrama.elementAt(i) instanceof DecisionFigure){
				DecisionFigure copia = new DecisionFigure();
				DecisionFigure actual = (DecisionFigure)diagrama.elementAt(i);
				for(int j=0; j<actual.instructionComposed.simpleInstructionList.size(); j++){
					copia.instructionComposed.simpleInstructionList.add(actual.instructionComposed.simpleInstructionList.elementAt(j));	
				}
				retroseso.elementAt(posicionRetroceso).diagrama.add(copia);
			}
			else if(diagrama.elementAt(i) instanceof ForFigure){
				ForFigure copia = new ForFigure();
				ForFigure actual = (ForFigure)diagrama.elementAt(i);
				for(int j=0; j<actual.instructionComposed.simpleInstructionList.size(); j++){
					copia.instructionComposed.simpleInstructionList.add(actual.instructionComposed.simpleInstructionList.elementAt(j));	
				}
				retroseso.elementAt(posicionRetroceso).diagrama.add(copia);
			}
			else if(diagrama.elementAt(i) instanceof WhileFigure){
				WhileFigure copia = new WhileFigure();
				WhileFigure actual = (WhileFigure)diagrama.elementAt(i);
				for(int j=0; j<actual.instructionComposed.simpleInstructionList.size(); j++){
					copia.instructionComposed.simpleInstructionList.add(actual.instructionComposed.simpleInstructionList.elementAt(j));	
				}
				retroseso.elementAt(posicionRetroceso).diagrama.add(copia);
			}
			else if(diagrama.elementAt(i) instanceof SentenceFigure){
				SentenceFigure copia = new SentenceFigure();
				SentenceFigure actual = (SentenceFigure)diagrama.elementAt(i);
				copia.instruccion.simpleInstruction = actual.instruccion.simpleInstruction;
				retroseso.elementAt(posicionRetroceso).diagrama.add(copia);
			}
			else if(diagrama.elementAt(i) instanceof InputFigure){
				InputFigure copia = new InputFigure();
				InputFigure actual = (InputFigure)diagrama.elementAt(i);
				copia.instruction.simpleInstruction = actual.instruction.simpleInstruction;
				retroseso.elementAt(posicionRetroceso).diagrama.add(copia);
			}
			else if(diagrama.elementAt(i) instanceof OutputFigure){
				OutputFigure copia = new OutputFigure();
				OutputFigure actual = (OutputFigure)diagrama.elementAt(i);
				copia.instruction.simpleInstruction = actual.instruction.simpleInstruction;
				retroseso.elementAt(posicionRetroceso).diagrama.add(copia);
			}
			else{
				retroseso.elementAt(posicionRetroceso).diagrama.add(diagrama.elementAt(i));
			}
		}
		seleccion[posicionRetroceso] = selec.getFiguraSeleccionada();
	}
	public void resetRetroceso(){
		retroseso.removeAllElements();
		posicionRetroceso=-1;
		for(int pos=0; pos<4; pos++){
			seleccion[pos] = -1;
		}
	}
	public void retroceder(){
		if(posicionRetroceso == 0){
			posicionRetroceso++;
		}
		posicionRetroceso--;
		tab.retroceder(retroseso, posicionRetroceso,seleccion[posicionRetroceso]);
	}
	public void addInfo(){
		info.setInformacion("Inicio un nuevo diagrama");
		info.setDiagrama(tab.getTabItem().getLeaf().getDiagrama());
	}
	public Guardar getSave() {
		return save;
	}
	public void setSave(Guardar save) {
		this.save = save;
	}
	public Informacion getInfo() {
		return info;
	}
	public void setInfo(Informacion info) {
		this.info = info;
	}
}
