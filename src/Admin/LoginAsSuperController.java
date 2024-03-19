package Admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginAsSuperController {

	@FXML
	private ToggleGroup LoginAs;

	@FXML
	private Label errorLabel1;

	@FXML
	private JFXButton loginButton1;

	@FXML
	private Label loginLabel1;

	@FXML
	private AnchorPane loginPaneSuper;

	@FXML
	private PasswordField passwordField1;

	@FXML
	void LoginAsAdmin(ActionEvent event) {
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
	void login(ActionEvent event) throws IOException {

		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:.\\src\\Database\\Transport_System.db")) {

			String sql = "SELECT * FROM Super_Admin where Password = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, passwordField1.getText());
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				Parent root = FXMLLoader.load(getClass().getResource("/Admin/SuperAdminDashboard.fxml"));
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Wrong Password! \n Try Again With Corrcet Password");
				alert.showAndWait();
			}
		} catch (SQLException e) {

		}
	}

}
