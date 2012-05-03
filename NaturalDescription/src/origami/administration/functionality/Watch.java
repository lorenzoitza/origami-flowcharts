package origami.administration.functionality;


public class Watch extends WatchController{

    public void sendNext(){
	inputActionPerformed("next");
    }
    public void main(){
	//inputActionPerformed("file main.exe");
	inputActionPerformed("file test.exe");
	inputActionPerformed("break 5");
	inputActionPerformed("run");
	inputActionPerformed("watch i");
	sendNext();
	sendNext();
	sendNext();
    }
    public static void main(String args[]){
	Watch watch = new Watch(); 
	watch.execute("gdb");
	watch.main();
	
    }
}
