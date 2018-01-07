/** Defines an object representing a single fitness class
 */
public class FitnessClass implements Comparable<FitnessClass> {
    
	// Declaring the instance variables
	private String id = "";
	private String name = "";
	private String tutor = "";
	private int startTime = 0;
	private final int NUM_WEEKS = 5;
	private int[] attendanceSet = new int[NUM_WEEKS];
	private double average;
	
	
	// The constructor for the FitnessClass object
	public FitnessClass(String an_id, String a_name, String a_tutor, int a_time) {
		id = an_id;
		name = a_name;
		tutor = a_tutor;
		startTime = a_time;
	}
	
	

	
	// Getter methods for the instance variables
	public String getID(){
		return id;
	}

	public String getName(){
		return name;
	}

	public String getTutor(){
		return tutor;
	}

	public int getTime(){
		return startTime;
	}

	public int[] getAttendance(){
		return attendanceSet;
	}
	
	
	
	//Setter methods for the instance variables
	public void setID(String id){
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public void setTutor(String tutor){
		this.tutor = tutor;
	}

	public void setTime(int startTime){
		this.startTime = startTime;
	}
	
	
	public void setAttendances(int [] att) {		
		attendanceSet = att;
	}
	
	
	
	
	

	
	//Method for the average attendance for the FitnessClass object
	private double getAverage( ){
		
		//Initialising the variables that are to be used
		double sum = 0.0;
		double total = 0.0;
		 
		//Iterate over the array elements
		// and calculate the average by adding and dividing in the end
		for (int i = 0 ; i < attendanceSet.length; i++) {
			sum += attendanceSet[i];
			total++;		 
		}	 
		average = (sum/total);
		return average;	
	}
	
	
	


	//Method to compare two Fitness class objects
	// based on their average attendances
    public int compareTo(FitnessClass other) {
    	
    	if(this.getAverage() < other.getAverage()) {
    		return 1;
    	}
    	else if(this.getAverage() > other.getAverage()) {
    		return -1;
    	}
    	else {
    		return 0;
    	}

    }
    

    
}