package ManageDriver;

public class DriverData {
	private int driver_id;
	private String name, fatherName, idCard, license_no, license_exp_date, dob, contact, salary, busId;

	public DriverData(int driver_id, String name, String fatherName, String idCard, String license_no,
			String license_exp_date, String dob, String contact, String salary, String busId) {
		this.driver_id = driver_id;
		this.name = name;
		this.fatherName = fatherName;
		this.idCard = idCard;
		this.dob = dob;
		this.license_exp_date = license_exp_date;
		this.license_no = license_no;
		this.contact = contact;
		this.salary = salary;
		this.busId = busId;
	}

	public int getDriver_id() {
		return driver_id;
	}

	public String getName() {
		return name;
	}

	public String getFatherName() {
		return fatherName;
	}

	public String getIdCard() {
		return idCard;
	}

	public String getLicense_no() {
		return license_no;
	}

	public String getLicense_exp_date() {
		return license_exp_date;
	}

	public String getDob() {
		return dob;
	}

	public String getContact() {
		return contact;
	}

	public String getSalary() {
		return salary;
	}

	public String getBusId() {
		return busId;
	}

}
