package ManageVehicle;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class UpdateVehicleController {

	@FXML
	private Pane DataPane;

	@FXML
	private JFXButton SearchButton;

	@FXML
	private CheckBox tokenStatusCheckbox;

	@FXML
	private JFXButton UpdateRecordButton;

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
	private DatePicker tokenExpiryDateField;

	@FXML
	private DatePicker tokenIssueDateField;

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
				// Setting date on textfield picked from database

				accidentalStatusField.setText(rs.getString("accidental_status"));
				driverIdField.setText(rs.getString("driver_id"));
				fuelEfficiencyField.setText(rs.getString("fuel_efficiency"));
				registrationNumberField.setText(rs.getString("registration_number"));
				routeIdField.setText(rs.getString("route_id"));
				if ((rs.getString("token_status")).equals("1")) {
					tokenStatusCheckbox.setSelected(true);
					tokenNumberField.setVisible(true);
					tokenTypeField.setVisible(true);
					tokenExpiryDateField.setVisible(true);
					tokenIssueDateField.setVisible(true);

					tokenNumberField.setText(rs.getString("token_number"));
					tokenTypeField.setText(rs.getString("token_type"));
					tokenExpiryDateField.setValue(LocalDate.parse(rs.getString("token_expiry_date")));
					tokenIssueDateField.setValue(LocalDate.parse(rs.getString("token_issue_date")));
				} else {
					tokenStatusCheckbox.setSelected(false);
					tokenNumberField.setVisible(false);
					tokenTypeField.setVisible(false);
					tokenExpiryDateField.setVisible(false);
					tokenIssueDateField.setVisible(false);
				}

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
	void UpdateRecord(ActionEvent event) throws SQLException {
		// Prompt the user to update the record
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Update Record");
		alert.setHeaderText("Update Conductor Record");
		alert.setContentText("Do you want to update the record?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			String VehicleID = Vehicle_IDTextField.getText();

			boolean tokenStatus = tokenStatusCheckbox.isSelected();

			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Prepare the SQL statement to update the record
			String updateQuery = "UPDATE vehicles SET vehicle_type = ?, vehicle_model = ?, "
					+ "registration_number = ?, vehicle_capacity = ?, "
					+ "vehicle_occupation = ?, accidental_status = ?, fuel_efficiency = ?, "
					+ "driver_id = ?, route_id = ?, token_status = ?, token_number = ?, "
					+ "token_type = ?, token_issue_date = ?, token_expiry_date = ? WHERE vehicle_id = ?";
			statement = conn.prepareStatement(updateQuery);

			// Set the parameter values
			statement.setString(1, vehicleTypeField.getText());
			statement.setString(2, vehicleModelField.getText());
			statement.setString(3, registrationNumberField.getText());
			statement.setString(4, vehicleCapacityField.getText());
			statement.setString(5, vehicleOccupationField.getText());
			statement.setString(6, accidentalStatusField.getText());
			statement.setString(7, fuelEfficiencyField.getText());
			statement.setString(8, driverIdField.getText());
			statement.setString(9, routeIdField.getText());
			statement.setBoolean(10, tokenStatus);

			if (tokenStatusCheckbox.isSelected()) {
				statement.setString(11, tokenNumberField.getText());
				statement.setString(12, tokenTypeField.getText());
				statement.setString(13, tokenIssueDateField.getValue().toString());
				statement.setString(14, tokenExpiryDateField.getValue().toString());
			}
			statement.setString(15, VehicleID);
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
			} // else ends here
		} // Outer If ends here
	} // method Update record ends here

	@FXML
	public void initialize() {
		tokenStatusCheckbox.setOnAction(e -> {
			if (tokenStatusCheckbox.isSelected()) {
				tokenNumberField.setVisible(true);
				tokenTypeField.setVisible(true);
				tokenExpiryDateField.setVisible(true);
				tokenIssueDateField.setVisible(true);
			} else if (!tokenStatusCheckbox.isSelected()) {
				tokenNumberField.setVisible(false);
				tokenTypeField.setVisible(false);
				tokenExpiryDateField.setVisible(false);
				tokenIssueDateField.setVisible(false);
			}
		});
	}

}
