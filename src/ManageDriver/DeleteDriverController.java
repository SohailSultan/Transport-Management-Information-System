package ManageDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class DeleteDriverController {

	@FXML
	private TextField Bus_IDTextField;

	@FXML
	private Button DeleteButton;

	@FXML
	private TextField DoBTextField;

	@FXML
	private TextField LicenseExpDateTextField;

	@FXML
	private TextField LicenseNoTextField;

	@FXML
	private Button SearchButton;

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
				DoBTextField.setText(resultSet.getString("dob"));
				contactTextField.setText(resultSet.getString("contact"));
				fatherNameTextField.setText(resultSet.getString("father_name"));
				LicenseNoTextField.setText(resultSet.getString("license_no"));
				LicenseExpDateTextField.setText(resultSet.getString("license_exp_date"));
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
	void DeleteRecord(ActionEvent event) throws SQLException {
		// Confirm deletion from user
		Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
		confirmationAlert.setTitle("Confirm Deletion");
		confirmationAlert.setContentText("Are you sure you want to delete this conductor record?");
		Optional<ButtonType> result = confirmationAlert.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			String driverID = driverIdTextField.getText();
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			// Delete the conductor record
			String deleteSql = "DELETE FROM Driver WHERE driver_id = ?";
			PreparedStatement deleteStatement = conn.prepareStatement(deleteSql);
			deleteStatement.setString(1, driverID);
			deleteStatement.executeUpdate();
			// Display success message
			Alert successAlert = new Alert(AlertType.INFORMATION);
			successAlert.setTitle("Deletion Successful");
			successAlert.setContentText("Conductor record deleted successfully.");
			insertNotification("Admin Deleted Data for Driver against CNIC: " + idCardTextField.getText());
			successAlert.showAndWait();
			// Clear the text fields
			nameTextField.clear();
			DoBTextField.clear();
			contactTextField.clear();
			LicenseExpDateTextField.clear();
			LicenseNoTextField.clear();
			fatherNameTextField.clear();
			idCardTextField.clear();
			salaryTextField.clear();
			Bus_IDTextField.clear();
		} else {
			// Display Failure message
			Alert successAlert = new Alert(AlertType.INFORMATION);
			successAlert.setTitle("Deletion Failed");
			successAlert.setContentText("Conductor record is not deleted.");
			successAlert.showAndWait();
		}
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
