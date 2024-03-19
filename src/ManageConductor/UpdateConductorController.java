package ManageConductor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UpdateConductorController {

	@FXML
	private TextField Bus_IDTextField;

	@FXML
	private Button SearchButton;

	@FXML
	private Button UpdateButton;

	@FXML
	private TextField ageTextField;

	@FXML
	private TextField contactTextField;

	@FXML
	private AnchorPane contentPane;

	@FXML
	private TextField fatherNameTextField;

	@FXML
	private TextField idCardTextField;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField salaryTextField;
	@FXML
	private TextField ConductorIDTextField;
	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	void SearchRecord(ActionEvent event) {

		String conductorID = ConductorIDTextField.getText(); // Get the conductor ID from the text field
		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Prepare the SQL statement to search for the conductor record
			String sql = "SELECT * FROM conductor WHERE conductor_id = ? OR id_card = ?";
			statement = conn.prepareStatement(sql);
			statement.setString(1, conductorID);
			statement.setString(2, conductorID);
			// Execute the query and retrieve the result
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				// Retrieve the conductor information from the result set

				String name = resultSet.getString("name");
				String age = resultSet.getString("age");
				String contact = resultSet.getString("contact");
				String fatherName = resultSet.getString("father_name");
				String salary = resultSet.getString("salary");
				String busID = resultSet.getString("Vehicle_Regno");
				String idCard = resultSet.getString("id_card");
				String id = resultSet.getString("conductor_id");

				// Display the conductor information in the text fields
				nameTextField.setText(name);
				ageTextField.setText(age);
				contactTextField.setText(contact);
				fatherNameTextField.setText(fatherName);
				salaryTextField.setText(salary);
				Bus_IDTextField.setText(busID);
				idCardTextField.setText(idCard);
				ConductorIDTextField.setText(id);
			} else {
				ConductorIDTextField.setText(conductorID);
				// Display an alert if no matching record is found
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("No Record Found");
				alert.setContentText("No conductor record found for the given Conductor ID.");
				alert.showAndWait();
			}

		} catch (SQLException e) {
			// Alert message shown if there's an error with the database or query
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Error occurred while searching the conductor record: " + e.getMessage());
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
			String conductorID = ConductorIDTextField.getText();
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Update the record with the new data
			String newName = nameTextField.getText().trim();
			String newAge = ageTextField.getText().trim();
			String newContact = contactTextField.getText().trim();
			String newFatherName = fatherNameTextField.getText().trim();
			String newIdCard = idCardTextField.getText().trim();
			String newSalary = salaryTextField.getText().trim();
			String newBusId = Bus_IDTextField.getText().trim();

			// Prepare the SQL statement to update the conductor record
			String updateQuery = "UPDATE conductor SET name = ?, age = ?, contact = ?, father_name = ?, "
					+ "id_card = ?, salary = ?, Vehicle_Regno = ? WHERE conductor_id = ?";
			statement = conn.prepareStatement(updateQuery);
			statement.setString(1, newName);
			statement.setString(2, newAge);
			statement.setString(3, newContact);
			statement.setString(4, newFatherName);
			statement.setString(5, newIdCard);
			statement.setString(6, newSalary);
			statement.setString(7, newBusId);
			statement.setString(8, conductorID);

			// Execute the update query
			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				insertNotification("Admin Updated Data for Conductor against CNIC: " + idCardTextField.getText());
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

//        cnicTextField.setPromptText("Enter CNIC (e.g., 13501-3201348-3)");

		idCardTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!isValidCNIC(newValue)) {
				idCardTextField.setStyle("-fx-border-color: red; -fx-text-fill: red;");
			} else {
				idCardTextField.setStyle("-fx-border-color: green; -fx-text-fill: green;");
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

	private void insertNotification(String notification) {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			PreparedStatement statement;
			String SelectQuery = "SELECT username FROM Admin WHERE login_status = 'online'";
			statement = conn.prepareStatement(SelectQuery);
			ResultSet rs = statement.executeQuery();
			String username = rs.getString("username");
			rs.next();
			rs.close();

			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
			String currentTime = sdf.format(new Date());
			String InsertQuery = "INSERT INTO Notifications (Time, username, Notification) VALUES (?, ? ,?)";
			statement = conn.prepareStatement(InsertQuery);
			statement.setString(1, currentTime);
			statement.setString(2, username);
			statement.setString(3, notification);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error While Sending Notification" + e);
		}
	}
}
