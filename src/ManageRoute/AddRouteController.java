package ManageRoute;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddRouteController {

	@FXML
	private TextField BusIDTextField;

	@FXML
	private TextField Conductor_IDTextField;

	@FXML
	private TextField Driver_IDTextField;

	@FXML
	private TextField EndNameTextField;

	@FXML
	private TextField FairsTextField;

	@FXML
	private TextField OccupationTextField;

	@FXML
	private TextField PickupTimeTextField;

	@FXML
	private TextField ReachTimeTextField;

	@FXML
	private Button SaveButton;

	@FXML
	private TextField StartNameTextField;

	@FXML
	private AnchorPane contentPane;

	@FXML
	private TextField routeIdTextField;

	@FXML
	private TextField routenameTextField;

	@FXML
	private TextField StopsNameTextField;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	void SaveRecord(ActionEvent event) {
		// Retrieve the input values from the text fields
		String busID = BusIDTextField.getText();
		String conductorID = Conductor_IDTextField.getText();
		String driverID = Driver_IDTextField.getText();
		String endName = EndNameTextField.getText();
		String fairs = FairsTextField.getText();
		String occupation = OccupationTextField.getText();
		String pickupTime = PickupTimeTextField.getText();
		String reachTime = ReachTimeTextField.getText();
		String startName = StartNameTextField.getText();
		String routeName = routenameTextField.getText();
		String stopsName = StopsNameTextField.getText();

		// Define the SQL insert statement
		String sqlQuery = "INSERT INTO routes (bus_id, conductor_id, driver_id, end_name, fairs, occupation, "
				+ "pickup_time, reach_time, start_name, route_name, stops_name) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		// Create a try-with-resources block to automatically close the resources
		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			// Set the parameter values in the prepared statement
			statement = conn.prepareStatement(sqlQuery);
			statement.setString(1, busID);
			statement.setString(2, conductorID);
			statement.setString(3, driverID);
			statement.setString(4, endName);
			statement.setString(5, fairs);
			statement.setString(6, occupation);
			statement.setString(7, pickupTime);
			statement.setString(8, reachTime);
			statement.setString(9, startName);
			statement.setString(10, routeName);
			statement.setString(11, stopsName);

			// Execute the SQL statement
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				// Alert message showed after Record saved
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setContentText("Data has been saved to the database.");
				alert.showAndWait();
			} else {
				// Alert message showed after Record is not saved
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Failed!");
				alert.setContentText("Error! record is not saved: ");
				alert.showAndWait();
			}

		} catch (SQLException e) {
			// Alert message showed after database not connected
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Failed!");
			alert.setContentText("Error! Database Error: " + e.getMessage());
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
