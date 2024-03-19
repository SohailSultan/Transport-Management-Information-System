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

import com.gluonhq.charm.glisten.control.ProgressIndicator;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DashboardController implements Initializable {
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
	private ProgressIndicator Progressindic;
	@FXML
	private ProgressIndicator Progressindic1;
	@FXML
	private ProgressIndicator Progressindic2;
	@FXML
	private ProgressIndicator Progressindic3;
	@FXML
	private ProgressIndicator Progressindic4;
	@FXML
	private ProgressIndicator Progressindic5;

	@FXML
	private TextField SelectYearTextField;
	private Connection conn;
	// SQLite database connection
	Statement statement;
	PreparedStatement pstmt;
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		RotateProgress();
		selectMonthChoiceBox.setOnAction(e -> {
			Fee_StatusCount();
		});

		SelectYearTextField.setOnAction(e -> {
			Fee_StatusCount();
		});

		// Create a timer to update the label every second
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				initClock();
			}
		};

		// Start the timer
		timer.start();
		addValuestoMonthChoiceBox();
		setCurrentMonthOnChoiceField();
		setCurrentYearOnTextField();

		// Run heavy operations on background thread
		Platform.runLater(this::performHeavyOperations);

		// Update UI with the results of data processing
		Fee_StatusCount();

		// add Username to Admin
		SetAdminProfile();

		ShowLoginStatus();

	}

	private void ChangeReaminingBal_offullPayment() {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			String sqlQuery = "UPDATE fee_records SET RemainingBalance = '0' WHERE paymentPlan = 'Advance' OR paymentPlan = 'Full'";
			pstmt = conn.prepareStatement(sqlQuery);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error while updating RemainingBalance");
		}

	}

	private void ChangeReaminingBal_of2ndInstallment() {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			String sqlQuery = "UPDATE fee_records SET RemainingBalance = '0' WHERE paymentPlan = 'Last Installment2' OR paymentPlan = 'Last Installment1'";
			pstmt = conn.prepareStatement(sqlQuery);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error while updating RemainingBalance");
		}
	}

	private void UpdateOccupation() {
		String selectedMonth = selectMonthChoiceBox.getValue();
		String selectedYear = SelectYearTextField.getText();

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			String sqlQuery2 = "UPDATE Routes\r\n"
					+ "SET occupation = (SELECT COUNT(COALESCE(s.BusID, f.BusID)) AS 'Total Passengers'\r\n"
					+ "                               FROM fee_records fr\r\n"
					+ "                               LEFT JOIN Students s ON fr.cnic = s.CNIC \r\n"
					+ "                               LEFT JOIN Faculty f ON fr.cnic = f.CNIC \r\n"
					+ "                               WHERE fr.month = ? AND fr.year = ?\r\n"
					+ "                               AND COALESCE(s.RouteID, f.RouteID) = Routes.route_id);\r\n";
			pstmt = conn.prepareStatement(sqlQuery2);
			pstmt.setString(1, selectedMonth);
			pstmt.setString(2, selectedYear);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error while updating Route Occupation \n" + e);
			alert.showAndWait();
		}

	}

	private void UpdateVehicleOccupation() {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			String sqlQuery1 = "UPDATE vehicles\r\n" + "SET vehicle_occupation = (\r\n"
					+ "  SELECT Routes.occupation\r\n" + "  FROM Routes\r\n"
					+ "  WHERE vehicles.route_id = Routes.route_id\r\n" + ");";
			pstmt = conn.prepareStatement(sqlQuery1);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error while updating Vehicle Occupation \n" + e);
			alert.showAndWait();
		}

	}

	private void initClock() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy \n hh:mm:ss a");
		String currentTime = sdf.format(new Date());
		timeLabel.setText(currentTime);

	}

	void RotateProgress() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
			// Start spinning
			Progressindic.setProgress(-1);
			Progressindic1.setProgress(-1);
			Progressindic2.setProgress(-1);
			Progressindic3.setProgress(-1);
			Progressindic4.setProgress(-1);
			Progressindic5.setProgress(-1);
		}), new KeyFrame(Duration.seconds(4), event -> {
			// Stop spinning
			Progressindic.setProgress(0);
			Progressindic1.setProgress(0);
			Progressindic2.setProgress(0);
			Progressindic3.setProgress(0);
			Progressindic4.setProgress(0);
			Progressindic5.setProgress(0);
		}));
		timeline.setCycleCount(1); // Run the timeline only once
		// Start the timeline
		timeline.play();
	}

	private void addValuestoMonthChoiceBox() {
		selectMonthChoiceBox.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August",
				"September", "October", "November", "December");
	}

	private void setCurrentMonthOnChoiceField() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
		selectMonthChoiceBox.setValue(sdf.format(new Date()));
	}

	private void setCurrentYearOnTextField() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		SelectYearTextField.setText(sdf.format(new Date()));
	}

	private void SetAdminProfile() {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			String usernameSql = "Select username From Admin WHERE login_status = 'online'";
			pstmt = conn.prepareStatement(usernameSql);
			ResultSet rs = pstmt.executeQuery();
			String username = rs.getString("username");
			rs.next();
			userNameLink.setText(username);
			rs.close();
			conn.close();
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error in Initialize" + e);
		}
	}

	private void ShowLoginStatus() {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			statement = conn.createStatement();

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
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error while showing Login status " + e);
		}
	}

	private void performHeavyOperations() {
		Task<Void> task = new Task<>() {
			@Override
			protected Void call() throws Exception {
				try {
					conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
					statement = conn.createStatement();

					changeFacultyFeeStatus();
					changeStudentsFeeStatus();
					ChangeReaminingBal_offullPayment();
					ChangeReaminingBal_of2ndInstallment();

					// Update UI labels using Platform.runLater
					Platform.runLater(() -> {
						try {
							fetchAndUpdateCounts();
						} catch (SQLException e) {
						}
					});

					// Close resources
					statement.close();
				} catch (SQLException e) {
					// Handle exceptions
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
				return null;
			}
		};

		task.setOnSucceeded(event -> {
			// Update UI or perform any necessary actions after heavy operations are done
			UpdateOccupation();
			UpdateVehicleOccupation();
		});

		task.setOnFailed(event -> {
			// Handle any failures or exceptions
		});

		// Run the task in a separate background thread
		Thread thread = new Thread(task);
		thread.setDaemon(true); // Set the thread as a daemon so it's terminated when the application exits
		thread.start();
	}

	private void fetchAndUpdateCounts() throws SQLException {
		String countQuery = "SELECT " + "(SELECT COUNT(*) FROM students) AS studentCount, "
				+ "(SELECT COUNT(*) FROM conductor) AS conductorCount, "
				+ "(SELECT COUNT(*) FROM Faculty) AS facultyCount, "
				+ "(SELECT COUNT(*) FROM vehicles) AS vehicleCount, " + "(SELECT COUNT(*) FROM Driver) AS driverCount, "
				+ "(SELECT COUNT(*) FROM Routes) AS routeCount ";

		ResultSet resultSet = statement.executeQuery(countQuery);
		if (resultSet.next()) {
			int studentCount = resultSet.getInt("studentCount");
			int conductorCount = resultSet.getInt("conductorCount");
			int facultyCount = resultSet.getInt("facultyCount");
			int vehicleCount = resultSet.getInt("vehicleCount");
			int driverCount = resultSet.getInt("driverCount");
			int routeCount = resultSet.getInt("routeCount");

			// Update UI labels
			StudentCountLabel.setText(Integer.toString(studentCount));
			conductorCountLabel.setText(Integer.toString(conductorCount));
			FacultyCountLabel.setText(Integer.toString(facultyCount));
			vehicleCountLabel.setText(Integer.toString(vehicleCount));
			driverCountLabel.setText(Integer.toString(driverCount));
			RouteCountLabel.setText(Integer.toString(routeCount));
			resultSet.close();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error While showing counts");
			alert.showAndWait();
		}

	}

	private void changeFacultyFeeStatus() {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Change status of Faculty to 'not paid' who didn't pay fee
			String updateNotPaidSQL = "UPDATE Faculty SET FeeStatus = 'Not Paid' WHERE CNIC NOT IN "
					+ "(SELECT cnic FROM fee_records WHERE month = ? AND year = ?)";

			// Change status of Faculty to 'paid' who paid fee
			String updatePaidSQL = "UPDATE Faculty SET FeeStatus = 'Paid' WHERE CNIC IN "
					+ "(SELECT cnic FROM fee_records WHERE month = ? AND year = ?)";

			// Execute update queries using prepared statements
			try (PreparedStatement pstmtNotPaid = conn.prepareStatement(updateNotPaidSQL);
					PreparedStatement pstmtPaid = conn.prepareStatement(updatePaidSQL)) {

				String selectedMonth = selectMonthChoiceBox.getValue();
				String selectedYear = SelectYearTextField.getText();

				pstmtNotPaid.setString(1, selectedMonth);
				pstmtNotPaid.setString(2, selectedYear);
				pstmtNotPaid.executeUpdate();

				pstmtPaid.setString(1, selectedMonth);
				pstmtPaid.setString(2, selectedYear);
				pstmtPaid.executeUpdate();
			}
		} catch (SQLException e) {
			// Handle exceptions
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

	private void changeStudentsFeeStatus() {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Change status of students to 'not paid' who didn't pay fee
			String updateNotPaidSQL = "UPDATE Students SET FeeStatus = 'Not Paid' WHERE CNIC NOT IN "
					+ "(SELECT cnic FROM fee_records WHERE month = ? AND year = ?)";

			// Change status of students to 'paid' who paid fee
			String updatePaidSQL = "UPDATE Students SET FeeStatus = 'Paid' WHERE CNIC IN "
					+ "(SELECT cnic FROM fee_records WHERE month = ? AND year = ?)";

			// Execute update queries using prepared statements
			try (PreparedStatement pstmtNotPaid = conn.prepareStatement(updateNotPaidSQL);
					PreparedStatement pstmtPaid = conn.prepareStatement(updatePaidSQL)) {

				String selectedMonth = selectMonthChoiceBox.getValue();
				String selectedYear = SelectYearTextField.getText();

				pstmtNotPaid.setString(1, selectedMonth);
				pstmtNotPaid.setString(2, selectedYear);
				pstmtNotPaid.executeUpdate();

				pstmtPaid.setString(1, selectedMonth);
				pstmtPaid.setString(2, selectedYear);
				pstmtPaid.executeUpdate();
			}
		} catch (SQLException e) {
			// Handle exceptions
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

	private void Fee_StatusCount() {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			// Count status of Faculty and students who didn't pay fee
			String notPaidSQL = " SELECT  (SELECT COUNT(*) FROM Faculty WHERE CNIC NOT IN "
					+ "(SELECT cnic FROM fee_records WHERE month = ? AND year = ?)) AS notpaidfacultyCount,"
					+ "(SELECT COUNT(*) FROM Students WHERE CNIC NOT IN "
					+ "(SELECT cnic FROM fee_records WHERE month = ? AND year = ?)) AS notpaidstudentCount";

			// Count status of Faculty and students who paid fee
			String paidSQL = " SELECT  (SELECT COUNT(*) FROM Faculty WHERE CNIC IN "
					+ "(SELECT cnic FROM fee_records WHERE month = ? AND year = ?)) AS paidfacultyCount,"
					+ "(SELECT COUNT(*) FROM Students WHERE CNIC IN "
					+ "(SELECT cnic FROM fee_records WHERE month = ? AND year = ?)) AS paidstudentCount";

			// Execute count queries using prepared statements
			try (PreparedStatement pstmtNotPaid = conn.prepareStatement(notPaidSQL);
					PreparedStatement pstmtPaid = conn.prepareStatement(paidSQL)) {

				String selectedMonth = selectMonthChoiceBox.getValue();
				String selectedYear = SelectYearTextField.getText();

				// Set parameters for both prepared statements
				pstmtNotPaid.setString(1, selectedMonth);
				pstmtNotPaid.setString(2, selectedYear);
				pstmtNotPaid.setString(3, selectedMonth);
				pstmtNotPaid.setString(4, selectedYear);

				pstmtPaid.setString(1, selectedMonth);
				pstmtPaid.setString(2, selectedYear);
				pstmtPaid.setString(3, selectedMonth);
				pstmtPaid.setString(4, selectedYear);

				// Execute queries
				ResultSet rsNotPaid = pstmtNotPaid.executeQuery();
				ResultSet rsPaid = pstmtPaid.executeQuery();

				// Get the counts from the result sets

				int notpaidFacultyCount = rsNotPaid.getInt("notpaidfacultyCount");
				int notpaidStudentCount = rsNotPaid.getInt("notpaidstudentCount");
				int paidFacultyCount = rsPaid.getInt("paidfacultyCount");
				int paidStudentCount = rsPaid.getInt("paidstudentCount");

				// Close result sets
				rsNotPaid.close();
				rsPaid.close();

				// Update UI labels
				DefaultFacultyLabel.setText(Integer.toString(notpaidFacultyCount));
				DefaultStudentLabel.setText(Integer.toString(notpaidStudentCount));
				FacultyPaidLabel.setText(Integer.toString(paidFacultyCount));
				StudentPaidLabel.setText(Integer.toString(paidStudentCount));

				// Update PIE chart data
				ObservableList<PieChart.Data> piechartdata = FXCollections.observableArrayList(
						new PieChart.Data("Fee Paid", paidStudentCount + paidFacultyCount),
						new PieChart.Data("Fee Defaulter", notpaidStudentCount + notpaidFacultyCount));
				pieChart.setData(piechartdata);
				piechartdata.forEach(data -> data.nameProperty()
						.bind(Bindings.concat(data.getName(), " \n", data.pieValueProperty().intValue())));
			}
		} catch (SQLException e) {
			// Handle exceptions
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

	public void searchVehicleOccupation() {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			String vehicleNoToSearch = VehicleNoTextField.getText();
			if (vehicleNoToSearch.isEmpty()) {
				warnLabel.setText("Please Enter Vehicle Reg No.");
			} else {
				// Prepare SQL statement to retrieve vehicle information
				String sqlQuery = "SELECT registration_number, vehicle_capacity, vehicle_occupation "
						+ "FROM vehicles WHERE registration_number = ?";

				// Execute query using prepared statement
				try (PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {
					pstmt.setString(1, vehicleNoToSearch);
					ResultSet resultSet = pstmt.executeQuery();

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

						// Calculate seats left
						int seatsLeft = capacity - occupation;
						seatLeftLabel.setText(Integer.toString(seatsLeft));
						warnLabel.setText(seatsLeft + " Seats left");

					} else {
						warnLabel.setText("Vehicle not found");
					}

					resultSet.close();
				}
			}
		} catch (SQLException e) {
			// Handle exceptions
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

	public void homeButton(ActionEvent event) {
		loadScene(event, "dashboard.fxml");
	}

	public void viewRecordOptions() {
		loadFXML("/ViewRecords/viewRecordsOptions.fxml");
	}

	public void ImportDataOptions() {
		loadFXML("/ImportExceltoDB/ImportExcelData.fxml");
	}

	public void ManageVehicles(ActionEvent event) {
		loadScene(event, "/ManageVehicle/ManageVehicle.fxml");
	}

	public void ManageRoutes(ActionEvent event) {
		loadScene(event, "/ManageRoute/ManageRoute.fxml");
	}

	public void ManageDriver(ActionEvent event) {
		loadScene(event, "/ManageDriver/manageDriver.fxml");
	}

	public void ManageConductors(ActionEvent event) {
		loadScene(event, "/ManageConductor/manageConductor.fxml");
	}

	public void ManageExpense() {
		loadFXML("/ManageExpense/ExpenseTypeOption.fxml");
		contentArea.setStyle("-fx-background-color: transparent;");
	}

	public void ManageFaculty(ActionEvent event) {
		loadScene(event, "/ManageFaculty/ManageFaculty.fxml");
	}

	public void ManageFee(ActionEvent event) {
		loadScene(event, "/ManageFee/ManageFee.fxml");
	}

	public void ManageReservation(ActionEvent event) {
		loadScene(event, "/ManageReservation/ManageReservation.fxml");
	}

	public void ManageStudents(ActionEvent event) {
		loadScene(event, "/ManageStudents/ManageStudents.fxml");
	}

	public void VehicleMaintenance(ActionEvent event) {
		loadScene(event, "/VehicleMaintenance/VehicleMaintenance.fxml");
	}

	public void GenerateReports() {
		loadStage("/GenerateReports/generateReports.fxml");
	}

	@FXML
	void ManageAdminProfile() {
		loadStage("/Admin/AdminProfile.fxml", userNameLink.getText() + "'s Profile");
	}

	public void close(ActionEvent event) throws Exception {
		closeWindow(closeButton);
	}

	public void min(ActionEvent event) throws Exception {
		minimizeWindow(minButton);
	}

	public void logout(ActionEvent event) throws Exception {
		confirmLogout(event);
	}

	private void loadScene(ActionEvent event, String fxmlPath) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
			Scene scene = new Scene(root);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadFXML(String fxmlPath) {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource(fxmlPath));
			contentArea.getChildren().removeAll();
			contentArea.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadStage(String fxmlPath) {
		loadStage(fxmlPath, null);
	}

	private void loadStage(String fxmlPath, String title) {
		try {
			loader = new FXMLLoader();
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

	private void closeWindow(Node node) {
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}

	private void minimizeWindow(Node node) {
		Stage stage = (Stage) node.getScene().getWindow();
		stage.setIconified(true);
	}

	private void confirmLogout(ActionEvent event) {
		Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
		confirmationAlert.setTitle("Logout Confirmation!");
		confirmationAlert.setHeaderText("Are you sure you want to logout?");
		confirmationAlert.setContentText("Any unsaved changes may be lost.");
		Optional<ButtonType> result = confirmationAlert.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			// Perform logout operations here
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
			String currentTime = sdf.format(new Date());
			String query = "UPDATE LoginRecord SET logout_status = ? WHERE id = '1'";
			insertNotification(userNameLink.getText(), "User Admin Has been logged out Successfully");
			try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
					PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setString(1, currentTime);
				pstmt.executeUpdate();

				String loginStatusUpdateQuery = "UPDATE Admin SET login_status = 'offline'";
				try (PreparedStatement pstmt2 = conn.prepareStatement(loginStatusUpdateQuery)) {
					pstmt2.executeUpdate();
				} catch (SQLException e) {
					System.out.println("Error updating login status: " + e);
				}

				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/Login/login.fxml"));
				stage.setX(250);
				stage.setY(100);
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
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
