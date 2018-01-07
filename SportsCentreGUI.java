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
	
	
	/**The constants */
	private final int MAX_CLASSES = 7;
	
	/**The file writer */
//	private FileWriter writer;
	
	/**The fitness program object */
	private FitnessProgram fp  = new FitnessProgram();
	
	
	
	
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
		
		initLadiesDay();
		initAttendances();
		updateDisplay();
		displayReport();
		
		
	}

	
	
	
	
	
	/**
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay() {

		
		try
		{	FileReader fr = null;
			try
			{ // open input file and create a scanner 
				fr = new FileReader(classesInFile);
				Scanner in = new Scanner(fr);
			
					
				// For each line in the classes in file, call the method 
				// that builds the array list on start time as written in the FitnessProgram class
				while (in.hasNextLine())
				{
					// read in a line from the input file
					String line = in.nextLine();
					
					//Use the method from the FitnessProgram class to build the array
					// by using the above extracted line as a parameter
					fp.buildList(line);
	
				}
				
				//This part is just for testing, can delete in the end.
				System.err.println("The list of fitness classes has initially " + fp.nonNullCount() + " actual elements.");
				System.err.println("=============================================================");				
			}
			finally
			{ 
				if (fr!=null) fr.close();
			}
		}
		catch (IOException e){	
			System.out.println("Exception "+e);
		}
	}
	
	
	
	
	
	

	/**
	 * Initialises the attendances using data
	 * from the file AttendancesIn.txt
	 */
	public void initAttendances() {

	
		try
		{	FileReader fr = null;
			try
			{ // open input file and create a scanner 
				fr = new FileReader(attendancesFile);
				Scanner in = new Scanner(fr);
			
				// declare delimiters to be used for corresponding input file lines
				String delimiters = "[ ,.]+";
			
			
				// EXtract each line in the file
				// and split it into tokens to be
				// used as the instance variables when
				// constructing a fitness class object
		
				while (in.hasNextLine())
				{
					// read in a line from the input file
					String line = in.nextLine();
				
					// split up the line into tokens using the relevant delimiter
					String [] tokens = line.split(delimiters);
				
					// Each token corrensponds
					// to an object attribute
					String id = tokens[0];
					int week1_att = Integer.parseInt(tokens[1]);
					int week2_att = Integer.parseInt(tokens[2]);
					int week3_att = Integer.parseInt(tokens[3]);
					int week4_att = Integer.parseInt(tokens[4]);
					int week5_att = Integer.parseInt(tokens[5]);
				
					// Make the attendance array from the above tokens
					int[] attendanceSet = {week1_att,week2_att,week3_att,week4_att,week5_att};
					
					//Use the matching method to assign the attendances to the corresponding class
					fp.matchAttendances(id, attendanceSet);				
				}						
			}
			finally
			{ 
				if (fr!=null) fr.close();
			}
		}
		catch (IOException e){	
			System.out.println("Exception "+e);
		}

	}


	
	
	
	
	
	/**
	 * Instantiates timetable display and adds it to GUI
	 */
	public void updateDisplay() {
	    
		
		// Making the header for the display, containing the time slots
		String [] header = {"9-10","10-11","11-12","12-13","13-14","14-15","15-16"};
		
		display.setText(" ");
	
		// Loop over the number of classes, and assign each element of header array
		for(int i = 0; i<MAX_CLASSES; i++) {
			display.append(String.format("  %-10s",header[i]));
		}
		
		display.append("\n");
		
		// Loop over the possible times,and for each one call the method 
		//that finds the class associated with that time and extract its name 
		// with the getter method. Then it can be added to the relevant slot in the display.
		// If there is no class at this time, the space will be marked as available
		for(int t = 0; t<MAX_CLASSES; t++) {
			try {
				
				display.append(String.format("  %-10s", (fp.fitClassAtTime(t + 9)).getName()));	
			}
			catch(NullPointerException e) {
				display.append( String.format(" %-10s",  " Available "));
			}
		}
		display.append("\n");
		
		
		
		
		// Same as above for getting the tutor information and add it to the display
		for(int t = 0; t<MAX_CLASSES; t++) {
			
			try {
				display.append(String.format("  %-10s", (fp.fitClassAtTime(t+9)).getTutor()));
			}
			catch(NullPointerException e) {
				display.append( String.format(" %-10s",  "           "));
				
			}
		}	
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
	    
		//Get the info from the textfields
		// and store them to variables
		String id = idIn.getText();
	    String name = classIn.getText();
	    String tutor = tutorIn.getText();
	    
	    
	    
	    // If any of the text fields is empty, notify user and clear them so they can be re used
	    if (id.equals("") || name.equals("") || tutor.equals("")) {
	    	JOptionPane.showMessageDialog(null, "Provide all information needed", "Message", JOptionPane.ERROR_MESSAGE);
	    	idIn.setText("");
			classIn.setText("");
			tutorIn.setText("");
	    }
	    
	    // If the number of actual classes is seven, then there is no mpre space. Notify user
	    else if(fp.getActualObjNum() > 6) {
	    	JOptionPane.showMessageDialog(null, "Number of classes full", "Message", JOptionPane.ERROR_MESSAGE);
	    	idIn.setText("");
			classIn.setText("");
			tutorIn.setText("");
	    }
	    
	    //If the id in question is already in the list, it cannot be reused. Notify user
	    else if(fp.isIDpresent(idIn.getText())) {
	    	JOptionPane.showMessageDialog(null, "ID already exists", "Message", JOptionPane.ERROR_MESSAGE);
	    	idIn.setText("");
			classIn.setText("");
			tutorIn.setText("");
	    }
	    
	    // All tests passed, so user can go on with adding a valid class to the array
	    else {
	    	//Call the add class method from 
		    // the FitnessProgram class by 
		    // passing the above variables    
		    fp.addClass(id, name,tutor);
		    	
		    //Clear the fields 
		    idIn.setText("");
			classIn.setText("");
			tutorIn.setText("");
	    }

	}

	

	
	
	
	
	/**
	 * Processes deleting a class
	 */
	public void processDeletion() {

		//Extract the id from the textfield and store it in a variable
		String id = idIn.getText();
		
		// If the field is empty, notify user 
		if (id.equals("")) {
			JOptionPane.showMessageDialog(null, "Provide a class ID to delete", "Message", JOptionPane.ERROR_MESSAGE);
		}
		
		
		// Call the method that finds the object with that ID.
		// If it does not exist, notify user
		else if(fp.fitClassWithID(id) == null) {
			JOptionPane.showMessageDialog(null, "The ID does not exist", "Message", JOptionPane.ERROR_MESSAGE);
			idIn.setText("");
			classIn.setText("");
			tutorIn.setText("");
		}

		
		else {		// proceed with deleting the class and clearing the fields
			fp.delClass(id);
			idIn.setText("");
			classIn.setText("");
			tutorIn.setText("");
		}
		
	}
	
	
	

	

	
	/**
	 * Instantiates a new window and displays the attendance report
	 */
	public void displayReport() {
		
		//Create a REportFrame object and
		// call the makeReport method from REportFrame
		report = new ReportFrame();	
		report.makeReport(fp);
	}

	
	
	
	
	
	
	/**
	 * Writes lines to file representing class name, 
	 * tutor and start time and then exits from the program
	 */
	public void processSaveAndClose() {
	    
		//Initialising the file writer to null
		FileWriter writer = null;
		
		//I will be using the result of the make report method
		// taht was used to produce the view attendances functionality
		// as in the display report method above
		String savedInfo = report.makeReport(fp);
		
		try {
			writer = new FileWriter(classesOutFile);
			
			writer.write(savedInfo);
			writer.close();
		}
		catch(IOException e) {
			System.err.println("File not found");
		}

	}

	
	
	
	

	
	
	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
		
	    if (ae.getSource() == attendanceButton) {
	    	report.setVisible(true);
	    	
	    	displayReport();
	    }
	    else if (ae.getSource() == addButton) {
	    	processAdding();
	    	updateDisplay();
			displayReport();
	    	
	    }
	    
	    else if (ae.getSource() == deleteButton) {
	    	processDeletion();
	    	updateDisplay();
	    	displayReport();
	    }
	    
	    else if (ae.getSource() == closeButton ) {
	    	processSaveAndClose();
	    	System.exit(0);
	    }
	}
}