import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;


@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class Mockito {
	
	List<Move> steps = mock(List.class);
	FakeMovesCollection fakes;
	int player;
	int pos_x;
	int pos_y;
	int round;
	char color;
	
	@Mock
	MoveCollection move;
	
	@InjectMocks
	MongoDB moves = new MongoDB(fakes);
	
	@Test
	public void mockingWorksAsExpected (){
		Move movess=mock(Move.class);	
		
		doReturn(movess).when(move).getOne(1);
		assertThat(moves.getOne(1)).isEqualTo(movess);
	}
	
	@Test
	public void TestGetAll_ReturnCorrectList() {
		
		List<Move>correctList = new ArrayList<Move>();
		Move step1,step2,step3 = mock(Move.class);
		step1 = new Move(1,5,1,1,'x');
		step2 = new Move(2,5,2,2,'o');
		step3 = new Move(1,5,3,3,'x');
		correctList.add(step1);
		correctList.add(step2);
		correctList.add(step3);
		doReturn(correctList).when(move).getAll();

		assertThat(moves.getAll()).isEqualTo(correctList);
	}
	
	@Test
	public void TestGetAll_ReturnEmptyList() {

		List<Move> expected = mock(List.class);
		
		doReturn(expected).when(move).getAll();
		
		assertThat(moves.getAll()).isEqualTo(expected);
	}
	
	@Test
	public void TestInsert_ReturnInsertedMove () {
		Move step = mock(Move.class);
		step = new Move(1,5,1,100,'x');
		
		move.insert(step);
		doReturn(step).when(move).getOne(100);
		assertThat(moves.getOne(100)._round).isEqualTo(step._round);
	}
	
	@Test
	public void TestGetOne_ReturnCorrectNotNull () {
		Move step = mock(Move.class);
		step = new Move(1,5,1,100,'x');
		move.insert(step);
		
		doReturn(step).when(move).getOne(100);
		assertNotNull(moves.getOne(100));
		
	}
	
	@Test
	public void TestGetOne_ReturnCorrect () {
		Move step = mock(Move.class);
		step = new Move(1,5,1,100,'x');
		move.insert(step);
		
		doReturn(step).when(move).getOne(100);
		assertThat(moves.getOne(100)).isEqualTo(step);
	}
	
	@Test
	public void TestGetFirst_ReturnCorrect () {
		List<Move>correctList = new ArrayList<Move>();
		Move step1,step2,step3 = mock(Move.class);
		step1 = new Move(1,5,1,1,'x');
		step2 = new Move(2,5,2,2,'o');
		step3 = new Move(1,5,3,3,'x');
		correctList.add(step1);
		correctList.add(step2);
		correctList.add(step3);
		doReturn(step1).when(move).getFirst();

		assertThat(moves.getFirst()).isEqualTo(step1);
	}
	
	@Test
	public void TestGetLast_ReturnCorrect () {
		List<Move>correctList = new ArrayList<Move>();
		Move step1,step2,step3 = mock(Move.class);
		step1 = new Move(1,5,1,1,'x');
		step2 = new Move(2,5,2,2,'o');
		step3 = new Move(1,5,3,3,'x');
		correctList.add(step1);
		correctList.add(step2);
		correctList.add(step3);
		doReturn(step3).when(move).getLast();

		assertThat(moves.getLast()).isEqualTo(step3);
	}
}
