package ForgotPassword;

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
import javafx.stage.Stage;

public class ForgotPasswordController {
	@FXML
	private Stage stage;
	@FXML
	private Parent root;
	@FXML
	private TextField usernameTextfield;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField authPinField;
	@FXML
	private Label warningLabel;
	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;

	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	public void Submit(ActionEvent event) throws IOException, SQLException {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:.\\src\\Database\\Transport_System.db");
			String sql = "SELECT * FROM Super_Admin where AuthPIN = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, authPinField.getText());
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				try {
					String userName = usernameTextfield.getText();
					String password = passwordField.getText();
					String sqlQuery = "UPDATE Admin SET password = ? WHERE username = ?";
					statement = conn.prepareStatement(sqlQuery);

					statement.setString(1, password);
					statement.setString(2, userName);
					int rowsAffected = statement.executeUpdate();
					if (rowsAffected > 0) {
						insertNotification(userName, "User Admin Reset Password Successfully        ");
						// Alert message showed after Record saved
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Success");
						alert.setContentText("Password Reset Seccessfully!");
						alert.showAndWait();

						// After Reset Successful Login Screen will be displayed by below code
						stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						Parent fxmlfile = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
						Scene scene = new Scene(fxmlfile);
						stage.setScene(scene);
						stage.setTitle("Transport Management System");
						stage.show();
					} else {
						// Alert message showed after Record is not saved
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Failed!");
						alert.setContentText("Error! Username not found in the database.");
						alert.showAndWait();
					}

				} catch (SQLException e) {
					// Alert message showed after database not connected
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Failed!");
					alert.setContentText("Error updating password: " + e.getMessage());
					alert.showAndWait();
				} finally {
					// Close the connection in the finally block
					if (conn != null) {
						try {
							conn.close();
							statement.close();

						} catch (SQLException e) {
						}
					}

				}

			} else {
				warningLabel.setText("Error! Enter Correct Authentication PIN");
				warningLabel.setStyle(
						"-fx-text-fill: red; -fx-background-color: white; -fx-background-radius: 50 0 100 0;");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Failed!");
				alert.setContentText("Error! Enter valid Authentication PIN");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Database Error " + e);
			alert.showAndWait();
		}

	}

	public void CanceltoLoginPage(ActionEvent event) throws Exception {
		try {
			root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
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
		}
	}

}
