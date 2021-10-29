package alg.cb.similarity;

import alg.cb.casebase.Movie;

public class GenomeQualitySimilarity implements SimilarityMetric{
	
	public GenomeQualitySimilarity() {		
	}

	@Override
	public double calculateSimilarity(Movie m1, Movie m2) {
		double alpha = 0.7;
		
		SimilarityMetric metric1 = new GenomeCosineSimilarity();
		double genome= metric1.calculateSimilarity(m1,m2);
		
		double qualityM2 = m2.getMeanRating() / 5.0;

		return (alpha *genome) + ((1-alpha)*qualityM2);	
		
	}

}
