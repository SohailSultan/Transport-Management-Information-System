package ManageExpense;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ExpenseTableController {

	@FXML
	private TableColumn<ExpenseData, String> AmountCol;

	@FXML
	private TableColumn<ExpenseData, String> ConsumerCNICCol;

	@FXML
	private TableColumn<ExpenseData, String> ConsumerIDCol;

	@FXML
	private TableColumn<ExpenseData, String> ConsumerNameCol;

	@FXML
	private TableColumn<ExpenseData, String> DateCol;

	@FXML
	private TableColumn<ExpenseData, String> ExpIDCol;

	@FXML
	private TableColumn<ExpenseData, String> ExpenseDetailCol;

	@FXML
	private TableColumn<ExpenseData, String> ExpenseTypeCol;

	@FXML
	private TableColumn<ExpenseData, String> MonthCol;

	@FXML
	private TableColumn<ExpenseData, String> PaymentCol;

	@FXML
	private TableColumn<ExpenseData, String> YearCol;

	@FXML
	private TableView<ExpenseData> ExpenseTable;

	@FXML
	private TextField searchTextField;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	public void initialize() {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM expenses");

			while (resultSet.next()) {
				int expense_id = resultSet.getInt("expense_id");
				String date = resultSet.getString("date");
				String expense_type = resultSet.getString("expense_type");
				String expense_detail = resultSet.getString("expense_detail");
				String amount = resultSet.getString("amount");
				String payment_method = resultSet.getString("payment_method");
				String consumer_id = resultSet.getString("consumer_id");
				String consumer_name = resultSet.getString("consumer_name");
				String cnic = resultSet.getString("cnic");
				String month = resultSet.getString("month");
				String year = resultSet.getString("year");

				// new PropertyValueFactory<>("driver_id") = setting above variables to table
				// columns after retrieving data from database
				AmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
				ConsumerCNICCol.setCellValueFactory(new PropertyValueFactory<>("cnic"));
				ConsumerIDCol.setCellValueFactory(new PropertyValueFactory<>("consumer_id"));
				ConsumerNameCol.setCellValueFactory(new PropertyValueFactory<>("consumer_name"));
				DateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
				ExpIDCol.setCellValueFactory(new PropertyValueFactory<>("expense_id"));
				ExpenseDetailCol.setCellValueFactory(new PropertyValueFactory<>("expense_detail"));
				ExpenseTypeCol.setCellValueFactory(new PropertyValueFactory<>("expense_type"));
				MonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
				PaymentCol.setCellValueFactory(new PropertyValueFactory<>("payment_method"));
				YearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

				ExpenseData Expense = new ExpenseData(expense_id, date, expense_type, expense_detail, amount,
						payment_method, consumer_id, consumer_name, cnic, month, year);
				ExpenseTable.getItems().add(Expense);
			}
			resultSet.close();
			statement.close();

			FilteredList<ExpenseData> filteredData;
			// Create a filtered list from the original list
			filteredData = new FilteredList<>(ExpenseTable.getItems(), p -> true);

			// Set the filter predicate based on the search query
			searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(Expense -> {
					if (newValue == null || newValue.isEmpty()) {
						return true; // Show all items if search query is empty
					}

					String lowerCaseQuery = newValue.toLowerCase();
					// Match against different properties of the related Data object
					if (isInteger(newValue) && Expense.getExpense_id() == Integer.parseInt(newValue)) {
						return true; // Match against id
					} else if (Expense.getConsumer_name().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against consumer name
					} else if (Expense.getExpense_type().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against expense type
					} else if (Expense.getCnic().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against consumer CNIC
					}

					// No match
					return false;
				});
			});

			// Set the filtered list as the new items of the TableView
			ExpenseTable.setItems(filteredData);

			// ...
		} catch (SQLException e) {
			e.printStackTrace();
		}

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

}
