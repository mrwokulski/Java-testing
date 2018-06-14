import com.mongodb.client.MongoCollection; 
import com.mongodb.client.MongoDatabase; 

import org.bson.Document; 
import com.mongodb.MongoClient; 
import com.mongodb.MongoCredential; 

public class Move {
	 
	int _player;
	int _pos_x;
	int _pos_y;
	int _round;
	char _color;
	

	public Move (int player, int pos_x, int pos_y, int round, char color) {
		 _player = player;
		 _pos_x = pos_x;
		 _pos_y = pos_y;
		 _round = round;
		 _color = color;
	};
	
	public Move () {};
	
}
