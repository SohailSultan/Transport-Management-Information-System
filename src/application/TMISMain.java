package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TMISMain extends Application {
	private Connection conn;
	Statement statement;
	PreparedStatement pstmt;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:src/Database/Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@Override
	public void start(Stage stage) {

		try {

			Parent root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
//			Parent root = FXMLLoader.load(getClass().getResource("/Dashboard/dashboard.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);

			// create image for window icon
			Image iconImage = new Image(getClass().getResourceAsStream("/Pictures/busIcon2.png"));
			stage.getIcons().add(iconImage); // set image icon on stage
			stage.setOnCloseRequest(event -> {

				event.consume(); // Consume the event to prevent automatic window close
				showConfirmationDialog(stage);
			});

			stage.setTitle("Transport Management System For IIUI");
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(args String[]){
		Login lg = new Login();
		lg.show();
	}

	private void showConfirmationDialog(Stage stage) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Close");
		alert.setHeaderText("Do you want to close the window?");
		alert.setContentText("Any unsaved changes will be lost.");
		alert.initOwner(stage); // Set the owner of the alert to the stage

		alert.showAndWait().ifPresent(result -> {
			if (result == ButtonType.OK) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
					String currentTime = sdf.format(new Date());
					String query = "UPDATE LoginRecord SET logout_status = ? WHERE id = '1'";
					conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, currentTime);
					pstmt.executeUpdate();

					String LoginStatus = "UPDATE Admin SET login_status = 'offline'";
					PreparedStatement pstmt2 = conn.prepareStatement(LoginStatus);
					pstmt2.executeUpdate();

					pstmt.close();
					pstmt2.close();
					conn.close();

				} catch (SQLException e) {
					Alert alert1 = new Alert(AlertType.ERROR);
					alert1.setTitle("Error");
					alert1.setHeaderText("Error while updating logout status \n"+e);
					alert1.showAndWait();
				}

				stage.close(); // Close the window if the user confirms
			}
		});
	}

//	public static void main(String[] args) {
//		launch(args);
//	}
}