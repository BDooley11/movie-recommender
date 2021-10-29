package alg.cb.reader;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import alg.cb.casebase.Casebase;
import alg.cb.casebase.Movie;
import alg.cb.util.Matrix;

public class DatasetReader{
	
	private Casebase cb;
	private Matrix trainRatings;
	private Matrix testRatings;
	
	public DatasetReader(String movieFile, String genomeScoresFile, String ratingsFile){
		readCasebase(movieFile, genomeScoresFile, ratingsFile);
	}
	
	public DatasetReader(String movieFile, String genomeScoresFile,String trainRatingsFile, String testRatingsFile) {
		readCasebase(movieFile, genomeScoresFile, trainRatingsFile);
		trainRatings = readUserRatings(trainRatingsFile);
		testRatings = readUserRatings(testRatingsFile);
	}
	
	public Matrix getTrainRatings() {
		return trainRatings;
	}
	
	public Matrix getTestRatings() {
		return testRatings;
	}
	
	public Casebase getCasebase() {
		return cb;	
	}
		
	private Matrix readUserRatings(String ratingsFile) {
		Matrix UserRatingsMatix = new Matrix();
		File UserRatings = new File(ratingsFile);
		
		// Declare a Scanner reference variable
		Scanner input = null;
		try {
			// Create a Scanner for the file
			input = new Scanner(UserRatings);

			// Read data from the file
			while (input.hasNext()) { // returns true if the scanner has more data to be read
				String line = input.nextLine();
				StringTokenizer st = new StringTokenizer(line, ",");
				
				while (st.hasMoreTokens()) {	
					int userID = Integer.valueOf(st.nextToken());
					int movieID = Integer.valueOf(st.nextToken());
					double rating = Double.valueOf(st.nextToken());
					
					UserRatingsMatix.addValue(userID, movieID, rating);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		} finally {
			// Close the Scanner instance
			if (input != null)
			input.close();
		}		
		return UserRatingsMatix;
	}
	
	private void readCasebase(String movieFile, String genomeScoresFile, String ratingsFile) {
		Matrix genomeScores = readGenomeScores(genomeScoresFile);
		Matrix ratings = readMovieRatings(ratingsFile);
		
		cb = new Casebase();
		File movie = new File(movieFile);
		
		// chapter 12 example 2 ReadData.java
		Scanner input = null;
		try {
			// Create a Scanner for the file
			input = new Scanner(movie);

			// Read data from the file
			while (input.hasNext()) { // returns true if the scanner has more data to be read

				// this logic on the split is to keep it as simple as possible. For the most part there will be 3 tokens.
				// id, title/year and genres. In some cases a title/year may contain a ',' which is the only special case
				// I need to allow for. Want to keep initial split as minimal as possible so fewer special cases I need to deal with.
				String line = input.nextLine();
				StringTokenizer st = new StringTokenizer(line, ",");
				
				while (st.hasMoreTokens()) {			
					// first token will always be ID
					int id = Integer.valueOf(st.nextToken());
					
					// second token will be title and year combined, unless title contained ',' which the below if statement deals with.
					String title = st.nextToken().trim();
					String yearString;
					int year;
					
					// if title begins with '"' then enter while loop adding next token until it ends with '"' too.
					// this ensures we now have the full title and year string.
					// The final statements removes the '"' from the beginning and end of the string.
					if (title.startsWith("\"")) {
						while (!title.endsWith("\""))
							title = (title + "," + st.nextToken()).trim();
						title = title.substring(1, title.length() - 1).trim();
					}
								
					// final formatting. yearString will be last 6 characters of title. NOTE:this will include ()
					// title is then trimmed to exclude the last 6 characters which is the year.
					// year is then parsed to an int and the () removed.
					yearString = title.substring(title.length() - 6);
					title = title.substring(0, title.length() - 6).trim();
					year = Integer.valueOf(yearString.substring(1, yearString.length() - 1));
										
					//could have split by '|' initially and shorten this section however this would cause an issue
					// if a title ever contained a '|' which they don't in this case but still want to keep it robust.
					StringTokenizer genreSplit = new StringTokenizer(st.nextToken(), "|");
					Set<String> genres = new HashSet<>();
					while (genreSplit.hasMoreTokens()) {
						String word = genreSplit.nextToken().toLowerCase().trim();
						if (!word.contains("imax"))
							genres.add(word);
					}
						
					Movie movieObject = new Movie(id,title,year,genres, genomeScores.getRow(id), ratings.getRow(id));
					cb.addMovie(id,movieObject);
				}			
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		} finally {
			// Close the Scanner instance
			if (input != null)
				input.close();
		}	
	}	
	
	private Matrix readGenomeScores(String genomeScoresFile) {
		Matrix genomeMatix = new Matrix();
		File genomeScores = new File(genomeScoresFile);
		
		// Declare a Scanner reference variable
		Scanner input = null;
		try {
			// Create a Scanner for the file
			input = new Scanner(genomeScores);

			// Read data from the file
			while (input.hasNext()) { // returns true if the scanner has more data to be read
				String line = input.nextLine();
				StringTokenizer st = new StringTokenizer(line, ",");
				
				while (st.hasMoreTokens()) {	
					int movieID = Integer.valueOf(st.nextToken());
					int tagID = Integer.valueOf(st.nextToken());
					double tagRelevance = Double.valueOf(st.nextToken());
					
					genomeMatix.addValue(movieID, tagID, tagRelevance);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		} finally {
			// Close the Scanner instance
			if (input != null)
			input.close();
		}
		return genomeMatix;
	}
		
	private Matrix readMovieRatings(String ratingsFile) {
		Matrix ratingsMatix = new Matrix();
		File ratings = new File(ratingsFile);
		
		// Declare a Scanner reference variable
		Scanner input = null;
		try {
			// Create a Scanner for the file
			input = new Scanner(ratings);

			// Read data from the file
			while (input.hasNext()) { // returns true if the scanner has more data to be read
				String line = input.nextLine();
				StringTokenizer st = new StringTokenizer(line, ",");
				
				while (st.hasMoreTokens()) {	
					int userID = Integer.valueOf(st.nextToken());
					int movieID = Integer.valueOf(st.nextToken());
					double rating = Double.valueOf(st.nextToken());
					
					ratingsMatix.addValue(movieID, userID, rating);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		} finally {
			// Close the Scanner instance
			if (input != null)
			input.close();
		}		
		return ratingsMatix;
	}
		
}

