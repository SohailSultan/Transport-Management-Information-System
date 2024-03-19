package ManageExpense.Vehicle_Tokens;

public class TokenData {
	private int Exp_ID;
	private String Vehicle_no, amount, Expense_type, Payment_method, Remarks;
	String Expiry_date;
	String purcahse_date;

	TokenData(int Exp_ID, String Vehicle_no, String amount, String purcahse_date, String Expense_type,
			String Expiry_date, String Payment_method, String Remarks) {
		this.Exp_ID = Exp_ID;
		this.setAmount(amount);
		this.setVehicle_no(Vehicle_no);
		this.setPurcahse_date(purcahse_date);
		this.setExpense_type(Expense_type);
		this.setExpiry_date(Expiry_date);
		this.setPayment_method(Payment_method);
		this.setRemarks(Remarks);
	}

	public int getExp_ID() {
		return Exp_ID;
	}

	public void setExp_ID(int Exp_ID) {
		this.Exp_ID = Exp_ID;
	}

	public String getVehicle_no() {
		return Vehicle_no;
	}

	public void setVehicle_no(String vehicle_no) {
		Vehicle_no = vehicle_no;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPurcahse_date() {
		return purcahse_date;
	}

	public void setPurcahse_date(String purcahse_date) {
		this.purcahse_date = purcahse_date;
	}

	public String getExpense_type() {
		return Expense_type;
	}

	public void setExpense_type(String expense_type) {
		Expense_type = expense_type;
	}

	public String getExpiry_date() {
		return Expiry_date;
	}

	public void setExpiry_date(String expiry_date) {
		Expiry_date = expiry_date;
	}

	public String getPayment_method() {
		return Payment_method;
	}

	public void setPayment_method(String payment_method) {
		Payment_method = payment_method;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
}
