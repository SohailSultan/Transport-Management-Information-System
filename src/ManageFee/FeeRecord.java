package ManageFee;

public class FeeRecord {
	private int feeID;
	private String amount, paidBy, date, customerName, cnic, discount, month, year, fatherName, department;

	public FeeRecord(int feeID, String amount, String paidBy, String date, String customerName, String cnic,
			String discount, String month, String year, String fatherName, String department) {
		this.feeID = feeID;
		this.amount = amount;
		this.paidBy = paidBy;
		this.date = date;
		this.customerName = customerName;
		this.cnic = cnic;
		this.discount = discount;
		this.month = month;
		this.year = year;
		this.fatherName = fatherName;
		this.department = department;
	}

	public int getFeeID() {
		return feeID;
	}

	public String getAmount() {
		return amount;
	}

	public String getPaidBy() {
		return paidBy;
	}

	public String getDate() {
		return date;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCnic() {
		return cnic;
	}

	public String getDiscount() {
		return discount;
	}

	public String getMonth() {
		return month;
	}

	public String getYear() {
		return year;
	}

	public String getFatherName() {
		return fatherName;
	}

	public String getDepartment() {
		return department;
	}

}
