import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
public class FitnessProgram {
    
	
	// The constant for maximum number of classes
	private final int MAX_CLASSES= 7;
	
	
	//The instance variables for the class
	private FitnessClass [] fitnessList = new FitnessClass[MAX_CLASSES]; 
	private FitnessClass [] tempList; 		//This will contain only actual objects. Will use that to sort on average attendance
	private int nonNullObjNum = 0;
	
	
	// The default constructor for the FitnessProgram
	public FitnessProgram() {
		
	}
	
	
	//Getter methods for the instance variables
	public FitnessClass[] getFitnessList(){
		return fitnessList;
	}
	
	public int getActualObjNum() {
		return nonNullObjNum;
	}
	
	
	
	
	
	
	// Method to build the list on start
	// time from a line coming
	// from data in a file after it is being read
	public void buildList(String line) {
		
		//Make a list of tokens as extracted
		//from a line
		String [] myLine = line.split("[ ,.;:]+") ;

		//Need the time to be an integer as it
		// is declared as such in the fitness 
		// class constructor
		String myTime = myLine[3].trim();		
		int a_time = Integer.parseInt(myTime);

		//Builds a new instance of the class FitnessClass
		// by using the tokens from the line
		FitnessClass fc = new FitnessClass(myLine[0], myLine[1], myLine[2], a_time);	
		
		//Assign the object to the appropriate list index
		fitnessList[fc.getTime() - 9] = fc;
	}
	
	
	

	
	//Method to return a FitnessClass object at index 'x' in the 
	// fitnessList array with 'x' given as a parameter
	public FitnessClass fitClassAtIndex(int x) {
		
		// Iterate over a number of array elements
		// up to the maximum number of classes. If
		// it is not null then return it
		for (int i = 0; i < MAX_CLASSES; i++) {
			if (fitnessList[i] != null) {
				return fitnessList[i];
			}			
		}
		return null;
	}
	
	
	
	//Method to return the fitness class starting
	// at a particular time given as a parameter named 't'
	// It will be used for building the display in the
	// JTextArea of the GUI
	public FitnessClass fitClassAtTime(int t) {
		
		//Iterate over the objects in the array
		// If the object's time is the same as the one
		// in question (t), then we have found the class
		// at that time t.
		for (int i = 0; i < MAX_CLASSES; i++) {
			if (fitnessList[i] != null && fitnessList[i].getTime() == t) {
				return fitnessList[i];
			}
		}
		return null;
		
		
		
	}
	
	
	
	
	
	//Method to return an object with a particular
	// ID, else null if the ID is not valid
	public FitnessClass fitClassWithID(String identity) {
		
		// Loop over the elements. If the element is not null and the id's match then we have found
		// the object with the id in question
		for (int i = 0; i < MAX_CLASSES; i++) {
			if (fitnessList[i] != null && fitnessList[i].getID().equals(identity)) {
				return fitnessList[i];
			}
		}
		return null;
	}
	

	

	
	// Method to add a fitness class object
	// in the fitrnessList array. It will need
	// the class id, the name, and the tutor as
	// parameters. Will search the array, and if there is a null space,
	// it will add the new class there
	public void addClass(String id, String name, String tutor){ 

		//Loop over the array list. If I find a 
		// null object, then the space is available
		// to add the new class.
		for (int i = 0; i<MAX_CLASSES;i++){
			try {
				//Try to get the id for the class. If empty, it will
				// go to catch and begin adding
				fitnessList[i].getID();
				
			} catch(NullPointerException e) {  
				//Determine the time of the first available
				// open space
				int time = i + 9; 
				
				
				//Create a new class object
				FitnessClass fc = new FitnessClass(id, name, tutor, time);
				
				//Occupy the list
				fitnessList[i] = fc; 
				
				//Increment the number
				// of actual classes, so it
				//can be used to calculate
				//averages
				nonNullObjNum++;
				return; 
			}
		}
		

	}
	
	
	

	
	
	
	//Method to delete a fitness class object 
	// from the fitnessList array
	public void delClass(String test_id) {
		
		//Loop over array and for each of its
		// elements, check if the id in question
		// belongs to an existing class. If it does
		// then delete it by turn it to null and reduce
		//the number of actual classes by 1 in order to have accurate average calculation when needed
		for (int i = 0; i < MAX_CLASSES; i++) {
			try {
				if(fitnessList[i].getID().equals(test_id)) {
					fitnessList[i] = null;
					nonNullObjNum--;
				}
			}
			catch(NullPointerException e) {
				
			}
		}
	}
	
	
	
	
	

	
	
	//A method to check if a class
	// with a particular id is already 
	//included in the array
	public boolean isIDpresent(String test_id) {
			
		//Initialising the boolean variable
		boolean idPresent = false;
			
		// Use the method to check if the test id
		// belongs to a class that is already in the 
		// array. If yes, it cannot be repeated, and 
		//so the boolean can be used in the GUI addition process
		for (int i = 0; i < MAX_CLASSES; i++) {
			if ( fitClassWithID(test_id) != null) {
				idPresent = true;
			}
		}
		return idPresent;	
	}
		
	



	
	//Method to count the non null objects in the
	// array of fitness class objects.
	public int nonNullCount() {
		
		for (int i = 0; i < MAX_CLASSES; i++) {
			if (fitnessList[i] != null) {
				nonNullObjNum++;
			}
		}
		return nonNullObjNum;
	}
	
	
	//Method that counts the null objects in
	// the array list. It might be unnecessary
	//since I have the nonNullCount() method 
	//above. Will decide which one to keep
	public int nullCount(){
		int nullCount = 0;

		for (int i = 0; i < MAX_CLASSES; i++) {			
			try{
				fitnessList[i].getID();
			} catch(NullPointerException e) {
				nullCount++;
			}
		}	
		return nullCount;
	}
	
	
	
	
	
	//Method to sort the class list on average attendance
	// Will use Arrays.sort for that. 
	public FitnessClass[] sortOnAverageAttendance() {
		
		//Create a temporary list with actual objects only.
		// Here, the method that counts only the non- null
		// elements of the array is used		
		tempList = new FitnessClass[MAX_CLASSES - this.nullCount()];
						
		// Index position for tempList
		int i = 0;
		
		//Loop over the array and whenever
		// a class is found, copy it to the temp list
		// so in the end it will contains only actual
		// classes and not null ones
		for (int j = 0; j < MAX_CLASSES; j++) {
			try {
				if (fitnessList[j] == null) {
				}
				else {
					tempList[i] = fitnessList[j];
					i++;
				}
			}
			catch(NullPointerException e) {
				
			}
		
		}
		Arrays.sort(tempList);
		return tempList;
		
	}
	
	
	
	
	
	
	
	// A method to match the class ids with the attendance 
	//set for each one of them. It will be used in the GUI to
	// initialise attendance data using the info from the attendance
	// file.
	public void matchAttendances(String id, int[] attendanceSet) {
		for (int i = 0; i<MAX_CLASSES;i++) {
			try{				
				if(fitnessList[i].getID().equals(id)){					
					fitnessList[i].setAttendances(attendanceSet);				
				}		
			}
			catch(NullPointerException e) {		
				
			}
		}
	}
	
	
	
	
	
	// Method to calculate the average for each
	// fitness array. It will be used to calculate the
	// data for the report frame. Not to be confused with the
	// get average method of the FitnessClass class
	public double singleAverage(int[] single_ave) {
		
		//initialising the necesasary variables
		double single_average =0.0;
		double sum = 0.0;
		
		// loop over a dummy int array and get the average of the elements
		for (int i = 0 ; i< single_ave.length; i++) {
			sum += single_ave[i];
			single_average = sum / single_ave.length;
		}
		return single_average;
	}
	
	
	
	
	
	
	// A method to calculate the overall average
	// to be used when producing the report 
	public double overallAverage() {
		
		//Necessary variables initialisation
		double overallSum = 0.0;
		double overallAve = 0.0;
		
		// Loop over the array list and for each element
		// extract the attendance list using the relevant method.
		// This attendance list of each element, will be used as a parameter
		// when calling the single average calculation above, and it will produce the
		// overall average for all classes.  This will be used for the report
		for (int i=0; i< MAX_CLASSES; i++) {
			
			try {
				// get attendances for each fitness object
				int[] test_attendance = fitnessList[i].getAttendance();
				
				// calculate the average for each and add it to the overall sum
				overallSum += singleAverage(test_attendance);
				
			}
			catch(NullPointerException e) {
				
			}

			
		}
		
		//calculate and return the overall average
		overallAve = overallSum / nonNullObjNum;
		return overallAve;
	}
	
	
	
}