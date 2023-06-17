package ProjectAvion.src;

import java.util.ArrayList;

public class Map {
	private Pos positionAirport;

	public Map(Pos p, ArrayList<Avion> avions) {
		this.positionAirport = p;

		System.out.println("===");
		for (Avion a : avions) {
			if (positionAirport.closeAirport(a.getPosition()))
				System.out.println(a);
		}
	}
}
