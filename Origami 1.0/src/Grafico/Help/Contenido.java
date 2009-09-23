package Grafico.Help;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Contenido{
	public void crearContenido(){
		String path = System.getProperty("user.dir");
		VentanaHelp.browser.setUrl(path+"\\help\\Origami.html");
	}
	public void crearContenidoEstructuras(){
		
	}
	public void crearContenidoIf(){
		String path = System.getProperty("user.dir");
		VentanaHelp.browser.setUrl(path+"\\help\\Decision.html");
	}
	public void crearContenidoWhile(){
		String path = System.getProperty("user.dir");
		VentanaHelp.browser.setUrl(path+"\\help\\CicloWhile.html");
	}
	public void crearContenidoFor(){
		String path = System.getProperty("user.dir");
		VentanaHelp.browser.setUrl(path+"\\help\\CicloFor.html");
	}
	public void crearContenidoProceso(){
		String path = System.getProperty("user.dir");
		VentanaHelp.browser.setUrl(path+"\\help\\Proceso.html");
	}
	public void crearContenidoES(){
		String path = System.getProperty("user.dir");
		VentanaHelp.browser.setUrl(path+"\\help\\Entrada-Salida.html");
	}
	public void crearContenidoAyuda(){
		
	}
	public void crearContenidoHistoria(){
		String path = System.getProperty("user.dir");
		VentanaHelp.browser.setUrl(path+"\\help\\Contactanos.html");
	}
}