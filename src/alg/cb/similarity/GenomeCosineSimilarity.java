package alg.cb.similarity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import alg.cb.casebase.Movie;

public class GenomeCosineSimilarity implements SimilarityMetric {
	
	public GenomeCosineSimilarity() {		
	}
	
	@Override
	public double calculateSimilarity(Movie m1, Movie m2) {
		
		Map<Integer,Double> movie1 = new HashMap<>(m1.getGenomeScores());
		Map<Integer,Double> movie2 = new HashMap<>(m2.getGenomeScores());
		
		double top = 0;
		double bottomMovie1 = 0;
		double bottomMovie2 = 0;
		
		// we know there are only 1128 genomes currently but want to make this more robust in case this changes in future.
		Set<Integer> genomes = new HashSet<>();
		genomes.addAll(movie1.keySet());
		genomes.addAll(movie2.keySet());
		
		for (Integer tag: genomes) {
			//We know that currently every movie has every tag but just in case one is missing adding in check.
			double tag1 = (double) ((movie1.containsKey(tag)) ? movie1.get(tag) : 0) ;
			double tag2 = (double) ((movie2.containsKey(tag)) ? movie2.get(tag) : 0) ;
			
			top += tag1 * tag2;			
			bottomMovie1 += Math.pow(tag1,2);
			bottomMovie2 += Math.pow(tag2,2);
		}
		
		return top/(Math.sqrt(bottomMovie1*bottomMovie2));
	}
	
}
