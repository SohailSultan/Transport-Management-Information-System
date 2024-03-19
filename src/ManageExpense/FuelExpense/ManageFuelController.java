package ManageExpense.FuelExpense;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ManageFuelController {

	@FXML
	private TableColumn<FuelRecord, String> AmountCol;

	@FXML
	private TextField AmountTextfield;

	@FXML
	private DatePicker Date;

	@FXML
	private TableColumn<FuelRecord, String> DateCol;

	@FXML
	private JFXButton DeleteButton;

	@FXML
	private TableColumn<FuelRecord, String> ExpIDCol;

	@FXML
	private TableView<FuelRecord> FuelExpenseTable;

	@FXML
	private TextField FuelLitreTextfield;

	@FXML
	private TableColumn<FuelRecord, String> Fuel_LitreCol;

	@FXML
	private TextField IDTextfield;

	@FXML
	private JFXButton InsertButton;

	@FXML
	private AnchorPane InsertPane;

	@FXML
	private TextField PMethodTextfield;

	@FXML
	private TableColumn<FuelRecord, String> PaymentCol;

	@FXML
	private JFXButton RefreshButton;

	@FXML
	private TableColumn<FuelRecord, String> RemarksCol;

	@FXML
	private TextField RemarksTextfield;

	@FXML
	private JFXButton UpdateButton;

	@FXML
	private TableColumn<FuelRecord, String> VRegCol;

	@FXML
	private TextField VRegNoTextfield;

	@FXML
	private JFXButton clearButton;

	@FXML
	private Pane screen_pane;

	@FXML
	private TextField searchTextField;

	Connection conn;
	PreparedStatement statement;

	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	void selectRecord() {
		FuelExpenseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				int ExpenseID = newSelection.getExpense_Id();
				IDTextfield.setText(String.valueOf(ExpenseID));
				VRegNoTextfield.setText(newSelection.getVehicle_RegNo());
				FuelLitreTextfield.setText(newSelection.getFuel_litre());
				AmountTextfield.setText(newSelection.getAmount());

				LocalDate localDate = LocalDate.parse(newSelection.getDate());
				Date.setValue(localDate);

				PMethodTextfield.setText(newSelection.getPayment_Method());
				RemarksTextfield.setText(newSelection.getRemarks());

			}
		});
	}

	@FXML
	void Delete_FuelExpense(ActionEvent event) {
		// Prompt the user to update the record
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Update Record");
		alert.setHeaderText("Update Conductor Record");
		alert.setContentText("Do you want to update the record?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			try {
				// Establish a connection to the SQLite database
				conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

				String deleteQuery = "DELETE FROM Fuel WHERE Expense_Id = ?";

				statement = conn.prepareStatement(deleteQuery);
				statement.setString(1, IDTextfield.getText());
				int rowsDeleted = statement.executeUpdate();

				if (rowsDeleted > 0) {
					Alert successAlert = new Alert(AlertType.INFORMATION);
					successAlert.setTitle("Deletion Successful");
					successAlert.setContentText(" Record deleted successfully.");
					successAlert.showAndWait();
				}

			} catch (SQLException e) {
				// Alert message shown if there is an error during database interaction
				Alert alert2 = new Alert(AlertType.ERROR);
				alert2.setTitle("Error");
				alert2.setContentText("Error inserting data into the database: \n" + e.getMessage());
				alert2.showAndWait();
			}
		}
	}

	@FXML
	void Insert_FuelExpense(ActionEvent event) {
		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			String insertQuery = "INSERT INTO Fuel ( Vehicle_RegNo, Fuel_litre, Amount, Date,"
					+ " Payment_Method, Remarks) VALUES(?, ?, ?, ?, ?, ?)";
			statement = conn.prepareStatement(insertQuery);
			statement.setString(1, VRegNoTextfield.getText());
			statement.setString(2, FuelLitreTextfield.getText());
			statement.setString(3, AmountTextfield.getText());
			statement.setString(4, Date.getValue().toString());
			statement.setString(5, PMethodTextfield.getText());
			statement.setString(6, RemarksTextfield.getText());

			// Execute the INSERT statement
			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				// Data inserted successfully
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setContentText("Data inserted successfully");
				alert.showAndWait();

			} else {
				// Display error message if the Insert failed
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setTitle("Error");
				errorAlert.setContentText("Failed to Insert record.");
				errorAlert.showAndWait();
			}
		} catch (SQLException e) {
			// Alert message shown if there is an error during database interaction
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Error inserting data into the database: \n" + e.getMessage());
			alert.showAndWait();
		}
	}

	@FXML
	void Update_FuelExpense(ActionEvent event) {
		// Prompt the user to update the record
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Update Record");
		alert.setHeaderText("Update Conductor Record");
		alert.setContentText("Do you want to update the record?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			try {
				// Establish a connection to the SQLite database
				conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				String UpdateQuery = "UPDATE Fuel SET Vehicle_RegNo = ?, Fuel_litre = ?,"
						+ " Amount= ?, Date = ?, Payment_Method = ?, Remarks = ?";
				statement = conn.prepareStatement(UpdateQuery);
				statement.setString(1, VRegNoTextfield.getText());
				statement.setString(2, FuelLitreTextfield.getText());
				statement.setString(3, AmountTextfield.getText());
				statement.setString(4, Date.getValue().toString());
				statement.setString(5, PMethodTextfield.getText());
				statement.setString(6, RemarksTextfield.getText());
				statement.setString(7, IDTextfield.getText());
				int rowsUpdated = statement.executeUpdate();
				if (rowsUpdated > 0) {
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

				} // else ends here

			} catch (SQLException e) {
				// Alert message shown if there is an error during database interaction
				Alert alert2 = new Alert(AlertType.ERROR);
				alert2.setTitle("Error");
				alert2.setContentText("Error inserting data into the database: \n" + e.getMessage());
				alert2.showAndWait();
			}
		}
	}

	public void ShowDataOnTable() throws SQLException {

		conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM Fuel");

		while (resultSet.next()) {
			int Expense_Id = resultSet.getInt("Expense_Id");
			String Vehicle_RegNo = resultSet.getString("Vehicle_RegNo");
			String Fuel_litre = resultSet.getString("Fuel_litre");
			String Amount = resultSet.getString("Amount");
			String Date = resultSet.getString("Date");
			String Payment_Method = resultSet.getString("Payment_Method");
			String Remarks = resultSet.getString("Remarks");

			// columns after retrieving data from database
			ExpIDCol.setCellValueFactory(new PropertyValueFactory<>("Expense_Id"));
			VRegCol.setCellValueFactory(new PropertyValueFactory<>("Vehicle_RegNo"));
			Fuel_LitreCol.setCellValueFactory(new PropertyValueFactory<>("Fuel_litre"));
			AmountCol.setCellValueFactory(new PropertyValueFactory<>("Amount"));
			DateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
			PaymentCol.setCellValueFactory(new PropertyValueFactory<>("Payment_Method"));
			RemarksCol.setCellValueFactory(new PropertyValueFactory<>("Remarks"));

			FuelRecord Fuel = new FuelRecord(Expense_Id, Vehicle_RegNo, Fuel_litre, Amount, Date, Payment_Method,
					Remarks);
			FuelExpenseTable.getItems().add(Fuel);

		}
		resultSet.close();
		statement.close();

		// ...
	}

	@FXML
	private void initialize() throws Exception {
		selectRecord();
		ShowDataOnTable();

		SimpleDateFormat DateDF = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = DateDF.format(new Date());
		Date.setValue(LocalDate.parse(currentDate));

		FilteredList<FuelRecord> filteredData;
		// Create a filtered list from the original list
		filteredData = new FilteredList<>(FuelExpenseTable.getItems(), p -> true);

		// Set the filter predicate based on the search query
		searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Fee -> {
				if (newValue == null || newValue.isEmpty()) {
					return true; // Show all items if search query is empty
				}

				String lowerCaseQuery = newValue.toLowerCase();
				// Match against different properties of the related Data object
				if (isInteger(newValue) && Fee.getExpense_Id() == Integer.parseInt(newValue)) {
					return true; // Match against iD
				}
				if (Fee.getPayment_Method().toLowerCase().contains(lowerCaseQuery)) {
					return true;
				} else if (Fee.getVehicle_RegNo().toLowerCase().contains(lowerCaseQuery)) {
					return true;
				} else if (Fee.getAmount().toLowerCase().contains(lowerCaseQuery)) {
					return true;
				} else if (Fee.getDate().toLowerCase().contains(lowerCaseQuery)) {
					return true;
				}

				// No match
				return false;
			});
		});
		// Set the filtered list as the new items of the TableView
		FuelExpenseTable.setItems(filteredData);

	}

	// Utility method to check if a string is a valid integer
	private boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@FXML
	void Clear(ActionEvent event) {
		IDTextfield.clear();
		VRegNoTextfield.clear();
		FuelLitreTextfield.clear();
		AmountTextfield.clear();
		Date.setValue(null);
		PMethodTextfield.clear();
		RemarksTextfield.clear();
	}

	@FXML
	void Refresh(ActionEvent event) {
		try {

			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageExpense/FuelExpense/ManageFuel.fxml"));
			screen_pane.getChildren().removeAll();
			screen_pane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
