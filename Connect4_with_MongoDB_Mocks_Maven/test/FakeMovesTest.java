
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
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
    
    @Test
    public void testGetAll_ReturnsListOfMoves () {
    	
    	List<Move> expected= new ArrayList<Move>();
    	Move step1 = new Move(1, 5, 0, 1 , 'x');
        Move step2 = new Move(2, 5, 1, 2 , 'o');
        Move step3 = new Move(1, 5, 2, 3 , 'x');
    	expected.add(step1);
    	expected.add(step2);
    	expected.add(step3);
    	
    	
    	assertThat((service.getAll()).size()).isEqualTo(expected.size());
    }
	
    @Test
    public void testGetOne_ReturnsCorrectMove () {
    	
    	List<Move> steps = new ArrayList<Move>();
    	Move step1 = new Move(1, 5, 0, 1 , 'x');

    	steps.add(step1);
    	Move expected = steps.get(0);
    	
    	assertEquals(expected._color, service.getOne(1)._color);
    	assertEquals(expected._pos_x, service.getOne(1)._pos_x);
    	assertEquals(expected._pos_y, service.getOne(1)._pos_y);
    	assertEquals(expected._round, service.getOne(1)._round);
    	assertEquals(expected._player, service.getOne(1)._player);
    }
    
    @Test
    public void testGetOne_WrongIdReturnsNull () {
    	
    	assertNull(service.getOne(100));
    }
    
    @Test
    public void testInsert_WrongPosX (){
    	
	    Move step = new Move(2, -1, 1, 4, 'o');
		assertThatThrownBy(() -> { service.insert(step);}).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void testInsert_WrongPosY (){
    	
	    Move step = new Move(2, 1, -1, 4, 'o');
		assertThatThrownBy(() -> { service.insert(step);}).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void testGetFirst_ReturnFirstFromList () {
    	
    	Move expected =  new Move(1, 5, 0, 1, 'x');
    	assertEquals(expected._color, service.getFirst()._color);
    	assertEquals(expected._pos_x, service.getFirst()._pos_x);
    	assertEquals(expected._pos_y, service.getFirst()._pos_y);
    	assertEquals(expected._round, service.getFirst()._round);
    	assertEquals(expected._player, service.getFirst()._player);
    }
    
    @Test
    public void testGetLast_ReturnLastFromList () {
    	
    	Move expected =  new Move(1, 5, 2, 3 , 'x');
    	assertEquals(expected._color, service.getLast()._color);
    	assertEquals(expected._pos_x, service.getLast()._pos_x);
    	assertEquals(expected._pos_y, service.getLast()._pos_y);
    	assertEquals(expected._round, service.getLast()._round);
    	assertEquals(expected._player, service.getLast()._player);
    }
    
    
}
