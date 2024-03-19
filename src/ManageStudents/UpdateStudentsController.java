package ManageStudents;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class UpdateStudentsController {

	@FXML
	private Pane DataPane;

	@FXML
	private ChoiceBox<String> FeeStatusChoiceBox;

	@FXML
	private ChoiceBox<String> GenderChoiceBox;

	@FXML
	private TextField PicknDropPointTextField;

	@FXML
	private TextField SAPIDTextField;

	@FXML
	private JFXButton SearchButton;

	@FXML
	private TextField SemesterTextField;

	@FXML
	private TextField Student_IDTextField;

	@FXML
	private JFXButton UpdateButton;

	@FXML
	private TextField addressTextField;

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
	private DatePicker dobTextField;

	@FXML
	private TextField fatherNameTextField;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField routeIdTextField;

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
		String SearchField = Student_IDTextField.getText();
		String SAPID = SAPIDTextField.getText();
		String CNIC = cnicTextField.getText();
		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			// Execute the search query
			String sqlQuery = "SELECT * FROM students WHERE StudentID = ? OR SAPID = ? OR CNIC = ?";
			statement = conn.prepareStatement(sqlQuery);
			statement.setString(1, SearchField);
			statement.setString(2, SAPID);
			statement.setString(3, CNIC);
			ResultSet rs = statement.executeQuery();

			// Check if a record is found
			if (rs.next()) {
				// Update the UI text fields with the retrieved data
				SAPIDTextField.setText(rs.getString("SAPID"));
				nameTextField.setText(rs.getString("Name"));
				fatherNameTextField.setText(rs.getString("FatherName"));
				GenderChoiceBox.setValue((rs.getString("gender")));
				addressTextField.setText(rs.getString("Address"));
				dobTextField.setValue(LocalDate.parse(rs.getString("DOB")));
				cnicTextField.setText(rs.getString("CNIC"));
				contactTextField.setText(rs.getString("Contact"));
				SemesterTextField.setText(rs.getString("Semester"));
				departmentTextField.setText(rs.getString("Department"));
				PicknDropPointTextField.setText(rs.getString("PicknDropPoint"));
				FeeStatusChoiceBox.setValue((rs.getString("FeeStatus")));
				routeIdTextField.setText(rs.getString("RouteID"));
				busIdTextField.setText(rs.getString("BusID"));
				Student_IDTextField.setText(rs.getString("StudentID"));
				StatusChoiceBox.setValue(rs.getString("Status"));
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
				} // catch ends here
			} // if ends here
		} // finally ends here

	}// method ends here

	@FXML
	void UpdateRecord(ActionEvent event) throws SQLException {
		// Prompt the user to update the record
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Update Record");
		alert.setHeaderText("Update Conductor Record");
		alert.setContentText("Do you want to update the record?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			String StudentID = Student_IDTextField.getText();
			String SAPID = SAPIDTextField.getText();
			String CNIC = cnicTextField.getText();
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Prepare the SQL statement to update the record
			String updateQuery = " UPDATE Students  SET SAPID = ?, Name = ?,  FatherName = ? ,"
					+ "DOB = ?, Semester = ?,  Department = ?,  Contact = ?, "
					+ "CNIC = ?, PicknDropPoint = ?, RouteID = ?, BusID = ?, "
					+ "Address = ?, FeeStatus = ?, gender = ?, Status = ?  WHERE StudentID = ? OR SAPID = ? OR CNIC = ?";
			statement = conn.prepareStatement(updateQuery);
			// Set the parameter values
			statement.setString(1, SAPIDTextField.getText().trim());
			statement.setString(2, nameTextField.getText().trim());
			statement.setString(3, fatherNameTextField.getText().trim());
			statement.setString(4, dobTextField.getValue().toString());
			statement.setString(5, SemesterTextField.getText().trim());
			statement.setString(6, departmentTextField.getText().trim());
			statement.setString(7, contactTextField.getText().trim());
			statement.setString(8, cnicTextField.getText().toString().trim());
			statement.setString(9, PicknDropPointTextField.getText().trim());
			statement.setString(10, routeIdTextField.getText().trim());
			statement.setString(11, busIdTextField.getText().trim());
			statement.setString(12, addressTextField.getText().trim());
			statement.setString(13, FeeStatusChoiceBox.getValue().trim());
			statement.setString(14, GenderChoiceBox.getValue().trim());
			statement.setString(15, StatusChoiceBox.getValue());
			statement.setString(16, StudentID);
			statement.setString(17, SAPID);
			statement.setString(18, CNIC);

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
			} // else ends here
		} // Outer If ends here
	} // method Update record ends here

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
		// Initialize the choice box values
		FeeStatusChoiceBox.getItems().addAll("Paid", "Not Paid");
		StatusChoiceBox.getItems().addAll("active", "passed out");
		GenderChoiceBox.getItems().addAll("Male", "Female");
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
