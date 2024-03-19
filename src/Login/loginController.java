package Login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class loginController {
	@FXML
	private TextField usernameTextfield;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label notificationLabel;
	@FXML
	private Label errorLabel;
	@FXML
	private Stage stage;
	@FXML
	private Parent root;
	@FXML
	AnchorPane loginPane;
	@FXML
	Label welcomeLabel;
	Connection conn;
	PreparedStatement pstmt;

	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:src/Database/Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	public void login(ActionEvent event) throws IOException {
		try {
			FXMLLoader loginfxml = new FXMLLoader(getClass().getResource("/Login/login.fxml"));
			root = loginfxml.load();
			Label mylabel2 = (Label) loginfxml.getNamespace().get("errorLabel");
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			String username = usernameTextfield.getText();
			String password = passwordField.getText();

			if (username.equals("") && password.equals("")) {
				mylabel2.setText("Username and passwords field are empty");
				mylabel2.setStyle(
						"-fx-text-fill: red; -fx-background-color: white; -fx-background-radius: 50 0 100 0;");
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();

			} else {

				try {
					String sql = "SELECT * FROM Admin WHERE username = ? AND password = ?";
					conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, username);
					pstmt.setString(2, password);
					ResultSet rs = pstmt.executeQuery();

					if (rs.next()) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("Congratulations! \n You're Logged In to The System");
						alert.showAndWait();

						errorLabel.setText("COngrats" + rs.next());
						insertNotification(username, "User Admin Logged In to System Successfully ");
						try {
							String LoginStatus = "UPDATE Admin SET login_status = 'online' WHERE username = ?";
							pstmt = conn.prepareStatement(LoginStatus);
							pstmt.setString(1, username);
							pstmt.executeUpdate();
						} catch (SQLException e) {
							Alert alert2 = new Alert(AlertType.ERROR);
							alert2.setContentText("Error while Updating Admin Status" + e);
							alert2.showAndWait();
						}

						// update Login Status
						SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
						String currentTime = sdf.format(new Date());
						String query = "UPDATE LoginRecord SET login_status = ? WHERE id = '1'";
						conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
						pstmt = conn.prepareStatement(query);
						pstmt.setString(1, currentTime);
						pstmt.executeUpdate();

						Parent fxmlfile = FXMLLoader.load(getClass().getResource("/Dashboard/dashboard.fxml"));
						Scene scene = new Scene(fxmlfile);
						stage.setScene(scene);
						stage.setX(10);
						stage.setY(10);
						stage.show();

					} else {

						errorLabel.setText("Login Failed! Invalid username or password");
						mylabel2.setStyle(
								"-fx-text-fill: red; -fx-background-color: white; -fx-background-radius: 50 0 100 0;");
					}
				} catch (SQLException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Error while Login \n" + e);
					alert.showAndWait();
				} finally {
					if (conn != null) {
						pstmt.close();
						conn.close();
					}
				}
			}

		}  catch (SQLException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error while Login \n" + e1);
			alert.showAndWait();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error: \n" + e);
			alert.showAndWait();
		}
	} // else statement ends here

	public void forgotPassword(ActionEvent event) throws Exception {
		try {

			Parent fxml = FXMLLoader.load(getClass().getResource("/ForgotPassword/forgotpassword.fxml"));
			loginPane.getChildren().removeAll();
			loginPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error :" + e);
			alert.showAndWait();
		}
	}

	public void SignUp(ActionEvent event) throws Exception {
		try {

			Parent fxml = FXMLLoader.load(getClass().getResource("/SignUp/SignUp.fxml"));
			loginPane.getChildren().removeAll();
			loginPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error :" + e);
			alert.showAndWait();
		}
	}

	public void LoginAsSuper(ActionEvent event) throws Exception {
		try {

			Parent fxml = FXMLLoader.load(getClass().getResource("/Admin/LoginAsSuper.fxml"));
			loginPane.getChildren().removeAll();
			loginPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error while Loading FXML" + e);
			alert.showAndWait();
		}
	}

	private void insertNotification(String username, String notification) {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
			String currentTime = sdf.format(new Date());
			String InsertQuery = "INSERT INTO Notifications (Time, username, Notification) VALUES (?, ? ,?)";
			PreparedStatement statement = conn.prepareStatement(InsertQuery);
			statement.setString(1, currentTime);
			statement.setString(2, username);
			statement.setString(3, notification);
			statement.executeUpdate();
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error While Sending Notification" + e);
			alert.showAndWait();
		}
	}

}
