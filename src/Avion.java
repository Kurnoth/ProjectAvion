package ProjectAvion.src;

import java.io.Serializable;

public class Avion implements Serializable{

	private final int flightNumber;
	private double latitude;
	private double longitude;
	private int vitesse;
	private int altitude;
	private int cap;
	
	public Avion(int fn, double lat, double lon, int v, int alt, int c) {
		this.flightNumber = fn;
		this.latitude = lat;
		this.longitude = lon;
		this.vitesse = v;
		this.altitude = alt;
		this.cap = c;
	}

	public Pos getPosition() {
		return new Pos(this.latitude, this.longitude);
	}

	@Override
	public String toString() {
		return "flightNumber:" + flightNumber + " - latitude : " + latitude
				+ " - longitude : " + longitude + " - vitesse : " + vitesse
				+ " - altitude : " + altitude + " - cap : " + cap;
	}

	public int getFlightNumber() {
		return flightNumber;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public int getCap() {
		return cap;
	}

	public void setCap(int cap) {
		this.cap = cap;
	}
}

