
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.hamcrest.core.Is;

public class FakeMovesTest {
	
	IMoveService service;
	List<Move> move;

    @BeforeEach
    public void setUp(){
        service = new FakeMovesCollection();
        Move step1 = new Move(1, 5, 0, 1 , 'x');
        Move step2 = new Move(2, 5, 1, 2 , 'o');
        Move step3 = new Move(1, 5, 2, 3 , 'x');
        service.insert(step1);
        service.insert(step2);
        service.insert(step3);
    }
    //i+1, posx, posy, round, color
    @Test
    public void testGetAllIsEmpty() { 
    	service.delete();
    	assertThatThrownBy(() -> { service.getAll();}).isInstanceOf(NullPointerException.class);
    }
	
	

}
