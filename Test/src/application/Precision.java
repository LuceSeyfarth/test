package application;

import java.util.Calendar;

/**
 * @author Thorben
 *
 * ein Enum von gültigen Zeit- Präzisionswerten
 */
public enum Precision {

	YEAR("Jahr", Calendar.YEAR, "yyyy", true, -1),
	MONTH("Monat", Calendar.MONTH, "MM.", true, 12),
	DAY("Tag", Calendar.DAY_OF_MONTH, "dd.", true, 31),
	HOUR("Stunde", Calendar.HOUR_OF_DAY, "HH:", false, 23),
	MINUTE("Minute", Calendar.MINUTE, "mm:", false, 59),
	SECOND("Sekunde", Calendar.SECOND, "ss,", false, 59),
	MILLISECOND("Millisekunde", Calendar.MILLISECOND, "SSS ", false, 999);

	// entsprechendes Feld der Calendar- Klasse
	public final int calendarValue;
	// Label für entsprechendes Zeitformat
	public final String patternLabel;
	// 'true', wenn diese Präzisionsstufe Jahr, Monat oder Tag ist
	public final Boolean dateField;
	// der name der Präzisionsstufe (für toString- Methode)
	private final String label;
	// Maximalwert dieser Präzisionsstufe; -1 wenn ohne Maximalwert
	private final int maxValue;
	
	private Precision(String label, int calendarValue, String patternLabel, Boolean parseForward, int maxValue) {
		this.label = label;
		this.calendarValue = calendarValue;
		this.patternLabel = patternLabel;
		this.dateField = parseForward;
		this.maxValue = maxValue;
	}
	
	/**
	 * Die Methode prüft, ob der übergebene Wert den Maximalwert dieser
	 * Präzisionsstufe überschreitet
	 * 
	 * @param value		Der zu prüfende Wert
	 * @return		'true', wenn der zu prüfende Wert den Maximalwert
	 * 				überschreitet, 'false' sonst
	 */
	public boolean limitExceededByValue(int value) {
		// kein Limit, wenn maxValue negativ
		if(maxValue < 0) {
			return false;
		}
		return value > maxValue;
	}
	
	@Override
	public String toString() {
		return label;
	}
	
}
