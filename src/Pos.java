package ProjectAvion.src;

public class Pos {

	private double latitude;
	private double longitude;
	private static double distanceMax = 1; //30km

	public Pos(double lat, double lon) {
		this.latitude = lat;
		this.longitude = lon;
	}

	public boolean closeAirport(Pos p) {
		if (((Math.abs(p.getLatitude() - this.latitude) > distanceMax) && (Math.abs(p.getLongitude() - this.longitude) > distanceMax)) || ((Math.abs(p.getLatitude() - this.latitude) < distanceMax) && (Math.abs(p.getLongitude() - this.longitude) < distanceMax))) {
			return true;
		}
		return false;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public static double getDistanceMax() {
		return distanceMax;
	}
}
