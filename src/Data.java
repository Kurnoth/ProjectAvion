package ProjectAvion.src;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Data {
	static private int i = 0;
	/*static double tab[][] = { 
			{ 100, 48.2512, 3.7545, 150, 3000, 120 },
			{ 200, 48.5452, 3.6615, 140, 3500, 240 },
			{ 300, 47.5655, 2.6452, 180, 6000, 110 },
			{ 400, 48.5584, 3.6652, 165, 2000, 90 },
			{ 500, 18.5452, 2.7010, 160, 1200, 40 } };
		*/
	private static ArrayList<Avion> listeAvions = new ArrayList<Avion>();


	public static ArrayList<Avion> initAvions() {
		for ( int i = 0; i < ThreadLocalRandom.current().nextInt(3, 7 + 1); i++) {
			Avion a = new Avion((listeAvions.size() + 1) * 100,
				Math.round(ThreadLocalRandom.current().nextDouble(47, 49 + 1) * 10000d) / 10000d,
				Math.round(ThreadLocalRandom.current().nextDouble(2, 4 + 1) * 10000d) / 10000d,
				ThreadLocalRandom.current().nextInt(100, 200 + 1),
			    ThreadLocalRandom.current().nextInt(1000, 10000 + 1),
				ThreadLocalRandom.current().nextInt(0, 360 + 1));
			listeAvions.add(a);
		}
		System.out.println("StartInit");
		for (Avion a : listeAvions) {
			System.out.println(a);
		}
		System.out.println("EndInit");
		return listeAvions;		
	}

	public static Avion getNextAircraft() {
		
		if (listeAvions.size() == 0)
			listeAvions = initAvions();		
		if (listeAvions.size() - 1 > i) {
			i++;
			return listeAvions.get(i);
		}
		return null;
	}

	public static void setI(int i) {
		Data.i = i;
	}

}
