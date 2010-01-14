package origami.graphics.widgets;

import java.util.Vector;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

import origami.administration.AdminDiagrama;
import origami.administration.AdminSeleccion;
import origami.administration.CustomLeaf;
import origami.administration.Figura;
import origami.administration.Informacion;
import origami.administration.funtionality.SaveDiagramController;
import origami.graphics.figures.DecisionFigure;
import origami.graphics.figures.ForFigure;
import origami.graphics.figures.InputFigure;
import origami.graphics.figures.OutputFigure;
import origami.graphics.figures.SentenceFigure;
import origami.graphics.figures.WhileFigure;

/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class TabItem extends CTabItem {

    private int Id;

    private Vector<AdminDiagrama> retroseso = new Vector<AdminDiagrama>();

    private int[] seleccion = new int[5];

    private int posicionRetroceso = -1;

    private SaveDiagramController save = new SaveDiagramController();

    private TabFolder tab;

    private Informacion information = new Informacion();

    private AdminSeleccion adminSelection;

    private CustomLeaf leaf;

    public TabItem(CTabFolder arg0, int arg1, AdminSeleccion selec) {
	super(arg0, arg1);
	this.adminSelection = selec;
    }

    public void setTabFolder(TabFolder tabfolder) {
	tab = tabfolder;
	save.setTabFolder(tab);
    }

    public void initLeaf() {
	leaf = new CustomLeaf(tab, adminSelection);
    }

    public CustomLeaf getLeaf() {
	return leaf;
    }

    public void enabledLeaf() {
	leaf.enabledCustomLeaf();
    }

    public void SetId(int id) {
	this.Id = id;
    }

    public int GetId() {
	return this.Id;
    }

    public void addUndo(Vector<Figura> diagrama, AdminSeleccion selec) {
	if (posicionRetroceso == 4) {
	    retroseso.remove(0);
	    posicionRetroceso--;
	    for (int position = 0; position <= 3; position++) {
		seleccion[position] = seleccion[position + 1];
	    }
	}
	posicionRetroceso++;
	retroseso.add(new AdminDiagrama(selec));
	retroseso.elementAt(posicionRetroceso).diagram.removeAllElements();
	for (int index = 0; index < diagrama.size(); index++) {
	    if (diagrama.elementAt(index) instanceof DecisionFigure) {
		DecisionFigure copia = new DecisionFigure();
		DecisionFigure actual = (DecisionFigure) diagrama.elementAt(index);
		for (int element = 0; element < actual.instructionComposed.simpleInstructionList
			.size(); element++) {
		    copia.instructionComposed.simpleInstructionList
			    .add(actual.instructionComposed.simpleInstructionList
				    .elementAt(element));
		}
		retroseso.elementAt(posicionRetroceso).diagram.add(copia);
	    } else if (diagrama.elementAt(index) instanceof ForFigure) {
		ForFigure copia = new ForFigure();
		ForFigure actual = (ForFigure) diagrama.elementAt(index);
		for (int element = 0; element < actual.instructionComposed.simpleInstructionList
			.size(); element++) {
		    copia.instructionComposed.simpleInstructionList
			    .add(actual.instructionComposed.simpleInstructionList
				    .elementAt(element));
		}
		retroseso.elementAt(posicionRetroceso).diagram.add(copia);
	    } else if (diagrama.elementAt(index) instanceof WhileFigure) {
		WhileFigure copia = new WhileFigure();
		WhileFigure actual = (WhileFigure) diagrama.elementAt(index);
		for (int element = 0; element < actual.instructionComposed.simpleInstructionList
			.size(); element++) {
		    copia.instructionComposed.simpleInstructionList
			    .add(actual.instructionComposed.simpleInstructionList
				    .elementAt(element));
		}
		retroseso.elementAt(posicionRetroceso).diagram.add(copia);
	    } else if (diagrama.elementAt(index) instanceof SentenceFigure) {
		SentenceFigure copia = new SentenceFigure();
		SentenceFigure actual = (SentenceFigure) diagrama.elementAt(index);
		copia.instruction.simpleInstruction =
			actual.instruction.simpleInstruction;
		retroseso.elementAt(posicionRetroceso).diagram.add(copia);
	    } else if (diagrama.elementAt(index) instanceof InputFigure) {
		InputFigure copia = new InputFigure();
		InputFigure actual = (InputFigure) diagrama.elementAt(index);
		copia.instruction.simpleInstruction =
			actual.instruction.simpleInstruction;
		retroseso.elementAt(posicionRetroceso).diagram.add(copia);
	    } else if (diagrama.elementAt(index) instanceof OutputFigure) {
		OutputFigure copia = new OutputFigure();
		OutputFigure actual = (OutputFigure) diagrama.elementAt(index);
		copia.instruction.simpleInstruction =
			actual.instruction.simpleInstruction;
		retroseso.elementAt(posicionRetroceso).diagram.add(copia);
	    } else {
		retroseso.elementAt(posicionRetroceso).diagram.add(diagrama
			.elementAt(index));
	    }
	}
	seleccion[posicionRetroceso] = selec.getFiguraSeleccionada();
    }

    public void resetUndo() {
	retroseso.removeAllElements();
	posicionRetroceso = -1;
	for (int index = 0; index < 4; index++) {
	    seleccion[index] = -1;
	}
    }

    public void undo() {
	if (posicionRetroceso == 0) {
	    posicionRetroceso++;
	}
	posicionRetroceso--;
	tab.applyUndo(retroseso, posicionRetroceso,
		seleccion[posicionRetroceso]);
    }

    public void addInformation() {
	information.setInformacion("Inicio un nuevo diagrama");
	information.setDiagrama(tab.getTabItem().getLeaf().getDiagrama());
    }

    public SaveDiagramController getSave() {
	return save;
    }

    public void setSave(SaveDiagramController save) {
	this.save = save;
    }

    public Informacion getInformation() {
	return information;
    }

    public void setInformation(Informacion info) {
	this.information = info;
    }
}
