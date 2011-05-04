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


public class DiagramStructure {
    
    private Composite diagramArea;
    
    private FigureCanvas lightweightSystem;
    
    private Figure panel;
    
    private ScalableLayeredPane scaledPane;
    
    private static DiagramStructure instance;

    private DiagramStructure(){
	MainWindow.getComponents().addFiguresToolBar();
	
	initPanels();
	
	initScroll();
    }
    
    public static DiagramStructure getInstance(){
	if(instance==null){
	    instance = new DiagramStructure();
	}
	return instance;
    }
    
    private void initPanels(){
	diagramArea = new Composite(MainWindow.shell,SWT.NONE);
	diagramArea.setLayoutData(MainWindow.getComponents().diagramData);
	diagramArea.setLayout(getLayoutComposite());
	
	lightweightSystem = new FigureCanvas(diagramArea);
	lightweightSystem.setLayoutData(getLayoutFigureCanvas());
	
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
			lightweightSystem.scrollToY(lightweightSystem.getVerticalBar().getSelection()+direction*30);
		}});
	lightweightSystem.getVerticalBar().addSelectionListener(new SelectionListener(){
		public void widgetSelected(SelectionEvent arg0) {
			int direction=1;
			if(arg0.detail==16777217){
				direction=-1;
			}
			lightweightSystem.getVerticalBar().setSelection(lightweightSystem.getVerticalBar().getSelection()+direction*30);
		}
		public void widgetDefaultSelected(SelectionEvent arg0) {
		}});
    }
    
    public void addPaintDiagram(PaintDiagram chart){
	lightweightSystem.setContents(panel);
	panel.add(scaledPane);
	scaledPane.removeAll();
	scaledPane.add(chart);
    }
    
    public Figure getPanel(){
	return panel;
    }
    public ScrollBar getHScrollBar(){
    	return lightweightSystem.getHorizontalBar();
    }
    public ScrollBar getVScrollBar(){
    	return lightweightSystem.getVerticalBar();
    }
    public ScalableLayeredPane getScaledPane() {
	return scaledPane;
    }
    public void resetScrollBar(){
    	lightweightSystem.scrollToX((int)((lightweightSystem.getHorizontalBar().getMaximum()-lightweightSystem.getHorizontalBar().getSize().x)*50/100));
    	lightweightSystem.scrollToY(0);
    }
    public void setScrollBar(int x, int y){
    	lightweightSystem.scrollToX(x);
    	lightweightSystem.scrollToY(y);
    }
    public void setBoundsToZero(){
    	diagramArea.setBounds(0,0,0,0);
    	lightweightSystem.setBounds(0,0,0,0);
    	panel.setBounds(new Rectangle(0,0,0,0));
    	scaledPane.setBounds(new Rectangle(0,0,0,0));
    }
}
