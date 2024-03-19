package ManageExpense;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class manageExpenseController {
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

	// view Expense button
	public void ViewExpense(ActionEvent event) {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageExpense/ExpenseTableView.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
			contentPane.setStyle("-fx-background-color: transparent;");

		} catch (IOException e) {
			e.printStackTrace();
		}
	} // ViewExpense function ends here

	// Add Expense button on manage vehicle pane
	public void addExpense() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageExpense/AddExpense.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
			contentPane.setStyle("-fx-background-color: transparent;");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// add Expense method ends here

	public void updateExpense() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageExpense/UpdateExpense.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// update Reservation method ends here

	public void DeleteExpense() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageExpense/DeleteExpense.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void SearchExpense() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageExpense/SearchExpense.fxml"));
			contentPane.getChildren().removeAll();
			contentPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
