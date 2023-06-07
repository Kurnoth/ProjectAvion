package ProjectAvion.src;

public class Avion {
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
	
	// il faut que j'implémente ici une méthode qui doit faire avancer l'avion après 15 secondes 
	// peu etre c'est mieu de donner le temps en paramètre ... 
	
	
	
}

