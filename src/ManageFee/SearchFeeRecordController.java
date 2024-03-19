package ManageFee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class SearchFeeRecordController {

	@FXML
	private TextField AmountTextField;

	@FXML
	private TextField CNICTextField;

	@FXML
	private TextField CustomerNameTextField;

	@FXML
	private TextField DateTextField;

	@FXML
	private TextField DepartmentTextField;

	@FXML
	private TextField DiscountTextField;

	@FXML
	private TextField FatherNameTextField;

	@FXML
	private TextField FeeIDTextField;

	@FXML
	private TextField MonthTextField;

	@FXML
	private TextField PaidByTextfield;

	@FXML
	private TextField YearTextField;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	void SearchRecord(ActionEvent event) {
		String FeeID = FeeIDTextField.getText(); // Get the Fee ID from the text field
		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Prepare the SQL statement to search for the record
			String sql = "SELECT * FROM fee_records WHERE feeID = ? OR cnic = ?";
			statement = conn.prepareStatement(sql);
			statement.setString(1, FeeID);
			statement.setString(2, FeeID);

			// Execute the query and retrieve the result
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {

				/*
				 * Retrieve the information from the result set AND Display the information in
				 * the text fields after putting resultset data in textfields
				 **/
				AmountTextField.setText(resultSet.getString("amount"));
				PaidByTextfield.setText(resultSet.getString("paidBy"));
				DateTextField.setText(resultSet.getString("date"));
				CustomerNameTextField.setText(resultSet.getString("customerName"));
				CNICTextField.setText(resultSet.getString("cnic"));
				DiscountTextField.setText(resultSet.getString("discount"));
				MonthTextField.setText(resultSet.getString("month"));
				YearTextField.setText(resultSet.getString("year"));
				FatherNameTextField.setText(resultSet.getString("fatherName"));
				DepartmentTextField.setText(resultSet.getString("department"));
				FeeIDTextField.setText(resultSet.getString("feeID"));
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
