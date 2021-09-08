package application;

/**
 * @author Thorben
 *
 * Interface f�r einen Parser, der aus einem gegebenen String einen Zeitpunk macht
 */
public interface ParserI {
	
	// default- Werte f�r Fehlermeldiung und Pr�zision eines Zeitpunkts
	public static final String DEFAULT_ERROR_MESSAGE = "Der �bergebene String stellt keinen korrekten Zeitpunkt dar";
	public static final Precision DEFAULT_PRECISION = Precision.MILLISECOND; 

	/**
	* Diese Methode versucht, den �bergebenen String als Zeitpunkt in
	* default- Pr�zision zu interpretieren und auf der Kommandozeile
	* auszugeben
	* <p>
	* Bei �bergabe eines (Teil-)Werts, der nicht als positive Zahl
	* interpretierbar ist, wird eine entsprechende Fehlermeldung aus- und
	* zur�ckgegeben
	* 
	* @param  time		Der als Zeitpunkt zu interpretierende String;
	* 					Erwartet werden Zahlenwerte jeweils getrennt
	* 					voneinander durch '/', beginnend bei dem Wert
	* 					niedrigster Pr�zision in Reihenfolge aufsteigender
	* 					Pr�zision bis hin zur default- Pr�zision
	* 					(zum Beispiel 'Stunde/Minute/Sekunde/Millisekunde');
	* 					�berz�hlige Werte werden ignoriert;
	* 					Bezeichnungen die nicht als g�ltige Zahlen interpretierbar
	* 					sind, f�hren zur Ausgabe einer Fehlermeldung
	* @return      ein String, der den ausgegebenen Zeitpunkt repr�sentiert
	*/
	public String parseTime(String time);
	
	/**
	* Diese Methode versucht, den �bergebenen String als Zeitpunkt zu
	* interpretieren und auf der Kommandozeile auszugeben
	* <p>
	* Bei �bergabe eines (Teil-)Werts, der nicht als positive Zahl
	* interpretierbar ist, wird eine entsprechende Fehlermeldung aus- und
	* zur�ckgegeben
	* 
	* @param  time		Der als Zeitpunkt zu interpretierende String;
	* 					Erwartet werden Zahlenwerte jeweils getrennt
	* 					voneinander durch '/', beginnend bei dem Wert
	* 					niedrigster Pr�zision in Reihenfolge aufsteigender
	* 					Pr�zision (zum Beispiel 'Stunde/Minute/Sekunde');
	* 					�berz�hlige Werte werden ignoriert;
	* 					Bezeichnungen die nicht als g�ltige Zahlen interpretierbar
	* 					sind, f�hren zur Ausgabe einer Fehlermeldung
	* @param  precision Die gew�nschte Pr�zision des Zeitpunkts
	* 					(Jahr, Monat, Tag, Stunde, Minute, Sekunde oder Millisekunde)
	* @return      ein String, der den ausgegebenen Zeitpunkt repr�sentiert
	*/
	public String parseTime(String time, Precision precision);
	
}
