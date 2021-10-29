package alg.cbp.recommender;

import java.util.Set;
import java.util.List;

import alg.cb.casebase.Casebase;
import alg.cb.casebase.Movie;
import alg.cb.similarity.ConfidenceSimilarity;
import alg.cb.similarity.PopularitySimilarity;
import alg.cb.similarity.SentimentSimilarity;
import alg.cb.similarity.SimilarityMetric;
import alg.cb.util.Matrix;

public abstract class PRecommender {
	
	private Casebase cb;
	private Matrix similarities;
	
	protected PRecommender(Casebase cb, SimilarityMetric metric) {
		this.cb = cb;
		
		// Calculate the pairwise similarities between all movies
		similarities = new Matrix();

		// Get all movies
		Object[] movies = cb.getMovies().values().toArray();

		// Calculate the pairwise similarities between all movies
		for (int i = 0; i < movies.length; i++)
			for (int j = i + 1; j < movies.length; j++) {
				Movie m1 = (Movie)movies[i];
				Movie m2 = (Movie)movies[j];

				if (metric instanceof ConfidenceSimilarity || 
						metric instanceof PopularitySimilarity ||
						metric instanceof SentimentSimilarity) { // similarity is not symmetric
					double sim = metric.calculateSimilarity(m1, m2);
					if(sim > 0) similarities.addValue(m1.getId(), m2.getId(), sim);

					sim = metric.calculateSimilarity(m2, m1);
					if (sim > 0) similarities.addValue(m2.getId(), m1.getId(), sim);	
				} 
				else { // similarity is symmetric
					double sim = metric.calculateSimilarity(m1, m2);
					if(sim > 0) {
						similarities.addValue(m1.getId(), m2.getId(), sim);
						similarities.addValue(m2.getId(), m1.getId(), sim);		
					} 
				}
			}
	}
	
	public Casebase getCasebase() {
		return cb;	
	}
	
	public double getSimilarity(Movie m1, Movie m2) {
		return similarities.getValue(m1.getId(), m2.getId());	
	}
	
	public abstract List<Movie> getRecommendations(Set<Movie> targetMovies);

}
