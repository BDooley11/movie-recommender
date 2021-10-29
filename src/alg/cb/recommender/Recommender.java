package alg.cb.recommender;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import alg.cb.casebase.Casebase;
import alg.cb.casebase.Movie;
import alg.cb.similarity.SimilarityMetric;
import alg.cb.util.Matrix;
import alg.cb.util.ScoredObjectDsc;

public class Recommender {
	
	private Casebase cb;
	private Matrix similarities;
	
	public Recommender(Casebase cb, SimilarityMetric metric) {
		this.cb = cb;
		similarities = movieSimilarityMatrix(metric,cb);	
	}
	
	public static Matrix movieSimilarityMatrix(SimilarityMetric metric, Casebase cb) {
		Matrix similarities = new Matrix(); 
		Set<Integer> movieID = cb.getMovieIds();
		
		for (int id1: movieID) {
			for (int id2: movieID) {
				if(id1 != id2) {
					Movie m1 = cb.getMovie(id1);
					Movie m2 = cb.getMovie(id2);
					double sim = metric.calculateSimilarity(m1, m2);
					if(sim > 0)
						similarities.addValue(id1, id2, sim);				
				}
			}
		}				
		return similarities;
	}
	
	public Casebase getCasebase() {
		return cb;	
	}
	
	public List<Movie> getRecommendations(Movie target){
		SortedSet<ScoredObjectDsc> ss = new TreeSet<>();
		Set<Integer> movieIds = cb.getMovieIds();
		Integer targetID  = target.getId();
		
		for (int movieId: movieIds) {
			if(targetID != movieId) {
				double score = similarities.getValue(targetID,movieId);
				Movie movie = cb.getMovie(movieId);
				if(score > 0) {
					ScoredObjectDsc st = new ScoredObjectDsc(score, movie);
					ss.add(st);
				}
			}
		}

		List<Movie> recommendations = new ArrayList<>();
		for (Iterator<ScoredObjectDsc> it = ss.iterator(); it.hasNext(); ) {
			ScoredObjectDsc st = it.next();
			Movie m = (Movie)st.getObject();
			recommendations.add(m);
		}
		
		return recommendations;
	}
	
}
