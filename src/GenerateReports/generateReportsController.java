package GenerateReports;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class generateReportsController {

	@FXML
	private AnchorPane contentPane, ParametersPane;
	@FXML
	private TextField Textfield2, TextField3;
	@FXML
	private Label TextField3Label;
	@FXML
	private ChoiceBox<String> selectMonthChoiceBox;

	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	@FXML
	public void initialize() {
		selectMonthChoiceBox.getItems().addAll("", "January", "February", "March", "April", "May", "June", "July",
				"August", "September", "October", "November", "December");

		SimpleDateFormat month = new SimpleDateFormat("MMMM");
		selectMonthChoiceBox.setValue(month.format(new Date()));

		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		Textfield2.setText(year.format(new Date()));
	}

	@FXML
	private ProgressIndicator loadingIndicator;

	void PrintData(String jrxmlFileName) {
		loadingIndicator.setVisible(true);
		showLoadingIndicator();
		insertNotification("Admin Generated Report Successfully");

		Thread reportGenerationThread = new Thread(() -> {
			try {
				JasperPrint jasperPrint = generateReport(jrxmlFileName);

				Platform.runLater(() -> {
					showReport(jasperPrint);
					hideLoadingIndicator();
				});
			} catch (Exception e) {
				Platform.runLater(() -> showReportGenerationErrorAlert(e));
				e.printStackTrace();
			}
		});

		reportGenerationThread.start();
	}

	// Rest of the code remains the same

	private JasperPrint generateReport(String jasperFileName) throws JRException {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObjectFromFile("src\\GenerateReports\\JasperFiles\\" + jasperFileName);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);

			conn.close();
			return jasperPrint;
		} catch (SQLException e) {
			showReportGenerationErrorAlert(e);
		}
		return null;
	}

	void PrintDataOfComplexqueries(String jrxmlFileName, Map<String, Object> parameters) {
		loadingIndicator.setVisible(true);
		showLoadingIndicator();
		insertNotification("Admin Generated Report Successfully");

		Thread reportGenerationThread = new Thread(() -> {
			try {
				JasperPrint jasperPrint = generateReportOfComplexQueries(jrxmlFileName, parameters);

				Platform.runLater(() -> {
					showReport(jasperPrint);
					hideLoadingIndicator();
				});
			} catch (Exception e) {
				Platform.runLater(() -> showReportGenerationErrorAlert(e));
				e.printStackTrace();
			}
		});

		reportGenerationThread.start();
	}

	private JasperPrint generateReportOfComplexQueries(String jasperFileName, Map<String, Object> parameters)
			throws JRException {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObjectFromFile("src\\GenerateReports\\JasperFiles\\" + jasperFileName);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

			conn.close();
			return jasperPrint;
		} catch (SQLException e) {
			showReportGenerationErrorAlert(e);
		}
		return null;
	}

	@FXML
	void conductor() {
//		for (Node child : ParametersPane.getChildren()) {
//	        child.setVisible(false);
//	    }
//		Textfield1.setVisible(true);
//		Textfield2.setVisible(true);
		PrintData("/ConductorData.jasper");
	}

	@FXML
	void MaintenanceAll() {
		PrintData("/Maintenance.jasper");
	}

	@FXML
	void VehiclesAll() {
		PrintData("/VehiclesData.jasper");
	}

	@FXML
	void RoutesAll() {
		PrintData("/AllRoutes.jasper");
	}

	private void showReport(JasperPrint jasperPrint) {
		if (jasperPrint != null) {
			JasperViewer viewer = new JasperViewer(jasperPrint, false);
			viewer.setTitle("Transport Management Information System For IIUI (REports)");
			viewer.setVisible(true);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Report generation failed.");
			// Handle the case where jasperPrint is nulls
		}
	}

	private void showLoadingIndicator() {
		// Show a loading indicator or progress bar
		loadingIndicator.setVisible(true);
	}

	private void hideLoadingIndicator() {
		// Hide the loading indicator or progress bar
		loadingIndicator.setVisible(false);
	}

	// Similar methods for showReport, showLoadingIndicator, and
	// hideLoadingIndicator as in the original code

	private void showReportGenerationErrorAlert(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText("Report generation failed: " + e.getMessage());
		alert.showAndWait();
	}

	private void insertNotification(String notification) {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			PreparedStatement statement;
			String SelectQuery = "SELECT username FROM Admin WHERE login_status = 'online'";
			statement = conn.prepareStatement(SelectQuery);
			ResultSet rs = statement.executeQuery();
			String username = rs.getString("username");
			rs.next();
			rs.close();

			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
			String currentTime = sdf.format(new Date());
			String InsertQuery = "INSERT INTO Notifications (Time, username, Notification) VALUES (?, ? ,?)";
			statement = conn.prepareStatement(InsertQuery);
			statement.setString(1, currentTime);
			statement.setString(2, username);
			statement.setString(3, notification);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error While Sending Notification" + e);
		}
	}

	// __________________________________________ ALL REPORT Options
	// _______________________________________________

	@FXML
	void AllDrivers(ActionEvent event) {
		PrintData("/AllDrivers.jasper");
	}

	@FXML
	void AllExpensesReport(ActionEvent event) {
		PrintData("/All_Expense_Report.jasper");

	}

	@FXML
	void AllFaculty(ActionEvent event) {
		PrintData("/AllFaculty.jasper");

	}

	@FXML
	void AllFeeRecords(ActionEvent event) {
		PrintData("/AllFeeRecord.jasper");

	}

	@FXML
	void AllSalaryReport(ActionEvent event) {
		PrintData("/Salary_Report.jasper");

	}

	@FXML
	void Annual_Salary_Report(ActionEvent event) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("yearParam", Textfield2.getText()); // Replace with the actual parameter name
		PrintDataOfComplexqueries("Salary_Report_Annual.jasper", parameters);

	}

	@FXML
	void AllStudents(ActionEvent event) {
		PrintData("/AllStudents.jasper");

	}

	@FXML
	void AllTokensReports(ActionEvent event) {
		PrintData("/Vehicles_Tokens_Report.jasper");

	}

	@FXML
	void All_Reservation_report(ActionEvent event) {
		PrintData("/Reservation.jasper");

	}

	@FXML
	void Annual_Expense_Report(ActionEvent event) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("yearParam", Textfield2.getText()); // Replace with the actual parameter name
		PrintDataOfComplexqueries("Annual_Expense_Report.jasper", parameters);

	}

	@FXML
	void Annual_Fee_Report(ActionEvent event) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("yearParam", Textfield2.getText()); // Replace with the actual parameter name
		PrintDataOfComplexqueries("FeeRecord_By_Year.jasper", parameters);

	}

	@FXML
	void Conductor_Vehicles_Route_List(ActionEvent event) {
		PrintData("");

	}

	@FXML
	void Driver_Vehicles_Route_List(ActionEvent event) {
		PrintData("");

	}

	@FXML
	void Expense_By_Vehicle(ActionEvent event) {
		PrintData("");

	}

	@FXML
	void FeeReportBy_Faculty(ActionEvent event) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("monthParam", selectMonthChoiceBox.getValue());
		parameters.put("yearParam", Textfield2.getText());
		PrintDataOfComplexqueries("FeeRecord_By_Faculty.jasper", parameters);

	}

	@FXML
	void FeeReportBy_Route(ActionEvent event) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("monthParam", selectMonthChoiceBox.getValue());
		parameters.put("yearParam", Textfield2.getText());
		PrintDataOfComplexqueries("Fee_reportByRoutes.jasper", parameters);

	}

	@FXML
	void FeeReportBy_Students(ActionEvent event) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("monthParam", selectMonthChoiceBox.getValue());
		parameters.put("yearParam", Textfield2.getText());
		PrintDataOfComplexqueries("FeeRecord_By_Students.jasper", parameters);
	}

	@FXML
	void FeeReportBy_Vehicle(ActionEvent event) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("monthParam", selectMonthChoiceBox.getValue());
		parameters.put("yearParam", Textfield2.getText());
		PrintDataOfComplexqueries("Fee_reportByVehicles.jasper", parameters);
	}

	@FXML
	void MaintenanceCompletedList(ActionEvent event) {
		PrintData("/Maintenance_Completed.jasper");

	}

	@FXML
	void MaintenancePendingList(ActionEvent event) {
		PrintData("/Maintenance_Pending.jasper");

	}

	@FXML
	void Monthly_Expense_report(ActionEvent event) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("monthParam", selectMonthChoiceBox.getValue());
		parameters.put("yearParam", Textfield2.getText());
		PrintDataOfComplexqueries("monthly_Expense_Report.jasper", parameters);
	}

	@FXML
	void Monthly_Fee_report(ActionEvent event) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("monthParam", selectMonthChoiceBox.getValue());
		parameters.put("yearParam", Textfield2.getText());
		PrintDataOfComplexqueries("FeeRecord_By_Month.jasper", parameters);

	}

	@FXML
	void Monthly_Salary_report(ActionEvent event) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("monthParam", selectMonthChoiceBox.getValue());
		parameters.put("yearParam", Textfield2.getText());
		PrintDataOfComplexqueries("Salary_Report_Monthly.jasper", parameters);

	}

	@FXML
	void PaidPassenger_SingleRouteList(ActionEvent event) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("monthParam", selectMonthChoiceBox.getValue());
		parameters.put("yearParam", Textfield2.getText());
		parameters.put("routenoParam", TextField3.getText());
		PrintDataOfComplexqueries("Passenger_SingleRoute_List.jasper", parameters);

	}

	@FXML
	void PaidPassenger_VehicleList(ActionEvent event) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("monthParam", selectMonthChoiceBox.getValue());
		parameters.put("yearParam", Textfield2.getText());
		parameters.put("vehiclenoParam", TextField3.getText());
		PrintDataOfComplexqueries("Passenger_SingleVehicle_List.jasper", parameters);

	}

	@FXML
	void ReservationCompleted(ActionEvent event) {
		PrintData("/Reservation_Completed.jasper");

	}

	@FXML
	void ReservationPending(ActionEvent event) {
		PrintData("/Reservation_Pending.jasper");

	}

	@FXML
	void RoutesOccupationReport(ActionEvent event) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("monthParam", selectMonthChoiceBox.getValue());
		parameters.put("yearParam", Textfield2.getText());
		PrintDataOfComplexqueries("Route_Occupation_report.jasper", parameters);
	}

	@FXML
	void TokenExpiryReport(ActionEvent event) {
		PrintData("/Vehicles_TokensExpiry_Report.jasper");

	}

	@FXML
	void VehicleOccupationReport(ActionEvent event) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("monthParam", selectMonthChoiceBox.getValue());
		parameters.put("yearParam", Textfield2.getText());
		PrintDataOfComplexqueries("Vehicle_Occupation_report.jasper", parameters);

	}

	@FXML
	void VehicleReport() {
		TextField3Label.setText("Enter Vehicle Reg. No");
	}

	@FXML
	void RouteReport() {
		TextField3Label.setText("Enter Route No");
	}

	@FXML
	void StudentReport() {
		TextField3Label.setText("Enter SAP ID");
	}

	@FXML
	void FacultyReport() {
		TextField3Label.setText("Enter CNIC");
	}

	@FXML
	void DriverReport() {
		TextField3Label.setText("Enter CNIC");
	}

	@FXML
	void ConductorReport() {
		TextField3Label.setText("Enter CNIC");
	}

	@FXML
	void FeeReport() {
		TextField3Label.setText("Enter Vehicle reg No");
	}

	@FXML
	void expenseReport() {
		TextField3Label.setText("Enter Vehicle reg No");
	}

	@FXML
	void SalaryReport() {
	}

	@FXML
	void ReservationReport() {
	}

	@FXML
	void MaintenanceReport() {

	}

	@FXML
	void TokenReport() {

	}
}
