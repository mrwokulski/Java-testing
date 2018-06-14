import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Hashtable;

class HighscoresTest {
	
	private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private ByteArrayOutputStream errContent = new ByteArrayOutputStream();
 
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	public void restoreStreams() {
	    System.setOut(System.out);
	    System.setErr(System.err);
	}

	@Test
	public void testHighscoreRead() throws ClassNotFoundException, IOException {
		Highscores hs = new Highscores();
		hs.read();
		hs.highScores.put("testWrite",1);
		int actual = hs.highScores.get("testWrite");
		assertEquals(1, actual);
	}
	
	@Test
	public void testHighscoreReadNoFile() {
		assertThrows(IOException.class,
				()->{
					Highscores hs = new Highscores();
					hs.pathr = "src/main/highscores1.list";
					hs.read();
				});
	}
	
	@Test
	public void testHighscoreWrite() throws ClassNotFoundException, IOException {
		Highscores hs = new Highscores();
		hs.read();
		hs.highScores.put("testWrite", 1);
		hs.save();
		setUpStreams();
		hs.print();
		restoreStreams();
		int actual = hs.highScores.get("testWrite");
		assertEquals(1, actual);
	}
	
	@Test
	public void testHighscorePrint() throws ClassNotFoundException, IOException {
		Highscores hs = new Highscores();		
		hs.read();
		hs.print();
		Hashtable<String,Integer> expected = hs.highScores;
		
		boolean same=false;
		setUpStreams();
		System.out.print(expected);
		String expect = outContent.toString();
		hs.print();
		restoreStreams();
		
		assertTrue(outContent.toString().contains(expect));
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "resources/highscore.csv")
	public void testAddHighscore(String name, int score, int expected ) throws IOException, ClassNotFoundException {
		//Arrange
		Czworki gra = new Czworki();
		//Act
		gra.hs.read();
		Highscores.highScores.put(name, score);
		gra.AddScore(name);
		int actual = gra.hs.highScores.get(name);
		//Assert
		assertEquals(expected, actual);
	}
}
