package ManageExpense.Vehicle_Tokens;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ManageTokenController {

	@FXML
	private TextField AmountTextfield;

	@FXML
	private JFXButton DeleteButton;

	@FXML
	private TableView<TokenData> Token_InsuranceExpenseTable;

	@FXML
	private TableColumn<TokenData, String> ExpDateCol;

	@FXML
	private TableColumn<TokenData, Integer> ExpIDCol;

	@FXML
	private TableColumn<TokenData, String> ExpTypeCol;

	@FXML
	private TableColumn<TokenData, String> AmountCol;

	@FXML
	private TextField PMethodTextfield;

	@FXML
	private TableColumn<TokenData, String> PaymentCol;

	@FXML
	private TableColumn<TokenData, String> PurDateCol;

	@FXML
	private TableColumn<TokenData, String> RemarksCol;

	@FXML
	private TableColumn<TokenData, String> VRegCol;

	@FXML
	private DatePicker ExpiryDate;

	@FXML
	private TextField RemarksTextfield;

	@FXML
	private TextField IDTextfield;

	@FXML
	private JFXButton InsertButton;

	@FXML
	private AnchorPane InsertPane;

	@FXML
	private DatePicker PurchseDate;

	@FXML
	private JFXButton UpdateButton;

	@FXML
	private TextField VRegNoTextfield;

	@FXML
	private Pane screen_pane;

	@FXML
	private TextField searchTextField;

	@FXML
	private ChoiceBox<String> ExpenseType;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	void selectRecord() {
		Token_InsuranceExpenseTable.getSelectionModel().selectedItemProperty()
				.addListener((obs, oldSelection, newSelection) -> {
					if (newSelection != null) {
						int ExpenseID = newSelection.getExp_ID();
						IDTextfield.setText(String.valueOf(ExpenseID));
						VRegNoTextfield.setText(newSelection.getVehicle_no());
						ExpenseType.setValue(newSelection.getExpense_type());
						AmountTextfield.setText(newSelection.getAmount());

						LocalDate localDate = LocalDate.parse(newSelection.getPurcahse_date());
						PurchseDate.setValue(localDate);

						LocalDate localDate2 = LocalDate.parse(newSelection.getExpiry_date());
						ExpiryDate.setValue(localDate2);

						PMethodTextfield.setText(newSelection.getPayment_method());
						RemarksTextfield.setText(newSelection.getRemarks());

					}
				});
	}

	@FXML
	void Delete_FuelExpense(ActionEvent event) throws SQLException {
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

				String deleteQuery = "DELETE FROM Other_Expense WHERE Expense_Id = ?";

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
			String insertQuery = "INSERT INTO Other_Expense (Vehicle_RegNo, Expense_type, Amount, "
					+ "Purchase_date, Expiry_Date, Payment_Method, Remarks ) VALUES(?, ?, ?, ?, ?, ?, ?)";
			statement = conn.prepareStatement(insertQuery);
			statement.setString(1, VRegNoTextfield.getText());
			statement.setString(2, ExpenseType.getValue());
			statement.setString(3, AmountTextfield.getText());
			statement.setString(4, PurchseDate.getValue().toString());
			statement.setString(5, ExpiryDate.getValue().toString());
			statement.setString(6, PMethodTextfield.getText());
			statement.setString(7, RemarksTextfield.getText());

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
	void Update_FuelExpense(ActionEvent event) throws Exception {
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
				String UpdateQuery = "UPDATE Other_Expense SET Vehicle_RegNo = ?, Expense_type = ?, Amount = ?, "
						+ "Purchase_date = ?, Expiry_Date = ?, Payment_Method = ?, Remarks = ? WHERE Expense_Id = ?";
				statement = conn.prepareStatement(UpdateQuery);
				statement.setString(1, VRegNoTextfield.getText());
				statement.setString(2, ExpenseType.getValue());
				statement.setString(3, AmountTextfield.getText());
				statement.setString(4, PurchseDate.getValue().toString());
				statement.setString(5, ExpiryDate.getValue().toString());
				statement.setString(6, PMethodTextfield.getText());
				statement.setString(7, RemarksTextfield.getText());
				statement.setString(8, IDTextfield.getText());
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
		ResultSet resultSet = statement.executeQuery("SELECT * FROM Other_Expense");

		while (resultSet.next()) {
			int Exp_ID = resultSet.getInt("Expense_Id");
			String Vehicle_no = resultSet.getString("Vehicle_RegNo");
			String Expense_type = resultSet.getString("Expense_type");
			String amount = resultSet.getString("amount");
			String purcahse_date = resultSet.getString("Purchase_date");
			String Expiry_date = resultSet.getString("Expiry_Date");
			String Payment_method = resultSet.getString("Payment_Method");
			String Remarks = resultSet.getString("Remarks");

			// columns after retrieving data from database
			ExpIDCol.setCellValueFactory(new PropertyValueFactory<>("Exp_ID"));
			VRegCol.setCellValueFactory(new PropertyValueFactory<>("Vehicle_no"));
			ExpTypeCol.setCellValueFactory(new PropertyValueFactory<>("Expense_type"));
			AmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
			PurDateCol.setCellValueFactory(new PropertyValueFactory<>("purcahse_date"));
			ExpDateCol.setCellValueFactory(new PropertyValueFactory<>("Expiry_date"));
			PaymentCol.setCellValueFactory(new PropertyValueFactory<>("Payment_method"));
			RemarksCol.setCellValueFactory(new PropertyValueFactory<>("Remarks"));

			TokenData Fee = new TokenData(Exp_ID, Vehicle_no, amount, purcahse_date, Expense_type, Expiry_date,
					Payment_method, Remarks);
			Token_InsuranceExpenseTable.getItems().add(Fee);

		}
		resultSet.close();
		statement.close();

		// ...
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
	void ClearFields() {
		IDTextfield.clear();
		VRegNoTextfield.clear();
		ExpenseType.setValue(null);
		AmountTextfield.clear();
		PurchseDate.setValue(null);
		ExpiryDate.setValue(null);
		PMethodTextfield.clear();
		RemarksTextfield.clear();
	}

	@FXML
	void Refresh() {
		try {

			Parent fxml = FXMLLoader
					.load(getClass().getResource("/ManageExpense/Vehicle_Insurance_Tokens/ManageInsurance.fxml"));
			screen_pane.getChildren().removeAll();
			screen_pane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void initialize() throws Exception {
		selectRecord();
		ShowDataOnTable();

		SimpleDateFormat DateDF = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = DateDF.format(new Date());
		PurchseDate.setValue(LocalDate.parse(currentDate));

		// Initialize the choice box values
		ExpenseType.getItems().addAll("Life Time", "Yearly");

		FilteredList<TokenData> filteredData;
		// Create a filtered list from the original list
		filteredData = new FilteredList<>(Token_InsuranceExpenseTable.getItems(), p -> true);

		// Set the filter predicate based on the search query
		searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Fee -> {
				if (newValue == null || newValue.isEmpty()) {
					return true; // Show all items if search query is empty
				}

				String lowerCaseQuery = newValue.toLowerCase();
				// Match against different properties of the related Data object
				if (isInteger(newValue) && Fee.getExp_ID() == Integer.parseInt(newValue)) {
					return true; // Match against iD
				}
				if (Fee.getExpense_type().toLowerCase().contains(lowerCaseQuery)) {
					return true;
				} else if (Fee.getVehicle_no().toLowerCase().contains(lowerCaseQuery)) {
					return true;
				} else if (Fee.getPayment_method().toLowerCase().contains(lowerCaseQuery)) {
					return true;
				} else if (Fee.getAmount().toLowerCase().contains(lowerCaseQuery)) {
					return true;
				} else if (Fee.getExpense_type().toLowerCase().contains(lowerCaseQuery)) {
					return true;
				}

				// No match
				return false;
			});
		});
		// Set the filtered list as the new items of the TableView
		Token_InsuranceExpenseTable.setItems(filteredData);

	}

}
