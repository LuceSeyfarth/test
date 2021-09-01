//package de.conceptpeople.math;
package application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

public class DateTimeParser implements ParserI {
	
	private Calendar calendar;
	// StringBuilder fürs zusammenbauen des benötigten Zeitformats
	private StringBuilder dateBuilder;
	private StringBuilder timeBuilder;
	
	@Override
	public String parseTime(String time, Precision precision) {
		// erstelle einen neuen StingTokenizer mit Trennzeichen "/"
		StringTokenizer toki = new StringTokenizer(time, "/");
		
		// erstelle einen Array für die Zeittoken; Größe ist abhängig von der
		// gewünschten Genauigkeit
		String[] tokens = new String[precision.ordinal() + 1];
		int index = 0;
		// fülle den Token- Array mit den übergebenen Zeittoken und merke
		// den höchsten verwendeten Index
		while(toki.hasMoreElements() && index < tokens.length) {
			tokens[index] = toki.nextToken();
			index++;
		}
		index--;
		// wenn maximaler Index negativ (= keine Zeittoken), gib Fehlermeldung aus
		// und zurück
		if(index < 0) {
			return displayErrorMessage();
		}
		
		// initialisiere Calendar- Instanz und die StringBuilder
		// für das Zeitformat
		initCalendarAndBuilders();
		// Setze Monat auf 0 (Januar) für den Fall dass kein Monat angegeben
		// ist (höchster Wert für Tag = 31)
		calendar.set(Calendar.MONTH, 0);
		
		// iteriere über die Zeittoken
		for(int i = 0; i <= index; i++) {
			// konvertiere den Zeittoken in einen int- Wert
			int value = convert(tokens[i]);
			// wenn Rückgabewert = -1, handelt es sich um keinen gültigen Token;
			// gib Fehlermeldung aus und zurück
			if(value == -1) {
				return displayErrorMessage();
			}
			// berechne und setze anzugebende Präzision für gegebenen Token
			Precision currentPrecision = Precision.values()[precision.ordinal() - index + i];
			// prüfe, ob der Wert des Tokens das Limit für den Zeitwert überschreitet;
			// wenn ja, gib entsprechende Fehlermeldung aus und zurück
			if(currentPrecision.limitExceededByValue(value)) {
				return displayErrorMessage("Der angegebene " + currentPrecision + "-Wert ist ungültig");
			}

			// wenn Präzision = Monat, verringere den Wert des Tokens um 1,
			// da interne Monatswerte in Calendar von 0 - 11 anstatt von 1 - 12
			// abgebildet werden
			if(currentPrecision == Precision.MONTH) {
				value--;
			}
			
			// setze den Wert in der Calendar- Instanz
			calendar.set(currentPrecision.calendarValue, value);
			// wenn der gesetzte Wert ungleich dem ursprünglichen Wert ist,
			// gab es einen Overflow (z.B. Tag 30 im Februar);
			// dann gib entsprechende Fehlermeldung aus und zurück
			int setValue = calendar.get(currentPrecision.calendarValue);
			if(value != setValue) {
				return displayErrorMessage("Der angegebene " + currentPrecision + "-Wert ist ungültig");
			}
			
			// konkateniere entsprechendes patternLabel der Präzisionsstufe
			if(currentPrecision.dateField) {
				dateBuilder.insert(0, currentPrecision.patternLabel);
			}
			else {
				timeBuilder.append(currentPrecision.patternLabel);
			}
		}
		
		// initialisiere die entsprechende Formatierung nach gegebenen
		// Präzisionsstufen
		timeBuilder.setLength(Math.max(timeBuilder.length() - 1, 0));
		dateBuilder.append(' ').append(timeBuilder.toString());
		String pattern = dateBuilder.toString().trim();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		// erstelle einen richtig formatierten Zeitpunkt, gib diesen aus
		// und zurück
		String formattedTime = simpleDateFormat.format(calendar.getTime());
		System.out.println(formattedTime);
		return formattedTime;
	}
	
	/* konvertiert einen String, wenn möglich, in den entsprechenden Integer- Wert
	 * 
	 * gibt -1 zurück, wenn kein Zahlenwert oder der Integer kleiner als 1 ist
	 */
	private int convert(String valueToConvert) {
		int returnValue;
		// versuche, den String in einen Integer- Wert zu konvertieren
		try {
			returnValue = Integer.valueOf(valueToConvert);
			// wenn negativ, gib -1 zurück
			if(returnValue < 0) {
				return -1;
			}
		}
		// Wenn kein gültiger Integer- Wert, gib -1 zurück
		catch (NumberFormatException e) {
			returnValue = -1;
		}
		return returnValue;
	}
	
	@Override
	public String parseTime(String time) {
		// verwende die Basismethode mit der default- Präzision
		return parseTime(time, DEFAULT_PRECISION);
	}
	
	/* initialisiert StringBuilders für Datums- und Zeitformat;
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
	
	// gib default- Fehlermeldung aus und zurück
	private String displayErrorMessage() {
		return displayErrorMessage(DEFAULT_ERROR_MESSAGE);
	}
	
	// gib angegebene Fehlermeldung aus und zurück
	private String displayErrorMessage(String message) {
		System.out.println(message);
		return message;
	}
	
}
