package ManageConductor;

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

public class DeleteConductorController {

	@FXML
	private TextField Bus_IDTextField;

	@FXML
	private Button DeleteButton;

	@FXML
	private Button SearchButton;

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
	// Get the conductor ID from the text field

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
	void DeleteRecord() throws SQLException {
		// Confirm deletion from user
		Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
		confirmationAlert.setTitle("Confirm Deletion");
		confirmationAlert.setContentText("Are you sure you want to delete this conductor record?");
		Optional<ButtonType> result = confirmationAlert.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			String conductorID = ConductorIDTextField.getText();
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			// Delete the conductor record
			String deleteSql = "DELETE FROM conductor WHERE conductor_id = ? OR id_card = ?";
			PreparedStatement deleteStatement = conn.prepareStatement(deleteSql);
			deleteStatement.setString(1, conductorID);
			deleteStatement.setString(2, conductorID);
			deleteStatement.executeUpdate();

			// Display success message
			Alert successAlert = new Alert(AlertType.INFORMATION);
			successAlert.setTitle("Deletion Successful");
			successAlert.setContentText("Conductor record deleted successfully.");
			successAlert.showAndWait();
			insertNotification("Admin Deleted Data for Conductor against CNIC: " + idCardTextField.getText());

			// Clear the text fields
			nameTextField.clear();
			ageTextField.clear();
			contactTextField.clear();
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
