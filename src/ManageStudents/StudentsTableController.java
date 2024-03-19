package ManageStudents;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentsTableController {

	@FXML
	private TableView<StudentData> StudentsTable;

	@FXML
	private TableColumn<StudentData, String> AddressCol;
	@FXML
	private TableColumn<StudentData, String> Name_Col;
	@FXML
	private TableColumn<StudentData, String> CNIC_Col;

	@FXML
	private TableColumn<StudentData, String> Contact_Col;

	@FXML
	private TableColumn<StudentData, String> DOBCol;

	@FXML
	private TableColumn<StudentData, String> Department_Col;

	@FXML
	private TableColumn<StudentData, String> Father_NameCol;

	@FXML
	private TableColumn<StudentData, String> Fee_StatusCol;

	@FXML
	private TableColumn<StudentData, String> GenderCol;

	@FXML
	private TableColumn<StudentData, String> IdCol;

	@FXML
	private TableColumn<StudentData, String> PicknDropCol;

	@FXML
	private TableColumn<StudentData, String> RouteID_Col;

	@FXML
	private TableColumn<StudentData, String> SAPIDCol;

	@FXML
	private TableColumn<StudentData, String> Semester_Col;

	@FXML
	private TableColumn<StudentData, String> VehicleID_Col;

	@FXML
	private TextField searchTextField;

	// SQLite database connection
	private Connection conn;
	PreparedStatement statement;
	// Define the database URL, username, and password
	private static final String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	public void initialize() {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Students");

			while (resultSet.next()) {

				int StudentID = resultSet.getInt("StudentID");
				String SAPID = resultSet.getString("SAPID");
				String Name = resultSet.getString("Name");
				String FatherName = resultSet.getString("FatherName");
				String DOB = resultSet.getString("DOB");
				String Semester = resultSet.getString("Semester");
				String Department = resultSet.getString("Department");
				String Contact = resultSet.getString("Contact");
				String CNIC = resultSet.getString("CNIC");
				String PicknDropPoint = resultSet.getString("PicknDropPoint");
				String RouteID = resultSet.getString("RouteID");
				String BusID = resultSet.getString("BusID");
				String Address = resultSet.getString("Address");
				String FeeStatus = resultSet.getString("FeeStatus");
				String gender = resultSet.getString("gender");

				IdCol.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
				SAPIDCol.setCellValueFactory(new PropertyValueFactory<>("SAPID"));
				Name_Col.setCellValueFactory(new PropertyValueFactory<>("Name"));
				Father_NameCol.setCellValueFactory(new PropertyValueFactory<>("FatherName"));
				DOBCol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
				Semester_Col.setCellValueFactory(new PropertyValueFactory<>("Semester"));
				Department_Col.setCellValueFactory(new PropertyValueFactory<>("Department"));
				Contact_Col.setCellValueFactory(new PropertyValueFactory<>("Contact"));
				CNIC_Col.setCellValueFactory(new PropertyValueFactory<>("CNIC"));
				PicknDropCol.setCellValueFactory(new PropertyValueFactory<>("PicknDropPoint"));
				RouteID_Col.setCellValueFactory(new PropertyValueFactory<>("RouteID"));
				VehicleID_Col.setCellValueFactory(new PropertyValueFactory<>("BusID"));
				AddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
				Fee_StatusCol.setCellValueFactory(new PropertyValueFactory<>("FeeStatus"));
				GenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));

				StudentData Student = new StudentData(StudentID, SAPID, Name, FatherName, DOB, Semester, Department,
						Contact, CNIC, PicknDropPoint, RouteID, BusID, Address, FeeStatus, gender);
				StudentsTable.getItems().add(Student);

			}
			resultSet.close();
			statement.close();

			FilteredList<StudentData> filteredData;
			// Create a filtered list from the original list
			filteredData = new FilteredList<>(StudentsTable.getItems(), p -> true);

			// Set the filter predicate based on the search query
			searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(Student -> {
					if (newValue == null || newValue.isEmpty()) {
						return true; // Show all items if search query is empty
					}

					String lowerCaseQuery = newValue.toLowerCase();
					// Match against different properties of the related Data object
					if (isInteger(newValue) && Student.getStudentID() == Integer.parseInt(newValue)) {
						return true; // Match against iD
					}
					if (Student.getFatherName().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against FatherName
					} else if (Student.getName().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against name
					} else if (Student.getCNIC().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against CNIC
					} else if (Student.getSAPID().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against SAP
					} else if (Student.getSemester().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Semester
					} else if (Student.getDepartment().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Department
					} else if (Student.getGender().toLowerCase().contains(lowerCaseQuery)) {
						return true; // Match against Gender
					}

					// No match
					return false;
				});
			});

			// Set the filtered list as the new items of the TableView
			StudentsTable.setItems(filteredData);

			// ...
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Utility method to check if a string is a valid integer
	private boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
