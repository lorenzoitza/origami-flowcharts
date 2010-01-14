package origami.graphics.widgets;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;

import origami.graphics.MainWindow;
import origami.graphics.listeners.AddDecisionFigureListener;
import origami.graphics.listeners.AddForFigureListener;
import origami.graphics.listeners.AddInputFigureListener;
import origami.graphics.listeners.AddOutputFigureListener;
import origami.graphics.listeners.AddSentenceFigureListener;
import origami.graphics.listeners.AddWhileFigureListener;
import origami.graphics.listeners.KeyTypeListener;
import origami.images.ImageLoader;

public class CustomFiguresToolBar {

    private Display display;

    private ToolBar figureToolBar;

    private ArrayList<Button> toolBarButtons;

    public CustomFiguresToolBar(GridData toolBarLayoutData, Display display) {
	this.display = display;

	toolBarButtons = new ArrayList<Button>();

	figureToolBar =
		new ToolBar(MainWindow.shell, SWT.LEFT | SWT.FLAT | SWT.BORDER);
	figureToolBar.setCursor(new Cursor(null, SWT.CURSOR_ARROW));
	figureToolBar.setLayoutData(toolBarLayoutData);
	figureToolBar.setLayout(getToolBarLayout());

	initToolBarButtons(figureToolBar);
    }

    private GridLayout getToolBarLayout() {
	GridLayout gridLayout = new GridLayout();

	int numColumns = 1;
	gridLayout.numColumns = numColumns;

	int verticalSpacing = 3;
	gridLayout.verticalSpacing = verticalSpacing;

	int horizontalSpacing = 0;
	gridLayout.horizontalSpacing = horizontalSpacing;

	int marginWidth = 0;
	gridLayout.marginWidth = marginWidth;

	int marginHeight = 0;
	gridLayout.marginHeight = marginHeight;

	return gridLayout;
    }

    private void initToolBarButtons(ToolBar toolBar) {
	Button toolBarButton;

	toolBarButton = new Button(toolBar, SWT.FLAT);
	toolBarButton.setImage(ImageLoader.getImage("Entrada.png"));
	toolBarButton.pack();
	toolBarButton.setToolTipText("Entrada");
	toolBarButton.addSelectionListener(new AddInputFigureListener(display));
	toolBarButton.addKeyListener(new KeyTypeListener());
	toolBarButtons.add(toolBarButton);

	toolBarButton = new Button(toolBar, SWT.FLAT);
	toolBarButton.setImage(ImageLoader.getImage("Proceso.png"));
	toolBarButton.pack();
	toolBarButton.setToolTipText("Expresion");
	toolBarButton
		.addSelectionListener(new AddSentenceFigureListener(display));
	toolBarButton.addKeyListener(new KeyTypeListener());
	toolBarButtons.add(toolBarButton);

	toolBarButton = new Button(toolBar, SWT.FLAT);
	toolBarButton.setImage(ImageLoader.getImage("If.png"));
	toolBarButton.pack();
	toolBarButton.setToolTipText("Decision");
	toolBarButton
		.addSelectionListener(new AddDecisionFigureListener(display));
	toolBarButton.addKeyListener(new KeyTypeListener());
	toolBarButtons.add(toolBarButton);

	toolBarButton = new Button(toolBar, SWT.FLAT);
	toolBarButton.setImage(ImageLoader.getImage("While.png"));
	toolBarButton.pack();
	toolBarButton.setToolTipText("Ciclo Mientras");
	toolBarButton.addSelectionListener(new AddWhileFigureListener(display));
	toolBarButton.addKeyListener(new KeyTypeListener());
	toolBarButtons.add(toolBarButton);

	toolBarButton = new Button(toolBar, SWT.FLAT);
	toolBarButton.setImage(ImageLoader.getImage("For.png"));
	toolBarButton.pack();
	toolBarButton.setToolTipText("Ciclo Para");
	toolBarButton.addSelectionListener(new AddForFigureListener(display));
	toolBarButton.addKeyListener(new KeyTypeListener());
	toolBarButtons.add(toolBarButton);

	toolBarButton = new Button(toolBar, SWT.FLAT);
	toolBarButton.setImage(ImageLoader.getImage("Salida.png"));
	toolBarButton.pack();
	toolBarButton.setToolTipText("Salida");
	toolBarButton.addSelectionListener(new AddOutputFigureListener(display));
	toolBarButton.addKeyListener(new KeyTypeListener());
	toolBarButtons.add(toolBarButton);

	createLayoutExtraSpace(toolBar);

	int xCoord = 40;
	int yCoord = 600;
	int width = 50;
	int height = 30;
	Button botonConsola = new Button(toolBar, SWT.FLAT);
	botonConsola.setBounds(xCoord, yCoord, width, height);
	botonConsola.setImage(ImageLoader.getImage("consola.ico"));
	botonConsola.pack();
	botonConsola.setToolTipText("Consola ");
	botonConsola.addSelectionListener(consoleListener());
    }

    private SelectionListener consoleListener() {
	return new SelectionAdapter() {

	    public void widgetSelected(SelectionEvent event) {
		if (CustomMenu.getConsoleMenuItem().getSelection()) {
		    CustomMenu.getConsoleMenuItem().setSelection(false);
		    MainWindow.getComponents().moverConsola(false);
		} else {
		    MainWindow.getComponents().moverConsola(true);
		    CustomMenu.getConsoleMenuItem().setSelection(true);
		}
	    }
	};
    }

    private void createLayoutExtraSpace(ToolBar toolBar) {
	GridData labelData = new GridData();
	labelData.grabExcessVerticalSpace = true;

	Label label = new Label(toolBar, SWT.NONE);
	label.setVisible(false);
	label.setLayoutData(labelData);
    }

    public void setEnabledAllButtons(boolean isEnabled) {
	int buttonIndex;
	for (buttonIndex = 0; buttonIndex < toolBarButtons.size(); buttonIndex++) {
	    toolBarButtons.get(buttonIndex).setEnabled(isEnabled);
	}
    }

    public ToolBar getBarraFiguras() {
	return figureToolBar;
    }

    public ArrayList<Button> getToolBarButtons() {
	return toolBarButtons;
    }

}
