package alg.cb.similarity;

import java.util.Map;
import alg.cb.casebase.Movie;

public class ConfidenceSimilarity implements SimilarityMetric {

	private double threshold;

	public ConfidenceSimilarity(double threshold) {
		this.threshold = threshold;
	}

	@Override
	public double calculateSimilarity(Movie m1, Movie m2) {

		// Get the ratings for each movie
		Map<Integer,Double> r1 = m1.getRatings();
		Map<Integer,Double> r2 = m2.getRatings();

		// Return zero if either movie has no ratings
		if (r1 == null || r2 == null) 
			return 0;

		// Calculate the support of m1
		int suppM1 = 0;
		for (int id: r1.keySet())
			if (r1.get(id) >= threshold)
				suppM1++;

		// Calculate the support of (m1 and m2)
		int suppM1andM2 = 0;
		for(int id: r1.keySet()) 
			if (r2.containsKey(id)) {
				if (r1.get(id) >= threshold && r2.get(id) >= threshold)
					suppM1andM2++;		
			}
		
		// Return zero if division by zero occurs
		return (suppM1 > 0) ? suppM1andM2 * 1.0 / suppM1 : 0;
	}
}