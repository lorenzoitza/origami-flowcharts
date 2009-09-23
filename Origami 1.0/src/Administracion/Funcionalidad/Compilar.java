package Administracion.Funcionalidad;

import java.io.*;
import Administracion.Funcionalidad.Codigo.Instruccion;
import Administracion.TabFolder;
/**
 * @version Origami 1.0
 * @author Juan Ku, Victor Rodriguez
 */
public class Compilar{
	private File arch;
	private File file;
	public boolean errorBandera =  false;
	public String error;
	public TabFolder tab;
	
	public Compilar(TabFolder tabfolder){
		tab = tabfolder;
	}
	public void main(boolean codigoC,boolean ejecucion){
		guardarCodigo(codigoC,ejecucion);
		compilarCodigo(codigoC);
	}
	public void eliminarArchivosCompilar(){
		try{
			File file2 = new File("main.exe");
			file2.delete();
        } 
        catch(Exception e){
        }
		file.delete();
		arch.delete();
	}
	public boolean crearExe(String nombre){
		Instruccion codigo = new Instruccion();
		String 	comando="cmd /c gcc -g -o " +nombre+" "+ nombre+".c";
		arch = new File(nombre+".c");
		codigo.main(tab.getHoja().getDiagrama(),true);
		try{       
			PrintWriter pw = new PrintWriter(arch);
			pw.write(codigo.codigoTotal);
			pw.close();
        } 
        catch(Exception e){
            e.printStackTrace();
        }
        boolean error = false;
        try{
        	Process p;
        	file = new File(nombre+".c");
        	while(true){
        		if(file.exists()){
        			p=Runtime.getRuntime().exec (comando);
        			InputStream stderr = p.getErrorStream();
                    InputStreamReader isr = new InputStreamReader(stderr);
                    BufferedReader brr = new BufferedReader(isr);
                    while((brr.readLine()) != null){
                    	error = true;
                    	break;
                    }
        			break;
        		}
        	}
        } 
        catch(Exception e){
            e.printStackTrace();
        }
		file.delete();
		arch.delete();
		return error;
	}
	public void guardarCodigo(boolean codigoC,boolean ejecucion){
		Instruccion codigo = new Instruccion();
		if(codigoC){
			arch = new File("main.c");
			codigo.main(tab.getHoja().getDiagrama(),true);
		}
		else if(!codigoC && ejecucion){
			arch = new File("main.cpp");
	    	codigo.main(tab.getHoja().getDiagrama(),false);
		}
		else if(!codigoC && !ejecucion){
			arch = new File("main.cpp");
	    	codigo.generarGDB(tab.getHoja().getDiagrama());
		}
		try{       
			PrintWriter pw = new PrintWriter(arch);
			pw.write(codigo.codigoTotal);
			pw.close();
        } 
        catch(Exception e){
            e.printStackTrace();
        }
    }
	public void compilarCodigo(boolean codigoC){
		String archivo,comando;
		if(codigoC){
			archivo = "main.c";
			comando="cmd /c gcc -g -o main main.c";
		}
		else{
			archivo = "main.cpp";
			comando="cmd /c g++ -g -o main main.cpp";
		}
		try{
        	Process p;
        	file = new File(archivo);
        	while(true){
        		if(file.exists()){
        			p=Runtime.getRuntime().exec (comando);
        			InputStream stderr = p.getErrorStream();
                    InputStreamReader isr = new InputStreamReader(stderr);
                    BufferedReader brr = new BufferedReader(isr);
                    String linea = null;
                    error = "<ERROR>\n";
                    while((linea = brr.readLine()) != null){
                    	error += linea + "\n";
                    	errorBandera = true;
                    }
        			break;
        		}
        	}
        } 
        catch(Exception e){
            e.printStackTrace();
        }
    }
}