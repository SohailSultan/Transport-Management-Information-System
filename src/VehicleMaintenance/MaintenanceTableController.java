package VehicleMaintenance;

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

public class MaintenanceTableController {

	@FXML
	private TableView<MaintenanceData> MaintenanceTable;

	@FXML
	private TableColumn<MaintenanceData, String> Amount_Col;

	@FXML
	private TableColumn<MaintenanceData, String> Completion_Date_Col;

	@FXML
	private TableColumn<MaintenanceData, Integer> ID_Col;

	@FXML
	private TableColumn<MaintenanceData, String> Maintenance_DescCol;

	@FXML
	private TableColumn<MaintenanceData, String> Schedule_Date_Col;

	@FXML
	private TableColumn<MaintenanceData, String> Type_Col;

	@FXML
	private TableColumn<MaintenanceData, String> VehicleRegID_Col;

	@FXML
	private TableColumn<MaintenanceData, String> Workshop_NameCol;

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
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Maintenance");

			while (resultSet.next()) {

				int MaintenanceID = resultSet.getInt("MaintenanceID");
				String MaintenanceType = resultSet.getString("MaintenanceType");
				String WorkshopName = resultSet.getString("WorkshopName");
				String Amount = resultSet.getString("Amount");
				String Description = resultSet.getString("Description");
				String VehicleRegID = resultSet.getString("VehicleRegID");
				String ScheduledDate = resultSet.getString("ScheduledDate");
				String CompletionDate = resultSet.getString("CompletionDate");

				ID_Col.setCellValueFactory(new PropertyValueFactory<>("MaintenanceID"));
				Type_Col.setCellValueFactory(new PropertyValueFactory<>("MaintenanceType"));
				Workshop_NameCol.setCellValueFactory(new PropertyValueFactory<>("WorkshopName"));
				Amount_Col.setCellValueFactory(new PropertyValueFactory<>("Amount"));
				Maintenance_DescCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
				VehicleRegID_Col.setCellValueFactory(new PropertyValueFactory<>("VehicleRegID"));
				Schedule_Date_Col.setCellValueFactory(new PropertyValueFactory<>("ScheduledDate"));
				Completion_Date_Col.setCellValueFactory(new PropertyValueFactory<>("CompletionDate"));

				MaintenanceData Maintenance = new MaintenanceData(MaintenanceID, MaintenanceType, WorkshopName, Amount,
						Description, VehicleRegID, ScheduledDate, CompletionDate);
				MaintenanceTable.getItems().add(Maintenance);

			}
			resultSet.close();
			statement.close();

			FilteredList<MaintenanceData> filteredData;
			// Create a filtered list from the original list
			filteredData = new FilteredList<>(MaintenanceTable.getItems(), p -> true);

			// Set the filter predicate based on the search query
			searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(Maintenance -> {
					if (newValue == null || newValue.isEmpty()) {
						return true; // Show all items if search query is empty
					}

					String lowerCaseQuery = newValue.toLowerCase();
					// Match against different properties of the related Data object
					if (isInteger(newValue) && Maintenance.getMaintenanceID() == Integer.parseInt(newValue)) {
						return true; // Match against iD
					}
					if (Maintenance.getMaintenanceType().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Type
					} else if (Maintenance.getWorkshopName().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Workshop name
					} else if (Maintenance.getVehicleRegID().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Vehicle Registration
					} else if (Maintenance.getDescription().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Description
					} else if (Maintenance.getScheduledDate().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Date
					}

					// No match
					return false;
				});
			});

			// Set the filtered list as the new items of the TableView
			MaintenanceTable.setItems(filteredData);

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
