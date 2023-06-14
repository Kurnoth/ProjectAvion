package ProjectAvion.src;

public class Map {
	private Pos positionAirport;

	public Map(Pos p) {
		this.positionAirport = p;

		while (true) {
			System.out.println("===");
			Avion a;
			while ((a = Data.getNextAircraft()) != null) {
				//if (positionAirport.closeAirport(a.getPosition())) {
					System.out.println(a);
					Data.setI(Data.getI() + 1);
				//}
			}
			try {
				Thread.sleep(6000);
				Data.setI(0);
				Data.updatePosition();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
