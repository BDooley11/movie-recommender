package alg.cb.similarity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import alg.cb.casebase.Movie;

public class RatingsCosineSimilarity implements SimilarityMetric {
	
	public RatingsCosineSimilarity() {		
	}
	
	@Override
	public double calculateSimilarity(Movie m1, Movie m2) {
		
		Map<Integer,Double> movie1 = new HashMap<>(m1.getRatings());
		Map<Integer,Double> movie2 = new HashMap<>(m2.getRatings());
		
		double top = 0;
		double bottomMovie1 = 0;
		double bottomMovie2 = 0;
		
		Set<Integer> ratings = new HashSet<>();
		ratings.addAll(movie1.keySet());
		ratings.addAll(movie2.keySet());

		for (Integer user: ratings) {
			double user1 = (double) ((movie1.containsKey(user)) ? movie1.get(user) : 0) ;
			double user2 = (double) ((movie2.containsKey(user)) ? movie2.get(user) : 0) ;
									
			top += user1 * user2;			
			bottomMovie1 += Math.pow(user1,2);
			bottomMovie2 += Math.pow(user2,2);
			
		}
		
		return top/(Math.sqrt(bottomMovie1*bottomMovie2));
	}

}
