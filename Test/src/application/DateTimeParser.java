//package de.conceptpeople.math;
package application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

public class DateTimeParser implements ParserI {
	
	private Calendar calendar;
	// StringBuilder f�rs zusammenbauen des ben�tigten Zeitformats
	private StringBuilder dateBuilder;
	private StringBuilder timeBuilder;
	
	@Override
	public String parseTime(String time, Precision precision) {
		// erstelle einen neuen StingTokenizer mit Trennzeichen "/"
		StringTokenizer toki = new StringTokenizer(time, "/");
		
		// erstelle einen Array f�r die Zeittoken; Gr��e ist abh�ngig von der
		// gew�nschten Genauigkeit
		String[] tokens = new String[precision.ordinal() + 1];
		int index = 0;
		// f�lle den Token- Array mit den �bergebenen Zeittoken und merke
		// den h�chsten verwendeten Index
		while(toki.hasMoreElements() && index < tokens.length) {
			tokens[index] = toki.nextToken();
			index++;
		}
		index--;
		// wenn maximaler Index negativ (= keine Zeittoken), gib Fehlermeldung aus
		// und zur�ck
		if(index < 0) {
			return displayErrorMessage();
		}
		
		// initialisiere Calendar- Instanz und die StringBuilder
		// f�r das Zeitformat
		initCalendarAndBuilders();
		// Setze Monat auf 0 (Januar) f�r den Fall dass kein Monat angegeben
		// ist (h�chster Wert f�r Tag = 31)
		calendar.set(Calendar.MONTH, 0);
		
		// iteriere �ber die Zeittoken
		for(int i = 0; i <= index; i++) {
			// konvertiere den Zeittoken in einen int- Wert
			int value = convert(tokens[i]);
			// wenn R�ckgabewert = -1, handelt es sich um keinen g�ltigen Token;
			// gib Fehlermeldung aus und zur�ck
			if(value == -1) {
				return displayErrorMessage();
			}
			// berechne und setze anzugebende Pr�zision f�r gegebenen Token
			Precision currentPrecision = Precision.values()[precision.ordinal() - index + i];
			// pr�fe, ob der Wert des Tokens das Limit f�r den Zeitwert �berschreitet;
			// wenn ja, gib entsprechende Fehlermeldung aus und zur�ck
			if(currentPrecision.limitExceededByValue(value)) {
				return displayErrorMessage("Der angegebene " + currentPrecision + "-Wert ist ung�ltig");
			}

			// wenn Pr�zision = Monat, verringere den Wert des Tokens um 1,
			// da interne Monatswerte in Calendar von 0 - 11 anstatt von 1 - 12
			// abgebildet werden
			if(currentPrecision == Precision.MONTH) {
				value--;
			}
			
			// setze den Wert in der Calendar- Instanz
			calendar.set(currentPrecision.calendarValue, value);
			// wenn der gesetzte Wert ungleich dem urspr�nglichen Wert ist,
			// gab es einen Overflow (z.B. Tag 30 im Februar);
			// dann gib entsprechende Fehlermeldung aus und zur�ck
			int setValue = calendar.get(currentPrecision.calendarValue);
			if(value != setValue) {
				return displayErrorMessage("Der angegebene " + currentPrecision + "-Wert ist ung�ltig");
			}
			
			// konkateniere entsprechendes patternLabel der Pr�zisionsstufe
			if(currentPrecision.dateField) {
				dateBuilder.insert(0, currentPrecision.patternLabel);
			}
			else {
				timeBuilder.append(currentPrecision.patternLabel);
			}
		}
		
		// initialisiere die entsprechende Formatierung nach gegebenen
		// Pr�zisionsstufen
		timeBuilder.setLength(Math.max(timeBuilder.length() - 1, 0));
		dateBuilder.append(' ').append(timeBuilder.toString());
		String pattern = dateBuilder.toString().trim();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		// erstelle einen richtig formatierten Zeitpunkt, gib diesen aus
		// und zur�ck
		String formattedTime = simpleDateFormat.format(calendar.getTime());
		System.out.println(formattedTime);
		return formattedTime;
	}
	
	/* konvertiert einen String, wenn m�glich, in den entsprechenden Integer- Wert
	 * 
	 * gibt -1 zur�ck, wenn kein Zahlenwert oder der Integer kleiner als 1 ist
	 */
	private int convert(String valueToConvert) {
		int returnValue;
		// versuche, den String in einen Integer- Wert zu konvertieren
		try {
			returnValue = Integer.valueOf(valueToConvert);
			// wenn negativ, gib -1 zur�ck
			if(returnValue < 0) {
				return -1;
			}
		}
		// Wenn kein g�ltiger Integer- Wert, gib -1 zur�ck
		catch (NumberFormatException e) {
			returnValue = -1;
		}
		return returnValue;
	}
	
	@Override
	public String parseTime(String time) {
		// verwende die Basismethode mit der default- Pr�zision
		return parseTime(time, DEFAULT_PRECISION);
	}
	
	/* initialisiert StringBuilders f�r Datums- und Zeitformat;
	 * 
	 * erstellt neue Objekte beim ersten Aufruf, leert ansonsten die Buffer
	 * der StringBuilder
	 */
	private void initCalendarAndBuilders() {
		if(calendar == null) {
			calendar = Calendar.getInstance();
		}
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.YEAR, 2020);
		if(dateBuilder == null) {
			dateBuilder = new StringBuilder();
		}
		else {
			dateBuilder.setLength(0);
		}
		if(timeBuilder == null) {
			timeBuilder = new StringBuilder();
		}
		else {
			timeBuilder.setLength(0);
		}
	}
	
	// gib default- Fehlermeldung aus und zur�ck
	private String displayErrorMessage() {
		return displayErrorMessage(DEFAULT_ERROR_MESSAGE);
	}
	
	// gib angegebene Fehlermeldung aus und zur�ck
	private String displayErrorMessage(String message) {
		System.out.println(message);
		return message;
	}
	
}
