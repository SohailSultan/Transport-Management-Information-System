package Admin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AdminProfileController {
	@FXML
	private Stage stage;
	@FXML
	private ImageView ProfileImage;

	@FXML
	private JFXButton SaveButton;

	@FXML
	private Pane screen_pane;

	@FXML
	private TextField EmailTextField;

	@FXML
	private TextField NameTextField;

	@FXML
	private PasswordField PasswordField;

	@FXML
	private TextField userNameTextField;

	@FXML
	private JFXButton UploadImageButton;

	Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	void BackToHome(ActionEvent event) {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	void EditRecord(ActionEvent event) {
		SaveButton.setDisable(false);
		UploadImageButton.setDisable(false);
		NameTextField.setEditable(true);
		PasswordField.setEditable(true);
		EmailTextField.setEditable(true);

		NameTextField.setStyle("-fx-border-color: Blue;");
		PasswordField.setStyle("-fx-border-color: Blue;");
		EmailTextField.setStyle("-fx-border-color: Blue;");

	}

	@FXML
	void SaveRecord(ActionEvent event) {

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			conn.setAutoCommit(false); // Start a transaction
			statement = conn.prepareStatement(
					"UPDATE Admin SET first_name = ?, last_name = ?, email = ?, password = ? WHERE username = ?");
			String fullName = NameTextField.getText();
			// split first and last name from full name
			String[] names = fullName.split(" ");
			if (names.length >= 2) {
				String firstName = names[0];
				String lastName = names[names.length - 1];
				statement.setString(1, firstName);
				statement.setString(2, lastName);
			}
			statement.setString(3, EmailTextField.getText());
			statement.setString(4, PasswordField.getText());
			statement.setString(5, userNameTextField.getText());
			if (statement.executeUpdate() > 0) {
				conn.commit();
				statement.close();
				conn.close();
				SaveButton.setDisable(true);
				UploadImageButton.setDisable(true);
				NameTextField.setEditable(false);
				userNameTextField.setEditable(false);
				PasswordField.setEditable(false);
				EmailTextField.setEditable(false);

				NameTextField.setStyle("-fx-border-color: Silver;");
				PasswordField.setStyle("-fx-border-color: Silver;");
				EmailTextField.setStyle("-fx-border-color: Silver;");

			}

		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Failed!");
			alert.setContentText("Error! Record Updating Failed: " + e.getMessage());
			alert.showAndWait();
		}

	}

	@FXML
	void initialize() throws SQLException {

		SetDataonProfile();

		// Create a Circle clip for rounding the ImageView
		Circle clip = new Circle();
		clip.setCenterX(ProfileImage.getFitWidth() / 2);
		clip.setCenterY(ProfileImage.getFitHeight() / 2);
		clip.setRadius(ProfileImage.getFitWidth() / 2);

		// Apply the clip to the ImageView
		ProfileImage.setClip(clip);

		loadImageFromDatabase();

	}

	private void SetDataonProfile() {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Admin WHERE login_status = 'online'");
			resultSet.next();
			String Firstname = resultSet.getString("first_name");
			String Lastname = resultSet.getString("last_name");
			NameTextField.setText((Firstname + "  " + Lastname));
			userNameTextField.setText((resultSet.getString("username")));
			EmailTextField.setText((resultSet.getString("email")));
			String storedPassword = resultSet.getString("password"); // Store password here
			PasswordField.setText(storedPassword);
			statement.close();
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Profile Data loading Error" + e);
		} finally {
			if (conn != null) {
				try {

					conn.close();
				} catch (SQLException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Connection error" + e);
				}

			}
		}
	}

	@FXML
	void UploadImage() {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(stage);

		if (selectedFile != null) {
			saveImageToDatabase(selectedFile);
			loadImageFromDatabase();
		}
	}

	private void saveImageToDatabase(File imageFile) {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			FileInputStream fis = new FileInputStream(imageFile);

			statement = conn.prepareStatement("UPDATE Admin SET Image = ? WHERE username = ?");
			statement.setBinaryStream(1, fis, (int) imageFile.length());
			statement.setString(2, userNameTextField.getText());
			statement.executeUpdate();
			statement.close();
			fis.close();
			loadImageFromDatabase();
			conn.close();
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("File Close eroor" + e);
		} catch (IOException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("File Close eroor" + e1);
		}
	}

	private void loadImageFromDatabase() {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			statement = conn.prepareStatement("SELECT Image FROM Admin WHERE username = ?");
			statement.setString(1, userNameTextField.getText());
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				byte[] imageData = resultSet.getBytes("Image");
				if (imageData != null && imageData.length > 0) {
					Image image = new Image(new ByteArrayInputStream(imageData));
					ProfileImage.setFitHeight(176);
					ProfileImage.setFitWidth(198);
					ProfileImage.setImage(image);
				} else {
					ProfileImage.setImage(null);
				}
			}
			resultSet.close();
			statement.close();

		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("File Close eroor" + e);
		} finally {
			// Close connection
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// Handle exceptions
			}
		}
	}
}
