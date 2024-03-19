package ManageVehicle;

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

public class VehicleTableController {

	@FXML
	private TableView<VehicleData> vehicleTableView;

	@FXML
	private TableColumn<VehicleData, String> accidentalStatusColumn;

	@FXML
	private TableColumn<VehicleData, String> driverIdColumn;

	@FXML
	private TableColumn<VehicleData, String> fuelEfficiencyColumn;

	@FXML
	private TableColumn<VehicleData, String> registrationNumberColumn;

	@FXML
	private TableColumn<VehicleData, String> routeIdColumn;

	@FXML
	private TableColumn<VehicleData, String> tokenExpiryDateColumn;

	@FXML
	private TableColumn<VehicleData, String> tokenIssueDateColumn;

	@FXML
	private TableColumn<VehicleData, String> tokenNumberColumn;

	@FXML
	private TableColumn<VehicleData, String> tokenStatusColumn;

	@FXML
	private TableColumn<VehicleData, String> tokenTypeColumn;

	@FXML
	private TableColumn<VehicleData, String> vehicleCapacityColumn;

	@FXML
	private TableColumn<VehicleData, Integer> vehicleIdColumn;

	@FXML
	private TableColumn<VehicleData, String> vehicleModelColumn;

	@FXML
	private TableColumn<VehicleData, String> vehicleOccupationColumn;

	@FXML
	private TableColumn<VehicleData, String> vehicleTypeColumn;

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
			ResultSet resultSet = statement.executeQuery("SELECT * FROM vehicles");

			while (resultSet.next()) {
				int vehicleId = resultSet.getInt("vehicle_id");
				String vehicleType = resultSet.getString("vehicle_type");
				String vehicleModel = resultSet.getString("vehicle_model");
				String registrationNumber = resultSet.getString("registration_number");
				String vehicleCapacity = resultSet.getString("vehicle_capacity");
				String vehicleOccupation = resultSet.getString("vehicle_occupation");
				String accidentalStatus = resultSet.getString("accidental_status");
				String fuelEfficiency = resultSet.getString("fuel_efficiency");
				String driverId = resultSet.getString("driver_id");
				String routeId = resultSet.getString("route_id");
				String tokenStatus = resultSet.getString("token_status");
				String tokenNumber = resultSet.getString("token_number");
				String tokenType = resultSet.getString("token_type");
				String tokenIssueDate = resultSet.getString("token_issue_date");
				String tokenExpiryDate = resultSet.getString("token_expiry_date");

				vehicleIdColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
				vehicleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
				vehicleModelColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleModel"));
				registrationNumberColumn.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
				vehicleCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleCapacity"));
				vehicleOccupationColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleOccupation"));
				accidentalStatusColumn.setCellValueFactory(new PropertyValueFactory<>("accidentalStatus"));
				fuelEfficiencyColumn.setCellValueFactory(new PropertyValueFactory<>("fuelEfficiency"));
				driverIdColumn.setCellValueFactory(new PropertyValueFactory<>("driverId"));
				routeIdColumn.setCellValueFactory(new PropertyValueFactory<>("routeId"));
				tokenStatusColumn.setCellValueFactory(new PropertyValueFactory<>("tokenStatus"));
				tokenNumberColumn.setCellValueFactory(new PropertyValueFactory<>("tokenNumber"));
				tokenTypeColumn.setCellValueFactory(new PropertyValueFactory<>("tokenType"));
				tokenIssueDateColumn.setCellValueFactory(new PropertyValueFactory<>("tokenIssueDate"));

				tokenExpiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("tokenExpiryDate"));

				VehicleData vehicleData = new VehicleData(accidentalStatus, driverId, fuelEfficiency,
						registrationNumber, routeId, tokenExpiryDate, tokenIssueDate, tokenNumber, tokenStatus,
						tokenType, vehicleCapacity, vehicleId, vehicleModel, vehicleOccupation, vehicleType);

				vehicleTableView.getItems().add(vehicleData);
			}
			resultSet.close();
			statement.close();

			FilteredList<VehicleData> filteredData;
			// Create a filtered list from the original list
			filteredData = new FilteredList<>(vehicleTableView.getItems(), p -> true);

			// Set the filter predicate based on the search query
			searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(Vehicle -> {
					if (newValue == null || newValue.isEmpty()) {
						return true; // Show all items if search query is empty
					}

					String lowerCaseQuery = newValue.toLowerCase();
					// Match against different properties of the related Data object
					if (isInteger(newValue) && Vehicle.getVehicleId() == Integer.parseInt(newValue)) {
						return true; // Match against iD
					}
					if (Vehicle.getRegistrationNumber().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Registration Number
					} else if (Vehicle.getVehicleModel().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against model
					} else if (Vehicle.getAccidentalStatus().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Accidental Status
					}

					// No match
					return false;
				});
			});

			// Set the filtered list as the new items of the TableView
			vehicleTableView.setItems(filteredData);

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
