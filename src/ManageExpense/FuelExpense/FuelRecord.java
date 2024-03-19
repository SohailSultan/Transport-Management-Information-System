package ManageExpense.FuelExpense;

public class FuelRecord {
	private int Expense_Id;
	private String Vehicle_RegNo, Fuel_litre, Amount, Date, Payment_Method, Remarks;

	public FuelRecord(int Expense_Id, String Vehicle_RegNo, String Fuel_litre, String Amount, String Date,
			String Payment_Method, String Remarks) {
		this.Expense_Id = Expense_Id;
		this.Vehicle_RegNo = Vehicle_RegNo;
		this.Fuel_litre = Fuel_litre;
		this.Amount = Amount;
		this.Date = Date;
		this.Payment_Method = Payment_Method;
		this.Remarks = Remarks;
	}

	public int getExpense_Id() {
		return Expense_Id;
	}

	public void setExpense_Id(int expense_Id) {
		Expense_Id = expense_Id;
	}

	public String getVehicle_RegNo() {
		return Vehicle_RegNo;
	}

	public void setVehicle_RegNo(String vehicle_RegNo) {
		Vehicle_RegNo = vehicle_RegNo;
	}

	public String getFuel_litre() {
		return Fuel_litre;
	}

	public void setFuel_litre(String fuel_litre) {
		Fuel_litre = fuel_litre;
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

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

}
