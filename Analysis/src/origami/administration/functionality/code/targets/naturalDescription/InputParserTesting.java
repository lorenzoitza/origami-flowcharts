package origami.administration.functionality.code.targets.naturalDescription;


public class InputParserTesting {
    
    public static void main(String[] ar){
	/*String test = "float y =  t, , s =0, ,;int x,,,,,, y, s; inti, y, z,;Leer: int z, char r,";
	
	InputParser ip = new InputParser(test);
	ip.parsingLineInput();
	System.out.println(ip.printTableVariable());
	System.out.println(ip.getLineOutput());
	*/
	String testExpression = "x =0 ;y = y +1 ;y=1--y; y++1= ;oiuoi=424c;y = 999; y= y*3; y=y/2;5=89/89; y=0/8; y=r*3";
	String testExpression1 = "x=x+";
	//System.out.println(testExpression1.split("[\\+\\-\\*/\\(\\)]",-1).length);
	String testCondition =  "Si ( r>0 && !(k<=rt||x>0)| r && r== 3  p != r *(34) || r>0){";
	//String testFor = "for(int i =0;;){";
	//System.out.println(new ForParser(testFor).getLineOutput());
	
	String testOutput = "\\p\"a,a,a,a,a\",a,a,a;\\p\"otra cadena\", cm d,";
	System.out.println(new OutputParser(testOutput).getLineOutput());
    }

}
