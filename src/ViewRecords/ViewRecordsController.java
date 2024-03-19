package ViewRecords;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class ViewRecordsController {

	@FXML
	private Pane screen_pane;
	JFXButton backButton = new JFXButton("Back");

	@FXML
	public void initialize() {

		backButton.setLayoutX(30);
		backButton.setLayoutY(40);
		backButton.setPrefSize(80, 30);
		backButton.setStyle("-fx-background-color: white; " + "-fx-border-color: black; -fx-border-radius: "
				+ "20px; -fx-background-radius: 20px; ");

		backButton.setOnAction(e -> {
			try {
				Parent fxml = FXMLLoader.load(getClass().getResource("/ViewRecords/viewRecordsOptions.fxml"));
				screen_pane.getChildren().removeAll();
				screen_pane.getChildren().setAll(fxml);
			} catch (IOException event) {
				event.printStackTrace();
			}
		});
	}

	@FXML
	void ConductorRecord(ActionEvent event) {
		try {
			screen_pane.setStyle("-fx-background-color: transparent; ");
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageConductor/ConductorTableView.fxml"));
			screen_pane.getChildren().removeAll();
			screen_pane.getChildren().setAll(fxml, backButton);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void DriverRecord(ActionEvent event) {
		try {
			screen_pane.setStyle("-fx-background-color: transparent; ");
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageDriver/DriverTableView.fxml"));
			screen_pane.getChildren().removeAll();
			screen_pane.getChildren().setAll(fxml, backButton);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void ExpenseRecord(ActionEvent event) {
		try {
			screen_pane.setStyle("-fx-background-color: transparent; ");
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageExpense/ExpenseTableView.fxml"));
			screen_pane.getChildren().removeAll();
			screen_pane.getChildren().setAll(fxml, backButton);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void FacultyRecord(ActionEvent event) {
		try {
			screen_pane.setStyle("-fx-background-color: transparent; ");
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageFaculty/FacultyTableView.fxml"));
			screen_pane.getChildren().removeAll();
			screen_pane.getChildren().setAll(fxml, backButton);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void FeeRecord(ActionEvent event) {
		try {
			screen_pane.setStyle("-fx-background-color: transparent; ");
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageFee/FeeTableView.fxml"));
			screen_pane.getChildren().removeAll();
			screen_pane.getChildren().setAll(fxml, backButton);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void MaintenanceRecord(ActionEvent event) {
		try {
			screen_pane.setStyle("-fx-background-color: transparent; ");
			Parent fxml = FXMLLoader.load(getClass().getResource("/VehicleMaintenance/MaintenanceTableView.fxml"));
			screen_pane.getChildren().removeAll();
			screen_pane.getChildren().setAll(fxml, backButton);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void ReservationRecord(ActionEvent event) {
		try {
			screen_pane.setStyle("-fx-background-color: transparent; ");
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageReservation/ReservationTableView.fxml"));
			screen_pane.getChildren().removeAll();
			screen_pane.getChildren().setAll(fxml, backButton);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void RouteRecord(ActionEvent event) {
		try {
			screen_pane.setStyle("-fx-background-color: transparent; ");
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageRoute/RouteTableView.fxml"));
			screen_pane.getChildren().removeAll();
			screen_pane.getChildren().setAll(fxml, backButton);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void StudentRecord(ActionEvent event) {
		try {
			screen_pane.setStyle("-fx-background-color: transparent; ");
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageStudents/StudentsTableView.fxml"));
			screen_pane.getChildren().removeAll();
			screen_pane.getChildren().setAll(fxml, backButton);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void VehiclesRecord(ActionEvent event) {
		try {
			screen_pane.setStyle("-fx-background-color: transparent; ");
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageVehicle/VehicleTableView.fxml"));
			screen_pane.getChildren().removeAll();
			screen_pane.getChildren().setAll(fxml, backButton);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
