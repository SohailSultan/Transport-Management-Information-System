package ManagePictures;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImageToDatabaseExample extends Application {

	private Connection connection;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		VBox root = new VBox(10);
		ImageView imageView = new ImageView();
		Button chooseButton = new Button("Choose Image");

		chooseButton.setOnAction(event -> {
			FileChooser fileChooser = new FileChooser();
			File selectedFile = fileChooser.showOpenDialog(primaryStage);

			if (selectedFile != null) {
				try {
					saveImageToDatabase(selectedFile);
					loadImageFromDatabase(imageView);
				} catch (IOException | SQLException e) {
					e.printStackTrace();
				}
			}
		});

		root.getChildren().addAll(imageView, chooseButton);

		Scene scene = new Scene(root, 400, 300);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Image to Database Example");
		primaryStage.show();
	}

	private void saveImageToDatabase(File imageFile) throws IOException, SQLException {
		FileInputStream fis = new FileInputStream(imageFile);

		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO images (image_data) VALUES (?)");
		preparedStatement.setBinaryStream(1, fis, (int) imageFile.length());
		preparedStatement.executeUpdate();

		preparedStatement.close();
		fis.close();
	}

	private void loadImageFromDatabase(ImageView imageView) throws SQLException, IOException {
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT image_data FROM images ORDER BY image_id DESC LIMIT 1");
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			byte[] imageData = resultSet.getBytes("image_data");
			Image image = new Image(new ByteArrayInputStream(imageData));
			imageView.setFitHeight(20);
			imageView.setFitWidth(20);
			imageView.setImage(image);
		}

		resultSet.close();
		preparedStatement.close();
	}

	@Override
	public void init() throws Exception {
		// Initialize the SQLite database connection
		connection = DriverManager.getConnection("jdbc:sqlite:.\\src\\Database\\Transport_System.db");
		connection.createStatement().execute("CREATE TABLE IF NOT EXISTS images ("
				+ "image_id INTEGER PRIMARY KEY AUTOINCREMENT," + "image_data BLOB)");
	}

	@Override
	public void stop() throws Exception {
		// Close the SQLite database connection
		if (connection != null) {
			connection.close();
		}
	}
}
