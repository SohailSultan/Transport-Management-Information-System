package Dashboard;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DashboardControllerabc implements Initializable {
	@FXML
	private Button minButton;
	@FXML
	private Button closeButton;
	@FXML
	private Stage stage;

	@FXML
	private Parent root;
	@FXML
	private AnchorPane mainPane;
	@FXML
	private PieChart pieChart;
	@FXML
	private ImageView backgroundImage;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private Pane screen_pane;
	@FXML
	private AnchorPane contentArea;

	@FXML
	private Label FacultyCountLabel;
	@FXML
	private Label StudentCountLabel;
	@FXML
	private Label driverCountLabel;
	@FXML
	private Label conductorCountLabel;
	@FXML
	private Label vehicleCountLabel;
	@FXML
	private Label RouteCountLabel;

	@FXML
	private TextField VehicleNoTextField;
	@FXML
	private Label vehicleNumberLabel;
	@FXML
	private Label capacityLabel;
	@FXML
	private Label occupationLabel;
	@FXML
	private Label seatLeftLabel;
	@FXML
	private Label warnLabel;

	@FXML
	private Label StudentPaidLabel;
	@FXML
	private Label DefaultStudentLabel;
	@FXML
	private Label FacultyPaidLabel;
	@FXML
	private Label DefaultFacultyLabel;
	@FXML
	private Label timeLabel;
	@FXML
	private Label adminLabel;
	@FXML
	private Label logoutStatusLabel;
	@FXML
	private Label loginStatusLabel;
	private FXMLLoader loader;

	@FXML
	private Label StLftLabel;
	@FXML
	private Label VCLabel;
	@FXML
	private Label VNLabel;
	@FXML
	private Label VOLabel;
	@FXML
	private Hyperlink userNameLink;
	@FXML
	private ChoiceBox<String> selectMonthChoiceBox;

	@FXML
	private TextField SelectYearTextField;

	// SQLite database connection
	private Connection conn;
	Statement statement;
	PreparedStatement pstmt;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	public void homeButton(ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void viewRecordOptions() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ViewRecords/viewRecordsOptions.fxml"));
			contentArea.getChildren().removeAll();
			contentArea.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ImportDataOptions() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ImportExceltoDB/ImportExcelData.fxml"));
			contentArea.getChildren().removeAll();
			contentArea.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ManageVehicles(ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/ManageVehicle/ManageVehicle.fxml"));
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ManageRoutes(ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/ManageRoute/ManageRoute.fxml"));
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ManageDriver(ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/ManageDriver/manageDriver.fxml"));
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ManageConductors(ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/ManageConductor/manageConductor.fxml"));
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ManageExpense() {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/ManageExpense/ExpenseTypeOption.fxml"));
			contentArea.getChildren().removeAll();
			contentArea.getChildren().setAll(fxml);
			contentArea.setStyle("-fx-background-color: transparent;");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ManageFaculty(ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/ManageFaculty/ManageFaculty.fxml"));
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ManageFee(ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/ManageFee/ManageFee.fxml"));
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ManageReservation(ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/ManageReservation/ManageReservation.fxml"));
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ManageStudents(ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/ManageStudents/ManageStudents.fxml"));
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void VehicleMaintenance(ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/VehicleMaintenance/VehicleMaintenance.fxml"));
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void GenerateReports() {
		try {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/GenerateReports/generateReports.fxml"));
			loader.load();
			Scene scene = new Scene(loader.getRoot());
			Stage stage = new Stage();
			stage.setScene(scene);
			// stage.initModality(Modality.APPLICATION_MODAL); // to disable minimize button
			stage.show();
//			Parent fxml = FXMLLoader.load(getClass().getResource("/GenerateReports/generateReports.fxml"));
//			contentArea.getChildren().removeAll();
//			contentArea.getChildren().setAll(fxml);
		} catch (IOException e) {
		}
	}

	@FXML
	void ManageAdminProfile() {
		try {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Admin/AdminProfile.fxml"));
			loader.load();
			Scene scene = new Scene(loader.getRoot());
			Stage stage = new Stage();
			stage.setScene(scene);
			Image iconImage = new Image(getClass().getResourceAsStream("/Pictures/adminIcon.png"));
			stage.getIcons().add(iconImage); // set image icon on stage
			stage.setTitle(userNameLink.getText() + "'s Profle");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close(ActionEvent event) throws Exception {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	public void min(ActionEvent event) throws Exception {
		Stage stage = (Stage) minButton.getScene().getWindow();
		stage.setIconified(true);

	}

	public void logout(ActionEvent event) throws Exception {

		// Confirm logout from Dashboard
		Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
		confirmationAlert.setTitle("Logout Confirmation!");
		confirmationAlert.setHeaderText("Are you sure you want to logout?");
		confirmationAlert.setContentText("Any unsaved changes may be lost.");
		Optional<ButtonType> result = confirmationAlert.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
			String currentTime = sdf.format(new Date());
			String query = "UPDATE LoginRecord SET logout_status = ? WHERE id = '1'";
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, currentTime);
			pstmt.executeUpdate();
			try {

				String LoginStatus = "UPDATE Admin SET login_status = 'offline'";
				PreparedStatement pstmt2 = conn.prepareStatement(LoginStatus);
				pstmt2.executeUpdate();

				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("Error is here" + e);
			}

			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			// stage.initStyle(StageStyle.UNDECORATED);
			root = FXMLLoader.load(getClass().getResource("/Login/login.fxml"));
			stage.setX(250);
			stage.setY(100);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();

		}
	}

	private void initClock() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy \n hh:mm:ss a");
		String currentTime = sdf.format(new Date());
		timeLabel.setText(currentTime);

	}

	void addValuestoMonthChoiceBox() {
		selectMonthChoiceBox.getItems().addAll("January", "Febuary", "March", "April", "May", "June", "July", "August",
				"September", "October", "November", "December");
	}

	private void setCurrentYearOnTextField() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String currentTime = sdf.format(new Date());
		SelectYearTextField.setText(currentTime);

	}

	private void setCurrentMonthOnChoiceField() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
		String currentTime = sdf.format(new Date());
		selectMonthChoiceBox.setValue(currentTime);

	}

	// Set Students in Defaulter who did'nt paid fee yet
	void changeStudentsFeeStatus() {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			// Change status of students to 'not paid' who didn't pay fee
			String updateNotPaidSQL = "UPDATE Students SET FeeStatus = 'Not Paid' WHERE CNIC NOT IN "
					+ "(SELECT cnic FROM fee_records WHERE month = ? AND year = ?)";

			try (PreparedStatement pstmtNotPaid = conn.prepareStatement(updateNotPaidSQL)) {
				pstmtNotPaid.setString(1, selectMonthChoiceBox.getValue().toUpperCase());
				pstmtNotPaid.setString(2, SelectYearTextField.getText());
				pstmtNotPaid.executeUpdate();
				pstmtNotPaid.close();
			}

			// Change status of students to 'paid' who paid fee
			String updatePaidSQL = "UPDATE Students SET FeeStatus = 'Paid' WHERE CNIC IN "
					+ "(SELECT cnic FROM fee_records WHERE month = ? AND year = ?)";

			try (PreparedStatement pstmtPaid = conn.prepareStatement(updatePaidSQL)) {
				pstmtPaid.setString(1, selectMonthChoiceBox.getValue().toUpperCase());
				pstmtPaid.setString(2, SelectYearTextField.getText());
				pstmtPaid.executeUpdate();
				pstmtPaid.close();
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error in Change status of Student" + e);
		}

	}

	// Set Faculty in Defaulter who did'nt paid fee yet
	void changeFacultyFeeStatus() {

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			// Change status of Faculty to 'not paid' who didn't pay fee
			String updateNotPaidSQL = "UPDATE Faculty SET FeeStatus = 'Not Paid' WHERE CNIC NOT IN "
					+ "(SELECT cnic FROM fee_records WHERE month = ? AND year = ?)";

			try (PreparedStatement pstmtNotPaid = conn.prepareStatement(updateNotPaidSQL)) {
				pstmtNotPaid.setString(1, selectMonthChoiceBox.getValue().toUpperCase());
				pstmtNotPaid.setString(2, SelectYearTextField.getText());
				pstmtNotPaid.executeUpdate();
				pstmtNotPaid.close();

			}

			// Change status of Faculty to 'paid' who paid fee
			String updatePaidSQL = "UPDATE Faculty SET FeeStatus = 'Paid' WHERE CNIC IN "
					+ "(SELECT cnic FROM fee_records WHERE month = ? AND year = ?)";

			try (PreparedStatement pstmtPaid = conn.prepareStatement(updatePaidSQL)) {
				pstmtPaid.setString(1, selectMonthChoiceBox.getValue().toUpperCase());
				pstmtPaid.setString(2, SelectYearTextField.getText());
				pstmtPaid.executeUpdate();
				pstmtPaid.close();
			}

		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error in Change status of Faculty" + e);
		}

	}

	void Fee_StatusCount() {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			// _________________________________ For Fee Defaulter_______________________

			// Count status of Faculty who didn't pay fee
			String FacultyNotPaidSQL = "Select COUNT(*) AS total From Faculty WHERE CNIC NOT IN "
					+ "(SELECT cnic FROM fee_records WHERE month = ? AND year = ?)";
			try (PreparedStatement pstmtNotPaid = conn.prepareStatement(FacultyNotPaidSQL)) {
				pstmtNotPaid.setString(1, selectMonthChoiceBox.getValue().toUpperCase());
				pstmtNotPaid.setString(2, SelectYearTextField.getText());
				ResultSet rs = pstmtNotPaid.executeQuery();
				int NotPaidFculty = rs.getInt("total");
				DefaultFacultyLabel.setText((Integer.toString(NotPaidFculty)));
			}

			// Count status of students who didn't pay fee
			String StudentNotPaidSQL = "Select COUNT(*) AS total From Students WHERE CNIC NOT IN "
					+ "(SELECT cnic FROM fee_records WHERE month = ? AND year = ?)";
			try (PreparedStatement pstmtNotPaid = conn.prepareStatement(StudentNotPaidSQL)) {
				pstmtNotPaid.setString(1, selectMonthChoiceBox.getValue().toUpperCase());
				pstmtNotPaid.setString(2, SelectYearTextField.getText());
				ResultSet rs = pstmtNotPaid.executeQuery();
				int NotPaidFculty = rs.getInt("total");
				DefaultStudentLabel.setText((Integer.toString(NotPaidFculty)));
			}

			// _________________________________________ For Fee Paid_______________________

			// Count status of Faculty who paid fee
			String FacultyPaidSQL = "Select COUNT(*) AS total From Faculty WHERE CNIC IN "
					+ "(SELECT cnic FROM fee_records WHERE month = ? AND year = ?)";
			try (PreparedStatement pstmtPaid = conn.prepareStatement(FacultyPaidSQL)) {
				pstmtPaid.setString(1, selectMonthChoiceBox.getValue().toUpperCase());
				pstmtPaid.setString(2, SelectYearTextField.getText());
				ResultSet rs = pstmtPaid.executeQuery();
				int NotPaidFculty = rs.getInt("total");
				FacultyPaidLabel.setText((Integer.toString(NotPaidFculty)));
			}

			// Count status of students who paid fee
			String StudentPaidSQL = "Select COUNT(*) AS total From Students WHERE CNIC IN "
					+ "(SELECT cnic FROM fee_records WHERE month = ? AND year = ?)";
			try (PreparedStatement pstmtPaid = conn.prepareStatement(StudentPaidSQL)) {
				pstmtPaid.setString(1, selectMonthChoiceBox.getValue().toUpperCase());
				pstmtPaid.setString(2, SelectYearTextField.getText());
				ResultSet rs = pstmtPaid.executeQuery();
				int NotPaidFculty = rs.getInt("total");
				StudentPaidLabel.setText((Integer.toString(NotPaidFculty)));
			}

			int totalpaidStudents = Integer.parseInt(StudentPaidLabel.getText());

			int totalpaidFaculty = Integer.parseInt(FacultyPaidLabel.getText());

			int totalDefaultStudents = Integer.parseInt(DefaultStudentLabel.getText());

			int totalDefaultFaculty = Integer.parseInt(DefaultFacultyLabel.getText());

			// show data on PIE Chart
			ObservableList<PieChart.Data> piechartdata = FXCollections.observableArrayList(
					new PieChart.Data("Fee Paid", totalpaidStudents + totalpaidFaculty),
					new PieChart.Data("Fee Defaulter", totalDefaultStudents + totalDefaultFaculty));
			pieChart.setData(piechartdata);
			piechartdata.forEach(data -> data.nameProperty()
					.bind(Bindings.concat(data.getName(), " \n", data.pieValueProperty().intValue())));
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error :" + e);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String usernameSql = "Select username From Admin WHERE login_status = 'online'";
			PreparedStatement pstmt1 = conn.prepareStatement(usernameSql);
			ResultSet rs = pstmt1.executeQuery();
			String username = rs.getString("username");
			rs.next();

			userNameLink.setText(username);
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error in Initialize" + e);
		}
		addValuestoMonthChoiceBox();
		setCurrentMonthOnChoiceField();
		setCurrentYearOnTextField();

		selectMonthChoiceBox.setOnAction(e -> {
			Fee_StatusCount();
		});

		SelectYearTextField.setOnAction(e -> {
			Fee_StatusCount();
		});
		try {

			// Create a timer to update the label every second
			AnimationTimer timer = new AnimationTimer() {
				@Override
				public void handle(long now) {
					initClock();
				}
			};

			// Start the timer
			timer.start();

			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			statement = conn.createStatement();

			feeStatusShowonPieChart();
			// setting Logout status of admin on dashboard
			ResultSet statusupdater = statement
					.executeQuery("SELECT  logout_status FROM LoginRecord ORDER BY id DESC LIMIT 1 ");
			statusupdater.next();
			String statusoffline = statusupdater.getString("logout_status");
			logoutStatusLabel.setText("Last Logout: " + statusoffline);

			// setting login status of admin on dashboard
			ResultSet statusupdater1 = statement
					.executeQuery("SELECT  login_status FROM LoginRecord ORDER BY id DESC LIMIT 1 ");
			statusupdater1.next();
			String statusonline = statusupdater1.getString("login_status");
			loginStatusLabel.setText("Last Login: " + statusonline);

			// Execute the query to count the number of students
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM students");

			// Check if the result set contains any rows
			if (resultSet.next()) {
				// Retrieve the total number of students from the result set
				int totalStudents = resultSet.getInt("total");

				// Update the label text with the total number of students
				StudentCountLabel.setText(Integer.toString(totalStudents));
			}

			ResultSet resultSet2 = statement.executeQuery("SELECT COUNT(*) AS total2 FROM conductor");
			if (resultSet2.next()) {
				int totalconductor = resultSet2.getInt("total2");
				conductorCountLabel.setText(Integer.toString(totalconductor));
			}

			ResultSet resultSet3 = statement.executeQuery("SELECT COUNT(*) AS total3 FROM Faculty");
			if (resultSet3.next()) {
				int totalFaculty = resultSet3.getInt("total3");
				FacultyCountLabel.setText(Integer.toString(totalFaculty));
			}

			ResultSet resultSet4 = statement.executeQuery("SELECT COUNT(*) AS total4 FROM vehicles");
			if (resultSet4.next()) {
				int totalvehicles = resultSet4.getInt("total4");
				vehicleCountLabel.setText(Integer.toString(totalvehicles));
			}

			ResultSet resultSet5 = statement.executeQuery("SELECT COUNT(*) AS total5 FROM Driver");
			if (resultSet5.next()) {
				int totalDriver = resultSet5.getInt("total5");
				driverCountLabel.setText(Integer.toString(totalDriver));
			}

			ResultSet resultSet6 = statement.executeQuery("SELECT COUNT(*) AS total6 FROM Routes");
			if (resultSet6.next()) {
				int totalRoutes = resultSet6.getInt("total6");
				RouteCountLabel.setText(Integer.toString(totalRoutes));

				resultSet.close();
				resultSet2.close();
				resultSet3.close();
				resultSet4.close();
				resultSet5.close();
				resultSet6.close();
			}

		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error :" + e);
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
		changeFacultyFeeStatus();
		changeStudentsFeeStatus();
		Fee_StatusCount();

	}

	void feeStatusShowonPieChart() {

	}

	// Search Occupation Of vehicle
	public void searchVehicleOccupation() {
		try {

			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			String vehicleNoToSearch = VehicleNoTextField.getText();
			if (VehicleNoTextField.getText() == "") {
				warnLabel.setText(" Please Enter Vehicle Reg No.");
			} else {
				// Prepare SQL statement for insertion
				String sqlQuery = "SELECT registration_number, vehicle_capacity, vehicle_occupation FROM vehicles where registration_number='"
						+ vehicleNoToSearch + "'";
				statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlQuery);
				if (resultSet.next()) {
					VCLabel.setVisible(true);
					VOLabel.setVisible(true);
					StLftLabel.setVisible(true);
					VNLabel.setVisible(true);
					String registrationNumber = resultSet.getString("registration_number");
					String vehicleCapacity = resultSet.getString("vehicle_capacity");
					String vehicleOccupation = resultSet.getString("vehicle_occupation");
					vehicleNumberLabel.setText(registrationNumber);
					occupationLabel.setText(vehicleOccupation);
					capacityLabel.setText(vehicleCapacity);
					// Parse the strings to integers
					int capacity = Integer.parseInt(vehicleCapacity);
					int occupation = Integer.parseInt(vehicleOccupation);

					// Subtract the values
					int seatsLeft = capacity - occupation;
					seatLeftLabel.setText(Integer.toString(seatsLeft));
					warnLabel.setText(seatsLeft + " Seats left");

					resultSet.close();
				} else {
					warnLabel.setText("Vehicle not found");
				}
			} // OUter Else closed here
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error :" + e);
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
	}
}
