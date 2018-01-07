import java.awt.*;
import java.util.Arrays;

import javax.swing.*;

/**
 * Class to define window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame {
      
	
	//The instance variables for the class
//	private FitnessProgram fp;
	private JTextArea attendanceTextArea;
	
	
	// Dimensions of the frame
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 220;
	

	
	
	public ReportFrame() {
		
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocation(400, 400);
		this.setTitle("Attendance Report");

		attendanceTextArea = new JTextArea(); 
		attendanceTextArea.setEditable(false);
		attendanceTextArea.setFont(new Font("Courier",Font.PLAIN,14));
				
		this.add(attendanceTextArea);

		
	}
	
	

	
	
	// The method for the report.
	public String makeReport(FitnessProgram fp) {
			
			
		// Sort the fp object by average attendance by using the relevant method
		FitnessClass[] myList = fp.sortOnAverageAttendance();
			
		//Make the titles for the header
		String id = "Id";
		String name = "Class";
		String tutor = "Tutor";
		String attendancesHeader = "Attendances";
		String avgAttendance = "Average Attendance";
		String overall = "Overall average: ";

		attendanceTextArea.setText("");

			
		//Formatting the line that will contain the headers
		attendanceTextArea.append(String.format("   "
				+ "%-10s %-20s %-20s %-20s %-20s %n", id, name, tutor, attendancesHeader, avgAttendance));

				
		// Using a loop with an arbitrary number of cycles to create the spacing 
		for(int i = 0; i<10; i++) { 
			attendanceTextArea.append("===========");
		}
		attendanceTextArea.append("\n");
						
			
		// Producing the information for the report
		for(int i = 0; i< myList.length; i++){
				
			try {

				// get attendances for each list element
				int [] attendanceSet = myList[i].getAttendance();
					
				//Calculate the average for each individual object in the array and store it to a variable
				// by using the single average calculation method
				double classAverage = fp.singleAverage(attendanceSet);
					
				// Make a single string for the attendances of each week 
				String totAtt = "" +attendanceSet[0] +" " +attendanceSet[1] +" " 
						+attendanceSet[2] +" " +attendanceSet[3] +" " +attendanceSet[4];
					
					
				// Adding the info for each class
				attendanceTextArea.append(String.format("   %-10s %-20s %-20s %-25s %-20.2f %n", 
						myList[i].getID(), myList[i].getName(), 
						myList[i].getTutor(), totAtt, 
						classAverage));		
			}
			catch(NullPointerException e) {
					
			}
							
		}
			
		// Calculate and show the overall average by usign the relevant method					
		double overallAve = fp.overallAverage();
			
		//add the overall average to the report 
		attendanceTextArea.append(String.format("%n%s %.2f" ,overall, overallAve));
								
		// This will be used by the GUI for the output file
		// in order to write the report in a txt.
		String outputFileContent = attendanceTextArea.getText().toString();
					
		return outputFileContent; 
				
	}
	
	
}