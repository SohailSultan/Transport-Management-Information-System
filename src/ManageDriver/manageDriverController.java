package ManageDriver;

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

public class manageDriverController {
	@FXML
	private Button minButton;
	private Stage stage;
	// private Scene scene;
	private Parent root;
	@FXML
	AnchorPane contentPane;

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

	// view driver button
	public void ViewDrivers(ActionEvent event) {

		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageDriver/DriverTableView.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
			contentPane.setStyle("-fx-background-color: transparent;");
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // ViewDrivers function ends here

	// Add Driver button on manage vehicle pane
	public void addDriver() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageDriver/AddDriver.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
			contentPane.setStyle("-fx-background-color: transparent;");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// add Driver method ends here

	public void updateDriver() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageDriver/UpdateDriver.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// update vehicle method ends here

	public void SearchDriver() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageDriver/SearchDriver.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void DeleteDriver() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageDriver/DeleteDriver.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
