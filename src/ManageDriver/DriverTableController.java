package ManageDriver;

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

public class DriverTableController {

	@FXML
	private TableColumn<DriverData, String> CNICCol;

	@FXML
	private TableColumn<DriverData, String> ContactCol;

	@FXML
	private TableColumn<DriverData, String> DoBCol;

	@FXML
	private TableColumn<DriverData, String> FNameCol;

	@FXML
	private TableColumn<DriverData, String> IDCol;

	@FXML
	private TableColumn<DriverData, String> L_ExpiryDateCol;

	@FXML
	private TableColumn<DriverData, String> LicenseNoCol;

	@FXML
	private TableColumn<DriverData, String> NameCol;

	@FXML
	private TableColumn<DriverData, String> SalaryCol;

	@FXML
	private TableColumn<DriverData, String> VehicleIDCol;

	@FXML
	private TextField searchTextField;

	@FXML
	private TableView<DriverData> DriverTable;

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
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Driver");

			while (resultSet.next()) {
				int driver_id = resultSet.getInt("driver_id");
				String name = resultSet.getString("name");
				String fatherName = resultSet.getString("father_name");
				String idCard = resultSet.getString("id_card");
				String license_no = resultSet.getString("license_no");
				String license_exp_date = resultSet.getString("license_exp_date");
				String dob = resultSet.getString("dob");
				String contact = resultSet.getString("contact");
				String salary = resultSet.getString("salary");
				String busId = resultSet.getString("bus_id");

				// new PropertyValueFactory<>("driver_id") = setting above variables to table
				// columns after retrieving data from database
				IDCol.setCellValueFactory(new PropertyValueFactory<>("driver_id"));
				NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
				FNameCol.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
				CNICCol.setCellValueFactory(new PropertyValueFactory<>("idCard"));
				LicenseNoCol.setCellValueFactory(new PropertyValueFactory<>("license_no"));
				L_ExpiryDateCol.setCellValueFactory(new PropertyValueFactory<>("license_exp_date"));
				DoBCol.setCellValueFactory(new PropertyValueFactory<>("dob"));
				ContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
				SalaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
				VehicleIDCol.setCellValueFactory(new PropertyValueFactory<>("busId"));
				DriverData conductor = new DriverData(driver_id, name, fatherName, idCard, license_no, license_exp_date,
						dob, contact, salary, busId);
				DriverTable.getItems().add(conductor);
			}
			resultSet.close();
			statement.close();
			FilteredList<DriverData> filteredData;
			// Create a filtered list from the original list
			filteredData = new FilteredList<>(DriverTable.getItems(), p -> true);

			// Set the filter predicate based on the search query
			searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(driver -> {
					if (newValue == null || newValue.isEmpty()) {
						return true; // Show all items if search query is empty
					}

					String lowerCaseQuery = newValue.toLowerCase();
					// Match against different properties of the related Data object
					if (isInteger(newValue) && driver.getDriver_id() == Integer.parseInt(newValue)) {
						return true; // Match against name
					}
					if (driver.getName().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against name
					} else if (driver.getFatherName().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against father name
					} else if (driver.getIdCard().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against CNIC
					}

					// No match
					return false;
				});
			});

			// Set the filtered list as the new items of the TableView
			DriverTable.setItems(filteredData);

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
