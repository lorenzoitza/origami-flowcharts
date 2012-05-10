package origami.administration.functionality.code.targets.naturalDescription;


public class InputParserTesting {
    
    public static void main(String[] ar){
	String test = "float y =  t, , s =0, ,;int x,,,,,, y, s; inti, y, z,;Leer: int z, char r,";
	
	InputParser ip = new InputParser(test);
	ip.parsingLineInput();
	System.out.println(ip.printTableVariable());
	System.out.println(ip.getLineOutput());
	
    }

}
