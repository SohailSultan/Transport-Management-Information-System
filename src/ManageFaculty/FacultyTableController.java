package ManageFaculty;

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

public class FacultyTableController {

	@FXML
	private TableView<FacultyData> FacultyTable;

	@FXML
	private TableColumn<FacultyData, String> AddressCol;

	@FXML
	private TableColumn<FacultyData, String> Faculty_TypeCol;

	@FXML
	private TableColumn<FacultyData, String> Bank_AccountNoCol;

	@FXML
	private TableColumn<FacultyData, String> CNICCol;

	@FXML
	private TableColumn<FacultyData, String> Contact_NoCol;

	@FXML
	private TableColumn<FacultyData, String> DepartmentCol;

	@FXML
	private TableColumn<FacultyData, String> DesignationCol;

	@FXML
	private TableColumn<FacultyData, String> DoBCol;

	@FXML
	private TableColumn<FacultyData, Integer> Faculty_IDCol;

	@FXML
	private TableColumn<FacultyData, String> Father_NameCol;

	@FXML
	private TableColumn<FacultyData, String> FeeStatusCol;

	@FXML
	private TableColumn<FacultyData, String> NameCol;

	@FXML
	private TableColumn<FacultyData, String> RouteIDCol;

	@FXML
	private TableColumn<FacultyData, String> SalaryCol;

	@FXML
	private TableColumn<FacultyData, String> VehicleIDCol;

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
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Faculty");

			while (resultSet.next()) {
				int FacultyID = resultSet.getInt("FacultyID");
				String FacultyType = resultSet.getString("FacultyType");
				String Name = resultSet.getString("Name");
				String FatherName = resultSet.getString("FatherName");
				String DOB = resultSet.getString("DOB");
				String Designation = resultSet.getString("Designation");
				String Department = resultSet.getString("Department");
				String Contact = resultSet.getString("Contact");
				String CNIC = resultSet.getString("CNIC");
				String BankAccount = resultSet.getString("BankAccount");
				String RouteID = resultSet.getString("RouteID");
				String BusID = resultSet.getString("BusID");
				String Salary = resultSet.getString("Salary");
				String Address = resultSet.getString("Address");
				String FeeStatus = resultSet.getString("FeeStatus");

				// new PropertyValueFactory<>("FacultyID") = setting above variables to table
				// columns after retrieving data from database
				Faculty_IDCol.setCellValueFactory(new PropertyValueFactory<>("FacultyID"));
				Faculty_TypeCol.setCellValueFactory(new PropertyValueFactory<>("FacultyType"));
				NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
				Father_NameCol.setCellValueFactory(new PropertyValueFactory<>("FatherName"));
				DoBCol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
				DesignationCol.setCellValueFactory(new PropertyValueFactory<>("Designation"));
				DepartmentCol.setCellValueFactory(new PropertyValueFactory<>("Department"));
				Contact_NoCol.setCellValueFactory(new PropertyValueFactory<>("Contact"));
				CNICCol.setCellValueFactory(new PropertyValueFactory<>("CNIC"));
				Bank_AccountNoCol.setCellValueFactory(new PropertyValueFactory<>("BankAccount"));
				RouteIDCol.setCellValueFactory(new PropertyValueFactory<>("RouteID"));
				VehicleIDCol.setCellValueFactory(new PropertyValueFactory<>("BusID"));
				SalaryCol.setCellValueFactory(new PropertyValueFactory<>("Salary"));
				AddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
				FeeStatusCol.setCellValueFactory(new PropertyValueFactory<>("FeeStatus"));

				FacultyData Faculty = new FacultyData(FacultyID, FacultyType, Name, FatherName, DOB, Designation,
						Department, Contact, CNIC, BankAccount, RouteID, BusID, Salary, Address, FeeStatus);
				FacultyTable.getItems().add(Faculty);
			}
			resultSet.close();
			statement.close();

			FilteredList<FacultyData> filteredData;
			// Create a filtered list from the original list
			filteredData = new FilteredList<>(FacultyTable.getItems(), p -> true);

			// Set the filter predicate based on the search query
			searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(Faculty -> {
					if (newValue == null || newValue.isEmpty()) {
						return true; // Show all items if search query is empty
					}

					String lowerCaseQuery = newValue.toLowerCase();
					// Match against different properties of the related Data object
					if (isInteger(newValue) && Faculty.getFacultyID() == Integer.parseInt(newValue)) {
						return true; // Match against iD
					}
					if (Faculty.getName().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against name
					} else if (Faculty.getFatherName().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against father name
					} else if (Faculty.getCNIC().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against CNIC
					} else if (Faculty.getDesignation().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Designation
					} else if (Faculty.getDepartment().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Department
					}

					// No match
					return false;
				});
			});

			// Set the filtered list as the new items of the TableView
			FacultyTable.setItems(filteredData);

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
