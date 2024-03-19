package ManageVehicle;

public class VehicleData {

	private String accidentalStatus;
	private String driverId;
	private String fuelEfficiency;
	private String registrationNumber;
	private String routeId;
	private String tokenExpiryDate;
	private String tokenIssueDate;
	private String tokenNumber;
	private String tokenStatus;
	private String tokenType;
	private String vehicleCapacity;
	private int vehicleId;
	private String vehicleModel;
	private String vehicleOccupation;
	private String vehicleType;

	public VehicleData(String accidentalStatus, String driverId, String fuelEfficiency, String registrationNumber,
			String routeId, String tokenExpiryDate, String tokenIssueDate, String tokenNumber, String tokenStatus,
			String tokenType, String vehicleCapacity, int vehicleId, String vehicleModel, String vehicleOccupation,
			String vehicleType) {
		this.accidentalStatus = accidentalStatus;
		this.driverId = driverId;
		this.fuelEfficiency = fuelEfficiency;
		this.registrationNumber = registrationNumber;
		this.routeId = routeId;
		this.tokenExpiryDate = tokenExpiryDate;
		this.tokenIssueDate = tokenIssueDate;
		this.tokenNumber = tokenNumber;
		this.tokenStatus = tokenStatus;
		this.tokenType = tokenType;
		this.vehicleCapacity = vehicleCapacity;
		this.vehicleId = vehicleId;
		this.vehicleModel = vehicleModel;
		this.vehicleOccupation = vehicleOccupation;
		this.vehicleType = vehicleType;
	}

	public String getAccidentalStatus() {
		return accidentalStatus;
	}

	public String getDriverId() {
		return driverId;
	}

	public String getFuelEfficiency() {
		return fuelEfficiency;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public String getRouteId() {
		return routeId;
	}

	public String getTokenExpiryDate() {
		return tokenExpiryDate;
	}

	public String getTokenIssueDate() {
		return tokenIssueDate;
	}

	public String getTokenNumber() {
		return tokenNumber;
	}

	public String getTokenStatus() {
		return tokenStatus;
	}

	public String getTokenType() {
		return tokenType;
	}

	public String getVehicleCapacity() {
		return vehicleCapacity;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public String getVehicleOccupation() {
		return vehicleOccupation;
	}

	public String getVehicleType() {
		return vehicleType;
	}

}
