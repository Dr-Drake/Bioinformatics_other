import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Defines a GUI that displays details of a FitnessProgram object
 * and contains buttons enabling access to the required functionality.
 */
public class SportsCentreGUI extends JFrame implements ActionListener {
	
	/** GUI JButtons */
	private JButton closeButton, attendanceButton;
	private JButton addButton, deleteButton;

	/** GUI JTextFields */
	private JTextField idIn, classIn, tutorIn;

	/** Display of class timetable */
	private JTextArea display;

	/** Display of attendance information */
	private ReportFrame report;

	/** Names of input text files */
	private final String classesInFile = "ClassesIn.txt";
	private final String classesOutFile = "ClassesOut.txt";
	private final String attendancesFile = "AttendancesIn.txt";
	
	/** Addition variables for creating class list and initiating attendances */
	private FitnessProgram fProg;
	
	
	/**
	 * Constructor for AssEx3GUI class
	 */
	public SportsCentreGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Boyd-Orr Sports Centre");
		setSize(700, 300);
		display = new JTextArea();
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		add(display, BorderLayout.CENTER);
		layoutTop();
		layoutBottom();
		// more code needed here
		
		report = new ReportFrame();
		fProg = new FitnessProgram();
		
	}

	/**
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay() {
	    
		fProg.buildClassList(classesInFile);
	    
	}

	/**
	 * Initialises the attendances using data
	 * from the file AttendancesIn.txt
	 */
	public void initAttendances() {

		
		try{
			FileReader read = new FileReader(attendancesFile);  	
			
		
		try{
			Scanner in = new Scanner(read);
			
		// The loop below reads each line from the file stores it in a variable called line.
		while (in.hasNextLine()) {
				
			String line = in.nextLine();
			fProg.buildAttendance(line);
				
	}
		
		in.close();
	}
		finally {
			read.close();
		}
	}
		catch (IOException e ){
			System.out.println("No file found");
		}
	}

	/**
	 * Instantiates timetable display and adds it to GUI
	 */
	public void updateDisplay() {
		
		// This variable stores the time slot format the time slots at the top
	    String Header = formatDisplay("9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16");
	    
	    // These variables store the class name of each fitness class on the list
	    String c1 = fProg.showClassName(0);
	    String c2 = fProg.showClassName(1);
	    String c3 = fProg.showClassName(2);
	    String c4 = fProg.showClassName(3);
	    String c5 = fProg.showClassName(4);
	    String c6 = fProg.showClassName(5);
	    String c7 = fProg.showClassName(6);
	    
	    // These variables store the tutor name of each fitness class on the list
	    String t1 = fProg.showTutorName(0);
	    String t2 = fProg.showTutorName(1);
	    String t3 = fProg.showTutorName(2);
	    String t4 = fProg.showTutorName(3);
	    String t5 = fProg.showTutorName(4);
	    String t6 = fProg.showTutorName(5);
	    String t7 = fProg.showTutorName(6);
	    
	    // classLine stores the format of the fitness classes
	    // tutorLine stores the format of the tutor names
	    String classLine = formatDisplay(c1, c2, c3, c4, c5, c6, c7);
	    String tutorLine = formatDisplay(t1, t2, t3, t4, t5, t6, t7);

	    
		display.setEditable(false);
		display.append(Header + "\n");
		display.append(classLine + "\n");
		display.append(tutorLine + "\n");
	    
	}
	
	// Method for formating the display
	public String formatDisplay(String a, String b, String c, String d, String e, String f, String g) {
		String out = String.format("     %-10s  %-10s  %-10s  %-10s  %-10s  %-10s  %-10s", a, b, c, d, e, f, g);
		return out;
	}

	/**
	 * adds buttons to top of GUI
	 */
	public void layoutTop() {
		JPanel top = new JPanel();
		closeButton = new JButton("Save and Exit");
		closeButton.addActionListener(this);
		top.add(closeButton);
		attendanceButton = new JButton("View Attendances");
		attendanceButton.addActionListener(this);
		top.add(attendanceButton);
		add(top, BorderLayout.NORTH);
	}

	/**
	 * adds labels, text fields and buttons to bottom of GUI
	 */
	public void layoutBottom() {
		// instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(3, 3));

		// add upper label, text field and button
		JLabel idLabel = new JLabel("Enter Class Id");
		bottom.add(idLabel);
		idIn = new JTextField();
		bottom.add(idIn);
		JPanel panel1 = new JPanel();
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		panel1.add(addButton);
		bottom.add(panel1);

		// add middle label, text field and button
		JLabel nmeLabel = new JLabel("Enter Class Name");
		bottom.add(nmeLabel);
		classIn = new JTextField();
		bottom.add(classIn);
		JPanel panel2 = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		panel2.add(deleteButton);
		bottom.add(panel2);

		// add lower label text field and button
		JLabel tutLabel = new JLabel("Enter Tutor Name");
		bottom.add(tutLabel);
		tutorIn = new JTextField();
		bottom.add(tutorIn);

		add(bottom, BorderLayout.SOUTH);
	}
	

	/**
	 * Processes adding a class
	 */
	public void processAdding() {
	   String id = idIn.getText();
	   String Class = classIn.getText();
	   String tutor = tutorIn.getText();
	   
	   
	  // The following persists if any of the text fields are empty. It also shows an error message.
	   for(;;) {
	   
	   if (id.isEmpty() || Class.isEmpty() || tutor.isEmpty() ) {
		   JOptionPane.showMessageDialog(null, "All the information is required", "Some information missing", JOptionPane.ERROR_MESSAGE);
		   return;
	   }
	   else break;
	   
	   } 
	   
	  // This test ensures that the string is only 3 letter 
	   if (id.length() != 3 || id.charAt(0) == ' ' || id.charAt(1) == ' ' || id.charAt(2) == ' ') {
		   JOptionPane.showMessageDialog(null, "The ID should be one word and 3 letters", "Error", JOptionPane.ERROR_MESSAGE);
		   return;
	   }
	   
	  
	   // This loop and test ensures that the class name is only one word
	   for (int j = 0; j < Class.length(); j++) {
	    if (Class.charAt(j) == ' ') {
		   JOptionPane.showMessageDialog(null, "The class name should be one word (no spaces)", "Error", JOptionPane.ERROR_MESSAGE);
		   return;
	   }
	   } 
	   
	   
	// This loop and test ensures that the tutor name is only one word
	   for (int z = 0; z < tutor.length(); z++) {
	    if (tutor.charAt(z) == ' ') {
		   JOptionPane.showMessageDialog(null, "The tutor name should be one word (no spaces)", "Error", JOptionPane.ERROR_MESSAGE);
		   return;
	   }
	   }
	   
	   
	 
	   try {
	
		 // The following tests if the class is full. If so, a warning message is displayed
		   if (fProg.getClassTotal() >= fProg.getClassList().length) {
				JOptionPane.showMessageDialog(null, "Class List is full", "Warning", JOptionPane.WARNING_MESSAGE);
				   return;}
		  
		// This tests if the id entered in the text field already exists for a class
		   else if (fProg.checkDuplication(id) == true) {
		   JOptionPane.showMessageDialog(null, "Class with that ID already exists", "Warning", JOptionPane.WARNING_MESSAGE);
	   }
	    
		   
	  }
	
	catch (NullPointerException e) {
		
	// This code runs if the test for class id duplication fails.
		fProg.addClass(id, Class, tutor);
		   display.setText(" ");				
		   updateDisplay();
	}
	}

	/**
	 * Processes deleting a class
	 */
	public void processDeletion() {
		String id = idIn.getText();
		   
		   
		   
		  // The following persists if the Id text field is empty. It also shows an error message.
		   for(;;) {
		   
		   if (id.isEmpty()) {
			   JOptionPane.showMessageDialog(null, "ID information is needed to delete class", "ID information missing", JOptionPane.ERROR_MESSAGE);
			   return;
		   }
		   else break;
		   
		   }
		   
		   fProg.deleteClass(id);
		   display.setText(" ");				
		   updateDisplay();
	}

	/**
	 * Instantiates a new window and displays the attendance report
	 */
	public void displayReport() {
		report.setVisible(true);		// To instantiate a new window
		
		FitnessClass [] fc = new FitnessClass[7];
		fc = fProg.sortClass();
		report.buildReport(fc);
		
	}
	
	
	
	
	/**
	 * Writes lines to file representing class name, 
	 * tutor and start time and then exits from the program
	 */
	public void processSaveAndClose() {
	   
		FitnessClass [] fc;
	    try{
	    	String classDetails; 			// This variable stores the formatted class details of a clas
	    	
	    	PrintWriter write = new PrintWriter(classesOutFile);
	 	    fc = fProg.getClassList();
	 	    
	 	 // This loop takes various information about each class in the fc array.
	 	 // These information are concatenated to form the class details.
	 	 // These details are then written into a file.
	 	    for (int i = 0; i < fc.length; i++) {
	 	    	if (fc[i] != null) {
	 	    		String id = fc[i].getClassId();
	 	    		String cName = fc[i].getClassName();
	 	    		String tName = fc[i].getTutor();
	 	    		int sTime = fc[i].getStartTime();
	 	    		
	 	    		classDetails = id + " " + cName + " " + tName + " " + sTime;
	 	    		write.println(classDetails);
	 	    				
	 	    	}
	 	    }
	 	    
	 	    write.close();
	 	   
	 	    
	 	    
	 	    
			
		
			
		}
		 
		
		
		catch (IOException e){
			System.out.println("Exception found");
		}
	}
	

	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
	
		if (ae.getSource() == attendanceButton ) {    // The report is displayed, then cleared upon closing.
			report.clearReport();
			displayReport();
		}
		
		if (ae.getSource() == addButton) {
			processAdding();
			
		}
		
		if (ae.getSource() == deleteButton) {
			processDeletion();
		}
		
		if (ae.getSource() == closeButton) {
			processSaveAndClose();
			System.exit(0);
		}
	
		
	}
}
