package VehicleMaintenance;

public class MaintenanceData {
	private int MaintenanceID;
	private String MaintenanceType, WorkshopName, Amount, Description, VehicleRegID, ScheduledDate, CompletionDate;

	public MaintenanceData(int MaintenanceID, String MaintenanceType, String WorkshopName, String Amount,
			String Description, String VehicleRegID, String ScheduledDate, String CompletionDate) {
		this.MaintenanceID = MaintenanceID;
		this.MaintenanceType = MaintenanceType;
		this.WorkshopName = WorkshopName;
		this.Amount = Amount;
		this.Description = Description;
		this.VehicleRegID = VehicleRegID;
		this.ScheduledDate = ScheduledDate;
		this.CompletionDate = CompletionDate;
	}

	public int getMaintenanceID() {
		return MaintenanceID;
	}

	public String getMaintenanceType() {
		return MaintenanceType;
	}

	public String getWorkshopName() {
		return WorkshopName;
	}

	public String getAmount() {
		return Amount;
	}

	public String getDescription() {
		return Description;
	}

	public String getVehicleRegID() {
		return VehicleRegID;
	}

	public String getScheduledDate() {
		return ScheduledDate;
	}

	public String getCompletionDate() {
		return CompletionDate;
	}

}
