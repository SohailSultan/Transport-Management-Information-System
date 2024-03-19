package ManageConductor;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class manageConductorController {
	@FXML
	private Stage stage;
	private Parent root;
	@FXML
	private AnchorPane contentPane;

	public void homeButton(ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/Dashboard/dashboard.fxml"));
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void viewConductors(ActionEvent event) {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageConductor/ConductorTableView.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addConductor(ActionEvent event) {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageConductor/AddConductor.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateConductors(ActionEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("/ManageConductor/UpdateConductor.fxml"));
		contentPane.getChildren().removeAll();
		contentPane.getChildren().setAll(fxml);
	}

	public void DeleteConductor(ActionEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("/ManageConductor/DeleteConductor.fxml"));
		contentPane.getChildren().removeAll();
		contentPane.getChildren().setAll(fxml);
	}

	public void SearchConductor(ActionEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("/ManageConductor/SearchConductor.fxml"));
		contentPane.getChildren().removeAll();
		contentPane.getChildren().setAll(fxml);
	}

	public void ImportData(ActionEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("/ImportExceltoDB/ImportConductor.fxml"));
		contentPane.getChildren().removeAll();
		contentPane.getChildren().setAll(fxml);
	}

}
