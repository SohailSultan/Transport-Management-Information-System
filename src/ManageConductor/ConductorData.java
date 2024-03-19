package ManageConductor;

public class ConductorData {
	private int conductorId;
	private String name;
	private String fatherName;
	private String idCard;
	private String age;
	private String contact;
	private String salary;
	private String busId;

	public ConductorData(int conductorId, String name, String fatherName, String idCard, String age, String contact,
			String salary, String busId) {
		this.conductorId = conductorId;
		this.name = name;
		this.fatherName = fatherName;
		this.idCard = idCard;
		this.age = age;
		this.contact = contact;
		this.salary = salary;
		this.busId = busId;
	}

	public int getConductorId() {
		return conductorId;
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

	public String getAge() {
		return age;
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
