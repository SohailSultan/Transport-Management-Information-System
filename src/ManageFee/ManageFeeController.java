package ManageFee;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ManageFeeController {

	private Stage stage;
	// private Scene scene;
	private Parent root;

	@FXML
	AnchorPane contentPane;

	// move back to home page(Dashboard) from manage Fee
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

	// view fee button
	public void ViewFeeRecord(ActionEvent event) {

		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageFee/FeeTableView.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
			contentPane.setStyle("-fx-background-color: transparent;");

		} catch (IOException e) {
			e.printStackTrace();
		}
	} // View fee function ends here

	// Add fee button on manage fee pane
	public void addFeeRecord() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageFee/AddFeeRecord.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
			contentPane.setStyle("-fx-background-color: transparent;");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// add fee method ends here

	public void updateFeeRecord() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageFee/UpdateFeeRecord.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// update fee method ends here

	public void DeleteFeeRecord() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageFee/DeleteFeeRecord.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void SearchFeeRecord() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageFee/SearchFeeRecord.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
