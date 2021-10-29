package alg.cb.casebase;
import java.util.*;

public class Casebase {
	
	private Map<Integer,Movie> cb;
	
	public Casebase() {
		cb = new HashMap<>();
	}
	
	public void addMovie(int id, Movie m) {
		cb.put(id, m);	
	}

	public Movie getMovie(int id) {
		return cb.get(id);
	}
	
	public Map<Integer,Movie> getMovies(){
		return cb;			
	}
	
	public Set<Integer> getMovieIds(){
		return cb.keySet();	
	}
	
	public int getNumberMovies() {
		return cb.size();		
	}
	
	
}
