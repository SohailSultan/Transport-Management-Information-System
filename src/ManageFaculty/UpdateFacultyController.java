package ManageFaculty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class UpdateFacultyController {

	@FXML
	private Pane DataPane;

	@FXML
	private TextField FacultyIDTextField;

	@FXML
	private TextField FeeStatusTextField;

	@FXML
	private Button SaveButton;

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
	private DatePicker dobTextField;

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

	@FXML
	private ChoiceBox<String> StatusChoiceBox;

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
		String CNIC = cnicTextField.getText();
		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Prepare the SQL statement to search for the record
			String sql = "SELECT * FROM Faculty WHERE FacultyID = ? OR CNIC = ?";
			statement = conn.prepareStatement(sql);
			statement.setString(1, FacultyID);
			statement.setString(2, CNIC);

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
				dobTextField.setValue(LocalDate.parse(resultSet.getString("DOB")));
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
				StatusChoiceBox.setValue(resultSet.getString("Status"));
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

	@FXML // Update record method
	void saveRecord(ActionEvent event) throws SQLException {
		// Prompt the user to update the record
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Update Record");
		alert.setHeaderText("Update Conductor Record");
		alert.setContentText("Do you want to update the record?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			String FacultyID = FacultyIDTextField.getText();
			String CNIC = cnicTextField.getText();
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Prepare the SQL statement to update the record
			String updateQuery = "UPDATE Faculty SET FacultyType = ?, Name = ?, FatherName = ?, DOB = ?, Designation = ?, Department = ?,"
					+ " Contact = ?, CNIC = ?, BankAccount = ?, RouteID = ?, BusID = ?, Salary = ?, Address = ?, FeeStatus = ?, Status = ?"
					+ " WHERE FacultyID = ? OR CNIC = ?;";
			statement = conn.prepareStatement(updateQuery);
			// Update the record with the new data
			statement.setString(1, facultyTypeTextField.getText().trim());
			statement.setString(2, nameTextField.getText().trim());
			statement.setString(3, fatherNameTextField.getText().trim());
			statement.setString(4, dobTextField.getValue().toString());
			statement.setString(5, designationTextField.getText().trim());
			statement.setString(6, departmentTextField.getText().trim());
			statement.setString(7, contactTextField.getText().trim());
			statement.setString(8, cnicTextField.getText().trim());
			statement.setString(9, bankAccountTextField.getText().trim());
			statement.setString(10, routeIdTextField.getText().trim());
			statement.setString(11, busIdTextField.getText().trim());
			statement.setString(12, salaryTextField.getText().trim());
			statement.setString(13, addressTextField.getText().trim());
			statement.setString(14, FeeStatusTextField.getText().trim());
			statement.setString(15, StatusChoiceBox.getValue());
			statement.setString(16, FacultyID);
			statement.setString(17, CNIC);

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

	private boolean isValidPhoneNumber(String phoneNumber) {
		String regex = "^[0-9]{4}-[0-9]{7}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}

	@FXML
	public void initialize() {
		StatusChoiceBox.getItems().addAll("active", "passed out");
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
