package origami.administration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class OrigamiException extends Exception{
	private String mensaje="";
	private static int cantError=0;
	
	public OrigamiException(Exception e){
		cantError++;
		verificaFichero();
		updateStats();
		obtenerErrMensaje(e);
		guardarErrMensaje();
	}
	private void verificaFichero(){
		File file = new File("configuracion/buglog.txt");
		if(!file.exists()){
			cantError=1;
		}
	}
	private void updateStats(){
		FileReader fichero;
		BufferedReader bf;
		String linea="";
		mensaje="";
        try{
            fichero = new FileReader("configuracion/buglog.txt");
            bf = new BufferedReader(fichero);
            int cont=0;
            while((linea = bf.readLine())!=null){
        		if(cont>=1){
        			mensaje+=linea+"\r\n";
        		}
        		else{
        			String num="";
        			for(int x=15;x<linea.length();x++){
        				if(linea.substring(x,x+1)==" "){
        					break;
        				}
        				else{
        					num+=linea.substring(x,x+1);
        				}
        			}
        			cantError=Integer.valueOf(num)+1;
        		}
        		cont++;
        	}
            bf.close();
            fichero.close();

        }catch(Exception e){
            e.printStackTrace();
        } 
        FileWriter fichero2;
        PrintWriter pw;
        String stats ="TOTAL ERRORES: "+cantError+"\r\n";
        mensaje=stats+mensaje;
        try{
        	fichero2 = new FileWriter("configuracion/buglog.txt",false);
            pw = new PrintWriter(fichero2);
            pw.println(mensaje);
            pw.close();
            fichero2.close();

        }catch(Exception e){
            e.printStackTrace();
        } 
	}
	private void obtenerErrMensaje(Exception e){
		System.err.println(e.getMessage() + "\n" );
		e.printStackTrace();

	    StackTraceElement[] elementosRastreo = e.getStackTrace();
	    mensaje="************************************************************" +
	    		"************************************************************" +
	    		"****";
	    mensaje += "\r\nRastreo de pila proveniente de getStackTrace:\r\n";
	    mensaje += "Clase\t\t\t\t\t\t\tArchivo\t\t\tLinea\tMetodo\r\n";
	    for(int i=0;i<elementosRastreo.length;i++){
	    	StackTraceElement elementoActual = elementosRastreo[ i ];
	    	mensaje += elementoActual.getClassName();
	    	for(int x=0;x<56-elementoActual.getClassName().length();x++){
	    		mensaje+=" ";
	    	}
	    	mensaje += elementoActual.getFileName();
	    	for(int x=0;x<24-elementoActual.getFileName().length();x++){
	    		mensaje+=" ";
	    	}
	    	mensaje += elementoActual.getLineNumber();
	    	for(int x=0;x<8-Integer.toString(elementoActual.getLineNumber()).length();x++){
	    		mensaje+=" ";
	    	}
	    	mensaje += elementoActual.getMethodName()+"\r\n";
	    }
	}
	private void guardarErrMensaje(){
		FileWriter fichero;
        PrintWriter pw;
        try{
            fichero = new FileWriter("configuracion/buglog.txt",true);
            pw = new PrintWriter(fichero);
            pw.println(mensaje);
            pw.close();
            fichero.close();

        }catch(Exception e){
            e.printStackTrace();
        } 
	}
}
