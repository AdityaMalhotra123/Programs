//I am the sole author of the work in this repository.
//$ Good
public class Student {
	//defiining attributes of a student
	private String name;
	private String address;
	private long telNumber;
	private int suNumber;
	private long homeNumber;
	/**
	* Initializes attributes of a student.
	* @param n, a, t, su, h represemt name, address, telNumber, suNumber, homeNumber respectively
	* @post constructs Student
	*
	*/
	public Student(String n, String a, long t, int su, long h) {
		name = n;
		address = a;
		telNumber = t;
		suNumber = su;
		homeNumber = h;
	}
	/**
	* @pre name is of data type String
	* @post returns name of student
	*
	* @return name
	*/
	public String getName() {
		return name;
	}
	/**
	* @pre address is of data type String
	* @post returns Campus address of student
	*
	* @return address
	*/
	public String getAddress() {
		return address;
	}
	/**
	* @pre suNumber is of data type int
	* @post returns suNumber
	*
	* @return suNumber
	*/
	public int getSUNumber() {
		return suNumber;
	}
	/** getareaCode()
	@pre homeNumber is of long data type.
	@post returns area Code
	@return  last three digits of homeNumber
	*/
	public int getareaCode() {
		//if homeNumber is unknown, return -1
		if (homeNumber == -1) {
			return -1;
		} else {
			//find first three digits of homeNumber
			return (int)(homeNumber/10000000);
		}
	}

	public String toString() {
		return "";
	}

}
