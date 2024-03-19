package ImportExceltoDB;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ImportExcelDataController {

	@FXML
	private Pane screen_pane;

	@FXML
	private Parent root;

	Connection conn;
	PreparedStatement pstmt;
	String DB_URL = "jdbc:sqlite:.\\src\\Database\\Transport_System.db  ";
	String DB_USER = "";
	String DB_PASSWORD = "";

	FileChooser fileChooser;
	File selectedFile;
	int rowcount = 0;

	public void fileChooser() {
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Excel Files", "*.xlsx"));
		selectedFile = fileChooser.showOpenDialog(null);
	}

	// to avoid String and Numeric Problem
	private String getCellValueAsString(Cell cell) {
		String cellValue = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case STRING:
				cellValue = cell.getStringCellValue();
				break;
			case NUMERIC:
				cellValue = String.valueOf(cell.getNumericCellValue());
				break;
			// Handle other cell types if needed
			default:
				break;
			}
		}
		return cellValue;
	}

	private void showAlert(String message, String title) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@FXML
	void ImportConductorsData(ActionEvent event) throws Exception {

		fileChooser();
		try {
			if (selectedFile != null) {
				FileInputStream file = new FileInputStream(selectedFile);
				try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
					XSSFSheet sheet = workbook.getSheetAt(0);
					Iterator<Row> rowIterator = sheet.iterator();

					if (rowIterator.hasNext()) {
						rowIterator.next();
					}

					while (rowIterator.hasNext()) {
						Row row = rowIterator.next();
						Iterator<Cell> cellIterator = row.cellIterator();

						if (cellIterator.hasNext()) {

							Cell cell2 = cellIterator.next();
							String value2 = getCellValueAsString(cell2);

							Cell cell3 = cellIterator.next();
							String value3 = getCellValueAsString(cell3);

							Cell cell4 = cellIterator.next();
							String value4 = getCellValueAsString(cell4);

							Cell cell5 = cellIterator.next();
							String value5 = getCellValueAsString(cell5);

							Cell cell6 = cellIterator.next();
							String value6 = getCellValueAsString(cell6);

							Cell cell7 = cellIterator.next();
							String value7 = getCellValueAsString(cell7);

							Cell cell8 = cellIterator.next();
							String value8 = getCellValueAsString(cell8);
							if (value4 != "") {
								// Save Excel's extracted data to Database
								conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
								String query = "INSERT INTO conductor ( name ,father_name, id_card, age, contact ,salary, Vehicle_Regno)"
										+ " VALUES (?, ?, ?, ?, ?, ?, ?)";

								PreparedStatement preparedStatement = conn.prepareStatement(query);
								preparedStatement.setString(1, value2);
								preparedStatement.setString(2, value3);
								preparedStatement.setString(3, value4);
								preparedStatement.setString(4, value5);
								preparedStatement.setString(5, value6);
								preparedStatement.setString(6, value7);
								preparedStatement.setString(7, value8);
								// Add more setString() statements for additional columns if needed
								preparedStatement.executeUpdate();
								rowcount++;

								preparedStatement.close();
							} // IF in IF ends here
//							else {
//								showAlert("Data has been Imported.\n" + rowcount + " Rows Record Added", "Success");
//							}
						} // if in while loop ends here

					} // while loop closed here
					showAlert("Data has been Imported.\n" + rowcount + " Rows Record Added", "Success");
					insertNotification("Admin Imported Data of " + rowcount + " Rows in conductor Data ");

					file.close();

				} catch (SQLException e) {
					showAlert("Data import failed.\n \n" + e, "Failed");

				} catch (NoSuchElementException e) {
					showAlert("Data has been Imported \n." + e, "Success");
				}

			} // if statement closed here

		} catch (Exception e) {
		}

	}

	@FXML
	void ImportDriversData(ActionEvent event) throws Exception {

		fileChooser();
		try {
			if (selectedFile != null) {
				FileInputStream file = new FileInputStream(selectedFile);
				try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
					XSSFSheet sheet = workbook.getSheetAt(0);
					Iterator<Row> rowIterator = sheet.iterator();

					if (rowIterator.hasNext()) {
						rowIterator.next();
					}

					while (rowIterator.hasNext()) {
						Row row = rowIterator.next();
						Iterator<Cell> cellIterator = row.cellIterator();
						if (cellIterator.hasNext()) {

							Cell cell1 = cellIterator.next();
							String value1 = getCellValueAsString(cell1);

							Cell cell2 = cellIterator.next();
							String value2 = getCellValueAsString(cell2);

							Cell cell3 = cellIterator.next();
							String value3 = getCellValueAsString(cell3);

							Cell cell4 = cellIterator.next();
							String value4 = getCellValueAsString(cell4);

							Cell cell5 = cellIterator.next();
							String value5 = getCellValueAsString(cell5);

							Cell cell6 = cellIterator.next();
							String value6 = getCellValueAsString(cell6);

							Cell cell7 = cellIterator.next();
							String value7 = getCellValueAsString(cell7);

							Cell cell8 = cellIterator.next();
							String value8 = getCellValueAsString(cell8);

							Cell cell9 = cellIterator.next();
							String value9 = getCellValueAsString(cell9);

							Cell cell10 = cellIterator.next();
							String value10 = getCellValueAsString(cell10);

							Cell cell11 = cellIterator.next();
							String value11 = getCellValueAsString(cell11);

							// Save Excel's extracted data to Database
							conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
							String query = "INSERT INTO Driver (name, father_name, id_card, license_no, "
									+ "license_exp_date, dob, contact, salary, bus_id,  LeavingDate, JoinDate) "
									+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

							PreparedStatement preparedStatement = conn.prepareStatement(query);
							preparedStatement.setString(1, value1);
							preparedStatement.setString(2, value2);
							preparedStatement.setString(3, value3);
							preparedStatement.setString(4, value4);
							preparedStatement.setString(5, value5);
							preparedStatement.setString(6, value6);
							preparedStatement.setString(7, value7);
							preparedStatement.setString(8, value8);
							preparedStatement.setString(9, value9);
							preparedStatement.setString(10, value10);
							preparedStatement.setString(11, value11);
							preparedStatement.executeUpdate();
							rowcount++;
							preparedStatement.close();

						} // if in while loop ends here

					} // while loop closed here
					showAlert("Data has been Imported.\n" + rowcount + " Rows Record Added", "Success");
					insertNotification("Admin Imported Data of " + rowcount + " Rows in Driver Data ");
					file.close();

				} catch (SQLException e) {
					showAlert("Data import failed.\n \n" + e, "Failed");

				} catch (NoSuchElementException e) {
					showAlert("Data has been Imported \n And saved in the database Successfully." + e, "Success");
				}
			} // if statement closed here

		} catch (Exception e) {
		}
	}

	@FXML
	void ImportFacultyData(ActionEvent event) {
		fileChooser();
		try {
			if (selectedFile != null) {
				FileInputStream file = new FileInputStream(selectedFile);
				try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
					XSSFSheet sheet = workbook.getSheetAt(0);
					Iterator<Row> rowIterator = sheet.iterator();

					if (rowIterator.hasNext()) {
						rowIterator.next();
					}

					while (rowIterator.hasNext()) {
						Row row = rowIterator.next();
						Iterator<Cell> cellIterator = row.cellIterator();
						if (cellIterator.hasNext()) {

							Cell cell1 = cellIterator.next();
							String value1 = getCellValueAsString(cell1);

							Cell cell2 = cellIterator.next();
							String value2 = getCellValueAsString(cell2);

							Cell cell3 = cellIterator.next();
							String value3 = getCellValueAsString(cell3);

							Cell cell4 = cellIterator.next();
							String value4 = getCellValueAsString(cell4);

							Cell cell5 = cellIterator.next();
							String value5 = getCellValueAsString(cell5);

							Cell cell6 = cellIterator.next();
							String value6 = getCellValueAsString(cell6);

							Cell cell7 = cellIterator.next();
							String value7 = getCellValueAsString(cell7);

							Cell cell8 = cellIterator.next();
							String value8 = getCellValueAsString(cell8);

							Cell cell9 = cellIterator.next();
							String value9 = getCellValueAsString(cell9);

							Cell cell10 = cellIterator.next();
							String value10 = getCellValueAsString(cell10);

							Cell cell11 = cellIterator.next();
							String value11 = getCellValueAsString(cell11);

							Cell cell12 = cellIterator.next();
							String value12 = getCellValueAsString(cell12);

							Cell cell13 = cellIterator.next();
							String value13 = getCellValueAsString(cell13);

							Cell cell14 = cellIterator.next();
							String value14 = getCellValueAsString(cell14);

							// Save Excel's extracted data to Database
							conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
							String query = "INSERT INTO Faculty (Name, FacultyType, FatherName, DOB, Designation, Department, "
									+ "Contact, CNIC, BankAccount, RouteID, BusID, Salary, Address, FeeStatus) "
									+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

							PreparedStatement preparedStatement = conn.prepareStatement(query);
							preparedStatement.setString(1, value1);
							preparedStatement.setString(2, value2);
							preparedStatement.setString(3, value3);
							preparedStatement.setString(4, value4);
							preparedStatement.setString(5, value5);
							preparedStatement.setString(6, value6);
							preparedStatement.setString(7, value7);
							preparedStatement.setString(8, value8);
							preparedStatement.setString(9, value9);
							preparedStatement.setString(10, value10);
							preparedStatement.setString(11, value11);
							preparedStatement.setString(12, value12);
							preparedStatement.setString(13, value13);
							preparedStatement.setString(14, value14);

							preparedStatement.executeUpdate();
							rowcount++;

							preparedStatement.close();

						} // if in while loop ends here

					} // while loop closed here
					showAlert("Data has been Imported.\n" + rowcount + " Rows Record Added", "Success");
					insertNotification("Admin Imported Data of " + rowcount + " Rows in Faculty Data ");
					file.close();

				} catch (SQLException e) {
					showAlert("Data import failed.\n \n" + e, "Failed");

				} catch (NoSuchElementException e) {
					showAlert("Data has been Imported \\n And saved in the database Successfully." + e, "Success");
				}

			} // if statement closed here

		} catch (Exception e) {
		}
	}

	@FXML
	void ImportFeeData(ActionEvent event) {

		fileChooser();
		try {
			if (selectedFile != null) {
				FileInputStream file = new FileInputStream(selectedFile);
				try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
					XSSFSheet sheet = workbook.getSheetAt(0);
					Iterator<Row> rowIterator = sheet.iterator();

					if (rowIterator.hasNext()) {
						rowIterator.next();
					}

					while (rowIterator.hasNext()) {
						Row row = rowIterator.next();
						Iterator<Cell> cellIterator = row.cellIterator();
						if (cellIterator.hasNext()) {

							Cell cell1 = cellIterator.next();
							String value1 = getCellValueAsString(cell1);

							Cell cell2 = cellIterator.next();
							String value2 = getCellValueAsString(cell2);

							Cell cell3 = cellIterator.next();
							String value3 = getCellValueAsString(cell3);

							Cell cell4 = cellIterator.next();
							String value4 = getCellValueAsString(cell4);

							Cell cell5 = cellIterator.next();
							String value5 = getCellValueAsString(cell5);

							Cell cell6 = cellIterator.next();
							String value6 = getCellValueAsString(cell6);

							Cell cell7 = cellIterator.next();
							String value7 = getCellValueAsString(cell7);

							Cell cell8 = cellIterator.next();
							String value8 = getCellValueAsString(cell8);

							Cell cell9 = cellIterator.next();
							String value9 = getCellValueAsString(cell9);

							Cell cell10 = cellIterator.next();
							String value10 = getCellValueAsString(cell10);

							Cell cell11 = cellIterator.next();
							String value11 = getCellValueAsString(cell11);

							Cell cell12 = cellIterator.next();
							String value12 = getCellValueAsString(cell12);

							Cell cell13 = cellIterator.next();
							String value13 = getCellValueAsString(cell13);

							// Save Excel's extracted data to Database
							conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
							String query = "INSERT INTO fee_records (amount, paidBy, date, customerName, cnic, "
									+ "discount, month, year, fatherName, department, NextDueDate, RemainingBalance, paymentPlan) "
									+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

							PreparedStatement preparedStatement = conn.prepareStatement(query);
							preparedStatement.setString(1, value1);
							preparedStatement.setString(2, value2);
							preparedStatement.setString(3, value3);
							preparedStatement.setString(4, value4);
							preparedStatement.setString(5, value5);
							preparedStatement.setString(6, value6);
							preparedStatement.setString(7, value7);
							preparedStatement.setString(8, value8);
							preparedStatement.setString(9, value9);
							preparedStatement.setString(10, value10);
							preparedStatement.setString(11, value11);
							preparedStatement.setString(12, value12);
							preparedStatement.setString(13, value13);

							preparedStatement.executeUpdate();
							rowcount++;

							preparedStatement.close();

						} // if in while loop ends here

					} // while loop closed here
					showAlert("Data has been Imported.\n" + rowcount + " Rows Record Added", "Success");
					insertNotification("Admin Imported Data of " + rowcount + " Rows in fee_records Data ");
					file.close();

				} catch (SQLException e) {
					showAlert("Data import failed.\n \n" + e, "Failed");

				} catch (NoSuchElementException e) {
					showAlert("Data has been Imported \\n And saved in the database Successfully." + e, "Success");
				}

			} // if statement closed here

		} catch (Exception e) {
		}
	}

	@FXML
	void ImportMaintenanceData(ActionEvent event) {
		fileChooser();
		try {
			if (selectedFile != null) {
				FileInputStream file = new FileInputStream(selectedFile);
				try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
					XSSFSheet sheet = workbook.getSheetAt(0);
					Iterator<Row> rowIterator = sheet.iterator();

					if (rowIterator.hasNext()) {
						rowIterator.next();
					}

					while (rowIterator.hasNext()) {
						Row row = rowIterator.next();
						Iterator<Cell> cellIterator = row.cellIterator();
						if (cellIterator.hasNext()) {

							Cell cell2 = cellIterator.next();
							String value2 = getCellValueAsString(cell2);

							Cell cell3 = cellIterator.next();
							String value3 = getCellValueAsString(cell3);

							Cell cell4 = cellIterator.next();
							String value4 = getCellValueAsString(cell4);

							Cell cell5 = cellIterator.next();
							String value5 = getCellValueAsString(cell5);

							Cell cell6 = cellIterator.next();
							String value6 = getCellValueAsString(cell6);

							Cell cell7 = cellIterator.next();
							String value7 = getCellValueAsString(cell7);

							Cell cell8 = cellIterator.next();
							String value8 = getCellValueAsString(cell8);

							// Save Excel's extracted data to Database
							conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
							String query = "INSERT INTO Maintenance (MaintenanceType, Amount, Description, VehicleRegID,"
									+ " WorkshopName, ScheduledDate, CompletionDate) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";
							PreparedStatement preparedStatement = conn.prepareStatement(query);
							preparedStatement.setString(1, value2);
							preparedStatement.setString(2, value3);
							preparedStatement.setString(3, value4);
							preparedStatement.setString(4, value5);
							preparedStatement.setString(5, value6);
							preparedStatement.setString(6, value7);
							preparedStatement.setString(7, value8);
							// Add more setString() statements for additional columns if needed
							preparedStatement.executeUpdate();
							rowcount++;
							preparedStatement.close();

						} // if in while loop ends here

					} // while loop closed here
					showAlert("Data has been Imported.\n" + rowcount + " Rows Record Added", "Success");
					insertNotification("Admin Imported Data of " + rowcount + " Rows in Maintenance Data ");

					file.close();

				} catch (SQLException e) {
					showAlert("Data import failed.\n \n" + e, "Failed");

				} catch (NoSuchElementException e) {
					showAlert("Data has been Imported \\n And saved in the database Successfully." + e, "Success");
				}

			} // if statement closed here

		} catch (Exception e) {
		}

	}

	@FXML
	void ImportReservationData(ActionEvent event) {

		fileChooser();
		try {
			if (selectedFile != null) {
				FileInputStream file = new FileInputStream(selectedFile);
				try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
					XSSFSheet sheet = workbook.getSheetAt(0);
					Iterator<Row> rowIterator = sheet.iterator();

					if (rowIterator.hasNext()) {
						rowIterator.next();
					}

					while (rowIterator.hasNext()) {
						Row row = rowIterator.next();
						Iterator<Cell> cellIterator = row.cellIterator();
						if (cellIterator.hasNext()) {

							Cell cell1 = cellIterator.next();
							String value1 = getCellValueAsString(cell1);

							Cell cell2 = cellIterator.next();
							String value2 = getCellValueAsString(cell2);

							Cell cell3 = cellIterator.next();
							String value3 = getCellValueAsString(cell3);

							Cell cell4 = cellIterator.next();
							String value4 = getCellValueAsString(cell4);

							Cell cell5 = cellIterator.next();
							String value5 = getCellValueAsString(cell5);

							Cell cell6 = cellIterator.next();
							String value6 = getCellValueAsString(cell6);

							Cell cell7 = cellIterator.next();
							String value7 = getCellValueAsString(cell7);

							Cell cell8 = cellIterator.next();
							String value8 = getCellValueAsString(cell8);

							Cell cell9 = cellIterator.next();
							String value9 = getCellValueAsString(cell9);

							Cell cell10 = cellIterator.next();
							String value10 = getCellValueAsString(cell10);

							Cell cell11 = cellIterator.next();
							String value11 = getCellValueAsString(cell11);

							// Save Excel's extracted data to Database
							conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
							String query = "INSERT INTO Reservation (DestinationArrival, DestinationDept, "
									+ "DestinationName, DriverID, EventHolderName, EventName, EventType, ScheduledDate, "
									+ "UniversityArrival, UniversityDept, VehicleRegNo) "
									+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

							PreparedStatement preparedStatement = conn.prepareStatement(query);
							preparedStatement.setString(1, value1);
							preparedStatement.setString(2, value2);
							preparedStatement.setString(3, value3);
							preparedStatement.setString(4, value4);
							preparedStatement.setString(5, value5);
							preparedStatement.setString(6, value6);
							preparedStatement.setString(7, value7);
							preparedStatement.setString(8, value8);
							preparedStatement.setString(9, value9);
							preparedStatement.setString(10, value10);
							preparedStatement.setString(11, value11);

							preparedStatement.executeUpdate();
							rowcount++;

							preparedStatement.close();

						} // if in while loop ends here

					} // while loop closed here
					showAlert("Data has been Imported.\n" + rowcount + " Rows Record Added", "Success");
					insertNotification("Admin Imported Data of " + rowcount + " Rows in Reservation Data ");

					file.close();

				} catch (SQLException e) {
					showAlert("Data import failed.\n \n" + e, "Failed");

				} catch (NoSuchElementException e) {
					showAlert("Data has been Imported \\n And saved in the database Successfully." + e, "Success");
				}

			} // if statement closed here

		} catch (Exception e) {
		}
	}

	@FXML
	void ImportRoutesData(ActionEvent event) {

		fileChooser();
		try {
			if (selectedFile != null) {
				FileInputStream file = new FileInputStream(selectedFile);
				try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
					XSSFSheet sheet = workbook.getSheetAt(0);
					Iterator<Row> rowIterator = sheet.iterator();

					if (rowIterator.hasNext()) {
						rowIterator.next();
					}

					while (rowIterator.hasNext()) {
						Row row = rowIterator.next();
						Iterator<Cell> cellIterator = row.cellIterator();
						if (cellIterator.hasNext()) {

							Cell cell1 = cellIterator.next();
							String value1 = getCellValueAsString(cell1);

							Cell cell2 = cellIterator.next();
							String value2 = getCellValueAsString(cell2);

							Cell cell3 = cellIterator.next();
							String value3 = getCellValueAsString(cell3);

							Cell cell4 = cellIterator.next();
							String value4 = getCellValueAsString(cell4);

							Cell cell5 = cellIterator.next();
							String value5 = getCellValueAsString(cell5);

							Cell cell6 = cellIterator.next();
							String value6 = getCellValueAsString(cell6);

							Cell cell7 = cellIterator.next();
							String value7 = getCellValueAsString(cell7);

							Cell cell8 = cellIterator.next();
							String value8 = getCellValueAsString(cell8);

							Cell cell9 = cellIterator.next();
							String value9 = getCellValueAsString(cell9);

							Cell cell10 = cellIterator.next();
							String value10 = getCellValueAsString(cell10);

							Cell cell11 = cellIterator.next();
							String value11 = getCellValueAsString(cell11);

							// Save Excel's extracted data to Database
							conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
							String query = "INSERT INTO routes (bus_id, conductor_id, driver_id, end_name, fairs, occupation, "
									+ "pickup_time, reach_time, start_name, route_name, stops_name) "
									+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

							PreparedStatement preparedStatement = conn.prepareStatement(query);
							preparedStatement.setString(1, value1);
							preparedStatement.setString(2, value2);
							preparedStatement.setString(3, value3);
							preparedStatement.setString(4, value4);
							preparedStatement.setString(5, value5);
							preparedStatement.setString(6, value6);
							preparedStatement.setString(7, value7);
							preparedStatement.setString(8, value8);
							preparedStatement.setString(9, value9);
							preparedStatement.setString(10, value10);
							preparedStatement.setString(11, value11);

							preparedStatement.executeUpdate();
							rowcount++;

							preparedStatement.close();

						} // if in while loop ends here

					} // while loop closed here
					showAlert("Data has been Imported.\n" + rowcount + " Rows Record Added", "Success");
					insertNotification("Admin Imported Data of " + rowcount + " Rows in routes Data ");
					file.close();

				} catch (SQLException e) {
					showAlert("Data import failed.\n \n" + e, "Failed");

				} catch (NoSuchElementException e) {
					showAlert("Data has been Imported \\n And saved in the database Successfully." + e, "Success");
				}

			} // if statement closed here

		} catch (Exception e) {
		}
	}

	@FXML
	void ImportStudentsData(ActionEvent event) {

		fileChooser();
		try {
			if (selectedFile != null) {
				FileInputStream file = new FileInputStream(selectedFile);
				try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
					XSSFSheet sheet = workbook.getSheetAt(0);
					Iterator<Row> rowIterator = sheet.iterator();
					if (rowIterator.hasNext()) {
						rowIterator.next();
					}

					while (rowIterator.hasNext()) {
						Row row = rowIterator.next();
						Iterator<Cell> cellIterator = row.cellIterator();

						if (cellIterator.hasNext()) {

							Cell cell1 = cellIterator.next();
							String value1 = getCellValueAsString(cell1);

							Cell cell2 = cellIterator.next();
							String value2 = getCellValueAsString(cell2);

							Cell cell3 = cellIterator.next();
							String value3 = getCellValueAsString(cell3);

							Cell cell4 = cellIterator.next();
							String value4 = getCellValueAsString(cell4);

							Cell cell5 = cellIterator.next();
							String value5 = getCellValueAsString(cell5);

							Cell cell6 = cellIterator.next();
							String value6 = getCellValueAsString(cell6);

							Cell cell7 = cellIterator.next();
							String value7 = getCellValueAsString(cell7);

							Cell cell8 = cellIterator.next();
							String value8 = getCellValueAsString(cell8);

							Cell cell9 = cellIterator.next();
							String value9 = getCellValueAsString(cell9);

							Cell cell10 = cellIterator.next();
							String value10 = getCellValueAsString(cell10);

							Cell cell11 = cellIterator.next();
							String value11 = getCellValueAsString(cell11);

							Cell cell12 = cellIterator.next();
							String value12 = getCellValueAsString(cell12);

							Cell cell13 = cellIterator.next();
							String value13 = getCellValueAsString(cell13);

							Cell cell14 = cellIterator.next();
							String value14 = getCellValueAsString(cell14);

							// Save Excel's extracted data to Database
							conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
							String query = "INSERT INTO students (SAPID, Name, FatherName, DOB, Semester, "
									+ "Department, Contact, CNIC, PicknDropPoint, RouteID, BusID, Address, FeeStatus, gender) "
									+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

							PreparedStatement preparedStatement = conn.prepareStatement(query);
							preparedStatement.setString(1, value1);
							preparedStatement.setString(2, value2);
							preparedStatement.setString(3, value3);
							preparedStatement.setString(4, value4);
							preparedStatement.setString(5, value5);
							preparedStatement.setString(6, value6);
							preparedStatement.setString(7, value7);
							preparedStatement.setString(8, value8);
							preparedStatement.setString(9, value9);
							preparedStatement.setString(10, value10);
							preparedStatement.setString(11, value11);
							preparedStatement.setString(12, value12);
							preparedStatement.setString(13, value13);
							preparedStatement.setString(14, value14);

							preparedStatement.executeUpdate();
							rowcount++;

							preparedStatement.close();

						} // if in while loop ends here

					} // while loop closed here
					showAlert("Data has been Imported.\n" + rowcount + " Rows Record Added", "Success");
					insertNotification("Admin Imported Data of " + rowcount + " Rows in students Data ");
					file.close();

				} catch (SQLException e) {
					showAlert("Data import failed.\n \n" + e, "Failed");

				} catch (NoSuchElementException e) {
					showAlert("Data has been Imported \\n And saved in the database Successfully." + e, "Success");
				}

			} // if statement closed here

		} catch (Exception e) {
		}
	}

	@FXML
	void ImportVehiclesData(ActionEvent event) {

		fileChooser();
		try {
			if (selectedFile != null) {
				FileInputStream file = new FileInputStream(selectedFile);
				try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
					XSSFSheet sheet = workbook.getSheetAt(0);
					Iterator<Row> rowIterator = sheet.iterator();

					if (rowIterator.hasNext()) {
						rowIterator.next();
					}

					while (rowIterator.hasNext()) {
						Row row = rowIterator.next();
						Iterator<Cell> cellIterator = row.cellIterator();
						if (cellIterator.hasNext()) {

							Cell cell2 = cellIterator.next();
							String value2 = getCellValueAsString(cell2);

							Cell cell3 = cellIterator.next();
							String value3 = getCellValueAsString(cell3);

							Cell cell4 = cellIterator.next();
							String value4 = getCellValueAsString(cell4);

							Cell cell5 = cellIterator.next();
							String value5 = getCellValueAsString(cell5);

							Cell cell6 = cellIterator.next();
							String value6 = getCellValueAsString(cell6);

							Cell cell7 = cellIterator.next();
							String value7 = getCellValueAsString(cell7);

							Cell cell8 = cellIterator.next();
							String value8 = getCellValueAsString(cell8);

							Cell cell9 = cellIterator.next();
							String value9 = getCellValueAsString(cell9);

							Cell cell10 = cellIterator.next();
							String value10 = getCellValueAsString(cell10);

							Cell cell11 = cellIterator.next();
							String value11 = getCellValueAsString(cell11);

							Cell cell12 = cellIterator.next();
							String value12 = getCellValueAsString(cell12);

							Cell cell13 = cellIterator.next();
							String value13 = getCellValueAsString(cell13);

							Cell cell14 = cellIterator.next();
							String value14 = getCellValueAsString(cell14);

							Cell cell15 = cellIterator.next();
							String value15 = getCellValueAsString(cell15);

							// Save Excel's extracted data to Database
							conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
							String query = "INSERT INTO Vehicles (vehicle_type, vehicle_model,  "
									+ "registration_number , vehicle_capacity  , vehicle_occupation ,  accidental_status"
									+ "  , fuel_efficiency ,  driver_id  , route_id , token_status,"
									+ " token_number ,  token_type  , token_issue_date , token_expiry_date ) "
									+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

							PreparedStatement preparedStatement = conn.prepareStatement(query);
							preparedStatement.setString(1, value2);
							preparedStatement.setString(2, value3);
							preparedStatement.setString(3, value4);
							preparedStatement.setString(4, value5);
							preparedStatement.setString(5, value6);
							preparedStatement.setString(6, value7);
							preparedStatement.setString(7, value8);
							preparedStatement.setString(8, value9);
							preparedStatement.setString(9, value10);
							preparedStatement.setString(10, value11);
							preparedStatement.setString(11, value12);
							preparedStatement.setString(12, value13);
							preparedStatement.setString(13, value14);
							preparedStatement.setString(14, value15);
							// Add more setString() statements for additional columns if needed
							preparedStatement.executeUpdate();
							rowcount++;
							preparedStatement.close();

						} // if in while loop ends here

					} // while loop closed here
					showAlert("Data has been Imported.\n" + rowcount + " Rows Record Added", "Success");
					insertNotification("Admin Imported Data of " + rowcount + " Rows in Vehicle Data ");
					file.close();

				} catch (SQLException e) {
					showAlert("Data import failed.\n \n" + e, "Failed");

				} catch (NoSuchElementException e) {
					showAlert("Data has been Imported \\n And saved in the database Successfully." + e, "Success");
				}

			} // if statement closed here

		} catch (

		Exception e) {
		}
	}

	@FXML
	void ImportExpenseData(ActionEvent event) {

		fileChooser();
		try {
			if (selectedFile != null) {
				FileInputStream file = new FileInputStream(selectedFile);
				try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
					XSSFSheet sheet = workbook.getSheetAt(0);
					Iterator<Row> rowIterator = sheet.iterator();

					if (rowIterator.hasNext()) {
						rowIterator.next();
					}

					while (rowIterator.hasNext()) {
						Row row = rowIterator.next();
						Iterator<Cell> cellIterator = row.cellIterator();
						if (cellIterator.hasNext()) {

							Cell cell1 = cellIterator.next();
							String value1 = getCellValueAsString(cell1);

							Cell cell2 = cellIterator.next();
							String value2 = getCellValueAsString(cell2);

							Cell cell3 = cellIterator.next();
							String value3 = getCellValueAsString(cell3);

							Cell cell4 = cellIterator.next();
							String value4 = getCellValueAsString(cell4);

							Cell cell5 = cellIterator.next();
							String value5 = getCellValueAsString(cell5);

							Cell cell6 = cellIterator.next();
							String value6 = getCellValueAsString(cell6);

							Cell cell7 = cellIterator.next();
							String value7 = getCellValueAsString(cell7);

							// Save Excel's extracted data to Database
							conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
							String query = "INSERT INTO Other_Expense ( Vehicle_RegNo,  Expense_type, Amount, Purchase_date,  "
									+ "Expiry_Date,  Payment_Method, Remarks ) VALUES ( ?, ?, ?, ?,?, ?, ? );";

							PreparedStatement preparedStatement = conn.prepareStatement(query);
							preparedStatement.setString(1, value1);
							preparedStatement.setString(2, value2);
							preparedStatement.setString(3, value3);
							preparedStatement.setString(4, value4);
							preparedStatement.setString(5, value5);
							preparedStatement.setString(6, value6);
							preparedStatement.setString(7, value7);

							preparedStatement.executeUpdate();
							rowcount++;

							preparedStatement.close();

						} // if in while loop ends here

					} // while loop closed here
					showAlert("Data has been Imported.\n" + rowcount + " Rows Record Added", "Success");
					insertNotification("Admin Imported Data of " + rowcount + " Rows in ExpenseData Data ");
					file.close();

				} catch (SQLException e) {
					showAlert("Data import failed.\n \n" + e, "Failed");

				} catch (NoSuchElementException e) {
					showAlert("Data has been Imported \\n And saved in the database Successfully." + e, "Success");
				}

			} // if statement closed here

		} catch (Exception e) {
		}
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

}
