package ManageExpense;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ExpenseMenuOptionController {

	@FXML
	private Pane screen_pane;
	Stage stage;

	@FXML
	void FuelExpense(ActionEvent event) {
		try {

			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageExpense/FuelExpense/ManageFuel.fxml"));
			screen_pane.getChildren().removeAll();
			screen_pane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void MiscExpense(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/ManageExpense/manageExpense.fxml"));
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void SalaryExpense(ActionEvent event) {
		try {

			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageExpense/SalaryExpense/ManageSalary.fxml"));
			screen_pane.getChildren().removeAll();
			screen_pane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void TokenExpense(ActionEvent event) {
		try {

			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageExpense/Vehicle_Tokens/ManageTokens.fxml"));
			screen_pane.getChildren().removeAll();
			screen_pane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
