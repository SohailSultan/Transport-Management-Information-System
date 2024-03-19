package ManageFee;

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

public class FeeRecordTableController {

	@FXML
	private TableView<FeeRecord> FeeRecordTable;

	@FXML
	private TableColumn<FeeRecord, String> AmountCol;

	@FXML
	private TableColumn<FeeRecord, String> CNICNoCol;

	@FXML
	private TableColumn<FeeRecord, String> Customer_NameCol;

	@FXML
	private TableColumn<FeeRecord, String> DateCol;

	@FXML
	private TableColumn<FeeRecord, String> DepartmentCol;

	@FXML
	private TableColumn<FeeRecord, String> DiscountCol;

	@FXML
	private TableColumn<FeeRecord, String> Father_NameCol;

	@FXML
	private TableColumn<FeeRecord, String> Fee_IDCol;

	@FXML
	private TableColumn<FeeRecord, String> Paid_byCol;

	@FXML
	private TableColumn<FeeRecord, String> Payment_MonthCol;

	@FXML
	private TableColumn<FeeRecord, String> Payment_YearCol;

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
			ResultSet resultSet = statement.executeQuery("SELECT * FROM fee_records");

			while (resultSet.next()) {
				int feeID = resultSet.getInt("feeID");
				String amount = resultSet.getString("amount");
				String paidBy = resultSet.getString("paidBy");
				String date = resultSet.getString("date");
				String customerName = resultSet.getString("customerName");
				String cnic = resultSet.getString("cnic");
				String discount = resultSet.getString("discount");
				String month = resultSet.getString("month");
				String year = resultSet.getString("year");
				String fatherName = resultSet.getString("fatherName");
				String department = resultSet.getString("department");

				// new PropertyValueFactory<>("FacultyID") = setting above variables to table
				// columns after retrieving data from database
				Fee_IDCol.setCellValueFactory(new PropertyValueFactory<>("feeID"));
				AmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
				Paid_byCol.setCellValueFactory(new PropertyValueFactory<>("paidBy"));
				DateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
				Customer_NameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
				CNICNoCol.setCellValueFactory(new PropertyValueFactory<>("cnic"));
				DiscountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
				Payment_MonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
				Payment_YearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
				Father_NameCol.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
				DepartmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));

				FeeRecord Fee = new FeeRecord(feeID, amount, paidBy, date, customerName, cnic, discount, month, year,
						fatherName, department);
				FeeRecordTable.getItems().add(Fee);
			}
			resultSet.close();
			statement.close();

			FilteredList<FeeRecord> filteredData;
			// Create a filtered list from the original list
			filteredData = new FilteredList<>(FeeRecordTable.getItems(), p -> true);

			// Set the filter predicate based on the search query
			searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(Fee -> {
					if (newValue == null || newValue.isEmpty()) {
						return true; // Show all items if search query is empty
					}

					String lowerCaseQuery = newValue.toLowerCase();
					// Match against different properties of the related Data object
					if (isInteger(newValue) && Fee.getFeeID() == Integer.parseInt(newValue)) {
						return true; // Match against iD
					}
					if (Fee.getCustomerName().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against name
					} else if (Fee.getFatherName().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against father name
					} else if (Fee.getCnic().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against CNIC
					} else if (Fee.getPaidBy().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against PaidBy
					} else if (Fee.getMonth().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against month
					}

					// No match
					return false;
				});
			});

			// Set the filtered list as the new items of the TableView
			FeeRecordTable.setItems(filteredData);

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
