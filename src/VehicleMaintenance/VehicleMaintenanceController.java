package VehicleMaintenance;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VehicleMaintenanceController {
	@FXML
	private Button minButton;
	@FXML
	private Stage stage;
	@FXML
	private Parent root;
	@FXML
	AnchorPane contentPane;

	// move back to home page(Dashboard) from manage Students
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

	// view Maintenance button
	public void ViewMaintenance(ActionEvent event) {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/VehicleMaintenance/MaintenanceTableView.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
			contentPane.setStyle("-fx-background-color: transparent;");

		} catch (IOException e) {
			e.printStackTrace();
		}
	} // ViewMaintenance function ends here

	// Add Maintenance button on manage vehicle pane
	public void addMaintenance() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/VehicleMaintenance/AddMaintenance.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
			contentPane.setStyle("-fx-background-color: transparent;");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// add Maintenance method ends here

	public void updateMaintenance() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/VehicleMaintenance/UpdateMaintenance.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// update Maintenance method ends here

	public void DeleteMaintenance() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/VehicleMaintenance/DeleteMaintenance.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void SearchMaintenance() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/VehicleMaintenance/SearchMaintenance.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
