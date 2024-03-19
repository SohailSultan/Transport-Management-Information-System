package ManageStudents;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class SearchStudentsController {

	@FXML
	private Pane DataPane;

	@FXML
	private TextField FeeStatusTextField, StatusTextField;

	@FXML
	private TextField GenderTextField;

	@FXML
	private TextField PicknDropPointTextField;

	@FXML
	private TextField SAPIDTextField;

	@FXML
	private TextField SemesterTextField;

	@FXML
	private TextField Student_IDTextField;

	@FXML
	private TextField addressTextField;

	@FXML
	private TextField busIdTextField;

	@FXML
	private TextField cnicTextField;

	@FXML
	private TextField contactTextField;

	@FXML
	private AnchorPane contentPane;

	@FXML
	private TextField departmentTextField;

	@FXML
	private TextField dobTextField;

	@FXML
	private TextField fatherNameTextField;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField routeIdTextField;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	void SearchRecord(ActionEvent event) {
		String SearchField = Student_IDTextField.getText();
		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			// Execute the search query
			String sqlQuery = "SELECT * FROM students WHERE StudentID = ? OR SAPID = ? OR CNIC = ?";
			statement = conn.prepareStatement(sqlQuery);
			statement.setString(1, SearchField);
			statement.setString(2, SearchField);
			statement.setString(3, SearchField);
			ResultSet rs = statement.executeQuery();

			// Check if a record is found
			if (rs.next()) {
				// Update the UI text fields with the retrieved data
				SAPIDTextField.setText(rs.getString("SAPID"));
				nameTextField.setText(rs.getString("Name"));
				fatherNameTextField.setText(rs.getString("FatherName"));
				GenderTextField.setText(rs.getString("gender"));
				addressTextField.setText(rs.getString("Address"));
				dobTextField.setText(rs.getString("DOB"));
				cnicTextField.setText(rs.getString("CNIC"));
				contactTextField.setText(rs.getString("Contact"));
				SemesterTextField.setText(rs.getString("Semester"));
				departmentTextField.setText(rs.getString("Department"));
				PicknDropPointTextField.setText(rs.getString("PicknDropPoint"));
				FeeStatusTextField.setText(rs.getString("FeeStatus"));
				routeIdTextField.setText(rs.getString("RouteID"));
				busIdTextField.setText(rs.getString("BusID"));
				Student_IDTextField.setText(rs.getString("StudentID"));
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
