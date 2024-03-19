package ManageReservation;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ManageReservationController {

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

	// view Reservation button
	public void ViewReservation(ActionEvent event) {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageReservation/ReservationTableView.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // ViewReservation function ends here

	// Add Reservation button on manage vehicle pane
	public void addReservation() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageReservation/AddReservation.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
			contentPane.setStyle("-fx-background-color: transparent;");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// add Reservation method ends here

	public void updateReservation() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageReservation/UpdateReservation.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// update Reservation method ends here

	public void CancelReservation() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageReservation/CancelReservation.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void SearchReservation() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageReservation/SearchReservation.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
