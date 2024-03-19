package ManageVehicle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddVehicleController {

	@FXML
	private TextField vehicleTypeField;
	@FXML
	private TextField vehicleModelField;
	@FXML
	private TextField registrationNumberField;
	@FXML
	private TextField vehicleCapacityField;
	@FXML
	private TextField accidentalStatusField;
	@FXML
	private TextField fuelEfficiencyField;
	@FXML
	private TextField driverIdField;
	@FXML
	private TextField routeIdField;
	@FXML
	private CheckBox tokenStatusCheckbox;
	@FXML
	private TextField tokenNumberField;
	@FXML
	private TextField tokenTypeField;
	@FXML
	private DatePicker tokenIssueDateField;
	@FXML
	private DatePicker tokenExpiryDateField;

	// SQLite database connection
	private Connection conn;
	PreparedStatement pstmt;

	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	void SaveRecord(ActionEvent event) {

		// get the values from the text fields and checkboxes
		String vehicleType = vehicleTypeField.getText();
		String vehicleModel = vehicleModelField.getText();
		String registrationNumber = registrationNumberField.getText();
		String vehicleCapacity = vehicleCapacityField.getText();
		String accidentalStatus = accidentalStatusField.getText();
		String fuelEfficiency = fuelEfficiencyField.getText();
		String driverId = driverIdField.getText();
		String routeId = routeIdField.getText();
		boolean tokenStatus = tokenStatusCheckbox.isSelected();
		String tokenNumber = tokenNumberField.getText();
		String tokenType = tokenTypeField.getText();
		String tokenIssueDate = tokenIssueDateField.getValue().toString();
		String tokenExpiryDate = tokenExpiryDateField.getValue().toString();

		// prepare the SQL statement to insert the vehicle record into the database
		String sqlQuery = "INSERT INTO vehicles (vehicle_type, vehicle_model, registration_number, "
				+ "vehicle_capacity, vehicle_occupation, accidental_status, fuel_efficiency, driver_id, route_id, "
				+ "token_status, token_number, token_type, token_issue_date, token_expiry_date) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			pstmt = conn.prepareStatement(sqlQuery);
			pstmt.setString(1, vehicleType);
			pstmt.setString(2, vehicleModel);
			pstmt.setString(3, registrationNumber);
			pstmt.setString(4, vehicleCapacity);
			pstmt.setString(5, "");
			pstmt.setString(6, accidentalStatus);
			pstmt.setString(7, fuelEfficiency);
			pstmt.setString(8, driverId);
			pstmt.setString(9, routeId);
			pstmt.setBoolean(10, tokenStatus);
			if (tokenStatusCheckbox.isSelected()) {
				pstmt.setString(11, tokenNumber);
				pstmt.setString(12, tokenType);
				pstmt.setString(13, tokenIssueDate);
				pstmt.setString(14, tokenExpiryDate);
			}

			pstmt.executeUpdate();
			// Alert message showed after Record saved
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setContentText("Vehicle data has been saved to the database.\n Vehicle Registration #: "
					+ registrationNumberField.getText());
			alert.showAndWait();

			// clear textfields after saving record
			vehicleTypeField.setText("");
			vehicleModelField.setText("");
			registrationNumberField.setText("");
			vehicleCapacityField.setText("");
			accidentalStatusField.setText("");
			fuelEfficiencyField.setText("");
			driverIdField.setText("");
			routeIdField.setText("");
			tokenNumberField.setText("");
			tokenTypeField.setText("");
			tokenIssueDateField.setValue(null);
			tokenExpiryDateField.setValue(null);

		} catch (SQLException e) {

			// Alert message showed after Record saved
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Failed!");
			alert.setContentText("Error! Vehicle record is not saved: " + e.getMessage());
			alert.showAndWait();
		} finally {
			// Close the connection in the finally block
			if (conn != null) {
				try {
					conn.close();
					pstmt.close();

				} catch (SQLException e) {
				}
			}

		}
	}

	@FXML
	public void initialize() {
		tokenNumberField.setVisible(false);
		tokenTypeField.setVisible(false);
		tokenExpiryDateField.setVisible(false);
		tokenIssueDateField.setVisible(false);

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
