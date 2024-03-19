package ManageConductor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class SearchConductorController {

	@FXML
	private TextField Bus_IDTextField;

	@FXML
	private TextField IDTextField;
	@FXML
	private TextField ConductorIDTextField;
	@FXML
	private Button SearchButton;

	@FXML
	private TextField ageTextField;

	@FXML
	private TextField contactTextField;

	@FXML
	private AnchorPane contentPane;

	@FXML
	private TextField fatherNameTextField;

	@FXML
	private TextField idCardTextField;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField salaryTextField;
	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	void SearchRecord() {

		String conductorID = IDTextField.getText(); // Get the conductor ID from the text field
		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Prepare the SQL statement to search for the conductor record
			String sql = "SELECT * FROM conductor WHERE conductor_id = ? OR id_card = ?";
			statement = conn.prepareStatement(sql);
			statement.setString(1, conductorID);
			statement.setString(2, conductorID);
			// Execute the query and retrieve the result
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				// Retrieve the conductor information from the result set

				String name = resultSet.getString("name");
				String age = resultSet.getString("age");
				String contact = resultSet.getString("contact");
				String fatherName = resultSet.getString("father_name");
				String salary = resultSet.getString("salary");
				String busID = resultSet.getString("Vehicle_Regno");
				String idCard = resultSet.getString("id_card");
				String id = resultSet.getString("conductor_id");

				// Display the conductor information in the text fields
				nameTextField.setText(name);
				ageTextField.setText(age);
				contactTextField.setText(contact);
				fatherNameTextField.setText(fatherName);
				salaryTextField.setText(salary);
				Bus_IDTextField.setText(busID);
				idCardTextField.setText(idCard);
				ConductorIDTextField.setText(id);
			} else {
				ConductorIDTextField.setText(conductorID);
				// Display an alert if no matching record is found
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("No Record Found");
				alert.setContentText("No conductor record found for the given Conductor ID.");
				alert.showAndWait();
			}

		} catch (SQLException e) {
			// Alert message shown if there's an error with the database or query
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Error occurred while searching the conductor record: " + e.getMessage());
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

		} // finally ends here
	} // search method ends here

	// press enter after write ID in TextField to search record
	@FXML
	public void initialize() {
		IDTextField.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				SearchRecord();
			}
		});
	}

}
