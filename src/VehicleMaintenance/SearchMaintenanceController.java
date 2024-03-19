package VehicleMaintenance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class SearchMaintenanceController {

	@FXML
	private TextField AmountTextField;

	@FXML
	private TextField CompletionDateTextField;

	@FXML
	private Pane DataPane;

	@FXML
	private TextArea DescriptionTextArea;

	@FXML
	private TextField MaintenanceTypeTextField;

	@FXML
	private TextField Maintenance_IDTextField;

	@FXML
	private TextField ScheduledDateTextField;

	@FXML
	private TextField VehicleRegIDTextField;

	@FXML
	private TextField WorkshopNameTextField;

	@FXML
	private TextField StatusTextField;

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
				ScheduledDateTextField.setText(rs.getString("ScheduledDate"));
				CompletionDateTextField.setText(rs.getString("CompletionDate"));
				Maintenance_IDTextField.setText(rs.getString("MaintenanceID"));
				StatusTextField.setText(rs.getString("Status"));

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

}
