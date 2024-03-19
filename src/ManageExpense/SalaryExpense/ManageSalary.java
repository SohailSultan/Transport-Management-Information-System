package ManageExpense.SalaryExpense;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class ManageSalary {

	@FXML
	private TableColumn<SalaryRecord, String> AmountCol;

	@FXML
	private TextField AmountTextfield;

	@FXML
	private TableColumn<SalaryRecord, String> BACNoCol;

	@FXML
	private TextField BACNo_Textfield;

	@FXML
	private TableColumn<SalaryRecord, String> CNICCol;

	@FXML
	private TextField CNICTextfield;

	@FXML
	private DatePicker Date;

	@FXML
	private TableColumn<SalaryRecord, String> DateCol;

	@FXML
	private JFXButton DeleteButton;

	@FXML
	private TableColumn<SalaryRecord, String> DesignationCol;

	@FXML
	private TextField DesignationTextfield;

	@FXML
	private TextField IDTextfield;

	@FXML
	private JFXButton InsertButton;

	@FXML
	private AnchorPane InsertPane;

	@FXML
	private TableColumn<SalaryRecord, String> NameCol;

	@FXML
	private TextField NameTextfield;

	@FXML
	private TextField PMethodTextfield;

	@FXML
	private TableColumn<SalaryRecord, String> PaymentCol;

	@FXML
	private JFXButton RefreshButton;

	@FXML
	private TableView<SalaryRecord> SalaryExpenseTable;

	@FXML
	private TableColumn<SalaryRecord, String> SalaryIDCol;

	@FXML
	private JFXButton UpdateButton;

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
		SalaryExpenseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				int SalaryID = newSelection.getSalary_Id();
				IDTextfield.setText(String.valueOf(SalaryID));
				NameTextfield.setText(newSelection.getName());
				CNICTextfield.setText(newSelection.getCnic());
				DesignationTextfield.setText(newSelection.getDesignation());
				AmountTextfield.setText(newSelection.getAmount());

				LocalDate localDate = LocalDate.parse(newSelection.getDate());
				Date.setValue(localDate);

				PMethodTextfield.setText(newSelection.getPayment_Method());
				BACNo_Textfield.setText(newSelection.getBank_AccountNo());
			}
		});
	}

	@FXML
	void Delete_SalaryExpense(ActionEvent event) {
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

				String deleteQuery = "DELETE FROM Salary WHERE Salary_Id = ?";

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
	void Insert_SalaryExpense(ActionEvent event) {
		try {
			// Establish a connection to the SQLite database
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			String insertQuery = "INSERT INTO Salary (Name, Cnic, Designation, Amount, "
					+ "Date, Payment_Method, Bank_AccountNo) VALUES(?, ?, ?, ?, ?, ?, ?)";
			statement = conn.prepareStatement(insertQuery);
			statement.setString(1, NameTextfield.getText());
			statement.setString(2, CNICTextfield.getText());
			statement.setString(3, DesignationTextfield.getText());
			statement.setString(4, AmountTextfield.getText());
			statement.setString(5, Date.getValue().toString());
			statement.setString(6, PMethodTextfield.getText());
			statement.setString(7, BACNo_Textfield.getText());

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
	void Update_SalaryExpense(ActionEvent event) {
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
				String UpdateQuery = "UPDATE Salary SET Name = ?, Cnic = ?, Designation = ?,"
						+ " Amount = ?, Date = ?, Payment_Method = ?, Bank_AccountNo = ?";
				statement = conn.prepareStatement(UpdateQuery);
				statement.setString(1, NameTextfield.getText());
				statement.setString(2, CNICTextfield.getText());
				statement.setString(3, DesignationTextfield.getText());
				statement.setString(4, AmountTextfield.getText());
				statement.setString(5, Date.getValue().toString());
				statement.setString(6, PMethodTextfield.getText());
				statement.setString(7, BACNo_Textfield.getText());
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
		ResultSet resultSet = statement.executeQuery("SELECT * FROM Salary");

		while (resultSet.next()) {
			int Salary_Id = resultSet.getInt("Salary_Id");
			String Name = resultSet.getString("Name");
			String Cnic = resultSet.getString("Cnic");
			String Designation = resultSet.getString("Designation");
			String Amount = resultSet.getString("Amount");
			String Date = resultSet.getString("Date");
			String Payment_Method = resultSet.getString("Payment_Method");
			String Bank_AccountNo = resultSet.getString("Bank_AccountNo");

			// columns after retrieving data from database
			SalaryIDCol.setCellValueFactory(new PropertyValueFactory<>("Salary_Id"));
			NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
			CNICCol.setCellValueFactory(new PropertyValueFactory<>("Cnic"));
			DesignationCol.setCellValueFactory(new PropertyValueFactory<>("Designation"));
			AmountCol.setCellValueFactory(new PropertyValueFactory<>("Amount"));
			DateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
			PaymentCol.setCellValueFactory(new PropertyValueFactory<>("Payment_Method"));
			BACNoCol.setCellValueFactory(new PropertyValueFactory<>("Bank_AccountNo"));

			SalaryRecord salary = new SalaryRecord(Salary_Id, Name, Cnic, Designation, Amount, Date, Payment_Method,
					Bank_AccountNo);
			SalaryExpenseTable.getItems().add(salary);

		}
		resultSet.close();
		statement.close();

		// ...
	}

	private boolean isValidCNIC(String cnic) {
		String regex = "^[0-9]{5}-[0-9]{7}-[0-9]$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(cnic);
		return matcher.matches();
	}

	@FXML
	private void initialize() throws Exception {
		selectRecord();
		ShowDataOnTable();

		// Set Current Date on DatePicker
		SimpleDateFormat DateDF = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = DateDF.format(new Date());
		Date.setValue(LocalDate.parse(currentDate));

//        cnicTextField.setPromptText("Enter CNIC (e.g., 13501-3201348-3)");

		CNICTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!isValidCNIC(newValue)) {
				CNICTextfield.setStyle("-fx-border-color: red; -fx-text-fill: red;");
			} else {
				CNICTextfield.setStyle("-fx-border-color: green; -fx-text-fill: green;");
			}
		});

		FilteredList<SalaryRecord> filteredData;
		// Create a filtered list from the original list
		filteredData = new FilteredList<>(SalaryExpenseTable.getItems(), p -> true);

		// Set the filter predicate based on the search query
		searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Fee -> {
				if (newValue == null || newValue.isEmpty()) {
					return true; // Show all items if search query is empty
				}

				String lowerCaseQuery = newValue.toLowerCase();
				// Match against different properties of the related Data object
				if (isInteger(newValue) && Fee.getSalary_Id() == Integer.parseInt(newValue)) {
					return true; // Match against iD
				}
				if (Fee.getPayment_Method().toLowerCase().contains(lowerCaseQuery)) {
					return true;
				} else if (Fee.getName().toLowerCase().contains(lowerCaseQuery)) {
					return true;
				} else if (Fee.getAmount().toLowerCase().contains(lowerCaseQuery)) {
					return true;
				} else if (Fee.getDate().toLowerCase().contains(lowerCaseQuery)) {
					return true;
				} else if (Fee.getDesignation().toLowerCase().contains(lowerCaseQuery)) {
					return true;
				}

				// No match
				return false;
			});
		});
		// Set the filtered list as the new items of the TableView
		SalaryExpenseTable.setItems(filteredData);

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
		NameTextfield.clear();
		CNICTextfield.clear();
		DesignationTextfield.clear();
		AmountTextfield.clear();
		Date.getValue();
		PMethodTextfield.clear();
		BACNo_Textfield.clear();
	}

	@FXML
	void Refresh(ActionEvent event) {
		try {

			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageExpense/SalaryExpense/ManageSalary.fxml"));
			screen_pane.getChildren().removeAll();
			screen_pane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
