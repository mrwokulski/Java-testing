import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MoveCollection implements IMoveService {

	public MongoCollection moves;
		
	public MoveCollection () throws UnknownHostException {
		@SuppressWarnings({ "deprecation", "resource" })
		DB db = new MongoClient().getDB("steps");
		moves = new Jongo(db).getCollection("steps");
	}
	
	@Override
	public List<Move> getAll() {
		List<Move> all = new ArrayList<Move>();
		MongoCursor<Move> cursor = moves.find().as(Move.class);
		
		if(cursor == null)
			return Collections.emptyList();
		for(Move m : cursor) {
			all.add(m);
		}
		return all;
	}

	@Override
	public Move getOne(int _move) {
		Move move = moves.findOne("{_id: #", _move).as(Move.class);
		return move;
	}

	@Override
	public void insert(Move move) {
		
		if(move._pos_x < 0) 
			throw new IllegalArgumentException("Wrong posx.");
		else if(move._pos_y < 0) 
			throw new IllegalArgumentException("Wrong posy.");
		else 
			moves.insert(move);
	}

	@Override
	public void delete() {
		moves.drop();
		
	}
	@Override
	public Move getFirst() {
		Move move = moves.findOne().as(Move.class);
		return move;
	}
	@Override
	public Move getLast() {
		Move move = moves.findOne("{_id: #", moves.count() ).as(Move.class);
		return move;
	}


}
