package ManageRoute;

public class RouteData {

	private int routeId;
	private String routeName;
	private String startPoint;
	private String endPoint;
	private String busId;
	private String conductorId;
	private String driverId;
	private String fairs;
	private String occupation;
	private String pickupTime;
	private String reachTime;
	private String stopsName;

	public RouteData(int routeId, String routeName, String startPoint, String endPoint, String pickupTime,
			String reachTime, String fairs, String occupation, String busId, String driverId, String conductorId,
			String stopsName) {
		this.routeId = routeId;
		this.routeName = routeName;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.busId = busId;
		this.conductorId = conductorId;
		this.driverId = driverId;
		this.fairs = fairs;
		this.occupation = occupation;
		this.pickupTime = pickupTime;
		this.reachTime = reachTime;
		this.stopsName = stopsName;
	}

	public int getRouteId() {
		return routeId;
	}

	public String getRouteName() {
		return routeName;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public String getBusId() {
		return busId;
	}

	public String getConductorId() {
		return conductorId;
	}

	public String getDriverId() {
		return driverId;
	}

	public String getFairs() {
		return fairs;
	}

	public String getOccupation() {
		return occupation;
	}

	public String getPickupTime() {
		return pickupTime;
	}

	public String getReachTime() {
		return reachTime;
	}

	public String getStopsName() {
		return stopsName;
	}

}
