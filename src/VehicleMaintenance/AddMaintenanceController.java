package VehicleMaintenance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddMaintenanceController {

	@FXML
	private TextField MaintenanceTypeTextField;
	@FXML
	private TextField AmountTextField;

	@FXML
	private TextArea DescriptionTextArea;

	@FXML
	private TextField VehicleRegIDTextField;

	@FXML
	private TextField WorkshopNameTextField;

	@FXML
	private DatePicker ScheduledDate_Datepicker;

	@FXML
	private DatePicker CompletionDate_Datepicker;

	@FXML
	private AnchorPane contentPane;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	void SaveRecord(ActionEvent event) {
		String maintenanceType = MaintenanceTypeTextField.getText();
		String amount = AmountTextField.getText();
		String description = DescriptionTextArea.getText();
		String vehicleRegID = VehicleRegIDTextField.getText();
		String workshopName = WorkshopNameTextField.getText();
		String scheduledDate = ScheduledDate_Datepicker.getValue().toString();
		String completionDate = CompletionDate_Datepicker.getValue().toString();

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			// Prepare the SQL statement with placeholders for the values
			String sql = "INSERT INTO Maintenance (MaintenanceType, Amount, Description, "
					+ "VehicleRegID, WorkshopName, ScheduledDate, CompletionDate, Status) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			statement = conn.prepareStatement(sql);

			// Set the values for the placeholders in the SQL statement
			statement.setString(1, maintenanceType);
			statement.setString(2, amount);
			statement.setString(3, description);
			statement.setString(4, vehicleRegID);
			statement.setString(5, workshopName);
			statement.setString(6, scheduledDate);
			statement.setString(7, completionDate);
			statement.setString(8, "Pending");

			// Execute the INSERT statement
			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				// Data inserted successfully
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setContentText("Data inserted successfully");
				alert.showAndWait();

				// Clear the input fields
				MaintenanceTypeTextField.clear();
				AmountTextField.clear();
				DescriptionTextArea.clear();
				VehicleRegIDTextField.clear();
				WorkshopNameTextField.clear();
				ScheduledDate_Datepicker.setValue(null);
				CompletionDate_Datepicker.setValue(null);
			}
		} catch (SQLException e) {
			// Alert message shown if there is an error during database interaction
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Error inserting data into the database: " + e.getMessage());
			alert.showAndWait();
		} finally {
			// Close the statement and connection in the finally block
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// Error closing statement
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// Error closing connection
				}
			}
		}
	}

}
