package ManageFaculty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddFacultyController {
	// Declare FXML elements
	@FXML
	private TextField facultyTypeTextField;
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField facultyIdTextField;
	@FXML
	private TextField fatherNameTextField;
	@FXML
	private DatePicker dobTextField;
	@FXML
	private TextField designationTextField;
	@FXML
	private TextField departmentTextField;
	@FXML
	private TextField contactTextField;
	@FXML
	private TextField cnicTextField;
	@FXML
	private TextField bankAccountTextField;
	@FXML
	private TextField routeIdTextField;
	@FXML
	private TextField busIdTextField;
	@FXML
	private TextField salaryTextField;
	@FXML
	private TextField addressTextField;
	@FXML
	private TextField FeeStatusTextField;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	private void saveRecord() {
		// Retrieve data from the text fields
		String facultyType = facultyTypeTextField.getText();
		String name = nameTextField.getText();
		String fatherName = fatherNameTextField.getText();
		String dob = dobTextField.getValue().toString();
		String designation = designationTextField.getText();
		String department = departmentTextField.getText();
		String contact = contactTextField.getText();
		String cnic = cnicTextField.getText();
		String bankAccount = bankAccountTextField.getText();
		String routeId = routeIdTextField.getText();
		String busId = busIdTextField.getText();
		String salary = salaryTextField.getText();
		String address = addressTextField.getText();
		String feeStatus = FeeStatusTextField.getText();

		// Prepare the SQL statement
		String sqlQuery = "INSERT INTO Faculty (Name, FacultyType, FatherName, DOB, Designation, Department, Contact, CNIC, "
				+ "BankAccount, RouteID, BusID, Salary, Address, FeeStatus, Status) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'active')";

		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			// Create a prepared statement
			statement = conn.prepareStatement(sqlQuery);
			statement.setString(1, name);
			statement.setString(2, facultyType);
			statement.setString(3, fatherName);
			statement.setString(4, dob);
			statement.setString(5, designation);
			statement.setString(6, department);
			statement.setString(7, contact);
			statement.setString(8, cnic);
			statement.setString(9, bankAccount);
			statement.setString(10, routeId);
			statement.setString(11, busId);
			statement.setString(12, salary);
			statement.setString(13, address);
			statement.setString(14, feeStatus);

			// Execute the SQL statement
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				// Alert message showed after Record saved
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setContentText("Data has been saved to the database.");
				alert.showAndWait();
			} else {
				// Alert message showed after Record is not saved
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Failed!");
				alert.setContentText("Error! record is not saved: ");
				alert.showAndWait();
			}

		} catch (SQLException e) {
			// Alert message showed after database not connected
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Failed!");
			alert.setContentText("Error! Database Error: " + e.getMessage());
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

	public void clearTextFields() {
		facultyTypeTextField.clear();
		nameTextField.clear();
		fatherNameTextField.clear();
		dobTextField.setValue(null);
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

	private boolean isValidPhoneNumber(String phoneNumber) {
		String regex = "^[0-9]{4}-[0-9]{7}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}

	@FXML
	public void initialize() {

//        cnicTextField.setPromptText("Enter CNIC (e.g., 13501-3201348-3)");

		cnicTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!isValidCNIC(newValue)) {
				cnicTextField.setStyle("-fx-border-color: red; -fx-text-fill: red;");
			} else {
				cnicTextField.setStyle("-fx-border-color: green; -fx-text-fill: green;");
			}
		});

		contactTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!isValidPhoneNumber(newValue)) {
				contactTextField.setStyle("-fx-border-color: red;");
			} else {
				contactTextField.setStyle("");
			}
		});

	}
}
