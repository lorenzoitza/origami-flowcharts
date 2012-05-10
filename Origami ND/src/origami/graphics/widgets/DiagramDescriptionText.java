package origami.graphics.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.swt.widgets.Text;

import origami.administration.AdminDiagram;
import origami.administration.functionality.code.InstructionNaturalDescription;
import origami.administration.functionality.code.ManagerCodeFormat;



public class DiagramDescriptionText {
    private Text textWidget;
    
    
    public DiagramDescriptionText(Composite parent){
	textWidget = new Text(parent, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);
	textWidget.setEditable(false);
	
    }

    public Text getTextWidget() {
        return textWidget;
    }
   
    public void setTextWidget(Text textWidget) {
        this.textWidget = textWidget;
    }
    
    public String getTextDescription() {
        return textWidget.getText();
    }
  
    public void setTextDescription(String textDescription) {
        this.textWidget.setText(textDescription);
    }
    public void update(AdminDiagram diagram){
	ManagerCodeFormat manager = new ManagerCodeFormat(diagram.getDiagram());
	manager.formatCodeNaturalDescription();
	setTextDescription(manager.getInstructionsFormat());
		
    }
}
