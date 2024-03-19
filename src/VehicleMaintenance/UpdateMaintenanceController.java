package VehicleMaintenance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class UpdateMaintenanceController {

	@FXML
	private TextField AmountTextField;

	@FXML
	private DatePicker CompletionDate_Datepicker;

	@FXML
	private Pane DataPane;

	@FXML
	private TextArea DescriptionTextArea;

	@FXML
	private TextField MaintenanceTypeTextField;

	@FXML
	private TextField Maintenance_IDTextField;

	@FXML
	private DatePicker ScheduledDate_Datepicker;

	@FXML
	private JFXButton SearchButton;

	@FXML
	private JFXButton UpdateRecordButton;

	@FXML
	private TextField VehicleRegIDTextField;

	@FXML
	private TextField WorkshopNameTextField;

	@FXML
	private ChoiceBox<String> StatusChoiceBox;

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
	public void initialize() {
		StatusChoiceBox.getItems().addAll("Completed", "Cancelled", "Pending");

	}

	@FXML
	void SearchRecord(ActionEvent event) {
		String MaintenanceID = Maintenance_IDTextField.getText();
		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			// Execute the search query
			String sqlQuery = "SELECT * FROM Maintenance WHERE MaintenanceID = ? OR VehicleRegID = ?";
			statement = conn.prepareStatement(sqlQuery);
			statement.setString(1, MaintenanceID);
			statement.setString(2, MaintenanceID);
			ResultSet rs = statement.executeQuery();

			// Check if a record is found and show on TextFields
			if (rs.next()) {
				// Update the UI text fields with the retrieved data
				MaintenanceTypeTextField.setText(rs.getString("MaintenanceType"));
				WorkshopNameTextField.setText(rs.getString("WorkshopName"));
				AmountTextField.setText(rs.getString("Amount"));
				DescriptionTextArea.setText(rs.getString("Description"));
				VehicleRegIDTextField.setText(rs.getString("VehicleRegID"));

				// Setting date on textfield picked from database
				LocalDate localDate = LocalDate.parse(rs.getString("ScheduledDate"));
				ScheduledDate_Datepicker.setValue(localDate);

				String CompletionDate = rs.getString("CompletionDate");
				LocalDate localDate2 = LocalDate.parse(CompletionDate);
				CompletionDate_Datepicker.setValue(localDate2);
				Maintenance_IDTextField.setText(rs.getString("MaintenanceID"));
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
			String MaintenanceID = Maintenance_IDTextField.getText();

			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Prepare the SQL statement to update the record
			String updateQuery = " UPDATE Maintenance  SET MaintenanceType = ?, Amount = ?,"
					+ "       Description = ?,     VehicleRegID = ?,      WorkshopName = ?,"
					+ "       ScheduledDate = ?,      CompletionDate = ?, Status = ? WHERE MaintenanceID = ?;";
			statement = conn.prepareStatement(updateQuery);
			// Set the parameter values
			statement.setString(1, MaintenanceTypeTextField.getText().trim());
			statement.setString(2, AmountTextField.getText().trim());
			statement.setString(3, DescriptionTextArea.getText().trim());
			statement.setString(4, VehicleRegIDTextField.getText().trim());
			statement.setString(5, WorkshopNameTextField.getText().trim());
			statement.setString(6, ScheduledDate_Datepicker.getValue().toString().trim());
			statement.setString(7, CompletionDate_Datepicker.getValue().toString().trim());
			statement.setString(8, StatusChoiceBox.getValue().toString().trim());
			statement.setString(9, MaintenanceID);

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

}
