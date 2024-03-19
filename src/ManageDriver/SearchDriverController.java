package ManageDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class SearchDriverController {

	@FXML
	private TextField Bus_IDTextField;

	@FXML
	private TextField DoBTextField;

	@FXML
	private TextField LicenseExpDateTextField;

	@FXML
	private TextField LicenseNoTextField;

	@FXML
	private Button SearchButton;

	@FXML
	private TextField SearchIDTextField;

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
	private TextField JoinDateTextField;

	@FXML
	private TextField LeavingDateTextField;

	@FXML
	private Pane page1;

	@FXML
	private Pane page2;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	void GoToPage2() {
		page1.setVisible(false);
		page2.setVisible(true);
	}

	@FXML
	void GoToPage1() {
		page1.setVisible(true);
		page2.setVisible(false);
	}

	@FXML
	void SearchDriver(ActionEvent event) {
		String driverID = SearchIDTextField.getText(); // Get the driver ID from the text field
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
				LeavingDateTextField.setText(resultSet.getString("LeavingDate"));
				JoinDateTextField.setText(resultSet.getString("JoinDate"));
				idCardTextField.setText(resultSet.getString("id_card"));
				SearchIDTextField.setText(resultSet.getString("driver_id"));
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
