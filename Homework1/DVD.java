
public class DVD {

	// Fields:

	private String title;		// Title of this DVD
	private String rating;		// Rating of this DVD
	private int runningTime;	// Running time of this DVD in minutes

	public DVD(String dvdTitle, String dvdRating, int dvdRunningTime) 
	{
		this.title = dvdTitle;
		this.rating = dvdRating;
		this.runningTime = dvdRunningTime;
	
	}
	
	public String getTitle() 
	{
		
		return this.title; //return the title of the DVD
		
	}
	
	public String getRating() 
	{
		
		return this.rating; //return the rating of the DVD
		
	}
	
	public int getRunningTime() 
	{

		return this.runningTime; //return the running time
		
	}

	public void setTitle(String newTitle) {
		
		this.title = newTitle;	//set title of the DVD
		
	}

	public void setRating(String newRating) {


		this.rating = newRating;	//set the rating of the DVD


	}

	public void setRunningTime(int newRunningTime) {


		this.runningTime = newRunningTime;	//set the running time of the DVD


	}

	public String toString() {


		String dvdInfo = this.title + "," + this.rating + "," + this.runningTime;
		return dvdInfo; //return the string version of the DVD that has info
	}
	
	
}
