package ManageVehicle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class DeleteVehicleController {

	@FXML
	private Pane DataPane;

	@FXML
	private JFXButton DeleteRecordButton;

	@FXML
	private JFXButton SearchButton;

	@FXML
	private TextField Vehicle_IDTextField;

	@FXML
	private TextField accidentalStatusField;

	@FXML
	private AnchorPane contentPane;

	@FXML
	private TextField driverIdField;

	@FXML
	private TextField fuelEfficiencyField;

	@FXML
	private TextField registrationNumberField;

	@FXML
	private TextField routeIdField;

	@FXML
	private TextField tokenExpiryDateField;

	@FXML
	private TextField tokenIssueDateField;

	@FXML
	private TextField tokenNumberField;

	@FXML
	private TextField tokenTypeField;

	@FXML
	private TextField vehicleCapacityField;

	@FXML
	private TextField vehicleModelField;

	@FXML
	private TextField vehicleOccupationField;

	@FXML
	private TextField vehicleTypeField;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	void SearchRecord(ActionEvent event) {
		String VehicleID = Vehicle_IDTextField.getText();
		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			// Execute the search query
			String sqlQuery = "SELECT * FROM vehicles WHERE vehicle_id = ? OR registration_number = ?";
			statement = conn.prepareStatement(sqlQuery);
			statement.setString(1, VehicleID);
			statement.setString(2, VehicleID);
			ResultSet rs = statement.executeQuery();

			// Check if a record is found
			if (rs.next()) {
				// Retrieve data from the result set And
				// Update the UI text fields with the retrieved data
				accidentalStatusField.setText(rs.getString("accidental_status"));
				driverIdField.setText(rs.getString("driver_id"));
				fuelEfficiencyField.setText(rs.getString("fuel_efficiency"));
				registrationNumberField.setText(rs.getString("registration_number"));
				routeIdField.setText(rs.getString("route_id"));
				tokenExpiryDateField.setText(rs.getString("token_expiry_date"));
				tokenIssueDateField.setText(rs.getString("token_issue_date"));
				tokenNumberField.setText(rs.getString("token_number"));
				tokenTypeField.setText(rs.getString("token_type"));
				vehicleCapacityField.setText(rs.getString("vehicle_capacity"));
				vehicleModelField.setText(rs.getString("vehicle_model"));
				vehicleOccupationField.setText(rs.getString("vehicle_occupation"));
				vehicleTypeField.setText(rs.getString("vehicle_type"));
				Vehicle_IDTextField.setText(rs.getString("vehicle_id"));

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
	void DeleteRecord(ActionEvent event) throws SQLException {
		// Confirm deletion from user
		Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
		confirmationAlert.setTitle("Confirm Deletion");
		confirmationAlert.setContentText("Are you sure you want to delete this record?");
		Optional<ButtonType> result = confirmationAlert.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			String VehicleID = Vehicle_IDTextField.getText();

			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			// Execute the search query
			String sqlQuery = "DELETE FROM vehicles WHERE vehicle_id = ?";
			statement = conn.prepareStatement(sqlQuery);
			statement.setString(1, VehicleID);
			// Execute the delete statement
			int rowsDeleted = statement.executeUpdate();

			if (rowsDeleted > 0) {
				Alert successAlert = new Alert(AlertType.INFORMATION);
				successAlert.setTitle("Deletion Successful");
				successAlert.setContentText(" Record deleted successfully.");
				successAlert.showAndWait();

				// clear textfields after deleting record
				vehicleTypeField.setText("");
				vehicleModelField.setText("");
				registrationNumberField.setText("");
				vehicleCapacityField.setText("");
				vehicleOccupationField.setText("");
				accidentalStatusField.setText("");
				fuelEfficiencyField.setText("");
				driverIdField.setText("");
				routeIdField.setText("");
				tokenNumberField.setText("");
				tokenTypeField.setText("");
				tokenIssueDateField.setText("");
				tokenExpiryDateField.setText("");

			} else {
				// Display Failure message
				Alert successAlert = new Alert(AlertType.INFORMATION);
				successAlert.setTitle("Deletion Failed");
				successAlert.setContentText(" Record is not deleted.");
				successAlert.showAndWait();
			} // else closed here

		} // outer IF closed here
	}
}
