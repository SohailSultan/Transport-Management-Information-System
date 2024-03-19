package ManageExpense;

public class ExpenseData {
	private int expense_id;
	private String date, expense_type, expense_detail, amount, payment_method, consumer_id, consumer_name, cnic, month,
			year;

	public ExpenseData(int expense_id, String date, String expense_type, String expense_detail, String amount,
			String payment_method, String consumer_id, String consumer_name, String cnic, String month, String year) {
		this.date = date;
		this.amount = amount;
		this.cnic = cnic;
		this.consumer_id = consumer_id;
		this.consumer_name = consumer_name;
		this.expense_detail = expense_detail;
		this.expense_id = expense_id;
		this.expense_type = expense_type;
		this.month = month;
		this.payment_method = payment_method;
		this.year = year;

	}

	// getter for variables
	public int getExpense_id() {
		return expense_id;
	}

	public String getDate() {
		return date;
	}

	public String getExpense_type() {
		return expense_type;
	}

	public String getExpense_detail() {
		return expense_detail;
	}

	public String getAmount() {
		return amount;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public String getConsumer_id() {
		return consumer_id;
	}

	public String getConsumer_name() {
		return consumer_name;
	}

	public String getCnic() {
		return cnic;
	}

	public String getMonth() {
		return month;
	}

	public String getYear() {
		return year;
	}

}
