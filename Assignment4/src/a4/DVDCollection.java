package a4;
import java.io.*;
import java.util.Scanner;

public class DVDCollection {

	// Data fields
	
	/** The current number of DVDs in the array */
	private int numdvds;
	
	/** The array to contain the DVDs */
	private DVD[] dvdArray;
	
	/** The name of the data file that contains dvd data */
	private String sourceName;
	
	/** Boolean flag to indicate whether the DVD collection was
	    modified since it was last saved. */
	private boolean modified;
	
	//will store the file being worked on
	private static String fileName;
	
	//all the valid movie ratings in context of USA
	private static final String[] validRatings = {"G", "PG", "PG-13", "R", "NC-17" };
	/**
	 *  Constructs an empty directory as an array
	 *  with an initial capacity of 7. When we try to
	 *  insert into a full array, we will double the size of
	 *  the array first.
	 */
	public DVDCollection() {
		numdvds = 0;
		dvdArray = new DVD[7];
	}
	
	public String toString() {
		//comma separated not the "/"
		// Return a string containing all the DVDs in the
		// order they are stored in the array 

		String returnString = "";
		int totalDVD = this.numdvds;
		for(int i = 0 ; i < totalDVD; ++i) {
			returnString = returnString  + this.dvdArray[i].toString() +"\n";
		}
		
		return returnString;
	}

	public void addOrModifyDVD(String title, String rating, String runningTime) {
		
		//make sure the rating is valid, then validate the runtime
		if(validRating(rating)) {
			try {
				//if successfully converted to int means valid runtime
				//if not the catch block is executed meaning, invalid runtime
				int movieRunTime = Integer.parseInt(runningTime);
				
				//if movie present returns true and the method modifies it
				Boolean moviePresent = changeMovieFeat(title,rating, movieRunTime);
				//if movie not present, need to add
				if(!moviePresent) {
					//System.out.println("LoadData, adding movie to array entered");
					//check to see if the array is full
					if(numdvds == dvdArray.length) {
						//resize the array if the array if full

						this.resizeArray(dvdArray);
					}
					//create an instance of the DVD with the info provided
					DVD newDvd = new DVD(title, rating.toUpperCase(), movieRunTime);
					this.addMovie(newDvd);
				}
				//since modified, eitheir added or edited
				modified = true;
				
			}
			catch(Exception e){
				//e.printStackTrace();
				//not valid movie running time
			}
		}
	}
	
	public void removeDVD(String title) {
		Boolean deleted = false;
		String movieToDelete= title.toUpperCase();
		System.out.println(movieToDelete);
		for(int i = 0; i < numdvds; i++) {
			DVD currDvd = dvdArray[i];
			String currMovie = currDvd.getTitle().toUpperCase();
			//movie found
			if(currMovie.compareTo(movieToDelete) == 0) {
				//pull up all the elements up one position to overlap the deleted DVD
				for(int j = i; j < numdvds - 1; j++) {
					dvdArray[j] = dvdArray[j + 1];
				}
				deleted = true;
				break;
			}
		}
		if(deleted) {
			//reduce number or dvds
			//set modified to true
			--numdvds;
			modified = true;
		}

	}
	
	public String getDVDsByRating(String rating) {
		
		//check if the entered rating is valid
		if(validRating(rating)) {
			String movieString = "";
			
			//iterates through the array
			//each movie that matches the rating
			//added to the string	
			for(int i = 0; i < numdvds; i++) {
				
				DVD currDvd = dvdArray[i];
				if(currDvd.getRating().compareTo(rating) == 0) {
					movieString = movieString + currDvd.toString() +"\n";
				}
			}
			return movieString;
		}
		return "";
	}

	public int getTotalRunningTime() {
		
		int totalRunTime = 0;
		//iterate through each movie in the array
		//add the runtime
		for(int i = 0 ; i < numdvds; ++i) {
			totalRunTime += dvdArray[i].getRunningTime();
		}
		return totalRunTime;

	}

	
	public void loadData(String filename) {
		
		//setting up the fileName we'll be working on
		//for save
		DVDCollection.fileName = filename;
		File myFile = new File(filename);
		
		//if such file exists
		try {
			//if file exists, new file not created 
			//works on that file
			//if does not exist, new created
			if(!myFile.createNewFile()) {
				//take myFile as the argument to iterate through the file
				Scanner myScanner = new Scanner(myFile);
				
				//loop until end of file
				while(myScanner.hasNextLine()) {
					//read each line	
					String line = myScanner.nextLine();
					//scanner to parse the line
					Scanner lineParser = new Scanner(line);
					//attributes separated by commas
					//scanner reads till the "," or whitespace
					lineParser.useDelimiter(",");
					
					String movieName = "";
					String runTime = "";
					String rating = "";
					//this ensures that invalid entries where any attribute of
					//the DVD is missing is not added to the array
					if(lineParser.hasNext()) {
						movieName = lineParser.next().trim();
						if(lineParser.hasNext()) {
							rating = lineParser.next().trim();
							//validate the rating as well
							//if rating is valid, then only parse the runtime
							if(validRating(rating)) {
								if(lineParser.hasNext()) {
									runTime = lineParser.next().trim();
								}
							}	
						}
					}
					//if all attributes are present add it to the array
					if(!movieName.isBlank() && !rating.isBlank() && !runTime.isBlank()) {
						this.addOrModifyDVD(movieName, rating, runTime);
						//System.out.println("Movie info: " + movieName + " | " + rating + " | " + runTime);
					}
				}
				//myScanner.close();
		
			}
			//modified is false at the first load as
			//youre not really modyfying the file
			//but since you use addorModify method those set the modified to true
			//so explicitly setting it to false in the load
			//will only be true if anything added or removed from the list
			modified = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void save() {
		String myFile = DVDCollection.fileName;
		if(modified) {
			//create a write
			try {
				FileWriter myWriter = new FileWriter(myFile);
				myWriter.write(this.toString());
				myWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			//System.out.println(myFile + " is being saved!");
		}
		else {
			//System.out.println(myFile + " is being saved but no changes were made to the original file");
		}
		
		
	}
	//--------------------------HELPER METHODS ONLY-------------------------------------------------//
	
	
	// Additional private helper methods go here:
	
	//method to check whether a rating is valid or not
	private boolean validRating(String rating) {
		for(String r: DVDCollection.validRatings) {
			if(rating.toUpperCase().compareTo(r.toUpperCase()) == 0) {
				return true;
			}
		}
		return false;
	}
	
	//method to check if movie exists in the collection
	private Boolean changeMovieFeat(String movieTitle,String rating, int runtime ) {
		for(int i = 0 ; i < this.numdvds; ++i) {
			DVD currDvd = dvdArray[i];
			if(currDvd.getTitle().compareTo(movieTitle) == 0) {
				//if movie with the given title exists 
				//it is modified accordingly and then boolean is returned
				currDvd.setRating(rating);
				currDvd.setRunningTime(runtime);
				return true;
			}
		}
		//movie does not exist in the collection
		//returns false
		return false;
	}
	//if the array is full need to resize
	//double the size of the array
	private void resizeArray(DVD[] oldArray) {
		int oldSize = oldArray.length;
		int newSize = 2 * oldSize;
		
		DVD[] newDvdsArray = new DVD[newSize];
		//copy all the old items in the new array
		for(int i = 0; i < oldSize; ++i) {
			newDvdsArray[i] = oldArray[i];
		}
		
		this.dvdArray = newDvdsArray;
	}
	
	//insert movie in the correct spot
	private void addMovie(DVD newDVD) {
		//System.out.println("Add movie helper method entered, adding " + newDVD.getTitle());
		//if the array is empty, add it to the spot
		if(this.numdvds == 0) {
			dvdArray[this.numdvds] = newDVD;
			//System.out.println("First movie added");
			this.numdvds++;
		}
		//array is not empty
		else {
			Boolean added = false;
			for(int i = 0 ; i < numdvds; ++i) {
				//means the new movie title should be above the old one
				if(newDVD.getTitle().compareTo(dvdArray[i].getTitle()) < 0) {
					//we are going to drag the movies one step down to empty that space
					for(int j = numdvds; j > i; j--) {
						dvdArray[j] = dvdArray[j - 1];
					}
					//place the movie at the vacant space now
					dvdArray[i] = newDVD;
					added = true;
					this.numdvds++;
					break;
				}
			}
			//no movies were found that was larger lexically
			//so add as the last item of the list
			if(!added) {
				dvdArray[numdvds] = newDVD;
				this.numdvds++;
			}
		}
	}

	
}
