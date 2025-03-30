package a4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;
/**
 *  This class is an implementation of DVDUserInterface
 *  that uses JOptionPane to display the menu of command choices. 
 */

public class DVDGUI implements DVDUserInterface {
	 
	 private DVDCollection dvdlist;
	 
	 public DVDGUI()
	 {	 	
		 String fileName = null;
		 while(fileName == null || fileName.isEmpty()) {
			 fileName = JOptionPane.showInputDialog("Enter the name of data file to load");
			 if(fileName == null || fileName.isEmpty()) {
				JOptionPane.showMessageDialog(null, "File Name cannot be empty");	 
			 }
		 }
		 dvdlist = new DVDCollection();
		 dvdlist.loadData(fileName);
	 }
	 
	 public void processCommands()
	 {
		 JFrame mainFrame = new JFrame("DVD MANAGER");
		 mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 mainFrame.setSize(500,250);
		 mainFrame.setLocationRelativeTo(null);
		 mainFrame.setVisible(true);
		 
		 //the main panels to hold the buttons
		 JPanel mainPanel = new JPanel();
		 mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
		 
		 JButton viewButton = new JButton("View Collection");
		 JButton addModifyButton = new JButton("Add/Modify DVD");
		 JButton removeButton = new JButton("Remove DVD");
		 JButton byratingButton = new JButton("Get DVDs By Rating");
		 JButton totalRunTimeButton = new JButton("Get Total Running Time");
		 JButton exitButton = new JButton("Exit and Save");
		 JButton extraButton = new JButton("Recommend Movie");
		 
		 viewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doViewDVD();
			}
		 });
		 
		 addModifyButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					doAddOrModifyDVD();
				}
			 });
		 
		 removeButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					doRemoveDVD();
				}
			 });
		 
		 byratingButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					doGetDVDsByRating();
				}
			 });
		 
		 totalRunTimeButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					doGetTotalRunningTime();
				}
			 });
		 
		 exitButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					doSave();
				}
			 });
		
		 extraButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doExtraThing();
				
			}
		});
		 mainPanel.add(viewButton);
		 mainPanel.add(addModifyButton);
		 mainPanel.add(removeButton);
		 mainPanel.add(byratingButton);
		 mainPanel.add(totalRunTimeButton);
		 mainPanel.add(extraButton);
		 mainPanel.add(exitButton);
		 
		 mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
	 }
	private void doExtraThing() {
		String allMovieInfo = dvdlist.toString();
		Scanner txtScanner = new Scanner(allMovieInfo);
		//an array of movies
		ArrayList<DVD> dvds = new ArrayList<DVD>();
		while(txtScanner.hasNextLine()) {
			
			//extract just the movie name
			String lineString = txtScanner.nextLine();
			Scanner nameScanner = new Scanner(lineString);
			nameScanner.useDelimiter(",");
			String titleString = nameScanner.next();
			String ratingString = nameScanner.next();
			String runtTimeString = nameScanner.next();
			
			dvds.add(new DVD(titleString, ratingString, Integer.parseInt(runtTimeString)));
		}
		int size = dvds.size();
		System.out.println(size);
		Random random = new Random();
		int randomIndex = random.nextInt(size);
		
		DVD generatedDvd = dvds.get(randomIndex);
		JOptionPane.showMessageDialog(null, "We recommend you this movie: " + generatedDvd);
		movieDisplay(generatedDvd.getTitle(), generatedDvd.getRating(), String.valueOf(generatedDvd.getRunningTime()));
	}
	private void doViewDVD() {
		// TODO Auto-generated method stub
		String allMovieString = dvdlist.toString();
		//main panel to hold the dvds
		JFrame viewFrame = getMovieDisplayFrame(allMovieString, "DVD Collection");
	}
	
	
	
	private void doAddOrModifyDVD() {

		// Request the title
		String title = JOptionPane.showInputDialog("Enter title");
		if (title == null) {
			return;		// dialog was cancelled
		}
		title = title.toUpperCase();
		
		// Request the rating
		String rating = JOptionPane.showInputDialog("Enter rating for " + title);
		if (rating == null) {
			return;		// dialog was cancelled
		}
		rating = rating.toUpperCase();
		
		// Request the running time
		String time = JOptionPane.showInputDialog("Enter running time for " + title);
		if (time == null) {
			return;
		}
		
        // Add or modify the DVD (assuming the rating and time are valid
        dvdlist.addOrModifyDVD(title, rating, time);
                
        // Display current collection to the console for debugging
        String messageString = "Adding/Modifying: " + title;
                 
        //JPanel display = new JPanel(new GridLayout(4,1,0,0));
        //JLabel msg = new JLabel(messageString);
        JOptionPane.showMessageDialog(null, messageString);
        JOptionPane.showMessageDialog(null, "You will now see the updated Collection");

        JFrame display = getMovieDisplayFrame(dvdlist.toString(), "Updated Collection");
        display.setVisible(true);
                
		
	}
	
	private void doRemoveDVD() {

		// Request the title
		String title = JOptionPane.showInputDialog("Enter title");
		if (title == null) {
			return;		// dialog was cancelled
		}
		title = title.toUpperCase();
		
                // Remove the matching DVD if found
                dvdlist.removeDVD(title);
                
                // Display current collection to the console for debugging
                String messageString = "Movie removed from the collection: " + title;
                JOptionPane.showMessageDialog(null, messageString);
                JOptionPane.showMessageDialog(null, "You will now see the updated Collection");
          
                JFrame display = getMovieDisplayFrame(dvdlist.toString(), "Updated Collection");
                JButton closeButton = new JButton("close");
                //when the close button is pressed the current frame closes
                closeButton.addActionListener(new ActionListener() {
        			
        			@Override
        			public void actionPerformed(ActionEvent e) {
        				display.dispose();
        				
        			}
        		});
                display.getContentPane().add(closeButton, BorderLayout.PAGE_END);;
                display.setVisible(true);

	}
	
	private void doGetDVDsByRating() {

		// Request the rating
		String rating = JOptionPane.showInputDialog("Enter rating");
		if (rating == null) {
			return;		// dialog was cancelled
		}
		rating = rating.toUpperCase();
		
                String results = dvdlist.getDVDsByRating(rating);
                String messageString = "DVDs with rating " + rating;
           
                if(results == "" || results.isEmpty()) {
                	messageString = "No movies with the rating: " + rating;
                	JOptionPane.showMessageDialog(null, messageString);
                }
                else {
                    JOptionPane.showMessageDialog(null, "You will now see " + messageString);
                    String header = "Movies With Rating: " + rating;
                    JFrame display = getMovieDisplayFrame(results, header );
                    JButton closeButton = new JButton("close");
                    //when the close button is pressed the current frame closes
                    closeButton.addActionListener(new ActionListener() {
            			
            			@Override
            			public void actionPerformed(ActionEvent e) {
            				display.dispose();
            				
            			}
            		});
                    display.getContentPane().add(closeButton, BorderLayout.PAGE_END);;
                    display.setVisible(true);
                }
                

	}

    private void doGetTotalRunningTime() {
             
      int total = dvdlist.getTotalRunningTime();
      String  messageString = "Total Runtime: " + total + " mins";
      JOptionPane.showMessageDialog(null, messageString);	
                           
    }

	private void doSave() {
		
		dvdlist.save();
		//close the window
        System.exit(0);
		
	}
	//this is a method to display movie in the format: GenreImage name viewButton
	//View Button gives the MovieImage and movie description
	private void movieDisplay(String titleString, String ratingString, String runTimeString) {
		JFrame descriptionFrame = new JFrame("Movie Description");
		descriptionFrame.setSize(620, 270);
		descriptionFrame.setLocationRelativeTo(null);
		JPanel movieInfoPanel = new JPanel();
		movieInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		//when view pressed,
		//movie poster will be shown, larger image
		String imageName = titleString + ".jpg";
		
		//to check if the image exists for a given movie
		//if not a default image is used
		
		File myimage = new File(imageName);
		if(!myimage.exists()) {
			imageName = "mov.png";
		}
		
		ImageIcon movieImageIcon = new ImageIcon(imageName);
		Image scaledImage = movieImageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		
		//Jlabel for the image
		JLabel imageLabel = new JLabel(scaledIcon);
		movieInfoPanel.add(imageLabel);

		JLabel titleLabel = new JLabel("Title: " + titleString + " , ");
		JLabel ratingLabel = new JLabel("Rating: " + ratingString + " , ");
		JLabel runtimeLabel = new JLabel("Run Time: " + runTimeString + " mins");
	
	
		movieInfoPanel.add(titleLabel);
		movieInfoPanel.add(ratingLabel);
		movieInfoPanel.add(runtimeLabel);
		
		descriptionFrame.add(movieInfoPanel);
		
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,20));
		
		JButton editButton = new JButton("Edit");
		bottomPanel.add(editButton);
		//When edit button pressed,
		//get two options, edit rating or edit runtime
		editButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//dipose the intial frame where you clicked the edit cause youre gonna edit it now
				descriptionFrame.dispose();
				JPanel editorJPanel = new JPanel(new FlowLayout());
				String options[] = {"Edit Rating", "Edit Runtime"};
				String editMessageString = "Editing Info for " + titleString;
				int choice = JOptionPane.showOptionDialog(null,
						 editMessageString, 
						 "Editing " + titleString, 
						 JOptionPane.YES_NO_CANCEL_OPTION, 
						 JOptionPane.QUESTION_MESSAGE, 
						 null, 
						 options,
						 options[options.length - 1]);
				switch(choice) {
					case 0:
						String newRatingString  = JOptionPane.showInputDialog("Enter the new rating for " + titleString);
						dvdlist.addOrModifyDVD(titleString, newRatingString, runTimeString);
						dvdlist.save();
						String messageString = "Modifying the rating of " + titleString;
		                JOptionPane.showMessageDialog(null, messageString);
		                movieDisplay(titleString, newRatingString, runTimeString);
						break;
					case 1:
						String newRunTimeString = JOptionPane.showInputDialog("Enter the new run time for " + titleString);
						dvdlist.addOrModifyDVD(titleString, ratingString, newRunTimeString);
						dvdlist.save();
						String messageString1 = "Modifying the runtime of " + titleString;
		                JOptionPane.showMessageDialog(null, messageString1);
		                movieDisplay(titleString, ratingString, newRunTimeString);
						break;
					default:
						break;
				}
			}
		});
		//create a close button to dispose the frame
		JButton closeButton = new JButton("Close");
		bottomPanel.add(closeButton);
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				descriptionFrame.dispose();
				doViewDVD();
				
			}
		});
		descriptionFrame.getContentPane().add(bottomPanel, BorderLayout.PAGE_END);
		
		descriptionFrame.setVisible(true);
	}
	//get frame that displays list of movies
	private JFrame getMovieDisplayFrame(String allMovieString, String header) {
		Scanner txtScanner = new Scanner(allMovieString);
		//frame to hold the collection window
		JFrame viewFrame = new JFrame(header);
		viewFrame.setSize(700,500);
		viewFrame.setLocationRelativeTo(null);
		viewFrame.setVisible(true);
		JPanel mainPanel = new JPanel();
		viewFrame.add(mainPanel);
		LayoutManager layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(layout);
		
		while(txtScanner.hasNextLine()) {
			JPanel subPanel = new JPanel();
			LayoutManager subLayout = new GridLayout();
			subPanel.setLayout(subLayout);
			
			//extract just the movie name
			String lineString = txtScanner.nextLine();
			Scanner nameScanner = new Scanner(lineString);
			nameScanner.useDelimiter(",");
			String titleString = nameScanner.next();
			String ratingString = nameScanner.next();
			String runtTimeString = nameScanner.next();
			
			//for each movie
			// displayed as icon movie view edit
			String imageName = ratingString + ".png";
			ImageIcon movieImageIcon = new ImageIcon(imageName);
			Image scaledImage = movieImageIcon.getImage().getScaledInstance(75, 50, Image.SCALE_SMOOTH);
			ImageIcon scaledIcon = new ImageIcon(scaledImage);
			
			//Jlabel for the image
			JLabel imageLabel = new JLabel(scaledIcon);
			//adding to our panel
			subPanel.add(imageLabel);
			
			JLabel movieLabel = new JLabel(titleString);
			subPanel.add(movieLabel);
			
			//creating and adding the buttons to our panel
			JButton viewButton = new JButton("View");
			
			viewButton.setSize(4, 10);; // Width: 80, Height: 30
			//editButton.setPreferredSize(new Dimension(80, 30)); // Width: 80, Height: 30
			subPanel.add(viewButton);
			//subPanel.add(editButton);
			
			//the functionality of the buttons
			
			viewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//create a Panel that shows the full movie info
					viewFrame.dispose();
					movieDisplay(titleString, ratingString, runtTimeString);
					
				}
			});
			mainPanel.add(subPanel);
			
		}
		
        JButton closeButton = new JButton("close");
        //when the close button is pressed the current frame closes
        closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				viewFrame.dispose();
				
			}
		});
        viewFrame.getContentPane().add(closeButton, BorderLayout.PAGE_END);;
 
		return viewFrame;
		
	}	
}
