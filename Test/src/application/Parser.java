//package de.conceptpeople.math;
package application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

public class Parser {

	public void parseAndDisplay(String s) {
		StringTokenizer toki = new StringTokenizer(s, "/");
		
		String t1 = toki.nextToken();
		String t2 = toki.nextToken();
		String t3 = toki.nextToken();
		String t4 = toki.nextToken();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, convert(t1));
		calendar.set(Calendar.MINUTE, convert(t2));
		calendar.set(Calendar.SECOND, convert(t3));
		calendar.set(Calendar.MILLISECOND, convert(t4));
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		System.out.println(simpleDateFormat.format(calendar.getTime()));
	}
	
	
	public int convert(String valueToConvert) {
		Integer returnValue = null;
		try {
			returnValue = Integer.valueOf(valueToConvert);
		} catch (NumberFormatException e) {
		}
		return returnValue;
	}
}
