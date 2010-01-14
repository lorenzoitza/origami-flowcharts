package origami.graphics.Help;

import org.eclipse.swt.browser.Browser;

public class HelpContents{
    private Browser browser;
    
    private String pathContent;
    
    private final String userPath = "user.dir";
    
    private final String folderPath = "\\help\\";
    
    public HelpContents(Browser browser){
	this.browser = browser;
	
	this.pathContent = System.getProperty(userPath) + folderPath;
    }
    public void goHelpPage(String helpPage){
	browser.setUrl(pathContent + helpPage);
    }
}