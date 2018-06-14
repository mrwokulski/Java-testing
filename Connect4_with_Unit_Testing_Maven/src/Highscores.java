
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

public class Highscores {
	
	public static Hashtable<String, Integer> highScores = new Hashtable<String, Integer>();
	
	public String pathr = "highscores.table";
	
	public Highscores() {}
	
	public void save() throws IOException {
		FileOutputStream file = new FileOutputStream(pathr);
		ObjectOutputStream object = new ObjectOutputStream(file);
		
		object.writeObject(highScores);
	}
	
	public void read() throws IOException, ClassNotFoundException {
		try {
			FileInputStream file = new FileInputStream(pathr);
			ObjectInputStream object = new ObjectInputStream(file);
			highScores = (Hashtable<String, Integer>) object.readObject();
		} catch (IOException e) {
			System.out.println("Coś poszło nie tak - czytanie z pliku!");
			throw new IOException(e);
		}
	}
	
	public void print() {
		System.out.print(highScores);
	}
}

	
	

