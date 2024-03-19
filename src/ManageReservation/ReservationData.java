package ManageReservation;

public class ReservationData {
	private int reservationID;
	private String destinationArrival;
	private String destinationDept;
	private String destinationName;
	private String driverID;
	private String eventHolderName;
	private String eventName;
	private String eventType;
	private String scheduledDate;
	private String universityArrival;
	private String universityDept;
	private String vehicleRegNo;

	public ReservationData(int reservationID, String destinationArrival, String destinationDept, String destinationName,
			String driverID, String eventHolderName, String eventName, String eventType, String scheduledDate,
			String universityArrival, String universityDept, String vehicleRegNo) {
		this.reservationID = reservationID;
		this.destinationArrival = destinationArrival;
		this.destinationDept = destinationDept;
		this.destinationName = destinationName;
		this.driverID = driverID;
		this.eventHolderName = eventHolderName;
		this.eventName = eventName;
		this.eventType = eventType;
		this.scheduledDate = scheduledDate;
		this.universityArrival = universityArrival;
		this.universityDept = universityDept;
		this.vehicleRegNo = vehicleRegNo;
	}

	// Getters for the properties
	public int getReservationID() {
		return reservationID;
	}

	public String getDestinationArrival() {
		return destinationArrival;
	}

	public String getDestinationDept() {
		return destinationDept;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public String getDriverID() {
		return driverID;
	}

	public String getEventHolderName() {
		return eventHolderName;
	}

	public String getEventName() {
		return eventName;
	}

	public String getEventType() {
		return eventType;
	}

	public String getScheduledDate() {
		return scheduledDate;
	}

	public String getUniversityArrival() {
		return universityArrival;
	}

	public String getUniversityDept() {
		return universityDept;
	}

	public String getVehicleRegNo() {
		return vehicleRegNo;
	}
}
