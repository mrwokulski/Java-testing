import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

class CzworkiTest {

	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream inContent = new ByteArrayOutputStream();

	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	public void restoreStreams() {
	    System.setOut(System.out);
	    System.setErr(System.err);
	}
	
	public InputStream inputStream(String input) {
		InputStream in = new ByteArrayInputStream(input.getBytes());
		return in;
	}

	public String printToString(int x, int y) {
		Czworki gra = new Czworki();
		for(int i=0; i<x; i++) {
			System.out.print("\n");
			for(int j=0; j<y;j++) {
				System.out.print(gra.plansza[i][j]+" ");
			}
		}
		String output = outContent.toString();
		return output;
	}
	
	public void printGame(Czworki gra) {
		for(int i=0; i<6; i++) {
			System.out.print("\n");
			for(int j=0; j<7;j++) {
				System.out.print(gra.plansza[i][j]+" ");
			}
		}
	}
	
	
	@ParameterizedTest
	@CsvSource({ "6,7", "10,10", "5,4" })
	void testSetup(int height, int width) throws ClassNotFoundException, IOException {
		Czworki gra = new Czworki();
		String[][] actual = gra.Setup(height, width);
		assertThat(actual.length, is(equalTo(height)));
		assertThat(actual[0].length, is(equalTo(width)));
	}

	@Test
	public void testSetupWrongParam() {
		assertThrows(NegativeArraySizeException.class,
				()->{
					Czworki gra = new Czworki();
					int x = 1;
					int y = -2;
					gra.Setup(x, y);
				});
	}
	/*
	@Test
	void testPrint() throws ClassNotFoundException, IOException {
		int x=4,y=4;
		Czworki gra = new Czworki();
	
		String[][] plansza = gra.Setup(x, y);
		gra.Print(plansza);
		String actual = outContent.toString();
		String expected = printToString(x,y);
		assertThat(actual,is(equalTo(expected)));
	}
	*/
	@ParameterizedTest
	@CsvSource({ "0", "1" })
	void testMove(int player) throws ClassNotFoundException, IOException {
		System.out.println("\nMove test nr. "+(player+1)+"");
		Czworki gra = new Czworki();
		gra.plansza = gra.Setup(6, 7);
		
		//setUpStreams();
		int state = gra.Move(gra.plansza, player, 1);
		//restoreStreams();
		gra.Print(gra.plansza);
		assertThat(gra.plansza[5][0], is(not(equalTo("-"))));
	}
	
	@Test
	void testCheckWinHorizontal() throws ClassNotFoundException, IOException {
		Czworki gra = new Czworki();
		gra.plansza = gra.Setup(6, 7);
		for(int i=0;i<4;i++) 
			gra.plansza[2][i] = "x";
		
		int actual = gra.CheckWin();
		
		assertThat(actual, is(equalTo(1)));
	}
	
	@Test
	void testCheckWinVertical() throws ClassNotFoundException, IOException {
		Czworki gra = new Czworki();
		gra.plansza = gra.Setup(6, 7);
		for(int i=5;i>1;i--) 
			gra.plansza[i][0] = "x";
		
		int actual = gra.CheckWin();
		
		assertThat(actual, is(equalTo(1)));
	}
	
	@Test
	void testCheckWinDiagonal() throws ClassNotFoundException, IOException {
		Czworki gra = new Czworki();
		gra.width=7;
		gra.height=6;
		gra.plansza = gra.Setup(gra.height, gra.width);
		int counter=0;
		for(int i=5;i>1;i--) {
			for(int j=0;j<4;j++) {
				if(j==counter)
				gra.plansza[i][j] = "x";
			}
			counter++;
		}
		int actual = gra.CheckWin();
		
		assertThat(actual, is(equalTo(1)));
	}
	
	@Test
	void testCheckTie() throws ClassNotFoundException, IOException {
		Czworki gra = new Czworki();
		gra.plansza = gra.Setup(6, 7);
		
		for(int i=0;i<7;i++) {
			gra.plansza[0][i] = "x";
		}
		//setUpStreams();
		int actual = gra.Move(gra.plansza, 0, 1);
		//restoreStreams();
		
		assertThat(actual, is(equalTo(2)));
	}

	@ParameterizedTest
	@CsvSource({ "test1", "test2", "test3" })
	void testAddScore(String name) throws ClassNotFoundException, IOException {
		Highscores hs = new Highscores();
		Czworki gra = new Czworki();
		hs.read();
		boolean added = false;
		
		Highscores.highScores.put(name,0);
		gra.hs.save();
		gra.AddScore(name);
		if(Highscores.highScores.get(name) != null)
			added = true;
		gra.hs.save();
		
		assertTrue(added);
	}
	
	@ParameterizedTest
	@CsvSource({"0", "1"})
	void testChooseColor(int color) {
		Czworki gra = new Czworki();
		String c = Integer.toString(color);
		ByteArrayInputStream in = new ByteArrayInputStream(c.getBytes());
		
		System.setIn(in);
		gra.ChooseColor();
		System.setIn(System.in);
			
		assertThat(gra.player1,is(equalTo(color)));
	}

	@ParameterizedTest
	@CsvFileSource(resources = "resources/width.csv")
	void testChooseWidth(int width) {
		Czworki gra = new Czworki();
		String w = Integer.toString(width);
		ByteArrayInputStream in = new ByteArrayInputStream(w.getBytes());

		System.setIn(in);
		gra.ChooseWidth();
		System.setIn(System.in);
		
		assertThat(gra.width,is(equalTo(width)));
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "resources/height.csv")
	void testChooseHeight(int height) {
		Czworki gra = new Czworki();
		String h = Integer.toString(height);
		ByteArrayInputStream in = new ByteArrayInputStream(h.getBytes());

		System.setIn(in);
		gra.ChooseHeight();
		System.setIn(System.in);
		
		assertThat(gra.height,is(equalTo(height)));
	}
	
	@Test
	void testWin() throws ClassNotFoundException, IOException {
		Czworki gra = new Czworki();
		String name = "testWin";
		ByteArrayInputStream in = new ByteArrayInputStream(name.getBytes());

		System.setIn(in);
		setUpStreams();
		gra.Win();
		System.setIn(System.in);
		restoreStreams();
		String expected = "Twoja ilosc wygranych to "+gra.hs.highScores.get(name)+"!\n";
		
		assertThat(outContent.toString(),is(equalTo(expected)));
	}
	/*
	@Test
	void testMenu() throws ClassNotFoundException, IOException {
		Czworki gra = new Czworki();
		Highscores hs = new Highscores();
		//Scanner sc = new Scanner(System.in);
		//setUpStreams();
		gra.Menu();
		String data2 = "1 2"; System.setIn(new ByteArrayInputStream(data2.getBytes()));
		Hashtable<String,Integer> expected = gra.hs.highScores;
		//System.setIn(new ByteArrayInputStream("1".getBytes()));
		//restoreStreams();
		
		assertTrue(outContent.toString().contains(expected.toString()));
	}
	
	@Test
	void testGame() throws ClassNotFoundException, IOException {
		Czworki gra = new Czworki();
		gra.state=0;
		String wid = "7";
		String hei = "6";
		String player = "0";

		InputStream stdin = System.in;
		gra.Game(gra);
		try {
		  System.setIn(new ByteArrayInputStream(wid.getBytes()));
		  Scanner scanner = new Scanner(System.in);
		  System.out.println(scanner.nextLine());
		} finally {
		  System.setIn(stdin);
		}
	}
	*/
}
