package alg.cb.similarity;

import alg.cb.casebase.Movie;

// chose to combine these two as they had highest coverage and relatively high relevance.
// popularity has high relevance but just recommends the popular movies and has poor coverage so not used.
// chose this approach over quality as quality is subjective so rather than take an overall approach
// which may negate a movie due to a small total ratings which are negative. Using RatingsCosine is better I think. 
public class GenomeRatingsSimilarity implements SimilarityMetric{
	
	public GenomeRatingsSimilarity() {		
	}

	@Override
	public double calculateSimilarity(Movie m1, Movie m2) {
		double alpha = 0.6;
		SimilarityMetric metric1 = new GenomeCosineSimilarity();
		double genome= metric1.calculateSimilarity(m1,m2);
		
		SimilarityMetric metric2 = new RatingsCosineSimilarity();
		double rating= metric2.calculateSimilarity(m1,m2);
		
		return (alpha *genome) + ((1-alpha)*rating);	
	}
}
