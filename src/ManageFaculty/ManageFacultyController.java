package ManageFaculty;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ManageFacultyController {
	@FXML
	private Stage stage;
	@FXML
	private Parent root;
	@FXML
	AnchorPane contentPane;

	// move back to home page(Dashboard) from manage Faculty
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

	// view Faculty button
	public void ViewFaculty(ActionEvent event) {

		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageFaculty/FacultyTableView.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
			contentPane.setStyle("-fx-background-color: transparent;");

		} catch (IOException e) {
			e.printStackTrace();
		}
	} // ViewFaculty function ends here

	// Add Faculty button on manage vehicle pane
	public void addFaculty() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageFaculty/AddFaculty.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
			contentPane.setStyle("-fx-background-color: transparent;");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// add Faculty method ends here

	public void updateFaculty() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageFaculty/UpdateFaculty.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// update Faculty method ends here

	public void DeleteFaculty() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageFaculty/DeleteFaculty.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void SearchFaculty() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageFaculty/SearchFaculty.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
