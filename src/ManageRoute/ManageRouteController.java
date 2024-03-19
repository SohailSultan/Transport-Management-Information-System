package ManageRoute;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ManageRouteController {

	@FXML
	private Parent root;
	@FXML
	AnchorPane contentPane;
	@FXML
	private Stage stage;

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
	public void ViewRoutes(ActionEvent event) {

		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageRoute/RouteTableView.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
			contentPane.setStyle("-fx-background-color: transparent;");
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // ViewDrivers function ends here

	// Add Driver button on manage vehicle pane
	public void addRoute() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageRoute/AddRoute.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
			contentPane.setStyle("-fx-background-color: transparent;");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// add Driver method ends here

	public void updateRoute() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageRoute/UpdateRoute.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// update vehicle method ends here

	public void DeleteRoute() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageRoute/DeleteRoute.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void SearchRoute() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageRoute/SearchRoute.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
