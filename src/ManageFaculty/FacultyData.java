package ManageFaculty;

public class FacultyData {
	private int facultyID;
	private String facultyType, name, fatherName, DOB, designation, department, contact, CNIC, bankAccount, routeID,
			BusID, salary, address, feeStatus;

	public FacultyData(int facultyID, String facultyType, String name, String fatherName, String DOB,
			String designation, String department, String contact, String CNIC, String bankAccount, String routeID,
			String BusID, String salary, String address, String feeStatus) {
		this.address = address;
		this.facultyID = facultyID;
		this.facultyType = facultyType;
		this.name = name;
		this.fatherName = fatherName;
		this.contact = contact;
		this.DOB = DOB;
		this.designation = designation;
		this.department = department;
		this.CNIC = CNIC;
		this.bankAccount = bankAccount;
		this.routeID = routeID;
		this.BusID = BusID;
		this.salary = salary;
		this.feeStatus = feeStatus;

	}

	public int getFacultyID() {
		return facultyID;
	}

	public String getFacultyType() {
		return facultyType;
	}

	public String getName() {
		return name;
	}

	public String getFatherName() {
		return fatherName;
	}

	public String getDOB() {
		return DOB;
	}

	public String getDesignation() {
		return designation;
	}

	public String getDepartment() {
		return department;
	}

	public String getContact() {
		return contact;
	}

	public String getCNIC() {
		return CNIC;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public String getRouteID() {
		return routeID;
	}

	public String getBusID() {
		return BusID;
	}

	public String getSalary() {
		return salary;
	}

	public String getAddress() {
		return address;
	}

	public String getFeeStatus() {
		return feeStatus;
	}

}
