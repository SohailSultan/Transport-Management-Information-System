package ManageReservation;

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
import javafx.scene.layout.Pane;

public class SearchReservationController {

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
	private TextField ScheduledDateTextField;

	@FXML
	private Button SearchButton;

	@FXML
	private TextField UniversityArrivalTextField;

	@FXML
	private TextField UniversityDeptTextField;

	@FXML
	private TextField VehicleRegNoTextField;

	@FXML
	private TextField StatusTextField;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

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
				UniversityDeptTextField.setText(resultSet.getString("UniversityDept"));
				UniversityArrivalTextField.setText(resultSet.getString("UniversityArrival"));
				VehicleRegNoTextField.setText(resultSet.getString("VehicleRegNo"));
				DriverIDTextField.setText(resultSet.getString("DriverID"));
				ScheduledDateTextField.setText(resultSet.getString("ScheduledDate"));
				StatusTextField.setText(resultSet.getString("Status"));
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
