/** Defines an object representing a single fitness class
 */
public class FitnessClass implements Comparable<FitnessClass> {
    
	// Declaring the instance variables
	private String classId;
    private String className;
    private String tutorName;
    private int startTime;
    private int [] attendance;
    private int num_of_weeks = 5;
    
    
    // Default Constructor to instantiate array
    public FitnessClass() {
    	super();
    	attendance = new int [num_of_weeks];
    	
    }
    
    
    
    
   // Non-default Constructor to set the parameters
    public FitnessClass(String cId, String cName, String tName, int sTime) {
    	
    	setClassId(cId);
    	setClassName(cName);
    	setTutor(tName);
    	setStartTime(sTime);
    	
    	attendance = new int [num_of_weeks];
    }
    
    
    
    // Modifier Methods
    public void setClassId(String id) {						
    	classId = id;
    }
    public void setClassName(String clName) {
    	className = clName;
    }
    public void setTutor(String tutor) {
    	tutorName = tutor;
    }
    public void setStartTime(int start) {
    	startTime = start;
    }
    public void setAttendance(int [] attend) {
    	attendance = attend;
    }
    
    
    
    // Accessor Methods
    public String getClassId() {
    	return classId;
    }
    public String getClassName() {
    	return className;
    }
    public String getTutor() {
    	return tutorName;
    }
    public int getStartTime() {
    	return startTime;
    }
    public int [] getAttendance() {
    	return attendance;
    }

    
    
    // Method for calculating average attendance
    public double getAvgAttendance() {
    	double total = attendance[0] + attendance[1] + attendance[2] + attendance[3] + attendance[4];
    	double average = total/num_of_weeks;
    	
    	return average;
    	}
    
    
    
    
    
    
    // Method to properly format attendance report.
    // It returns a string containing the classes' id, name, tutor, attendances, and average attendance.
    public String formatReport() {
    	
        String att1 = "" + attendance[0];
		String att2 = "" + attendance[1]; 
		String att3 = "" + attendance[2]; 
		String att4 = "" + attendance[3];
		String att5 = "" + attendance[4];
		
		
		double avgAttendance = this.getAvgAttendance();
		
		
		String classDetails = String.format("   %-3s  %-15s  %-15s  %-3s  %-3s  %-3s  %-3s  %-10s  %-15.2f", classId, className, tutorName, att1, att2, att3, att4, att5, avgAttendance);
		
		return classDetails;
    } 
    
    
    // Method for comparing FitnessClass objects appropriately on average attendance
    public int compareTo(FitnessClass other) {
    	
    
    	if (this.getAvgAttendance() < other.getAvgAttendance()) {
    		return 1;
    		}
    	else if (this.getAvgAttendance() == other.getAvgAttendance()) {
    		return 0;
    	}
    	else return -1;  
    }  
    	
    
    
    
    
    
}
