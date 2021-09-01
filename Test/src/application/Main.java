package application;

public class Main {
	
	public static void main(String[] args) {
        
		Parser parser = new Parser();
		ParserI parser2 = new DateTimeParser();
		parser.parseAndDisplay("18/33/11/12");
		//parser.convert("bla");
		
		parser2.parseTime("31/18/33/11/1012", Precision.MILLISECOND);
    }
	
}
