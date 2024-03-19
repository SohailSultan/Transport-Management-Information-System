package ManageRoute;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class SearchRouteController {

	@FXML
	private TextField BusIDTextField;

	@FXML
	private TextField Conductor_IDTextField;

	@FXML
	private Pane DataPane;

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
	private TextField Route_IDTextField;

	@FXML
	private Button SearchButton;

	@FXML
	private TextField StartNameTextField;

	@FXML
	private TextArea StopsNameTextArea;

	@FXML
	private AnchorPane contentPane;

	@FXML
	private TextField routenameTextField;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	void SearchRecord(ActionEvent event) {
		String routeID = Route_IDTextField.getText();
		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			// Execute the search query
			String sqlQuery = "SELECT * FROM Routes WHERE route_id = ?";
			statement = conn.prepareStatement(sqlQuery);
			statement.setString(1, routeID);
			ResultSet rs = statement.executeQuery();

			// Check if a record is found
			if (rs.next()) {
				// Update the UI text fields with the retrieved data
				routenameTextField.setText(rs.getString("route_name"));
				StartNameTextField.setText(rs.getString("start_name"));
				EndNameTextField.setText(rs.getString("end_name"));
				BusIDTextField.setText(rs.getString("bus_id"));
				Conductor_IDTextField.setText(rs.getString("conductor_id"));
				Driver_IDTextField.setText(rs.getString("driver_id"));
				FairsTextField.setText(rs.getString("fairs"));
				OccupationTextField.setText(rs.getString("occupation"));
				PickupTimeTextField.setText(rs.getString("pickup_time"));
				ReachTimeTextField.setText(rs.getString("reach_time"));
				StopsNameTextArea.setText(rs.getString("stops_name"));
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
} // class ends here
