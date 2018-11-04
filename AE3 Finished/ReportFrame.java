import java.awt.*;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.*;

/**
 * Class to define window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame {
      
	// Declaring instance variables
	private FitnessProgram prog;
	private JTextArea area;
	
	
	
	// Default Constructor
	public ReportFrame() {
		
		setTitle("Attendance");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 300);
		area = new JTextArea();
		area.setEditable(false);
		area.setFont(new Font("Courier", Font.PLAIN, 14));
		add(area, BorderLayout.CENTER);
		
		prog = new FitnessProgram();
		
	}
	
	
	
	

	
	/**A method to build the report for display on the JTextArea **/
	public void buildReport(FitnessClass[] f) {
		
	// String header represents the formated header
	// c represents the formated report for a class
	// The String builder object used to build a header underline
	// totAvg holds the format of the total average attendance of fitness classes in the array.
		String header = String.format("   %-3s  %-15s  %-15s  %-25s  %-10s", "Id", "Class", "Tutor", "Attendances", "Average Attendance");
		String c = "";
		StringBuilder star = new StringBuilder("*");
		
		double overall = prog.getTotalAvgAtt(f);
		String totAvg = String.format("%80s %.2f", "Overall Average:      ", overall);
		
		
		
		// This loop builds the header underline.
		// It makes sure the underline is as long as the header
		int j = 0;
		while (j <= header.length()) {
			star.append("*");
			j++;
		}
		String underline = star.toString();
		
		area.append(header + "\n");              // The header is appended first
		area.append(underline + "\n");			 // The underline is appended second
		
	for (int i = 0; i < f.length; i++) {
			if (f[i] != null) {
				c = f[i].formatReport();
				area.append(c + "\n");			// The class details each class are appended next
			}
		} 
	
		area.append("\n");
		area.append(totAvg);					// The total average attendance is appended last
		
	}
		
	
		
		
		// Method for clearing JtextArea
		public void clearReport() {
			
		    area.setText(" ");
			
		}
		
		
		
	
	
}
