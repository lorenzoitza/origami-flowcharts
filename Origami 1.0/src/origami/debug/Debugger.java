/**
 * 
 */
package origami.debug;

import java.util.Calendar;

import origami.graphics.view.OpenType;


/**
 * Clase que ayuda a debugear en Origami.
 * @author Hudy
 *
 */
public class Debugger {
	
	private static String getTime(){
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		return "["+hour+":"+minute+":"+second;
	}
	
	public static void debug(Class<?> clazz, String message){
		System.out.println(getTime()+" DEBUG] "+clazz.getCanonicalName()+" --> "+message);
	}
	
	public static void error(Class<?> clazz, String message){
		System.err.print(getTime()+" ERROR] "+ clazz.getName()+" --> "+message);
	}
	
}
