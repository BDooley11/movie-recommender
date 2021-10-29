/**
 * A class to evaluate a personalised recommender.
 */

package alg.cbp.evaluator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import alg.cb.casebase.Movie;
import alg.cb.util.Matrix;
import alg.cbp.recommender.PRecommender;

public class PEvaluator {
	private double threshold; // Movies with ratings >= threshold are considered liked by users
	private Map<Integer,List<Movie>> recommendations; // Stores the recommendations made for each user
	private Matrix testRatings;  // Stores the test ratings for each user

	/**
	 * constructor - creates a new Evaluator object
	 * @param recommender - the recommender
	 * @param threshold - movies with ratings >= threshold are considered liked by users
	 * @param trainRatings - a matrix which stores user training ratings
	 * @param testRatings - a matrix which stores user test ratings
	 */
	public PEvaluator(PRecommender recommender, double threshold, Matrix trainRatings, Matrix testRatings) {
		this.threshold = threshold;
		this.testRatings = testRatings;
		recommendations = new HashMap<>();
		
		// Create a hash set to hold the target movies for the current user. The 
		// target movies are those movies in the training set which the user has 
		// liked (movies with ratings >= threshold are considered liked).
		for (int userId: trainRatings.getRowIds()) {
			Set<Movie> targetMovies = new HashSet<>();
			Set<Integer> candidateMovies = trainRatings.getColIds(userId);
			for (int movie: candidateMovies) {
				if(trainRatings.getValue(userId,movie) >= threshold) 
					targetMovies.add(recommender.getCasebase().getMovie(movie));
			}	
			// Get the recommendations for the current user based on the target movies
			// and add the recommendations to the map.
			recommendations.put(userId,recommender.getRecommendations(targetMovies));	
		}
	}

	/**
	 * @return the coverage which is given by the percentage of  
	 * users for which at least one recommendation can be made
	 */
	public double getCoverage() {
		int totalUsers = recommendations.size();
		int count = 0;
		
		Set<Integer> users = recommendations.keySet();
		for (int user: users) {
			if(recommendations.get(user).size() > 0)
				count++;
		}
		return count/totalUsers;
	}

	/**
	 * @param k - the number of recommendations to consider
	 * @return the average precision, recall, and F1 over all users for which at least one
	 * recommendation can be made
	 */
	public double[] getPRF1(int k) {
		// Implement this method
		double[] values = new double[3];
		Set<Integer> users = recommendations.keySet();
		double precision = 0.0;
		double recall = 0.0;
		double f1 = 0.0;
		
		// Precision
		for (int user: users) {	
			Set<Integer> recommendedMovies = new HashSet<>();		
			Set<Integer> testMovies = new HashSet<>();
			double intersection = 0.0;

			//get set of recommended movies for user
			for (int i = 0; i < recommendations.get(user).size() && i < k; i++) 
				recommendedMovies.add(recommendations.get(user).get(i).getId());

			// get a set of movies user has rated in test file.
			Set<Integer> movies = testRatings.getColIds(user);
			for (int m: movies) {
				// for each movie in test file check if rating given was greater than threshold. 
				if(testRatings.getValue(user,m) >= threshold) {
					testMovies.add(m);
				}
			}

			// calculate intersection
			for(int i: recommendedMovies) {
				if(testMovies.contains(i))
					intersection++;
			}
			
			if (testMovies.size() > 0) {
				double p = intersection/k;
				double r = intersection/testMovies.size();
				precision += p;
				recall += r;
				if( p+r > 0)
					f1 += (2*p*r)/(p+r);
			}
				
		}
		
		//get total number of users with at least one recommendation
		int totalUsers = 0;
		for (int user: users) {
			if(recommendations.get(user).size() > 0)
				totalUsers++;
		}
			
		// divide total relevant movies by number of users.
		values[0] = precision/totalUsers;
		values[1] = recall/totalUsers;
		values[2] = f1/totalUsers;
			
		return values;
	}
}
