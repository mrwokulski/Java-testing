import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Czworki {
	
	public static int height=6;
	public static int width=7;
	public static int player1;
	public static int player2;
	public static String[][] plansza;
	public int state=0;
	public Highscores hs = new Highscores();
	
	public String[][] Setup(int x, int y) throws ClassNotFoundException, IOException {
		String[][] plansza= new String[x][y];
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) plansza[i][j] = "-";
		}
		return plansza;
	}
	
	public void Print(String[][] plansza) {
		for(int i=0; i<height; i++) {
			System.out.print("\n");
			for(int j=0; j<width;j++) {
				System.out.print(plansza[i][j]+" ");
			}
		}
	}
	
	public int Move(String[][] plansza, int player, int col) {
		int rows = plansza.length;
		int state=0;
		int counter=0;
		
		for(int i=rows-1; i >= 0; i--) {
			if(plansza[i][col-1] == "-") {
				if(player == player1)	
					plansza[i][col-1] = "x";
				else 
					plansza[i][col-1] = "o";
				System.out.println("Ruch gracza "+(player+1)+" na ["+col+"]");
				
				for(int j=0;j<width;j++) {
					if(plansza[0][j] != "-") 
						counter++;
				}
				if(counter == width) {
					return state=2;
				}
				
				state = CheckWin();
				return state;
			}
		}
		return state;
	}
	
	public int CheckWin() {
		//for kazdy kierunek for dla kazdego elementu - sprawdz czy 3 nastepne elem istnieja i sa takie same
		int[][] directions = {{1,0}, {1,-1}, {1,1}, {0,1}};
	    for (int[] d : directions) {
	        int dx = d[0];
	        int dy = d[1];
	        for (int i = 0; i < height; i++) {
	            for (int j = 0; j < width; j++) {
	                int lastx = i + 3*dx;
	                int lasty = j + 3*dy;
	                if (0 <= lastx && lastx < height && 0 <= lasty && lasty < width) {
	                    String w = plansza[i][j];
	                    if (w != "-" && w == plansza[i+dx][j+dy] 
	                                 && w == plansza[i+2*dx][j+2*dy] 
	                                 && w == plansza[lastx][lasty]) {
	                        return 1;
	                    }
	                }
	            }
	        }
	    }
		return 0;
	}
	
	public void ChooseColor() {
		Scanner sc = new Scanner(System.in);
		System.out.println("-Wybierz kolor dla gracza pierwszego-\nCzerwony : 0\nZielony : 1");
		player1 = sc.nextInt();
		if(player1 == 0 || player1 == 1) {
			if(player1 == 0)
				player2 = 1;
			else 
				player2 = 0;
		} else {
			System.out.println("Nieprawidłowy kolor! Ustawiono kolor czerwony dla gracza 1.!");
			player1 = 0;
			player2 = 1;
		}
	}
	
	public void ChooseWidth() {
		Scanner sc = new Scanner(System.in);
		width=sc.nextInt();
	}
	
	public void ChooseHeight() {
		Scanner sc = new Scanner(System.in);
		height=sc.nextInt();
	}
	
	public void Game() throws ClassNotFoundException, IOException {
		Scanner sc = new Scanner(System.in);
		int winner=-1;
		int counter=0;
		
		System.out.println("-Podaj wielkość planszy- \nWysokość: ");
		ChooseHeight();
		System.out.println("\nSzerokość: ");
		ChooseWidth();
		try {
		plansza = Setup(height,width);
		} catch (NegativeArraySizeException e) {
			System.out.println("Wprowadz liczby od 1 do 100!");
			Game();
		}
		ChooseColor();
		
		while(state == 0) {
			int move=0;
			System.out.println("\n\n\n[NOWA TURA]");
			Print(plansza);
			
			for(int i=0;i<2;i++) {
				if(state == 0) {
					System.out.println("\n\nRuch "+	(i+1) +" gracza, podaj kolumne: (1-"+width+")");
					try {
					move = sc.nextInt();
					if(plansza[0][move-1] == "-") 
						state = Move(plansza, i, move);
					else {
						System.out.println("Kolumna "+ move +" jest pełna, wybierz inną.");
						System.out.println("\nPodaj wolną kolumne: ");
						Print(plansza);
						int newmove = sc.nextInt();
						state = Move(plansza, i, newmove);
					}
					} catch (ArrayIndexOutOfBoundsException | InputMismatchException e) {
						System.out.println("Ruch może się odbyć od kolumny 1 do "+width+" uzywajac jedynie cyfr!");
					}
					Print(plansza);
					
					if(state != 0) {
						if(state == 1) {
							if(i==0)
								winner = 1;
							else
								winner = 2;
						}
						if(state == 2)
							System.out.println("\nGra zakończona remisem - wszystkie pola zapełnione");
						continue;
					}
				}
			}
		}
		if(state == 1) {
			System.out.println("\n------------------------\n-------------KONIEC GRY!");
			System.out.println("\nGracz "+winner+" wygral! Podaj swoje imie: ");
			Win();
		}
		if(state == 2) {
			System.out.println("\n------------------------\n-------------KONIEC GRY!");
			System.out.println("\nGra zakonczona remisem!");
		}
	}
	
	public void Win() throws ClassNotFoundException, IOException {
		Scanner sc = new Scanner(System.in);
		String name = sc.next();
		AddScore(name);
		System.out.println("Twoja ilosc wygranych to "+hs.highScores.get(name)+"!");
	}
	public void AddScore(String name) throws ClassNotFoundException, IOException {
		int count=0;
		if (Highscores.highScores.get(name) == null)
			count = 1;
		else {
			count = Highscores.highScores.get(name);
			count++;
		}
		Highscores.highScores.put(name, count);
		hs.save();
	}
	
	public void Menu() throws ClassNotFoundException, IOException {
		Scanner sc = new Scanner(System.in);
		hs.read();
		
		System.out.println("\n1- Wyświetl wyniki\n2- Rozpocznij grę\n3 - Zakończ");
		int option = sc.nextInt();
		if(option == 1) {
			hs.print();
			Menu();
		} else if(option == 2) {
			Game();
		} else if(option == 3){
			
		} else {
			System.out.println("Podano zły wybór opcji, spróbuj ponownie!");
			Menu();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Czworki gra = new Czworki();
		gra.Menu();
	}
}
