package a4;
import java.util.*;

/**
 * 	Program to display and modify a simple DVD collection
 */

public class DVDManager {

	public static void main(String[] args) {
		
		
		DVDUserInterface dlInterface = new DVDGUI();
		dlInterface.processCommands();
		
	}

}
