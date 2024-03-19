package ManageExpense;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SearchExpenseController {

	@FXML
	private TextField AmountTextField;

	@FXML
	private TextField CNICTextField;

	@FXML
	private TextField ConsumerIDTextField;

	@FXML
	private TextField ConsumerNameTextField;

	@FXML
	private TextField DateTextField;

	@FXML
	private TextArea ExpenseDetailTextArea;

	@FXML
	private TextField ExpenseIDTextField;

	@FXML
	private TextField ExpenseTypeTextField;

	@FXML
	private TextField MonthTextField;

	@FXML
	private TextField PaymentMethodTextField;

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
				DateTextField.setText(resultSet.getString("date"));
				ExpenseDetailTextArea.setText(resultSet.getString("expense_detail"));
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

}
