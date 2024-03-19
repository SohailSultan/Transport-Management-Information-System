package ManageConductor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ConductorTableViewController {

	@FXML
	private TableColumn<ConductorData, String> CNICCol;

	@FXML
	private TableColumn<ConductorData, Integer> ConductorIDCol;

	@FXML
	private TableColumn<ConductorData, String> ContactCol;

	@FXML
	private TableColumn<ConductorData, String> DoBCol;

	@FXML
	private TableColumn<ConductorData, String> FatherNameCol;

	@FXML
	private TableColumn<ConductorData, String> NameCol;

	@FXML
	private TableColumn<ConductorData, String> VehicleIDCol;

	@FXML
	private TableColumn<ConductorData, String> AgeCol;

	@FXML
	private TableColumn<ConductorData, String> SalaryCol;

	@FXML
	private TableView<ConductorData> ConductorTable;

	@FXML
	private ProgressIndicator loadingIndicator;

	@FXML
	private TextField searchTextField;
	@FXML
	private JFXButton PrintButton;

	@FXML
	private AnchorPane contentPane;
	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	private FilteredList<ConductorData> filteredData;

	@FXML
	void PrintData() {
		loadingIndicator.setVisible(true);
		// Disable the generate button to prevent multiple clicks
		PrintButton.setDisable(true);

		// Show a loading indicator or progress bar
		showLoadingIndicator();

		// Start a new thread for report generation
		Thread reportGenerationThread = new Thread(() -> {
			try {
				JasperPrint jasperprint = generateReport();

				// Show the report in the UI thread
				Platform.runLater(() -> {
					showReport(jasperprint);

					// Re-enable the generate button and hide the loading indicator
					PrintButton.setDisable(false);
					hideLoadingIndicator();
				});
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Report generation failed." + e);
			}
		});

		reportGenerationThread.start();
	}

	private JasperPrint generateReport() throws JRException {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			JasperDesign jasperDesign = JRXmlLoader.load("src\\GenerateReports\\ConductorData.jrxml");
			String query = "SELECT * FROM conductor";
			JRDesignQuery jrquery = new JRDesignQuery();
			jrquery.setText(query);
			jasperDesign.setQuery(jrquery);

			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);

			conn.close();
			return jasperPrint;
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Report generation failed." + e);
		}
		return null;
	}

	private void showReport(JasperPrint jasperPrint) {
		if (jasperPrint != null) {
			JasperViewer viewer = new JasperViewer(jasperPrint, false);
			viewer.setTitle("Conductor List");
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

	public void initialize() {
		try {

			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM conductor");

			while (resultSet.next()) {
				int conductorId = resultSet.getInt("conductor_id");
				String name = resultSet.getString("name");
				String fatherName = resultSet.getString("father_name");
				String idCard = resultSet.getString("id_card");
				String Age = resultSet.getString("age");
				String contact = resultSet.getString("contact");
				String salary = resultSet.getString("salary");
				String busId = resultSet.getString("Vehicle_Regno");

				ConductorIDCol.setCellValueFactory(new PropertyValueFactory<>("conductorId"));
				NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
				FatherNameCol.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
				CNICCol.setCellValueFactory(new PropertyValueFactory<>("idCard"));
				AgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));
				ContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
				SalaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
				VehicleIDCol.setCellValueFactory(new PropertyValueFactory<>("busId"));
				ConductorData conductor = new ConductorData(conductorId, name, fatherName, idCard, Age, contact, salary,
						busId);
				ConductorTable.getItems().add(conductor);
			}
			resultSet.close();
			statement.close();

			// Create a filtered list from the original list
			filteredData = new FilteredList<>(ConductorTable.getItems(), p -> true);

			// Set the filter predicate based on the search query
			searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(conductor -> {
					if (newValue == null || newValue.isEmpty()) {
						return true; // Show all items if search query is empty
					}

					String lowerCaseQuery = newValue.toLowerCase();

					// Match against different properties of the ConductorData object
					if (conductor.getName().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against name
					} else if (conductor.getFatherName().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against father name
					} else if (conductor.getIdCard().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against CNIC
					}

					// No match
					return false;
				});
			});

			// Set the filtered list as the new items of the TableView
			ConductorTable.setItems(filteredData);

			// ...
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

//		ConductorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//			if (newSelection != null) {
//				int conductorId = newSelection.getConductorId();
//				String name = newSelection.getName();
//				String fatherName = newSelection.getFatherName();
//				String idCard = newSelection.getIdCard();
//				String age = newSelection.getAge();
//				String contact = newSelection.getContact();
//				String salary = newSelection.getSalary();
//				String busId = newSelection.getBusId();
//
//				/*// Do something with the selected values
//				System.out.println("Selected Conductor ID: " + conductorId);
//				System.out.println("Selected Name: " + name);
//				System.out.println("Selected Father Name: " + fatherName);
//				System.out.println("Selected CNIC: " + idCard);
//				System.out.println("Selected Age: " + age);
//				System.out.println("Selected Contact: " + contact);
//				System.out.println("Selected Salary: " + salary);
//				System.out.println("Selected Vehicle ID: " + busId);*/
//			}
//		});
	}

}
