package application;

/**
 * @author Thorben
 *
 * Interface für einen Parser, der aus einem gegebenen String einen Zeitpunk macht
 */
public interface ParserI {
	
	// default- Werte für Fehlermeldiung und Präzision eines Zeitpunkts
	public static final String DEFAULT_ERROR_MESSAGE = "Der übergebene String stellt keinen korrekten Zeitpunkt dar";
	public static final Precision DEFAULT_PRECISION = Precision.MILLISECOND; 

	/**
	* Diese Methode versucht, den übergebenen String als Zeitpunkt in
	* default- Präzision zu interpretieren und auf der Kommandozeile
	* auszugeben
	* <p>
	* Bei Übergabe eines (Teil-)Werts, der nicht als positive Zahl
	* interpretierbar ist, wird eine entsprechende Fehlermeldung aus- und
	* zurückgegeben
	* 
	* @param  time		Der als Zeitpunkt zu interpretierende String;
	* 					Erwartet werden Zahlenwerte jeweils getrennt
	* 					voneinander durch '/', beginnend bei dem Wert
	* 					niedrigster Präzision in Reihenfolge aufsteigender
	* 					Präzision bis hin zur default- Präzision
	* 					(zum Beispiel 'Stunde/Minute/Sekunde/Millisekunde');
	* 					Überzählige Werte werden ignoriert;
	* 					Bezeichnungen die nicht als gültige Zahlen interpretierbar
	* 					sind, führen zur Ausgabe einer Fehlermeldung
	* @return      ein String, der den ausgegebenen Zeitpunkt repräsentiert
	*/
	public String parseTime(String time);
	
	/**
	* Diese Methode versucht, den übergebenen String als Zeitpunkt zu
	* interpretieren und auf der Kommandozeile auszugeben
	* <p>
	* Bei Übergabe eines (Teil-)Werts, der nicht als positive Zahl
	* interpretierbar ist, wird eine entsprechende Fehlermeldung aus- und
	* zurückgegeben
	* 
	* @param  time		Der als Zeitpunkt zu interpretierende String;
	* 					Erwartet werden Zahlenwerte jeweils getrennt
	* 					voneinander durch '/', beginnend bei dem Wert
	* 					niedrigster Präzision in Reihenfolge aufsteigender
	* 					Präzision (zum Beispiel 'Stunde/Minute/Sekunde');
	* 					Überzählige Werte werden ignoriert;
	* 					Bezeichnungen die nicht als gültige Zahlen interpretierbar
	* 					sind, führen zur Ausgabe einer Fehlermeldung
	* @param  precision Die gewünschte Präzision des Zeitpunkts
	* 					(Jahr, Monat, Tag, Stunde, Minute, Sekunde oder Millisekunde)
	* @return      ein String, der den ausgegebenen Zeitpunkt repräsentiert
	*/
	public String parseTime(String time, Precision precision);
	
}
