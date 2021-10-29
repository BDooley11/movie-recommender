package alg.cb.casebase;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Movie {
	
	private int id;
	private String title;
	private int year;
	private Set<String> genres;
	private Map<Integer,Double> genomeScores;
	private Map<Integer,Double> ratings;
	
	
	public Movie(int id, String title, int year, Set<String> genres, Map<Integer,Double> genomeScores, 
			Map<Integer,Double>ratings) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.genres = genres;
		this.genomeScores = genomeScores;
		this.ratings = ratings;
	}
	
	// Return the id
	public int getId() {
		return id;
	}

	// Return the title
	public String getTitle() {
		return title;
	}
	
	// Return the year
	public int getYear() {
		return year;
	}
	
	// Return the genres
	public Set<String> getGenres() {
		return genres;
	}
	
	// Return the GenomeScores (tag ID: relevant score)
	public Map<Integer,Double> getGenomeScores() {
		return genomeScores;
	}
	
	// Return the ratings (userID: rating)
	public Map<Integer,Double> getRatings() {
		return ratings;
	}
	
	// Return the mean rating result
	public double getMeanRating() {
		double sum = 0;
	
		Collection<Double> vals = ratings.values();
		for (Double v: vals)
			sum += v;
		return (ratings.size() > 0) ? sum / ratings.size() : 0;		
	}
	
	// Return a String representation 
	@Override
	public String toString() {
		return id + ", " + title + ", " + year + ", " + genres.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Movie))
			return false;
		Movie other = (Movie) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
		
}
