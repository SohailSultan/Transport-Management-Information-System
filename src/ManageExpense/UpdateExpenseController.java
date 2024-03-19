package ManageExpense;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class UpdateExpenseController {

	@FXML
	private TextField AmountTextField;

	@FXML
	private TextField CNICTextField;

	@FXML
	private TextField ConsumerIDTextField;

	@FXML
	private TextField ConsumerNameTextField;

	@FXML
	private Pane DataPane;

	@FXML
	private DatePicker DateTextField;

	@FXML
	private TextField ExpenseDetailTextField;

	@FXML
	private TextField ExpenseIDTextField;

	@FXML
	private TextField ExpenseTypeTextField;

	@FXML
	private TextField MonthTextField;

	@FXML
	private TextField PaymentMethodTextField;

	@FXML
	private Button SearchButton;

	@FXML
	private Button UpdateRecordButton;

	@FXML
	private TextField YearTextField;

	@FXML
	private AnchorPane contentPane;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	void SearchRecord(ActionEvent event) {
		String ExpenseID = ExpenseIDTextField.getText(); // Get the Expense ID from the text field
		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Prepare the SQL statement to search for the record
			String sql = "SELECT * FROM expenses WHERE expense_id = ? OR cnic = ?";
			statement = conn.prepareStatement(sql);
			statement.setString(1, ExpenseID);
			statement.setString(2, ExpenseID);

			// Execute the query and retrieve the result
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {

				/*
				 * Retrieve the information from the result set AND Display the information in
				 * the text fields after putting resultset data in textfields
				 **/
				AmountTextField.setText(resultSet.getString("amount"));
				CNICTextField.setText(resultSet.getString("cnic"));
				ConsumerIDTextField.setText(resultSet.getString("consumer_id"));
				ConsumerNameTextField.setText(resultSet.getString("consumer_name"));
				DateTextField.setValue(LocalDate.parse(resultSet.getString("date").toString()));
				ExpenseDetailTextField.setText(resultSet.getString("expense_detail"));
				ExpenseTypeTextField.setText(resultSet.getString("expense_type"));
				MonthTextField.setText(resultSet.getString("month"));
				PaymentMethodTextField.setText(resultSet.getString("payment_method"));
				YearTextField.setText(resultSet.getString("year"));
				ExpenseIDTextField.setText(resultSet.getString("expense_id"));
			} else {
				// Display an alert if no matching record is found
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("No Record Found");
				alert.setContentText("No record found for the given ID.");
				alert.showAndWait();
			}

		} catch (SQLException e) {
			// Alert message shown if there's an error with the database or query
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Error occurred while searching the record: " + e.getMessage());
			alert.showAndWait();
		} finally {
			// Close the connection in the finally block
			if (conn != null) {
				try {
					conn.close();
					statement.close();
				} catch (SQLException e) {
				}
			}

		}

	}

	@FXML
	void UpdateRecord(ActionEvent event) throws SQLException {
		// Prompt the user to update the record
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Update Record");
		alert.setHeaderText("Update Conductor Record");
		alert.setContentText("Do you want to update the record?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			String ExpenseID = ExpenseIDTextField.getText();
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Prepare the SQL statement to update the conductor record
			String updateQuery = "UPDATE expenses SET date = ?, expense_type = ?, expense_detail = ?, amount = ?, "
					+ "payment_method = ?, consumer_id = ?, consumer_name = ?, cnic = ?, month = ?, year = ?WHERE expense_id = ?;";
			statement = conn.prepareStatement(updateQuery);
			// Update the record with the new data
			statement.setString(1, DateTextField.getValue().toString().trim());
			statement.setString(2, ExpenseTypeTextField.getText().trim());
			statement.setString(3, ExpenseDetailTextField.getText().trim());
			statement.setString(4, AmountTextField.getText().trim());
			statement.setString(5, PaymentMethodTextField.getText().trim());
			statement.setString(6, ConsumerIDTextField.getText().trim());
			statement.setString(7, ConsumerNameTextField.getText().trim());
			statement.setString(8, CNICTextField.getText().trim());
			statement.setString(9, MonthTextField.getText().trim());
			statement.setString(10, YearTextField.getText().trim());
			statement.setString(11, ExpenseID);

			// Execute the update query
			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				// Display success message
				Alert successAlert = new Alert(AlertType.INFORMATION);
				successAlert.setTitle("Success");
				successAlert.setContentText("Record updated successfully.");
				successAlert.showAndWait();
			} else {
				// Display error message if the update failed
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setTitle("Error");
				errorAlert.setContentText("Failed to update record.");
				errorAlert.showAndWait();
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

//        cnicTextField.setPromptText("Enter CNIC (e.g., 13501-3201348-3)");

		CNICTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!isValidCNIC(newValue)) {
				CNICTextField.setStyle("-fx-border-color: red; -fx-text-fill: red;");
			} else {
				CNICTextField.setStyle("-fx-border-color: green; -fx-text-fill: green;");
			}
		});

		DateTextField.setOnAction(event -> {
			LocalDate selectedDate = DateTextField.getValue();
			Month selectedMonth = selectedDate.getMonth();
			int selectedYear = selectedDate.getYear();
			// Convert Month to String
			MonthTextField.setText(selectedMonth.toString());

			// Convert int to String
			YearTextField.setText(String.valueOf(selectedYear));
		});

	}

}
