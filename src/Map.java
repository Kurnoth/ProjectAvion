package ProjectAvion.src;

import java.util.ArrayList;

public class Map {
	private Pos positionAirport;
	public ArrayList<Avion> closeAvion = new ArrayList<Avion>();

	public Map(Pos p) {
		this.positionAirport = p;
	}

	public void display(ArrayList<Avion> avions) {
		System.out.println("===");
		for (Avion a : avions) {
			if (positionAirport.closeAirport(a.getPosition())) {
				closeAvion.add(a);
				System.out.println(a);
			}
		}
	}

	public Pos getPositionAirport() {
		return positionAirport;
	}

	public ArrayList<Avion> getCloseAvion() {
		return closeAvion;
	}
	
}
