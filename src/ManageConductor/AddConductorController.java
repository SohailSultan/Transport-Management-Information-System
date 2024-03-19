package ManageConductor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class AddConductorController {

	@FXML
	private TextField conductorIdTextField;
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField fatherNameTextField;
	@FXML
	private TextField idCardTextField;
	@FXML
	private TextField ageTextField;
	@FXML
	private TextField contactTextField;
	@FXML
	private TextField salaryTextField;
	@FXML
	private TextField Bus_IDTextField;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	public void SaveRecord() {

		try {

			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			// Prepare SQL statement for insertion
			String sqlQuery = "INSERT INTO conductor (name, father_name, id_card, age, contact, salary, Vehicle_Regno) VALUES (?, ?, ?, ?, ?, ?, ?)";
			statement = conn.prepareStatement(sqlQuery);
			statement.setString(1, nameTextField.getText());
			statement.setString(2, fatherNameTextField.getText());
			statement.setString(3, idCardTextField.getText());
			statement.setString(4, ageTextField.getText());
			statement.setString(5, contactTextField.getText());
			statement.setString(6, salaryTextField.getText());
			statement.setString(7, Bus_IDTextField.getText());

			// Execute the SQL statement
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				// Alert message showed after Record saved
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setContentText("Conductor data has been saved to the database.\n Conductor Name #: "
						+ nameTextField.getText());
				insertNotification("Admin Inserted New Data for Conductor against CNIC: " + idCardTextField.getText());
				alert.showAndWait();
				nameTextField.setText("");
				fatherNameTextField.setText("");
				idCardTextField.setText("");
				ageTextField.setText("");
				contactTextField.setText("");
				salaryTextField.setText("");
				Bus_IDTextField.setText("");
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
			alert.setContentText("Error! Database is not connected: " + e.getMessage());
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
