package ManageDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UpdateDriver {

	@FXML
	private TextField Bus_IDTextField;

	@FXML
	private DatePicker DoBTextField;

	@FXML
	private DatePicker LicenseExpDateTextField;

	@FXML
	private TextField LicenseNoTextField;

	@FXML
	private Button SearchButton;

	@FXML
	private Button UpdateButton;

	@FXML
	private TextField contactTextField;

	@FXML
	private AnchorPane contentPane;

	@FXML
	private TextField driverIdTextField;

	@FXML
	private TextField fatherNameTextField;

	@FXML
	private TextField idCardTextField;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField salaryTextField;

	@FXML
	private DatePicker JoinDateTextField;

	@FXML
	private DatePicker LeavingDateTextField;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	void SearchDriver(ActionEvent event) {
		String driverID = driverIdTextField.getText(); // Get the driver ID from the text field
		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Prepare the SQL statement to search for the record
			String sql = "SELECT * FROM Driver WHERE driver_id = ? OR id_card = ?";
			statement = conn.prepareStatement(sql);
			statement.setString(1, driverID);
			statement.setString(2, driverID);

			// Execute the query and retrieve the result
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {

				/*
				 * Retrieve the information from the result set AND Display the information in
				 * the text fields after putting resultset data in textfields
				 **/
				nameTextField.setText(resultSet.getString("name"));
				DoBTextField.setValue(LocalDate.parse(resultSet.getString("dob")));
				contactTextField.setText(resultSet.getString("contact"));
				fatherNameTextField.setText(resultSet.getString("father_name"));
				LicenseNoTextField.setText(resultSet.getString("license_no"));
				// Local.Parse is used to covert date datatype to String
				LicenseExpDateTextField.setValue(LocalDate.parse(resultSet.getString("license_exp_date")));
				JoinDateTextField.setValue(LocalDate.parse(resultSet.getString("JoinDate")));

				String leavingDateStr = resultSet.getString("LeavingDate");
				if (leavingDateStr != null) {
					LeavingDateTextField.setValue(LocalDate.parse(leavingDateStr));
				} else {
					LeavingDateTextField.setValue(null); // Set DatePicker to no value
				}

				salaryTextField.setText(resultSet.getString("salary"));
				Bus_IDTextField.setText(resultSet.getString("bus_id"));
				idCardTextField.setText(resultSet.getString("id_card"));
				driverIdTextField.setText(resultSet.getString("driver_id"));
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
			String driverID = driverIdTextField.getText();
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Prepare the SQL statement to update the conductor record
			String updateQuery = "UPDATE Driver SET name = ?, father_name = ?, id_card = ?, dob = ?, contact = ?,  license_no = ?,"
					+ " license_exp_date =?, salary = ?, bus_id = ?, JoinDate = ?, LeavingDate = ?  WHERE driver_id = ?";
			statement = conn.prepareStatement(updateQuery);
			// Update the record with the new data
			statement.setString(1, nameTextField.getText().trim());
			statement.setString(2, fatherNameTextField.getText().trim());
			statement.setString(3, idCardTextField.getText().trim());
			statement.setString(4, DoBTextField.getValue().toString());
			statement.setString(5, contactTextField.getText().trim());
			statement.setString(6, LicenseNoTextField.getText().trim());
			statement.setString(7, LicenseExpDateTextField.getValue().toString());
			statement.setString(8, salaryTextField.getText().trim());
			statement.setString(9, Bus_IDTextField.getText().trim());
			statement.setString(10, JoinDateTextField.getValue().toString());
			if (LeavingDateTextField.getValue() != null) {
				statement.setString(11, LeavingDateTextField.getValue().toString());
			} else {
				statement.setNull(11, Types.DATE); // Set the parameter to NULL in the database
			}

			statement.setString(12, driverID);

			// Execute the update query
			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				insertNotification("Admin Updated Data for Driver against CNIC: " + idCardTextField.getText());
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
