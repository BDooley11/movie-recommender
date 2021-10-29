package alg.cb.similarity;

import alg.cb.casebase.Movie;

public interface SimilarityMetric  {
	public abstract double calculateSimilarity(Movie m1, Movie m2);
}
