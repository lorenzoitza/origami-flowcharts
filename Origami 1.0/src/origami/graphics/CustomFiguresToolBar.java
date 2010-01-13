package origami.graphics;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;

import origami.images.ImageLoader;
import origami.ui.listeners.AddDecisionFigureAction;
import origami.ui.listeners.AddForFigureAction;
import origami.ui.listeners.AddInputFigureAction;
import origami.ui.listeners.AddOutputFigureAction;
import origami.ui.listeners.AddSentenceFigureAction;
import origami.ui.listeners.AddWhileFigureAction;
import origami.ui.listeners.KeyEvent;


public class CustomFiguresToolBar {
    private Display display;
    
    private ToolBar barraFiguras;
    
    private ArrayList<Button> toolBarButtons;
    
    public CustomFiguresToolBar(GridData toolBarLayoutData, Display display) {
	this.display = display;

	toolBarButtons = new ArrayList<Button>();
	
	barraFiguras = new ToolBar(MainWindow.shell, SWT.LEFT | SWT.FLAT
		| SWT.BORDER);
	barraFiguras.setCursor(new Cursor(null, SWT.CURSOR_ARROW));
	barraFiguras.setLayoutData(toolBarLayoutData);
	barraFiguras.setLayout(getToolBarLayout());
	
	getToolBarButtons(barraFiguras);
    }
    
    private GridLayout getToolBarLayout(){
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
    
    private void getToolBarButtons(ToolBar toolBar) {
	Button toolBarButton;
	
	toolBarButton = new Button(toolBar, SWT.FLAT);
	toolBarButton.setImage(ImageLoader.getImage("Entrada.png"));
	toolBarButton.pack();
	toolBarButton.setToolTipText("Entrada");
	toolBarButton.addSelectionListener(new AddInputFigureAction(display));
	toolBarButton.addKeyListener(new KeyEvent());
	toolBarButtons.add(toolBarButton);
	
	toolBarButton = new Button(toolBar, SWT.FLAT);
	toolBarButton.setImage(ImageLoader.getImage("Proceso.png"));
	toolBarButton.pack();
	toolBarButton.setToolTipText("Expresin");
	toolBarButton.addSelectionListener(new AddSentenceFigureAction(display));
	toolBarButton.addKeyListener(new KeyEvent());
	toolBarButtons.add(toolBarButton);
	
	toolBarButton = new Button(toolBar, SWT.FLAT);
	toolBarButton.setImage(ImageLoader.getImage("If.png"));
	toolBarButton.pack();
	toolBarButton.setToolTipText("Decisin");
	toolBarButton.addSelectionListener(new AddDecisionFigureAction(display));
	toolBarButton.addKeyListener(new KeyEvent());
	toolBarButtons.add(toolBarButton);
	
	toolBarButton = new Button(toolBar, SWT.FLAT);
	toolBarButton.setImage(ImageLoader.getImage("While.png"));
	toolBarButton.pack();
	toolBarButton.setToolTipText("Ciclo Mientras");
	toolBarButton.addSelectionListener(new AddWhileFigureAction(display));
	toolBarButton.addKeyListener(new KeyEvent());
	toolBarButtons.add(toolBarButton);
	
	toolBarButton = new Button(toolBar, SWT.FLAT);
	toolBarButton.setImage(ImageLoader.getImage("For.png"));
	toolBarButton.pack();
	toolBarButton.setToolTipText("Ciclo Para");
	toolBarButton.addSelectionListener(new AddForFigureAction(display));
	toolBarButton.addKeyListener(new KeyEvent());
	toolBarButtons.add(toolBarButton);
	
	toolBarButton = new Button(toolBar, SWT.FLAT);
	toolBarButton.setImage(ImageLoader.getImage("Salida.png"));
	toolBarButton.pack();
	toolBarButton.setToolTipText("Salida");
	toolBarButton.addSelectionListener(new AddOutputFigureAction(display));
	toolBarButton.addKeyListener(new KeyEvent());
	toolBarButtons.add(toolBarButton);
	
	createLayoutExtraSpace(toolBar);
	
	int xCoord = 40;
	int yCoord = 600;
	int width = 50;
	int height= 30;
	Button botonConsola = new Button(toolBar, SWT.FLAT);
	botonConsola.setBounds(xCoord, yCoord, width, height);
	botonConsola.setImage(ImageLoader.getImage("consola.ico"));
	botonConsola.pack();
	botonConsola.setToolTipText("Consola ");
	botonConsola.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			if (CustomMenu.getConsoleMenuItem().getSelection()) {
			    CustomMenu.getConsoleMenuItem().setSelection(false);
				MainWindow.getComponents().moverConsola(false);
			} else {
			    MainWindow.getComponents().moverConsola(true);
			    CustomMenu.getConsoleMenuItem().setSelection(true);
			}
		}
	});
    }
    
    private void createLayoutExtraSpace(ToolBar toolBar){
	GridData labelData = new GridData();
	labelData.grabExcessVerticalSpace = true;
	
	Label label = new Label(toolBar, SWT.NONE);
	label.setVisible(false);
	label.setLayoutData(labelData);
    }
    
    public void setEnabledAllButtons(boolean isEnabled) {
	toolBarButtons.get(0).setEnabled(isEnabled);
	toolBarButtons.get(1).setEnabled(isEnabled);
	toolBarButtons.get(2).setEnabled(isEnabled);
	toolBarButtons.get(3).setEnabled(isEnabled);
	toolBarButtons.get(4).setEnabled(isEnabled);
	toolBarButtons.get(5).setEnabled(isEnabled);
    }
    
    public ToolBar getBarraFiguras() {
        return barraFiguras;
    }

    public ArrayList<Button> getToolBarButtons() {
        return toolBarButtons;
    }

}
