import java.rmi.UnknownHostException;
import java.util.Collections;
import java.util.List;


public class MongoDB {

	  
    public IMoveService service;
     
    public MongoDB() throws java.net.UnknownHostException {
    	
      service = new MoveCollection();
    }
   
    public MongoDB(FakeMovesCollection fake) {
    	
        service = fake;
    }
   
    public List<Move> getAll() {
    	
        return service.getAll();
    }
    
    public Move getOne(int round) {
    	
    	return service.getOne(round);
    }
   
    public void insert(Move move) {
    	
		if(move._pos_x < 0) 
			throw new IllegalArgumentException("Wrong posx.");
		else if(move._pos_y < 0) 
			throw new IllegalArgumentException("Wrong posy.");
		else 
			service.insert(move);
	}
    
    public void delete() {
    	
        service.delete();
    }
   
	
    public Move getFirst() {
    	
		return service.getFirst();
	}
	
    public Move getLast() {
    	
    	return service.getLast();
	}
}
