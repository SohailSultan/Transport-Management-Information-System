package ManageExpense.SalaryExpense;

public class SalaryRecord {
	private int Salary_Id;
	private String Name, Cnic, Designation, Amount, Date, Payment_Method, Bank_AccountNo;

	public SalaryRecord(int Salary_Id, String Name, String Cnic, String Designation, String Amount, String Date,
			String Payment_Method, String Bank_AccountNo) {

		this.Salary_Id = Salary_Id;
		this.Name = Name;
		this.Cnic = Cnic;
		this.Designation = Designation;
		this.Amount = Amount;
		this.Date = Date;
		this.Payment_Method = Payment_Method;
		this.Bank_AccountNo = Bank_AccountNo;
	}

	public int getSalary_Id() {
		return Salary_Id;
	}

	public void setSalary_Id(int salary_Id) {
		Salary_Id = salary_Id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getCnic() {
		return Cnic;
	}

	public void setCnic(String cnic) {
		Cnic = cnic;
	}

	public String getDesignation() {
		return Designation;
	}

	public void setDesignation(String designation) {
		Designation = designation;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getPayment_Method() {
		return Payment_Method;
	}

	public void setPayment_Method(String payment_Method) {
		Payment_Method = payment_Method;
	}

	public String getBank_AccountNo() {
		return Bank_AccountNo;
	}

	public void setBank_AccountNo(String bank_AccountNo) {
		Bank_AccountNo = bank_AccountNo;
	}

}
