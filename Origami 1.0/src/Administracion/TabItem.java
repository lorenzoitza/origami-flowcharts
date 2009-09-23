package Administracion;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

import Administracion.Funcionalidad.Guardar;
import Grafico.Figuras.Entrada;
import Grafico.Figuras.For;
import Grafico.Figuras.If;
import Grafico.Figuras.Imprimir;
import Grafico.Figuras.Proceso;
import Grafico.Figuras.While;
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
	
	public TabItem(CTabFolder arg0, int arg1) {
		super(arg0, arg1);
	}
	public void setTabFolder(TabFolder tabfolder){
		tab = tabfolder;
		save.setTabFolder(tab);
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
			if(diagrama.elementAt(i) instanceof If){
				If copia = new If(SWT.COLOR_BLUE);
				If actual = (If)diagrama.elementAt(i);
				for(int j=0; j<actual.instruccion.instruccion.size(); j++){
					copia.instruccion.instruccion.add(actual.instruccion.instruccion.elementAt(j));	
				}
				retroseso.elementAt(posicionRetroceso).diagrama.add(copia);
			}
			else if(diagrama.elementAt(i) instanceof For){
				For copia = new For(SWT.COLOR_BLUE);
				For actual = (For)diagrama.elementAt(i);
				for(int j=0; j<actual.instruccion.instruccion.size(); j++){
					copia.instruccion.instruccion.add(actual.instruccion.instruccion.elementAt(j));	
				}
				retroseso.elementAt(posicionRetroceso).diagrama.add(copia);
			}
			else if(diagrama.elementAt(i) instanceof While){
				While copia = new While(SWT.COLOR_BLUE);
				While actual = (While)diagrama.elementAt(i);
				for(int j=0; j<actual.instruccion.instruccion.size(); j++){
					copia.instruccion.instruccion.add(actual.instruccion.instruccion.elementAt(j));	
				}
				retroseso.elementAt(posicionRetroceso).diagrama.add(copia);
			}
			else if(diagrama.elementAt(i) instanceof Proceso){
				Proceso copia = new Proceso(SWT.COLOR_BLUE);
				Proceso actual = (Proceso)diagrama.elementAt(i);
				copia.instruccion.instruccion = actual.instruccion.instruccion;
				retroseso.elementAt(posicionRetroceso).diagrama.add(copia);
			}
			else if(diagrama.elementAt(i) instanceof Entrada){
				Entrada copia = new Entrada(SWT.COLOR_BLUE);
				Entrada actual = (Entrada)diagrama.elementAt(i);
				copia.instruccion.instruccion = actual.instruccion.instruccion;
				retroseso.elementAt(posicionRetroceso).diagrama.add(copia);
			}
			else if(diagrama.elementAt(i) instanceof Imprimir){
				Imprimir copia = new Imprimir(SWT.COLOR_BLUE);
				Imprimir actual = (Imprimir)diagrama.elementAt(i);
				copia.instruccion.instruccion = actual.instruccion.instruccion;
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
		info.setDiagrama(tab.getHoja().getDiagrama());
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
