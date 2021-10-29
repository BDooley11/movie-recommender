package alg.cb.similarity;

import java.util.HashSet;
import java.util.Set;

import alg.cb.casebase.Movie;

public class GenreJaccardSimilarity implements SimilarityMetric {
		
	public GenreJaccardSimilarity() {		
	}
		
	@Override
	public double calculateSimilarity(Movie m1, Movie m2) {
			
		// consider checking which Ratings Set is bigger as would be more efficient to iterate over
		// smaller one but insignificant in this scenario. Set overlap equal m1 genres and then retain all m2 genres in common.
		Set<String> overlap = new HashSet<>(m1.getGenres());
		overlap.retainAll(m2.getGenres());
			
		// store as type double rather than int to avoid unintended int division.
		double intersection = overlap.size();
		double bottom = m1.getGenres().size() + m2.getGenres().size() - intersection;
		
		return intersection/bottom;
	}
}
