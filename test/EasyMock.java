import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(EasyMockRunner.class)
public class EasyMock {
	
	FakeMovesCollection fakes;
	
	@TestSubject
	MongoDB move = new MongoDB(fakes);
	
	//A nice mock expects recorded calls in any order and returning null for other calls
	@Mock(type = MockType.NICE)
	MoveCollection moves;
	
	@Test
	public void easyMockWorking(){
		
		Move step = new Move();
		//Zapisanie zachowania - co sie ma stac
		expect(moves.getOne(step._round)).andReturn(step);
		//Odpalenie obiektu do sprawdzenia zachowania
		replay(moves);
		assertThat(move.getOne(step._round)).isEqualTo(step);
	}
	
	@Test
	public void TestGetOne_ReturnValid () {

		Move step = new Move();

		expect(moves.getOne(step._round)).andReturn(step);
		replay(moves);
		
		assertThat(move.getOne(step._round)).isEqualTo(step);
	}
	
	@Test
	public void TestInsert_ListContainsInserted () {
		List<Move>correctList = new ArrayList<Move>();
		Move step1 = new Move(1,5,1,1,'x');
		Move step2 = new Move(2,5,2,2,'o');
		Move step3 = new Move(1,5,3,3,'x');
		correctList.add(step1);
		correctList.add(step2);
		correctList.add(step3);
		moves.insert(step1);
		moves.insert(step2);
		moves.insert(step3);
		
		expect(moves.getAll()).andReturn(correctList);
		replay(moves);
		
		assertThat(move.getAll()).isEqualTo(correctList);
	}
	
	@Test
	public void TestInsert_GetAllIsNullWhenIsEmpty( ) {

		List<Move>correctList = new ArrayList<Move>();
		expect(moves.getAll()).andReturn(correctList);
		replay(moves);
		 
		 assertThat(move.getAll().size()).isEqualTo(0);
	}
	
	@Test
	public void TestGetFirst_IsNullWhenListEmpty () {
		
		List<Move>correctList = new ArrayList<Move>();
		expect(moves.getAll()).andReturn(correctList);
		replay(moves);
		
		assertNull(move.getFirst());
	}
	
	@Test
	public void TestGetLast_IsNullWhenListEmpty () {
		
		List<Move>correctList = new ArrayList<Move>();
		expect(moves.getAll()).andReturn(correctList);
		replay(moves);
		
		assertNull(move.getLast());
	}
	
	@Test
	public void TestGetOne_ReturnsNullWhenNotFound () {
		List<Move>correctList = new ArrayList<Move>();
		Move step = new Move(1,5,0,1,'x');
		correctList.add(step);
		
		expect(moves.getAll()).andReturn(correctList);
		replay(moves);
		
		move.insert(step);
		assertNull(move.getOne(100));
	}
	
	@Test
	public void TestDelete_AfterIsNull() {
		
		moves.delete();
		expect(moves.getAll()).andReturn(null);
		assertNull(move.getAll());
	}
}
