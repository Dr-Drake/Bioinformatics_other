import java.util.*;

import javax.swing.JOptionPane;

import java.io.*;
import java.lang.reflect.Array;
/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */

public class FitnessProgram {
    
	// Declaring the instance variables
	private FitnessClass [] preFit;
	private FitnessClass [] fit;
	private FitnessClass fitClass;
	private final int classConstant = 7;
	
	
	// The Default constructor 
	public FitnessProgram() {
		preFit = new FitnessClass [classConstant];
		fit = new FitnessClass [classConstant];
		
	}
	
	
	
	
	
	// Method for building the list of fitness classes.
	// It takes a file name as an input.
	public void buildClassList(String x) {
		int t = 0;
		
		String [] classDetails = new String [4];			
		try{
			FileReader reader = new FileReader(x);  	
			
		
		try{
			Scanner in = new Scanner(reader);
			
		// The loop below reads each line from the file stores it in a variable called line.
		// It generates a fitnessClass object for each index in the fitnessClass array.
			while (in.hasNextLine() && t < classConstant) {
				String line = in.nextLine();
				classDetails = line.split(" ");				// This classDetails array stores each word from the line in an index
															
				
				String id = classDetails [0];				// Each word in each index is then stored in separate variables.
				String className = classDetails [1];		// These words are parameters for the fitnessClass object
				String tutorName = classDetails [2];
				int startTime = Integer.parseInt(classDetails [3]);
				
				
				
				fitClass = new FitnessClass(id, className, tutorName, startTime);
				
				preFit[t] = fitClass;
				t++;
				
				
				
				
			}
			
		}
		finally {
			reader.close();
		}
		}
		catch (Exception e){
			System.out.println("Exception found");
		}
		
		this.sortByTime();
	}
	
	
	// Helper method for sorting class by start time
	private void sortByTime() {
		int x = 0;
		
		// The getClassAtTime method is created below.
		// It returns a FitnessClass at a specific start time
		// It gets the FitnessClass from the preFit array
		// The fit array then stores FitnessClasses ordered by start time.
		for (int i = 0; i < classConstant; i++) {
			if (getClassAtTime(9+x) != null) {
				fit[i] = getClassAtTime(9+x);
			
			}
			x++;
		}
	}

	
	
	
	
	// Method for populating attendance list of a given class
	// The parameter is anticipated to be a line of text
	public void buildAttendance(String x) {
		String [] attendance = new String [6];        // This array stores each word from the line
		int [] altAttendance = new int [5];				// This array stores the attendance
		
		int j = 0;
		
		
				
				attendance = x.split(" ");
				
				int a = Integer.parseInt(attendance [1]);			// Starting from the second index, each word is anticipated 
				int b = Integer.parseInt(attendance [2]);			// to be a number representing the attendance.
				int c = Integer.parseInt(attendance [3]);			// Each of this is converted to an integer type.
				int d = Integer.parseInt(attendance [4]);
				int e = Integer.parseInt(attendance [5]);
				
				
				
				altAttendance [0] = a;				
				altAttendance [1] = b;
				altAttendance [2] = c;
				altAttendance [3] = d;
				altAttendance [4] = e;
				
				
				// In this loop, if the id of a class matches the id in the attendance file
				// the attendance of that class is updated accordingly.
				for (int i = 0; i < classConstant; i++) {
					if (fit[i] != null && fit[i].getClassId().equals(attendance[0])){
						fit[i].setAttendance(altAttendance);
						
						
					}	
				}
					
		}
	

	
	
	// Method to return fitnessClass array instance
	public FitnessClass[] getClassList() {
		return fit;
	}
	
	// Method to return a fitnessClass object from a particular index in the fitnessClass array.
	public FitnessClass getClass(int y) {	
		return fit[y];
		 }
	
	
	
	//Method to return number of classes in the List (number of fitnessClass objects in the array)
	public int getClassTotal() {
		int total = 0;
		for (int i = 0; i < classConstant; i++) {
			
			if (preFit[i] != null) {				
				total++;
			} 
			
		}
		return total;
	}
	
	
	
	// Method to return fitnessClass object with a given ID
	public FitnessClass getIdClass(String d) {
		
		for (int i = 0; i < classConstant; i++) {
			if (fit[i].getClassId().equals(d)) {
				return fit[i];
			}
		}
		
		
		return null;
	}
	
	
	
	// Method to return fitnessClass starting at a particular time
	public FitnessClass getClassAtTime(int t) {
		try {
		for (int i = 0; i < classConstant; i++) {
			if (preFit[i].getStartTime() == t) {
				return preFit[i];
			}
			
		}
		}
		catch (Exception e) {}
		return null;
	}
	
	
	
	// Method to return the first start time that is available for a class
	public int earliestFreeTime() {
		int [] times = {9, 10, 11, 12, 13, 14, 15};			// This array holds the valid times
		int x = 0;
		int [] freeTimes = new int [7];						// This array will hold the times that are available
		int available = 0;
		
		// This loop adds the available times to the freeTimes array
		for (int i = 0; i < classConstant; i++) {
			x = times[i];
			
			
			if (getClassAtTime(x) == null){
				freeTimes[i] = times[i];
				
			}
			
		}
		
		Arrays.sort(freeTimes);
		
		
		// This loop finds the earliest available time in the freeTimes array
		for (int j = 0; j < classConstant; j++) {
			if (freeTimes[j] != 0) {
				available = freeTimes[j];
				break;
			}
		}
		
		return available;
		
		}
	
	
	
	
	// Method for adding classes
	public void addClass(String id, String className, String tutor) {
		int earliestTime = earliestFreeTime();					// This variable stores the earliest available start time
		int [] attendance = new int [5];
		
		fitClass = new FitnessClass(id, className, tutor, earliestTime);
		fitClass.setAttendance(attendance);						// Here the attendances are set to 0.
		
		
		
	// This loop helps search for spaces on the list, and adds a FitnessClass in that space.
	
		for (int i = 0; i < classConstant; i++) {
			
			if (preFit[i] == null && getClassTotal() <= classConstant) {
				preFit[i] = fitClass;
				break;
			}
			
		}
		
		for (int j = 0; j < classConstant; j++) {
			if (fit[j] == null && getClassTotal() <= classConstant) {
				fit[j] = fitClass;
				break;
			}
		}
	}
	
	
	
	/** Method for preventing the addition of classes with the same id **/
	public boolean checkDuplication(String id) {
		
		for (int i = 0; i < classConstant; i++) {
			if (preFit[i].getClassId().equals(id)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	
	
	
	// Method for deleting classes
	public void deleteClass(String id) {
		
	try {	
	 // These loops help search for classes with an id matching the input.
	 // When found, it deletes this class from the list.
		for (int i = 0; i < classConstant; i++) {
			
			if (preFit[i].getClassId().equals(id)) {
				preFit[i] = null;
				break;
			}
		}
		
	}
	catch (NullPointerException e) {
		
		// If class in the array does not have the id entered in the text field, a null pointer exception is thrown.
		// Once caught, the user is informed with a warning message, that the class id entered doesn't exist.
		JOptionPane.showMessageDialog(null, "This ID does not exist", "Warning", JOptionPane.WARNING_MESSAGE);
		   return;
	}
		
		for (int j = 0; j < classConstant; j++) {
			
			if (fit[j] != null && fit[j].getClassId().equals(id)) {
				fit[j] = null;
				break;
			}
		}  
	}
	
	
	
	
	// Method to return a class list that is sorted in non-increasing order of the average attendances
	public FitnessClass[] sortClass() {
		
		int limit = getClassTotal();
		
		
	// This loop makes sure the preFit array has continuous non-null indexes, if 
	// class is deleted in array index before the last index
		for (int i = 0; i < classConstant - 1; i ++) {
			if (preFit[i] == null && i < 6) {
				preFit[i] = preFit[i+1];
				preFit[i+1] = null;
			}
		}
		Arrays.sort(preFit, 0, limit);			//preFit has the same contents as fit.
		return preFit;  						// However, preFit has continuous non-null indexes, and we can calculate the
												// number of these non-null objects using the getClassTotal method.
												// This total specifies the length of array to be sorted.
	}
	
	
	
	
	
	// Method to return overall average attendance
	public double getTotalAvgAtt(FitnessClass[] f) {
		double total = 0;
		int counter = 0;
		
		
	// This loop sums up the average attendance values for each fitness class
		for (int i = 0; i < classConstant; i++) {
			if (f[i] != null) {
				total = total + f[i].getAvgAttendance();
				counter++;
			}
		}
		double overallAvg = total/counter;
		
		return overallAvg;
	}
	
	
	
	// Method for displaying class Name of a fitness class at an index
	public String showClassName(int x) {
		try {
			String name = fit[x].getClassName();        // This variable stores the name of the fitness class at  index x
			return name;
		}
		catch (Exception e){
			String empty = "Available";
			return empty;
		}
		
		
	}
	
	
	
	// Method for displaying the tutor name of fitness class at an index
	public String showTutorName(int y) {
		try {
			String tutor = fit[y].getTutor();
			return tutor;
		}
		catch (Exception t) {
			String empty = " ";
			return empty;
		}
	}
	
	
}
	



