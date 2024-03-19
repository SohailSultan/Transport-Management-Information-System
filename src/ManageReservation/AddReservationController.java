package ManageReservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddReservationController {

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
	private DatePicker ScheduledDate_Datepicker;
	@FXML
	private TextField UniversityArrivalTextField;
	@FXML
	private TextField UniversityDeptTextField;
	@FXML
	private TextField VehicleRegNoTextField;
	@FXML
	private AnchorPane contentPane;

	// SQLite database connection
	private Connection conn;
	private PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	public void initialize() {
		SimpleDateFormat DateDF = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = DateDF.format(new Date());
		ScheduledDate_Datepicker.setValue(LocalDate.parse(currentDate));
	}

	@FXML
	void SaveRecord(ActionEvent event) {
		// Retrieve the input field values
		String destinationArrival = DestinationArrivalTextField.getText();
		String destinationDept = DestinationDeptTextField.getText();
		String destinationName = DestinationNameTextField.getText();
		String driverID = DriverIDTextField.getText();
		String eventHolderName = EventHolderNameTextField.getText();
		String eventName = EventNameTextField.getText();
		String eventType = EventTypeTextField.getText();
		String scheduledDate = ScheduledDate_Datepicker.getValue().toString();
		String universityArrival = UniversityArrivalTextField.getText();
		String universityDept = UniversityDeptTextField.getText();
		String vehicleRegNo = VehicleRegNoTextField.getText();

		// Construct the SQL query for inserting a new record
		String query = "INSERT INTO Reservation (DestinationArrival, DestinationDept, DestinationName, DriverID, EventHolderName, EventName, "
				+ "EventType, ScheduledDate, UniversityArrival, UniversityDept, VehicleRegNo, Status) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		// Establish a connection to the SQLite database and execute the query
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			statement = conn.prepareStatement(query);
			statement.setString(1, destinationArrival);
			statement.setString(2, destinationDept);
			statement.setString(3, destinationName);
			statement.setString(4, driverID);
			statement.setString(5, eventHolderName);
			statement.setString(6, eventName);
			statement.setString(7, eventType);
			statement.setString(8, scheduledDate);
			statement.setString(9, universityArrival);
			statement.setString(10, universityDept);
			statement.setString(11, vehicleRegNo);
			statement.setString(12, "Pending");

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				// Alert message shown after successful insertion
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setContentText("Record inserted successfully!");
				alert.showAndWait();

				// Clear the input fields
				DestinationArrivalTextField.setText("");
				DestinationDeptTextField.setText("");
				DestinationNameTextField.setText("");
				DriverIDTextField.setText("");
				EventHolderNameTextField.setText("");
				EventNameTextField.setText("");
				EventTypeTextField.setText("");
				ScheduledDate_Datepicker.setValue(null);
				UniversityArrivalTextField.setText("");
				UniversityDeptTextField.setText("");
				VehicleRegNoTextField.setText("");
			}
		} catch (SQLException e) {
			// Alert message showed after an error occurred during insertion
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Error occurred during insertion: " + e.getMessage());
			alert.showAndWait();
		} finally {
			// Close the statement and connection in the finally block
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
