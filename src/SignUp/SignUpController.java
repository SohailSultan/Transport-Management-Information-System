package SignUp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SignUpController {
	@FXML
	private Stage stage;
	private Parent root;

	// Declare the form fields
	@FXML
	private TextField firstNameTextfield;
	@FXML
	private TextField lastNameTextfield;
	@FXML
	private TextField userNameTextfield;
	@FXML
	private TextField emailAddressTextfield;
	@FXML
	private PasswordField passWordField;
	@FXML
	private PasswordField authPinField;
	@FXML
	private Label warningLabel;
	@FXML
	private JFXButton UploadImageButton;

	Connection conn;
	PreparedStatement stmt;
	File selectedFile;

	@FXML
	void ChooseFile() {
		FileChooser fileChooser = new FileChooser();
		selectedFile = fileChooser.showOpenDialog(stage);
	}

	public void Submit(ActionEvent event) throws Exception {

		// Retrieve the user input data from the form fields
		String firstName = firstNameTextfield.getText();
		String lastName = lastNameTextfield.getText();
		String username = userNameTextfield.getText();
		String email = emailAddressTextfield.getText();
		String password = passWordField.getText();
		if (selectedFile != null) {
			FileInputStream fis = new FileInputStream(selectedFile);
			// Use JDBC to establish a connection to the SQLite database and insert the user
			try {
				conn = DriverManager.getConnection("jdbc:sqlite:.\\src\\Database\\Transport_System.db");
				String sql = "SELECT * FROM Super_Admin where AuthPIN = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, authPinField.getText());
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					// Define the INSERT query
					String INSERT_QUERY = "INSERT INTO Admin (first_name, last_name, username, email, password, Image) VALUES (?, ?, ?, ?, ?, ?)";
					stmt = conn.prepareStatement(INSERT_QUERY);
					// System.out.println("Connection status: " + conn.isValid(5));
					stmt.setString(1, firstName);
					stmt.setString(2, lastName);
					stmt.setString(3, username);
					stmt.setString(4, email);
					stmt.setString(5, password);
					stmt.setBinaryStream(6, fis, (int) selectedFile.length());

					if (stmt.executeUpdate() == 1) {
						// Show a success message
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Success");
						alert.setContentText("User data has been saved to the database.");
						alert.showAndWait();
					} else {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Failed");
						alert.setContentText("User Already Exist.");
						alert.showAndWait();
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
				// Show an error message
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Failed");
				alert.setContentText("User Already Exist \n Try different Username.\n"+ e);
				alert.showAndWait();
			} finally {
				// Close the connection in the finally block
				if (conn != null) {
					try {
						conn.close();
						stmt.close();

					} catch (SQLException e) {
					}
				}

			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Choose Profile Picture First.");
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

	/*
	 * ____________________________________Email
	 * Validation______________________________________________
	 */

	private boolean isValidEmail(String email) {
		String regex = "^[A-Za-z0-9._+-]+@gmail\\.com$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	@FXML
	public void initialize() {
		emailAddressTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!isValidEmail(newValue)) {
				emailAddressTextfield.setStyle("-fx-border-color: red; -fx-text-fill: red;");
			} else {
				emailAddressTextfield.setStyle("-fx-border-color: Green; -fx-text-fill: green;");
			}
		});
	}
}
