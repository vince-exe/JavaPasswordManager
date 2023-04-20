package utils;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

public class Utils {
	public final static String EU_FORMAT_DATE_TIME = "dd-MM-yyyy HH-mm-ss";
	
	public static String getDate(String format) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);  
		LocalDateTime now = LocalDateTime.now();  
		
		return dtf.format(now);
	}
}
