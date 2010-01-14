package origami.graphics.view;

import origami.administration.Figura;
import origami.graphics.widgets.TabFolder;

/**
 * 
 * Esta clase establece la propiedad de eliminar.
 * 
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class SelectionEvent {

    public static TabFolder tab;

    public static int getFigureIndex(Figura fig, TabFolder tab) {
	for (int figureIndex = 0; figureIndex < tab.getTabItem().getLeaf()
		.getSizeDiagrama(); figureIndex++) {
	    if (fig.getBounds() == tab.getTabItem().getLeaf().getFigureIndexOf(
		    figureIndex).getBounds()) {
		return figureIndex;
	    }
	}
	return -1;
    }
}