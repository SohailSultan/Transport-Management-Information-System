package ManageFaculty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class DeleteFacultyController {

	@FXML
	private Pane DataPane;

	@FXML
	private Button DeleteButton;

	@FXML
	private TextField FacultyIDTextField;

	@FXML
	private TextField FeeStatusTextField;

	@FXML
	private Button SearchButton;

	@FXML
	private TextField addressTextField;

	@FXML
	private TextField bankAccountTextField;

	@FXML
	private TextField busIdTextField;

	@FXML
	private TextField cnicTextField;

	@FXML
	private TextField contactTextField;

	@FXML
	private AnchorPane contentPane;

	@FXML
	private TextField departmentTextField;

	@FXML
	private TextField designationTextField;

	@FXML
	private TextField dobTextField;

	@FXML
	private TextField facultyTypeTextField;

	@FXML
	private TextField fatherNameTextField;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField routeIdTextField;

	@FXML
	private TextField salaryTextField;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	void SearchRecord(ActionEvent event) {
		String FacultyID = FacultyIDTextField.getText(); // Get the Expense ID from the text field
		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Prepare the SQL statement to search for the record
			String sql = "SELECT * FROM Faculty WHERE FacultyID = ? OR CNIC = ?";
			statement = conn.prepareStatement(sql);
			statement.setString(1, FacultyID);
			statement.setString(2, FacultyID);

			// Execute the query and retrieve the result
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {

				/*
				 * Retrieve the information from the result set AND Display the information in
				 * the text fields after putting resultset data in textfields
				 **/
				facultyTypeTextField.setText(resultSet.getString("FacultyType"));
				nameTextField.setText(resultSet.getString("Name"));
				fatherNameTextField.setText(resultSet.getString("FatherName"));
				dobTextField.setText(resultSet.getString("DOB"));
				designationTextField.setText(resultSet.getString("Designation"));
				departmentTextField.setText(resultSet.getString("Department"));
				contactTextField.setText(resultSet.getString("Contact"));
				cnicTextField.setText(resultSet.getString("CNIC"));
				bankAccountTextField.setText(resultSet.getString("BankAccount"));
				routeIdTextField.setText(resultSet.getString("RouteID"));
				busIdTextField.setText(resultSet.getString("BusID"));
				salaryTextField.setText(resultSet.getString("Salary"));
				addressTextField.setText(resultSet.getString("Address"));
				FeeStatusTextField.setText(resultSet.getString("FeeStatus"));
				FacultyIDTextField.setText(resultSet.getString("FacultyID"));
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
	void DeleteRecord(ActionEvent event) throws SQLException {
		// Confirm deletion from user
		Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
		confirmationAlert.setTitle("Confirm Deletion");
		confirmationAlert.setContentText("Are you sure you want to delete this record?");
		Optional<ButtonType> result = confirmationAlert.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			String ExpenseID = FacultyIDTextField.getText();
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			// Delete the Expense record
			String deleteSql = "DELETE FROM Faculty WHERE FacultyID = ?";
			PreparedStatement deleteStatement = conn.prepareStatement(deleteSql);
			deleteStatement.setString(1, ExpenseID);
			deleteStatement.executeUpdate();
			// Display success message
			Alert successAlert = new Alert(AlertType.INFORMATION);
			successAlert.setTitle("Deletion Successful");
			successAlert.setContentText(" Record deleted successfully.");
			successAlert.showAndWait();

			// Clear the text fields
			facultyTypeTextField.clear();
			nameTextField.clear();
			fatherNameTextField.clear();
			dobTextField.clear();
			designationTextField.clear();
			departmentTextField.clear();
			contactTextField.clear();
			cnicTextField.clear();
			bankAccountTextField.clear();
			routeIdTextField.clear();
			busIdTextField.clear();
			salaryTextField.clear();
			addressTextField.clear();
			FeeStatusTextField.clear();
		} else {
			// Display Failure message
			Alert successAlert = new Alert(AlertType.INFORMATION);
			successAlert.setTitle("Deletion Failed");
			successAlert.setContentText(" Record is not deleted.");
			successAlert.showAndWait();
		}
	}

}
