package ManageFee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UpdateFeeRecordController {

	@FXML
	private TextField AmountTextField;
	@FXML
	private TextField TotalAmountTextField;

	@FXML
	private TextField CNICTextField;
	@FXML
	private TextField FeeIDTextField;
	@FXML
	private TextField CustomerNameTextField;

	@FXML
	private DatePicker DateTextField;
	@FXML
	private DatePicker NextDateTextField;

	@FXML
	private TextField DepartmentTextField;

	@FXML
	private TextField DiscountTextField;

	@FXML
	private TextField FatherNameTextField;

	@FXML
	private TextField TotalMonthTextField;

	@FXML
	private ChoiceBox<String> PaidByChoiceBox;
	@FXML
	private ChoiceBox<String> Pay_PlanChoiceBox;
	@FXML
	private Button SaveRecordButton;
	@FXML
	private Label AddmonthLabel, TotalMonthsLabel;

	@FXML
	private TextField AddmonthTextField;

	@FXML
	private AnchorPane contentPane;

	@FXML
	private Hyperlink CheckCNICLabel;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	void SearchRecord(ActionEvent event) {
		String FeeID = FeeIDTextField.getText(); // Get the Fee ID from the text field
		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Prepare the SQL statement to search for the record
			String sql = "SELECT * FROM fee_records WHERE feeID = ? OR cnic = ?";
			statement = conn.prepareStatement(sql);
			statement.setString(1, FeeID);
			statement.setString(2, CNICTextField.getText());

			// Execute the query and retrieve the result
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {

				/*
				 * Retrieve the information from the result set AND Display the information in
				 * the text fields after putting resultset data in textfields
				 **/
				AmountTextField.setText(resultSet.getString("amount"));
				PaidByChoiceBox.setValue(resultSet.getString("paidBy"));
				DateTextField.setValue(LocalDate.parse(resultSet.getString("date")));
				CustomerNameTextField.setText(resultSet.getString("customerName"));
				CNICTextField.setText(resultSet.getString("cnic"));
				DiscountTextField.setText(resultSet.getString("discount"));
				FatherNameTextField.setText(resultSet.getString("fatherName"));
				DepartmentTextField.setText(resultSet.getString("department"));
				Pay_PlanChoiceBox.setValue(resultSet.getString("paymentPlan"));
				NextDateTextField.setValue(LocalDate.parse(resultSet.getString("NextDueDate")));
				FeeIDTextField.setText(resultSet.getString("feeID"));
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
			String FeeID = FeeIDTextField.getText();

			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			LocalDate currentDate = DateTextField.getValue()
					.plusMonths(Integer.parseInt(TotalMonthTextField.getText()));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM"); // Format for full month name
			String monthFullName = currentDate.format(formatter); // Get month in full name format
			String year = Integer.toString(currentDate.getYear());

			// Prepare the SQL statement to update the record
			String updateQuery = "UPDATE fee_records SET  amount = ?, paidBy = ?, date = ?, customerName = ?, cnic = ?, discount = ?,"
					+ "month = ?, year = ?, fatherName = ?, department = ?, paymentPlan = ?, "
					+ " NextDueDate = ?  WHERE feeID = ?";
			statement = conn.prepareStatement(updateQuery);
			// Update the record with the new data
			statement.setString(1, AmountTextField.getText().trim());
			statement.setString(2, PaidByChoiceBox.getValue().trim());
			statement.setString(3, currentDate.toString());
			statement.setString(4, CustomerNameTextField.getText().trim());
			statement.setString(5, CNICTextField.getText().trim());
			statement.setString(6, DiscountTextField.getText().trim());
			statement.setString(7, monthFullName);
			statement.setString(8, year);
			statement.setString(9, FatherNameTextField.getText().trim());
			statement.setString(10, DepartmentTextField.getText().trim());
			statement.setString(11, Pay_PlanChoiceBox.getValue());
			statement.setString(12, NextDateTextField.getValue().toString());
			statement.setString(13, FeeID);

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

	@FXML
	void ClearFields() {
		AmountTextField.clear();
		PaidByChoiceBox.getSelectionModel().clearSelection();
		CustomerNameTextField.clear();
		CNICTextField.clear();
		DiscountTextField.clear();
		FatherNameTextField.clear();
		DepartmentTextField.clear();
		NextDateTextField.setValue(null);
		Pay_PlanChoiceBox.getSelectionModel().clearSelection();

	}

	private boolean isValidCNIC(String cnic) {
		String regex = "^[0-9]{5}-[0-9]{7}-[0-9]$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(cnic);
		return matcher.matches();
	}

	/*
	 * _________________________________________Field Validation
	 * Part____________________________________________________
	 */

	@FXML
	public void initialize() {

		AddmonthTextField.setOnAction(e -> {
			int additional = Integer.parseInt(AddmonthTextField.getText());
			TotalMonthTextField.setText(String.valueOf(additional + 5));
			NextDateTextField
					.setValue(DateTextField.getValue().plusMonths(Integer.parseInt(TotalMonthTextField.getText())));

		});

		// Initialize the choice box values
		PaidByChoiceBox.getItems().addAll("Student", "Faculty", "Other");
		Pay_PlanChoiceBox.getItems().addAll("Semester-wise", "Half semester", "Monthly", "Full+Advance");

		CNICTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!isValidCNIC(newValue)) {
				CNICTextField.setStyle("-fx-border-color: red; -fx-text-fill: red;");
			} else {
				CNICTextField.setStyle("-fx-border-color: green; -fx-text-fill: green;");
			}
		});

	}

}
