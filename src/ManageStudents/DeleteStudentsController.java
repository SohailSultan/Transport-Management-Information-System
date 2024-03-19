package ManageStudents;

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

public class DeleteStudentsController {

	@FXML
	private Pane DataPane;

	@FXML
	private JFXButton DeleteButton;

	@FXML
	private TextField FeeStatusTextField;

	@FXML
	private TextField GenderTextField;

	@FXML
	private TextField PicknDropPointTextField;

	@FXML
	private TextField SAPIDTextField;

	@FXML
	private JFXButton SearchButton;

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
			String StudentID = Student_IDTextField.getText();

			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			// Execute the search query
			String sqlQuery = "DELETE FROM students WHERE StudentID = ?";
			statement = conn.prepareStatement(sqlQuery);
			statement.setString(1, StudentID);
			// Execute the delete statement
			int rowsDeleted = statement.executeUpdate();

			if (rowsDeleted > 0) {
				Alert successAlert = new Alert(AlertType.INFORMATION);
				successAlert.setTitle("Deletion Successful");
				successAlert.setContentText(" Record deleted successfully.");
				successAlert.showAndWait();

				// clear textfields after deleting record
				SAPIDTextField.clear();
				nameTextField.clear();
				fatherNameTextField.clear();
				dobTextField.clear();
				SemesterTextField.clear();
				departmentTextField.clear();
				contactTextField.clear();
				cnicTextField.clear();
				PicknDropPointTextField.clear();
				routeIdTextField.clear();
				busIdTextField.clear();
				addressTextField.clear();
				FeeStatusTextField.clear();
				GenderTextField.clear();
			} else {
				// Display Failure message
				Alert successAlert = new Alert(AlertType.INFORMATION);
				successAlert.setTitle("Deletion Failed");
				successAlert.setContentText(" Record is not deleted.");
				successAlert.showAndWait();
			} // else closed here

		} // outer IF closed here
	} // delete method ends here

}
