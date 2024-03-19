package ManageExpense;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddExpenseController {

	@FXML
	private TextField AmountTextField;

	@FXML
	private TextField CNICTextField;

	@FXML
	private TextField ConsumerIDTextField;

	@FXML
	private TextField ConsumerNameTextField;

	@FXML
	private DatePicker DateTextField;

	@FXML
	private TextField ExpenseDetailTextField;

	@FXML
	private TextField ExpenseTypeTextField;

	@FXML
	private TextField MonthTextField;

	@FXML
	private TextField PaymentMethodTextField;

	@FXML
	private Button SaveRecordButton;

	@FXML
	private TextField YearTextField;

	@FXML
	private AnchorPane contentPane;
	Connection connection;
	PreparedStatement statement;

	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	void SaveRecord(ActionEvent event) {

		String insertQuery = "INSERT INTO expenses (date, expense_type, expense_detail, amount, payment_method, "
				+ " consumer_id, consumer_name, cnic,  month,  year ) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {

			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			statement = connection.prepareStatement(insertQuery);
			// Set the parameter values
			statement.setString(1, DateTextField.getValue().toString());
			statement.setString(2, ExpenseTypeTextField.getText());
			statement.setString(3, ExpenseDetailTextField.getText());
			statement.setString(4, AmountTextField.getText());
			statement.setString(5, PaymentMethodTextField.getText());
			statement.setString(6, ConsumerIDTextField.getText());
			statement.setString(7, ConsumerNameTextField.getText());
			statement.setString(8, CNICTextField.getText());
			statement.setString(9, MonthTextField.getText());
			statement.setString(10, YearTextField.getText());

			// Execute the INSERT statement
			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				// Alert message showed after Record saved
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setContentText("Data has been saved to the database");
				alert.showAndWait();

				// Clear the text fields
				AmountTextField.clear();
				CNICTextField.clear();
				ConsumerIDTextField.clear();
				ConsumerNameTextField.clear();
				DateTextField.setValue(null);
				ExpenseDetailTextField.clear();
				ExpenseTypeTextField.clear();
				MonthTextField.clear();
				PaymentMethodTextField.clear();
				YearTextField.clear();

			} else {
				// Alert message showed after Record saved
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Failed!");
				alert.setContentText("Error! record is not saved: ");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			// Alert message showed after Record saved
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Failed!");
			alert.setContentText("Error! record is not saved: " + e.getMessage());
			alert.showAndWait();
		} finally {
			// Close the connection in the finally block
			if (connection != null) {
				try {
					// Close the statement and connection
					statement.close();
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
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

	@FXML
	public void initialize() {

		// Setting Current Date on TextFields

		SimpleDateFormat MonthDF = new SimpleDateFormat("MMMM");
		String currentMonth = MonthDF.format(new Date());
		MonthTextField.setText(currentMonth);

		SimpleDateFormat YearDF = new SimpleDateFormat("yyyy");
		String currentYear = YearDF.format(new Date());
		YearTextField.setText(currentYear);

		SimpleDateFormat DateDF = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = DateDF.format(new Date());
		DateTextField.setValue(LocalDate.parse(currentDate));

		DateTextField.setOnAction(event -> {
			LocalDate selectedDate = DateTextField.getValue();
			Month selectedMonth = selectedDate.getMonth();
			int selectedYear = selectedDate.getYear();
			// Convert Month to String
			MonthTextField.setText(selectedMonth.toString());

			// Convert int to String
			YearTextField.setText(String.valueOf(selectedYear));
		});

//        cnicTextField.setPromptText("Enter CNIC (e.g., 13501-3201348-3)");

		CNICTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!isValidCNIC(newValue)) {
				CNICTextField.setStyle("-fx-border-color: red; -fx-text-fill: red;");
			} else {
				CNICTextField.setStyle("-fx-border-color: green; -fx-text-fill: green;");
			}
		});

	}
}
