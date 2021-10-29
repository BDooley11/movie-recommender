package alg.cbp.recommender;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import alg.cb.casebase.Casebase;
import alg.cb.casebase.Movie;
import alg.cb.similarity.SimilarityMetric;
import alg.cb.util.ScoredObjectDsc;

public class MeanPRecommender extends PRecommender {
	
	public MeanPRecommender(Casebase cb, SimilarityMetric metric) {
		super(cb, metric);
	}
	
	public List<Movie> getRecommendations(Set<Movie> targetMovies) {
		SortedSet<ScoredObjectDsc> ss = new TreeSet<>();
		Set<Integer> movieIds = getCasebase().getMovieIds();

		for (int m:movieIds) {
			double meanRelevance = 0;
			Movie candidate = getCasebase().getMovie(m);
			if(!targetMovies.contains(candidate)){
				for (Movie target: targetMovies) {
					double score = getSimilarity(target, candidate);
					meanRelevance += score;	
				}
			}
			if (meanRelevance > 0) {
				ScoredObjectDsc st = new ScoredObjectDsc(meanRelevance/targetMovies.size(), candidate);
				ss.add(st);	
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
