package ManageStudents;

public class StudentData {
	private int StudentID;
	private String SAPID, Name, FatherName, DOB, Semester, Department, Contact, CNIC, PicknDropPoint, RouteID, BusID,
			Address, FeeStatus, gender;

	public StudentData(int studentID, String sAPID, String name, String fatherName, String DOB, String semester,
			String department, String contact, String CNIC, String picknDropPoint, String routeID, String BusID,
			String address, String feeStatus, String gender) {
		this.StudentID = studentID;
		this.SAPID = sAPID;
		this.Name = name;
		this.FatherName = fatherName;
		this.DOB = DOB;
		this.Semester = semester;
		this.Department = department;
		this.Contact = contact;
		this.CNIC = CNIC;
		this.PicknDropPoint = picknDropPoint;
		this.RouteID = routeID;
		this.BusID = BusID;
		this.Address = address;
		this.FeeStatus = feeStatus;
		this.gender = gender;
	}

	public int getStudentID() {
		return StudentID;
	}

	public String getSAPID() {
		return SAPID;
	}

	public String getName() {
		return Name;
	}

	public String getFatherName() {
		return FatherName;
	}

	public String getDOB() {
		return DOB;
	}

	public String getSemester() {
		return Semester;
	}

	public String getDepartment() {
		return Department;
	}

	public String getContact() {
		return Contact;
	}

	public String getCNIC() {
		return CNIC;
	}

	public String getPicknDropPoint() {
		return PicknDropPoint;
	}

	public String getRouteID() {
		return RouteID;
	}

	public String getBusID() {
		return BusID;
	}

	public String getAddress() {
		return Address;
	}

	public String getFeeStatus() {
		return FeeStatus;
	}

	public String getGender() {
		return gender;
	}

}
