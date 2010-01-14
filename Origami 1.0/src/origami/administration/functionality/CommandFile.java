package origami.administration.functionality;


public class CommandFile {
    
    public static String COMMAND_MAIN_C = "cmd /c gcc -g -o main main.c";
    public static String COMMAND_MAIN_CPP = "cmd /c g++ -g -o main main.cpp";
    
    private String sourcerFile;
    
    private String comand;
    
   
    public String getSourcerFile() {
        return sourcerFile;
    }
    
    public void setSourcerFile(String sourcerFile) {
        this.sourcerFile = sourcerFile;
    }
    
    public String getComand() {
        return comand;
    }
    
    public void setComand(String comand) {
        this.comand = comand;
    }
   

}
