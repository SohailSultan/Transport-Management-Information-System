package ManageFee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddFeeRecordController {

	@FXML
	private TextField AmountTextField;
	@FXML
	private TextField TotalAmountTextField;

	@FXML
	private TextField CNICTextField;

	@FXML
	private TextField CustomerNameTextField;

	@FXML
	private DatePicker DateTextField;

	@FXML
	private DatePicker NextDateTextField;

	@FXML
	private TextField DepartmentTextField;

	@FXML
	private TextField DiscountTextField;

	@FXML
	private TextField FatherNameTextField;

	@FXML
	private TextField MonthTextField;
	@FXML
	private TextField TotalMonthTextField;

	@FXML
	private ChoiceBox<String> PaidByChoiceBox;
	@FXML
	private ChoiceBox<String> Pay_PlanChoiceBox, InstallmentChoiceBox;
	@FXML
	private Button SaveRecordButton;
	@FXML
	private Label AddmonthLabel;

	@FXML
	private TextField AddmonthTextField;

	@FXML
	private TextField YearTextField;

	@FXML
	private AnchorPane contentPane;

	@FXML
	private Hyperlink CheckCNICLabel;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;

	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	/*
	 * void Insertrecord() {
	 *
	 * }
	 */

	@FXML
	void SaveRecord(ActionEvent event) {

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			int totalAfterDiscount = Integer.parseInt(AmountTextField.getText())
					- Integer.parseInt(DiscountTextField.getText());

			// Prepare the SQL statement for insertion
			String sql = "INSERT INTO fee_records (amount, paidBy, date, customerName, "
					+ "cnic, discount, month, year, fatherName, department, paymentPlan,RemainingBalance , NextDueDate) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			int Totalmonthin_semester = Integer.parseInt(TotalMonthTextField.getText());
			float TotalPaidAMount = totalAfterDiscount;
			float PaidAmount = (TotalPaidAMount / Totalmonthin_semester);

			float remainingBalance = Float.parseFloat(TotalAmountTextField.getText());

			for (int i = 0; i < Totalmonthin_semester; i++) {
				remainingBalance -= PaidAmount;
				LocalDate currentDate = DateTextField.getValue().plusMonths(i);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM"); // Format for full month name
				String monthFullName = currentDate.format(formatter); // Get month in full name format
				String year = Integer.toString(currentDate.getYear());
				statement = conn.prepareStatement(sql);
				statement.setString(1, Float.toString(PaidAmount));
				statement.setString(2, PaidByChoiceBox.getValue());
				statement.setString(3, DateTextField.getValue().plusMonths(i).toString());
				statement.setString(4, CustomerNameTextField.getText());
				statement.setString(5, CNICTextField.getText());
				statement.setString(6, DiscountTextField.getText());
				statement.setString(7, monthFullName);
				statement.setString(8, year);
				statement.setString(9, FatherNameTextField.getText());
				statement.setString(10, DepartmentTextField.getText());
				if ("Installment".equals(Pay_PlanChoiceBox.getValue())) {
					statement.setString(11, InstallmentChoiceBox.getValue() + (i + 1));
				} else {
					statement.setString(11, Pay_PlanChoiceBox.getValue());
				}

				if ("Installment".equals(Pay_PlanChoiceBox.getValue())) {

					if ("Last Installment".equals(InstallmentChoiceBox.getValue())) {
						int RemainingBalance = 12500;
						RemainingBalance -= 6250 * (i + 1);
						statement.setString(12, Float.toString(RemainingBalance));
					} else {
						statement.setString(12, Float.toString(remainingBalance));
					}
				} else {
					statement.setString(12, Float.toString(remainingBalance));
				}
				statement.setString(13, NextDateTextField.getValue().toString());
				// Execute the SQL statement
				statement.executeUpdate();
			}

			// Alert message showed after Record saved
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setContentText("Data has been saved to the database");
			alert.showAndWait();
		} catch (SQLException e) {
			// Alert message showed after Record saved
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Failed!");
			alert.setContentText("Error! record is not saved: " + e.getMessage());
			alert.showAndWait();
		} finally {
			// Close the connection in the finally block
			if (conn != null) {
				try {
					// Close the statement and connection
					statement.close();
					conn.close();

				} catch (SQLException e) {
				}
			}
		}
	}

	@FXML
	void ClearFields() {
		AmountTextField.clear();
		PaidByChoiceBox.getSelectionModel().clearSelection();
		CustomerNameTextField.clear();
		CNICTextField.clear();
		DiscountTextField.clear();
		FatherNameTextField.clear();
		DepartmentTextField.clear();
		NextDateTextField.setValue(null);
		Pay_PlanChoiceBox.getSelectionModel().clearSelection();

	}

	// Match CNIC Record of Fee Payer in respective tables
	@FXML
	void CheckCNICinRecord() throws SQLException {
		String Cnic = CNICTextField.getText();
		conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		if (PaidByChoiceBox.getValue().equals("Student")) {

			// Prepare the SQL statement for insertion
			String sql = "SELECT Name, FatherName, Department, Status FROM Students WHERE CNIC = ? ";
			statement = conn.prepareStatement(sql);
			statement.setString(1, Cnic);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				if ("active".equals(rs.getString("Status"))) {
					CustomerNameTextField.setText(rs.getString("Name"));
					FatherNameTextField.setText(rs.getString("FatherName"));
					DepartmentTextField.setText(rs.getString("Department"));
					CheckCNICLabel.setText(" Record Matched");
					CheckCNICLabel.setStyle("-fx-background-color: white; -fx-text-fill: Green; ");
					rs.close();
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText("Student Has been Passed out");
					alert.showAndWait();
					CNICTextField.clear();
				}

			} else {
				CheckCNICLabel.setText("Not Found In Record ");
				CheckCNICLabel.setStyle("-fx-background-color: white; -fx-text-fill: red;");
				rs.close();
			}

		} else if (PaidByChoiceBox.getValue().equals("Faculty")) {
			try {
				// Prepare the SQL statement for insertion
				String sql = "SELECT Name, FatherName, Department, Salary, Status FROM Faculty WHERE CNIC = ? ";
				statement = conn.prepareStatement(sql);
				statement.setString(1, Cnic);
				ResultSet rs = statement.executeQuery();

				if (rs.next()) {
					if ("active".equals(rs.getString("Status"))) {
						CustomerNameTextField.setText(rs.getString("Name"));
						FatherNameTextField.setText(rs.getString("FatherName"));
						DepartmentTextField.setText(rs.getString("Department"));
						int salary = Integer.parseInt(rs.getString("Salary"));
						if (salary < 50000) {
							TotalAmountTextField.setText("500");
							AmountTextField.setText("500");
						} else if (salary >= 50000 && salary < 100000) {
							TotalAmountTextField.setText("2000");
							AmountTextField.setText("2000");
						} else if (salary >= 100000) {
							TotalAmountTextField.setText("2500");
							AmountTextField.setText("2500");
						}

						CheckCNICLabel.setText(" Record Matched");
						CheckCNICLabel.setStyle("-fx-background-color: white; -fx-text-fill: Green;");
						rs.close();

					} else {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setContentText("Faculty Has been Passed out");
						alert.showAndWait();
						CNICTextField.clear();
					}
				} else {
					CheckCNICLabel.setText("Not Found In FACULTY Record ");
					CheckCNICLabel.setStyle("-fx-background-color: white; -fx-text-fill: red;");
					rs.close();
				}
			} catch (SQLException e) {

			}
		}

		conn.close();
	}

	/*
	 * _________________________________________Field Validation
	 * Part____________________________________________________
	 */

	private boolean isValidCNIC(String cnic) {
		String regex = "^[0-9]{5}-[0-9]{7}-[0-9]$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(cnic);
		return matcher.matches();
	}

	void ChangeStatus(int month) {

		Pay_PlanChoiceBox.setPrefWidth(228);
		AddmonthTextField.setVisible(false);
		InstallmentChoiceBox.setVisible(false);
		AddmonthLabel.setVisible(false);

		// Get the current date from DateTextField (assuming it's a LocalDate)
		LocalDate currentDate = DateTextField.getValue();

		// Calculate the next month's start date
		YearMonth nextMonth = YearMonth.from(currentDate).plusMonths(month);
		LocalDate nextMonthStart = nextMonth.atDay(1);

		// Set the value of NextDateTextField to the start of the next month
		NextDateTextField.setValue(nextMonthStart);
	}

	@FXML
	public void initialize() {

		Pay_PlanChoiceBox.setOnAction(e -> {

			if ("Full".equals(Pay_PlanChoiceBox.getValue())) {

				ChangeStatus(4);
				TotalMonthTextField.setText("4");
			} else if ("Installment".equals(Pay_PlanChoiceBox.getValue())) {

				Pay_PlanChoiceBox.setPrefWidth(120);
				InstallmentChoiceBox.setVisible(true);
				AddmonthLabel.setVisible(true);
				AddmonthLabel.setText("Choose Plan");

				// Get the current date from DateTextField (assuming it's a LocalDate)
				LocalDate currentDate = DateTextField.getValue();

				// Calculate the next month's start date
				YearMonth nextMonth = YearMonth.from(currentDate).plusMonths(2);
				LocalDate nextMonthStart = nextMonth.atDay(1);

				// Set the value of NextDateTextField to the start of the next month
				NextDateTextField.setValue(nextMonthStart);

				TotalMonthTextField.setText("2");
				Pay_PlanChoiceBox.getItems().addAll(InstallmentChoiceBox.getValue());

			} else if ("Monthly".equals(Pay_PlanChoiceBox.getValue())) {
				TotalMonthTextField.setText("1");
				ChangeStatus(1);

			} else if ("Advance".equals(Pay_PlanChoiceBox.getValue())) {
				Pay_PlanChoiceBox.setPrefWidth(178);
				AddmonthTextField.setVisible(true);
				AddmonthLabel.setVisible(true);
			}
		});

		AddmonthTextField.setOnAction(e -> {
			int additional = Integer.parseInt(AddmonthTextField.getText());
			TotalMonthTextField.setText(String.valueOf(additional + 4));

			// Get the current date from DateTextField (assuming it's a LocalDate)
			LocalDate currentDate = DateTextField.getValue();

			// Calculate the next month's start date
			YearMonth nextMonth = YearMonth.from(currentDate)
					.plusMonths(Integer.parseInt(TotalMonthTextField.getText()));
			LocalDate nextMonthStart = nextMonth.atDay(1);

			// Set the value of NextDateTextField to the start of the next month
			NextDateTextField.setValue(nextMonthStart);

		});

		// Setting Current Date on TextFields

		SimpleDateFormat MonthDF = new SimpleDateFormat("MMMM");
		String currentMonth = MonthDF.format(new Date());

		SimpleDateFormat YearDF = new SimpleDateFormat("yyyy");
		String currentYear = YearDF.format(new Date());

		SimpleDateFormat DateDF = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = DateDF.format(new Date());
		DateTextField.setValue(LocalDate.parse(currentDate));

		MonthTextField.setText(currentMonth);
		YearTextField.setText(currentYear);

		DateTextField.setOnAction(event -> {
			LocalDate selectedDate = DateTextField.getValue();
			Month selectedMonth = selectedDate.getMonth();
			int selectedYear = selectedDate.getYear();
			// Convert Month to String
			MonthTextField.setText(selectedMonth.toString());

			// Convert int to String
			YearTextField.setText(String.valueOf(selectedYear));
		});

		// 2023-07-04

		// Initialize the choice box values
		PaidByChoiceBox.getItems().addAll("Student", "Faculty", "Other");

		InstallmentChoiceBox.getItems().addAll("1st Installment", "Last Installment");

		PaidByChoiceBox.setOnAction(e -> {
			if ("Student".equals(PaidByChoiceBox.getValue())) {
				Pay_PlanChoiceBox.getItems().removeAll("Full", "Installment", "Advance", "Monthly");
				Pay_PlanChoiceBox.getItems().addAll("Full", "Installment", "Advance");
			} else if ("Faculty".equals(PaidByChoiceBox.getValue())) {
				Pay_PlanChoiceBox.getItems().removeAll("Full", "Installment", "Advance", "Monthly");
				Pay_PlanChoiceBox.getItems().addAll("Monthly");
			} else if ("Other".equals(PaidByChoiceBox.getValue())) {
				Pay_PlanChoiceBox.getItems().removeAll("Full", "Installment", "Advance", "Monthly");
				Pay_PlanChoiceBox.getItems().addAll("Full", "Installment", "Advance", "Monthly");
			}
		});

		CNICTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!isValidCNIC(newValue)) {
				CNICTextField.setStyle("-fx-border-color: red; -fx-text-fill: red;");
			} else {
				CNICTextField.setStyle("-fx-border-color: green; -fx-text-fill: green;");
			}
		});

	}
}