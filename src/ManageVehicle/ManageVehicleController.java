package ManageVehicle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jfoenix.controls.JFXListView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ManageVehicleController {
	@FXML
	Parent root;
	@FXML
	Stage stage;
	@FXML
	AnchorPane contentPane, VehicleAvailPane;

	@FXML
	private JFXListView<String> Id_ListView, Reg_NoListview, AvailibiltyListview, DriverNameListview,
			DriverCNICListview;

	private ObservableList<String> idItems, reg_NoItems, AvailItems, nameItems, CnicItems;

	@FXML
	void initialize() {
		idItems = FXCollections.observableArrayList();
		Id_ListView.setItems(idItems);

		reg_NoItems = FXCollections.observableArrayList();
		Reg_NoListview.setItems(reg_NoItems);

		AvailItems = FXCollections.observableArrayList();
		AvailibiltyListview.setItems(AvailItems);

		nameItems = FXCollections.observableArrayList();
		DriverNameListview.setItems(nameItems);

		CnicItems = FXCollections.observableArrayList();
		DriverCNICListview.setItems(CnicItems);
		AssignDriverToVehicle(); // Assign Driver ID to Vehicle if Registration Numbers matched in both table
		MakeVehicleAvailable(); // set driver ID in vehicle table Null when driver left the job
		ShowDataofAvailVehicles();

	}

	// move back to home page(Dashboard) from manage vehicle
	public void HomePage(ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/Dashboard/dashboard.fxml"));
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	} // HomePage function ends here

	// view vehicles button
	public void ViewVehicles(ActionEvent event) {

		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageVehicle/VehicleTableView.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
			contentPane.setStyle("-fx-background-color: transparent;");

		} catch (IOException e) {
			e.printStackTrace();
		}
	} // ViewVehicles function ends here

	// Add vehicles button on manage vehicle pane
	public void addVehicle() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageVehicle/AddVehicle.fxml"));
			contentPane.getChildren().removeAll();

			contentPane.getChildren().setAll(fxml);
			contentPane.setStyle("-fx-background-color: transparent;");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// add vehicle method ends here

	public void updateVehicle() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageVehicle/UpdateVehicle.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// update vehicle method ends here

	public void deleteVehicle() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageVehicle/deleteVehicle.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void searchVehicle() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageVehicle/searchVehicle.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void AvailableVehicles(ActionEvent event) throws IOException {
		try {
			root = FXMLLoader.load(getClass().getResource("/ManageVehicle/ManageVehicle.fxml"));
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void ShowDataofAvailVehicles() {
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:.\\src\\Database\\Transport_System.db")) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT\r\n" + "    v.registration_number AS Vehicle_Reg,\r\n"
					+ "    v.vehicle_id,\r\n" + "    CASE\r\n" + "        WHEN d.LeavingDate IS NULL THEN d.name\r\n"
					+ "        ELSE \"\"\r\n" + "    END AS DriverName,\r\n" + "    CASE\r\n"
					+ "        WHEN d.LeavingDate IS NULL THEN d.id_card\r\n" + "        ELSE \"\"\r\n"
					+ "    END AS DriverIDCard,\r\n" + "    CASE\r\n"
					+ "        WHEN d.LeavingDate IS NOT NULL THEN 'Available For New Driver'\r\n"
					+ "        WHEN v.driver_id IS NULL THEN 'Available For New Driver'  -- New case for null driver_id\r\n"
					+ "        ELSE 'Not Available'\r\n" + "    END AS Availability\r\n" + "FROM\r\n"
					+ "    vehicles v\r\n" + "LEFT JOIN\r\n" + "    Driver d ON v.driver_id = d.driver_id;\r\n" + " ");

			while (resultSet.next()) {
				String Id = resultSet.getString("vehicle_id");
				idItems.add(Id);

				String registration_number = resultSet.getString("Vehicle_Reg");
				reg_NoItems.add(registration_number);

				String Availaibilty = resultSet.getString("Availability");
				AvailItems.add(Availaibilty);

				String name = resultSet.getString("DriverName");
				nameItems.add(name);

				String DriverIDCard = resultSet.getString("DriverIDCard");
				CnicItems.add(DriverIDCard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void MakeVehicleAvailable() {
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:.\\src\\Database\\Transport_System.db")) {

			String updateQuery = "UPDATE vehicles\r\n" + "SET driver_id = NULL\r\n" + "WHERE driver_id IS NOT NULL\r\n"
					+ "    AND (\r\n" + "        SELECT COUNT(*) FROM Driver d\r\n"
					+ "        WHERE vehicles.registration_number = d.bus_id\r\n"
					+ "          AND d.LeavingDate IS NULL\r\n" + "    ) = 0;\r\n";
			PreparedStatement statement = connection.prepareStatement(updateQuery);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
		}
	}

	void AssignDriverToVehicle() {
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:.\\src\\Database\\Transport_System.db")) {

			String updateQuery = "UPDATE vehicles SET driver_id = (SELECT driver_id FROM Driver WHERE"
					+ " vehicles.registration_number = Driver.bus_id AND Driver.LeavingDate IS NULL)\r\n"
					+ "WHERE EXISTS (SELECT 1 FROM Driver WHERE vehicles.registration_number = Driver.bus_id AND Driver.LeavingDate IS NULL);\r\n";
			PreparedStatement statement = connection.prepareStatement(updateQuery);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
		}
	}

} // class ends here
