package application;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static application.Precision.*;

public class ParserTest {
	
	private ParserI parser;
	private String message;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		parser = new DateTimeParser();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/* verschiedene Ausgaben bei default- Präzision (= Millisekunden):
	 * 
	 * Millisekunde
	 * Sekunde + Millisekunde
	 * Minute + Sekunde + Millisekunde
	 * Stunde + Minute + Sekunde + Millisekunde
	 * Tag + Stunde + Minute + Sekunde + Millisekunde
	 * Monat + Tag + Stunde + Minute + Sekunde + Millisekunde
	 * Jahr + Monat + Tag + Stunde + Minute + Sekunde + Millisekunde
	 * Jahr + Monat + Tag + Stunde + Minute + Sekunde + Millisekunde (= 0)
	 * Jahr + Monat + Tag + Stunde + Minute + Sekunde + Millisekunde + 3 überzählige Werte
	 */
	@Test
	public void testParseTime_DefaultPrecision() {
		assertTrue(parser.parseTime("999").equals("999"));
		assertTrue(parser.parseTime("11/123").equals("11,123"));
		assertTrue(parser.parseTime("59/11/545").equals("59:11,545"));
		assertTrue(parser.parseTime("10/30/11/123").equals("10:30:11,123"));
		assertTrue(parser.parseTime("15/9/57/40/12").equals("15. 09:57:40,012"));
		assertTrue(parser.parseTime("3/13/18/23/35/03").equals("13.03. 18:23:35,003"));
		assertTrue(parser.parseTime("2025/2/28/1/1/1/766").equals("28.02.2025 01:01:01,766"));
		assertTrue(parser.parseTime("2025/2/28/1/1/1/0").equals("28.02.2025 01:01:01,000"));
		assertTrue(parser.parseTime("2025/2/28/1/2/3/445/9/18/27").equals("28.02.2025 01:02:03,445"));
	}
	
	// Test verschiedener Präzisionsstufen
	@Test
	public void testParseTime_PrecisionTest() {
		//fail("Not yet implemented");
		assertTrue(parser.parseTime("1989/9/11/2/30/11/89", MILLISECOND).equals("11.09.1989 02:30:11,089"));
		assertTrue(parser.parseTime("1989/9/11/2/30/11/89", SECOND).equals("11.09.1989 02:30:11"));
		assertTrue(parser.parseTime("1989/9/11/2/30/11/89", MINUTE).equals("11.09.1989 02:30"));
		assertTrue(parser.parseTime("1989/9/11/2/30/11/89", HOUR).equals("11.09.1989 02"));
		assertTrue(parser.parseTime("1989/9/11/2/30/11/89", DAY).equals("11.09.1989"));
		assertTrue(parser.parseTime("1989/9/11/2/30/11/89", MONTH).equals("09.1989"));
		assertTrue(parser.parseTime("1989/9/11/2/30/11/89", YEAR).equals("1989"));
	}
	
	// leere Strings werfen default- Fehlermeldung
	@Test
	public void testParseTime_Empty() {
		assertTrue(parser.parseTime("").equals(ParserI.DEFAULT_ERROR_MESSAGE));
		for(Precision prec : Precision.values()) {
			assertTrue(parser.parseTime("", prec).equals(ParserI.DEFAULT_ERROR_MESSAGE));
		}
	}
		
	// Strings mit Token, die keine gültigen, positiven Zahlen darstellen
	@Test
	public void testParseTime_IllegalNumber() {
		assertTrue(parser.parseTime("2025/2/28/1/1/1/-80").equals(ParserI.DEFAULT_ERROR_MESSAGE));
		assertTrue(parser.parseTime("2025/2/28/1/xxy/1/80").equals(ParserI.DEFAULT_ERROR_MESSAGE));
		assertTrue(parser.parseTime("///").equals(ParserI.DEFAULT_ERROR_MESSAGE));
	}
	
	/* Strings mit Token, die Maximalwerte überschreiten
	 * 
	 * Millisekunde > 999
	 * Sekunde > 59
	 * Minute > 59
	 * Stunde > 23
	 * Tag = 29.2.2025 (nicht existent)
	 * Tag = 0
	 * korrekter Tag
	 * Monat = 13
	 * Monat = 0
	 * korrekter Monat
	 * Jahr = 0
	 * korrektes Jahr
	 */
	@Test
	public void testParseTime_ExceedsLimit() {
		assertTrue(parser.parseTime("2025/2/28/1/1/30/1000").equals("Der angegebene Millisekunde-Wert ist ungültig"));
		assertTrue(parser.parseTime("2025/2/28/1/1/60/999").equals("Der angegebene Sekunde-Wert ist ungültig"));
		assertTrue(parser.parseTime("2025/2/28/1/60/40/766").equals("Der angegebene Minute-Wert ist ungültig"));
		assertTrue(parser.parseTime("2025/2/28/24/59/44/766").equals("Der angegebene Stunde-Wert ist ungültig"));
		assertTrue(parser.parseTime("2025/2/29/23/17/6/766").equals("Der angegebene Tag-Wert ist ungültig"));
		assertTrue(parser.parseTime("2025/2/0/23/17/6/766").equals("Der angegebene Tag-Wert ist ungültig"));
		assertTrue(parser.parseTime("2025/2/28/23/17/6/766").equals("28.02.2025 23:17:06,766"));
		assertTrue(parser.parseTime("2025/13/31/23/17/6/766").equals("Der angegebene Monat-Wert ist ungültig"));
		assertTrue(parser.parseTime("2025/0/31/23/17/6/766").equals("Der angegebene Monat-Wert ist ungültig"));
		assertTrue(parser.parseTime("2025/12/31/23/17/6/766").equals("31.12.2025 23:17:06,766"));
		assertTrue(parser.parseTime("0/13/31/23/17/6/766").equals("Der angegebene Jahr-Wert ist ungültig"));
		assertTrue(parser.parseTime("2025/12/31/23/17/6/766").equals("31.12.2025 23:17:06,766"));
	}
	
	@Test
	public void testParseTime_NoMonth() {
		assertTrue(parser.parseTime("31/23/17/6/766").equals("31. 23:17:06,766"));
	}
	
	@Test
	public void testParseTime() {
		//fail("Not yet implemented");
	}

}
