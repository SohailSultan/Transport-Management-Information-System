package Admin;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SuperAdminController implements Initializable {

	@FXML
	private AnchorPane ButtonPane, NotificationPane, AuthResetPane, manageAdminPane;

	@FXML
	private Hyperlink userNameLink;

	@FXML
	private TextField AuthenPasswordField, AuthenPasswordField2;
	@FXML
	private JFXButton viewRecordButton, SaveButton;

	@FXML
	private JFXListView<String> NotificationList, NotificationList1, adminListView, adminListView1, adminListView2;

	private ObservableList<String> items, items1, usernameItems, NameItem, otherItem;

	@FXML
	private AnchorPane loginPaneSuper;

	@FXML
	private Label ErrorLabel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		items = FXCollections.observableArrayList();
		items1 = FXCollections.observableArrayList();
		NotificationList.setItems(items);
		NotificationList1.setItems(items1);

		usernameItems = FXCollections.observableArrayList();
		NameItem = FXCollections.observableArrayList();
		otherItem = FXCollections.observableArrayList();
		adminListView.setItems(usernameItems);
		adminListView1.setItems(NameItem);
		adminListView2.setItems(otherItem);
		ShowListView();
		showAdminList();

	}

	private void ShowListView() {
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:.\\src\\Database\\Transport_System.db")) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Notifications");

			while (resultSet.next()) {
				int Id = resultSet.getInt("Id");
				String Notification = resultSet.getString("Notification");
				String usernsme = resultSet.getString("username");
				String Time = resultSet.getString("Time");

				// Parse the time value and convert it to a sortable format
				SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
				SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					Date timeDate = inputFormat.parse(Time);
					Time = outputFormat.format(timeDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				String row = Id + "\t\t" + Notification;
				items.add(row);
				String row1 = usernsme + "\t\t" + Time;
				items1.add(row1);
			}

			// Sort the list in descending order based on time
			items.sort(Comparator.reverseOrder());
			items1.sort(Comparator.reverseOrder());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void showAdminList() {
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:.\\src\\Database\\Transport_System.db")) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Admin");

			while (resultSet.next()) {
				String username = resultSet.getString("username");
				String first_name = resultSet.getString("first_name");
				String last_name = resultSet.getString("last_name");
				String email = resultSet.getString("email");
				String login_status = resultSet.getString("login_status");

				String usernamerow = username;
				String namerow = first_name + "\t\t\t" + last_name;
				String otherrow = email + "\t\t" + login_status;
				usernameItems.add(usernamerow);
				NameItem.add(namerow);
				otherItem.add(otherrow);
			}
		} catch (SQLException e) {

		}
	}

	@FXML
	void deleteSelectedRow(ActionEvent event) {
		String selectedRow = NotificationList.getSelectionModel().getSelectedItem();
		if (selectedRow != null) {
			String[] rowData = selectedRow.split("\t\t");
			int id = Integer.parseInt(rowData[0]);

			try (Connection connection = DriverManager
					.getConnection("jdbc:sqlite:.\\src\\Database\\Transport_System.db")) {
				String deleteQuery = "DELETE FROM Notifications WHERE Id = ?";
				PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
				deleteStatement.setInt(1, id);
				deleteStatement.executeUpdate();

				Home(event);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void deleteAdmin() {
		String selectedRow = adminListView.getSelectionModel().getSelectedItem();
		if (selectedRow != null) {
			String[] rowData = selectedRow.split("\t\t");
			String username = rowData[0];

			try (Connection connection = DriverManager
					.getConnection("jdbc:sqlite:.\\src\\Database\\Transport_System.db")) {
				String deleteQuery = "DELETE FROM Admin WHERE username = ?";
				PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
				deleteStatement.setString(1, username);
				deleteStatement.executeUpdate();
				adminListView.refresh();
				manageAdminPane();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void deleteAll(ActionEvent event) {
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:.\\src\\Database\\Transport_System.db")) {
			String deleteQuery = "DELETE FROM Notifications";
			PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
			deleteStatement.executeUpdate();
			Home(event);
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error while deleting" + e);
		}
	}

	@FXML
	void Home(ActionEvent event) {
		try {
			AuthResetPane.setVisible(false);
			manageAdminPane.setVisible(false);
			NotificationPane.setVisible(true);

			Parent root = FXMLLoader.load(getClass().getResource("/Admin/SuperAdminDashboard.fxml"));
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void Logout(ActionEvent event) {
		try {

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void Generate_Reports(ActionEvent event) throws IOException {
		loadStage("/GenerateReports/generateReports.fxml", "Generate Reports");
	}

	@FXML
	void View_Records(ActionEvent event) throws IOException {
		loadStage("/ViewRecords/viewRecordsOptions.fxml", "View Records");
	}

	private void loadStage(String fxmlPath, String title) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(fxmlPath));
			loader.load();
			Scene scene = new Scene(loader.getRoot());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			if (title != null) {
				stage.setTitle(title);
			}
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void ChangeAuthPIN() {
		String password1 = AuthenPasswordField.getText();
		if (password1.equals(AuthenPasswordField2.getText())) {
			ErrorLabel.setVisible(false);
			AuthenPasswordField2.setStyle("-fx-border-color: grey;");
			try (Connection connection = DriverManager
					.getConnection("jdbc:sqlite:.\\src\\Database\\Transport_System.db")) {
				String pin = AuthenPasswordField.getText();
				String updateQuery = "UPDATE Super_Admin SET AuthPIN = ?";
				PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
				updateStatement.setString(1, pin);
				updateStatement.executeUpdate();
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Password Has been changed Successfully!");
				alert.showAndWait();
			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Error while Updating" + e);
			}

		} else {
			AuthenPasswordField2.setStyle("-fx-border-color: red;");
			ErrorLabel.setVisible(true);
		}

	}

	@FXML
	void AuthPinChangePane() {
		NotificationPane.setVisible(false);
		manageAdminPane.setVisible(false);
		AuthResetPane.setVisible(true);

	}

	@FXML
	void manageAdminPane() {
		NotificationPane.setVisible(false);
		AuthResetPane.setVisible(false);
		manageAdminPane.setVisible(true);
	}

}
