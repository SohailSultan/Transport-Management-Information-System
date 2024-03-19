package ManageReservation;

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

public class ReservationTableController {

	@FXML
	private TableView<ReservationData> ReservationTable;

	@FXML
	private TableColumn<ReservationData, String> Arrival_Time1Col;

	@FXML
	private TableColumn<ReservationData, String> Arrival_Time2Col;

	@FXML
	private TableColumn<ReservationData, String> Departure1Col;

	@FXML
	private TableColumn<ReservationData, String> Departure2Col;

	@FXML
	private TableColumn<ReservationData, String> Destination_NameCol;

	@FXML
	private TableColumn<ReservationData, String> DriverIDCol;

	@FXML
	private TableColumn<ReservationData, String> Event_NameCol;

	@FXML
	private TableColumn<ReservationData, String> Event_TypeCol;

	@FXML
	private TableColumn<ReservationData, String> Holder_NameCol;

	@FXML
	private TableColumn<ReservationData, String> IDCol;

	@FXML
	private TableColumn<ReservationData, String> Scedule_DateCol;
	@FXML
	private TableColumn<ReservationData, String> Vehcle_RegCol;
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
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Reservation");

			while (resultSet.next()) {
				int ReservationID = resultSet.getInt("ReservationID");
				String DestinationArrival = resultSet.getString("DestinationArrival");
				String DestinationDept = resultSet.getString("DestinationDept");
				String DestinationName = resultSet.getString("DestinationName");
				String DriverID = resultSet.getString("DriverID");
				String EventHolderName = resultSet.getString("EventHolderName");
				String EventName = resultSet.getString("EventName");
				String EventType = resultSet.getString("EventType");
				String ScheduledDate = resultSet.getString("ScheduledDate");
				String UniversityArrival = resultSet.getString("UniversityArrival");
				String UniversityDept = resultSet.getString("UniversityDept");
				String VehicleRegNo = resultSet.getString("VehicleRegNo");

				// new PropertyValueFactory<>("FacultyID") = setting above variables to table
				// columns after retrieving data from database
				IDCol.setCellValueFactory(new PropertyValueFactory<>("ReservationID"));
				Arrival_Time1Col.setCellValueFactory(new PropertyValueFactory<>("DestinationArrival"));
				Departure2Col.setCellValueFactory(new PropertyValueFactory<>("DestinationDept"));
				Destination_NameCol.setCellValueFactory(new PropertyValueFactory<>("DestinationName"));
				DriverIDCol.setCellValueFactory(new PropertyValueFactory<>("DriverID"));
				Holder_NameCol.setCellValueFactory(new PropertyValueFactory<>("EventHolderName"));
				Event_NameCol.setCellValueFactory(new PropertyValueFactory<>("EventName"));
				Event_TypeCol.setCellValueFactory(new PropertyValueFactory<>("EventType"));
				Scedule_DateCol.setCellValueFactory(new PropertyValueFactory<>("ScheduledDate"));
				Arrival_Time2Col.setCellValueFactory(new PropertyValueFactory<>("UniversityArrival"));
				Departure1Col.setCellValueFactory(new PropertyValueFactory<>("UniversityDept"));
				Vehcle_RegCol.setCellValueFactory(new PropertyValueFactory<>("VehicleRegNo"));

				ReservationData Reservation = new ReservationData(ReservationID, DestinationArrival, DestinationDept,
						DestinationName, DriverID, EventHolderName, EventName, EventType, ScheduledDate,
						UniversityArrival, UniversityDept, VehicleRegNo);
				ReservationTable.getItems().add(Reservation);
			}
			resultSet.close();
			statement.close();

			FilteredList<ReservationData> filteredData;
			// Create a filtered list from the original list
			filteredData = new FilteredList<>(ReservationTable.getItems(), p -> true);

			// Set the filter predicate based on the search query
			searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(Reservation -> {
					if (newValue == null || newValue.isEmpty()) {
						return true; // Show all items if search query is empty
					}

					String lowerCaseQuery = newValue.toLowerCase();
					// Match against different properties of the related Data object
					if (isInteger(newValue) && Reservation.getReservationID() == Integer.parseInt(newValue)) {
						return true; // Match against iD
					}
					if (Reservation.getEventName().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Event name
					} else if (Reservation.getEventHolderName().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Event Holder name
					} else if (Reservation.getVehicleRegNo().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Vehicle Registration
					} else if (Reservation.getEventType().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Event type
					} else if (Reservation.getScheduledDate().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Date
					}

					// No match
					return false;
				});
			});

			// Set the filtered list as the new items of the TableView
			ReservationTable.setItems(filteredData);

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
