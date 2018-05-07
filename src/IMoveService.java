import java.util.List;

public interface IMoveService {
	List<Move> getAll();
	Move getOne(int round);
	void insert(Move move);
	void delete();
	Move getFirst();
	Move getLast();
}
