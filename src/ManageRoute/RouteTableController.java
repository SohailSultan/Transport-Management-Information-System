package ManageRoute;

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

public class RouteTableController {

	@FXML
	private TableView<RouteData> RouteTable;

	@FXML
	private TableColumn<RouteData, String> ArrivalTimeCol;

	@FXML
	private TableColumn<RouteData, String> ConductorIDCol;

	@FXML
	private TableColumn<RouteData, String> DepartTimeCol;

	@FXML
	private TableColumn<RouteData, String> DriverIDCol;

	@FXML
	private TableColumn<RouteData, String> EndingPointCol;

	@FXML
	private TableColumn<RouteData, String> FairsCol;

	@FXML
	private TableColumn<RouteData, String> OccupationCol;

	@FXML
	private TableColumn<RouteData, String> RouteIDCol;

	@FXML
	private TableColumn<RouteData, String> RouteNameCol;

	@FXML
	private TableColumn<RouteData, String> StartingPointCol;

	@FXML
	private TableColumn<RouteData, String> StopsNameCol;

	@FXML
	private TableColumn<RouteData, String> VehicleIDCol;

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
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Routes");

			while (resultSet.next()) {

				int routeId = resultSet.getInt("route_id");
				String routeName = resultSet.getString("route_name");
				String startPoint = resultSet.getString("start_name");
				String endPoint = resultSet.getString("end_name");
				String busId = resultSet.getString("bus_id");
				String conductorId = resultSet.getString("conductor_id");
				String driverId = resultSet.getString("driver_id");
				String fairs = resultSet.getString("fairs");
				String occupation = resultSet.getString("occupation");
				String pickupTime = resultSet.getString("pickup_time");
				String reachTime = resultSet.getString("reach_time");
				String stopsName = resultSet.getString("stops_name");

				RouteIDCol.setCellValueFactory(new PropertyValueFactory<>("routeId"));
				RouteNameCol.setCellValueFactory(new PropertyValueFactory<>("routeName"));
				StartingPointCol.setCellValueFactory(new PropertyValueFactory<>("startPoint"));
				EndingPointCol.setCellValueFactory(new PropertyValueFactory<>("endPoint"));
				DepartTimeCol.setCellValueFactory(new PropertyValueFactory<>("pickupTime"));
				ArrivalTimeCol.setCellValueFactory(new PropertyValueFactory<>("reachTime"));
				FairsCol.setCellValueFactory(new PropertyValueFactory<>("fairs"));
				OccupationCol.setCellValueFactory(new PropertyValueFactory<>("occupation"));
				VehicleIDCol.setCellValueFactory(new PropertyValueFactory<>("busId"));
				DriverIDCol.setCellValueFactory(new PropertyValueFactory<>("driverId"));
				ConductorIDCol.setCellValueFactory(new PropertyValueFactory<>("conductorId"));
				StopsNameCol.setCellValueFactory(new PropertyValueFactory<>("stopsName"));

				RouteData route = new RouteData(routeId, routeName, startPoint, endPoint, pickupTime, reachTime, fairs,
						occupation, busId, driverId, conductorId, stopsName);
				RouteTable.getItems().add(route);

			}
			resultSet.close();
			statement.close();

			FilteredList<RouteData> filteredData;
			// Create a filtered list from the original list
			filteredData = new FilteredList<>(RouteTable.getItems(), p -> true);

			// Set the filter predicate based on the search query
			searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(Route -> {
					if (newValue == null || newValue.isEmpty()) {
						return true; // Show all items if search query is empty
					}

					String lowerCaseQuery = newValue.toLowerCase();
					// Match against different properties of the related Data object
					if (isInteger(newValue) && Route.getRouteId() == Integer.parseInt(newValue)) {
						return true; // Match against iD
					}
					if (Route.getRouteName().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Route name
					} else if (Route.getStopsName().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against StopsName
					} else if (Route.getBusId().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Vehicle Registration
					} else if (Route.getDriverId().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Driver
					}

					// No match
					return false;
				});
			});

			// Set the filtered list as the new items of the TableView
			RouteTable.setItems(filteredData);

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
