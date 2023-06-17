package ProjectAvion.src;

import java.io.Serializable;

public class Avion implements Serializable{
	private int flightNumber;
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

	public String toString() {
		return "flightNumber:" + flightNumber + " - latitude : " + latitude
				+ " - longitude : " + longitude + " - vitesse : " + vitesse
				+ " - altitude : " + altitude + " - cap : " + cap;
	}
	public int getFlightNumber() {
		return flightNumber;
	}
	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}
	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}
	public void setCap(int cap) {
		this.cap = cap;
	}
	
	
	
}

