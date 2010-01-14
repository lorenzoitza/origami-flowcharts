package origami.graphics;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.ScalableLayeredPane;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;


/**
 * Realizar un singleton a esta clase para que de esta manera se asegure que solo
 * existe una base de los diagramas.
 * @author Administrador
 *
 */
public class BaseDeDiagrama {
    
    private Composite diagramaArea;
    private FigureCanvas lws;
    private Figure panel;
    private ScalableLayeredPane scaledPane;
    private static BaseDeDiagrama instance;

    private BaseDeDiagrama(){
	
	MainWindow.getComponents().addFiguresToolBar();
	
	initPanels();
	
	initScroll();
	
    }
    
    public static BaseDeDiagrama getInstance(){
	if(instance==null){
	    instance = new BaseDeDiagrama();
	}
	return instance;
    }
    
    private void initPanels(){
	diagramaArea = new Composite(MainWindow.shell,SWT.NONE);
	diagramaArea.setLayoutData(MainWindow.getComponents().diagramData);
	diagramaArea.setLayout(getLayoutComposite());
	
	lws = new FigureCanvas(diagramaArea);
	lws.setLayoutData(getLayoutFigureCanvas());
	
	panel = new Figure();
	panel.setLayoutManager(getLayoutFigure());
	
	
	scaledPane = new ScalableLayeredPane();
	
	org.eclipse.draw2d.GridLayout gridLayout = (org.eclipse.draw2d.GridLayout)panel.getLayoutManager();
	org.eclipse.draw2d.GridData gridData = new org.eclipse.draw2d.GridData(SWT.FILL,SWT.FILL,true,true,1,1);
	gridLayout.horizontalSpacing =gridLayout.verticalSpacing=0;
	gridLayout.marginWidth = gridLayout.marginHeight = 0;
	gridLayout.setConstraint(scaledPane, gridData);
    }
    
    private GridLayout getLayoutComposite(){
	GridLayout layoutComposite = new GridLayout(1, false);
	layoutComposite.horizontalSpacing =layoutComposite.verticalSpacing=0;
	layoutComposite.marginWidth = layoutComposite.marginHeight = 0;
	return layoutComposite;
    }
    
    private GridData getLayoutFigureCanvas(){
	GridData diagramaGrid =new GridData(SWT.FILL,SWT.FILL,true,true,1,1);
	return diagramaGrid;
    }
    
    private org.eclipse.draw2d.GridLayout getLayoutFigure(){
	org.eclipse.draw2d.GridLayout gridLayout = new org.eclipse.draw2d.GridLayout();
	gridLayout.numColumns = 1;
	return gridLayout;
    }
    private void initScroll(){
	MainWindow.shell.addMouseWheelListener(new MouseWheelListener(){
		public void mouseScrolled(MouseEvent arg0) {
			int direction=1;
			if(arg0.count>0){
				direction=-1;
			}
			lws.scrollToY(lws.getVerticalBar().getSelection()+direction*30);
		}});
	lws.getVerticalBar().addSelectionListener(new SelectionListener(){
		public void widgetSelected(SelectionEvent arg0) {
			int direction=1;
			if(arg0.detail==16777217){
				direction=-1;
			}
			lws.getVerticalBar().setSelection(lws.getVerticalBar().getSelection()+direction*30);
		}
		public void widgetDefaultSelected(SelectionEvent arg0) {
		}});
    }
    
    public void addPaintDiagram(PaintDiagram chart){
	lws.setContents(panel);
	panel.add(scaledPane);
	scaledPane.removeAll();
	scaledPane.add(chart);
    }
    
    public Figure getPanel(){
	return panel;
    }
    public ScrollBar getHScrollBar(){
    	return lws.getHorizontalBar();
    }
    public ScrollBar getVScrollBar(){
    	return lws.getVerticalBar();
    }
    public ScalableLayeredPane getScaledPane() {
	return scaledPane;
    }
    public void resetScrollBar(){
    	lws.scrollToX((int)((lws.getHorizontalBar().getMaximum()-lws.getHorizontalBar().getSize().x)*50/100));
    	lws.scrollToY(0);
    }
    public void setScrollBar(int x, int y){
    	lws.scrollToX(x);
    	lws.scrollToY(y);
    }
    public void setBoundsToZero(){
    	diagramaArea.setBounds(0,0,0,0);
    	lws.setBounds(0,0,0,0);
    	panel.setBounds(new Rectangle(0,0,0,0));
    	scaledPane.setBounds(new Rectangle(0,0,0,0));
    }
}
