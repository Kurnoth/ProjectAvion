package mapper;
public class Map {
	private Pos positionAirport;

	public Map(Pos p) {
		this.positionAirport = p;

		while (true) {
			System.out.println("===");
			Avion a;
			while ((a = Data.getNextAircraft()) != null) {
				if (positionAirport.closeAirport(a.getPosition())) {
					System.out.println(a);
				}
			}
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
