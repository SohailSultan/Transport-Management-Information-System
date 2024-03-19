package ManageReservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class UpdateReservationController {

	@FXML
	private Pane DataPane;

	@FXML
	private TextField DestinationArrivalTextField;

	@FXML
	private TextField DestinationDeptTextField;

	@FXML
	private TextField DestinationNameTextField;

	@FXML
	private TextField DriverIDTextField;

	@FXML
	private TextField EventHolderNameTextField;

	@FXML
	private TextField EventNameTextField;

	@FXML
	private TextField EventTypeTextField;

	@FXML
	private TextField Reservation_IDTextField;

	@FXML
	private DatePicker ScheduledDate_Datepicker;

	@FXML
	private Button SearchButton;

	@FXML
	private TextField UniversityArrivalTextField;

	@FXML
	private TextField UniversityDeptTextField;

	@FXML
	private Button UpdateRecordButton;

	@FXML
	private TextField VehicleRegNoTextField;

	@FXML
	private ChoiceBox<String> StatusChoiceBox;

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
		String reservationId = Reservation_IDTextField.getText();
		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			String query = "SELECT * FROM Reservation WHERE ReservationID = ?";
			statement = conn.prepareStatement(query);
			statement.setString(1, reservationId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				// Populate the text fields with the retrieved data
				EventNameTextField.setText(resultSet.getString("EventName"));
				EventTypeTextField.setText(resultSet.getString("EventType"));
				EventHolderNameTextField.setText(resultSet.getString("EventHolderName"));
				DestinationNameTextField.setText(resultSet.getString("DestinationName"));
				DestinationArrivalTextField.setText(resultSet.getString("DestinationArrival"));
				DestinationDeptTextField.setText(resultSet.getString("DestinationDept"));
				UniversityDeptTextField.setText(resultSet.getString("UniversityDept"));
				UniversityArrivalTextField.setText(resultSet.getString("UniversityArrival"));
				VehicleRegNoTextField.setText(resultSet.getString("VehicleRegNo"));
				DriverIDTextField.setText(resultSet.getString("DriverID"));
				ScheduledDate_Datepicker.setValue(LocalDate.parse(resultSet.getString("ScheduledDate")));
				StatusChoiceBox.setValue(resultSet.getString("Status"));
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
	void UpdateRecord(ActionEvent event) throws SQLException {
		// Prompt the user to update the record
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Update Record");
		alert.setHeaderText("Update Conductor Record");
		alert.setContentText("Do you want to update the record?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			String ReservationID = Reservation_IDTextField.getText();

			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Prepare the SQL statement to update the record
			String updateQuery = "UPDATE Reservation SET DestinationArrival = ?, DestinationDept = ?, "
					+ "DestinationName = ?, DriverID = ?, EventHolderName = ?, EventName = ?, EventType = ?, "
					+ "ScheduledDate = ?, UniversityArrival = ?, UniversityDept = ?, "
					+ "VehicleRegNo = ?, Status = ? WHERE ReservationID = ?";
			statement = conn.prepareStatement(updateQuery);
			// Set the parameter values
			statement.setString(1, DestinationArrivalTextField.getText());
			statement.setString(2, DestinationDeptTextField.getText());
			statement.setString(3, DestinationNameTextField.getText());
			statement.setString(4, DriverIDTextField.getText());
			statement.setString(5, EventHolderNameTextField.getText());
			statement.setString(6, EventNameTextField.getText());
			statement.setString(7, EventTypeTextField.getText());
			statement.setString(8, ScheduledDate_Datepicker.getValue().toString());
			statement.setString(9, UniversityArrivalTextField.getText());
			statement.setString(10, UniversityDeptTextField.getText());
			statement.setString(11, VehicleRegNoTextField.getText());
			statement.setString(12, StatusChoiceBox.getValue().toString());
			statement.setString(13, ReservationID);

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
