package Grafico.Help;

import org.eclipse.swt.browser.Browser;

public class HelpContents{
	private Browser browser;
	private String pathContent;
	
	public HelpContents(Browser browser){
		this.browser = browser;
		this.pathContent = System.getProperty("user.dir") + "\\help\\";
	}
	public void goHelpPage(String helpPage){
		browser.setUrl(pathContent + helpPage);
	}
}